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

import com.liferay.asset.kernel.model.AssetTagModel;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.content.dashboard.web.internal.constants.ContentDashboardPortletKeys;
import com.liferay.content.dashboard.web.internal.dao.search.ContentDashboardItemSearchContainerFactory;
import com.liferay.content.dashboard.web.internal.item.ContentDashboardItem;
import com.liferay.content.dashboard.web.internal.item.ContentDashboardItemFactoryTracker;
import com.liferay.content.dashboard.web.internal.search.request.ContentDashboardItemSearchClassMapperTracker;
import com.liferay.content.dashboard.web.internal.searcher.ContentDashboardSearchRequestBuilderFactory;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.PortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.searcher.Searcher;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Locale locale = themeDisplay.getLocale();

		WorkbookBuilder workbookBuilder = new WorkbookBuilder(
			locale, "Content Dashboard Data");

		workbookBuilder.localizedCell(
			"title"
		).localizedCell(
			"author"
		).localizedCell(
			"type"
		).localizedCell(
			"subtype"
		).localizedCell(
			"site-or-asset-library"
		).localizedCell(
			"status"
		).localizedCell(
			"categories"
		).localizedCell(
			"tags"
		).localizedCell(
			"modified-date"
		).localizedCell(
			"description"
		).localizedCell(
			"extension"
		).localizedCell(
			"file-name"
		).localizedCell(
			"size"
		).localizedCell(
			"display-date"
		).localizedCell(
			"creation-date"
		).localizedCell(
			"languages-translated-into"
		);

		ContentDashboardItemSearchContainerFactory
			contentDashboardItemSearchContainerFactory =
				ContentDashboardItemSearchContainerFactory.getInstance(
					_assetCategoryLocalService, _assetVocabularyLocalService,
					_contentDashboardItemFactoryTracker,
					_contentDashboardItemSearchClassMapperTracker,
					_contentDashboardSearchRequestBuilderFactory, _portal,
					resourceRequest, resourceResponse, _searcher);

		SearchContainer<ContentDashboardItem<?>> searchContainer =
			contentDashboardItemSearchContainerFactory.createWithAllResults();

		for (ContentDashboardItem<?> contentDashboardItem :
				searchContainer.getResults()) {

			workbookBuilder.row();

			workbookBuilder.cell(
				contentDashboardItem.getTitle(locale)
			).cell(
				contentDashboardItem.getUserName()
			).cell(
				contentDashboardItem.getTypeLabel(locale)
			).cell(
				Optional.ofNullable(
					contentDashboardItem.getContentDashboardItemSubtype()
				).map(
					contentDashboardItemSubtype ->
						contentDashboardItemSubtype.getLabel(locale)
				).orElse(
					StringPool.BLANK
				)
			).cell(
				contentDashboardItem.getScopeName(locale)
			).cell(
				() -> {
					List<ContentDashboardItem.Version> versions =
						contentDashboardItem.getVersions(locale);

					ContentDashboardItem.Version version = versions.get(0);

					return version.getLabel();
				}
			).cell(
				StringUtils.joinWith(
					", ",
					ListUtil.toList(
						contentDashboardItem.getAssetCategories(),
						assetCategory -> assetCategory.getTitle(locale)))
			).cell(
				StringUtils.joinWith(
					", ",
					ListUtil.toList(
						contentDashboardItem.getAssetTags(),
						AssetTagModel::getName))
			).cell(
				_toString(contentDashboardItem.getModifiedDate())
			).cell(
				contentDashboardItem.getDescription(locale)
			);

			Map<String, Object> specificInformation =
				contentDashboardItem.getSpecificInformation(locale);

			workbookBuilder.cell(_toString(specificInformation, "extension"));

			if (contentDashboardItem.getClipboard() != null) {
				ContentDashboardItem.Clipboard clipboard =
					contentDashboardItem.getClipboard();

				workbookBuilder.cell(_toString(clipboard.getName()));
			}

			workbookBuilder.cell(
				_toString(specificInformation, "size")
			).cell(
				_toString(specificInformation, "display-date")
			).cell(
				_toString(contentDashboardItem.getCreateDate())
			);

			List<Locale> locales = contentDashboardItem.getAvailableLocales();

			Stream<Locale> stream = locales.stream();

			workbookBuilder.cell(
				stream.map(
					LocaleUtil::toLanguageId
				).collect(
					Collectors.joining(StringPool.COMMA)
				));
		}

		LocalDate localDate = LocalDate.now();

		PortletResponseUtil.sendFile(
			resourceRequest, resourceResponse,
			"ContentDashboardItemsData" +
				localDate.format(DateTimeFormatter.ofPattern("MM_dd_yyyy")) +
					".xls",
			workbookBuilder.build(), ContentTypes.APPLICATION_VND_MS_EXCEL);
	}

	private String _toString(Date date) {
		Instant instant = date.toInstant();

		ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());

		LocalDateTime localDateTime = zonedDateTime.toLocalDateTime();

		return localDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
	}

	private String _toString(
		Map<String, Object> specificInformation, String fieldName) {

		return Optional.ofNullable(
			specificInformation
		).map(
			safeSpecificInformation -> safeSpecificInformation.get(fieldName)
		).filter(
			Objects::nonNull
		).map(
			field -> _toString(field)
		).orElse(
			StringPool.BLANK
		);
	}

	private String _toString(Object value) {
		if (value instanceof Date) {
			return _toString((Date)value);
		}

		return String.valueOf(value);
	}

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	@Reference
	private ContentDashboardItemFactoryTracker
		_contentDashboardItemFactoryTracker;

	@Reference
	private ContentDashboardItemSearchClassMapperTracker
		_contentDashboardItemSearchClassMapperTracker;

	@Reference
	private ContentDashboardSearchRequestBuilderFactory
		_contentDashboardSearchRequestBuilderFactory;

	@Reference
	private Portal _portal;

	@Reference
	private Searcher _searcher;

	private class WorkbookBuilder {

		public WorkbookBuilder(Locale locale, String sheetName) {
			_locale = locale;

			_sheet = _workbook.createSheet(sheetName);

			row();
		}

		public byte[] build() throws IOException {
			ByteArrayOutputStream byteArrayOutputStream =
				new ByteArrayOutputStream();

			_workbook.write(byteArrayOutputStream);

			return byteArrayOutputStream.toByteArray();
		}

		public WorkbookBuilder cell(String value) {
			Cell cell = _row.createCell(_cellIndex++);

			cell.setCellValue(value);

			return this;
		}

		public WorkbookBuilder cell(Supplier<String> supplier) {
			return cell(supplier.get());
		}

		public WorkbookBuilder cellIndexIncrement(int cellIndexIncrement) {
			_cellIndex += cellIndexIncrement;

			return this;
		}

		public WorkbookBuilder localizedCell(String value) {
			return cell(LanguageUtil.get(_locale, value));
		}

		public WorkbookBuilder row() {
			_cellIndex = 0;
			_row = _sheet.createRow(_rowIndex++);

			return this;
		}

		private int _cellIndex;
		private final Locale _locale;
		private Row _row;
		private short _rowIndex;
		private final Sheet _sheet;
		private Workbook _workbook = new HSSFWorkbook();

	}

}