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

package com.liferay.portal.portlet.tracker.internal;

import com.liferay.portlet.PortletBagFactory;

import javax.portlet.Portlet;

/**
 * @author Raymond Augé
 */
public class BundlePortletBagFactory extends PortletBagFactory {

	public BundlePortletBagFactory(Portlet portlet) {
		_portlet = portlet;
	}

	@Override
	protected Portlet getPortletInstance(
		com.liferay.portal.model.Portlet portletModel) {

		return _portlet;
	}

	private final Portlet _portlet;

}