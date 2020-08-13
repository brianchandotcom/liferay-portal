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

package com.liferay.segments.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.segments.service.SegmentsExperimentServiceUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>SegmentsExperimentServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.segments.model.SegmentsExperimentSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.segments.model.SegmentsExperiment</code>, that is translated to a
 * <code>com.liferay.segments.model.SegmentsExperimentSoap</code>. Methods that SOAP
 * cannot safely wire are skipped.
 * </p>
 *
 * <p>
 * The benefits of using the SOAP utility is that it is cross platform
 * compatible. SOAP allows different languages like Java, .NET, C++, PHP, and
 * even Perl, to call the generated services. One drawback of SOAP is that it is
 * slow because it needs to serialize all calls into a text format (XML).
 * </p>
 *
 * <p>
 * You can see a list of services at http://localhost:8080/api/axis. Set the
 * property <b>axis.servlet.hosts.allowed</b> in portal.properties to configure
 * security.
 * </p>
 *
 * <p>
 * The SOAP utility is only generated for remote services.
 * </p>
 *
 * @author Eduardo Garcia
 * @see SegmentsExperimentServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class SegmentsExperimentServiceSoap {

	public static com.liferay.segments.model.SegmentsExperimentSoap
			addSegmentsExperiment(
				long segmentsExperienceId, long classNameId, long classPK,
				String name, String description, String goal, String goalTarget,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.segments.model.SegmentsExperiment returnValue =
				SegmentsExperimentServiceUtil.addSegmentsExperiment(
					segmentsExperienceId, classNameId, classPK, name,
					description, goal, goalTarget, serviceContext);

			return com.liferay.segments.model.SegmentsExperimentSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.segments.model.SegmentsExperimentSoap
			deleteSegmentsExperiment(long segmentsExperimentId)
		throws RemoteException {

		try {
			com.liferay.segments.model.SegmentsExperiment returnValue =
				SegmentsExperimentServiceUtil.deleteSegmentsExperiment(
					segmentsExperimentId);

			return com.liferay.segments.model.SegmentsExperimentSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.segments.model.SegmentsExperimentSoap
			deleteSegmentsExperiment(String segmentsExperimentKey)
		throws RemoteException {

		try {
			com.liferay.segments.model.SegmentsExperiment returnValue =
				SegmentsExperimentServiceUtil.deleteSegmentsExperiment(
					segmentsExperimentKey);

			return com.liferay.segments.model.SegmentsExperimentSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.segments.model.SegmentsExperimentSoap
			fetchSegmentsExperiment(
				long segmentsExperienceId, long classNameId, long classPK,
				int[] statuses)
		throws RemoteException {

		try {
			com.liferay.segments.model.SegmentsExperiment returnValue =
				SegmentsExperimentServiceUtil.fetchSegmentsExperiment(
					segmentsExperienceId, classNameId, classPK, statuses);

			return com.liferay.segments.model.SegmentsExperimentSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.segments.model.SegmentsExperimentSoap
			fetchSegmentsExperiment(long groupId, String segmentsExperimentKey)
		throws RemoteException {

		try {
			com.liferay.segments.model.SegmentsExperiment returnValue =
				SegmentsExperimentServiceUtil.fetchSegmentsExperiment(
					groupId, segmentsExperimentKey);

			return com.liferay.segments.model.SegmentsExperimentSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.segments.model.SegmentsExperimentSoap[]
			getSegmentsExperienceSegmentsExperiments(
				long[] segmentsExperienceIds, long classNameId, long classPK,
				int[] statuses, int start, int end)
		throws RemoteException {

		try {
			java.util.List<com.liferay.segments.model.SegmentsExperiment>
				returnValue =
					SegmentsExperimentServiceUtil.
						getSegmentsExperienceSegmentsExperiments(
							segmentsExperienceIds, classNameId, classPK,
							statuses, start, end);

			return com.liferay.segments.model.SegmentsExperimentSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.segments.model.SegmentsExperimentSoap
			getSegmentsExperiment(long segmentsExperimentId)
		throws RemoteException {

		try {
			com.liferay.segments.model.SegmentsExperiment returnValue =
				SegmentsExperimentServiceUtil.getSegmentsExperiment(
					segmentsExperimentId);

			return com.liferay.segments.model.SegmentsExperimentSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.segments.model.SegmentsExperimentSoap
			getSegmentsExperiment(String segmentsExperimentKey)
		throws RemoteException {

		try {
			com.liferay.segments.model.SegmentsExperiment returnValue =
				SegmentsExperimentServiceUtil.getSegmentsExperiment(
					segmentsExperimentKey);

			return com.liferay.segments.model.SegmentsExperimentSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.segments.model.SegmentsExperimentSoap[]
			getSegmentsExperiments(long groupId, long classNameId, long classPK)
		throws RemoteException {

		try {
			java.util.List<com.liferay.segments.model.SegmentsExperiment>
				returnValue =
					SegmentsExperimentServiceUtil.getSegmentsExperiments(
						groupId, classNameId, classPK);

			return com.liferay.segments.model.SegmentsExperimentSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.segments.model.SegmentsExperimentSoap[]
			getSegmentsExperiments(
				long segmentsExperienceId, long classNameId, long classPK,
				int[] statuses,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.segments.model.SegmentsExperiment>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.segments.model.SegmentsExperiment>
				returnValue =
					SegmentsExperimentServiceUtil.getSegmentsExperiments(
						segmentsExperienceId, classNameId, classPK, statuses,
						orderByComparator);

			return com.liferay.segments.model.SegmentsExperimentSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.segments.model.SegmentsExperimentSoap
			updateSegmentsExperiment(
				long segmentsExperimentId, String name, String description,
				String goal, String goalTarget)
		throws RemoteException {

		try {
			com.liferay.segments.model.SegmentsExperiment returnValue =
				SegmentsExperimentServiceUtil.updateSegmentsExperiment(
					segmentsExperimentId, name, description, goal, goalTarget);

			return com.liferay.segments.model.SegmentsExperimentSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.segments.model.SegmentsExperimentSoap
			updateSegmentsExperimentStatus(
				long segmentsExperimentId, int status)
		throws RemoteException {

		try {
			com.liferay.segments.model.SegmentsExperiment returnValue =
				SegmentsExperimentServiceUtil.updateSegmentsExperimentStatus(
					segmentsExperimentId, status);

			return com.liferay.segments.model.SegmentsExperimentSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.segments.model.SegmentsExperimentSoap
			updateSegmentsExperimentStatus(
				long segmentsExperimentId, long winnerSegmentsExperienceId,
				int status)
		throws RemoteException {

		try {
			com.liferay.segments.model.SegmentsExperiment returnValue =
				SegmentsExperimentServiceUtil.updateSegmentsExperimentStatus(
					segmentsExperimentId, winnerSegmentsExperienceId, status);

			return com.liferay.segments.model.SegmentsExperimentSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.segments.model.SegmentsExperimentSoap
			updateSegmentsExperimentStatus(
				String segmentsExperimentKey, int status)
		throws RemoteException {

		try {
			com.liferay.segments.model.SegmentsExperiment returnValue =
				SegmentsExperimentServiceUtil.updateSegmentsExperimentStatus(
					segmentsExperimentKey, status);

			return com.liferay.segments.model.SegmentsExperimentSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.segments.model.SegmentsExperimentSoap
			updateSegmentsExperimentStatus(
				String segmentsExperimentKey,
				String winnerSegmentsExperienceKey, int status)
		throws RemoteException {

		try {
			com.liferay.segments.model.SegmentsExperiment returnValue =
				SegmentsExperimentServiceUtil.updateSegmentsExperimentStatus(
					segmentsExperimentKey, winnerSegmentsExperienceKey, status);

			return com.liferay.segments.model.SegmentsExperimentSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		SegmentsExperimentServiceSoap.class);

}