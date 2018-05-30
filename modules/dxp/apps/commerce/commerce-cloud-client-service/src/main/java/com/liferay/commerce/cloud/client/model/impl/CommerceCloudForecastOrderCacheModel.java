/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.cloud.client.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.cloud.client.model.CommerceCloudForecastOrder;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CommerceCloudForecastOrder in entity cache.
 *
 * @author Andrea Di Giorgi
 * @see CommerceCloudForecastOrder
 * @generated
 */
@ProviderType
public class CommerceCloudForecastOrderCacheModel implements CacheModel<CommerceCloudForecastOrder>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceCloudForecastOrderCacheModel)) {
			return false;
		}

		CommerceCloudForecastOrderCacheModel commerceCloudForecastOrderCacheModel =
			(CommerceCloudForecastOrderCacheModel)obj;

		if (commerceCloudForecastOrderId == commerceCloudForecastOrderCacheModel.commerceCloudForecastOrderId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, commerceCloudForecastOrderId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(13);

		sb.append("{commerceCloudForecastOrderId=");
		sb.append(commerceCloudForecastOrderId);
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
	public CommerceCloudForecastOrder toEntityModel() {
		CommerceCloudForecastOrderImpl commerceCloudForecastOrderImpl = new CommerceCloudForecastOrderImpl();

		commerceCloudForecastOrderImpl.setCommerceCloudForecastOrderId(commerceCloudForecastOrderId);
		commerceCloudForecastOrderImpl.setGroupId(groupId);
		commerceCloudForecastOrderImpl.setCompanyId(companyId);

		if (createDate == Long.MIN_VALUE) {
			commerceCloudForecastOrderImpl.setCreateDate(null);
		}
		else {
			commerceCloudForecastOrderImpl.setCreateDate(new Date(createDate));
		}

		commerceCloudForecastOrderImpl.setCommerceOrderId(commerceOrderId);

		if (syncDate == Long.MIN_VALUE) {
			commerceCloudForecastOrderImpl.setSyncDate(null);
		}
		else {
			commerceCloudForecastOrderImpl.setSyncDate(new Date(syncDate));
		}

		commerceCloudForecastOrderImpl.resetOriginalValues();

		return commerceCloudForecastOrderImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		commerceCloudForecastOrderId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();
		createDate = objectInput.readLong();

		commerceOrderId = objectInput.readLong();
		syncDate = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(commerceCloudForecastOrderId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);
		objectOutput.writeLong(createDate);

		objectOutput.writeLong(commerceOrderId);
		objectOutput.writeLong(syncDate);
	}

	public long commerceCloudForecastOrderId;
	public long groupId;
	public long companyId;
	public long createDate;
	public long commerceOrderId;
	public long syncDate;
}