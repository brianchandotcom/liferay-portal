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

/**
 * <p>
 * This class is a wrapper for {@link SocialActivityStatsEntry}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SocialActivityStatsEntry
 * @generated
 */
public class SocialActivityStatsEntryWrapper implements SocialActivityStatsEntry {
	public SocialActivityStatsEntryWrapper(
		SocialActivityStatsEntry socialActivityStatsEntry) {
		_socialActivityStatsEntry = socialActivityStatsEntry;
	}

	public Class<?> getModelClass() {
		return SocialActivityStatsEntry.class;
	}

	public String getModelClassName() {
		return SocialActivityStatsEntry.class.getName();
	}

	/**
	* Returns the primary key of this social activity stats entry.
	*
	* @return the primary key of this social activity stats entry
	*/
	public long getPrimaryKey() {
		return _socialActivityStatsEntry.getPrimaryKey();
	}

	/**
	* Sets the primary key of this social activity stats entry.
	*
	* @param primaryKey the primary key of this social activity stats entry
	*/
	public void setPrimaryKey(long primaryKey) {
		_socialActivityStatsEntry.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the activity stats entry ID of this social activity stats entry.
	*
	* @return the activity stats entry ID of this social activity stats entry
	*/
	public long getActivityStatsEntryId() {
		return _socialActivityStatsEntry.getActivityStatsEntryId();
	}

	/**
	* Sets the activity stats entry ID of this social activity stats entry.
	*
	* @param activityStatsEntryId the activity stats entry ID of this social activity stats entry
	*/
	public void setActivityStatsEntryId(long activityStatsEntryId) {
		_socialActivityStatsEntry.setActivityStatsEntryId(activityStatsEntryId);
	}

	/**
	* Returns the group ID of this social activity stats entry.
	*
	* @return the group ID of this social activity stats entry
	*/
	public long getGroupId() {
		return _socialActivityStatsEntry.getGroupId();
	}

	/**
	* Sets the group ID of this social activity stats entry.
	*
	* @param groupId the group ID of this social activity stats entry
	*/
	public void setGroupId(long groupId) {
		_socialActivityStatsEntry.setGroupId(groupId);
	}

	/**
	* Returns the company ID of this social activity stats entry.
	*
	* @return the company ID of this social activity stats entry
	*/
	public long getCompanyId() {
		return _socialActivityStatsEntry.getCompanyId();
	}

	/**
	* Sets the company ID of this social activity stats entry.
	*
	* @param companyId the company ID of this social activity stats entry
	*/
	public void setCompanyId(long companyId) {
		_socialActivityStatsEntry.setCompanyId(companyId);
	}

	/**
	* Returns the fully qualified class name of this social activity stats entry.
	*
	* @return the fully qualified class name of this social activity stats entry
	*/
	public java.lang.String getClassName() {
		return _socialActivityStatsEntry.getClassName();
	}

	/**
	* Returns the class name ID of this social activity stats entry.
	*
	* @return the class name ID of this social activity stats entry
	*/
	public long getClassNameId() {
		return _socialActivityStatsEntry.getClassNameId();
	}

	/**
	* Sets the class name ID of this social activity stats entry.
	*
	* @param classNameId the class name ID of this social activity stats entry
	*/
	public void setClassNameId(long classNameId) {
		_socialActivityStatsEntry.setClassNameId(classNameId);
	}

	/**
	* Returns the class p k of this social activity stats entry.
	*
	* @return the class p k of this social activity stats entry
	*/
	public long getClassPK() {
		return _socialActivityStatsEntry.getClassPK();
	}

	/**
	* Sets the class p k of this social activity stats entry.
	*
	* @param classPK the class p k of this social activity stats entry
	*/
	public void setClassPK(long classPK) {
		_socialActivityStatsEntry.setClassPK(classPK);
	}

	/**
	* Returns the class type of this social activity stats entry.
	*
	* @return the class type of this social activity stats entry
	*/
	public int getClassType() {
		return _socialActivityStatsEntry.getClassType();
	}

	/**
	* Sets the class type of this social activity stats entry.
	*
	* @param classType the class type of this social activity stats entry
	*/
	public void setClassType(int classType) {
		_socialActivityStatsEntry.setClassType(classType);
	}

	/**
	* Returns the stat name of this social activity stats entry.
	*
	* @return the stat name of this social activity stats entry
	*/
	public java.lang.String getStatName() {
		return _socialActivityStatsEntry.getStatName();
	}

	/**
	* Sets the stat name of this social activity stats entry.
	*
	* @param statName the stat name of this social activity stats entry
	*/
	public void setStatName(java.lang.String statName) {
		_socialActivityStatsEntry.setStatName(statName);
	}

	/**
	* Returns the current value of this social activity stats entry.
	*
	* @return the current value of this social activity stats entry
	*/
	public int getCurrentValue() {
		return _socialActivityStatsEntry.getCurrentValue();
	}

	/**
	* Sets the current value of this social activity stats entry.
	*
	* @param currentValue the current value of this social activity stats entry
	*/
	public void setCurrentValue(int currentValue) {
		_socialActivityStatsEntry.setCurrentValue(currentValue);
	}

	/**
	* Returns the overall value of this social activity stats entry.
	*
	* @return the overall value of this social activity stats entry
	*/
	public int getOverallValue() {
		return _socialActivityStatsEntry.getOverallValue();
	}

	/**
	* Sets the overall value of this social activity stats entry.
	*
	* @param overallValue the overall value of this social activity stats entry
	*/
	public void setOverallValue(int overallValue) {
		_socialActivityStatsEntry.setOverallValue(overallValue);
	}

	/**
	* Returns the grace value of this social activity stats entry.
	*
	* @return the grace value of this social activity stats entry
	*/
	public int getGraceValue() {
		return _socialActivityStatsEntry.getGraceValue();
	}

	/**
	* Sets the grace value of this social activity stats entry.
	*
	* @param graceValue the grace value of this social activity stats entry
	*/
	public void setGraceValue(int graceValue) {
		_socialActivityStatsEntry.setGraceValue(graceValue);
	}

	/**
	* Returns the stat period start of this social activity stats entry.
	*
	* @return the stat period start of this social activity stats entry
	*/
	public int getStatPeriodStart() {
		return _socialActivityStatsEntry.getStatPeriodStart();
	}

	/**
	* Sets the stat period start of this social activity stats entry.
	*
	* @param statPeriodStart the stat period start of this social activity stats entry
	*/
	public void setStatPeriodStart(int statPeriodStart) {
		_socialActivityStatsEntry.setStatPeriodStart(statPeriodStart);
	}

	/**
	* Returns the stat period end of this social activity stats entry.
	*
	* @return the stat period end of this social activity stats entry
	*/
	public int getStatPeriodEnd() {
		return _socialActivityStatsEntry.getStatPeriodEnd();
	}

	/**
	* Sets the stat period end of this social activity stats entry.
	*
	* @param statPeriodEnd the stat period end of this social activity stats entry
	*/
	public void setStatPeriodEnd(int statPeriodEnd) {
		_socialActivityStatsEntry.setStatPeriodEnd(statPeriodEnd);
	}

	public boolean isNew() {
		return _socialActivityStatsEntry.isNew();
	}

	public void setNew(boolean n) {
		_socialActivityStatsEntry.setNew(n);
	}

	public boolean isCachedModel() {
		return _socialActivityStatsEntry.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_socialActivityStatsEntry.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _socialActivityStatsEntry.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_socialActivityStatsEntry.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _socialActivityStatsEntry.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_socialActivityStatsEntry.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _socialActivityStatsEntry.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_socialActivityStatsEntry.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new SocialActivityStatsEntryWrapper((SocialActivityStatsEntry)_socialActivityStatsEntry.clone());
	}

	public int compareTo(
		com.liferay.portlet.social.model.SocialActivityStatsEntry socialActivityStatsEntry) {
		return _socialActivityStatsEntry.compareTo(socialActivityStatsEntry);
	}

	@Override
	public int hashCode() {
		return _socialActivityStatsEntry.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.portlet.social.model.SocialActivityStatsEntry> toCacheModel() {
		return _socialActivityStatsEntry.toCacheModel();
	}

	public com.liferay.portlet.social.model.SocialActivityStatsEntry toEscapedModel() {
		return new SocialActivityStatsEntryWrapper(_socialActivityStatsEntry.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _socialActivityStatsEntry.toString();
	}

	public java.lang.String toXmlString() {
		return _socialActivityStatsEntry.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_socialActivityStatsEntry.persist();
	}

	public boolean isPeriodActive() {
		return _socialActivityStatsEntry.isPeriodActive();
	}

	public SocialActivityStatsEntry getWrappedSocialActivityStatsEntry() {
		return _socialActivityStatsEntry;
	}

	public void resetOriginalValues() {
		_socialActivityStatsEntry.resetOriginalValues();
	}

	private SocialActivityStatsEntry _socialActivityStatsEntry;
}