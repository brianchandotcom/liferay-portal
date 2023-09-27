/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link CommerceOrderTypeRel}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceOrderTypeRel
 * @generated
 */
public class CommerceOrderTypeRelWrapper
	extends BaseModelWrapper<CommerceOrderTypeRel>
	implements CommerceOrderTypeRel, ModelWrapper<CommerceOrderTypeRel> {

	public CommerceOrderTypeRelWrapper(
		CommerceOrderTypeRel commerceOrderTypeRel) {

		super(commerceOrderTypeRel);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put("externalReferenceCode", getExternalReferenceCode());
		attributes.put("commerceOrderTypeRelId", getCommerceOrderTypeRelId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("commerceOrderTypeId", getCommerceOrderTypeId());

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

		Long commerceOrderTypeRelId = (Long)attributes.get(
			"commerceOrderTypeRelId");

		if (commerceOrderTypeRelId != null) {
			setCommerceOrderTypeRelId(commerceOrderTypeRelId);
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

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}

		Long commerceOrderTypeId = (Long)attributes.get("commerceOrderTypeId");

		if (commerceOrderTypeId != null) {
			setCommerceOrderTypeId(commerceOrderTypeId);
		}
	}

	@Override
	public CommerceOrderTypeRel cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the fully qualified class name of this commerce order type rel.
	 *
	 * @return the fully qualified class name of this commerce order type rel
	 */
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	 * Returns the class name ID of this commerce order type rel.
	 *
	 * @return the class name ID of this commerce order type rel
	 */
	@Override
	public long getClassNameId() {
		return model.getClassNameId();
	}

	/**
	 * Returns the class pk of this commerce order type rel.
	 *
	 * @return the class pk of this commerce order type rel
	 */
	@Override
	public long getClassPK() {
		return model.getClassPK();
	}

	/**
	 * Returns the commerce order type ID of this commerce order type rel.
	 *
	 * @return the commerce order type ID of this commerce order type rel
	 */
	@Override
	public long getCommerceOrderTypeId() {
		return model.getCommerceOrderTypeId();
	}

	/**
	 * Returns the commerce order type rel ID of this commerce order type rel.
	 *
	 * @return the commerce order type rel ID of this commerce order type rel
	 */
	@Override
	public long getCommerceOrderTypeRelId() {
		return model.getCommerceOrderTypeRelId();
	}

	/**
	 * Returns the company ID of this commerce order type rel.
	 *
	 * @return the company ID of this commerce order type rel
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this commerce order type rel.
	 *
	 * @return the create date of this commerce order type rel
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the external reference code of this commerce order type rel.
	 *
	 * @return the external reference code of this commerce order type rel
	 */
	@Override
	public String getExternalReferenceCode() {
		return model.getExternalReferenceCode();
	}

	/**
	 * Returns the modified date of this commerce order type rel.
	 *
	 * @return the modified date of this commerce order type rel
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this commerce order type rel.
	 *
	 * @return the mvcc version of this commerce order type rel
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this commerce order type rel.
	 *
	 * @return the primary key of this commerce order type rel
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this commerce order type rel.
	 *
	 * @return the user ID of this commerce order type rel
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this commerce order type rel.
	 *
	 * @return the user name of this commerce order type rel
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce order type rel.
	 *
	 * @return the user uuid of this commerce order type rel
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this commerce order type rel.
	 *
	 * @return the uuid of this commerce order type rel
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	@Override
	public void persist() {
		model.persist();
	}

	@Override
	public void setClassName(String className) {
		model.setClassName(className);
	}

	/**
	 * Sets the class name ID of this commerce order type rel.
	 *
	 * @param classNameId the class name ID of this commerce order type rel
	 */
	@Override
	public void setClassNameId(long classNameId) {
		model.setClassNameId(classNameId);
	}

	/**
	 * Sets the class pk of this commerce order type rel.
	 *
	 * @param classPK the class pk of this commerce order type rel
	 */
	@Override
	public void setClassPK(long classPK) {
		model.setClassPK(classPK);
	}

	/**
	 * Sets the commerce order type ID of this commerce order type rel.
	 *
	 * @param commerceOrderTypeId the commerce order type ID of this commerce order type rel
	 */
	@Override
	public void setCommerceOrderTypeId(long commerceOrderTypeId) {
		model.setCommerceOrderTypeId(commerceOrderTypeId);
	}

	/**
	 * Sets the commerce order type rel ID of this commerce order type rel.
	 *
	 * @param commerceOrderTypeRelId the commerce order type rel ID of this commerce order type rel
	 */
	@Override
	public void setCommerceOrderTypeRelId(long commerceOrderTypeRelId) {
		model.setCommerceOrderTypeRelId(commerceOrderTypeRelId);
	}

	/**
	 * Sets the company ID of this commerce order type rel.
	 *
	 * @param companyId the company ID of this commerce order type rel
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this commerce order type rel.
	 *
	 * @param createDate the create date of this commerce order type rel
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the external reference code of this commerce order type rel.
	 *
	 * @param externalReferenceCode the external reference code of this commerce order type rel
	 */
	@Override
	public void setExternalReferenceCode(String externalReferenceCode) {
		model.setExternalReferenceCode(externalReferenceCode);
	}

	/**
	 * Sets the modified date of this commerce order type rel.
	 *
	 * @param modifiedDate the modified date of this commerce order type rel
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this commerce order type rel.
	 *
	 * @param mvccVersion the mvcc version of this commerce order type rel
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this commerce order type rel.
	 *
	 * @param primaryKey the primary key of this commerce order type rel
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this commerce order type rel.
	 *
	 * @param userId the user ID of this commerce order type rel
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce order type rel.
	 *
	 * @param userName the user name of this commerce order type rel
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce order type rel.
	 *
	 * @param userUuid the user uuid of this commerce order type rel
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this commerce order type rel.
	 *
	 * @param uuid the uuid of this commerce order type rel
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
	protected CommerceOrderTypeRelWrapper wrap(
		CommerceOrderTypeRel commerceOrderTypeRel) {

		return new CommerceOrderTypeRelWrapper(commerceOrderTypeRel);
	}

}