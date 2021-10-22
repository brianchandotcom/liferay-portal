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

import javax.validation.Valid;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@GraphQLName(
	description = "Represents a fragment style.", value = "FragmentStyle"
)
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "FragmentStyle")
public class FragmentStyle implements Serializable {

	public static FragmentStyle toDTO(String json) {
		return ObjectMapperUtil.readValue(FragmentStyle.class, json);
	}

	public static FragmentStyle unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(FragmentStyle.class, json);
	}

	@Schema(description = "The fragment's background color.")
	public String getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	@JsonIgnore
	public void setBackgroundColor(
		UnsafeSupplier<String, Exception> backgroundColorUnsafeSupplier) {

		try {
			backgroundColor = backgroundColorUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "The fragment's background color.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String backgroundColor;

	@Schema(description = "The fragment's background image.")
	@Valid
	public FragmentImage getBackgroundFragmentImage() {
		return backgroundFragmentImage;
	}

	public void setBackgroundFragmentImage(
		FragmentImage backgroundFragmentImage) {

		this.backgroundFragmentImage = backgroundFragmentImage;
	}

	@JsonIgnore
	public void setBackgroundFragmentImage(
		UnsafeSupplier<FragmentImage, Exception>
			backgroundFragmentImageUnsafeSupplier) {

		try {
			backgroundFragmentImage =
				backgroundFragmentImageUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "The fragment's background image.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected FragmentImage backgroundFragmentImage;

	@Schema(description = "The fragment's border color.")
	public String getBorderColor() {
		return borderColor;
	}

	public void setBorderColor(String borderColor) {
		this.borderColor = borderColor;
	}

	@JsonIgnore
	public void setBorderColor(
		UnsafeSupplier<String, Exception> borderColorUnsafeSupplier) {

		try {
			borderColor = borderColorUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "The fragment's border color.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String borderColor;

	@Schema(description = "The fragment's background radius.")
	public String getBorderRadius() {
		return borderRadius;
	}

	public void setBorderRadius(String borderRadius) {
		this.borderRadius = borderRadius;
	}

	@JsonIgnore
	public void setBorderRadius(
		UnsafeSupplier<String, Exception> borderRadiusUnsafeSupplier) {

		try {
			borderRadius = borderRadiusUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "The fragment's background radius.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String borderRadius;

	@Schema(description = "The fragment's border width.")
	public String getBorderWidth() {
		return borderWidth;
	}

	public void setBorderWidth(String borderWidth) {
		this.borderWidth = borderWidth;
	}

	@JsonIgnore
	public void setBorderWidth(
		UnsafeSupplier<String, Exception> borderWidthUnsafeSupplier) {

		try {
			borderWidth = borderWidthUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "The fragment's border width.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String borderWidth;

	@Schema(description = "The fragment's font family.")
	public String getFontFamily() {
		return fontFamily;
	}

	public void setFontFamily(String fontFamily) {
		this.fontFamily = fontFamily;
	}

	@JsonIgnore
	public void setFontFamily(
		UnsafeSupplier<String, Exception> fontFamilyUnsafeSupplier) {

		try {
			fontFamily = fontFamilyUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "The fragment's font family.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String fontFamily;

	@Schema(description = "The fragment's font size.")
	public String getFontSize() {
		return fontSize;
	}

	public void setFontSize(String fontSize) {
		this.fontSize = fontSize;
	}

	@JsonIgnore
	public void setFontSize(
		UnsafeSupplier<String, Exception> fontSizeUnsafeSupplier) {

		try {
			fontSize = fontSizeUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "The fragment's font size.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String fontSize;

	@Schema(description = "The fragment's font weight.")
	public String getFontWeight() {
		return fontWeight;
	}

	public void setFontWeight(String fontWeight) {
		this.fontWeight = fontWeight;
	}

	@JsonIgnore
	public void setFontWeight(
		UnsafeSupplier<String, Exception> fontWeightUnsafeSupplier) {

		try {
			fontWeight = fontWeightUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "The fragment's font weight.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String fontWeight;

	@Schema(description = "The fragment's height.")
	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	@JsonIgnore
	public void setHeight(
		UnsafeSupplier<String, Exception> heightUnsafeSupplier) {

		try {
			height = heightUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "The fragment's height.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String height;

	@Schema(description = "Specifies if the fragment is hidden to the user.")
	public Boolean getHidden() {
		return hidden;
	}

	public void setHidden(Boolean hidden) {
		this.hidden = hidden;
	}

	@JsonIgnore
	public void setHidden(
		UnsafeSupplier<Boolean, Exception> hiddenUnsafeSupplier) {

		try {
			hidden = hiddenUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(
		description = "Specifies if the fragment is hidden to the user."
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Boolean hidden;

	@Schema(description = "The fragment's margin bottom.")
	public String getMarginBottom() {
		return marginBottom;
	}

	public void setMarginBottom(String marginBottom) {
		this.marginBottom = marginBottom;
	}

	@JsonIgnore
	public void setMarginBottom(
		UnsafeSupplier<String, Exception> marginBottomUnsafeSupplier) {

		try {
			marginBottom = marginBottomUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "The fragment's margin bottom.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String marginBottom;

	@Schema(description = "The fragment's margin left.")
	public String getMarginLeft() {
		return marginLeft;
	}

	public void setMarginLeft(String marginLeft) {
		this.marginLeft = marginLeft;
	}

	@JsonIgnore
	public void setMarginLeft(
		UnsafeSupplier<String, Exception> marginLeftUnsafeSupplier) {

		try {
			marginLeft = marginLeftUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "The fragment's margin left.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String marginLeft;

	@Schema(description = "The fragment's margin right.")
	public String getMarginRight() {
		return marginRight;
	}

	public void setMarginRight(String marginRight) {
		this.marginRight = marginRight;
	}

	@JsonIgnore
	public void setMarginRight(
		UnsafeSupplier<String, Exception> marginRightUnsafeSupplier) {

		try {
			marginRight = marginRightUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "The fragment's margin right.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String marginRight;

	@Schema(description = "The fragment's margin top.")
	public String getMarginTop() {
		return marginTop;
	}

	public void setMarginTop(String marginTop) {
		this.marginTop = marginTop;
	}

	@JsonIgnore
	public void setMarginTop(
		UnsafeSupplier<String, Exception> marginTopUnsafeSupplier) {

		try {
			marginTop = marginTopUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "The fragment's margin top.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String marginTop;

	@Schema(description = "The fragment's max height.")
	public String getMaxHeight() {
		return maxHeight;
	}

	public void setMaxHeight(String maxHeight) {
		this.maxHeight = maxHeight;
	}

	@JsonIgnore
	public void setMaxHeight(
		UnsafeSupplier<String, Exception> maxHeightUnsafeSupplier) {

		try {
			maxHeight = maxHeightUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "The fragment's max height.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String maxHeight;

	@Schema(description = "The fragment's max width.")
	public String getMaxWidth() {
		return maxWidth;
	}

	public void setMaxWidth(String maxWidth) {
		this.maxWidth = maxWidth;
	}

	@JsonIgnore
	public void setMaxWidth(
		UnsafeSupplier<String, Exception> maxWidthUnsafeSupplier) {

		try {
			maxWidth = maxWidthUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "The fragment's max width.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String maxWidth;

	@Schema(description = "The fragment's min height.")
	public String getMinHeight() {
		return minHeight;
	}

	public void setMinHeight(String minHeight) {
		this.minHeight = minHeight;
	}

	@JsonIgnore
	public void setMinHeight(
		UnsafeSupplier<String, Exception> minHeightUnsafeSupplier) {

		try {
			minHeight = minHeightUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "The fragment's min height.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String minHeight;

	@Schema(description = "The fragment's min width.")
	public String getMinWidth() {
		return minWidth;
	}

	public void setMinWidth(String minWidth) {
		this.minWidth = minWidth;
	}

	@JsonIgnore
	public void setMinWidth(
		UnsafeSupplier<String, Exception> minWidthUnsafeSupplier) {

		try {
			minWidth = minWidthUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "The fragment's min width.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String minWidth;

	@Schema(description = "The fragment's opacity.")
	public String getOpacity() {
		return opacity;
	}

	public void setOpacity(String opacity) {
		this.opacity = opacity;
	}

	@JsonIgnore
	public void setOpacity(
		UnsafeSupplier<String, Exception> opacityUnsafeSupplier) {

		try {
			opacity = opacityUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "The fragment's opacity.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String opacity;

	@Schema(description = "The fragment's overflow behavior.")
	public String getOverflow() {
		return overflow;
	}

	public void setOverflow(String overflow) {
		this.overflow = overflow;
	}

	@JsonIgnore
	public void setOverflow(
		UnsafeSupplier<String, Exception> overflowUnsafeSupplier) {

		try {
			overflow = overflowUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "The fragment's overflow behavior.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String overflow;

	@Schema(description = "The fragment's padding bottom.")
	public String getPaddingBottom() {
		return paddingBottom;
	}

	public void setPaddingBottom(String paddingBottom) {
		this.paddingBottom = paddingBottom;
	}

	@JsonIgnore
	public void setPaddingBottom(
		UnsafeSupplier<String, Exception> paddingBottomUnsafeSupplier) {

		try {
			paddingBottom = paddingBottomUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "The fragment's padding bottom.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String paddingBottom;

	@Schema(description = "The fragment's padding left.")
	public String getPaddingLeft() {
		return paddingLeft;
	}

	public void setPaddingLeft(String paddingLeft) {
		this.paddingLeft = paddingLeft;
	}

	@JsonIgnore
	public void setPaddingLeft(
		UnsafeSupplier<String, Exception> paddingLeftUnsafeSupplier) {

		try {
			paddingLeft = paddingLeftUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "The fragment's padding left.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String paddingLeft;

	@Schema(description = "The fragment's padding right.")
	public String getPaddingRight() {
		return paddingRight;
	}

	public void setPaddingRight(String paddingRight) {
		this.paddingRight = paddingRight;
	}

	@JsonIgnore
	public void setPaddingRight(
		UnsafeSupplier<String, Exception> paddingRightUnsafeSupplier) {

		try {
			paddingRight = paddingRightUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "The fragment's padding right.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String paddingRight;

	@Schema(description = "The fragment's padding top.")
	public String getPaddingTop() {
		return paddingTop;
	}

	public void setPaddingTop(String paddingTop) {
		this.paddingTop = paddingTop;
	}

	@JsonIgnore
	public void setPaddingTop(
		UnsafeSupplier<String, Exception> paddingTopUnsafeSupplier) {

		try {
			paddingTop = paddingTopUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "The fragment's padding top.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String paddingTop;

	@Schema(description = "The fragment's shadow effect.")
	public String getShadow() {
		return shadow;
	}

	public void setShadow(String shadow) {
		this.shadow = shadow;
	}

	@JsonIgnore
	public void setShadow(
		UnsafeSupplier<String, Exception> shadowUnsafeSupplier) {

		try {
			shadow = shadowUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "The fragment's shadow effect.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String shadow;

	@Schema(description = "The fragment's text align.")
	public String getTextAlign() {
		return textAlign;
	}

	public void setTextAlign(String textAlign) {
		this.textAlign = textAlign;
	}

	@JsonIgnore
	public void setTextAlign(
		UnsafeSupplier<String, Exception> textAlignUnsafeSupplier) {

		try {
			textAlign = textAlignUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "The fragment's text align.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String textAlign;

	@Schema(description = "The fragment's text color.")
	public String getTextColor() {
		return textColor;
	}

	public void setTextColor(String textColor) {
		this.textColor = textColor;
	}

	@JsonIgnore
	public void setTextColor(
		UnsafeSupplier<String, Exception> textColorUnsafeSupplier) {

		try {
			textColor = textColorUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "The fragment's text color.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String textColor;

	@Schema(description = "The fragment's width.")
	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	@JsonIgnore
	public void setWidth(
		UnsafeSupplier<String, Exception> widthUnsafeSupplier) {

		try {
			width = widthUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "The fragment's width.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String width;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof FragmentStyle)) {
			return false;
		}

		FragmentStyle fragmentStyle = (FragmentStyle)object;

		return Objects.equals(toString(), fragmentStyle.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		if (backgroundColor != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"backgroundColor\": ");

			sb.append("\"");

			sb.append(_escape(backgroundColor));

			sb.append("\"");
		}

		if (backgroundFragmentImage != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"backgroundFragmentImage\": ");

			sb.append(String.valueOf(backgroundFragmentImage));
		}

		if (borderColor != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"borderColor\": ");

			sb.append("\"");

			sb.append(_escape(borderColor));

			sb.append("\"");
		}

		if (borderRadius != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"borderRadius\": ");

			sb.append("\"");

			sb.append(_escape(borderRadius));

			sb.append("\"");
		}

		if (borderWidth != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"borderWidth\": ");

			sb.append("\"");

			sb.append(_escape(borderWidth));

			sb.append("\"");
		}

		if (fontFamily != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"fontFamily\": ");

			sb.append("\"");

			sb.append(_escape(fontFamily));

			sb.append("\"");
		}

		if (fontSize != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"fontSize\": ");

			sb.append("\"");

			sb.append(_escape(fontSize));

			sb.append("\"");
		}

		if (fontWeight != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"fontWeight\": ");

			sb.append("\"");

			sb.append(_escape(fontWeight));

			sb.append("\"");
		}

		if (height != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"height\": ");

			sb.append("\"");

			sb.append(_escape(height));

			sb.append("\"");
		}

		if (hidden != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"hidden\": ");

			sb.append(hidden);
		}

		if (marginBottom != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"marginBottom\": ");

			sb.append("\"");

			sb.append(_escape(marginBottom));

			sb.append("\"");
		}

		if (marginLeft != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"marginLeft\": ");

			sb.append("\"");

			sb.append(_escape(marginLeft));

			sb.append("\"");
		}

		if (marginRight != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"marginRight\": ");

			sb.append("\"");

			sb.append(_escape(marginRight));

			sb.append("\"");
		}

		if (marginTop != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"marginTop\": ");

			sb.append("\"");

			sb.append(_escape(marginTop));

			sb.append("\"");
		}

		if (maxHeight != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"maxHeight\": ");

			sb.append("\"");

			sb.append(_escape(maxHeight));

			sb.append("\"");
		}

		if (maxWidth != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"maxWidth\": ");

			sb.append("\"");

			sb.append(_escape(maxWidth));

			sb.append("\"");
		}

		if (minHeight != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"minHeight\": ");

			sb.append("\"");

			sb.append(_escape(minHeight));

			sb.append("\"");
		}

		if (minWidth != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"minWidth\": ");

			sb.append("\"");

			sb.append(_escape(minWidth));

			sb.append("\"");
		}

		if (opacity != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"opacity\": ");

			sb.append("\"");

			sb.append(_escape(opacity));

			sb.append("\"");
		}

		if (overflow != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"overflow\": ");

			sb.append("\"");

			sb.append(_escape(overflow));

			sb.append("\"");
		}

		if (paddingBottom != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"paddingBottom\": ");

			sb.append("\"");

			sb.append(_escape(paddingBottom));

			sb.append("\"");
		}

		if (paddingLeft != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"paddingLeft\": ");

			sb.append("\"");

			sb.append(_escape(paddingLeft));

			sb.append("\"");
		}

		if (paddingRight != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"paddingRight\": ");

			sb.append("\"");

			sb.append(_escape(paddingRight));

			sb.append("\"");
		}

		if (paddingTop != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"paddingTop\": ");

			sb.append("\"");

			sb.append(_escape(paddingTop));

			sb.append("\"");
		}

		if (shadow != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"shadow\": ");

			sb.append("\"");

			sb.append(_escape(shadow));

			sb.append("\"");
		}

		if (textAlign != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"textAlign\": ");

			sb.append("\"");

			sb.append(_escape(textAlign));

			sb.append("\"");
		}

		if (textColor != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"textColor\": ");

			sb.append("\"");

			sb.append(_escape(textColor));

			sb.append("\"");
		}

		if (width != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"width\": ");

			sb.append("\"");

			sb.append(_escape(width));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		accessMode = Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.headless.delivery.dto.v1_0.FragmentStyle",
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