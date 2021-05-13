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

package com.liferay.batch.planner.service.test.util;

import com.liferay.batch.planner.model.BatchPlannerPlan;
import com.liferay.batch.planner.plan.PlanExternalType;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

import java.io.Serializable;

import java.util.Date;
import java.util.Map;

/**
 * @author Igor Beslic
 */
public class BatchPlannerPlanTestUtil {

	public static BatchPlannerPlan randomBatchPlannerPlan(
		User user, int nameSalt) {

		return randomBatchPlannerPlan(user, _randomName(nameSalt));
	}

	public static BatchPlannerPlan randomBatchPlannerPlan(
		User user, String name) {

		PlanExternalType planExternalType = _randomPlanExternalType();

		return _randomBatchPlannerPlan(
			RandomTestUtil.randomBoolean(), user.getCompanyId(),
			RandomTestUtil.randomBoolean(), planExternalType.name(),
			RandomTestUtil.randomString(20), RandomTestUtil.randomString(20),
			name, user.getUserId());
	}

	private static BatchPlannerPlan _randomBatchPlannerPlan(
		boolean active, long companyId, boolean export, String externalType,
		String externalURL, String internalClassName, String name,
		long userId) {

		return new BatchPlannerPlan() {

			@Override
			public Object clone() {
				throw new UnsupportedOperationException();
			}

			@Override
			public int compareTo(BatchPlannerPlan o) {
				throw new UnsupportedOperationException();
			}

			@Override
			public boolean getActive() {
				return _active;
			}

			@Override
			public long getBatchPlannerPlanId() {
				throw new UnsupportedOperationException();
			}

			@Override
			public long getCompanyId() {
				return _companyId;
			}

			@Override
			public Date getCreateDate() {
				throw new UnsupportedOperationException();
			}

			@Override
			public ExpandoBridge getExpandoBridge() {
				throw new UnsupportedOperationException();
			}

			@Override
			public boolean getExport() {
				return _export;
			}

			@Override
			public String getExternalType() {
				return _externalType;
			}

			@Override
			public String getExternalURL() {
				return _externalURL;
			}

			@Override
			public String getInternalClassName() {
				return _internalClassName;
			}

			@Override
			public Map<String, Object> getModelAttributes() {
				throw new UnsupportedOperationException();
			}

			@Override
			public Class<?> getModelClass() {
				throw new UnsupportedOperationException();
			}

			@Override
			public String getModelClassName() {
				throw new UnsupportedOperationException();
			}

			@Override
			public Date getModifiedDate() {
				throw new UnsupportedOperationException();
			}

			@Override
			public long getMvccVersion() {
				throw new UnsupportedOperationException();
			}

			@Override
			public String getName() {
				return _name;
			}

			@Override
			public long getPrimaryKey() {
				throw new UnsupportedOperationException();
			}

			@Override
			public Serializable getPrimaryKeyObj() {
				throw new UnsupportedOperationException();
			}

			@Override
			public long getUserId() {
				return _userId;
			}

			@Override
			public String getUserName() {
				throw new UnsupportedOperationException();
			}

			@Override
			public String getUserUuid() {
				throw new UnsupportedOperationException();
			}

			@Override
			public boolean isActive() {
				return _active;
			}

			@Override
			public boolean isCachedModel() {
				throw new UnsupportedOperationException();
			}

			@Override
			public boolean isEntityCacheEnabled() {
				throw new UnsupportedOperationException();
			}

			@Override
			public boolean isEscapedModel() {
				throw new UnsupportedOperationException();
			}

			@Override
			public boolean isExport() {
				return _export;
			}

			@Override
			public boolean isFinderCacheEnabled() {
				throw new UnsupportedOperationException();
			}

			@Override
			public boolean isNew() {
				throw new UnsupportedOperationException();
			}

			@Override
			public void persist() {
				throw new UnsupportedOperationException();
			}

			@Override
			public void resetOriginalValues() {
				throw new UnsupportedOperationException();
			}

			@Override
			public void setActive(boolean active) {
				throw new UnsupportedOperationException();
			}

			@Override
			public void setBatchPlannerPlanId(long batchPlannerPlanId) {
				throw new UnsupportedOperationException();
			}

			@Override
			public void setCachedModel(boolean cachedModel) {
				throw new UnsupportedOperationException();
			}

			@Override
			public void setCompanyId(long companyId) {
				throw new UnsupportedOperationException();
			}

			@Override
			public void setCreateDate(Date createDate) {
				throw new UnsupportedOperationException();
			}

			@Override
			public void setExpandoBridgeAttributes(BaseModel<?> baseModel) {
				throw new UnsupportedOperationException();
			}

			@Override
			public void setExpandoBridgeAttributes(
				ExpandoBridge expandoBridge) {

				throw new UnsupportedOperationException();
			}

			@Override
			public void setExpandoBridgeAttributes(
				ServiceContext serviceContext) {

				throw new UnsupportedOperationException();
			}

			@Override
			public void setExport(boolean export) {
				throw new UnsupportedOperationException();
			}

			@Override
			public void setExternalType(String externalType) {
				throw new UnsupportedOperationException();
			}

			@Override
			public void setExternalURL(String externalURL) {
				throw new UnsupportedOperationException();
			}

			@Override
			public void setInternalClassName(String internalClassName) {
				throw new UnsupportedOperationException();
			}

			@Override
			public void setModelAttributes(Map<String, Object> attributes) {
				throw new UnsupportedOperationException();
			}

			@Override
			public void setModifiedDate(Date modifiedDate) {
				throw new UnsupportedOperationException();
			}

			@Override
			public void setMvccVersion(long mvccVersion) {
				throw new UnsupportedOperationException();
			}

			@Override
			public void setName(String name) {
				throw new UnsupportedOperationException();
			}

			@Override
			public void setNew(boolean n) {
				throw new UnsupportedOperationException();
			}

			@Override
			public void setPrimaryKey(long primaryKey) {
				throw new UnsupportedOperationException();
			}

			@Override
			public void setPrimaryKeyObj(Serializable primaryKeyObj) {
				throw new UnsupportedOperationException();
			}

			@Override
			public void setUserId(long userId) {
				throw new UnsupportedOperationException();
			}

			@Override
			public void setUserName(String userName) {
				throw new UnsupportedOperationException();
			}

			@Override
			public void setUserUuid(String userUuid) {
				throw new UnsupportedOperationException();
			}

			@Override
			public CacheModel<BatchPlannerPlan> toCacheModel() {
				throw new UnsupportedOperationException();
			}

			@Override
			public BatchPlannerPlan toEscapedModel() {
				throw new UnsupportedOperationException();
			}

			@Override
			public BatchPlannerPlan toUnescapedModel() {
				throw new UnsupportedOperationException();
			}

			@Override
			public String toXmlString() {
				throw new UnsupportedOperationException();
			}

			private final boolean _active = active;
			private final long _companyId = companyId;
			private final boolean _export = export;
			private final String _externalType = externalType;
			private final String _externalURL = externalURL;
			private final String _internalClassName = internalClassName;
			private final String _name = name;
			private final long _userId = userId;

		};
	}

	private static String _randomName(int nameSalt) {
		if (nameSalt < 0) {
			return null;
		}

		return String.format(
			"TEST-PLAN-%06d-%s", nameSalt % 999999,
			RandomTestUtil.randomString(RandomTestUtil.randomInt(20, 57)));
	}

	private static PlanExternalType _randomPlanExternalType() {
		final PlanExternalType[] planExternalTypes = PlanExternalType.values();

		return planExternalTypes
			[RandomTestUtil.randomInt(0, planExternalTypes.length - 1)];
	}

}