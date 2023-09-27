/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.utility.page.model;

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
 * This class is a wrapper for {@link LayoutUtilityPageEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutUtilityPageEntry
 * @generated
 */
public class LayoutUtilityPageEntryWrapper
	extends BaseModelWrapper<LayoutUtilityPageEntry>
	implements LayoutUtilityPageEntry, ModelWrapper<LayoutUtilityPageEntry> {

	public LayoutUtilityPageEntryWrapper(
		LayoutUtilityPageEntry layoutUtilityPageEntry) {

		super(layoutUtilityPageEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("uuid", getUuid());
		attributes.put("externalReferenceCode", getExternalReferenceCode());
		attributes.put(
			"LayoutUtilityPageEntryId", getLayoutUtilityPageEntryId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("plid", getPlid());
		attributes.put("previewFileEntryId", getPreviewFileEntryId());
		attributes.put(
			"defaultLayoutUtilityPageEntry", isDefaultLayoutUtilityPageEntry());
		attributes.put("name", getName());
		attributes.put("type", getType());
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

		Long LayoutUtilityPageEntryId = (Long)attributes.get(
			"LayoutUtilityPageEntryId");

		if (LayoutUtilityPageEntryId != null) {
			setLayoutUtilityPageEntryId(LayoutUtilityPageEntryId);
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

		Long plid = (Long)attributes.get("plid");

		if (plid != null) {
			setPlid(plid);
		}

		Long previewFileEntryId = (Long)attributes.get("previewFileEntryId");

		if (previewFileEntryId != null) {
			setPreviewFileEntryId(previewFileEntryId);
		}

		Boolean defaultLayoutUtilityPageEntry = (Boolean)attributes.get(
			"defaultLayoutUtilityPageEntry");

		if (defaultLayoutUtilityPageEntry != null) {
			setDefaultLayoutUtilityPageEntry(defaultLayoutUtilityPageEntry);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String type = (String)attributes.get("type");

		if (type != null) {
			setType(type);
		}

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}
	}

	@Override
	public LayoutUtilityPageEntry cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the company ID of this layout utility page entry.
	 *
	 * @return the company ID of this layout utility page entry
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this layout utility page entry.
	 *
	 * @return the create date of this layout utility page entry
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the ct collection ID of this layout utility page entry.
	 *
	 * @return the ct collection ID of this layout utility page entry
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the default layout utility page entry of this layout utility page entry.
	 *
	 * @return the default layout utility page entry of this layout utility page entry
	 */
	@Override
	public boolean getDefaultLayoutUtilityPageEntry() {
		return model.getDefaultLayoutUtilityPageEntry();
	}

	/**
	 * Returns the external reference code of this layout utility page entry.
	 *
	 * @return the external reference code of this layout utility page entry
	 */
	@Override
	public String getExternalReferenceCode() {
		return model.getExternalReferenceCode();
	}

	/**
	 * Returns the group ID of this layout utility page entry.
	 *
	 * @return the group ID of this layout utility page entry
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	@Override
	public String getImagePreviewURL(
		com.liferay.portal.kernel.theme.ThemeDisplay themeDisplay) {

		return model.getImagePreviewURL(themeDisplay);
	}

	/**
	 * Returns the last publish date of this layout utility page entry.
	 *
	 * @return the last publish date of this layout utility page entry
	 */
	@Override
	public Date getLastPublishDate() {
		return model.getLastPublishDate();
	}

	/**
	 * Returns the layout utility page entry ID of this layout utility page entry.
	 *
	 * @return the layout utility page entry ID of this layout utility page entry
	 */
	@Override
	public long getLayoutUtilityPageEntryId() {
		return model.getLayoutUtilityPageEntryId();
	}

	/**
	 * Returns the modified date of this layout utility page entry.
	 *
	 * @return the modified date of this layout utility page entry
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this layout utility page entry.
	 *
	 * @return the mvcc version of this layout utility page entry
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the name of this layout utility page entry.
	 *
	 * @return the name of this layout utility page entry
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the plid of this layout utility page entry.
	 *
	 * @return the plid of this layout utility page entry
	 */
	@Override
	public long getPlid() {
		return model.getPlid();
	}

	/**
	 * Returns the preview file entry ID of this layout utility page entry.
	 *
	 * @return the preview file entry ID of this layout utility page entry
	 */
	@Override
	public long getPreviewFileEntryId() {
		return model.getPreviewFileEntryId();
	}

	/**
	 * Returns the primary key of this layout utility page entry.
	 *
	 * @return the primary key of this layout utility page entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the type of this layout utility page entry.
	 *
	 * @return the type of this layout utility page entry
	 */
	@Override
	public String getType() {
		return model.getType();
	}

	/**
	 * Returns the user ID of this layout utility page entry.
	 *
	 * @return the user ID of this layout utility page entry
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this layout utility page entry.
	 *
	 * @return the user name of this layout utility page entry
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this layout utility page entry.
	 *
	 * @return the user uuid of this layout utility page entry
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this layout utility page entry.
	 *
	 * @return the uuid of this layout utility page entry
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns <code>true</code> if this layout utility page entry is default layout utility page entry.
	 *
	 * @return <code>true</code> if this layout utility page entry is default layout utility page entry; <code>false</code> otherwise
	 */
	@Override
	public boolean isDefaultLayoutUtilityPageEntry() {
		return model.isDefaultLayoutUtilityPageEntry();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this layout utility page entry.
	 *
	 * @param companyId the company ID of this layout utility page entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this layout utility page entry.
	 *
	 * @param createDate the create date of this layout utility page entry
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the ct collection ID of this layout utility page entry.
	 *
	 * @param ctCollectionId the ct collection ID of this layout utility page entry
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets whether this layout utility page entry is default layout utility page entry.
	 *
	 * @param defaultLayoutUtilityPageEntry the default layout utility page entry of this layout utility page entry
	 */
	@Override
	public void setDefaultLayoutUtilityPageEntry(
		boolean defaultLayoutUtilityPageEntry) {

		model.setDefaultLayoutUtilityPageEntry(defaultLayoutUtilityPageEntry);
	}

	/**
	 * Sets the external reference code of this layout utility page entry.
	 *
	 * @param externalReferenceCode the external reference code of this layout utility page entry
	 */
	@Override
	public void setExternalReferenceCode(String externalReferenceCode) {
		model.setExternalReferenceCode(externalReferenceCode);
	}

	/**
	 * Sets the group ID of this layout utility page entry.
	 *
	 * @param groupId the group ID of this layout utility page entry
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the last publish date of this layout utility page entry.
	 *
	 * @param lastPublishDate the last publish date of this layout utility page entry
	 */
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		model.setLastPublishDate(lastPublishDate);
	}

	/**
	 * Sets the layout utility page entry ID of this layout utility page entry.
	 *
	 * @param LayoutUtilityPageEntryId the layout utility page entry ID of this layout utility page entry
	 */
	@Override
	public void setLayoutUtilityPageEntryId(long LayoutUtilityPageEntryId) {
		model.setLayoutUtilityPageEntryId(LayoutUtilityPageEntryId);
	}

	/**
	 * Sets the modified date of this layout utility page entry.
	 *
	 * @param modifiedDate the modified date of this layout utility page entry
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this layout utility page entry.
	 *
	 * @param mvccVersion the mvcc version of this layout utility page entry
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this layout utility page entry.
	 *
	 * @param name the name of this layout utility page entry
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the plid of this layout utility page entry.
	 *
	 * @param plid the plid of this layout utility page entry
	 */
	@Override
	public void setPlid(long plid) {
		model.setPlid(plid);
	}

	/**
	 * Sets the preview file entry ID of this layout utility page entry.
	 *
	 * @param previewFileEntryId the preview file entry ID of this layout utility page entry
	 */
	@Override
	public void setPreviewFileEntryId(long previewFileEntryId) {
		model.setPreviewFileEntryId(previewFileEntryId);
	}

	/**
	 * Sets the primary key of this layout utility page entry.
	 *
	 * @param primaryKey the primary key of this layout utility page entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the type of this layout utility page entry.
	 *
	 * @param type the type of this layout utility page entry
	 */
	@Override
	public void setType(String type) {
		model.setType(type);
	}

	/**
	 * Sets the user ID of this layout utility page entry.
	 *
	 * @param userId the user ID of this layout utility page entry
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this layout utility page entry.
	 *
	 * @param userName the user name of this layout utility page entry
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this layout utility page entry.
	 *
	 * @param userUuid the user uuid of this layout utility page entry
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this layout utility page entry.
	 *
	 * @param uuid the uuid of this layout utility page entry
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
	public Map<String, Function<LayoutUtilityPageEntry, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<LayoutUtilityPageEntry, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected LayoutUtilityPageEntryWrapper wrap(
		LayoutUtilityPageEntry layoutUtilityPageEntry) {

		return new LayoutUtilityPageEntryWrapper(layoutUtilityPageEntry);
	}

}