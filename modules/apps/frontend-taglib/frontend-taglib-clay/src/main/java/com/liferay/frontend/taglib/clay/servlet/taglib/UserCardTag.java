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

import com.liferay.frontend.taglib.clay.servlet.taglib.soy.UserCard;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItem;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

/**
 * @author Carlos Lancha
 */
public class UserCardTag extends BaseCardTag {

	@Override
	public int doStartTag() throws JspException {
		setAttributeNamespace(_ATTRIBUTE_NAMESPACE);

		return super.doStartTag();
	}

	@Override
	public String getIcon() {
		String icon = super.getIcon();

		if (icon == null) {
			if ((getUserCard() != null) && (getUserCard().getIcon() != null)) {
				return getUserCard().getIcon();
			}

			return "user";
		}

		return icon;
	}

	public String getImageAlt() {
		if ((_imageAlt == null) && (getUserCard() != null)) {
			return getUserCard().getImageAlt();
		}

		return _imageAlt;
	}

	public String getImageSrc() {
		if ((_imageSrc == null) && (getUserCard() != null)) {
			return getUserCard().getImageSrc();
		}

		return _imageSrc;
	}

	public List<LabelItem> getLabels() {
		if ((_labels == null) && (getUserCard() != null)) {
			return getUserCard().getLabels();
		}

		return _labels;
	}

	public String getName() {
		if ((_name == null) && (getUserCard() != null)) {
			return getUserCard().getName();
		}

		return _name;
	}

	public String getSubtitle() {
		if ((_subtitle == null) && (getUserCard() != null)) {
			return getUserCard().getSubtitle();
		}

		return _subtitle;
	}

	public UserCard getUserCard() {
		return (UserCard)getCardModel();
	}

	public String getUserColorClass() {
		if ((_userColorClass == null) && (getUserCard() != null)) {
			return getUserCard().getUserColorClass();
		}

		return _userColorClass;
	}

	public void setImageAlt(String imageAlt) {
		_imageAlt = imageAlt;
	}

	public void setImageSrc(String imageSrc) {
		_imageSrc = imageSrc;
	}

	public void setLabels(List<LabelItem> labels) {
		_labels = labels;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setSubtitle(String subtitle) {
		_subtitle = subtitle;
	}

	public void setUserCard(UserCard userCard) {
		setCardModel(userCard);
	}

	public void setUserColorClass(String userColorClass) {
		_userColorClass = userColorClass;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_imageAlt = null;
		_imageSrc = null;
		_labels = null;
		_name = null;
		_subtitle = null;
		_userColorClass = null;
	}

	@Override
	protected String getHydratedModuleName() {
		return "frontend-taglib-clay/cards/UserCard";
	}

	@Override
	protected Map<String, Object> prepareProps(Map<String, Object> props) {
		props.put("description", getSubtitle());
		props.put("labels", getLabels());
		props.put("name", getName());
		props.put("userDisplayType", getUserColorClass());
		props.put("userImageAlt", getImageAlt());
		props.put("userImageSrc", getImageSrc());

		return super.prepareProps(props);
	}

	@Override
	protected String processCssClasses(Set<String> cssClasses) {
		Boolean selectable = isSelectable();

		if ((selectable == null) || !selectable) {
			cssClasses.add("card");
		}

		cssClasses.add("card-type-asset");
		cssClasses.add("form-check");
		cssClasses.add("form-check-card");
		cssClasses.add("form-check-top-left");
		cssClasses.add("user-card");

		return super.processCssClasses(cssClasses);
	}

	@Override
	protected int processStartTag() throws Exception {
		super.processStartTag();

		JspWriter jspWriter = pageContext.getOut();

		jspWriter.write("<div class=\"card\"><div class=\"aspect-ratio ");
		jspWriter.write("card-item-first\">");

		Boolean disabled = isDisabled();
		Boolean selectable = isSelectable();

		if (selectable) {
			jspWriter.write("<div class=\"custom-checkbox custom-control\">");
			jspWriter.write("<label><input ");

			jspWriter.write("class=\"custom-control-input\"");

			Boolean selected = isSelected();

			if (selected) {
				jspWriter.write("checked ");
			}

			if (disabled) {
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
				jspWriter.write("\"");
			}

			jspWriter.write("/><span class=\"custom-control-label\"></span>");
		}

		jspWriter.write("<div class=\"aspect-ratio-item-center-middle ");
		jspWriter.write("card-type-asset-icon\">");

		StickerTag stickerTag = new StickerTag();

		stickerTag.setCssClass("sticker-user-icon");
		stickerTag.setDisplayType(getUserColorClass());
		stickerTag.setShape("circle");

		String imageSrc = getImageSrc();

		if (Validator.isNotNull(imageSrc)) {
			stickerTag.setImageAlt(getImageAlt());
			stickerTag.setImageSrc(imageSrc);
		}
		else {
			stickerTag.setIcon(getIcon());
		}

		stickerTag.doTag(pageContext);

		jspWriter.write("</div>");

		if (selectable) {
			jspWriter.write("</label></div>");
		}

		jspWriter.write("</div><div class=\"card-body\"><div ");
		jspWriter.write("class=\"card-row\"><div class=\"autofit-col ");
		jspWriter.write("autofit-col-expand\"><p class=\"card-title\"><span ");
		jspWriter.write("class=\"text-truncate-inline\">");

		String href = getHref();
		String name = getName();

		if (((disabled == null) || !disabled) && Validator.isNotNull(href)) {
			jspWriter.write("<a class=\"text-truncate\" href=\"");
			jspWriter.write(href);
			jspWriter.write("\">");
			jspWriter.write(name);
			jspWriter.write("</a>");
		}
		else {
			jspWriter.write("<span class=\"text-truncate\">");
			jspWriter.write(name);
			jspWriter.write("</span>");
		}

		jspWriter.write("</span></p><p class=\"card-subtitle\"><span class=\"");
		jspWriter.write("text-truncate-inline\"><span class=\"text-truncate\"");
		jspWriter.write(">");
		jspWriter.write(getSubtitle());
		jspWriter.write("</span></span></p>");

		List<LabelItem> labels = getLabels();

		if (!ListUtil.isEmpty(labels)) {
			jspWriter.write("<div class=\"card-detail\">");

			for (LabelItem labelItem : labels) {
				LabelTag labelTag = new LabelTag();

				if ((boolean)labelItem.get("dismissible")) {
					labelTag.setDismissible(true);
				}

				String displayType = (String)labelItem.get("displayType");

				if (Validator.isNotNull(displayType)) {
					labelTag.setDisplayType(displayType);
				}

				labelTag.setLabel((String)labelItem.get("label"));

				if ((boolean)labelItem.get("large")) {
					labelTag.setLarge(true);
				}

				labelTag.doTag(pageContext);
			}

			jspWriter.write("</div>");
		}

		jspWriter.write("</div>");

		if (!ListUtil.isEmpty(getActionDropdownItems())) {
			jspWriter.write("<div class=\"autofit-col\"><div class=\"dropdown");
			jspWriter.write("\"><div class=\"component-action dropdown-toggle");
			jspWriter.write("\">");

			IconTag iconTag = new IconTag();

			iconTag.setSymbol("ellipsis-v");

			iconTag.doTag(pageContext);

			jspWriter.write("</div></div></div>");
		}

		jspWriter.write("</div></div></div>");

		return SKIP_BODY;
	}

	private static final String _ATTRIBUTE_NAMESPACE = "clay:user_card:";

	private String _imageAlt;
	private String _imageSrc;
	private List<LabelItem> _labels;
	private String _name;
	private String _subtitle;
	private String _userColorClass;

}