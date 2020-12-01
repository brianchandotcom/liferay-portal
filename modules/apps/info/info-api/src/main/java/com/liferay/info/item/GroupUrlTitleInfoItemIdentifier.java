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
		StringBundler sb = new StringBundler(7);

		sb.append("{className=");
		sb.append(GroupKeyInfoItemIdentifier.class.getName());
		sb.append(", _groupId=");
		sb.append(_groupId);
		sb.append(", _urlTitle=");
		sb.append(_urlTitle);
		sb.append("}");

		return sb.toString();
	}

	private final long _groupId;
	private final String _urlTitle;

}