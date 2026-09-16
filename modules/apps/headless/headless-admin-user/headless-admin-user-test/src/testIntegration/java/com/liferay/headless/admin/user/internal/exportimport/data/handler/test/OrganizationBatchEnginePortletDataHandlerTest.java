/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.user.internal.exportimport.data.handler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.test.rule.ExportImportScopes;
import com.liferay.exportimport.test.util.exportimport.data.handler.BaseBatchEnginePortletDataHandlerTestCase;
import com.liferay.exportimport.vulcan.batch.engine.ExportImportVulcanBatchEngineTaskItemDelegate;
import com.liferay.exportimport.vulcan.batch.engine.ExportImportVulcanBatchEngineTaskItemDelegate.Scope;
import com.liferay.headless.admin.user.resource.v1_0.OrganizationResource;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.ListTypeConstants;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.OrganizationConstants;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ListTypeLocalService;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Date;
import java.util.List;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Alejandro Tardín
 */
@ExportImportScopes(Scope.COMPANY)
@RunWith(Arquillian.class)
public class OrganizationBatchEnginePortletDataHandlerTest
	extends BaseBatchEnginePortletDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Override
	protected String addEntry(long groupId, long userId, Date dateModified)
		throws Exception {

		long companyId = _getCompanyId(groupId);

		Organization organization = _organizationLocalService.addOrganization(
			RandomTestUtil.randomString(), userId,
			OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID,
			RandomTestUtil.randomString(),
			OrganizationConstants.TYPE_ORGANIZATION, 0, 0,
			_getStatusListTypeId(companyId), RandomTestUtil.randomString(),
			false,
			ServiceContextTestUtil.getServiceContext(
				companyId, groupId, userId));

		organization.setModifiedDate(dateModified);

		organization = _organizationLocalService.updateOrganization(
			organization);

		return organization.getExternalReferenceCode();
	}

	@Override
	protected void addStagedModels() throws Exception {
	}

	@Override
	protected void deleteEntry(long groupId, String externalReferenceCode)
		throws Exception {

		_organizationLocalService.deleteOrganization(
			_getOrganization(groupId, externalReferenceCode));
	}

	@Override
	protected long getCreatorUserId(long groupId, String externalReferenceCode)
		throws Exception {

		Organization organization = _getOrganization(
			groupId, externalReferenceCode);

		return organization.getUserId();
	}

	@Override
	protected Object getEntryValue(long groupId, String externalReferenceCode)
		throws Exception {

		Organization organization = _getOrganization(
			groupId, externalReferenceCode);

		return organization.getComments();
	}

	@Override
	protected ExportImportVulcanBatchEngineTaskItemDelegate<?>
		getExportImportVulcanBatchEngineTaskItemDelegate() {

		return getExportImportVulcanBatchEngineTaskItemDelegate(
			OrganizationResource.class);
	}

	@Override
	protected List<String> getExternalReferenceCodes(long groupId)
		throws Exception {

		return TransformUtil.transform(
			_organizationLocalService.getOrganizations(
				_getCompanyId(groupId),
				OrganizationConstants.ANY_PARENT_ORGANIZATION_ID),
			Organization::getExternalReferenceCode);
	}

	@Override
	protected long getPrimaryKey(long groupId, String externalReferenceCode)
		throws Exception {

		Organization organization = _getOrganization(
			groupId, externalReferenceCode);

		return organization.getOrganizationId();
	}

	@Override
	protected boolean supportsComments() {
		return false;
	}

	@Override
	protected boolean supportsEmptyEntries() {
		return false;
	}

	@Override
	protected boolean supportsPermissions() {
		return true;
	}

	@Override
	protected void updateEntry(long groupId, String externalReferenceCode)
		throws Exception {

		Organization organization = _getOrganization(
			groupId, externalReferenceCode);

		long companyId = _getCompanyId(groupId);

		_organizationLocalService.updateOrganization(
			organization.getExternalReferenceCode(), companyId,
			organization.getOrganizationId(),
			organization.getParentOrganizationId(), organization.getName(),
			organization.getType(), organization.getRegionId(),
			organization.getCountryId(), organization.getStatusListTypeId(),
			RandomTestUtil.randomString(), false, null, false,
			ServiceContextTestUtil.getServiceContext(
				companyId, groupId, organization.getUserId()));
	}

	private long _getCompanyId(long groupId) throws Exception {
		Group group = _groupLocalService.getGroup(groupId);

		return group.getCompanyId();
	}

	private Organization _getOrganization(
			long groupId, String externalReferenceCode)
		throws Exception {

		return _organizationLocalService.
			fetchOrganizationByExternalReferenceCode(
				externalReferenceCode, _getCompanyId(groupId));
	}

	private long _getStatusListTypeId(long companyId) throws Exception {
		ListType listType = _listTypeLocalService.getListType(
			companyId, ListTypeConstants.ORGANIZATION_STATUS_DEFAULT,
			ListTypeConstants.ORGANIZATION_STATUS);

		return listType.getListTypeId();
	}

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private ListTypeLocalService _listTypeLocalService;

	@Inject
	private OrganizationLocalService _organizationLocalService;

}