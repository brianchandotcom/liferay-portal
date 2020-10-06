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

package com.liferay.dispatch.service.test;

import com.liferay.dispatch.model.DispatchTrigger;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.io.Serializable;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * Dispatch trigger values holder
 *
 * @author Igor Beslic
 */
public class RandomDispatchUtil {

	/**
	 * Returns DispatchTrigger instance with <code>active</code> and
	 * <code>cronExpression</code> updated with new random values.
	 *
	 * If <code>nameSalt</code> less then <code>0</code> {@link
	 * DispatchTrigger#getName()}
	 * method
	 * returns <code>null</code>.
	 *
	 * @param  dispatchTrigger the original dispatch trigger
	 * @param  nameSalt the dispatch trigger name suffix
	 * @return updated random RandomDispatchUtil
	 */
	public static DispatchTrigger randomDispatchTrigger(
		DispatchTrigger dispatchTrigger, int nameSalt) {

		Objects.requireNonNull(dispatchTrigger);

		return _randomDispatchTrigger(
			RandomTestUtil.randomBoolean(), dispatchTrigger.getCompanyId(),
			_randomCronExpression(), dispatchTrigger.getTaskType(),
			dispatchTrigger.getTaskSettingsUnicodeProperties(),
			_randomName(dispatchTrigger.getUserId(), nameSalt),
			dispatchTrigger.isSystem(), dispatchTrigger.getUserId());
	}

	/**
	 * Returns DispatchTrigger object initialized with random values.
	 *
	 * If <code>nameSalt</code> less then <code>0</code> {@link
	 * DispatchTrigger#getName()}
	 * method
	 * returns <code>null</code>.
	 *
	 * @param  user the owner user of dispatch trigger
	 * @param  nameSalt the dispatch trigger name suffix
	 * @return random dispatch trigger
	 */
	public static DispatchTrigger randomDispatchTrigger(
		User user, int nameSalt) {

		Objects.requireNonNull(user);

		return _randomDispatchTrigger(
			RandomTestUtil.randomBoolean(), user.getCompanyId(),
			_randomCronExpression(), RandomTestUtil.randomString(20),
			RandomTestUtil.randomUnicodeProperties(
				RandomTestUtil.randomInt(10, 30), 32, 64),
			_randomName(user.getUserId(), nameSalt),
			RandomTestUtil.randomBoolean(), user.getUserId());
	}

	private static String _randomCronExpression() {
		return String.format(
			"0 0 0 ? %d/2 * 2077", RandomTestUtil.randomInt(1, 12));
	}

	private static DispatchTrigger _randomDispatchTrigger(
		boolean active, long companyId, String cronExpression, String taskType,
		UnicodeProperties unicodeProperties, String name, boolean system,
		long userId) {

		return new DispatchTrigger() {

			@Override
			public Object clone() {
				throw new UnsupportedOperationException();
			}

			@Override
			public int compareTo(DispatchTrigger dispatchTrigger) {
				throw new UnsupportedOperationException();
			}

			@Override
			public boolean getActive() {
				return _active;
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
			public String getCronExpression() {
				return _cronExpression;
			}

			@Override
			public long getDispatchTriggerId() {
				throw new UnsupportedOperationException();
			}

			@Override
			public Date getEndDate() {
				throw new UnsupportedOperationException();
			}

			@Override
			public ExpandoBridge getExpandoBridge() {
				throw new UnsupportedOperationException();
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
				return 0;
			}

			@Override
			public String getName() {
				return _name;
			}

			@Override
			public long getPrimaryKey() {
				return 0;
			}

			@Override
			public Serializable getPrimaryKeyObj() {
				throw new UnsupportedOperationException();
			}

			@Override
			public Date getStartDate() {
				throw new UnsupportedOperationException();
			}

			@Override
			public boolean getSystem() {
				return _system;
			}

			@Override
			public String getTaskSettings() {
				throw new UnsupportedOperationException();
			}

			@Override
			public UnicodeProperties getTaskSettingsUnicodeProperties() {
				return _taskSettingsUnicodeProperties;
			}

			@Override
			public String getTaskType() {
				return _taskType;
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
			public boolean isFinderCacheEnabled() {
				throw new UnsupportedOperationException();
			}

			@Override
			public boolean isNew() {
				throw new UnsupportedOperationException();
			}

			@Override
			public boolean isSystem() {
				return _system;
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
			public void setCronExpression(String cronExpression) {
				throw new UnsupportedOperationException();
			}

			@Override
			public void setDispatchTriggerId(long dispatchTriggerId) {
				throw new UnsupportedOperationException();
			}

			@Override
			public void setEndDate(Date endDate) {
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
			public void setStartDate(Date startDate) {
				throw new UnsupportedOperationException();
			}

			@Override
			public void setSystem(boolean system) {
				throw new UnsupportedOperationException();
			}

			@Override
			public void setTaskSettings(String taskSettings) {
				throw new UnsupportedOperationException();
			}

			@Override
			public void setTaskSettingsUnicodeProperties(
				UnicodeProperties taskSettingsUnicodeProperties) {

				throw new UnsupportedOperationException();
			}

			@Override
			public void setTaskType(String taskType) {
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
			public CacheModel<DispatchTrigger> toCacheModel() {
				throw new UnsupportedOperationException();
			}

			@Override
			public DispatchTrigger toEscapedModel() {
				throw new UnsupportedOperationException();
			}

			@Override
			public DispatchTrigger toUnescapedModel() {
				throw new UnsupportedOperationException();
			}

			@Override
			public String toXmlString() {
				throw new UnsupportedOperationException();
			}

			private final boolean _active = active;
			private final long _companyId = companyId;
			private final String _cronExpression = cronExpression;
			private final String _name = name;
			private final boolean _system = system;
			private final UnicodeProperties _taskSettingsUnicodeProperties =
				unicodeProperties;
			private final String _taskType = taskType;
			private final long _userId = userId;

		};
	}

	private static String _randomName(long userId, int nameSalt) {
		if (nameSalt < 0) {
			return null;
		}

		return String.format(_NAME_PATTERN, userId, nameSalt);
	}

	private static final String _NAME_PATTERN = "TEST-TRIGGER-%06d-%06d";

}