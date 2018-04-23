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

package com.liferay.commerce.cloud.client.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.cloud.client.model.CommerceCloudOrderForecastSync;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CommerceCloudOrderForecastSync in entity cache.
 *
 * @author Andrea Di Giorgi
 * @see CommerceCloudOrderForecastSync
 * @generated
 */
@ProviderType
public class CommerceCloudOrderForecastSyncCacheModel implements CacheModel<CommerceCloudOrderForecastSync>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceCloudOrderForecastSyncCacheModel)) {
			return false;
		}

		CommerceCloudOrderForecastSyncCacheModel commerceCloudOrderForecastSyncCacheModel =
			(CommerceCloudOrderForecastSyncCacheModel)obj;

		if (commerceCloudOrderForecastSyncId == commerceCloudOrderForecastSyncCacheModel.commerceCloudOrderForecastSyncId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, commerceCloudOrderForecastSyncId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(13);

		sb.append("{commerceCloudOrderForecastSyncId=");
		sb.append(commerceCloudOrderForecastSyncId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", commerceOrderId=");
		sb.append(commerceOrderId);
		sb.append(", syncDate=");
		sb.append(syncDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceCloudOrderForecastSync toEntityModel() {
		CommerceCloudOrderForecastSyncImpl commerceCloudOrderForecastSyncImpl = new CommerceCloudOrderForecastSyncImpl();

		commerceCloudOrderForecastSyncImpl.setCommerceCloudOrderForecastSyncId(commerceCloudOrderForecastSyncId);
		commerceCloudOrderForecastSyncImpl.setGroupId(groupId);
		commerceCloudOrderForecastSyncImpl.setCompanyId(companyId);

		if (createDate == Long.MIN_VALUE) {
			commerceCloudOrderForecastSyncImpl.setCreateDate(null);
		}
		else {
			commerceCloudOrderForecastSyncImpl.setCreateDate(new Date(
					createDate));
		}

		commerceCloudOrderForecastSyncImpl.setCommerceOrderId(commerceOrderId);

		if (syncDate == Long.MIN_VALUE) {
			commerceCloudOrderForecastSyncImpl.setSyncDate(null);
		}
		else {
			commerceCloudOrderForecastSyncImpl.setSyncDate(new Date(syncDate));
		}

		commerceCloudOrderForecastSyncImpl.resetOriginalValues();

		return commerceCloudOrderForecastSyncImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		commerceCloudOrderForecastSyncId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();
		createDate = objectInput.readLong();

		commerceOrderId = objectInput.readLong();
		syncDate = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(commerceCloudOrderForecastSyncId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);
		objectOutput.writeLong(createDate);

		objectOutput.writeLong(commerceOrderId);
		objectOutput.writeLong(syncDate);
	}

	public long commerceCloudOrderForecastSyncId;
	public long groupId;
	public long companyId;
	public long createDate;
	public long commerceOrderId;
	public long syncDate;
}