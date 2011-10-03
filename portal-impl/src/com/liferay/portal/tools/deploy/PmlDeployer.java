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
package com.liferay.portal.tools.deploy;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.OrganizationConstants;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.InitUtil;

import java.io.File;
import java.io.IOException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author <a href="mailto:kamesh.sampath@accenture.com">kamesh.sampath</a>
 *
 */
public class PmlDeployer extends PmlWorker {

	private static Log _log = LogFactoryUtil.getLog(PmlDeployer.class);

	public static void main(String[] args) throws Exception {

		try {
			InitUtil.initWithSpring();

			File pmlFile = new File(args[0]);

			PmlDeployer deployer = new PmlDeployer(pmlFile);
			deployer.deploy();
		} catch (IOException e) {
			_log.error("Error while deploying PML", e);
		}

	}

	/**
	 *
	 */
	protected PmlDeployer() {

	}

	/**
	 *
	 * @param file
	 * @throws IOException
	 */
	protected PmlDeployer(File pmlFile) throws IOException {
		this.pmlFileContent = FileUtil.read(pmlFile);
	}

	protected PmlDeployer(String pmlFileContent) {
		this.pmlFileContent = pmlFileContent;
	}

	public void deploy() throws Exception {
		_log.info("Deploying PML");
		parseAndProcess();
	}

	/**
	 *
	 * @param pmlFile
	 * @throws DocumentException
	 */
	private void parseAndProcess() throws Exception {
		_log.info("Parsing and Processing the PML");
		Document pmlDocument = SAXReaderUtil.read(this.pmlFileContent);
		Element root = pmlDocument.getRootElement();

		long companyId = createCompany(root);

		long defaultUserId = UserLocalServiceUtil.getDefaultUserId(companyId);

		Element sites = root.element("sites");
		if (sites != null) {
			processSites(companyId, defaultUserId, sites);
		}

		Element organizations = root.element("organizations");
		if (organizations != null) {
			processOrganizations(companyId, defaultUserId, organizations);
		}

	}

	/**
	 *
	 * @param defaultUserId
	 * @param companyId
	 * @param defaultCompanyId
	 * @param defaultUserId
	 * @param root
	 */
	private Map<String, Long> processOrganizations(long companyId,
			long defaultUserId, Element eOrganizations) throws Exception {
		Map<String, Long> organizationMap = new HashMap<String, Long>();
		_log.info("Processing Organizations for Portal:");
		long userId = defaultUserId;
		long parentOrganizationId = OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID;

		List<Element> listOfeOrganization = eOrganizations
				.elements("organization");

		for (Element org : listOfeOrganization) {
			createOrganizationHierachy(companyId, userId, parentOrganizationId,
					org, organizationMap);
		}

		return organizationMap;

	}

	protected String pmlFileContent;

}