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

package com.liferay.subscription.internal.model.adapter;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.portal.kernel.bean.AutoEscape;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.Subscription;
import com.liferay.portal.kernel.model.adapter.ModelAdapterUtil;
import com.liferay.portal.kernel.model.adapter.builder.ModelAdapterBuilder;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Date;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Adolfo Pérez
 */
@Component(immediate = true, service = ModelAdapterBuilder.class)
public class SubscriptionModelAdapterBuilder
	implements ModelAdapterBuilder
		<com.liferay.subscription.model.Subscription, Subscription> {

	@Override
	public Subscription build(
		com.liferay.subscription.model.Subscription adapteeModel) {

		if (adapteeModel == null) {
			return null;
		}

		return new SubscriptionAdapter(adapteeModel);
	}

	private static class SubscriptionAdapter implements Subscription {

		public SubscriptionAdapter(
			com.liferay.subscription.model.Subscription subscription) {

			_subscription = subscription;
		}

		@Override
		public Object clone() {
			return _subscription.clone();
		}

		@Override
		public int compareTo(Subscription subscription) {
			long primaryKey = subscription.getPrimaryKey();

			if (getPrimaryKey() < primaryKey) {
				return -1;
			}
			else if (getPrimaryKey() > primaryKey) {
				return 1;
			}
			else {
				return 0;
			}
		}

		@Override
		public boolean equals(Object o) {
			return _subscription.equals(o);
		}

		@Override
		public String getClassName() {
			return _subscription.getClassName();
		}

		@Override
		public long getClassNameId() {
			return _subscription.getClassNameId();
		}

		@Override
		public long getClassPK() {
			return _subscription.getClassPK();
		}

		@Override
		public long getCompanyId() {
			return _subscription.getCompanyId();
		}

		@Override
		public Date getCreateDate() {
			return _subscription.getCreateDate();
		}

		@Override
		public ExpandoBridge getExpandoBridge() {
			return _subscription.getExpandoBridge();
		}

		@AutoEscape
		@Override
		public String getFrequency() {
			return _subscription.getFrequency();
		}

		@Override
		public long getGroupId() {
			return _subscription.getGroupId();
		}

		@Override
		public Map<String, Object> getModelAttributes() {
			return _subscription.getModelAttributes();
		}

		@Override
		public Class<?> getModelClass() {
			return _subscription.getModelClass();
		}

		@Override
		public String getModelClassName() {
			return _subscription.getModelClassName();
		}

		@Override
		public Date getModifiedDate() {
			return _subscription.getModifiedDate();
		}

		@Override
		public long getMvccVersion() {
			return _subscription.getMvccVersion();
		}

		@Override
		public long getPrimaryKey() {
			return _subscription.getPrimaryKey();
		}

		@Override
		public Serializable getPrimaryKeyObj() {
			return _subscription.getPrimaryKeyObj();
		}

		@Override
		public long getSubscriptionId() {
			return _subscription.getSubscriptionId();
		}

		@Override
		public long getUserId() {
			return _subscription.getUserId();
		}

		@AutoEscape
		@Override
		public String getUserName() {
			return _subscription.getUserName();
		}

		@Override
		public String getUserUuid() {
			return _subscription.getUserUuid();
		}

		@Override
		public int hashCode() {
			return _subscription.hashCode();
		}

		@Override
		public boolean isCachedModel() {
			return _subscription.isCachedModel();
		}

		@Override
		public boolean isEntityCacheEnabled() {
			return _subscription.isEntityCacheEnabled();
		}

		@Override
		public boolean isEscapedModel() {
			return _subscription.isEscapedModel();
		}

		@Override
		public boolean isFinderCacheEnabled() {
			return _subscription.isFinderCacheEnabled();
		}

		@Override
		public boolean isNew() {
			return _subscription.isNew();
		}

		@Override
		public void persist() {
			_subscription.persist();
		}

		@Override
		public void resetOriginalValues() {
			_subscription.resetOriginalValues();
		}

		@Override
		public void setCachedModel(boolean cachedModel) {
			_subscription.setCachedModel(cachedModel);
		}

		@Override
		public void setClassName(String className) {
			_subscription.setClassName(className);
		}

		@Override
		public void setClassNameId(long classNameId) {
			_subscription.setClassNameId(classNameId);
		}

		@Override
		public void setClassPK(long classPK) {
			_subscription.setClassPK(classPK);
		}

		@Override
		public void setCompanyId(long companyId) {
			_subscription.setCompanyId(companyId);
		}

		@Override
		public void setCreateDate(Date createDate) {
			_subscription.setCreateDate(createDate);
		}

		@Override
		public void setExpandoBridgeAttributes(BaseModel<?> baseModel) {
			_subscription.setExpandoBridgeAttributes(baseModel);
		}

		@Override
		public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
			_subscription.setExpandoBridgeAttributes(expandoBridge);
		}

		@Override
		public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
			_subscription.setExpandoBridgeAttributes(serviceContext);
		}

		@Override
		public void setFrequency(String frequency) {
			_subscription.setFrequency(frequency);
		}

		@Override
		public void setGroupId(long groupId) {
			_subscription.setGroupId(groupId);
		}

		@Override
		public void setModelAttributes(Map<String, Object> attributes) {
			_subscription.setModelAttributes(attributes);
		}

		@Override
		public void setModifiedDate(Date modifiedDate) {
			_subscription.setModifiedDate(modifiedDate);
		}

		@Override
		public void setMvccVersion(long mvccVersion) {
			_subscription.setMvccVersion(mvccVersion);
		}

		@Override
		public void setNew(boolean n) {
			_subscription.setNew(n);
		}

		@Override
		public void setPrimaryKey(long primaryKey) {
			_subscription.setPrimaryKey(primaryKey);
		}

		@Override
		public void setPrimaryKeyObj(Serializable primaryKeyObj) {
			_subscription.setPrimaryKeyObj(primaryKeyObj);
		}

		@Override
		public void setSubscriptionId(long subscriptionId) {
			_subscription.setSubscriptionId(subscriptionId);
		}

		@Override
		public void setUserId(long userId) {
			_subscription.setUserId(userId);
		}

		@Override
		public void setUserName(String userName) {
			_subscription.setUserName(userName);
		}

		@Override
		public void setUserUuid(String userUuid) {
			_subscription.setUserUuid(userUuid);
		}

		@Override
		public CacheModel<Subscription> toCacheModel() {
			CacheModel<com.liferay.subscription.model.Subscription> cacheModel =
				_subscription.toCacheModel();

			return new CacheModel<Subscription>() {

				@Override
				public Subscription toEntityModel() {
					return _adapt(cacheModel.toEntityModel());
				}

			};
		}

		@Override
		public Subscription toEscapedModel() {
			return _adapt(_subscription.toEscapedModel());
		}

		@Override
		public String toString() {
			return _subscription.toString();
		}

		@Override
		public Subscription toUnescapedModel() {
			return _adapt(_subscription.toUnescapedModel());
		}

		@Override
		public String toXmlString() {
			return _subscription.toXmlString();
		}

		private Subscription _adapt(
			com.liferay.subscription.model.Subscription subscription) {

			return ModelAdapterUtil.adapt(
				subscription, com.liferay.subscription.model.Subscription.class,
				Subscription.class);
		}

		private final com.liferay.subscription.model.Subscription _subscription;

	}

}