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

package com.liferay.template.web.internal.display.context;

import com.liferay.dynamic.data.mapping.configuration.DDMWebConfiguration;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMTemplateServiceUtil;
import com.liferay.dynamic.data.mapping.util.comparator.TemplateIdComparator;
import com.liferay.dynamic.data.mapping.util.comparator.TemplateModifiedDateComparator;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemListBuilder;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.template.constants.TemplatePortletKeys;
import com.liferay.template.web.internal.security.permissions.resource.DDMTemplatePermission;
import com.liferay.template.web.internal.util.DDMTemplateActionDropdownItemsProvider;
import com.liferay.template.web.internal.util.FFTemplateConfigurationUtil;

import java.util.List;
import java.util.Objects;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Lourdes Fernández Besada
 * @author Eudaldo Alonso
 */
public abstract class BaseTemplateDisplayContext {

	public BaseTemplateDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		this.liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;

		_ddmWebConfiguration =
			(DDMWebConfiguration)liferayPortletRequest.getAttribute(
				DDMWebConfiguration.class.getName());
		_httpServletRequest = PortalUtil.getHttpServletRequest(
			liferayPortletRequest);
		themeDisplay = (ThemeDisplay)liferayPortletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public abstract long[] getClassNameIds();

	public List<DropdownItem> getDDMTemplateActionDropdownItems(
			DDMTemplate ddmTemplate)
		throws Exception {

		DDMTemplateActionDropdownItemsProvider
			ddmTemplateActionDropdownItemsProvider =
				new DDMTemplateActionDropdownItemsProvider(
					isAddButtonEnabled(), ddmTemplate, _httpServletRequest,
					_liferayPortletResponse, getTabs1());

		return ddmTemplateActionDropdownItemsProvider.getActionDropdownItems();
	}

	public String getDDMTemplateEditURL(DDMTemplate ddmTemplate)
		throws PortalException {

		if (!DDMTemplatePermission.contains(
				themeDisplay.getPermissionChecker(), ddmTemplate,
				ActionKeys.UPDATE)) {

			return StringPool.BLANK;
		}

		return PortletURLBuilder.createRenderURL(
			_liferayPortletResponse
		).setMVCRenderCommandName(
			"/template/edit_ddm_template"
		).setRedirect(
			themeDisplay.getURLCurrent()
		).setTabs1(
			getTabs1()
		).setParameter(
			"ddmTemplateId", ddmTemplate.getTemplateId()
		).buildString();
	}

	public String getDDMTemplateScope(DDMTemplate ddmTemplate)
		throws PortalException {

		Group group = GroupLocalServiceUtil.getGroup(ddmTemplate.getGroupId());

		return LanguageUtil.get(
			_httpServletRequest, group.getScopeLabel(themeDisplay));
	}

