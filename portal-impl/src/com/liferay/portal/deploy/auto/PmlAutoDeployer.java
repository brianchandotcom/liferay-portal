/*******************************************************************************
 * Copyright (c) 2000-2011 Accenture Services Pvt. Ltd., All rights reserved.
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
 *
 * Contributors:
 *    Kamesh Sampath - initial implementation
 ******************************************************************************/
package com.liferay.portal.deploy.auto;

import com.liferay.portal.kernel.deploy.auto.AutoDeployException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.tools.deploy.PmlDeployer;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;

import java.io.File;

/**
 *
 * @author <a href="mailto:kamesh.sampath@accenture.com">kamesh.sampath</a>
 *
 */
public class PmlAutoDeployer extends PmlDeployer implements AutoDeployer {

	/**
	 * (non-Javadoc)
	 *
	 * @see com.liferay.portal.deploy.auto.AutoDeployer#autoDeploy(java.io.File,
	 *      java.lang.String)
	 */
	@Override
	public void autoDeploy(File file, String context)
			throws AutoDeployException {
		_log.info("PmlAutoDeployer Reading content as String");
		try {
			pmlFileContent = FileUtil.read(file);
			deploy();

		} catch (Exception e) {
			throw new AutoDeployException(e);
		}

	}

	/**
	 *
	 * @param file
	 * @throws AutoDeployException
	 */
	public void autoDeploy(String file) throws Exception {

		File pmlFile = new File(PrefsPropsUtil.getString(
				PropsKeys.AUTO_DEPLOY_DEPLOY_DIR,
				PropsValues.AUTO_DEPLOY_DEPLOY_DIR), file);
		autoDeploy(pmlFile, null);
		_log.info("Successfully processed file");

	}

	private static Log _log = LogFactoryUtil.getLog(PmlAutoDeployer.class);
}