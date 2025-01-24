/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.seo.model.impl;

import com.liferay.layout.seo.model.LayoutSEOEntryCustomMetaTag;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing LayoutSEOEntryCustomMetaTag in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class LayoutSEOEntryCustomMetaTagCacheModel
	implements CacheModel<LayoutSEOEntryCustomMetaTag>, Externalizable,
			   MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof LayoutSEOEntryCustomMetaTagCacheModel)) {
			return false;
		}

		LayoutSEOEntryCustomMetaTagCacheModel
			layoutSEOEntryCustomMetaTagCacheModel =
				(LayoutSEOEntryCustomMetaTagCacheModel)object;

		if ((layoutSEOEntryCustomMetaTagId ==
				layoutSEOEntryCustomMetaTagCacheModel.
					layoutSEOEntryCustomMetaTagId) &&
			(mvccVersion ==
				layoutSEOEntryCustomMetaTagCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, layoutSEOEntryCustomMetaTagId);

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
		StringBundler sb = new StringBundler(17);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", layoutSEOEntryCustomMetaTagId=");
		sb.append(layoutSEOEntryCustomMetaTagId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", layoutSEOEntryId=");
		sb.append(layoutSEOEntryId);
		sb.append(", property=");
		sb.append(property);
		sb.append(", content=");
		sb.append(content);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public LayoutSEOEntryCustomMetaTag toEntityModel() {
		LayoutSEOEntryCustomMetaTagImpl layoutSEOEntryCustomMetaTagImpl =
			new LayoutSEOEntryCustomMetaTagImpl();

		layoutSEOEntryCustomMetaTagImpl.setMvccVersion(mvccVersion);
		layoutSEOEntryCustomMetaTagImpl.setCtCollectionId(ctCollectionId);
		layoutSEOEntryCustomMetaTagImpl.setLayoutSEOEntryCustomMetaTagId(
			layoutSEOEntryCustomMetaTagId);
		layoutSEOEntryCustomMetaTagImpl.setGroupId(groupId);
		layoutSEOEntryCustomMetaTagImpl.setCompanyId(companyId);
		layoutSEOEntryCustomMetaTagImpl.setLayoutSEOEntryId(layoutSEOEntryId);

		if (property == null) {
			layoutSEOEntryCustomMetaTagImpl.setProperty("");
		}
		else {
			layoutSEOEntryCustomMetaTagImpl.setProperty(property);
		}

		if (content == null) {
			layoutSEOEntryCustomMetaTagImpl.setContent("");
		}
		else {
			layoutSEOEntryCustomMetaTagImpl.setContent(content);
		}

		layoutSEOEntryCustomMetaTagImpl.resetOriginalValues();

		return layoutSEOEntryCustomMetaTagImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();

		layoutSEOEntryCustomMetaTagId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		layoutSEOEntryId = objectInput.readLong();
		property = objectInput.readUTF();
		content = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ctCollectionId);

		objectOutput.writeLong(layoutSEOEntryCustomMetaTagId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(layoutSEOEntryId);

		if (property == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(property);
		}

		if (content == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(content);
		}
	}

	public long mvccVersion;
	public long ctCollectionId;
	public long layoutSEOEntryCustomMetaTagId;
	public long groupId;
	public long companyId;
	public long layoutSEOEntryId;
	public String property;
	public String content;

}