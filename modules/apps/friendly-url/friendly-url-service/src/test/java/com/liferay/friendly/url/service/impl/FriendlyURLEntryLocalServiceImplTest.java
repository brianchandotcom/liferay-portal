/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.friendly.url.service.impl;

import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.batch.engine.thread.local.BatchEngineThreadLocal;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.exportimport.kernel.lar.ExportImportHelper;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.friendly.url.model.FriendlyURLEntry;
import com.liferay.friendly.url.model.FriendlyURLEntryLocalization;
import com.liferay.friendly.url.model.FriendlyURLEntryMapping;
import com.liferay.friendly.url.service.persistence.FriendlyURLEntryLocalizationPersistence;
import com.liferay.friendly.url.service.persistence.FriendlyURLEntryMappingPersistence;
import com.liferay.friendly.url.service.persistence.FriendlyURLEntryPersistence;
import com.liferay.layout.test.util.LayoutFriendlyURLRandomizerBumper;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ModelHintsUtil;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.FriendlyURLNormalizer;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;

/**
 * @author Lourdes Fernández Besada
 */
public class FriendlyURLEntryLocalServiceImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		Mockito.when(
			ModelHintsUtil.getMaxLength(
				FriendlyURLEntryLocalization.class.getName(), "urlTitle")
		).thenReturn(
			75
		);

		Mockito.when(
			ResourceActionsUtil.getCompositeModelNameSeparator()
		).thenReturn(
			StringPool.DASH
		);
	}

	@After
	public void tearDown() throws Exception {
		_batchEngineThreadLocalMockedStatic.close();
		_exportImportThreadLocalMockedStatic.close();
		_modelHintsUtilMockedStatic.close();
		_resourceActionsUtilMockedStatic.close();
	}

	@Test
	@TestInfo("LPD-74703")
	public void testAddFriendlyURLEntry() throws Exception {
		_mockThreadLocals(false, false);

		_testAddFriendlyURLEntry(0, null, 1);

		_mockThreadLocals(false, true);

		_testAddFriendlyURLEntry(0, null, 0);

		_mockThreadLocals(true, false);

		_testAddFriendlyURLEntry(1, null, 1);

		_mockThreadLocals(true, true);

		_testAddFriendlyURLEntry(1, null, 0);

		Portlet portlet = Mockito.mock(Portlet.class);

		PortletDataHandler portletDataHandler = Mockito.mock(
			PortletDataHandler.class);

		Mockito.when(
			portlet.getPortletDataHandlerInstance()
		).thenReturn(
			portletDataHandler
		);

		_testAddFriendlyURLEntry(1, portlet, 0);

		Mockito.verify(
			portletDataHandler
		).isBatch();

		Mockito.when(
			portletDataHandler.isBatch()
		).thenReturn(
			true
		);

		_testAddFriendlyURLEntry(1, portlet, 1);
	}

	private FriendlyURLEntryLocalServiceImpl
			_getFriendlyURLEntryLocalServiceImpl(Portlet portlet)
		throws Exception {

		FriendlyURLEntryLocalServiceImpl friendlyURLEntryLocalServiceImpl =
			new FriendlyURLEntryLocalServiceImpl();

		Mockito.reset(
			_assetEntryLocalService, _classNameLocalService,
			_counterLocalService, _exportImportHelper, _friendlyURLEntry,
			_friendlyURLEntryLocalizationPersistence, _friendlyURLEntryMapping,
			_friendlyURLEntryMappingPersistence, _friendlyURLEntryPersistence,
			_friendlyURLNormalizer, _groupLocalService, _language, _portal);

		ReflectionTestUtil.setFieldValue(
			friendlyURLEntryLocalServiceImpl, "_assetEntryLocalService",
			_assetEntryLocalService);
		ReflectionTestUtil.setFieldValue(
			friendlyURLEntryLocalServiceImpl, "_classNameLocalService",
			_classNameLocalService);
		ReflectionTestUtil.setFieldValue(
			friendlyURLEntryLocalServiceImpl, "counterLocalService",
			_counterLocalService);

		Mockito.when(
			_exportImportHelper.getDataSiteLevelPortlet(
				Mockito.anyString(), Mockito.anyLong(), Mockito.anyBoolean())
		).thenReturn(
			portlet
		);

		ReflectionTestUtil.setFieldValue(
			friendlyURLEntryLocalServiceImpl, "_exportImportHelper",
			_exportImportHelper);

		Mockito.when(
			_friendlyURLEntryLocalizationPersistence.create(Mockito.anyLong())
		).thenReturn(
			_friendlyURLEntryLocalization
		);

		ReflectionTestUtil.setFieldValue(
			friendlyURLEntryLocalServiceImpl,
			"friendlyURLEntryLocalizationPersistence",
			_friendlyURLEntryLocalizationPersistence);

		Mockito.when(
			_friendlyURLEntryMappingPersistence.create(Mockito.anyLong())
		).thenReturn(
			_friendlyURLEntryMapping
		);

		ReflectionTestUtil.setFieldValue(
			friendlyURLEntryLocalServiceImpl,
			"_friendlyURLEntryMappingPersistence",
			_friendlyURLEntryMappingPersistence);

		Mockito.when(
			_friendlyURLEntryPersistence.create(Mockito.anyLong())
		).thenReturn(
			_friendlyURLEntry
		);

		Mockito.when(
			_friendlyURLEntryPersistence.findByPrimaryKey(Mockito.anyLong())
		).thenReturn(
			_friendlyURLEntry
		);

		Mockito.when(
			_friendlyURLEntryPersistence.update(_friendlyURLEntry)
		).thenReturn(
			_friendlyURLEntry
		);

		ReflectionTestUtil.setFieldValue(
			friendlyURLEntryLocalServiceImpl, "friendlyURLEntryPersistence",
			_friendlyURLEntryPersistence);

		Mockito.when(
			_friendlyURLNormalizer.normalizeWithEncoding(Mockito.anyString())
		).thenAnswer(
			invocationOnMock -> invocationOnMock.getArgument(0, String.class)
		);

		ReflectionTestUtil.setFieldValue(
			friendlyURLEntryLocalServiceImpl, "_friendlyURLNormalizer",
			_friendlyURLNormalizer);

		Mockito.when(
			_groupLocalService.getGroup(Mockito.anyLong())
		).thenReturn(
			Mockito.mock(Group.class)
		);

		ReflectionTestUtil.setFieldValue(
			friendlyURLEntryLocalServiceImpl, "_groupLocalService",
			_groupLocalService);

		Mockito.when(
			_language.getAvailableLocales(Mockito.anyLong())
		).thenReturn(
			SetUtil.fromArray(LocaleUtil.US)
		);

		ReflectionTestUtil.setFieldValue(
			friendlyURLEntryLocalServiceImpl, "_language", _language);

		Mockito.when(
			_portal.fetchClassName(Mockito.anyLong())
		).thenReturn(
			RandomTestUtil.randomString()
		);

		ReflectionTestUtil.setFieldValue(
			friendlyURLEntryLocalServiceImpl, "_portal", _portal);

		return friendlyURLEntryLocalServiceImpl;
	}

	private void _mockThreadLocals(
		boolean batchEngineInProcess, boolean importInProcess) {

		Mockito.when(
			BatchEngineThreadLocal.isBatchImportInProcess()
		).thenReturn(
			batchEngineInProcess
		);

		Mockito.when(
			ExportImportThreadLocal.isImportInProcess()
		).thenReturn(
			importInProcess
		);
	}

	private void _testAddFriendlyURLEntry(
			int exportImportHelperWantedNumberOfInvocations, Portlet portlet,
			int updateFriendlyURLEntryMappingWantedNumberOfInvocations)
		throws Exception {

		FriendlyURLEntryLocalServiceImpl friendlyURLEntryLocalServiceImpl =
			_getFriendlyURLEntryLocalServiceImpl(portlet);

		String urlTitle = StringUtil.toLowerCase(
			StringUtil.removeSubstring(
				RandomTestUtil.randomString(
					LayoutFriendlyURLRandomizerBumper.INSTANCE),
				StringPool.SLASH));

		Assert.assertEquals(
			_friendlyURLEntry,
			friendlyURLEntryLocalServiceImpl.addFriendlyURLEntry(
				RandomTestUtil.randomLong(), RandomTestUtil.randomLong(),
				RandomTestUtil.randomLong(), RandomTestUtil.randomString(),
				HashMapBuilder.put(
					LocaleUtil.toLanguageId(LocaleUtil.US), urlTitle
				).build(),
				Mockito.mock(ServiceContext.class)));

		Mockito.verify(
			_exportImportHelper,
			VerificationModeFactory.times(
				exportImportHelperWantedNumberOfInvocations)
		).getDataSiteLevelPortlet(
			Mockito.anyString(), Mockito.anyLong(), Mockito.anyBoolean()
		);

		Mockito.verify(
			_friendlyURLEntryLocalizationPersistence
		).create(
			Mockito.anyLong()
		);

		Mockito.verify(
			_friendlyURLEntryLocalization
		).setUrlTitle(
			urlTitle
		);

		Mockito.verify(
			_friendlyURLEntryLocalizationPersistence
		).update(
			_friendlyURLEntryLocalization
		);

		Mockito.verify(
			_friendlyURLEntryMappingPersistence
		).create(
			Mockito.anyLong()
		);

		Mockito.verify(
			_friendlyURLEntryMappingPersistence,
			VerificationModeFactory.times(
				updateFriendlyURLEntryMappingWantedNumberOfInvocations)
		).update(
			_friendlyURLEntryMapping
		);

		Mockito.verify(
			_friendlyURLEntryPersistence
		).create(
			Mockito.anyLong()
		);

		Mockito.verify(
			_friendlyURLEntryPersistence
		).update(
			_friendlyURLEntry
		);
	}

	private final AssetEntryLocalService _assetEntryLocalService = Mockito.mock(
		AssetEntryLocalService.class);
	private final MockedStatic<BatchEngineThreadLocal>
		_batchEngineThreadLocalMockedStatic = Mockito.mockStatic(
			BatchEngineThreadLocal.class);
	private final ClassNameLocalService _classNameLocalService = Mockito.mock(
		ClassNameLocalService.class);
	private final CounterLocalService _counterLocalService = Mockito.mock(
		CounterLocalService.class);
	private final ExportImportHelper _exportImportHelper = Mockito.mock(
		ExportImportHelper.class);
	private final MockedStatic<ExportImportThreadLocal>
		_exportImportThreadLocalMockedStatic = Mockito.mockStatic(
			ExportImportThreadLocal.class);
	private final FriendlyURLEntry _friendlyURLEntry = Mockito.mock(
		FriendlyURLEntry.class);
	private final FriendlyURLEntryLocalization _friendlyURLEntryLocalization =
		Mockito.mock(FriendlyURLEntryLocalization.class);
	private final FriendlyURLEntryLocalizationPersistence
		_friendlyURLEntryLocalizationPersistence = Mockito.mock(
			FriendlyURLEntryLocalizationPersistence.class);
	private final FriendlyURLEntryMapping _friendlyURLEntryMapping =
		Mockito.mock(FriendlyURLEntryMapping.class);
	private final FriendlyURLEntryMappingPersistence
		_friendlyURLEntryMappingPersistence = Mockito.mock(
			FriendlyURLEntryMappingPersistence.class);
	private final FriendlyURLEntryPersistence _friendlyURLEntryPersistence =
		Mockito.mock(FriendlyURLEntryPersistence.class);
	private final FriendlyURLNormalizer _friendlyURLNormalizer = Mockito.mock(
		FriendlyURLNormalizer.class);
	private final GroupLocalService _groupLocalService = Mockito.mock(
		GroupLocalService.class);
	private final Language _language = Mockito.mock(Language.class);
	private final MockedStatic<ModelHintsUtil> _modelHintsUtilMockedStatic =
		Mockito.mockStatic(ModelHintsUtil.class);
	private final Portal _portal = Mockito.mock(Portal.class);
	private final MockedStatic<ResourceActionsUtil>
		_resourceActionsUtilMockedStatic = Mockito.mockStatic(
			ResourceActionsUtil.class);

}