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

package com.liferay.blogs.web.blogsaggregator.lar;

import com.liferay.blogs.web.constants.BlogsPortletKeys;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.OrganizationLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.persistence.OrganizationUtil;
import com.liferay.portlet.exportimport.lar.DataLevel;
import com.liferay.portlet.exportimport.lar.DefaultConfigurationPortletDataHandler;
import com.liferay.portlet.exportimport.lar.PortletDataContext;
import com.liferay.portlet.exportimport.lar.PortletDataHandler;

import java.util.Map;

import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Component;

/**
 * @author Julio Camarero
 */
@Component(
	property = {
		"javax.portlet.name=" + BlogsPortletKeys.BLOGS_AGGREGATOR,
		"javax.portlet.name=" + BlogsPortletKeys.RECENT_BLOGGERS
	},
	service = PortletDataHandler.class
)
public class BlogsAggregatorPortletDataHandler
	extends DefaultConfigurationPortletDataHandler {

	public BlogsAggregatorPortletDataHandler() {
		setDataLevel(DataLevel.PORTLET_INSTANCE);
		setPublishToLiveByDefault(true);
	}

	@Override
	protected PortletPreferences doProcessExportPortletPreferences(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		return updateExportPortletPreferences(
			portletDataContext, portletId, portletPreferences);
	}

	@Override
	protected PortletPreferences doProcessImportPortletPreferences(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		return updateImportPortletPreferences(
			portletDataContext, portletId, portletPreferences);
	}

	@Override
	protected String getExportPortletPreferencesUuid(
			PortletDataContext portletDataContext, Portlet portlet,
			String className, long primaryKeyLong)
		throws Exception {

		String uuid = null;

		Element rootElement = portletDataContext.getExportDataRootElement();

		if (className.equals(Organization.class.getName())) {
			Organization organization =
				OrganizationLocalServiceUtil.fetchOrganization(primaryKeyLong);

			if (organization != null) {
				uuid = organization.getUuid();

				portletDataContext.addReferenceElement(
					portlet, rootElement, organization,
					PortletDataContext.REFERENCE_TYPE_DEPENDENCY, true);
			}
		}

		return uuid;
	}

	@Override
	protected Long getImportPortletPreferencesNewPrimaryKey(
			PortletDataContext portletDataContext, Class<?> clazz,
			long companyGroupId, Map<Long, Long> primaryKeys, String uuid)
		throws Exception {

		if (Validator.isNumber(uuid)) {
			long oldPrimaryKey = GetterUtil.getLong(uuid);

			return MapUtil.getLong(primaryKeys, oldPrimaryKey, oldPrimaryKey);
		}

		String className = clazz.getName();

		if (className.equals(Organization.class.getName())) {
			Organization organization = OrganizationUtil.fetchByUuid_C_First(
				uuid, portletDataContext.getCompanyId(), null);

			if (organization != null) {
				return organization.getOrganizationId();
			}
		}

		return null;
	}

	protected PortletPreferences updateExportPortletPreferences(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		long organizationId = GetterUtil.getLong(
			portletPreferences.getValue("organizationId", null));

		if (organizationId > 0) {
			Portlet portlet = PortletLocalServiceUtil.getPortletById(
				portletDataContext.getCompanyId(), portletId);

			updateExportPortletPreferencesClassPKs(
				portletDataContext, portlet, portletPreferences,
				"organizationId", Organization.class.getName());
		}

		return portletPreferences;
	}

	protected PortletPreferences updateImportPortletPreferences(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		long organizationId = GetterUtil.getLong(
			portletPreferences.getValue("organizationId", null));

		if (organizationId > 0) {
			Company company = CompanyLocalServiceUtil.getCompanyById(
				portletDataContext.getCompanyId());

			Group companyGroup = company.getGroup();

			updateImportPortletPreferencesClassPKs(
				portletDataContext, portletPreferences, "organizationId",
				Organization.class, companyGroup.getGroupId());
		}

		return portletPreferences;
	}

}