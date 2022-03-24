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
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

/**
 * @author Chema Balsas
 */
public class NavigationBarTag extends BaseContainerTag {

	@Override
	public int doStartTag() throws JspException {
		setAttributeNamespace(_ATTRIBUTE_NAMESPACE);

		setContainerElement("nav");

		return super.doStartTag();
	}

	public boolean getInverted() {
		return _inverted;
	}

	public List<NavigationItem> getNavigationItems() {
		return _navigationItems;
	}

	public void setInverted(boolean inverted) {
		_inverted = inverted;
	}

	public void setNavigationItems(List<NavigationItem> navigationItems) {
		_navigationItems = navigationItems;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_inverted = false;
		_navigationItems = null;
	}

	@Override
	protected String getHydratedModuleName() {
		return "{NavigationBar} from frontend-taglib-clay";
	}

	@Override
	protected Map<String, Object> prepareProps(Map<String, Object> props) {
		props.put("inverted", _inverted);
		props.put("navigationItems", _navigationItems);

		return super.prepareProps(props);
	}

	@Override
	protected String processCssClasses(Set<String> cssClasses) {
		cssClasses.add("navbar");
		cssClasses.add("navbar-collapse-absolute");
		cssClasses.add("navbar-expand-md");
		cssClasses.add("navbar-underline");
		cssClasses.add("navigation-bar");

		cssClasses.add(
			_inverted ? "navigation-bar-secondary" : "navigation-bar-light");

		return super.processCssClasses(cssClasses);
	}

	@Override
	protected int processStartTag() throws Exception {
		super.processStartTag();

		if (_navigationItems != null) {
			JspWriter jspWriter = pageContext.getOut();

			jspWriter.write("<div class=\"container-fluid ");
			jspWriter.write("container-fluid-max-xl\"><div ");
			jspWriter.write("class=\"collapse navbar-collapse\"><div ");
			jspWriter.write("class=\"container-fluid ");
			jspWriter.write("container-fluid-max-xl\"><ul ");
			jspWriter.write("class=\"navbar-nav\">");

			for (int i = 0; i < _navigationItems.size(); i++) {
				NavigationItem navigationItem = _navigationItems.get(i);

				jspWriter.write("<li class=\"nav-item\"");
				jspWriter.write(" data-nav-item-index=\"");
				jspWriter.write(String.valueOf(i));
				jspWriter.write("\"><a class=\"nav-link");

				if ((navigationItem.get("active") != null) &&
					(Boolean)navigationItem.get("active")) {

					jspWriter.write(" active");
				}

				jspWriter.write("\"");

				if (Validator.isNotNull((String)navigationItem.get("href"))) {
					jspWriter.write(" href=\"");
					jspWriter.write((String)navigationItem.get("href"));
					jspWriter.write("\"");
				}

				jspWriter.write("><span class=\"navbar-text-truncate\">");
				jspWriter.write((String)navigationItem.get("label"));
				jspWriter.write("</span></a></li>");
			}

			jspWriter.write("</ul></div></div></div>");

			return SKIP_BODY;
		}

		return EVAL_BODY_INCLUDE;
	}

	private static final String _ATTRIBUTE_NAMESPACE = "clay:navigation_bar:";

	private boolean _inverted;
	private List<NavigationItem> _navigationItems;

}