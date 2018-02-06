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

package com.liferay.fragment.service.impl;

import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.base.FragmentEntryLinkLocalServiceBaseImpl;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Eudaldo Alonso
 */
public class FragmentEntryLinkLocalServiceImpl
	extends FragmentEntryLinkLocalServiceBaseImpl {

	@Override
	public FragmentEntryLink addFragmentEntryLink(
		long groupId, long fragmentEntryId, long layoutPageTemplateEntryId,
		String editableValues, int position) {

		long fragmentEntryLinkId = counterLocalService.increment();

		FragmentEntryLink fragmentEntryLink =
			fragmentEntryLinkPersistence.create(fragmentEntryLinkId);

		fragmentEntryLink.setGroupId(groupId);
		fragmentEntryLink.setFragmentEntryId(fragmentEntryId);
		fragmentEntryLink.setLayoutPageTemplateEntryId(
			layoutPageTemplateEntryId);
		fragmentEntryLink.setEditableValues(editableValues);
		fragmentEntryLink.setPosition(position);

		fragmentEntryLinkPersistence.update(fragmentEntryLink);

		return fragmentEntryLink;
	}

	@Override
	public FragmentEntryLink deleteFragmentEntryLink(
		FragmentEntryLink fragmentEntryLink) {

		fragmentEntryLinkPersistence.remove(fragmentEntryLink);

		return fragmentEntryLink;
	}

	@Override
	public List<FragmentEntryLink>
		deleteLayoutPageTemplateEntryFragmentEntryLinks(
			long groupId, long layoutPageTemplateEntryId) {

		List<FragmentEntryLink> deletedFragmentEntryLinks = new ArrayList<>();

		List<FragmentEntryLink> fragmentEntryLinks = getFragmentEntryLinks(
			groupId, layoutPageTemplateEntryId);

		if (ListUtil.isEmpty(fragmentEntryLinks)) {
			return Collections.emptyList();
		}

		for (FragmentEntryLink fragmentEntryLink : fragmentEntryLinks) {
			fragmentEntryLinkPersistence.remove(fragmentEntryLink);

			deletedFragmentEntryLinks.add(fragmentEntryLink);
		}

		return deletedFragmentEntryLinks;
	}

	@Override
	public List<FragmentEntryLink> getFragmentEntryLinks(
		long groupId, long layoutPageTemplateEntryId) {

		return fragmentEntryLinkPersistence.findByG_L(
			groupId, layoutPageTemplateEntryId);
	}

}