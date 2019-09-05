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

package com.liferay.knowledge.base.service.http;

import com.liferay.knowledge.base.service.KBTemplateServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>KBTemplateServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.knowledge.base.model.KBTemplateSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.knowledge.base.model.KBTemplate</code>, that is translated to a
 * <code>com.liferay.knowledge.base.model.KBTemplateSoap</code>. Methods that SOAP
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
 * @author Brian Wing Shun Chan
 * @see KBTemplateServiceHttp
 * @generated
 */
public class KBTemplateServiceSoap {

	public static com.liferay.knowledge.base.model.KBTemplateSoap addKBTemplate(
			String portletId, String title, String content,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.knowledge.base.model.KBTemplate returnValue =
				KBTemplateServiceUtil.addKBTemplate(
					portletId, title, content, serviceContext);

			return com.liferay.knowledge.base.model.KBTemplateSoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.knowledge.base.model.KBTemplateSoap
			deleteKBTemplate(long kbTemplateId)
		throws RemoteException {

		try {
			com.liferay.knowledge.base.model.KBTemplate returnValue =
				KBTemplateServiceUtil.deleteKBTemplate(kbTemplateId);

			return com.liferay.knowledge.base.model.KBTemplateSoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteKBTemplates(long groupId, long[] kbTemplateIds)
		throws RemoteException {

		try {
			KBTemplateServiceUtil.deleteKBTemplates(groupId, kbTemplateIds);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.knowledge.base.model.KBTemplateSoap[]
			getGroupKBTemplates(
				long groupId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.knowledge.base.model.KBTemplate>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.knowledge.base.model.KBTemplate>
				returnValue = KBTemplateServiceUtil.getGroupKBTemplates(
					groupId, start, end, orderByComparator);

			return com.liferay.knowledge.base.model.KBTemplateSoap.toSoapModels(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getGroupKBTemplatesCount(long groupId)
		throws RemoteException {

		try {
			int returnValue = KBTemplateServiceUtil.getGroupKBTemplatesCount(
				groupId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.knowledge.base.model.KBTemplateSoap getKBTemplate(
			long kbTemplateId)
		throws RemoteException {

		try {
			com.liferay.knowledge.base.model.KBTemplate returnValue =
				KBTemplateServiceUtil.getKBTemplate(kbTemplateId);

			return com.liferay.knowledge.base.model.KBTemplateSoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.knowledge.base.model.KBTemplateSearchDisplay
			getKBTemplateSearchDisplay(
				long groupId, String title, String content,
				java.util.Date startDate, java.util.Date endDate,
				boolean andOperator, int[] curStartValues, int cur, int delta,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.knowledge.base.model.KBTemplate>
						orderByComparator)
		throws RemoteException {

		try {
			com.liferay.knowledge.base.model.KBTemplateSearchDisplay
				returnValue = KBTemplateServiceUtil.getKBTemplateSearchDisplay(
					groupId, title, content, startDate, endDate, andOperator,
					curStartValues, cur, delta, orderByComparator);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.knowledge.base.model.KBTemplateSoap
			updateKBTemplate(
				long kbTemplateId, String title, String content,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.knowledge.base.model.KBTemplate returnValue =
				KBTemplateServiceUtil.updateKBTemplate(
					kbTemplateId, title, content, serviceContext);

			return com.liferay.knowledge.base.model.KBTemplateSoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		KBTemplateServiceSoap.class);

}