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

package com.liferay.portal.verify;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Organization;
import com.liferay.portal.service.OrganizationLocalServiceUtil;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Daniel Kocsis
 */
public class VerifyOrganization extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		rebuildTree();

		updateOrganizationAssets();

		updateOrganizationAssetEntries();
	}

	protected void rebuildTree() throws Exception {
		long[] companyIds = PortalInstances.getCompanyIdsBySQL();

		for (long companyId : companyIds) {
			OrganizationLocalServiceUtil.rebuildTree(companyId);
		}
	}

	protected void updateOrganizationAssetEntries() throws Exception {
		ActionableDynamicQuery actionableDynamicQuery =
			OrganizationLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod() {

				@Override
				public void performAction(Object object) {
					Organization organization = (Organization)object;

					try {
						AssetEntry assetEntry =
							AssetEntryLocalServiceUtil.getEntry(
								Organization.class.getName(),
								organization.getOrganizationId());

						if (Validator.isNotNull(assetEntry.getClassUuid())) {
							return;
						}

						assetEntry.setClassUuid(organization.getUuid());

						AssetEntryLocalServiceUtil.updateAssetEntry(assetEntry);
					}
					catch (Exception e) {
						if (_log.isWarnEnabled()) {
							_log.warn(
								"Unable to update asset entry for " +
									"organization " +
										organization.getOrganizationId(),
								e);
						}
					}
				}

			});

		actionableDynamicQuery.performActions();
	}

	protected void updateOrganizationAssets() throws Exception {
		List<Organization> organizations =
			OrganizationLocalServiceUtil.getNoAssetOrganizations();

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Processing " + organizations.size() + " organizations with " +
					"no asset");
		}

		for (Organization organization : organizations) {
			try {
				OrganizationLocalServiceUtil.updateAsset(
					organization.getUserId(), organization, null, null);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to update asset for organization " +
							organization.getOrganizationId() + ": " +
								e.getMessage());
				}
			}
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Assets verified for organizations");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		VerifyOrganization.class);

}