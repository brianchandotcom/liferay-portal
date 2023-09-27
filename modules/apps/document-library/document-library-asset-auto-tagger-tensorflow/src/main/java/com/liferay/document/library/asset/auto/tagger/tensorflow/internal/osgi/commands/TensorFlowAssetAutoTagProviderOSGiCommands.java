/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.asset.auto.tagger.tensorflow.internal.osgi.commands;

import com.liferay.document.library.asset.auto.tagger.tensorflow.internal.util.TensorFlowProcessHolder;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = {
		"osgi.command.function=" + TensorFlowAssetAutoTagProviderOSGiCommands.RESET_PROCESS_COUNTER,
		"osgi.command.scope=" + TensorFlowAssetAutoTagProviderOSGiCommands.SCOPE
	},
	service = TensorFlowAssetAutoTagProviderOSGiCommands.class
)
public class TensorFlowAssetAutoTagProviderOSGiCommands {

	public static final String RESET_PROCESS_COUNTER = "resetProcessCounter";

	public static final String SCOPE = "tensorFlowAssetAutoTagProvider";

	public void resetProcessCounter() {
		TensorFlowProcessHolder.resetCounter();
	}

}