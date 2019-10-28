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

package com.liferay.segments.internal.search.spi.model.index.contributor;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.search.localization.SearchLocalizationHelper;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;
import com.liferay.segments.internal.search.SegmentsEntryField;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.model.SegmentsEntryRole;
import com.liferay.segments.service.SegmentsEntryRoleLocalService;

import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 */
@Component(
	immediate = true,
	property = "indexer.class.name=com.liferay.segments.model.SegmentsEntry",
	service = ModelDocumentContributor.class
)
public class SegmentsEntryModelDocumentContributor
	implements ModelDocumentContributor<SegmentsEntry> {

	@Override
	public void contribute(Document document, SegmentsEntry segmentsEntry) {
		document.addKeyword(
			SegmentsEntryField.ACTIVE, segmentsEntry.isActive());
		document.addLocalizedKeyword(
			Field.DESCRIPTION, segmentsEntry.getDescriptionMap(), true);
		document.addDate(Field.MODIFIED_DATE, segmentsEntry.getModifiedDate());

		Locale siteDefaultLocale = _getSiteDefaultLocale(
			segmentsEntry.getGroupId());

		_searchLocalizationHelper.addLocalizedField(
			document, Field.NAME, siteDefaultLocale,
			segmentsEntry.getNameMap());

		document.addLocalizedKeyword(
			"localized_name",
			LocalizationUtil.populateLocalizationMap(
				segmentsEntry.getNameMap(),
				segmentsEntry.getDefaultLanguageId(),
				segmentsEntry.getGroupId()),
			true, true);

		document.addKeyword(
			"roleIds", _getRoleIds(segmentsEntry.getSegmentsEntryId()));
	}

	private long[] _getRoleIds(long segmentsEntryId) {
		List<SegmentsEntryRole> segmentsEntryRoles =
			_segmentsEntryRoleLocalService.getSegmentsEntryRoles(
				segmentsEntryId);

		Stream<SegmentsEntryRole> stream = segmentsEntryRoles.stream();

		return stream.mapToLong(
			SegmentsEntryRole::getRoleId
		).toArray();
	}

	private Locale _getSiteDefaultLocale(long groupId) {
		try {
			return _portal.getSiteDefaultLocale(groupId);
		}
		catch (PortalException pe) {
			throw new SystemException(pe);
		}
	}

	@Reference
	private Portal _portal;

	@Reference
	private SearchLocalizationHelper _searchLocalizationHelper;

	@Reference
	private SegmentsEntryRoleLocalService _segmentsEntryRoleLocalService;

}