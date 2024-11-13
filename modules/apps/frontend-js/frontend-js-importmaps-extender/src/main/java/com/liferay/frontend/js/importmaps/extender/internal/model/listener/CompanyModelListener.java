/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.importmaps.extender.internal.model.listener;

import com.liferay.frontend.js.importmaps.extender.internal.servlet.taglib.JSImportMapsExtenderTopHeadDynamicInclude;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera Avellón
 */
@Component(service = ModelListener.class)
public class CompanyModelListener extends BaseModelListener<Company> {

	@Override
	public void onAfterCreate(Company model) {
		_rebuildImportMaps();
	}

	@Override
	public void onAfterRemove(Company model) {
		_rebuildImportMaps();
	}

	private void _rebuildImportMaps() {
		JSImportMapsExtenderTopHeadDynamicInclude
			jsImportMapsExtenderTopHeadDynamicInclude =
				(JSImportMapsExtenderTopHeadDynamicInclude)_dynamicInclude;

		jsImportMapsExtenderTopHeadDynamicInclude.rebuildImportMaps(
			JSImportMapsExtenderTopHeadDynamicInclude.ALL_COMPANIES);
	}

	@Reference(
		target = "(component.name=com.liferay.frontend.js.importmaps.extender.internal.servlet.taglib.JSImportMapsExtenderTopHeadDynamicInclude)"
	)
	private DynamicInclude _dynamicInclude;

}