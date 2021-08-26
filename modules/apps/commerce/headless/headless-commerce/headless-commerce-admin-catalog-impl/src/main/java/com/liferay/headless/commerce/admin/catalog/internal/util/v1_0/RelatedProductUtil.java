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

package com.liferay.headless.commerce.admin.catalog.internal.util.v1_0;

import com.liferay.commerce.product.exception.NoSuchCPDefinitionException;
import com.liferay.commerce.product.exception.NoSuchCPDefinitionLinkException;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionLink;
import com.liferay.commerce.product.service.CPDefinitionLinkService;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.RelatedProduct;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Alessio Antonio Rendina
 */
public class RelatedProductUtil {

	public static CPDefinitionLink addOrUpdateCPDefinitionLink(
			CPDefinitionLinkService cpDefinitionLinkService,
			CPDefinitionService cpDefinitionService,
			RelatedProduct relatedProduct, long cpDefinitionId,
			ServiceContext serviceContext)
		throws PortalException {

		try {
			CPDefinitionLink cpDefinitionLink =
				cpDefinitionLinkService.getCPDefinitionLink(
					relatedProduct.getId());

			return cpDefinitionLinkService.updateCPDefinitionLink(
				relatedProduct.getId(),
				GetterUtil.get(
					relatedProduct.getPriority(),
					cpDefinitionLink.getPriority()),
				serviceContext);
		}
		catch (NoSuchCPDefinitionLinkException
					noSuchCPDefinitionLinkException) {

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to find relatedProduct with ID: " +
						relatedProduct.getId(),
					noSuchCPDefinitionLinkException);
			}
		}

		CPDefinition cpDefinition = null;

		if (Validator.isNotNull(
				relatedProduct.getProductExternalReferenceCode())) {

			cpDefinition =
				cpDefinitionService.
					fetchCPDefinitionByCProductExternalReferenceCode(
						relatedProduct.getProductExternalReferenceCode(),
						serviceContext.getCompanyId());

			if (cpDefinition == null) {
				throw new NoSuchCPDefinitionException(
					"Unable to find product with external reference code " +
						relatedProduct.getProductExternalReferenceCode());
			}
		}
		else {
			cpDefinition = cpDefinitionService.fetchCPDefinitionByCProductId(
				relatedProduct.getProductId());

			if (cpDefinition == null) {
				throw new NoSuchCPDefinitionException(
					"Unable to find Product with ID: " +
						relatedProduct.getProductId());
			}
		}

		return cpDefinitionLinkService.addCPDefinitionLink(
			cpDefinitionId, cpDefinition.getCProductId(),
			GetterUtil.get(relatedProduct.getPriority(), 0D),
			relatedProduct.getType(), serviceContext);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RelatedProductUtil.class);

}