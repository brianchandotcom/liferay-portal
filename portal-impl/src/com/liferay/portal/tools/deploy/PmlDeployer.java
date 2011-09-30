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

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;
import java.util.Vector;
import java.util.Map;

import com.liferay.portal.NoSuchCountryException;
import com.liferay.portal.NoSuchOrganizationException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Country;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.OrganizationConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.CountryServiceUtil;
import com.liferay.portal.service.OrganizationLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.InitUtil;
import com.liferay.portal.util.PropsUtil;

/**
 * 
 * @author <a href="mailto:kamesh.sampath@accenture.com">kamesh.sampath</a>
 * 
 */
public class PmlDeployer {

	public static void main(String[] args) throws Exception {

		try {
			InitUtil.initWithSpring();

			File pmlFile = new File(args[0]);

			PmlDeployer deployer = new PmlDeployer(pmlFile);
			deployer.deploy();
		} catch (IOException e) {
			_log.error(e);
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

		processSites(root);

		Map<String, Long> organizationMap = processOrganizations(companyId,
				defaultUserId, root);
		
		

	}

	/**
	 * 
	 * @param root
	 * @return
	 * @throws SystemException
	 */
	private long createCompany(Element root) throws SystemException {
		_log.info("PmlDeployer.createCompany()");
		List<Company> companies = CompanyLocalServiceUtil.getCompanies();
		Company company = companies.get(0);
		long companyId = company.getCompanyId();
		createDefaultUser(companyId);
		return companyId;
	}

	/**
	 * 
	 * @param companyId
	 * @return
	 */
	private User createDefaultUser(long companyId) {
		_log.info("PmlDeployer.validateAndCreateUser()");
		// TODO
		return null;
	}

	/**
	 * 
	 * @param root
	 */
	private Vector<Long> processSites(Element root) throws Exception {
		Vector<Long> siteIds = new Vector<Long>();
		_log.info("Processing Communites for Portal:"
				+ root.attributeValue("id"));
		return siteIds;

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
			long defaultUserId, Element root) throws Exception {
		Map<String, Long> organizationMap = new HashMap<String, Long>();
		_log.info("Processing Organizations for Portal:"
				+ root.attributeValue("id"));
		long userId = defaultUserId;
		long parentOrganizationId = OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID;

		Element eOrganizations = root.element("organizations");
		List<Element> listOfeOrganization = eOrganizations
				.elements("organization");

		for (Element org : listOfeOrganization) {
			createOrganizationHierachy(companyId, userId, parentOrganizationId,
					org, organizationMap);
		}

		return organizationMap;

	}

	/**
	 * 
	 * @param companyId
	 * @param defaultUserId
	 * @param parentOrganizationId
	 * @param eOrganization
	 * @param organizationMap
	 * @throws Exception
	 */
	private void createOrganizationHierachy(long companyId, long defaultUserId,
			long parentOrganizationId, Element eOrganization,
			Map<String, Long> organizationMap) throws Exception {

		String name = eOrganization.attributeValue("name");
		String type = eOrganization.attributeValue("type");
		boolean recursable = true;
		long regionId = 0;
		long countryId = 0;
		String comments = null;
		int statusId = GetterUtil
				.getInteger(PropsUtil
						.get("sql.data.com.liferay.portal.model.ListType.organization.status"));

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		if (OrganizationConstants.TYPE_LOCATION.equals(type)) {
			try {
				Country country = CountryServiceUtil
						.getCountryByName(eOrganization
								.attributeValue("location-country"));
				countryId = country.getCountryId();
			} catch (NoSuchCountryException e) {
				// nothing to do leave the country to default
			}

		}

		// Check if the Organizaiton already exist
		Organization organization = null;
		try {
			organization = OrganizationLocalServiceUtil.getOrganization(
					companyId, name);
		} catch (NoSuchOrganizationException e) {
			organization = OrganizationLocalServiceUtil.addOrganization(
					defaultUserId, parentOrganizationId, name, type,
					recursable, regionId, countryId, statusId, comments, true,
					serviceContext);

		}
		organizationMap.put(name, organization.getOrganizationId());
		// Check for child Organizations
		Element eChildOrganizations = eOrganization.element("organizations");
		if (eChildOrganizations != null) {
			parentOrganizationId = organization.getOrganizationId();
			Stack<Element> childOrganizationStack = new Stack<Element>();
			childOrganizationStack.addAll(eChildOrganizations
					.elements("organization"));
			while (!childOrganizationStack.isEmpty()) {
				createOrganizationHierachy(companyId, defaultUserId,
						parentOrganizationId, childOrganizationStack.pop(),
						organizationMap);
			}
		}

	}

	// TODO more methods to come
	private static Log _log = LogFactoryUtil.getLog(PmlDeployer.class);
	protected String pmlFileContent;

}