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

package com.liferay.portal.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing LayoutSet in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutSet
 * @generated
 */
@ProviderType
public class LayoutSetCacheModel implements CacheModel<LayoutSet>,
	Externalizable, MVCCModel {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof LayoutSetCacheModel)) {
			return false;
		}

		LayoutSetCacheModel layoutSetCacheModel = (LayoutSetCacheModel)obj;

		if ((layoutSetId == layoutSetCacheModel.layoutSetId) &&
				(mvccVersion == layoutSetCacheModel.mvccVersion)) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, layoutSetId);

		return HashUtil.hash(hashCode, mvccVersion);
	}

	@Override
	public long getMvccVersion() {
		return mvccVersion;
	}

	@Override
	public void setMvccVersion(long mvccVersion) {
		this.mvccVersion = mvccVersion;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(33);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", layoutSetId=");
		sb.append(layoutSetId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", logoId=");
		sb.append(logoId);
		sb.append(", themeId=");
		sb.append(themeId);
		sb.append(", colorSchemeId=");
		sb.append(colorSchemeId);
		sb.append(", wapThemeId=");
		sb.append(wapThemeId);
		sb.append(", wapColorSchemeId=");
		sb.append(wapColorSchemeId);
		sb.append(", css=");
		sb.append(css);
		sb.append(", pageCount=");
		sb.append(pageCount);
		sb.append(", settings=");
		sb.append(settings);
		sb.append(", layoutSetPrototypeUuid=");
		sb.append(layoutSetPrototypeUuid);
		sb.append(", layoutSetPrototypeLinkEnabled=");
		sb.append(layoutSetPrototypeLinkEnabled);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public LayoutSet toEntityModel() {
		LayoutSetImpl layoutSetImpl = new LayoutSetImpl();

		layoutSetImpl.setMvccVersion(mvccVersion);
		layoutSetImpl.setLayoutSetId(layoutSetId);
		layoutSetImpl.setGroupId(groupId);
		layoutSetImpl.setCompanyId(companyId);

		if (createDate == Long.MIN_VALUE) {
			layoutSetImpl.setCreateDate(null);
		}
		else {
			layoutSetImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			layoutSetImpl.setModifiedDate(null);
		}
		else {
			layoutSetImpl.setModifiedDate(new Date(modifiedDate));
		}

		layoutSetImpl.setLogoId(logoId);

		if (themeId == null) {
			layoutSetImpl.setThemeId(StringPool.BLANK);
		}
		else {
			layoutSetImpl.setThemeId(themeId);
		}

		if (colorSchemeId == null) {
			layoutSetImpl.setColorSchemeId(StringPool.BLANK);
		}
		else {
			layoutSetImpl.setColorSchemeId(colorSchemeId);
		}

		if (wapThemeId == null) {
			layoutSetImpl.setWapThemeId(StringPool.BLANK);
		}
		else {
			layoutSetImpl.setWapThemeId(wapThemeId);
		}

		if (wapColorSchemeId == null) {
			layoutSetImpl.setWapColorSchemeId(StringPool.BLANK);
		}
		else {
			layoutSetImpl.setWapColorSchemeId(wapColorSchemeId);
		}

		if (css == null) {
			layoutSetImpl.setCss(StringPool.BLANK);
		}
		else {
			layoutSetImpl.setCss(css);
		}

		layoutSetImpl.setPageCount(pageCount);

		if (settings == null) {
			layoutSetImpl.setSettings(StringPool.BLANK);
		}
		else {
			layoutSetImpl.setSettings(settings);
		}

		if (layoutSetPrototypeUuid == null) {
			layoutSetImpl.setLayoutSetPrototypeUuid(StringPool.BLANK);
		}
		else {
			layoutSetImpl.setLayoutSetPrototypeUuid(layoutSetPrototypeUuid);
		}

		layoutSetImpl.setLayoutSetPrototypeLinkEnabled(layoutSetPrototypeLinkEnabled);

		layoutSetImpl.resetOriginalValues();

		layoutSetImpl.setCompanyFallbackVirtualHostname(_companyFallbackVirtualHostname);

		layoutSetImpl.setVirtualHostname(_virtualHostname);

		return layoutSetImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {
		mvccVersion = objectInput.readLong();
		layoutSetId = objectInput.readLong();
		groupId = objectInput.readLong();
		companyId = objectInput.readLong();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		logoId = objectInput.readLong();
		themeId = objectInput.readUTF();
		colorSchemeId = objectInput.readUTF();
		wapThemeId = objectInput.readUTF();
		wapColorSchemeId = objectInput.readUTF();
		css = objectInput.readUTF();
		pageCount = objectInput.readInt();
		settings = objectInput.readUTF();
		layoutSetPrototypeUuid = objectInput.readUTF();
		layoutSetPrototypeLinkEnabled = objectInput.readBoolean();

		_companyFallbackVirtualHostname = (java.lang.String)objectInput.readObject();
		_virtualHostname = (java.lang.String)objectInput.readObject();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(mvccVersion);
		objectOutput.writeLong(layoutSetId);
		objectOutput.writeLong(groupId);
		objectOutput.writeLong(companyId);
		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);
		objectOutput.writeLong(logoId);

		if (themeId == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(themeId);
		}

		if (colorSchemeId == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(colorSchemeId);
		}

		if (wapThemeId == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(wapThemeId);
		}

		if (wapColorSchemeId == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(wapColorSchemeId);
		}

		if (css == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(css);
		}

		objectOutput.writeInt(pageCount);

		if (settings == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(settings);
		}

		if (layoutSetPrototypeUuid == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(layoutSetPrototypeUuid);
		}

		objectOutput.writeBoolean(layoutSetPrototypeLinkEnabled);

		objectOutput.writeObject(_companyFallbackVirtualHostname);
		objectOutput.writeObject(_virtualHostname);
	}

	public long mvccVersion;
	public long layoutSetId;
	public long groupId;
	public long companyId;
	public long createDate;
	public long modifiedDate;
	public long logoId;
	public String themeId;
	public String colorSchemeId;
	public String wapThemeId;
	public String wapColorSchemeId;
	public String css;
	public int pageCount;
	public String settings;
	public String layoutSetPrototypeUuid;
	public boolean layoutSetPrototypeLinkEnabled;
	public java.lang.String _companyFallbackVirtualHostname;
	public java.lang.String _virtualHostname;
}