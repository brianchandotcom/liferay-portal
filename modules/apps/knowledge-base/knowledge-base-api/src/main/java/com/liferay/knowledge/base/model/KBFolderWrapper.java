/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.model;

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
 * This class is a wrapper for {@link KBFolder}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see KBFolder
 * @generated
 */
public class KBFolderWrapper
	extends BaseModelWrapper<KBFolder>
	implements KBFolder, ModelWrapper<KBFolder> {

	public KBFolderWrapper(KBFolder kbFolder) {
		super(kbFolder);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("uuid", getUuid());
		attributes.put("externalReferenceCode", getExternalReferenceCode());
		attributes.put("kbFolderId", getKbFolderId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("parentKBFolderId", getParentKBFolderId());
		attributes.put("name", getName());
		attributes.put("urlTitle", getUrlTitle());
		attributes.put("description", getDescription());
		attributes.put("lastPublishDate", getLastPublishDate());

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

		String externalReferenceCode = (String)attributes.get(
			"externalReferenceCode");

		if (externalReferenceCode != null) {
			setExternalReferenceCode(externalReferenceCode);
		}

		Long kbFolderId = (Long)attributes.get("kbFolderId");

		if (kbFolderId != null) {
			setKbFolderId(kbFolderId);
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

		Long parentKBFolderId = (Long)attributes.get("parentKBFolderId");

		if (parentKBFolderId != null) {
			setParentKBFolderId(parentKBFolderId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String urlTitle = (String)attributes.get("urlTitle");

		if (urlTitle != null) {
			setUrlTitle(urlTitle);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}
	}

	@Override
	public KBFolder cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	@Override
	public java.util.List<Long> getAncestorKBFolderIds()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getAncestorKBFolderIds();
	}

	@Override
	public long getClassNameId() {
		return model.getClassNameId();
	}

	/**
	 * Returns the company ID of this kb folder.
	 *
	 * @return the company ID of this kb folder
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this kb folder.
	 *
	 * @return the create date of this kb folder
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the ct collection ID of this kb folder.
	 *
	 * @return the ct collection ID of this kb folder
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the description of this kb folder.
	 *
	 * @return the description of this kb folder
	 */
	@Override
	public String getDescription() {
		return model.getDescription();
	}

	/**
	 * Returns the external reference code of this kb folder.
	 *
	 * @return the external reference code of this kb folder
	 */
	@Override
	public String getExternalReferenceCode() {
		return model.getExternalReferenceCode();
	}

	/**
	 * Returns the group ID of this kb folder.
	 *
	 * @return the group ID of this kb folder
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the kb folder ID of this kb folder.
	 *
	 * @return the kb folder ID of this kb folder
	 */
	@Override
	public long getKbFolderId() {
		return model.getKbFolderId();
	}

	/**
	 * Returns the last publish date of this kb folder.
	 *
	 * @return the last publish date of this kb folder
	 */
	@Override
	public Date getLastPublishDate() {
		return model.getLastPublishDate();
	}

	/**
	 * Returns the modified date of this kb folder.
	 *
	 * @return the modified date of this kb folder
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this kb folder.
	 *
	 * @return the mvcc version of this kb folder
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the name of this kb folder.
	 *
	 * @return the name of this kb folder
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	@Override
	public KBFolder getParentKBFolder()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getParentKBFolder();
	}

	/**
	 * Returns the parent kb folder ID of this kb folder.
	 *
	 * @return the parent kb folder ID of this kb folder
	 */
	@Override
	public long getParentKBFolderId() {
		return model.getParentKBFolderId();
	}

	@Override
	public String getParentTitle(java.util.Locale locale)
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getParentTitle(locale);
	}

	/**
	 * Returns the primary key of this kb folder.
	 *
	 * @return the primary key of this kb folder
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the url title of this kb folder.
	 *
	 * @return the url title of this kb folder
	 */
	@Override
	public String getUrlTitle() {
		return model.getUrlTitle();
	}

	/**
	 * Returns the user ID of this kb folder.
	 *
	 * @return the user ID of this kb folder
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this kb folder.
	 *
	 * @return the user name of this kb folder
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this kb folder.
	 *
	 * @return the user uuid of this kb folder
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this kb folder.
	 *
	 * @return the uuid of this kb folder
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	@Override
	public boolean isEmpty()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.isEmpty();
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
	 * Sets the company ID of this kb folder.
	 *
	 * @param companyId the company ID of this kb folder
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this kb folder.
	 *
	 * @param createDate the create date of this kb folder
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the ct collection ID of this kb folder.
	 *
	 * @param ctCollectionId the ct collection ID of this kb folder
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the description of this kb folder.
	 *
	 * @param description the description of this kb folder
	 */
	@Override
	public void setDescription(String description) {
		model.setDescription(description);
	}

	/**
	 * Sets the external reference code of this kb folder.
	 *
	 * @param externalReferenceCode the external reference code of this kb folder
	 */
	@Override
	public void setExternalReferenceCode(String externalReferenceCode) {
		model.setExternalReferenceCode(externalReferenceCode);
	}

	/**
	 * Sets the group ID of this kb folder.
	 *
	 * @param groupId the group ID of this kb folder
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the kb folder ID of this kb folder.
	 *
	 * @param kbFolderId the kb folder ID of this kb folder
	 */
	@Override
	public void setKbFolderId(long kbFolderId) {
		model.setKbFolderId(kbFolderId);
	}

	/**
	 * Sets the last publish date of this kb folder.
	 *
	 * @param lastPublishDate the last publish date of this kb folder
	 */
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		model.setLastPublishDate(lastPublishDate);
	}

	/**
	 * Sets the modified date of this kb folder.
	 *
	 * @param modifiedDate the modified date of this kb folder
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this kb folder.
	 *
	 * @param mvccVersion the mvcc version of this kb folder
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this kb folder.
	 *
	 * @param name the name of this kb folder
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the parent kb folder ID of this kb folder.
	 *
	 * @param parentKBFolderId the parent kb folder ID of this kb folder
	 */
	@Override
	public void setParentKBFolderId(long parentKBFolderId) {
		model.setParentKBFolderId(parentKBFolderId);
	}

	/**
	 * Sets the primary key of this kb folder.
	 *
	 * @param primaryKey the primary key of this kb folder
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the url title of this kb folder.
	 *
	 * @param urlTitle the url title of this kb folder
	 */
	@Override
	public void setUrlTitle(String urlTitle) {
		model.setUrlTitle(urlTitle);
	}

	/**
	 * Sets the user ID of this kb folder.
	 *
	 * @param userId the user ID of this kb folder
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this kb folder.
	 *
	 * @param userName the user name of this kb folder
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this kb folder.
	 *
	 * @param userUuid the user uuid of this kb folder
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this kb folder.
	 *
	 * @param uuid the uuid of this kb folder
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
	public Map<String, Function<KBFolder, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<KBFolder, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected KBFolderWrapper wrap(KBFolder kbFolder) {
		return new KBFolderWrapper(kbFolder);
	}

}