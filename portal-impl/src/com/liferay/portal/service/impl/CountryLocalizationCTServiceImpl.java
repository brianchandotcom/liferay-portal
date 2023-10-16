/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.service.impl;

import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.change.tracking.CTAware;
import com.liferay.portal.kernel.model.CountryLocalization;
import com.liferay.portal.kernel.service.change.tracking.CTService;
import com.liferay.portal.kernel.service.persistence.CountryLocalizationPersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@CTAware
public class CountryLocalizationCTServiceImpl
	implements CTService<CountryLocalization> {

	@Override
	public CTPersistence<CountryLocalization> getCTPersistence() {
		return _countryLocalizationPersistence;
	}

	@Override
	public Class<CountryLocalization> getModelClass() {
		return CountryLocalization.class;
	}

	@Override
	public <R, E extends Throwable> R updateWithUnsafeFunction(
			UnsafeFunction<CTPersistence<CountryLocalization>, R, E>
				updateUnsafeFunction)
		throws E {

		return updateUnsafeFunction.apply(_countryLocalizationPersistence);
	}

	@BeanReference(type = CountryLocalizationPersistence.class)
	private CountryLocalizationPersistence _countryLocalizationPersistence;

}