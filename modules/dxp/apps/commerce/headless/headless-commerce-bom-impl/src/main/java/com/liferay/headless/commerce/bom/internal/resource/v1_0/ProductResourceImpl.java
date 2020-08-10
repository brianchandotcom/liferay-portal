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

package com.liferay.headless.commerce.bom.internal.resource.v1_0;

import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPInstanceService;
import com.liferay.headless.commerce.bom.dto.v1_0.Product;
import com.liferay.headless.commerce.bom.internal.dto.v1_0.converter.ProductDTOConverter;
import com.liferay.headless.commerce.bom.resource.v1_0.ProductResource;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/product.properties",
	scope = ServiceScope.PROTOTYPE, service = ProductResource.class
)
public class ProductResourceImpl extends BaseProductResourceImpl {

	@Override
	public Page<Product> getProductsPage(String q) throws Exception {
		BaseModelSearchResult<CPInstance> cpInstanceBaseModelSearchResult =
			_cpInstanceService.searchCPInstances(
				contextCompany.getCompanyId(), q,
				WorkflowConstants.STATUS_APPROVED, 0, 20, null);

		return Page.of(
			_toProducts(cpInstanceBaseModelSearchResult.getBaseModels()), null,
			cpInstanceBaseModelSearchResult.getLength());
	}

	private List<Product> _toProducts(List<CPInstance> cpInstances)
		throws Exception {

		List<Product> products = new ArrayList<>();

		for (CPInstance cpInstance : cpInstances) {
			products.add(
				_productDTOConverter.toDTO(
					new DefaultDTOConverterContext(
						cpInstance.getCPInstanceId(),
						contextAcceptLanguage.getPreferredLocale())));
		}

		return products;
	}

	@Reference
	private CPInstanceService _cpInstanceService;

	@Reference
	private ProductDTOConverter _productDTOConverter;

}