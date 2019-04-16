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

import com.liferay.talend.data.store.OAuth2DataStore;
import com.liferay.talend.http.client.exception.EncoderException;

import org.talend.sdk.component.api.service.http.Encoder;

/**
 * @author Igor Beslic
 */
public class XWWWFormURLEncoder implements Encoder {

	@Override
	public byte[] encode(Object object) throws EncoderException {
		if (object instanceof OAuth2DataStore) {
			return _encode((OAuth2DataStore)object);
		}

		throw new EncoderException(
			"Unable to encode payload of type " + object.getClass());
	}

	private byte[] _encode(OAuth2DataStore oAuth2DataStore) {
		StringBuilder sb = new StringBuilder();

		sb.append("client_id=");
		sb.append(oAuth2DataStore.getConsumerKey());
		sb.append("&");
		sb.append("client_secret=");
		sb.append(oAuth2DataStore.getConsumerSecret());
		sb.append("&");
		sb.append("grant_type=client_credentials");
		sb.append("&");
		sb.append("response_type=code");

		String body = sb.toString();

		return body.getBytes();
	}

}