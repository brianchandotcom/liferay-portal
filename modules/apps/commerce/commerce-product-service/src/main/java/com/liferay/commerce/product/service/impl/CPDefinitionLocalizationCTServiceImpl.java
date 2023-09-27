/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.service.impl;

import com.liferay.commerce.product.model.CPDefinitionLocalization;
import com.liferay.commerce.product.service.persistence.CPDefinitionLocalizationPersistence;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.change.tracking.CTAware;
import com.liferay.portal.kernel.service.change.tracking.CTService;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 * @generated
 */
@Component(service = AopService.class)
@CTAware
public class CPDefinitionLocalizationCTServiceImpl
	implements AopService, CTService<CPDefinitionLocalization> {

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

	@Reference
	private CPDefinitionLocalizationPersistence
		_cpDefinitionLocalizationPersistence;

}