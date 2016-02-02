/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.tld.formatter.ant;

import com.liferay.tld.formatter.TLDFormatter;
import com.liferay.tld.formatter.TLDFormatterArgs;
import com.liferay.tld.formatter.TLDFormatterInvoker;

import java.util.Set;

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

			Set<String> modifiedFileNames = tldFormatter.getModifiedFileNames();

			project.addIdReference(
				TLDFormatterArgs.OUTPUT_KEY_MODIFIED_FILES, modifiedFileNames);
		}
		catch (Exception e) {
			throw new BuildException(e);
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