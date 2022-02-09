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

package com.liferay.asset.info.display.internal.field;

import com.liferay.asset.info.display.field.AssetEntryInfoDisplayFieldProvider;
import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.info.display.contributor.field.InfoDisplayContributorField;
import com.liferay.info.display.contributor.field.InfoDisplayContributorFieldTracker;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(immediate = true, service = AssetEntryInfoDisplayFieldProvider.class)
public class AssetEntryInfoDisplayFieldProviderImpl
	implements AssetEntryInfoDisplayFieldProvider {

	@Override
	public Map<String, Object> getAssetEntryInfoDisplayFieldsValues(
			String className, long classPK, Locale locale)
		throws PortalException {

		Map<String, Object> infoDisplayFieldsValues = new HashMap<>();

		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				className);

		try {
			AssetEntry assetEntry = assetRendererFactory.getAssetEntry(
				className, classPK);

			List<InfoDisplayContributorField<?>> infoDisplayContributorFields =
				_infoDisplayContributorFieldTracker.
					getInfoDisplayContributorFields(AssetEntry.class.getName());

			for (InfoDisplayContributorField<?> infoDisplayContributorField :
					infoDisplayContributorFields) {

				InfoDisplayContributorField<AssetEntry>
					assetEntryInfoDisplayContributorField =
						(InfoDisplayContributorField<AssetEntry>)
							infoDisplayContributorField;

				Object fieldValue =
					assetEntryInfoDisplayContributorField.getValue(
						assetEntry, locale);

				infoDisplayFieldsValues.putIfAbsent(
					assetEntryInfoDisplayContributorField.getKey(), fieldValue);
			}
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}
		}

		return infoDisplayFieldsValues;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetEntryInfoDisplayFieldProviderImpl.class);

	@Reference
	private InfoDisplayContributorFieldTracker
		_infoDisplayContributorFieldTracker;

}