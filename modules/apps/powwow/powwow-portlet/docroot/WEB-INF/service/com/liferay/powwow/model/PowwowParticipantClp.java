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

package com.liferay.powwow.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import com.liferay.powwow.service.ClpSerializer;
import com.liferay.powwow.service.PowwowParticipantLocalServiceUtil;

import java.io.Serializable;

import java.lang.reflect.Method;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @generated
 */
@ProviderType
public class PowwowParticipantClp extends BaseModelImpl<PowwowParticipant>
	implements PowwowParticipant {
	public PowwowParticipantClp() {
	}

	@Override
	public Class<?> getModelClass() {
		return PowwowParticipant.class;
	}

	@Override
	public String getModelClassName() {
		return PowwowParticipant.class.getName();
	}

	@Override
	public long getPrimaryKey() {
		return _powwowParticipantId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setPowwowParticipantId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _powwowParticipantId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("powwowParticipantId", getPowwowParticipantId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("powwowMeetingId", getPowwowMeetingId());
		attributes.put("name", getName());
		attributes.put("participantUserId", getParticipantUserId());
		attributes.put("emailAddress", getEmailAddress());
		attributes.put("type", getType());
		attributes.put("status", getStatus());

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long powwowParticipantId = (Long)attributes.get("powwowParticipantId");

		if (powwowParticipantId != null) {
			setPowwowParticipantId(powwowParticipantId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String userName = (String)attributes.get("userName");

		if (userName != null) {
			setUserName(userName);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		Long powwowMeetingId = (Long)attributes.get("powwowMeetingId");

		if (powwowMeetingId != null) {
			setPowwowMeetingId(powwowMeetingId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		Long participantUserId = (Long)attributes.get("participantUserId");

		if (participantUserId != null) {
			setParticipantUserId(participantUserId);
		}

		String emailAddress = (String)attributes.get("emailAddress");

		if (emailAddress != null) {
			setEmailAddress(emailAddress);
		}

		Integer type = (Integer)attributes.get("type");

		if (type != null) {
			setType(type);
		}

		Integer status = (Integer)attributes.get("status");

		if (status != null) {
			setStatus(status);
		}

		_entityCacheEnabled = GetterUtil.getBoolean("entityCacheEnabled");
		_finderCacheEnabled = GetterUtil.getBoolean("finderCacheEnabled");
	}

	@Override
	public long getPowwowParticipantId() {
		return _powwowParticipantId;
	}

	@Override
	public void setPowwowParticipantId(long powwowParticipantId) {
		_powwowParticipantId = powwowParticipantId;

		if (_powwowParticipantRemoteModel != null) {
			try {
				Class<?> clazz = _powwowParticipantRemoteModel.getClass();

				Method method = clazz.getMethod("setPowwowParticipantId",
						long.class);

				method.invoke(_powwowParticipantRemoteModel, powwowParticipantId);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public long getGroupId() {
		return _groupId;
	}

	@Override
	public void setGroupId(long groupId) {
		_groupId = groupId;

		if (_powwowParticipantRemoteModel != null) {
			try {
				Class<?> clazz = _powwowParticipantRemoteModel.getClass();

				Method method = clazz.getMethod("setGroupId", long.class);

				method.invoke(_powwowParticipantRemoteModel, groupId);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public void setCompanyId(long companyId) {
		_companyId = companyId;

		if (_powwowParticipantRemoteModel != null) {
			try {
				Class<?> clazz = _powwowParticipantRemoteModel.getClass();

				Method method = clazz.getMethod("setCompanyId", long.class);

				method.invoke(_powwowParticipantRemoteModel, companyId);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public long getUserId() {
		return _userId;
	}

	@Override
	public void setUserId(long userId) {
		_userId = userId;

		if (_powwowParticipantRemoteModel != null) {
			try {
				Class<?> clazz = _powwowParticipantRemoteModel.getClass();

				Method method = clazz.getMethod("setUserId", long.class);

				method.invoke(_powwowParticipantRemoteModel, userId);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public String getUserUuid() {
		try {
			User user = UserLocalServiceUtil.getUserById(getUserId());

			return user.getUuid();
		}
		catch (PortalException pe) {
			return StringPool.BLANK;
		}
	}

	@Override
	public void setUserUuid(String userUuid) {
	}

	@Override
	public String getUserName() {
		return _userName;
	}

	@Override
	public void setUserName(String userName) {
		_userName = userName;

		if (_powwowParticipantRemoteModel != null) {
			try {
				Class<?> clazz = _powwowParticipantRemoteModel.getClass();

				Method method = clazz.getMethod("setUserName", String.class);

				method.invoke(_powwowParticipantRemoteModel, userName);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public Date getCreateDate() {
		return _createDate;
	}

	@Override
	public void setCreateDate(Date createDate) {
		_createDate = createDate;

		if (_powwowParticipantRemoteModel != null) {
			try {
				Class<?> clazz = _powwowParticipantRemoteModel.getClass();

				Method method = clazz.getMethod("setCreateDate", Date.class);

				method.invoke(_powwowParticipantRemoteModel, createDate);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public Date getModifiedDate() {
		return _modifiedDate;
	}

	@Override
	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;

		if (_powwowParticipantRemoteModel != null) {
			try {
				Class<?> clazz = _powwowParticipantRemoteModel.getClass();

				Method method = clazz.getMethod("setModifiedDate", Date.class);

				method.invoke(_powwowParticipantRemoteModel, modifiedDate);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public long getPowwowMeetingId() {
		return _powwowMeetingId;
	}

	@Override
	public void setPowwowMeetingId(long powwowMeetingId) {
		_powwowMeetingId = powwowMeetingId;

		if (_powwowParticipantRemoteModel != null) {
			try {
				Class<?> clazz = _powwowParticipantRemoteModel.getClass();

				Method method = clazz.getMethod("setPowwowMeetingId", long.class);

				method.invoke(_powwowParticipantRemoteModel, powwowMeetingId);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public void setName(String name) {
		_name = name;

		if (_powwowParticipantRemoteModel != null) {
			try {
				Class<?> clazz = _powwowParticipantRemoteModel.getClass();

				Method method = clazz.getMethod("setName", String.class);

				method.invoke(_powwowParticipantRemoteModel, name);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public long getParticipantUserId() {
		return _participantUserId;
	}

	@Override
	public void setParticipantUserId(long participantUserId) {
		_participantUserId = participantUserId;

		if (_powwowParticipantRemoteModel != null) {
			try {
				Class<?> clazz = _powwowParticipantRemoteModel.getClass();

				Method method = clazz.getMethod("setParticipantUserId",
						long.class);

				method.invoke(_powwowParticipantRemoteModel, participantUserId);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public String getParticipantUserUuid() {
		try {
			User user = UserLocalServiceUtil.getUserById(getParticipantUserId());

			return user.getUuid();
		}
		catch (PortalException pe) {
			return StringPool.BLANK;
		}
	}

	@Override
	public void setParticipantUserUuid(String participantUserUuid) {
	}

	@Override
	public String getEmailAddress() {
		return _emailAddress;
	}

	@Override
	public void setEmailAddress(String emailAddress) {
		_emailAddress = emailAddress;

		if (_powwowParticipantRemoteModel != null) {
			try {
				Class<?> clazz = _powwowParticipantRemoteModel.getClass();

				Method method = clazz.getMethod("setEmailAddress", String.class);

				method.invoke(_powwowParticipantRemoteModel, emailAddress);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public int getType() {
		return _type;
	}

	@Override
	public void setType(int type) {
		_type = type;

		if (_powwowParticipantRemoteModel != null) {
			try {
				Class<?> clazz = _powwowParticipantRemoteModel.getClass();

				Method method = clazz.getMethod("setType", int.class);

				method.invoke(_powwowParticipantRemoteModel, type);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public int getStatus() {
		return _status;
	}

	@Override
	public void setStatus(int status) {
		_status = status;

		if (_powwowParticipantRemoteModel != null) {
			try {
				Class<?> clazz = _powwowParticipantRemoteModel.getClass();

				Method method = clazz.getMethod("setStatus", int.class);

				method.invoke(_powwowParticipantRemoteModel, status);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	public BaseModel<?> getPowwowParticipantRemoteModel() {
		return _powwowParticipantRemoteModel;
	}

	public void setPowwowParticipantRemoteModel(
		BaseModel<?> powwowParticipantRemoteModel) {
		_powwowParticipantRemoteModel = powwowParticipantRemoteModel;
	}

	public Object invokeOnRemoteModel(String methodName,
		Class<?>[] parameterTypes, Object[] parameterValues)
		throws Exception {
		Object[] remoteParameterValues = new Object[parameterValues.length];

		for (int i = 0; i < parameterValues.length; i++) {
			if (parameterValues[i] != null) {
				remoteParameterValues[i] = ClpSerializer.translateInput(parameterValues[i]);
			}
		}

		Class<?> remoteModelClass = _powwowParticipantRemoteModel.getClass();

		ClassLoader remoteModelClassLoader = remoteModelClass.getClassLoader();

		Class<?>[] remoteParameterTypes = new Class[parameterTypes.length];

		for (int i = 0; i < parameterTypes.length; i++) {
			if (parameterTypes[i].isPrimitive()) {
				remoteParameterTypes[i] = parameterTypes[i];
			}
			else {
				String parameterTypeName = parameterTypes[i].getName();

				remoteParameterTypes[i] = remoteModelClassLoader.loadClass(parameterTypeName);
			}
		}

		Method method = remoteModelClass.getMethod(methodName,
				remoteParameterTypes);

		Object returnValue = method.invoke(_powwowParticipantRemoteModel,
				remoteParameterValues);

		if (returnValue != null) {
			returnValue = ClpSerializer.translateOutput(returnValue);
		}

		return returnValue;
	}

	@Override
	public void persist() {
		if (this.isNew()) {
			PowwowParticipantLocalServiceUtil.addPowwowParticipant(this);
		}
		else {
			PowwowParticipantLocalServiceUtil.updatePowwowParticipant(this);
		}
	}

	@Override
	public PowwowParticipant toEscapedModel() {
		return (PowwowParticipant)ProxyUtil.newProxyInstance(PowwowParticipant.class.getClassLoader(),
			new Class[] { PowwowParticipant.class },
			new AutoEscapeBeanHandler(this));
	}

	@Override
	public Object clone() {
		PowwowParticipantClp clone = new PowwowParticipantClp();

		clone.setPowwowParticipantId(getPowwowParticipantId());
		clone.setGroupId(getGroupId());
		clone.setCompanyId(getCompanyId());
		clone.setUserId(getUserId());
		clone.setUserName(getUserName());
		clone.setCreateDate(getCreateDate());
		clone.setModifiedDate(getModifiedDate());
		clone.setPowwowMeetingId(getPowwowMeetingId());
		clone.setName(getName());
		clone.setParticipantUserId(getParticipantUserId());
		clone.setEmailAddress(getEmailAddress());
		clone.setType(getType());
		clone.setStatus(getStatus());

		return clone;
	}

	@Override
	public int compareTo(PowwowParticipant powwowParticipant) {
		long primaryKey = powwowParticipant.getPrimaryKey();

		if (getPrimaryKey() < primaryKey) {
			return -1;
		}
		else if (getPrimaryKey() > primaryKey) {
			return 1;
		}
		else {
			return 0;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof PowwowParticipantClp)) {
			return false;
		}

		PowwowParticipantClp powwowParticipant = (PowwowParticipantClp)obj;

		long primaryKey = powwowParticipant.getPrimaryKey();

		if (getPrimaryKey() == primaryKey) {
			return true;
		}
		else {
			return false;
		}
	}

	public Class<?> getClpSerializerClass() {
		return _clpSerializerClass;
	}

	@Override
	public int hashCode() {
		return (int)getPrimaryKey();
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _entityCacheEnabled;
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _finderCacheEnabled;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(27);

		sb.append("{powwowParticipantId=");
		sb.append(getPowwowParticipantId());
		sb.append(", groupId=");
		sb.append(getGroupId());
		sb.append(", companyId=");
		sb.append(getCompanyId());
		sb.append(", userId=");
		sb.append(getUserId());
		sb.append(", userName=");
		sb.append(getUserName());
		sb.append(", createDate=");
		sb.append(getCreateDate());
		sb.append(", modifiedDate=");
		sb.append(getModifiedDate());
		sb.append(", powwowMeetingId=");
		sb.append(getPowwowMeetingId());
		sb.append(", name=");
		sb.append(getName());
		sb.append(", participantUserId=");
		sb.append(getParticipantUserId());
		sb.append(", emailAddress=");
		sb.append(getEmailAddress());
		sb.append(", type=");
		sb.append(getType());
		sb.append(", status=");
		sb.append(getStatus());
		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		StringBundler sb = new StringBundler(43);

		sb.append("<model><model-name>");
		sb.append("com.liferay.powwow.model.PowwowParticipant");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>powwowParticipantId</column-name><column-value><![CDATA[");
		sb.append(getPowwowParticipantId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>groupId</column-name><column-value><![CDATA[");
		sb.append(getGroupId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>companyId</column-name><column-value><![CDATA[");
		sb.append(getCompanyId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userId</column-name><column-value><![CDATA[");
		sb.append(getUserId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userName</column-name><column-value><![CDATA[");
		sb.append(getUserName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>createDate</column-name><column-value><![CDATA[");
		sb.append(getCreateDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>modifiedDate</column-name><column-value><![CDATA[");
		sb.append(getModifiedDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>powwowMeetingId</column-name><column-value><![CDATA[");
		sb.append(getPowwowMeetingId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>name</column-name><column-value><![CDATA[");
		sb.append(getName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>participantUserId</column-name><column-value><![CDATA[");
		sb.append(getParticipantUserId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>emailAddress</column-name><column-value><![CDATA[");
		sb.append(getEmailAddress());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>type</column-name><column-value><![CDATA[");
		sb.append(getType());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>status</column-name><column-value><![CDATA[");
		sb.append(getStatus());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _powwowParticipantId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _powwowMeetingId;
	private String _name;
	private long _participantUserId;
	private String _emailAddress;
	private int _type;
	private int _status;
	private BaseModel<?> _powwowParticipantRemoteModel;
	private Class<?> _clpSerializerClass = ClpSerializer.class;
	private boolean _entityCacheEnabled;
	private boolean _finderCacheEnabled;
}