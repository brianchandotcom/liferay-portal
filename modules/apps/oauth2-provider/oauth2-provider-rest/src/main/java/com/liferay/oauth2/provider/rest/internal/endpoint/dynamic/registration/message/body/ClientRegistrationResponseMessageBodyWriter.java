/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth2.provider.rest.internal.endpoint.dynamic.registration.message.body;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;

import com.liferay.oauth2.provider.rest.internal.endpoint.authorize.message.body.BaseMessageBodyWriter;
import com.liferay.oauth2.provider.rest.internal.endpoint.dynamic.registration.model.LiferayClientRegistrationResponse;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;

import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.MessageBodyWriter;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;
import java.io.OutputStream;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import java.nio.charset.StandardCharsets;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jorge García Jiménez
 */
@Component(
	property = {
		"osgi.jaxrs.application.select=(osgi.jaxrs.name=Liferay.OAuth2.Application)",
		"osgi.jaxrs.extension=true",
		"osgi.jaxrs.name=ClientRegistrationResponseMessageBodyWriter"
	},
	service = MessageBodyWriter.class
)
@Produces("application/json")
@Provider
public class ClientRegistrationResponseMessageBodyWriter
	extends BaseMessageBodyWriter<LiferayClientRegistrationResponse> {

	@Override
	public boolean isWriteable(
		Class<?> clazz, Type type, Annotation[] annotations,
		MediaType mediaType) {

		if (LiferayClientRegistrationResponse.class.isAssignableFrom(clazz) &&
			StringUtil.equalsIgnoreCase(mediaType.getType(), "application") &&
			StringUtil.equalsIgnoreCase(mediaType.getSubtype(), "json")) {

			return true;
		}

		return false;
	}

	@Override
	public void writeTo(
			LiferayClientRegistrationResponse liferayClientRegistrationResponse,
			Class<?> clazz, Type type, Annotation[] annotations,
			MediaType mediaType, MultivaluedMap<String, Object> multivaluedMap,
			OutputStream outputStream)
		throws WebApplicationException {

		ObjectMapper objectMapper = ObjectMapperHolder._objectMapper;

		try {
			String string = objectMapper.writeValueAsString(
				liferayClientRegistrationResponse);

			outputStream.write(string.getBytes(StandardCharsets.UTF_8));
		}
		catch (IOException ioException) {
			if (_log.isDebugEnabled()) {
				_log.debug(ioException);
			}

			throw new WebApplicationException(
				Response.status(
					Response.Status.INTERNAL_SERVER_ERROR
				).build());
		}
	}

	@Override
	protected String writeTo(
		LiferayClientRegistrationResponse clientRegistrationResponse,
		String authorizeScreenURL) {

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ClientRegistrationResponseMessageBodyWriter.class);

	private static class ObjectMapperHolder {

		private static final ObjectMapper _objectMapper = new ObjectMapper() {
			{
				configure(
					DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
				setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
				setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
				setSerializationInclusion(JsonInclude.Include.NON_NULL);
			}
		};

	}

}