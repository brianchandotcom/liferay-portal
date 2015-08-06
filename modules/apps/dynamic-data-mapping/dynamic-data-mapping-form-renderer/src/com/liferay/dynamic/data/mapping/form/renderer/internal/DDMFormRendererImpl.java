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
import com.liferay.dynamic.data.mapping.io.DDMFormFieldTypesJSONSerializerUtil;
import com.liferay.dynamic.data.mapping.io.DDMFormJSONSerializerUtil;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesJSONSerializerUtil;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.registry.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.registry.DDMFormFieldTypeRegistryUtil;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.expression.ExpressionFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.template.URLTemplateResource;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.Writer;

import java.net.URL;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(
	immediate = true, property = {"templatePath=/META-INF/resources/form.soy"},
	service = {DDMFormRenderer.class}
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
			return doRender(ddmForm, ddmFormRenderingContext);
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

		template.put(TemplateConstants.NAMESPACE, "ddm.multiple_page_form");

		populateCommonContext(template, ddmForm, ddmFormRenderingContext);

		List<Object> pages = getPages(
			ddmForm, ddmFormLayout, ddmFormRenderingContext);

		template.put("pages", pages);

		return render(template);
	}

	protected String doRender(
			DDMForm ddmForm, DDMFormRenderingContext ddmFormRenderingContext)
		throws PortalException {

		Template template = TemplateManagerUtil.getTemplate(
			TemplateConstants.LANG_TYPE_SOY, _templateResource, false);

		template.put(TemplateConstants.NAMESPACE, "ddm.single_page_form");

		populateCommonContext(template, ddmForm, ddmFormRenderingContext);

		List<String> fields = getFields(ddmForm, ddmFormRenderingContext);

		template.put("fields", fields);

		return render(template);
	}

	protected List<String> getFields(
			DDMForm ddmForm, DDMFormRenderingContext ddmFormRenderingContext)
		throws DDMFormRenderingException {

		Map<String, String> renderedDDMFormFieldsMap =
			getRenderedDDMFormFieldsMap(ddmForm, ddmFormRenderingContext);

		DDMFormTransformer ddmFormTransformer = new DDMFormTransformer(
			ddmForm, renderedDDMFormFieldsMap);

		return ddmFormTransformer.getFields();
	}

	protected List<Object> getPages(
			DDMForm ddmForm, DDMFormLayout ddmFormLayout,
			DDMFormRenderingContext ddmFormRenderingContext)
		throws DDMFormRenderingException {

		Map<String, String> renderedDDMFormFieldsMap =
			getRenderedDDMFormFieldsMap(ddmForm, ddmFormRenderingContext);

		DDMFormLayoutTransformer ddmFormLayoutTransformer =
			new DDMFormLayoutTransformer(
				ddmFormLayout, renderedDDMFormFieldsMap,
				ddmFormRenderingContext.getLocale());

		return ddmFormLayoutTransformer.getPages();
	}

	protected Map<String, String> getRenderedDDMFormFieldsMap(
			DDMForm ddmForm, DDMFormRenderingContext ddmFormRenderingContext)
		throws DDMFormRenderingException {

		DDMFormRendererHelper ddmFormRendererHelper = new DDMFormRendererHelper(
			ddmForm, ddmFormRenderingContext);

		ddmFormRendererHelper.setExpressionEvaluator(
			new ExpressionEvaluator(_expressionFactory));

		return ddmFormRendererHelper.getRenderedDDMFormFieldsMap();
	}

	protected TemplateResource getTemplateResource(String templatePath) {
		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		URL templateURL = classLoader.getResource(templatePath);

		return new URLTemplateResource(templateURL.getPath(), templateURL);
	}

	protected void populateCommonContext(
			Template template, DDMForm ddmForm,
			DDMFormRenderingContext ddmFormRenderingContext)
		throws PortalException {

		template.put("containerId", StringUtil.randomId());
		template.put(
			"definition", DDMFormJSONSerializerUtil.serialize(ddmForm));

		List<DDMFormFieldType> ddmFormFieldTypes =
			DDMFormFieldTypeRegistryUtil.getDDMFormFieldTypes();

		template.put(
			"fieldTypes",
			DDMFormFieldTypesJSONSerializerUtil.serialize(ddmFormFieldTypes));
		template.put(
			"portletNamespace", ddmFormRenderingContext.getPortletNamespace());

		DDMFormValues ddmFormValues =
			ddmFormRenderingContext.getDDMFormValues();

		if (ddmFormValues != null) {
			template.put(
				"values",
				DDMFormValuesJSONSerializerUtil.serialize(ddmFormValues));
		}
		else {
			template.put("values", JSONFactoryUtil.getNullJSON());
		}
	}

	protected String render(Template template) throws TemplateException {
		Writer writer = new UnsyncStringWriter();

		template.processTemplate(writer);

		return writer.toString();
	}

	@Reference
	protected void setExpressionFactory(ExpressionFactory expressionFactory) {
		_expressionFactory = expressionFactory;
	}

	private ExpressionFactory _expressionFactory;
	private TemplateResource _templateResource;

}