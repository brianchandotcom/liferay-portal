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
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionManagerUtil;
import com.liferay.portal.workflow.kaleo.designer.web.internal.constants.KaleoDesignerActionKeys;
import com.liferay.portal.workflow.kaleo.designer.web.internal.permission.KaleoDesignerPermission;
import com.liferay.portal.workflow.kaleo.designer.web.internal.portlet.display.context.util.KaleoDesignerRequestHelper;
import com.liferay.portal.workflow.kaleo.designer.web.internal.search.KaleoDefinitionVersionSearchTerms;
import com.liferay.portal.workflow.kaleo.designer.web.internal.util.filter.KaleoDefinitionVersionActivePredicateFilter;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinition;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionVersionLocalService;
import com.liferay.portal.workflow.kaleo.util.comparator.KaleoDefinitionVersionModifiedDateComparator;
import com.liferay.portal.workflow.kaleo.util.comparator.KaleoDefinitionVersionTitleComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.portlet.RenderRequest;

/**
 * @author Rafael Praxedes
 */
public class KaleoDesignerDisplayContext {

	public KaleoDesignerDisplayContext(
			RenderRequest renderRequest,
			KaleoDefinitionVersionLocalService
				kaleoDefinitionVersionLocalService,
			UserLocalService userLocalService)
		throws PortalException {

		_themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_kaleoDefinitionVersionLocalService =
			kaleoDefinitionVersionLocalService;
		_userLocalService = userLocalService;

		_kaleoDesignerRequestHelper = new KaleoDesignerRequestHelper(
			renderRequest);
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

		if (orderByCol.equals("title")) {
			orderByComparator = new KaleoDefinitionVersionTitleComparator(
				orderByAsc);
		}
		else if (orderByCol.equals("last-modified")) {
			orderByComparator =
				new KaleoDefinitionVersionModifiedDateComparator(orderByAsc);
		}

		return orderByComparator;
	}

	public Date getModifiedDate(KaleoDefinitionVersion kaleoDefinitionVersion) {
		try {
			KaleoDefinition kaleoDefinition =
				kaleoDefinitionVersion.getKaleoDefinition();

			return kaleoDefinition.getModifiedDate();
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}
		}

		return kaleoDefinitionVersion.getModifiedDate();
	}

	public String getOrderByCol() {
		return ParamUtil.getString(
			_kaleoDesignerRequestHelper.getRequest(), "orderByCol", "title");
	}

	public String getOrderByType() {
		return ParamUtil.getString(
			_kaleoDesignerRequestHelper.getRequest(), "orderByType", "asc");
	}

	public String getPublishKaleoDefinitionVersionButtonLabel(
		KaleoDefinitionVersion kaleoDefinitionVersion) {

		try {
			KaleoDefinition kaleoDefinition =
				kaleoDefinitionVersion.getKaleoDefinition();

			if (kaleoDefinition.isActive()) {
				return "update";
			}
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}
		}

		return "publish";
	}

	public List<KaleoDefinitionVersion> getSearchContainerResults(
			SearchContainer<KaleoDefinitionVersion> searchContainer, int status)
		throws PortalException {

		KaleoDefinitionVersionSearchTerms searchTerms =
			(KaleoDefinitionVersionSearchTerms)searchContainer.getSearchTerms();

		List<KaleoDefinitionVersion> kaleoDefinitionVersions =
			_kaleoDefinitionVersionLocalService.
				getLatestKaleoDefinitionVersions(
					_kaleoDesignerRequestHelper.getCompanyId(),
					searchTerms.getKeywords(), WorkflowConstants.STATUS_ANY,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					getKaleoDefinitionVersionOrderByComparator());

		kaleoDefinitionVersions = ListUtil.filter(
			kaleoDefinitionVersions,
			new KaleoDefinitionVersionActivePredicateFilter(status));

		searchContainer.setTotal(kaleoDefinitionVersions.size());

		if (kaleoDefinitionVersions.size() >
				(searchContainer.getEnd() - searchContainer.getStart())) {

			kaleoDefinitionVersions = ListUtil.subList(
				kaleoDefinitionVersions, searchContainer.getStart(),
				searchContainer.getEnd());
		}

		return kaleoDefinitionVersions;
	}

	public String getUserName(KaleoDefinitionVersion kaleoDefinitionVersion) {
		User user = _userLocalService.fetchUser(
			kaleoDefinitionVersion.getUserId());

		if ((user == null) || user.isDefaultUser() ||
			Validator.isNull(user.getFullName())) {

			return null;
		}

		return user.getFullName();
	}

	public List<WorkflowDefinition> getWorkflowDefinitions(
			KaleoDefinitionVersion kaleoDefinitionVersion)
		throws PortalException {

		int count =
			WorkflowDefinitionManagerUtil.getActiveWorkflowDefinitionCount(
				_kaleoDesignerRequestHelper.getCompanyId(),
				kaleoDefinitionVersion.getName());

		if (count <= 0) {
			return new ArrayList<>();
		}

		List<WorkflowDefinition> workflowDefinitions =
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

	public boolean isPublishKaleoDefinitionVersionButtonVisible(
		PermissionChecker permissionChecker) {

		return KaleoDesignerPermission.contains(
			permissionChecker, _themeDisplay.getCompanyGroupId(),
			KaleoDesignerActionKeys.PUBLISH);
	}

	public boolean isSaveKaleoDefinitionVersionButtonVisible(
		PermissionChecker permissionChecker,
		KaleoDefinitionVersion kaleoDefinitionVersion) {

		if (!KaleoDesignerPermission.contains(
				permissionChecker, _themeDisplay.getCompanyGroupId(),
				KaleoDesignerActionKeys.ADD_DRAFT)) {

			return false;
		}

		if (kaleoDefinitionVersion == null) {
			return true;
		}

		return kaleoDefinitionVersion.isDraft();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		KaleoDesignerDisplayContext.class);

	private final KaleoDefinitionVersionLocalService
		_kaleoDefinitionVersionLocalService;
	private final KaleoDesignerRequestHelper _kaleoDesignerRequestHelper;
	private final ThemeDisplay _themeDisplay;
	private final UserLocalService _userLocalService;

}