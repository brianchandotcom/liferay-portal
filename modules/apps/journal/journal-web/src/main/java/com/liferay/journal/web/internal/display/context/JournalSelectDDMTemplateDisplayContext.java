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

package com.liferay.journal.web.internal.display.context;

import com.liferay.depot.util.SiteConnectedGroupGroupProviderUtil;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMTemplateServiceUtil;
import com.liferay.dynamic.data.mapping.util.DDMUtil;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.portlet.SearchDisplayStyleUtil;
import com.liferay.portal.kernel.portlet.SearchOrderByUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class JournalSelectDDMTemplateDisplayContext {

	public JournalSelectDDMTemplateDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;

		_httpServletRequest = PortalUtil.getHttpServletRequest(renderRequest);
	}

	public long getDDMStructureId() {
		if (_ddmStructureId != null) {
			return _ddmStructureId;
		}

		_ddmStructureId = ParamUtil.getLong(_renderRequest, "ddmStructureId");

		return _ddmStructureId;
	}

	public long getDDMTemplateId() {
		if (_ddmTemplateId != null) {
			return _ddmTemplateId;
		}

		_ddmTemplateId = ParamUtil.getLong(
			_httpServletRequest, "ddmTemplateId");

		return _ddmTemplateId;
	}

	public String getDisplayStyle() {
		if (_displayStyle != null) {
			return _displayStyle;
		}

		_displayStyle = SearchDisplayStyleUtil.getDisplayStyle(
			_httpServletRequest, JournalPortletKeys.JOURNAL,
			"ddm-template-display-style", "icon");

		return _displayStyle;
	}

	public String getEventName() {
		if (_eventName != null) {
			return _eventName;
		}

		_eventName = ParamUtil.getString(
			_httpServletRequest, "eventName",
			_renderResponse.getNamespace() + "selectDDMTemplate");

		return _eventName;
	}

	public String getOrderByCol() {
		if (Validator.isNotNull(_orderByCol)) {
			return _orderByCol;
		}

		_orderByCol = SearchOrderByUtil.getOrderByCol(
			_httpServletRequest, JournalPortletKeys.JOURNAL,
			"select-ddm-template-order-by-col", "modified-date");

		return _orderByCol;
	}

	public String getOrderByType() {
		if (Validator.isNotNull(_orderByType)) {
			return _orderByType;
		}

		_orderByType = SearchOrderByUtil.getOrderByType(
			_httpServletRequest, JournalPortletKeys.JOURNAL,
			"select-ddm-template-order-by-type", "desc");

		return _orderByType;
	}

	public SearchContainer<DDMTemplate> getTemplateSearch() throws Exception {
		if (_templateSearch != null) {
			return _templateSearch;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		SearchContainer<DDMTemplate> templateSearch = new SearchContainer(
			_renderRequest, _getPortletURL(), null, "there-are-no-templates");

		if (templateSearch.isSearch()) {
			templateSearch.setEmptyResultsMessage("no-templates-were-found");
		}

		templateSearch.setOrderByCol(getOrderByCol());
		templateSearch.setOrderByComparator(
			DDMUtil.getTemplateOrderByComparator(
				getOrderByCol(), getOrderByType()));
		templateSearch.setOrderByType(getOrderByType());

		long[] groupIds =
			SiteConnectedGroupGroupProviderUtil.
				getCurrentAndAncestorSiteAndDepotGroupIds(
					themeDisplay.getScopeGroupId(), true);

		templateSearch.setResultsAndTotal(
			() -> DDMTemplateServiceUtil.search(
				themeDisplay.getCompanyId(), groupIds,
				new long[] {PortalUtil.getClassNameId(DDMStructure.class)},
				new long[] {getDDMStructureId()},
				PortalUtil.getClassNameId(JournalArticle.class.getName()),
				_getKeywords(), StringPool.BLANK, StringPool.BLANK,
				WorkflowConstants.STATUS_ANY, templateSearch.getStart(),
				templateSearch.getEnd(), templateSearch.getOrderByComparator()),
			DDMTemplateServiceUtil.searchCount(
				themeDisplay.getCompanyId(), groupIds,
				new long[] {PortalUtil.getClassNameId(DDMStructure.class)},
				new long[] {getDDMStructureId()},
				PortalUtil.getClassNameId(JournalArticle.class.getName()),
				_getKeywords(), StringPool.BLANK, StringPool.BLANK,
				WorkflowConstants.STATUS_ANY));

		_templateSearch = templateSearch;

		return _templateSearch;
	}

	public boolean isSearch() {
		if (Validator.isNotNull(_getKeywords())) {
			return true;
		}

		return false;
	}

	private String _getKeywords() {
		if (_keywords != null) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(_renderRequest, "keywords");

		return _keywords;
	}

	private PortletURL _getPortletURL() {
		return PortletURLBuilder.createRenderURL(
			_renderResponse
		).setMVCPath(
			"/select_ddm_template.jsp"
		).setKeywords(
			() -> {
				String keywords = _getKeywords();

				if (Validator.isNotNull(keywords)) {
					return keywords;
				}

				return null;
			}
		).setParameter(
			"ddmStructureId",
			() -> {
				long ddmStructureId = getDDMStructureId();

				if (ddmStructureId != 0) {
					return ddmStructureId;
				}

				return null;
			}
		).setParameter(
			"ddmTemplateId",
			() -> {
				long ddmTemplateId = getDDMTemplateId();

				if (ddmTemplateId != 0) {
					return ddmTemplateId;
				}

				return null;
			}
		).setParameter(
			"orderByCol",
			() -> {
				String orderByCol = getOrderByCol();

				if (Validator.isNotNull(orderByCol)) {
					return orderByCol;
				}

				return null;
			}
		).setParameter(
			"orderByType",
			() -> {
				String orderByType = getOrderByType();

				if (Validator.isNotNull(orderByType)) {
					return orderByType;
				}

				return null;
			}
		).buildPortletURL();
	}

	private Long _ddmStructureId;
	private Long _ddmTemplateId;
	private String _displayStyle;
	private String _eventName;
	private final HttpServletRequest _httpServletRequest;
	private String _keywords;
	private String _orderByCol;
	private String _orderByType;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private SearchContainer<DDMTemplate> _templateSearch;

}