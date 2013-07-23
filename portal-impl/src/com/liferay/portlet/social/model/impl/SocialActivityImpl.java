/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.social.model.impl;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;

import java.util.Locale;

/**
 * @author Brian Wing Shun Chan
 * @author Zsolt Berentey
 */
public class SocialActivityImpl extends SocialActivityBaseImpl {

	@Override
	public AssetEntry getAssetEntry() throws SystemException {
		if ((_assetEntry == null) && Validator.isNotNull(getClassName()) &&
			(getClassPK() > 0)) {

			_assetEntry = AssetEntryLocalServiceUtil.fetchEntry(
				getClassName(), getClassPK());
		}

		return _assetEntry;
	}

	@Override
	public String getExtraDataValue(String key) throws JSONException {
		JSONObject extraDataJSONObject = _getExtraDataJSONObject();

		return extraDataJSONObject.getString(key);
	}

	public String getExtraDataValue(String key, Locale locale)
		throws JSONException {

		JSONObject extraDataJSONObject = _getExtraDataJSONObject();

		return LocalizationUtil.getLocalization(
			extraDataJSONObject.getString(key),
			LocaleUtil.toLanguageId(locale));
	}

	@Override
	public boolean isClassName(String className) {
		if (className == null) {
			return false;
		}

		return className.equals(getClassName());
	}

	@Override
	public void setAssetEntry(AssetEntry assetEntry) {
		_assetEntry = assetEntry;
	}

	@Override
	public void setExtraData(String extraData) {
		_extraDataJSONObject = null;

		super.setExtraData(extraData);
	}

	public void setExtraDataValue(String key, String value)
		throws JSONException {

		JSONObject extraDataJSONObject = _getExtraDataJSONObject();

		extraDataJSONObject.put(key, value);

		super.setExtraData(extraDataJSONObject.toString());
	}

	private JSONObject _getExtraDataJSONObject() throws JSONException {
		if (_extraDataJSONObject != null) {
			return _extraDataJSONObject;
		}

		if (Validator.isNull(getExtraData())) {
			_extraDataJSONObject = JSONFactoryUtil.createJSONObject();
		}
		else {
			_extraDataJSONObject = JSONFactoryUtil.createJSONObject(
				getExtraData());
		}

		return _extraDataJSONObject;
	}

	private AssetEntry _assetEntry;
	private JSONObject _extraDataJSONObject;

}