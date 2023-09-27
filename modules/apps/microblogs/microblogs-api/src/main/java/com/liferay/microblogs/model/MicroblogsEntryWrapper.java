/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.microblogs.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link MicroblogsEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see MicroblogsEntry
 * @generated
 */
public class MicroblogsEntryWrapper
	extends BaseModelWrapper<MicroblogsEntry>
	implements MicroblogsEntry, ModelWrapper<MicroblogsEntry> {

	public MicroblogsEntryWrapper(MicroblogsEntry microblogsEntry) {
		super(microblogsEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("microblogsEntryId", getMicroblogsEntryId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("creatorClassNameId", getCreatorClassNameId());
		attributes.put("creatorClassPK", getCreatorClassPK());
		attributes.put("content", getContent());
		attributes.put("type", getType());
		attributes.put("parentMicroblogsEntryId", getParentMicroblogsEntryId());
		attributes.put("socialRelationType", getSocialRelationType());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long microblogsEntryId = (Long)attributes.get("microblogsEntryId");

		if (microblogsEntryId != null) {
			setMicroblogsEntryId(microblogsEntryId);
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

		Long creatorClassNameId = (Long)attributes.get("creatorClassNameId");

		if (creatorClassNameId != null) {
			setCreatorClassNameId(creatorClassNameId);
		}

		Long creatorClassPK = (Long)attributes.get("creatorClassPK");

		if (creatorClassPK != null) {
			setCreatorClassPK(creatorClassPK);
		}

		String content = (String)attributes.get("content");

		if (content != null) {
			setContent(content);
		}

		Integer type = (Integer)attributes.get("type");

		if (type != null) {
			setType(type);
		}

		Long parentMicroblogsEntryId = (Long)attributes.get(
			"parentMicroblogsEntryId");

		if (parentMicroblogsEntryId != null) {
			setParentMicroblogsEntryId(parentMicroblogsEntryId);
		}

		Integer socialRelationType = (Integer)attributes.get(
			"socialRelationType");

		if (socialRelationType != null) {
			setSocialRelationType(socialRelationType);
		}
	}

	@Override
	public MicroblogsEntry cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	@Override
	public long fetchParentMicroblogsEntryUserId() {
		return model.fetchParentMicroblogsEntryUserId();
	}

	/**
	 * Returns the company ID of this microblogs entry.
	 *
	 * @return the company ID of this microblogs entry
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the content of this microblogs entry.
	 *
	 * @return the content of this microblogs entry
	 */
	@Override
	public String getContent() {
		return model.getContent();
	}

	/**
	 * Returns the create date of this microblogs entry.
	 *
	 * @return the create date of this microblogs entry
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the creator class name ID of this microblogs entry.
	 *
	 * @return the creator class name ID of this microblogs entry
	 */
	@Override
	public long getCreatorClassNameId() {
		return model.getCreatorClassNameId();
	}

	/**
	 * Returns the creator class pk of this microblogs entry.
	 *
	 * @return the creator class pk of this microblogs entry
	 */
	@Override
	public long getCreatorClassPK() {
		return model.getCreatorClassPK();
	}

	/**
	 * Returns the microblogs entry ID of this microblogs entry.
	 *
	 * @return the microblogs entry ID of this microblogs entry
	 */
	@Override
	public long getMicroblogsEntryId() {
		return model.getMicroblogsEntryId();
	}

	/**
	 * Returns the modified date of this microblogs entry.
	 *
	 * @return the modified date of this microblogs entry
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the parent microblogs entry ID of this microblogs entry.
	 *
	 * @return the parent microblogs entry ID of this microblogs entry
	 */
	@Override
	public long getParentMicroblogsEntryId() {
		return model.getParentMicroblogsEntryId();
	}

	@Override
	public long getParentMicroblogsEntryUserId()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getParentMicroblogsEntryUserId();
	}

	/**
	 * Returns the primary key of this microblogs entry.
	 *
	 * @return the primary key of this microblogs entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the social relation type of this microblogs entry.
	 *
	 * @return the social relation type of this microblogs entry
	 */
	@Override
	public int getSocialRelationType() {
		return model.getSocialRelationType();
	}

	/**
	 * Returns the type of this microblogs entry.
	 *
	 * @return the type of this microblogs entry
	 */
	@Override
	public int getType() {
		return model.getType();
	}

	/**
	 * Returns the user ID of this microblogs entry.
	 *
	 * @return the user ID of this microblogs entry
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this microblogs entry.
	 *
	 * @return the user name of this microblogs entry
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this microblogs entry.
	 *
	 * @return the user uuid of this microblogs entry
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
	 * Sets the company ID of this microblogs entry.
	 *
	 * @param companyId the company ID of this microblogs entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the content of this microblogs entry.
	 *
	 * @param content the content of this microblogs entry
	 */
	@Override
	public void setContent(String content) {
		model.setContent(content);
	}

	/**
	 * Sets the create date of this microblogs entry.
	 *
	 * @param createDate the create date of this microblogs entry
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the creator class name ID of this microblogs entry.
	 *
	 * @param creatorClassNameId the creator class name ID of this microblogs entry
	 */
	@Override
	public void setCreatorClassNameId(long creatorClassNameId) {
		model.setCreatorClassNameId(creatorClassNameId);
	}

	/**
	 * Sets the creator class pk of this microblogs entry.
	 *
	 * @param creatorClassPK the creator class pk of this microblogs entry
	 */
	@Override
	public void setCreatorClassPK(long creatorClassPK) {
		model.setCreatorClassPK(creatorClassPK);
	}

	/**
	 * Sets the microblogs entry ID of this microblogs entry.
	 *
	 * @param microblogsEntryId the microblogs entry ID of this microblogs entry
	 */
	@Override
	public void setMicroblogsEntryId(long microblogsEntryId) {
		model.setMicroblogsEntryId(microblogsEntryId);
	}

	/**
	 * Sets the modified date of this microblogs entry.
	 *
	 * @param modifiedDate the modified date of this microblogs entry
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the parent microblogs entry ID of this microblogs entry.
	 *
	 * @param parentMicroblogsEntryId the parent microblogs entry ID of this microblogs entry
	 */
	@Override
	public void setParentMicroblogsEntryId(long parentMicroblogsEntryId) {
		model.setParentMicroblogsEntryId(parentMicroblogsEntryId);
	}

	/**
	 * Sets the primary key of this microblogs entry.
	 *
	 * @param primaryKey the primary key of this microblogs entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the social relation type of this microblogs entry.
	 *
	 * @param socialRelationType the social relation type of this microblogs entry
	 */
	@Override
	public void setSocialRelationType(int socialRelationType) {
		model.setSocialRelationType(socialRelationType);
	}

	/**
	 * Sets the type of this microblogs entry.
	 *
	 * @param type the type of this microblogs entry
	 */
	@Override
	public void setType(int type) {
		model.setType(type);
	}

	/**
	 * Sets the user ID of this microblogs entry.
	 *
	 * @param userId the user ID of this microblogs entry
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this microblogs entry.
	 *
	 * @param userName the user name of this microblogs entry
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this microblogs entry.
	 *
	 * @param userUuid the user uuid of this microblogs entry
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	protected MicroblogsEntryWrapper wrap(MicroblogsEntry microblogsEntry) {
		return new MicroblogsEntryWrapper(microblogsEntry);
	}

}