/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.organizations.internal.object.system;

import com.liferay.headless.admin.user.dto.v1_0.Organization;
import com.liferay.headless.admin.user.resource.v1_0.OrganizationResource;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.field.builder.TextObjectFieldBuilder;
import com.liferay.object.model.ObjectField;
import com.liferay.object.system.BaseSystemObjectDefinitionManager;
import com.liferay.object.system.JaxRsApplicationDescriptor;
import com.liferay.object.system.SystemObjectDefinitionManager;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.OrganizationTable;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mateus Santana
 */
@Component(service = SystemObjectDefinitionManager.class)
public class OrganizationSystemObjectDefinitionManager
	extends BaseSystemObjectDefinitionManager {

	@Override
	public long addBaseModel(User user, Map<String, Object> values)
		throws Exception {

		OrganizationResource organizationResource = _buildOrganizationResource(
			user);

		Organization organization = organizationResource.postOrganization(
			_toOrganization(values));

		setExtendedProperties(
			Organization.class.getName(), organization, user, values);

		return GetterUtil.getLong(organization.getId());
	}

	@Override
	public BaseModel<?> deleteBaseModel(BaseModel<?> baseModel)
		throws PortalException {

		return _organizationLocalService.deleteOrganization(
			(com.liferay.portal.kernel.model.Organization)baseModel);
	}

	@Override
	public BaseModel<?> fetchBaseModelByExternalReferenceCode(
		String externalReferenceCode, long companyId) {

		return _organizationLocalService.
			fetchOrganizationByExternalReferenceCode(
				externalReferenceCode, companyId);
	}

	@Override
	public BaseModel<?> getBaseModelByExternalReferenceCode(
			String externalReferenceCode, long companyId)
		throws PortalException {

		return _organizationLocalService.getOrganizationByExternalReferenceCode(
			externalReferenceCode, companyId);
	}

	@Override
	public String getBaseModelExternalReferenceCode(long primaryKey)
		throws PortalException {

		com.liferay.portal.kernel.model.Organization organization =
			_organizationLocalService.getOrganization(primaryKey);

		return organization.getExternalReferenceCode();
	}

	@Override
	public String getExternalReferenceCode() {
		return "L_ORGANIZATION";
	}

	@Override
	public JaxRsApplicationDescriptor getJaxRsApplicationDescriptor() {
		return new JaxRsApplicationDescriptor(
			"Liferay.Headless.Admin.User", "headless-admin-user",
			"organizations", "v1.0");
	}

	@Override
	public Map<Locale, String> getLabelMap() {
		return createLabelMap("organization");
	}

	@Override
	public Class<?> getModelClass() {
		return com.liferay.portal.kernel.model.Organization.class;
	}

	@Override
	public List<ObjectField> getObjectFields() {
		return Arrays.asList(
			new TextObjectFieldBuilder(
			).labelMap(
				createLabelMap("comments")
			).name(
				"comment"
			).system(
				true
			).build(),
			new TextObjectFieldBuilder(
			).labelMap(
				createLabelMap("name")
			).name(
				"name"
			).required(
				true
			).system(
				true
			).build());
	}

	@Override
	public Map<Locale, String> getPluralLabelMap() {
		return createLabelMap("organizations");
	}

	@Override
	public Column<?, Long> getPrimaryKeyColumn() {
		return OrganizationTable.INSTANCE.organizationId;
	}

	@Override
	public String getScope() {
		return ObjectDefinitionConstants.SCOPE_COMPANY;
	}

	@Override
	public Table getTable() {
		return OrganizationTable.INSTANCE;
	}

	@Override
	public String getTitleObjectFieldName() {
		return "name";
	}

	@Override
	public int getVersion() {
		return 1;
	}

	@Override
	public void updateBaseModel(
			long primaryKey, User user, Map<String, Object> values)
		throws Exception {

		throw new UnsupportedOperationException();
	}

	private OrganizationResource _buildOrganizationResource(User user) {
		OrganizationResource.Builder builder =
			_organizationResourceFactory.create();

		return builder.checkPermissions(
			false
		).preferredLocale(
			user.getLocale()
		).user(
			user
		).build();
	}

	private Organization _toOrganization(Map<String, Object> values) {
		return new Organization() {
			{
				comment = GetterUtil.getString(values.get("comment"));
				name = GetterUtil.getString(values.get("name"));
			}
		};
	}

	@Reference
	private OrganizationLocalService _organizationLocalService;

	@Reference
	private OrganizationResource.Factory _organizationResourceFactory;

}