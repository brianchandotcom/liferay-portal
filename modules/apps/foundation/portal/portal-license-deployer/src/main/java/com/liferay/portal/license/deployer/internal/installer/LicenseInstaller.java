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

package com.liferay.portal.license.deployer.internal.installer;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.license.util.LicenseManagerUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import java.io.File;

import org.apache.felix.fileinstall.ArtifactInstaller;

/**
 * @author Amos Fong
 */
public class LicenseInstaller implements ArtifactInstaller {

	@Override
	public boolean canHandle(File artifact) {
		String extension = FileUtil.getExtension(artifact.getName());

		if (!extension.equals("xml")) {
			return false;
		}

		try {
			String content = FileUtil.read(artifact);

			Document document = SAXReaderUtil.read(content);

			Element rootElement = document.getRootElement();

			String rootElementName = rootElement.getName();

			if (rootElementName.equals("license")) {
				return true;
			}
		}
		catch (Exception e) {
		}

		return false;
	}

	@Override
	public void install(File file) throws Exception {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		String content = FileUtil.read(file);

		jsonObject.put("licenseXML", content);

		LicenseManagerUtil.registerLicense(jsonObject);
	}

	@Override
	public void uninstall(File file) throws Exception {
	}

	@Override
	public void update(File file) throws Exception {
	}

}