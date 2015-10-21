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

package com.liferay.portlet.asset.model.adapter.impl;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetLink;
import com.liferay.portlet.asset.model.adapter.StagedAssetLink;
import com.liferay.portlet.asset.model.impl.AssetLinkImpl;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.exportimport.lar.StagedModelType;

import java.util.Date;

/**
 * @author Mate Thurzo
 */
public class StagedAssetLinkImpl extends AssetLinkImpl
	implements StagedAssetLink {

	public StagedAssetLinkImpl() {
	}

	public StagedAssetLinkImpl(AssetLink assetLink) {
		_assetLink = assetLink;

		populateEntry1Attributes();
		populateEntry2Attributes();

		populateUuid();
	}

	@Override
	public String getEntry1ClassName() {
		if (Validator.isNotNull(_entry1ClassName)) {
			return _entry1ClassName;
		}

		populateEntry1Attributes();

		return _entry1ClassName;
	}

	@Override
	public String getEntry1Uuid() {
		if (Validator.isNotNull(_entry1Uuid)) {
			return _entry1Uuid;
		}

		populateEntry1Attributes();

		return _entry1Uuid;
	}

	@Override
	public String getEntry2ClassName() {
		if (Validator.isNotNull(_entry2ClassName)) {
			return _entry2ClassName;
		}

		populateEntry2Attributes();

		return _entry2ClassName;
	}

	@Override
	public String getEntry2Uuid() {
		if (Validator.isNotNull(_entry2Uuid)) {
			return _entry2Uuid;
		}

		populateEntry2Attributes();

		return _entry2Uuid;
	}

	@Override
	public Date getModifiedDate() {
		return _assetLink.getCreateDate();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return new StagedModelType(StagedAssetLink.class);
	}

	@Override
	public String getUuid() {
		if (Validator.isNotNull(_uuid)) {
			return _uuid;
		}

		populateUuid();

		return _uuid;
	}

	public void setAssetLink(AssetLink assetLink) {
		_assetLink = assetLink;
	}

	@Override
	public void setModifiedDate(Date date) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setUuid(String uuid) {
		throw new UnsupportedOperationException();
	}

	protected void populateEntry1Attributes() {
		if (Validator.isNotNull(_entry1ClassName) &&
			Validator.isNotNull(_entry1Uuid)) {

			return;
		}

		AssetEntry entry1 = AssetEntryLocalServiceUtil.fetchAssetEntry(
			_assetLink.getEntryId1());

		if (entry1 == null) {
			return;
		}

		_entry1ClassName = entry1.getClassName();
		_entry1Uuid = entry1.getClassUuid();
	}

	protected void populateEntry2Attributes() {
		if (Validator.isNotNull(_entry2ClassName) &&
			Validator.isNotNull(_entry2Uuid)) {

			return;
		}

		AssetEntry entry2 = AssetEntryLocalServiceUtil.fetchAssetEntry(
			_assetLink.getEntryId2());

		if (entry2 == null) {
			return;
		}

		_entry2ClassName = entry2.getClassName();
		_entry2Uuid = entry2.getClassUuid();
	}

	protected void populateUuid() {
		if (Validator.isNotNull(_uuid)) {
			return;
		}

		String entry1Uuid = getEntry1Uuid();
		String entry2Uuid = getEntry2Uuid();

		if (Validator.isNotNull(entry1Uuid) &&
			Validator.isNotNull(entry2Uuid)) {

			_uuid = entry1Uuid + StringPool.POUND + entry2Uuid;
		}
	}

	private AssetLink _assetLink;
	private String _entry1ClassName;
	private String _entry1Uuid;
	private String _entry2ClassName;
	private String _entry2Uuid;
	private String _uuid;

}