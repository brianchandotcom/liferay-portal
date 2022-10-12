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

import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPOption;
import com.liferay.commerce.product.service.CPDefinitionOptionRelLocalService;
import com.liferay.headless.commerce.core.util.LanguageUtils;
import com.liferay.headless.commerce.machine.learning.dto.v1_0.ProductOption;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
@Component(
	property = "dto.class.name=com.liferay.commerce.product.model.CPDefinitionOptionRel",
	service = {DTOConverter.class, ProductOptionDTOConverter.class}
)
public class ProductOptionDTOConverter
	implements DTOConverter<CPDefinitionOptionRel, ProductOption> {

	@Override
	public String getContentType() {
		return ProductOption.class.getSimpleName();
	}

	@Override
	public CPDefinitionOptionRel getObject(String externalReferenceCode)
		throws Exception {

		return _cpDefinitionOptionRelLocalService.fetchCPDefinitionOptionRel(
			GetterUtil.getLong(externalReferenceCode));
	}

	@Override
	public ProductOption toDTO(
			DTOConverterContext dtoConverterContext,
			CPDefinitionOptionRel cpDefinitionOptionRel)
		throws Exception {

		if (cpDefinitionOptionRel == null) {
			return null;
		}

		CPOption cpOption = cpDefinitionOptionRel.getCPOption();

		return new ProductOption() {
			{
				key = cpDefinitionOptionRel.getKey();
				optionKey = cpOption.getKey();
				values = TransformUtil.transformToArray(
					cpDefinitionOptionRel.getCPDefinitionOptionValueRels(),
					cpDefinitionOptionValueRel ->
						LanguageUtils.getLanguageIdMap(
							cpDefinitionOptionValueRel.getNameMap()),
					Map.class);
			}
		};
	}

	@Reference
	private CPDefinitionOptionRelLocalService
		_cpDefinitionOptionRelLocalService;

}