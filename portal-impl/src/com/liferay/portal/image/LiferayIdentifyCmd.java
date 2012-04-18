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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.util.LinkedList;
import java.util.List;

import jodd.util.StringPool;

import org.im4java.core.IdentifyCmd;
import org.im4java.process.ArrayListOutputConsumer;

/**
 * @author Alexander Chow
 */
public class LiferayIdentifyCmd extends IdentifyCmd {

	public static String[] run(
			String globalSearchPath, List<String> resourceLimits,
			List<String> commandArguments)
		throws Exception {

		setGlobalSearchPath(globalSearchPath);

		LinkedList<String> arguments = new LinkedList<String>();

		arguments.addAll(_instance.getCommand());
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

		ArrayListOutputConsumer outputConsumer = new ArrayListOutputConsumer();

		_instance.setOutputConsumer(outputConsumer);
		_instance.run(arguments);

		List<String> outputList = outputConsumer.getOutput();

		if (outputList != null) {
			return outputList.toArray(new String[0]);
		}
		else {
			return new String[0];
		}
	}

	private static Log _log = LogFactoryUtil.getLog(LiferayIdentifyCmd.class);

	private static LiferayIdentifyCmd _instance = new LiferayIdentifyCmd();

}