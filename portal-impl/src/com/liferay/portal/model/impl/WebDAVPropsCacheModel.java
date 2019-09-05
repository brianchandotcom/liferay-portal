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

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.portal.kernel.model.WebDAVProps;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing WebDAVProps in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class WebDAVPropsCacheModel
	implements CacheModel<WebDAVProps>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof WebDAVPropsCacheModel)) {
			return false;
		}

		WebDAVPropsCacheModel webDAVPropsCacheModel =
			(WebDAVPropsCacheModel)obj;

		if ((webDavPropsId == webDAVPropsCacheModel.webDavPropsId) &&
			(mvccVersion == webDAVPropsCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, webDavPropsId);

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
		sb.append(", webDavPropsId=");
		sb.append(webDavPropsId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", props=");
		sb.append(props);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public WebDAVProps toEntityModel() {
		WebDAVPropsImpl webDAVPropsImpl = new WebDAVPropsImpl();

		webDAVPropsImpl.setMvccVersion(mvccVersion);
		webDAVPropsImpl.setWebDavPropsId(webDavPropsId);
		webDAVPropsImpl.setCompanyId(companyId);

		if (createDate == Long.MIN_VALUE) {
			webDAVPropsImpl.setCreateDate(null);
		}
		else {
			webDAVPropsImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			webDAVPropsImpl.setModifiedDate(null);
		}
		else {
			webDAVPropsImpl.setModifiedDate(new Date(modifiedDate));
		}

		webDAVPropsImpl.setClassNameId(classNameId);
		webDAVPropsImpl.setClassPK(classPK);

		if (props == null) {
			webDAVPropsImpl.setProps("");
		}
		else {
			webDAVPropsImpl.setProps(props);
		}

		webDAVPropsImpl.resetOriginalValues();

		return webDAVPropsImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		webDavPropsId = objectInput.readLong();

		companyId = objectInput.readLong();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();
		props = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(webDavPropsId);

		objectOutput.writeLong(companyId);
		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		objectOutput.writeLong(classNameId);

		objectOutput.writeLong(classPK);

		if (props == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(props);
		}
	}

	public long mvccVersion;
	public long webDavPropsId;
	public long companyId;
	public long createDate;
	public long modifiedDate;
	public long classNameId;
	public long classPK;
	public String props;

}