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
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.service.AssetListEntryService;
import com.liferay.headless.delivery.dto.v1_0.ContentSetElement;
import com.liferay.headless.delivery.dto.v1_0.converter.DTOConverter;
import com.liferay.headless.delivery.dto.v1_0.converter.DefaultDTOConverterContext;
import com.liferay.headless.delivery.internal.dto.v1_0.converter.DTOConverterRegistry;
import com.liferay.headless.delivery.resource.v1_0.ContentSetElementResource;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.CamelCaseUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.segments.provider.SegmentsEntryProviderRegistry;

import java.time.LocalDate;
import java.time.ZonedDateTime;

import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;

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
	public Page<ContentSetElement> getContentSetContentSetElementsPage(
			Long contentSetId, Pagination pagination)
		throws Exception {

		AssetListEntry assetListEntry =
			_assetListEntryService.getAssetListEntry(contentSetId);

		return _getContentSetContentSetElementsPage(assetListEntry, pagination);
	}

	@Override
	public Page<ContentSetElement> getSiteContentSetByKeyContentSetElementsPage(
			Long siteId, String key, Pagination pagination)
		throws Exception {

		AssetListEntry assetListEntry =
			_assetListEntryService.getAssetListEntry(siteId, key);

		return _getContentSetContentSetElementsPage(assetListEntry, pagination);
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

	private com.liferay.segments.context.Context _createSegmentsContext() {
		com.liferay.segments.context.Context context =
			new com.liferay.segments.context.Context();

		MultivaluedMap<String, String> multivaluedMap =
			_httpHeaders.getRequestHeaders();

		for (Map.Entry<String, List<String>> entry :
				multivaluedMap.entrySet()) {

			String key = StringUtil.toLowerCase(entry.getKey());

			List<String> values = entry.getValue();

			String value = values.get(0);

			if (key.equals("accept-language")) {
				context.put(
					com.liferay.segments.context.Context.LANGUAGE_ID,
					value.replace("-", "_"));
			}
			else if (key.equals("host")) {
				context.put(com.liferay.segments.context.Context.URL, value);
			}
			else if (key.equals("referer")) {
				context.put(
					com.liferay.segments.context.Context.REFERRER_URL, value);
			}
			else if (key.equals("user-agent")) {
				context.put(
					com.liferay.segments.context.Context.USER_AGENT, value);
			}
			else if (key.startsWith("x-")) {
				context.put(
					CamelCaseUtil.toCamelCase(key.replace("x-", "")), value);
			}
			else {
				context.put(key, value);
			}
		}

		context.put(
			com.liferay.segments.context.Context.LOCAL_DATE,
			LocalDate.from(ZonedDateTime.now()));

		return context;
	}

	private Page<ContentSetElement> _getContentSetContentSetElementsPage(
			AssetListEntry assetListEntry, Pagination pagination)
		throws Exception {

		long[] segmentsEntryIds =
			_segmentsEntryProviderRegistry.getSegmentsEntryIds(
				assetListEntry.getGroupId(), _user.getModelClassName(),
				_user.getPrimaryKey(), _createSegmentsContext());

		return Page.of(
			transform(
				assetListEntry.getAssetEntries(
					segmentsEntryIds, pagination.getStartPosition(),
					pagination.getEndPosition()),
				this::_toContentSetElement),
			pagination, assetListEntry.getAssetEntriesCount(segmentsEntryIds));
	}

	private ContentSetElement _toContentSetElement(AssetEntry assetEntry) {
		DTOConverter dtoConverter = DTOConverterRegistry.getDTOConverter(
			assetEntry.getClassName());

		return new ContentSetElement() {
			{
				id = assetEntry.getClassPK();
				title = assetEntry.getTitle(
					contextAcceptLanguage.getPreferredLocale());

				setContent(
					() -> {
						if (dtoConverter == null) {
							return null;
						}

						return dtoConverter.toDTO(
							new DefaultDTOConverterContext(
								contextAcceptLanguage.getPreferredLocale(),
								assetEntry.getClassPK(), contextUriInfo));
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
	private AssetListEntryService _assetListEntryService;

	@Context
	private HttpHeaders _httpHeaders;

	@Reference
	private SegmentsEntryProviderRegistry _segmentsEntryProviderRegistry;

	@Context
	private User _user;

}