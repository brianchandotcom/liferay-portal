/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.model.impl;

import com.liferay.dynamic.data.mapping.model.DDMTemplateLink;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing DDMTemplateLink in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class DDMTemplateLinkCacheModel
	implements CacheModel<DDMTemplateLink>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DDMTemplateLinkCacheModel)) {
			return false;
		}

		DDMTemplateLinkCacheModel ddmTemplateLinkCacheModel =
			(DDMTemplateLinkCacheModel)object;

		if ((templateLinkId == ddmTemplateLinkCacheModel.templateLinkId) &&
			(mvccVersion == ddmTemplateLinkCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, templateLinkId);

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
		StringBundler sb = new StringBundler(15);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", templateLinkId=");
		sb.append(templateLinkId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", templateId=");
		sb.append(templateId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public DDMTemplateLink toEntityModel() {
		DDMTemplateLinkImpl ddmTemplateLinkImpl = new DDMTemplateLinkImpl();

		ddmTemplateLinkImpl.setMvccVersion(mvccVersion);
		ddmTemplateLinkImpl.setCtCollectionId(ctCollectionId);
		ddmTemplateLinkImpl.setTemplateLinkId(templateLinkId);
		ddmTemplateLinkImpl.setCompanyId(companyId);
		ddmTemplateLinkImpl.setClassNameId(classNameId);
		ddmTemplateLinkImpl.setClassPK(classPK);
		ddmTemplateLinkImpl.setTemplateId(templateId);

		ddmTemplateLinkImpl.resetOriginalValues();

		return ddmTemplateLinkImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();

		templateLinkId = objectInput.readLong();

		companyId = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();

		templateId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ctCollectionId);

		objectOutput.writeLong(templateLinkId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(classNameId);

		objectOutput.writeLong(classPK);

		objectOutput.writeLong(templateId);
	}

	public long mvccVersion;
	public long ctCollectionId;
	public long templateLinkId;
	public long companyId;
	public long classNameId;
	public long classPK;
	public long templateId;

}