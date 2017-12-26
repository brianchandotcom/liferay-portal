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

import com.liferay.fragment.model.FragmentEntryInstanceLink;
import com.liferay.fragment.service.base.FragmentEntryInstanceLinkLocalServiceBaseImpl;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Eudaldo Alonso
 */
public class FragmentEntryInstanceLinkLocalServiceImpl
	extends FragmentEntryInstanceLinkLocalServiceBaseImpl {

	@Override
	public FragmentEntryInstanceLink addFragmentEntryInstanceLink(
		long groupId, long layoutPageTemplateEntryId, long fragmentEntryId,
		int position) {

		long fragmentEntryInstanceLinkId = counterLocalService.increment();

		FragmentEntryInstanceLink fragmentEntryInstanceLink =
			fragmentEntryInstanceLinkPersistence.create(
				fragmentEntryInstanceLinkId);

		fragmentEntryInstanceLink.setGroupId(groupId);
		fragmentEntryInstanceLink.setLayoutPageTemplateEntryId(
			layoutPageTemplateEntryId);
		fragmentEntryInstanceLink.setFragmentEntryId(fragmentEntryId);
		fragmentEntryInstanceLink.setPosition(position);

		fragmentEntryInstanceLinkPersistence.update(fragmentEntryInstanceLink);

		return fragmentEntryInstanceLink;
	}

	@Override
	public List<FragmentEntryInstanceLink> deleteByLayoutPageTemplateEntry(
		long groupId, long layoutPageTemplateEntryId) {

		List<FragmentEntryInstanceLink> deletedFragmentEntryInstanceLinks =
			new ArrayList<>();

		List<FragmentEntryInstanceLink> fragmentEntryInstanceLinks =
			getFragmentEntryInstanceLinks(groupId, layoutPageTemplateEntryId);

		if (ListUtil.isEmpty(fragmentEntryInstanceLinks)) {
			return Collections.emptyList();
		}

		for (FragmentEntryInstanceLink fragmentEntryInstanceLink :
				fragmentEntryInstanceLinks) {

			fragmentEntryInstanceLinkPersistence.remove(
				fragmentEntryInstanceLink);

			deletedFragmentEntryInstanceLinks.add(fragmentEntryInstanceLink);
		}

		return deletedFragmentEntryInstanceLinks;
	}

	@Override
	public FragmentEntryInstanceLink deleteFragmentEntryInstanceLink(
		FragmentEntryInstanceLink fragmentEntryInstanceLink) {

		fragmentEntryInstanceLinkPersistence.remove(fragmentEntryInstanceLink);

		return fragmentEntryInstanceLink;
	}

	@Override
	public List<FragmentEntryInstanceLink> getFragmentEntryInstanceLinks(
		long groupId, long layoutPageTemplateEntryId) {

		return fragmentEntryInstanceLinkPersistence.findByG_L(
			groupId, layoutPageTemplateEntryId);
	}

}