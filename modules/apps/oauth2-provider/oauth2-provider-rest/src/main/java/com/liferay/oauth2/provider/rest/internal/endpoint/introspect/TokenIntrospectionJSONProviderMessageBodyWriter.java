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

package com.liferay.oauth2.provider.rest.internal.endpoint.introspect;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;
import java.io.OutputStream;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import java.nio.charset.StandardCharsets;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import org.apache.cxf.rs.security.oauth2.utils.OAuthConstants;

import org.osgi.service.component.annotations.Component;

/**
 * @author Tomas Polesovsky
 */
@Component(
	property = {
		"osgi.jaxrs.application.select=(osgi.jaxrs.name=Liferay.OAuth2.Application)",
		"osgi.jaxrs.extension=true",
		"osgi.jaxrs.name=TokenIntrospectionJSONProviderMessageBodyWriter"
	},
	service = MessageBodyWriter.class
)
@Produces("application/json")
@Provider
public class TokenIntrospectionJSONProviderMessageBodyWriter
	implements MessageBodyWriter<TokenIntrospection> {

	@Override
	public long getSize(
		TokenIntrospection tokenIntrospection, Class<?> clazz, Type genericType,
		Annotation[] annotations, MediaType mediaType) {

		return -1;
	}

	@Override
	public boolean isWriteable(
		Class<?> clazz, Type genericType, Annotation[] annotations,
		MediaType mediaType) {

		return TokenIntrospection.class.isAssignableFrom(clazz);
	}

	@Override
	public void writeTo(
			TokenIntrospection tokenIntrospection, Class<?> clazz,
			Type genericType, Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders,
			OutputStream outputStream)
		throws IOException, WebApplicationException {

		if (!tokenIntrospection.isActive()) {
			StringBundler sb = new StringBundler(6);

			sb.append("{");

			append(sb, "active", false, false);

			sb.append("}");

			String string = sb.toString();

			outputStream.write(string.getBytes(StandardCharsets.UTF_8));

			outputStream.flush();

			return;
		}

		StringBundler sb = new StringBundler(72);

		sb.append("{");

		append(sb, "active", tokenIntrospection.isActive(), false);

		if (tokenIntrospection.getAud() != null) {
			List<String> audience = new ArrayList<>(
				tokenIntrospection.getAud());

			audience.removeIf(String::isEmpty);

			if (!audience.isEmpty()) {
				StringBundler audienceSB;

				if (audience.size() == 1) {
					audienceSB = new StringBundler(7);

					Iterator<String> iterator = audience.iterator();

					append(audienceSB, "aud", iterator.next());
				}
				else {
					audienceSB = new StringBundler(5);

					append(audienceSB, "aud", audience);
				}

				sb.append(audienceSB);
			}
		}

		append(sb, OAuthConstants.CLIENT_ID, tokenIntrospection.getClientId());

		append(sb, "exp", tokenIntrospection.getExp());
		append(sb, "iat", tokenIntrospection.getIat());
		append(sb, "iss", tokenIntrospection.getIss());
		append(sb, "jti", tokenIntrospection.getJti());
		append(sb, "nbf", tokenIntrospection.getNbf());
		append(sb, OAuthConstants.SCOPE, tokenIntrospection.getScope());
		append(sb, "sub", tokenIntrospection.getSub());
		append(
			sb, OAuthConstants.ACCESS_TOKEN_TYPE,
			tokenIntrospection.getTokenType());

		append(sb, "username", tokenIntrospection.getUsername());

		Map<String, String> extensions = tokenIntrospection.getExtensions();

		if (MapUtil.isNotEmpty(extensions)) {
			StringBundler extensionSB = new StringBundler(
				extensions.size() * 7);

			for (Map.Entry<String, String> extension : extensions.entrySet()) {
				append(extensionSB, extension.getKey(), extension.getValue());
			}

			sb.append(extensionSB);
		}

		sb.append("}");

		String result = sb.toString();

		outputStream.write(result.getBytes(StandardCharsets.UTF_8));

		outputStream.flush();
	}

	protected void append(StringBundler sb, String key, List<String> value) {
		StringBundler arraySB = new StringBundler((value.size() * 3 - 1) + 2);

		arraySB.append("[");

		for (int i = 0; i < value.size(); i++) {
			if (i > 0) {
				arraySB.append(",");
			}

			appendValue(arraySB, value.get(i), true);
		}

		arraySB.append("]");

		sb.append(",");

		append(sb, key, arraySB.toString(), false);
	}

	protected void append(StringBundler sb, String key, Long value) {
		if (value == null) {
			return;
		}

		sb.append(",");

		append(sb, key, value, false);
	}

	protected void append(
		StringBundler sb, String key, Object value, boolean quote) {

		sb.append("\"");
		sb.append(key);
		sb.append("\":");

		appendValue(sb, value, quote);
	}

	protected void append(StringBundler sb, String key, String value) {
		if (value == null) {
			return;
		}

		sb.append(",");

		append(sb, key, value, true);
	}

	protected void appendValue(StringBundler sb, Object value, boolean quote) {
		if (quote) {
			sb.append("\"");

			String stringValue;

			if (value == null) {
				stringValue = "null";
			}
			else {
				stringValue = StringUtil.replace(
					value.toString(), new String[] {"\\", "\""},
					new String[] {"\\\\", "\\\""});
			}

			sb.append(stringValue);

			sb.append("\"");
		}
		else {
			sb.append(value);
		}
	}

}