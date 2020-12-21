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

package com.liferay.headless.delivery.internal.jaxrs.exception.mapper;

import com.liferay.asset.kernel.exception.AssetCategoryException;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.BaseExceptionMapper;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.Problem;

import java.util.Optional;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import org.osgi.service.component.annotations.Component;

/**
 * Converts any {@code AssetCategoryException} to a {@code 400} error.
 *
 * @author Javier de Arcos
 */
@Component(
	property = {
		"osgi.jaxrs.application.select=(osgi.jaxrs.name=Liferay.Headless.Delivery)",
		"osgi.jaxrs.extension=true",
		"osgi.jaxrs.name=Liferay.Headless.Delivery.TaxonomyCategoryExceptionMapper"
	},
	service = ExceptionMapper.class
)
public class TaxonomyCategoryExceptionMapper
	extends BaseExceptionMapper<AssetCategoryException> {

	@Override
	protected Problem getProblem(
		AssetCategoryException assetCategoryException) {

		String errorMessage = StringPool.BLANK;
		String vocabularyName = Optional.ofNullable(
			assetCategoryException.getVocabulary()
		).map(
			AssetVocabulary::getName
		).orElse(
			StringPool.BLANK
		);

		if (assetCategoryException.getType() ==
				AssetCategoryException.AT_LEAST_ONE_CATEGORY) {

			errorMessage = "Select at least one category for " + vocabularyName;
		}
		else if (assetCategoryException.getType() ==
					AssetCategoryException.TOO_MANY_CATEGORIES) {

			errorMessage =
				"Cannot select more than one category for " + vocabularyName;
		}

		return new Problem(Response.Status.BAD_REQUEST, errorMessage);
	}

}