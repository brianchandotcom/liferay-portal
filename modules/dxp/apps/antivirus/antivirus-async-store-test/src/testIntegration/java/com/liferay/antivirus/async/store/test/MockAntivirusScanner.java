/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.antivirus.async.store.test;

import com.liferay.document.library.kernel.antivirus.AntivirusScanner;
import com.liferay.document.library.kernel.antivirus.AntivirusScannerException;
import com.liferay.petra.function.UnsafeRunnable;

import java.io.File;
import java.io.InputStream;

/**
 * @author Raymond Augé
 */
public class MockAntivirusScanner implements AntivirusScanner {

	public MockAntivirusScanner(
		UnsafeRunnable<AntivirusScannerException> unsafeRunnable) {

		_unsafeRunnable = unsafeRunnable;
	}

	@Override
	public boolean isActive() {
		return true;
	}

	@Override
	public void scan(byte[] bytes) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void scan(File file) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void scan(InputStream inputStream) throws AntivirusScannerException {
		_unsafeRunnable.run();
	}

	private final UnsafeRunnable<AntivirusScannerException> _unsafeRunnable;

}