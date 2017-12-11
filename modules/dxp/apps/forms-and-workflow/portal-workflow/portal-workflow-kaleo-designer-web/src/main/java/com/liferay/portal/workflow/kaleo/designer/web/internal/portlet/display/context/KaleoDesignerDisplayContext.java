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

package com.liferay.portal.workflow.kaleo.designer.web.internal.portlet.display.context;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionManagerUtil;
import com.liferay.portal.workflow.kaleo.designer.web.internal.portlet.display.context.util.KaleoDesignerRequestHelper;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;
import com.liferay.portal.workflow.kaleo.util.comparator.KaleoDefinitionVersionNameComparator;
import com.liferay.portal.workflow.kaleo.util.comparator.KaleoDefinitionVersionTitleComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Rafael Praxedes
 */
public class KaleoDesignerDisplayContext {

	public KaleoDesignerDisplayContext(
			RenderRequest renderRequest, RenderResponse renderResponse,
			UserLocalService userLocalService)
		throws PortalException {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_userLocalService = userLocalService;

		_kaleoDesignerRequestHelper = new KaleoDesignerRequestHelper(
			renderRequest);
	}

	public PortletURL getBasePortletURL() throws PortalException {
		PortletURL portletURL = _renderResponse.createRenderURL();

		String keywords = ParamUtil.getString(_renderRequest, "keywords");

		if (Validator.isNotNull(keywords)) {
			portletURL.setParameter("keywords", keywords);
		}

		return portletURL;
	}

	public OrderByComparator<KaleoDefinitionVersion>
		getKaleoDefinitionVersionOrderByComparator() {

		boolean orderByAsc = false;
		String orderByCol = getOrderByCol();
		String orderByType = getOrderByType();

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<KaleoDefinitionVersion> orderByComparator = null;

		if (orderByCol.equals("name")) {
			orderByComparator = new KaleoDefinitionVersionNameComparator(
				orderByAsc);
		}
		else if (orderByCol.equals("title")) {
			orderByComparator = new KaleoDefinitionVersionTitleComparator(
				orderByAsc);
		}

		return orderByComparator;
	}

	public String getKaleoDraftDefinitionDisplayStyle() {
		return _DISPLAY_VIEWS[0];
	}

	public String[] getKaleoDraftDefinitionDisplayViews() {
		return _DISPLAY_VIEWS;
	}

	public String getOrderByCol() {
		return ParamUtil.getString(_renderRequest, "orderByCol", "name");
	}

	public String getOrderByType() {
		return ParamUtil.getString(_renderRequest, "orderByType", "desc");
	}

	public String[] getOrderColumns() {
		return _ORDER_COLUMNS;
	}

	public ResourceBundle getResourceBundle(Locale locale) {
		Class<?> clazz = getClass();

		return ResourceBundleUtil.getBundle(
			"content.Language", locale, clazz.getClassLoader());
	}

	public String getUserName(KaleoDefinitionVersion workflowDefinition) {
		User user = _userLocalService.fetchUser(workflowDefinition.getUserId());

		if ((user == null) || user.isDefaultUser() ||
			Validator.isNull(user.getFullName())) {

			return null;
		}

		return user.getFullName();
	}

	public List<WorkflowDefinition> getWorkflowDefinitions(
			KaleoDefinitionVersion kaleoDefinitionVersion)
		throws PortalException {

		List<WorkflowDefinition> workflowDefinitions = new ArrayList<>();

		workflowDefinitions =
			WorkflowDefinitionManagerUtil.getWorkflowDefinitions(
				_kaleoDesignerRequestHelper.getCompanyId(),
				kaleoDefinitionVersion.getName(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

		Collections.reverse(workflowDefinitions);

		return workflowDefinitions;
	}

	public String getWorkflowDefinitionVersionDisplayUserName(
		WorkflowDefinition workflowDefinition) {

		User user = _userLocalService.fetchUser(workflowDefinition.getUserId());

		if ((user == null) || user.isDefaultUser() ||
			Validator.isNull(user.getFullName())) {

			return StringPool.BLANK;
		}

		return user.getFullName();
	}

	private static final String[] _DISPLAY_VIEWS = {"list"};

	private static final String[] _ORDER_COLUMNS = {"name", "title"};

	private final KaleoDesignerRequestHelper _kaleoDesignerRequestHelper;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final UserLocalService _userLocalService;

}