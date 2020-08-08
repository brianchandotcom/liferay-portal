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

package com.liferay.headless.delivery.internal.resource.v1_0;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.list.asset.entry.provider.AssetListAssetEntryProvider;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.service.AssetListEntryService;
import com.liferay.headless.delivery.dto.v1_0.ContentSetElement;
import com.liferay.headless.delivery.resource.v1_0.ContentSetElementResource;
import com.liferay.portal.kernel.util.CamelCaseUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;
import com.liferay.segments.context.Context;
import com.liferay.segments.provider.SegmentsEntryProviderRegistry;

import java.time.LocalDate;
import java.time.ZonedDateTime;

import java.util.Enumeration;
import java.util.HashMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/content-set-element.properties",
	scope = ServiceScope.PROTOTYPE, service = ContentSetElementResource.class
)
public class ContentSetElementResourceImpl
	extends BaseContentSetElementResourceImpl {

	@Override
	public Page<ContentSetElement>
			getAssetLibraryContentSetByKeyContentSetElementsPage(
				Long assetLibraryId, String key, Pagination pagination)
		throws Exception {

		return getSiteContentSetByKeyContentSetElementsPage(
			assetLibraryId, key, pagination);
	}

	@Override
	public Page<ContentSetElement>
			getAssetLibraryContentSetByUuidContentSetElementsPage(
				Long assetLibraryId, String uuid, Pagination pagination)
		throws Exception {

		return getSiteContentSetByUuidContentSetElementsPage(
			assetLibraryId, uuid, pagination);
	}

	@Override
	public Page<ContentSetElement> getContentSetContentSetElementsPage(
			Long contentSetId, Pagination pagination)
		throws Exception {

		return _getContentSetContentSetElementsPage(
			_assetListEntryService.getAssetListEntry(contentSetId), pagination);
	}

	@Override
	public Page<ContentSetElement> getSiteContentSetByKeyContentSetElementsPage(
			Long siteId, String key, Pagination pagination)
		throws Exception {

		return _getContentSetContentSetElementsPage(
			_assetListEntryService.getAssetListEntry(siteId, key), pagination);
	}

	@Override
	public Page<ContentSetElement>
			getSiteContentSetByUuidContentSetElementsPage(
				Long siteId, String uuid, Pagination pagination)
		throws Exception {

		AssetListEntry assetListEntry =
			_assetListEntryService.getAssetListEntryByUuidAndGroupId(
				uuid, siteId);

		return _getContentSetContentSetElementsPage(assetListEntry, pagination);
	}

	private Context _createSegmentsContext() {
		Context context = new Context();

		Enumeration<String> enumeration =
			contextHttpServletRequest.getHeaderNames();

		while (enumeration.hasMoreElements()) {
			String key = enumeration.nextElement();

			String value = contextHttpServletRequest.getHeader(key);

			if (key.equals("accept-language")) {
				context.put(
					Context.LANGUAGE_ID, StringUtil.replace(value, '-', '_'));
			}
			else if (key.equals("host")) {
				context.put(Context.URL, value);
			}
			else if (key.equals("referer")) {
				context.put(Context.REFERRER_URL, value);
			}
			else if (key.equals("user-agent")) {
				context.put(Context.USER_AGENT, value);
			}
			else if (key.startsWith("x-")) {
				context.put(
					CamelCaseUtil.toCamelCase(
						StringUtil.removeSubstring(key, "x-")),
					value);
			}
			else {
				context.put(key, value);
			}
		}

		context.put(Context.LOCAL_DATE, LocalDate.from(ZonedDateTime.now()));

		return context;
	}

	private Page<ContentSetElement> _getContentSetContentSetElementsPage(
			AssetListEntry assetListEntry, Pagination pagination)
		throws Exception {

		long[] segmentsEntryIds =
			_segmentsEntryProviderRegistry.getSegmentsEntryIds(
				assetListEntry.getGroupId(), contextUser.getModelClassName(),
				contextUser.getPrimaryKey(), _createSegmentsContext());

		return Page.of(
			transform(
				_assetListAssetEntryProvider.getAssetEntries(
					assetListEntry, segmentsEntryIds,
					pagination.getStartPosition(), pagination.getEndPosition()),
				this::_toContentSetElement),
			pagination,
			_assetListAssetEntryProvider.getAssetEntriesCount(
				assetListEntry, segmentsEntryIds));
	}

	private ContentSetElement _toContentSetElement(AssetEntry assetEntry) {
		DTOConverter<?, ?> dtoConverter = _dtoConverterRegistry.getDTOConverter(
			assetEntry.getClassName());

		return new ContentSetElement() {
			{
				id = assetEntry.getClassPK();
				title = assetEntry.getTitle(
					contextAcceptLanguage.getPreferredLocale());
				title_i18n = LocalizedMapUtil.getI18nMap(
					contextAcceptLanguage.isAcceptAllLanguages(),
					assetEntry.getTitleMap());

				setContent(
					() -> {
						if (dtoConverter == null) {
							return null;
						}

						return dtoConverter.toDTO(
							new DefaultDTOConverterContext(
								contextAcceptLanguage.isAcceptAllLanguages(),
								new HashMap<>(), _dtoConverterRegistry,
								contextHttpServletRequest,
								assetEntry.getClassPK(),
								contextAcceptLanguage.getPreferredLocale(),
								contextUriInfo, contextUser));
					});
				setContentType(
					() -> {
						if (dtoConverter == null) {
							return assetEntry.getClassName();
						}

						return dtoConverter.getContentType();
					});
			}
		};
	}

	@Reference
	private AssetListAssetEntryProvider _assetListAssetEntryProvider;

	@Reference
	private AssetListEntryService _assetListEntryService;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private SegmentsEntryProviderRegistry _segmentsEntryProviderRegistry;

}