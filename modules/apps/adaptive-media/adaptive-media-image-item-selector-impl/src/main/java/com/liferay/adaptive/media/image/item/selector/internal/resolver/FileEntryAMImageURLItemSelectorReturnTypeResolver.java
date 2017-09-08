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

package com.liferay.adaptive.media.image.item.selector.internal.resolver;

import com.liferay.adaptive.media.image.item.selector.AMImageURLItemSelectorReturnType;
import com.liferay.adaptive.media.image.mediaquery.Condition;
import com.liferay.adaptive.media.image.mediaquery.MediaQuery;
import com.liferay.adaptive.media.image.mediaquery.MediaQueryProvider;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.item.selector.ItemSelectorReturnTypeResolver;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.StringPool;

import java.util.List;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Roberto Díaz
 */
@Component(
	immediate = true, property = {"service.ranking:Integer=100"},
	service = {
		FileEntryAMImageURLItemSelectorReturnTypeResolver.class,
		ItemSelectorReturnTypeResolver.class
	}
)
public class FileEntryAMImageURLItemSelectorReturnTypeResolver
	implements ItemSelectorReturnTypeResolver
		<AMImageURLItemSelectorReturnType, FileEntry> {

	@Override
	public Class<AMImageURLItemSelectorReturnType>
		getItemSelectorReturnTypeClass() {

		return AMImageURLItemSelectorReturnType.class;
	}

	@Override
	public Class<FileEntry> getModelClass() {
		return FileEntry.class;
	}

	@Override
	public String getValue(FileEntry fileEntry, ThemeDisplay themeDisplay)
		throws Exception {

		JSONObject fileEntryJSONObject = JSONFactoryUtil.createJSONObject();

		fileEntryJSONObject.put("fileEntryId", fileEntry.getFileEntryId());

		String previewURL = null;

		if (fileEntry.getGroupId() == fileEntry.getRepositoryId()) {
			previewURL = DLUtil.getPreviewURL(
				fileEntry, fileEntry.getFileVersion(), themeDisplay,
				StringPool.BLANK, false, false);
		}
		else {
			previewURL = PortletFileRepositoryUtil.getPortletFileEntryURL(
				themeDisplay, fileEntry, StringPool.BLANK, false);
		}

		fileEntryJSONObject.put("defaultSource", previewURL);

		JSONArray sourcesArray = JSONFactoryUtil.createJSONArray();

		List<MediaQuery> mediaQueries = _mediaQueryProvider.getMediaQueries(
			fileEntry);

		Stream<MediaQuery> mediaQueryStream = mediaQueries.stream();

		mediaQueryStream.map(
			this::_getSourceJSONObject
		).forEach(
			sourcesArray::put
		);

		fileEntryJSONObject.put("sources", sourcesArray);

		return fileEntryJSONObject.toString();
	}

	private JSONObject _getSourceJSONObject(MediaQuery mediaQuery) {
		JSONObject sourceJSONObject = JSONFactoryUtil.createJSONObject();

		sourceJSONObject.put("src", mediaQuery.getSrc());

		JSONObject attributesJSONObject = JSONFactoryUtil.createJSONObject();

		for (Condition condition : mediaQuery.getConditions()) {
			attributesJSONObject.put(
				condition.getAttribute(), condition.getValue());
		}

		return sourceJSONObject.put("attributes", attributesJSONObject);
	}

	@Reference
	private MediaQueryProvider _mediaQueryProvider;

}