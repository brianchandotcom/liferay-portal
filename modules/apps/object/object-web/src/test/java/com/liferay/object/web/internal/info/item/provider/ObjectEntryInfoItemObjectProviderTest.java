/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.web.internal.info.item.provider;

import com.liferay.info.exception.NoSuchInfoItemException;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.ERCInfoItemIdentifier;
import com.liferay.info.item.InfoItemIdentifier;
import com.liferay.info.item.provider.filter.InfoItemServiceFilter;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManagerRegistry;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.web.internal.util.ObjectEntryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Lourdes Fernández Besada
 */
public class ObjectEntryInfoItemObjectProviderTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@AfterClass
	public static void tearDownClass() {
		_objectEntryUtilMockedStatic.close();
	}

	@Before
	public void setUp() {
		_objectDefinition = Mockito.mock(ObjectDefinition.class);
		_objectEntryLocalService = Mockito.mock(ObjectEntryLocalService.class);
		_objectEntryManagerRegistry = Mockito.mock(
			ObjectEntryManagerRegistry.class);

		_objectEntryManager = Mockito.mock(ObjectEntryManager.class);

		Mockito.when(
			_objectEntryManagerRegistry.getObjectEntryManager(null)
		).thenReturn(
			_objectEntryManager
		);

		_objectEntryUtilMockedStatic.reset();
	}

	@Test
	public void testGetInfoItemLiferayObjectEntry() throws Exception {
		long classPK = RandomTestUtil.randomLong();
		ObjectEntry objectEntry = Mockito.mock(ObjectEntry.class);

		Mockito.when(
			_objectEntryLocalService.fetchObjectEntry(classPK)
		).thenReturn(
			objectEntry
		);

		Assert.assertEquals(
			objectEntry,
			_assertGetInfoItem(new ClassPKInfoItemIdentifier(classPK)));

		Mockito.verifyNoInteractions(_objectEntryManagerRegistry);
	}

	@Test
	public void testGetInfoItemLiferayObjectEntryNoSuchInfoItemException() {
		long classPK = RandomTestUtil.randomLong();

		_assertGetInfoItemNoSuchInfoItemException(
			new ClassPKInfoItemIdentifier(classPK),
			"Unable to get object entry " + classPK);

		Mockito.verify(
			_objectEntryLocalService
		).fetchObjectEntry(
			classPK
		);

		Mockito.verifyNoInteractions(_objectEntryManagerRegistry);
	}

	@Test
	public void testGetInfoItemProxyObjectEntry() throws Exception {
		String externalReferenceCode = RandomTestUtil.randomString();
		ObjectEntry objectEntry = Mockito.mock(ObjectEntry.class);

		_setUpProxyObjectEntry(externalReferenceCode, objectEntry);

		Assert.assertEquals(
			objectEntry,
			_assertGetInfoItem(
				new ERCInfoItemIdentifier(externalReferenceCode)));

		Mockito.verifyNoInteractions(_objectEntryLocalService);
	}

	@Test
	public void testGetInfoItemProxyObjectEntryNoSuchInfoItemException()
		throws Exception {

		String externalReferenceCode = RandomTestUtil.randomString();

		_assertGetInfoItemNoSuchInfoItemException(
			new ERCInfoItemIdentifier(externalReferenceCode),
			"Unable to get object entry " + externalReferenceCode);

		Mockito.verifyNoInteractions(_objectEntryLocalService);
		Mockito.verify(
			_objectEntryManager
		).getObjectEntry(
			Mockito.eq(0L), Mockito.any(DefaultDTOConverterContext.class),
			Mockito.eq(externalReferenceCode), Mockito.eq(_objectDefinition),
			Mockito.eq(null)
		);
	}

	@Test
	public void testGetInfoItemUnsoportedInfoItemIdentifierNoSuchInfoItemException() {
		InfoItemIdentifier infoItemIdentifier = new InfoItemIdentifier() {

			@Override
			public InfoItemServiceFilter getInfoItemServiceFilter() {
				return null;
			}

			@Override
			public String getVersion() {
				return null;
			}

			@Override
			public void setVersion(String version) {
			}

		};

		_assertGetInfoItemNoSuchInfoItemException(
			infoItemIdentifier,
			"Unsupported info item identifier type " + infoItemIdentifier);

		Mockito.verifyNoInteractions(_objectEntryLocalService);
		Mockito.verifyNoInteractions(_objectEntryManagerRegistry);
	}

	private ObjectEntry _assertGetInfoItem(
			InfoItemIdentifier infoItemIdentifier)
		throws NoSuchInfoItemException {

		ObjectEntryInfoItemObjectProvider objectEntryInfoItemObjectProvider =
			new ObjectEntryInfoItemObjectProvider(
				_objectDefinition, _objectEntryLocalService,
				_objectEntryManagerRegistry);

		try {
			_pushServiceContext();

			return objectEntryInfoItemObjectProvider.getInfoItem(
				infoItemIdentifier);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	private void _assertGetInfoItemNoSuchInfoItemException(
		InfoItemIdentifier infoItemIdentifier, String message) {

		boolean noSuchInfoItemExceptionThrown = false;

		try {
			_assertGetInfoItem(infoItemIdentifier);
		}
		catch (NoSuchInfoItemException noSuchInfoItemException) {
			noSuchInfoItemExceptionThrown = true;

			Assert.assertEquals(message, noSuchInfoItemException.getMessage());
		}

		Assert.assertTrue(noSuchInfoItemExceptionThrown);
	}

	private void _pushServiceContext() {
		ServiceContext serviceContext = Mockito.mock(ServiceContext.class);

		ThemeDisplay themeDisplay = Mockito.mock(ThemeDisplay.class);

		Mockito.when(
			themeDisplay.getScopeGroup()
		).thenReturn(
			Mockito.mock(Group.class)
		);

		Mockito.when(
			serviceContext.getThemeDisplay()
		).thenReturn(
			themeDisplay
		);

		ServiceContextThreadLocal.pushServiceContext(serviceContext);
	}

	private void _setUpProxyObjectEntry(
			String externalReferenceCode, ObjectEntry objectEntry)
		throws Exception {

		com.liferay.object.rest.dto.v1_0.ObjectEntry proxyObjectEntry =
			Mockito.mock(com.liferay.object.rest.dto.v1_0.ObjectEntry.class);

		Mockito.when(
			_objectEntryManager.getObjectEntry(
				Mockito.eq(0L), Mockito.any(DefaultDTOConverterContext.class),
				Mockito.eq(externalReferenceCode),
				Mockito.eq(_objectDefinition), Mockito.eq(null))
		).thenReturn(
			proxyObjectEntry
		);

		_objectEntryUtilMockedStatic.when(
			() -> ObjectEntryUtil.toObjectEntry(0L, proxyObjectEntry)
		).thenReturn(
			objectEntry
		);
	}

	private static final MockedStatic<ObjectEntryUtil>
		_objectEntryUtilMockedStatic = Mockito.mockStatic(
			ObjectEntryUtil.class);

	private ObjectDefinition _objectDefinition;
	private ObjectEntryLocalService _objectEntryLocalService;
	private ObjectEntryManager _objectEntryManager;
	private ObjectEntryManagerRegistry _objectEntryManagerRegistry;

}