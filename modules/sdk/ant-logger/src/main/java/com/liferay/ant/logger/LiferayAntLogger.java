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

package com.liferay.ant.logger;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.tools.ant.BuildEvent;
import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Target;

/**
 * @author Kevin Yen
 */
public class LiferayAntLogger extends DefaultLogger {

	public void targetFinished(BuildEvent event) {
		super.targetFinished(event);

		Target target = event.getTarget();

		long startTime = _startTimeMap.get(target);

		long duration = System.currentTimeMillis() - startTime;

		StringBuilder sb = new StringBuilder();

		sb.append("  [logging] ");
		sb.append(_simpleDateFormat.format(new Date(startTime)));
		sb.append(" : Target ");
		sb.append(target.getName());
		sb.append(" finished in ");
		sb.append(duration);
		sb.append("ms");

		printMessage(sb.toString(), out, event.getPriority());
	}

	public void targetStarted(BuildEvent event) {
		super.targetStarted(event);

		Target target = event.getTarget();

		long currentTime = System.currentTimeMillis();

		_startTimeMap.put(target, currentTime);

		StringBuilder sb = new StringBuilder();

		sb.append("  [logging] ");
		sb.append(_simpleDateFormat.format(new Date(currentTime)));
		sb.append(" : Target ");
		sb.append(target.getName());
		sb.append(" started");

		printMessage(sb.toString(), out, event.getPriority());
	}

	private static final SimpleDateFormat _simpleDateFormat =
		new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SSS aa");

	private final Map<Target, Long> _startTimeMap = new HashMap<>();

}