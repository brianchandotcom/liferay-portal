/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.list.internal.exportimport.content.processor;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * @author Gustavo Lima
 */
public class AssetListEntryExportImportContentProcessorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_assetListEntryExportImportContentProcessor =
			new AssetListEntryExportImportContentProcessor();
	}

	@Test
	public void testAddGroupMappingsElementWithInexistentGroup() {
		PortletDataContext portletDataContext = Mockito.mock(
			PortletDataContext.class);

		Element rootElement = Mockito.mock(Element.class);

		Mockito.doReturn(
			rootElement
		).when(
			portletDataContext
		).getExportDataRootElement();

		Element groupIdMappingsElement = Mockito.spy(Element.class);

		Mockito.doReturn(
			groupIdMappingsElement
		).when(
			rootElement
		).addElement(
			Mockito.anyString()
		);

		GroupLocalService groupLocalService = Mockito.mock(
			GroupLocalService.class);

		Mockito.doReturn(
			null
		).when(
			groupLocalService
		).fetchGroup(
			Mockito.anyLong()
		);

		ReflectionTestUtil.setFieldValue(
			_assetListEntryExportImportContentProcessor, "_groupLocalService",
			groupLocalService);

		_assetListEntryExportImportContentProcessor.addGroupMappingsElement(
			portletDataContext, new long[] {RandomTestUtil.randomLong()});

		Mockito.verify(
			groupIdMappingsElement, Mockito.never()
		).addElement(
			Mockito.anyString()
		);
	}

	private AssetListEntryExportImportContentProcessor
		_assetListEntryExportImportContentProcessor;

}