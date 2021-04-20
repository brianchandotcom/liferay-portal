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

package com.liferay.headless.commerce.bom.internal.dto.v1_0.converter;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPInstanceService;
import com.liferay.commerce.product.url.CPFriendlyURL;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.headless.commerce.bom.dto.v1_0.Product;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, property = "model.class.name=commerceProductInstance",
	service = {DTOConverter.class, ProductDTOConverter.class}
)
public class ProductDTOConverter implements DTOConverter<CPInstance, Product> {

	@Override
	public String getContentType() {
		return Product.class.getSimpleName();
	}

	@Override
	public Product toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		CPInstance cpInstance = _cpInstanceService.getCPInstance(
			(Long)dtoConverterContext.getId());

		CPDefinition cpDefinition = cpInstance.getCPDefinition();

		return new Product() {
			{
				id = cpInstance.getCPInstanceUuid();
				name = cpDefinition.getName(
					LocaleUtil.toLanguageId(dtoConverterContext.getLocale()));
				sku = cpInstance.getSku();
				thumbnailUrl = _cpInstanceHelper.getCPInstanceThumbnailSrc(
					cpInstance.getCPInstanceId());

				String cpDefinitionURL = cpDefinition.getURL(
					LocaleUtil.toLanguageId(dtoConverterContext.getLocale()));

				String productURLSeparator =
					_cpFriendlyURL.getProductURLSeparator(
						cpInstance.getCompanyId());

				url = productURLSeparator + cpDefinitionURL;
			}
		};
	}

	@Reference
	private CPFriendlyURL _cpFriendlyURL;

	@Reference
	private CPInstanceHelper _cpInstanceHelper;

	@Reference
	private CPInstanceService _cpInstanceService;

}