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

import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.base.FragmentEntryLinkLocalServiceBaseImpl;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

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
		long groupId, long originalFragmentEntryLinkId, long fragmentEntryId,
		long classNameId, long classPK, String css, String html, String js,
		String editableValues, int position) {

		long fragmentEntryLinkId = counterLocalService.increment();

		FragmentEntryLink fragmentEntryLink =
			fragmentEntryLinkPersistence.create(fragmentEntryLinkId);

		fragmentEntryLink.setGroupId(groupId);
		fragmentEntryLink.setOriginalFragmentEntryLinkId(
			originalFragmentEntryLinkId);
		fragmentEntryLink.setFragmentEntryId(fragmentEntryId);
		fragmentEntryLink.setClassNameId(classNameId);
		fragmentEntryLink.setClassPK(classPK);
		fragmentEntryLink.setCss(css);
		fragmentEntryLink.setHtml(html);
		fragmentEntryLink.setJs(js);
		fragmentEntryLink.setEditableValues(editableValues);
		fragmentEntryLink.setPosition(position);

		fragmentEntryLinkPersistence.update(fragmentEntryLink);

		return fragmentEntryLink;
	}

	@Override
	public FragmentEntryLink addFragmentEntryLink(
		long groupId, long fragmentEntryId, long classNameId, long classPK,
		String css, String html, String js, String editableValues,
		int position) {

		return addFragmentEntryLink(
			groupId, 0, fragmentEntryId, classNameId, classPK, css, html, js,
			editableValues, position);
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
			long groupId, long classNameId, long classPK) {

		List<FragmentEntryLink> deletedFragmentEntryLinks = new ArrayList<>();

		List<FragmentEntryLink> fragmentEntryLinks = getFragmentEntryLinks(
			groupId, classNameId, classPK);

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
		long groupId, long classNameId, long classPK) {

		return fragmentEntryLinkPersistence.findByG_C_C(
			groupId, classNameId, classPK);
	}

	@Override
	public FragmentEntryLink updateFragmentEntryLink(
		long fragmentEntryLinkId, int position) {

		FragmentEntryLink fragmentEntryLink = fetchFragmentEntryLink(
			fragmentEntryLinkId);

		fragmentEntryLink.setPosition(position);

		fragmentEntryLinkPersistence.update(fragmentEntryLink);

		return fragmentEntryLink;
	}

	@Override
	public FragmentEntryLink updateFragmentEntryLink(
		long fragmentEntryLinkId, String editableValues) {

		FragmentEntryLink fragmentEntryLink = fetchFragmentEntryLink(
			fragmentEntryLinkId);

		fragmentEntryLink.setEditableValues(editableValues);

		fragmentEntryLinkPersistence.update(fragmentEntryLink);

		return fragmentEntryLink;
	}

	@Override
	public void updateFragmentEntryLinks(
			long groupId, long classNameId, long classPK,
			long[] fragmentEntryIds, String editableValues)
		throws JSONException {

		deleteLayoutPageTemplateEntryFragmentEntryLinks(
			groupId, classNameId, classPK);

		if (ArrayUtil.isEmpty(fragmentEntryIds)) {
			return;
		}

		JSONObject jsonObject = _jsonFactory.createJSONObject(editableValues);

		int position = 0;

		for (long fragmentId : fragmentEntryIds) {
			FragmentEntry fragmentEntry =
				fragmentEntryLocalService.fetchFragmentEntry(fragmentId);

			addFragmentEntryLink(
				groupId, fragmentEntry.getFragmentEntryId(), classNameId,
				classPK, fragmentEntry.getCss(), fragmentEntry.getHtml(),
				fragmentEntry.getJs(),
				jsonObject.getString(String.valueOf(position)), position++);
		}
	}

	@ServiceReference(type = JSONFactory.class)
	private JSONFactory _jsonFactory;

}