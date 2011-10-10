/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.mobiledevicerules.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import com.liferay.portlet.mobiledevicerules.service.MDRRuleGroupInstanceServiceUtil;

import java.rmi.RemoteException;

/**
 * <p>
 * This class provides a SOAP utility for the
 * {@link com.liferay.portlet.mobiledevicerules.service.MDRRuleGroupInstanceServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 * </p>
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstanceSoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance}, that is translated to a
 * {@link com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstanceSoap}. Methods that SOAP cannot
 * safely wire are skipped.
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
 * You can see a list of services at
 * http://localhost:8080/tunnel-web/secure/axis. Set the property
 * <b>tunnel.servlet.hosts.allowed</b> in portal.properties to configure
 * security.
 * </p>
 *
 * <p>
 * The SOAP utility is only generated for remote services.
 * </p>
 *
 * @author    Edward C. Han
 * @see       MDRRuleGroupInstanceServiceHttp
 * @see       com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstanceSoap
 * @see       com.liferay.portlet.mobiledevicerules.service.MDRRuleGroupInstanceServiceUtil
 * @generated
 */
public class MDRRuleGroupInstanceServiceSoap {
	public static com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstanceSoap addRuleGroupInstance(
		long groupId, java.lang.String className, long classPK,
		long ruleGroupId, int priority,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance returnValue =
				MDRRuleGroupInstanceServiceUtil.addRuleGroupInstance(groupId,
					className, classPK, ruleGroupId, priority, serviceContext);

			return com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstanceSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstanceSoap addRuleGroupInstance(
		long groupId, java.lang.String className, long classPK,
		long ruleGroupId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance returnValue =
				MDRRuleGroupInstanceServiceUtil.addRuleGroupInstance(groupId,
					className, classPK, ruleGroupId, serviceContext);

			return com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstanceSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteRuleGroupInstance(long ruleGroupInstanceId)
		throws RemoteException {
		try {
			MDRRuleGroupInstanceServiceUtil.deleteRuleGroupInstance(ruleGroupInstanceId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteRuleGroupInstance(
		com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstanceSoap ruleGroupInstance)
		throws RemoteException {
		try {
			MDRRuleGroupInstanceServiceUtil.deleteRuleGroupInstance(com.liferay.portlet.mobiledevicerules.model.impl.MDRRuleGroupInstanceModelImpl.toModel(
					ruleGroupInstance));
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstanceSoap[] getRuleGroupInstances(
		java.lang.String className, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance> returnValue =
				MDRRuleGroupInstanceServiceUtil.getRuleGroupInstances(className,
					classPK, start, end, orderByComparator);

			return com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstanceSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getRuleGroupInstancesCount(java.lang.String className,
		long classPK) throws RemoteException {
		try {
			int returnValue = MDRRuleGroupInstanceServiceUtil.getRuleGroupInstancesCount(className,
					classPK);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstanceSoap updateRuleGroupInstance(
		long ruleGroupInstanceId, int priority) throws RemoteException {
		try {
			com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance returnValue =
				MDRRuleGroupInstanceServiceUtil.updateRuleGroupInstance(ruleGroupInstanceId,
					priority);

			return com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstanceSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(MDRRuleGroupInstanceServiceSoap.class);
}