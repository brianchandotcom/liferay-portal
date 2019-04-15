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

package com.liferay.talend.http.client.codec;

import com.liferay.talend.datastore.OAuthDataStore;
import com.liferay.talend.http.client.exception.EncoderException;

import org.talend.sdk.component.api.service.http.Encoder;

/**
 * @author Igor Beslic
 */
public class XWWWFormURLEncoder implements Encoder {

	@Override
	public byte[] encode(Object o) throws EncoderException {
		if (o instanceof OAuthDataStore) {
			return _encode((OAuthDataStore)o);
		}

		throw new EncoderException(
			"Unable to encode payload of type " + o.getClass());
	}

	private byte[] _encode(OAuthDataStore oAuthDataStore) {
		StringBuilder sb = new StringBuilder();

		sb.append("client_id=");
		sb.append(oAuthDataStore.getConsumerKey());
		sb.append("&");
		sb.append("client_secret=");
		sb.append(oAuthDataStore.getConsumerSecret());
		sb.append("&");
		sb.append("grant_type=client_credentials");
		sb.append("&");
		sb.append("response_type=code");

		String body = sb.toString();

		return body.getBytes();
	}

}