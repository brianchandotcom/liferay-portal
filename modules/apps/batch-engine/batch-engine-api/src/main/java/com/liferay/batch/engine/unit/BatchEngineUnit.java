/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.unit;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Raymond Augé
 */
public interface BatchEngineUnit {

	public BatchEngineUnitConfiguration getBatchEngineUnitConfiguration()
		throws IOException;

	public InputStream getConfigurationInputStream() throws IOException;

	public String getDataFileName();

	public InputStream getDataInputStream() throws IOException;

	public String getFileName();

	public boolean isValid();

}