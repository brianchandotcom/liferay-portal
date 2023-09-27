/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.akismet.web.internal.component.enabler;

import com.liferay.akismet.client.AkismetClient;
import com.liferay.akismet.web.internal.application.list.ModerationPanelApp;
import com.liferay.akismet.web.internal.portlet.ModerationPortlet;
import com.liferay.akismet.web.internal.portlet.action.AkismetEditMessageMVCActionCommand;
import com.liferay.akismet.web.internal.servlet.taglib.MBMessageHeaderJSPDynamicInclude;
import com.liferay.osgi.util.ComponentUtil;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Tina Tian
 */
@Component(service = {})
public class ComponentEnabler {

	@Activate
	protected void activate(ComponentContext componentContext) {
		ComponentUtil.enableComponents(
			AkismetClient.class, null, componentContext,
			AkismetEditMessageMVCActionCommand.class,
			MBMessageHeaderJSPDynamicInclude.class, ModerationPanelApp.class,
			ModerationPortlet.class);
	}

}