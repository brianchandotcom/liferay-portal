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

package com.liferay.notification.type;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

/**
 * @author Feliphe Marinho
 */
public class NotificationContext {

	public List<Long> getAttachmentObjectFieldIds() {
		return _attachmentObjectFieldIds;
	}

	public Map<String, Serializable> getAttributes() {
		return _attributes;
	}

	public Serializable getAttributeValue(String name) {
		return _attributes.get(name);
	}

	public String getClassName() {
		return _className;
	}

	public long getClassPK() {
		return _classPK;
	}

	public long getNotificationTemplateId() {
		return _notificationTemplateId;
	}

	public String getNotificationTemplateName() {
		return _notificationTemplateName;
	}

	public long getObjectDefinitionId() {
		return _objectDefinitionId;
	}

	public Map<String, Object> getTermValues() {
		return _termValues;
	}

	public long getUserId() {
		return _userId;
	}

	public void setAttachmentObjectFieldIds(
		List<Long> attachmentObjectFieldIds) {

		_attachmentObjectFieldIds = attachmentObjectFieldIds;
	}

	public void setAttributes(Map<String, Serializable> attributes) {
		_attributes = attributes;
	}

	public void setClassName(String className) {
		_className = className;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	public void setNotificationTemplateId(long notificationTemplateId) {
		_notificationTemplateId = notificationTemplateId;
	}

	public void setNotificationTemplateName(String notificationTemplateName) {
		_notificationTemplateName = notificationTemplateName;
	}

	public void setObjectDefinitionId(long objectDefinitionId) {
		_objectDefinitionId = objectDefinitionId;
	}

	public void setTermValues(Map<String, Object> termValues) {
		_termValues = termValues;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	private List<Long> _attachmentObjectFieldIds;
	private Map<String, Serializable> _attributes;
	private String _className;
	private long _classPK;
	private long _notificationTemplateId;
	private String _notificationTemplateName;
	private long _objectDefinitionId;
	private Map<String, Object> _termValues;
	private long _userId;

}