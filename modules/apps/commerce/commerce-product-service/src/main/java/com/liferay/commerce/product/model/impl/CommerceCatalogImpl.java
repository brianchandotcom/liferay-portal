/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.model.impl;

import com.liferay.commerce.product.service.CommerceCatalogLocalServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.impl.GroupImpl;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceCatalogImpl extends CommerceCatalogBaseImpl {

	@Override
	public String getCatalogDefaultLanguageId() {
		String catalogDefaultLanguageId = super.getCatalogDefaultLanguageId();

		if (Validator.isBlank(catalogDefaultLanguageId)) {
			return catalogDefaultLanguageId;
		}

		return LocaleUtil.toLanguageId(
			LocaleUtil.fromLanguageId(catalogDefaultLanguageId));
	}

	@Override
	public Group getGroup() {
		if (getCommerceCatalogId() > 0) {
			try {
				return CommerceCatalogLocalServiceUtil.getCommerceCatalogGroup(
					getCommerceCatalogId());
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to get commerce catalog group", exception);
				}
			}
		}

		return new GroupImpl();
	}

	@Override
	public long getGroupId() {
		Group group = getGroup();

		return group.getGroupId();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceCatalogImpl.class);

}