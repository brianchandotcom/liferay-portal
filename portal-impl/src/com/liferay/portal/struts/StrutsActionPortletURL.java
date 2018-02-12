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

package com.liferay.portal.struts;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portlet.PortletResponseImpl;
import com.liferay.portlet.PortletURLImplWrapper;

import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class StrutsActionPortletURL extends PortletURLImplWrapper {

	public StrutsActionPortletURL(
		PortletResponseImpl portletResponseImpl, long plid, String lifecycle) {

		super(portletResponseImpl, plid, lifecycle);

		_portlet = portletResponseImpl.getPortlet();

		_strutsPath =
			StringPool.SLASH + _portlet.getStrutsPath() + StringPool.SLASH;
	}

	@Override
	public void setParameter(String name, String value) {
		if (name.equals("struts_action")) {
			if (!value.startsWith(_strutsPath)) {
				int pos = value.lastIndexOf(CharPool.SLASH);

				value = _strutsPath + value.substring(pos + 1);
			}
		}

		super.setParameter(name, value);
	}

	@Override
	public void setParameters(Map<String, String[]> params) {
		for (Map.Entry<String, String[]> entry : params.entrySet()) {
			String name = entry.getKey();
			String[] values = entry.getValue();

			if (name.equals("struts_action")) {
				for (int i = 0; i < values.length; i++) {
					String value = values[i];

					if (!value.startsWith(_strutsPath)) {
						int pos = value.lastIndexOf(CharPool.SLASH);

						value = _strutsPath + value.substring(pos + 1);

						values[i] = value;
					}
				}
			}
		}

		super.setParameters(params);
	}

	private final Portlet _portlet;
	private final String _strutsPath;

}