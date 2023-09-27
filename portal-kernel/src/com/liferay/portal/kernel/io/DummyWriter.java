/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.io;

import java.io.Writer;

/**
 * @author Shuyang Zhou
 */
public class DummyWriter extends Writer {

	@Override
	public Writer append(char c) {
		return this;
	}

	@Override
	public Writer append(CharSequence charSequence) {
		return this;
	}

	@Override
	public Writer append(CharSequence charSequence, int start, int end) {
		return this;
	}

	@Override
	public void close() {
	}

	@Override
	public void flush() {
	}

	@Override
	public void write(char[] chars) {
	}

	@Override
	public void write(char[] chars, int offset, int length) {
	}

	@Override
	public void write(int c) {
	}

	@Override
	public void write(String string) {
	}

	@Override
	public void write(String string, int offset, int length) {
	}

}