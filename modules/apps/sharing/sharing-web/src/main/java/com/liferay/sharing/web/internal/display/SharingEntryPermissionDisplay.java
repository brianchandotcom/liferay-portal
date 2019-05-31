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
import com.liferay.sharing.security.permission.SharingEntryAction;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Sergio González
 */
public class SharingEntryPermissionDisplay {

	public static List<SharingEntryPermissionDisplay>
		getSharingEntryPermissionDisplays(
			List<SharingEntryAction> sharingEntryActions,
			ResourceBundle resourceBundle) {

		List<SharingEntryPermissionDisplay> sharingEntryPermissionDisplays =
			new ArrayList<>();

		if (sharingEntryActions.contains(SharingEntryAction.ADD_DISCUSSION) &&
			sharingEntryActions.contains(SharingEntryAction.UPDATE) &&
			sharingEntryActions.contains(SharingEntryAction.VIEW)) {

			sharingEntryPermissionDisplays.add(
				new SharingEntryPermissionDisplay(
					SharingEntryPermissionDisplayAction.UPDATE, true,
					resourceBundle));
		}
		else {
			sharingEntryPermissionDisplays.add(
				new SharingEntryPermissionDisplay(
					SharingEntryPermissionDisplayAction.UPDATE, false,
					resourceBundle));
		}

		if (sharingEntryActions.contains(SharingEntryAction.ADD_DISCUSSION) &&
			sharingEntryActions.contains(SharingEntryAction.VIEW)) {

			sharingEntryPermissionDisplays.add(
				new SharingEntryPermissionDisplay(
					SharingEntryPermissionDisplayAction.COMMENTS, true,
					resourceBundle));
		}
		else {
			sharingEntryPermissionDisplays.add(
				new SharingEntryPermissionDisplay(
					SharingEntryPermissionDisplayAction.COMMENTS, false,
					resourceBundle));
		}

		if (sharingEntryActions.contains(SharingEntryAction.VIEW)) {
			sharingEntryPermissionDisplays.add(
				new SharingEntryPermissionDisplay(
					SharingEntryPermissionDisplayAction.VIEW, true,
					resourceBundle));
		}
		else {
			sharingEntryPermissionDisplays.add(
				new SharingEntryPermissionDisplay(
					SharingEntryPermissionDisplayAction.VIEW, false,
					resourceBundle));
		}

		return sharingEntryPermissionDisplays;
	}

	public String getDescription() {
		return _description;
	}

	public String getPhrase() {
		return _phrase;
	}

	public String getSharingEntryPermissionDisplayActionId() {
		return _sharingEntryPermissionDisplayActionId;
	}

	public String getTitle() {
		return _title;
	}

	public boolean isEnabled() {
		return _enabled;
	}

	private SharingEntryPermissionDisplay(
		SharingEntryPermissionDisplayAction sharingEntryPermissionDisplayAction,
		boolean enabled, ResourceBundle resourceBundle) {

		_enabled = enabled;

		_description = LanguageUtil.get(
			resourceBundle,
			sharingEntryPermissionDisplayAction.getDescriptionKey());
		_phrase = LanguageUtil.format(
			resourceBundle, "can-x",
			sharingEntryPermissionDisplayAction.getVerbKey());
		_sharingEntryPermissionDisplayActionId =
			sharingEntryPermissionDisplayAction.getActionId();
		_title = LanguageUtil.get(
			resourceBundle, sharingEntryPermissionDisplayAction.getTitleKey());
	}

	private final String _description;
	private final boolean _enabled;
	private final String _phrase;
	private final String _sharingEntryPermissionDisplayActionId;
	private final String _title;

}