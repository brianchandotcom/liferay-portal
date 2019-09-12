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

package com.liferay.blogs.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link BlogsEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see BlogsEntry
 * @generated
 */
public class BlogsEntryWrapper
	extends BaseModelWrapper<BlogsEntry>
	implements BlogsEntry, ModelWrapper<BlogsEntry> {

	public BlogsEntryWrapper(BlogsEntry blogsEntry) {
		super(blogsEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put("entryId", getEntryId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("title", getTitle());
		attributes.put("subtitle", getSubtitle());
		attributes.put("urlTitle", getUrlTitle());
		attributes.put("description", getDescription());
		attributes.put("content", getContent());
		attributes.put("displayDate", getDisplayDate());
		attributes.put("allowPingbacks", isAllowPingbacks());
		attributes.put("allowTrackbacks", isAllowTrackbacks());
		attributes.put("trackbacks", getTrackbacks());
		attributes.put("coverImageCaption", getCoverImageCaption());
		attributes.put("coverImageFileEntryId", getCoverImageFileEntryId());
		attributes.put("coverImageURL", getCoverImageURL());
		attributes.put("smallImage", isSmallImage());
		attributes.put("smallImageFileEntryId", getSmallImageFileEntryId());
		attributes.put("smallImageId", getSmallImageId());
		attributes.put("smallImageURL", getSmallImageURL());
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

		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long entryId = (Long)attributes.get("entryId");

		if (entryId != null) {
			setEntryId(entryId);
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

		String title = (String)attributes.get("title");

		if (title != null) {
			setTitle(title);
		}

		String subtitle = (String)attributes.get("subtitle");

		if (subtitle != null) {
			setSubtitle(subtitle);
		}

		String urlTitle = (String)attributes.get("urlTitle");

		if (urlTitle != null) {
			setUrlTitle(urlTitle);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		String content = (String)attributes.get("content");

		if (content != null) {
			setContent(content);
		}

		Date displayDate = (Date)attributes.get("displayDate");

		if (displayDate != null) {
			setDisplayDate(displayDate);
		}

		Boolean allowPingbacks = (Boolean)attributes.get("allowPingbacks");

		if (allowPingbacks != null) {
			setAllowPingbacks(allowPingbacks);
		}

		Boolean allowTrackbacks = (Boolean)attributes.get("allowTrackbacks");

		if (allowTrackbacks != null) {
			setAllowTrackbacks(allowTrackbacks);
		}

		String trackbacks = (String)attributes.get("trackbacks");

		if (trackbacks != null) {
			setTrackbacks(trackbacks);
		}

		String coverImageCaption = (String)attributes.get("coverImageCaption");

		if (coverImageCaption != null) {
			setCoverImageCaption(coverImageCaption);
		}

		Long coverImageFileEntryId = (Long)attributes.get(
			"coverImageFileEntryId");

		if (coverImageFileEntryId != null) {
			setCoverImageFileEntryId(coverImageFileEntryId);
		}

		String coverImageURL = (String)attributes.get("coverImageURL");

		if (coverImageURL != null) {
			setCoverImageURL(coverImageURL);
		}

		Boolean smallImage = (Boolean)attributes.get("smallImage");

		if (smallImage != null) {
			setSmallImage(smallImage);
		}

		Long smallImageFileEntryId = (Long)attributes.get(
			"smallImageFileEntryId");

		if (smallImageFileEntryId != null) {
			setSmallImageFileEntryId(smallImageFileEntryId);
		}

		Long smallImageId = (Long)attributes.get("smallImageId");

		if (smallImageId != null) {
			setSmallImageId(smallImageId);
		}

		String smallImageURL = (String)attributes.get("smallImageURL");

		if (smallImageURL != null) {
			setSmallImageURL(smallImageURL);
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

	/**
	 * Returns the allow pingbacks of this blogs entry.
	 *
	 * @return the allow pingbacks of this blogs entry
	 */
	@Override
	public boolean getAllowPingbacks() {
		return model.getAllowPingbacks();
	}

	/**
	 * Returns the allow trackbacks of this blogs entry.
	 *
	 * @return the allow trackbacks of this blogs entry
	 */
	@Override
	public boolean getAllowTrackbacks() {
		return model.getAllowTrackbacks();
	}

	/**
	 * Returns the company ID of this blogs entry.
	 *
	 * @return the company ID of this blogs entry
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the content of this blogs entry.
	 *
	 * @return the content of this blogs entry
	 */
	@Override
	public String getContent() {
		return model.getContent();
	}

	/**
	 * Returns the cover image caption of this blogs entry.
	 *
	 * @return the cover image caption of this blogs entry
	 */
	@Override
	public String getCoverImageCaption() {
		return model.getCoverImageCaption();
	}

	/**
	 * Returns the cover image file entry ID of this blogs entry.
	 *
	 * @return the cover image file entry ID of this blogs entry
	 */
	@Override
	public long getCoverImageFileEntryId() {
		return model.getCoverImageFileEntryId();
	}

	/**
	 * Returns the cover image url of this blogs entry.
	 *
	 * @return the cover image url of this blogs entry
	 */
	@Override
	public String getCoverImageURL() {
		return model.getCoverImageURL();
	}

	@Override
	public String getCoverImageURL(
			com.liferay.portal.kernel.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getCoverImageURL(themeDisplay);
	}

	/**
	 * Returns the create date of this blogs entry.
	 *
	 * @return the create date of this blogs entry
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the description of this blogs entry.
	 *
	 * @return the description of this blogs entry
	 */
	@Override
	public String getDescription() {
		return model.getDescription();
	}

	/**
	 * Returns the display date of this blogs entry.
	 *
	 * @return the display date of this blogs entry
	 */
	@Override
	public Date getDisplayDate() {
		return model.getDisplayDate();
	}

	/**
	 * Returns the entry ID of this blogs entry.
	 *
	 * @return the entry ID of this blogs entry
	 */
	@Override
	public long getEntryId() {
		return model.getEntryId();
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 #getSmallImageURL(ThemeDisplay)}
	 */
	@Deprecated
	@Override
	public String getEntryImageURL(
		com.liferay.portal.kernel.theme.ThemeDisplay themeDisplay) {

		return model.getEntryImageURL(themeDisplay);
	}

	/**
	 * Returns the group ID of this blogs entry.
	 *
	 * @return the group ID of this blogs entry
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the last publish date of this blogs entry.
	 *
	 * @return the last publish date of this blogs entry
	 */
	@Override
	public Date getLastPublishDate() {
		return model.getLastPublishDate();
	}

	/**
	 * Returns the modified date of this blogs entry.
	 *
	 * @return the modified date of this blogs entry
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this blogs entry.
	 *
	 * @return the mvcc version of this blogs entry
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this blogs entry.
	 *
	 * @return the primary key of this blogs entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the small image of this blogs entry.
	 *
	 * @return the small image of this blogs entry
	 */
	@Override
	public boolean getSmallImage() {
		return model.getSmallImage();
	}

	/**
	 * Returns the small image file entry ID of this blogs entry.
	 *
	 * @return the small image file entry ID of this blogs entry
	 */
	@Override
	public long getSmallImageFileEntryId() {
		return model.getSmallImageFileEntryId();
	}

	/**
	 * Returns the small image ID of this blogs entry.
	 *
	 * @return the small image ID of this blogs entry
	 */
	@Override
	public long getSmallImageId() {
		return model.getSmallImageId();
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public String getSmallImageType()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getSmallImageType();
	}

	/**
	 * Returns the small image url of this blogs entry.
	 *
	 * @return the small image url of this blogs entry
	 */
	@Override
	public String getSmallImageURL() {
		return model.getSmallImageURL();
	}

	@Override
	public String getSmallImageURL(
			com.liferay.portal.kernel.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getSmallImageURL(themeDisplay);
	}

	/**
	 * Returns the status of this blogs entry.
	 *
	 * @return the status of this blogs entry
	 */
	@Override
	public int getStatus() {
		return model.getStatus();
	}

	/**
	 * Returns the status by user ID of this blogs entry.
	 *
	 * @return the status by user ID of this blogs entry
	 */
	@Override
	public long getStatusByUserId() {
		return model.getStatusByUserId();
	}

	/**
	 * Returns the status by user name of this blogs entry.
	 *
	 * @return the status by user name of this blogs entry
	 */
	@Override
	public String getStatusByUserName() {
		return model.getStatusByUserName();
	}

	/**
	 * Returns the status by user uuid of this blogs entry.
	 *
	 * @return the status by user uuid of this blogs entry
	 */
	@Override
	public String getStatusByUserUuid() {
		return model.getStatusByUserUuid();
	}

	/**
	 * Returns the status date of this blogs entry.
	 *
	 * @return the status date of this blogs entry
	 */
	@Override
	public Date getStatusDate() {
		return model.getStatusDate();
	}

	/**
	 * Returns the subtitle of this blogs entry.
	 *
	 * @return the subtitle of this blogs entry
	 */
	@Override
	public String getSubtitle() {
		return model.getSubtitle();
	}

	/**
	 * Returns the title of this blogs entry.
	 *
	 * @return the title of this blogs entry
	 */
	@Override
	public String getTitle() {
		return model.getTitle();
	}

	/**
	 * Returns the trackbacks of this blogs entry.
	 *
	 * @return the trackbacks of this blogs entry
	 */
	@Override
	public String getTrackbacks() {
		return model.getTrackbacks();
	}

	/**
	 * Returns the trash entry created when this blogs entry was moved to the Recycle Bin. The trash entry may belong to one of the ancestors of this blogs entry.
	 *
	 * @return the trash entry created when this blogs entry was moved to the Recycle Bin
	 */
	@Override
	public com.liferay.trash.kernel.model.TrashEntry getTrashEntry()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getTrashEntry();
	}

	/**
	 * Returns the class primary key of the trash entry for this blogs entry.
	 *
	 * @return the class primary key of the trash entry for this blogs entry
	 */
	@Override
	public long getTrashEntryClassPK() {
		return model.getTrashEntryClassPK();
	}

	/**
	 * Returns the trash handler for this blogs entry.
	 *
	 * @return the trash handler for this blogs entry
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public com.liferay.portal.kernel.trash.TrashHandler getTrashHandler() {
		return model.getTrashHandler();
	}

	/**
	 * Returns the url title of this blogs entry.
	 *
	 * @return the url title of this blogs entry
	 */
	@Override
	public String getUrlTitle() {
		return model.getUrlTitle();
	}

	/**
	 * Returns the user ID of this blogs entry.
	 *
	 * @return the user ID of this blogs entry
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this blogs entry.
	 *
	 * @return the user name of this blogs entry
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this blogs entry.
	 *
	 * @return the user uuid of this blogs entry
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this blogs entry.
	 *
	 * @return the uuid of this blogs entry
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns <code>true</code> if this blogs entry is allow pingbacks.
	 *
	 * @return <code>true</code> if this blogs entry is allow pingbacks; <code>false</code> otherwise
	 */
	@Override
	public boolean isAllowPingbacks() {
		return model.isAllowPingbacks();
	}

	/**
	 * Returns <code>true</code> if this blogs entry is allow trackbacks.
	 *
	 * @return <code>true</code> if this blogs entry is allow trackbacks; <code>false</code> otherwise
	 */
	@Override
	public boolean isAllowTrackbacks() {
		return model.isAllowTrackbacks();
	}

	/**
	 * Returns <code>true</code> if this blogs entry is approved.
	 *
	 * @return <code>true</code> if this blogs entry is approved; <code>false</code> otherwise
	 */
	@Override
	public boolean isApproved() {
		return model.isApproved();
	}

	/**
	 * Returns <code>true</code> if this blogs entry is denied.
	 *
	 * @return <code>true</code> if this blogs entry is denied; <code>false</code> otherwise
	 */
	@Override
	public boolean isDenied() {
		return model.isDenied();
	}

	/**
	 * Returns <code>true</code> if this blogs entry is a draft.
	 *
	 * @return <code>true</code> if this blogs entry is a draft; <code>false</code> otherwise
	 */
	@Override
	public boolean isDraft() {
		return model.isDraft();
	}

	/**
	 * Returns <code>true</code> if this blogs entry is expired.
	 *
	 * @return <code>true</code> if this blogs entry is expired; <code>false</code> otherwise
	 */
	@Override
	public boolean isExpired() {
		return model.isExpired();
	}

	/**
	 * Returns <code>true</code> if this blogs entry is inactive.
	 *
	 * @return <code>true</code> if this blogs entry is inactive; <code>false</code> otherwise
	 */
	@Override
	public boolean isInactive() {
		return model.isInactive();
	}

	/**
	 * Returns <code>true</code> if this blogs entry is incomplete.
	 *
	 * @return <code>true</code> if this blogs entry is incomplete; <code>false</code> otherwise
	 */
	@Override
	public boolean isIncomplete() {
		return model.isIncomplete();
	}

	/**
	 * Returns <code>true</code> if this blogs entry is in the Recycle Bin.
	 *
	 * @return <code>true</code> if this blogs entry is in the Recycle Bin; <code>false</code> otherwise
	 */
	@Override
	public boolean isInTrash() {
		return model.isInTrash();
	}

	/**
	 * Returns <code>true</code> if the parent of this blogs entry is in the Recycle Bin.
	 *
	 * @return <code>true</code> if the parent of this blogs entry is in the Recycle Bin; <code>false</code> otherwise
	 */
	@Override
	public boolean isInTrashContainer() {
		return model.isInTrashContainer();
	}

	@Override
	public boolean isInTrashExplicitly() {
		return model.isInTrashExplicitly();
	}

	@Override
	public boolean isInTrashImplicitly() {
		return model.isInTrashImplicitly();
	}

	/**
	 * Returns <code>true</code> if this blogs entry is pending.
	 *
	 * @return <code>true</code> if this blogs entry is pending; <code>false</code> otherwise
	 */
	@Override
	public boolean isPending() {
		return model.isPending();
	}

	/**
	 * Returns <code>true</code> if this blogs entry is scheduled.
	 *
	 * @return <code>true</code> if this blogs entry is scheduled; <code>false</code> otherwise
	 */
	@Override
	public boolean isScheduled() {
		return model.isScheduled();
	}

	/**
	 * Returns <code>true</code> if this blogs entry is small image.
	 *
	 * @return <code>true</code> if this blogs entry is small image; <code>false</code> otherwise
	 */
	@Override
	public boolean isSmallImage() {
		return model.isSmallImage();
	}

	@Override
	public boolean isVisible() {
		return model.isVisible();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a blogs entry model instance should use the <code>BlogsEntry</code> interface instead.
	 */
	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets whether this blogs entry is allow pingbacks.
	 *
	 * @param allowPingbacks the allow pingbacks of this blogs entry
	 */
	@Override
	public void setAllowPingbacks(boolean allowPingbacks) {
		model.setAllowPingbacks(allowPingbacks);
	}

	/**
	 * Sets whether this blogs entry is allow trackbacks.
	 *
	 * @param allowTrackbacks the allow trackbacks of this blogs entry
	 */
	@Override
	public void setAllowTrackbacks(boolean allowTrackbacks) {
		model.setAllowTrackbacks(allowTrackbacks);
	}

	/**
	 * Sets the company ID of this blogs entry.
	 *
	 * @param companyId the company ID of this blogs entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the content of this blogs entry.
	 *
	 * @param content the content of this blogs entry
	 */
	@Override
	public void setContent(String content) {
		model.setContent(content);
	}

	/**
	 * Sets the cover image caption of this blogs entry.
	 *
	 * @param coverImageCaption the cover image caption of this blogs entry
	 */
	@Override
	public void setCoverImageCaption(String coverImageCaption) {
		model.setCoverImageCaption(coverImageCaption);
	}

	/**
	 * Sets the cover image file entry ID of this blogs entry.
	 *
	 * @param coverImageFileEntryId the cover image file entry ID of this blogs entry
	 */
	@Override
	public void setCoverImageFileEntryId(long coverImageFileEntryId) {
		model.setCoverImageFileEntryId(coverImageFileEntryId);
	}

	/**
	 * Sets the cover image url of this blogs entry.
	 *
	 * @param coverImageURL the cover image url of this blogs entry
	 */
	@Override
	public void setCoverImageURL(String coverImageURL) {
		model.setCoverImageURL(coverImageURL);
	}

	/**
	 * Sets the create date of this blogs entry.
	 *
	 * @param createDate the create date of this blogs entry
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the description of this blogs entry.
	 *
	 * @param description the description of this blogs entry
	 */
	@Override
	public void setDescription(String description) {
		model.setDescription(description);
	}

	/**
	 * Sets the display date of this blogs entry.
	 *
	 * @param displayDate the display date of this blogs entry
	 */
	@Override
	public void setDisplayDate(Date displayDate) {
		model.setDisplayDate(displayDate);
	}

	/**
	 * Sets the entry ID of this blogs entry.
	 *
	 * @param entryId the entry ID of this blogs entry
	 */
	@Override
	public void setEntryId(long entryId) {
		model.setEntryId(entryId);
	}

	/**
	 * Sets the group ID of this blogs entry.
	 *
	 * @param groupId the group ID of this blogs entry
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the last publish date of this blogs entry.
	 *
	 * @param lastPublishDate the last publish date of this blogs entry
	 */
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		model.setLastPublishDate(lastPublishDate);
	}

	/**
	 * Sets the modified date of this blogs entry.
	 *
	 * @param modifiedDate the modified date of this blogs entry
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this blogs entry.
	 *
	 * @param mvccVersion the mvcc version of this blogs entry
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this blogs entry.
	 *
	 * @param primaryKey the primary key of this blogs entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets whether this blogs entry is small image.
	 *
	 * @param smallImage the small image of this blogs entry
	 */
	@Override
	public void setSmallImage(boolean smallImage) {
		model.setSmallImage(smallImage);
	}

	/**
	 * Sets the small image file entry ID of this blogs entry.
	 *
	 * @param smallImageFileEntryId the small image file entry ID of this blogs entry
	 */
	@Override
	public void setSmallImageFileEntryId(long smallImageFileEntryId) {
		model.setSmallImageFileEntryId(smallImageFileEntryId);
	}

	/**
	 * Sets the small image ID of this blogs entry.
	 *
	 * @param smallImageId the small image ID of this blogs entry
	 */
	@Override
	public void setSmallImageId(long smallImageId) {
		model.setSmallImageId(smallImageId);
	}

	@Override
	public void setSmallImageType(String smallImageType) {
		model.setSmallImageType(smallImageType);
	}

	/**
	 * Sets the small image url of this blogs entry.
	 *
	 * @param smallImageURL the small image url of this blogs entry
	 */
	@Override
	public void setSmallImageURL(String smallImageURL) {
		model.setSmallImageURL(smallImageURL);
	}

	/**
	 * Sets the status of this blogs entry.
	 *
	 * @param status the status of this blogs entry
	 */
	@Override
	public void setStatus(int status) {
		model.setStatus(status);
	}

	/**
	 * Sets the status by user ID of this blogs entry.
	 *
	 * @param statusByUserId the status by user ID of this blogs entry
	 */
	@Override
	public void setStatusByUserId(long statusByUserId) {
		model.setStatusByUserId(statusByUserId);
	}

	/**
	 * Sets the status by user name of this blogs entry.
	 *
	 * @param statusByUserName the status by user name of this blogs entry
	 */
	@Override
	public void setStatusByUserName(String statusByUserName) {
		model.setStatusByUserName(statusByUserName);
	}

	/**
	 * Sets the status by user uuid of this blogs entry.
	 *
	 * @param statusByUserUuid the status by user uuid of this blogs entry
	 */
	@Override
	public void setStatusByUserUuid(String statusByUserUuid) {
		model.setStatusByUserUuid(statusByUserUuid);
	}

	/**
	 * Sets the status date of this blogs entry.
	 *
	 * @param statusDate the status date of this blogs entry
	 */
	@Override
	public void setStatusDate(Date statusDate) {
		model.setStatusDate(statusDate);
	}

	/**
	 * Sets the subtitle of this blogs entry.
	 *
	 * @param subtitle the subtitle of this blogs entry
	 */
	@Override
	public void setSubtitle(String subtitle) {
		model.setSubtitle(subtitle);
	}

	/**
	 * Sets the title of this blogs entry.
	 *
	 * @param title the title of this blogs entry
	 */
	@Override
	public void setTitle(String title) {
		model.setTitle(title);
	}

	/**
	 * Sets the trackbacks of this blogs entry.
	 *
	 * @param trackbacks the trackbacks of this blogs entry
	 */
	@Override
	public void setTrackbacks(String trackbacks) {
		model.setTrackbacks(trackbacks);
	}

	/**
	 * Sets the url title of this blogs entry.
	 *
	 * @param urlTitle the url title of this blogs entry
	 */
	@Override
	public void setUrlTitle(String urlTitle) {
		model.setUrlTitle(urlTitle);
	}

	/**
	 * Sets the user ID of this blogs entry.
	 *
	 * @param userId the user ID of this blogs entry
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this blogs entry.
	 *
	 * @param userName the user name of this blogs entry
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this blogs entry.
	 *
	 * @param userUuid the user uuid of this blogs entry
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this blogs entry.
	 *
	 * @param uuid the uuid of this blogs entry
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected BlogsEntryWrapper wrap(BlogsEntry blogsEntry) {
		return new BlogsEntryWrapper(blogsEntry);
	}

}