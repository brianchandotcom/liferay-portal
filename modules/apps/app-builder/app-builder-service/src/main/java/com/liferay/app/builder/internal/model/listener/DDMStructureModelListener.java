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

package com.liferay.app.builder.internal.model.listener;

import com.liferay.app.builder.model.AppBuilderApp;
import com.liferay.app.builder.service.AppBuilderAppLocalService;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gabriel Albuquerque
 */
@Component(immediate = true, service = ModelListener.class)
public class DDMStructureModelListener extends BaseModelListener<DDMStructure> {

	@Override
	public void onAfterRemove(DDMStructure ddmStructure)
		throws ModelListenerException {

		try {
			List<AppBuilderApp> appBuilderApps =
				_appBuilderAppLocalService.getAppBuilderApps(
					ddmStructure.getStructureId());

			for (AppBuilderApp appBuilderApp : appBuilderApps) {
				_appBuilderAppLocalService.deleteAppBuilderApp(
					appBuilderApp.getAppBuilderAppId());
			}
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMStructureModelListener.class);

	@Reference
	private AppBuilderAppLocalService _appBuilderAppLocalService;

}