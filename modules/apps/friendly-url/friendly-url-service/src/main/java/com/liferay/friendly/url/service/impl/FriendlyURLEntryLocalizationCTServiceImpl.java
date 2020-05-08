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

package com.liferay.friendly.url.service.impl;

import com.liferay.friendly.url.model.FriendlyURLEntryLocalization;
import com.liferay.friendly.url.service.persistence.FriendlyURLEntryLocalizationPersistence;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.service.change.tracking.CTService;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = AopService.class)
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