/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.tagscompiler;

import com.liferay.portal.kernel.portlet.BaseFriendlyURLMapper;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Map;

import javax.portlet.PortletMode;
import javax.portlet.WindowState;

/**
 * @author Brian Wing Shun Chan
 */
public class TagsCompilerFriendlyURLMapper extends BaseFriendlyURLMapper {

	public String buildPath(LiferayPortletURL liferayPortletURL) {
		return null;
	}

	@Override
	public boolean isCheckMappingWithPrefix() {
		return _CHECK_MAPPING_WITH_PREFIX;
	}

	public void populateParams(
		String friendlyURLPath, Map<String, String[]> parameterMap,
		Map<String, Object> requestContext) {

		addParameter(parameterMap, "p_p_id", getPortletId());
		addParameter(parameterMap, "p_p_lifecycle", "0");
		addParameter(parameterMap, "p_p_state", WindowState.NORMAL);
		addParameter(parameterMap, "p_p_mode", PortletMode.VIEW);

		int x = friendlyURLPath.indexOf(CharPool.SLASH, 1);
		int y = friendlyURLPath.length();

		String[] entries = StringUtil.split(
			friendlyURLPath.substring(x + 1, y), CharPool.SLASH);

		if (entries.length > 0) {
			StringBundler sb = new StringBundler(entries.length * 2 - 1);

			for (int i = 0; i < entries.length; i++) {
				String entry = StringUtil.replace(
					entries[i], CharPool.PLUS, CharPool.SPACE);

				if (i != 0) {
					sb.append(StringPool.COMMA);
				}

				sb.append(entry);
			}

			addParameter(parameterMap, "entries", sb.toString());
		}
	}

	private static final boolean _CHECK_MAPPING_WITH_PREFIX = false;

}