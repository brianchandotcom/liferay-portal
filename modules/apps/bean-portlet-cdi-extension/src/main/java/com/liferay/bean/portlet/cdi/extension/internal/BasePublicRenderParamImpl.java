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

package com.liferay.bean.portlet.cdi.extension.internal;

import javax.xml.namespace.QName;

/**
 * @author Neil Griffin
 */
public abstract class BasePublicRenderParamImpl implements PublicRenderParam {

	@Override
	public String getIdentifier() {
		return _id;
	}

	@Override
	public QName getQName() {
		return _qName;
	}

	@Override
	public void setIdentifier(String identifier) {
		_id = identifier;
	}

	@Override
	public void setQName(QName qName) {
		_qName = qName;
	}

	private String _id;
	private QName _qName;

}