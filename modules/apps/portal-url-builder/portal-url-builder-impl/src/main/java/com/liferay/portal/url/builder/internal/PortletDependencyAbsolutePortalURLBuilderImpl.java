/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.url.builder.internal;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.portlet.PortletDependency;
import com.liferay.portal.kernel.servlet.PortalWebResourceConstants;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.url.builder.PortletDependencyAbsolutePortalURLBuilder;
import com.liferay.portal.url.builder.internal.util.URLUtil;

/**
 * @author Iván Zaera Avellón
 */
public class PortletDependencyAbsolutePortalURLBuilderImpl
	implements PortletDependencyAbsolutePortalURLBuilder {

	public PortletDependencyAbsolutePortalURLBuilderImpl(
		String cdnHost, String cssURN, String javaScriptURN, String pathProxy,
		PortletDependency portletDependency) {

		_cdnHost = cdnHost;
		_cssURN = cssURN;
		_javaScriptURN = javaScriptURN;
		_pathProxy = pathProxy;
		_portletDependency = portletDependency;
	}

	@Override
	public String build() {
		StringBundler relativeURLSB = new StringBundler(7);

		boolean ignoreCDNHost = _ignoreCDNHost;
		boolean ignorePathProxy = false;

		if (PortletDependency.Type.CSS == _portletDependency.getType()) {
			String resourcePath = _cssURN;

			if (Validator.isNull(resourcePath)) {
				resourcePath = PortalWebResourceConstants.RESOURCE_TYPE_CSS;
			}
			else {
				ignoreCDNHost = true;
				ignorePathProxy = true;
			}

			relativeURLSB.append(resourcePath);
		}
		else if (PortletDependency.Type.JAVASCRIPT ==
					_portletDependency.getType()) {

			String resourcePath = _javaScriptURN;

			if (Validator.isNull(resourcePath)) {
				resourcePath = PortalWebResourceConstants.RESOURCE_TYPE_JS;
			}
			else {
				ignoreCDNHost = true;
				ignorePathProxy = true;
			}

			relativeURLSB.append(resourcePath);
		}

		if (Validator.isNotNull(_portletDependency.getScope())) {
			relativeURLSB.append(StringPool.FORWARD_SLASH);
			relativeURLSB.append(_portletDependency.getScope());
		}

		if (Validator.isNotNull(_portletDependency.getVersion())) {
			relativeURLSB.append(StringPool.FORWARD_SLASH);
			relativeURLSB.append(_portletDependency.getVersion());
		}

		relativeURLSB.append(StringPool.FORWARD_SLASH);
		relativeURLSB.append(_portletDependency.getName());

		StringBundler sb = new StringBundler();

		URLUtil.appendURL(
			sb, _cdnHost, ignoreCDNHost, ignorePathProxy, StringPool.BLANK,
			_pathProxy, relativeURLSB.toString());

		return sb.toString();
	}

	@Override
	public PortletDependencyAbsolutePortalURLBuilder ignoreCDNHost() {
		_ignoreCDNHost = true;

		return this;
	}

	private final String _cdnHost;
	private final String _cssURN;
	private boolean _ignoreCDNHost;
	private final String _javaScriptURN;
	private final String _pathProxy;
	private final PortletDependency _portletDependency;

}