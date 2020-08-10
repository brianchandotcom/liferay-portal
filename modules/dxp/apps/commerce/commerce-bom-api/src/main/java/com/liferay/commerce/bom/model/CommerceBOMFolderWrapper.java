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
 * This class is a wrapper for {@link CommerceBOMFolder}.
 * </p>
 *
 * @author Luca Pellizzon
 * @see CommerceBOMFolder
 * @generated
 */
public class CommerceBOMFolderWrapper
	implements CommerceBOMFolder, ModelWrapper<CommerceBOMFolder> {

	public CommerceBOMFolderWrapper(CommerceBOMFolder commerceBOMFolder) {
		_commerceBOMFolder = commerceBOMFolder;
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceBOMFolder.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceBOMFolder.class.getName();
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

		return _commerceBOMFolder.buildTreePath();
	}

	@Override
	public Object clone() {
		return new CommerceBOMFolderWrapper(
			(CommerceBOMFolder)_commerceBOMFolder.clone());
	}

	@Override
	public int compareTo(CommerceBOMFolder commerceBOMFolder) {
		return _commerceBOMFolder.compareTo(commerceBOMFolder);
	}

	@Override
	public java.util.List<Long> getAncestorCommerceBOMFolderIds()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceBOMFolder.getAncestorCommerceBOMFolderIds();
	}

	@Override
	public java.util.List<CommerceBOMFolder> getAncestors()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceBOMFolder.getAncestors();
	}

	/**
	 * Returns the commerce bom folder ID of this commerce bom folder.
	 *
	 * @return the commerce bom folder ID of this commerce bom folder
	 */
	@Override
	public long getCommerceBOMFolderId() {
		return _commerceBOMFolder.getCommerceBOMFolderId();
	}

	/**
	 * Returns the company ID of this commerce bom folder.
	 *
	 * @return the company ID of this commerce bom folder
	 */
	@Override
	public long getCompanyId() {
		return _commerceBOMFolder.getCompanyId();
	}

	/**
	 * Returns the create date of this commerce bom folder.
	 *
	 * @return the create date of this commerce bom folder
	 */
	@Override
	public Date getCreateDate() {
		return _commerceBOMFolder.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commerceBOMFolder.getExpandoBridge();
	}

	/**
	 * Returns the logo ID of this commerce bom folder.
	 *
	 * @return the logo ID of this commerce bom folder
	 */
	@Override
	public long getLogoId() {
		return _commerceBOMFolder.getLogoId();
	}

	/**
	 * Returns the modified date of this commerce bom folder.
	 *
	 * @return the modified date of this commerce bom folder
	 */
	@Override
	public Date getModifiedDate() {
		return _commerceBOMFolder.getModifiedDate();
	}

	/**
	 * Returns the name of this commerce bom folder.
	 *
	 * @return the name of this commerce bom folder
	 */
	@Override
	public String getName() {
		return _commerceBOMFolder.getName();
	}

	@Override
	public CommerceBOMFolder getParentCommerceBOMFolder()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceBOMFolder.getParentCommerceBOMFolder();
	}

	/**
	 * Returns the parent commerce bom folder ID of this commerce bom folder.
	 *
	 * @return the parent commerce bom folder ID of this commerce bom folder
	 */
	@Override
	public long getParentCommerceBOMFolderId() {
		return _commerceBOMFolder.getParentCommerceBOMFolderId();
	}

	/**
	 * Returns the primary key of this commerce bom folder.
	 *
	 * @return the primary key of this commerce bom folder
	 */
	@Override
	public long getPrimaryKey() {
		return _commerceBOMFolder.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceBOMFolder.getPrimaryKeyObj();
	}

	/**
	 * Returns the tree path of this commerce bom folder.
	 *
	 * @return the tree path of this commerce bom folder
	 */
	@Override
	public String getTreePath() {
		return _commerceBOMFolder.getTreePath();
	}

	/**
	 * Returns the user ID of this commerce bom folder.
	 *
	 * @return the user ID of this commerce bom folder
	 */
	@Override
	public long getUserId() {
		return _commerceBOMFolder.getUserId();
	}

	/**
	 * Returns the user name of this commerce bom folder.
	 *
	 * @return the user name of this commerce bom folder
	 */
	@Override
	public String getUserName() {
		return _commerceBOMFolder.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce bom folder.
	 *
	 * @return the user uuid of this commerce bom folder
	 */
	@Override
	public String getUserUuid() {
		return _commerceBOMFolder.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _commerceBOMFolder.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _commerceBOMFolder.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commerceBOMFolder.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commerceBOMFolder.isNew();
	}

	@Override
	public boolean isRoot() {
		return _commerceBOMFolder.isRoot();
	}

	@Override
	public void persist() {
		_commerceBOMFolder.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commerceBOMFolder.setCachedModel(cachedModel);
	}

	/**
	 * Sets the commerce bom folder ID of this commerce bom folder.
	 *
	 * @param commerceBOMFolderId the commerce bom folder ID of this commerce bom folder
	 */
	@Override
	public void setCommerceBOMFolderId(long commerceBOMFolderId) {
		_commerceBOMFolder.setCommerceBOMFolderId(commerceBOMFolderId);
	}

	/**
	 * Sets the company ID of this commerce bom folder.
	 *
	 * @param companyId the company ID of this commerce bom folder
	 */
	@Override
	public void setCompanyId(long companyId) {
		_commerceBOMFolder.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this commerce bom folder.
	 *
	 * @param createDate the create date of this commerce bom folder
	 */
	@Override
	public void setCreateDate(Date createDate) {
		_commerceBOMFolder.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {

		_commerceBOMFolder.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commerceBOMFolder.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commerceBOMFolder.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	 * Sets the logo ID of this commerce bom folder.
	 *
	 * @param logoId the logo ID of this commerce bom folder
	 */
	@Override
	public void setLogoId(long logoId) {
		_commerceBOMFolder.setLogoId(logoId);
	}

	/**
	 * Sets the modified date of this commerce bom folder.
	 *
	 * @param modifiedDate the modified date of this commerce bom folder
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commerceBOMFolder.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the name of this commerce bom folder.
	 *
	 * @param name the name of this commerce bom folder
	 */
	@Override
	public void setName(String name) {
		_commerceBOMFolder.setName(name);
	}

	@Override
	public void setNew(boolean n) {
		_commerceBOMFolder.setNew(n);
	}

	/**
	 * Sets the parent commerce bom folder ID of this commerce bom folder.
	 *
	 * @param parentCommerceBOMFolderId the parent commerce bom folder ID of this commerce bom folder
	 */
	@Override
	public void setParentCommerceBOMFolderId(long parentCommerceBOMFolderId) {
		_commerceBOMFolder.setParentCommerceBOMFolderId(
			parentCommerceBOMFolderId);
	}

	/**
	 * Sets the primary key of this commerce bom folder.
	 *
	 * @param primaryKey the primary key of this commerce bom folder
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commerceBOMFolder.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commerceBOMFolder.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	 * Sets the tree path of this commerce bom folder.
	 *
	 * @param treePath the tree path of this commerce bom folder
	 */
	@Override
	public void setTreePath(String treePath) {
		_commerceBOMFolder.setTreePath(treePath);
	}

	/**
	 * Sets the user ID of this commerce bom folder.
	 *
	 * @param userId the user ID of this commerce bom folder
	 */
	@Override
	public void setUserId(long userId) {
		_commerceBOMFolder.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce bom folder.
	 *
	 * @param userName the user name of this commerce bom folder
	 */
	@Override
	public void setUserName(String userName) {
		_commerceBOMFolder.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce bom folder.
	 *
	 * @param userUuid the user uuid of this commerce bom folder
	 */
	@Override
	public void setUserUuid(String userUuid) {
		_commerceBOMFolder.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CommerceBOMFolder>
		toCacheModel() {

		return _commerceBOMFolder.toCacheModel();
	}

	@Override
	public CommerceBOMFolder toEscapedModel() {
		return new CommerceBOMFolderWrapper(
			_commerceBOMFolder.toEscapedModel());
	}

	@Override
	public String toString() {
		return _commerceBOMFolder.toString();
	}

	@Override
	public CommerceBOMFolder toUnescapedModel() {
		return new CommerceBOMFolderWrapper(
			_commerceBOMFolder.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _commerceBOMFolder.toXmlString();
	}

	@Override
	public void updateTreePath(String treePath) {
		_commerceBOMFolder.updateTreePath(treePath);
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommerceBOMFolderWrapper)) {
			return false;
		}

		CommerceBOMFolderWrapper commerceBOMFolderWrapper =
			(CommerceBOMFolderWrapper)object;

		if (Objects.equals(
				_commerceBOMFolder,
				commerceBOMFolderWrapper._commerceBOMFolder)) {

			return true;
		}

		return false;
	}

	@Override
	public CommerceBOMFolder getWrappedModel() {
		return _commerceBOMFolder;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commerceBOMFolder.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commerceBOMFolder.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commerceBOMFolder.resetOriginalValues();
	}

	private final CommerceBOMFolder _commerceBOMFolder;

}