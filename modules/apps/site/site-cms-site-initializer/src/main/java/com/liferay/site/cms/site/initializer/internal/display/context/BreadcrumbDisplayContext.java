/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.display.context;

import com.liferay.depot.constants.DepotRolesConstants;
import com.liferay.depot.model.DepotEntry;
import com.liferay.object.constants.ObjectEntryFolderConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntryFolder;
import com.liferay.object.service.ObjectDefinitionLocalServiceUtil;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.cms.site.initializer.internal.constants.CMSSpaceConstants;
import com.liferay.site.cms.site.initializer.internal.util.ActionUtil;
import com.liferay.taglib.security.PermissionsURLTag;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author Marco Galluzzi
 */
public class BreadcrumbDisplayContext {

	public BreadcrumbDisplayContext(
		long groupId, GroupLocalService groupLocalService,
		HttpServletRequest httpServletRequest, String size) {

		_groupId = groupId;
		_groupLocalService = groupLocalService;
		_httpServletRequest = httpServletRequest;
		_size = GetterUtil.get(size, CMSSpaceConstants.SPACE_STICKER_LG);

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public String getAPIURL() {
		return "/o/headless-asset-library/v1.0/asset-libraries?filter=type " +
			"eq 'Space'&nestedFields=numberOfConnectedSites" +
				",numberOfUserAccounts,numberOfUserGroups";
	}

	public Map<String, Object> getProps() throws Exception {
		Group group = _getGroup();

		return HashMapBuilder.<String, Object>put(
			"actionItems",
			JSONUtil.putAll(
				JSONUtil.put(
					"href",
					ActionUtil.getSpaceSettingsURL(
						group.getClassPK(), _themeDisplay.getURLCurrent(),
						_themeDisplay)
				).put(
					"label",
					LanguageUtil.get(_httpServletRequest, "space-settings")
				).put(
					"symbolLeft", "cog"
				),
				JSONUtil.put(
					"href",
					PermissionsURLTag.doTag(
						StringPool.BLANK, DepotEntry.class.getName(),
						group.getName(), group.getGroupId(),
						String.valueOf(group.getClassPK()),
						LiferayWindowState.POP_UP.toString(), null,
						_httpServletRequest)
				).put(
					"label",
					LanguageUtil.get(_httpServletRequest, "permissions")
				).put(
					"symbolLeft", "password-policies"
				).put(
					"target", "modal"
				),
				JSONUtil.put(
					"defaultPermissionAdditionalProps",
					_getDefaultPermissionAdditionalProps()
				).put(
					"href", StringPool.BLANK
				).put(
					"label",
					LanguageUtil.get(_httpServletRequest, "default-permissions")
				).put(
					"symbolLeft", "password-policies"
				).put(
					"target", "defaultPermissionsModal"
				),
				JSONUtil.put(
					"confirmationMessage",
					LanguageUtil.get(
						_httpServletRequest, "delete-space-confirmation-body")
				).put(
					"confirmationTitle",
					LanguageUtil.format(
						_httpServletRequest, "delete-space-confirmation-title",
						group.getDescriptiveName())
				).put(
					"href",
					"/o/headless-asset-library/v1.0/asset-libraries" +
						"/by-external-reference-code/" +
							group.getExternalReferenceCode()
				).put(
					"label", LanguageUtil.get(_httpServletRequest, "delete")
				).put(
					"redirect",
					StringBundler.concat(
						_themeDisplay.getPathFriendlyURLPublic(),
						GroupConstants.CMS_FRIENDLY_URL, "/all-spaces")
				).put(
					"successMessage",
					LanguageUtil.format(
						_httpServletRequest, "x-was-successfully-deleted",
						group.getDescriptiveName())
				).put(
					"symbolLeft", "trash"
				).put(
					"target", "asyncDelete"
				))
		).put(
			"breadcrumbItems",
			JSONUtil.put(
				JSONUtil.put(
					"active", false
				).put(
					"href", StringPool.BLANK
				).put(
					"label", group.getDescriptiveName(_themeDisplay.getLocale())
				))
		).put(
			"displayType",
			() -> {
				UnicodeProperties unicodeProperties =
					group.getTypeSettingsProperties();

				return GetterUtil.get(
					unicodeProperties.get("logoColor"), "outline-0");
			}
		).put(
			"size", _size
		).build();
	}

	private Map<String, Object> _getDefaultPermissionAdditionalProps()
		throws Exception {

		return HashMapBuilder.<String, Object>put(
			"actions",
			() -> HashMapBuilder.put(
				ObjectEntryFolderConstants.EXTERNAL_REFERENCE_CODE_CONTENTS,
				() -> {
					ObjectDefinition objectDefinition =
						ObjectDefinitionLocalServiceUtil.
							getObjectDefinitionByExternalReferenceCode(
								"L_BASIC_WEB_CONTENT",
								_themeDisplay.getCompanyId());

					List<String> guestUnsupportedActions =
						ResourceActionsUtil.getResourceGuestUnsupportedActions(
							null, objectDefinition.getClassName());

					return TransformUtil.transformToArray(
						ResourceActionsUtil.getResourceActions(
							objectDefinition.getClassName()),
						resourceAction -> HashMapBuilder.<String, Object>put(
							"guestUnsupported",
							guestUnsupportedActions.contains(resourceAction)
						).put(
							"key", resourceAction
						).put(
							"label",
							ResourceActionsUtil.getAction(
								_httpServletRequest, resourceAction)
						).build(),
						Map.class);
				}
			).put(
				ObjectEntryFolderConstants.EXTERNAL_REFERENCE_CODE_FILES,
				() -> {
					ObjectDefinition objectDefinition =
						ObjectDefinitionLocalServiceUtil.
							getObjectDefinitionByExternalReferenceCode(
								"L_BASIC_DOCUMENT",
								_themeDisplay.getCompanyId());

					List<String> guestUnsupportedActions =
						ResourceActionsUtil.getResourceGuestUnsupportedActions(
							null, objectDefinition.getClassName());

					return TransformUtil.transformToArray(
						ResourceActionsUtil.getResourceActions(
							objectDefinition.getClassName()),
						resourceAction -> HashMapBuilder.<String, Object>put(
							"guestUnsupported",
							guestUnsupportedActions.contains(resourceAction)
						).put(
							"key", resourceAction
						).put(
							"label",
							ResourceActionsUtil.getAction(
								_httpServletRequest, resourceAction)
						).build(),
						Map.class);
				}
			).put(
				"OBJECT_ENTRY_FOLDERS",
				() -> {
					List<String> guestUnsupportedActions =
						ResourceActionsUtil.getResourceGuestUnsupportedActions(
							null, ObjectEntryFolder.class.getName());

					return TransformUtil.transformToArray(
						ResourceActionsUtil.getResourceActions(
							ObjectEntryFolder.class.getName()),
						resourceAction -> HashMapBuilder.<String, Object>put(
							"guestUnsupported",
							guestUnsupportedActions.contains(resourceAction)
						).put(
							"key", resourceAction
						).put(
							"label",
							ResourceActionsUtil.getAction(
								_httpServletRequest, resourceAction)
						).build(),
						Map.class);
				}
			).build()
		).put(
			"apiURL", getAPIURL()
		).put(
			"classExternalReferenceCode",
			() -> {
				Group group = _getGroup();

				return group.getExternalReferenceCode();
			}
		).put(
			"roles",
			() -> TransformUtil.transformToArray(
				RoleLocalServiceUtil.getGroupRolesAndTeamRoles(
					_themeDisplay.getCompanyId(), null,
					Arrays.asList(
						RoleConstants.ADMINISTRATOR,
						DepotRolesConstants.ASSET_LIBRARY_OWNER),
					null, null,
					new int[] {
						RoleConstants.TYPE_REGULAR, RoleConstants.TYPE_DEPOT
					},
					0, 0, QueryUtil.ALL_POS, QueryUtil.ALL_POS),
				role -> HashMapBuilder.put(
					"key", role.getName()
				).put(
					"name", role.getTitle(_themeDisplay.getLocale())
				).put(
					"type", String.valueOf(role.getType())
				).build(),
				Map.class)
		).build();
	}

	private Group _getGroup() throws Exception {
		return _groupLocalService.getGroup(_groupId);
	}

	private final long _groupId;
	private final GroupLocalService _groupLocalService;
	private final HttpServletRequest _httpServletRequest;
	private final String _size;
	private final ThemeDisplay _themeDisplay;

}