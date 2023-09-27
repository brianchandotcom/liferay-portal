/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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