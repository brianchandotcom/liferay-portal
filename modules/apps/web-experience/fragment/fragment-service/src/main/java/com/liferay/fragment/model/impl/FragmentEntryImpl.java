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

package com.liferay.fragment.model.impl;

import com.liferay.portal.kernel.util.StringBundler;

/**
 * @author Eudaldo Alonso
 */
public class FragmentEntryImpl extends FragmentEntryBaseImpl {

	@Override
	public String getContent() {
		StringBundler sb = new StringBundler(8);

		sb.append("<html><head>");
		sb.append("<style>");
		sb.append(getCss());
		sb.append("</style><script>");
		sb.append(getJs());
		sb.append("</script></head><body>");
		sb.append(getHtml());
		sb.append("</body></html>");

		return sb.toString();
	}

}