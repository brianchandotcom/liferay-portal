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

package com.liferay.asset.internal.verify;

import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.verify.VerifyLayout;
import com.liferay.portal.verify.VerifyProcess;

import org.osgi.service.component.annotations.Component;

/**
 * @author Douglas Wong
 */
@Component(
	immediate = true,
	property = {"verify.process.name=com.liferay.asset.service"},
	service = VerifyProcess.class
)
public class AssetServiceVerifyProcess extends VerifyLayout {

	@Override
	protected void doVerify() throws Exception {
		verifyAssetLayouts();
	}

	protected void verifyAssetLayouts() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			verifyUuid("AssetEntry");
		}
	}

}