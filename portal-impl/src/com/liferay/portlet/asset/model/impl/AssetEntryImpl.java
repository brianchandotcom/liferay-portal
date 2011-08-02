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

package com.liferay.portlet.asset.model.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetTag;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetTagLocalServiceUtil;
import com.liferay.portlet.social.model.SocialEquityAssetEntry;
import com.liferay.portlet.social.model.SocialEquityValue;
import com.liferay.portlet.social.service.persistence.SocialEquityAssetEntryUtil;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Brian Wing Shun Chan
 */
public class AssetEntryImpl extends AssetEntryBaseImpl {

	public AssetEntryImpl() {
	}

	public List<AssetCategory> getCategories() throws SystemException {
		return AssetCategoryLocalServiceUtil.getEntryCategories(getEntryId());
	}

	public long[] getCategoryIds() throws SystemException {
		return StringUtil.split(
			ListUtil.toString(
				getCategories(), AssetCategory.CATEGORY_ID_ACCESSOR), 0L);
	}

	public double getSocialInformationEquity() {
		if (_socialInformationEquity == null) {
			try {
				SocialEquityAssetEntry equityAssetEntry =
					SocialEquityAssetEntryUtil.findByAssetEntryId(
						getEntryId());

				SocialEquityValue socialEquityValue = new SocialEquityValue(
					equityAssetEntry.getInformationK(),
					equityAssetEntry.getInformationB());

				_socialInformationEquity = new AtomicReference<Double>(
					socialEquityValue.getValue());
			}
			catch (PortalException pe) {
				return 0;
			}
			catch (SystemException se) {
				return 0;
			}
		}

		return _socialInformationEquity.get();
	}

	public String[] getTagNames() throws SystemException {
		return StringUtil.split(
			ListUtil.toString(getTags(), AssetTag.NAME_ACCESSOR));
	}

	public List<AssetTag> getTags() throws SystemException {
		return AssetTagLocalServiceUtil.getEntryTags(getEntryId());
	}

	public void updateSocialInformationEquity(double value) {
		if (_socialInformationEquity != null) {
			double currentValue = 0;
			double newValue = 0;

			do {
				currentValue = _socialInformationEquity.get();

				newValue = currentValue + value;

			}
			while (!_socialInformationEquity.compareAndSet(
						currentValue, newValue));
		}
	}

	private AtomicReference<Double> _socialInformationEquity = null;

}