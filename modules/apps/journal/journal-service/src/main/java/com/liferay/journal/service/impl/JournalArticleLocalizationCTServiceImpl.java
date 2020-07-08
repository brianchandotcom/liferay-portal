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

package com.liferay.journal.service.impl;

import com.liferay.journal.model.JournalArticleLocalization;
import com.liferay.journal.service.persistence.JournalArticleLocalizationPersistence;
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
public class JournalArticleLocalizationCTServiceImpl
	implements AopService, CTService<JournalArticleLocalization> {

	@Override
	public CTPersistence<JournalArticleLocalization> getCTPersistence() {
		return _journalArticleLocalizationPersistence;
	}

	@Override
	public Class<JournalArticleLocalization> getModelClass() {
		return JournalArticleLocalization.class;
	}

	@Override
	public <R, E extends Throwable> R updateWithUnsafeFunction(
			UnsafeFunction<CTPersistence<JournalArticleLocalization>, R, E>
				updateUnsafeFunction)
		throws E {

		return updateUnsafeFunction.apply(
			_journalArticleLocalizationPersistence);
	}

	@Reference
	private JournalArticleLocalizationPersistence
		_journalArticleLocalizationPersistence;

}