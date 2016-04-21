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

package com.liferay.asset.kernel.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.NestedSetsTreeNodeModel;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the AssetCategory service. Represents a row in the &quot;AssetCategory&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see AssetCategoryModel
 * @see com.liferay.portlet.asset.model.impl.AssetCategoryImpl
 * @see com.liferay.portlet.asset.model.impl.AssetCategoryModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.portlet.asset.model.impl.AssetCategoryImpl")
@ProviderType
public interface AssetCategory extends AssetCategoryModel,
	NestedSetsTreeNodeModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.portlet.asset.model.impl.AssetCategoryImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<AssetCategory, String> UUID_ACCESSOR = new Accessor<AssetCategory, String>() {
			@Override
			public String get(AssetCategory assetCategory) {
				return assetCategory.getUuid();
			}

			@Override
			public Class<String> getAttributeClass() {
				return String.class;
			}

			@Override
			public Class<AssetCategory> getTypeClass() {
				return AssetCategory.class;
			}
		};

	public static final Accessor<AssetCategory, Long> CATEGORY_ID_ACCESSOR = new Accessor<AssetCategory, Long>() {
			@Override
			public Long get(AssetCategory assetCategory) {
				return assetCategory.getCategoryId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<AssetCategory> getTypeClass() {
				return AssetCategory.class;
			}
		};

	public static final Accessor<AssetCategory, String> NAME_ACCESSOR = new Accessor<AssetCategory, String>() {
			@Override
			public String get(AssetCategory assetCategory) {
				return assetCategory.getName();
			}

			@Override
			public Class<String> getAttributeClass() {
				return String.class;
			}

			@Override
			public Class<AssetCategory> getTypeClass() {
				return AssetCategory.class;
			}
		};

	public java.util.List<AssetCategory> getAncestors()
		throws com.liferay.portal.kernel.exception.PortalException;

	public AssetCategory getParentCategory();

	public java.lang.String getPath(java.util.Locale locale)
		throws com.liferay.portal.kernel.exception.PortalException;

	public boolean isRootCategory();
}