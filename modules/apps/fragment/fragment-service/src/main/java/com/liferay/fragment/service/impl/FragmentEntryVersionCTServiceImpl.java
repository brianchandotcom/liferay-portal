/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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