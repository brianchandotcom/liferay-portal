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
import com.liferay.dynamic.data.mapping.constants.DDMTemplateConstants;
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
import com.liferay.portal.kernel.template.TemplateHandler;
import com.liferay.portal.kernel.template.TemplateHandlerRegistryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portlet.display.template.PortletDisplayTemplate;
import com.liferay.template.web.internal.security.permissions.resource.DDMTemplatePermission;
import com.liferay.template.web.internal.util.DDMTemplateActionDropdownItemsProvider;

import java.util.List;
import java.util.Objects;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Lourdes Fernández Besada
 */
public class TemplateDisplayContext {

	public TemplateDisplayContext(
		DDMWebConfiguration ddmWebConfiguration,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		_ddmWebConfiguration = ddmWebConfiguration;
		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;

		_httpServletRequest = PortalUtil.getHttpServletRequest(
			_liferayPortletRequest);
		_themeDisplay = (ThemeDisplay)liferayPortletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public boolean enableTemplateCreation() {
		return _ddmWebConfiguration.enableTemplateCreation();
	}

	public List<DropdownItem> getDDMTemplateActionDropdownItems(
			DDMTemplate ddmTemplate)
		throws Exception {

		DDMTemplateActionDropdownItemsProvider
			ddmTemplateActionDropdownItemsProvider =
				new DDMTemplateActionDropdownItemsProvider(
					ddmTemplate, _httpServletRequest, _liferayPortletResponse);

		return ddmTemplateActionDropdownItemsProvider.getActionDropdownItems();
	}

	public String getDDMTemplateEditURL(DDMTemplate ddmTemplate)
		throws PortalException {

		if (!DDMTemplatePermission.contains(
				_themeDisplay.getPermissionChecker(), ddmTemplate,
				ActionKeys.UPDATE)) {

			return StringPool.BLANK;
		}

		return PortletURLBuilder.createRenderURL(
			_liferayPortletResponse
		).setMVCPath(
			"/edit_ddm_template.jsp"
		).setRedirect(
			_themeDisplay.getURLCurrent()
		).setParameter(
			"ddmTemplateId", ddmTemplate.getTemplateId()
		).buildString();
	}

	public String getDDMTemplateScope(DDMTemplate ddmTemplate)
		throws PortalException {

		Group group = GroupLocalServiceUtil.getGroup(ddmTemplate.getGroupId());

		return LanguageUtil.get(
			_httpServletRequest, group.getScopeLabel(_themeDisplay));
	}

	public String getDDMTemplateType(DDMTemplate ddmTemplate) {
		String type = ddmTemplate.getType();

		if (!type.equals(DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY)) {
			return type;
		}

		return getTemplateType(ddmTemplate.getClassNameId());
	}

	public List<NavigationItem> getNavigationItems() {
		return NavigationItemListBuilder.add(
			navigationItem -> {
				navigationItem.setActive(
					Objects.equals(_getTabs1(), "information-templates"));
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
					Objects.equals(_getTabs1(), "widget-templates"));
				navigationItem.setHref(
					_liferayPortletResponse.createRenderURL(), "tabs1",
					"widget-templates");
				navigationItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "widget-templates"));
			}
		).build();
	}

	public SearchContainer<DDMTemplate> getTemplateSearchContainer() {
		if (_ddmTemplateSearchContainer != null) {
			return _ddmTemplateSearchContainer;
		}

		SearchContainer<DDMTemplate> ddmTemplateSearchContainer =
			new SearchContainer<>(
				_liferayPortletRequest, _getPortletURL(), null,
				"there-are-no-templates");

		ddmTemplateSearchContainer.setOrderByCol(_getOrderByCol());
		ddmTemplateSearchContainer.setOrderByComparator(
			_getTemplateOrderByComparator());
		ddmTemplateSearchContainer.setOrderByType(_getOrderByType());
		ddmTemplateSearchContainer.setRowChecker(
			new EmptyOnClickRowChecker(_liferayPortletResponse));
		ddmTemplateSearchContainer.setResults(
			DDMTemplateServiceUtil.search(
				_themeDisplay.getCompanyId(),
				new long[] {_themeDisplay.getScopeGroupId()},
				TemplateHandlerRegistryUtil.getClassNameIds(), null,
				PortalUtil.getClassNameId(PortletDisplayTemplate.class),
				_getKeywords(), StringPool.BLANK, StringPool.BLANK,
				WorkflowConstants.STATUS_ANY,
				ddmTemplateSearchContainer.getStart(),
				ddmTemplateSearchContainer.getEnd(),
				ddmTemplateSearchContainer.getOrderByComparator()));
		ddmTemplateSearchContainer.setTotal(
			DDMTemplateServiceUtil.searchCount(
				_themeDisplay.getCompanyId(),
				new long[] {_themeDisplay.getScopeGroupId()},
				TemplateHandlerRegistryUtil.getClassNameIds(), null,
				PortalUtil.getClassNameId(PortletDisplayTemplate.class),
				_getKeywords(), StringPool.BLANK, StringPool.BLANK,
				WorkflowConstants.STATUS_ANY));

		_ddmTemplateSearchContainer = ddmTemplateSearchContainer;

		return _ddmTemplateSearchContainer;
	}

	public String getTemplateType(long classNameId) {
		TemplateHandler templateHandler =
			TemplateHandlerRegistryUtil.getTemplateHandler(classNameId);

		return templateHandler.getName(_themeDisplay.getLocale());
	}

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
			_getTabs1()
		).build();
	}

	private String _getTabs1() {
		if (_tabs1 != null) {
			return _tabs1;
		}

		_tabs1 = ParamUtil.getString(
			_liferayPortletRequest, "tabs1", "information-templates");

		return _tabs1;
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
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private String _orderByCol;
	private String _orderByType;
	private String _tabs1;
	private final ThemeDisplay _themeDisplay;

}