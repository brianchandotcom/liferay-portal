/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.friendly.url.service.impl;

import com.liferay.friendly.url.model.FriendlyURLEntryLocalization;
import com.liferay.friendly.url.service.persistence.FriendlyURLEntryLocalizationPersistence;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.change.tracking.CTAware;
import com.liferay.portal.kernel.service.change.tracking.CTService;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = AopService.class)
@CTAware
public class FriendlyURLEntryLocalizationCTServiceImpl
	implements AopService, CTService<FriendlyURLEntryLocalization> {

	@Override
	public CTPersistence<FriendlyURLEntryLocalization> getCTPersistence() {
		return _friendlyURLEntryLocalizationPersistence;
	}

	@Override
	public Class<FriendlyURLEntryLocalization> getModelClass() {
		return FriendlyURLEntryLocalization.class;
	}

	@Override
	public <R, E extends Throwable> R updateWithUnsafeFunction(
			UnsafeFunction<CTPersistence<FriendlyURLEntryLocalization>, R, E>
				updateUnsafeFunction)
		throws E {

		return updateUnsafeFunction.apply(
			_friendlyURLEntryLocalizationPersistence);
	}

	@Reference
	private FriendlyURLEntryLocalizationPersistence
		_friendlyURLEntryLocalizationPersistence;

}