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

package com.liferay.translation.web.internal.display.context;

import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.translation.model.TranslationEntry;
import com.liferay.translation.service.TranslationEntryLocalService;
import com.liferay.translation.web.internal.configuration.FFBulkTranslationConfiguration;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Adolfo Pérez
 */
public class ImportTranslationDisplayContext {

	public ImportTranslationDisplayContext(
		long classNameId, long classPK, long companyId,
		FFBulkTranslationConfiguration ffBulkTranslationConfiguration,
		long groupId, HttpServletRequest httpServletRequest,
		LiferayPortletResponse liferayPortletResponse, String title,
		TranslationEntryLocalService translationEntryLocalService,
		WorkflowDefinitionLinkLocalService workflowDefinitionLinkLocalService) {

		_classNameId = classNameId;
		_classPK = classPK;
		_companyId = companyId;
		_ffBulkTranslationConfiguration = ffBulkTranslationConfiguration;
		_groupId = groupId;
		_httpServletRequest = httpServletRequest;
		_liferayPortletResponse = liferayPortletResponse;
		_title = title;
		_translationEntryLocalService = translationEntryLocalService;
		_workflowDefinitionLinkLocalService =
			workflowDefinitionLinkLocalService;
	}

	public PortletURL getImportTranslationURL() throws PortalException {
		return PortletURLBuilder.createActionURL(
			_liferayPortletResponse
		).setActionName(
			"/translation/import_translation"
		).setParameter(
			"classNameId", _classNameId
		).setParameter(
			"classPK", _classPK
		).setParameter(
			"groupId", _groupId
		).setParameter(
			"title", getTitle()
		).buildPortletURL();
	}

	public String getPublishButtonLabel() throws PortalException {
		if (_workflowDefinitionLinkLocalService.hasWorkflowDefinitionLink(
				_companyId, _groupId, TranslationEntry.class.getName())) {

			return "submit-for-publication";
		}

		return "publish";
	}

	public String getRedirect() {
		if (Validator.isNotNull(_redirect)) {
			return _redirect;
		}

		_redirect = ParamUtil.getString(_httpServletRequest, "redirect");

		return _redirect;
	}

	public String getSaveButtonLabel() {
		if (_workflowDefinitionLinkLocalService.hasWorkflowDefinitionLink(
				_companyId, _groupId, TranslationEntry.class.getName())) {

			return "save-as-draft";
		}

		return "save";
	}

	public String getTitle() throws PortalException {
		return _title;
	}

	public boolean isBulkTranslationEnabled() {
		return _ffBulkTranslationConfiguration.enabled();
	}

	public boolean isPending() throws PortalException {
		if (_workflowDefinitionLinkLocalService.hasWorkflowDefinitionLink(
				_companyId, _groupId, TranslationEntry.class.getName()) &&
			_isAnyTranslationPending()) {

			return true;
		}

		return false;
	}

	private boolean _isAnyTranslationPending() {
		if (_classPK == 0) {
			return false;
		}

		int translationEntriesCount =
			_translationEntryLocalService.getTranslationEntriesCount(
				PortalUtil.getClassName(_classNameId), _classPK,
				new int[] {
					WorkflowConstants.STATUS_APPROVED,
					WorkflowConstants.STATUS_DRAFT
				},
				true);

		if (translationEntriesCount > 0) {
			return true;
		}

		return false;
	}

	private final long _classNameId;
	private final long _classPK;
	private final long _companyId;
	private final FFBulkTranslationConfiguration
		_ffBulkTranslationConfiguration;
	private final long _groupId;
	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private String _redirect;
	private final String _title;
	private final TranslationEntryLocalService _translationEntryLocalService;
	private final WorkflowDefinitionLinkLocalService
		_workflowDefinitionLinkLocalService;

}