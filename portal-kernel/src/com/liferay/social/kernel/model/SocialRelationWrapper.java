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

package com.liferay.social.kernel.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link SocialRelation}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SocialRelation
 * @generated
 */
public class SocialRelationWrapper
	extends BaseModelWrapper<SocialRelation>
	implements ModelWrapper<SocialRelation>, SocialRelation {

	public SocialRelationWrapper(SocialRelation socialRelation) {
		super(socialRelation);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("relationId", getRelationId());
		attributes.put("companyId", getCompanyId());
		attributes.put("createDate", getCreateDate());
		attributes.put("userId1", getUserId1());
		attributes.put("userId2", getUserId2());
		attributes.put("type", getType());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long relationId = (Long)attributes.get("relationId");

		if (relationId != null) {
			setRelationId(relationId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long createDate = (Long)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Long userId1 = (Long)attributes.get("userId1");

		if (userId1 != null) {
			setUserId1(userId1);
		}

		Long userId2 = (Long)attributes.get("userId2");

		if (userId2 != null) {
			setUserId2(userId2);
		}

		Integer type = (Integer)attributes.get("type");

		if (type != null) {
			setType(type);
		}
	}

	/**
	 * Returns the company ID of this social relation.
	 *
	 * @return the company ID of this social relation
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this social relation.
	 *
	 * @return the create date of this social relation
	 */
	@Override
	public long getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the primary key of this social relation.
	 *
	 * @return the primary key of this social relation
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the relation ID of this social relation.
	 *
	 * @return the relation ID of this social relation
	 */
	@Override
	public long getRelationId() {
		return model.getRelationId();
	}

	/**
	 * Returns the type of this social relation.
	 *
	 * @return the type of this social relation
	 */
	@Override
	public int getType() {
		return model.getType();
	}

	/**
	 * Returns the user id1 of this social relation.
	 *
	 * @return the user id1 of this social relation
	 */
	@Override
	public long getUserId1() {
		return model.getUserId1();
	}

	/**
	 * Returns the user id2 of this social relation.
	 *
	 * @return the user id2 of this social relation
	 */
	@Override
	public long getUserId2() {
		return model.getUserId2();
	}

	/**
	 * Returns the uuid of this social relation.
	 *
	 * @return the uuid of this social relation
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a social relation model instance should use the <code>SocialRelation</code> interface instead.
	 */
	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this social relation.
	 *
	 * @param companyId the company ID of this social relation
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this social relation.
	 *
	 * @param createDate the create date of this social relation
	 */
	@Override
	public void setCreateDate(long createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the primary key of this social relation.
	 *
	 * @param primaryKey the primary key of this social relation
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the relation ID of this social relation.
	 *
	 * @param relationId the relation ID of this social relation
	 */
	@Override
	public void setRelationId(long relationId) {
		model.setRelationId(relationId);
	}

	/**
	 * Sets the type of this social relation.
	 *
	 * @param type the type of this social relation
	 */
	@Override
	public void setType(int type) {
		model.setType(type);
	}

	/**
	 * Sets the user id1 of this social relation.
	 *
	 * @param userId1 the user id1 of this social relation
	 */
	@Override
	public void setUserId1(long userId1) {
		model.setUserId1(userId1);
	}

	/**
	 * Sets the user id2 of this social relation.
	 *
	 * @param userId2 the user id2 of this social relation
	 */
	@Override
	public void setUserId2(long userId2) {
		model.setUserId2(userId2);
	}

	/**
	 * Sets the uuid of this social relation.
	 *
	 * @param uuid the uuid of this social relation
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	@Override
	protected SocialRelationWrapper wrap(SocialRelation socialRelation) {
		return new SocialRelationWrapper(socialRelation);
	}

}