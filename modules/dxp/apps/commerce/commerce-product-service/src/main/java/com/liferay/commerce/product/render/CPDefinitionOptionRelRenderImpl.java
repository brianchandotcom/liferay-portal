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

package com.liferay.commerce.product.render;

import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.service.CPDefinitionOptionRelService;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderer;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingContext;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Locale;

/**
 * @author Marco Leo
 */
@Component(
	immediate = true,
	service = CPDefinitionOptionRelRender.class
)
public class CPDefinitionOptionRelRenderImpl
	implements CPDefinitionOptionRelRender {

	@Override
	public String render(long cpDefinitionId,
						 HttpServletRequest httpServletRequest,
						 HttpServletResponse httpServletResponse)
		throws PortalException {

		DDMForm ddmForm = new DDMForm();

		List<CPDefinitionOptionRel> cpDefinitionOptionRels =
			_cpDefinitionOptionRelService.
				getSkuContributorCPDefinitionOptionRels(cpDefinitionId);

		for(CPDefinitionOptionRel cpDefinitionOptionRel
			: cpDefinitionOptionRels){

			List<CPDefinitionOptionValueRel> cpDefinitionOptionValueRels =
				cpDefinitionOptionRel.getCPDefinitionOptionValueRels();

			DDMFormField ddmFormField = new DDMFormField(String.valueOf(cpDefinitionOptionRel.getCPDefinitionOptionRelId()),cpDefinitionOptionRel.getDDMFormFieldTypeName());

			if(cpDefinitionOptionValueRels.size() > 0){

				DDMFormFieldOptions options = new DDMFormFieldOptions();

				options.addOptionLabel("key1", Locale.US , "label1");
				options.addOptionLabel("key2", Locale.US , "label2");
				options.addOptionLabel("key3", Locale.US , "label3");
				options.addOptionLabel("key5", Locale.US , "label4");


			}

			ddmFormField.setRequired(true);

		}





		LocalizedValue locval = new LocalizedValue(Locale.US);
		locval.addString(Locale.US,"testselect");

		ddmFormField.setLabel(locval);
		ddmFormField.setDDMFormFieldOptions(options);

		ddmForm.addDDMFormField(ddmFormField);

		DDMFormField ddmFormField2 = new DDMFormField("testtext","text");

		LocalizedValue locval2 = new LocalizedValue(Locale.US);
		locval.addString(Locale.US,"testtext");

		ddmFormField2.setLabel(locval);


		ddmForm.addDDMFormField(ddmFormField2);

		DDMFormRenderingContext context = new DDMFormRenderingContext();

		context.setLocale(Locale.US);
		context.setHttpServletRequest(httpServletRequest);
		context.setHttpServletResponse(httpServletResponse);
		context.setContainerId("testid");

		String html = _ddmFormRenderer.render(ddmForm,context);

		return html;
	}

	@Reference
	private DDMFormRenderer _ddmFormRenderer;

	@Reference
	private CPDefinitionOptionRelService _cpDefinitionOptionRelService;

}
