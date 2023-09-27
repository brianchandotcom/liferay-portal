/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.service.impl;

import com.liferay.dynamic.data.mapping.model.DDMFieldAttribute;
import com.liferay.dynamic.data.mapping.service.persistence.DDMFieldAttributePersistence;
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
public class DDMFieldAttributeCTServiceImpl
	implements AopService, CTService<DDMFieldAttribute> {

	@Override
	public CTPersistence<DDMFieldAttribute> getCTPersistence() {
		return _ddmFieldAttributePersistence;
	}

	@Override
	public Class<DDMFieldAttribute> getModelClass() {
		return DDMFieldAttribute.class;
	}

	@Override
	public <R, E extends Throwable> R updateWithUnsafeFunction(
			UnsafeFunction<CTPersistence<DDMFieldAttribute>, R, E>
				updateUnsafeFunction)
		throws E {

		return updateUnsafeFunction.apply(_ddmFieldAttributePersistence);
	}

	@Reference
	private DDMFieldAttributePersistence _ddmFieldAttributePersistence;

}