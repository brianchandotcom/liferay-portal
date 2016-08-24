/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.wsrp.internal.messaging;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.HotDeployMessageListener;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.wsrp.internal.jmx.WSRPConsumerPortletManager;
import com.liferay.wsrp.service.WSRPConsumerPortletLocalService;
import com.liferay.wsrp.util.ExtensionHelperUtil;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Shuyang Zhou
 * @author Peter Fellwock
 */
@Component(
	immediate = true, property = {"destination.name=" + DestinationNames.WSRP},
	service = MessageListener.class
)
public class WSRPMessageListener extends HotDeployMessageListener {

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	public void registerMBeanServer(MBeanServer mBeanServer) {
		try {
			mBeanServer.registerMBean(
				new WSRPConsumerPortletManager(), new ObjectName(
					"com.liferay.wsrp:classification=wsrp," +
						"name=WSRPConsumerPortletManager"));
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to register WSRP consumer portlet manager", e);
			}
		}
	}

	public void unregisterMBeanServer(MBeanServer mBeanServer) {
		try {
			mBeanServer.unregisterMBean(
				new ObjectName(
					"com.liferay.wsrp:classification=wsrp," +
						"name=WSRPConsumerPortletManager"));
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to unregister WSRP consumer portlet manager", e);
			}
		}
	}

	@Activate
	protected void activate() {
		ExtensionHelperUtil.initialize();

		try {
			_wsrpConsumerPortletLocalService.destroyWSRPConsumerPortlets();
		}
		catch (PortalException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to destroy WSRP consumer portlets", pe);
			}
		}

		_wsrpConsumerPortletLocalService.initWSRPConsumerPortlets();
	}

	@Reference(unbind = "-")
	protected void setWSRPConsumerPortletLocalService(
		WSRPConsumerPortletLocalService wSRPConsumerPortletLocalService) {

		_wsrpConsumerPortletLocalService = wSRPConsumerPortletLocalService;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		WSRPMessageListener.class);

	private static WSRPConsumerPortletLocalService
		_wsrpConsumerPortletLocalService;

}