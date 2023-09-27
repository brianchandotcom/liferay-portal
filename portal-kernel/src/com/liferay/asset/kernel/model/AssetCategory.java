/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.kernel.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.model.TreeModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the AssetCategory service. Represents a row in the &quot;AssetCategory&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see AssetCategoryModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.portlet.asset.model.impl.AssetCategoryImpl"
)
@ProviderType
public interface AssetCategory
	extends AssetCategoryModel, PersistedModel, TreeModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portlet.asset.model.impl.AssetCategoryImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<AssetCategory, String> UUID_ACCESSOR =
		new Accessor<AssetCategory, String>() {

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

	public static final Accessor<AssetCategory, Long> CATEGORY_ID_ACCESSOR =
		new Accessor<AssetCategory, Long>() {

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
	public static final Accessor<AssetCategory, String> NAME_ACCESSOR =
		new Accessor<AssetCategory, String>() {

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

	public String getPath(java.util.Locale locale)
		throws com.liferay.portal.kernel.exception.PortalException;

	public String getPath(java.util.Locale locale, boolean reverse)
		throws com.liferay.portal.kernel.exception.PortalException;

	public boolean isRootCategory();

}