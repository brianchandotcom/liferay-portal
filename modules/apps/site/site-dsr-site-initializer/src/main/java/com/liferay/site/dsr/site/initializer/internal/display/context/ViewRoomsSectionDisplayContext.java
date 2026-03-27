/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.dsr.site.initializer.internal.display.context;

import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.frontend.data.set.model.FDSActionDropdownItemBuilder;
import com.liferay.frontend.data.set.model.FDSActionDropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectEntryService;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.LayoutSetPrototype;
import com.liferay.portal.kernel.service.LayoutSetPrototypeLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.site.dsr.site.initializer.internal.constants.DSRConstants;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;

/**
 * @author Stefano Motta
 */
public class ViewRoomsSectionDisplayContext extends BaseSectionDisplayContext {

	public ViewRoomsSectionDisplayContext(
		Map<String, Object> configurationMap,
		HttpServletRequest httpServletRequest,
		ObjectDefinition objectDefinition,
		ObjectEntryService objectEntryService) {

		super(
			configurationMap, httpServletRequest, objectDefinition,
			objectEntryService);
	}

	public Map<String, Object> getAdditionalProps() {
		return HashMapBuilder.<String, Object>put(
			"createRedirectURL",
			() -> StringBundler.concat(
				themeDisplay.getPortalURL(), themeDisplay.getPathMain(),
				DSRConstants.DSR_FRIENDLY_URL,
				"/view_room?mode=edit&siteId={siteId}")
		).put(
			"siteTemplates",
			() -> TransformUtil.transform(
				LayoutSetPrototypeLocalServiceUtil.getLayoutSetPrototypes(
					objectDefinition.getCompanyId()),
				this::_getLayoutSetPrototypeJSONObject)
		).build();
	}

	@Override
	public String getAPIURL() {
		StringBundler sb = new StringBundler(5);

		sb.append("/o/search/v1.0/search?emptySearch=true&");
		sb.append("filter=objectDefinitionId eq ");
		sb.append(objectDefinition.getObjectDefinitionId());
		sb.append("&nestedFields=embedded,r_accountToDSRRooms_accountEntryId");

		if (isHomePage()) {
			sb.append("&pageSize=5&sort=dateModified:desc");
		}

		return sb.toString();
	}

	@Override
	public CreationMenu getCreationMenu() throws Exception {
		if (isHomePage() || !hasAddObjectEntryPortletResourcePermission()) {
			return null;
		}

		return CreationMenuBuilder.addPrimaryDropdownItem(
			dropdownItem -> {
				dropdownItem.putData("action", "createDigitalSalesRoom");
				dropdownItem.putData(
					"objectDefinitionId",
					String.valueOf(objectDefinition.getObjectDefinitionId()));
				dropdownItem.putData(
					"title",
					objectDefinition.getLabel(themeDisplay.getLocale()));
				dropdownItem.setIcon("forms");
				dropdownItem.setLabel(
					LanguageUtil.get(
						httpServletRequest, "new-digital-sales-room"));
			}
		).build();
	}

	@Override
	public List<FDSActionDropdownItem> getFDSActionDropdownItems() {
		return FDSActionDropdownItemList.of(
			FDSActionDropdownItemBuilder.setHref(
				() -> StringBundler.concat(
					themeDisplay.getPortalURL(), themeDisplay.getPathMain(),
					DSRConstants.DSR_FRIENDLY_URL, "/view_room?siteId=",
					"{embedded.siteId}")
			).setIcon(
				"view"
			).setLabel(
				LanguageUtil.get(httpServletRequest, "view")
			).setPermissionKey(
				"get"
			).build(
				"view"
			),
			FDSActionDropdownItemBuilder.setHref(
				() -> StringBundler.concat(
					themeDisplay.getPortalURL(), themeDisplay.getPathMain(),
					DSRConstants.DSR_FRIENDLY_URL,
					"/view_room?mode=edit&siteId={embedded.siteId}")
			).setIcon(
				"pencil"
			).setLabel(
				LanguageUtil.get(httpServletRequest, "edit")
			).setPermissionKey(
				"update"
			).build(
				"edit"
			),
			FDSActionDropdownItemBuilder.setHref(
				"#"
			).setIcon(
				"share"
			).setLabel(
				LanguageUtil.get(httpServletRequest, "share")
			).setPermissionKey(
				"update"
			).build(
				"share"
			),
			FDSActionDropdownItemBuilder.setHref(
				"#"
			).setIcon(
				"trash"
			).setLabel(
				LanguageUtil.get(httpServletRequest, "delete")
			).setMethod(
				"delete"
			).setPermissionKey(
				"delete"
			).build(
				"delete"
			));
	}

	public boolean isHomePage() {
		return GetterUtil.getBoolean(configurationMap.get("isHomePage"));
	}

	private JSONObject _getLayoutSetPrototypeJSONObject(
		LayoutSetPrototype layoutSetPrototype) {

		return JSONUtil.put(
			"description",
			() -> layoutSetPrototype.getDescription(themeDisplay.getLocale())
		).put(
			"friendlyURL",
			() -> {
				Group group = layoutSetPrototype.getGroup();

				return group.getDisplayURL(themeDisplay, true);
			}
		).put(
			"name", () -> layoutSetPrototype.getName(themeDisplay.getLocale())
		).put(
			"uuid", layoutSetPrototype.getUuid()
		);
	}

}