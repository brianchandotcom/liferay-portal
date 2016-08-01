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

package com.liferay.mobile.device.rules.internal.verify;

import com.liferay.mobile.device.rules.internal.verify.model.MDRRuleGroupInstanceVerifiableModel;
import com.liferay.mobile.device.rules.internal.verify.model.MDRRuleGroupVerifiableModel;
import com.liferay.mobile.device.rules.service.MDRActionLocalService;
import com.liferay.portal.verify.VerifyProcess;
import com.liferay.portal.verify.VerifyResourcePermissions;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tomas Polesovsky
 */
@Component(
	immediate = true,
	property = {"verify.process.name=com.liferay.mobile.device.rules.service"},
	service = VerifyProcess.class
)
public class MDRServiceVerifyProcess extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		verifyResourcedModels();
	}

	@Reference(unbind = "-")
	protected void setMDRActionLocalService(
		MDRActionLocalService mdrActionLocalService) {
	}

	protected void verifyResourcedModels() throws Exception {
		_verifyResourcePermissions.verify(
			new MDRRuleGroupInstanceVerifiableModel());
		_verifyResourcePermissions.verify(new MDRRuleGroupVerifiableModel());
	}

	private final VerifyResourcePermissions _verifyResourcePermissions =
		new VerifyResourcePermissions();

}