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

package com.liferay.dynamic.data.mapping.model.impl;

import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.service.DDMStructureLayoutLocalServiceUtil;
import com.liferay.dynamic.data.mapping.service.DDMStructureVersionLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.cache.CacheField;

/**
 * @author Marcellus Tavares
 */
public class DDMStructureLayoutImpl extends DDMStructureLayoutBaseImpl {

	@Override
	public DDMFormLayout getDDMFormLayout() {
		if (_ddmFormLayout == null) {
			try {
				_ddmFormLayout =
					DDMStructureLayoutLocalServiceUtil.
						getStructureLayoutDDMFormLayout(this);
			}
			catch (Exception exception) {
				_log.error(exception, exception);

				return new DDMFormLayout();
			}
		}

		return new DDMFormLayout(_ddmFormLayout);
	}

	@Override
	public DDMStructure getDDMStructure() throws PortalException {
		DDMStructureVersion ddmStructureVersion =
			DDMStructureVersionLocalServiceUtil.getDDMStructureVersion(
				getStructureVersionId());

		return ddmStructureVersion.getStructure();
	}

	@Override
	public long getDDMStructureId() throws PortalException {
		DDMStructure ddmStructure = getDDMStructure();

		return ddmStructure.getStructureId();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMStructureLayoutImpl.class);

	@CacheField(methodName = "DDMFormLayout", propagateToInterface = true)
	private DDMFormLayout _ddmFormLayout;

}