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

package com.liferay.oauth2.provider.service.impl;

import com.liferay.osgi.util.ComponentUtil;
import com.liferay.portlet.documentlibrary.store.StoreFactory;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Shuyang Zhou
 */
@Component(service = {})
public class OAuth2ApplicationLocalServiceImplEnabler {

	@Activate
	protected void activate(ComponentContext componentContext) {
		ComponentUtil.enableComponents(
			StoreFactory.class, "(dl.store.impl.enabled=true)",
			componentContext, OAuth2ApplicationLocalServiceImpl.class);
	}

}