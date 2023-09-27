/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.style.book.service.impl;

import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.change.tracking.CTAware;
import com.liferay.portal.kernel.service.change.tracking.CTService;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;
import com.liferay.style.book.model.StyleBookEntryVersion;
import com.liferay.style.book.service.persistence.StyleBookEntryVersionPersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = AopService.class)
@CTAware
public class StyleBookEntryVersionCTServiceImpl
	implements AopService, CTService<StyleBookEntryVersion> {

	@Override
	public CTPersistence<StyleBookEntryVersion> getCTPersistence() {
		return _styleBookEntryVersionPersistence;
	}

	@Override
	public Class<StyleBookEntryVersion> getModelClass() {
		return StyleBookEntryVersion.class;
	}

	@Override
	public <R, E extends Throwable> R updateWithUnsafeFunction(
			UnsafeFunction<CTPersistence<StyleBookEntryVersion>, R, E>
				updateUnsafeFunction)
		throws E {

		return updateUnsafeFunction.apply(_styleBookEntryVersionPersistence);
	}

	@Reference
	private StyleBookEntryVersionPersistence _styleBookEntryVersionPersistence;

}