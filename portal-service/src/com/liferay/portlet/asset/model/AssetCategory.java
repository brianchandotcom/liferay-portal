/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.asset.model;

import com.liferay.portal.kernel.util.Accessor;
import com.liferay.portal.model.PersistedModel;

/**
 * The extended model interface for the AssetCategory service. Represents a row in the &quot;AssetCategory&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see AssetCategoryModel
 * @see com.liferay.portlet.asset.model.impl.AssetCategoryImpl
 * @see com.liferay.portlet.asset.model.impl.AssetCategoryModelImpl
 * @generated
 */
public interface AssetCategory extends AssetCategoryModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.portlet.asset.model.impl.AssetCategoryImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<AssetCategory, String> UUID_ACCESSOR = new Accessor<AssetCategory, String>() {
			public String get(AssetCategory assetCategory) {
				return assetCategory.getUuid();
			}
		};

	public static final Accessor<AssetCategory, Long> CATEGORY_ID_ACCESSOR = new Accessor<AssetCategory, Long>() {
			public Long get(AssetCategory assetCategory) {
				return assetCategory.getCategoryId();
			}
		};

	public static final Accessor<AssetCategory, String> NAME_ACCESSOR = new Accessor<AssetCategory, String>() {
			public String get(AssetCategory assetCategory) {
				return assetCategory.getName();
			}
		};

	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> getAncestors()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public java.lang.String getTitle(java.lang.String languageId);

	public java.lang.String getTitle(java.lang.String languageId,
		boolean useDefault);

	public boolean isRootCategory();
}