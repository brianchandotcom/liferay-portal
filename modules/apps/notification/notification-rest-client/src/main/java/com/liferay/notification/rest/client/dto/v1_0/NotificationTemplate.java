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

package com.liferay.notification.rest.client.dto.v1_0;

import com.liferay.notification.rest.client.function.UnsafeSupplier;
import com.liferay.notification.rest.client.serdes.v1_0.NotificationTemplateSerDes;

import java.io.Serializable;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Gabriel Albuquerque
 * @generated
 */
@Generated("")
public class NotificationTemplate implements Cloneable, Serializable {

	public static NotificationTemplate toDTO(String json) {
		return NotificationTemplateSerDes.toDTO(json);
	}

	public Map<String, Map<String, String>> getActions() {
		return actions;
	}

	public void setActions(Map<String, Map<String, String>> actions) {
		this.actions = actions;
	}

	public void setActions(
		UnsafeSupplier<Map<String, Map<String, String>>, Exception>
			actionsUnsafeSupplier) {

		try {
			actions = actionsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Map<String, Map<String, String>> actions;

	public Long[] getAttachmentObjectFieldIds() {
		return attachmentObjectFieldIds;
	}

	public void setAttachmentObjectFieldIds(Long[] attachmentObjectFieldIds) {
		this.attachmentObjectFieldIds = attachmentObjectFieldIds;
	}

	public void setAttachmentObjectFieldIds(
		UnsafeSupplier<Long[], Exception>
			attachmentObjectFieldIdsUnsafeSupplier) {

		try {
			attachmentObjectFieldIds =
				attachmentObjectFieldIdsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long[] attachmentObjectFieldIds;

	public String getBcc() {
		return bcc;
	}

	public void setBcc(String bcc) {
		this.bcc = bcc;
	}

	public void setBcc(UnsafeSupplier<String, Exception> bccUnsafeSupplier) {
		try {
			bcc = bccUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String bcc;

	public Map<String, String> getBody() {
		return body;
	}

	public void setBody(Map<String, String> body) {
		this.body = body;
	}

	public void setBody(
		UnsafeSupplier<Map<String, String>, Exception> bodyUnsafeSupplier) {

		try {
			body = bodyUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Map<String, String> body;

	public String getCc() {
		return cc;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	public void setCc(UnsafeSupplier<String, Exception> ccUnsafeSupplier) {
		try {
			cc = ccUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String cc;

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public void setDateCreated(
		UnsafeSupplier<Date, Exception> dateCreatedUnsafeSupplier) {

		try {
			dateCreated = dateCreatedUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Date dateCreated;

	public Date getDateModified() {
		return dateModified;
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}

	public void setDateModified(
		UnsafeSupplier<Date, Exception> dateModifiedUnsafeSupplier) {

		try {
			dateModified = dateModifiedUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Date dateModified;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDescription(
		UnsafeSupplier<String, Exception> descriptionUnsafeSupplier) {

		try {
			description = descriptionUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String description;

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public void setFrom(UnsafeSupplier<String, Exception> fromUnsafeSupplier) {
		try {
			from = fromUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String from;

	public Map<String, String> getFromName() {
		return fromName;
	}

	public void setFromName(Map<String, String> fromName) {
		this.fromName = fromName;
	}

	public void setFromName(
		UnsafeSupplier<Map<String, String>, Exception> fromNameUnsafeSupplier) {

		try {
			fromName = fromNameUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Map<String, String> fromName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setId(UnsafeSupplier<Long, Exception> idUnsafeSupplier) {
		try {
			id = idUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long id;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setName(UnsafeSupplier<String, Exception> nameUnsafeSupplier) {
		try {
			name = nameUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String name;

	public Map<String, String> getName_i18n() {
		return name_i18n;
	}

	public void setName_i18n(Map<String, String> name_i18n) {
		this.name_i18n = name_i18n;
	}

	public void setName_i18n(
		UnsafeSupplier<Map<String, String>, Exception>
			name_i18nUnsafeSupplier) {

		try {
			name_i18n = name_i18nUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Map<String, String> name_i18n;

	public Long getObjectDefinitionId() {
		return objectDefinitionId;
	}

	public void setObjectDefinitionId(Long objectDefinitionId) {
		this.objectDefinitionId = objectDefinitionId;
	}

	public void setObjectDefinitionId(
		UnsafeSupplier<Long, Exception> objectDefinitionIdUnsafeSupplier) {

		try {
			objectDefinitionId = objectDefinitionIdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long objectDefinitionId;

	public RecipientType getRecipientType() {
		return recipientType;
	}

	public String getRecipientTypeAsString() {
		if (recipientType == null) {
			return null;
		}

		return recipientType.toString();
	}

	public void setRecipientType(RecipientType recipientType) {
		this.recipientType = recipientType;
	}

	public void setRecipientType(
		UnsafeSupplier<RecipientType, Exception> recipientTypeUnsafeSupplier) {

		try {
			recipientType = recipientTypeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected RecipientType recipientType;

	public Map<String, String> getSubject() {
		return subject;
	}

	public void setSubject(Map<String, String> subject) {
		this.subject = subject;
	}

	public void setSubject(
		UnsafeSupplier<Map<String, String>, Exception> subjectUnsafeSupplier) {

		try {
			subject = subjectUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Map<String, String> subject;

	public Map<String, String> getTo() {
		return to;
	}

	public void setTo(Map<String, String> to) {
		this.to = to;
	}

	public void setTo(
		UnsafeSupplier<Map<String, String>, Exception> toUnsafeSupplier) {

		try {
			to = toUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Map<String, String> to;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setType(UnsafeSupplier<String, Exception> typeUnsafeSupplier) {
		try {
			type = typeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String type;

	@Override
	public NotificationTemplate clone() throws CloneNotSupportedException {
		return (NotificationTemplate)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof NotificationTemplate)) {
			return false;
		}

		NotificationTemplate notificationTemplate =
			(NotificationTemplate)object;

		return Objects.equals(toString(), notificationTemplate.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return NotificationTemplateSerDes.toJSON(this);
	}

	public static enum RecipientType {

		ROLE("role"), TERM("term"), USER("user");

		public static RecipientType create(String value) {
			for (RecipientType recipientType : values()) {
				if (Objects.equals(recipientType.getValue(), value) ||
					Objects.equals(recipientType.name(), value)) {

					return recipientType;
				}
			}

			return null;
		}

		public String getValue() {
			return _value;
		}

		@Override
		public String toString() {
			return _value;
		}

		private RecipientType(String value) {
			_value = value;
		}

		private final String _value;

	}

}