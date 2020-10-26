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

package com.liferay.frontend.taglib.clay.servlet.taglib;

import com.liferay.frontend.taglib.clay.internal.servlet.taglib.BaseContainerTag;
import com.liferay.frontend.taglib.clay.servlet.taglib.soy.VerticalCard;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItem;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.util.TagResourceBundleUtil;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

/**
 * @author Julien Castelain
 */
public class VerticalCardTag extends BaseContainerTag {

	@Override
	public int doStartTag() throws JspException {
		setAttributeNamespace(_ATTRIBUTE_NAMESPACE);

		return super.doStartTag();
	}

	public List<DropdownItem> getActionDropdownItems() {
		if ((_actionDropdownItems == null) && (_verticalCard != null)) {
			return _verticalCard.getActionDropdownItems();
		}

		return _actionDropdownItems;
	}

	@Override
	public String getCssClass() {
		if ((super.getCssClass() == null) && (_verticalCard != null)) {
			if (_verticalCard.getCssClass() != null) {
				return _verticalCard.getCssClass();
			}

			if (_verticalCard.getElementClasses() != null) {
				return _verticalCard.getElementClasses();
			}
		}

		return super.getCssClass();
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public Map<String, String> getData() {
		if ((super.getData() == null) && (_verticalCard != null)) {
			return _verticalCard.getData();
		}

		return super.getData();
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public String getDefaultEventHandler() {
		if ((super.getDefaultEventHandler() == null) &&
			(_verticalCard != null)) {

			return _verticalCard.getDefaultEventHandler();
		}

		return super.getDefaultEventHandler();
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link #getCssClass()}
	 */
	@Deprecated
	@Override
	public String getElementClasses() {
		if ((super.getCssClass() == null) && (_verticalCard != null)) {
			return _verticalCard.getElementClasses();
		}

		return super.getCssClass();
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
	 */
	@Deprecated
	public String getGroupName() {
		return _groupName;
	}

	public String getHref() {
		if ((_href == null) && (_verticalCard != null)) {
			return _verticalCard.getHref();
		}

		return _href;
	}

	public String getIcon() {
		if ((_icon == null) && (_verticalCard != null)) {
			return _verticalCard.getIcon();
		}

		return "documents-and-media";
	}

	@Override
	public String getId() {
		if ((super.getId() == null) && (_verticalCard != null)) {
			return _verticalCard.getId();
		}

		return super.getId();
	}

	public String getImageAlt() {
		if ((_imageAlt == null) && (_verticalCard != null)) {
			return _verticalCard.getImageAlt();
		}

		return _imageAlt;
	}

	public String getImageSrc() {
		if ((_imageSrc == null) && (_verticalCard != null)) {
			return _verticalCard.getImageSrc();
		}

		return _imageSrc;
	}

	public String getInputName() {
		if ((_inputName == null) && (_verticalCard != null)) {
			return _verticalCard.getInputName();
		}

		return _inputName;
	}

	public String getInputValue() {
		if ((_inputValue == null) && (_verticalCard != null)) {
			return _verticalCard.getInputValue();
		}

		return _inputValue;
	}

	public List<LabelItem> getLabels() {
		if ((_labels == null) && (_verticalCard != null)) {
			return _verticalCard.getLabels();
		}

		return _labels;
	}

	public Map<String, String> getLabelStylesMap() {
		if ((_labelStylesMap == null) && (_verticalCard != null)) {
			return _verticalCard.getLabelStylesMap();
		}

		return _labelStylesMap;
	}

	public String getStickerCssClass() {
		if ((_stickerCssClass == null) && (_verticalCard != null)) {
			return _verticalCard.getStickerCssClass();
		}

		return _stickerCssClass;
	}

	public String getStickerIcon() {
		if ((_stickerIcon == null) && (_verticalCard != null)) {
			return _verticalCard.getStickerIcon();
		}

		return _stickerIcon;
	}

	public String getStickerImageAlt() {
		if ((_stickerImageAlt == null) && (_verticalCard != null)) {
			return _verticalCard.getStickerImageAlt();
		}

		return _stickerImageAlt;
	}

	public String getStickerImageSrc() {
		if ((_stickerImageSrc == null) && (_verticalCard != null)) {
			return _verticalCard.getStickerImageSrc();
		}

		return _stickerImageSrc;
	}

	public String getStickerLabel() {
		if ((_stickerLabel == null) && (_verticalCard != null)) {
			return _verticalCard.getStickerLabel();
		}

		return LanguageUtil.get(
			TagResourceBundleUtil.getResourceBundle(pageContext),
			_stickerLabel);
	}

	public String getStickerShape() {
		if ((_stickerShape == null) && (_verticalCard != null)) {
			return _verticalCard.getStickerShape();
		}

		return _stickerShape;
	}

	public String getStickerStyle() {
		if ((_stickerStyle == null) && (_verticalCard != null)) {
			return _verticalCard.getStickerStyle();
		}

		return _stickerStyle;
	}

	public String getSubtitle() {
		if ((_subtitle == null) && (_verticalCard != null)) {
			return _verticalCard.getSubtitle();
		}

		return LanguageUtil.get(
			TagResourceBundleUtil.getResourceBundle(pageContext), _subtitle);
	}

	public String getTitle() {
		if ((_title == null) && (_verticalCard != null)) {
			return _verticalCard.getTitle();
		}

		return LanguageUtil.get(
			TagResourceBundleUtil.getResourceBundle(pageContext), _title);
	}

	public VerticalCard getVerticalCard() {
		return _verticalCard;
	}

	public Boolean isDisabled() {
		if (_disabled == null) {
			if (_verticalCard != null) {
				return _verticalCard.isDisabled();
			}

			return false;
		}

		return _disabled;
	}

	public Boolean isFlushHorizontal() {
		if (_flushHorizontal == null) {
			if (_verticalCard != null) {
				return _verticalCard.isFlushHorizontal();
			}

			return false;
		}

		return _flushHorizontal;
	}

	public Boolean isFlushVertical() {
		if (_flushVertical == null) {
			if (_verticalCard != null) {
				return _verticalCard.isFlushVertical();
			}

			return false;
		}

		return _flushVertical;
	}

	public Boolean isSelectable() {
		if (_selectable == null) {
			if (_verticalCard != null) {
				return _verticalCard.isSelectable();
			}

			return true;
		}

		return _selectable;
	}

	public Boolean isSelected() {
		if (_selected == null) {
			if (_verticalCard != null) {
				return _verticalCard.isSelected();
			}

			return false;
		}

		return _selected;
	}

	public void setActionDropdownItems(List<DropdownItem> actionDropdownItems) {
		_actionDropdownItems = actionDropdownItems;
	}

	public void setDisabled(Boolean disabled) {
		_disabled = disabled;
	}

	public void setFlushHorizontal(boolean flushHorizontal) {
		_flushHorizontal = flushHorizontal;
	}

	public void setFlushVertical(boolean flushVertical) {
		_flushVertical = flushVertical;
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
	 */
	@Deprecated
	public void setGroupName(String groupName) {
		_groupName = groupName;
	}

	public void setHref(String href) {
		_href = href;
	}

	public void setIcon(String icon) {
		_icon = icon;
	}

	public void setImageAlt(String imageAlt) {
		_imageAlt = imageAlt;
	}

	public void setImageSrc(String imageSrc) {
		_imageSrc = imageSrc;
	}

	public void setInputName(String inputName) {
		_inputName = inputName;
	}

	public void setInputValue(String inputValue) {
		_inputValue = inputValue;
	}

	public void setLabels(List<LabelItem> labels) {
		_labels = labels;
	}

	public void setLabelStylesMap(Map<String, String> labelStylesMap) {
		_labelStylesMap = labelStylesMap;
	}

	public void setSelectable(Boolean selectable) {
		_selectable = selectable;
	}

	public void setSelected(Boolean selected) {
		_selected = selected;
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
	 */
	@Deprecated
	public void setSpritemap(String spritemap) {
		_spritemap = spritemap;
	}

	public void setStickerCssClass(String stickerCssClass) {
		_stickerCssClass = stickerCssClass;
	}

	public void setStickerIcon(String stickerIcon) {
		_stickerIcon = stickerIcon;
	}

	public void setStickerImageAlt(String stickerImageAlt) {
		_stickerImageAlt = stickerImageAlt;
	}

	public void setStickerImageSrc(String stickerImageSrc) {
		_stickerImageSrc = stickerImageSrc;
	}

	public void setStickerLabel(String stickerLabel) {
		_stickerLabel = stickerLabel;
	}

	public void setStickerShape(String stickerShape) {
		_stickerShape = stickerShape;
	}

	public void setStickerStyle(String stickerStyle) {
		_stickerStyle = stickerStyle;
	}

	public void setSubtitle(String subtitle) {
		_subtitle = subtitle;
	}

	public void setTitle(String title) {
		_title = title;
	}

	public void setVerticalCard(VerticalCard verticalCard) {
		_verticalCard = verticalCard;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_actionDropdownItems = null;
		_disabled = null;
		_flushHorizontal = null;
		_flushVertical = null;
		_groupName = null;
		_href = null;
		_icon = null;
		_imageAlt = null;
		_imageSrc = null;
		_inputName = null;
		_inputValue = null;
		_labels = null;
		_labelStylesMap = null;
		_selectable = null;
		_selected = null;
		_spritemap = null;
		_stickerCssClass = null;
		_stickerIcon = null;
		_stickerImageAlt = null;
		_stickerImageSrc = null;
		_stickerLabel = null;
		_stickerShape = null;
		_stickerStyle = null;
		_subtitle = null;
		_title = null;
		_verticalCard = null;
	}

	@Override
	protected String getHydratedModuleName() {
		return "frontend-taglib-clay/cards/VerticalCard";
	}

	@Override
	protected Map<String, Object> prepareProps(Map<String, Object> props) {
		props.put("actions", getActionDropdownItems());
		props.put("description", getSubtitle());
		props.put("disabled", isDisabled());
		props.put("displayType", _getDisplayType());
		props.put("flushHorizontal", isFlushHorizontal());
		props.put("flushVertical", isFlushVertical());
		props.put("href", getHref());
		props.put("id", getId());
		props.put("imageAlt", getImageAlt());
		props.put("imageSrc", getImageSrc());
		props.put("inputName", getInputName());
		props.put("inputValue", getInputValue());
		props.put("labels", getLabels());
		props.put("labelStylesMap", getLabelStylesMap());
		props.put("selectable", isSelectable());
		props.put("selected", isSelected());
		props.put("stickerCssClass", getStickerCssClass());
		props.put("stickerIcon", getStickerIcon());
		props.put("stickerImageAlt", getStickerImageAlt());
		props.put("stickerImageSrc", getStickerImageSrc());
		props.put("stickerLabel", getStickerLabel());
		props.put("stickerShape", getStickerShape());
		props.put("stickerStyle", getStickerStyle());
		props.put("symbol", getIcon());
		props.put("title", getTitle());

		return super.prepareProps(props);
	}

	@Override
	protected String processCssClasses(Set<String> cssClasses) {
		cssClasses.add("card-type-asset");

		if (Validator.isNotNull(_imageSrc)) {
			cssClasses.add("image-card");
		}
		else {
			cssClasses.add("file-card");
		}

		if (!isSelectable()) {
			cssClasses.add("card");
		}
		else {
			cssClasses.add("form-check");
			cssClasses.add("form-check-card");
			cssClasses.add("form-check-top-left");
		}

		return super.processCssClasses(cssClasses);
	}

	@Override
	protected int processStartTag() throws Exception {
		super.processStartTag();

		JspWriter jspWriter = pageContext.getOut();

		if (isSelectable()) {
			jspWriter.write("<div class=\"card\"><div class=\"custom-control ");
			jspWriter.write("custom-checkbox\"><label><input ");

			if (isSelected()) {
				jspWriter.write("checked ");
			}

			jspWriter.write("class=\"custom-control-input\" ");

			if (isDisabled()) {
				jspWriter.write("disabled ");
			}

			String inputName = getInputName();

			if (Validator.isNotNull(inputName)) {
				jspWriter.write("name=\"");
				jspWriter.write(inputName);
				jspWriter.write("\" ");
			}

			jspWriter.write("type=\"checkbox\" ");

			String inputValue = getInputValue();

			if (Validator.isNotNull(inputValue)) {
				jspWriter.write("value=\"");
				jspWriter.write(inputValue);
				jspWriter.write("\" ");
			}

			jspWriter.write("/><span class=\"custom-control-label\"></span>");
		}

		jspWriter.write("<div class=\"card-item-first aspect-ratio\">");

		if (Validator.isNotNull(getImageSrc())) {
			jspWriter.write("<img");

			String imageAlt = getImageAlt();

			if (Validator.isNotNull(imageAlt)) {
				jspWriter.write(" alt=\"");
				jspWriter.write(imageAlt);
				jspWriter.write("\"");
			}

			jspWriter.write(" class=\"aspect-ratio-item");
			jspWriter.write(" aspect-ratio-item-center-middle");
			jspWriter.write(" aspect-ratio-item-fluid ");

			Boolean flushHorizontal = isFlushHorizontal();

			if (flushHorizontal != null) {
				jspWriter.write("aspect-ratio-item-flush");
			}

			Boolean flushVertical = isFlushVertical();

			if (flushVertical != null) {
				jspWriter.write("aspect-ratio-item-vertical-flush");
			}

			jspWriter.write("\" src=\"");
			jspWriter.write(getImageSrc());
			jspWriter.write("\">");
		}
		else {
			jspWriter.write("<div class=\"aspect-ratio-item");
			jspWriter.write(" aspect-ratio-item-center-middle");
			jspWriter.write(" aspect-ratio-item-fluid card-type-asset-icon\">");

			IconTag icon = new IconTag();

			icon.setSymbol(getIcon());
			icon.doTag(pageContext);

			jspWriter.write("</div>");
		}

		StickerTag stickerTag = new StickerTag();

		String stickerIcon = getStickerIcon();
		String stickerImageSrc = getStickerImageSrc();
		String stickerLabel = getStickerLabel();

		if (Validator.isNotNull(stickerIcon)) {
			stickerTag.setIcon(stickerIcon);
		}
		else if (Validator.isNotNull(stickerImageSrc)) {
			String stickerImageAlt = getStickerImageAlt();

			if (Validator.isNotNull(stickerImageAlt)) {
				stickerTag.setImageAlt(stickerImageAlt);
			}

			stickerTag.setImageSrc(stickerImageSrc);
		}
		else if (Validator.isNotNull(stickerLabel)) {
			stickerTag.setLabel(stickerLabel);
		}

		String stickerStyle = getStickerStyle();

		if (Validator.isNotNull(stickerStyle)) {
			stickerTag.setDisplayType(stickerStyle);
		}
		else {
			stickerTag.setDisplayType("primary");
		}

		stickerTag.setPosition("bottom-left");

		String stickerShape = getStickerShape();

		if (Validator.isNotNull(stickerShape)) {
			stickerTag.setShape(stickerShape);
		}

		stickerTag.doTag(pageContext);

		if (isSelectable()) {
			jspWriter.write("</div></label></div>");
		}
		else {
			jspWriter.write("</div>");
		}

		jspWriter.write("<div class=\"card-body\"><div class=\"card-row\">");
		jspWriter.write("<div class=\"autofit-col autofit-col-expand\">");

		String title = getTitle();

		jspWriter.write("<p class=\"card-title\" title=\"");

		if (Validator.isNotNull(title)) {
			jspWriter.write(title);
		}

		jspWriter.write("\"><span class=\"text-truncate-inline\">");

		String href = getHref();

		if (Validator.isNotNull(href) && !isDisabled()) {
			LinkTag linkTag = new LinkTag();

			linkTag.setCssClass("text-truncate");
			linkTag.setHref(href);
			linkTag.setLabel(title);
			linkTag.doTag(pageContext);
		}
		else {
			jspWriter.write("<span class=\"text-truncate\">");

			if (Validator.isNotNull(title)) {
				jspWriter.write(title);
			}

			jspWriter.write("</span>");
		}

		jspWriter.write("</span></p>");

		jspWriter.write("<p class=\"card-subtitle\"><span class=\"");
		jspWriter.write("text-truncate-inline\"><span class=\"");
		jspWriter.write("text-truncate\">");

		String subtitle = getSubtitle();

		if (Validator.isNotNull(subtitle)) {
			jspWriter.write(subtitle);
		}

		jspWriter.write("</span></span></p>");

		List<LabelItem> labels = getLabels();

		if (!ListUtil.isEmpty(labels)) {
			jspWriter.write("<div class=\"card-detail\">");

			for (LabelItem labelItem : labels) {
				LabelTag labelTag = new LabelTag();

				boolean labelIsDismissible = (boolean)labelItem.get(
					"dismissible");
				boolean labelIsLarge = (boolean)labelItem.get("large");
				String labelDisplayType = (String)labelItem.get("displayType");
				String labelLabel = (String)labelItem.get("label");

				if (labelIsDismissible) {
					labelTag.setDismissible(labelIsDismissible);
				}

				if (labelIsLarge) {
					labelTag.setLarge(labelIsLarge);
				}

				if (Validator.isNotNull(labelDisplayType)) {
					labelTag.setDisplayType(labelDisplayType);
				}

				labelTag.setLabel(labelLabel);

				labelTag.doTag(pageContext);
			}

			jspWriter.write("</div>");
		}

		jspWriter.write("</div>");

		if (!ListUtil.isEmpty(getActionDropdownItems())) {
			jspWriter.write(
				"<div class=\"autofit-col\"><div class=\"dropdown\">");
			jspWriter.write("<div class=\"dropdown-toggle component-action\">");

			IconTag iconTag = new IconTag();

			iconTag.setSymbol("ellipsis-v");
			iconTag.doTag(pageContext);

			jspWriter.write("</div></div></div>");
		}

		jspWriter.write("</div></div>");

		if (isSelectable()) {
			jspWriter.write("</div>");
		}

		return SKIP_BODY;
	}

	private String _getDisplayType() {
		if (Validator.isNotNull(_imageSrc)) {
			return "image";
		}

		return "file";
	}

	private static final String _ATTRIBUTE_NAMESPACE = "clay:verticalcard:";

	private List<DropdownItem> _actionDropdownItems;
	private Boolean _disabled;
	private Boolean _flushHorizontal;
	private Boolean _flushVertical;
	private String _groupName;
	private String _href;
	private String _icon;
	private String _imageAlt;
	private String _imageSrc;
	private String _inputName;
	private String _inputValue;
	private List<LabelItem> _labels;
	private Map<String, String> _labelStylesMap;
	private Boolean _selectable;
	private Boolean _selected;
	private String _spritemap;
	private String _stickerCssClass;
	private String _stickerIcon;
	private String _stickerImageAlt;
	private String _stickerImageSrc;
	private String _stickerLabel;
	private String _stickerShape;
	private String _stickerStyle;
	private String _subtitle;
	private String _title;
	private VerticalCard _verticalCard;

}