/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.item;

import com.liferay.info.item.provider.filter.InfoItemServiceFilter;
import com.liferay.petra.string.StringBundler;

import java.util.Objects;

/**
 * @author Jorge Ferrer
 */
public class GroupUrlTitleInfoItemIdentifier extends BaseInfoItemIdentifier {

	public static final InfoItemServiceFilter INFO_ITEM_SERVICE_FILTER =
		getInfoItemServiceFilter(GroupUrlTitleInfoItemIdentifier.class);

	public GroupUrlTitleInfoItemIdentifier(long groupId, String urlTitle) {
		_groupId = groupId;
		_urlTitle = urlTitle;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof GroupUrlTitleInfoItemIdentifier)) {
			return false;
		}

		GroupUrlTitleInfoItemIdentifier groupUrlTitleInfoItemIdentifier =
			(GroupUrlTitleInfoItemIdentifier)object;

		if (Objects.equals(
				_groupId, groupUrlTitleInfoItemIdentifier._groupId) &&
			Objects.equals(
				_urlTitle, groupUrlTitleInfoItemIdentifier._urlTitle)) {

			return true;
		}

		return false;
	}

	public long getGroupId() {
		return _groupId;
	}

	@Override
	public InfoItemServiceFilter getInfoItemServiceFilter() {
		return INFO_ITEM_SERVICE_FILTER;
	}

	public String getUrlTitle() {
		return _urlTitle;
	}

	@Override
	public int hashCode() {
		return Objects.hash(_groupId, _urlTitle);
	}

	@Override
	public String toString() {
		return StringBundler.concat(
			"{className=", GroupKeyInfoItemIdentifier.class.getName(),
			", _groupId=", _groupId, ", _urlTitle=", _urlTitle, "}");
	}

	private final long _groupId;
	private final String _urlTitle;

}