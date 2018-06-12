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

package com.liferay.commerce.initializer.customer.portal.internal;

import com.liferay.commerce.organization.constants.CommerceOrganizationConstants;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.search.IndexStatusManagerThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowThreadLocal;

import java.math.BigDecimal;

import java.net.URL;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 */
public abstract class BaseCustomerPortalSampleInitializer {

	public void initialize(long groupId) throws Exception {
		Group group = groupLocalService.getGroup(groupId);

		ExportImportThreadLocal.setPortletImportInProcess(true);
		IndexStatusManagerThreadLocal.setIndexReadOnly(true);
		WorkflowThreadLocal.setEnabled(false);

		try {
			_initialize(group);
		}
		finally {
			ExportImportThreadLocal.setPortletImportInProcess(false);
			IndexStatusManagerThreadLocal.setIndexReadOnly(false);
			WorkflowThreadLocal.setEnabled(true);
		}

		try (LoggingTimer loggingTimer = new LoggingTimer("reindex")) {
			reindex(group);
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	protected void cleanUp(Group group, long[] accountOrganizationIds)
		throws PortalException {
	}

	protected BigDecimal getBigDecimal(JSONObject jsonObject, String key) {
		String value = jsonObject.getString(key);

		if (Validator.isNull(value)) {
			return null;
		}

		return new BigDecimal(value);
	}

	protected abstract Log getLog();

	protected abstract String getName();

	protected abstract void importSample(
			long now, JSONObject jsonObject, Group group,
			long[] accountOrganizationIds, Map<String, Long> cpInstanceSKUsMap)
		throws PortalException;

	protected void reindex(Group group) throws PortalException {
	}

	@Reference
	protected CPInstanceLocalService cpInstanceLocalService;

	@Reference
	protected GroupLocalService groupLocalService;

	@Reference
	protected JSONFactory jsonFactory;

	@Reference
	protected OrganizationLocalService organizationLocalService;

	private long[] _getAccountOrganizationIds(Group group)
		throws PortalException {

		Organization organization = organizationLocalService.getOrganization(
			group.getOrganizationId());

		List<Organization> descendantOrganizations =
			organization.getDescendants();

		List<Long> organizationIds = new ArrayList<>(
			descendantOrganizations.size());

		for (Organization descendantOrganization : descendantOrganizations) {
			if (CommerceOrganizationConstants.TYPE_ACCOUNT.equals(
					descendantOrganization.getType())) {

				organizationIds.add(descendantOrganization.getOrganizationId());
			}
		}

		return ArrayUtil.toLongArray(organizationIds);
	}

	private Map<String, Long> _getCPInstanceSKUsMap(Group group)
		throws PortalException {

		final Map<String, Long> cpInstanceSKUsMap = new HashMap<>();

		ActionableDynamicQuery actionableDynamicQuery =
			cpInstanceLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setGroupId(group.getGroupId());
		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<CPInstance>() {

				public void performAction(CPInstance cpInstance)
					throws PortalException {

					cpInstanceSKUsMap.put(
						cpInstance.getSku(), cpInstance.getCPInstanceId());
				}

			});

		actionableDynamicQuery.performActions();

		return cpInstanceSKUsMap;
	}

	private void _initialize(Group group) throws Exception {
		Log log = getLog();

		long[] accountOrganizationIds = _getAccountOrganizationIds(group);
		Map<String, Long> cpInstanceSKUsMap = _getCPInstanceSKUsMap(group);

		if (ArrayUtil.isEmpty(accountOrganizationIds) ||
			cpInstanceSKUsMap.isEmpty()) {

			if (log.isInfoEnabled()) {
				log.info("Skipping import on group " + group.getGroupId());
			}

			return;
		}

		cleanUp(group, accountOrganizationIds);

		long now = System.currentTimeMillis();

		Bundle bundle = _bundleContext.getBundle();

		Enumeration<URL> enumeration = bundle.findEntries(
			CustomerPortalSiteInitializer.DEPENDENCY_PATH + getName(),
			"*.json", false);

		while (enumeration.hasMoreElements()) {
			URL url = enumeration.nextElement();

			String name = url.getFile();

			name = name.substring(name.lastIndexOf(CharPool.SLASH) + 1);

			try (LoggingTimer loggingTimer = new LoggingTimer(name)) {
				String json = StringUtil.read(url.openStream());

				JSONArray jsonArray = jsonFactory.createJSONArray(json);

				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);

					importSample(
						now, jsonObject, group, accountOrganizationIds,
						cpInstanceSKUsMap);
				}
			}
		}
	}

	private BundleContext _bundleContext;

}