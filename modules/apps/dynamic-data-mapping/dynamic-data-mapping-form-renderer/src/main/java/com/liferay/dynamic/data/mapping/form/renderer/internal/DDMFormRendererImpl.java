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

package com.liferay.dynamic.data.mapping.form.renderer.internal;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderer;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingContext;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingException;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormTemplateContextFactory;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.util.DDM;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.petra.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.template.react.renderer.ComponentDescriptor;
import com.liferay.portal.template.react.renderer.ReactRenderer;

import java.io.Writer;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(immediate = true, service = DDMFormRenderer.class)
public class DDMFormRendererImpl implements DDMFormRenderer {

	@Override
	public Map<String, Object> getContext(
			DDMForm ddmForm, DDMFormLayout ddmFormLayout,
			DDMFormRenderingContext ddmFormRenderingContext)
		throws Exception {

		Map<String, Object> ddmFormTemplateContext =
			_ddmFormTemplateContextFactory.create(
				ddmForm, ddmFormLayout, ddmFormRenderingContext);

		ddmFormTemplateContext.put("editable", false);

		ddmFormTemplateContext.remove("fieldTypes");

		HttpServletRequest httpServletRequest =
			ddmFormRenderingContext.getHttpServletRequest();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		String pathThemeImages = themeDisplay.getPathThemeImages();

		String spriteMap = pathThemeImages.concat("/clay/icons.svg");

		ddmFormTemplateContext.put("spritemap", spriteMap);

		return ddmFormTemplateContext;
	}

	@Override
	public String render(
			DDMForm ddmForm, DDMFormLayout ddmFormLayout,
			DDMFormRenderingContext ddmFormRenderingContext)
		throws DDMFormRenderingException {

		try {
			return doRender(ddmForm, ddmFormLayout, ddmFormRenderingContext);
		}
		catch (DDMFormRenderingException ddmFormRenderingException) {
			throw ddmFormRenderingException;
		}
		catch (Exception exception) {
			throw new DDMFormRenderingException(exception);
		}
	}

	@Override
	public String render(
			DDMForm ddmForm, DDMFormRenderingContext ddmFormRenderingContext)
		throws DDMFormRenderingException {

		try {
			return doRender(
				ddmForm, _ddm.getDefaultDDMFormLayout(ddmForm),
				ddmFormRenderingContext);
		}
		catch (DDMFormRenderingException ddmFormRenderingException) {
			throw ddmFormRenderingException;
		}
		catch (Exception exception) {
			throw new DDMFormRenderingException(exception);
		}
	}

	protected String doRender(
			DDMForm ddmForm, DDMFormLayout ddmFormLayout,
			DDMFormRenderingContext ddmFormRenderingContext)
		throws Exception {

		Writer writer = new UnsyncStringWriter();

		writer.append("<div id=\"");
		writer.append(ddmFormRenderingContext.getContainerId());
		writer.append("\">");

		_reactRenderer.renderReact(
			new ComponentDescriptor(
				_npmResolver.resolveModuleName(_MODULE_NAME),
				ddmFormRenderingContext.getContainerId()),
			getContext(ddmForm, ddmFormLayout, ddmFormRenderingContext),
			ddmFormRenderingContext.getHttpServletRequest(), writer);

		writer.append("</div>");

		return writer.toString();
	}

	private static final String _MODULE_NAME =
		"dynamic-data-mapping-form-renderer/js/containers/Form.es";

	@Reference
	private DDM _ddm;

	@Reference
	private DDMFormFieldTypeServicesTracker _ddmFormFieldTypeServicesTracker;

	@Reference
	private DDMFormTemplateContextFactory _ddmFormTemplateContextFactory;

	@Reference
	private NPMResolver _npmResolver;

	@Reference
	private ReactRenderer _reactRenderer;

}