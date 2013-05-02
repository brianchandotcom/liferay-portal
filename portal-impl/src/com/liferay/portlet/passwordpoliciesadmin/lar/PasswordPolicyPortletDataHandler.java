/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.passwordpoliciesadmin.lar;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.lar.BasePortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.PasswordPolicy;
import com.liferay.portal.service.PasswordPolicyLocalServiceUtil;
import com.liferay.portal.service.persistence.PasswordPolicyActionableDynamicQuery;

import java.util.List;

import javax.portlet.PortletPreferences;

/**
 * @author Daniela Zapata Riesco
 */
public class PasswordPolicyPortletDataHandler extends BasePortletDataHandler {

	public static final String NAMESPACE = "password_policies_admin";

	@Override
	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		if (portletDataContext.addPrimaryKey(
			PasswordPolicyPortletDataHandler.class, "deleteData")) {

			return portletPreferences;
		}

		List<PasswordPolicy> passwordPolicies =
			PasswordPolicyLocalServiceUtil.getPasswordPolicies(
				portletDataContext.getCompanyId());

		for (PasswordPolicy passwordPolicy : passwordPolicies) {
			if (!passwordPolicy.isDefaultPolicy()) {
				PasswordPolicyLocalServiceUtil.deletePasswordPolicy(
					passwordPolicy);
			}
		}

		return portletPreferences;
	}

	@Override
	protected String doExportData(
			final PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		portletDataContext.addPermissions(
			"com.liferay.portlet.passwordpoliciesadmin",
			portletDataContext.getScopeGroupId());

		Element rootElement = addExportDataRootElement(portletDataContext);

		rootElement.addAttribute(
			"group-id", String.valueOf(portletDataContext.getScopeGroupId()));

		ActionableDynamicQuery actionableDynamicQuery =
			new PasswordPolicyActionableDynamicQuery() {

			@Override
			protected void addCriteria(DynamicQuery dynamicQuery) {
				portletDataContext.addDateRangeCriteria(
					dynamicQuery, "modifiedDate");
			}

			@Override
			protected void performAction(Object object) throws PortalException {
				PasswordPolicy passwordPolicy = (PasswordPolicy)object;

				StagedModelDataHandlerUtil.exportStagedModel(
					portletDataContext, passwordPolicy);
			}
		};

		actionableDynamicQuery.setGroupId(portletDataContext.getScopeGroupId());

		actionableDynamicQuery.performActions();

		return getExportDataRootElementString(rootElement);
	}

	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		portletDataContext.importPermissions(
			"com.liferay.portlet.passwordpoliciesadmin",
			portletDataContext.getSourceGroupId(),
			portletDataContext.getScopeGroupId());

		Element passwordPoliciesElement =
			portletDataContext.getImportDataGroupElement(
				PasswordPolicy.class);

		List<Element> passwordPolicyElements =
			passwordPoliciesElement.elements();

		for (Element passwordPolicyElement : passwordPolicyElements) {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, passwordPolicyElement);
		}

		return null;
	}

}