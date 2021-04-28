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

package com.liferay.commerce.shop.by.diagram.model.impl;

import com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSettings;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CPDefinitionDiagramSettings in entity cache.
 *
 * @author Alessio Antonio Rendina
 * @generated
 */
public class CPDefinitionDiagramSettingsCacheModel
	implements CacheModel<CPDefinitionDiagramSettings>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CPDefinitionDiagramSettingsCacheModel)) {
			return false;
		}

		CPDefinitionDiagramSettingsCacheModel
			cpDefinitionDiagramSettingsCacheModel =
				(CPDefinitionDiagramSettingsCacheModel)object;

		if (CPDefinitionDiagramSettingsId ==
				cpDefinitionDiagramSettingsCacheModel.
					CPDefinitionDiagramSettingsId) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, CPDefinitionDiagramSettingsId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(19);

		sb.append("{CPDefinitionDiagramSettingsId=");
		sb.append(CPDefinitionDiagramSettingsId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", CPDefinitionId=");
		sb.append(CPDefinitionId);
		sb.append(", assetCategoryId=");
		sb.append(assetCategoryId);
		sb.append(", name=");
		sb.append(name);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CPDefinitionDiagramSettings toEntityModel() {
		CPDefinitionDiagramSettingsImpl cpDefinitionDiagramSettingsImpl =
			new CPDefinitionDiagramSettingsImpl();

		cpDefinitionDiagramSettingsImpl.setCPDefinitionDiagramSettingsId(
			CPDefinitionDiagramSettingsId);
		cpDefinitionDiagramSettingsImpl.setCompanyId(companyId);
		cpDefinitionDiagramSettingsImpl.setUserId(userId);

		if (userName == null) {
			cpDefinitionDiagramSettingsImpl.setUserName("");
		}
		else {
			cpDefinitionDiagramSettingsImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			cpDefinitionDiagramSettingsImpl.setCreateDate(null);
		}
		else {
			cpDefinitionDiagramSettingsImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			cpDefinitionDiagramSettingsImpl.setModifiedDate(null);
		}
		else {
			cpDefinitionDiagramSettingsImpl.setModifiedDate(
				new Date(modifiedDate));
		}

		cpDefinitionDiagramSettingsImpl.setCPDefinitionId(CPDefinitionId);
		cpDefinitionDiagramSettingsImpl.setAssetCategoryId(assetCategoryId);

		if (name == null) {
			cpDefinitionDiagramSettingsImpl.setName("");
		}
		else {
			cpDefinitionDiagramSettingsImpl.setName(name);
		}

		cpDefinitionDiagramSettingsImpl.resetOriginalValues();

		return cpDefinitionDiagramSettingsImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		CPDefinitionDiagramSettingsId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		CPDefinitionId = objectInput.readLong();

		assetCategoryId = objectInput.readLong();
		name = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(CPDefinitionDiagramSettingsId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		objectOutput.writeLong(CPDefinitionId);

		objectOutput.writeLong(assetCategoryId);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}
	}

	public long CPDefinitionDiagramSettingsId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long CPDefinitionId;
	public long assetCategoryId;
	public String name;

}