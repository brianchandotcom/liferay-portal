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

package com.liferay.commerce.product.internal.util.data.provider;

import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.service.CPDefinitionOptionRelService;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProvider;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderContext;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderException;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderRequest;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderResponse;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderResponseOutput;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 * @author Marco Leo
 */
@Component(
	immediate = true,
	property = "ddm.data.provider.instance.id=getCPInstanceOptionsValues"
)
public class CPInstanceOptionsValuesDataProvider implements DDMDataProvider {

	@Override
	public List<KeyValuePair> getData(
			DDMDataProviderContext ddmDataProviderContext)
		throws DDMDataProviderException {

		return Collections.emptyList();
	}

	@Override
	public DDMDataProviderResponse getData(
			DDMDataProviderRequest ddmDataProviderRequest)
		throws DDMDataProviderException {

		HttpServletRequest httpServletRequest =
			ddmDataProviderRequest.getHttpServletRequest();

		Locale locale = httpServletRequest.getLocale();

		long cpDefinitionId = GetterUtil.getLong(
			ddmDataProviderRequest.getParameter("cpDefinitionId"));

		if (cpDefinitionId == 0) {
			return DDMDataProviderResponse.of();
		}

		try {
			/*
			 * Extract the filters and the outputs based on fields that were
			 * filled or not in the data provider request
			 * Ex:
			 * If we have size and color as product options,
			 * In this case, we will have four possible states as follow:
			 * 1 - Size and Color both empty -> It won't do any query and it
			 * 	will return an empty result.
			 * 2 - Size filled and Color empty -> It'll assume that size will
			 * 	compose the query filter and color will be the output. So,
			 * 	it will return all colors for the specified size
			 * 3 - Size empty and Color filled - the same approach that item 2
			 * 4 - Size and Color both filled - the same approach that item 1
			 */
			Map<String, String> parameters =
				ddmDataProviderRequest.getParameters();

			Map<String, String> outputParameterNames = new HashMap<>();

			Map<String, String> filters = new HashMap<>();

			List<CPDefinitionOptionRel> cpDefinitionOptionRels =
				_cpDefinitionOptionRelService.getCPDefinitionOptionRels(
					cpDefinitionId, true);

			for (CPDefinitionOptionRel cpDefinitionOptionRel :
					cpDefinitionOptionRels) {

				long cpDefinitionOptionRelId =
					cpDefinitionOptionRel.getCPDefinitionOptionRelId();

				String parameterValue = parameters.get(
					String.valueOf(cpDefinitionOptionRelId));

				// Collect filters and outputs

				if (Validator.isNull(parameterValue)) {
					outputParameterNames.put(
						String.valueOf(cpDefinitionOptionRelId),
						String.valueOf(cpDefinitionOptionRelId));
				}
				else {
					filters.put(
						String.valueOf(cpDefinitionOptionRelId),
						parameterValue);
				}
			}

			// Do search and populate the outputs if the outputs are not empty

			if (outputParameterNames.isEmpty()) {
				return DDMDataProviderResponse.of();
			}

			List<DDMDataProviderResponseOutput> ddmDataProviderResponseOutputs =
				new ArrayList<>();

			for (Map.Entry<String, String> outputParameterNameEntry :
					outputParameterNames.entrySet()) {

				String fieldName =
					"ATTRIBUTE_" + outputParameterNameEntry.getKey() +
						"_VALUE_ID";

				List<CPDefinitionOptionValueRel> cpDefinitionOptionValueRels =
					_cpInstanceHelper.getCPDefinitionOptionValueRel(
						cpDefinitionId, fieldName, filters);

				List<KeyValuePair> data = new ArrayList<>();

				for (CPDefinitionOptionValueRel cpDefinitionOptionValueRel :
						cpDefinitionOptionValueRels) {

					String key = String.valueOf(
						cpDefinitionOptionValueRel.
							getCPDefinitionOptionValueRelId());

					data.add(
						new KeyValuePair(
							key, cpDefinitionOptionValueRel.getName(locale)));
				}

				ddmDataProviderResponseOutputs.add(
					DDMDataProviderResponseOutput.of(
						outputParameterNameEntry.getValue(), "list", data));
			}

			DDMDataProviderResponseOutput[] ddmDataProviderResponseOutputArray =
				new DDMDataProviderResponseOutput[
					ddmDataProviderResponseOutputs.size()];

			ddmDataProviderResponseOutputArray =
				ddmDataProviderResponseOutputs.toArray(
					ddmDataProviderResponseOutputArray);

			return DDMDataProviderResponse.of(
				ddmDataProviderResponseOutputArray);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return DDMDataProviderResponse.of();
	}

	@Override
	public Class<?> getSettings() {
		throw new UnsupportedOperationException();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CPInstanceOptionsValuesDataProvider.class);

	@Reference
	private CPDefinitionOptionRelService _cpDefinitionOptionRelService;

	@Reference
	private CPInstanceHelper _cpInstanceHelper;

}