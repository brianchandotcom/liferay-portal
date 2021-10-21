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

package com.liferay.batch.planner.service.http;

import com.liferay.batch.planner.service.BatchPlannerPlanServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>BatchPlannerPlanServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.batch.planner.model.BatchPlannerPlanSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.batch.planner.model.BatchPlannerPlan</code>, that is translated to a
 * <code>com.liferay.batch.planner.model.BatchPlannerPlanSoap</code>. Methods that SOAP
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
 * @author Igor Beslic
 * @see BatchPlannerPlanServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class BatchPlannerPlanServiceSoap {

	public static com.liferay.batch.planner.model.BatchPlannerPlanSoap
			addBatchPlannerPlan(
				boolean export, String externalType, String externalURL,
				String internalClassName, String name,
				String taskItemDelegateName, boolean template)
		throws RemoteException {

		try {
			com.liferay.batch.planner.model.BatchPlannerPlan returnValue =
				BatchPlannerPlanServiceUtil.addBatchPlannerPlan(
					export, externalType, externalURL, internalClassName, name,
					taskItemDelegateName, template);

			return com.liferay.batch.planner.model.BatchPlannerPlanSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.batch.planner.model.BatchPlannerPlanSoap
			deleteBatchPlannerPlan(long batchPlannerPlanId)
		throws RemoteException {

		try {
			com.liferay.batch.planner.model.BatchPlannerPlan returnValue =
				BatchPlannerPlanServiceUtil.deleteBatchPlannerPlan(
					batchPlannerPlanId);

			return com.liferay.batch.planner.model.BatchPlannerPlanSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.batch.planner.model.BatchPlannerPlanSoap
			fetchBatchPlannerPlan(long batchPlannerPlanId)
		throws RemoteException {

		try {
			com.liferay.batch.planner.model.BatchPlannerPlan returnValue =
				BatchPlannerPlanServiceUtil.fetchBatchPlannerPlan(
					batchPlannerPlanId);

			return com.liferay.batch.planner.model.BatchPlannerPlanSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.batch.planner.model.BatchPlannerPlanSoap
			getBatchPlannerPlan(long batchPlannerPlanId)
		throws RemoteException {

		try {
			com.liferay.batch.planner.model.BatchPlannerPlan returnValue =
				BatchPlannerPlanServiceUtil.getBatchPlannerPlan(
					batchPlannerPlanId);

			return com.liferay.batch.planner.model.BatchPlannerPlanSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.batch.planner.model.BatchPlannerPlanSoap[]
			getBatchPlannerPlans(
				long companyId, boolean export, boolean template, int start,
				int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.batch.planner.model.BatchPlannerPlan>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.batch.planner.model.BatchPlannerPlan>
				returnValue = BatchPlannerPlanServiceUtil.getBatchPlannerPlans(
					companyId, export, template, start, end, orderByComparator);

			return com.liferay.batch.planner.model.BatchPlannerPlanSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.batch.planner.model.BatchPlannerPlanSoap[]
			getBatchPlannerPlans(
				long companyId, boolean template, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.batch.planner.model.BatchPlannerPlan>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.batch.planner.model.BatchPlannerPlan>
				returnValue = BatchPlannerPlanServiceUtil.getBatchPlannerPlans(
					companyId, template, start, end, orderByComparator);

			return com.liferay.batch.planner.model.BatchPlannerPlanSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.batch.planner.model.BatchPlannerPlanSoap[]
			getBatchPlannerPlans(long companyId, int start, int end)
		throws RemoteException {

		try {
			java.util.List<com.liferay.batch.planner.model.BatchPlannerPlan>
				returnValue = BatchPlannerPlanServiceUtil.getBatchPlannerPlans(
					companyId, start, end);

			return com.liferay.batch.planner.model.BatchPlannerPlanSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.batch.planner.model.BatchPlannerPlanSoap[]
			getBatchPlannerPlans(
				long companyId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.batch.planner.model.BatchPlannerPlan>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.batch.planner.model.BatchPlannerPlan>
				returnValue = BatchPlannerPlanServiceUtil.getBatchPlannerPlans(
					companyId, start, end, orderByComparator);

			return com.liferay.batch.planner.model.BatchPlannerPlanSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getBatchPlannerPlansCount(long companyId)
		throws RemoteException {

		try {
			int returnValue =
				BatchPlannerPlanServiceUtil.getBatchPlannerPlansCount(
					companyId);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getBatchPlannerPlansCount(
			long companyId, boolean template)
		throws RemoteException {

		try {
			int returnValue =
				BatchPlannerPlanServiceUtil.getBatchPlannerPlansCount(
					companyId, template);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getBatchPlannerPlansCount(
			long companyId, boolean export, boolean template)
		throws RemoteException {

		try {
			int returnValue =
				BatchPlannerPlanServiceUtil.getBatchPlannerPlansCount(
					companyId, export, template);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.batch.planner.model.BatchPlannerPlanSoap
			updateBatchPlannerPlan(long batchPlannerPlanId, String name)
		throws RemoteException {

		try {
			com.liferay.batch.planner.model.BatchPlannerPlan returnValue =
				BatchPlannerPlanServiceUtil.updateBatchPlannerPlan(
					batchPlannerPlanId, name);

			return com.liferay.batch.planner.model.BatchPlannerPlanSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		BatchPlannerPlanServiceSoap.class);

}