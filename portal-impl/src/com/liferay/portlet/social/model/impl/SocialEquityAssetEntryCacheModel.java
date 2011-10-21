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

package com.liferay.portlet.social.model.impl;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.CacheModel;

import com.liferay.portlet.social.model.SocialEquityAssetEntry;

import java.io.Serializable;

/**
 * The cache model class for representing SocialEquityAssetEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see SocialEquityAssetEntry
 * @generated
 */
public class SocialEquityAssetEntryCacheModel implements CacheModel<SocialEquityAssetEntry>,
	Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(15);

		sb.append("{equityAssetEntryId=");
		sb.append(equityAssetEntryId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", assetEntryId=");
		sb.append(assetEntryId);
		sb.append(", informationK=");
		sb.append(informationK);
		sb.append(", informationB=");
		sb.append(informationB);
		sb.append("}");

		return sb.toString();
	}

	public SocialEquityAssetEntry toEntityModel() {
		SocialEquityAssetEntryImpl socialEquityAssetEntryImpl = new SocialEquityAssetEntryImpl();

		socialEquityAssetEntryImpl.setEquityAssetEntryId(equityAssetEntryId);
		socialEquityAssetEntryImpl.setGroupId(groupId);
		socialEquityAssetEntryImpl.setCompanyId(companyId);
		socialEquityAssetEntryImpl.setUserId(userId);
		socialEquityAssetEntryImpl.setAssetEntryId(assetEntryId);
		socialEquityAssetEntryImpl.setInformationK(informationK);
		socialEquityAssetEntryImpl.setInformationB(informationB);

		socialEquityAssetEntryImpl.resetOriginalValues();

		return socialEquityAssetEntryImpl;
	}

	public long equityAssetEntryId;
	public long groupId;
	public long companyId;
	public long userId;
	public long assetEntryId;
	public double informationK;
	public double informationB;
}