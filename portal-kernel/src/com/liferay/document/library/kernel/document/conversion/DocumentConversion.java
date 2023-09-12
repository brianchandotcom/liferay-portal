/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.kernel.document.conversion;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Pei-Jung Lan
 */
public interface DocumentConversion {

	public File convert(
			String id, InputStream inputStream, String sourceExtension,
			String targetExtension)
		throws IOException;

	public void generatePreviews();

	public String[] getConversions(String extension);

	public String getFilePath(String id, String targetExtension);

	public boolean isComparableVersion(String extension);

	public boolean isConvertBeforeCompare(String extension);

	public boolean isEnabled();

}