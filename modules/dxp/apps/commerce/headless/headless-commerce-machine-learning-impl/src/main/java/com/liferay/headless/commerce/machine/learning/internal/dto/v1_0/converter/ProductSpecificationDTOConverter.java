/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.headless.commerce.machine.learning.internal.dto.v1_0.converter;

import com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue;
import com.liferay.commerce.product.model.CPSpecificationOption;
import com.liferay.commerce.product.service.CPDefinitionSpecificationOptionValueLocalService;
import com.liferay.headless.commerce.core.util.LanguageUtils;
import com.liferay.headless.commerce.machine.learning.dto.v1_0.ProductSpecification;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
@Component(
	property = "dto.class.name=com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue",
	service = {DTOConverter.class, ProductSpecificationDTOConverter.class}
)
public class ProductSpecificationDTOConverter
	implements DTOConverter
		<CPDefinitionSpecificationOptionValue, ProductSpecification> {

	@Override
	public String getContentType() {
		return ProductSpecification.class.getSimpleName();
	}

	@Override
	public CPDefinitionSpecificationOptionValue getObject(
			String externalReferenceCode)
		throws Exception {

		return _cpDefinitionSpecificationOptionValueLocalService.
			fetchCPDefinitionSpecificationOptionValue(
				GetterUtil.getLong(externalReferenceCode));
	}

	@Override
	public ProductSpecification toDTO(
			DTOConverterContext dtoConverterContext,
			CPDefinitionSpecificationOptionValue
				cpDefinitionSpecificationOptionValue)
		throws Exception {

		if (cpDefinitionSpecificationOptionValue == null) {
			return null;
		}

		CPSpecificationOption cpSpecificationOption =
			cpDefinitionSpecificationOptionValue.getCPSpecificationOption();

		return new ProductSpecification() {
			{
				id =
					cpDefinitionSpecificationOptionValue.
						getCPDefinitionSpecificationOptionValueId();
				optionCategoryId =
					cpDefinitionSpecificationOptionValue.
						getCPOptionCategoryId();
				specificationKey = cpSpecificationOption.getKey();
				value = LanguageUtils.getLanguageIdMap(
					cpDefinitionSpecificationOptionValue.getValueMap());
			}
		};
	}

	@Reference
	private CPDefinitionSpecificationOptionValueLocalService
		_cpDefinitionSpecificationOptionValueLocalService;

}