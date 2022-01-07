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

package com.liferay.object.system;

import com.liferay.object.action.engine.ObjectActionEngine;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.system.model.listener.SystemObjectDefinitionMetadataModelListener;
import com.liferay.object.util.LocalizedMapUtil;
import com.liferay.object.util.ObjectFieldUtil;
import com.liferay.petra.sql.dsl.Table;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.Locale;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 * @author Brian Wing Shun Chan
 */
public abstract class BaseSystemObjectDefinitionMetadata
	implements SystemObjectDefinitionMetadata {

	@Override
	public String getModelClassName() {
		Class<?> modelClass = getModelClass();

		return modelClass.getName();
	}

	@Override
	public String getName() {
		Table table = getTable();

		String tableName = table.getName();

		if (tableName.endsWith("_")) {
			return tableName.substring(0, tableName.length() - 1);
		}

		return tableName;
	}

	@Activate
	protected void activate(BundleContext bundleContext) throws Exception {
		_serviceRegistration = bundleContext.registerService(
			ModelListener.class.getName(),
			new SystemObjectDefinitionMetadataModelListener(
				jsonFactory, getModelClass(), objectActionEngine,
				objectDefinitionLocalService, objectEntryLocalService),
			null);
	}

	protected Map<Locale, String> createLabelMap(String labelKey) {
		return LocalizedMapUtil.getLocalizedMap(_translate(labelKey));
	}

	protected ObjectField createObjectField(
		String businessType, String labelKey, String name, boolean required,
		String type) {

		return createObjectField(
			businessType, null, labelKey, name, required, type);
	}

	protected ObjectField createObjectField(
		String businessType, String dbColumnName, String labelKey, String name,
		boolean required, String type) {

		return ObjectFieldUtil.createObjectField(
			0, businessType, dbColumnName, false, false, null,
			_translate(labelKey), name, required, type);
	}

	@Deactivate
	protected void deactivate() throws Exception {
		_serviceRegistration.unregister();
	}

	@Reference
	protected JSONFactory jsonFactory;

	@Reference
	protected ObjectActionEngine objectActionEngine;

	@Reference
	protected ObjectDefinitionLocalService objectDefinitionLocalService;

	@Reference
	protected ObjectEntryLocalService objectEntryLocalService;

	private String _translate(String labelKey) {
		return LanguageUtil.get(LocaleUtil.getDefault(), labelKey);
	}

	private ServiceRegistration<?> _serviceRegistration;

}