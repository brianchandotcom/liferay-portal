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

package com.liferay.portal.security.sso.openid.connect.internal.provider;

import com.liferay.petra.io.Deserializer;
import com.liferay.petra.io.Serializer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectSession;
import com.liferay.portal.security.sso.openid.connect.constants.OpenIdConnectWebKeys;
import com.liferay.portal.security.sso.openid.connect.provider.OpenIdConnectSessionProvider;

import java.io.Serializable;

import java.nio.ByteBuffer;

import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;

/**
 * @author Istvan Sajtos
 */
@Component(
	service = {
		OpenIdConnectSessionProvider.class,
		OpenIdConnectSessionProviderImpl.class
	}
)
public class OpenIdConnectSessionProviderImpl
	implements OpenIdConnectSessionProvider {

	public static void setOpenIdConnectSession(
		HttpSession httpSession, OpenIdConnectSession openIdConnectSession) {

		httpSession.setAttribute(
			OpenIdConnectWebKeys.OPEN_ID_CONNECT_SESSION,
			_getData((Serializable)openIdConnectSession));
	}

	public OpenIdConnectSession getOpenIdConnectSession(
		HttpSession httpSession) {

		byte[] data = (byte[])httpSession.getAttribute(
			OpenIdConnectWebKeys.OPEN_ID_CONNECT_SESSION);

		if (data == null) {
			return null;
		}

		return (OpenIdConnectSession)_getSerializable(data);
	}

	private static byte[] _getData(Serializable serializable) {
		Serializer serializer = new Serializer();

		serializer.writeObject(serializable);

		ByteBuffer byteBuffer = serializer.toByteBuffer();

		return byteBuffer.array();
	}

	private Serializable _getSerializable(byte[] data) {
		Deserializer deserializer = new Deserializer(ByteBuffer.wrap(data));

		try {
			return deserializer.readObject();
		}
		catch (ClassNotFoundException classNotFoundException) {
			_log.error("Unable to deserialize object", classNotFoundException);

			return null;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OpenIdConnectSessionProviderImpl.class);

}