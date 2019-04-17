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

package com.liferay.document.library.web.internal.info.display.contributor;

import com.liferay.asset.info.display.contributor.BaseAssetInfoDisplayContributor;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.info.display.contributor.InfoDisplayContributor;
import com.liferay.info.display.contributor.InfoDisplayObjectProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alejandro Tardín
 */
@Component(service = InfoDisplayContributor.class)
public class DLFileEntryAssetInfoDisplayContributor
	extends BaseAssetInfoDisplayContributor<FileEntry> {

	@Override
	public String getClassName() {
		return DLFileEntryConstants.getClassName();
	}

	@Override
	public InfoDisplayObjectProvider getInfoDisplayObjectProvider(long classPK)
		throws PortalException {

		InfoDisplayObjectProvider<AssetEntry> infoDisplayObjectProvider =
			super.getInfoDisplayObjectProvider(classPK);

		return new InfoDisplayObjectProvider<AssetEntry>() {

			@Override
			public long getClassNameId() {
				return infoDisplayObjectProvider.getClassNameId();
			}

			@Override
			public long getClassPK() {
				return infoDisplayObjectProvider.getClassPK();
			}

			@Override
			public long getClassTypeId() {
				return infoDisplayObjectProvider.getClassTypeId();
			}

			@Override
			public String getDescription(Locale locale) {
				return infoDisplayObjectProvider.getDescription(locale);
			}

			@Override
			public AssetEntry getDisplayObject() {
				return infoDisplayObjectProvider.getDisplayObject();
			}

			@Override
			public long getGroupId() {
				return infoDisplayObjectProvider.getGroupId();
			}

			@Override
			public String getKeywords(Locale locale) {
				return infoDisplayObjectProvider.getKeywords(locale);
			}

			@Override
			public String getTitle(Locale locale) {
				return infoDisplayObjectProvider.getTitle(locale);
			}

			@Override
			public String getURLTitle(Locale locale) {
				return String.valueOf(infoDisplayObjectProvider.getClassPK());
			}

		};
	}

	@Override
	public InfoDisplayObjectProvider getInfoDisplayObjectProvider(
			long groupId, String urlTitle)
		throws PortalException {

		return getInfoDisplayObjectProvider(Long.valueOf(urlTitle));
	}

	@Override
	public String getInfoURLSeparator() {
		return "/d/";
	}

	@Override
	protected Map<String, Object> getClassTypeValues(
		FileEntry fileEntry, Locale locale) {

		return new HashMap<>();
	}

}