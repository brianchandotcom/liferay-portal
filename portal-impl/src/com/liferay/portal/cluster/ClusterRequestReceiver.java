/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.cluster;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.cluster.Address;
import com.liferay.portal.kernel.cluster.ClusterException;
import com.liferay.portal.kernel.cluster.ClusterMessageType;
import com.liferay.portal.kernel.cluster.ClusterNode;
import com.liferay.portal.kernel.cluster.ClusterNodeResponse;
import com.liferay.portal.kernel.cluster.ClusterRequest;
import com.liferay.portal.kernel.cluster.FutureClusterResponses;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.jgroups.Channel;
import org.jgroups.Message;
import org.jgroups.View;

/**
 * @author Michael C. Han
 * @author Tina Tian
 */
public class ClusterRequestReceiver extends BaseReceiver {

	public ClusterRequestReceiver(ClusterExecutorImpl clusterExecutorImpl) {
		_countDownLatch = new CountDownLatch(1);
		_clusterExecutorImpl = clusterExecutorImpl;
	}

	public void openLatch() {
		_countDownLatch.countDown();
	}

	@Override
	public void receive(Message message) {
		try {
			_countDownLatch.await();
		}
		catch (InterruptedException ie) {
			_log.error(
				"Latch opened prematurely by interruption. Dependence may " +
					"not be ready.");
		}

		Channel controlChannel = _clusterExecutorImpl.getControlChannel();

		org.jgroups.Address localAddress = controlChannel.getAddress();

		Object obj = message.getObject();

		if (obj == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("Message content is null");
			}

			return;
		}

		org.jgroups.Address sourceAddress = message.getSrc();

		if (localAddress.equals(sourceAddress)) {
			boolean isProcessed = processLocalMessage(obj);

			if (isProcessed) {
				return;
			}
		}

