/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.translation.manager;

import com.liferay.petra.function.UnsafeSupplier;

import java.io.IOException;
import java.io.InputStream;

import java.util.function.Supplier;

/**
 * @author Roberto Díaz
 */
public class Translation {

	public Translation(
		Supplier<String> contentTypeSupplier, String fileName,
		UnsafeSupplier<InputStream, IOException> inputStreamUnsafeSupplier) {

		_contentTypeSupplier = contentTypeSupplier;
		_fileName = fileName;
		_inputStreamUnsafeSupplier = inputStreamUnsafeSupplier;
	}

	public String getContentType() {
		return _contentTypeSupplier.get();
	}

	public String getFileName() {
		return _fileName;
	}

	public InputStream getInputStream() throws IOException {
		return _inputStreamUnsafeSupplier.get();
	}

	private final Supplier<String> _contentTypeSupplier;
	private final String _fileName;
	private final UnsafeSupplier<InputStream, IOException>
		_inputStreamUnsafeSupplier;

}