/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.wsdd.builder.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;

import java.io.IOException;

import org.apache.tools.ant.BuildEvent;
import org.apache.tools.ant.BuildLogger;
import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Project;

/**
 * @author Brian Wing Shun Chan
 */
public class AntUtil {

	public static Project getProject() {
		Project project = new Project();

		BuildLogger buildLogger = new DefaultLogger() {

			@Override
			public void messageLogged(BuildEvent buildEvent) {
				int priority = buildEvent.getPriority();

				if (priority > msgOutputLevel) {
					return;
				}

				StringBundler sb = new StringBundler();

				try (UnsyncBufferedReader unsyncBufferedReader =
						new UnsyncBufferedReader(
							new UnsyncStringReader(buildEvent.getMessage()))) {

					boolean first = true;

					String line = unsyncBufferedReader.readLine();

					while (line != null) {
						if (!first) {
							sb.append(StringPool.OS_EOL);
						}

						first = false;

						sb.append(StringPool.DOUBLE_SPACE);
						sb.append(line);

						line = unsyncBufferedReader.readLine();
					}
				}
				catch (IOException ioException) {
				}

				String message = sb.toString();

				if (priority != Project.MSG_ERR) {
					printMessage(message, out, priority);
				}
				else {
					printMessage(message, err, priority);
				}

				log(message);
			}

		};

		buildLogger.setErrorPrintStream(System.err);
		buildLogger.setMessageOutputLevel(Project.MSG_WARN);
		buildLogger.setOutputPrintStream(System.out);

		project.addBuildListener(buildLogger);

		return project;
	}

}