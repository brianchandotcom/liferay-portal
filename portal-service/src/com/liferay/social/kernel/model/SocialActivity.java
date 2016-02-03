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

package com.liferay.social.kernel.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.util.Accessor;
import com.liferay.portal.model.PersistedModel;

/**
 * The extended model interface for the SocialActivity service. Represents a row in the &quot;SocialActivity&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see SocialActivityModel
 * @see com.liferay.portlet.social.model.impl.SocialActivityImpl
 * @see com.liferay.portlet.social.model.impl.SocialActivityModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.portlet.social.model.impl.SocialActivityImpl")
@ProviderType
public interface SocialActivity extends SocialActivityModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.portlet.social.model.impl.SocialActivityImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<SocialActivity, Long> ACTIVITY_ID_ACCESSOR = new Accessor<SocialActivity, Long>() {
			@Override
			public Long get(SocialActivity socialActivity) {
				return socialActivity.getActivityId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<SocialActivity> getTypeClass() {
				return SocialActivity.class;
			}
		};

	public com.liferay.portlet.asset.model.AssetEntry getAssetEntry();

	public java.lang.String getExtraDataValue(java.lang.String key)
		throws com.liferay.portal.kernel.json.JSONException;

	public java.lang.String getExtraDataValue(java.lang.String key,
		java.util.Locale locale)
		throws com.liferay.portal.kernel.json.JSONException;

	public boolean isClassName(java.lang.String className);

	public void setAssetEntry(
		com.liferay.portlet.asset.model.AssetEntry assetEntry);

	public void setExtraDataValue(java.lang.String key, java.lang.String value)
		throws com.liferay.portal.kernel.json.JSONException;
}