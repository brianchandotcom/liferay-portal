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

package com.liferay.asset.display.page.model.impl;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryServiceUtil;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;

import java.util.Locale;
import java.util.Objects;

/**
 * The extended model implementation for the AssetDisplayPageEntry service.
 * Represents a row in the &quot;AssetDisplayPageEntry&quot; database table,
 * with each column mapped to a property of this class.
 *
 * <p>
 * Helper methods and all application logic should be put in this class.
 * Whenever methods are added, rerun ServiceBuilder to copy their definitions
 * into the {@link com.liferay.asset.display.page.model.AssetDisplayPageEntry}
 * interface.
 * </p>
 *
 * @author Brian Wing Shun Chan
 */
public class AssetDisplayPageEntryImpl extends AssetDisplayPageEntryBaseImpl {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. All methods that expect a asset
	 * display page entry model instance should use the {@link
	 * com.liferay.asset.display.page.model.AssetDisplayPageEntry} interface
	 * instead.
	 */
	public AssetDisplayPageEntryImpl() {
	}

	public String getTitle(Locale locale) throws PortalException {
		String className = getClassName();

		if (Objects.equals(className, FileEntry.class.getName())) {
			className = DLFileEntry.class.getName();
		}

		AssetEntry assetEntry = AssetEntryServiceUtil.getEntry(
			className, getClassPK());

		return assetEntry.getTitle(locale);
	}

}