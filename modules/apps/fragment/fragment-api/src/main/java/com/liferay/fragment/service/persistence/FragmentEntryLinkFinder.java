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

package com.liferay.fragment.service.persistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public interface FragmentEntryLinkFinder {

	public int countByG_F(long groupId, long fragmentEntryId);

	public int countByG_F_C(
		long groupId, long fragmentEntryId, long classNameId);

	public int countByG_F_C_L(
		long groupId, long fragmentEntryId, long classNameId,
		int layoutPageTemplateEntryType);

	public java.util.List<com.liferay.fragment.model.FragmentEntryLink>
		findByG_F(
			long groupId, long fragmentEntryId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.fragment.model.FragmentEntryLink>
					orderByComparator);

	public java.util.List<com.liferay.fragment.model.FragmentEntryLink>
		findByG_F_C(
			long groupId, long fragmentEntryId, long classNameId, int start,
			int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.fragment.model.FragmentEntryLink>
					orderByComparator);

	public java.util.List<com.liferay.fragment.model.FragmentEntryLink>
		findByG_F_C_L(
			long groupId, long fragmentEntryId, long classNameId,
			int layoutPageTemplateEntryType, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.fragment.model.FragmentEntryLink>
					orderByComparator);

}