/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.type;

import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.InfoItemIdentifier;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.Locale;

/**
 * @author Jorge Ferrer
 */
public class WebImage {

	public WebImage(String url) {
		_url = url;
	}

	public WebImage(String url, InfoItemReference infoItemReference) {
		this(url);

		_infoItemReference = infoItemReference;
	}

	public String getAlt() {
		if (_altInfoLocalizedValue != null) {
			return _altInfoLocalizedValue.getValue(LocaleUtil.getDefault());
		}

		return StringPool.BLANK;
	}

	public InfoLocalizedValue<String> getAltInfoLocalizedValue() {
		return _altInfoLocalizedValue;
	}

	public InfoItemReference getInfoItemReference() {
		return _infoItemReference;
	}

	public String getURL() {
		return _url;
	}

	public WebImage setAlt(String alt) {
		_altInfoLocalizedValue = InfoLocalizedValue.singleValue(alt);

		return this;
	}

	public WebImage setAltInfoLocalizedValue(
		InfoLocalizedValue<String> altInfoLocalizedValue) {

		_altInfoLocalizedValue = altInfoLocalizedValue;

		return this;
	}

	public JSONObject toJSONObject() {
		return toJSONObject(LocaleUtil.getDefault());
	}

	public JSONObject toJSONObject(Locale locale) {
		JSONObject jsonObject = JSONUtil.put("url", _url);

		if (_altInfoLocalizedValue != null) {
			jsonObject.put("alt", _altInfoLocalizedValue.getValue(locale));
		}

		if (_infoItemReference != null) {
			jsonObject.put("className", _infoItemReference.getClassName());

			InfoItemIdentifier infoItemIdentifier =
				_infoItemReference.getInfoItemIdentifier();

			if (infoItemIdentifier instanceof ClassPKInfoItemIdentifier) {
				ClassPKInfoItemIdentifier classPKInfoItemIdentifier =
					(ClassPKInfoItemIdentifier)infoItemIdentifier;

				jsonObject.put(
					"classPK", classPKInfoItemIdentifier.getClassPK());
			}
		}

		return jsonObject;
	}

	@Override
	public String toString() {
		return getURL();
	}

	private InfoLocalizedValue<String> _altInfoLocalizedValue;
	private InfoItemReference _infoItemReference;
	private final String _url;

}