		if (obj instanceof ClusterRequest) {
			ClusterRequest clusterRequest = (ClusterRequest)obj;

			processClusterRequest(clusterRequest, sourceAddress, localAddress);
		}
		else if (obj instanceof ClusterNodeResponse) {
			ClusterNodeResponse clusterNodeResponse = (ClusterNodeResponse)obj;

			processClusterResponse(clusterNodeResponse, sourceAddress);
		}
		else if (_log.isWarnEnabled()) {
			_log.warn(
				"Unable to process message content of type " + obj.getClass());
		}
	}

	@Override
	public void viewAccepted(View view) {
		super.viewAccepted(view);

		if (_lastView == null) {
			_lastView = view;

			return;
		}

		List<Address> departAddresses = getDepartAddresses(view);
		List<Address> newAddresses = getNewAddresses(view);

		_lastView = view;

		try {
			_countDownLatch.await();
		}
		catch (InterruptedException ie) {
			_log.error(
				"Latch opened prematurely by interruption. Dependence may " +
					"not be ready.");
		}

		if (!newAddresses.isEmpty()) {
			_clusterExecutorImpl.sendNotifyRequest();
		}

		if (!departAddresses.isEmpty()) {
			_clusterExecutorImpl.memberRemoved(departAddresses);
		}
	}

	protected List<Address> getDepartAddresses(View view) {
		List<org.jgroups.Address> currentJGroupsAddresses = view.getMembers();
		List<org.jgroups.Address> lastJGroupsAddresses = _lastView.getMembers();

		List<org.jgroups.Address> departJGroupsAddresses =
			new ArrayList<org.jgroups.Address>(lastJGroupsAddresses);

		departJGroupsAddresses.removeAll(currentJGroupsAddresses);

		if (departJGroupsAddresses.isEmpty()) {
			return Collections.emptyList();
		}

		List<Address> departAddresses = new ArrayList<Address>(
			departJGroupsAddresses.size());

		for (org.jgroups.Address departJGroupsAddress :
				departJGroupsAddresses) {

			Address departAddress = new AddressImpl(departJGroupsAddress);

			departAddresses.add(departAddress);
		}

		return departAddresses;
	}

	protected List<Address> getNewAddresses(View view) {
		List<org.jgroups.Address> currentJGroupsAddresses = view.getMembers();
		List<org.jgroups.Address> lastJGroupsAddresses = _lastView.getMembers();

		List<org.jgroups.Address> newJGroupsAddresses =
			new ArrayList<org.jgroups.Address>(currentJGroupsAddresses);

		newJGroupsAddresses.removeAll(lastJGroupsAddresses);

		if (newJGroupsAddresses.isEmpty()) {
			return Collections.emptyList();
		}

		List<Address> newAddresses = new ArrayList<Address>(
			newJGroupsAddresses.size());

		for (org.jgroups.Address newJGroupsAddress : newJGroupsAddresses) {
			Address newAddress = new AddressImpl(newJGroupsAddress);

			newAddresses.add(newAddress);
		}

		return newAddresses;
	}

	protected Object invoke(
			String servletContextName, String beanIdentifier,
			MethodHandler methodHandler)
		throws Exception {

		if (servletContextName == null) {
			if (Validator.isNull(beanIdentifier)) {
				return methodHandler.invoke(true);
			}
			else {
				Object bean = PortalBeanLocatorUtil.locate(beanIdentifier);

				return methodHandler.invoke(bean);
			}
		}

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			ClassLoader classLoader =
				(ClassLoader)PortletBeanLocatorUtil.locate(
					servletContextName, "portletClassLoader");

			currentThread.setContextClassLoader(classLoader);

			if (Validator.isNull(beanIdentifier)) {
				return methodHandler.invoke(true);
			}
			else {
				Object bean = PortletBeanLocatorUtil.locate(
					servletContextName, beanIdentifier);

				return methodHandler.invoke(bean);
			}
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	protected void processClusterRequest(
		ClusterRequest clusterRequest, org.jgroups.Address sourceAddress,
		org.jgroups.Address localAddress) {

		ClusterMessageType clusterMessageType =
			clusterRequest.getClusterMessageType();

		ClusterNodeResponse clusterNodeResponse = new ClusterNodeResponse();

		Address address = new AddressImpl(localAddress);

		clusterNodeResponse.setAddress(address);

		ClusterNode localClusterNode =
			_clusterExecutorImpl.getLocalClusterNode();

		clusterNodeResponse.setClusterNode(localClusterNode);

		if (clusterMessageType.equals(ClusterMessageType.NOTIFY) ||
			clusterMessageType.equals(ClusterMessageType.UPDATE)) {

			ClusterNode originatingClusterNode =
				clusterRequest.getOriginatingClusterNode();

			if (originatingClusterNode != null) {
				_clusterExecutorImpl.memberJoined(
					new AddressImpl(sourceAddress), originatingClusterNode);

				clusterNodeResponse.setClusterMessageType(clusterMessageType);
			}
			else {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Content of notify message does not contain cluster " +
							"node information");
				}

				return;
			}
		}
		else {
			clusterNodeResponse.setClusterMessageType(
				ClusterMessageType.EXECUTE);
			clusterNodeResponse.setMulticast(clusterRequest.isMulticast());
			clusterNodeResponse.setUuid(clusterRequest.getUuid());

			MethodHandler methodHandler = clusterRequest.getMethodHandler();

			if (methodHandler != null) {
				try {
					ClusterInvokeThreadLocal.setEnabled(false);

					Object returnValue = invoke(
						clusterRequest.getServletContextName(),
						clusterRequest.getBeanIdentifier(), methodHandler);

					if (returnValue instanceof Serializable) {
						clusterNodeResponse.setResult(returnValue);
					}
					else if (returnValue != null) {
						clusterNodeResponse.setException(
							new ClusterException(
								"Return value is not serializable"));
					}
				}
				catch (Exception e) {
					clusterNodeResponse.setException(e);

					_log.error("Failed to invoke method " + methodHandler, e);
				}
				finally {
					ClusterInvokeThreadLocal.setEnabled(true);
				}
			}
			else {
				clusterNodeResponse.setException(
					new ClusterException(
						"Payload is not of type " +
							MethodHandler.class.getName()));
			}
		}

		Channel controlChannel = _clusterExecutorImpl.getControlChannel();

		try {
			controlChannel.send(sourceAddress, clusterNodeResponse);
		}
		catch (Exception e) {
			_log.error(
				"Unable to send response message " + clusterNodeResponse, e);
		}
		catch (Throwable t) {
			_log.error(t, t);
		}
	}

	protected void processClusterResponse(
		ClusterNodeResponse clusterNodeResponse,
		org.jgroups.Address sourceAddress) {

		ClusterMessageType clusterMessageType =
			clusterNodeResponse.getClusterMessageType();

		if (clusterMessageType.equals(ClusterMessageType.NOTIFY) ||
			clusterMessageType.equals(ClusterMessageType.UPDATE)) {

			ClusterNode clusterNode = clusterNodeResponse.getClusterNode();

			if (clusterNode != null) {
				Address joinAddress = new AddressImpl(sourceAddress);

				_clusterExecutorImpl.memberJoined(joinAddress, clusterNode);
			}
			else {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Response of notify message does not contain cluster " +
							"node information");
				}
			}

			return;
		}

		String uuid = clusterNodeResponse.getUuid();

		FutureClusterResponses futureClusterResponses =
			_clusterExecutorImpl.getExecutionResults(uuid);

		if (futureClusterResponses == null) {
			if (_log.isInfoEnabled()) {
				_log.info("Unable to find response container for " + uuid);
			}

			return;
		}

		Address address = new AddressImpl(sourceAddress);

		if (futureClusterResponses.expectsReply(address)) {
			futureClusterResponses.addClusterNodeResponse(clusterNodeResponse);
		}
		else {
			if (_log.isWarnEnabled()) {
				_log.warn("Unknown uuid " + uuid + " from " + sourceAddress);
			}
		}
	}

	protected boolean processLocalMessage(Object message) {
		if (message instanceof ClusterRequest) {
			ClusterRequest clusterRequest = (ClusterRequest)message;

			if (clusterRequest.isSkipLocal()) {
				return true;
			}
		}

		if (_clusterExecutorImpl.isShortcutLocalMethod()) {
			return true;
		}

		return false;
	}

	private static Log _log = LogFactoryUtil.getLog(
		ClusterRequestReceiver.class);

	private ClusterExecutorImpl _clusterExecutorImpl;
	private CountDownLatch _countDownLatch;
	private volatile View _lastView;

}