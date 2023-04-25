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

package com.liferay.headless.delivery.internal.dto.v1_0.converter;

import com.liferay.client.extension.constants.ClientExtensionEntryConstants;
import com.liferay.client.extension.model.ClientExtensionEntryRel;
import com.liferay.client.extension.service.ClientExtensionEntryRelLocalService;
import com.liferay.client.extension.type.CET;
import com.liferay.client.extension.type.manager.CETManager;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.headless.delivery.dto.v1_0.ClientExtension;
import com.liferay.headless.delivery.dto.v1_0.MasterPage;
import com.liferay.headless.delivery.dto.v1_0.PageDefinition;
import com.liferay.headless.delivery.dto.v1_0.Settings;
import com.liferay.headless.delivery.dto.v1_0.StyleBook;
import com.liferay.headless.delivery.dto.v1_0.util.ContentDocumentUtil;
import com.liferay.headless.delivery.internal.dto.v1_0.mapper.LayoutStructureItemMapperRegistry;
import com.liferay.headless.delivery.internal.dto.v1_0.util.PageElementUtil;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.util.constants.LayoutStructureConstants;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ColorScheme;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.style.book.model.StyleBookEntry;
import com.liferay.style.book.service.StyleBookEntryLocalService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rubén Pulido
 * @author Javier de Arcos
 */
