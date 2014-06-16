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

package com.liferay.portal.wab.extender.internal.util;

import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.File;
import java.io.IOException;

import org.apache.tools.ant.BuildEvent;
import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Expand;
import org.apache.tools.ant.util.StringUtils;

/**
 * @author Raymond Augé
 * @author Brian Wing Shun Chan
 */
public class AntUtil {

	public static void expandFile(File source, File destination) {
		Expand expand = new Expand();

		expand.setProject(getAntProject());
		expand.setSrc(source);
		expand.setDest(destination);

		expand.execute();
	}

	public static Project getAntProject() {
		Project project = new Project();

		DefaultLogger logger = new DefaultLogger() {

			@Override
			public void messageLogged(BuildEvent event) {
				int priority = event.getPriority();

				if (priority <= msgOutputLevel) {
					StringBundler sb = new StringBundler();

					try {
						UnsyncBufferedReader unsyncBufferedReader =
							new UnsyncBufferedReader(
								new UnsyncStringReader(event.getMessage()));

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
			}
		};

		logger.setMessageOutputLevel(Project.MSG_INFO);
		logger.setOutputPrintStream(System.out);
		logger.setErrorPrintStream(System.err);

		project.addBuildListener(logger);

		return project;
	}

}