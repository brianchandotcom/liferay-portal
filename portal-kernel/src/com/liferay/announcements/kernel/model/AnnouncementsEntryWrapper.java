/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.announcements.kernel.model;

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
 * This class is a wrapper for {@link AnnouncementsEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AnnouncementsEntry
 * @generated
 */
public class AnnouncementsEntryWrapper
	extends BaseModelWrapper<AnnouncementsEntry>
	implements AnnouncementsEntry, ModelWrapper<AnnouncementsEntry> {

	public AnnouncementsEntryWrapper(AnnouncementsEntry announcementsEntry) {
		super(announcementsEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("uuid", getUuid());
		attributes.put("entryId", getEntryId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("title", getTitle());
		attributes.put("content", getContent());
		attributes.put("url", getUrl());
		attributes.put("type", getType());
		attributes.put("displayDate", getDisplayDate());
		attributes.put("expirationDate", getExpirationDate());
		attributes.put("priority", getPriority());
		attributes.put("alert", isAlert());

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

		Long entryId = (Long)attributes.get("entryId");

		if (entryId != null) {
			setEntryId(entryId);
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

		String title = (String)attributes.get("title");

		if (title != null) {
			setTitle(title);
		}

		String content = (String)attributes.get("content");

		if (content != null) {
			setContent(content);
		}

		String url = (String)attributes.get("url");

		if (url != null) {
			setUrl(url);
		}

		String type = (String)attributes.get("type");

		if (type != null) {
			setType(type);
		}

		Date displayDate = (Date)attributes.get("displayDate");

		if (displayDate != null) {
			setDisplayDate(displayDate);
		}

		Date expirationDate = (Date)attributes.get("expirationDate");

		if (expirationDate != null) {
			setExpirationDate(expirationDate);
		}

		Integer priority = (Integer)attributes.get("priority");

		if (priority != null) {
			setPriority(priority);
		}

		Boolean alert = (Boolean)attributes.get("alert");

		if (alert != null) {
			setAlert(alert);
		}
	}

	@Override
	public AnnouncementsEntry cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the alert of this announcements entry.
	 *
	 * @return the alert of this announcements entry
	 */
	@Override
	public boolean getAlert() {
		return model.getAlert();
	}

	/**
	 * Returns the fully qualified class name of this announcements entry.
	 *
	 * @return the fully qualified class name of this announcements entry
	 */
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	 * Returns the class name ID of this announcements entry.
	 *
	 * @return the class name ID of this announcements entry
	 */
	@Override
	public long getClassNameId() {
		return model.getClassNameId();
	}

	/**
	 * Returns the class pk of this announcements entry.
	 *
	 * @return the class pk of this announcements entry
	 */
	@Override
	public long getClassPK() {
		return model.getClassPK();
	}

	/**
	 * Returns the company ID of this announcements entry.
	 *
	 * @return the company ID of this announcements entry
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the content of this announcements entry.
	 *
	 * @return the content of this announcements entry
	 */
	@Override
	public String getContent() {
		return model.getContent();
	}

	/**
	 * Returns the create date of this announcements entry.
	 *
	 * @return the create date of this announcements entry
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the ct collection ID of this announcements entry.
	 *
	 * @return the ct collection ID of this announcements entry
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the display date of this announcements entry.
	 *
	 * @return the display date of this announcements entry
	 */
	@Override
	public Date getDisplayDate() {
		return model.getDisplayDate();
	}

	/**
	 * Returns the entry ID of this announcements entry.
	 *
	 * @return the entry ID of this announcements entry
	 */
	@Override
	public long getEntryId() {
		return model.getEntryId();
	}

	/**
	 * Returns the expiration date of this announcements entry.
	 *
	 * @return the expiration date of this announcements entry
	 */
	@Override
	public Date getExpirationDate() {
		return model.getExpirationDate();
	}

	@Override
	public long getGroupId()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getGroupId();
	}

	/**
	 * Returns the modified date of this announcements entry.
	 *
	 * @return the modified date of this announcements entry
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this announcements entry.
	 *
	 * @return the mvcc version of this announcements entry
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this announcements entry.
	 *
	 * @return the primary key of this announcements entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the priority of this announcements entry.
	 *
	 * @return the priority of this announcements entry
	 */
	@Override
	public int getPriority() {
		return model.getPriority();
	}

	/**
	 * Returns the title of this announcements entry.
	 *
	 * @return the title of this announcements entry
	 */
	@Override
	public String getTitle() {
		return model.getTitle();
	}

	/**
	 * Returns the type of this announcements entry.
	 *
	 * @return the type of this announcements entry
	 */
	@Override
	public String getType() {
		return model.getType();
	}

	/**
	 * Returns the url of this announcements entry.
	 *
	 * @return the url of this announcements entry
	 */
	@Override
	public String getUrl() {
		return model.getUrl();
	}

	/**
	 * Returns the user ID of this announcements entry.
	 *
	 * @return the user ID of this announcements entry
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this announcements entry.
	 *
	 * @return the user name of this announcements entry
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this announcements entry.
	 *
	 * @return the user uuid of this announcements entry
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this announcements entry.
	 *
	 * @return the uuid of this announcements entry
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns <code>true</code> if this announcements entry is alert.
	 *
	 * @return <code>true</code> if this announcements entry is alert; <code>false</code> otherwise
	 */
	@Override
	public boolean isAlert() {
		return model.isAlert();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets whether this announcements entry is alert.
	 *
	 * @param alert the alert of this announcements entry
	 */
	@Override
	public void setAlert(boolean alert) {
		model.setAlert(alert);
	}

	@Override
	public void setClassName(String className) {
		model.setClassName(className);
	}

	/**
	 * Sets the class name ID of this announcements entry.
	 *
	 * @param classNameId the class name ID of this announcements entry
	 */
	@Override
	public void setClassNameId(long classNameId) {
		model.setClassNameId(classNameId);
	}

	/**
	 * Sets the class pk of this announcements entry.
	 *
	 * @param classPK the class pk of this announcements entry
	 */
	@Override
	public void setClassPK(long classPK) {
		model.setClassPK(classPK);
	}

	/**
	 * Sets the company ID of this announcements entry.
	 *
	 * @param companyId the company ID of this announcements entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the content of this announcements entry.
	 *
	 * @param content the content of this announcements entry
	 */
	@Override
	public void setContent(String content) {
		model.setContent(content);
	}

	/**
	 * Sets the create date of this announcements entry.
	 *
	 * @param createDate the create date of this announcements entry
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the ct collection ID of this announcements entry.
	 *
	 * @param ctCollectionId the ct collection ID of this announcements entry
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the display date of this announcements entry.
	 *
	 * @param displayDate the display date of this announcements entry
	 */
	@Override
	public void setDisplayDate(Date displayDate) {
		model.setDisplayDate(displayDate);
	}

	/**
	 * Sets the entry ID of this announcements entry.
	 *
	 * @param entryId the entry ID of this announcements entry
	 */
	@Override
	public void setEntryId(long entryId) {
		model.setEntryId(entryId);
	}

	/**
	 * Sets the expiration date of this announcements entry.
	 *
	 * @param expirationDate the expiration date of this announcements entry
	 */
	@Override
	public void setExpirationDate(Date expirationDate) {
		model.setExpirationDate(expirationDate);
	}

	/**
	 * Sets the modified date of this announcements entry.
	 *
	 * @param modifiedDate the modified date of this announcements entry
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this announcements entry.
	 *
	 * @param mvccVersion the mvcc version of this announcements entry
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this announcements entry.
	 *
	 * @param primaryKey the primary key of this announcements entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the priority of this announcements entry.
	 *
	 * @param priority the priority of this announcements entry
	 */
	@Override
	public void setPriority(int priority) {
		model.setPriority(priority);
	}

	/**
	 * Sets the title of this announcements entry.
	 *
	 * @param title the title of this announcements entry
	 */
	@Override
	public void setTitle(String title) {
		model.setTitle(title);
	}

	/**
	 * Sets the type of this announcements entry.
	 *
	 * @param type the type of this announcements entry
	 */
	@Override
	public void setType(String type) {
		model.setType(type);
	}

	/**
	 * Sets the url of this announcements entry.
	 *
	 * @param url the url of this announcements entry
	 */
	@Override
	public void setUrl(String url) {
		model.setUrl(url);
	}

	/**
	 * Sets the user ID of this announcements entry.
	 *
	 * @param userId the user ID of this announcements entry
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this announcements entry.
	 *
	 * @param userName the user name of this announcements entry
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this announcements entry.
	 *
	 * @param userUuid the user uuid of this announcements entry
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this announcements entry.
	 *
	 * @param uuid the uuid of this announcements entry
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
	public Map<String, Function<AnnouncementsEntry, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<AnnouncementsEntry, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected AnnouncementsEntryWrapper wrap(
		AnnouncementsEntry announcementsEntry) {

		return new AnnouncementsEntryWrapper(announcementsEntry);
	}

}