/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
			String className, List<SharingEntryAction> sharingEntryActions,
			ResourceBundle resourceBundle) {

		List<SharingEntryPermissionDisplay> sharingEntryPermissionDisplays =
			new ArrayList<>();

		if (sharingEntryActions.contains(SharingEntryAction.ADD_DISCUSSION) &&
			sharingEntryActions.contains(SharingEntryAction.UPDATE) &&
			sharingEntryActions.contains(SharingEntryAction.VIEW)) {

			sharingEntryPermissionDisplays.add(
				new SharingEntryPermissionDisplay(
					className, SharingEntryPermissionDisplayAction.UPDATE, true,
					resourceBundle));
		}
		else {
			sharingEntryPermissionDisplays.add(
				new SharingEntryPermissionDisplay(
					className, SharingEntryPermissionDisplayAction.UPDATE,
					false, resourceBundle));
		}

		if (sharingEntryActions.contains(SharingEntryAction.ADD_DISCUSSION) &&
			sharingEntryActions.contains(SharingEntryAction.VIEW)) {

			sharingEntryPermissionDisplays.add(
				new SharingEntryPermissionDisplay(
					className, SharingEntryPermissionDisplayAction.COMMENTS,
					true, resourceBundle));
		}
		else {
			sharingEntryPermissionDisplays.add(
				new SharingEntryPermissionDisplay(
					className, SharingEntryPermissionDisplayAction.COMMENTS,
					false, resourceBundle));
		}

		if (sharingEntryActions.contains(SharingEntryAction.VIEW)) {
			sharingEntryPermissionDisplays.add(
				new SharingEntryPermissionDisplay(
					className, SharingEntryPermissionDisplayAction.VIEW, true,
					resourceBundle));
		}
		else {
			sharingEntryPermissionDisplays.add(
				new SharingEntryPermissionDisplay(
					className, SharingEntryPermissionDisplayAction.VIEW, false,
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
		String className,
		SharingEntryPermissionDisplayAction sharingEntryPermissionDisplayAction,
		boolean enabled, ResourceBundle resourceBundle) {

		_enabled = enabled;

		_description = sharingEntryPermissionDisplayAction.getDescription(
			className, resourceBundle);
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