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

package com.liferay.asset.display.template.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the AssetDisplayTemplate service. Represents a row in the &quot;AssetDisplayTemplate&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see AssetDisplayTemplateModel
 * @see com.liferay.asset.display.template.model.impl.AssetDisplayTemplateImpl
 * @see com.liferay.asset.display.template.model.impl.AssetDisplayTemplateModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.asset.display.template.model.impl.AssetDisplayTemplateImpl")
@ProviderType
public interface AssetDisplayTemplate extends AssetDisplayTemplateModel,
	PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.asset.display.template.model.impl.AssetDisplayTemplateImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<AssetDisplayTemplate, Long> ASSET_DISPLAY_TEMPLATE_ID_ACCESSOR =
		new Accessor<AssetDisplayTemplate, Long>() {
			@Override
			public Long get(AssetDisplayTemplate assetDisplayTemplate) {
				return assetDisplayTemplate.getAssetDisplayTemplateId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<AssetDisplayTemplate> getTypeClass() {
				return AssetDisplayTemplate.class;
			}
		};

	public String getAssetTypeName(java.util.Locale locale)
		throws com.liferay.portal.kernel.exception.PortalException;
}