/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.reports.engine.console.web.internal.display.lar;

import com.liferay.exportimport.kernel.lar.DataLevel;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.portal.reports.engine.console.constants.ReportsEngineConsolePortletKeys;
import com.liferay.portal.reports.engine.console.web.internal.admin.lar.AdminPortletDataHandler;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Arthur Chan
 */
@Component(
	property = "javax.portlet.name=" + ReportsEngineConsolePortletKeys.DISPLAY_REPORTS,
	service = PortletDataHandler.class
)
public class DisplayPortletDataHandler extends AdminPortletDataHandler {

	@Activate
	@Override
	protected void activate() {
		super.activate();

		setDataLevel(DataLevel.PORTLET_INSTANCE);
	}

}