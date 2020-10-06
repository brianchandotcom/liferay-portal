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

package com.liferay.dispatch.service.test.util;

import com.liferay.dispatch.model.DispatchLog;
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
public class DispatchLogTestUtil {

	public static DispatchLog randomDispatchLog(User user, int status) {
		final Date startDate = RandomTestUtil.nextDate();

		return _randomDispatchLog(
			user.getCompanyId(),
			new Date(
				startDate.getTime() + RandomTestUtil.randomInt(60000, 120000)),
			RandomTestUtil.randomString(2000),
			RandomTestUtil.randomString(3000), startDate, status,
			user.getUserId());
	}

	private static DispatchLog _randomDispatchLog(
		long companyId, Date endDate, String error, String output,
		Date startDate, int status, long userId) {

		return new DispatchLog() {

			@Override
			public Object clone() {
				throw new UnsupportedOperationException();
			}

			@Override
			public int compareTo(DispatchLog o) {
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
			public long getDispatchLogId() {
				throw new UnsupportedOperationException();
			}

			@Override
			public long getDispatchTriggerId() {
				throw new UnsupportedOperationException();
			}

			@Override
			public Date getEndDate() {
				return _endDate;
			}

			@Override
			public String getError() {
				return _error;
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
				throw new UnsupportedOperationException();
			}

			@Override
			public String getOutput() {
				return _output;
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
			public Date getStartDate() {
				return _startDate;
			}

			@Override
			public int getStatus() {
				return _status;
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
			public void persist() {
				throw new UnsupportedOperationException();
			}

			@Override
			public void resetOriginalValues() {
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
			public void setDispatchLogId(long dispatchLogId) {
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
			public void setError(String error) {
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
			public void setNew(boolean n) {
				throw new UnsupportedOperationException();
			}

			@Override
			public void setOutput(String output) {
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
			public void setStatus(int status) {
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
			public CacheModel<DispatchLog> toCacheModel() {
				throw new UnsupportedOperationException();
			}

			@Override
			public DispatchLog toEscapedModel() {
				throw new UnsupportedOperationException();
			}

			@Override
			public DispatchLog toUnescapedModel() {
				throw new UnsupportedOperationException();
			}

			@Override
			public String toXmlString() {
				throw new UnsupportedOperationException();
			}

			private final long _companyId = companyId;
			private final Date _endDate = endDate;
			private final String _error = error;
			private final String _output = output;
			private final Date _startDate = startDate;
			private final int _status = status;
			private final long _userId = userId;

		};
	}

}