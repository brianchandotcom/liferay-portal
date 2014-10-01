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

package com.liferay.portlet.blogs.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelWrapper;

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
@ProviderType
public class BlogsEntryWrapper implements BlogsEntry, ModelWrapper<BlogsEntry> {
	public BlogsEntryWrapper(BlogsEntry blogsEntry) {
		_blogsEntry = blogsEntry;
	}

	@Override
	public Class<?> getModelClass() {
		return BlogsEntry.class;
	}

	@Override
	public String getModelClassName() {
		return BlogsEntry.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

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
		attributes.put("allowPingbacks", getAllowPingbacks());
		attributes.put("allowTrackbacks", getAllowTrackbacks());
		attributes.put("trackbacks", getTrackbacks());
		attributes.put("coverImageFileEntryId", getCoverImageFileEntryId());
		attributes.put("coverImageURL", getCoverImageURL());
		attributes.put("smallImage", getSmallImage());
		attributes.put("smallImageFileEntryId", getSmallImageFileEntryId());
		attributes.put("smallImageId", getSmallImageId());
		attributes.put("smallImageURL", getSmallImageURL());
		attributes.put("status", getStatus());
		attributes.put("statusByUserId", getStatusByUserId());
		attributes.put("statusByUserName", getStatusByUserName());
		attributes.put("statusDate", getStatusDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
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
	public java.lang.Object clone() {
		return new BlogsEntryWrapper((BlogsEntry)_blogsEntry.clone());
	}

	@Override
	public int compareTo(com.liferay.portlet.blogs.model.BlogsEntry blogsEntry) {
		return _blogsEntry.compareTo(blogsEntry);
	}

	/**
	* Returns the allow pingbacks of this blogs entry.
	*
	* @return the allow pingbacks of this blogs entry
	*/
	@Override
	public boolean getAllowPingbacks() {
		return _blogsEntry.getAllowPingbacks();
	}

	/**
	* Returns the allow trackbacks of this blogs entry.
	*
	* @return the allow trackbacks of this blogs entry
	*/
	@Override
	public boolean getAllowTrackbacks() {
		return _blogsEntry.getAllowTrackbacks();
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #isApproved()}
	*/
	@Deprecated
	@Override
	public boolean getApproved() {
		return _blogsEntry.getApproved();
	}

	/**
	* Returns the company ID of this blogs entry.
	*
	* @return the company ID of this blogs entry
	*/
	@Override
	public long getCompanyId() {
		return _blogsEntry.getCompanyId();
	}

	/**
	* Returns the content of this blogs entry.
	*
	* @return the content of this blogs entry
	*/
	@Override
	public java.lang.String getContent() {
		return _blogsEntry.getContent();
	}

	/**
	* Returns the cover image file entry ID of this blogs entry.
	*
	* @return the cover image file entry ID of this blogs entry
	*/
	@Override
	public long getCoverImageFileEntryId() {
		return _blogsEntry.getCoverImageFileEntryId();
	}

	/**
	* Returns the cover image u r l of this blogs entry.
	*
	* @return the cover image u r l of this blogs entry
	*/
	@Override
	public java.lang.String getCoverImageURL() {
		return _blogsEntry.getCoverImageURL();
	}

	/**
	* Returns the create date of this blogs entry.
	*
	* @return the create date of this blogs entry
	*/
	@Override
	public java.util.Date getCreateDate() {
		return _blogsEntry.getCreateDate();
	}

	/**
	* Returns the description of this blogs entry.
	*
	* @return the description of this blogs entry
	*/
	@Override
	public java.lang.String getDescription() {
		return _blogsEntry.getDescription();
	}

	/**
	* Returns the display date of this blogs entry.
	*
	* @return the display date of this blogs entry
	*/
	@Override
	public java.util.Date getDisplayDate() {
		return _blogsEntry.getDisplayDate();
	}

	/**
	* Returns the entry ID of this blogs entry.
	*
	* @return the entry ID of this blogs entry
	*/
	@Override
	public long getEntryId() {
		return _blogsEntry.getEntryId();
	}

	@Override
	public java.lang.String getEntryImageURL(
		com.liferay.portal.theme.ThemeDisplay themeDisplay) {
		return _blogsEntry.getEntryImageURL(themeDisplay);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _blogsEntry.getExpandoBridge();
	}

	/**
	* Returns the group ID of this blogs entry.
	*
	* @return the group ID of this blogs entry
	*/
	@Override
	public long getGroupId() {
		return _blogsEntry.getGroupId();
	}

	@Override
	public long getImagesFolderId() {
		return _blogsEntry.getImagesFolderId();
	}

	/**
	* Returns the modified date of this blogs entry.
	*
	* @return the modified date of this blogs entry
	*/
	@Override
	public java.util.Date getModifiedDate() {
		return _blogsEntry.getModifiedDate();
	}

	/**
	* Returns the primary key of this blogs entry.
	*
	* @return the primary key of this blogs entry
	*/
	@Override
	public long getPrimaryKey() {
		return _blogsEntry.getPrimaryKey();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _blogsEntry.getPrimaryKeyObj();
	}

	/**
	* Returns the small image of this blogs entry.
	*
	* @return the small image of this blogs entry
	*/
	@Override
	public boolean getSmallImage() {
		return _blogsEntry.getSmallImage();
	}

	/**
	* Returns the small image file entry ID of this blogs entry.
	*
	* @return the small image file entry ID of this blogs entry
	*/
	@Override
	public long getSmallImageFileEntryId() {
		return _blogsEntry.getSmallImageFileEntryId();
	}

	/**
	* Returns the small image ID of this blogs entry.
	*
	* @return the small image ID of this blogs entry
	*/
	@Override
	public long getSmallImageId() {
		return _blogsEntry.getSmallImageId();
	}

	@Override
	public java.lang.String getSmallImageType()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _blogsEntry.getSmallImageType();
	}

	/**
	* Returns the small image u r l of this blogs entry.
	*
	* @return the small image u r l of this blogs entry
	*/
	@Override
	public java.lang.String getSmallImageURL() {
		return _blogsEntry.getSmallImageURL();
	}

	/**
	* Returns the status of this blogs entry.
	*
	* @return the status of this blogs entry
	*/
	@Override
	public int getStatus() {
		return _blogsEntry.getStatus();
	}

	/**
	* Returns the status by user ID of this blogs entry.
	*
	* @return the status by user ID of this blogs entry
	*/
	@Override
	public long getStatusByUserId() {
		return _blogsEntry.getStatusByUserId();
	}

	/**
	* Returns the status by user name of this blogs entry.
	*
	* @return the status by user name of this blogs entry
	*/
	@Override
	public java.lang.String getStatusByUserName() {
		return _blogsEntry.getStatusByUserName();
	}

	/**
	* Returns the status by user uuid of this blogs entry.
	*
	* @return the status by user uuid of this blogs entry
	*/
	@Override
	public java.lang.String getStatusByUserUuid() {
		return _blogsEntry.getStatusByUserUuid();
	}

	/**
	* Returns the status date of this blogs entry.
	*
	* @return the status date of this blogs entry
	*/
	@Override
	public java.util.Date getStatusDate() {
		return _blogsEntry.getStatusDate();
	}

	/**
	* Returns the subtitle of this blogs entry.
	*
	* @return the subtitle of this blogs entry
	*/
	@Override
	public java.lang.String getSubtitle() {
		return _blogsEntry.getSubtitle();
	}

	/**
	* Returns the title of this blogs entry.
	*
	* @return the title of this blogs entry
	*/
	@Override
	public java.lang.String getTitle() {
		return _blogsEntry.getTitle();
	}

	/**
	* Returns the trackbacks of this blogs entry.
	*
	* @return the trackbacks of this blogs entry
	*/
	@Override
	public java.lang.String getTrackbacks() {
		return _blogsEntry.getTrackbacks();
	}

	/**
	* Returns the trash entry created when this blogs entry was moved to the Recycle Bin. The trash entry may belong to one of the ancestors of this blogs entry.
	*
	* @return the trash entry created when this blogs entry was moved to the Recycle Bin
	*/
	@Override
	public com.liferay.portlet.trash.model.TrashEntry getTrashEntry()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _blogsEntry.getTrashEntry();
	}

	/**
	* Returns the class primary key of the trash entry for this blogs entry.
	*
	* @return the class primary key of the trash entry for this blogs entry
	*/
	@Override
	public long getTrashEntryClassPK() {
		return _blogsEntry.getTrashEntryClassPK();
	}

	/**
	* Returns the trash handler for this blogs entry.
	*
	* @return the trash handler for this blogs entry
	*/
	@Override
	public com.liferay.portal.kernel.trash.TrashHandler getTrashHandler() {
		return _blogsEntry.getTrashHandler();
	}

	/**
	* Returns the url title of this blogs entry.
	*
	* @return the url title of this blogs entry
	*/
	@Override
	public java.lang.String getUrlTitle() {
		return _blogsEntry.getUrlTitle();
	}

	/**
	* Returns the user ID of this blogs entry.
	*
	* @return the user ID of this blogs entry
	*/
	@Override
	public long getUserId() {
		return _blogsEntry.getUserId();
	}

	/**
	* Returns the user name of this blogs entry.
	*
	* @return the user name of this blogs entry
	*/
	@Override
	public java.lang.String getUserName() {
		return _blogsEntry.getUserName();
	}

	/**
	* Returns the user uuid of this blogs entry.
	*
	* @return the user uuid of this blogs entry
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _blogsEntry.getUserUuid();
	}

	/**
	* Returns the uuid of this blogs entry.
	*
	* @return the uuid of this blogs entry
	*/
	@Override
	public java.lang.String getUuid() {
		return _blogsEntry.getUuid();
	}

	@Override
	public int hashCode() {
		return _blogsEntry.hashCode();
	}

	/**
	* Returns <code>true</code> if this blogs entry is allow pingbacks.
	*
	* @return <code>true</code> if this blogs entry is allow pingbacks; <code>false</code> otherwise
	*/
	@Override
	public boolean isAllowPingbacks() {
		return _blogsEntry.isAllowPingbacks();
	}

	/**
	* Returns <code>true</code> if this blogs entry is allow trackbacks.
	*
	* @return <code>true</code> if this blogs entry is allow trackbacks; <code>false</code> otherwise
	*/
	@Override
	public boolean isAllowTrackbacks() {
		return _blogsEntry.isAllowTrackbacks();
	}

	/**
	* Returns <code>true</code> if this blogs entry is approved.
	*
	* @return <code>true</code> if this blogs entry is approved; <code>false</code> otherwise
	*/
	@Override
	public boolean isApproved() {
		return _blogsEntry.isApproved();
	}

	@Override
	public boolean isCachedModel() {
		return _blogsEntry.isCachedModel();
	}

	/**
	* Returns <code>true</code> if this blogs entry is denied.
	*
	* @return <code>true</code> if this blogs entry is denied; <code>false</code> otherwise
	*/
	@Override
	public boolean isDenied() {
		return _blogsEntry.isDenied();
	}

	/**
	* Returns <code>true</code> if this blogs entry is a draft.
	*
	* @return <code>true</code> if this blogs entry is a draft; <code>false</code> otherwise
	*/
	@Override
	public boolean isDraft() {
		return _blogsEntry.isDraft();
	}

	@Override
	public boolean isEscapedModel() {
		return _blogsEntry.isEscapedModel();
	}

	/**
	* Returns <code>true</code> if this blogs entry is expired.
	*
	* @return <code>true</code> if this blogs entry is expired; <code>false</code> otherwise
	*/
	@Override
	public boolean isExpired() {
		return _blogsEntry.isExpired();
	}

	/**
	* Returns <code>true</code> if this blogs entry is in the Recycle Bin.
	*
	* @return <code>true</code> if this blogs entry is in the Recycle Bin; <code>false</code> otherwise
	*/
	@Override
	public boolean isInTrash() {
		return _blogsEntry.isInTrash();
	}

	/**
	* Returns <code>true</code> if the parent of this blogs entry is in the Recycle Bin.
	*
	* @return <code>true</code> if the parent of this blogs entry is in the Recycle Bin; <code>false</code> otherwise
	*/
	@Override
	public boolean isInTrashContainer() {
		return _blogsEntry.isInTrashContainer();
	}

	@Override
	public boolean isInTrashExplicitly() {
		return _blogsEntry.isInTrashExplicitly();
	}

	@Override
	public boolean isInTrashImplicitly() {
		return _blogsEntry.isInTrashImplicitly();
	}

	/**
	* Returns <code>true</code> if this blogs entry is inactive.
	*
	* @return <code>true</code> if this blogs entry is inactive; <code>false</code> otherwise
	*/
	@Override
	public boolean isInactive() {
		return _blogsEntry.isInactive();
	}

	/**
	* Returns <code>true</code> if this blogs entry is incomplete.
	*
	* @return <code>true</code> if this blogs entry is incomplete; <code>false</code> otherwise
	*/
	@Override
	public boolean isIncomplete() {
		return _blogsEntry.isIncomplete();
	}

	@Override
	public boolean isNew() {
		return _blogsEntry.isNew();
	}

	/**
	* Returns <code>true</code> if this blogs entry is pending.
	*
	* @return <code>true</code> if this blogs entry is pending; <code>false</code> otherwise
	*/
	@Override
	public boolean isPending() {
		return _blogsEntry.isPending();
	}

	/**
	* Returns <code>true</code> if this blogs entry is scheduled.
	*
	* @return <code>true</code> if this blogs entry is scheduled; <code>false</code> otherwise
	*/
	@Override
	public boolean isScheduled() {
		return _blogsEntry.isScheduled();
	}

	/**
	* Returns <code>true</code> if this blogs entry is small image.
	*
	* @return <code>true</code> if this blogs entry is small image; <code>false</code> otherwise
	*/
	@Override
	public boolean isSmallImage() {
		return _blogsEntry.isSmallImage();
	}

	@Override
	public boolean isVisible() {
		return _blogsEntry.isVisible();
	}

	@Override
	public void persist() {
		_blogsEntry.persist();
	}

	/**
	* Sets whether this blogs entry is allow pingbacks.
	*
	* @param allowPingbacks the allow pingbacks of this blogs entry
	*/
	@Override
	public void setAllowPingbacks(boolean allowPingbacks) {
		_blogsEntry.setAllowPingbacks(allowPingbacks);
	}

	/**
	* Sets whether this blogs entry is allow trackbacks.
	*
	* @param allowTrackbacks the allow trackbacks of this blogs entry
	*/
	@Override
	public void setAllowTrackbacks(boolean allowTrackbacks) {
		_blogsEntry.setAllowTrackbacks(allowTrackbacks);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_blogsEntry.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this blogs entry.
	*
	* @param companyId the company ID of this blogs entry
	*/
	@Override
	public void setCompanyId(long companyId) {
		_blogsEntry.setCompanyId(companyId);
	}

	/**
	* Sets the content of this blogs entry.
	*
	* @param content the content of this blogs entry
	*/
	@Override
	public void setContent(java.lang.String content) {
		_blogsEntry.setContent(content);
	}

	/**
	* Sets the cover image file entry ID of this blogs entry.
	*
	* @param coverImageFileEntryId the cover image file entry ID of this blogs entry
	*/
	@Override
	public void setCoverImageFileEntryId(long coverImageFileEntryId) {
		_blogsEntry.setCoverImageFileEntryId(coverImageFileEntryId);
	}

	/**
	* Sets the cover image u r l of this blogs entry.
	*
	* @param coverImageURL the cover image u r l of this blogs entry
	*/
	@Override
	public void setCoverImageURL(java.lang.String coverImageURL) {
		_blogsEntry.setCoverImageURL(coverImageURL);
	}

	/**
	* Sets the create date of this blogs entry.
	*
	* @param createDate the create date of this blogs entry
	*/
	@Override
	public void setCreateDate(java.util.Date createDate) {
		_blogsEntry.setCreateDate(createDate);
	}

	/**
	* Sets the description of this blogs entry.
	*
	* @param description the description of this blogs entry
	*/
	@Override
	public void setDescription(java.lang.String description) {
		_blogsEntry.setDescription(description);
	}

	/**
	* Sets the display date of this blogs entry.
	*
	* @param displayDate the display date of this blogs entry
	*/
	@Override
	public void setDisplayDate(java.util.Date displayDate) {
		_blogsEntry.setDisplayDate(displayDate);
	}

	/**
	* Sets the entry ID of this blogs entry.
	*
	* @param entryId the entry ID of this blogs entry
	*/
	@Override
	public void setEntryId(long entryId) {
		_blogsEntry.setEntryId(entryId);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_blogsEntry.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_blogsEntry.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_blogsEntry.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this blogs entry.
	*
	* @param groupId the group ID of this blogs entry
	*/
	@Override
	public void setGroupId(long groupId) {
		_blogsEntry.setGroupId(groupId);
	}

	/**
	* Sets the modified date of this blogs entry.
	*
	* @param modifiedDate the modified date of this blogs entry
	*/
	@Override
	public void setModifiedDate(java.util.Date modifiedDate) {
		_blogsEntry.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_blogsEntry.setNew(n);
	}

	/**
	* Sets the primary key of this blogs entry.
	*
	* @param primaryKey the primary key of this blogs entry
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_blogsEntry.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_blogsEntry.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets whether this blogs entry is small image.
	*
	* @param smallImage the small image of this blogs entry
	*/
	@Override
	public void setSmallImage(boolean smallImage) {
		_blogsEntry.setSmallImage(smallImage);
	}

	/**
	* Sets the small image file entry ID of this blogs entry.
	*
	* @param smallImageFileEntryId the small image file entry ID of this blogs entry
	*/
	@Override
	public void setSmallImageFileEntryId(long smallImageFileEntryId) {
		_blogsEntry.setSmallImageFileEntryId(smallImageFileEntryId);
	}

	/**
	* Sets the small image ID of this blogs entry.
	*
	* @param smallImageId the small image ID of this blogs entry
	*/
	@Override
	public void setSmallImageId(long smallImageId) {
		_blogsEntry.setSmallImageId(smallImageId);
	}

	@Override
	public void setSmallImageType(java.lang.String smallImageType) {
		_blogsEntry.setSmallImageType(smallImageType);
	}

	/**
	* Sets the small image u r l of this blogs entry.
	*
	* @param smallImageURL the small image u r l of this blogs entry
	*/
	@Override
	public void setSmallImageURL(java.lang.String smallImageURL) {
		_blogsEntry.setSmallImageURL(smallImageURL);
	}

	/**
	* Sets the status of this blogs entry.
	*
	* @param status the status of this blogs entry
	*/
	@Override
	public void setStatus(int status) {
		_blogsEntry.setStatus(status);
	}

	/**
	* Sets the status by user ID of this blogs entry.
	*
	* @param statusByUserId the status by user ID of this blogs entry
	*/
	@Override
	public void setStatusByUserId(long statusByUserId) {
		_blogsEntry.setStatusByUserId(statusByUserId);
	}

	/**
	* Sets the status by user name of this blogs entry.
	*
	* @param statusByUserName the status by user name of this blogs entry
	*/
	@Override
	public void setStatusByUserName(java.lang.String statusByUserName) {
		_blogsEntry.setStatusByUserName(statusByUserName);
	}

	/**
	* Sets the status by user uuid of this blogs entry.
	*
	* @param statusByUserUuid the status by user uuid of this blogs entry
	*/
	@Override
	public void setStatusByUserUuid(java.lang.String statusByUserUuid) {
		_blogsEntry.setStatusByUserUuid(statusByUserUuid);
	}

	/**
	* Sets the status date of this blogs entry.
	*
	* @param statusDate the status date of this blogs entry
	*/
	@Override
	public void setStatusDate(java.util.Date statusDate) {
		_blogsEntry.setStatusDate(statusDate);
	}

	/**
	* Sets the subtitle of this blogs entry.
	*
	* @param subtitle the subtitle of this blogs entry
	*/
	@Override
	public void setSubtitle(java.lang.String subtitle) {
		_blogsEntry.setSubtitle(subtitle);
	}

	/**
	* Sets the title of this blogs entry.
	*
	* @param title the title of this blogs entry
	*/
	@Override
	public void setTitle(java.lang.String title) {
		_blogsEntry.setTitle(title);
	}

	/**
	* Sets the trackbacks of this blogs entry.
	*
	* @param trackbacks the trackbacks of this blogs entry
	*/
	@Override
	public void setTrackbacks(java.lang.String trackbacks) {
		_blogsEntry.setTrackbacks(trackbacks);
	}

	/**
	* Sets the url title of this blogs entry.
	*
	* @param urlTitle the url title of this blogs entry
	*/
	@Override
	public void setUrlTitle(java.lang.String urlTitle) {
		_blogsEntry.setUrlTitle(urlTitle);
	}

	/**
	* Sets the user ID of this blogs entry.
	*
	* @param userId the user ID of this blogs entry
	*/
	@Override
	public void setUserId(long userId) {
		_blogsEntry.setUserId(userId);
	}

	/**
	* Sets the user name of this blogs entry.
	*
	* @param userName the user name of this blogs entry
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_blogsEntry.setUserName(userName);
	}

	/**
	* Sets the user uuid of this blogs entry.
	*
	* @param userUuid the user uuid of this blogs entry
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_blogsEntry.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this blogs entry.
	*
	* @param uuid the uuid of this blogs entry
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_blogsEntry.setUuid(uuid);
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portlet.blogs.model.BlogsEntry> toCacheModel() {
		return _blogsEntry.toCacheModel();
	}

	@Override
	public com.liferay.portlet.blogs.model.BlogsEntry toEscapedModel() {
		return new BlogsEntryWrapper(_blogsEntry.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _blogsEntry.toString();
	}

	@Override
	public com.liferay.portlet.blogs.model.BlogsEntry toUnescapedModel() {
		return new BlogsEntryWrapper(_blogsEntry.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _blogsEntry.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof BlogsEntryWrapper)) {
			return false;
		}

		BlogsEntryWrapper blogsEntryWrapper = (BlogsEntryWrapper)obj;

		if (Validator.equals(_blogsEntry, blogsEntryWrapper._blogsEntry)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _blogsEntry.getStagedModelType();
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	@Deprecated
	public BlogsEntry getWrappedBlogsEntry() {
		return _blogsEntry;
	}

	@Override
	public BlogsEntry getWrappedModel() {
		return _blogsEntry;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _blogsEntry.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _blogsEntry.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_blogsEntry.resetOriginalValues();
	}

	private final BlogsEntry _blogsEntry;
}