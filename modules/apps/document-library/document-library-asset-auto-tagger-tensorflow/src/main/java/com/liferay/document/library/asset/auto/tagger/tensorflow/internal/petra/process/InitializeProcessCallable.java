/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.asset.auto.tagger.tensorflow.internal.petra.process;

import com.liferay.document.library.asset.auto.tagger.tensorflow.internal.util.InceptionImageLabelerUtil;
import com.liferay.petra.process.ProcessCallable;
import com.liferay.petra.process.ProcessException;

/**
 * @author Alejandro Tardín
 */
public class InitializeProcessCallable implements ProcessCallable<String> {

	public InitializeProcessCallable(byte[] graphBytes) {
		_graphBytes = graphBytes;
	}

	@Override
	public String call() throws ProcessException {
		InceptionImageLabelerUtil.initializeModel(_graphBytes);

		return "DONE";
	}

	private static final long serialVersionUID = 1L;

	private final byte[] _graphBytes;

}