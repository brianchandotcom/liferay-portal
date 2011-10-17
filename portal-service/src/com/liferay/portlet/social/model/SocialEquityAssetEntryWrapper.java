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
 * This class is a wrapper for {@link SocialEquityAssetEntry}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SocialEquityAssetEntry
 * @generated
 */
public class SocialEquityAssetEntryWrapper implements SocialEquityAssetEntry,
	ModelWrapper<SocialEquityAssetEntry> {
	public SocialEquityAssetEntryWrapper(
		SocialEquityAssetEntry socialEquityAssetEntry) {
		_socialEquityAssetEntry = socialEquityAssetEntry;
	}

	public Class<?> getModelClass() {
		return SocialEquityAssetEntry.class;
	}

	public String getModelClassName() {
		return SocialEquityAssetEntry.class.getName();
	}

	/**
	* Returns the primary key of this social equity asset entry.
	*
	* @return the primary key of this social equity asset entry
	*/
	public long getPrimaryKey() {
		return _socialEquityAssetEntry.getPrimaryKey();
	}

	/**
	* Sets the primary key of this social equity asset entry.
	*
	* @param primaryKey the primary key of this social equity asset entry
	*/
	public void setPrimaryKey(long primaryKey) {
		_socialEquityAssetEntry.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the equity asset entry ID of this social equity asset entry.
	*
	* @return the equity asset entry ID of this social equity asset entry
	*/
	public long getEquityAssetEntryId() {
		return _socialEquityAssetEntry.getEquityAssetEntryId();
	}

	/**
	* Sets the equity asset entry ID of this social equity asset entry.
	*
	* @param equityAssetEntryId the equity asset entry ID of this social equity asset entry
	*/
	public void setEquityAssetEntryId(long equityAssetEntryId) {
		_socialEquityAssetEntry.setEquityAssetEntryId(equityAssetEntryId);
	}

	/**
	* Returns the group ID of this social equity asset entry.
	*
	* @return the group ID of this social equity asset entry
	*/
	public long getGroupId() {
		return _socialEquityAssetEntry.getGroupId();
	}

	/**
	* Sets the group ID of this social equity asset entry.
	*
	* @param groupId the group ID of this social equity asset entry
	*/
	public void setGroupId(long groupId) {
		_socialEquityAssetEntry.setGroupId(groupId);
	}

	/**
	* Returns the company ID of this social equity asset entry.
	*
	* @return the company ID of this social equity asset entry
	*/
	public long getCompanyId() {
		return _socialEquityAssetEntry.getCompanyId();
	}

	/**
	* Sets the company ID of this social equity asset entry.
	*
	* @param companyId the company ID of this social equity asset entry
	*/
	public void setCompanyId(long companyId) {
		_socialEquityAssetEntry.setCompanyId(companyId);
	}

	/**
	* Returns the user ID of this social equity asset entry.
	*
	* @return the user ID of this social equity asset entry
	*/
	public long getUserId() {
		return _socialEquityAssetEntry.getUserId();
	}

	/**
	* Sets the user ID of this social equity asset entry.
	*
	* @param userId the user ID of this social equity asset entry
	*/
	public void setUserId(long userId) {
		_socialEquityAssetEntry.setUserId(userId);
	}

	/**
	* Returns the user uuid of this social equity asset entry.
	*
	* @return the user uuid of this social equity asset entry
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityAssetEntry.getUserUuid();
	}

	/**
	* Sets the user uuid of this social equity asset entry.
	*
	* @param userUuid the user uuid of this social equity asset entry
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_socialEquityAssetEntry.setUserUuid(userUuid);
	}

	/**
	* Returns the asset entry ID of this social equity asset entry.
	*
	* @return the asset entry ID of this social equity asset entry
	*/
	public long getAssetEntryId() {
		return _socialEquityAssetEntry.getAssetEntryId();
	}

	/**
	* Sets the asset entry ID of this social equity asset entry.
	*
	* @param assetEntryId the asset entry ID of this social equity asset entry
	*/
	public void setAssetEntryId(long assetEntryId) {
		_socialEquityAssetEntry.setAssetEntryId(assetEntryId);
	}

	/**
	* Returns the information k of this social equity asset entry.
	*
	* @return the information k of this social equity asset entry
	*/
	public double getInformationK() {
		return _socialEquityAssetEntry.getInformationK();
	}

	/**
	* Sets the information k of this social equity asset entry.
	*
	* @param informationK the information k of this social equity asset entry
	*/
	public void setInformationK(double informationK) {
		_socialEquityAssetEntry.setInformationK(informationK);
	}

	/**
	* Returns the information b of this social equity asset entry.
	*
	* @return the information b of this social equity asset entry
	*/
	public double getInformationB() {
		return _socialEquityAssetEntry.getInformationB();
	}

	/**
	* Sets the information b of this social equity asset entry.
	*
	* @param informationB the information b of this social equity asset entry
	*/
	public void setInformationB(double informationB) {
		_socialEquityAssetEntry.setInformationB(informationB);
	}

	public boolean isNew() {
		return _socialEquityAssetEntry.isNew();
	}

	public void setNew(boolean n) {
		_socialEquityAssetEntry.setNew(n);
	}

	public boolean isCachedModel() {
		return _socialEquityAssetEntry.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_socialEquityAssetEntry.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _socialEquityAssetEntry.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _socialEquityAssetEntry.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_socialEquityAssetEntry.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _socialEquityAssetEntry.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_socialEquityAssetEntry.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new SocialEquityAssetEntryWrapper((SocialEquityAssetEntry)_socialEquityAssetEntry.clone());
	}

	public int compareTo(
		com.liferay.portlet.social.model.SocialEquityAssetEntry socialEquityAssetEntry) {
		return _socialEquityAssetEntry.compareTo(socialEquityAssetEntry);
	}

	@Override
	public int hashCode() {
		return _socialEquityAssetEntry.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.portlet.social.model.SocialEquityAssetEntry> toCacheModel() {
		return _socialEquityAssetEntry.toCacheModel();
	}

	public com.liferay.portlet.social.model.SocialEquityAssetEntry toEscapedModel() {
		return new SocialEquityAssetEntryWrapper(_socialEquityAssetEntry.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _socialEquityAssetEntry.toString();
	}

	public java.lang.String toXmlString() {
		return _socialEquityAssetEntry.toXmlString();
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public SocialEquityAssetEntry getWrappedSocialEquityAssetEntry() {
		return _socialEquityAssetEntry;
	}

	public SocialEquityAssetEntry getWrappedModel() {
		return _socialEquityAssetEntry;
	}

	public void resetOriginalValues() {
		_socialEquityAssetEntry.resetOriginalValues();
	}

	private SocialEquityAssetEntry _socialEquityAssetEntry;
}