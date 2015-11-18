/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.json.transformer;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Company;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionThreadLocal;

/**
 * @author Igor Spasic
 */
public class CompanyJSONTransformer extends FlexjsonObjectJSONTransformer {

	@Override
	public void transform(Object object) {
		Company company = (Company)object;

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if ((permissionChecker == null) ||
			!permissionChecker.isCompanyAdmin(company.getCompanyId())) {

			company.setKey(StringPool.BLANK);
			company.setKeyObj(null);
		}

		super.transform(object);
	}

}