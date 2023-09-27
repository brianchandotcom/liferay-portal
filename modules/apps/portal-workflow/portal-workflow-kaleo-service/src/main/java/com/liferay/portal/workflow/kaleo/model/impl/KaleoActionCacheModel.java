/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.portal.workflow.kaleo.model.KaleoAction;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing KaleoAction in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class KaleoActionCacheModel
	implements CacheModel<KaleoAction>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof KaleoActionCacheModel)) {
			return false;
		}

		KaleoActionCacheModel kaleoActionCacheModel =
			(KaleoActionCacheModel)object;

		if ((kaleoActionId == kaleoActionCacheModel.kaleoActionId) &&
			(mvccVersion == kaleoActionCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, kaleoActionId);

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
		StringBundler sb = new StringBundler(47);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", kaleoActionId=");
		sb.append(kaleoActionId);
		sb.append(", groupId=");
		sb.append(groupId);
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
		sb.append(", kaleoClassName=");
		sb.append(kaleoClassName);
		sb.append(", kaleoClassPK=");
		sb.append(kaleoClassPK);
		sb.append(", kaleoDefinitionId=");
		sb.append(kaleoDefinitionId);
		sb.append(", kaleoDefinitionVersionId=");
		sb.append(kaleoDefinitionVersionId);
		sb.append(", kaleoNodeName=");
		sb.append(kaleoNodeName);
		sb.append(", name=");
		sb.append(name);
		sb.append(", description=");
		sb.append(description);
		sb.append(", executionType=");
		sb.append(executionType);
		sb.append(", script=");
		sb.append(script);
		sb.append(", scriptLanguage=");
		sb.append(scriptLanguage);
		sb.append(", scriptRequiredContexts=");
		sb.append(scriptRequiredContexts);
		sb.append(", priority=");
		sb.append(priority);
		sb.append(", type=");
		sb.append(type);
		sb.append(", status=");
		sb.append(status);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public KaleoAction toEntityModel() {
		KaleoActionImpl kaleoActionImpl = new KaleoActionImpl();

		kaleoActionImpl.setMvccVersion(mvccVersion);
		kaleoActionImpl.setCtCollectionId(ctCollectionId);
		kaleoActionImpl.setKaleoActionId(kaleoActionId);
		kaleoActionImpl.setGroupId(groupId);
		kaleoActionImpl.setCompanyId(companyId);
		kaleoActionImpl.setUserId(userId);

		if (userName == null) {
			kaleoActionImpl.setUserName("");
		}
		else {
			kaleoActionImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			kaleoActionImpl.setCreateDate(null);
		}
		else {
			kaleoActionImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			kaleoActionImpl.setModifiedDate(null);
		}
		else {
			kaleoActionImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (kaleoClassName == null) {
			kaleoActionImpl.setKaleoClassName("");
		}
		else {
			kaleoActionImpl.setKaleoClassName(kaleoClassName);
		}

		kaleoActionImpl.setKaleoClassPK(kaleoClassPK);
		kaleoActionImpl.setKaleoDefinitionId(kaleoDefinitionId);
		kaleoActionImpl.setKaleoDefinitionVersionId(kaleoDefinitionVersionId);

		if (kaleoNodeName == null) {
			kaleoActionImpl.setKaleoNodeName("");
		}
		else {
			kaleoActionImpl.setKaleoNodeName(kaleoNodeName);
		}

		if (name == null) {
			kaleoActionImpl.setName("");
		}
		else {
			kaleoActionImpl.setName(name);
		}

		if (description == null) {
			kaleoActionImpl.setDescription("");
		}
		else {
			kaleoActionImpl.setDescription(description);
		}

		if (executionType == null) {
			kaleoActionImpl.setExecutionType("");
		}
		else {
			kaleoActionImpl.setExecutionType(executionType);
		}

		if (script == null) {
			kaleoActionImpl.setScript("");
		}
		else {
			kaleoActionImpl.setScript(script);
		}

		if (scriptLanguage == null) {
			kaleoActionImpl.setScriptLanguage("");
		}
		else {
			kaleoActionImpl.setScriptLanguage(scriptLanguage);
		}

		if (scriptRequiredContexts == null) {
			kaleoActionImpl.setScriptRequiredContexts("");
		}
		else {
			kaleoActionImpl.setScriptRequiredContexts(scriptRequiredContexts);
		}

		kaleoActionImpl.setPriority(priority);

		if (type == null) {
			kaleoActionImpl.setType("");
		}
		else {
			kaleoActionImpl.setType(type);
		}

		kaleoActionImpl.setStatus(status);

		kaleoActionImpl.resetOriginalValues();

		return kaleoActionImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();

		kaleoActionId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		kaleoClassName = objectInput.readUTF();

		kaleoClassPK = objectInput.readLong();

		kaleoDefinitionId = objectInput.readLong();

		kaleoDefinitionVersionId = objectInput.readLong();
		kaleoNodeName = objectInput.readUTF();
		name = objectInput.readUTF();
		description = objectInput.readUTF();
		executionType = objectInput.readUTF();
		script = (String)objectInput.readObject();
		scriptLanguage = objectInput.readUTF();
		scriptRequiredContexts = objectInput.readUTF();

		priority = objectInput.readInt();
		type = objectInput.readUTF();

		status = objectInput.readInt();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ctCollectionId);

		objectOutput.writeLong(kaleoActionId);

		objectOutput.writeLong(groupId);

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

		if (kaleoClassName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(kaleoClassName);
		}

		objectOutput.writeLong(kaleoClassPK);

		objectOutput.writeLong(kaleoDefinitionId);

		objectOutput.writeLong(kaleoDefinitionVersionId);

		if (kaleoNodeName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(kaleoNodeName);
		}

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (description == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(description);
		}

		if (executionType == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(executionType);
		}

		if (script == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(script);
		}

		if (scriptLanguage == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(scriptLanguage);
		}

		if (scriptRequiredContexts == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(scriptRequiredContexts);
		}

		objectOutput.writeInt(priority);

		if (type == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(type);
		}

		objectOutput.writeInt(status);
	}

	public long mvccVersion;
	public long ctCollectionId;
	public long kaleoActionId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String kaleoClassName;
	public long kaleoClassPK;
	public long kaleoDefinitionId;
	public long kaleoDefinitionVersionId;
	public String kaleoNodeName;
	public String name;
	public String description;
	public String executionType;
	public String script;
	public String scriptLanguage;
	public String scriptRequiredContexts;
	public int priority;
	public String type;
	public int status;

}