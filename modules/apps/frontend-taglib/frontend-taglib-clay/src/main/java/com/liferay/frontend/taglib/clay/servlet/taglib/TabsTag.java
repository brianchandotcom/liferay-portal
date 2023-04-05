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
import com.liferay.frontend.taglib.clay.servlet.taglib.util.TabsItem;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.util.TagResourceBundleUtil;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

/**
 * @author Carlos Lancha
 */
public class TabsTag extends BaseContainerTag {

	@Override
	public int doStartTag() throws JspException {
		setAttributeNamespace(_ATTRIBUTE_NAMESPACE);

		return super.doStartTag();
	}

	public String getActivation() {
		return _activation;
	}

	public String getDisplayType() {
		return _displayType;
	}

	public int getPanelsCount() {
		return _panelsCount;
	}

	public List<TabsItem> getTabsItems() {
		return _tabsItems;
	}

	public boolean isFade() {
		return _fade;
	}

	public boolean isJustified() {
		return _justified;
	}

	public void setActivation(String activation) {
		_activation = activation;
	}

	public void setDisplayType(String displayType) {
		_displayType = displayType;
	}

	public void setFade(boolean fade) {
		_fade = fade;
	}

	public void setJustified(boolean justified) {
		_justified = justified;
	}

	public void setPanelsCount(int panelsCount) {
		_panelsCount = panelsCount;
	}

	public void setTabsItems(List<TabsItem> tabsItems) {
		_tabsItems = tabsItems;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_activation = "manual";
		_displayType = null;
		_fade = false;
		_justified = false;
		_panelsCount = 0;
		_tabsItems = null;
	}

	@Override
	protected String getHydratedModuleName() {
		return "{Tabs} from frontend-taglib-clay";
	}

	@Override
	protected Map<String, Object> prepareProps(Map<String, Object> props) {
		props.put("activation", _activation);
		props.put("displayType", _displayType);
		props.put("fade", _fade);
		props.put("justified", _justified);
		props.put("tabsItems", _tabsItems);

		return super.prepareProps(props);
	}

	@Override
	protected String processBodyCssClasses(Set<String> cssClasses) {
		cssClasses.add("tab-content");

		return super.processBodyCssClasses(cssClasses);
	}

	@Override
	protected int processStartTag() throws Exception {
		super.processStartTag();

		JspWriter jspWriter = pageContext.getOut();

		jspWriter.write("<ul class=\"nav nav-tabs\" role=\"tablist\">");

		for (TabsItem tabsItem : _tabsItems) {
			jspWriter.write("<li class=\"nav-item\" role=\"none\">");

			String itemCssClass = "nav-link";

			Boolean active = (Boolean)tabsItem.get("active");

			if ((active != null) && active) {
				itemCssClass = itemCssClass + " active";
			}

			if (Validator.isNotNull(tabsItem.get("disabled"))) {
				itemCssClass = itemCssClass + " disabled";
			}

			String label = (String)tabsItem.get("label");

			if (Validator.isNotNull(tabsItem.get("href"))) {
				LinkTag linkTag = new LinkTag();

				linkTag.setCssClass(itemCssClass);
				linkTag.setHref((String)tabsItem.get("href"));
				linkTag.setLabel(
					LanguageUtil.get(
						TagResourceBundleUtil.getResourceBundle(pageContext),
						label));

				linkTag.doTag(pageContext);
			}
			else {
				ButtonTag buttonTag = new ButtonTag();

				buttonTag.setCssClass(itemCssClass);
				buttonTag.setDisplayType("unstyled");
				buttonTag.setLabel(
					LanguageUtil.get(
						TagResourceBundleUtil.getResourceBundle(pageContext),
						label));

				buttonTag.doTag(pageContext);
			}

			jspWriter.write("</li>");
		}

		jspWriter.write("</ul>");

		return EVAL_BODY_INCLUDE;
	}

	private static final String _ATTRIBUTE_NAMESPACE = "clay:tabs:";

	private String _activation = "manual";
	private String _displayType;
	private boolean _fade;
	private boolean _justified;
	private int _panelsCount;
	private List<TabsItem> _tabsItems;

}