/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.saml.opensaml.integration.internal.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.saml.util.MetadataUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;

import net.shibboleth.utilities.java.support.xml.ParserPool;

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
@Component(immediate = true, service = MetadataUtil.class)
public class MetadataUtilImpl implements MetadataUtil {

	@Override
	public InputStream getMetadata(String url) throws Exception {
		HttpGet httpGet = new HttpGet(url);

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
	public String parseMetadataXml(InputStream inputStream, String entityId)
		throws Exception {

		try (InputStream is = inputStream) {
			XMLObject xmlObject = XMLObjectSupport.unmarshallFromInputStream(
				parserPool, inputStream);

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
	protected HttpClient httpClient;

	@Reference
	protected ParserPool parserPool;

}