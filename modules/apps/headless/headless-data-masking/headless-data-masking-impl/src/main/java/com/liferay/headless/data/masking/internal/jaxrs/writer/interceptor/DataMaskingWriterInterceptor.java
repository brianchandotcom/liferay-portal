/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.data.masking.internal.jaxrs.writer.interceptor;

import com.liferay.headless.data.masking.engine.DataMaskingEngine;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import jakarta.annotation.Priority;

import jakarta.servlet.http.HttpServletRequest;

import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.ext.WriterInterceptor;
import jakarta.ws.rs.ext.WriterInterceptorContext;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Jose Luis Navarro
 */
@Component(
	property = {
		"osgi.jaxrs.application.select=(osgi.jaxrs.name=*)",
		"osgi.jaxrs.extension=true",
		"osgi.jaxrs.name=Liferay.Headless.Data.Masking.WriterInterceptor"
	},
	scope = ServiceScope.PROTOTYPE, service = WriterInterceptor.class
)
@Priority(Priorities.USER - 100)
public class DataMaskingWriterInterceptor implements WriterInterceptor {

	@Override
	public void aroundWriteTo(WriterInterceptorContext writerInterceptorContext)
		throws IOException {

		List<String> maskExternalReferenceCodes = _parseDataMasksHeader(
			_httpServletRequest.getHeader(_HEADER_DATA_MASKS));

		if (maskExternalReferenceCodes.isEmpty() ||
			!_isRedactableMediaType(writerInterceptorContext.getMediaType())) {

			writerInterceptorContext.proceed();

			return;
		}

		long companyId = _portal.getCompanyId(_httpServletRequest);

		OutputStream originalOutputStream =
			writerInterceptorContext.getOutputStream();

		ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		writerInterceptorContext.setOutputStream(byteArrayOutputStream);

		try {
			writerInterceptorContext.proceed();

			Charset charset = _resolveCharset(
				writerInterceptorContext.getMediaType());

			String body = byteArrayOutputStream.toString(charset);

			String redacted = _dataMaskingEngine.redact(
				companyId, maskExternalReferenceCodes, body);

			originalOutputStream.write(redacted.getBytes(charset));
		}
		finally {
			writerInterceptorContext.setOutputStream(originalOutputStream);
		}
	}

	private boolean _isRedactableMediaType(MediaType mediaType) {
		if ((mediaType != null) &&
			(mediaType.isCompatible(MediaType.APPLICATION_JSON_TYPE) ||
			 mediaType.isCompatible(MediaType.TEXT_PLAIN_TYPE))) {

			return true;
		}

		return false;
	}

	private List<String> _parseDataMasksHeader(String headerValue) {
		List<String> externalReferenceCodes = new ArrayList<>();

		if (Validator.isNull(headerValue)) {
			return externalReferenceCodes;
		}

		for (String token : StringUtil.split(headerValue, ',')) {
			String trimmed = token.trim();

			if (!trimmed.isEmpty()) {
				externalReferenceCodes.add(trimmed);
			}
		}

		return externalReferenceCodes;
	}

	private Charset _resolveCharset(MediaType mediaType) {
		if (mediaType == null) {
			return StandardCharsets.UTF_8;
		}

		String charset = mediaType.getParameters(
		).get(
			MediaType.CHARSET_PARAMETER
		);

		if (Validator.isNull(charset)) {
			return StandardCharsets.UTF_8;
		}

		try {
			return Charset.forName(charset);
		}
		catch (RuntimeException runtimeException) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to resolve charset \"" + charset +
						"\"; falling back to UTF-8",
					runtimeException);
			}

			return StandardCharsets.UTF_8;
		}
	}

	private static final String _HEADER_DATA_MASKS = "X-Liferay-Data-Masks";

	private static final Log _log = LogFactoryUtil.getLog(
		DataMaskingWriterInterceptor.class);

	@Reference
	private DataMaskingEngine _dataMaskingEngine;

	@Context
	private HttpServletRequest _httpServletRequest;

	@Reference
	private Portal _portal;

}