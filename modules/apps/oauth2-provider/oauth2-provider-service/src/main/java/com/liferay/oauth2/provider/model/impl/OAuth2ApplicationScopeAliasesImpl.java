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

import aQute.bnd.annotation.ProviderType;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Arrays;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
@ProviderType
public class OAuth2ApplicationScopeAliasesImpl
	extends OAuth2ApplicationScopeAliasesBaseImpl {

	@Override
	public List<String> getScopeAliasesList() {
		return Arrays.asList(
			StringUtil.split(getScopeAliases(), StringPool.SPACE));
	}

	@Override
	public void setScopeAliases(String scopeAliases) {
		super.setScopeAliases(scopeAliases);

		if (scopeAliases != null) {
			setScopeAliasesHash(scopeAliases.hashCode());
		}
		else {
			setScopeAliasesHash(0);
		}
	}

	@Override
	public void setScopeAliasesList(List<String> scopeAliasesList) {
		String scopeAliases = StringUtil.merge(
			ListUtil.sort(scopeAliasesList), StringPool.SPACE);

		setScopeAliases(scopeAliases);
	}

}