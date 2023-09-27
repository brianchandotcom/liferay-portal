/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.kernel.antivirus;

import java.io.File;
import java.io.InputStream;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Michael C. Han
 * @author Raymond Augé
 */
@ProviderType
public interface AntivirusScanner {

	public boolean isActive();

	public void scan(byte[] bytes) throws AntivirusScannerException;

	public void scan(File file) throws AntivirusScannerException;

	public void scan(InputStream inputStream) throws AntivirusScannerException;

}