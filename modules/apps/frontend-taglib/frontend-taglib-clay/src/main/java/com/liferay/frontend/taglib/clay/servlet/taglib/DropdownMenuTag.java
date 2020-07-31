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

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;

import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;

/**
 * @author Chema Balsas
 */
public class DropdownMenuTag extends ButtonTag {

	@Override
	public int doStartTag() throws JspException {
		setAttributeNamespace(_ATTRIBUTE_NAMESPACE);

		return super.doStartTag();
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getLabel()}
	 */
	@Deprecated
	public String getButtonLabel() {
		return getLabel();
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getDisplayType()}
	 */
	@Deprecated
	public String getButtonStyle() {
		return getDisplayType();
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public String getButtonType() {
		return _buttonType;
	}

	public List<DropdownItem> getDropdownItems() {
		return _dropdownItems;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public boolean getExpanded() {
		return _expanded;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public String getItemsIconAlignment() {
		return _itemsIconAlignment;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public boolean getSearchable() {
		return _searchable;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public boolean getShowToggleIcon() {
		return _showToggleIcon;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getCssClass()}
	 */
	@Deprecated
	public String getTriggerCssClasses() {
		return getCssClass();
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public String getTriggerTitle() {
		return _triggerTitle;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #setLabel(String)}
	 */
	@Deprecated
	public void setButtonLabel(String buttonLabel) {
		setLabel(buttonLabel);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #setDisplayType(String)}
	 */
	@Deprecated
	public void setButtonStyle(String buttonStyle) {
		setDisplayType(buttonStyle);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public void setButtonType(String buttonType) {
		_buttonType = buttonType;
	}

	public void setDropdownItems(List<DropdownItem> dropdownItems) {
		_dropdownItems = dropdownItems;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public void setExpanded(boolean expanded) {
		_expanded = expanded;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public void setItemsIconAlignment(String itemsIconAlignment) {
		_itemsIconAlignment = itemsIconAlignment;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public void setSearchable(boolean searchable) {
		_searchable = searchable;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public void setShowToggleIcon(boolean showToggleIcon) {
		_showToggleIcon = showToggleIcon;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #setCssClas(String)}
	 */
	@Deprecated
	public void setTriggerCssClasses(String triggerCssClasses) {
		setCssClass(triggerCssClasses);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public void setTriggerTitle(String triggerTitle) {
		_triggerTitle = triggerTitle;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_buttonType = null;
		_dropdownItems = null;
		_expanded = false;
		_itemsIconAlignment = null;
		_searchable = false;
		_showToggleIcon = false;
		_triggerTitle = null;
	}

	@Override
	protected String getHydratedModuleName() {
		return "frontend-taglib-clay/DropdownMenu";
	}

	@Override
	protected Map<String, Object> prepareProps(Map<String, Object> props) {
		props.put("items", _dropdownItems);

		return super.prepareProps(props);
	}

	private static final String _ATTRIBUTE_NAMESPACE = "clay:dropdown-menu:";

	private String _buttonType;
	private List<DropdownItem> _dropdownItems;
	private boolean _expanded;
	private String _itemsIconAlignment;
	private boolean _searchable;
	private boolean _showToggleIcon;
	private String _triggerTitle;

}