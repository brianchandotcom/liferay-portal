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

package com.liferay.segments.web.internal.util;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.web.internal.display.context.SegmentsDisplayContext;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Diego Hu
 */
public class SegmentsEntryActionDropdownItemsProvider {

	public SegmentsEntryActionDropdownItemsProvider(
		HttpServletRequest httpServletRequest,
		SegmentsDisplayContext segmentsDisplayContext,
		SegmentsEntry segmentsEntry) {

		_httpServletRequest = httpServletRequest;
		_segmentsDisplayContext = segmentsDisplayContext;
		_segmentsEntry = segmentsEntry;
	}

	public List<DropdownItem> getActionDropdownItems() {
		return DropdownItemListBuilder.addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					DropdownItemListBuilder.add(
						() -> _segmentsDisplayContext.isShowUpdateAction(
							_segmentsEntry),
						dropdownItem -> {
							dropdownItem.setHref(
								_segmentsDisplayContext.getEditURL(
									_segmentsEntry));
							dropdownItem.setLabel(
								LanguageUtil.get(_httpServletRequest, "edit"));
						}
					).add(
						() -> _segmentsDisplayContext.isShowViewAction(
							_segmentsEntry),
						dropdownItem -> {
							dropdownItem.putData(
								"action", "viewMembersSegmentsEntry");
							dropdownItem.putData(
								"viewMembersSegmentsEntryURL",
								_segmentsDisplayContext.getPreviewMembersURL(
									_segmentsEntry));
							dropdownItem.setLabel(
								LanguageUtil.get(
									_httpServletRequest, "view-members"));
						}
					).add(
						() ->
							_segmentsDisplayContext.isShowAssignUserRolesAction(
								_segmentsEntry),
						dropdownItem -> {
							Map<String, Object> assignUserRolesDataMap =
								_segmentsDisplayContext.
									getAssignUserRolesDataMap(_segmentsEntry);

							dropdownItem.putData(
								"action", "assignSiteRolesSegmentsEntry");
							dropdownItem.putData(
								"itemSelectorURL",
								String.valueOf(
									assignUserRolesDataMap.get(
										"itemSelectorURL")));
							dropdownItem.putData(
								"segmentsEntryId",
								String.valueOf(
									assignUserRolesDataMap.get(
										"segmentsEntryId")));
							dropdownItem.setDisabled(
								!_segmentsDisplayContext.
									isRoleSegmentationEnabled(
										_segmentsEntry.getCompanyId()));
							dropdownItem.setLabel(
								LanguageUtil.get(
									_httpServletRequest, "assign-site-roles"));
						}
					).add(
						() -> _segmentsDisplayContext.isShowPermissionAction(
							_segmentsEntry),
						dropdownItem -> {
							dropdownItem.putData(
								"action", "permissionsSegmentsEntry");
							dropdownItem.putData(
								"permissionsSegmentsEntryURL",
								_segmentsDisplayContext.getPermissionURL(
									_segmentsEntry));
							dropdownItem.setLabel(
								LanguageUtil.get(
									_httpServletRequest, "permissions"));
						}
					).add(
						() -> _segmentsDisplayContext.isShowDeleteAction(
							_segmentsEntry),
						dropdownItem -> {
							dropdownItem.putData(
								"action", "deleteSegmentEntry");
							dropdownItem.putData(
								"deleteSegmentEntryURL",
								_segmentsDisplayContext.getDeleteURL(
									_segmentsEntry));
							dropdownItem.setLabel(
								LanguageUtil.get(
									_httpServletRequest, "delete"));
						}
					).build());
				dropdownGroupItem.setSeparator(true);
			}
		).build();
	}

	private final HttpServletRequest _httpServletRequest;
	private final SegmentsDisplayContext _segmentsDisplayContext;
	private final SegmentsEntry _segmentsEntry;

}