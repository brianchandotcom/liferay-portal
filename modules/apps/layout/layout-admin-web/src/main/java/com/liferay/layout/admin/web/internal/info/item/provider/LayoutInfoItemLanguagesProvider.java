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

package com.liferay.layout.admin.web.internal.info.item.provider;

import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.layout.admin.web.internal.info.item.helper.LayoutInfoItemLanguagesProviderHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.segments.service.SegmentsExperienceLocalService;
import com.liferay.translation.info.item.provider.InfoItemLanguagesProvider;

import java.util.Collections;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(service = InfoItemLanguagesProvider.class)
public class LayoutInfoItemLanguagesProvider
	implements InfoItemLanguagesProvider<Layout> {

	@Override
	public String[] getAvailableLanguageIds(Layout layout)
		throws PortalException {

		Set<String> availableLanguageIds = new TreeSet<>(
			Comparator.naturalOrder());

		if (layout.isTypeContent()) {
			Collections.addAll(
				availableLanguageIds, layout.getAvailableLanguageIds());
		}

		long defaultSegmentsExperienceId =
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				layout.getPlid());

		Collections.addAll(
			availableLanguageIds,
			_layoutInfoItemLanguagesProviderHelper.getAvailableLanguageIds(
				layout, defaultSegmentsExperienceId));

		return availableLanguageIds.toArray(new String[0]);
	}

	@Override
	public String getDefaultLanguageId(Layout layout) {
		return _layoutInfoItemLanguagesProviderHelper.getDefaultLanguageId(
			layout);
	}

	@Activate
	@Modified
	protected void activate() {
		_layoutInfoItemLanguagesProviderHelper =
			new LayoutInfoItemLanguagesProviderHelper(
				_fragmentEntryLinkLocalService, _language);
	}

	@Reference
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Reference
	private Language _language;

	private volatile LayoutInfoItemLanguagesProviderHelper
		_layoutInfoItemLanguagesProviderHelper;

	@Reference
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

}