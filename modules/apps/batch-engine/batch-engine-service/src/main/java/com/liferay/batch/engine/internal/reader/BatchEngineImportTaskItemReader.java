/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.internal.reader;

import java.io.Closeable;

import java.util.Map;

/**
 * @author Ivica Cardic
 */
public interface BatchEngineImportTaskItemReader extends Closeable {

	public Map<String, Object> read() throws Exception;

}