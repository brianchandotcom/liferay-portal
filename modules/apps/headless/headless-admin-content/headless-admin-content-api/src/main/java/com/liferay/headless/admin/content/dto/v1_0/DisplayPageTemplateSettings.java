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

package com.liferay.headless.admin.content.dto.v1_0;

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
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@GraphQLName("DisplayPageTemplateSettings")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "DisplayPageTemplateSettings")
public class DisplayPageTemplateSettings implements Serializable {

	public static DisplayPageTemplateSettings toDTO(String json) {
		return ObjectMapperUtil.readValue(
			DisplayPageTemplateSettings.class, json);
	}

	@Schema
	@Valid
	public ContentAssociation getContentAssociation() {
		return contentAssociation;
	}

	public void setContentAssociation(ContentAssociation contentAssociation) {
		this.contentAssociation = contentAssociation;
	}

	@JsonIgnore
	public void setContentAssociation(
		UnsafeSupplier<ContentAssociation, Exception>
			contentAssociationUnsafeSupplier) {

		try {
			contentAssociation = contentAssociationUnsafeSupplier.get();
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
	protected ContentAssociation contentAssociation;

	@Schema
	@Valid
	public OpenGraphSettingsMapping getOpenGraphSettingsMapping() {
		return openGraphSettingsMapping;
	}

	public void setOpenGraphSettingsMapping(
		OpenGraphSettingsMapping openGraphSettingsMapping) {

		this.openGraphSettingsMapping = openGraphSettingsMapping;
	}

	@JsonIgnore
	public void setOpenGraphSettingsMapping(
		UnsafeSupplier<OpenGraphSettingsMapping, Exception>
			openGraphSettingsMappingUnsafeSupplier) {

		try {
			openGraphSettingsMapping =
				openGraphSettingsMappingUnsafeSupplier.get();
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
	protected OpenGraphSettingsMapping openGraphSettingsMapping;

	@Schema
	@Valid
	public SEOSettingsMapping getSeoSettingsMapping() {
		return seoSettingsMapping;
	}

	public void setSeoSettingsMapping(SEOSettingsMapping seoSettingsMapping) {
		this.seoSettingsMapping = seoSettingsMapping;
	}

	@JsonIgnore
	public void setSeoSettingsMapping(
		UnsafeSupplier<SEOSettingsMapping, Exception>
			seoSettingsMappingUnsafeSupplier) {

		try {
			seoSettingsMapping = seoSettingsMappingUnsafeSupplier.get();
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
	protected SEOSettingsMapping seoSettingsMapping;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DisplayPageTemplateSettings)) {
			return false;
		}

		DisplayPageTemplateSettings displayPageTemplateSettings =
			(DisplayPageTemplateSettings)object;

		return Objects.equals(
			toString(), displayPageTemplateSettings.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		if (contentAssociation != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"contentAssociation\": ");

			sb.append(String.valueOf(contentAssociation));
		}

		if (openGraphSettingsMapping != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"openGraphSettingsMapping\": ");

			sb.append(String.valueOf(openGraphSettingsMapping));
		}

		if (seoSettingsMapping != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"seoSettingsMapping\": ");

			sb.append(String.valueOf(seoSettingsMapping));
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		defaultValue = "com.liferay.headless.admin.content.dto.v1_0.DisplayPageTemplateSettings",
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
			sb.append("\":");

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
				sb.append(",");
			}
		}

		sb.append("}");

		return sb.toString();
	}

}