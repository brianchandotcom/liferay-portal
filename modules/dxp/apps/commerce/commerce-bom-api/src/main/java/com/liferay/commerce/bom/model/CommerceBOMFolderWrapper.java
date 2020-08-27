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
 * This class is a wrapper for {@link CommerceBOMFolder}.
 * </p>
 *
 * @author Luca Pellizzon
 * @see CommerceBOMFolder
 * @generated
 */
public class CommerceBOMFolderWrapper
	extends BaseModelWrapper<CommerceBOMFolder>
	implements CommerceBOMFolder, ModelWrapper<CommerceBOMFolder> {

	public CommerceBOMFolderWrapper(CommerceBOMFolder commerceBOMFolder) {
		super(commerceBOMFolder);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("commerceBOMFolderId", getCommerceBOMFolderId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put(
			"parentCommerceBOMFolderId", getParentCommerceBOMFolderId());
		attributes.put("name", getName());
		attributes.put("logoId", getLogoId());
		attributes.put("treePath", getTreePath());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long commerceBOMFolderId = (Long)attributes.get("commerceBOMFolderId");

		if (commerceBOMFolderId != null) {
			setCommerceBOMFolderId(commerceBOMFolderId);
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

		Long parentCommerceBOMFolderId = (Long)attributes.get(
			"parentCommerceBOMFolderId");

		if (parentCommerceBOMFolderId != null) {
			setParentCommerceBOMFolderId(parentCommerceBOMFolderId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		Long logoId = (Long)attributes.get("logoId");

		if (logoId != null) {
			setLogoId(logoId);
		}

		String treePath = (String)attributes.get("treePath");

		if (treePath != null) {
			setTreePath(treePath);
		}
	}

	@Override
	public String buildTreePath()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.buildTreePath();
	}

	@Override
	public java.util.List<Long> getAncestorCommerceBOMFolderIds()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getAncestorCommerceBOMFolderIds();
	}

	@Override
	public java.util.List<CommerceBOMFolder> getAncestors()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getAncestors();
	}

	/**
	 * Returns the commerce bom folder ID of this commerce bom folder.
	 *
	 * @return the commerce bom folder ID of this commerce bom folder
	 */
	@Override
	public long getCommerceBOMFolderId() {
		return model.getCommerceBOMFolderId();
	}

	/**
	 * Returns the company ID of this commerce bom folder.
	 *
	 * @return the company ID of this commerce bom folder
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this commerce bom folder.
	 *
	 * @return the create date of this commerce bom folder
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the logo ID of this commerce bom folder.
	 *
	 * @return the logo ID of this commerce bom folder
	 */
	@Override
	public long getLogoId() {
		return model.getLogoId();
	}

	/**
	 * Returns the modified date of this commerce bom folder.
	 *
	 * @return the modified date of this commerce bom folder
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the name of this commerce bom folder.
	 *
	 * @return the name of this commerce bom folder
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	@Override
	public CommerceBOMFolder getParentCommerceBOMFolder()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getParentCommerceBOMFolder();
	}

	/**
	 * Returns the parent commerce bom folder ID of this commerce bom folder.
	 *
	 * @return the parent commerce bom folder ID of this commerce bom folder
	 */
	@Override
	public long getParentCommerceBOMFolderId() {
		return model.getParentCommerceBOMFolderId();
	}

	/**
	 * Returns the primary key of this commerce bom folder.
	 *
	 * @return the primary key of this commerce bom folder
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the tree path of this commerce bom folder.
	 *
	 * @return the tree path of this commerce bom folder
	 */
	@Override
	public String getTreePath() {
		return model.getTreePath();
	}

	/**
	 * Returns the user ID of this commerce bom folder.
	 *
	 * @return the user ID of this commerce bom folder
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this commerce bom folder.
	 *
	 * @return the user name of this commerce bom folder
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce bom folder.
	 *
	 * @return the user uuid of this commerce bom folder
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	@Override
	public boolean isRoot() {
		return model.isRoot();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the commerce bom folder ID of this commerce bom folder.
	 *
	 * @param commerceBOMFolderId the commerce bom folder ID of this commerce bom folder
	 */
	@Override
	public void setCommerceBOMFolderId(long commerceBOMFolderId) {
		model.setCommerceBOMFolderId(commerceBOMFolderId);
	}

	/**
	 * Sets the company ID of this commerce bom folder.
	 *
	 * @param companyId the company ID of this commerce bom folder
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this commerce bom folder.
	 *
	 * @param createDate the create date of this commerce bom folder
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the logo ID of this commerce bom folder.
	 *
	 * @param logoId the logo ID of this commerce bom folder
	 */
	@Override
	public void setLogoId(long logoId) {
		model.setLogoId(logoId);
	}

	/**
	 * Sets the modified date of this commerce bom folder.
	 *
	 * @param modifiedDate the modified date of this commerce bom folder
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the name of this commerce bom folder.
	 *
	 * @param name the name of this commerce bom folder
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the parent commerce bom folder ID of this commerce bom folder.
	 *
	 * @param parentCommerceBOMFolderId the parent commerce bom folder ID of this commerce bom folder
	 */
	@Override
	public void setParentCommerceBOMFolderId(long parentCommerceBOMFolderId) {
		model.setParentCommerceBOMFolderId(parentCommerceBOMFolderId);
	}

	/**
	 * Sets the primary key of this commerce bom folder.
	 *
	 * @param primaryKey the primary key of this commerce bom folder
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the tree path of this commerce bom folder.
	 *
	 * @param treePath the tree path of this commerce bom folder
	 */
	@Override
	public void setTreePath(String treePath) {
		model.setTreePath(treePath);
	}

	/**
	 * Sets the user ID of this commerce bom folder.
	 *
	 * @param userId the user ID of this commerce bom folder
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce bom folder.
	 *
	 * @param userName the user name of this commerce bom folder
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce bom folder.
	 *
	 * @param userUuid the user uuid of this commerce bom folder
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	public void updateTreePath(String treePath) {
		model.updateTreePath(treePath);
	}

	@Override
	protected CommerceBOMFolderWrapper wrap(
		CommerceBOMFolder commerceBOMFolder) {

		return new CommerceBOMFolderWrapper(commerceBOMFolder);
	}

}