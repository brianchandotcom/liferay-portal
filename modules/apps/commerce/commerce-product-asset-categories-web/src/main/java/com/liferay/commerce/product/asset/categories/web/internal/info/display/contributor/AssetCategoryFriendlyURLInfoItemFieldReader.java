/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.asset.categories.web.internal.info.display.contributor;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.commerce.product.url.CPFriendlyURL;
import com.liferay.friendly.url.model.FriendlyURLEntry;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalService;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.type.URLInfoFieldType;
import com.liferay.info.item.field.reader.InfoItemFieldReader;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(service = InfoItemFieldReader.class)
public class AssetCategoryFriendlyURLInfoItemFieldReader
	implements InfoItemFieldReader<AssetCategory> {

	@Override
	public InfoField getInfoField() {
		return InfoField.builder(
		).infoFieldType(
			URLInfoFieldType.INSTANCE
		).namespace(
			AssetCategory.class.getSimpleName()
		).name(
			"friendlyURL"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(getClass(), "friendly-url")
		).build();
	}

	@Override
	public Object getValue(AssetCategory assetCategory) {
		try {
			FriendlyURLEntry friendlyURLEntry =
				_friendlyURLEntryLocalService.getMainFriendlyURLEntry(
					_portal.getClassNameId(AssetCategory.class),
					assetCategory.getCategoryId());

			if (friendlyURLEntry == null) {
				return null;
			}

			ThemeDisplay themeDisplay = _getThemeDisplay();

			if (themeDisplay == null) {
				return null;
			}

			String groupFriendlyURL = _portal.getGroupFriendlyURL(
				themeDisplay.getLayoutSet(), themeDisplay, false, false);

			return InfoLocalizedValue.function(
				locale -> {
					String assetCategoryURLSeparator =
						_cpFriendlyURL.getAssetCategoryURLSeparator(
							themeDisplay.getCompanyId());
					String languageId = LocaleUtil.toLanguageId(locale);

					return groupFriendlyURL + assetCategoryURLSeparator +
						friendlyURLEntry.getUrlTitle(languageId);
				});
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}
		}

		return null;
	}

	private ThemeDisplay _getThemeDisplay() {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext != null) {
			return serviceContext.getThemeDisplay();
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetCategoryFriendlyURLInfoItemFieldReader.class);

	@Reference
	private CPFriendlyURL _cpFriendlyURL;

	@Reference
	private FriendlyURLEntryLocalService _friendlyURLEntryLocalService;

	@Reference
	private Portal _portal;

}