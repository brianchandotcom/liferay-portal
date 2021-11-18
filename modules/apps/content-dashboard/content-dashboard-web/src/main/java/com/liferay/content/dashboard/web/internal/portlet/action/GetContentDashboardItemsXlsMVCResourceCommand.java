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
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.searcher.Searcher;

import java.io.ByteArrayOutputStream;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.List;
import java.util.Locale;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

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

		try {
			Workbook workbook = new HSSFWorkbook();

			ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

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

			List<ContentDashboardItem<?>> contentDashboardItems = searchContainer.getResults();

			for (ContentDashboardItem<?> contentDashboardItem : contentDashboardItems) {
				Row row = sheet.createRow(sheet.getLastRowNum() + 1);

				_createDataRow(
					contentDashboardItem, locale, resourceRequest,
					resourceResponse, row, themeDisplay);
			}

			ByteArrayOutputStream byteArrayOutputStream =
				new ByteArrayOutputStream();

			workbook.write(byteArrayOutputStream);

			LocalDate now = LocalDate.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
				"MM_dd_yyyy");

			PortletResponseUtil.sendFile(
				resourceRequest, resourceResponse,
				"ContentDashboardItemsData" + now.format(formatter) + ".xls",
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

		String[] keys = {
			contentDashboardItem.getTitle(locale),
			contentDashboardItem.getUserName(),
			contentDashboardItem.getTypeLabel(locale)
		};

		for (String key : keys) {
			cellIndex = _createCell(cellIndex, row, key);
		}

		cellIndex = _createSubtypeCell(
			cellIndex, contentDashboardItem, locale, row);

		cellIndex = _createCell(
			cellIndex, row, contentDashboardItem.getScopeName(locale));

		cellIndex = _createVersionCell(
			cellIndex, contentDashboardItem, locale, row);

		cellIndex = _createCell(
			cellIndex, row,
			StringUtils.joinWith(
				", ",
				ListUtil.toList(
					contentDashboardItem.getAssetCategories(), category -> category.getTitle(locale))));

		cellIndex = _createCell(
			cellIndex, row,
			StringUtils.joinWith(
				", ",
				ListUtil.toList(
					contentDashboardItem.getAssetTags(),
					AssetTagModel::getName)));

		return _createCell(
			cellIndex, row,
			String.valueOf(contentDashboardItem.getModifiedDate()));
	}

	private int _createBasicDataHeaderCells(
		Row headerRow, int headerRowCellIndex, Locale locale) {

		String[] keys = {
			LanguageUtil.get(locale, "title"),
			LanguageUtil.get(locale, "author"),
			LanguageUtil.get(locale, "type"),
			LanguageUtil.get(locale, "subtype"),
			LanguageUtil.get(locale, "site-or-asset-library"),
			LanguageUtil.get(locale, "status"),
			LanguageUtil.get(locale, "categories"),
			LanguageUtil.get(locale, "tags"),
			LanguageUtil.get(locale, "modified-date")
		};

		for (String key : keys) {
			headerRowCellIndex = _createCell(
				headerRowCellIndex, headerRow, LanguageUtil.get(locale, key));
		}

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

		String[] keys = {
			"description", "extension", "file-name",
			LanguageUtil.get(locale, "size")
		};

		for (String key : keys) {
			headerRowCellIndex = _createCell(
				headerRowCellIndex, headerRow, LanguageUtil.get(locale, key));
		}

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

		String[] keys = {
			"display-date", "creation-date", "languages-translated-into"
		};

		for (String key : keys) {
			headerRowCellIndex = _createCell(
				headerRowCellIndex, headerRow, LanguageUtil.get(locale, key));
		}
	}

	private int _createSubtypeCell(
		int cellIndex, ContentDashboardItem<?> contentDashboardItem,
		Locale locale, Row row) {

		ContentDashboardItemSubtype contentDashboardItemSubtype =
			contentDashboardItem.getContentDashboardItemSubtype();

		return _createCell(
			cellIndex, row, contentDashboardItemSubtype.getLabel(locale));
	}

	private int _createVersionCell(
		int cellIndex, ContentDashboardItem<?> contentDashboardItem,
		Locale locale, Row row) {

		List<ContentDashboardItem.Version> versions =
			contentDashboardItem.getVersions(locale);

		ContentDashboardItem.Version latestVersion = versions.get(0);

		return _createCell(cellIndex, row, latestVersion.getLabel());
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

		String[] keys = {"description", "extension", "fileName", "size"};

		for (String key : keys) {
			String cellValue = fileSpecificDataJSONObject.get(
				key
			).toString();

			cellIndex = _createCell(cellIndex, row, cellValue);
		}

		return cellIndex;
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

		String[] keys = {"displayDate", "creationDate", "languagesTranslated"};

		for (String key : keys) {
			Object object = specificInformationJSONObject.get(key);
			String cellValue = null;

			if (key.equals("languagesTranslated")) {
				cellValue = StringUtil.merge((String[])object);
			}
			else {
				cellValue = object.toString();
			}

			cellIndex = _createCell(cellIndex, row, cellValue);
		}
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