/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.users.admin.test.util.search;

import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.asset.kernel.service.AssetTagLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.OrganizationConstants;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.users.admin.test.util.search.OrganizationBlueprint.OrganizationBlueprintBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author André de Oliveira
 */
public class OrganizationSearchFixture {

	public static OrganizationBlueprintBuilder
		getTestOrganizationBlueprintBuilder() {

		long userId = getTestUserId();

		OrganizationBlueprintBuilder organizationBlueprintBuilder =
			new OrganizationBlueprintBuilder();

		return organizationBlueprintBuilder.name(
			RandomTestUtil.randomString()
		).parentOrganizationId(
			OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID
		).site(
			false
		).userId(
			userId
		);
	}

	public OrganizationSearchFixture(
		OrganizationLocalService organizationLocalService) {

		_organizationLocalService = organizationLocalService;

		_assetTagLocalService = AssetTagLocalServiceUtil.getService();
	}

	public OrganizationSearchFixture(
		OrganizationLocalService organizationLocalService,
		AssetTagLocalService assetTagLocalService) {

		_organizationLocalService = organizationLocalService;
		_assetTagLocalService = assetTagLocalService;
	}

	public Organization addOrganization(
		OrganizationBlueprint organizationBlueprint) {

		Organization organization = _addOrganization(organizationBlueprint);

		String[] assetTagNames = organizationBlueprint.getAssetTagNames();

		if (assetTagNames != null) {
			_updateAsset(organization, assetTagNames);
		}

		_organizations.add(organization);

		return organization;
	}

	public List<AssetTag> getAssetTags() {
		return _assetTags;
	}

	public List<Organization> getOrganizations() {
		return Collections.unmodifiableList(_organizations);
	}

	protected static long getTestUserId() {
		try {
			return TestPropsValues.getUserId();
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	private Organization _addOrganization(
		OrganizationBlueprint organizationBlueprint) {

		try {
			return _organizationLocalService.addOrganization(
				organizationBlueprint.getUserId(),
				organizationBlueprint.getParentOrganizationId(),
				organizationBlueprint.getName(),
				organizationBlueprint.isSite());
		}
		catch (RuntimeException runtimeException) {
			throw runtimeException;
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	private void _updateAsset(
		Organization organization, String[] assetTagNames) {

		try {
			_organizationLocalService.updateAsset(
				organization.getUserId(), organization, null, assetTagNames);
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}

		List<AssetTag> assetTags = _assetTagLocalService.getTags(
			organization.getModelClassName(), organization.getPrimaryKey());

		_assetTags.addAll(assetTags);
	}

	private final AssetTagLocalService _assetTagLocalService;
	private final List<AssetTag> _assetTags = new ArrayList<>();
	private final OrganizationLocalService _organizationLocalService;
	private final List<Organization> _organizations = new ArrayList<>();

}