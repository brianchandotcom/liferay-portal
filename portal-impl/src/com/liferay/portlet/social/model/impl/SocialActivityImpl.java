/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.social.model.impl;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;

/**
 * @author Brian Wing Shun Chan
 * @author Zsolt Berentey
 */
public class SocialActivityImpl extends SocialActivityBaseImpl {

	@Override
	public AssetEntry getAssetEntry() {
		if ((_assetEntry == null) && Validator.isNotNull(getClassName()) &&
			(getClassPK() > 0)) {

			_assetEntry = AssetEntryLocalServiceUtil.fetchEntry(
				getClassName(), getClassPK());
		}

		return _assetEntry;
	}

	@Override
	public String getExtraDataValue(String key) throws JSONException {
		JSONObject extraDataJSONObject = getExtraDataJSONObject();

		return extraDataJSONObject.getString(key);
	}

	@Override
	public String getExtraDataValue(String key, Locale locale)
		throws JSONException {

		JSONObject extraDataJSONObject = getExtraDataJSONObject();

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

	@Override
	public void setExtraDataValue(String key, String value)
		throws JSONException {

		JSONObject extraDataJSONObject = getExtraDataJSONObject();

		extraDataJSONObject.put(key, value);

		super.setExtraData(extraDataJSONObject.toString());
	}

	protected JSONObject getExtraDataJSONObject() throws JSONException {
		if (_extraDataJSONObject != null) {
			return _extraDataJSONObject;
		}

		_extraDataJSONObject = JSONFactoryUtil.createJSONObject(getExtraData());

		return _extraDataJSONObject;
	}

	private AssetEntry _assetEntry;
	private JSONObject _extraDataJSONObject;

}