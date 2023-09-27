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
@GraphQLName(
	description = "Represents the settings of a Page.", value = "PageSettings"
)
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "PageSettings")
public class PageSettings implements Serializable {

	public static PageSettings toDTO(String json) {
		return ObjectMapperUtil.readValue(PageSettings.class, json);
	}

	public static PageSettings unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(PageSettings.class, json);
	}

	@Schema(description = "A list of custom metatags this page has.")
	@Valid
	public CustomMetaTag[] getCustomMetaTags() {
		return customMetaTags;
	}

	public void setCustomMetaTags(CustomMetaTag[] customMetaTags) {
		this.customMetaTags = customMetaTags;
	}

	@JsonIgnore
	public void setCustomMetaTags(
		UnsafeSupplier<CustomMetaTag[], Exception>
			customMetaTagsUnsafeSupplier) {

		try {
			customMetaTags = customMetaTagsUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "A list of custom metatags this page has.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected CustomMetaTag[] customMetaTags;

	@Schema(
		description = "A flag that indicates whether the page is hidden from navigation."
	)
	public Boolean getHiddenFromNavigation() {
		return hiddenFromNavigation;
	}

	public void setHiddenFromNavigation(Boolean hiddenFromNavigation) {
		this.hiddenFromNavigation = hiddenFromNavigation;
	}

	@JsonIgnore
	public void setHiddenFromNavigation(
		UnsafeSupplier<Boolean, Exception> hiddenFromNavigationUnsafeSupplier) {

		try {
			hiddenFromNavigation = hiddenFromNavigationUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(
		description = "A flag that indicates whether the page is hidden from navigation."
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Boolean hiddenFromNavigation;

	@Schema(description = "The page's Open Graph settings.")
	@Valid
	public OpenGraphSettings getOpenGraphSettings() {
		return openGraphSettings;
	}

	public void setOpenGraphSettings(OpenGraphSettings openGraphSettings) {
		this.openGraphSettings = openGraphSettings;
	}

	@JsonIgnore
	public void setOpenGraphSettings(
		UnsafeSupplier<OpenGraphSettings, Exception>
			openGraphSettingsUnsafeSupplier) {

		try {
			openGraphSettings = openGraphSettingsUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "The page's Open Graph settings.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected OpenGraphSettings openGraphSettings;

	@Schema(description = "The page's SEO settings.")
	@Valid
	public SEOSettings getSeoSettings() {
		return seoSettings;
	}

	public void setSeoSettings(SEOSettings seoSettings) {
		this.seoSettings = seoSettings;
	}

	@JsonIgnore
	public void setSeoSettings(
		UnsafeSupplier<SEOSettings, Exception> seoSettingsUnsafeSupplier) {

		try {
			seoSettings = seoSettingsUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "The page's SEO settings.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected SEOSettings seoSettings;

	@Schema(description = "The page's site navigation menu settings.")
	@Valid
	public SitePageNavigationMenuSettings getSitePageNavigationMenuSettings() {
		return sitePageNavigationMenuSettings;
	}

	public void setSitePageNavigationMenuSettings(
		SitePageNavigationMenuSettings sitePageNavigationMenuSettings) {

		this.sitePageNavigationMenuSettings = sitePageNavigationMenuSettings;
	}

	@JsonIgnore
	public void setSitePageNavigationMenuSettings(
		UnsafeSupplier<SitePageNavigationMenuSettings, Exception>
			sitePageNavigationMenuSettingsUnsafeSupplier) {

		try {
			sitePageNavigationMenuSettings =
				sitePageNavigationMenuSettingsUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "The page's site navigation menu settings.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected SitePageNavigationMenuSettings sitePageNavigationMenuSettings;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof PageSettings)) {
			return false;
		}

		PageSettings pageSettings = (PageSettings)object;

		return Objects.equals(toString(), pageSettings.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		if (customMetaTags != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"customMetaTags\": ");

			sb.append("[");

			for (int i = 0; i < customMetaTags.length; i++) {
				sb.append(String.valueOf(customMetaTags[i]));

				if ((i + 1) < customMetaTags.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (hiddenFromNavigation != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"hiddenFromNavigation\": ");

			sb.append(hiddenFromNavigation);
		}

		if (openGraphSettings != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"openGraphSettings\": ");

			sb.append(String.valueOf(openGraphSettings));
		}

		if (seoSettings != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"seoSettings\": ");

			sb.append(String.valueOf(seoSettings));
		}

		if (sitePageNavigationMenuSettings != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"sitePageNavigationMenuSettings\": ");

			sb.append(String.valueOf(sitePageNavigationMenuSettings));
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		accessMode = Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.headless.delivery.dto.v1_0.PageSettings",
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