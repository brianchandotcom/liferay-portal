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

package com.liferay.fragment.service.impl;

import com.liferay.fragment.model.FragmentEntryVersion;
import com.liferay.fragment.service.persistence.FragmentEntryVersionPersistence;
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
public class FragmentEntryVersionCTServiceImpl
	implements AopService, CTService<FragmentEntryVersion> {

	@Override
	public CTPersistence<FragmentEntryVersion> getCTPersistence() {
		return _fragmentEntryVersionPersistence;
	}

	@Override
	public Class<FragmentEntryVersion> getModelClass() {
		return FragmentEntryVersion.class;
	}

	@Override
	public <R, E extends Throwable> R updateWithUnsafeFunction(
			UnsafeFunction<CTPersistence<FragmentEntryVersion>, R, E>
				updateUnsafeFunction)
		throws E {

		return updateUnsafeFunction.apply(_fragmentEntryVersionPersistence);
	}

	@Reference
	private FragmentEntryVersionPersistence _fragmentEntryVersionPersistence;

}