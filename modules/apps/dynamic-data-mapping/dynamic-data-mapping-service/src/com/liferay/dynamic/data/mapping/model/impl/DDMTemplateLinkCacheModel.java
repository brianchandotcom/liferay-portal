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

package com.liferay.dynamic.data.mapping.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.dynamic.data.mapping.model.DDMTemplateLink;

import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing DDMTemplateLink in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see DDMTemplateLink
 * @generated
 */
@ProviderType
public class DDMTemplateLinkCacheModel implements CacheModel<DDMTemplateLink>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DDMTemplateLinkCacheModel)) {
			return false;
		}

		DDMTemplateLinkCacheModel ddmTemplateLinkCacheModel = (DDMTemplateLinkCacheModel)obj;

		if (templateLinkId == ddmTemplateLinkCacheModel.templateLinkId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, templateLinkId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(11);

		sb.append("{templateLinkId=");
		sb.append(templateLinkId);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", templateId=");
		sb.append(templateId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public DDMTemplateLink toEntityModel() {
		DDMTemplateLinkImpl ddmTemplateLinkImpl = new DDMTemplateLinkImpl();

		ddmTemplateLinkImpl.setTemplateLinkId(templateLinkId);
		ddmTemplateLinkImpl.setClassNameId(classNameId);
		ddmTemplateLinkImpl.setClassPK(classPK);
		ddmTemplateLinkImpl.setTemplateId(templateId);
		ddmTemplateLinkImpl.setCompanyId(companyId);

		ddmTemplateLinkImpl.resetOriginalValues();

		return ddmTemplateLinkImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		templateLinkId = objectInput.readLong();
		classNameId = objectInput.readLong();
		classPK = objectInput.readLong();
		templateId = objectInput.readLong();
		companyId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(templateLinkId);
		objectOutput.writeLong(classNameId);
		objectOutput.writeLong(classPK);
		objectOutput.writeLong(templateId);
		objectOutput.writeLong(companyId);
	}

	public long templateLinkId;
	public long classNameId;
	public long classPK;
	public long templateId;
	public long companyId;
}