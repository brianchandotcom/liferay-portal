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

package com.liferay.portlet.asset.service.persistence.impl;

import com.liferay.asset.kernel.model.AssetCategoryProperty;
import com.liferay.asset.kernel.service.persistence.AssetCategoryPropertyPersistence;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 * @deprecated
 * @generated
 */
@Deprecated
public class AssetCategoryPropertyFinderBaseImpl
	extends BasePersistenceImpl<AssetCategoryProperty> {

	public AssetCategoryPropertyFinderBaseImpl() {
		setModelClass(AssetCategoryProperty.class);

		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("key", "key_");

		setDBColumnNames(dbColumnNames);
	}

	@Override
	public Set<String> getBadColumnNames() {
		return getAssetCategoryPropertyPersistence().getBadColumnNames();
	}

	/**
	 * Returns the asset category property persistence.
	 *
	 * @return the asset category property persistence
	 */
	public AssetCategoryPropertyPersistence
		getAssetCategoryPropertyPersistence() {

		return assetCategoryPropertyPersistence;
	}

	/**
	 * Sets the asset category property persistence.
	 *
	 * @param assetCategoryPropertyPersistence the asset category property persistence
	 */
	public void setAssetCategoryPropertyPersistence(
		AssetCategoryPropertyPersistence assetCategoryPropertyPersistence) {

		this.assetCategoryPropertyPersistence =
			assetCategoryPropertyPersistence;
	}

	@BeanReference(type = AssetCategoryPropertyPersistence.class)
	protected AssetCategoryPropertyPersistence assetCategoryPropertyPersistence;

	private static final Log _log = LogFactoryUtil.getLog(
		AssetCategoryPropertyFinderBaseImpl.class);

}