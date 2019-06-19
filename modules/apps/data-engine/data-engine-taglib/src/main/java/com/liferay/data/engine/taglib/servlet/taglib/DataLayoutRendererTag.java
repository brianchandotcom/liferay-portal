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

package com.liferay.data.engine.taglib.servlet.taglib;

import com.liferay.data.engine.taglib.servlet.taglib.base.BaseDataLayoutRendererTag;
import com.liferay.data.engine.taglib.servlet.taglib.util.DataLayoutTaglibUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.PortalUtil;

import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jeyvison Nascimento
 */
public class DataLayoutRendererTag extends BaseDataLayoutRendererTag {

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		super.setAttributes(httpServletRequest);

		setNamespacedAttribute(httpServletRequest, "content", _getContent());
	}

	private String _getContent() {
		String content = StringPool.BLANK;

		try {
			content = DataLayoutTaglibUtil.renderDataLayout(
				getDataLayoutId(), getDataRecordId(), request,
				PortalUtil.getHttpServletResponse(
					(RenderResponse)request.getAttribute(
						JavaConstants.JAVAX_PORTLET_RESPONSE)));
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}

		return content;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DataLayoutRendererTag.class);

}