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

package com.liferay.layout.page.template.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.layout.page.template.service.LayoutPageTemplateServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * {@link LayoutPageTemplateServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.layout.page.template.model.LayoutPageTemplateSoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.layout.page.template.model.LayoutPageTemplate}, that is translated to a
 * {@link com.liferay.layout.page.template.model.LayoutPageTemplateSoap}. Methods that SOAP cannot
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
 * @see LayoutPageTemplateServiceHttp
 * @see com.liferay.layout.page.template.model.LayoutPageTemplateSoap
 * @see LayoutPageTemplateServiceUtil
 * @generated
 */
@ProviderType
public class LayoutPageTemplateServiceSoap {
	public static com.liferay.layout.page.template.model.LayoutPageTemplateSoap addLayoutPageTemplate(
		long groupId, long layoutPageTemplateFolderId, java.lang.String name,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.layout.page.template.model.LayoutPageTemplate returnValue =
				LayoutPageTemplateServiceUtil.addLayoutPageTemplate(groupId,
					layoutPageTemplateFolderId, name, serviceContext);

			return com.liferay.layout.page.template.model.LayoutPageTemplateSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.layout.page.template.model.LayoutPageTemplateSoap deleteLayoutPageTemplate(
		long pageTemplateId) throws RemoteException {
		try {
			com.liferay.layout.page.template.model.LayoutPageTemplate returnValue =
				LayoutPageTemplateServiceUtil.deleteLayoutPageTemplate(pageTemplateId);

			return com.liferay.layout.page.template.model.LayoutPageTemplateSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.layout.page.template.model.LayoutPageTemplateSoap[] deletePageTemplates(
		long[] pageTemplatesIds) throws RemoteException {
		try {
			java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplate> returnValue =
				LayoutPageTemplateServiceUtil.deletePageTemplates(pageTemplatesIds);

			return com.liferay.layout.page.template.model.LayoutPageTemplateSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.layout.page.template.model.LayoutPageTemplateSoap fetchLayoutPageTemplate(
		long pageTemplateId) throws RemoteException {
		try {
			com.liferay.layout.page.template.model.LayoutPageTemplate returnValue =
				LayoutPageTemplateServiceUtil.fetchLayoutPageTemplate(pageTemplateId);

			return com.liferay.layout.page.template.model.LayoutPageTemplateSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.layout.page.template.model.LayoutPageTemplateSoap[] fetchPageTemplates(
		long layoutPageTemplateFolderId) throws RemoteException {
		try {
			java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplate> returnValue =
				LayoutPageTemplateServiceUtil.fetchPageTemplates(layoutPageTemplateFolderId);

			return com.liferay.layout.page.template.model.LayoutPageTemplateSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getLayoutPageTemplateFoldersCount(long groupId,
		long layoutPageTemplateFolderId) throws RemoteException {
		try {
			int returnValue = LayoutPageTemplateServiceUtil.getLayoutPageTemplateFoldersCount(groupId,
					layoutPageTemplateFolderId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getLayoutPageTemplateFoldersCount(long groupId,
		long layoutPageTemplateFolderId, java.lang.String name)
		throws RemoteException {
		try {
			int returnValue = LayoutPageTemplateServiceUtil.getLayoutPageTemplateFoldersCount(groupId,
					layoutPageTemplateFolderId, name);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.layout.page.template.model.LayoutPageTemplateSoap[] getPageTemplates(
		long groupId, long layoutPageTemplateFolderId, int start, int end)
		throws RemoteException {
		try {
			java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplate> returnValue =
				LayoutPageTemplateServiceUtil.getPageTemplates(groupId,
					layoutPageTemplateFolderId, start, end);

			return com.liferay.layout.page.template.model.LayoutPageTemplateSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.layout.page.template.model.LayoutPageTemplateSoap[] getPageTemplates(
		long groupId, long layoutPageTemplateFolderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.layout.page.template.model.LayoutPageTemplate> orderByComparator)
		throws RemoteException {
		try {
			java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplate> returnValue =
				LayoutPageTemplateServiceUtil.getPageTemplates(groupId,
					layoutPageTemplateFolderId, start, end, orderByComparator);

			return com.liferay.layout.page.template.model.LayoutPageTemplateSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.layout.page.template.model.LayoutPageTemplateSoap[] getPageTemplates(
		long groupId, long layoutPageTemplateFolderId, java.lang.String name,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.layout.page.template.model.LayoutPageTemplate> orderByComparator)
		throws RemoteException {
		try {
			java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplate> returnValue =
				LayoutPageTemplateServiceUtil.getPageTemplates(groupId,
					layoutPageTemplateFolderId, name, start, end,
					orderByComparator);

			return com.liferay.layout.page.template.model.LayoutPageTemplateSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.layout.page.template.model.LayoutPageTemplateSoap updateLayoutPageTemplate(
		long pageTemplateId, java.lang.String name) throws RemoteException {
		try {
			com.liferay.layout.page.template.model.LayoutPageTemplate returnValue =
				LayoutPageTemplateServiceUtil.updateLayoutPageTemplate(pageTemplateId,
					name);

			return com.liferay.layout.page.template.model.LayoutPageTemplateSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(LayoutPageTemplateServiceSoap.class);
}