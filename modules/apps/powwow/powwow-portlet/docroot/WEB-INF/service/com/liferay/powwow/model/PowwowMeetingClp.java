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
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import com.liferay.powwow.service.ClpSerializer;
import com.liferay.powwow.service.PowwowMeetingLocalServiceUtil;

import java.io.Serializable;

import java.lang.reflect.Method;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @generated
 */
@ProviderType
public class PowwowMeetingClp extends BaseModelImpl<PowwowMeeting>
	implements PowwowMeeting {
	public PowwowMeetingClp() {
	}

	@Override
	public Class<?> getModelClass() {
		return PowwowMeeting.class;
	}

	@Override
	public String getModelClassName() {
		return PowwowMeeting.class.getName();
	}

	@Override
	public long getPrimaryKey() {
		return _powwowMeetingId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setPowwowMeetingId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _powwowMeetingId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("powwowMeetingId", getPowwowMeetingId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("powwowServerId", getPowwowServerId());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("providerType", getProviderType());
		attributes.put("providerTypeMetadata", getProviderTypeMetadata());
		attributes.put("languageId", getLanguageId());
		attributes.put("calendarBookingId", getCalendarBookingId());
		attributes.put("status", getStatus());

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long powwowMeetingId = (Long)attributes.get("powwowMeetingId");

		if (powwowMeetingId != null) {
			setPowwowMeetingId(powwowMeetingId);
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

		Long powwowServerId = (Long)attributes.get("powwowServerId");

		if (powwowServerId != null) {
			setPowwowServerId(powwowServerId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		String providerType = (String)attributes.get("providerType");

		if (providerType != null) {
			setProviderType(providerType);
		}

		String providerTypeMetadata = (String)attributes.get(
				"providerTypeMetadata");

		if (providerTypeMetadata != null) {
			setProviderTypeMetadata(providerTypeMetadata);
		}

		String languageId = (String)attributes.get("languageId");

		if (languageId != null) {
			setLanguageId(languageId);
		}

		Long calendarBookingId = (Long)attributes.get("calendarBookingId");

		if (calendarBookingId != null) {
			setCalendarBookingId(calendarBookingId);
		}

		Integer status = (Integer)attributes.get("status");

		if (status != null) {
			setStatus(status);
		}

		_entityCacheEnabled = GetterUtil.getBoolean("entityCacheEnabled");
		_finderCacheEnabled = GetterUtil.getBoolean("finderCacheEnabled");
	}

	@Override
	public long getPowwowMeetingId() {
		return _powwowMeetingId;
	}

	@Override
	public void setPowwowMeetingId(long powwowMeetingId) {
		_powwowMeetingId = powwowMeetingId;

		if (_powwowMeetingRemoteModel != null) {
			try {
				Class<?> clazz = _powwowMeetingRemoteModel.getClass();

				Method method = clazz.getMethod("setPowwowMeetingId", long.class);

				method.invoke(_powwowMeetingRemoteModel, powwowMeetingId);
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

		if (_powwowMeetingRemoteModel != null) {
			try {
				Class<?> clazz = _powwowMeetingRemoteModel.getClass();

				Method method = clazz.getMethod("setGroupId", long.class);

				method.invoke(_powwowMeetingRemoteModel, groupId);
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

		if (_powwowMeetingRemoteModel != null) {
			try {
				Class<?> clazz = _powwowMeetingRemoteModel.getClass();

				Method method = clazz.getMethod("setCompanyId", long.class);

				method.invoke(_powwowMeetingRemoteModel, companyId);
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

		if (_powwowMeetingRemoteModel != null) {
			try {
				Class<?> clazz = _powwowMeetingRemoteModel.getClass();

				Method method = clazz.getMethod("setUserId", long.class);

				method.invoke(_powwowMeetingRemoteModel, userId);
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

		if (_powwowMeetingRemoteModel != null) {
			try {
				Class<?> clazz = _powwowMeetingRemoteModel.getClass();

				Method method = clazz.getMethod("setUserName", String.class);

				method.invoke(_powwowMeetingRemoteModel, userName);
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

		if (_powwowMeetingRemoteModel != null) {
			try {
				Class<?> clazz = _powwowMeetingRemoteModel.getClass();

				Method method = clazz.getMethod("setCreateDate", Date.class);

				method.invoke(_powwowMeetingRemoteModel, createDate);
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

		if (_powwowMeetingRemoteModel != null) {
			try {
				Class<?> clazz = _powwowMeetingRemoteModel.getClass();

				Method method = clazz.getMethod("setModifiedDate", Date.class);

				method.invoke(_powwowMeetingRemoteModel, modifiedDate);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public long getPowwowServerId() {
		return _powwowServerId;
	}

	@Override
	public void setPowwowServerId(long powwowServerId) {
		_powwowServerId = powwowServerId;

		if (_powwowMeetingRemoteModel != null) {
			try {
				Class<?> clazz = _powwowMeetingRemoteModel.getClass();

				Method method = clazz.getMethod("setPowwowServerId", long.class);

				method.invoke(_powwowMeetingRemoteModel, powwowServerId);
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

		if (_powwowMeetingRemoteModel != null) {
			try {
				Class<?> clazz = _powwowMeetingRemoteModel.getClass();

				Method method = clazz.getMethod("setName", String.class);

				method.invoke(_powwowMeetingRemoteModel, name);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public String getDescription() {
		return _description;
	}

	@Override
	public void setDescription(String description) {
		_description = description;

		if (_powwowMeetingRemoteModel != null) {
			try {
				Class<?> clazz = _powwowMeetingRemoteModel.getClass();

				Method method = clazz.getMethod("setDescription", String.class);

				method.invoke(_powwowMeetingRemoteModel, description);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public String getProviderType() {
		return _providerType;
	}

	@Override
	public void setProviderType(String providerType) {
		_providerType = providerType;

		if (_powwowMeetingRemoteModel != null) {
			try {
				Class<?> clazz = _powwowMeetingRemoteModel.getClass();

				Method method = clazz.getMethod("setProviderType", String.class);

				method.invoke(_powwowMeetingRemoteModel, providerType);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public String getProviderTypeMetadata() {
		return _providerTypeMetadata;
	}

	@Override
	public void setProviderTypeMetadata(String providerTypeMetadata) {
		_providerTypeMetadata = providerTypeMetadata;

		if (_powwowMeetingRemoteModel != null) {
			try {
				Class<?> clazz = _powwowMeetingRemoteModel.getClass();

				Method method = clazz.getMethod("setProviderTypeMetadata",
						String.class);

				method.invoke(_powwowMeetingRemoteModel, providerTypeMetadata);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public String getLanguageId() {
		return _languageId;
	}

	@Override
	public void setLanguageId(String languageId) {
		_languageId = languageId;

		if (_powwowMeetingRemoteModel != null) {
			try {
				Class<?> clazz = _powwowMeetingRemoteModel.getClass();

				Method method = clazz.getMethod("setLanguageId", String.class);

				method.invoke(_powwowMeetingRemoteModel, languageId);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public long getCalendarBookingId() {
		return _calendarBookingId;
	}

	@Override
	public void setCalendarBookingId(long calendarBookingId) {
		_calendarBookingId = calendarBookingId;

		if (_powwowMeetingRemoteModel != null) {
			try {
				Class<?> clazz = _powwowMeetingRemoteModel.getClass();

				Method method = clazz.getMethod("setCalendarBookingId",
						long.class);

				method.invoke(_powwowMeetingRemoteModel, calendarBookingId);
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

		if (_powwowMeetingRemoteModel != null) {
			try {
				Class<?> clazz = _powwowMeetingRemoteModel.getClass();

				Method method = clazz.getMethod("setStatus", int.class);

				method.invoke(_powwowMeetingRemoteModel, status);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public Map<java.lang.String, Serializable> getProviderTypeMetadataMap() {
		try {
			String methodName = "getProviderTypeMetadataMap";

			Class<?>[] parameterTypes = new Class<?>[] {  };

			Object[] parameterValues = new Object[] {  };

			Map<java.lang.String, Serializable> returnObj = (Map<java.lang.String, Serializable>)invokeOnRemoteModel(methodName,
					parameterTypes, parameterValues);

			return returnObj;
		}
		catch (Exception e) {
			throw new UnsupportedOperationException(e);
		}
	}

	public BaseModel<?> getPowwowMeetingRemoteModel() {
		return _powwowMeetingRemoteModel;
	}

	public void setPowwowMeetingRemoteModel(
		BaseModel<?> powwowMeetingRemoteModel) {
		_powwowMeetingRemoteModel = powwowMeetingRemoteModel;
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

		Class<?> remoteModelClass = _powwowMeetingRemoteModel.getClass();

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

		Object returnValue = method.invoke(_powwowMeetingRemoteModel,
				remoteParameterValues);

		if (returnValue != null) {
			returnValue = ClpSerializer.translateOutput(returnValue);
		}

		return returnValue;
	}

	@Override
	public void persist() {
		if (this.isNew()) {
			PowwowMeetingLocalServiceUtil.addPowwowMeeting(this);
		}
		else {
			PowwowMeetingLocalServiceUtil.updatePowwowMeeting(this);
		}
	}

	@Override
	public PowwowMeeting toEscapedModel() {
		return (PowwowMeeting)ProxyUtil.newProxyInstance(PowwowMeeting.class.getClassLoader(),
			new Class[] { PowwowMeeting.class }, new AutoEscapeBeanHandler(this));
	}

	@Override
	public Object clone() {
		PowwowMeetingClp clone = new PowwowMeetingClp();

		clone.setPowwowMeetingId(getPowwowMeetingId());
		clone.setGroupId(getGroupId());
		clone.setCompanyId(getCompanyId());
		clone.setUserId(getUserId());
		clone.setUserName(getUserName());
		clone.setCreateDate(getCreateDate());
		clone.setModifiedDate(getModifiedDate());
		clone.setPowwowServerId(getPowwowServerId());
		clone.setName(getName());
		clone.setDescription(getDescription());
		clone.setProviderType(getProviderType());
		clone.setProviderTypeMetadata(getProviderTypeMetadata());
		clone.setLanguageId(getLanguageId());
		clone.setCalendarBookingId(getCalendarBookingId());
		clone.setStatus(getStatus());

		return clone;
	}

	@Override
	public int compareTo(PowwowMeeting powwowMeeting) {
		int value = 0;

		value = DateUtil.compareTo(getCreateDate(),
				powwowMeeting.getCreateDate());

		value = value * -1;

		if (value != 0) {
			return value;
		}

		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof PowwowMeetingClp)) {
			return false;
		}

		PowwowMeetingClp powwowMeeting = (PowwowMeetingClp)obj;

		long primaryKey = powwowMeeting.getPrimaryKey();

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
		StringBundler sb = new StringBundler(31);

		sb.append("{powwowMeetingId=");
		sb.append(getPowwowMeetingId());
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
		sb.append(", powwowServerId=");
		sb.append(getPowwowServerId());
		sb.append(", name=");
		sb.append(getName());
		sb.append(", description=");
		sb.append(getDescription());
		sb.append(", providerType=");
		sb.append(getProviderType());
		sb.append(", providerTypeMetadata=");
		sb.append(getProviderTypeMetadata());
		sb.append(", languageId=");
		sb.append(getLanguageId());
		sb.append(", calendarBookingId=");
		sb.append(getCalendarBookingId());
		sb.append(", status=");
		sb.append(getStatus());
		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		StringBundler sb = new StringBundler(49);

		sb.append("<model><model-name>");
		sb.append("com.liferay.powwow.model.PowwowMeeting");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>powwowMeetingId</column-name><column-value><![CDATA[");
		sb.append(getPowwowMeetingId());
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
			"<column><column-name>powwowServerId</column-name><column-value><![CDATA[");
		sb.append(getPowwowServerId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>name</column-name><column-value><![CDATA[");
		sb.append(getName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>description</column-name><column-value><![CDATA[");
		sb.append(getDescription());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>providerType</column-name><column-value><![CDATA[");
		sb.append(getProviderType());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>providerTypeMetadata</column-name><column-value><![CDATA[");
		sb.append(getProviderTypeMetadata());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>languageId</column-name><column-value><![CDATA[");
		sb.append(getLanguageId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>calendarBookingId</column-name><column-value><![CDATA[");
		sb.append(getCalendarBookingId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>status</column-name><column-value><![CDATA[");
		sb.append(getStatus());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _powwowMeetingId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _powwowServerId;
	private String _name;
	private String _description;
	private String _providerType;
	private String _providerTypeMetadata;
	private String _languageId;
	private long _calendarBookingId;
	private int _status;
	private BaseModel<?> _powwowMeetingRemoteModel;
	private Class<?> _clpSerializerClass = ClpSerializer.class;
	private boolean _entityCacheEnabled;
	private boolean _finderCacheEnabled;
}