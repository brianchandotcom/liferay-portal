/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * <p>
 * This class is a wrapper for {@link CPConfigurationListRel}.
 * </p>
 *
 * @author Marco Leo
 * @see CPConfigurationListRel
 * @generated
 */
public class CPConfigurationListRelWrapper
	extends BaseModelWrapper<CPConfigurationListRel>
	implements CPConfigurationListRel, ModelWrapper<CPConfigurationListRel> {

	public CPConfigurationListRelWrapper(
		CPConfigurationListRel cpConfigurationListRel) {

		super(cpConfigurationListRel);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put(
			"CPConfigurationListRelId", getCPConfigurationListRelId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("CPConfigurationListId", getCPConfigurationListId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long ctCollectionId = (Long)attributes.get("ctCollectionId");

		if (ctCollectionId != null) {
			setCtCollectionId(ctCollectionId);
		}

		Long CPConfigurationListRelId = (Long)attributes.get(
			"CPConfigurationListRelId");

		if (CPConfigurationListRelId != null) {
			setCPConfigurationListRelId(CPConfigurationListRelId);
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

		Long CPConfigurationListId = (Long)attributes.get(
			"CPConfigurationListId");

		if (CPConfigurationListId != null) {
			setCPConfigurationListId(CPConfigurationListId);
		}
	}

	@Override
	public CPConfigurationListRel cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the fully qualified class name of this cp configuration list rel.
	 *
	 * @return the fully qualified class name of this cp configuration list rel
	 */
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	 * Returns the class name ID of this cp configuration list rel.
	 *
	 * @return the class name ID of this cp configuration list rel
	 */
	@Override
	public long getClassNameId() {
		return model.getClassNameId();
	}

	/**
	 * Returns the class pk of this cp configuration list rel.
	 *
	 * @return the class pk of this cp configuration list rel
	 */
	@Override
	public long getClassPK() {
		return model.getClassPK();
	}

	/**
	 * Returns the company ID of this cp configuration list rel.
	 *
	 * @return the company ID of this cp configuration list rel
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the cp configuration list ID of this cp configuration list rel.
	 *
	 * @return the cp configuration list ID of this cp configuration list rel
	 */
	@Override
	public long getCPConfigurationListId() {
		return model.getCPConfigurationListId();
	}

	/**
	 * Returns the cp configuration list rel ID of this cp configuration list rel.
	 *
	 * @return the cp configuration list rel ID of this cp configuration list rel
	 */
	@Override
	public long getCPConfigurationListRelId() {
		return model.getCPConfigurationListRelId();
	}

	/**
	 * Returns the create date of this cp configuration list rel.
	 *
	 * @return the create date of this cp configuration list rel
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the ct collection ID of this cp configuration list rel.
	 *
	 * @return the ct collection ID of this cp configuration list rel
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the modified date of this cp configuration list rel.
	 *
	 * @return the modified date of this cp configuration list rel
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this cp configuration list rel.
	 *
	 * @return the mvcc version of this cp configuration list rel
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this cp configuration list rel.
	 *
	 * @return the primary key of this cp configuration list rel
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this cp configuration list rel.
	 *
	 * @return the user ID of this cp configuration list rel
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this cp configuration list rel.
	 *
	 * @return the user name of this cp configuration list rel
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this cp configuration list rel.
	 *
	 * @return the user uuid of this cp configuration list rel
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
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
	 * Sets the class name ID of this cp configuration list rel.
	 *
	 * @param classNameId the class name ID of this cp configuration list rel
	 */
	@Override
	public void setClassNameId(long classNameId) {
		model.setClassNameId(classNameId);
	}

	/**
	 * Sets the class pk of this cp configuration list rel.
	 *
	 * @param classPK the class pk of this cp configuration list rel
	 */
	@Override
	public void setClassPK(long classPK) {
		model.setClassPK(classPK);
	}

	/**
	 * Sets the company ID of this cp configuration list rel.
	 *
	 * @param companyId the company ID of this cp configuration list rel
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the cp configuration list ID of this cp configuration list rel.
	 *
	 * @param CPConfigurationListId the cp configuration list ID of this cp configuration list rel
	 */
	@Override
	public void setCPConfigurationListId(long CPConfigurationListId) {
		model.setCPConfigurationListId(CPConfigurationListId);
	}

	/**
	 * Sets the cp configuration list rel ID of this cp configuration list rel.
	 *
	 * @param CPConfigurationListRelId the cp configuration list rel ID of this cp configuration list rel
	 */
	@Override
	public void setCPConfigurationListRelId(long CPConfigurationListRelId) {
		model.setCPConfigurationListRelId(CPConfigurationListRelId);
	}

	/**
	 * Sets the create date of this cp configuration list rel.
	 *
	 * @param createDate the create date of this cp configuration list rel
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the ct collection ID of this cp configuration list rel.
	 *
	 * @param ctCollectionId the ct collection ID of this cp configuration list rel
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the modified date of this cp configuration list rel.
	 *
	 * @param modifiedDate the modified date of this cp configuration list rel
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this cp configuration list rel.
	 *
	 * @param mvccVersion the mvcc version of this cp configuration list rel
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this cp configuration list rel.
	 *
	 * @param primaryKey the primary key of this cp configuration list rel
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this cp configuration list rel.
	 *
	 * @param userId the user ID of this cp configuration list rel
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this cp configuration list rel.
	 *
	 * @param userName the user name of this cp configuration list rel
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this cp configuration list rel.
	 *
	 * @param userUuid the user uuid of this cp configuration list rel
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	public Map<String, Function<CPConfigurationListRel, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<CPConfigurationListRel, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	protected CPConfigurationListRelWrapper wrap(
		CPConfigurationListRel cpConfigurationListRel) {

		return new CPConfigurationListRelWrapper(cpConfigurationListRel);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-715195916