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

import com.liferay.data.engine.renderer.DataLayoutRenderer;
import com.liferay.data.engine.renderer.DataLayoutRendererContext;
import com.liferay.data.engine.rest.dto.v2_0.DataDefinition;
import com.liferay.data.engine.rest.dto.v2_0.DataLayout;
import com.liferay.data.engine.taglib.internal.servlet.taglib.util.DataLayoutTaglibUtil;
import com.liferay.data.engine.taglib.servlet.taglib.base.BaseDataLayoutRendererTag;
import com.liferay.osgi.util.service.Snapshot;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;

import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

/**
 * @author Jeyvison Nascimento
 */
public class DataLayoutRendererTag extends BaseDataLayoutRendererTag {

	@Override
	public int doStartTag() throws JspException {
		int result = super.doStartTag();

		setNamespacedAttribute(getRequest(), "content", _getContent());

		return result;
	}

	private String _getContent() {
		String content = StringPool.BLANK;

		try {
			DataLayoutRendererContext dataLayoutRendererContext =
				new DataLayoutRendererContext();

			dataLayoutRendererContext.setContainerId(getContainerId());

			if (Validator.isNotNull(getContentType())) {
				dataLayoutRendererContext.setContentType(getContentType());
			}

			HttpServletRequest httpServletRequest = getRequest();

			if (Validator.isNotNull(getDataRecordId())) {
				dataLayoutRendererContext.setDataRecordValues(
					DataLayoutTaglibUtil.getDataRecordValues(
						getDataRecordId(), httpServletRequest));
			}
			else {
				dataLayoutRendererContext.setDataRecordValues(
					getDataRecordValues());
			}

			if (Validator.isNotNull(getDefaultLanguageId())) {
				dataLayoutRendererContext.setDefaultLanguageId(
					getDefaultLanguageId());
			}

			dataLayoutRendererContext.setHttpServletRequest(httpServletRequest);
			dataLayoutRendererContext.setHttpServletResponse(
				PortalUtil.getHttpServletResponse(
					(PortletResponse)httpServletRequest.getAttribute(
						JavaConstants.JAVAX_PORTLET_RESPONSE)));

			if (Validator.isNotNull(getLanguageId())) {
				dataLayoutRendererContext.setLanguageId(getLanguageId());
			}
			else {
				dataLayoutRendererContext.setLanguageId(
					LanguageUtil.getLanguageId(
						PortalUtil.getLocale(httpServletRequest)));
			}

			dataLayoutRendererContext.setPersistDefaultValues(
				getPersistDefaultValues());
			dataLayoutRendererContext.setPersisted(getPersisted());
			dataLayoutRendererContext.setPortletNamespace(getNamespace());
			dataLayoutRendererContext.setReadOnly(getReadOnly());
			dataLayoutRendererContext.setSubmittable(getSubmittable());

			if (Validator.isNotNull(getDataLayoutId())) {
				content = _renderDataLayout(
					getDataLayoutId(), dataLayoutRendererContext);
			}
			else if (Validator.isNotNull(getDataDefinitionId())) {
				DataDefinition dataDefinition =
					DataLayoutTaglibUtil.getDataDefinition(
						getDataDefinitionId(), httpServletRequest);

				DataLayout dataLayout = dataDefinition.getDefaultDataLayout();

				if (dataLayout != null) {
					content = _renderDataLayout(
						dataLayout.getId(), dataLayoutRendererContext);
				}
			}
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return content;
	}

	private String _renderDataLayout(
			Long dataLayoutId,
			DataLayoutRendererContext dataLayoutRendererContext)
		throws Exception {

		DataLayoutRenderer dataLayoutRenderer =
			_dataLayoutRendererSnapshot.get();

		return dataLayoutRenderer.render(
			dataLayoutId, dataLayoutRendererContext);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DataLayoutRendererTag.class);

	private static final Snapshot<DataLayoutRenderer>
		_dataLayoutRendererSnapshot = new Snapshot<>(
			DataLayoutRendererTag.class, DataLayoutRenderer.class);

}