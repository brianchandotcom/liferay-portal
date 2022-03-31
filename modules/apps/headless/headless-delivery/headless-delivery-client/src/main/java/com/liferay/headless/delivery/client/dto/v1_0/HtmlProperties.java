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

package com.liferay.headless.delivery.client.dto.v1_0;

import com.liferay.headless.delivery.client.function.UnsafeSupplier;
import com.liferay.headless.delivery.client.serdes.v1_0.HtmlPropertiesSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class HtmlProperties implements Cloneable, Serializable {

	public static HtmlProperties toDTO(String json) {
		return HtmlPropertiesSerDes.toDTO(json);
	}

	public HtmlTag getHtmlTag() {
		return htmlTag;
	}

	public String getHtmlTagAsString() {
		if (htmlTag == null) {
			return null;
		}

		return htmlTag.toString();
	}

	public void setHtmlTag(HtmlTag htmlTag) {
		this.htmlTag = htmlTag;
	}

	public void setHtmlTag(
		UnsafeSupplier<HtmlTag, Exception> htmlTagUnsafeSupplier) {

		try {
			htmlTag = htmlTagUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected HtmlTag htmlTag;

	@Override
	public HtmlProperties clone() throws CloneNotSupportedException {
		return (HtmlProperties)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof HtmlProperties)) {
			return false;
		}

		HtmlProperties htmlProperties = (HtmlProperties)object;

		return Objects.equals(toString(), htmlProperties.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return HtmlPropertiesSerDes.toJSON(this);
	}

	public static enum HtmlTag {

		ARTICLE("Article"), ASIDE("Aside"), DIV("Div"), FOOTER("Footer"),
		HEADER("Header"), MAIN("Main"), NAV("Nav"), SECTION("Section");

		public static HtmlTag create(String value) {
			for (HtmlTag htmlTag : values()) {
				if (Objects.equals(htmlTag.getValue(), value) ||
					Objects.equals(htmlTag.name(), value)) {

					return htmlTag;
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

		private HtmlTag(String value) {
			_value = value;
		}

		private final String _value;

	}

}