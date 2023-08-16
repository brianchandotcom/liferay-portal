/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ColorScheme;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
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
		long classNameId, DTOConverterContext dtoConverterContext,
		Layout layout, String type) {

		ClientExtension[] clientExtensions = TransformUtil.transformToArray(
			_clientExtensionEntryRelLocalService.getClientExtensionEntryRels(
				classNameId, layout.getPlid(), type),
			clientExtensionEntryRel -> {
				CET cet = _cetManager.getCET(
					layout.getCompanyId(),
					clientExtensionEntryRel.getCETExternalReferenceCode());

				if (cet == null) {
					return null;
				}

				return new ClientExtension() {
					{
						clientExtensionConfig = _getClientExtensionConfig(
							clientExtensionEntryRel);
						externalReferenceCode = cet.getExternalReferenceCode();
						name = cet.getName(dtoConverterContext.getLocale());
					}
				};
			},
			ClientExtension.class);

		if (ArrayUtil.isEmpty(clientExtensions)) {
			return null;
		}

		return clientExtensions;
	}

	private ClientExtension _getThemeCSSClientExtension(
		long classNameId, Layout layout,
		DTOConverterContext dtoConverterContext) {

		CET cet = _getCET(
			classNameId, layout.getPlid(), layout.getCompanyId(),
			ClientExtensionEntryConstants.TYPE_THEME_CSS);

		if (cet == null) {
			return null;
		}

		return new ClientExtension() {
			{
				externalReferenceCode = cet.getExternalReferenceCode();
				name = cet.getName(dtoConverterContext.getLocale());
			}
		};
	}

	private Settings _toSettings(
		DTOConverterContext dtoConverterContext, Layout layout) {

		long classNameId = _portal.getClassNameId(Layout.class.getName());
		UnicodeProperties unicodeProperties =
			layout.getTypeSettingsProperties();

		return new Settings() {
			{
				globalCSSClientExtensions = _getClientExtensions(
					classNameId, dtoConverterContext, layout,
					ClientExtensionEntryConstants.TYPE_GLOBAL_CSS);
				globalJSClientExtensions = _getClientExtensions(
					classNameId, dtoConverterContext, layout,
					ClientExtensionEntryConstants.TYPE_GLOBAL_JS);
				themeCSSClientExtension = _getThemeCSSClientExtension(
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
							return new ClientExtension() {
								{
									externalReferenceCode =
										cet.getExternalReferenceCode();
									name = cet.getName(
										dtoConverterContext.getLocale());
								}
							};
						}

						long faviconFileEntryId =
							layout.getFaviconFileEntryId();

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