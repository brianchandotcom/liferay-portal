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

package com.liferay.portlet.dynamicdatamapping.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;

import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateServiceUtil;

import java.rmi.RemoteException;

import java.util.Locale;
import java.util.Map;

/**
 * <p>
 * This class provides a SOAP utility for the
 * {@link com.liferay.portlet.dynamicdatamapping.service.DDMTemplateServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 * </p>
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.portlet.dynamicdatamapping.model.DDMTemplateSoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.portlet.dynamicdatamapping.model.DDMTemplate}, that is translated to a
 * {@link com.liferay.portlet.dynamicdatamapping.model.DDMTemplateSoap}. Methods that SOAP cannot
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
 * @author    Brian Wing Shun Chan
 * @see       DDMTemplateServiceHttp
 * @see       com.liferay.portlet.dynamicdatamapping.model.DDMTemplateSoap
 * @see       com.liferay.portlet.dynamicdatamapping.service.DDMTemplateServiceUtil
 * @generated
 */
public class DDMTemplateServiceSoap {
	public static com.liferay.portlet.dynamicdatamapping.model.DDMTemplateSoap addTemplate(
		long groupId, long classNameId, long classPK,
		java.lang.String[] nameMapLanguageIds,
		java.lang.String[] nameMapValues,
		java.lang.String[] descriptionMapLanguageIds,
		java.lang.String[] descriptionMapValues, java.lang.String type,
		java.lang.String mode, java.lang.String language,
		java.lang.String script,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(nameMapLanguageIds,
					nameMapValues);
			Map<Locale, String> descriptionMap = LocalizationUtil.getLocalizationMap(descriptionMapLanguageIds,
					descriptionMapValues);

			com.liferay.portlet.dynamicdatamapping.model.DDMTemplate returnValue =
				DDMTemplateServiceUtil.addTemplate(groupId, classNameId,
					classPK, nameMap, descriptionMap, type, mode, language,
					script, serviceContext);

			return com.liferay.portlet.dynamicdatamapping.model.DDMTemplateSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMTemplateSoap[] copyTemplates(
		long classNameId, long classPK, long newClassPK, java.lang.String type,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> returnValue =
				DDMTemplateServiceUtil.copyTemplates(classNameId, classPK,
					newClassPK, type, serviceContext);

			return com.liferay.portlet.dynamicdatamapping.model.DDMTemplateSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteTemplate(long templateId)
		throws RemoteException {
		try {
			DDMTemplateServiceUtil.deleteTemplate(templateId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMTemplateSoap fetchTemplate(
		long groupId, java.lang.String templateKey) throws RemoteException {
		try {
			com.liferay.portlet.dynamicdatamapping.model.DDMTemplate returnValue =
				DDMTemplateServiceUtil.fetchTemplate(groupId, templateKey);

			return com.liferay.portlet.dynamicdatamapping.model.DDMTemplateSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMTemplateSoap getTemplate(
		long templateId) throws RemoteException {
		try {
			com.liferay.portlet.dynamicdatamapping.model.DDMTemplate returnValue =
				DDMTemplateServiceUtil.getTemplate(templateId);

			return com.liferay.portlet.dynamicdatamapping.model.DDMTemplateSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMTemplateSoap getTemplate(
		long groupId, java.lang.String templateKey) throws RemoteException {
		try {
			com.liferay.portlet.dynamicdatamapping.model.DDMTemplate returnValue =
				DDMTemplateServiceUtil.getTemplate(groupId, templateKey);

			return com.liferay.portlet.dynamicdatamapping.model.DDMTemplateSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMTemplateSoap getTemplate(
		long groupId, java.lang.String templateKey,
		boolean includeGlobalTemplates) throws RemoteException {
		try {
			com.liferay.portlet.dynamicdatamapping.model.DDMTemplate returnValue =
				DDMTemplateServiceUtil.getTemplate(groupId, templateKey,
					includeGlobalTemplates);

			return com.liferay.portlet.dynamicdatamapping.model.DDMTemplateSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMTemplateSoap[] getTemplates(
		long groupId, long classNameId) throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> returnValue =
				DDMTemplateServiceUtil.getTemplates(groupId, classNameId);

			return com.liferay.portlet.dynamicdatamapping.model.DDMTemplateSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMTemplateSoap[] getTemplates(
		long groupId, long classNameId, long classPK) throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> returnValue =
				DDMTemplateServiceUtil.getTemplates(groupId, classNameId,
					classPK);

			return com.liferay.portlet.dynamicdatamapping.model.DDMTemplateSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMTemplateSoap[] getTemplates(
		long classNameId, long classPK, java.lang.String type,
		java.lang.String mode) throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> returnValue =
				DDMTemplateServiceUtil.getTemplates(classNameId, classPK, type,
					mode);

			return com.liferay.portlet.dynamicdatamapping.model.DDMTemplateSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMTemplateSoap[] search(
		long companyId, long groupId, long classNameId, long classPK,
		java.lang.String keywords, java.lang.String type,
		java.lang.String mode, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> returnValue =
				DDMTemplateServiceUtil.search(companyId, groupId, classNameId,
					classPK, keywords, type, mode, start, end, orderByComparator);

			return com.liferay.portlet.dynamicdatamapping.model.DDMTemplateSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMTemplateSoap[] search(
		long companyId, long groupId, long classNameId, long classPK,
		java.lang.String name, java.lang.String description,
		java.lang.String type, java.lang.String mode,
		java.lang.String language, boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> returnValue =
				DDMTemplateServiceUtil.search(companyId, groupId, classNameId,
					classPK, name, description, type, mode, language,
					andOperator, start, end, orderByComparator);

			return com.liferay.portlet.dynamicdatamapping.model.DDMTemplateSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMTemplateSoap[] search(
		long companyId, long[] groupIds, long[] classNameIds, long classPK,
		java.lang.String keywords, java.lang.String type,
		java.lang.String mode, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> returnValue =
				DDMTemplateServiceUtil.search(companyId, groupIds,
					classNameIds, classPK, keywords, type, mode, start, end,
					orderByComparator);

			return com.liferay.portlet.dynamicdatamapping.model.DDMTemplateSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMTemplateSoap[] search(
		long companyId, long[] groupIds, long[] classNameIds, long classPK,
		java.lang.String name, java.lang.String description,
		java.lang.String type, java.lang.String mode,
		java.lang.String language, boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> returnValue =
				DDMTemplateServiceUtil.search(companyId, groupIds,
					classNameIds, classPK, name, description, type, mode,
					language, andOperator, start, end, orderByComparator);

			return com.liferay.portlet.dynamicdatamapping.model.DDMTemplateSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int searchCount(long companyId, long groupId,
		long classNameId, long classPK, java.lang.String keywords,
		java.lang.String type, java.lang.String mode) throws RemoteException {
		try {
			int returnValue = DDMTemplateServiceUtil.searchCount(companyId,
					groupId, classNameId, classPK, keywords, type, mode);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int searchCount(long companyId, long groupId,
		long classNameId, long classPK, java.lang.String name,
		java.lang.String description, java.lang.String type,
		java.lang.String mode, java.lang.String language, boolean andOperator)
		throws RemoteException {
		try {
			int returnValue = DDMTemplateServiceUtil.searchCount(companyId,
					groupId, classNameId, classPK, name, description, type,
					mode, language, andOperator);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int searchCount(long companyId, long[] groupIds,
		long[] classNameIds, long classPK, java.lang.String keywords,
		java.lang.String type, java.lang.String mode) throws RemoteException {
		try {
			int returnValue = DDMTemplateServiceUtil.searchCount(companyId,
					groupIds, classNameIds, classPK, keywords, type, mode);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int searchCount(long companyId, long[] groupIds,
		long[] classNameIds, long classPK, java.lang.String name,
		java.lang.String description, java.lang.String type,
		java.lang.String mode, java.lang.String language, boolean andOperator)
		throws RemoteException {
		try {
			int returnValue = DDMTemplateServiceUtil.searchCount(companyId,
					groupIds, classNameIds, classPK, name, description, type,
					mode, language, andOperator);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(DDMTemplateServiceSoap.class);
}