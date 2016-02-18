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

package com.liferay.shopping.verify;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.verify.VerifyProcess;
import com.liferay.portal.verify.VerifyResourcePermissions;
import com.liferay.shopping.verify.model.ShoppingCategoryVerifiableModel;
import com.liferay.shopping.verify.model.ShoppingItemVerifiableResourcedModel;

import org.osgi.service.component.annotations.Component;

/**
 * @author Peter Fellwock
 */
@Component(service = ShoppingServiceVerifyProcess.class)
public class ShoppingServiceVerifyProcess extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		verifyResourcedModels();
	}

	protected void verifyResourcedModels() throws Exception {
		_verifyResourcePermissions.verify(
			new ShoppingCategoryVerifiableModel());
		_verifyResourcePermissions.verify(
			new ShoppingItemVerifiableResourcedModel());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ShoppingServiceVerifyProcess.class);

	private final VerifyResourcePermissions _verifyResourcePermissions =
		new VerifyResourcePermissions();

}