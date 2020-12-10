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
import com.liferay.frontend.taglib.clay.servlet.taglib.soy.BaseClayCard;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.petra.string.StringPool;

import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;

/**
 * @author Carlos Lancha
 */
public class BaseCardTag extends BaseContainerTag {

	@Override
	public int doStartTag() throws JspException {
		if (_cardModel != null) {
			Map<String, String> dynamicAttributes =
				_cardModel.getDynamicAttributes();

			if (dynamicAttributes != null) {
				for (Map.Entry<String, String> entry :
						dynamicAttributes.entrySet()) {

					setDynamicAttribute(
						StringPool.BLANK, entry.getKey(), entry.getValue());
				}
			}
		}

		return super.doStartTag();
	}

	public List<DropdownItem> getActionDropdownItems() {
		if ((_actionDropdownItems == null) && (_cardModel != null)) {
			return _cardModel.getActionDropdownItems();
		}

		return _actionDropdownItems;
	}

	public BaseClayCard getCardModel() {
		return _cardModel;
	}

	@Override
	public String getCssClass() {
		if ((super.getCssClass() == null) && (_cardModel != null)) {
			if (_cardModel.getCssClass() != null) {
				return _cardModel.getCssClass();
			}

			if (_cardModel.getElementClasses() != null) {
				return _cardModel.getElementClasses();
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
		if ((super.getData() == null) && (_cardModel != null)) {
			return _cardModel.getData();
		}

		return super.getData();
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public String getDefaultEventHandler() {
		if ((super.getDefaultEventHandler() == null) && (_cardModel != null)) {
			return _cardModel.getDefaultEventHandler();
		}

		return super.getDefaultEventHandler();
	}

	public String getHref() {
		if ((_href == null) && (_cardModel != null)) {
			return _cardModel.getHref();
		}

		return _href;
	}

	public String getIcon() {
		return _icon;
	}

	@Override
	public String getId() {
		if ((super.getId() == null) && (_cardModel != null)) {
			return _cardModel.getId();
		}

		return super.getId();
	}

	public String getInputName() {
		if ((_inputName == null) && (_cardModel != null)) {
			return _cardModel.getInputName();
		}

		return _inputName;
	}

	public String getInputValue() {
		if ((_inputValue == null) && (_cardModel != null)) {
			return _cardModel.getInputValue();
		}

		return _inputValue;
	}

	public Boolean isDisabled() {
		if (_disabled == null) {
			if (_cardModel != null) {
				return _cardModel.isDisabled();
			}

			return false;
		}

		return _disabled;
	}

	public Boolean isSelectable() {
		if (_selectable == null) {
			if (_cardModel != null) {
				return _cardModel.isSelectable();
			}

			return false;
		}

		return _selectable;
	}

	public Boolean isSelected() {
		if (_selected == null) {
			if (_cardModel != null) {
				return _cardModel.isSelected();
			}

			return false;
		}

		return _selected;
	}

	public void setActionDropdownItems(List<DropdownItem> actionDropdownItems) {
		_actionDropdownItems = actionDropdownItems;
	}

	public void setCardModel(BaseClayCard cardModel) {
		_cardModel = cardModel;
	}

	public void setDisabled(Boolean disabled) {
		_disabled = disabled;
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

	public void setInputName(String inputName) {
		_inputName = inputName;
	}

	public void setInputValue(String inputValue) {
		_inputValue = inputValue;
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

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_actionDropdownItems = null;
		_cardModel = null;
		_data = null;
		_disabled = null;
		_groupName = null;
		_href = null;
		_icon = null;
		_inputName = null;
		_inputValue = null;
		_selectable = null;
		_selected = null;
		_spritemap = null;
	}

	@Override
	protected Map<String, Object> prepareProps(Map<String, Object> props) {
		props.put("actions", getActionDropdownItems());
		props.put("disabled", isDisabled());
		props.put("href", getHref());
		props.put("inputName", getInputName());
		props.put("inputValue", getInputValue());
		props.put("selectable", isSelectable());
		props.put("selected", isSelected());
		props.put("symbol", getIcon());

		return super.prepareProps(props);
	}

	private List<DropdownItem> _actionDropdownItems;
	private BaseClayCard _cardModel;
	private Map<String, String> _data;
	private Boolean _disabled;
	private String _groupName;
	private String _href;
	private String _icon;
	private String _inputName;
	private String _inputValue;
	private Boolean _selectable;
	private Boolean _selected;
	private String _spritemap;

}