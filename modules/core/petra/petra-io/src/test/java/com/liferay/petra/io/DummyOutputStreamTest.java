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

package com.liferay.petra.io;

import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;

import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Preston Crary
 */
public class DummyOutputStreamTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@Test
	public void testDummyOutputStream() {
		try (DummyOutputStream dummyOutputStream = new DummyOutputStream()) {
			dummyOutputStream.write(0);
			dummyOutputStream.write(new byte[0]);
			dummyOutputStream.write(new byte[0], 0, 0);

			dummyOutputStream.flush();
		}
	}

}