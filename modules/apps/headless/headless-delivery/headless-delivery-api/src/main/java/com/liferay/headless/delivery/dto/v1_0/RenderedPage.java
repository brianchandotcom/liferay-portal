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

package com.liferay.headless.delivery.dto.v1_0;

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

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@GraphQLName("RenderedPage")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "RenderedPage")
public class RenderedPage implements Serializable {

	public static RenderedPage toDTO(String json) {
		return ObjectMapperUtil.readValue(RenderedPage.class, json);
	}

	@Schema
	public String getMasterPageId() {
		return masterPageId;
	}

	public void setMasterPageId(String masterPageId) {
		this.masterPageId = masterPageId;
	}

	@JsonIgnore
	public void setMasterPageId(
		UnsafeSupplier<String, Exception> masterPageIdUnsafeSupplier) {

		try {
			masterPageId = masterPageIdUnsafeSupplier.get();
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
	protected String masterPageId;

	@Schema(
		description = "The name of the master page used to render the content."
	)
	public String getMasterPageName() {
		return masterPageName;
	}

	public void setMasterPageName(String masterPageName) {
		this.masterPageName = masterPageName;
	}

	@JsonIgnore
	public void setMasterPageName(
		UnsafeSupplier<String, Exception> masterPageNameUnsafeSupplier) {

		try {
			masterPageName = masterPageNameUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(
		description = "The name of the master page used to render the content."
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String masterPageName;

	@Schema
	public String getPageTemplateId() {
		return pageTemplateId;
	}

	public void setPageTemplateId(String pageTemplateId) {
		this.pageTemplateId = pageTemplateId;
	}

	@JsonIgnore
	public void setPageTemplateId(
		UnsafeSupplier<String, Exception> pageTemplateIdUnsafeSupplier) {

		try {
			pageTemplateId = pageTemplateIdUnsafeSupplier.get();
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
	protected String pageTemplateId;

	@Schema(
		description = "The name of the template used to render the content."
	)
	public String getPageTemplateName() {
		return pageTemplateName;
	}

	public void setPageTemplateName(String pageTemplateName) {
		this.pageTemplateName = pageTemplateName;
	}

	@JsonIgnore
	public void setPageTemplateName(
		UnsafeSupplier<String, Exception> pageTemplateNameUnsafeSupplier) {

		try {
			pageTemplateName = pageTemplateNameUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(
		description = "The name of the template used to render the content."
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String pageTemplateName;

	@Schema(description = "An absolute URL to the rendered page.")
	public String getRenderedPageURL() {
		return renderedPageURL;
	}

	public void setRenderedPageURL(String renderedPageURL) {
		this.renderedPageURL = renderedPageURL;
	}

	@JsonIgnore
	public void setRenderedPageURL(
		UnsafeSupplier<String, Exception> renderedPageURLUnsafeSupplier) {

		try {
			renderedPageURL = renderedPageURLUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "An absolute URL to the rendered page.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String renderedPageURL;

	@Schema(
		description = "The viewport for which this rendered page has been designed."
	)
	public String getViewPortType() {
		return viewPortType;
	}

	public void setViewPortType(String viewPortType) {
		this.viewPortType = viewPortType;
	}

	@JsonIgnore
	public void setViewPortType(
		UnsafeSupplier<String, Exception> viewPortTypeUnsafeSupplier) {

		try {
			viewPortType = viewPortTypeUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(
		description = "The viewport for which this rendered page has been designed."
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String viewPortType;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof RenderedPage)) {
			return false;
		}

		RenderedPage renderedPage = (RenderedPage)object;

		return Objects.equals(toString(), renderedPage.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		if (masterPageId != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"masterPageId\": ");

			sb.append("\"");

			sb.append(_escape(masterPageId));

			sb.append("\"");
		}

		if (masterPageName != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"masterPageName\": ");

			sb.append("\"");

			sb.append(_escape(masterPageName));

			sb.append("\"");
		}

		if (pageTemplateId != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"pageTemplateId\": ");

			sb.append("\"");

			sb.append(_escape(pageTemplateId));

			sb.append("\"");
		}

		if (pageTemplateName != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"pageTemplateName\": ");

			sb.append("\"");

			sb.append(_escape(pageTemplateName));

			sb.append("\"");
		}

		if (renderedPageURL != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"renderedPageURL\": ");

			sb.append("\"");

			sb.append(_escape(renderedPageURL));

			sb.append("\"");
		}

		if (viewPortType != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"viewPortType\": ");

			sb.append("\"");

			sb.append(_escape(viewPortType));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		defaultValue = "com.liferay.headless.delivery.dto.v1_0.RenderedPage",
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