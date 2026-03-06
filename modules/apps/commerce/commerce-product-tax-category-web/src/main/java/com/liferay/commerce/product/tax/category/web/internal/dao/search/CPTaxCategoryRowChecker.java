/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.tax.category.web.internal.dao.search;

import com.liferay.commerce.product.model.CPTaxCategory;
import com.liferay.commerce.product.tax.category.web.internal.display.context.CPTaxCategoryDisplayContext;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;

import jakarta.portlet.PortletResponse;

/**
 * @author Tancredi Covioli
 */
public class CPTaxCategoryRowChecker extends EmptyOnClickRowChecker {

	public CPTaxCategoryRowChecker(
		CPTaxCategoryDisplayContext cpTaxCategoryDisplayContext,
		PortletResponse portletResponse) {

		super(portletResponse);

		_cpTaxCategoryDisplayContext = cpTaxCategoryDisplayContext;
	}

	@Override
	public boolean isDisabled(Object object) {
		CPTaxCategory cpTaxCategory = (CPTaxCategory)object;

		try {
			if (!_cpTaxCategoryDisplayContext.hasModelResourcePermission(
					cpTaxCategory, ActionKeys.DELETE) &&
				!_cpTaxCategoryDisplayContext.hasModelResourcePermission(
					cpTaxCategory, ActionKeys.UPDATE)) {

				return true;
			}
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception);
			}
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CPTaxCategoryRowChecker.class);

	private final CPTaxCategoryDisplayContext _cpTaxCategoryDisplayContext;

}