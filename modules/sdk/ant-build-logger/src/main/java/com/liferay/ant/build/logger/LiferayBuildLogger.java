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

package com.liferay.ant.build.logger;

import org.apache.tools.ant.BuildEvent;
import org.apache.tools.ant.BuildListener;
import org.apache.tools.ant.Project;

/**
 * @author William Newbury
 * @author Shuyang Zhou
 * @author Kevin Yen
 */
public class LiferayBuildLogger implements BuildListener {

	public LiferayBuildLogger(BuildListener buildListener) {
		_buildListener = buildListener;
	}

	@Override
	public void buildFinished(BuildEvent buildEvent) {
		_buildListener.buildFinished(buildEvent);
	}

	@Override
	public void buildStarted(BuildEvent buildEvent) {
		_buildListener.buildStarted(buildEvent);
	}

	@Override
	public void messageLogged(BuildEvent buildEvent) {
		String message = buildEvent.getMessage();

		if (message.startsWith("Trying to override old definition of ")) {
			buildEvent.setMessage(message, Project.MSG_DEBUG);
		}

		_buildListener.messageLogged(buildEvent);
	}

	@Override
	public void targetFinished(BuildEvent buildEvent) {
		_buildListener.targetFinished(buildEvent);
	}

	@Override
	public void targetStarted(BuildEvent buildEvent) {
		_buildListener.targetStarted(buildEvent);
	}

	@Override
	public void taskFinished(BuildEvent buildEvent) {
		_buildListener.taskFinished(buildEvent);
	}

	@Override
	public void taskStarted(BuildEvent buildEvent) {
		_buildListener.taskStarted(buildEvent);
	}

	private final BuildListener _buildListener;

}