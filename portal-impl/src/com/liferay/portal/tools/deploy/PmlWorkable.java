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

import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.User;

import java.util.Map;

/**
 * @author <a href="mailto:kamesh.sampath@accenture.com">kamesh.sampath</a>
 *
 */
public interface PmlWorkable {

	/**
	 *
	 * @param root
	 * @return
	 * @throws Exception
	 */
	public long createCompany(Element root) throws Exception;

	/**
	 *
	 * @param companyId
	 * @return
	 */
	User createDefaultUser(long companyId);

	/**
	 *
	 * @param root
	 * @throws Exception
	 */
	void processSites(long companyId, long defaultUserId, Element root)
			throws Exception;

	/**
	 *
	 * @param companyId
	 * @param defaultUserId
	 * @param parentOrganizationId
	 * @param eOrganization
	 * @param organizationMap
	 * @throws Exception
	 */
	void createOrganizationHierachy(long companyId, long defaultUserId,
			long parentOrganizationId, Element eOrganization,
			Map<String, Long> organizationMap) throws Exception;

	/**
	 *
	 * @param group
	 * @param page
	 * @param privateLayout
	 * @throws Exception
	 */
	void addAndConfigurePage(Group group, Element page, boolean privateLayout)
			throws Exception;

	/**
	 *
	 * @param layout
	 * @param layouteTemplateId
	 * @param portletsGroup
	 * @throws Exception
	 */
	void addPortlets(Layout layout, String layouteTemplateId,
			Element portletsGroup) throws Exception;

	/**
	 *
	 * @param layout
	 * @param portletId
	 * @throws Exception
	 */
	void addResources(Layout layout, String portletId) throws Exception;

	/**
	 *
	 * @param layout
	 * @throws Exception
	 */
	void updateLayout(Layout layout) throws Exception;

}