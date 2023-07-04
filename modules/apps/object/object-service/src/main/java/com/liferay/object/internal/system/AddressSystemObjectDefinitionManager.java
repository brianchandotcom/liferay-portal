/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.system;

import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.field.builder.TextObjectFieldBuilder;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.system.BaseSystemObjectDefinitionManager;
import com.liferay.object.system.JaxRsApplicationDescriptor;
import com.liferay.object.system.SystemObjectDefinitionManager;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.AddressTable;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.AddressLocalService;
import com.liferay.portal.kernel.util.SetUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rodrigo Paulino
 */
@Component(service = SystemObjectDefinitionManager.class)
public class AddressSystemObjectDefinitionManager
	extends BaseSystemObjectDefinitionManager {

	@Override
	public long addBaseModel(User user, Map<String, Object> values)
		throws Exception {

		return ReflectionUtil.throwException(
			new UnsupportedOperationException());
	}

	@Override
	public BaseModel<?> deleteBaseModel(BaseModel<?> baseModel)
		throws PortalException {

		return _addressLocalService.deleteAddress((Address)baseModel);
	}

	public BaseModel<?> fetchBaseModelByExternalReferenceCode(
		String externalReferenceCode, long companyId) {

		return _addressLocalService.fetchAddressByExternalReferenceCode(
			externalReferenceCode, companyId);
	}

	@Override
	public Set<String> getAllowedObjectRelationshipTypes() {
		return SetUtil.fromArray(ObjectRelationshipConstants.TYPE_ONE_TO_MANY);
	}

	@Override
	public BaseModel<?> getBaseModelByExternalReferenceCode(
			String externalReferenceCode, long companyId)
		throws PortalException {

		return _addressLocalService.getAddressByExternalReferenceCode(
			externalReferenceCode, companyId);
	}

	@Override
	public String getBaseModelExternalReferenceCode(long primaryKey)
		throws PortalException {

		Address address = _addressLocalService.getAddress(primaryKey);

		return address.getExternalReferenceCode();
	}

	@Override
	public String getExternalReferenceCode() {
		return "L_POSTAL_ADDRESS";
	}

	@Override
	public JaxRsApplicationDescriptor getJaxRsApplicationDescriptor() {
		return new JaxRsApplicationDescriptor(
			"Liferay.Headless.Admin.User", "headless-admin-user",
			"accounts/{accountId}/postal-addresses", "v1.0");
	}

	@Override
	public Map<Locale, String> getLabelMap() {
		return createLabelMap("postal-address");
	}

	@Override
	public Class<?> getModelClass() {
		return Address.class;
	}

	@Override
	public List<ObjectField> getObjectFields() {
		return Arrays.asList(
			new TextObjectFieldBuilder(
			).labelMap(
				createLabelMap("name")
			).name(
				"name"
			).required(
				true
			).system(
				true
			).build(),
			new TextObjectFieldBuilder(
			).dbColumnName(
				"street1"
			).labelMap(
				createLabelMap("street1")
			).name(
				"streetAddressLine1"
			).required(
				true
			).system(
				true
			).build());
	}

	@Override
	public Map<Locale, String> getPluralLabelMap() {
		return createLabelMap("postal-addresses");
	}

	@Override
	public Column<?, Long> getPrimaryKeyColumn() {
		return AddressTable.INSTANCE.addressId;
	}

	@Override
	public String getScope() {
		return ObjectDefinitionConstants.SCOPE_COMPANY;
	}

	@Override
	public Table getTable() {
		return AddressTable.INSTANCE;
	}

	@Override
	public String getTitleObjectFieldName() {
		return "name";
	}

	@Override
	public Map<String, Object> getVariables(
		String contentType, ObjectDefinition objectDefinition,
		boolean oldValues, JSONObject payloadJSONObject) {

		Map<String, Object> variables = super.getVariables(
			contentType, objectDefinition, oldValues, payloadJSONObject);

		if (variables.containsKey("street1")) {
			variables.put("streetAddressLine1", variables.get("street1"));
		}

		return variables;
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

	@Reference
	private AddressLocalService _addressLocalService;

}