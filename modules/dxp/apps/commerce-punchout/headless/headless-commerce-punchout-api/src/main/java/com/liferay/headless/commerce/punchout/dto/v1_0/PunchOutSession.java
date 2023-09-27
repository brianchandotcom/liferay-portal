/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.punchout.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
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
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Jaclyn Ong
 * @generated
 */
@Generated("")
@GraphQLName("PunchOutSession")
@JsonFilter("Liferay.Vulcan")
@Schema(
	requiredProperties = {
		"buyerAccountReferenceCode", "buyerGroup", "buyerUser", "cart",
		"punchOutReturnURL", "punchOutSessionType"
	}
)
@XmlRootElement(name = "PunchOutSession")
public class PunchOutSession implements Serializable {

	public static PunchOutSession toDTO(String json) {
		return ObjectMapperUtil.readValue(PunchOutSession.class, json);
	}

	public static PunchOutSession unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(PunchOutSession.class, json);
	}

	@Schema
	public String getBuyerAccountReferenceCode() {
		return buyerAccountReferenceCode;
	}

	public void setBuyerAccountReferenceCode(String buyerAccountReferenceCode) {
		this.buyerAccountReferenceCode = buyerAccountReferenceCode;
	}

	@JsonIgnore
	public void setBuyerAccountReferenceCode(
		UnsafeSupplier<String, Exception>
			buyerAccountReferenceCodeUnsafeSupplier) {

		try {
			buyerAccountReferenceCode =
				buyerAccountReferenceCodeUnsafeSupplier.get();
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
	@NotEmpty
	protected String buyerAccountReferenceCode;

	@Schema
	@Valid
	public Group getBuyerGroup() {
		return buyerGroup;
	}

	public void setBuyerGroup(Group buyerGroup) {
		this.buyerGroup = buyerGroup;
	}

	@JsonIgnore
	public void setBuyerGroup(
		UnsafeSupplier<Group, Exception> buyerGroupUnsafeSupplier) {

		try {
			buyerGroup = buyerGroupUnsafeSupplier.get();
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
	@NotNull
	protected Group buyerGroup;

	@Schema
	@Valid
	public Organization getBuyerOrganization() {
		return buyerOrganization;
	}

	public void setBuyerOrganization(Organization buyerOrganization) {
		this.buyerOrganization = buyerOrganization;
	}

	@JsonIgnore
	public void setBuyerOrganization(
		UnsafeSupplier<Organization, Exception>
			buyerOrganizationUnsafeSupplier) {

		try {
			buyerOrganization = buyerOrganizationUnsafeSupplier.get();
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
	protected Organization buyerOrganization;

	@Schema
	@Valid
	public User getBuyerUser() {
		return buyerUser;
	}

	public void setBuyerUser(User buyerUser) {
		this.buyerUser = buyerUser;
	}

	@JsonIgnore
	public void setBuyerUser(
		UnsafeSupplier<User, Exception> buyerUserUnsafeSupplier) {

		try {
			buyerUser = buyerUserUnsafeSupplier.get();
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
	@NotNull
	protected User buyerUser;

	@Schema
	@Valid
	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	@JsonIgnore
	public void setCart(UnsafeSupplier<Cart, Exception> cartUnsafeSupplier) {
		try {
			cart = cartUnsafeSupplier.get();
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
	@NotNull
	protected Cart cart;

	@Schema
	public String getPunchOutReturnURL() {
		return punchOutReturnURL;
	}

	public void setPunchOutReturnURL(String punchOutReturnURL) {
		this.punchOutReturnURL = punchOutReturnURL;
	}

	@JsonIgnore
	public void setPunchOutReturnURL(
		UnsafeSupplier<String, Exception> punchOutReturnURLUnsafeSupplier) {

		try {
			punchOutReturnURL = punchOutReturnURLUnsafeSupplier.get();
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
	@NotEmpty
	protected String punchOutReturnURL;

	@Schema
	public String getPunchOutSessionType() {
		return punchOutSessionType;
	}

	public void setPunchOutSessionType(String punchOutSessionType) {
		this.punchOutSessionType = punchOutSessionType;
	}

	@JsonIgnore
	public void setPunchOutSessionType(
		UnsafeSupplier<String, Exception> punchOutSessionTypeUnsafeSupplier) {

		try {
			punchOutSessionType = punchOutSessionTypeUnsafeSupplier.get();
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
	@NotEmpty
	protected String punchOutSessionType;

	@Schema
	public String getPunchOutStartURL() {
		return punchOutStartURL;
	}

	public void setPunchOutStartURL(String punchOutStartURL) {
		this.punchOutStartURL = punchOutStartURL;
	}

	@JsonIgnore
	public void setPunchOutStartURL(
		UnsafeSupplier<String, Exception> punchOutStartURLUnsafeSupplier) {

		try {
			punchOutStartURL = punchOutStartURLUnsafeSupplier.get();
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
	protected String punchOutStartURL;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof PunchOutSession)) {
			return false;
		}

		PunchOutSession punchOutSession = (PunchOutSession)object;

		return Objects.equals(toString(), punchOutSession.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		if (buyerAccountReferenceCode != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"buyerAccountReferenceCode\": ");

			sb.append("\"");

			sb.append(_escape(buyerAccountReferenceCode));

			sb.append("\"");
		}

		if (buyerGroup != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"buyerGroup\": ");

			sb.append(String.valueOf(buyerGroup));
		}

		if (buyerOrganization != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"buyerOrganization\": ");

			sb.append(String.valueOf(buyerOrganization));
		}

		if (buyerUser != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"buyerUser\": ");

			sb.append(String.valueOf(buyerUser));
		}

		if (cart != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"cart\": ");

			sb.append(String.valueOf(cart));
		}

		if (punchOutReturnURL != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"punchOutReturnURL\": ");

			sb.append("\"");

			sb.append(_escape(punchOutReturnURL));

			sb.append("\"");
		}

		if (punchOutSessionType != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"punchOutSessionType\": ");

			sb.append("\"");

			sb.append(_escape(punchOutSessionType));

			sb.append("\"");
		}

		if (punchOutStartURL != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"punchOutStartURL\": ");

			sb.append("\"");

			sb.append(_escape(punchOutStartURL));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		accessMode = Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.headless.commerce.punchout.dto.v1_0.PunchOutSession",
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