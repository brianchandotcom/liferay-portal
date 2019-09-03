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

package com.liferay.mail.reader.web.internal.portlet.action;

import com.liferay.osgi.util.ComponentUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageListener;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Shuyang Zhou
 */
@Component(immediate = true, service = {})
public class LoginPostActionEnabler {

	@Activate
	public void activate(ComponentContext componentContext) {
		ComponentUtil.enableComponents(
			MessageListener.class,
			"(destination.name=" + DestinationNames.MAIL_SYNCHRONIZER + ")",
			componentContext, LoginPostAction.class);
	}

	@Deactivate
	public void deactivate(ComponentContext componentContext) {
		componentContext.disableComponent(LoginPostAction.class.getName());
	}

}