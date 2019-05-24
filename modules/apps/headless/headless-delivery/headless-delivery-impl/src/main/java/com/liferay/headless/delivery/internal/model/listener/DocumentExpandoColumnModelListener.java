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

package com.liferay.headless.delivery.internal.model.listener;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.service.ExpandoColumnLocalService;
import com.liferay.expando.kernel.service.ExpandoTableLocalService;
import com.liferay.headless.delivery.internal.odata.entity.v1_0.DocumentEntityModel;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.service.ClassNameLocalService;

import java.util.ArrayList;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Javier Gamarra
 */
@Component(immediate = true, service = ModelListener.class)
public class DocumentExpandoColumnModelListener
	extends BaseModelListener<ExpandoColumn> {

	@Activate
	public void activate(BundleContext bundleContext) {
		_customFieldsModelListener = new CustomFieldsModelListener(
			bundleContext, _classNameLocalService, DLFileEntry.class,
			entityFields -> new DocumentEntityModel(
				new ArrayList<>(entityFields.values())),
			_expandoColumnLocalService, _expandoTableLocalService);
	}

	@Deactivate
	public void deactivate() {
		_customFieldsModelListener.unregister();
	}

	@Override
	public void onAfterCreate(ExpandoColumn expandoColumn)
		throws ModelListenerException {

		_customFieldsModelListener.onAfterCreate(expandoColumn);
	}

	@Override
	public void onAfterRemove(ExpandoColumn expandoColumn)
		throws ModelListenerException {

		_customFieldsModelListener.onAfterRemove(expandoColumn);
	}

	@Override
	public void onAfterUpdate(ExpandoColumn expandoColumn)
		throws ModelListenerException {

		_customFieldsModelListener.onAfterUpdate(expandoColumn);
		_customFieldsModelListener.onAfterCreate(expandoColumn);
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

	private CustomFieldsModelListener _customFieldsModelListener;

	@Reference
	private ExpandoColumnLocalService _expandoColumnLocalService;

	@Reference
	private ExpandoTableLocalService _expandoTableLocalService;

}