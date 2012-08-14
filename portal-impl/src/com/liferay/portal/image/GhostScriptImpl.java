/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.image;

import com.liferay.portal.kernel.image.GhostScript;
import com.liferay.portal.kernel.image.ImageMagick;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.process.ProcessUtil;
import com.liferay.portal.kernel.util.OSDetector;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Future;

import jodd.util.StringPool;

/**
 * @author Ivica Cardic
 */
public class GhostScriptImpl implements GhostScript {

	public Future<?> execute(List<String> commandArguments) throws Exception {
		if (!isEnabled()) {
			StringBundler sb = new StringBundler(5);

			sb.append("Cannot execute the GhostScript command. Please install");
			sb.append("ImageMagick and GhostScript and enable ImageMagick in ");
			sb.append("portal-ext.properties or in the Server Administration ");
			sb.append("control panel at: http://<server>/group/control_panel/");
			sb.append("manage/-/server/external-services");

			throw new IllegalStateException(sb.toString());
		}

		LinkedList<String> arguments = new LinkedList<String>();

		arguments.add(_commandPath);
		arguments.add("-dBATCH");
		arguments.add("-dSAFER");
		arguments.add("-dNOPAUSE");
		arguments.add("-dNOPROMPT");
		arguments.add("-sFONTPATH" + _globalSearchPath);
		arguments.addAll(commandArguments);

		if (_log.isInfoEnabled()) {
			StringBundler sb = new StringBundler(arguments.size() * 2);

			for (String argument : arguments) {
				sb.append(argument);
				sb.append(StringPool.SPACE);
			}

			_log.info("Excecuting command '" + sb.toString() + "'");
		}

		return ProcessUtil.execute(
			ProcessUtil.LOGGING_OUTPUT_PROCESSOR, arguments);
	}

	public boolean isEnabled() {
		return _imageMagick.isEnabled();
	}

	public void reset() {
		if (isEnabled()) {
			try {
				_globalSearchPath = _imageMagick.getGlobalSearchPath();

				_commandPath = getCommandPath();
			}
			catch (Exception e) {
				_log.warn(e, e);
			}
		}
	}

	protected String getCommandPath() throws Exception {
		String commandPath;

		if (OSDetector.isWindows()) {
			commandPath = getCommandPathWindows();
		}
		else {
			commandPath = getCommandPathUnix();
		}

		if (commandPath == null) {
			throw new FileNotFoundException(
				"The Ghostscript command cannot be found.");
		}

		return commandPath;
	}

	protected String getCommandPathUnix() throws Exception {
		String dirNames[] = _globalSearchPath.split(File.pathSeparator);

		for (String dirName : dirNames) {
			File command = new File(dirName, _GHOSTSCRIPT_UNIX_COMMAND);

			if (command.exists()) {
				return command.getCanonicalPath();
			}
		}

		return null;
	}

	protected String getCommandPathWindows() throws Exception {
		String dirNames[] = _globalSearchPath.split(File.pathSeparator);

		for (String dirName : dirNames) {
			for (String commandName : _GHOSTSCRIPT_WINDOWS_COMMANDS) {
				File command = new File(dirName, commandName + ".exe");

				if (!command.exists()) {
					command = new File(dirName, commandName + ".cmd");

					if (!command.exists()) {
						command = new File(dirName, commandName + ".bat");

						if (!command.exists()) {
							continue;
						}
					}
				}

				return command.getCanonicalPath();
			}
		}

		return null;
	}

	private static final String _GHOSTSCRIPT_UNIX_COMMAND = "gs";

	private static final String[] _GHOSTSCRIPT_WINDOWS_COMMANDS = new String[] {
		"gswin32c", "gswin64c"
	};

	private static Log _log = LogFactoryUtil.getLog(GhostScriptImpl.class);

	private static ImageMagick _imageMagick = ImageMagickImpl.getInstance();

	private String _commandPath;

	private String _globalSearchPath;

}