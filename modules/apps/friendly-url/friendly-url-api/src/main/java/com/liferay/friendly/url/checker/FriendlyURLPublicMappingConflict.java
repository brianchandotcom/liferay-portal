/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.friendly.url.checker;

/**
 * @author David Truong
 */
public class FriendlyURLPublicMappingConflict {

	public FriendlyURLPublicMappingConflict(
		String className, long classPK, Long conflictingGroupId,
		String conflictingGroupName, String friendlyURL, String title,
		Type type) {

		_className = className;
		_classPK = classPK;
		_conflictingGroupId = conflictingGroupId;
		_conflictingGroupName = conflictingGroupName;
		_friendlyURL = friendlyURL;
		_title = title;
		_type = type;
	}

	public String getClassName() {
		return _className;
	}

	public long getClassPK() {
		return _classPK;
	}

	public Long getConflictingGroupId() {
		return _conflictingGroupId;
	}

	public String getConflictingGroupName() {
		return _conflictingGroupName;
	}

	public String getFriendlyURL() {
		return _friendlyURL;
	}

	public String getTitle() {
		return _title;
	}

	public Type getType() {
		return _type;
	}

	public enum Type {

		CROSS_SITE, RESERVED_KEYWORD, SELF

	}

	private final String _className;
	private final long _classPK;
	private final Long _conflictingGroupId;
	private final String _conflictingGroupName;
	private final String _friendlyURL;
	private final String _title;
	private final Type _type;

}