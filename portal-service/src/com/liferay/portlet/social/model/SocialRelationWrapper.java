/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.social.model;

import com.liferay.portal.model.ModelWrapper;

/**
 * <p>
 * This class is a wrapper for {@link SocialRelation}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SocialRelation
 * @generated
 */
public class SocialRelationWrapper implements SocialRelation,
	ModelWrapper<SocialRelation> {
	public SocialRelationWrapper(SocialRelation socialRelation) {
		_socialRelation = socialRelation;
	}

	public Class<?> getModelClass() {
		return SocialRelation.class;
	}

	public String getModelClassName() {
		return SocialRelation.class.getName();
	}

	/**
	* Returns the primary key of this social relation.
	*
	* @return the primary key of this social relation
	*/
	public long getPrimaryKey() {
		return _socialRelation.getPrimaryKey();
	}

	/**
	* Sets the primary key of this social relation.
	*
	* @param primaryKey the primary key of this social relation
	*/
	public void setPrimaryKey(long primaryKey) {
		_socialRelation.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the uuid of this social relation.
	*
	* @return the uuid of this social relation
	*/
	public java.lang.String getUuid() {
		return _socialRelation.getUuid();
	}

	/**
	* Sets the uuid of this social relation.
	*
	* @param uuid the uuid of this social relation
	*/
	public void setUuid(java.lang.String uuid) {
		_socialRelation.setUuid(uuid);
	}

	/**
	* Returns the relation ID of this social relation.
	*
	* @return the relation ID of this social relation
	*/
	public long getRelationId() {
		return _socialRelation.getRelationId();
	}

	/**
	* Sets the relation ID of this social relation.
	*
	* @param relationId the relation ID of this social relation
	*/
	public void setRelationId(long relationId) {
		_socialRelation.setRelationId(relationId);
	}

	/**
	* Returns the company ID of this social relation.
	*
	* @return the company ID of this social relation
	*/
	public long getCompanyId() {
		return _socialRelation.getCompanyId();
	}

	/**
	* Sets the company ID of this social relation.
	*
	* @param companyId the company ID of this social relation
	*/
	public void setCompanyId(long companyId) {
		_socialRelation.setCompanyId(companyId);
	}

	/**
	* Returns the create date of this social relation.
	*
	* @return the create date of this social relation
	*/
	public long getCreateDate() {
		return _socialRelation.getCreateDate();
	}

	/**
	* Sets the create date of this social relation.
	*
	* @param createDate the create date of this social relation
	*/
	public void setCreateDate(long createDate) {
		_socialRelation.setCreateDate(createDate);
	}

	/**
	* Returns the user id1 of this social relation.
	*
	* @return the user id1 of this social relation
	*/
	public long getUserId1() {
		return _socialRelation.getUserId1();
	}

	/**
	* Sets the user id1 of this social relation.
	*
	* @param userId1 the user id1 of this social relation
	*/
	public void setUserId1(long userId1) {
		_socialRelation.setUserId1(userId1);
	}

	/**
	* Returns the user id2 of this social relation.
	*
	* @return the user id2 of this social relation
	*/
	public long getUserId2() {
		return _socialRelation.getUserId2();
	}

	/**
	* Sets the user id2 of this social relation.
	*
	* @param userId2 the user id2 of this social relation
	*/
	public void setUserId2(long userId2) {
		_socialRelation.setUserId2(userId2);
	}

	/**
	* Returns the type of this social relation.
	*
	* @return the type of this social relation
	*/
	public int getType() {
		return _socialRelation.getType();
	}

	/**
	* Sets the type of this social relation.
	*
	* @param type the type of this social relation
	*/
	public void setType(int type) {
		_socialRelation.setType(type);
	}

	public boolean isNew() {
		return _socialRelation.isNew();
	}

	public void setNew(boolean n) {
		_socialRelation.setNew(n);
	}

	public boolean isCachedModel() {
		return _socialRelation.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_socialRelation.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _socialRelation.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _socialRelation.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_socialRelation.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _socialRelation.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_socialRelation.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new SocialRelationWrapper((SocialRelation)_socialRelation.clone());
	}

	public int compareTo(
		com.liferay.portlet.social.model.SocialRelation socialRelation) {
		return _socialRelation.compareTo(socialRelation);
	}

	@Override
	public int hashCode() {
		return _socialRelation.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.portlet.social.model.SocialRelation> toCacheModel() {
		return _socialRelation.toCacheModel();
	}

	public com.liferay.portlet.social.model.SocialRelation toEscapedModel() {
		return new SocialRelationWrapper(_socialRelation.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _socialRelation.toString();
	}

	public java.lang.String toXmlString() {
		return _socialRelation.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_socialRelation.persist();
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public SocialRelation getWrappedSocialRelation() {
		return _socialRelation;
	}

	public SocialRelation getWrappedModel() {
		return _socialRelation;
	}

	public void resetOriginalValues() {
		_socialRelation.resetOriginalValues();
	}

	private SocialRelation _socialRelation;
}