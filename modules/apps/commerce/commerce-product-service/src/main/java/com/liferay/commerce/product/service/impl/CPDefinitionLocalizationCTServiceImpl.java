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

package com.liferay.commerce.product.service.impl;

import com.liferay.commerce.product.model.CPDefinitionLocalization;
import com.liferay.commerce.product.service.persistence.CPDefinitionLocalizationPersistence;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.change.tracking.CTAware;
import com.liferay.portal.kernel.service.change.tracking.CTService;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

/**
 * @author Marco Leo
 * @generated
 */
@CTAware
public class CPDefinitionLocalizationCTServiceImpl
	implements CTService<CPDefinitionLocalization> {

	@Override
	public CTPersistence<CPDefinitionLocalization> getCTPersistence() {
		return _cpDefinitionLocalizationPersistence;
	}

	@Override
	public Class<CPDefinitionLocalization> getModelClass() {
		return CPDefinitionLocalization.class;
	}

	@Override
	public <R, E extends Throwable> R updateWithUnsafeFunction(
			UnsafeFunction<CTPersistence<CPDefinitionLocalization>, R, E>
				updateUnsafeFunction)
		throws E {

		return updateUnsafeFunction.apply(_cpDefinitionLocalizationPersistence);
	}

	@BeanReference(type = CPDefinitionLocalizationPersistence.class)
	private CPDefinitionLocalizationPersistence
		_cpDefinitionLocalizationPersistence;

}