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

import java.io.File;

import com.liferay.portal.kernel.deploy.auto.AutoDeployException;
import com.liferay.portal.kernel.deploy.auto.AutoDeployListener;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

/**
 * @author <a href="mailto:kamesh.sampath@accenture.com">kamesh.sampath</a>
 * 
 */
public class PmlAutoDeployListener implements AutoDeployListener {

	public PmlAutoDeployListener() {
		_log.info("Om Shakti");
		pmlAutoDeployer = new PmlAutoDeployer();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.liferay.portal.kernel.deploy.auto.AutoDeployListener#deploy(java.
	 *      io.File, java.lang.String)
	 */
	public void deploy(File file, String context) throws AutoDeployException {

		if (isPmlFile(file)) {
			_log.info("Processing Portal Markup Language File");

			try {
				pmlAutoDeployer.autoDeploy(file.getName());
			} catch (Exception e) {
				_log.error("Unable to deploy file:" + file.getName(), e);
				throw new AutoDeployException(e);
			}
		} else {
			return;
		}

	}

	/**
	 * @param file
	 */
	private boolean isPmlFile(File file) {
		String extension = FileUtil.getExtension(file.getName());

		if (extension.equals("pml")) {

			try {
				String pmlFileContent = FileUtil.read(file);

				Document document = SAXReaderUtil.read(pmlFileContent);

				Element rootElement = document.getRootElement();

				String rootElementName = rootElement.getName();

				if (rootElementName.equals("portal")) {
					return true;
				}
			} catch (Exception e) {
				_log.error("Error deploying file" + file.getPath(), e);
			}
		} else {
			return false;
		}
		return false;
	}

	private static Log _log = LogFactoryUtil
			.getLog(PmlAutoDeployListener.class);
	private PmlAutoDeployer pmlAutoDeployer;

}