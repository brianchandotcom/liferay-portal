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

package com.liferay.portal.kernel.deploy.auto;

import com.liferay.portal.kernel.deploy.auto.context.AutoDeploymentContext;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.File;
import java.io.IOException;

/**
 * @author Ivica Cardic
 * @author Brian Wing Shun Chan
 * @author Ryan Park
 * @author Manuel de la Peña
 */
public abstract class BaseAutoDeployListener implements AutoDeployListener {

	@Override
	public final int deploy(AutoDeploymentContext autoDeploymentContext)
		throws AutoDeployException {

		File file = autoDeploymentContext.getFile();

		if (_log.isDebugEnabled()) {
			_log.debug("Invoking deploy for " + file.getPath());
		}

		if (_log.isInfoEnabled()) {
			_log.info(getPluginPathInfoMessage(file));
		}

		try (AutoDeployer autoDeployer = buildAutoDeployer()) {
			int code = autoDeployer.autoDeploy(autoDeploymentContext);

			if ((code == AutoDeployer.CODE_DEFAULT) && _log.isInfoEnabled()) {
				_log.info(getSuccessMessage(file));
			}

			return code;
		}
		catch (IOException ioe) {
			throw new AutoDeployException(ioe);
		}
	}

	@Override
	public boolean isDeployable(AutoDeploymentContext autoDeploymentContext)
		throws AutoDeployException {

		return isDeployable(autoDeploymentContext.getFile());
	}

	protected abstract AutoDeployer buildAutoDeployer()
		throws AutoDeployException;

	protected abstract String getPluginPathInfoMessage(File file);

	protected abstract String getSuccessMessage(File file);

	protected abstract boolean isDeployable(File file)
		throws AutoDeployException;

	private static final Log _log = LogFactoryUtil.getLog(
		BaseAutoDeployListener.class);

}