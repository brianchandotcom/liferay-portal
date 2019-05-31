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

package com.liferay.change.tracking.headless.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.annotation.Generated;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Mate Thurzo
 * @generated
 */
@Generated("")
@GraphQLName("Settings")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "Settings")
public class Settings {

	@Schema(description = "True if change tracking is allowed.")
	public Boolean getChangeTrackingAllowed() {
		return changeTrackingAllowed;
	}

	public void setChangeTrackingAllowed(Boolean changeTrackingAllowed) {
		this.changeTrackingAllowed = changeTrackingAllowed;
	}

	@JsonIgnore
	public void setChangeTrackingAllowed(
		UnsafeSupplier<Boolean, Exception>
			changeTrackingAllowedUnsafeSupplier) {

		try {
			changeTrackingAllowed = changeTrackingAllowedUnsafeSupplier.get();
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
	protected Boolean changeTrackingAllowed;

	@Schema(description = "True if change tracking is enabled.")
	public Boolean getChangeTrackingEnabled() {
		return changeTrackingEnabled;
	}

	public void setChangeTrackingEnabled(Boolean changeTrackingEnabled) {
		this.changeTrackingEnabled = changeTrackingEnabled;
	}

	@JsonIgnore
	public void setChangeTrackingEnabled(
		UnsafeSupplier<Boolean, Exception>
			changeTrackingEnabledUnsafeSupplier) {

		try {
			changeTrackingEnabled = changeTrackingEnabledUnsafeSupplier.get();
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
	protected Boolean changeTrackingEnabled;

	@Schema(description = "True if the checkout configuration is enabled.")
	public Boolean getCheckoutCTCollectionConfirmationEnabled() {
		return checkoutCTCollectionConfirmationEnabled;
	}

	public void setCheckoutCTCollectionConfirmationEnabled(
		Boolean checkoutCTCollectionConfirmationEnabled) {

		this.checkoutCTCollectionConfirmationEnabled =
			checkoutCTCollectionConfirmationEnabled;
	}

	@JsonIgnore
	public void setCheckoutCTCollectionConfirmationEnabled(
		UnsafeSupplier<Boolean, Exception>
			checkoutCTCollectionConfirmationEnabledUnsafeSupplier) {

		try {
			checkoutCTCollectionConfirmationEnabled =
				checkoutCTCollectionConfirmationEnabledUnsafeSupplier.get();
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
	protected Boolean checkoutCTCollectionConfirmationEnabled;

	@Schema(
		description = "The company id of the change tracking configuration."
	)
	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	@JsonIgnore
	public void setCompanyId(
		UnsafeSupplier<Integer, Exception> companyIdUnsafeSupplier) {

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

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Integer companyId;

	@Schema(description = "The language keys of the supported content types'.")
	public String[] getSupportedContentTypeLanguageKeys() {
		return supportedContentTypeLanguageKeys;
	}

	public void setSupportedContentTypeLanguageKeys(
		String[] supportedContentTypeLanguageKeys) {

		this.supportedContentTypeLanguageKeys =
			supportedContentTypeLanguageKeys;
	}

	@JsonIgnore
	public void setSupportedContentTypeLanguageKeys(
		UnsafeSupplier<String[], Exception>
			supportedContentTypeLanguageKeysUnsafeSupplier) {

		try {
			supportedContentTypeLanguageKeys =
				supportedContentTypeLanguageKeysUnsafeSupplier.get();
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
	protected String[] supportedContentTypeLanguageKeys;

	@Schema(description = "The supported content types.")
	public String[] getSupportedContentTypes() {
		return supportedContentTypes;
	}

	public void setSupportedContentTypes(String[] supportedContentTypes) {
		this.supportedContentTypes = supportedContentTypes;
	}

	@JsonIgnore
	public void setSupportedContentTypes(
		UnsafeSupplier<String[], Exception>
			supportedContentTypesUnsafeSupplier) {

		try {
			supportedContentTypes = supportedContentTypesUnsafeSupplier.get();
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
	protected String[] supportedContentTypes;

	@Schema(description = "The user id of the change tracking settings.")
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@JsonIgnore
	public void setUserId(
		UnsafeSupplier<Integer, Exception> userIdUnsafeSupplier) {

		try {
			userId = userIdUnsafeSupplier.get();
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
	protected Integer userId;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Settings)) {
			return false;
		}

		Settings settings = (Settings)object;

		return Objects.equals(toString(), settings.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		if (changeTrackingAllowed != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"changeTrackingAllowed\": ");

			sb.append(changeTrackingAllowed);
		}

		if (changeTrackingEnabled != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"changeTrackingEnabled\": ");

			sb.append(changeTrackingEnabled);
		}

		if (checkoutCTCollectionConfirmationEnabled != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"checkoutCTCollectionConfirmationEnabled\": ");

			sb.append(checkoutCTCollectionConfirmationEnabled);
		}

		if (companyId != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"companyId\": ");

			sb.append(companyId);
		}

		if (supportedContentTypeLanguageKeys != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"supportedContentTypeLanguageKeys\": ");

			sb.append("[");

			for (int i = 0; i < supportedContentTypeLanguageKeys.length; i++) {
				sb.append("\"");

				sb.append(_escape(supportedContentTypeLanguageKeys[i]));

				sb.append("\"");

				if ((i + 1) < supportedContentTypeLanguageKeys.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (supportedContentTypes != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"supportedContentTypes\": ");

			sb.append("[");

			for (int i = 0; i < supportedContentTypes.length; i++) {
				sb.append("\"");

				sb.append(_escape(supportedContentTypes[i]));

				sb.append("\"");

				if ((i + 1) < supportedContentTypes.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (userId != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"userId\": ");

			sb.append(userId);
		}

		sb.append("}");

		return sb.toString();
	}

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		return string.replaceAll("\"", "\\\\\"");
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
			sb.append("\"");
			sb.append(entry.getValue());
			sb.append("\"");

			if (iterator.hasNext()) {
				sb.append(",");
			}
		}

		sb.append("}");

		return sb.toString();
	}

}