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

import com.liferay.info.item.provider.InfoItemWorkflowProvider;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Adolfo Pérez
 */
public class ImportTranslationDisplayContext {

	public ImportTranslationDisplayContext(
		long classNameId, long classPK, long groupId,
		HttpServletRequest httpServletRequest,
		InfoItemWorkflowProvider<Object> infoItemWorkflowProvider,
		LiferayPortletResponse liferayPortletResponse, Object model,
		String title) {

		_classNameId = classNameId;
		_classPK = classPK;
		_groupId = groupId;
		_httpServletRequest = httpServletRequest;
		_infoItemWorkflowProvider = infoItemWorkflowProvider;
		_liferayPortletResponse = liferayPortletResponse;
		_model = model;
		_title = title;
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
		if ((_infoItemWorkflowProvider == null) ||
			!_infoItemWorkflowProvider.isWorkflowEnabled(_model)) {

			return "publish";
		}

		return "submit-for-publication";
	}

	public String getRedirect() {
		if (Validator.isNotNull(_redirect)) {
			return _redirect;
		}

		_redirect = ParamUtil.getString(_httpServletRequest, "redirect");

		return _redirect;
	}

	public String getSaveButtonLabel() {
		if (_infoItemWorkflowProvider == null) {
			return "save";
		}

		int status = _infoItemWorkflowProvider.getStatus(_model);

		if ((status == WorkflowConstants.STATUS_APPROVED) ||
			(status == WorkflowConstants.STATUS_DRAFT) ||
			(status == WorkflowConstants.STATUS_EXPIRED) ||
			(status == WorkflowConstants.STATUS_SCHEDULED)) {

			return "save-as-draft";
		}

		return "save";
	}

	public String getTitle() throws PortalException {
		return _title;
	}

	public boolean isPending() throws PortalException {
		if ((_infoItemWorkflowProvider == null) ||
			!_infoItemWorkflowProvider.isWorkflowEnabled(_model)) {

			return false;
		}

		if (_infoItemWorkflowProvider.getStatus(_model) ==
				WorkflowConstants.STATUS_PENDING) {

			return true;
		}

		return false;
	}

	private final long _classNameId;
	private final long _classPK;
	private final long _groupId;
	private final HttpServletRequest _httpServletRequest;
	private final InfoItemWorkflowProvider<Object> _infoItemWorkflowProvider;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final Object _model;
	private String _redirect;
	private final String _title;

}