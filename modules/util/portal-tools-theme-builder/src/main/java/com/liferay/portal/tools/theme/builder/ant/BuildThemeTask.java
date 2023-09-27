/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.theme.builder.ant;

import com.liferay.portal.tools.theme.builder.ThemeBuilder;
import com.liferay.portal.tools.theme.builder.ThemeBuilderArgs;

import org.apache.tools.ant.BuildException;

/**
 * @author Andrea Di Giorgi
 */
public class BuildThemeTask extends ThemeBuilderArgs {

	public void execute() {
		try {
			ThemeBuilder themeBuilder = new ThemeBuilder(this);

			themeBuilder.build();
		}
		catch (Exception exception) {
			throw new BuildException(exception);
		}
	}

}