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

package com.liferay.oauth2.provider.model.impl;

import com.liferay.oauth2.provider.constants.GrantType;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Arrays;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class OAuth2ApplicationImpl extends OAuth2ApplicationBaseImpl {

	@Override
	public List<GrantType> getAllowedGrantTypesList() {
		return TransformUtil.transformToList(
			StringUtil.split(getAllowedGrantTypes()), GrantType::valueOf);
	}

	@Override
	public List<String> getFeaturesList() {
		return Arrays.asList(StringUtil.split(getFeatures()));
	}

	@Override
	public List<String> getRedirectURIsList() {
		return Arrays.asList(
			StringUtil.split(getRedirectURIs(), StringPool.NEW_LINE));
	}

	@Override
	public void setAllowedGrantTypesList(
		List<GrantType> allowedGrantTypesList) {

		setAllowedGrantTypes(
			com.liferay.petra.string.StringUtil.merge(
				allowedGrantTypesList, GrantType::toString, StringPool.COMMA));
	}

	@Override
	public void setFeaturesList(List<String> featuresList) {
		setFeatures(StringUtil.merge(featuresList));
	}

	@Override
	public void setRedirectURIsList(List<String> redirectURIsList) {
		setRedirectURIs(
			StringUtil.merge(redirectURIsList, StringPool.NEW_LINE));
	}

}