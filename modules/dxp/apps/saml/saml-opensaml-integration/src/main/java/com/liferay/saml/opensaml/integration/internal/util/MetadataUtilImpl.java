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

package com.liferay.saml.opensaml.integration.internal.util;

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.saml.util.MetadataUtil;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;

import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;

import org.opensaml.saml2.metadata.EntityDescriptor;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.parse.ParserPool;
import org.opensaml.xml.util.XMLObjectHelper;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mika Koivisto
 */
@Component(
	configurationPid = "com.liferay.saml.runtime.configuration.MetadataUtilConfiguration",
	configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true,
	service = MetadataUtil.class
)
public class MetadataUtilImpl implements MetadataUtil {

	@Override
	public InputStream getMetadata(String url) {
		HttpGet httpGet = new HttpGet(url);

		try (CloseableHttpResponse closeableHttpResponse =
				(CloseableHttpResponse)httpClient.execute(httpGet)) {

			StatusLine statusLine = closeableHttpResponse.getStatusLine();

			if (statusLine.getStatusCode() != HttpStatus.SC_OK) {
				return null;
			}

			HttpEntity httpEntity = closeableHttpResponse.getEntity();

			InputStream inputStream = httpEntity.getContent();

			Header header = httpEntity.getContentEncoding();

			if (header != null) {
				String contentEncoding = header.getValue();

				if (StringUtil.equalsIgnoreCase(contentEncoding, "deflate")) {
					inputStream = new InflaterInputStream(inputStream);
				}
				else if (StringUtil.equalsIgnoreCase(contentEncoding, "gzip")) {
					inputStream = new GZIPInputStream(inputStream);
				}
			}

			UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
				new UnsyncByteArrayOutputStream();

			StreamUtil.transfer(inputStream, unsyncByteArrayOutputStream);

			byte[] bytes = unsyncByteArrayOutputStream.toByteArray();

			return new ByteArrayInputStream(bytes);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to get SAML metadata from " + url, e);
			}
			else if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get SAML metadata from " + url + ": " +
						e.getMessage());
			}
		}
		finally {
			httpGet.releaseConnection();
		}

		return null;
	}

	@Override
	public String parseMetadataXml(InputStream inputStream, String entityId)
		throws Exception {

		try (InputStream is = inputStream) {
			XMLObject xmlObject = XMLObjectHelper.unmarshallFromInputStream(
				parserPool, inputStream);

			EntityDescriptor entityDescriptor =
				SamlUtil.getEntityDescriptorById(entityId, xmlObject);

			if (entityDescriptor == null) {
				return null;
			}

			StringWriter stringWriter = new StringWriter();

			XMLObjectHelper.marshallToWriter(entityDescriptor, stringWriter);

			return stringWriter.toString();
		}
	}

	@Reference
	protected HttpClient httpClient;

	@Reference
	protected ParserPool parserPool;

	private static final Log _log = LogFactoryUtil.getLog(
		MetadataUtilImpl.class);

}