/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.friendly.url.verifier;

/**
 * @author David Truong
 */
public class LayoutFriendlyURLPublicMappingConflict {

	public LayoutFriendlyURLPublicMappingConflict(
		long layoutPlid, String layoutName, String pageURL, Type type,
		Long conflictingGroupId, String conflictingGroupName) {

		_layoutPlid = layoutPlid;
		_layoutName = layoutName;
		_pageURL = pageURL;
		_type = type;
		_conflictingGroupId = conflictingGroupId;
		_conflictingGroupName = conflictingGroupName;
	}

	public Long getConflictingGroupId() {
		return _conflictingGroupId;
	}

	public String getConflictingGroupName() {
		return _conflictingGroupName;
	}

	public String getLayoutName() {
		return _layoutName;
	}

	public long getLayoutPlid() {
		return _layoutPlid;
	}

	public String getPageURL() {
		return _pageURL;
	}

	public Type getType() {
		return _type;
	}

	public enum Type {

		CROSS_SITE, RESERVED_KEYWORD, SELF

	}

	private final Long _conflictingGroupId;
	private final String _conflictingGroupName;
	private final String _layoutName;
	private final long _layoutPlid;
	private final String _pageURL;
	private final Type _type;

}