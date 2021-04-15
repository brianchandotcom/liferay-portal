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

package com.liferay.portlet.asset.service.impl;

import com.liferay.asset.kernel.model.AssetCategoryProperty;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portlet.asset.service.base.AssetCategoryPropertyLocalServiceBaseImpl;

import java.util.List;

/**
 * @author     Brian Wing Shun Chan
 * @author     Jorge Ferrer
 * @deprecated As of Judson (7.1.x), replaced by {@link
 *             com.liferay.asset.category.property.service.impl.AssetCategoryPropertyLocalServiceImpl}
 */
@Deprecated
public class AssetCategoryPropertyLocalServiceImpl
	extends AssetCategoryPropertyLocalServiceBaseImpl {

	@Override
	public AssetCategoryProperty addCategoryProperty(
			long userId, long categoryId, String key, String value)
		throws PortalException {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.asset.category.property.service.impl." +
					"AssetCategoryPropertyLocalServiceImpl");
	}

	@Override
	public void deleteCategoryProperties(long entryId) {
		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.asset.category.property.service.impl." +
					"AssetCategoryPropertyLocalServiceImpl");
	}

	@Override
	public void deleteCategoryProperty(AssetCategoryProperty categoryProperty) {
		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.asset.category.property.service.impl." +
					"AssetCategoryPropertyLocalServiceImpl");
	}

	@Override
	public void deleteCategoryProperty(long categoryPropertyId)
		throws PortalException {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.asset.category.property.service.impl." +
					"AssetCategoryPropertyLocalServiceImpl");
	}

	@Override
	public List<AssetCategoryProperty> getCategoryProperties() {
		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.asset.category.property.service.impl." +
					"AssetCategoryPropertyLocalServiceImpl");
	}

	@Override
	public List<AssetCategoryProperty> getCategoryProperties(long entryId) {
		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.asset.category.property.service.impl." +
					"AssetCategoryPropertyLocalServiceImpl");
	}

	@Override
	public AssetCategoryProperty getCategoryProperty(long categoryPropertyId)
		throws PortalException {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.asset.category.property.service.impl." +
					"AssetCategoryPropertyLocalServiceImpl");
	}

	@Override
	public AssetCategoryProperty getCategoryProperty(
			long categoryId, String key)
		throws PortalException {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.asset.category.property.service.impl." +
					"AssetCategoryPropertyLocalServiceImpl");
	}

	@Override
	public List<AssetCategoryProperty> getCategoryPropertyValues(
		long groupId, String key) {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.asset.category.property.service.impl." +
					"AssetCategoryPropertyLocalServiceImpl");
	}

	@Override
	public AssetCategoryProperty updateCategoryProperty(
			long userId, long categoryPropertyId, String key, String value)
		throws PortalException {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.asset.category.property.service.impl." +
					"AssetCategoryPropertyLocalServiceImpl");
	}

	@Override
	public AssetCategoryProperty updateCategoryProperty(
			long categoryPropertyId, String key, String value)
		throws PortalException {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.asset.category.property.service.impl." +
					"AssetCategoryPropertyLocalServiceImpl");
	}

	protected boolean hasCategoryProperty(long categoryId, String key) {
		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.asset.category.property.service.impl." +
					"AssetCategoryPropertyLocalServiceImpl");
	}

	protected void validate(String key, String value) throws PortalException {
		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.asset.category.property.service.impl." +
					"AssetCategoryPropertyLocalServiceImpl");
	}

}