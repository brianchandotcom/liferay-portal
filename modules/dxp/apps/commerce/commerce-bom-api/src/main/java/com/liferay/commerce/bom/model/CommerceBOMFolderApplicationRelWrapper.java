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

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
	extends BaseModelWrapper<CommerceBOMFolderApplicationRel>
	implements CommerceBOMFolderApplicationRel,
			   ModelWrapper<CommerceBOMFolderApplicationRel> {

	public CommerceBOMFolderApplicationRelWrapper(
		CommerceBOMFolderApplicationRel commerceBOMFolderApplicationRel) {

		super(commerceBOMFolderApplicationRel);
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
	public com.liferay.commerce.application.model.CommerceApplicationModel
			getCommerceApplicationModel()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getCommerceApplicationModel();
	}

	/**
	 * Returns the commerce application model ID of this commerce bom folder application rel.
	 *
	 * @return the commerce application model ID of this commerce bom folder application rel
	 */
	@Override
	public long getCommerceApplicationModelId() {
		return model.getCommerceApplicationModelId();
	}

	@Override
	public CommerceBOMFolder getCommerceBOMFolder()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getCommerceBOMFolder();
	}

	/**
	 * Returns the commerce bom folder application rel ID of this commerce bom folder application rel.
	 *
	 * @return the commerce bom folder application rel ID of this commerce bom folder application rel
	 */
	@Override
	public long getCommerceBOMFolderApplicationRelId() {
		return model.getCommerceBOMFolderApplicationRelId();
	}

	/**
	 * Returns the commerce bom folder ID of this commerce bom folder application rel.
	 *
	 * @return the commerce bom folder ID of this commerce bom folder application rel
	 */
	@Override
	public long getCommerceBOMFolderId() {
		return model.getCommerceBOMFolderId();
	}

	/**
	 * Returns the company ID of this commerce bom folder application rel.
	 *
	 * @return the company ID of this commerce bom folder application rel
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this commerce bom folder application rel.
	 *
	 * @return the create date of this commerce bom folder application rel
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the modified date of this commerce bom folder application rel.
	 *
	 * @return the modified date of this commerce bom folder application rel
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the primary key of this commerce bom folder application rel.
	 *
	 * @return the primary key of this commerce bom folder application rel
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this commerce bom folder application rel.
	 *
	 * @return the user ID of this commerce bom folder application rel
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this commerce bom folder application rel.
	 *
	 * @return the user name of this commerce bom folder application rel
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce bom folder application rel.
	 *
	 * @return the user uuid of this commerce bom folder application rel
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
	 * Sets the commerce application model ID of this commerce bom folder application rel.
	 *
	 * @param commerceApplicationModelId the commerce application model ID of this commerce bom folder application rel
	 */
	@Override
	public void setCommerceApplicationModelId(long commerceApplicationModelId) {
		model.setCommerceApplicationModelId(commerceApplicationModelId);
	}

	/**
	 * Sets the commerce bom folder application rel ID of this commerce bom folder application rel.
	 *
	 * @param commerceBOMFolderApplicationRelId the commerce bom folder application rel ID of this commerce bom folder application rel
	 */
	@Override
	public void setCommerceBOMFolderApplicationRelId(
		long commerceBOMFolderApplicationRelId) {

		model.setCommerceBOMFolderApplicationRelId(
			commerceBOMFolderApplicationRelId);
	}

	/**
	 * Sets the commerce bom folder ID of this commerce bom folder application rel.
	 *
	 * @param commerceBOMFolderId the commerce bom folder ID of this commerce bom folder application rel
	 */
	@Override
	public void setCommerceBOMFolderId(long commerceBOMFolderId) {
		model.setCommerceBOMFolderId(commerceBOMFolderId);
	}

	/**
	 * Sets the company ID of this commerce bom folder application rel.
	 *
	 * @param companyId the company ID of this commerce bom folder application rel
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this commerce bom folder application rel.
	 *
	 * @param createDate the create date of this commerce bom folder application rel
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the modified date of this commerce bom folder application rel.
	 *
	 * @param modifiedDate the modified date of this commerce bom folder application rel
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the primary key of this commerce bom folder application rel.
	 *
	 * @param primaryKey the primary key of this commerce bom folder application rel
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this commerce bom folder application rel.
	 *
	 * @param userId the user ID of this commerce bom folder application rel
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce bom folder application rel.
	 *
	 * @param userName the user name of this commerce bom folder application rel
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce bom folder application rel.
	 *
	 * @param userUuid the user uuid of this commerce bom folder application rel
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected CommerceBOMFolderApplicationRelWrapper wrap(
		CommerceBOMFolderApplicationRel commerceBOMFolderApplicationRel) {

		return new CommerceBOMFolderApplicationRelWrapper(
			commerceBOMFolderApplicationRel);
	}

}