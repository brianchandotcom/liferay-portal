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
import com.liferay.petra.function.UnsafeConsumer;

import java.io.File;
import java.io.InputStream;

/**
 * @author Raymond Augé
 */
public class MockAntivirusScanner implements AntivirusScanner {

	public MockAntivirusScanner(
		UnsafeConsumer<byte[], AntivirusScannerException> bytesConsumer,
		UnsafeConsumer<File, AntivirusScannerException> fileConsumer,
		UnsafeConsumer<InputStream, AntivirusScannerException>
			inputStreamConsumer) {

		_bytesConsumer = bytesConsumer;
		_fileConsumer = fileConsumer;
		_inputStreamConsumer = inputStreamConsumer;
	}

	@Override
	public boolean isActive() {
		return true;
	}

	@Override
	public void scan(byte[] bytes) throws AntivirusScannerException {
		_bytesConsumer.accept(bytes);
	}

	@Override
	public void scan(File file) throws AntivirusScannerException {
		_fileConsumer.accept(file);
	}

	@Override
	public void scan(InputStream inputStream) throws AntivirusScannerException {
		_inputStreamConsumer.accept(inputStream);
	}

	public static class Builder {

		public MockAntivirusScanner build() {
			return new MockAntivirusScanner(
				_bytesConsumer, _fileConsumer, _inputStreamConsumer);
		}

		public Builder bytesConsumer(
			UnsafeConsumer<byte[], AntivirusScannerException> bytesConsumer) {

			_bytesConsumer = bytesConsumer;

			return this;
		}

		public Builder fileConsumer(
			UnsafeConsumer<File, AntivirusScannerException> fileConsumer) {

			_fileConsumer = fileConsumer;

			return this;
		}

		public Builder inputStreamConsumer(
			UnsafeConsumer<InputStream, AntivirusScannerException>
				inputStreamConsumer) {

			_inputStreamConsumer = inputStreamConsumer;

			return this;
		}

		private UnsafeConsumer<byte[], AntivirusScannerException>
			_bytesConsumer = i -> {
			};
		private UnsafeConsumer<File, AntivirusScannerException> _fileConsumer =
			i -> {
			};
		private UnsafeConsumer<InputStream, AntivirusScannerException>
			_inputStreamConsumer = i -> {
			};

	}

	private final UnsafeConsumer<byte[], AntivirusScannerException>
		_bytesConsumer;
	private final UnsafeConsumer<File, AntivirusScannerException> _fileConsumer;
	private final UnsafeConsumer<InputStream, AntivirusScannerException>
		_inputStreamConsumer;

}