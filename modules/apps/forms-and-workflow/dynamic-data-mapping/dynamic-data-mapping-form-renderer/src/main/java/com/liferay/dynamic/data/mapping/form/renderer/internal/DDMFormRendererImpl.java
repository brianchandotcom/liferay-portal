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

import com.liferay.dynamic.data.mapping.form.field.type.BaseDDMFormFieldRenderer;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldRenderer;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderer;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingContext;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingException;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormTemplateContextFactory;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.util.DDM;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONSerializer;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.template.URLTemplateResource;
import com.liferay.portal.kernel.util.MapUtil;

import java.io.Writer;

import java.net.URL;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

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

		TemplateResource formTemplateResource = getFormTemplateResource(
			templatePath);

		_templateResources.add(formTemplateResource);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		unbind = "removeDDMFormFieldRenderer"
	)
	protected void addDDMFormFieldRenderer(
		DDMFormFieldRenderer ddmFormFieldRenderer) {

		TemplateResource templateResource = getTemplateResource(
			ddmFormFieldRenderer);

		if (templateResource != null) {
			_templateResources.add(templateResource);
		}
	}

	protected String doRender(
			DDMForm ddmForm, DDMFormLayout ddmFormLayout,
			DDMFormRenderingContext ddmFormRenderingContext)
		throws PortalException {

		Template template = TemplateManagerUtil.getTemplate(
			TemplateConstants.LANG_TYPE_SOY, _templateResources, false);

		populateCommonContext(
			template, ddmForm, ddmFormLayout, ddmFormRenderingContext);

		String templateNamespace = (String)template.get("templateNamespace");

		String html = render(template, templateNamespace);

		String javaScript = render(template, "ddm.form_renderer_js");

		return html.concat(javaScript);
	}

	protected TemplateResource getFormTemplateResource(String templatePath) {
		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		URL templateURL = classLoader.getResource(templatePath);

		return new URLTemplateResource(templateURL.getPath(), templateURL);
	}

	protected TemplateResource getTemplateResource(
		DDMFormFieldRenderer ddmFormFieldRenderer) {

		if (ddmFormFieldRenderer instanceof BaseDDMFormFieldRenderer) {
			BaseDDMFormFieldRenderer baseDDMFormFieldRenderer =
				(BaseDDMFormFieldRenderer)ddmFormFieldRenderer;

			return baseDDMFormFieldRenderer.getTemplateResource();
		}

		return null;
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

		Map<String, Object> ddmFormTemplateContext =
			_ddmFormTemplateContextFactory.create(
				ddmForm, ddmFormLayout, ddmFormRenderingContext);

		template.putAll(ddmFormTemplateContext);

		ddmFormTemplateContext.remove("fieldTypes");

		JSONSerializer jsonSerializer = _jsonFactory.createJSONSerializer();

		template.put(
			"context", jsonSerializer.serializeDeep(ddmFormTemplateContext));
	}

	protected void removeDDMFormFieldRenderer(
		DDMFormFieldRenderer ddmFormFieldRenderer) {

		TemplateResource templateResource = getTemplateResource(
			ddmFormFieldRenderer);

		if (templateResource != null) {
			_templateResources.remove(templateResource);
		}
	}

	protected String render(Template template, String namespace)
		throws TemplateException {

		Writer writer = new UnsyncStringWriter();

		template.put(TemplateConstants.NAMESPACE, namespace);
		template.put(TemplateConstants.RENDER_STRICT, Boolean.FALSE);

		template.processTemplate(writer);

		return writer.toString();
	}

	@Reference
	private DDM _ddm;

	@Reference
	private DDMFormTemplateContextFactory _ddmFormTemplateContextFactory;

	@Reference
	private JSONFactory _jsonFactory;

	private final List<TemplateResource> _templateResources =
		new CopyOnWriteArrayList<>();

}