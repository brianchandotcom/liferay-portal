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

package com.liferay.commerce.internal.instance.lifecycle;

import com.liferay.commerce.constants.CommerceSAPConstants;
import com.liferay.oauth2.provider.scope.spi.scope.finder.ScopeFinder;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Collection;
import java.util.List;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Joao Victor Alves
 */
@Component(
	property = {"osgi.jaxrs.name=Liferay.Commerce", "sap.scope.finder=true"},
	service = ScopeFinder.class
)
public class CommerceServiceScopeFinder implements ScopeFinder {

	@Override
	public Collection<String> findScopes() {
		return _scopeAliasesList;
	}

	@Activate
	protected void activate() {
		_scopeAliasesList = TransformUtil.transformToList(
			CommerceSAPConstants.SAP_ENTRY_OBJECT_ARRAYS,
			sapEntryObjectArray -> StringUtil.replaceFirst(
				sapEntryObjectArray[0], "OAUTH2_", StringPool.BLANK));
	}

	private List<String> _scopeAliasesList;

}