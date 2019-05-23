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

package com.liferay.portal.vulcan.internal.util.resource;

import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.vulcan.fields.nested.NestedField;
import com.liferay.portal.vulcan.internal.util.dto.ProductOption;
import com.liferay.portal.vulcan.internal.util.dto.Sku;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.core.Context;

/**
 * @author Ivica Cardic
 */
public class ProductResourceImpl extends BaseProductResourceImpl {

	@NestedField("productOptions")
	@Override
	public List<ProductOption> getProductOptions(
		Long id, String name, Date createDate, Pagination pagination) {

		if (id != 1) {
			return Collections.emptyList();
		}

		List<ProductOption> productOptions = Arrays.asList(
			_getProductOptionDTO(1L, "test1"),
			_getProductOptionDTO(2L, "test2"),
			_getProductOptionDTO(3L, "test3"));

		if (name != null) {
			Stream<ProductOption> productOptionDTOStream =
				productOptions.stream();

			productOptions = productOptionDTOStream.filter(
				productOptionDTO -> Objects.equals(
					productOptionDTO.getName(), name)
			).collect(
				Collectors.toList()
			);
		}

		return productOptions;
	}

	@NestedField("skus")
	@Override
	public Page<Sku> getSkus(String id, Pagination pagination) {
		if (!Objects.equals(id, "1")) {
			return Page.of(Collections.emptyList());
		}

		List<Sku> skus = Arrays.asList(
			_getSkuDTO(1L), _getSkuDTO(2L), _getSkuDTO(3L), _getSkuDTO(4L));

		skus = skus.subList(
			pagination.getStartPosition(),
			Math.min(pagination.getEndPosition(), skus.size()));

		return Page.of(skus);
	}

	@Context
	public ThemeDisplay themeDisplay;

	private ProductOption _getProductOptionDTO(long id, String name) {
		ProductOption productOption = new ProductOption();

		productOption.setId(id);
		productOption.setName(name);

		return productOption;
	}

	private Sku _getSkuDTO(long id) {
		Sku sku = new Sku();

		sku.setId(id);

		return sku;
	}

}