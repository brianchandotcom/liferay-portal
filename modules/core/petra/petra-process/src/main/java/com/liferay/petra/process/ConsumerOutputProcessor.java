/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.process;

import com.liferay.petra.io.DummyOutputStream;
import com.liferay.petra.io.StreamUtil;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Shuyang Zhou
 */
public class ConsumerOutputProcessor implements OutputProcessor<Void, Void> {

	public static final OutputProcessor<Void, Void> INSTANCE =
		new ConsumerOutputProcessor();

	@Override
	public Void processStdErr(InputStream stdErrInputStream)
		throws ProcessException {

		_consume(stdErrInputStream);

		return null;
	}

	@Override
	public Void processStdOut(InputStream stdOutInputStream)
		throws ProcessException {

		_consume(stdOutInputStream);

		return null;
	}

	private void _consume(InputStream inputStream) throws ProcessException {
		try {
			StreamUtil.transfer(inputStream, new DummyOutputStream());
		}
		catch (IOException ioException) {
			throw new ProcessException(ioException);
		}
	}

}