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

package com.liferay.sharing.web.internal.constants;

import com.liferay.sharing.constants.SharingEntryActionKey;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author Sergio González
 */
public enum SharingEntryPermissionDisplayActionKey {

	COMMENTS(
		"COMMENTS", "comments", "collaborators-can-comment-on-the-document",
		SharingEntryActionKey.ADD_DISCUSSION, SharingEntryActionKey.VIEW),
	UPDATE(
		"UPDATE", "edition",
		"collaborators-can-view-comment-update-the-document",
		SharingEntryActionKey.ADD_DISCUSSION, SharingEntryActionKey.UPDATE,
		SharingEntryActionKey.VIEW),
	VIEW(
		"VIEW", "view", "collaborators-can-only-view-the-document",
		SharingEntryActionKey.VIEW);

	public static SharingEntryPermissionDisplayActionKey parseFromActionId(
		String actionId) {

		if (Objects.equals(COMMENTS.getActionId(), actionId)) {
			return COMMENTS;
		}
		else if (Objects.equals(UPDATE.getActionId(), actionId)) {
			return UPDATE;
		}
		else if (Objects.equals(VIEW.getActionId(), actionId)) {
			return VIEW;
		}

		throw new IllegalArgumentException("Invalid action id " + actionId);
	}

	public String getActionId() {
		return _actionId;
	}

	public String getDescriptionKey() {
		return _descriptionKey;
	}

	public List<SharingEntryActionKey> getSharingEntryActionKeys() {
		return _sharingEntryActionKeys;
	}

	public String getTitleKey() {
		return _titleKey;
	}

	private SharingEntryPermissionDisplayActionKey(
		String actionId, String titleKey, String descriptionKey,
		SharingEntryActionKey... sharingEntryActionKeys) {

		_actionId = actionId;
		_titleKey = titleKey;
		_descriptionKey = descriptionKey;
		_sharingEntryActionKeys = Arrays.asList(sharingEntryActionKeys);
	}

	private final String _actionId;
	private final String _descriptionKey;
	private final List<SharingEntryActionKey> _sharingEntryActionKeys;
	private final String _titleKey;

}