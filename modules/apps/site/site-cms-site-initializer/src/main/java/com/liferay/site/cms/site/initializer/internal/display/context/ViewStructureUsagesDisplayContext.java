/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.display.context;

import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import jakarta.portlet.ActionRequest;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Marco Galluzzi
 */
public class ViewStructureUsagesDisplayContext {

	public ViewStructureUsagesDisplayContext(
		HttpServletRequest httpServletRequest, Language language,
		ObjectDefinition objectDefinition) {

		_httpServletRequest = httpServletRequest;
		_language = language;
		_objectDefinition = objectDefinition;

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public String getAPIURL() {
		return StringBundler.concat(
			"/o/search/v1.0/search?emptySearch=true&",
			"filter=(folderId ne 0 and objectDefinitionId eq ",
			ParamUtil.getLong(_httpServletRequest, "objectDefinitionId"),
			" and status in (", _STATUSES, "))&nestedFields=embedded");
	}

	public Map<String, Object> getBreadcrumbProps() throws PortalException {
		return HashMapBuilder.<String, Object>put(
			"breadcrumbItems",
			JSONUtil.putAll(
				JSONUtil.put(
					"active", false
				).put(
					"href",
					() -> PortalUtil.getLayoutFullURL(
						LayoutLocalServiceUtil.getLayoutByFriendlyURL(
							_themeDisplay.getScopeGroupId(), false,
							"/structures"),
						_themeDisplay)
				).put(
					"label",
					LanguageUtil.get(_themeDisplay.getLocale(), "structures")
				)
			).put(
				JSONUtil.put(
					"active", true
				).put(
					"label",
					LanguageUtil.format(
						_themeDisplay.getLocale(), "x-usages",
						_objectDefinition.getLabel(
							_themeDisplay.getLanguageId()))
				)
			)
		).put(
			"hideSpace", true
		).build();
	}

	public List<DropdownItem> getBulkActionDropdownItems() {
		return Collections.emptyList();
	}

	public List<FDSActionDropdownItem> getFDSActionDropdownItems() {
		return ListUtil.fromArray(
			new FDSActionDropdownItem(
				StringBundler.concat(
					_themeDisplay.getPortalURL(), _themeDisplay.getPathMain(),
					GroupConstants.CMS_FRIENDLY_URL,
					"/edit_content_item?objectEntryId={embedded.id}&",
					"redirect=", _themeDisplay.getURLCurrent()),
				"pencil", "edit", LanguageUtil.get(_httpServletRequest, "edit"),
				"get", "update", null),
			new FDSActionDropdownItem(
				PortletURLBuilder.create(
					PortalUtil.getControlPanelPortletURL(
						_httpServletRequest,
						"com_liferay_portlet_configuration_web_portlet_" +
							"PortletConfigurationPortlet",
						ActionRequest.RENDER_PHASE)
				).setMVCPath(
					"/edit_permissions.jsp"
				).setRedirect(
					_themeDisplay.getURLCurrent()
				).setParameter(
					"modelResource", "{entryClassName}"
				).setParameter(
					"modelResourceDescription", "{embedded.name}"
				).setParameter(
					"resourceGroupId", "{embedded.scopeId}"
				).setParameter(
					"resourcePrimKey", "{embedded.id}"
				).setWindowState(
					LiferayWindowState.POP_UP
				).buildString(),
				"password-policies", "permissions",
				_language.get(_httpServletRequest, "permissions"), "get", null,
				"modal-permissions"),
			new FDSActionDropdownItem(
				_language.get(
					_httpServletRequest,
					"are-you-sure-you-want-to-delete-this-entry"),
				null, "trash", "delete",
				_language.get(_httpServletRequest, "delete"), "delete",
				"delete", "headless"));
	}

	private static final String _STATUSES = StringUtil.merge(
		new int[] {
			WorkflowConstants.STATUS_APPROVED, WorkflowConstants.STATUS_DRAFT,
			WorkflowConstants.STATUS_EXPIRED, WorkflowConstants.STATUS_PENDING,
			WorkflowConstants.STATUS_SCHEDULED
		});

	private final HttpServletRequest _httpServletRequest;
	private final Language _language;
	private final ObjectDefinition _objectDefinition;
	private final ThemeDisplay _themeDisplay;

}