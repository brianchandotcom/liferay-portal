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

package com.liferay.commerce.product.internal.renderer;

import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.renderer.CPDefinitionOptionRelRenderer;
import com.liferay.commerce.product.service.CPDefinitionOptionRelService;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderer;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingContext;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.Portal;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(immediate = true)
public class CPDefinitionOptionRelRendererImpl
	implements CPDefinitionOptionRelRenderer {

	@Override
	public String render(
			long cpDefinitionId, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws PortalException {

		DDMForm ddmForm = new DDMForm();

		Locale locale = _portal.getLocale(httpServletRequest);

		List<CPDefinitionOptionRel> cpDefinitionOptionRels =
			_cpDefinitionOptionRelService.
				getSkuContributorCPDefinitionOptionRels(cpDefinitionId);

		for (CPDefinitionOptionRel cpDefinitionOptionRel :
				cpDefinitionOptionRels) {

			List<CPDefinitionOptionValueRel> cpDefinitionOptionValueRels =
				cpDefinitionOptionRel.getCPDefinitionOptionValueRels();

			DDMFormField ddmFormField = new DDMFormField(
				String.valueOf(cpDefinitionOptionRel.
					getCPDefinitionOptionRelId()),
				cpDefinitionOptionRel.getDDMFormFieldTypeName());

			if (!cpDefinitionOptionValueRels.isEmpty()) {
				DDMFormFieldOptions ddmFormFieldOptions =
					new DDMFormFieldOptions();

				for (CPDefinitionOptionValueRel cpDefinitionOptionValueRel :
						cpDefinitionOptionValueRels) {

					ddmFormFieldOptions.addOptionLabel(
						String.valueOf(cpDefinitionOptionValueRel.
							getCPDefinitionOptionValueRelId()),
						locale, cpDefinitionOptionValueRel.getTitle(locale));
				}

				ddmFormField.setDDMFormFieldOptions(ddmFormFieldOptions);
			}

			LocalizedValue localizedValue = new LocalizedValue(locale);

			localizedValue.addString(
				locale, cpDefinitionOptionRel.getName(locale));

			ddmFormField.setLabel(localizedValue);

			ddmFormField.setRequired(true);

			ddmForm.addDDMFormField(ddmFormField);
		}

		DDMFormRenderingContext ddmFormRenderingContext =
			new DDMFormRenderingContext();

		ddmFormRenderingContext.setContainerId("options");
		ddmFormRenderingContext.setHttpServletRequest(httpServletRequest);
		ddmFormRenderingContext.setHttpServletResponse(httpServletResponse);
		ddmFormRenderingContext.setLocale(locale);

		return _ddmFormRenderer.render(ddmForm, ddmFormRenderingContext);
	}

	@Reference
	private CPDefinitionOptionRelService _cpDefinitionOptionRelService;

	@Reference
	private DDMFormRenderer _ddmFormRenderer;

	@Reference
	private Portal _portal;

}