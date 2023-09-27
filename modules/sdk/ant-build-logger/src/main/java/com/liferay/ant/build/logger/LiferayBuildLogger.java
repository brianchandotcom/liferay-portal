/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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