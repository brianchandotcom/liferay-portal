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

package com.liferay.oauth2.provider.client.test;

import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.oauth2.provider.constants.GrantType;
import com.liferay.oauth2.provider.test.internal.TestPreviewURLApplication;
import com.liferay.oauth2.provider.test.internal.activator.BaseTestPreparatorBundleActivator;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.PortalUtil;

import java.net.URISyntaxException;
import java.net.URL;

import java.util.Arrays;
import java.util.Collections;
import java.util.Dictionary;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.ServiceReference;

/**
 * @author Víctor Galán
 */
@RunAsClient
@RunWith(Arquillian.class)
public class OAuth2WebServerServletTest extends BaseClientTestCase {

	@Deployment
	public static Archive<?> getArchive() throws Exception {
		return BaseClientTestCase.getArchive(
			OAuth2WebServerServletTestPreparator.class);
	}

	@Test
	public void test() throws Exception {
		String tokenString = getToken("oauthTestApplication");

		WebTarget webTarget = getWebTarget("/preview-url");

		Invocation.Builder builder = authorize(
			webTarget.request(), tokenString);

		String previewURL = builder.get(String.class);

		WebTarget previewURLWebTarget = _getRootWebTarget(previewURL);

		Invocation.Builder unauthorizedBuilder = previewURLWebTarget.request();

		Response unauthorizedResponse = unauthorizedBuilder.get();

		Assert.assertNotEquals(200, unauthorizedResponse.getStatus());

		Invocation.Builder authorizedBuilder = authorize(
			unauthorizedBuilder, tokenString);

		String fileContent = authorizedBuilder.get(String.class);

		Assert.assertEquals(_TEST_FILE_CONTENT, fileContent);
	}

	public static class OAuth2WebServerServletTestPreparator
		extends BaseTestPreparatorBundleActivator {

		@Override
		protected void prepareTest() throws Exception {
			long defaultCompanyId = PortalUtil.getDefaultCompanyId();

			String previewURL = null;

			ServiceReference<DLAppLocalService>
				dlAppLocalServiceServiceReference =
					bundleContext.getServiceReference(DLAppLocalService.class);
			ServiceReference<DLURLHelper> dlUrlHelperServiceReference =
				bundleContext.getServiceReference(DLURLHelper.class);

			User user = UserTestUtil.getAdminUser(defaultCompanyId);

			try {
				DLAppLocalService dlAppLocalService = bundleContext.getService(
					dlAppLocalServiceServiceReference);

				FileEntry fileEntry = dlAppLocalService.addFileEntry(
					user.getUserId(), user.getGroupId(), 0, "test-file.txt",
					"text/plain", _TEST_FILE_CONTENT.getBytes(),
					new ServiceContext());

				autoCloseables.add(
					() -> {
						dlAppLocalService.deleteFileEntry(
							fileEntry.getFileEntryId());

						bundleContext.ungetService(
							dlAppLocalServiceServiceReference);
						bundleContext.ungetService(dlUrlHelperServiceReference);
					});

				DLURLHelper dlurlHelper = bundleContext.getService(
					dlUrlHelperServiceReference);

				previewURL = dlurlHelper.getPreviewURL(
					fileEntry, fileEntry.getFileVersion(), null, "", false,
					false);
			}
			catch (Exception e) {
				bundleContext.ungetService(dlAppLocalServiceServiceReference);
				bundleContext.ungetService(dlUrlHelperServiceReference);

				throw e;
			}

			Dictionary<String, Object> properties = new HashMapDictionary<>();

			properties.put(
				"osgi.jaxrs.name", TestPreviewURLApplication.class.getName());

			registerJaxRsApplication(
				new TestPreviewURLApplication(previewURL), "preview-url",
				properties);

			createOAuth2Application(
				defaultCompanyId, user, "oauthTestApplication",
				Collections.singletonList(GrantType.CLIENT_CREDENTIALS),
				Arrays.asList("GET", "everything.read.documents.download"));
		}

	}

	private WebTarget _getRootWebTarget(String path) throws URISyntaxException {
		Client client = getClient();

		return client.target(_url.toURI() + path);
	}

	private static final String _TEST_FILE_CONTENT = "Test File Content";

	@ArquillianResource
	private URL _url;

}