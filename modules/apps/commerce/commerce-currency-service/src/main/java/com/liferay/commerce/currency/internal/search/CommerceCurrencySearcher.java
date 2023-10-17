/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.currency.internal.search;

import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.product.constants.CPField;
import com.liferay.portal.kernel.search.BaseSearcher;
import com.liferay.portal.kernel.search.Field;

import org.osgi.service.component.annotations.Component;

/**
 * @author Mahmoud Azzam
 */
@Component(
	property = "model.class.name=com.liferay.commerce.currency.model.CommerceCurrency",
	service = BaseSearcher.class
)
public class CommerceCurrencySearcher extends BaseSearcher {

	public CommerceCurrencySearcher() {
		setDefaultSelectedFieldNames(
			Field.COMPANY_ID, Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK,
			CPField.CODE, Field.NAME, CPField.ACTIVE, Field.UID);
		setFilterSearch(true);
		setPermissionAware(true);
	}

	@Override
	public String getClassName() {
		return _CLASS_NAME;
	}

	private static final String _CLASS_NAME = CommerceCurrency.class.getName();

}