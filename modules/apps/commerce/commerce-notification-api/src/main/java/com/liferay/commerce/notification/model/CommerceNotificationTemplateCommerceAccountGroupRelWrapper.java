/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.notification.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link CommerceNotificationTemplateCommerceAccountGroupRel}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceNotificationTemplateCommerceAccountGroupRel
 * @generated
 */
public class CommerceNotificationTemplateCommerceAccountGroupRelWrapper
	extends BaseModelWrapper
		<CommerceNotificationTemplateCommerceAccountGroupRel>
	implements CommerceNotificationTemplateCommerceAccountGroupRel,
			   ModelWrapper
				   <CommerceNotificationTemplateCommerceAccountGroupRel> {

	public CommerceNotificationTemplateCommerceAccountGroupRelWrapper(
		CommerceNotificationTemplateCommerceAccountGroupRel
			commerceNotificationTemplateCommerceAccountGroupRel) {

		super(commerceNotificationTemplateCommerceAccountGroupRel);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put(
			"commerceNotificationTemplateCommerceAccountGroupRelId",
			getCommerceNotificationTemplateCommerceAccountGroupRelId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put(
			"commerceNotificationTemplateId",
			getCommerceNotificationTemplateId());
		attributes.put("commerceAccountGroupId", getCommerceAccountGroupId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long commerceNotificationTemplateCommerceAccountGroupRelId =
			(Long)attributes.get(
				"commerceNotificationTemplateCommerceAccountGroupRelId");

		if (commerceNotificationTemplateCommerceAccountGroupRelId != null) {
			setCommerceNotificationTemplateCommerceAccountGroupRelId(
				commerceNotificationTemplateCommerceAccountGroupRelId);
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

		Long commerceNotificationTemplateId = (Long)attributes.get(
			"commerceNotificationTemplateId");

		if (commerceNotificationTemplateId != null) {
			setCommerceNotificationTemplateId(commerceNotificationTemplateId);
		}

		Long commerceAccountGroupId = (Long)attributes.get(
			"commerceAccountGroupId");

		if (commerceAccountGroupId != null) {
			setCommerceAccountGroupId(commerceAccountGroupId);
		}
	}

	@Override
	public CommerceNotificationTemplateCommerceAccountGroupRel
		cloneWithOriginalValues() {

		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the commerce account group ID of this commerce notification template commerce account group rel.
	 *
	 * @return the commerce account group ID of this commerce notification template commerce account group rel
	 */
	@Override
	public long getCommerceAccountGroupId() {
		return model.getCommerceAccountGroupId();
	}

	/**
	 * Returns the commerce notification template commerce account group rel ID of this commerce notification template commerce account group rel.
	 *
	 * @return the commerce notification template commerce account group rel ID of this commerce notification template commerce account group rel
	 */
	@Override
	public long getCommerceNotificationTemplateCommerceAccountGroupRelId() {
		return model.getCommerceNotificationTemplateCommerceAccountGroupRelId();
	}

	/**
	 * Returns the commerce notification template ID of this commerce notification template commerce account group rel.
	 *
	 * @return the commerce notification template ID of this commerce notification template commerce account group rel
	 */
	@Override
	public long getCommerceNotificationTemplateId() {
		return model.getCommerceNotificationTemplateId();
	}

	/**
	 * Returns the company ID of this commerce notification template commerce account group rel.
	 *
	 * @return the company ID of this commerce notification template commerce account group rel
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this commerce notification template commerce account group rel.
	 *
	 * @return the create date of this commerce notification template commerce account group rel
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the group ID of this commerce notification template commerce account group rel.
	 *
	 * @return the group ID of this commerce notification template commerce account group rel
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the modified date of this commerce notification template commerce account group rel.
	 *
	 * @return the modified date of this commerce notification template commerce account group rel
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this commerce notification template commerce account group rel.
	 *
	 * @return the mvcc version of this commerce notification template commerce account group rel
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this commerce notification template commerce account group rel.
	 *
	 * @return the primary key of this commerce notification template commerce account group rel
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this commerce notification template commerce account group rel.
	 *
	 * @return the user ID of this commerce notification template commerce account group rel
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this commerce notification template commerce account group rel.
	 *
	 * @return the user name of this commerce notification template commerce account group rel
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce notification template commerce account group rel.
	 *
	 * @return the user uuid of this commerce notification template commerce account group rel
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the commerce account group ID of this commerce notification template commerce account group rel.
	 *
	 * @param commerceAccountGroupId the commerce account group ID of this commerce notification template commerce account group rel
	 */
	@Override
	public void setCommerceAccountGroupId(long commerceAccountGroupId) {
		model.setCommerceAccountGroupId(commerceAccountGroupId);
	}

	/**
	 * Sets the commerce notification template commerce account group rel ID of this commerce notification template commerce account group rel.
	 *
	 * @param commerceNotificationTemplateCommerceAccountGroupRelId the commerce notification template commerce account group rel ID of this commerce notification template commerce account group rel
	 */
	@Override
	public void setCommerceNotificationTemplateCommerceAccountGroupRelId(
		long commerceNotificationTemplateCommerceAccountGroupRelId) {

		model.setCommerceNotificationTemplateCommerceAccountGroupRelId(
			commerceNotificationTemplateCommerceAccountGroupRelId);
	}

	/**
	 * Sets the commerce notification template ID of this commerce notification template commerce account group rel.
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID of this commerce notification template commerce account group rel
	 */
	@Override
	public void setCommerceNotificationTemplateId(
		long commerceNotificationTemplateId) {

		model.setCommerceNotificationTemplateId(commerceNotificationTemplateId);
	}

	/**
	 * Sets the company ID of this commerce notification template commerce account group rel.
	 *
	 * @param companyId the company ID of this commerce notification template commerce account group rel
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this commerce notification template commerce account group rel.
	 *
	 * @param createDate the create date of this commerce notification template commerce account group rel
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the group ID of this commerce notification template commerce account group rel.
	 *
	 * @param groupId the group ID of this commerce notification template commerce account group rel
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the modified date of this commerce notification template commerce account group rel.
	 *
	 * @param modifiedDate the modified date of this commerce notification template commerce account group rel
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this commerce notification template commerce account group rel.
	 *
	 * @param mvccVersion the mvcc version of this commerce notification template commerce account group rel
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this commerce notification template commerce account group rel.
	 *
	 * @param primaryKey the primary key of this commerce notification template commerce account group rel
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this commerce notification template commerce account group rel.
	 *
	 * @param userId the user ID of this commerce notification template commerce account group rel
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce notification template commerce account group rel.
	 *
	 * @param userName the user name of this commerce notification template commerce account group rel
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce notification template commerce account group rel.
	 *
	 * @param userUuid the user uuid of this commerce notification template commerce account group rel
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
	protected CommerceNotificationTemplateCommerceAccountGroupRelWrapper wrap(
		CommerceNotificationTemplateCommerceAccountGroupRel
			commerceNotificationTemplateCommerceAccountGroupRel) {

		return new CommerceNotificationTemplateCommerceAccountGroupRelWrapper(
			commerceNotificationTemplateCommerceAccountGroupRel);
	}

}