/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.bom.model;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link CommerceBOMFolderApplicationRel}.
 * </p>
 *
 * @author Luca Pellizzon
 * @see CommerceBOMFolderApplicationRel
 * @generated
 */
public class CommerceBOMFolderApplicationRelWrapper
	implements CommerceBOMFolderApplicationRel,
			   ModelWrapper<CommerceBOMFolderApplicationRel> {

	public CommerceBOMFolderApplicationRelWrapper(
		CommerceBOMFolderApplicationRel commerceBOMFolderApplicationRel) {

		_commerceBOMFolderApplicationRel = commerceBOMFolderApplicationRel;
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceBOMFolderApplicationRel.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceBOMFolderApplicationRel.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put(
			"commerceBOMFolderApplicationRelId",
			getCommerceBOMFolderApplicationRelId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("commerceBOMFolderId", getCommerceBOMFolderId());
		attributes.put(
			"commerceApplicationModelId", getCommerceApplicationModelId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long commerceBOMFolderApplicationRelId = (Long)attributes.get(
			"commerceBOMFolderApplicationRelId");

		if (commerceBOMFolderApplicationRelId != null) {
			setCommerceBOMFolderApplicationRelId(
				commerceBOMFolderApplicationRelId);
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

		Long commerceBOMFolderId = (Long)attributes.get("commerceBOMFolderId");

		if (commerceBOMFolderId != null) {
			setCommerceBOMFolderId(commerceBOMFolderId);
		}

		Long commerceApplicationModelId = (Long)attributes.get(
			"commerceApplicationModelId");

		if (commerceApplicationModelId != null) {
			setCommerceApplicationModelId(commerceApplicationModelId);
		}
	}

	@Override
	public Object clone() {
		return new CommerceBOMFolderApplicationRelWrapper(
			(CommerceBOMFolderApplicationRel)
				_commerceBOMFolderApplicationRel.clone());
	}

	@Override
	public int compareTo(
		CommerceBOMFolderApplicationRel commerceBOMFolderApplicationRel) {

		return _commerceBOMFolderApplicationRel.compareTo(
			commerceBOMFolderApplicationRel);
	}

	@Override
	public com.liferay.commerce.application.model.CommerceApplicationModel
			getCommerceApplicationModel()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceBOMFolderApplicationRel.getCommerceApplicationModel();
	}

	/**
	 * Returns the commerce application model ID of this commerce bom folder application rel.
	 *
	 * @return the commerce application model ID of this commerce bom folder application rel
	 */
	@Override
	public long getCommerceApplicationModelId() {
		return _commerceBOMFolderApplicationRel.getCommerceApplicationModelId();
	}

	@Override
	public CommerceBOMFolder getCommerceBOMFolder()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceBOMFolderApplicationRel.getCommerceBOMFolder();
	}

	/**
	 * Returns the commerce bom folder application rel ID of this commerce bom folder application rel.
	 *
	 * @return the commerce bom folder application rel ID of this commerce bom folder application rel
	 */
	@Override
	public long getCommerceBOMFolderApplicationRelId() {
		return _commerceBOMFolderApplicationRel.
			getCommerceBOMFolderApplicationRelId();
	}

	/**
	 * Returns the commerce bom folder ID of this commerce bom folder application rel.
	 *
	 * @return the commerce bom folder ID of this commerce bom folder application rel
	 */
	@Override
	public long getCommerceBOMFolderId() {
		return _commerceBOMFolderApplicationRel.getCommerceBOMFolderId();
	}

	/**
	 * Returns the company ID of this commerce bom folder application rel.
	 *
	 * @return the company ID of this commerce bom folder application rel
	 */
	@Override
	public long getCompanyId() {
		return _commerceBOMFolderApplicationRel.getCompanyId();
	}

	/**
	 * Returns the create date of this commerce bom folder application rel.
	 *
	 * @return the create date of this commerce bom folder application rel
	 */
	@Override
	public Date getCreateDate() {
		return _commerceBOMFolderApplicationRel.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commerceBOMFolderApplicationRel.getExpandoBridge();
	}

	/**
	 * Returns the modified date of this commerce bom folder application rel.
	 *
	 * @return the modified date of this commerce bom folder application rel
	 */
	@Override
	public Date getModifiedDate() {
		return _commerceBOMFolderApplicationRel.getModifiedDate();
	}

	/**
	 * Returns the primary key of this commerce bom folder application rel.
	 *
	 * @return the primary key of this commerce bom folder application rel
	 */
	@Override
	public long getPrimaryKey() {
		return _commerceBOMFolderApplicationRel.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceBOMFolderApplicationRel.getPrimaryKeyObj();
	}

	/**
	 * Returns the user ID of this commerce bom folder application rel.
	 *
	 * @return the user ID of this commerce bom folder application rel
	 */
	@Override
	public long getUserId() {
		return _commerceBOMFolderApplicationRel.getUserId();
	}

	/**
	 * Returns the user name of this commerce bom folder application rel.
	 *
	 * @return the user name of this commerce bom folder application rel
	 */
	@Override
	public String getUserName() {
		return _commerceBOMFolderApplicationRel.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce bom folder application rel.
	 *
	 * @return the user uuid of this commerce bom folder application rel
	 */
	@Override
	public String getUserUuid() {
		return _commerceBOMFolderApplicationRel.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _commerceBOMFolderApplicationRel.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _commerceBOMFolderApplicationRel.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commerceBOMFolderApplicationRel.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commerceBOMFolderApplicationRel.isNew();
	}

	@Override
	public void persist() {
		_commerceBOMFolderApplicationRel.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commerceBOMFolderApplicationRel.setCachedModel(cachedModel);
	}

	/**
	 * Sets the commerce application model ID of this commerce bom folder application rel.
	 *
	 * @param commerceApplicationModelId the commerce application model ID of this commerce bom folder application rel
	 */
	@Override
	public void setCommerceApplicationModelId(long commerceApplicationModelId) {
		_commerceBOMFolderApplicationRel.setCommerceApplicationModelId(
			commerceApplicationModelId);
	}

	/**
	 * Sets the commerce bom folder application rel ID of this commerce bom folder application rel.
	 *
	 * @param commerceBOMFolderApplicationRelId the commerce bom folder application rel ID of this commerce bom folder application rel
	 */
	@Override
	public void setCommerceBOMFolderApplicationRelId(
		long commerceBOMFolderApplicationRelId) {

		_commerceBOMFolderApplicationRel.setCommerceBOMFolderApplicationRelId(
			commerceBOMFolderApplicationRelId);
	}

	/**
	 * Sets the commerce bom folder ID of this commerce bom folder application rel.
	 *
	 * @param commerceBOMFolderId the commerce bom folder ID of this commerce bom folder application rel
	 */
	@Override
	public void setCommerceBOMFolderId(long commerceBOMFolderId) {
		_commerceBOMFolderApplicationRel.setCommerceBOMFolderId(
			commerceBOMFolderId);
	}

	/**
	 * Sets the company ID of this commerce bom folder application rel.
	 *
	 * @param companyId the company ID of this commerce bom folder application rel
	 */
	@Override
	public void setCompanyId(long companyId) {
		_commerceBOMFolderApplicationRel.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this commerce bom folder application rel.
	 *
	 * @param createDate the create date of this commerce bom folder application rel
	 */
	@Override
	public void setCreateDate(Date createDate) {
		_commerceBOMFolderApplicationRel.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {

		_commerceBOMFolderApplicationRel.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commerceBOMFolderApplicationRel.setExpandoBridgeAttributes(
			expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commerceBOMFolderApplicationRel.setExpandoBridgeAttributes(
			serviceContext);
	}

	/**
	 * Sets the modified date of this commerce bom folder application rel.
	 *
	 * @param modifiedDate the modified date of this commerce bom folder application rel
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commerceBOMFolderApplicationRel.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_commerceBOMFolderApplicationRel.setNew(n);
	}

	/**
	 * Sets the primary key of this commerce bom folder application rel.
	 *
	 * @param primaryKey the primary key of this commerce bom folder application rel
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commerceBOMFolderApplicationRel.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commerceBOMFolderApplicationRel.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	 * Sets the user ID of this commerce bom folder application rel.
	 *
	 * @param userId the user ID of this commerce bom folder application rel
	 */
	@Override
	public void setUserId(long userId) {
		_commerceBOMFolderApplicationRel.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce bom folder application rel.
	 *
	 * @param userName the user name of this commerce bom folder application rel
	 */
	@Override
	public void setUserName(String userName) {
		_commerceBOMFolderApplicationRel.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce bom folder application rel.
	 *
	 * @param userUuid the user uuid of this commerce bom folder application rel
	 */
	@Override
	public void setUserUuid(String userUuid) {
		_commerceBOMFolderApplicationRel.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel
		<CommerceBOMFolderApplicationRel> toCacheModel() {

		return _commerceBOMFolderApplicationRel.toCacheModel();
	}

	@Override
	public CommerceBOMFolderApplicationRel toEscapedModel() {
		return new CommerceBOMFolderApplicationRelWrapper(
			_commerceBOMFolderApplicationRel.toEscapedModel());
	}

	@Override
	public String toString() {
		return _commerceBOMFolderApplicationRel.toString();
	}

	@Override
	public CommerceBOMFolderApplicationRel toUnescapedModel() {
		return new CommerceBOMFolderApplicationRelWrapper(
			_commerceBOMFolderApplicationRel.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _commerceBOMFolderApplicationRel.toXmlString();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommerceBOMFolderApplicationRelWrapper)) {
			return false;
		}

		CommerceBOMFolderApplicationRelWrapper
			commerceBOMFolderApplicationRelWrapper =
				(CommerceBOMFolderApplicationRelWrapper)object;

		if (Objects.equals(
				_commerceBOMFolderApplicationRel,
				commerceBOMFolderApplicationRelWrapper.
					_commerceBOMFolderApplicationRel)) {

			return true;
		}

		return false;
	}

	@Override
	public CommerceBOMFolderApplicationRel getWrappedModel() {
		return _commerceBOMFolderApplicationRel;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commerceBOMFolderApplicationRel.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commerceBOMFolderApplicationRel.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commerceBOMFolderApplicationRel.resetOriginalValues();
	}

	private final CommerceBOMFolderApplicationRel
		_commerceBOMFolderApplicationRel;

}