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

package com.liferay.dynamic.data.mapping.web.internal.model.listener;

import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.staging.model.listener.StagingModelListener;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Akos Thurzo
 */
@Component(immediate = true, service = ModelListener.class)
public class DDMTemplateStagingModelListener
	extends BaseModelListener<DDMTemplate> {

	@Override
	public void onAfterCreate(DDMTemplate ddmTemplate)
		throws ModelListenerException {

		_stagingModelListener.onAfterCreate(ddmTemplate);
	}

	@Override
	public void onAfterRemove(DDMTemplate ddmTemplate)
		throws ModelListenerException {

		_stagingModelListener.onAfterRemove(ddmTemplate);
	}

	@Override
	public void onAfterUpdate(DDMTemplate ddmTemplate)
		throws ModelListenerException {

		_stagingModelListener.onAfterUpdate(ddmTemplate);
	}

	@Reference
	private StagingModelListener<DDMTemplate> _stagingModelListener;

}