/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.object.system;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionTable;
import com.liferay.commerce.product.model.CProduct;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.CProductLocalService;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.Product;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.ProductResource;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.field.builder.BooleanObjectFieldBuilder;
import com.liferay.object.field.builder.LongIntegerObjectFieldBuilder;
import com.liferay.object.field.builder.TextObjectFieldBuilder;
import com.liferay.object.model.ObjectField;
import com.liferay.object.system.BaseSystemObjectDefinitionManager;
import com.liferay.object.system.JaxRsApplicationDescriptor;
import com.liferay.object.system.SystemObjectDefinitionManager;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author José Abelenda
 */
@Component(enabled = true, service = SystemObjectDefinitionManager.class)
public class CPDefinitionSystemObjectDefinitionManager
	extends BaseSystemObjectDefinitionManager {

	@Override
	public long addBaseModel(User user, Map<String, Object> values)
		throws Exception {

		ProductResource productResource = _buildProductResource(user);

		Product product = productResource.postProduct(_toProduct(values));

		setExtendedProperties(Product.class.getName(), product, user, values);

		return product.getId();
	}

	@Override
	public BaseModel<?> deleteBaseModel(BaseModel<?> baseModel)
		throws PortalException {

		return _cpDefinitionLocalService.deleteCPDefinition(
			(CPDefinition)baseModel);
	}

	public BaseModel<?> fetchBaseModelByExternalReferenceCode(
		String externalReferenceCode, long companyId) {

		return _cProductLocalService.fetchCProductByExternalReferenceCode(
			externalReferenceCode, companyId);
	}

	@Override
	public BaseModel<?> getBaseModelByExternalReferenceCode(
			String externalReferenceCode, long companyId)
		throws PortalException {

		CProduct cProduct =
			_cProductLocalService.getCProductByExternalReferenceCode(
				externalReferenceCode, companyId);

		return _cpDefinitionLocalService.getCProductCPDefinition(
			cProduct.getCProductId(), cProduct.getLatestVersion());
	}

	@Override
	public String getBaseModelExternalReferenceCode(long primaryKey)
		throws PortalException {

		CProduct cProduct = _cProductLocalService.getCProduct(primaryKey);

		return cProduct.getExternalReferenceCode();
	}

	@Override
	public String getExternalReferenceCode() {
		return "L_COMMERCE_PRODUCT_DEFINITION";
	}

	@Override
	public JaxRsApplicationDescriptor getJaxRsApplicationDescriptor() {
		return new JaxRsApplicationDescriptor(
			"Liferay.Headless.Commerce.Admin.Catalog",
			"headless-commerce-admin-catalog", "products", "v1.0");
	}

	@Override
	public Map<Locale, String> getLabelMap() {
		return createLabelMap("cp-definition");
	}

	@Override
	public Class<?> getModelClass() {
		return CPDefinition.class;
	}

	@Override
	public List<ObjectField> getObjectFields() {
		return Arrays.asList(
			new BooleanObjectFieldBuilder(
			).labelMap(
				createLabelMap("active")
			).name(
				"active"
			).required(
				true
			).system(
				true
			).build(),
			new LongIntegerObjectFieldBuilder(
			).labelMap(
				createLabelMap("catalog-id")
			).name(
				"catalogId"
			).required(
				true
			).system(
				true
			).build(),
			new TextObjectFieldBuilder(
			).labelMap(
				createLabelMap("description")
			).name(
				"description"
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
			).build(),
			new TextObjectFieldBuilder(
			).dbColumnName(
				"CPDefinitionId"
			).labelMap(
				createLabelMap("product-id")
			).name(
				"productId"
			).system(
				true
			).build(),
			new TextObjectFieldBuilder(
			).labelMap(
				createLabelMap("product-type")
			).name(
				"productType"
			).required(
				true
			).system(
				true
			).build(),
			new TextObjectFieldBuilder(
			).labelMap(
				createLabelMap("short-description")
			).name(
				"shortDescription"
			).system(
				true
			).build(),
			new TextObjectFieldBuilder(
			).labelMap(
				createLabelMap("sku")
			).name(
				"skuFormatted"
			).system(
				true
			).build(),
			new TextObjectFieldBuilder(
			).labelMap(
				createLabelMap("thumbnail")
			).name(
				"thumbnail"
			).system(
				true
			).build(),
			new TextObjectFieldBuilder(
			).labelMap(
				createLabelMap("uuid")
			).name(
				"uuid"
			).system(
				true
			).build());
	}

	@Override
	public Map<Locale, String> getPluralLabelMap() {
		return createLabelMap("cp-definitions");
	}

	@Override
	public Column<?, Long> getPrimaryKeyColumn() {
		return CPDefinitionTable.INSTANCE.CPDefinitionId;
	}

	@Override
	public String getRESTDTOIdPropertyName() {
		return "productId";
	}

	@Override
	public String getScope() {
		return ObjectDefinitionConstants.SCOPE_COMPANY;
	}

	@Override
	public Table getTable() {
		return CPDefinitionTable.INSTANCE;
	}

	@Override
	public String getTitleObjectFieldName() {
		return "name";
	}

	@Override
	public int getVersion() {
		return 2;
	}

	@Override
	public void updateBaseModel(
			long primaryKey, User user, Map<String, Object> values)
		throws Exception {

		ProductResource productResource = _buildProductResource(user);

		CPDefinition cpDefinition = _cpDefinitionLocalService.getCPDefinition(
			primaryKey);

		productResource.patchProduct(
			cpDefinition.getCProductId(), _toProduct(values));

		setExtendedProperties(
			Product.class.getName(), JSONUtil.put("id", primaryKey), user,
			values);
	}

	private ProductResource _buildProductResource(User user) {
		ProductResource.Builder builder = _productResourceFactory.create();

		return builder.checkPermissions(
			false
		).preferredLocale(
			user.getLocale()
		).user(
			user
		).build();
	}

	private Product _toProduct(Map<String, Object> values) {
		return new Product() {
			{
				active = GetterUtil.getBoolean(values.get("active"));
				catalogId = GetterUtil.getLong(values.get("catalogId"));
				description = getLanguageIdMap("description", values);
				externalReferenceCode = GetterUtil.getString(
					values.get("externalReferenceCode"));
				name = getLanguageIdMap("name", values);
				productId = GetterUtil.getLong(values.get("productId"));
				productType = GetterUtil.getString(values.get("productType"));
				shortDescription = getLanguageIdMap("shortDescription", values);
				skuFormatted = GetterUtil.getString(values.get("skuFormatted"));
				thumbnail = GetterUtil.getString(values.get("thumbnail"));
			}
		};
	}

	@Reference
	private CPDefinitionLocalService _cpDefinitionLocalService;

	@Reference
	private CProductLocalService _cProductLocalService;

	@Reference
	private ProductResource.Factory _productResourceFactory;

}