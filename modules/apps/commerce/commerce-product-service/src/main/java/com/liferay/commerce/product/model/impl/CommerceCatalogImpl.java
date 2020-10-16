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

	public CommerceCatalogImpl() {
	}

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