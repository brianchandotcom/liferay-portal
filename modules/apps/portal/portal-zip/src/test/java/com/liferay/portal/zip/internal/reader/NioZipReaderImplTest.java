/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.zip.internal.reader;

import com.liferay.portal.kernel.zip.ZipReader;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.junit.ClassRule;

/**
 * @author Alejandro Tardín
 */
public class NioZipReaderImplTest extends BaseReaderImplTestCase {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Override
	protected ZipReader getZipReader(File file) {
		return new NioZipReaderImpl(file);
	}

	@Override
	protected ZipReader getZipReader(InputStream inputStream)
		throws IOException {

		return new NioZipReaderImpl(inputStream);
	}

}