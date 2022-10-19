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

import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.content.dashboard.item.ContentDashboardItem;
import com.liferay.content.dashboard.item.ContentDashboardItemFactory;
import com.liferay.content.dashboard.item.ContentDashboardItemVersion;
import com.liferay.content.dashboard.web.internal.constants.ContentDashboardPortletKeys;
import com.liferay.content.dashboard.web.internal.item.ContentDashboardItemFactoryTracker;
import com.liferay.content.dashboard.web.internal.search.request.ContentDashboardSearchContextBuilder;
import com.liferay.content.dashboard.web.internal.searcher.ContentDashboardSearchRequestBuilderFactory;
import com.liferay.info.search.InfoSearchClassMapperTracker;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.configuration.DefaultSearchResultPermissionFilterConfiguration;
import com.liferay.portal.search.searcher.SearchResponse;
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

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Yurena Cabrera
 */
@Component(
	configurationPid = "com.liferay.portal.search.configuration.DefaultSearchResultPermissionFilterConfiguration",
	property = {
		"javax.portlet.name=" + ContentDashboardPortletKeys.CONTENT_DASHBOARD_ADMIN,
		"mvc.command.name=/content_dashboard/get_content_dashboard_items_xls"
	},
	service = MVCResourceCommand.class
)
public class GetContentDashboardItemsXlsMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_defaultSearchResultPermissionFilterConfiguration =
			ConfigurableUtil.createConfigurable(
				DefaultSearchResultPermissionFilterConfiguration.class,
				properties);
	}

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Locale locale = themeDisplay.getLocale();

		WorkbookBuilder workbookBuilder = new WorkbookBuilder(
			locale, _language.get(locale, "content-dashboard-data"));

		_addWorkbookHeaders(workbookBuilder);

		_addWorkbookRows(locale, resourceRequest, workbookBuilder);

		LocalDate localDate = LocalDate.now();

		PortletResponseUtil.sendFile(
			resourceRequest, resourceResponse,
			"ContentDashboardItemsData" +
				localDate.format(DateTimeFormatter.ofPattern("MM_dd_yyyy")) +
					".xls",
			workbookBuilder.build(), ContentTypes.APPLICATION_VND_MS_EXCEL);
	}

	private void _addWorkbookCell(
		ContentDashboardItem<?> contentDashboardItem, Locale locale,
		WorkbookBuilder workbookBuilder) {

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
				List<ContentDashboardItemVersion>
					latestContentDashboardItemVersions =
						contentDashboardItem.
							getLatestContentDashboardItemVersions(locale);

				ContentDashboardItemVersion contentDashboardItemVersion =
					latestContentDashboardItemVersions.get(0);

				return contentDashboardItemVersion.getLabel();
			}
		).cell(
			StringUtil.merge(
				ListUtil.toList(
					contentDashboardItem.getAssetCategories(),
					assetCategory -> assetCategory.getTitle(locale)),
				StringPool.COMMA_AND_SPACE)
		).cell(
			ListUtil.toString(
				contentDashboardItem.getAssetTags(), AssetTag.NAME_ACCESSOR,
				StringPool.COMMA_AND_SPACE)
		).cell(
			_toString(contentDashboardItem.getModifiedDate())
		).cell(
			contentDashboardItem.getDescription(locale)
		);

		Map<String, Object> specificInformation =
			contentDashboardItem.getSpecificInformation(locale);

		workbookBuilder.cell(_toString(specificInformation, "extension"));

		workbookBuilder.cell(_toString(specificInformation, "file-name"));

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

	private void _addWorkbookHeaders(WorkbookBuilder workbookBuilder) {
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
	}

	private void _addWorkbookRows(
		Locale locale, ResourceRequest resourceRequest,
		WorkbookBuilder workbookBuilder) {

		int searchQueryResultWindowLimit =
			_defaultSearchResultPermissionFilterConfiguration.
				searchQueryResultWindowLimit();
		int start = 0;

		while (true) {
			SearchResponse searchResponse = _getSearchResponse(
				start + searchQueryResultWindowLimit, resourceRequest, start);

			List<Document> documents = searchResponse.getDocuments71();

			if (ListUtil.isEmpty(documents)) {
				break;
			}

			for (Document document : documents) {
				ContentDashboardItem<?> contentDashboardItem =
					_toContentDashboardItem(document);

				if (contentDashboardItem != null) {
					workbookBuilder.row();

					_addWorkbookCell(
						contentDashboardItem, locale, workbookBuilder);
				}
			}

			if (documents.size() < searchQueryResultWindowLimit) {
				break;
			}

			start = start + searchQueryResultWindowLimit;
		}
	}

	private SearchResponse _getSearchResponse(
		int end, ResourceRequest resourceRequest, int start) {

		return _searcher.search(
			_contentDashboardSearchRequestBuilderFactory.builder(
				new ContentDashboardSearchContextBuilder(
					_portal.getHttpServletRequest(resourceRequest),
					_assetCategoryLocalService, _assetVocabularyLocalService
				).withEnd(
					end
				).withSort(
					new Sort(Field.CREATE_DATE, Sort.LONG_TYPE, false),
					new Sort(Field.CLASS_NAME_ID, Sort.LONG_TYPE, false),
					new Sort(Field.CLASS_PK, Sort.LONG_TYPE, false)
				).withStart(
					start
				).build()
			).build());
	}

	private ContentDashboardItem<?> _toContentDashboardItem(Document document) {
		ContentDashboardItemFactory<?> contentDashboardItemFactory =
			_contentDashboardItemFactoryTracker.getContentDashboardItemFactory(
				_infoSearchClassMapperTracker.getClassName(
					document.get(Field.ENTRY_CLASS_NAME)));

		if (contentDashboardItemFactory == null) {
			return null;
		}

		try {
			return contentDashboardItemFactory.create(
				GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)));
		}
		catch (PortalException portalException) {
			_log.error(portalException);

			return null;
		}
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

		if (value == null) {
			return StringPool.BLANK;
		}

		return String.valueOf(value);
	}

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

	private volatile DefaultSearchResultPermissionFilterConfiguration
		_defaultSearchResultPermissionFilterConfiguration;

	@Reference
	private InfoSearchClassMapperTracker _infoSearchClassMapperTracker;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	@Reference
	private Props _props;

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
			return cell(_language.get(_locale, value));
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