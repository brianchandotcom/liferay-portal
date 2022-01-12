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

package com.liferay.commerce.product.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * <p>
 * This class is a wrapper for {@link CPDisplayLayout}.
 * </p>
 *
 * @author Marco Leo
 * @see CPDisplayLayout
 * @generated
 */
public class CPDisplayLayoutWrapper
	extends BaseModelWrapper<CPDisplayLayout>
	implements CPDisplayLayout, ModelWrapper<CPDisplayLayout> {

	public CPDisplayLayoutWrapper(CPDisplayLayout cpDisplayLayout) {
		super(cpDisplayLayout);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("uuid", getUuid());
		attributes.put("CPDisplayLayoutId", getCPDisplayLayoutId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("layoutUuid", getLayoutUuid());

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

		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long CPDisplayLayoutId = (Long)attributes.get("CPDisplayLayoutId");

		if (CPDisplayLayoutId != null) {
			setCPDisplayLayoutId(CPDisplayLayoutId);
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

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}

		String layoutUuid = (String)attributes.get("layoutUuid");

		if (layoutUuid != null) {
			setLayoutUuid(layoutUuid);
		}
	}

	@Override
	public CPDisplayLayout cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	@Override
	public com.liferay.asset.kernel.model.AssetCategory fetchAssetCategory() {
		return model.fetchAssetCategory();
	}

	@Override
	public CPDefinition fetchCPDefinition() {
		return model.fetchCPDefinition();
	}

	@Override
	public com.liferay.portal.kernel.model.Layout fetchLayout() {
		return model.fetchLayout();
	}

	/**
	 * Returns the fully qualified class name of this cp display layout.
	 *
	 * @return the fully qualified class name of this cp display layout
	 */
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	 * Returns the class name ID of this cp display layout.
	 *
	 * @return the class name ID of this cp display layout
	 */
	@Override
	public long getClassNameId() {
		return model.getClassNameId();
	}

	/**
	 * Returns the class pk of this cp display layout.
	 *
	 * @return the class pk of this cp display layout
	 */
	@Override
	public long getClassPK() {
		return model.getClassPK();
	}

	/**
	 * Returns the company ID of this cp display layout.
	 *
	 * @return the company ID of this cp display layout
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the cp display layout ID of this cp display layout.
	 *
	 * @return the cp display layout ID of this cp display layout
	 */
	@Override
	public long getCPDisplayLayoutId() {
		return model.getCPDisplayLayoutId();
	}

	/**
	 * Returns the create date of this cp display layout.
	 *
	 * @return the create date of this cp display layout
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the ct collection ID of this cp display layout.
	 *
	 * @return the ct collection ID of this cp display layout
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the group ID of this cp display layout.
	 *
	 * @return the group ID of this cp display layout
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the layout uuid of this cp display layout.
	 *
	 * @return the layout uuid of this cp display layout
	 */
	@Override
	public String getLayoutUuid() {
		return model.getLayoutUuid();
	}

	/**
	 * Returns the modified date of this cp display layout.
	 *
	 * @return the modified date of this cp display layout
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this cp display layout.
	 *
	 * @return the mvcc version of this cp display layout
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this cp display layout.
	 *
	 * @return the primary key of this cp display layout
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this cp display layout.
	 *
	 * @return the user ID of this cp display layout
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this cp display layout.
	 *
	 * @return the user name of this cp display layout
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this cp display layout.
	 *
	 * @return the user uuid of this cp display layout
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this cp display layout.
	 *
	 * @return the uuid of this cp display layout
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
	 * Sets the class name ID of this cp display layout.
	 *
	 * @param classNameId the class name ID of this cp display layout
	 */
	@Override
	public void setClassNameId(long classNameId) {
		model.setClassNameId(classNameId);
	}

	/**
	 * Sets the class pk of this cp display layout.
	 *
	 * @param classPK the class pk of this cp display layout
	 */
	@Override
	public void setClassPK(long classPK) {
		model.setClassPK(classPK);
	}

	/**
	 * Sets the company ID of this cp display layout.
	 *
	 * @param companyId the company ID of this cp display layout
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the cp display layout ID of this cp display layout.
	 *
	 * @param CPDisplayLayoutId the cp display layout ID of this cp display layout
	 */
	@Override
	public void setCPDisplayLayoutId(long CPDisplayLayoutId) {
		model.setCPDisplayLayoutId(CPDisplayLayoutId);
	}

	/**
	 * Sets the create date of this cp display layout.
	 *
	 * @param createDate the create date of this cp display layout
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the ct collection ID of this cp display layout.
	 *
	 * @param ctCollectionId the ct collection ID of this cp display layout
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the group ID of this cp display layout.
	 *
	 * @param groupId the group ID of this cp display layout
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the layout uuid of this cp display layout.
	 *
	 * @param layoutUuid the layout uuid of this cp display layout
	 */
	@Override
	public void setLayoutUuid(String layoutUuid) {
		model.setLayoutUuid(layoutUuid);
	}

	/**
	 * Sets the modified date of this cp display layout.
	 *
	 * @param modifiedDate the modified date of this cp display layout
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this cp display layout.
	 *
	 * @param mvccVersion the mvcc version of this cp display layout
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this cp display layout.
	 *
	 * @param primaryKey the primary key of this cp display layout
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this cp display layout.
	 *
	 * @param userId the user ID of this cp display layout
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this cp display layout.
	 *
	 * @param userName the user name of this cp display layout
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this cp display layout.
	 *
	 * @param userUuid the user uuid of this cp display layout
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this cp display layout.
	 *
	 * @param uuid the uuid of this cp display layout
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	@Override
	public Map<String, Function<CPDisplayLayout, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<CPDisplayLayout, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected CPDisplayLayoutWrapper wrap(CPDisplayLayout cpDisplayLayout) {
		return new CPDisplayLayoutWrapper(cpDisplayLayout);
	}

}