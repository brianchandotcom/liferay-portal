/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.unit;

import org.osgi.framework.Bundle;

/**
 * @author Alejandro Tardín
 */
public interface BundleBatchEngineUnit extends BatchEngineUnit {

	public Bundle getBundle();

}