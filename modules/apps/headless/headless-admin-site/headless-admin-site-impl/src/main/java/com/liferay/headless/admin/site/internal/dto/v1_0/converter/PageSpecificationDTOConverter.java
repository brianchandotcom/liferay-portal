/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.dto.v1_0.converter;

import com.liferay.client.extension.constants.ClientExtensionEntryConstants;
import com.liferay.client.extension.model.ClientExtensionEntryRel;
import com.liferay.client.extension.service.ClientExtensionEntryRelLocalService;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.headless.admin.site.dto.v1_0.ClientExtension;
import com.liferay.headless.admin.site.dto.v1_0.ContentPageSpecification;
import com.liferay.headless.admin.site.dto.v1_0.ItemExternalReference;
import com.liferay.headless.admin.site.dto.v1_0.PageSpecification;
import com.liferay.headless.admin.site.dto.v1_0.Settings;
import com.liferay.headless.admin.site.dto.v1_0.StyleBook;
import com.liferay.headless.admin.site.dto.v1_0.WidgetPageSpecification;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.model.ColorScheme;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ThemeLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.style.book.model.StyleBookEntry;
import com.liferay.style.book.service.StyleBookEntryLocalService;

import java.util.HashMap;
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

		if (layout.isTypeAssetDisplay() || layout.isTypeContent()) {
			return _toContentPageSpecification(layout);
		}

		return _toWidgetPageSpecification(layout);
	}

	private ClientExtension _getClientExtension(
		ClientExtensionEntryRel clientExtensionEntryRel) {

		if (clientExtensionEntryRel == null) {
			return null;
		}

		return new ClientExtension() {
			{
				setClientExtensionConfig(
					() -> _getClientExtensionConfig(clientExtensionEntryRel));
				setExternalReferenceCode(
					clientExtensionEntryRel::getCETExternalReferenceCode);
			}
		};
	}

	private ClientExtension _getClientExtension(
		long classNameId, long classPK, String type) {

		return _getClientExtension(
			_clientExtensionEntryRelLocalService.fetchClientExtensionEntryRel(
				classNameId, classPK, type));
	}

	private Map<String, String> _getClientExtensionConfig(
		ClientExtensionEntryRel clientExtensionEntryRel) {

		if (clientExtensionEntryRel == null) {
			return null;
		}

		UnicodeProperties unicodeProperties = UnicodePropertiesBuilder.fastLoad(
			clientExtensionEntryRel.getTypeSettings()
		).build();

		if (unicodeProperties.isEmpty()) {
			return null;
		}

		Map<String, String> clientExtensionConfig = new HashMap<>();

		for (Map.Entry<String, String> entry : unicodeProperties.entrySet()) {
			clientExtensionConfig.put(entry.getKey(), entry.getValue());
		}

		return clientExtensionConfig;
	}

	private ClientExtension[] _getClientExtensions(
		long classNameId, long classPK, String type) {

		ClientExtension[] clientExtensions = TransformUtil.transformToArray(
			_clientExtensionEntryRelLocalService.getClientExtensionEntryRels(
				classNameId, classPK, type),
			clientExtensionEntryRel -> _getClientExtension(
				clientExtensionEntryRel),
			ClientExtension.class);

		if (ArrayUtil.isEmpty(clientExtensions)) {
			return null;
		}

		return clientExtensions;
	}

	private Settings _setSettings(Layout layout) throws Exception {
		long classNameId = _portal.getClassNameId(Layout.class.getName());
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
						ClientExtension clientExtension = _getClientExtension(
							classNameId, layout.getPlid(),
							ClientExtensionEntryConstants.TYPE_THEME_FAVICON);

						if (clientExtension != null) {
							return clientExtension;
						}

						long faviconFileEntryId =
							layout.getFaviconFileEntryId();

						if (faviconFileEntryId == 0) {
							return null;
						}

						FileEntry fileEntry = _dlAppService.getFileEntry(
							faviconFileEntryId);

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
				setGlobalCSSClientExtensions(
					() -> _getClientExtensions(
						classNameId, layout.getPlid(),
						ClientExtensionEntryConstants.TYPE_GLOBAL_CSS));
				setGlobalJSClientExtensions(
					() -> _getClientExtensions(
						classNameId, layout.getPlid(),
						ClientExtensionEntryConstants.TYPE_GLOBAL_JS));
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
				setThemeCSSClientExtension(
					() -> _getClientExtension(
						classNameId, layout.getPlid(),
						ClientExtensionEntryConstants.TYPE_THEME_CSS));
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
				setThemeSpritemapClientExtension(
					() -> _getClientExtension(
						classNameId, layout.getPlid(),
						ClientExtensionEntryConstants.TYPE_THEME_SPRITEMAP));
			}
		};
	}

	private PageSpecification _toContentPageSpecification(Layout layout) {
		return new ContentPageSpecification() {
			{
				setExternalReferenceCode(layout::getExternalReferenceCode);
				setSettings(() -> _setSettings(layout));
				setStatus(
					() -> {
						if (!layout.isDraftLayout()) {
							return Status.APPROVED;
						}

						return Status.DRAFT;
					});
				setType(() -> Type.CONTENT_PAGE_SPECIFICATION);
			}
		};
	}

	private PageSpecification _toWidgetPageSpecification(Layout layout) {
		return new WidgetPageSpecification() {
			{
				setExternalReferenceCode(layout::getExternalReferenceCode);
				setSettings(() -> _setSettings(layout));
				setStatus(() -> Status.APPROVED);
				setType(() -> Type.WIDGET_PAGE_SPECIFICATION);
			}
		};
	}

	@Reference
	private ClientExtensionEntryRelLocalService
		_clientExtensionEntryRelLocalService;

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private StyleBookEntryLocalService _styleBookEntryLocalService;

	@Reference
	private ThemeLocalService _themeLocalService;

}