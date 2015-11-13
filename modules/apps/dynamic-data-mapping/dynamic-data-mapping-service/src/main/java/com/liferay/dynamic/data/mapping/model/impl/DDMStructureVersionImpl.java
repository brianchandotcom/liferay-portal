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

import aQute.bnd.annotation.ProviderType;

import com.liferay.dynamic.data.mapping.io.DDMFormJSONDeserializerUtil;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureLayout;
import com.liferay.dynamic.data.mapping.service.DDMStructureLayoutLocalServiceUtil;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.CacheField;

/**
 * @author Brian Wing Shun Chan
 * @author Leonardo Barros
 */
@ProviderType
public class DDMStructureVersionImpl extends DDMStructureVersionBaseImpl {

	@Override
	public DDMForm getDDMForm() {
		if (_ddmForm == null) {
			try {
				_ddmForm = DDMFormJSONDeserializerUtil.deserialize(
					getDefinition());
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		return new DDMForm(_ddmForm);
	}

	@Override
	public DDMFormLayout getDDMFormLayout() throws PortalException {
		DDMStructureLayout ddmStructureLayout =
			DDMStructureLayoutLocalServiceUtil.
				getStructureLayoutByStructureVersionId(getStructureVersionId());

		return ddmStructureLayout.getDDMFormLayout();
	}

	@Override
	public DDMStructure getStructure() throws PortalException {
		return DDMStructureLocalServiceUtil.getStructure(getStructureId());
	}

	@Override
	public void setDDMForm(DDMForm ddmForm) {
		_ddmForm = ddmForm;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMStructureVersionImpl.class);

	@CacheField(methodName = "DDMForm")
	private DDMForm _ddmForm;

}