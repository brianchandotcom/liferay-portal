/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.internal.info.item.provider;

import com.liferay.asset.display.page.portlet.AssetDisplayPageFriendlyURLProvider;
import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldSet;
import com.liferay.info.field.InfoFieldSetEntry;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.field.type.DisplayPageInfoFieldType;
import com.liferay.info.field.type.InfoFieldType;
import com.liferay.info.field.type.URLInfoFieldType;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.info.item.provider.DisplayPageInfoItemFieldSetProvider;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(service = DisplayPageInfoItemFieldSetProvider.class)
public class DisplayPageInfoItemFieldSetProviderImpl
	implements DisplayPageInfoItemFieldSetProvider {

	@Override
	public InfoFieldSet getInfoFieldSet(
		String itemClassName, String infoItemFormVariationKey,
		long scopeGroupId) {

		return InfoFieldSet.builder(
		).infoFieldSetEntries(
			_getInfoFieldSetEntries(
				itemClassName, infoItemFormVariationKey, scopeGroupId)
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(getClass(), "display-page")
		).name(
			"display-page"
		).build();
	}

	@Override
	public List<InfoFieldValue<Object>> getInfoFieldValues(
			InfoItemReference infoItemReference,
			String infoItemFormVariationKey, ThemeDisplay themeDisplay)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPS-183727") ||
			(themeDisplay == null)) {

			return Collections.emptyList();
		}

		List<InfoFieldValue<Object>> infoFieldValues = new ArrayList<>();

		infoFieldValues.add(
			new InfoFieldValue<>(
				InfoField.builder(
					infoItemReference.getClassName()
				).infoFieldType(
					URLInfoFieldType.INSTANCE
				).name(
					"displayPageURL"
				).labelInfoLocalizedValue(
					InfoLocalizedValue.localize(getClass(), "default")
				).build(),
				_getDefaultDisplayPageURL(infoItemReference, themeDisplay)));

		List<LayoutPageTemplateEntry> layoutPageTemplateEntries =
			_layoutPageTemplateEntryService.getLayoutPageTemplateEntries(
				themeDisplay.getScopeGroupId(),
				_portal.getClassNameId(infoItemReference.getClassName()),
				GetterUtil.getLong(infoItemFormVariationKey),
				LayoutPageTemplateEntryTypeConstants.TYPE_DISPLAY_PAGE);

		for (LayoutPageTemplateEntry layoutPageTemplateEntry :
				layoutPageTemplateEntries) {

			infoFieldValues.add(
				new InfoFieldValue<>(
					InfoField.builder(
					).infoFieldType(
						URLInfoFieldType.INSTANCE
					).uniqueId(
						_getUniqueId(
							layoutPageTemplateEntry.
								getLayoutPageTemplateEntryKey())
					).name(
						layoutPageTemplateEntry.getName()
					).labelInfoLocalizedValue(
						InfoLocalizedValue.singleValue(
							layoutPageTemplateEntry.getName())
					).build(),
					HttpComponentsUtil.addParameters(
						themeDisplay.getPortalURL() + _portal.getPathContext() +
							"/display-page/custom/",
						"className", infoItemReference.getClassName(),
						"classPK", _getClassPK(infoItemReference), "selPlid",
						layoutPageTemplateEntry.getPlid())));
		}

		return infoFieldValues;
	}

	private long _getClassPK(InfoItemReference infoItemReference) {
		if (infoItemReference.getInfoItemIdentifier() instanceof
				ClassPKInfoItemIdentifier) {

			ClassPKInfoItemIdentifier classPKInfoItemIdentifier =
				(ClassPKInfoItemIdentifier)
					infoItemReference.getInfoItemIdentifier();

			return classPKInfoItemIdentifier.getClassPK();
		}

		return 0;
	}

	private String _getDefaultDisplayPageURL(
			InfoItemReference infoItemReference, ThemeDisplay themeDisplay)
		throws Exception {

		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				infoItemReference.getClassName());

		if (assetRendererFactory == null) {
			return _assetDisplayPageFriendlyURLProvider.getFriendlyURL(
				infoItemReference, themeDisplay);
		}

		try {
			AssetRenderer<?> assetRenderer =
				assetRendererFactory.getAssetRenderer(
					_getClassPK(infoItemReference));

			if (assetRenderer == null) {
				return _assetDisplayPageFriendlyURLProvider.getFriendlyURL(
					infoItemReference, themeDisplay);
			}

			String viewInContextURL = assetRenderer.getURLViewInContext(
				themeDisplay, StringPool.BLANK);

			if (Validator.isNotNull(viewInContextURL)) {
				return viewInContextURL;
			}
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return _assetDisplayPageFriendlyURLProvider.getFriendlyURL(
			infoItemReference, themeDisplay);
	}

	private InfoField<InfoFieldType> _getDefaultDisplayPageURLInfoField(
		String className) {

		return InfoField.builder(
			className
		).infoFieldType(
			_getDisplayPageInfoFieldType()
		).name(
			"displayPageURL"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(getClass(), "default")
		).build();
	}

	private InfoFieldType _getDisplayPageInfoFieldType() {
		if (FeatureFlagManagerUtil.isEnabled("LPS-183498")) {
			return DisplayPageInfoFieldType.INSTANCE;
		}

		return URLInfoFieldType.INSTANCE;
	}

	private List<InfoFieldSetEntry> _getInfoFieldSetEntries(
		String itemClassName, String infoItemFormVariationKey,
		long scopeGroupId) {

		List<InfoFieldSetEntry> infoFieldSetEntries = new ArrayList<>();

		infoFieldSetEntries.add(
			_getDefaultDisplayPageURLInfoField(itemClassName));

		List<LayoutPageTemplateEntry> layoutPageTemplateEntries =
			_layoutPageTemplateEntryService.getLayoutPageTemplateEntries(
				scopeGroupId, _portal.getClassNameId(itemClassName),
				GetterUtil.getLong(infoItemFormVariationKey),
				LayoutPageTemplateEntryTypeConstants.TYPE_DISPLAY_PAGE);

		for (LayoutPageTemplateEntry layoutPageTemplateEntry :
				layoutPageTemplateEntries) {

			infoFieldSetEntries.add(
				InfoField.builder(
				).infoFieldType(
					_getDisplayPageInfoFieldType()
				).uniqueId(
					_getUniqueId(
						layoutPageTemplateEntry.getLayoutPageTemplateEntryKey())
				).name(
					layoutPageTemplateEntry.getName()
				).labelInfoLocalizedValue(
					InfoLocalizedValue.singleValue(
						layoutPageTemplateEntry.getName())
				).build());
		}

		return infoFieldSetEntries;
	}

	private String _getUniqueId(String layoutPageTemplateEntryKey) {
		return LayoutPageTemplateEntry.class.getSimpleName() +
			StringPool.UNDERLINE + layoutPageTemplateEntryKey;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DisplayPageInfoItemFieldSetProviderImpl.class);

	@Reference
	private AssetDisplayPageFriendlyURLProvider
		_assetDisplayPageFriendlyURLProvider;

	@Reference
	private LayoutPageTemplateEntryService _layoutPageTemplateEntryService;

	@Reference
	private Portal _portal;

}