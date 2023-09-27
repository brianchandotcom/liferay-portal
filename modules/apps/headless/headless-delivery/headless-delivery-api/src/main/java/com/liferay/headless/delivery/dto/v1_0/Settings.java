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
@GraphQLName(
	description = "Represents the settings of a page.", value = "Settings"
)
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "Settings")
public class Settings implements Serializable {

	public static Settings toDTO(String json) {
		return ObjectMapperUtil.readValue(Settings.class, json);
	}

	public static Settings unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(Settings.class, json);
	}

	@Schema(description = "The page's color scheme name.")
	public String getColorSchemeName() {
		return colorSchemeName;
	}

	public void setColorSchemeName(String colorSchemeName) {
		this.colorSchemeName = colorSchemeName;
	}

	@JsonIgnore
	public void setColorSchemeName(
		UnsafeSupplier<String, Exception> colorSchemeNameUnsafeSupplier) {

		try {
			colorSchemeName = colorSchemeNameUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "The page's color scheme name.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String colorSchemeName;

	@Schema(description = "The page's CSS.")
	public String getCss() {
		return css;
	}

	public void setCss(String css) {
		this.css = css;
	}

	@JsonIgnore
	public void setCss(UnsafeSupplier<String, Exception> cssUnsafeSupplier) {
		try {
			css = cssUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "The page's CSS.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String css;

	@Schema(description = "The FavIcon of the page")
	@Valid
	public Object getFavIcon() {
		return favIcon;
	}

	public void setFavIcon(Object favIcon) {
		this.favIcon = favIcon;
	}

	@JsonIgnore
	public void setFavIcon(
		UnsafeSupplier<Object, Exception> favIconUnsafeSupplier) {

		try {
			favIcon = favIconUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "The FavIcon of the page")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Object favIcon;

	@Schema(
		description = "The client extensions for global css associated to the page."
	)
	@Valid
	public ClientExtension[] getGlobalCSSClientExtensions() {
		return globalCSSClientExtensions;
	}

	public void setGlobalCSSClientExtensions(
		ClientExtension[] globalCSSClientExtensions) {

		this.globalCSSClientExtensions = globalCSSClientExtensions;
	}

	@JsonIgnore
	public void setGlobalCSSClientExtensions(
		UnsafeSupplier<ClientExtension[], Exception>
			globalCSSClientExtensionsUnsafeSupplier) {

		try {
			globalCSSClientExtensions =
				globalCSSClientExtensionsUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(
		description = "The client extensions for global css associated to the page."
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected ClientExtension[] globalCSSClientExtensions;

	@Schema(
		description = "The client extensions for global js associated to the page."
	)
	@Valid
	public ClientExtension[] getGlobalJSClientExtensions() {
		return globalJSClientExtensions;
	}

	public void setGlobalJSClientExtensions(
		ClientExtension[] globalJSClientExtensions) {

		this.globalJSClientExtensions = globalJSClientExtensions;
	}

	@JsonIgnore
	public void setGlobalJSClientExtensions(
		UnsafeSupplier<ClientExtension[], Exception>
			globalJSClientExtensionsUnsafeSupplier) {

		try {
			globalJSClientExtensions =
				globalJSClientExtensionsUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(
		description = "The client extensions for global js associated to the page."
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected ClientExtension[] globalJSClientExtensions;

	@Schema(description = "The page's JavaScript.")
	public String getJavascript() {
		return javascript;
	}

	public void setJavascript(String javascript) {
		this.javascript = javascript;
	}

	@JsonIgnore
	public void setJavascript(
		UnsafeSupplier<String, Exception> javascriptUnsafeSupplier) {

		try {
			javascript = javascriptUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "The page's JavaScript.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String javascript;

	@Schema(description = "The page's master page.")
	@Valid
	public MasterPage getMasterPage() {
		return masterPage;
	}

	public void setMasterPage(MasterPage masterPage) {
		this.masterPage = masterPage;
	}

	@JsonIgnore
	public void setMasterPage(
		UnsafeSupplier<MasterPage, Exception> masterPageUnsafeSupplier) {

		try {
			masterPage = masterPageUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "The page's master page.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected MasterPage masterPage;

	@Schema(description = "The StyleBook that is applied to the page.")
	@Valid
	public StyleBook getStyleBook() {
		return styleBook;
	}

	public void setStyleBook(StyleBook styleBook) {
		this.styleBook = styleBook;
	}

	@JsonIgnore
	public void setStyleBook(
		UnsafeSupplier<StyleBook, Exception> styleBookUnsafeSupplier) {

		try {
			styleBook = styleBookUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "The StyleBook that is applied to the page.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected StyleBook styleBook;

	@Schema(description = "The Client Extension for the theme css of a page")
	@Valid
	public ClientExtension getThemeCSSClientExtension() {
		return themeCSSClientExtension;
	}

	public void setThemeCSSClientExtension(
		ClientExtension themeCSSClientExtension) {

		this.themeCSSClientExtension = themeCSSClientExtension;
	}

	@JsonIgnore
	public void setThemeCSSClientExtension(
		UnsafeSupplier<ClientExtension, Exception>
			themeCSSClientExtensionUnsafeSupplier) {

		try {
			themeCSSClientExtension =
				themeCSSClientExtensionUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(
		description = "The Client Extension for the theme css of a page"
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected ClientExtension themeCSSClientExtension;

	@Schema(description = "The page's theme name.")
	public String getThemeName() {
		return themeName;
	}

	public void setThemeName(String themeName) {
		this.themeName = themeName;
	}

	@JsonIgnore
	public void setThemeName(
		UnsafeSupplier<String, Exception> themeNameUnsafeSupplier) {

		try {
			themeName = themeNameUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "The page's theme name.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String themeName;

	@Schema(description = "The page's theme settings.")
	@Valid
	public Object getThemeSettings() {
		return themeSettings;
	}

	public void setThemeSettings(Object themeSettings) {
		this.themeSettings = themeSettings;
	}

	@JsonIgnore
	public void setThemeSettings(
		UnsafeSupplier<Object, Exception> themeSettingsUnsafeSupplier) {

		try {
			themeSettings = themeSettingsUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "The page's theme settings.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Object themeSettings;

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

		if (colorSchemeName != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"colorSchemeName\": ");

			sb.append("\"");

			sb.append(_escape(colorSchemeName));

			sb.append("\"");
		}

		if (css != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"css\": ");

			sb.append("\"");

			sb.append(_escape(css));

			sb.append("\"");
		}

		if (favIcon != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"favIcon\": ");

			if (favIcon instanceof Map) {
				sb.append(JSONFactoryUtil.createJSONObject((Map<?, ?>)favIcon));
			}
			else if (favIcon instanceof String) {
				sb.append("\"");
				sb.append(_escape((String)favIcon));
				sb.append("\"");
			}
			else {
				sb.append(favIcon);
			}
		}

		if (globalCSSClientExtensions != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"globalCSSClientExtensions\": ");

			sb.append("[");

			for (int i = 0; i < globalCSSClientExtensions.length; i++) {
				sb.append(String.valueOf(globalCSSClientExtensions[i]));

				if ((i + 1) < globalCSSClientExtensions.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (globalJSClientExtensions != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"globalJSClientExtensions\": ");

			sb.append("[");

			for (int i = 0; i < globalJSClientExtensions.length; i++) {
				sb.append(String.valueOf(globalJSClientExtensions[i]));

				if ((i + 1) < globalJSClientExtensions.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (javascript != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"javascript\": ");

			sb.append("\"");

			sb.append(_escape(javascript));

			sb.append("\"");
		}

		if (masterPage != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"masterPage\": ");

			sb.append(String.valueOf(masterPage));
		}

		if (styleBook != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"styleBook\": ");

			sb.append(String.valueOf(styleBook));
		}

		if (themeCSSClientExtension != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"themeCSSClientExtension\": ");

			sb.append(String.valueOf(themeCSSClientExtension));
		}

		if (themeName != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"themeName\": ");

			sb.append("\"");

			sb.append(_escape(themeName));

			sb.append("\"");
		}

		if (themeSettings != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"themeSettings\": ");

			if (themeSettings instanceof Map) {
				sb.append(
					JSONFactoryUtil.createJSONObject((Map<?, ?>)themeSettings));
			}
			else if (themeSettings instanceof String) {
				sb.append("\"");
				sb.append(_escape((String)themeSettings));
				sb.append("\"");
			}
			else {
				sb.append(themeSettings);
			}
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		accessMode = Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.headless.delivery.dto.v1_0.Settings",
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