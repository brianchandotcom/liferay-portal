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

import com.liferay.portal.LayoutFriendlyURLException;
import com.liferay.portal.NoSuchCountryException;
import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.NoSuchOrganizationException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Country;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.OrganizationConstants;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.CountryServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.OrganizationLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.ResourceLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portlet.PortletPreferencesFactoryUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import javax.portlet.PortletPreferences;

/**
 * @author <a href="mailto:kamesh.sampath@accenture.com">kamesh.sampath</a>
 *
 */
public class PmlWorker implements PmlWorkable {
	private static Log _log = LogFactoryUtil.getLog(PmlWorker.class);

	/**
	 *
	 * @param root
	 * @return
	 * @throws SystemException
	 */
	public long createCompany(Element root) throws Exception {
		// TODO
		List<Company> companies = CompanyLocalServiceUtil.getCompanies();
		Company company = companies.get(0);
		long companyId = company.getCompanyId();
		createDefaultUser(companyId);
		return companyId;
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see com.liferay.portal.tools.deploy.PmlWorkable#createDefaultUser(long)
	 */
	@Override
	public User createDefaultUser(long companyId) {
		// TODO
		return null;
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see
	 * com.liferay.portal.tools.deploy.PmlWorkable#processSites(com.liferay.
	 * portal.kernel.xml.Element)
	 */
	@Override
	public void processSites(long companyId, long defaultUserId, Element root)
			throws Exception {
		System.out.println("Creating Communites");
		Group guesCommunity = GroupLocalServiceUtil.getGroup(companyId,
				GroupConstants.GUEST);
		List<Element> sites = root.elements("site");
		final String className = guesCommunity.getClassName();
		final long classPK = guesCommunity.getClassPK();

		System.out.println("Community Class Name:" + className + " and classPK"
				+ classPK);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		for (Element eCommunity : sites) {
			String name = eCommunity.attributeValue("name");
			String friendlyURL = eCommunity.attributeValue("friendly-url");
			String description = "";

			// Create the Community
			Group community = null;
			try {
				community = GroupLocalServiceUtil.getGroup(companyId, name);
			} catch (NoSuchGroupException e) {
				community = GroupLocalServiceUtil.addGroup(defaultUserId,
						className, classPK, name, description,
						GroupConstants.TYPE_SITE_OPEN, StringPool.BLANK, true,
						true, serviceContext);
				System.out.println("Successfully created community"
						+ community.getGroupId());
			}

			// update Friendly URL
			if (friendlyURL != null) {
				community.setFriendlyURL(friendlyURL);
			}

			// Create the pages
			serviceContext.setScopeGroupId(community.getGroupId());

			// Create Community Pages
			List<Element> pages = eCommunity.elements("page");
			if (pages != null && !pages.isEmpty()) {
				// System.out.println("Creating pages for Commnity:" +
				// name);
				for (Element page : pages) {
					addAndConfigurePage(community, page, false);
				}
			}
		}

	}

	/**
	 * (non-Javadoc)
	 *
	 * @see
	 * com.liferay.portal.tools.deploy.PmlWorkable#createOrganizationHierachy
	 * (long, long, long, com.liferay.portal.kernel.xml.Element, java.util.Map)
	 */
	@Override
	public void createOrganizationHierachy(long companyId, long defaultUserId,
			long parentOrganizationId, Element eOrganization,
			Map<String, Long> organizationMap) throws Exception {
		_log.info("Creating Organizations");
		String name = eOrganization.attributeValue("name");
		String type = eOrganization.attributeValue("type");
		String friendlyUrl = eOrganization.attributeValue("friendly-url") == null ? name
				: eOrganization.attributeValue("friendly-url");
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

		Group group = organization.getGroup();

		// GroupLocalServiceUtil
		// .updateFriendlyURL(group.getGroupId(), friendlyUrl);

		serviceContext.setScopeGroupId(group.getGroupId());

		organizationMap.put(name, organization.getOrganizationId());

		// System.out.println("Successfully created Organization with Org.Id:  "
		// + organization.getOrganizationId() + "CompanyId:"
		// + organization.getCompanyId() + "and Group Id: "
		// + group.getGroupId());
		// Create Organization Pages
		List<Element> pages = eOrganization.elements("page");
		if (pages != null && !pages.isEmpty()) {
			// System.out.println("Creating pages for Organization:" + name);
			for (Element page : pages) {
				addAndConfigurePage(group, page, false);
			}
		}

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

	/**
	 * (non-Javadoc)
	 *
	 * @see
	 * com.liferay.portal.tools.deploy.PmlWorkable#addAndConfigurePage(com.liferay
	 * .portal.model.Group, com.liferay.portal.kernel.xml.Element, boolean)
	 */
	@Override
	public void addAndConfigurePage(Group group, Element page,
			boolean privateLayout) throws Exception {
		String name = page.attributeValue("name");
		String friendlyURL = page.attributeValue("friendly-url");
		String layouteTemplateId = page.attributeValue("layout");
		String strHidden = page.attributeValue("hidden");
		String type = LayoutConstants.TYPE_PORTLET;

		try {

			if (page.element("webcontent") != null) {
				type = LayoutConstants.TYPE_ARTICLE;
			} else if (page.element("URL") != null) {
				type = LayoutConstants.TYPE_URL;
			} else if (page.element("EMBEDDED") != null) {
				type = LayoutConstants.TYPE_EMBEDDED;
			} else if (page.element("LinkToPage") != null) {
				type = LayoutConstants.TYPE_LINK_TO_LAYOUT;
			}

			Layout layout = addPage(group, name, privateLayout,
					layouteTemplateId);

			// Add Portlets
			if (LayoutConstants.TYPE_PORTLET.equals(type)) {
				Element portlets = page.element("portlets");
				addPortlets(layout, layouteTemplateId, portlets);
			}

			// Set the Friendly URL
			try {
				LayoutLocalServiceUtil.updateFriendlyURL(layout.getPlid(),
						friendlyURL);
			} catch (LayoutFriendlyURLException e) {
				System.out
						.println(friendlyURL
								+ " friendly URL already set, skipping update of friendly URL");
			}

		} catch (Exception e) {
			throw e;
		}

		// TODO handle child pages

	}

	/**
	 *
	 * @param group
	 * @param name
	 * @param privateLayout
	 * @param friendlyURL
	 * @param layouteTemplateId
	 * @return
	 * @throws Exception
	 */
	protected Layout addPage(Group group, String name, boolean privateLayout,
			String layouteTemplateId) throws Exception {

		long groupId = group.getGroupId();
		long creatorUserId = group.getCreatorUserId();

		ServiceContext serviceContext = new ServiceContext();

		// Check if layout already exists
		List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(groupId,
				privateLayout);
		// System.out.println("Number of pages for Group:" + groupId + " :"
		// + layouts.size());
		for (Layout layout : layouts) {
			String lName = layout.getNameCurrentValue();
			// System.out.println("PmlWorker.addPage(): Page Name:" + lName);
			if (lName.equalsIgnoreCase(name)) {
				System.out.println("Page:" + name
						+ " already exists, skipping creation");
				return layout;
			}
		}

		Layout layout = LayoutLocalServiceUtil.addLayout(creatorUserId,
				groupId, privateLayout,
				LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, name,
				StringPool.BLANK, StringPool.BLANK,
				LayoutConstants.TYPE_PORTLET, false, StringPool.BLANK, false,
				serviceContext);

		LayoutTypePortlet layoutTypePortlet = (LayoutTypePortlet) layout
				.getLayoutType();

		layoutTypePortlet.setLayoutTemplateId(0, layouteTemplateId, false);

		addResources(layout, PortletKeys.DOCKBAR);

		return layout;
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see
	 * com.liferay.portal.tools.deploy.PmlWorkable#addPortlets(com.liferay.portal
	 * .model.Layout, java.lang.String, java.util.List)
	 */
	@Override
	public void addPortlets(Layout layout, String layouteTemplateId,
			Element portletsGroup) throws Exception {
		List<Element> portlets = portletsGroup.elements("portlet");
		List<Portlet> portletIds = new ArrayList<Portlet>();
		for (Element ePortlet : portlets) {
			String portletId = ePortlet.attributeValue("name");
			System.out.println("Portlet Id from PML:" + portletId);
			String columnId = ePortlet.attributeValue("layout-column");

			// TODO:Check for WAR portlets too and any logic for instantication

			// Get the Portlet from the Portlet Repo
			Portlet portlet = PortletLocalServiceUtil.getPortletById(portletId);
			portletId = portlet.getPortletId();
			System.out.println("Portlet Id from Repo:" + portletId);

			// Add Portlet to the layout
			portletId = addPortletId(layout, portletId, columnId);
			System.out.println("Successfully added Portlet " + portletId
					+ " to page");
			setupDefaultPreferencesForPortlet(layout, portletId);
			portletIds.add(portlet);
		}

		/*
		 * Clear any non existent portlets from the current page this might
		 * happen when the PML is updated and republished
		 */
		if (!portletIds.isEmpty()) {
			// TODO:Implement it with prermission check
			// clearRemovedPortlets(layout, portletIds);
		}

	}

	/**
	 * Clear any exising porlets that are not part of the updated layout
	 *
	 * @param layout
	 * @param currentPortlets
	 * @throws Exception
	 */
	private void clearRemovedPortlets(Layout layout,
			List<Portlet> currentPortlets) throws Exception {
		System.out.println("PmlWorker.clearRemovedPortlets():"
				+ layout.getNameCurrentValue());
		LayoutTypePortlet layoutTypePortlet = (LayoutTypePortlet) layout
				.getLayoutType();
		List<Portlet> allPortlets = layoutTypePortlet.getAllPortlets();
		allPortlets.removeAll(currentPortlets);
		for (Portlet portlet : allPortlets) {
			if (layoutTypePortlet.hasPortletId(portlet.getPortletId())) {
				System.out
						.println("PmlWorker.clearRemovedPortlets():Removing Portlet"
								+ portlet.getPortletId());
				layoutTypePortlet.removePortletId(0, portlet.getPortletId(),
						true);
			}
		}

	}

	public void setupDefaultPreferencesForPortlet(Layout layout,
			String portletId) throws Exception {
		PortletPreferences portletSetup = PortletPreferencesFactoryUtil
				.getLayoutPortletSetup(layout, portletId);

		portletSetup.setValue("portlet-setup-show-borders",
				String.valueOf(Boolean.FALSE));

		portletSetup.store();
	}

	/**
	 * @param layout
	 * @param portletId
	 * @param columnId
	 * @throws PortalException
	 * @throws SystemException
	 * @throws Exception
	 */
	private String addPortletId(Layout layout, String portletId, String columnId)
			throws PortalException, SystemException, Exception {

		System.out.println("Adding portlet:" + portletId + " to column:"
				+ columnId);

		LayoutTypePortlet layoutTypePortlet = (LayoutTypePortlet) layout
				.getLayoutType();

		List<Portlet> portletsOfColumn = layoutTypePortlet
				.getAllPortlets(columnId);
		boolean updateCol = true;

		for (Portlet portlet : portletsOfColumn) {
			String colPortletId = portlet.getPortletId();
			if (portletId.equals(colPortletId)) {
				return portletId;
			} else {
				updateCol = true;
			}

		}

		if (layoutTypePortlet.hasPortletId(portletId)) {
			System.out.println("Porlet:" + portletId
					+ " already exist in page:" + layout.getNameCurrentValue()
					+ ", updating its column!");
			if (updateCol) {
				layoutTypePortlet.setPortletIds(columnId, portletId);
			}

		} else {
			portletId = layoutTypePortlet.addPortletId(0, portletId, columnId,
					-1, false);
			addResources(layout, portletId);
		}

		updateLayout(layout);
		return portletId;
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see
	 * com.liferay.portal.tools.deploy.PmlWorkable#addResources(com.liferay.
	 * portal.model.Layout, java.lang.String)
	 */
	@Override
	public void addResources(Layout layout, String portletId) throws Exception {
		// System.out.println("Layout Company Id:" + layout.getCompanyId());
		// System.out.println("Layout Group Id:" + layout.getGroupId());
		String rootPortletId = PortletConstants.getRootPortletId(portletId);

		String portletPrimaryKey = PortletPermissionUtil.getPrimaryKey(
				layout.getPlid(), portletId);

		ResourceLocalServiceUtil.addResources(layout.getCompanyId(),
				layout.getGroupId(), 0, rootPortletId, portletPrimaryKey, true,
				true, true);

	}

	/**
	 * (non-Javadoc)
	 *
	 * @see
	 * com.liferay.portal.tools.deploy.PmlWorkable#updateLayout(com.liferay.
	 * portal.model.Layout)
	 */
	@Override
	public void updateLayout(Layout layout) throws Exception {
		LayoutLocalServiceUtil.updateLayout(layout.getGroupId(),
				layout.isPrivateLayout(), layout.getLayoutId(),
				layout.getTypeSettings());

	}

}