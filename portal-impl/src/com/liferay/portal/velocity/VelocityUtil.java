/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.velocity;

import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsUtil;

import java.util.Iterator;
import java.util.Map;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;

/**
 * @author Brian Wing Shun Chan
 */
public class VelocityUtil {

	public static String evaluate(String input) throws Exception {
		return evaluate(input, null);
	}

	public static String evaluate(String input, Map<String, Object> variables)
		throws Exception {

		VelocityEngine velocityEngine = new VelocityEngine();

		velocityEngine.setProperty(
			RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS,
			PropsUtil.get(PropsKeys.VELOCITY_ENGINE_LOGGER));

		velocityEngine.setProperty(
			RuntimeConstants.RUNTIME_LOG_LOGSYSTEM + ".log4j.category",
			PropsUtil.get(PropsKeys.VELOCITY_ENGINE_LOGGER_CATEGORY));

		velocityEngine.init();

		VelocityContext velocityContext = new VelocityContext();

		if (variables != null) {
			Iterator<Map.Entry<String, Object>> itr =
				variables.entrySet().iterator();

			while (itr.hasNext()) {
				Map.Entry<String, Object> entry = itr.next();

				String key = entry.getKey();
				Object value = entry.getValue();

				if (Validator.isNotNull(key)) {
					velocityContext.put(key, value);
				}
			}
		}

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		velocityEngine.evaluate(
			velocityContext, unsyncStringWriter, VelocityUtil.class.getName(),
			input);

		return unsyncStringWriter.toString();
	}

}