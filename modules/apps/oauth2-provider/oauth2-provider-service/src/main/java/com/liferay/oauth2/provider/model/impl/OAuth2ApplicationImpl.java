/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth2.provider.model.impl;

import com.liferay.oauth2.provider.constants.GrantType;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class OAuth2ApplicationImpl extends OAuth2ApplicationBaseImpl {

	@Override
	public List<GrantType> getAllowedGrantTypesList() {
		return TransformUtil.transform(
			StringUtil.split(getAllowedGrantTypes()), GrantType::valueOf);
	}

	@Override
	public List<String> getFeaturesList() {
		return StringUtil.split(getFeatures());
	}

	@Override
	public List<String> getRedirectURIsList() {
		return StringUtil.split(getRedirectURIs(), CharPool.NEW_LINE);
	}

	@Override
	public void setAllowedGrantTypesList(
		List<GrantType> allowedGrantTypesList) {

		setAllowedGrantTypes(
			StringUtil.merge(
				allowedGrantTypesList, GrantType::toString, StringPool.COMMA));
	}

	@Override
	public void setFeaturesList(List<String> featuresList) {
		setFeatures(StringUtil.merge(featuresList, StringPool.COMMA));
	}

	@Override
	public void setRedirectURIsList(List<String> redirectURIsList) {
		setRedirectURIs(
			StringUtil.merge(redirectURIsList, StringPool.NEW_LINE));
	}

}