/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.image.internal.url;

import com.liferay.adaptive.media.AMURIResolver;
import com.liferay.adaptive.media.image.configuration.AMImageConfigurationEntry;
import com.liferay.adaptive.media.image.internal.configuration.AMImageConfigurationEntryImpl;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.net.URI;

import java.util.Date;
import java.util.HashMap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Alejandro Tardín
 */
public class AMImageURLFactoryImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		Mockito.when(
			_fileVersion.getFileEntryId()
		).thenReturn(
			1L
		);

		Mockito.when(
			_fileVersion.getFileName()
		).thenReturn(
			"fileName"
		);

		Mockito.when(
			_fileVersion.getFileVersionId()
		).thenReturn(
			2L
		);

		Mockito.when(
			_fileVersion.getModifiedDate()
		).thenReturn(
			_modifiedDate
		);

		Mockito.when(
			_amURIResolver.resolveURI(Mockito.any(URI.class))
		).thenAnswer(
			invocation -> URI.create("prefix/" + invocation.getArguments()[0])
		);

		ReflectionTestUtil.setFieldValue(
			_amImageURLFactoryImpl, "_amURIResolver", _amURIResolver);
	}

	@Test
	public void testCreatesURLForFileEntry() throws Exception {
		URI uri = _amImageURLFactoryImpl.createFileEntryURL(
			_fileVersion, _amImageConfigurationEntry);

		Assert.assertEquals(
			"prefix/image/1/theUuid/fileName?t=" + _modifiedDate.getTime(),
			uri.toString());
	}

	@Test
	public void testCreatesURLForFileVersion() throws Exception {
		URI uri = _amImageURLFactoryImpl.createFileVersionURL(
			_fileVersion, _amImageConfigurationEntry);

		Assert.assertEquals(
			"prefix/image/1/2/theUuid/fileName", uri.toString());
	}

	private static final String _UUID = "theUuid";

	private final AMImageConfigurationEntry _amImageConfigurationEntry =
		new AMImageConfigurationEntryImpl("small", _UUID, new HashMap<>());
	private final AMImageURLFactoryImpl _amImageURLFactoryImpl =
		new AMImageURLFactoryImpl();
	private final AMURIResolver _amURIResolver = Mockito.mock(
		AMURIResolver.class);
	private final FileVersion _fileVersion = Mockito.mock(FileVersion.class);
	private final Date _modifiedDate = new Date();

}