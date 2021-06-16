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

package com.liferay.layout.content.page.editor.web.internal.portlet.action;

import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletApp;
import com.liferay.portal.kernel.model.PortletCategory;
import com.liferay.portal.kernel.model.PortletItem;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.PortletConfigFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.PortletItemLocalServiceUtil;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.util.comparator.PortletCategoryComparator;
import com.liferay.portal.kernel.util.comparator.PortletTitleComparator;
import com.liferay.portal.util.PortletCategoryUtil;
import com.liferay.portal.util.WebAppPool;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.PortletConfig;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pablo Molina
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET,
		"mvc.command.name=/layout_content_page_editor/get_widgets"
	},
	service = MVCResourceCommand.class
)
public class GetWidgetsMVCResourceCommand extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		_httpServletRequest = _portal.getOriginalServletRequest(
			_portal.getHttpServletRequest(resourceRequest));

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		JSONPortletResponseUtil.writeJSON(
			resourceRequest, resourceResponse, _getWidgets());
	}

	private String _getPortletCategoryTitle(PortletCategory portletCategory) {
		for (String portletId :
				PortletCategoryUtil.getFirstChildPortletIds(portletCategory)) {

			Portlet portlet = PortletLocalServiceUtil.getPortletById(
				_themeDisplay.getCompanyId(), portletId);

			if (portlet == null) {
				continue;
			}

			PortletApp portletApp = portlet.getPortletApp();

			if (!portletApp.isWARFile()) {
				continue;
			}

			PortletConfig portletConfig = PortletConfigFactoryUtil.create(
				portlet, _httpServletRequest.getServletContext());

			ResourceBundle portletResourceBundle =
				portletConfig.getResourceBundle(_themeDisplay.getLocale());

			String title = ResourceBundleUtil.getString(
				portletResourceBundle, portletCategory.getName());

			if (Validator.isNotNull(title)) {
				return title;
			}
		}

		return LanguageUtil.get(_httpServletRequest, portletCategory.getName());
	}

	private List<Map<String, Object>> _getPortletItems(Portlet portlet) {
		List<PortletItem> portletItems =
			PortletItemLocalServiceUtil.getPortletItems(
				_themeDisplay.getScopeGroupId(), portlet.getPortletId(),
				PortletPreferences.class.getName());

		if (ListUtil.isEmpty(portletItems)) {
			return Collections.emptyList();
		}

		Stream<PortletItem> stream = portletItems.stream();

		return stream.map(
			portletItem -> HashMapBuilder.<String, Object>put(
				"instanceable", portlet.isInstanceable()
			).put(
				"portletId", portlet.getPortletId()
			).put(
				"portletItemId", portletItem.getPortletItemId()
			).put(
				"title", HtmlUtil.escape(portletItem.getName())
			).put(
				"used", _isUsed(portlet)
			).build()
		).collect(
			Collectors.toList()
		);
	}

	private List<Map<String, Object>> _getPortlets(
		PortletCategory portletCategory) {

		HttpSession httpSession = _httpServletRequest.getSession();

		ServletContext servletContext = httpSession.getServletContext();

		Set<String> portletIds = portletCategory.getPortletIds();

		Stream<String> stream = portletIds.stream();

		return stream.map(
			portletId -> PortletLocalServiceUtil.getPortletById(
				_themeDisplay.getCompanyId(), portletId)
		).filter(
			portlet -> {
				if (portlet == null) {
					return false;
				}

				if (ArrayUtil.contains(
						_UNSUPPORTED_PORTLETS_NAMES,
						portlet.getPortletName())) {

					return false;
				}

				try {
					return PortletPermissionUtil.contains(
						_themeDisplay.getPermissionChecker(),
						_themeDisplay.getLayout(), portlet,
						ActionKeys.ADD_TO_PAGE);
				}
				catch (PortalException portalException) {
					_log.error(
						"Unable to check portlet permissions for " +
							portlet.getPortletId(),
						portalException);

					return false;
				}
			}
		).sorted(
			new PortletTitleComparator(
				servletContext, _themeDisplay.getLocale())
		).map(
			portlet -> HashMapBuilder.<String, Object>put(
				"instanceable", portlet.isInstanceable()
			).put(
				"portletId", portlet.getPortletId()
			).put(
				"portletItems", _getPortletItems(portlet)
			).put(
				"title",
				PortalUtil.getPortletTitle(
					portlet, servletContext, _themeDisplay.getLocale())
			).put(
				"used", _isUsed(portlet)
			).build()
		).collect(
			Collectors.toList()
		);
	}

	private List<Map<String, Object>> _getWidgetCategories(
		PortletCategory portletCategory) {

		Collection<PortletCategory> portletCategories =
			portletCategory.getCategories();

		Stream<PortletCategory> stream = portletCategories.stream();

		return stream.sorted(
			new PortletCategoryComparator(_themeDisplay.getLocale())
		).filter(
			currentPortletCategory -> !currentPortletCategory.isHidden()
		).map(
			currentPortletCategory -> HashMapBuilder.<String, Object>put(
				"categories", _getWidgetCategories(currentPortletCategory)
			).put(
				"path",
				StringUtil.replace(
					currentPortletCategory.getPath(), new String[] {"/", "."},
					new String[] {"-", "-"})
			).put(
				"portlets", _getPortlets(currentPortletCategory)
			).put(
				"title", _getPortletCategoryTitle(currentPortletCategory)
			).build()
		).collect(
			Collectors.toList()
		);
	}

	private List<Map<String, Object>> _getWidgets() throws Exception {
		PortletCategory portletCategory = (PortletCategory)WebAppPool.get(
			_themeDisplay.getCompanyId(), WebKeys.PORTLET_CATEGORY);

		portletCategory = PortletCategoryUtil.getRelevantPortletCategory(
			_themeDisplay.getPermissionChecker(), _themeDisplay.getCompanyId(),
			_themeDisplay.getLayout(), portletCategory,
			_themeDisplay.getLayoutTypePortlet());

		return _getWidgetCategories(portletCategory);
	}

	private boolean _isUsed(Portlet portlet) {
		if (portlet.isInstanceable()) {
			return false;
		}

		long count =
			PortletPreferencesLocalServiceUtil.getPortletPreferencesCount(
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, _themeDisplay.getPlid(),
				portlet.getPortletId());

		if (count > 0) {
			return true;
		}

		return false;
	}

	private static final String[] _UNSUPPORTED_PORTLETS_NAMES = {
		"com_liferay_nested_portlets_web_portlet_NestedPortletsPortlet"
	};

	private static final Log _log = LogFactoryUtil.getLog(
		GetWidgetsMVCResourceCommand.class);

	private HttpServletRequest _httpServletRequest;

	@Reference
	private Portal _portal;

	private ThemeDisplay _themeDisplay;

}