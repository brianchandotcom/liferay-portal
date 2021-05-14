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

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.oauth2.provider.constants.GrantType;
import com.liferay.oauth2.provider.internal.test.TestPreviewURLApplication;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Arrays;
import java.util.Collections;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.ext.RuntimeDelegate;

import org.apache.cxf.jaxrs.client.spec.ClientBuilderImpl;
import org.apache.cxf.jaxrs.impl.RuntimeDelegateImpl;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.ServiceReference;

/**
 * @author Víctor Galán
 */
@RunWith(Arquillian.class)
public class OAuth2WebServerServletTest extends BaseClientTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

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
					null, user.getUserId(), user.getGroupId(), 0,
					"test-file.txt", "text/plain",
					_TEST_FILE_CONTENT.getBytes(), null, null,
					new ServiceContext());

				autoCloseables.add(
					() -> {
						dlAppLocalService.deleteFileEntry(
							fileEntry.getFileEntryId());

						bundleContext.ungetService(
							dlAppLocalServiceServiceReference);
						bundleContext.ungetService(dlUrlHelperServiceReference);
					});

				DLURLHelper dlURLHelper = bundleContext.getService(
					dlUrlHelperServiceReference);

				previewURL = dlURLHelper.getPreviewURL(
					fileEntry, fileEntry.getFileVersion(), null, "", false,
					false);
			}
			catch (Exception exception) {
				bundleContext.ungetService(dlAppLocalServiceServiceReference);
				bundleContext.ungetService(dlUrlHelperServiceReference);

				throw exception;
			}

			registerJaxRsApplication(
				new TestPreviewURLApplication(previewURL), "preview-url",
				HashMapDictionaryBuilder.<String, Object>put(
					"osgi.jaxrs.name", TestPreviewURLApplication.class.getName()
				).build());

			createOAuth2Application(
				defaultCompanyId, user, "oauthTestApplication",
				Collections.singletonList(GrantType.CLIENT_CREDENTIALS),
				Arrays.asList("GET", "everything.read.documents.download"));
		}

	}

	@Override
	protected BundleActivator getBundleActivator() {
		return new OAuth2WebServerServletTestPreparator();
	}

	private WebTarget _getRootWebTarget(String path) {
		ClientBuilder clientBuilder = new ClientBuilderImpl();

		Client client = clientBuilder.build();

		RuntimeDelegate runtimeDelegate = new RuntimeDelegateImpl();

		UriBuilder uriBuilder = runtimeDelegate.createUriBuilder();

		return client.target(uriBuilder.uri("http://localhost:8080" + path));
	}

	private static final String _TEST_FILE_CONTENT = "Test File Content";

}