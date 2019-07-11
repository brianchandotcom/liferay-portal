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

import com.liferay.sharing.security.permission.SharingEntryAction;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author Sergio González
 */
public enum SharingEntryPermissionDisplayAction {

	COMMENTS(
		"COMMENTS", "comment", "collaborators-can-comment-on-the-document",
		"comment", SharingEntryAction.ADD_DISCUSSION, SharingEntryAction.VIEW),
	UPDATE(
		"UPDATE", "update",
		"collaborators-can-view-comment-update-the-document", "update",
		SharingEntryAction.ADD_DISCUSSION, SharingEntryAction.UPDATE,
		SharingEntryAction.VIEW),
	VIEW(
		"VIEW", "view", "collaborators-can-only-view-the-document", "view",
		SharingEntryAction.VIEW);

	public static SharingEntryPermissionDisplayAction parseFromActionId(
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

		throw new IllegalArgumentException("Invalid action ID " + actionId);
	}

	public String getActionId() {
		return _actionId;
	}

	public String getDescriptionKey() {
		return _descriptionKey;
	}

	public List<SharingEntryAction> getSharingEntryActions() {
		return _sharingEntryActions;
	}

	public String getTitleKey() {
		return _titleKey;
	}

	public String getVerbKey() {
		return _verbKey;
	}

	private SharingEntryPermissionDisplayAction(
		String actionId, String titleKey, String descriptionKey, String verbKey,
		SharingEntryAction... sharingEntryActions) {

		_actionId = actionId;
		_titleKey = titleKey;
		_descriptionKey = descriptionKey;
		_verbKey = verbKey;

		_sharingEntryActions = Arrays.asList(sharingEntryActions);
	}

	private final String _actionId;
	private final String _descriptionKey;
	private final List<SharingEntryAction> _sharingEntryActions;
	private final String _titleKey;
	private final String _verbKey;

}