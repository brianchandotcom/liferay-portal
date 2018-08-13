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

package com.liferay.portlet.dependency.factory.internal;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.portlet.PortletDependency;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.url.builder.AbsolutePortalURLBuilder;
import com.liferay.portal.url.builder.BuildableAbsolutePortalURLBuilder;
import com.liferay.portal.util.PropsValues;

/**
 * @author Neil Griffin
 */
public class PortletDependencyImpl implements PortletDependency {

	public PortletDependencyImpl(
		String name, String scope, String version, String markup,
		AbsolutePortalURLBuilder absolutePortalURLBuilder) {

		_name = name;
		_scope = scope;
		_version = version;
		_markup = markup;
		_absolutePortalURLBuilder = absolutePortalURLBuilder;

		if (_name == null) {
			_type = Type.OTHER;
		}
		else if (_name.endsWith(".css")) {
			_type = Type.CSS;
		}
		else if (_name.endsWith(".js")) {
			_type = Type.JAVASCRIPT;
		}
		else if (Validator.isNotNull(markup)) {
			markup = StringUtil.trim(markup);

			if (markup.regionMatches(true, 0, "<script", 0, 7)) {
				_type = Type.JAVASCRIPT;
			}
			else if (markup.regionMatches(true, 0, "<link", 0, 5)) {
				_type = Type.CSS;
			}
			else {
				_type = Type.OTHER;
			}
		}
		else {
			_type = Type.OTHER;
		}
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public String getScope() {
		return _scope;
	}

	@Override
	public Type getType() {
		return _type;
	}

	@Override
	public String getVersion() {
		return _version;
	}

	@Override
	public String toString() {
		StringBundler sb = toStringBundler();

		return sb.toString();
	}

	@Override
	public StringBundler toStringBundler() {
		if (Validator.isNull(_markup)) {
			StringBundler sb = new StringBundler(8);

			if (_type == Type.CSS) {
				sb.append("<link ");
				sb.append("href=\"");
				sb.append(_getURL());
				sb.append("\" type=\"text/css\"></link>");
			}
			else if (_type == Type.JAVASCRIPT) {
				sb.append("<script ");
				sb.append("src=\"");
				sb.append(_getURL());
				sb.append("\" type=\"text/javascript\"></script>");
			}
			else {
				sb.append("<!-- Unknown portlet resource dependency type ");
				sb.append("name=\"");
				sb.append(_name);
				sb.append("\" scope=\"");
				sb.append(_scope);
				sb.append("\" version=\"");
				sb.append(_version);
				sb.append("\" -->");
			}

			return sb;
		}

		return new StringBundler(_markup);
	}

	private String _getURL() {
		BuildableAbsolutePortalURLBuilder buildableAbsolutePortalURLBuilder =
			_absolutePortalURLBuilder.forPortletDependency(
				this, PropsValues.PORTLET_DEPENDENCY_CSS_URN,
				PropsValues.PORTLET_DEPENDENCY_JAVASCRIPT_URN);

		return buildableAbsolutePortalURLBuilder.build();
	}

	private static final long serialVersionUID = 1L;

	private final AbsolutePortalURLBuilder _absolutePortalURLBuilder;
	private final String _markup;
	private final String _name;
	private final String _scope;
	private final Type _type;
	private final String _version;

}