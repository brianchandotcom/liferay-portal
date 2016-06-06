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

import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderer;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingContext;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingException;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormTemplateContextFactory;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.util.DDM;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.template.URLTemplateResource;
import com.liferay.portal.kernel.util.MapUtil;

import java.io.Writer;

import java.net.URL;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(
	immediate = true, property = {"templatePath=/META-INF/resources/form.soy"}
)
public class DDMFormRendererImpl implements DDMFormRenderer {

	@Override
	public String render(
			DDMForm ddmForm, DDMFormLayout ddmFormLayout,
			DDMFormRenderingContext ddmFormRenderingContext)
		throws DDMFormRenderingException {

		try {
			return doRender(ddmForm, ddmFormLayout, ddmFormRenderingContext);
		}
		catch (DDMFormRenderingException ddmfre) {
			throw ddmfre;
		}
		catch (PortalException pe) {
			throw new DDMFormRenderingException(pe);
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
		catch (DDMFormRenderingException ddmfre) {
			throw ddmfre;
		}
		catch (PortalException pe) {
			throw new DDMFormRenderingException(pe);
		}
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		String templatePath = MapUtil.getString(properties, "templatePath");

		_templateResource = getTemplateResource(templatePath);
	}

	protected String doRender(
			DDMForm ddmForm, DDMFormLayout ddmFormLayout,
			DDMFormRenderingContext ddmFormRenderingContext)
		throws PortalException {

		Template template = TemplateManagerUtil.getTemplate(
			TemplateConstants.LANG_TYPE_SOY, _templateResource, false);

		populateCommonContext(
			template, ddmForm, ddmFormLayout, ddmFormRenderingContext);

		String templateNamespace = (String)template.get("templateNamespace");

		String html = render(template, templateNamespace);

		String javaScript = render(template, "ddm.form_renderer_js");

		return html.concat(javaScript);
	}

	protected TemplateResource getTemplateResource(String templatePath) {
		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		URL templateURL = classLoader.getResource(templatePath);

		return new URLTemplateResource(templateURL.getPath(), templateURL);
	}

	protected void populateCommonContext(
			Template template, DDMForm ddmForm, DDMFormLayout ddmFormLayout,
			DDMFormRenderingContext ddmFormRenderingContext)
		throws PortalException {

		Map<String, Object> templateContext =
			_ddmFormTemplateContextFactory.create(
				ddmForm, ddmFormLayout, ddmFormRenderingContext);

		template.putAll(templateContext);
	}

	protected String render(Template template, String namespace)
		throws TemplateException {

		Writer writer = new UnsyncStringWriter();

		template.put(TemplateConstants.NAMESPACE, namespace);
		template.put(TemplateConstants.RENDER_STRICT, Boolean.FALSE);

		template.processTemplate(writer);

		return writer.toString();
	}

	@Reference(unbind = "-")
	protected void setDDM(DDM ddm) {
		_ddm = ddm;
	}

	@Reference
	private DDM _ddm;

	@Reference
	private DDMFormTemplateContextFactory _ddmFormTemplateContextFactory;

	private TemplateResource _templateResource;

}