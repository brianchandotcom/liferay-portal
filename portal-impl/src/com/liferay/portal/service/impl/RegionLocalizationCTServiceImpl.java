/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.service.impl;

import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.change.tracking.CTAware;
import com.liferay.portal.kernel.model.RegionLocalization;
import com.liferay.portal.kernel.service.change.tracking.CTService;
import com.liferay.portal.kernel.service.persistence.RegionLocalizationPersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@CTAware
public class RegionLocalizationCTServiceImpl
	implements CTService<RegionLocalization> {

	@Override
	public CTPersistence<RegionLocalization> getCTPersistence() {
		return _regionLocalizationPersistence;
	}

	@Override
	public Class<RegionLocalization> getModelClass() {
		return RegionLocalization.class;
	}

	@Override
	public <R, E extends Throwable> R updateWithUnsafeFunction(
			UnsafeFunction<CTPersistence<RegionLocalization>, R, E>
				updateUnsafeFunction)
		throws E {

		return updateUnsafeFunction.apply(_regionLocalizationPersistence);
	}

	@BeanReference(type = RegionLocalizationPersistence.class)
	private RegionLocalizationPersistence _regionLocalizationPersistence;

}