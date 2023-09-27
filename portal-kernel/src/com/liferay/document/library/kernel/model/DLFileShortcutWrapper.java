/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.kernel.model;

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
 * This class is a wrapper for {@link DLFileShortcut}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLFileShortcut
 * @generated
 */
public class DLFileShortcutWrapper
	extends BaseModelWrapper<DLFileShortcut>
	implements DLFileShortcut, ModelWrapper<DLFileShortcut> {

	public DLFileShortcutWrapper(DLFileShortcut dlFileShortcut) {
		super(dlFileShortcut);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("uuid", getUuid());
		attributes.put("fileShortcutId", getFileShortcutId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("repositoryId", getRepositoryId());
		attributes.put("folderId", getFolderId());
		attributes.put("toFileEntryId", getToFileEntryId());
		attributes.put("treePath", getTreePath());
		attributes.put("active", isActive());
		attributes.put("lastPublishDate", getLastPublishDate());
		attributes.put("status", getStatus());
		attributes.put("statusByUserId", getStatusByUserId());
		attributes.put("statusByUserName", getStatusByUserName());
		attributes.put("statusDate", getStatusDate());

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

		Long fileShortcutId = (Long)attributes.get("fileShortcutId");

		if (fileShortcutId != null) {
			setFileShortcutId(fileShortcutId);
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

		Long repositoryId = (Long)attributes.get("repositoryId");

		if (repositoryId != null) {
			setRepositoryId(repositoryId);
		}

		Long folderId = (Long)attributes.get("folderId");

		if (folderId != null) {
			setFolderId(folderId);
		}

		Long toFileEntryId = (Long)attributes.get("toFileEntryId");

		if (toFileEntryId != null) {
			setToFileEntryId(toFileEntryId);
		}

		String treePath = (String)attributes.get("treePath");

		if (treePath != null) {
			setTreePath(treePath);
		}

		Boolean active = (Boolean)attributes.get("active");

		if (active != null) {
			setActive(active);
		}

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}

		Integer status = (Integer)attributes.get("status");

		if (status != null) {
			setStatus(status);
		}

		Long statusByUserId = (Long)attributes.get("statusByUserId");

		if (statusByUserId != null) {
			setStatusByUserId(statusByUserId);
		}

		String statusByUserName = (String)attributes.get("statusByUserName");

		if (statusByUserName != null) {
			setStatusByUserName(statusByUserName);
		}

		Date statusDate = (Date)attributes.get("statusDate");

		if (statusDate != null) {
			setStatusDate(statusDate);
		}
	}

	@Override
	public String buildTreePath()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.buildTreePath();
	}

	@Override
	public DLFileShortcut cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the active of this document library file shortcut.
	 *
	 * @return the active of this document library file shortcut
	 */
	@Override
	public boolean getActive() {
		return model.getActive();
	}

	/**
	 * Returns the company ID of this document library file shortcut.
	 *
	 * @return the company ID of this document library file shortcut
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this document library file shortcut.
	 *
	 * @return the create date of this document library file shortcut
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the ct collection ID of this document library file shortcut.
	 *
	 * @return the ct collection ID of this document library file shortcut
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	@Override
	public DLFolder getDLFolder()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getDLFolder();
	}

	/**
	 * Returns the file shortcut ID of this document library file shortcut.
	 *
	 * @return the file shortcut ID of this document library file shortcut
	 */
	@Override
	public long getFileShortcutId() {
		return model.getFileShortcutId();
	}

	@Override
	public com.liferay.portal.kernel.repository.model.FileVersion
			getFileVersion()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getFileVersion();
	}

	@Override
	public com.liferay.portal.kernel.repository.model.Folder getFolder()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getFolder();
	}

	/**
	 * Returns the folder ID of this document library file shortcut.
	 *
	 * @return the folder ID of this document library file shortcut
	 */
	@Override
	public long getFolderId() {
		return model.getFolderId();
	}

	/**
	 * Returns the group ID of this document library file shortcut.
	 *
	 * @return the group ID of this document library file shortcut
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the last publish date of this document library file shortcut.
	 *
	 * @return the last publish date of this document library file shortcut
	 */
	@Override
	public Date getLastPublishDate() {
		return model.getLastPublishDate();
	}

	/**
	 * Returns the modified date of this document library file shortcut.
	 *
	 * @return the modified date of this document library file shortcut
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this document library file shortcut.
	 *
	 * @return the mvcc version of this document library file shortcut
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this document library file shortcut.
	 *
	 * @return the primary key of this document library file shortcut
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the repository ID of this document library file shortcut.
	 *
	 * @return the repository ID of this document library file shortcut
	 */
	@Override
	public long getRepositoryId() {
		return model.getRepositoryId();
	}

	/**
	 * Returns the status of this document library file shortcut.
	 *
	 * @return the status of this document library file shortcut
	 */
	@Override
	public int getStatus() {
		return model.getStatus();
	}

	/**
	 * Returns the status by user ID of this document library file shortcut.
	 *
	 * @return the status by user ID of this document library file shortcut
	 */
	@Override
	public long getStatusByUserId() {
		return model.getStatusByUserId();
	}

	/**
	 * Returns the status by user name of this document library file shortcut.
	 *
	 * @return the status by user name of this document library file shortcut
	 */
	@Override
	public String getStatusByUserName() {
		return model.getStatusByUserName();
	}

	/**
	 * Returns the status by user uuid of this document library file shortcut.
	 *
	 * @return the status by user uuid of this document library file shortcut
	 */
	@Override
	public String getStatusByUserUuid() {
		return model.getStatusByUserUuid();
	}

	/**
	 * Returns the status date of this document library file shortcut.
	 *
	 * @return the status date of this document library file shortcut
	 */
	@Override
	public Date getStatusDate() {
		return model.getStatusDate();
	}

	/**
	 * Returns the to file entry ID of this document library file shortcut.
	 *
	 * @return the to file entry ID of this document library file shortcut
	 */
	@Override
	public long getToFileEntryId() {
		return model.getToFileEntryId();
	}

	@Override
	public String getToTitle() {
		return model.getToTitle();
	}

	/**
	 * Returns the class primary key of the trash entry for this document library file shortcut.
	 *
	 * @return the class primary key of the trash entry for this document library file shortcut
	 */
	@Override
	public long getTrashEntryClassPK() {
		return model.getTrashEntryClassPK();
	}

	/**
	 * Returns the tree path of this document library file shortcut.
	 *
	 * @return the tree path of this document library file shortcut
	 */
	@Override
	public String getTreePath() {
		return model.getTreePath();
	}

	/**
	 * Returns the user ID of this document library file shortcut.
	 *
	 * @return the user ID of this document library file shortcut
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this document library file shortcut.
	 *
	 * @return the user name of this document library file shortcut
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this document library file shortcut.
	 *
	 * @return the user uuid of this document library file shortcut
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this document library file shortcut.
	 *
	 * @return the uuid of this document library file shortcut
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns <code>true</code> if this document library file shortcut is active.
	 *
	 * @return <code>true</code> if this document library file shortcut is active; <code>false</code> otherwise
	 */
	@Override
	public boolean isActive() {
		return model.isActive();
	}

	/**
	 * Returns <code>true</code> if this document library file shortcut is approved.
	 *
	 * @return <code>true</code> if this document library file shortcut is approved; <code>false</code> otherwise
	 */
	@Override
	public boolean isApproved() {
		return model.isApproved();
	}

	/**
	 * Returns <code>true</code> if this document library file shortcut is denied.
	 *
	 * @return <code>true</code> if this document library file shortcut is denied; <code>false</code> otherwise
	 */
	@Override
	public boolean isDenied() {
		return model.isDenied();
	}

	/**
	 * Returns <code>true</code> if this document library file shortcut is a draft.
	 *
	 * @return <code>true</code> if this document library file shortcut is a draft; <code>false</code> otherwise
	 */
	@Override
	public boolean isDraft() {
		return model.isDraft();
	}

	/**
	 * Returns <code>true</code> if this document library file shortcut is expired.
	 *
	 * @return <code>true</code> if this document library file shortcut is expired; <code>false</code> otherwise
	 */
	@Override
	public boolean isExpired() {
		return model.isExpired();
	}

	/**
	 * Returns <code>true</code> if this document library file shortcut is inactive.
	 *
	 * @return <code>true</code> if this document library file shortcut is inactive; <code>false</code> otherwise
	 */
	@Override
	public boolean isInactive() {
		return model.isInactive();
	}

	/**
	 * Returns <code>true</code> if this document library file shortcut is incomplete.
	 *
	 * @return <code>true</code> if this document library file shortcut is incomplete; <code>false</code> otherwise
	 */
	@Override
	public boolean isIncomplete() {
		return model.isIncomplete();
	}

	@Override
	public boolean isInHiddenFolder() {
		return model.isInHiddenFolder();
	}

	/**
	 * Returns <code>true</code> if this document library file shortcut is in the Recycle Bin.
	 *
	 * @return <code>true</code> if this document library file shortcut is in the Recycle Bin; <code>false</code> otherwise
	 */
	@Override
	public boolean isInTrash() {
		return model.isInTrash();
	}

	/**
	 * Returns <code>true</code> if this document library file shortcut is pending.
	 *
	 * @return <code>true</code> if this document library file shortcut is pending; <code>false</code> otherwise
	 */
	@Override
	public boolean isPending() {
		return model.isPending();
	}

	/**
	 * Returns <code>true</code> if this document library file shortcut is scheduled.
	 *
	 * @return <code>true</code> if this document library file shortcut is scheduled; <code>false</code> otherwise
	 */
	@Override
	public boolean isScheduled() {
		return model.isScheduled();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets whether this document library file shortcut is active.
	 *
	 * @param active the active of this document library file shortcut
	 */
	@Override
	public void setActive(boolean active) {
		model.setActive(active);
	}

	/**
	 * Sets the company ID of this document library file shortcut.
	 *
	 * @param companyId the company ID of this document library file shortcut
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this document library file shortcut.
	 *
	 * @param createDate the create date of this document library file shortcut
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the ct collection ID of this document library file shortcut.
	 *
	 * @param ctCollectionId the ct collection ID of this document library file shortcut
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the file shortcut ID of this document library file shortcut.
	 *
	 * @param fileShortcutId the file shortcut ID of this document library file shortcut
	 */
	@Override
	public void setFileShortcutId(long fileShortcutId) {
		model.setFileShortcutId(fileShortcutId);
	}

	/**
	 * Sets the folder ID of this document library file shortcut.
	 *
	 * @param folderId the folder ID of this document library file shortcut
	 */
	@Override
	public void setFolderId(long folderId) {
		model.setFolderId(folderId);
	}

	/**
	 * Sets the group ID of this document library file shortcut.
	 *
	 * @param groupId the group ID of this document library file shortcut
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the last publish date of this document library file shortcut.
	 *
	 * @param lastPublishDate the last publish date of this document library file shortcut
	 */
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		model.setLastPublishDate(lastPublishDate);
	}

	/**
	 * Sets the modified date of this document library file shortcut.
	 *
	 * @param modifiedDate the modified date of this document library file shortcut
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this document library file shortcut.
	 *
	 * @param mvccVersion the mvcc version of this document library file shortcut
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this document library file shortcut.
	 *
	 * @param primaryKey the primary key of this document library file shortcut
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the repository ID of this document library file shortcut.
	 *
	 * @param repositoryId the repository ID of this document library file shortcut
	 */
	@Override
	public void setRepositoryId(long repositoryId) {
		model.setRepositoryId(repositoryId);
	}

	/**
	 * Sets the status of this document library file shortcut.
	 *
	 * @param status the status of this document library file shortcut
	 */
	@Override
	public void setStatus(int status) {
		model.setStatus(status);
	}

	/**
	 * Sets the status by user ID of this document library file shortcut.
	 *
	 * @param statusByUserId the status by user ID of this document library file shortcut
	 */
	@Override
	public void setStatusByUserId(long statusByUserId) {
		model.setStatusByUserId(statusByUserId);
	}

	/**
	 * Sets the status by user name of this document library file shortcut.
	 *
	 * @param statusByUserName the status by user name of this document library file shortcut
	 */
	@Override
	public void setStatusByUserName(String statusByUserName) {
		model.setStatusByUserName(statusByUserName);
	}

	/**
	 * Sets the status by user uuid of this document library file shortcut.
	 *
	 * @param statusByUserUuid the status by user uuid of this document library file shortcut
	 */
	@Override
	public void setStatusByUserUuid(String statusByUserUuid) {
		model.setStatusByUserUuid(statusByUserUuid);
	}

	/**
	 * Sets the status date of this document library file shortcut.
	 *
	 * @param statusDate the status date of this document library file shortcut
	 */
	@Override
	public void setStatusDate(Date statusDate) {
		model.setStatusDate(statusDate);
	}

	/**
	 * Sets the to file entry ID of this document library file shortcut.
	 *
	 * @param toFileEntryId the to file entry ID of this document library file shortcut
	 */
	@Override
	public void setToFileEntryId(long toFileEntryId) {
		model.setToFileEntryId(toFileEntryId);
	}

	/**
	 * Sets the tree path of this document library file shortcut.
	 *
	 * @param treePath the tree path of this document library file shortcut
	 */
	@Override
	public void setTreePath(String treePath) {
		model.setTreePath(treePath);
	}

	/**
	 * Sets the user ID of this document library file shortcut.
	 *
	 * @param userId the user ID of this document library file shortcut
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this document library file shortcut.
	 *
	 * @param userName the user name of this document library file shortcut
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this document library file shortcut.
	 *
	 * @param userUuid the user uuid of this document library file shortcut
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this document library file shortcut.
	 *
	 * @param uuid the uuid of this document library file shortcut
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
	public void updateTreePath(String treePath) {
		model.updateTreePath(treePath);
	}

	@Override
	public Map<String, Function<DLFileShortcut, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<DLFileShortcut, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected DLFileShortcutWrapper wrap(DLFileShortcut dlFileShortcut) {
		return new DLFileShortcutWrapper(dlFileShortcut);
	}

}