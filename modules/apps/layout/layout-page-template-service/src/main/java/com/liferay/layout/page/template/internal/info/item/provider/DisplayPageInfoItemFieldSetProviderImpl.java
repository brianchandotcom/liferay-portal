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
import com.liferay.info.item.ERCInfoItemIdentifier;
import com.liferay.info.item.InfoItemIdentifier;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.info.localized.bundle.FunctionInfoLocalizedValue;
import com.liferay.info.type.WebURL;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.info.item.provider.DisplayPageInfoItemFieldSetProvider;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

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
		String itemClassName, String infoItemFormVariationKey, String namespace,
		long scopeGroupId) {

		return InfoFieldSet.builder(
		).infoFieldSetEntries(
			_getInfoFieldSetEntries(
				itemClassName, infoItemFormVariationKey, namespace,
				scopeGroupId)
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(getClass(), "display-page")
		).name(
			"display-page"
		).build();
	}

	@Override
	public List<InfoFieldValue<Object>> getInfoFieldValues(
			InfoItemReference infoItemReference,
			String infoItemFormVariationKey, String namespace,
			ThemeDisplay themeDisplay)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPS-195205") ||
			(themeDisplay == null)) {

			return Collections.emptyList();
		}

		List<InfoFieldValue<Object>> infoFieldValues = new ArrayList<>();

		infoFieldValues.add(
			new InfoFieldValue<>(
				InfoField.builder(
					namespace
				).infoFieldType(
					URLInfoFieldType.INSTANCE
				).name(
					"displayPageURL"
				).labelInfoLocalizedValue(
					InfoLocalizedValue.localize(getClass(), "default")
				).build(),
				_getDefaultDisplayPageURL(infoItemReference, themeDisplay)));

		Group group = themeDisplay.getScopeGroup();

		String groupFriendlyURL = _portal.getGroupFriendlyURL(
			group.getPublicLayoutSet(), themeDisplay, false, false);

		String url = groupFriendlyURL + "/e";

		List<LayoutPageTemplateEntry> layoutPageTemplateEntries =
			_layoutPageTemplateEntryService.getLayoutPageTemplateEntries(
				themeDisplay.getScopeGroupId(),
				_portal.getClassNameId(infoItemReference.getClassName()),
				GetterUtil.getLong(infoItemFormVariationKey),
				LayoutPageTemplateEntryTypeConstants.TYPE_DISPLAY_PAGE);

		for (LayoutPageTemplateEntry layoutPageTemplateEntry :
				layoutPageTemplateEntries) {

			Layout layout = _layoutLocalService.fetchLayout(
				layoutPageTemplateEntry.getPlid());

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
					).attribute(
						URLInfoFieldType.NOFOLLOW, Boolean.TRUE
					).labelInfoLocalizedValue(
						InfoLocalizedValue.singleValue(
							layoutPageTemplateEntry.getName())
					).build(),
					new FunctionInfoLocalizedValue<>(
						locale -> {
							WebURL webURL = new WebURL(
								StringBundler.concat(
									url, layout.getFriendlyURL(locale),
									StringPool.SLASH,
									_portal.getClassNameId(
										infoItemReference.getClassName()),
									StringPool.SLASH,
									_getInfoItemIdentifier(infoItemReference)));

							webURL.setNofollow(true);

							return webURL;
						})));
		}

		return infoFieldValues;
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
			AssetRenderer<?> assetRenderer = null;

			if (infoItemReference.getInfoItemIdentifier() instanceof
					ClassPKInfoItemIdentifier) {

				ClassPKInfoItemIdentifier classPKInfoItemIdentifier =
					(ClassPKInfoItemIdentifier)
						infoItemReference.getInfoItemIdentifier();

				assetRenderer = assetRendererFactory.getAssetRenderer(
					classPKInfoItemIdentifier.getClassPK());
			}

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
		String namespace) {

		return InfoField.builder(
			namespace
		).infoFieldType(
			_getDisplayPageInfoFieldType()
		).name(
			"displayPageURL"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(getClass(), "default")
		).build();
	}

	private InfoFieldType _getDisplayPageInfoFieldType() {
		return DisplayPageInfoFieldType.INSTANCE;
	}

	private List<InfoFieldSetEntry> _getInfoFieldSetEntries(
		String itemClassName, String infoItemFormVariationKey, String namespace,
		long scopeGroupId) {

		List<InfoFieldSetEntry> infoFieldSetEntries = new ArrayList<>();

		infoFieldSetEntries.add(_getDefaultDisplayPageURLInfoField(namespace));

		List<LayoutPageTemplateEntry> layoutPageTemplateEntries =
			_layoutPageTemplateEntryService.getLayoutPageTemplateEntries(
				scopeGroupId, _portal.getClassNameId(itemClassName),
				GetterUtil.getLong(infoItemFormVariationKey),
				LayoutPageTemplateEntryTypeConstants.TYPE_DISPLAY_PAGE,
				WorkflowConstants.STATUS_APPROVED);

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

	private String _getInfoItemIdentifier(InfoItemReference infoItemReference) {
		InfoItemIdentifier infoItemIdentifier =
			infoItemReference.getInfoItemIdentifier();

		if (infoItemIdentifier instanceof ClassPKInfoItemIdentifier) {
			ClassPKInfoItemIdentifier classPKInfoItemIdentifier =
				(ClassPKInfoItemIdentifier)infoItemIdentifier;

			return String.valueOf(classPKInfoItemIdentifier.getClassPK());
		}

		if (infoItemIdentifier instanceof ERCInfoItemIdentifier) {
			ERCInfoItemIdentifier ercInfoItemIdentifier =
				(ERCInfoItemIdentifier)infoItemIdentifier;

			return ercInfoItemIdentifier.getExternalReferenceCode();
		}

		return StringPool.BLANK;
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
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPageTemplateEntryService _layoutPageTemplateEntryService;

	@Reference
	private Portal _portal;

}