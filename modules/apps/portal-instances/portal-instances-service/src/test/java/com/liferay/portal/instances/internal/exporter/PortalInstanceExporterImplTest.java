/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.instances.internal.exporter;

import com.liferay.petra.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.db.partition.util.DBPartitionUtil;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.CompanyService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.PropsValuesTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.sql.SQLException;

import java.util.Collections;
import java.util.Dictionary;

import org.apache.felix.cm.file.ConfigurationHandler;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Jorge Avalos
 */
public class PortalInstanceExporterImplTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		ReflectionTestUtil.setFieldValue(
			_portalInstanceExporterImpl, "_companyService", _companyService);
		ReflectionTestUtil.setFieldValue(
			_portalInstanceExporterImpl, "_groupLocalService",
			_groupLocalService);

		_dbPartitionUtilMockedStatic = Mockito.mockStatic(
			DBPartitionUtil.class);

		_dbPartitionUtilMockedStatic.when(
			() -> DBPartitionUtil.getConfigurations(CompanyConstants.SYSTEM)
		).thenReturn(
			Collections.emptyMap()
		);
	}

	@After
	public void tearDown() {
		_dbPartitionUtilMockedStatic.close();
	}

	@Test
	public void testConfigurationExportedForCompanyScope() throws Exception {
		String encodedDictionary = _getEncodedDictionary(
			ExtendedObjectClassDefinition.Scope.COMPANY, _COMPANY_ID);

		_setUpConfigurations(_CONFIGURATION_ID, encodedDictionary);

		_portalInstanceExporterImpl.exportPortalInstance(_COMPANY_ID);

		_dbPartitionUtilMockedStatic.verify(
			() -> DBPartitionUtil.exportConfiguration(
				_COMPANY_ID, _CONFIGURATION_ID, encodedDictionary));
	}

	@Test
	public void testConfigurationExportedForGroupScope() throws Exception {
		_setUpGroup(_COMPANY_ID, _GROUP_ID);

		String encodedDictionary = _getEncodedDictionary(
			ExtendedObjectClassDefinition.Scope.GROUP, _GROUP_ID);

		_setUpConfigurations(_CONFIGURATION_ID, encodedDictionary);

		_portalInstanceExporterImpl.exportPortalInstance(_COMPANY_ID);

		_dbPartitionUtilMockedStatic.verify(
			() -> DBPartitionUtil.exportConfiguration(
				_COMPANY_ID, _CONFIGURATION_ID, encodedDictionary));
	}

	@Test
	public void testConfigurationExportedForPortletInstanceScope()
		throws Exception {

		String encodedDictionary = _getEncodedDictionary(
			ExtendedObjectClassDefinition.Scope.PORTLET_INSTANCE,
			RandomTestUtil.randomString());

		_setUpConfigurations(_CONFIGURATION_ID, encodedDictionary);

		_portalInstanceExporterImpl.exportPortalInstance(_COMPANY_ID);

		_dbPartitionUtilMockedStatic.verify(
			() -> DBPartitionUtil.exportConfiguration(
				_COMPANY_ID, _CONFIGURATION_ID, encodedDictionary));
	}

	@Test
	public void testConfigurationNotExportedForEnabledDatabasePartitioning()
		throws Exception {

		try (SafeCloseable safeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"DATABASE_PARTITION_ENABLED", true)) {

			_portalInstanceExporterImpl.exportPortalInstance(_COMPANY_ID);
		}

		_dbPartitionUtilMockedStatic.verify(
			() -> DBPartitionUtil.getConfigurations(Mockito.anyLong()),
			Mockito.never());
	}

	@Test
	public void testConfigurationNotExportedForFailedCompanyExport()
		throws Exception {

		_setUpConfigurations(
			_CONFIGURATION_ID,
			_getEncodedDictionary(
				ExtendedObjectClassDefinition.Scope.COMPANY, _COMPANY_ID));

		Mockito.when(
			_companyService.exportCompany(_COMPANY_ID)
		).thenThrow(
			new PrincipalException.MustBeOmniadmin(RandomTestUtil.randomLong())
		);

		Assert.assertThrows(
			PrincipalException.MustBeOmniadmin.class,
			() -> _portalInstanceExporterImpl.exportPortalInstance(
				_COMPANY_ID));

		_assertNoConfigurationExported();
	}

	@Test
	public void testConfigurationNotExportedForMissingGroup() throws Exception {
		Mockito.when(
			_groupLocalService.fetchGroup(_GROUP_ID)
		).thenReturn(
			null
		);

		_setUpConfigurations(
			_CONFIGURATION_ID,
			_getEncodedDictionary(
				ExtendedObjectClassDefinition.Scope.GROUP, _GROUP_ID));

		_portalInstanceExporterImpl.exportPortalInstance(_COMPANY_ID);

		_assertNoConfigurationExported();
	}

	@Test
	public void testConfigurationNotExportedForNullEncodedDictionary()
		throws Exception {

		_dbPartitionUtilMockedStatic.when(
			() -> DBPartitionUtil.getConfigurations(CompanyConstants.SYSTEM)
		).thenReturn(
			Collections.<String, String>singletonMap(_CONFIGURATION_ID, null)
		);

		_portalInstanceExporterImpl.exportPortalInstance(_COMPANY_ID);

		_assertNoConfigurationExported();
	}

	@Test
	public void testConfigurationNotExportedForOtherCompanyGroupScope()
		throws Exception {

		_setUpGroup(_OTHER_COMPANY_ID, _GROUP_ID);
		_setUpConfigurations(
			_CONFIGURATION_ID,
			_getEncodedDictionary(
				ExtendedObjectClassDefinition.Scope.GROUP, _GROUP_ID));

		_portalInstanceExporterImpl.exportPortalInstance(_COMPANY_ID);

		_assertNoConfigurationExported();
	}

	@Test
	public void testConfigurationNotExportedForOtherCompanyGroupWithCompanyScope()
		throws Exception {

		_setUpGroup(_OTHER_COMPANY_ID, _GROUP_ID);
		_setUpConfigurations(
			_CONFIGURATION_ID,
			_getEncodedDictionary(
				HashMapDictionaryBuilder.<String, Object>put(
					ExtendedObjectClassDefinition.Scope.COMPANY.
						getPropertyKey(),
					_COMPANY_ID
				).put(
					ExtendedObjectClassDefinition.Scope.GROUP.getPropertyKey(),
					_GROUP_ID
				).build()));

		_portalInstanceExporterImpl.exportPortalInstance(_COMPANY_ID);

		_assertNoConfigurationExported();
	}

	@Test
	public void testConfigurationNotExportedForOtherCompanyScope()
		throws Exception {

		_setUpConfigurations(
			_CONFIGURATION_ID,
			_getEncodedDictionary(
				ExtendedObjectClassDefinition.Scope.COMPANY,
				_OTHER_COMPANY_ID));

		_portalInstanceExporterImpl.exportPortalInstance(_COMPANY_ID);

		_assertNoConfigurationExported();
	}

	@Test
	public void testConfigurationNotExportedForSystemScope() throws Exception {
		_setUpConfigurations(
			_CONFIGURATION_ID,
			_getEncodedDictionary(
				RandomTestUtil.randomString(), RandomTestUtil.randomString()));

		_portalInstanceExporterImpl.exportPortalInstance(_COMPANY_ID);

		_assertNoConfigurationExported();
	}

	@Test
	public void testExportPortalInstance() throws Exception {
		String exportedPartitionName =
			_portalInstanceExporterImpl.exportPortalInstance(_COMPANY_ID);

		Assert.assertEquals(
			DBPartitionUtil.DATABASE_EXPORTED_PARTITION_SCHEMA_NAME_PREFIX +
				_COMPANY_ID,
			exportedPartitionName);

		Mockito.verify(
			_companyService
		).exportCompany(
			_COMPANY_ID
		);
	}

	@Test
	public void testExportPortalInstanceForFailedConfigurationExport()
		throws Exception {

		String encodedDictionary = _getEncodedDictionary(
			ExtendedObjectClassDefinition.Scope.COMPANY, _COMPANY_ID);

		_setUpConfigurations(_CONFIGURATION_ID, encodedDictionary);

		_dbPartitionUtilMockedStatic.when(
			() -> DBPartitionUtil.exportConfiguration(
				_COMPANY_ID, _CONFIGURATION_ID, encodedDictionary)
		).thenThrow(
			new SQLException()
		);

		Assert.assertThrows(
			SQLException.class,
			() -> _portalInstanceExporterImpl.exportPortalInstance(
				_COMPANY_ID));
	}

	private void _assertNoConfigurationExported() {
		_dbPartitionUtilMockedStatic.verify(
			() -> DBPartitionUtil.exportConfiguration(
				Mockito.anyLong(), Mockito.anyString(), Mockito.anyString()),
			Mockito.never());
	}

	private String _getEncodedDictionary(Dictionary<String, Object> dictionary)
		throws Exception {

		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		ConfigurationHandler.write(unsyncByteArrayOutputStream, dictionary);

		return new String(
			unsyncByteArrayOutputStream.toByteArray(), StringPool.UTF8);
	}

	private String _getEncodedDictionary(
			ExtendedObjectClassDefinition.Scope scope, Object scopePK)
		throws Exception {

		return _getEncodedDictionary(scope.getPropertyKey(), scopePK);
	}

	private String _getEncodedDictionary(String propertyKey, Object value)
		throws Exception {

		return _getEncodedDictionary(
			HashMapDictionaryBuilder.<String, Object>put(
				propertyKey, value
			).build());
	}

	private void _setUpConfigurations(
		String configurationId, String encodedDictionary) {

		_dbPartitionUtilMockedStatic.when(
			() -> DBPartitionUtil.getConfigurations(CompanyConstants.SYSTEM)
		).thenReturn(
			HashMapBuilder.put(
				configurationId, encodedDictionary
			).build()
		);
	}

	private void _setUpGroup(long companyId, long groupId) {
		Group group = Mockito.mock(Group.class);

		Mockito.when(
			group.getCompanyId()
		).thenReturn(
			companyId
		);

		Mockito.when(
			_groupLocalService.fetchGroup(groupId)
		).thenReturn(
			group
		);
	}

	private static final long _COMPANY_ID = RandomTestUtil.randomLong();

	private static final String _CONFIGURATION_ID =
		RandomTestUtil.randomString();

	private static final long _GROUP_ID = RandomTestUtil.randomLong();

	private static final long _OTHER_COMPANY_ID = RandomTestUtil.randomLong();

	private final CompanyService _companyService = Mockito.mock(
		CompanyService.class);
	private MockedStatic<DBPartitionUtil> _dbPartitionUtilMockedStatic;
	private final GroupLocalService _groupLocalService = Mockito.mock(
		GroupLocalService.class);
	private final PortalInstanceExporterImpl _portalInstanceExporterImpl =
		new PortalInstanceExporterImpl();

}