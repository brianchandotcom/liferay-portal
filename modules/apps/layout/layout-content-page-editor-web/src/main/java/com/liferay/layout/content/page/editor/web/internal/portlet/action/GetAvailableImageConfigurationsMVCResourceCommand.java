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

package com.liferay.layout.content.page.editor.web.internal.portlet.action;

import com.liferay.adaptive.media.image.configuration.AMImageConfigurationEntry;
import com.liferay.adaptive.media.image.configuration.AMImageConfigurationHelper;
import com.liferay.adaptive.media.image.media.query.Condition;
import com.liferay.adaptive.media.image.media.query.MediaQuery;
import com.liferay.adaptive.media.image.media.query.MediaQueryProvider;
import com.liferay.adaptive.media.image.model.AMImageEntry;
import com.liferay.adaptive.media.image.service.AMImageEntryLocalService;
import com.liferay.adaptive.media.image.url.AMImageURLFactory;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.image.ImageTool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Image;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import java.net.URI;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(
	property = {
		"javax.portlet.name=" + ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET,
		"mvc.command.name=/layout_content_page_editor/get_available_image_configurations"
	},
	service = MVCResourceCommand.class
)
public class GetAvailableImageConfigurationsMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		long fileEntryId = ParamUtil.getLong(resourceRequest, "fileEntryId");

		FileEntry fileEntry = _dlAppService.getFileEntry(fileEntryId);

		JSONArray jsonArray = JSONUtil.put(
			JSONUtil.put(
				"label",
				_language.get(
					_portal.getHttpServletRequest(resourceRequest), "auto")
			).put(
				"size", fileEntry.getSize() / 1000
			).put(
				"value", "auto"
			).put(
				"width",
				() -> {
					Image image = _imageTool.getImage(
						fileEntry.getContentStream());

					return image.getWidth();
				}
			));

		Map<String, String> mediaQueriesMap = new HashMap<>();

		List<MediaQuery> mediaQueries = _mediaQueryProvider.getMediaQueries(
			fileEntry);

		for (MediaQuery mediaQuery : mediaQueries) {
			List<Condition> conditions = mediaQuery.getConditions();

			StringBundler sb = new StringBundler();

			for (Condition condition : conditions) {
				sb.append(StringPool.OPEN_PARENTHESIS);
				sb.append(condition.getAttribute());
				sb.append(StringPool.COLON);
				sb.append(condition.getValue());
				sb.append(StringPool.CLOSE_PARENTHESIS);

				if (conditions.indexOf(condition) != (conditions.size() - 1)) {
					sb.append(" and ");
				}
			}

			List<String> mediaQuerySources = StringUtil.split(
				mediaQuery.getSrc(), CharPool.COMMA);

			for (String mediaQuerySource : mediaQuerySources) {
				mediaQueriesMap.put(mediaQuerySource.trim(), sb.toString());
			}
		}

		FileVersion fileVersion = fileEntry.getFileVersion();

		List<AMImageEntry> amImageEntries =
			_amImageEntryLocalService.getAMImageEntries(
				fileVersion.getFileVersionId());

		for (AMImageEntry amImageEntry : amImageEntries) {
			JSONObject jsonObject = JSONUtil.put(
				"label", amImageEntry.getConfigurationUuid()
			).put(
				"size", amImageEntry.getSize() / 1000
			).put(
				"value", amImageEntry.getConfigurationUuid()
			).put(
				"width", amImageEntry.getWidth()
			);

			Optional<AMImageConfigurationEntry>
				amImageConfigurationEntryOptional =
					_amImageConfigurationHelper.getAMImageConfigurationEntry(
						fileEntry.getCompanyId(),
						amImageEntry.getConfigurationUuid());

			if (amImageConfigurationEntryOptional.isPresent()) {
				AMImageConfigurationEntry amImageConfigurationEntry =
					amImageConfigurationEntryOptional.get();

				URI uri = _amImageURLFactory.createFileEntryURL(
					fileEntry.getFileVersion(), amImageConfigurationEntry);

				jsonObject.put(
					"mediaQuery", mediaQueriesMap.get(uri.toString())
				).put(
					"url", uri.toString()
				);
			}

			jsonArray.put(jsonObject);
		}

		JSONPortletResponseUtil.writeJSON(
			resourceRequest, resourceResponse, jsonArray);
	}

	@Reference
	private AMImageConfigurationHelper _amImageConfigurationHelper;

	@Reference
	private AMImageEntryLocalService _amImageEntryLocalService;

	@Reference
	private AMImageURLFactory _amImageURLFactory;

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private ImageTool _imageTool;

	@Reference
	private Language _language;

	@Reference
	private MediaQueryProvider _mediaQueryProvider;

	@Reference
	private Portal _portal;

}