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

package com.liferay.portal.wab.generator.internal.util;

import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.File;
import java.io.IOException;

import org.apache.tools.ant.BuildEvent;
import org.apache.tools.ant.BuildLogger;
import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Expand;
import org.apache.tools.ant.util.StringUtils;

/**
 * @author Raymond Augé
 * @author Brian Wing Shun Chan
 */
public class AntUtil {

	public static void expandFile(File sourceFile, File destinationDir) {
		Expand expand = new Expand();

		expand.setDest(destinationDir);
		expand.setProject(getAntProject());
		expand.setSrc(sourceFile);

		expand.execute();
	}

	public static Project getAntProject() {
		Project project = new Project();

		BuildLogger buildLogger = new DefaultLogger() {

			@Override
			public void messageLogged(BuildEvent buildEvent) {
				int priority = buildEvent.getPriority();

				if (priority > msgOutputLevel) {
					return;
				}

				StringBundler sb = new StringBundler();

				try {
					UnsyncBufferedReader unsyncBufferedReader =
						new UnsyncBufferedReader(
							new UnsyncStringReader(buildEvent.getMessage()));

					String line = unsyncBufferedReader.readLine();

					boolean first = true;

					while (line != null) {
						if (!first) {
							sb.append(StringUtils.LINE_SEP);
						}

						first = false;

						sb.append("  ");
						sb.append(line);

						line = unsyncBufferedReader.readLine();
					}
				}
				catch (IOException ioe) {
				}

				String msg = sb.toString();

				if (priority != Project.MSG_ERR) {
					printMessage(msg, out, priority);
				}
				else {
					printMessage(msg, err, priority);
				}

				log(msg);
			}

		};

		buildLogger.setErrorPrintStream(System.err);
		buildLogger.setMessageOutputLevel(Project.MSG_INFO);
		buildLogger.setOutputPrintStream(System.out);

		project.addBuildListener(buildLogger);

		return project;
	}

}