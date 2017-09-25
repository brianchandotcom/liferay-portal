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

package com.liferay.layout.page.template.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.io.Serializable;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public class LayoutPageTemplateFragmentPK implements Comparable<LayoutPageTemplateFragmentPK>,
	Serializable {
	public long groupId;
	public long layoutPageTemplateId;
	public long fragmentEntryId;

	public LayoutPageTemplateFragmentPK() {
	}

	public LayoutPageTemplateFragmentPK(long groupId,
		long layoutPageTemplateId, long fragmentEntryId) {
		this.groupId = groupId;
		this.layoutPageTemplateId = layoutPageTemplateId;
		this.fragmentEntryId = fragmentEntryId;
	}

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	public long getLayoutPageTemplateId() {
		return layoutPageTemplateId;
	}

	public void setLayoutPageTemplateId(long layoutPageTemplateId) {
		this.layoutPageTemplateId = layoutPageTemplateId;
	}

	public long getFragmentEntryId() {
		return fragmentEntryId;
	}

	public void setFragmentEntryId(long fragmentEntryId) {
		this.fragmentEntryId = fragmentEntryId;
	}

	@Override
	public int compareTo(LayoutPageTemplateFragmentPK pk) {
		if (pk == null) {
			return -1;
		}

		int value = 0;

		if (groupId < pk.groupId) {
			value = -1;
		}
		else if (groupId > pk.groupId) {
			value = 1;
		}
		else {
			value = 0;
		}

		if (value != 0) {
			return value;
		}

		if (layoutPageTemplateId < pk.layoutPageTemplateId) {
			value = -1;
		}
		else if (layoutPageTemplateId > pk.layoutPageTemplateId) {
			value = 1;
		}
		else {
			value = 0;
		}

		if (value != 0) {
			return value;
		}

		if (fragmentEntryId < pk.fragmentEntryId) {
			value = -1;
		}
		else if (fragmentEntryId > pk.fragmentEntryId) {
			value = 1;
		}
		else {
			value = 0;
		}

		if (value != 0) {
			return value;
		}

		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof LayoutPageTemplateFragmentPK)) {
			return false;
		}

		LayoutPageTemplateFragmentPK pk = (LayoutPageTemplateFragmentPK)obj;

		if ((groupId == pk.groupId) &&
				(layoutPageTemplateId == pk.layoutPageTemplateId) &&
				(fragmentEntryId == pk.fragmentEntryId)) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		int hashCode = 0;

		hashCode = HashUtil.hash(hashCode, groupId);
		hashCode = HashUtil.hash(hashCode, layoutPageTemplateId);
		hashCode = HashUtil.hash(hashCode, fragmentEntryId);

		return hashCode;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(15);

		sb.append(StringPool.OPEN_CURLY_BRACE);

		sb.append("groupId");
		sb.append(StringPool.EQUAL);
		sb.append(groupId);

		sb.append(StringPool.COMMA);
		sb.append(StringPool.SPACE);
		sb.append("layoutPageTemplateId");
		sb.append(StringPool.EQUAL);
		sb.append(layoutPageTemplateId);

		sb.append(StringPool.COMMA);
		sb.append(StringPool.SPACE);
		sb.append("fragmentEntryId");
		sb.append(StringPool.EQUAL);
		sb.append(fragmentEntryId);

		sb.append(StringPool.CLOSE_CURLY_BRACE);

		return sb.toString();
	}
}