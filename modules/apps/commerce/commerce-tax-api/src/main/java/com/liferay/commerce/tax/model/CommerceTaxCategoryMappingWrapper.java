/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.tax.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link CommerceTaxCategoryMapping}.
 * </p>
 *
 * @author Marco Leo
 * @see CommerceTaxCategoryMapping
 * @generated
 */
public class CommerceTaxCategoryMappingWrapper
	extends BaseModelWrapper<CommerceTaxCategoryMapping>
	implements CommerceTaxCategoryMapping,
			   ModelWrapper<CommerceTaxCategoryMapping> {

	public CommerceTaxCategoryMappingWrapper(
		CommerceTaxCategoryMapping commerceTaxCategoryMapping) {

		super(commerceTaxCategoryMapping);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put("externalReferenceCode", getExternalReferenceCode());
		attributes.put(
			"commerceTaxCategoryMappingId", getCommerceTaxCategoryMappingId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("commerceTaxMethodId", getCommerceTaxMethodId());
		attributes.put("CPTaxCategoryId", getCPTaxCategoryId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		String externalReferenceCode = (String)attributes.get(
			"externalReferenceCode");

		if (externalReferenceCode != null) {
			setExternalReferenceCode(externalReferenceCode);
		}

		Long commerceTaxCategoryMappingId = (Long)attributes.get(
			"commerceTaxCategoryMappingId");

		if (commerceTaxCategoryMappingId != null) {
			setCommerceTaxCategoryMappingId(commerceTaxCategoryMappingId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String userName = (String)attributes.get("userName");

		if (userName != null) {
			setUserName(userName);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		Long commerceTaxMethodId = (Long)attributes.get("commerceTaxMethodId");

		if (commerceTaxMethodId != null) {
			setCommerceTaxMethodId(commerceTaxMethodId);
		}

		Long CPTaxCategoryId = (Long)attributes.get("CPTaxCategoryId");

		if (CPTaxCategoryId != null) {
			setCPTaxCategoryId(CPTaxCategoryId);
		}
	}

	@Override
	public CommerceTaxCategoryMapping cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the commerce tax category mapping ID of this commerce tax category mapping.
	 *
	 * @return the commerce tax category mapping ID of this commerce tax category mapping
	 */
	@Override
	public long getCommerceTaxCategoryMappingId() {
		return model.getCommerceTaxCategoryMappingId();
	}

	/**
	 * Returns the commerce tax method ID of this commerce tax category mapping.
	 *
	 * @return the commerce tax method ID of this commerce tax category mapping
	 */
	@Override
	public long getCommerceTaxMethodId() {
		return model.getCommerceTaxMethodId();
	}

	/**
	 * Returns the company ID of this commerce tax category mapping.
	 *
	 * @return the company ID of this commerce tax category mapping
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the cp tax category ID of this commerce tax category mapping.
	 *
	 * @return the cp tax category ID of this commerce tax category mapping
	 */
	@Override
	public long getCPTaxCategoryId() {
		return model.getCPTaxCategoryId();
	}

	/**
	 * Returns the create date of this commerce tax category mapping.
	 *
	 * @return the create date of this commerce tax category mapping
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the external reference code of this commerce tax category mapping.
	 *
	 * @return the external reference code of this commerce tax category mapping
	 */
	@Override
	public String getExternalReferenceCode() {
		return model.getExternalReferenceCode();
	}

	/**
	 * Returns the group ID of this commerce tax category mapping.
	 *
	 * @return the group ID of this commerce tax category mapping
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the modified date of this commerce tax category mapping.
	 *
	 * @return the modified date of this commerce tax category mapping
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this commerce tax category mapping.
	 *
	 * @return the mvcc version of this commerce tax category mapping
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this commerce tax category mapping.
	 *
	 * @return the primary key of this commerce tax category mapping
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this commerce tax category mapping.
	 *
	 * @return the user ID of this commerce tax category mapping
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this commerce tax category mapping.
	 *
	 * @return the user name of this commerce tax category mapping
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce tax category mapping.
	 *
	 * @return the user uuid of this commerce tax category mapping
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this commerce tax category mapping.
	 *
	 * @return the uuid of this commerce tax category mapping
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the commerce tax category mapping ID of this commerce tax category mapping.
	 *
	 * @param commerceTaxCategoryMappingId the commerce tax category mapping ID of this commerce tax category mapping
	 */
	@Override
	public void setCommerceTaxCategoryMappingId(
		long commerceTaxCategoryMappingId) {

		model.setCommerceTaxCategoryMappingId(commerceTaxCategoryMappingId);
	}

	/**
	 * Sets the commerce tax method ID of this commerce tax category mapping.
	 *
	 * @param commerceTaxMethodId the commerce tax method ID of this commerce tax category mapping
	 */
	@Override
	public void setCommerceTaxMethodId(long commerceTaxMethodId) {
		model.setCommerceTaxMethodId(commerceTaxMethodId);
	}

	/**
	 * Sets the company ID of this commerce tax category mapping.
	 *
	 * @param companyId the company ID of this commerce tax category mapping
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the cp tax category ID of this commerce tax category mapping.
	 *
	 * @param CPTaxCategoryId the cp tax category ID of this commerce tax category mapping
	 */
	@Override
	public void setCPTaxCategoryId(long CPTaxCategoryId) {
		model.setCPTaxCategoryId(CPTaxCategoryId);
	}

	/**
	 * Sets the create date of this commerce tax category mapping.
	 *
	 * @param createDate the create date of this commerce tax category mapping
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the external reference code of this commerce tax category mapping.
	 *
	 * @param externalReferenceCode the external reference code of this commerce tax category mapping
	 */
	@Override
	public void setExternalReferenceCode(String externalReferenceCode) {
		model.setExternalReferenceCode(externalReferenceCode);
	}

	/**
	 * Sets the group ID of this commerce tax category mapping.
	 *
	 * @param groupId the group ID of this commerce tax category mapping
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the modified date of this commerce tax category mapping.
	 *
	 * @param modifiedDate the modified date of this commerce tax category mapping
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this commerce tax category mapping.
	 *
	 * @param mvccVersion the mvcc version of this commerce tax category mapping
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this commerce tax category mapping.
	 *
	 * @param primaryKey the primary key of this commerce tax category mapping
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this commerce tax category mapping.
	 *
	 * @param userId the user ID of this commerce tax category mapping
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce tax category mapping.
	 *
	 * @param userName the user name of this commerce tax category mapping
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce tax category mapping.
	 *
	 * @param userUuid the user uuid of this commerce tax category mapping
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this commerce tax category mapping.
	 *
	 * @param uuid the uuid of this commerce tax category mapping
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected CommerceTaxCategoryMappingWrapper wrap(
		CommerceTaxCategoryMapping commerceTaxCategoryMapping) {

		return new CommerceTaxCategoryMappingWrapper(
			commerceTaxCategoryMapping);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:425540191