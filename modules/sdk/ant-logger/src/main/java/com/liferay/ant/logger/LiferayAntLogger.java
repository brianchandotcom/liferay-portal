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

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.tools.ant.BuildEvent;
import org.apache.tools.ant.DefaultLogger;

/**
 * @author Kevin Yen
 */
public class LiferayAntLogger extends DefaultLogger {

	public void targetFinished(BuildEvent event) {
		super.targetFinished(event);

		Date start = _startTimesMap.get((Object)event.getTarget());

		String name = "Target " + event.getTarget().getName();

		logFinish(event, start, name);
	}

	public void targetStarted(BuildEvent event) {
		super.targetStarted(event);

		Date now = new Date();

		_startTimesMap.put((Object)event.getTarget(), now);

		String name = "Target " + event.getTarget().getName();

		logStart(event, now, name);
	}

	private void logFinish(BuildEvent event, Date start, String name) {
		Date now = new Date();

		long duration = now.getTime() - start.getTime();

		String message =
			"  [logging] " + start.toString() + " : " + name + " finished in " +
				duration + "ms";

		printMessage(message, out, event.getPriority());
	}

	private void logStart(BuildEvent event, Date start, String name) {
		String message =
			"  [logging] " + start.toString() + " : " + name + " started";

		printMessage(message, out, event.getPriority());
	}

	private final Map<Object, Date> _startTimesMap = new HashMap();

}