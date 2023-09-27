/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
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

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@GraphQLName("TaxonomyCategoryBrief")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "TaxonomyCategoryBrief")
public class TaxonomyCategoryBrief implements Serializable {

	public static TaxonomyCategoryBrief toDTO(String json) {
		return ObjectMapperUtil.readValue(TaxonomyCategoryBrief.class, json);
	}

	public static TaxonomyCategoryBrief unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(
			TaxonomyCategoryBrief.class, json);
	}

	@Schema(
		description = "Optional field with the embedded taxonomy category, can be embedded with nestedFields"
	)
	@Valid
	public Object getEmbeddedTaxonomyCategory() {
		return embeddedTaxonomyCategory;
	}

	public void setEmbeddedTaxonomyCategory(Object embeddedTaxonomyCategory) {
		this.embeddedTaxonomyCategory = embeddedTaxonomyCategory;
	}

	@JsonIgnore
	public void setEmbeddedTaxonomyCategory(
		UnsafeSupplier<Object, Exception>
			embeddedTaxonomyCategoryUnsafeSupplier) {

		try {
			embeddedTaxonomyCategory =
				embeddedTaxonomyCategoryUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(
		description = "Optional field with the embedded taxonomy category, can be embedded with nestedFields"
	)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Object embeddedTaxonomyCategory;

	@Schema(
		description = "The category's ID. This can be used to retrieve more information in the `TaxonomyCategory` API."
	)
	public Long getTaxonomyCategoryId() {
		return taxonomyCategoryId;
	}

	public void setTaxonomyCategoryId(Long taxonomyCategoryId) {
		this.taxonomyCategoryId = taxonomyCategoryId;
	}

	@JsonIgnore
	public void setTaxonomyCategoryId(
		UnsafeSupplier<Long, Exception> taxonomyCategoryIdUnsafeSupplier) {

		try {
			taxonomyCategoryId = taxonomyCategoryIdUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(
		description = "The category's ID. This can be used to retrieve more information in the `TaxonomyCategory` API."
	)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Long taxonomyCategoryId;

	@Schema(description = "The category's name.")
	public String getTaxonomyCategoryName() {
		return taxonomyCategoryName;
	}

	public void setTaxonomyCategoryName(String taxonomyCategoryName) {
		this.taxonomyCategoryName = taxonomyCategoryName;
	}

	@JsonIgnore
	public void setTaxonomyCategoryName(
		UnsafeSupplier<String, Exception> taxonomyCategoryNameUnsafeSupplier) {

		try {
			taxonomyCategoryName = taxonomyCategoryNameUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "The category's name.")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String taxonomyCategoryName;

	@Schema(description = "The localized category's names.")
	@Valid
	public Map<String, String> getTaxonomyCategoryName_i18n() {
		return taxonomyCategoryName_i18n;
	}

	public void setTaxonomyCategoryName_i18n(
		Map<String, String> taxonomyCategoryName_i18n) {

		this.taxonomyCategoryName_i18n = taxonomyCategoryName_i18n;
	}

	@JsonIgnore
	public void setTaxonomyCategoryName_i18n(
		UnsafeSupplier<Map<String, String>, Exception>
			taxonomyCategoryName_i18nUnsafeSupplier) {

		try {
			taxonomyCategoryName_i18n =
				taxonomyCategoryName_i18nUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "The localized category's names.")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Map<String, String> taxonomyCategoryName_i18n;

	@Schema(description = "A unique reference to a taxonomy category.")
	@Valid
	public TaxonomyCategoryReference getTaxonomyCategoryReference() {
		return taxonomyCategoryReference;
	}

	public void setTaxonomyCategoryReference(
		TaxonomyCategoryReference taxonomyCategoryReference) {

		this.taxonomyCategoryReference = taxonomyCategoryReference;
	}

	@JsonIgnore
	public void setTaxonomyCategoryReference(
		UnsafeSupplier<TaxonomyCategoryReference, Exception>
			taxonomyCategoryReferenceUnsafeSupplier) {

		try {
			taxonomyCategoryReference =
				taxonomyCategoryReferenceUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "A unique reference to a taxonomy category.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected TaxonomyCategoryReference taxonomyCategoryReference;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof TaxonomyCategoryBrief)) {
			return false;
		}

		TaxonomyCategoryBrief taxonomyCategoryBrief =
			(TaxonomyCategoryBrief)object;

		return Objects.equals(toString(), taxonomyCategoryBrief.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		if (embeddedTaxonomyCategory != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"embeddedTaxonomyCategory\": ");

			if (embeddedTaxonomyCategory instanceof Map) {
				sb.append(
					JSONFactoryUtil.createJSONObject(
						(Map<?, ?>)embeddedTaxonomyCategory));
			}
			else if (embeddedTaxonomyCategory instanceof String) {
				sb.append("\"");
				sb.append(_escape((String)embeddedTaxonomyCategory));
				sb.append("\"");
			}
			else {
				sb.append(embeddedTaxonomyCategory);
			}
		}

		if (taxonomyCategoryId != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"taxonomyCategoryId\": ");

			sb.append(taxonomyCategoryId);
		}

		if (taxonomyCategoryName != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"taxonomyCategoryName\": ");

			sb.append("\"");

			sb.append(_escape(taxonomyCategoryName));

			sb.append("\"");
		}

		if (taxonomyCategoryName_i18n != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"taxonomyCategoryName_i18n\": ");

			sb.append(_toJSON(taxonomyCategoryName_i18n));
		}

		if (taxonomyCategoryReference != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"taxonomyCategoryReference\": ");

			sb.append(String.valueOf(taxonomyCategoryReference));
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		accessMode = Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.headless.delivery.dto.v1_0.TaxonomyCategoryBrief",
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