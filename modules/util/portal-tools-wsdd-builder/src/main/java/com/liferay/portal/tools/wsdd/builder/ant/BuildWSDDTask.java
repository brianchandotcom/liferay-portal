/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.wsdd.builder.ant;

import com.liferay.portal.tools.wsdd.builder.WSDDBuilderArgs;
import com.liferay.portal.tools.wsdd.builder.WSDDBuilderInvoker;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

/**
 * @author Andrea Di Giorgi
 */
public class BuildWSDDTask extends Task {

	@Override
	public void execute() throws BuildException {
		try {
			Project project = getProject();

			WSDDBuilderInvoker.invoke(project.getBaseDir(), _wsddBuilderArgs);
		}
		catch (Exception exception) {
			throw new BuildException(exception);
		}
	}

	public void setClassPath(String classPath) {
		_wsddBuilderArgs.setClassPath(classPath);
	}

	public void setInputFileName(String inputFileName) {
		_wsddBuilderArgs.setFileName(inputFileName);
	}

	public void setOutputDirName(String outputDirName) {
		_wsddBuilderArgs.setOutputPath(outputDirName);
	}

	public void setServerConfigFileName(String serverConfigFileName) {
		_wsddBuilderArgs.setServerConfigFileName(serverConfigFileName);
	}

	public void setServiceNamespace(String serviceNamespace) {
		_wsddBuilderArgs.setServiceNamespace(serviceNamespace);
	}

	private final WSDDBuilderArgs _wsddBuilderArgs = new WSDDBuilderArgs();

}