@Component(
	property = "dto.class.name=com.liferay.layout.util.structure.LayoutStructure",
	service = DTOConverter.class
)
public class PageDefinitionDTOConverter
	implements DTOConverter<LayoutStructure, PageDefinition> {

	@Override
	public String getContentType() {
		return PageDefinition.class.getSimpleName();
	}

	@Override
	public PageDefinition toDTO(
			DTOConverterContext dtoConverterContext,
			LayoutStructure layoutStructure)
		throws Exception {

		Layout layout = (Layout)dtoConverterContext.getAttribute("layout");

		if (layout == null) {
			throw new IllegalArgumentException(
				"Layout is not defined for layout structure item " +
					layoutStructure.getMainItemId());
		}

		LayoutStructureItem mainLayoutStructureItem =
			layoutStructure.getMainLayoutStructureItem();
		boolean saveInlineContent = GetterUtil.getBoolean(
			dtoConverterContext.getAttribute("saveInlineContent"), true);
		boolean saveMappingConfiguration = GetterUtil.getBoolean(
			dtoConverterContext.getAttribute("saveMappingConfiguration"), true);

		return new PageDefinition() {
			{
				pageElement = PageElementUtil.toPageElement(
					layout.getGroupId(), layoutStructure,
					mainLayoutStructureItem, _layoutStructureItemMapperRegistry,
					saveInlineContent, saveMappingConfiguration);
				settings = _toSettings(dtoConverterContext, layout);
				version =
					LayoutStructureConstants.LATEST_PAGE_DEFINITION_VERSION;
			}
		};
	}

	private CET _getCET(
		long classNameId, long classPK, long companyId, String type) {

		ClientExtensionEntryRel clientExtensionEntryRel =
			_clientExtensionEntryRelLocalService.fetchClientExtensionEntryRel(
				classNameId, classPK, type);

		if (clientExtensionEntryRel == null) {
			return null;
		}

		return _cetManager.getCET(
			companyId, clientExtensionEntryRel.getCETExternalReferenceCode());
	}

	private ClientExtension[] _getClientExtensions(
		long classNameId, DTOConverterContext dtoConverterContext,
		Layout layout, String type) {

		List<ClientExtension> clientExtensions = new ArrayList<>();

		List<ClientExtensionEntryRel> clientExtensionEntryRels =
			_clientExtensionEntryRelLocalService.getClientExtensionEntryRels(
				classNameId, layout.getPlid(), type);

		for (ClientExtensionEntryRel clientExtensionEntryRel :
				clientExtensionEntryRels) {

			CET cet = _cetManager.getCET(
				layout.getCompanyId(),
				clientExtensionEntryRel.getCETExternalReferenceCode());

			if (cet == null) {
				continue;
			}

			CET finalCET = cet;

			clientExtensions.add(
				new ClientExtension() {
					{
						externalReferenceCode =
							finalCET.getExternalReferenceCode();
						name = finalCET.getName(
							dtoConverterContext.getLocale());
					}
				});
		}

		if (ListUtil.isEmpty(clientExtensions)) {
			return null;
		}

		return clientExtensions.toArray(new ClientExtension[0]);
	}

	private long _getFaviconFileEntryId(Layout layout) {
		long faviconFileEntryId = layout.getFaviconFileEntryId();

		if (faviconFileEntryId != 0) {
			return faviconFileEntryId;
		}

		Layout masterLayout = _layoutLocalService.fetchLayout(
			layout.getMasterLayoutPlid());

		if (masterLayout != null) {
			long masterLayoutFaviconFileEntryId =
				masterLayout.getFaviconFileEntryId();

			if (masterLayoutFaviconFileEntryId != 0) {
				return masterLayoutFaviconFileEntryId;
			}
		}

		LayoutSet layoutSet = layout.getLayoutSet();

		if (layoutSet != null) {
			long layoutSetFaviconFileEntryId =
				layoutSet.getFaviconFileEntryId();

			if (layoutSetFaviconFileEntryId != 0) {
				return layoutSetFaviconFileEntryId;
			}
		}

		return 0;
	}

	private ClientExtension _getThemeCSSClientExtension(
		long classNameId, Layout layout,
		DTOConverterContext dtoConverterContext) {

		CET cet = _getCET(
			classNameId, layout.getPlid(), layout.getCompanyId(),
			ClientExtensionEntryConstants.TYPE_THEME_CSS);

		if (cet == null) {
			cet = _getCET(
				classNameId, layout.getMasterLayoutPlid(),
				layout.getCompanyId(),
				ClientExtensionEntryConstants.TYPE_THEME_CSS);
		}

		if (cet == null) {
			LayoutSet layoutSet = layout.getLayoutSet();

			cet = _getCET(
				classNameId, layoutSet.getLayoutSetId(), layout.getCompanyId(),
				ClientExtensionEntryConstants.TYPE_THEME_CSS);
		}

		if (cet == null) {
			return null;
		}

		CET finalCET = cet;

		return new ClientExtension() {
			{
				externalReferenceCode = finalCET.getExternalReferenceCode();
				name = finalCET.getName(dtoConverterContext.getLocale());
			}
		};
	}

	private Settings _toSettings(
		DTOConverterContext dtoConverterContext, Layout layout) {

		UnicodeProperties unicodeProperties =
			layout.getTypeSettingsProperties();

		long classNameId = _portal.getClassNameId(Layout.class.getName());

		return new Settings() {
			{
				cssClientExtensions = _getClientExtensions(
					classNameId, dtoConverterContext, layout,
					ClientExtensionEntryConstants.TYPE_GLOBAL_CSS);
				javascriptClientExtensions = _getClientExtensions(
					classNameId, dtoConverterContext, layout,
					ClientExtensionEntryConstants.TYPE_GLOBAL_JS);
				themeCssClientExtension = _getThemeCSSClientExtension(
					classNameId, layout, dtoConverterContext);

				setColorSchemeName(
					() -> {
						ColorScheme colorScheme = null;

						try {
							colorScheme = layout.getColorScheme();
						}
						catch (PortalException portalException) {
							if (_log.isWarnEnabled()) {
								_log.warn(portalException);
							}
						}

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
						CET cet = _getCET(
							classNameId, layout.getPlid(),
							layout.getCompanyId(),
							ClientExtensionEntryConstants.TYPE_THEME_FAVICON);

						if (cet != null) {
							CET finalCET = cet;

							return new ClientExtension() {
								{
									externalReferenceCode =
										finalCET.getExternalReferenceCode();
									name = finalCET.getName(
										dtoConverterContext.getLocale());
								}
							};
						}

						long faviconFileEntryId = _getFaviconFileEntryId(
							layout);

						if (faviconFileEntryId != 0) {
							return ContentDocumentUtil.toContentDocument(
								_dlURLHelper, "settings.favIcon.image",
								_dlAppService.getFileEntry(faviconFileEntryId),
								dtoConverterContext.getUriInfo());
						}

						return null;
					});
				setJavascript(
					() -> {
						for (Map.Entry<String, String> entry :
								unicodeProperties.entrySet()) {

							String key = entry.getKey();

							if (key.equals("javascript")) {
								return entry.getValue();
							}
						}

						return null;
					});
				setMasterPage(
					() -> {
						LayoutPageTemplateEntry layoutPageTemplateEntry =
							_layoutPageTemplateEntryLocalService.
								fetchLayoutPageTemplateEntryByPlid(
									layout.getMasterLayoutPlid());

						if (layoutPageTemplateEntry == null) {
							return null;
						}

						return new MasterPage() {
							{
								key =
									layoutPageTemplateEntry.
										getLayoutPageTemplateEntryKey();
							}
						};
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
								key = styleBookEntry.getStyleBookEntryKey();
								name = styleBookEntry.getName();
							}
						};
					});
				setThemeName(
					() -> {
						Theme theme = layout.getTheme();

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

	private static final Log _log = LogFactoryUtil.getLog(
		PageDefinitionDTOConverter.class);

	@Reference
	private CETManager _cetManager;

	@Reference
	private ClientExtensionEntryRelLocalService
		_clientExtensionEntryRelLocalService;

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private DLURLHelper _dlURLHelper;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Reference
	private LayoutStructureItemMapperRegistry
		_layoutStructureItemMapperRegistry;

	@Reference
	private Portal _portal;

	@Reference
	private StyleBookEntryLocalService _styleBookEntryLocalService;

}