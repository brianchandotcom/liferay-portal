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

package com.liferay.document.library.google.docs.internal.instance.lifecycle;

import com.liferay.document.library.google.docs.internal.util.GoogleDocsDLFileEntryTypeHelper;
import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializer;
import com.liferay.dynamic.data.mapping.kernel.DDMStructureManager;
import com.liferay.dynamic.data.mapping.kernel.DDMStructureManagerUtil;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureVersionLocalService;
import com.liferay.dynamic.data.mapping.util.DDM;
import com.liferay.dynamic.data.mapping.util.DDMStructurePermissionSupport;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.UserLocalService;

import java.lang.reflect.Field;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(immediate = true, service = PortalInstanceLifecycleListener.class)
public class GoogleDocsPortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		try {
			GoogleDocsDLFileEntryTypeHelper googleDocsDLFileEntryTypeHelper =
				new GoogleDocsDLFileEntryTypeHelper(
					company,
					_classNameLocalService.getClassNameId(
						DLFileEntryMetadata.class),
					_ddm, _xsdDDMFormDeserializer, _ddmStructureLocalService,
					_dlFileEntryTypeLocalService, _userLocalService);

			googleDocsDLFileEntryTypeHelper.addGoogleDocsDLFileEntryType();
		}
		catch (PortalException pe) {
			throw new ModelListenerException(pe);
		}
	}

	@Reference(unbind = "-")
	protected void setDDMStructureManager(
			DDMStructureManager ddmStructureManager)
		throws Exception {

		Field field = ReflectionUtil.getDeclaredField(
			DDMStructureManagerUtil.class, "_ddmStructureManager");

		field.set(null, ddmStructureManager);
	}

	@Reference(
		target = "(model.class.name=com.liferay.document.library.kernel.model.DLFileEntryMetadata)",
		unbind = "-"
	)
	protected void setDDMStructurePermissionSupport(
		DDMStructurePermissionSupport ddmStructurePermissionSupport) {
	}

	@Reference(unbind = "-")
	protected void setDDMStructureVersionLocalService(
		DDMStructureVersionLocalService ddmStructureVersionLocalService) {
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private DDM _ddm;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private DLFileEntryTypeLocalService _dlFileEntryTypeLocalService;

	@Reference
	private UserLocalService _userLocalService;

	@Reference(target = "(ddm.form.deserializer.type=xsd)")
	private DDMFormDeserializer _xsdDDMFormDeserializer;

}