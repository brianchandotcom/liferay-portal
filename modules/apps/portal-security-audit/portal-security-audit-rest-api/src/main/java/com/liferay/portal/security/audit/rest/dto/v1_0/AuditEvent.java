/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.audit.rest.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.headless.delivery.dto.v1_0.Creator;
import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.util.ObjectMapperUtil;

import jakarta.annotation.Generated;

import jakarta.validation.Valid;

import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

/**
 * @author Rafael Praxedes
 * @generated
 */
@Generated("")
@GraphQLName("AuditEvent")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "AuditEvent")
public class AuditEvent implements Serializable {

	public static AuditEvent toDTO(String json) {
		return ObjectMapperUtil.readValue(AuditEvent.class, json);
	}

	public static AuditEvent unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(AuditEvent.class, json);
	}

	@io.swagger.v3.oas.annotations.media.Schema
	public Long getAccountId() {
		if (_accountIdSupplier != null) {
			accountId = _accountIdSupplier.get();

			_accountIdSupplier = null;
		}

		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;

		_accountIdSupplier = null;
	}

	@JsonIgnore
	public void setAccountId(
		UnsafeSupplier<Long, Exception> accountIdUnsafeSupplier) {

		_accountIdSupplier = () -> {
			try {
				return accountIdUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Long accountId;

	@JsonIgnore
	private Supplier<Long> _accountIdSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	@Valid
	public Map<String, ?> getAdditionalInfo() {
		if (_additionalInfoSupplier != null) {
			additionalInfo = _additionalInfoSupplier.get();

			_additionalInfoSupplier = null;
		}

		return additionalInfo;
	}

	public void setAdditionalInfo(Map<String, ?> additionalInfo) {
		this.additionalInfo = additionalInfo;

		_additionalInfoSupplier = null;
	}

	@JsonIgnore
	public void setAdditionalInfo(
		UnsafeSupplier<Map<String, ?>, Exception>
			additionalInfoUnsafeSupplier) {

		_additionalInfoSupplier = () -> {
			try {
				return additionalInfoUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Map<String, ?> additionalInfo;

	@JsonIgnore
	private Supplier<Map<String, ?>> _additionalInfoSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	public String getClientHost() {
		if (_clientHostSupplier != null) {
			clientHost = _clientHostSupplier.get();

			_clientHostSupplier = null;
		}

		return clientHost;
	}

	public void setClientHost(String clientHost) {
		this.clientHost = clientHost;

		_clientHostSupplier = null;
	}

	@JsonIgnore
	public void setClientHost(
		UnsafeSupplier<String, Exception> clientHostUnsafeSupplier) {

		_clientHostSupplier = () -> {
			try {
				return clientHostUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String clientHost;

	@JsonIgnore
	private Supplier<String> _clientHostSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	public String getClientIP() {
		if (_clientIPSupplier != null) {
			clientIP = _clientIPSupplier.get();

			_clientIPSupplier = null;
		}

		return clientIP;
	}

	public void setClientIP(String clientIP) {
		this.clientIP = clientIP;

		_clientIPSupplier = null;
	}

	@JsonIgnore
	public void setClientIP(
		UnsafeSupplier<String, Exception> clientIPUnsafeSupplier) {

		_clientIPSupplier = () -> {
			try {
				return clientIPUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String clientIP;

	@JsonIgnore
	private Supplier<String> _clientIPSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	public String getContextName() {
		if (_contextNameSupplier != null) {
			contextName = _contextNameSupplier.get();

			_contextNameSupplier = null;
		}

		return contextName;
	}

	public void setContextName(String contextName) {
		this.contextName = contextName;

		_contextNameSupplier = null;
	}

	@JsonIgnore
	public void setContextName(
		UnsafeSupplier<String, Exception> contextNameUnsafeSupplier) {

		_contextNameSupplier = () -> {
			try {
				return contextNameUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String contextName;

	@JsonIgnore
	private Supplier<String> _contextNameSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	@Valid
	public Creator getCreator() {
		if (_creatorSupplier != null) {
			creator = _creatorSupplier.get();

			_creatorSupplier = null;
		}

		return creator;
	}

	public void setCreator(Creator creator) {
		this.creator = creator;

		_creatorSupplier = null;
	}

	@JsonIgnore
	public void setCreator(
		UnsafeSupplier<Creator, Exception> creatorUnsafeSupplier) {

		_creatorSupplier = () -> {
			try {
				return creatorUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Creator creator;

	@JsonIgnore
	private Supplier<Creator> _creatorSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	public Date getDateCreated() {
		if (_dateCreatedSupplier != null) {
			dateCreated = _dateCreatedSupplier.get();

			_dateCreatedSupplier = null;
		}

		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;

		_dateCreatedSupplier = null;
	}

	@JsonIgnore
	public void setDateCreated(
		UnsafeSupplier<Date, Exception> dateCreatedUnsafeSupplier) {

		_dateCreatedSupplier = () -> {
			try {
				return dateCreatedUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Date dateCreated;

	@JsonIgnore
	private Supplier<Date> _dateCreatedSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	public Long getEntityId() {
		if (_entityIdSupplier != null) {
			entityId = _entityIdSupplier.get();

			_entityIdSupplier = null;
		}

		return entityId;
	}

	public void setEntityId(Long entityId) {
		this.entityId = entityId;

		_entityIdSupplier = null;
	}

	@JsonIgnore
	public void setEntityId(
		UnsafeSupplier<Long, Exception> entityIdUnsafeSupplier) {

		_entityIdSupplier = () -> {
			try {
				return entityIdUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Long entityId;

	@JsonIgnore
	private Supplier<Long> _entityIdSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	public String getEntityType() {
		if (_entityTypeSupplier != null) {
			entityType = _entityTypeSupplier.get();

			_entityTypeSupplier = null;
		}

		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;

		_entityTypeSupplier = null;
	}

	@JsonIgnore
	public void setEntityType(
		UnsafeSupplier<String, Exception> entityTypeUnsafeSupplier) {

		_entityTypeSupplier = () -> {
			try {
				return entityTypeUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String entityType;

	@JsonIgnore
	private Supplier<String> _entityTypeSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	public String getEventType() {
		if (_eventTypeSupplier != null) {
			eventType = _eventTypeSupplier.get();

			_eventTypeSupplier = null;
		}

		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;

		_eventTypeSupplier = null;
	}

	@JsonIgnore
	public void setEventType(
		UnsafeSupplier<String, Exception> eventTypeUnsafeSupplier) {

		_eventTypeSupplier = () -> {
			try {
				return eventTypeUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String eventType;

	@JsonIgnore
	private Supplier<String> _eventTypeSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	public Long getGroupId() {
		if (_groupIdSupplier != null) {
			groupId = _groupIdSupplier.get();

			_groupIdSupplier = null;
		}

		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;

		_groupIdSupplier = null;
	}

	@JsonIgnore
	public void setGroupId(
		UnsafeSupplier<Long, Exception> groupIdUnsafeSupplier) {

		_groupIdSupplier = () -> {
			try {
				return groupIdUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Long groupId;

	@JsonIgnore
	private Supplier<Long> _groupIdSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	public Long getId() {
		if (_idSupplier != null) {
			id = _idSupplier.get();

			_idSupplier = null;
		}

		return id;
	}

	public void setId(Long id) {
		this.id = id;

		_idSupplier = null;
	}

	@JsonIgnore
	public void setId(UnsafeSupplier<Long, Exception> idUnsafeSupplier) {
		_idSupplier = () -> {
			try {
				return idUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Long id;

	@JsonIgnore
	private Supplier<Long> _idSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	public String getServerName() {
		if (_serverNameSupplier != null) {
			serverName = _serverNameSupplier.get();

			_serverNameSupplier = null;
		}

		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;

		_serverNameSupplier = null;
	}

	@JsonIgnore
	public void setServerName(
		UnsafeSupplier<String, Exception> serverNameUnsafeSupplier) {

		_serverNameSupplier = () -> {
			try {
				return serverNameUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String serverName;

	@JsonIgnore
	private Supplier<String> _serverNameSupplier;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof AuditEvent)) {
			return false;
		}

		AuditEvent auditEvent = (AuditEvent)object;

		return Objects.equals(toString(), auditEvent.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		Long accountId = getAccountId();

		if (accountId != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"accountId\": ");

			sb.append(accountId);
		}

		Map<String, ?> additionalInfo = getAdditionalInfo();

		if (additionalInfo != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"additionalInfo\": ");

			sb.append(_toJSON(additionalInfo));
		}

		String clientHost = getClientHost();

		if (clientHost != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"clientHost\": ");

			sb.append("\"");

			sb.append(_escape(clientHost));

			sb.append("\"");
		}

		String clientIP = getClientIP();

		if (clientIP != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"clientIP\": ");

			sb.append("\"");

			sb.append(_escape(clientIP));

			sb.append("\"");
		}

		String contextName = getContextName();

		if (contextName != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"contextName\": ");

			sb.append("\"");

			sb.append(_escape(contextName));

			sb.append("\"");
		}

		Creator creator = getCreator();

		if (creator != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"creator\": ");

			sb.append(creator);
		}

		Date dateCreated = getDateCreated();

		if (dateCreated != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateCreated\": ");

			sb.append("\"");

			sb.append(liferayToJSONDateFormat.format(dateCreated));

			sb.append("\"");
		}

		Long entityId = getEntityId();

		if (entityId != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"entityId\": ");

			sb.append(entityId);
		}

		String entityType = getEntityType();

		if (entityType != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"entityType\": ");

			sb.append("\"");

			sb.append(_escape(entityType));

			sb.append("\"");
		}

		String eventType = getEventType();

		if (eventType != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"eventType\": ");

			sb.append("\"");

			sb.append(_escape(eventType));

			sb.append("\"");
		}

		Long groupId = getGroupId();

		if (groupId != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"groupId\": ");

			sb.append(groupId);
		}

		Long id = getId();

		if (id != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(id);
		}

		String serverName = getServerName();

		if (serverName != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"serverName\": ");

			sb.append("\"");

			sb.append(_escape(serverName));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	@io.swagger.v3.oas.annotations.media.Schema(
		accessMode = io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.portal.security.audit.rest.dto.v1_0.AuditEvent",
		name = "x-class-name"
	)
	public String xClassName;

	private static String _escape(Object object) {
		return StringUtil.replace(
			String.valueOf(object), _JSON_ESCAPE_STRINGS[0],
			_JSON_ESCAPE_STRINGS[1]);
	}

	private static boolean _isArray(Object value) {
		if (value == null) {
			return false;
		}

		Class<?> clazz = value.getClass();

		return clazz.isArray();
	}

	private static String _toJSON(Map<String, ?> map) {
		StringBuilder sb = new StringBuilder("{");

		@SuppressWarnings("unchecked")
		Set set = map.entrySet();

		@SuppressWarnings("unchecked")
		Iterator<Map.Entry<String, ?>> iterator = set.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, ?> entry = iterator.next();

			sb.append("\"");
			sb.append(_escape(entry.getKey()));
			sb.append("\": ");

			Object value = entry.getValue();

			if (_isArray(value)) {
				sb.append("[");

				Object[] valueArray = (Object[])value;

				for (int i = 0; i < valueArray.length; i++) {
					if (valueArray[i] instanceof Map) {
						sb.append(_toJSON((Map<String, ?>)valueArray[i]));
					}
					else if (valueArray[i] instanceof String) {
						sb.append("\"");
						sb.append(valueArray[i]);
						sb.append("\"");
					}
					else {
						sb.append(valueArray[i]);
					}

					if ((i + 1) < valueArray.length) {
						sb.append(", ");
					}
				}

				sb.append("]");
			}
			else if (value instanceof Map) {
				sb.append(_toJSON((Map<String, ?>)value));
			}
			else if (value instanceof String) {
				sb.append("\"");
				sb.append(_escape(value));
				sb.append("\"");
			}
			else {
				sb.append(value);
			}

			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
	}

	private static final String[][] _JSON_ESCAPE_STRINGS = {
		{"\\", "\"", "\b", "\f", "\n", "\r", "\t"},
		{"\\\\", "\\\"", "\\b", "\\f", "\\n", "\\r", "\\t"}
	};

	private Map<String, Serializable> _extendedProperties;

}
// LIFERAY-REST-BUILDER-HASH:-547273746