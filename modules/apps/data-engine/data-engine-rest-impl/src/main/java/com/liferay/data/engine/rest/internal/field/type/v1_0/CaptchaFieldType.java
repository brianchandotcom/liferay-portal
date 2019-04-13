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

package com.liferay.data.engine.rest.internal.field.type.v1_0;

import com.liferay.captcha.taglib.servlet.taglib.CaptchaTag;
import com.liferay.data.engine.rest.dto.v1_0.DataDefinitionField;
import com.liferay.data.engine.rest.internal.field.type.v1_0.util.CustomPropertyUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.template.soy.data.SoyDataFactory;
import com.liferay.taglib.servlet.PageContextFactoryUtil;
import com.liferay.taglib.servlet.PipingServletResponse;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Marcela Cunha
 */
public class CaptchaFieldType extends FieldType {

	public CaptchaFieldType(
		DataDefinitionField dataDefinitionField,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse,
		SoyDataFactory soyDataFactory) {

		super(
			dataDefinitionField, httpServletRequest, httpServletResponse,
			soyDataFactory);
	}

	@Override
	protected void addContext(Map<String, Object> context) {
		String html = StringPool.BLANK;

		try {
			html = _renderCaptchaTag(
				dataDefinitionField, httpServletRequest, httpServletResponse);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		context.put("html", soyDataFactory.createSoyHTMLData(html));
	}

	private String _renderCaptchaTag(
			DataDefinitionField dataDefinitionField,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		CaptchaTag captchaTag = new CaptchaTag() {
			{
				setPageContext(
					PageContextFactoryUtil.create(
						httpServletRequest,
						new PipingServletResponse(
							httpServletResponse, unsyncStringWriter)));
				setUrl(
					CustomPropertyUtil.getString(
						dataDefinitionField.getCustomProperties(), "url"));
			}
		};

		captchaTag.runTag();

		return unsyncStringWriter.toString();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CaptchaFieldType.class);

}