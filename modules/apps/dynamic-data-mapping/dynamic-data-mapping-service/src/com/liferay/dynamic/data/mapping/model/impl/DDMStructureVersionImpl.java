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

package com.liferay.portlet.dynamicdatamapping.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.CacheField;
import com.liferay.portlet.dynamicdatamapping.io.DDMFormJSONDeserializerUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormLayout;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructureLayout;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLayoutLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;

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