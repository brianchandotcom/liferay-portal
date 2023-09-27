/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.tld.formatter.ant;

import com.liferay.tld.formatter.TLDFormatter;
import com.liferay.tld.formatter.TLDFormatterArgs;
import com.liferay.tld.formatter.TLDFormatterInvoker;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

/**
 * @author Andrea Di Giorgi
 */
public class FormatTLDTask extends Task {

	@Override
	public void execute() throws BuildException {
		try {
			Project project = getProject();

			TLDFormatter tldFormatter = TLDFormatterInvoker.invoke(
				project.getBaseDir(), _tldFormatterArgs);

			project.addIdReference(
				TLDFormatterArgs.OUTPUT_KEY_MODIFIED_FILES,
				tldFormatter.getModifiedFileNames());
		}
		catch (Exception exception) {
			throw new BuildException(exception);
		}
	}

	public void setBaseDirName(String baseDirName) {
		_tldFormatterArgs.setBaseDirName(baseDirName);
	}

	public void setPlugin(boolean plugin) {
		_tldFormatterArgs.setPlugin(plugin);
	}

	private final TLDFormatterArgs _tldFormatterArgs = new TLDFormatterArgs();

}