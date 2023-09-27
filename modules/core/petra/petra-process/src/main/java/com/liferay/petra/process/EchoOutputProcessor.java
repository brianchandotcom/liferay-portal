/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.process;

import com.liferay.petra.io.StreamUtil;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Shuyang Zhou
 */
public class EchoOutputProcessor implements OutputProcessor<Void, Void> {

	public static final OutputProcessor<Void, Void> INSTANCE =
		new EchoOutputProcessor();

	@Override
	public Void processStdErr(InputStream stdErrInputStream)
		throws ProcessException {

		try {
			StreamUtil.transfer(stdErrInputStream, System.err, false);
		}
		catch (IOException ioException) {
			throw new ProcessException(ioException);
		}

		return null;
	}

	@Override
	public Void processStdOut(InputStream stdOutInputStream)
		throws ProcessException {

		try {
			StreamUtil.transfer(stdOutInputStream, System.out, false);
		}
		catch (IOException ioException) {
			throw new ProcessException(ioException);
		}

		return null;
	}

}