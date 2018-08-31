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

package com.liferay.sharing.web.internal.display;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.sharing.constants.SharingEntryActionKey;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Sergio González
 */
public class SharingEntryPermissionDisplay {

	public static List<SharingEntryPermissionDisplay>
		getSharingEntryPermissionDisplays(
			List<SharingEntryActionKey> sharingEntryActionKeys,
			ResourceBundle resourceBundle) {

		List<SharingEntryPermissionDisplay> sharingEntryPermissionDisplays =
			new ArrayList<>();

		if (sharingEntryActionKeys.contains(
				SharingEntryActionKey.ADD_DISCUSSION) &&
			sharingEntryActionKeys.contains(SharingEntryActionKey.UPDATE) &&
			sharingEntryActionKeys.contains(SharingEntryActionKey.VIEW)) {

			sharingEntryPermissionDisplays.add(
				new SharingEntryPermissionDisplay(
					SharingEntryPermissionDisplayActionKey.UPDATE, true,
					resourceBundle));
		}
		else {
			sharingEntryPermissionDisplays.add(
				new SharingEntryPermissionDisplay(
					SharingEntryPermissionDisplayActionKey.UPDATE, false,
					resourceBundle));
		}

		if (sharingEntryActionKeys.contains(
				SharingEntryActionKey.ADD_DISCUSSION) &&
			sharingEntryActionKeys.contains(SharingEntryActionKey.VIEW)) {

			sharingEntryPermissionDisplays.add(
				new SharingEntryPermissionDisplay(
					SharingEntryPermissionDisplayActionKey.COMMENTS, true,
					resourceBundle));
		}
		else {
			sharingEntryPermissionDisplays.add(
				new SharingEntryPermissionDisplay(
					SharingEntryPermissionDisplayActionKey.COMMENTS, false,
					resourceBundle));
		}

		if (sharingEntryActionKeys.contains(SharingEntryActionKey.VIEW)) {
			sharingEntryPermissionDisplays.add(
				new SharingEntryPermissionDisplay(
					SharingEntryPermissionDisplayActionKey.VIEW, true,
					resourceBundle));
		}
		else {
			sharingEntryPermissionDisplays.add(
				new SharingEntryPermissionDisplay(
					SharingEntryPermissionDisplayActionKey.VIEW, false,
					resourceBundle));
		}

		return sharingEntryPermissionDisplays;
	}

	public SharingEntryPermissionDisplay(
		SharingEntryPermissionDisplayActionKey
			sharingEntryPermissionDisplayActionKey,
		boolean enabled, ResourceBundle resourceBundle) {

		_enabled = enabled;

		_description = LanguageUtil.get(
			resourceBundle,
			sharingEntryPermissionDisplayActionKey.getDescriptionKey());
		_sharingEntryPermissionDisplayActionKeyActionId =
			sharingEntryPermissionDisplayActionKey.getActionId();
		_title = LanguageUtil.get(
			resourceBundle,
			sharingEntryPermissionDisplayActionKey.getTitleKey());
	}

	public String getDescription() {
		return _description;
	}

	public String getSharingEntryPermissionDisplayActionKeyActionId() {
		return _sharingEntryPermissionDisplayActionKeyActionId;
	}

	public String getTitle() {
		return _title;
	}

	public boolean isEnabled() {
		return _enabled;
	}

	private final String _description;
	private final boolean _enabled;
	private final String _sharingEntryPermissionDisplayActionKeyActionId;
	private final String _title;

}