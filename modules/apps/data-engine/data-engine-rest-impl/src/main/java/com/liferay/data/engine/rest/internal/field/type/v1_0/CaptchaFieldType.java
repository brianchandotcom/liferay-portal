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
import com.liferay.data.engine.field.type.BaseFieldType;
import com.liferay.data.engine.field.type.FieldType;
import com.liferay.data.engine.spi.dto.SPIDataDefinitionField;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.template.soy.data.SoyDataFactory;
import com.liferay.taglib.servlet.PageContextFactoryUtil;
import com.liferay.taglib.servlet.PipingServletResponse;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcela Cunha
 */
@Component(
	immediate = true,
	property = {
		"data.engine.field.type.js.module=dynamic-data-mapping-form-field-type/Captcha/Captcha.es",
		"data.engine.field.type.system=true"
	},
	service = FieldType.class
)
public class CaptchaFieldType extends BaseFieldType {

	@Override
	public String getName() {
		return "captcha";
	}

	@Override
	protected void includeContext(
		Map<String, Object> context, HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse,
		SPIDataDefinitionField spiDataDefinitionField) {

		String html = StringPool.BLANK;

		try {
			html = _renderCaptchaTag(
				context, httpServletRequest, httpServletResponse);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		context.put("html", _soyDataFactory.createSoyRawData(html));
	}

	private String _renderCaptchaTag(
			Map<String, Object> context, HttpServletRequest httpServletRequest,
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
				setUrl(MapUtil.getString(context, "url"));
			}
		};

		captchaTag.runTag();

		return unsyncStringWriter.toString();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CaptchaFieldType.class);

	@Reference
	private SoyDataFactory _soyDataFactory;

}