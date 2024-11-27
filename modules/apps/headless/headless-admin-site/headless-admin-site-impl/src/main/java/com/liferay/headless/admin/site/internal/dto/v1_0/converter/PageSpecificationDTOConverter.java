/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.dto.v1_0.converter;

import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.headless.admin.site.dto.v1_0.ItemExternalReference;
import com.liferay.headless.admin.site.dto.v1_0.PageSpecification;
import com.liferay.headless.admin.site.dto.v1_0.Settings;
import com.liferay.headless.admin.site.dto.v1_0.StyleBook;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.portal.kernel.model.ColorScheme;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ThemeLocalService;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.style.book.model.StyleBookEntry;
import com.liferay.style.book.service.StyleBookEntryLocalService;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lourdes Fernández Besada
 */
@Component(
	property = "dto.class.name=com.liferay.portal.kernel.model.Layout",
	service = DTOConverter.class
)
public class PageSpecificationDTOConverter
	implements DTOConverter<Layout, PageSpecification> {

	@Override
	public String getContentType() {
		return PageSpecification.class.getSimpleName();
	}

	@Override
	public PageSpecification toDTO(
			DTOConverterContext dtoConverterContext, Layout layout)
		throws Exception {

		return new PageSpecification() {
			{
				setExternalReferenceCode(layout::getExternalReferenceCode);
				setSettings(() -> _setSettings(layout));
				setType(
					() -> {
						if (layout.isTypeAssetDisplay() ||
							layout.isTypeContent()) {

							return Type.CONTENT_PAGE_SPECIFICATION;
						}

						return Type.WIDGET_PAGE_SPECIFICATION;
					});
			}
		};
	}

	private Settings _setSettings(Layout layout) throws Exception {
		UnicodeProperties unicodeProperties =
			layout.getTypeSettingsProperties();

		return new Settings() {
			{
				setColorSchemeName(
					() -> {
						if (Validator.isNull(layout.getColorSchemeId()) ||
							Validator.isNull(layout.getThemeId())) {

							return null;
						}

						ColorScheme colorScheme =
							_themeLocalService.getColorScheme(
								layout.getCompanyId(), layout.getThemeId(),
								layout.getColorSchemeId());

						if (colorScheme == null) {
							return null;
						}

						return colorScheme.getName();
					});
				setCss(
					() -> {
						if (Validator.isNull(layout.getCss())) {
							return null;
						}

						return layout.getCss();
					});
				setFavIcon(
					() -> {
						if (layout.getFaviconFileEntryId() == 0) {
							return null;
						}

						FileEntry fileEntry = _dlAppService.getFileEntry(
							layout.getFaviconFileEntryId());

						if (fileEntry == null) {
							return null;
						}

						return new ItemExternalReference() {
							{
								setClassName(() -> FileEntry.class.getName());
								setCollectionType(CollectionType.COLLECTION);
								setExternalReferenceCode(
									fileEntry::getExternalReferenceCode);
							}
						};
					});
				setJavascript(
					() -> unicodeProperties.getProperty("javascript", null));
				setMasterPageExternalReferenceCode(
					() -> {
						if (layout.getMasterLayoutPlid() == 0) {
							return null;
						}

						LayoutPageTemplateEntry layoutPageTemplateEntry =
							_layoutPageTemplateEntryLocalService.
								fetchLayoutPageTemplateEntryByPlid(
									layout.getMasterLayoutPlid());

						if (layoutPageTemplateEntry == null) {
							return null;
						}

						return layoutPageTemplateEntry.
							getExternalReferenceCode();
					});
				setStyleBook(
					() -> {
						StyleBookEntry styleBookEntry =
							_styleBookEntryLocalService.fetchStyleBookEntry(
								layout.getStyleBookEntryId());

						if (styleBookEntry == null) {
							return null;
						}

						return new StyleBook() {
							{
								setKey(
									() ->
										styleBookEntry.getStyleBookEntryKey());
								setName(styleBookEntry::getName);
							}
						};
					});
				setThemeName(
					() -> {
						if (Validator.isNull(layout.getThemeId())) {
							return null;
						}

						Theme theme = _themeLocalService.fetchTheme(
							layout.getCompanyId(), layout.getThemeId());

						if (theme == null) {
							return null;
						}

						return theme.getName();
					});
				setThemeSettings(
					() -> {
						UnicodeProperties themeSettingsUnicodeProperties =
							new UnicodeProperties();

						for (Map.Entry<String, String> entry :
								unicodeProperties.entrySet()) {

							String key = entry.getKey();

							if (key.startsWith("lfr-theme:")) {
								themeSettingsUnicodeProperties.setProperty(
									key, entry.getValue());
							}
						}

						if (themeSettingsUnicodeProperties.isEmpty()) {
							return null;
						}

						return themeSettingsUnicodeProperties;
					});
			}
		};
	}

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Reference
	private StyleBookEntryLocalService _styleBookEntryLocalService;

	@Reference
	private ThemeLocalService _themeLocalService;

}