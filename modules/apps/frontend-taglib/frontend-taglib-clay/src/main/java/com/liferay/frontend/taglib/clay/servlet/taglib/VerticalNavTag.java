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
import com.liferay.frontend.taglib.clay.servlet.taglib.util.VerticalNavItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.VerticalNavItemList;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

/**
 * @author Eduardo Allegrini
 * @author Daniel Sanz
 */
public class VerticalNavTag extends BaseContainerTag {

	@Override
	public int doStartTag() throws JspException {
		setAttributeNamespace(_ATTRIBUTE_NAMESPACE);

		setContainerElement("nav");

		return super.doStartTag();
	}

	public boolean getDecorated() {
		return _decorated;
	}

	public boolean getLarge() {
		return _large;
	}

	public List<VerticalNavItem> getVerticalNavItems() {
		return _verticalNavItems;
	}

	public void setDecorated(boolean decorated) {
		_decorated = decorated;
	}

	public void setLarge(boolean large) {
		_large = large;
	}

	public void setVerticalNavItems(List<VerticalNavItem> verticalNavItems) {
		_verticalNavItems = verticalNavItems;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_decorated = false;
		_large = false;
		_verticalNavItems = null;
	}

	@Override
	protected String getHydratedModuleName() {
		return "{VerticalNav} from frontend-taglib-clay";
	}

	@Override
	protected Map<String, Object> prepareProps(Map<String, Object> props) {
		props.put("decorated", _decorated);
		props.put("large", _large);
		props.put("items", _verticalNavItems);

		return super.prepareProps(props);
	}

	@Override
	protected String processCssClasses(Set<String> cssClasses) {
		cssClasses.add("menubar menubar-transparent");

		if (_decorated) {
			cssClasses.add("menubar-decorated");
		}

		cssClasses.add(
			_large ? "menubar-vertical-expand-lg" :
				"menubar-vertical-expand-md");

		return super.processCssClasses(cssClasses);
	}

	@Override
	protected int processStartTag() throws Exception {
		super.processStartTag();

		JspWriter jspWriter = pageContext.getOut();

		jspWriter.write("<div class=\"collapse menubar-collapse\">");

		_renderVerticalNavItems(jspWriter, _verticalNavItems, 0);

		jspWriter.write("</div>");

		return EVAL_BODY_INCLUDE;
	}

	private void _renderVerticalNavItems(
			JspWriter jspWriter, List<VerticalNavItem> verticalNavItems,
			int depth)
		throws Exception {

		jspWriter.write("<ul aria-orientation=\"vertical\" role=\"menubar\"");

		jspWriter.write("class=\"nav ");

		if (depth == 0) {
			jspWriter.write("nav-nested\">");
		}
		else {
			jspWriter.write("nav-stacked\">");
		}

		for (VerticalNavItem verticalNavItem : verticalNavItems) {
			VerticalNavItemList items =
				(VerticalNavItemList)verticalNavItem.get("items");

			Boolean active = (Boolean)verticalNavItem.get("active");

			if (active == null) {
				active = Boolean.FALSE;
			}

			Boolean expanded = (Boolean)verticalNavItem.get("expanded");

			if (expanded == null) {
				expanded = Boolean.FALSE;
			}

			String href = (String)verticalNavItem.get("href");

			boolean button = false;

			if ((items != null) || Validator.isNull(href)) {
				button = true;
			}

			jspWriter.write("<li role=\"none\" class=\"nav-item\">");

			if (button) {
				jspWriter.write("<button class=\"nav-link collapse-icon");

				if (!expanded) {
					jspWriter.write(" collapsed");
				}

				if (active) {
					jspWriter.write(" active");
				}

				jspWriter.write(" btn btn-unstyled\" type=\"button\"");
				jspWriter.write(" aria-expanded=\"");
				jspWriter.write(expanded.toString());
				jspWriter.write("\" aria-haspopup=\"true\"");
				jspWriter.write(" role=\"button\" tabindex=\"-1\">");
			}
			else {
				jspWriter.write("<a class=\"nav-link ");
				jspWriter.write(" role=\"menuitem\" tabindex=\"-1\"");

				if (active) {
					jspWriter.write(" active");
				}

				jspWriter.write(" href=\"");
				jspWriter.write((String)verticalNavItem.get("href"));
				jspWriter.write("\">");
			}

			jspWriter.write((String)verticalNavItem.get("label"));

			if (items != null) {
				IconTag iconTag = new IconTag();

				if (expanded) {
					jspWriter.write("<span class=\"collapse-icon-open\">");
					iconTag.setSymbol("caret-bottom");
				}
				else {
					jspWriter.write("<span class=\"collapse-icon-closed\">");
					iconTag.setSymbol("caret-right");
				}

				iconTag.doTag(pageContext);
				jspWriter.write("</span>");
			}

			if (button) {
				jspWriter.write("</button>");
			}
			else {
				jspWriter.write("</a>");
			}

			if ((items != null) && expanded) {
				_renderVerticalNavItems(jspWriter, items, depth++);
			}

			jspWriter.write("</li>");
		}

		jspWriter.write("</ul>");
	}

	private static final String _ATTRIBUTE_NAMESPACE = "clay:vertical_nav:";

	private boolean _decorated;
	private boolean _large;
	private List<VerticalNavItem> _verticalNavItems;

}