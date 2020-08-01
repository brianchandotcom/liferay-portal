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

package com.liferay.asset.info.internal.item.provider;

import com.liferay.asset.kernel.exception.NoSuchEntryException;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.info.exception.NoSuchInfoItemException;
import com.liferay.info.item.InfoItemIdentifier;
import com.liferay.info.item.provider.InfoItemObjectProvider;
import com.liferay.portal.kernel.exception.PortalException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jorge Ferrer
 */
@Component(service = InfoItemObjectProvider.class)
public class AssetEntryInfoItemObjectProvider
	implements InfoItemObjectProvider<AssetEntry> {

	@Override
	public AssetEntry getInfoItem(InfoItemIdentifier infoItemIdentifier)
		throws NoSuchInfoItemException {

		try {
			return _assetEntryLocalService.getEntry(
				infoItemIdentifier.getClassPK());
		}
		catch (NoSuchEntryException noSuchEntryException) {
			throw new NoSuchInfoItemException(
				"Unable to get asset entry " + infoItemIdentifier.getClassPK(),
				noSuchEntryException);
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	@Override
	public AssetEntry getInfoItem(long classPK) throws NoSuchInfoItemException {
		InfoItemIdentifier infoItemIdentifier = new InfoItemIdentifier(classPK);

		return getInfoItem(infoItemIdentifier);
	}

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

}