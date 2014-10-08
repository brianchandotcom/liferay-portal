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

package com.liferay.portal.kernel.deploy.sandbox;

/**
 * @author Igor Spasic
 * @author Brian Wing Shun Chan
 */
public class SandboxDeployScanner extends Thread {

	public SandboxDeployScanner(
		ThreadGroup threadGroup, String name,
		SandboxDeployDir sandboxDeployDir) {

		super(threadGroup, name);

		_sandboxDeployDir = sandboxDeployDir;

		setContextClassLoader(getClass().getClassLoader());
		setDaemon(true);
		setPriority(MIN_PRIORITY);
	}

	public void pause() {
		_started = false;
	}

	@Override
	public void run() {
		try {
			sleep(1000 * 10);
		}
		catch (InterruptedException ie) {
		}

		while (_started) {
			try {
				sleep(_sandboxDeployDir.getInterval());
			}
			catch (InterruptedException ie) {
			}

			_sandboxDeployDir.scanDirectory();
		}
	}

	private final SandboxDeployDir _sandboxDeployDir;
	private boolean _started = true;

}