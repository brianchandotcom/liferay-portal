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

package com.liferay.portal.workflow.kaleo.designer.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionManagerUtil;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.workflow.kaleo.designer.constants.KaleoDesignerWebKeys;
import com.liferay.portal.workflow.kaleo.designer.model.KaleoDraftDefinition;
import com.liferay.portal.workflow.kaleo.designer.service.KaleoDraftDefinitionLocalServiceUtil;

import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * Provides utility methods used by Kaleo designer applications.
 *
 * @author Eduardo Lundgren
 */
public class KaleoDesignerUtil {

	public static KaleoDraftDefinition addMissingKaleoDraftDefinition(
			String name, int version, Map<Locale, String> titleMap,
			String content, ServiceContext serviceContext)
		throws PortalException {

		int kaleoDraftDefinitionsCount =
			KaleoDraftDefinitionLocalServiceUtil.getKaleoDraftDefinitionsCount(
				name, version, serviceContext);

		KaleoDraftDefinition kaleoDraftDefinition = null;

		if (kaleoDraftDefinitionsCount == 0) {
			kaleoDraftDefinition =
				KaleoDraftDefinitionLocalServiceUtil.addKaleoDraftDefinition(
					serviceContext.getUserId(),
					serviceContext.getScopeGroupId(), name, titleMap, content,
					version, 1, serviceContext);
		}
		else {
			kaleoDraftDefinition =
				KaleoDraftDefinitionLocalServiceUtil.
					getLatestKaleoDraftDefinition(
						name, version, serviceContext);
		}

		return kaleoDraftDefinition;
	}

	public static KaleoDraftDefinition addMissingKaleoDraftDefinition(
			String name, int version, String title, String content,
			ServiceContext serviceContext)
		throws PortalException {

		Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(
			title);

		return addMissingKaleoDraftDefinition(
			name, version, titleMap, content, serviceContext);
	}

	/**
	 * Deploys the workflow definition.
	 *
	 * @param  companyId the primary key of the workflow definition's company
	 * @param  userId the primary key of the workflow definition's creator/owner
	 * @param  titleMap the Kaleo workflow definition's locales and localized
	 *         titles
	 * @param  content the content wrapped in XML
	 * @return the workflow definition
	 * @throws WorkflowException if a workflow exception occurred
	 */
	public static WorkflowDefinition deployWorkflowDefinition(
			long companyId, long userId, Map<Locale, String> titleMap,
			String content)
		throws WorkflowException {

		try {
			WorkflowDefinition workflowDefinition =
				WorkflowDefinitionManagerUtil.deployWorkflowDefinition(
					companyId, userId, _getLocalizedTitleXML(titleMap),
					content.getBytes());

			return workflowDefinition;
		}
		catch (WorkflowException we) {
			_log.error(we, we);

			throw we;
		}
	}

	/**
	 * Returns the Kaleo draft definition, or creates one if it does not exist.
	 *
	 * @param  request the servlet request from which to read the parameters
	 * @return the Kaleo draft definition
	 * @throws Exception if an exception occurred
	 */
	public static KaleoDraftDefinition getKaleoDraftDefinition(
			HttpServletRequest request)
		throws Exception {

		KaleoDraftDefinition kaleoDraftDefinition =
			(KaleoDraftDefinition)request.getAttribute(
				KaleoDesignerWebKeys.KALEO_DRAFT_DEFINITION);

		if (kaleoDraftDefinition != null) {
			return kaleoDraftDefinition;
		}

		long kaleoDraftDefinitionId = GetterUtil.getLong(
			request.getAttribute(
				KaleoDesignerWebKeys.KALEO_DRAFT_DEFINITION_ID));

		if (kaleoDraftDefinitionId > 0) {
			return KaleoDraftDefinitionLocalServiceUtil.getKaleoDraftDefinition(
				kaleoDraftDefinitionId);
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(
			request, "title");

		Locale defaultLocale = themeDisplay.getSiteDefaultLocale();

		String name = ParamUtil.getString(
			request, "name", titleMap.get(defaultLocale));

		if (Validator.isNull(name)) {
			return null;
		}

		int version = ParamUtil.getInteger(request, "version");
		int draftVersion = ParamUtil.getInteger(request, "draftVersion");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			request);

		if ((version > 0) && (draftVersion > 0)) {
			return KaleoDraftDefinitionLocalServiceUtil.getKaleoDraftDefinition(
				name, version, draftVersion, serviceContext);
		}

		try {
			return KaleoDraftDefinitionLocalServiceUtil.
				getLatestKaleoDraftDefinition(name, version, serviceContext);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Could not find Kaleo draft definition for {name=" + name +
						", version=" + version + "}");
			}
		}

		if (version <= 0) {
			return null;
		}

		WorkflowDefinition workflowDefinition =
			WorkflowDefinitionManagerUtil.getWorkflowDefinition(
				serviceContext.getCompanyId(), name, version);

		if (!titleMap.containsValue(defaultLocale)) {
			titleMap.put(defaultLocale, name);
		}

		String content = workflowDefinition.getContent();

		return KaleoDraftDefinitionLocalServiceUtil.addKaleoDraftDefinition(
			themeDisplay.getUserId(), themeDisplay.getCompanyGroupId(), name,
			titleMap, content, version, 1, serviceContext);
	}

	/**
	 * Returns the Kaleo draft definition, or creates one if it does not exist.
	 *
	 * @param  portletRequest the portlet request from which to read the
	 *         parameters
	 * @return the Kaleo draft definition
	 * @throws Exception if an exception occurred
	 */
	public static KaleoDraftDefinition getKaleoDraftDefinition(
			PortletRequest portletRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		return getKaleoDraftDefinition(request);
	}

	/**
	 * Returns <code>true</code> if the Kaleo draft definition is published.
	 *
	 * @param  kaleoDraftDefinition the Kaleo draft definition
	 * @return <code>true</code> if the Kaleo draft definition is published;
	 *         <code>false</code> otherwise
	 */
	public static boolean isPublished(
		KaleoDraftDefinition kaleoDraftDefinition) {

		if (kaleoDraftDefinition.getVersion() == 0) {
			return false;
		}

		try {
			WorkflowDefinition workflowDefinition =
				WorkflowDefinitionManagerUtil.getWorkflowDefinition(
					kaleoDraftDefinition.getCompanyId(),
					kaleoDraftDefinition.getName(),
					kaleoDraftDefinition.getVersion());

			if (workflowDefinition.isActive() &&
				(kaleoDraftDefinition.getDraftVersion() == 1)) {

				return true;
			}
		}
		catch (Exception e) {
		}

		return false;
	}

	/**
	 * Returns the localized titles.
	 *
	 * @param  titleMap the locales and localized titles
	 * @return the localized titles
	 */
	private static String _getLocalizedTitleXML(Map<Locale, String> titleMap) {
		String title = StringPool.BLANK;

		if (titleMap == null) {
			return title;
		}

		String defaultLanguageId = LocaleUtil.toLanguageId(
			LocaleUtil.getDefault());

		for (Locale locale : LanguageUtil.getAvailableLocales()) {
			String languageId = LocaleUtil.toLanguageId(locale);

			String localizedTitle = titleMap.get(locale);

			if (Validator.isNotNull(localizedTitle)) {
				title = LocalizationUtil.updateLocalization(
					title, "Title", localizedTitle, languageId,
					defaultLanguageId);
			}
		}

		return title;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		KaleoDesignerUtil.class);

}