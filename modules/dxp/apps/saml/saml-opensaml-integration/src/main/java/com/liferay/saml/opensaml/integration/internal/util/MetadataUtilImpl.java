/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.opensaml.integration.internal.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.saml.opensaml.integration.internal.bootstrap.ParserPoolUtil;
import com.liferay.saml.opensaml.integration.internal.transport.HttpClientFactory;
import com.liferay.saml.util.MetadataUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mika Koivisto
 */
@Component(service = MetadataUtil.class)
public class MetadataUtilImpl implements MetadataUtil {

	@Override
	public InputStream getMetadata(String url) throws Exception {
		HttpGet httpGet = new HttpGet(url);

		HttpClient httpClient = httpClientFactory.getHttpClient();

		try (CloseableHttpResponse closeableHttpResponse =
				(CloseableHttpResponse)httpClient.execute(httpGet)) {

			StatusLine statusLine = closeableHttpResponse.getStatusLine();

			if (statusLine.getStatusCode() != HttpStatus.SC_OK) {
				throw new Exception(
					StringBundler.concat(
						"Unable to get SAML metadata from ", url,
						", invalid status code returned: ",
						statusLine.getStatusCode(), " ",
						statusLine.getReasonPhrase()));
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
		finally {
			httpGet.releaseConnection();
		}
	}

	@Override
	public String parseMetadataXml(InputStream inputStream1, String entityId)
		throws Exception {

		try (InputStream inputStream2 = inputStream1) {
			XMLObject xmlObject = XMLObjectSupport.unmarshallFromInputStream(
				ParserPoolUtil.getParserPool(), inputStream1);

			EntityDescriptor entityDescriptor =
				SamlUtil.getEntityDescriptorById(entityId, xmlObject);

			if (entityDescriptor == null) {
				return null;
			}

			ByteArrayOutputStream byteArrayOutputStream =
				new ByteArrayOutputStream();

			XMLObjectSupport.marshallToOutputStream(
				entityDescriptor, byteArrayOutputStream);

			return byteArrayOutputStream.toString();
		}
	}

	@Reference
	protected HttpClientFactory httpClientFactory;

}