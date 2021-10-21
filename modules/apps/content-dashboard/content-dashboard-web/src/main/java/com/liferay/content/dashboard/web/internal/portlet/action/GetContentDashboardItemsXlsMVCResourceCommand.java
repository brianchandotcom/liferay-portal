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

package com.liferay.content.dashboard.web.internal.portlet.action;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.model.AssetTagModel;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.content.dashboard.web.internal.constants.ContentDashboardPortletKeys;
import com.liferay.content.dashboard.web.internal.dao.search.ContentDashboardItemSearchContainerFactory;
import com.liferay.content.dashboard.web.internal.item.ContentDashboardItem;
import com.liferay.content.dashboard.web.internal.item.ContentDashboardItemFactoryTracker;
import com.liferay.content.dashboard.web.internal.item.FileEntryContentDashboardItem;
import com.liferay.content.dashboard.web.internal.item.JournalArticleContentDashboardItem;
import com.liferay.content.dashboard.web.internal.item.type.ContentDashboardItemSubtype;
import com.liferay.content.dashboard.web.internal.provider.AssetVocabulariesProvider;
import com.liferay.content.dashboard.web.internal.searcher.ContentDashboardSearchRequestBuilderFactory;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.searcher.Searcher;

import java.io.ByteArrayOutputStream;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Yurena Cabrera
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ContentDashboardPortletKeys.CONTENT_DASHBOARD_ADMIN,
		"mvc.command.name=/content_dashboard/get_content_dashboard_items_xls"
	},
	service = MVCResourceCommand.class
)
public class GetContentDashboardItemsXlsMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		Workbook workbook = new HSSFWorkbook();

		Sheet sheet = workbook.createSheet("Content Dashboard Data");

		Locale locale = _portal.getLocale(resourceRequest);

		_createHeaderRow(locale, sheet);

		ContentDashboardItemSearchContainerFactory
			contentDashboardItemSearchContainerFactory =
				ContentDashboardItemSearchContainerFactory.getInstance(
					_assetCategoryLocalService, _assetVocabularyLocalService,
					_contentDashboardItemFactoryTracker,
					_contentDashboardSearchRequestBuilderFactory, _portal,
					resourceRequest, resourceResponse, _searcher);

		SearchContainer<ContentDashboardItem<?>> searchContainer =
			contentDashboardItemSearchContainerFactory.createWithAllResults();

		List<ContentDashboardItem<?>> items = searchContainer.getResults();

		try {
			for (ContentDashboardItem<?> contentDashboardItem : items) {
				Row row = sheet.createRow(sheet.getLastRowNum() + 1);

				HttpServletRequest httpServletRequest =
					_portal.getHttpServletRequest(resourceRequest);

				ThemeDisplay themeDisplay =
					(ThemeDisplay)httpServletRequest.getAttribute(
						WebKeys.THEME_DISPLAY);

				_createDataRow(
					contentDashboardItem, locale, resourceRequest,
					resourceResponse, row, themeDisplay);
			}

			ByteArrayOutputStream byteArrayOutputStream =
				new ByteArrayOutputStream();

			workbook.write(byteArrayOutputStream);

			resourceResponse.setContentType(
				ContentTypes.APPLICATION_VND_MS_EXCEL);
			resourceResponse.setProperty(
				"Content-Disposition",
				"attachment; filename=\"ContentDashboardItemsData.xls\"");

			PortletResponseUtil.sendFile(
				resourceRequest, resourceResponse,
				"ContentDashboardItemsData.xls",
				byteArrayOutputStream.toByteArray(),
				ContentTypes.APPLICATION_VND_MS_EXCEL);
		}
		catch (Exception exception) {
			_log.error(exception, exception);
		}
	}

	private int _createBasicDataCells(
		int cellIndex, ContentDashboardItem<?> contentDashboardItem,
		Locale locale, Row row) {

		cellIndex = _createCell(
			cellIndex, row, contentDashboardItem.getTitle(locale));
		cellIndex = _createCell(
			cellIndex, row, contentDashboardItem.getUserName());
		cellIndex = _createCell(
			cellIndex, row, contentDashboardItem.getTypeLabel(locale));

		ContentDashboardItemSubtype contentDashboardItemSubtype =
			contentDashboardItem.getContentDashboardItemSubtype();

		cellIndex = _createCell(
			cellIndex, row, contentDashboardItemSubtype.getLabel(locale));

		cellIndex = _createCell(
			cellIndex, row, contentDashboardItem.getScopeName(locale));

		List<ContentDashboardItem.Version> versions =
			contentDashboardItem.getVersions(locale);

		ContentDashboardItem.Version latestVersion = versions.get(0);

		cellIndex = _createCell(cellIndex, row, latestVersion.getLabel());

		List<AssetCategory> categories =
			contentDashboardItem.getAssetCategories();

		Stream<AssetCategory> assetCategoryStream = categories.stream();

		cellIndex = _createCell(
			cellIndex, row,
			StringUtils.joinWith(
				", ",
				assetCategoryStream.map(
					assetCategory -> assetCategory.getTitle(locale)
				).collect(
					Collectors.toList()
				)));

		List<AssetTag> assetTags = contentDashboardItem.getAssetTags();

		Stream<AssetTag> assetTagsStream = assetTags.stream();

		cellIndex = _createCell(
			cellIndex, row,
			StringUtils.joinWith(
				", ",
				assetTagsStream.map(
					AssetTagModel::getName
				).collect(
					Collectors.toList()
				)));

		return _createCell(
			cellIndex, row,
			String.valueOf(contentDashboardItem.getModifiedDate()));
	}

	private int _createBasicDataHeaderCells(
		Row headerRow, int headerRowCellIndex, Locale locale) {

		headerRowCellIndex = _createCell(
			headerRowCellIndex, headerRow, LanguageUtil.get(locale, "title"));

		headerRowCellIndex = _createCell(
			headerRowCellIndex, headerRow, LanguageUtil.get(locale, "author"));

		headerRowCellIndex = _createCell(
			headerRowCellIndex, headerRow, LanguageUtil.get(locale, "type"));

		headerRowCellIndex = _createCell(
			headerRowCellIndex, headerRow, LanguageUtil.get(locale, "subtype"));

		headerRowCellIndex = _createCell(
			headerRowCellIndex, headerRow,
			LanguageUtil.get(locale, "site-or-asset-library"));

		headerRowCellIndex = _createCell(
			headerRowCellIndex, headerRow, LanguageUtil.get(locale, "status"));

		headerRowCellIndex = _createCell(
			headerRowCellIndex, headerRow,
			LanguageUtil.get(locale, "categories"));

		headerRowCellIndex = _createCell(
			headerRowCellIndex, headerRow, LanguageUtil.get(locale, "tags"));

		headerRowCellIndex = _createCell(
			headerRowCellIndex, headerRow,
			LanguageUtil.get(locale, "modified-date"));

		return headerRowCellIndex;
	}

	private int _createCell(int cellIndex, Row row, String value) {
		Cell titleDataCell = row.createCell(cellIndex++);

		titleDataCell.setCellValue(value);

		return cellIndex;
	}

	private void _createDataRow(
		ContentDashboardItem<?> contentDashboardItem, Locale locale,
		ResourceRequest resourceRequest, ResourceResponse resourceResponse,
		Row row, ThemeDisplay themeDisplay) {

		int cellIndex = _createBasicDataCells(
			0, contentDashboardItem, locale, row);

		if (contentDashboardItem instanceof FileEntryContentDashboardItem) {
			cellIndex = _getFileSpecificInformationJSONObject(
				cellIndex, contentDashboardItem, locale, resourceResponse,
				resourceRequest, row, themeDisplay);
		}

		if (contentDashboardItem instanceof
				JournalArticleContentDashboardItem) {

			_getJournalSpecificInformationJSONObject(
				cellIndex + _FIRST_JOURNAL_SPECIFIC_FIELD_INDEX,
				contentDashboardItem, locale, resourceResponse, resourceRequest,
				row, themeDisplay);
		}
	}

	private int _createFileHeaderCells(
		Row headerRow, int headerRowCellIndex, Locale locale) {

		headerRowCellIndex = _createCell(
			headerRowCellIndex, headerRow,
			LanguageUtil.get(locale, "description"));

		headerRowCellIndex = _createCell(
			headerRowCellIndex, headerRow,
			LanguageUtil.get(locale, "extension"));

		headerRowCellIndex = _createCell(
			headerRowCellIndex, headerRow,
			LanguageUtil.get(locale, "file-name"));

		headerRowCellIndex = _createCell(
			headerRowCellIndex, headerRow, LanguageUtil.get(locale, "size"));

		return headerRowCellIndex;
	}

	private void _createHeaderRow(Locale locale, Sheet sheet) {
		Row headerRow = sheet.createRow((short)0);

		int headerRowCellIndex = _createBasicDataHeaderCells(
			headerRow, 0, locale);

		headerRowCellIndex = _createFileHeaderCells(
			headerRow, headerRowCellIndex, locale);

		_createJournalArticleHeaderRow(headerRow, headerRowCellIndex, locale);
	}

	private void _createJournalArticleHeaderRow(
		Row headerRow, int headerRowCellIndex, Locale locale) {

		headerRowCellIndex = _createCell(
			headerRowCellIndex, headerRow,
			LanguageUtil.get(locale, "display-date"));

		headerRowCellIndex = _createCell(
			headerRowCellIndex, headerRow,
			LanguageUtil.get(locale, "creation-date"));

		_createCell(
			headerRowCellIndex, headerRow,
			LanguageUtil.get(locale, "languages-translated-into"));
	}

	private int _getFileSpecificInformationJSONObject(
		int cellIndex, ContentDashboardItem<?> contentDashboardItem,
		Locale locale, ResourceResponse resourceResponse,
		ResourceRequest resourceRequest, Row row, ThemeDisplay themeDisplay) {

		JSONObject fileSpecificDataJSONObject =
			contentDashboardItem.getSpecificInformationJSONObject(
				ParamUtil.getString(resourceRequest, "backURL"),
				_portal.getLiferayPortletResponse(resourceResponse), locale,
				themeDisplay);

		if (fileSpecificDataJSONObject == null)

			return cellIndex;

		String fileDescription = fileSpecificDataJSONObject.get(
			"description"
		).toString();

		cellIndex = _createCell(cellIndex, row, fileDescription);

		String extension = fileSpecificDataJSONObject.get(
			"extension"
		).toString();

		cellIndex = _createCell(cellIndex, row, extension);

		String fileName = fileSpecificDataJSONObject.get(
			"fileName"
		).toString();

		cellIndex = _createCell(cellIndex, row, fileName);

		String size = fileSpecificDataJSONObject.get(
			"size"
		).toString();

		return _createCell(cellIndex, row, size);
	}

	private void _getJournalSpecificInformationJSONObject(
		int cellIndex, ContentDashboardItem<?> contentDashboardItem,
		Locale locale, ResourceResponse resourceResponse,
		ResourceRequest resourceRequest, Row row, ThemeDisplay themeDisplay) {

		JSONObject specificInformationJSONObject =
			contentDashboardItem.getSpecificInformationJSONObject(
				ParamUtil.getString(resourceRequest, "backURL"),
				_portal.getLiferayPortletResponse(resourceResponse), locale,
				themeDisplay);

		if (specificInformationJSONObject == null)

			return;

		String displayDate = specificInformationJSONObject.get(
			"displayDate"
		).toString();

		cellIndex = _createCell(cellIndex, row, displayDate);

		String creationDate = specificInformationJSONObject.get(
			"creationDate"
		).toString();

		cellIndex = _createCell(cellIndex, row, creationDate);

		String languagesTranslated = Arrays.toString(
			(String[])specificInformationJSONObject.get("languagesTranslated"));

		_createCell(cellIndex, row, languagesTranslated);
	}

	private static final int _FIRST_JOURNAL_SPECIFIC_FIELD_INDEX = 4;

	private static final Log _log = LogFactoryUtil.getLog(
		GetContentDashboardItemsXlsMVCResourceCommand.class);

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	@Reference
	private ContentDashboardItemFactoryTracker
		_contentDashboardItemFactoryTracker;

	@Reference
	private ContentDashboardSearchRequestBuilderFactory
		_contentDashboardSearchRequestBuilderFactory;

	@Reference
	private Portal _portal;

	@Reference
	private Searcher _searcher;

}