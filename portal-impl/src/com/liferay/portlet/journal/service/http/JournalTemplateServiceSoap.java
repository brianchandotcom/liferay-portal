/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.journal.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;

import com.liferay.portlet.journal.service.JournalTemplateServiceUtil;

import java.rmi.RemoteException;

import java.util.Locale;
import java.util.Map;

/**
 * Provides the SOAP utility for the
 * {@link com.liferay.portlet.journal.service.JournalTemplateServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.portlet.journal.model.JournalTemplateSoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.portlet.journal.model.JournalTemplate}, that is translated to a
 * {@link com.liferay.portlet.journal.model.JournalTemplateSoap}. Methods that SOAP cannot
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
 * @see       JournalTemplateServiceHttp
 * @see       com.liferay.portlet.journal.model.JournalTemplateSoap
 * @see       com.liferay.portlet.journal.service.JournalTemplateServiceUtil
 * @generated
 */
public class JournalTemplateServiceSoap {
	public static com.liferay.portlet.journal.model.JournalTemplateSoap addTemplate(
		long groupId, java.lang.String templateId, boolean autoTemplateId,
		java.lang.String structureId, java.lang.String[] nameMapLanguageIds,
		java.lang.String[] nameMapValues,
		java.lang.String[] descriptionMapLanguageIds,
		java.lang.String[] descriptionMapValues, java.lang.String xsl,
		boolean formatXsl, java.lang.String langType, boolean cacheable,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(nameMapLanguageIds,
					nameMapValues);
			Map<Locale, String> descriptionMap = LocalizationUtil.getLocalizationMap(descriptionMapLanguageIds,
					descriptionMapValues);

			com.liferay.portlet.journal.model.JournalTemplate returnValue = JournalTemplateServiceUtil.addTemplate(groupId,
					templateId, autoTemplateId, structureId, nameMap,
					descriptionMap, xsl, formatXsl, langType, cacheable,
					serviceContext);

			return com.liferay.portlet.journal.model.JournalTemplateSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.journal.model.JournalTemplateSoap copyTemplate(
		long groupId, java.lang.String oldTemplateId,
		java.lang.String newTemplateId, boolean autoTemplateId)
		throws RemoteException {
		try {
			com.liferay.portlet.journal.model.JournalTemplate returnValue = JournalTemplateServiceUtil.copyTemplate(groupId,
					oldTemplateId, newTemplateId, autoTemplateId);

			return com.liferay.portlet.journal.model.JournalTemplateSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteTemplate(long groupId, java.lang.String templateId)
		throws RemoteException {
		try {
			JournalTemplateServiceUtil.deleteTemplate(groupId, templateId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.journal.model.JournalTemplateSoap[] getStructureTemplates(
		long groupId, java.lang.String structureId) throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.journal.model.JournalTemplate> returnValue =
				JournalTemplateServiceUtil.getStructureTemplates(groupId,
					structureId);

			return com.liferay.portlet.journal.model.JournalTemplateSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.journal.model.JournalTemplateSoap getTemplate(
		long groupId, java.lang.String templateId) throws RemoteException {
		try {
			com.liferay.portlet.journal.model.JournalTemplate returnValue = JournalTemplateServiceUtil.getTemplate(groupId,
					templateId);

			return com.liferay.portlet.journal.model.JournalTemplateSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.journal.model.JournalTemplateSoap getTemplate(
		long groupId, java.lang.String templateId,
		boolean includeGlobalTemplates) throws RemoteException {
		try {
			com.liferay.portlet.journal.model.JournalTemplate returnValue = JournalTemplateServiceUtil.getTemplate(groupId,
					templateId, includeGlobalTemplates);

			return com.liferay.portlet.journal.model.JournalTemplateSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.journal.model.JournalTemplateSoap[] search(
		long companyId, long[] groupIds, java.lang.String keywords,
		java.lang.String structureId, java.lang.String structureIdComparator,
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.journal.model.JournalTemplate> returnValue =
				JournalTemplateServiceUtil.search(companyId, groupIds,
					keywords, structureId, structureIdComparator, start, end,
					obc);

			return com.liferay.portlet.journal.model.JournalTemplateSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.journal.model.JournalTemplateSoap[] search(
		long companyId, long[] groupIds, java.lang.String templateId,
		java.lang.String structureId, java.lang.String structureIdComparator,
		java.lang.String name, java.lang.String description,
		boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.journal.model.JournalTemplate> returnValue =
				JournalTemplateServiceUtil.search(companyId, groupIds,
					templateId, structureId, structureIdComparator, name,
					description, andOperator, start, end, obc);

			return com.liferay.portlet.journal.model.JournalTemplateSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int searchCount(long companyId, long[] groupIds,
		java.lang.String keywords, java.lang.String structureId,
		java.lang.String structureIdComparator) throws RemoteException {
		try {
			int returnValue = JournalTemplateServiceUtil.searchCount(companyId,
					groupIds, keywords, structureId, structureIdComparator);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int searchCount(long companyId, long[] groupIds,
		java.lang.String templateId, java.lang.String structureId,
		java.lang.String structureIdComparator, java.lang.String name,
		java.lang.String description, boolean andOperator)
		throws RemoteException {
		try {
			int returnValue = JournalTemplateServiceUtil.searchCount(companyId,
					groupIds, templateId, structureId, structureIdComparator,
					name, description, andOperator);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.journal.model.JournalTemplateSoap updateTemplate(
		long groupId, java.lang.String templateId,
		java.lang.String structureId, java.lang.String[] nameMapLanguageIds,
		java.lang.String[] nameMapValues,
		java.lang.String[] descriptionMapLanguageIds,
		java.lang.String[] descriptionMapValues, java.lang.String xsl,
		boolean formatXsl, java.lang.String langType, boolean cacheable,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(nameMapLanguageIds,
					nameMapValues);
			Map<Locale, String> descriptionMap = LocalizationUtil.getLocalizationMap(descriptionMapLanguageIds,
					descriptionMapValues);

			com.liferay.portlet.journal.model.JournalTemplate returnValue = JournalTemplateServiceUtil.updateTemplate(groupId,
					templateId, structureId, nameMap, descriptionMap, xsl,
					formatXsl, langType, cacheable, serviceContext);

			return com.liferay.portlet.journal.model.JournalTemplateSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(JournalTemplateServiceSoap.class);
}