	public List<NavigationItem> getNavigationItems() {
		return NavigationItemListBuilder.add(
			FFTemplateConfigurationUtil::informationTemplatesEnabled,
			navigationItem -> {
				navigationItem.setActive(
					Objects.equals(getTabs1(), "information-templates"));
				navigationItem.setHref(
					_liferayPortletResponse.createRenderURL(), "tabs1",
					"information-templates");
				navigationItem.setLabel(
					LanguageUtil.get(
						_httpServletRequest, "information-templates"));
			}
		).add(
			navigationItem -> {
				navigationItem.setActive(
					Objects.equals(getTabs1(), "widget-templates"));
				navigationItem.setHref(
					_liferayPortletResponse.createRenderURL(), "tabs1",
					"widget-templates");
				navigationItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "widget-templates"));
			}
		).build();
	}

	public abstract long getResourceClassNameId();

	public String getTabs1() {
		if (_tabs1 != null) {
			return _tabs1;
		}

		_tabs1 = ParamUtil.getString(
			liferayPortletRequest, "tabs1", "widget-templates");

		return _tabs1;
	}

	public SearchContainer<DDMTemplate> getTemplateSearchContainer() {
		if (_ddmTemplateSearchContainer != null) {
			return _ddmTemplateSearchContainer;
		}

		SearchContainer<DDMTemplate> ddmTemplateSearchContainer =
			new SearchContainer<>(
				liferayPortletRequest, _getPortletURL(), null,
				"there-are-no-templates");

		ddmTemplateSearchContainer.setOrderByCol(_getOrderByCol());
		ddmTemplateSearchContainer.setOrderByComparator(
			_getTemplateOrderByComparator());
		ddmTemplateSearchContainer.setOrderByType(_getOrderByType());
		ddmTemplateSearchContainer.setRowChecker(
			new EmptyOnClickRowChecker(_liferayPortletResponse));

		ddmTemplateSearchContainer.setResults(
			DDMTemplateServiceUtil.search(
				themeDisplay.getCompanyId(),
				new long[] {themeDisplay.getScopeGroupId()}, getClassNameIds(),
				null, getResourceClassNameId(), _getKeywords(),
				StringPool.BLANK, StringPool.BLANK,
				WorkflowConstants.STATUS_ANY,
				ddmTemplateSearchContainer.getStart(),
				ddmTemplateSearchContainer.getEnd(),
				ddmTemplateSearchContainer.getOrderByComparator()));
		ddmTemplateSearchContainer.setTotal(
			DDMTemplateServiceUtil.searchCount(
				themeDisplay.getCompanyId(),
				new long[] {themeDisplay.getScopeGroupId()}, getClassNameIds(),
				null, getResourceClassNameId(), _getKeywords(),
				StringPool.BLANK, StringPool.BLANK,
				WorkflowConstants.STATUS_ANY));

		_ddmTemplateSearchContainer = ddmTemplateSearchContainer;

		return _ddmTemplateSearchContainer;
	}

	public abstract String getTemplateTypeLabel(long classNameId);

	public boolean isAddButtonEnabled() {
		if (!_ddmWebConfiguration.enableTemplateCreation()) {
			return false;
		}

		Group scopeGroup = themeDisplay.getScopeGroup();

		if (!scopeGroup.hasLocalOrRemoteStagingGroup() ||
			!scopeGroup.isStagedPortlet(TemplatePortletKeys.TEMPLATE)) {

			return true;
		}

		return false;
	}

	protected final LiferayPortletRequest liferayPortletRequest;
	protected final ThemeDisplay themeDisplay;

	private String _getKeywords() {
		if (_keywords != null) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(_httpServletRequest, "keywords");

		return _keywords;
	}

	private String _getOrderByCol() {
		if (Validator.isNotNull(_orderByCol)) {
			return _orderByCol;
		}

		_orderByCol = ParamUtil.getString(
			_httpServletRequest, SearchContainer.DEFAULT_ORDER_BY_COL_PARAM,
			"modified-date");

		return _orderByCol;
	}

	private String _getOrderByType() {
		if (Validator.isNotNull(_orderByType)) {
			return _orderByType;
		}

		_orderByType = ParamUtil.getString(
			_httpServletRequest, SearchContainer.DEFAULT_ORDER_BY_TYPE_PARAM,
			"asc");

		return _orderByType;
	}

	private PortletURL _getPortletURL() {
		return PortletURLBuilder.createRenderURL(
			_liferayPortletResponse
		).setTabs1(
			getTabs1()
		).buildPortletURL();
	}

	private OrderByComparator<DDMTemplate> _getTemplateOrderByComparator() {
		OrderByComparator<DDMTemplate> orderByComparator = null;

		boolean orderByAsc = false;

		if (Objects.equals(_getOrderByType(), "asc")) {
			orderByAsc = true;
		}

		if (Objects.equals(_getOrderByCol(), "id")) {
			orderByComparator = new TemplateIdComparator(orderByAsc);
		}
		else if (Objects.equals(_getOrderByCol(), "modified-date")) {
			orderByComparator = new TemplateModifiedDateComparator(orderByAsc);
		}

		return orderByComparator;
	}

	private SearchContainer<DDMTemplate> _ddmTemplateSearchContainer;
	private final DDMWebConfiguration _ddmWebConfiguration;
	private final HttpServletRequest _httpServletRequest;
	private String _keywords;
	private final LiferayPortletResponse _liferayPortletResponse;
	private String _orderByCol;
	private String _orderByType;
	private String _tabs1;

}