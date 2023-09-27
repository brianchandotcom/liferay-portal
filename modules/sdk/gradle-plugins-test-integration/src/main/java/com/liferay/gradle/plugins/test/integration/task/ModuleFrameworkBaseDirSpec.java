/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.test.integration.task;

import java.io.File;

/**
 * @author Andrea Di Giorgi
 */
public interface ModuleFrameworkBaseDirSpec {

	public File getModuleFrameworkBaseDir();

	public void setModuleFrameworkBaseDir(Object moduleFrameworkBaseDir);

}