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

package com.liferay.segments.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link SegmentsEntryRel}.
 * </p>
 *
 * @author Eduardo Garcia
 * @see SegmentsEntryRel
 * @generated
 */
public class SegmentsEntryRelWrapper
	extends BaseModelWrapper<SegmentsEntryRel>
	implements ModelWrapper<SegmentsEntryRel>, SegmentsEntryRel {

	public SegmentsEntryRelWrapper(SegmentsEntryRel segmentsEntryRel) {
		super(segmentsEntryRel);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("segmentsEntryRelId", getSegmentsEntryRelId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("segmentsEntryId", getSegmentsEntryId());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long segmentsEntryRelId = (Long)attributes.get("segmentsEntryRelId");

		if (segmentsEntryRelId != null) {
			setSegmentsEntryRelId(segmentsEntryRelId);
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

		Long segmentsEntryId = (Long)attributes.get("segmentsEntryId");

		if (segmentsEntryId != null) {
			setSegmentsEntryId(segmentsEntryId);
		}

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}
	}

	/**
	 * Returns the fully qualified class name of this segments entry rel.
	 *
	 * @return the fully qualified class name of this segments entry rel
	 */
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	 * Returns the class name ID of this segments entry rel.
	 *
	 * @return the class name ID of this segments entry rel
	 */
	@Override
	public long getClassNameId() {
		return model.getClassNameId();
	}

	/**
	 * Returns the class pk of this segments entry rel.
	 *
	 * @return the class pk of this segments entry rel
	 */
	@Override
	public long getClassPK() {
		return model.getClassPK();
	}

	/**
	 * Returns the company ID of this segments entry rel.
	 *
	 * @return the company ID of this segments entry rel
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this segments entry rel.
	 *
	 * @return the create date of this segments entry rel
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the group ID of this segments entry rel.
	 *
	 * @return the group ID of this segments entry rel
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the modified date of this segments entry rel.
	 *
	 * @return the modified date of this segments entry rel
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this segments entry rel.
	 *
	 * @return the mvcc version of this segments entry rel
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this segments entry rel.
	 *
	 * @return the primary key of this segments entry rel
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the segments entry ID of this segments entry rel.
	 *
	 * @return the segments entry ID of this segments entry rel
	 */
	@Override
	public long getSegmentsEntryId() {
		return model.getSegmentsEntryId();
	}

	/**
	 * Returns the segments entry rel ID of this segments entry rel.
	 *
	 * @return the segments entry rel ID of this segments entry rel
	 */
	@Override
	public long getSegmentsEntryRelId() {
		return model.getSegmentsEntryRelId();
	}

	/**
	 * Returns the user ID of this segments entry rel.
	 *
	 * @return the user ID of this segments entry rel
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this segments entry rel.
	 *
	 * @return the user name of this segments entry rel
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this segments entry rel.
	 *
	 * @return the user uuid of this segments entry rel
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a segments entry rel model instance should use the <code>SegmentsEntryRel</code> interface instead.
	 */
	@Override
	public void persist() {
		model.persist();
	}

	@Override
	public void setClassName(String className) {
		model.setClassName(className);
	}

	/**
	 * Sets the class name ID of this segments entry rel.
	 *
	 * @param classNameId the class name ID of this segments entry rel
	 */
	@Override
	public void setClassNameId(long classNameId) {
		model.setClassNameId(classNameId);
	}

	/**
	 * Sets the class pk of this segments entry rel.
	 *
	 * @param classPK the class pk of this segments entry rel
	 */
	@Override
	public void setClassPK(long classPK) {
		model.setClassPK(classPK);
	}

	/**
	 * Sets the company ID of this segments entry rel.
	 *
	 * @param companyId the company ID of this segments entry rel
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this segments entry rel.
	 *
	 * @param createDate the create date of this segments entry rel
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the group ID of this segments entry rel.
	 *
	 * @param groupId the group ID of this segments entry rel
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the modified date of this segments entry rel.
	 *
	 * @param modifiedDate the modified date of this segments entry rel
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this segments entry rel.
	 *
	 * @param mvccVersion the mvcc version of this segments entry rel
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this segments entry rel.
	 *
	 * @param primaryKey the primary key of this segments entry rel
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the segments entry ID of this segments entry rel.
	 *
	 * @param segmentsEntryId the segments entry ID of this segments entry rel
	 */
	@Override
	public void setSegmentsEntryId(long segmentsEntryId) {
		model.setSegmentsEntryId(segmentsEntryId);
	}

	/**
	 * Sets the segments entry rel ID of this segments entry rel.
	 *
	 * @param segmentsEntryRelId the segments entry rel ID of this segments entry rel
	 */
	@Override
	public void setSegmentsEntryRelId(long segmentsEntryRelId) {
		model.setSegmentsEntryRelId(segmentsEntryRelId);
	}

	/**
	 * Sets the user ID of this segments entry rel.
	 *
	 * @param userId the user ID of this segments entry rel
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this segments entry rel.
	 *
	 * @param userName the user name of this segments entry rel
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this segments entry rel.
	 *
	 * @param userUuid the user uuid of this segments entry rel
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected SegmentsEntryRelWrapper wrap(SegmentsEntryRel segmentsEntryRel) {
		return new SegmentsEntryRelWrapper(segmentsEntryRel);
	}

}