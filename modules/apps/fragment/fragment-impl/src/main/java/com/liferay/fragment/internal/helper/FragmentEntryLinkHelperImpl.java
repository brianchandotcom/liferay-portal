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

package com.liferay.fragment.internal.helper;

import com.liferay.fragment.contributor.FragmentCollectionContributorTracker;
import com.liferay.fragment.helper.FragmentEntryLinkHelper;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.renderer.FragmentRendererTracker;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = FragmentEntryLinkHelper.class)
public class FragmentEntryLinkHelperImpl implements FragmentEntryLinkHelper {

	@Override
	public String getFragmentEntryName(
		FragmentEntryLink fragmentEntryLink, Locale locale) {

		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.fetchFragmentEntry(
				fragmentEntryLink.getFragmentEntryId());

		if (fragmentEntry != null) {
			return fragmentEntry.getName();
		}

		String rendererKey = fragmentEntryLink.getRendererKey();

		if (Validator.isNull(rendererKey)) {
			return StringPool.BLANK;
		}

		Map<String, FragmentEntry> fragmentEntries =
			_fragmentCollectionContributorTracker.getFragmentEntries(locale);

		FragmentEntry contributedFragmentEntry = fragmentEntries.get(
			rendererKey);

		if (contributedFragmentEntry != null) {
			return contributedFragmentEntry.getName();
		}

		FragmentRenderer fragmentRenderer =
			_fragmentRendererTracker.getFragmentRenderer(
				fragmentEntryLink.getRendererKey());

		if (fragmentRenderer != null) {
			return fragmentRenderer.getLabel(locale);
		}

		return StringPool.BLANK;
	}

	@Reference
	private FragmentCollectionContributorTracker
		_fragmentCollectionContributorTracker;

	@Reference
	private FragmentEntryLocalService _fragmentEntryLocalService;

	@Reference
	private FragmentRendererTracker _fragmentRendererTracker;

}