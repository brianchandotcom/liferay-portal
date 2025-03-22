/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.display.context;

import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.info.constants.InfoDisplayWebKeys;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntryFolder;
import com.liferay.object.service.ObjectDefinitionService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marco Galluzzi
 */
public abstract class BaseSectionDisplayContext {

	public BaseSectionDisplayContext(
		HttpServletRequest httpServletRequest, Language language,
		ObjectDefinitionService objectDefinitionService) {

		this.httpServletRequest = httpServletRequest;
		this.language = language;

		_objectDefinitionService = objectDefinitionService;

		Object object = httpServletRequest.getAttribute(
			InfoDisplayWebKeys.INFO_ITEM);

		_objectEntryFolder =
			object instanceof ObjectEntryFolder ? (ObjectEntryFolder)object :
				null;

		themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public String getAPIURL() {
		String[] objectDefinitionFolderExternalReferenceCodes =
			getObjectDefinitionFolderExternalReferenceCodes();

		StringBundler sb = new StringBundler(9);

		sb.append("/o/search/v1.0/search?emptySearch=true&");
		sb.append("filter=objectDefinitionFolder in ('");
		sb.append(
			StringUtil.merge(
				objectDefinitionFolderExternalReferenceCodes, "','"));
		sb.append("')");

		String cmsSectionFilterString = getCMSSectionFilterString();

		if (Validator.isNotNull(cmsSectionFilterString)) {
			sb.append(" or ");
			sb.append(cmsSectionFilterString);
		}

		if (_objectEntryFolder != null) {
			sb.append(" and folderId eq");
			sb.append(_objectEntryFolder.getObjectEntryFolderId());
		}

		sb.append("&nestedFields=embedded");

		return sb.toString();
	}

	public List<DropdownItem> getBulkActionDropdownItems() {
		return ListUtil.fromArray(
			new FDSActionDropdownItem(
				"#", "document", "sampleBulkAction",
				LanguageUtil.get(httpServletRequest, "label"), null, null,
				null));
	}

	public abstract CreationMenu getCreationMenu();

	public abstract Map<String, Object> getEmptyState();

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
					themeDisplay.getURLCurrent()
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
				language.get(httpServletRequest, "permissions"), "get", null,
				"modal-permissions"),
			new FDSActionDropdownItem(
				language.get(
					httpServletRequest,
					"are-you-sure-you-want-to-delete-this-entry"),
				null, "trash", "delete",
				language.get(httpServletRequest, "delete"), "delete", "delete",
				"headless"));
	}

	protected void addStructureContentDropdownItems(CreationMenu creationMenu) {
		for (ObjectDefinition objectDefinition :
				_objectDefinitionService.getCMSObjectDefinitions(
					themeDisplay.getCompanyId(),
					getObjectDefinitionFolderExternalReferenceCodes())) {

			creationMenu.addPrimaryDropdownItem(
				dropdownItem -> {
					dropdownItem.putData("action", "createAsset");
					dropdownItem.putData(
						"redirect",
						StringBundler.concat(
							themeDisplay.getPortalURL(),
							themeDisplay.getPathMain(),
							"/cms/add_structured_content_item?",
							"objectDefinitionId=",
							objectDefinition.getObjectDefinitionId()));
					dropdownItem.putData(
						"title",
						objectDefinition.getLabel(themeDisplay.getLocale()));
					dropdownItem.setIcon("forms");
					dropdownItem.setLabel(
						objectDefinition.getLabel(themeDisplay.getLocale()));
				});
		}
	}

	protected abstract String getCMSSectionFilterString();

	protected abstract String[]
		getObjectDefinitionFolderExternalReferenceCodes();

	protected final HttpServletRequest httpServletRequest;
	protected final Language language;
	protected final ThemeDisplay themeDisplay;

	private final ObjectDefinitionService _objectDefinitionService;
	private final ObjectEntryFolder _objectEntryFolder;

}