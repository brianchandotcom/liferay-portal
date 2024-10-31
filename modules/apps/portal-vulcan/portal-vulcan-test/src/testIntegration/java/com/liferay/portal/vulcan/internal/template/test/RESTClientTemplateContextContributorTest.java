/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.internal.template.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.HTTPTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.kernel.zip.ZipWriterFactory;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.FileInputStream;
import java.io.InputStream;

import java.net.URL;

import java.util.Enumeration;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Alejandro Tardín
 */
@RunWith(Arquillian.class)
public class RESTClientTemplateContextContributorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void test() throws Exception {
		Bundle bundle = FrameworkUtil.getBundle(
			RESTClientTemplateContextContributorTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		bundle = bundleContext.installBundle(
			RandomTestUtil.randomString(), _toInputStream());

		bundle.start();

		try {
			Assert.assertEquals(
				200,
				HTTPTestUtil.invokeToHttpCode(
					JSONUtil.put(
						"name", RandomTestUtil.randomString()
					).put(
						"templateKey",
						"com.liferay.portal.vulcan.test.site.initializer"
					).put(
						"templateType", "site-initializer"
					).toString(),
					"headless-site/v1.0/sites", Http.Method.POST));
		}
		finally {
			bundle.uninstall();
		}
	}

	private InputStream _toInputStream() throws Exception {
		ZipWriter zipWriter = _zipWriterFactory.getZipWriter();

		String basePath =
			"com/liferay/portal/vulcan/internal/template/test/dependencies" +
				"/site-initializer/";

		Bundle bundle = FrameworkUtil.getBundle(
			RESTClientTemplateContextContributorTest.class);

		Enumeration<URL> enumeration = bundle.findEntries(basePath, "*", true);

		if (enumeration != null) {
			while (enumeration.hasMoreElements()) {
				URL url = enumeration.nextElement();

				String urlPath = url.getPath();

				if (urlPath.endsWith(StringPool.SLASH)) {
					continue;
				}

				String zipPath = urlPath.substring(basePath.length());

				if (zipPath.startsWith(StringPool.SLASH)) {
					zipPath = zipPath.substring(1);
				}

				try (InputStream inputStream = url.openStream()) {
					zipWriter.addEntry(zipPath, inputStream);
				}
			}
		}

		return new FileInputStream(zipWriter.getFile());
	}

	@Inject
	private ZipWriterFactory _zipWriterFactory;

}