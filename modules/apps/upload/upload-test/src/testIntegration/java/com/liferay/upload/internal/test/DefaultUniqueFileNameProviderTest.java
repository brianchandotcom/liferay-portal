/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.upload.internal.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.upload.configuration.UploadServletRequestConfigurationProviderUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.upload.UniqueFileNameProvider;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alejandro Tardín
 * @author Brian I. Kim
 */
@RunWith(Arquillian.class)
public class DefaultUniqueFileNameProviderTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testAppendsAParentheticalSuffixIfTheFileExists()
		throws Exception {

		String originalFileName = "filename.extension";

		for (int i = 1;
			 i <= UploadServletRequestConfigurationProviderUtil.getMaxTries();
			 i++) {

			String uniqueFileName = _uniqueFileNameProvider.provide(
				originalFileName, _existsUntil(i));

			Assert.assertEquals(
				"filename (" + i + ").extension", uniqueFileName);
		}
	}

	@Test
	public void testAppendsAParentheticalSuffixIfTheFileExistsWithNoExtension()
		throws Exception {

		String originalFileName = "filename";

		String uniqueFileName = _uniqueFileNameProvider.provide(
			originalFileName, _existsUntil(1));

		Assert.assertEquals("filename (1)", uniqueFileName);
	}

	@Test(expected = PortalException.class)
	public void testGivesUpIfTheFileExistsNTimes() throws Exception {
		_uniqueFileNameProvider.provide(
			"filename.extension",
			_existsUntil(
				UploadServletRequestConfigurationProviderUtil.getMaxSize() +
					1));
	}

	@Test
	public void testModifiesOnlyTheLastExistingParentheticalSuffix()
		throws Exception {

		String originalFileName = "filename (1) (2) (3) (4) (1).extension";

		String uniqueFileName = _uniqueFileNameProvider.provide(
			originalFileName, _existsUntil(2));

		Assert.assertEquals(
			"filename (1) (2) (3) (4) (2).extension", uniqueFileName);
	}

	@Test
	public void testModifiesTheExistingParentheticalSuffix() throws Exception {
		String originalFileName = "filename (1).extension";

		String uniqueFileName = _uniqueFileNameProvider.provide(
			originalFileName, _existsUntil(2));

		Assert.assertEquals("filename (2).extension", uniqueFileName);
	}

	@Test
	public void testModifiesTheExistingParentheticalSuffixWithNoExtension()
		throws Exception {

		String originalFileName = "filename (1)";

		String uniqueFileName = _uniqueFileNameProvider.provide(
			originalFileName, _existsUntil(2));

		Assert.assertEquals("filename (2)", uniqueFileName);
	}

	@Test
	public void testReturnsTheSameFileNameIfTheFileDoesNotExist()
		throws Exception {

		String originalFileName = "filename.extension";

		String uniqueFileName = _uniqueFileNameProvider.provide(
			originalFileName, fileName -> false);

		Assert.assertEquals(originalFileName, uniqueFileName);
	}

	private Predicate<String> _existsUntil(long n) {
		AtomicInteger atomicInteger = new AtomicInteger(0);

		return fileName -> atomicInteger.addAndGet(1) <= n;
	}

	@Inject
	private UniqueFileNameProvider _uniqueFileNameProvider;

}