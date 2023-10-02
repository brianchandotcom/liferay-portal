/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.image.internal;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.OSDetector;

import java.util.LinkedList;
import java.util.List;

import org.im4java.core.ConvertCmd;
import org.im4java.process.ProcessTask;

/**
 * @author Alexander Chow
 * @author Ivica Cardic
 */
public class LiferayConvertCmd extends ConvertCmd {

	@Override
	public LinkedList<String> getCommand() {
		if (OSDetector.isWindows()) {
			LinkedList<String> commands = new LinkedList<>();

			commands.add("magick");

			return commands;
		}

		return super.getCommand();
	}

	public ProcessTask getProcessTask(
			String globalSearchPath, List<String> resourceLimits,
			List<String> commandArguments)
		throws Exception {

		setGlobalSearchPath(globalSearchPath);

		LinkedList<String> arguments = new LinkedList<>();

		arguments.addAll(_liferayConvertCmd.getCommand());
		arguments.addAll(resourceLimits);
		arguments.addAll(commandArguments);

		if (_log.isInfoEnabled()) {
			StringBundler sb = new StringBundler(arguments.size() * 2);

			for (String argument : arguments) {
				sb.append(argument);
				sb.append(StringPool.SPACE);
			}

			_log.info("Excecuting command '" + sb.toString() + "'");
		}

		return getProcessTask(arguments);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LiferayConvertCmd.class);

	private static final LiferayConvertCmd _liferayConvertCmd =
		new LiferayConvertCmd();

}