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

package com.liferay.headless.portal.instances.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.util.ObjectMapperUtil;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.annotation.Generated;

import javax.validation.Valid;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Alberto Chaparro
 * @generated
 */
@Generated("")
@GraphQLName("PortalInstance")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "PortalInstance")
public class PortalInstance implements Serializable {

	public static PortalInstance toDTO(String json) {
		return ObjectMapperUtil.readValue(PortalInstance.class, json);
	}

	@Schema
	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	@JsonIgnore
	public void setActive(
		UnsafeSupplier<Boolean, Exception> activeUnsafeSupplier) {

		try {
			active = activeUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Boolean active;

	@Schema(description = "internal unique key.")
	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	@JsonIgnore
	public void setCompanyId(
		UnsafeSupplier<Long, Exception> companyIdUnsafeSupplier) {

		try {
			companyId = companyIdUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "internal unique key.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Long companyId;

	@Schema(description = "domain used for email authentication.")
	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	@JsonIgnore
	public void setDomain(
		UnsafeSupplier<String, Exception> domainUnsafeSupplier) {

		try {
			domain = domainUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "domain used for email authentication.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String domain;

	@Schema(
		description = "public unique key (corresponds to company's webId field)"
	)
	public String getPortalInstanceId() {
		return portalInstanceId;
	}

	public void setPortalInstanceId(String portalInstanceId) {
		this.portalInstanceId = portalInstanceId;
	}

	@JsonIgnore
	public void setPortalInstanceId(
		UnsafeSupplier<String, Exception> portalInstanceIdUnsafeSupplier) {

		try {
			portalInstanceId = portalInstanceIdUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(
		description = "public unique key (corresponds to company's webId field)"
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String portalInstanceId;

	@Schema(
		description = "optional portal initializer key for an portal instance initialization."
	)
	public String getPortalInstanceInitializerKey() {
		return portalInstanceInitializerKey;
	}

	public void setPortalInstanceInitializerKey(
		String portalInstanceInitializerKey) {

		this.portalInstanceInitializerKey = portalInstanceInitializerKey;
	}

	@JsonIgnore
	public void setPortalInstanceInitializerKey(
		UnsafeSupplier<String, Exception>
			portalInstanceInitializerKeyUnsafeSupplier) {

		try {
			portalInstanceInitializerKey =
				portalInstanceInitializerKeyUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(
		description = "optional portal initializer key for an portal instance initialization."
	)
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	protected String portalInstanceInitializerKey;

	@Schema(
		description = "optional portal initializer payload(contains required data for a portal instance initializer)."
	)
	@Valid
	public Map<String, String> getPortalInstanceInitializerPayload() {
		return portalInstanceInitializerPayload;
	}

	public void setPortalInstanceInitializerPayload(
		Map<String, String> portalInstanceInitializerPayload) {

		this.portalInstanceInitializerPayload =
			portalInstanceInitializerPayload;
	}

	@JsonIgnore
	public void setPortalInstanceInitializerPayload(
		UnsafeSupplier<Map<String, String>, Exception>
			portalInstanceInitializerPayloadUnsafeSupplier) {

		try {
			portalInstanceInitializerPayload =
				portalInstanceInitializerPayloadUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(
		description = "optional portal initializer payload(contains required data for a portal instance initializer)."
	)
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	protected Map<String, String> portalInstanceInitializerPayload;

	@Schema
	public String getVirtualHost() {
		return virtualHost;
	}

	public void setVirtualHost(String virtualHost) {
		this.virtualHost = virtualHost;
	}

	@JsonIgnore
	public void setVirtualHost(
		UnsafeSupplier<String, Exception> virtualHostUnsafeSupplier) {

		try {
			virtualHost = virtualHostUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String virtualHost;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof PortalInstance)) {
			return false;
		}

		PortalInstance portalInstance = (PortalInstance)object;

		return Objects.equals(toString(), portalInstance.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		if (active != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"active\": ");

			sb.append(active);
		}

		if (companyId != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"companyId\": ");

			sb.append(companyId);
		}

		if (domain != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"domain\": ");

			sb.append("\"");

			sb.append(_escape(domain));

			sb.append("\"");
		}

		if (portalInstanceId != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"portalInstanceId\": ");

			sb.append("\"");

			sb.append(_escape(portalInstanceId));

			sb.append("\"");
		}

		if (portalInstanceInitializerKey != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"portalInstanceInitializerKey\": ");

			sb.append("\"");

			sb.append(_escape(portalInstanceInitializerKey));

			sb.append("\"");
		}

		if (portalInstanceInitializerPayload != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"portalInstanceInitializerPayload\": ");

			sb.append(_toJSON(portalInstanceInitializerPayload));
		}

		if (virtualHost != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"virtualHost\": ");

			sb.append("\"");

			sb.append(_escape(virtualHost));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		accessMode = Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.headless.portal.instances.dto.v1_0.PortalInstance",
		name = "x-class-name"
	)
	public String xClassName;

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		return string.replaceAll("\"", "\\\\\"");
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
			sb.append(entry.getKey());
			sb.append("\": ");

			Object value = entry.getValue();

			if (_isArray(value)) {
				sb.append("[");

				Object[] valueArray = (Object[])value;

				for (int i = 0; i < valueArray.length; i++) {
					if (valueArray[i] instanceof String) {
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
				sb.append(value);
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

}