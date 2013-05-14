/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.xml.QName;
import com.liferay.portal.model.PortletApp;
import com.liferay.portal.model.PublicRenderParameter;

/**
 * @author Brian Wing Shun Chan
 */
public class PublicRenderParameterImpl implements PublicRenderParameter {

	public PublicRenderParameterImpl(
		String identifier, QName qName, PortletApp portletApp) {

		_identifier = identifier;
		_qName = qName;
		_portletApp = portletApp;
	}

	@Override
	public String getIdentifier() {
		return _identifier;
	}

	@Override
	public PortletApp getPortletApp() {
		return _portletApp;
	}

	@Override
	public QName getQName() {
		return _qName;
	}

	@Override
	public void setIdentifier(String identifier) {
		_identifier = identifier;
	}

	@Override
	public void setPortletApp(PortletApp portletApp) {
		_portletApp = portletApp;
	}

	@Override
	public void setQName(QName qName) {
		_qName = qName;
	}

	private String _identifier;
	private PortletApp _portletApp;
	private QName _qName;

}