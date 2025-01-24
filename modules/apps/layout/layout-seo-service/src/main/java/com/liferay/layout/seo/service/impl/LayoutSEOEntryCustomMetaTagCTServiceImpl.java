/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.seo.service.impl;

import com.liferay.layout.seo.model.LayoutSEOEntryCustomMetaTag;
import com.liferay.layout.seo.service.persistence.LayoutSEOEntryCustomMetaTagPersistence;
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
public class LayoutSEOEntryCustomMetaTagCTServiceImpl
	implements AopService, CTService<LayoutSEOEntryCustomMetaTag> {

	@Override
	public CTPersistence<LayoutSEOEntryCustomMetaTag> getCTPersistence() {
		return _layoutSEOEntryCustomMetaTagPersistence;
	}

	@Override
	public Class<LayoutSEOEntryCustomMetaTag> getModelClass() {
		return LayoutSEOEntryCustomMetaTag.class;
	}

	@Override
	public <R, E extends Throwable> R updateWithUnsafeFunction(
			UnsafeFunction<CTPersistence<LayoutSEOEntryCustomMetaTag>, R, E>
				updateUnsafeFunction)
		throws E {

		return updateUnsafeFunction.apply(
			_layoutSEOEntryCustomMetaTagPersistence);
	}

	@Reference
	private LayoutSEOEntryCustomMetaTagPersistence
		_layoutSEOEntryCustomMetaTagPersistence;

}