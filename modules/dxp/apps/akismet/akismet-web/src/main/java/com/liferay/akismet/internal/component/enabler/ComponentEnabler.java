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

package com.liferay.akismet.internal.component.enabler;

import com.liferay.akismet.client.AkismetClient;
import com.liferay.akismet.internal.application.list.ModerationPanelApp;
import com.liferay.akismet.internal.portlet.ModerationPortlet;
import com.liferay.akismet.internal.portlet.action.AkismetEditMessageMVCActionCommand;
import com.liferay.akismet.internal.servlet.taglib.MBMessageHeaderJSPDynamicInclude;
import com.liferay.osgi.util.ComponentUtil;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Tina Tian
 */
@Component(immediate = true, service = {})
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