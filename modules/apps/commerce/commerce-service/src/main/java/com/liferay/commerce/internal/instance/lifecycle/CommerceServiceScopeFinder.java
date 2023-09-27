/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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