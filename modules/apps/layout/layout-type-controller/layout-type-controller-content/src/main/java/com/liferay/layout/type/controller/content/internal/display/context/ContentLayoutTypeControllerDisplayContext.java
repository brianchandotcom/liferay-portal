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

package com.liferay.layout.type.controller.content.internal.display.context;

import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.util.FragmentEntryRenderUtil;
import com.liferay.layout.type.controller.content.internal.constants.ContentLayoutTypeControllerWebKeys;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Juergen Kappler
 */
public class ContentLayoutTypeControllerDisplayContext {

	public ContentLayoutTypeControllerDisplayContext(
		HttpServletRequest request, HttpServletResponse response) {

		_request = request;
		_response = response;
	}

	public String getRenderedContent() throws PortalException {
		List<FragmentEntryLink> fragmentEntryLinks =
			(List<FragmentEntryLink>)_request.getAttribute(
				ContentLayoutTypeControllerWebKeys.LAYOUT_FRAGMENTS);

		StringBundler sb = new StringBundler(fragmentEntryLinks.size());

		for (FragmentEntryLink fragmentEntryLink : fragmentEntryLinks) {
			sb.append(
				FragmentEntryRenderUtil.renderFragmentEntryLink(
					fragmentEntryLink, _request, _response));
		}

		String renderedContent = sb.toString();

		if (Validator.isNull(renderedContent)) {
			return StringPool.BLANK;
		}

		return renderedContent;
	}

	private final HttpServletRequest _request;
	private final HttpServletResponse _response;

}