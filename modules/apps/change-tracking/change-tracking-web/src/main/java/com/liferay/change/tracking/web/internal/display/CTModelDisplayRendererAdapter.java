/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.web.internal.display;

import com.liferay.change.tracking.spi.display.BaseCTDisplayRenderer;
import com.liferay.change.tracking.spi.display.CTDisplayRendererRegistry;
import com.liferay.portal.kernel.dao.orm.ORMException;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.service.change.tracking.CTService;
import com.liferay.portal.kernel.util.CamelCaseUtil;

import java.io.InputStream;

import java.sql.Blob;
import java.sql.SQLException;

import java.util.Locale;
import java.util.Map;
import java.util.function.Function;

/**
 * @author Samuel Trong Tran
 */
public class CTModelDisplayRendererAdapter<T extends BaseModel<T>>
	extends BaseCTDisplayRenderer<T> {

	public CTModelDisplayRendererAdapter(
		CTDisplayRendererRegistry ctDisplayRendererRegistry) {

		_ctDisplayRendererRegistry = ctDisplayRendererRegistry;
	}

	@Override
	public InputStream getDownloadInputStream(T model, String key) {
		if (model instanceof CTModel<?>) {
			CTModel<?> ctModel = (CTModel<?>)model;

			CTService<?> ctService = _ctDisplayRendererRegistry.getCTService(
				ctModel);

			return ctService.updateWithUnsafeFunction(
				ctPersistence -> {
					Map<String, Function<T, Object>> attributeGetterFunctions =
						model.getAttributeGetterFunctions();

					Function<T, Object> function = attributeGetterFunctions.get(
						CamelCaseUtil.toCamelCase(key));

					Blob blob = (Blob)function.apply(model);

					try {
						return blob.getBinaryStream();
					}
					catch (SQLException sqlException) {
						throw new ORMException(sqlException);
					}
				});
		}

		return null;
	}

	@Override
	public Class<T> getModelClass() {
		return null;
	}

	@Override
	public String getTitle(Locale locale, T model) {
		return null;
	}

	@Override
	public boolean isHideable(T model) {
		return true;
	}

	private final CTDisplayRendererRegistry _ctDisplayRendererRegistry;

}