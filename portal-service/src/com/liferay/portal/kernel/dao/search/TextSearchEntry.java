/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.dao.search;

import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;

import java.util.Map;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

/**
 * @author Brian Wing Shun Chan
 */
public class TextSearchEntry extends SearchEntry {

	@Override
	public Object clone() {
		TextSearchEntry textSearchEntry = new TextSearchEntry();

		BeanPropertiesUtil.copyProperties(this, textSearchEntry);

		return textSearchEntry;
	}

	public Map<String, Object> getData() {
		return _data;
	}

	public String getHref() {
		return _href;
	}

	public String getName() {
		return _name;
	}

	public String getName(PageContext pageContext) {
		return getName();
	}

	public String getTarget() {
		return _target;
	}

	public String getTitle() {
		return _title;
	}

	@Override
	public void print(PageContext pageContext) throws Exception {
		if (_href == null) {
			pageContext.getOut().print(getName(pageContext));
		}
		else {
			StringBundler sb = new StringBundler();

			sb.append("<a");

			if (_data != null) {
				for (Map.Entry<String, Object> entry : _data.entrySet()) {
					String key = entry.getKey();
					String value = String.valueOf(entry.getValue());

					sb.append(" data-");
					sb.append(key);
					sb.append("=\"");
					sb.append(value);
					sb.append("\"");
				}
			}

			sb.append(" href=\"");

			if (_href.startsWith("javascript:")) {
				sb.append(_href);
			}
			else {
				sb.append(HtmlUtil.escape(_href));
			}

			sb.append("\"");

			if (Validator.isNotNull(_target)) {
				sb.append(" target=\"");
				sb.append(_target);
				sb.append("\"");
			}

			if (Validator.isNotNull(_title)) {
				sb.append(" title=\"");
				sb.append(_title);
				sb.append("\"");
			}

			sb.append(">");
			sb.append(getName(pageContext));
			sb.append("</a>");

			JspWriter jspWriter = pageContext.getOut();

			jspWriter.print(sb.toString());
		}
	}

	public void setData(Map<String, Object> data) {
		_data = data;
	}

	public void setHref(String href) {
		_href = href;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setTarget(String target) {
		_target = target;
	}

	public void setTitle(String title) {
		_title = title;
	}

	private Map<String, Object> _data;
	private String _href;
	private String _name;
	private String _target;
	private String _title;

}