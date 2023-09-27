/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		catch (IOException ioException) {
			throw new AutoDeployException(ioException);
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