/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.display.context;

import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.cms.site.initializer.internal.configuration.CMSSiteInitializerConfiguration;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.portlet.ActionRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Sam Ziemer
 */
public class ContentsSectionDisplayContext extends BaseSectionDisplayContext {

	public ContentsSectionDisplayContext(
		CMSSiteInitializerConfiguration cmsSiteInitializerConfiguration,
		HttpServletRequest httpServletRequest, Language language,
		ObjectDefinitionService objectDefinitionService) {

		super(cmsSiteInitializerConfiguration, httpServletRequest);

		_language = language;
		_objectDefinitionService = objectDefinitionService;

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	@Override
	public List<DropdownItem> getBulkActionDropdownItems() {
		return ListUtil.fromArray(
			new FDSActionDropdownItem(
				"#", "document", "sampleBulkAction",
				_language.get(httpServletRequest, "label"), null, null, null));
	}

	@Override
	public CreationMenu getCreationMenu() {
		String url = _getAddStructuredContentItemURL();

		return new CreationMenu() {
			{
				addPrimaryDropdownItem(
					dropdownItem -> {
						dropdownItem.putData("action", "createFolder");
						dropdownItem.setIcon("folder");
						dropdownItem.setLabel(
							_language.get(httpServletRequest, "folder"));
					});

				for (ObjectDefinition objectDefinition :
						_objectDefinitionService.getObjectDefinitions(
							_themeDisplay.getCompanyId(), QueryUtil.ALL_POS,
							QueryUtil.ALL_POS)) {

					if (!objectDefinition.isApproved() ||
						objectDefinition.isSystem() ||
						!Objects.equals(
							objectDefinition.getScope(),
							ObjectDefinitionConstants.SCOPE_SITE) ||
						!objectDefinition.isEnableObjectEntryDraft()) {

						continue;
					}

					addPrimaryDropdownItem(
						dropdownItem -> {
							dropdownItem.setHref(
								url + objectDefinition.getObjectDefinitionId());
							dropdownItem.setIcon("forms");
							dropdownItem.setLabel(
								objectDefinition.getLabel(
									_themeDisplay.getLocale()));
						});
				}
			}
		};
	}

	@Override
	public Map<String, Object> getEmptyState() {
		return HashMapBuilder.<String, Object>put(
			"description",
			_language.get(
				httpServletRequest,
				"click-new-to-create-your-first-piece-of-content")
		).put(
			"image", "/states/cms_empty_state_content.svg"
		).put(
			"title", _language.get(httpServletRequest, "no-content-yet")
		).build();
	}

	public List<FDSActionDropdownItem> getFDSActionDropdownItems() {
		return ListUtil.fromArray(
			new FDSActionDropdownItem(
				PortletURLBuilder.create(
					PortalUtil.getControlPanelPortletURL(
						httpServletRequest,
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
					"resourcePrimKey", "{embedded.id}"
				).setWindowState(
					LiferayWindowState.POP_UP
				).buildString(),
				"password-policies", "permissions",
				_language.get(httpServletRequest, "permissions"), "get", null,
				"modal-permissions"));
	}

	@Override
	public String[] getObjectDefinitionFolderExternalReferenceCodes() {
		return cmsSiteInitializerConfiguration.
			contentsObjectDefinitionFolderExternalReferenceCodes();
	}

	private String _getAddStructuredContentItemURL() {
		StringBundler sb = new StringBundler(5);

		sb.append(_themeDisplay.getPortalURL());
		sb.append(_themeDisplay.getPathMain());
		sb.append("/cms/add_structured_content_item?groupId=");
		sb.append(_themeDisplay.getScopeGroupId());
		sb.append("&objectDefinitionId=");

		return sb.toString();
	}

	private final Language _language;
	private final ObjectDefinitionService _objectDefinitionService;
	private final ThemeDisplay _themeDisplay;

}