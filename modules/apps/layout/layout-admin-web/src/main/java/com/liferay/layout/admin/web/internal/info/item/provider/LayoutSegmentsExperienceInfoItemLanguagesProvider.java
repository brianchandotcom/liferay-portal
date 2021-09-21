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
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.translation.info.item.provider.InfoItemLanguagesProvider;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(service = InfoItemLanguagesProvider.class)
public class LayoutSegmentsExperienceInfoItemLanguagesProvider
	implements InfoItemLanguagesProvider<SegmentsExperience> {

	@Override
	public String[] getAvailableLanguageIds(
			SegmentsExperience segmentsExperience)
		throws PortalException {

		return _layoutInfoItemLanguagesProviderHelper.getAvailableLanguageIds(
			_getLayout(segmentsExperience),
			segmentsExperience.getSegmentsExperienceId());
	}

	@Override
	public String getDefaultLanguageId(SegmentsExperience segmentsExperience) {
		return _layoutInfoItemLanguagesProviderHelper.getDefaultLanguageId(
			_getLayout(segmentsExperience));
	}

	@Activate
	@Modified
	protected void activate() {
		_layoutInfoItemLanguagesProviderHelper =
			new LayoutInfoItemLanguagesProviderHelper(
				_fragmentEntryLinkLocalService, _language);
	}

	private Layout _getLayout(SegmentsExperience segmentsExperience) {
		try {
			Layout layout = _layoutLocalService.getLayout(
				segmentsExperience.getClassPK());

			if (layout.isDraftLayout()) {
				return layout;
			}

			Layout draftLayout = layout.fetchDraftLayout();

			if (draftLayout != null) {
				return draftLayout;
			}

			return layout;
		}
		catch (PortalException portalException) {
			return ReflectionUtil.throwException(portalException);
		}
	}

	@Reference
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Reference
	private Language _language;

	private volatile LayoutInfoItemLanguagesProviderHelper
		_layoutInfoItemLanguagesProviderHelper;

	@Reference
	private LayoutLocalService _layoutLocalService;

}