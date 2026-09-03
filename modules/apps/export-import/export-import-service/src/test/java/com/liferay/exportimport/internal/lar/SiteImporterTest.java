/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.internal.lar;

import com.liferay.exportimport.internal.util.SiteExportImportParameterUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataContextFactory;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.lar.SiteImporter;
import com.liferay.exportimport.report.constants.ExportImportReportEntryConstants;
import com.liferay.exportimport.report.service.ExportImportReportEntryLocalService;
import com.liferay.exportimport.site.ExportImportSiteProvider;
import com.liferay.exportimport.site.LARSite;
import com.liferay.exportimport.site.LARSiteReader;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.kernel.exception.DuplicateGroupException;
import com.liferay.portal.kernel.exception.GroupKeyException;
import com.liferay.portal.kernel.exception.GroupParentException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.GroupService;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.xml.SAXReaderImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Petteri Karttunen
 */
public class SiteImporterTest {

	@ClassRule
	@Rule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		SAXReaderUtil saxReaderUtil = new SAXReaderUtil();

		saxReaderUtil.setSAXReader(new SAXReaderImpl());
	}

	@Before
	public void setUp() throws Exception {
		_exportImportReportEntryLocalService = Mockito.mock(
			ExportImportReportEntryLocalService.class);
		_exportImportSiteProvider = Mockito.mock(
			ExportImportSiteProvider.class);
		_groupLocalService = Mockito.mock(GroupLocalService.class);
		_groupService = Mockito.mock(GroupService.class);
		_larSiteReader = Mockito.mock(LARSiteReader.class);
		_portletDataContextFactory = Mockito.mock(
			PortletDataContextFactory.class);

		_siteImporter = new SiteImporterImpl();

		ReflectionTestUtil.setFieldValue(
			_siteImporter, "_classNameLocalService",
			Mockito.mock(ClassNameLocalService.class));
		ReflectionTestUtil.setFieldValue(
			_siteImporter, "_exportImportReportEntryLocalService",
			_exportImportReportEntryLocalService);
		ReflectionTestUtil.setFieldValue(
			_siteImporter, "_exportImportSiteProvider",
			_exportImportSiteProvider);
		ReflectionTestUtil.setFieldValue(
			_siteImporter, "_groupLocalService", _groupLocalService);
		ReflectionTestUtil.setFieldValue(
			_siteImporter, "_groupService", _groupService);
		ReflectionTestUtil.setFieldValue(
			_siteImporter, "_larSiteReader", _larSiteReader);
		ReflectionTestUtil.setFieldValue(
			_siteImporter, "_portletDataContextFactory",
			_portletDataContextFactory);

		_portletDataContext = Mockito.mock(PortletDataContext.class);

		Mockito.when(
			_portletDataContext.getCompanyId()
		).thenReturn(
			_COMPANY_ID
		);

		Mockito.when(
			_exportImportSiteProvider.isSupported(Mockito.any())
		).thenReturn(
			true
		);

		Mockito.when(
			_portletDataContextFactory.createImportPortletDataContext(
				Mockito.anyLong(), Mockito.anyLong(), Mockito.any(),
				Mockito.any(), Mockito.any())
		).thenAnswer(
			invocation -> _mockGroupPortletDataContext(
				invocation.getArgument(2))
		);
	}

	@Test
	public void testImportSitesWhenManifestHasHeader() throws Exception {
		_setUpLARSites(_createLARSite("site", null));
		_setUpGroups("site");

		_importSites("site");

		PortletDataContext sitePortletDataContext =
			_sitePortletDataContexts.get(0);

		Mockito.verify(
			sitePortletDataContext
		).setSourceGroupId(
			_SOURCE_GROUP_ID
		);

		Mockito.verify(
			sitePortletDataContext
		).setPrivateLayout(
			false
		);

		Mockito.verify(
			sitePortletDataContext
		).setSourceCompanyGroupId(
			_SOURCE_COMPANY_GROUP_ID
		);

		Mockito.verify(
			sitePortletDataContext
		).setSourceCompanyId(
			_SOURCE_COMPANY_ID
		);

		Mockito.verify(
			sitePortletDataContext
		).setSourceUserPersonalSiteGroupId(
			_SOURCE_USER_PERSONAL_SITE_GROUP_ID
		);
	}

	@Test
	public void testImportSitesWhenNoSitesAreSelected() throws Exception {
		_setUpLARSites(_createLARSite("site", null));

		Assert.assertTrue(_importSites().isEmpty());

		Mockito.verifyNoInteractions(_exportImportReportEntryLocalService);
	}

	@Test
	public void testImportSitesWhenParentSiteIsBelowTheSite() throws Exception {
		_setUpLARSites(_createLARSite("child", "parent"));
		_setUpGroups("child", "parent");

		Mockito.when(
			_groupLocalService.updateGroup(
				Mockito.anyLong(), Mockito.anyLong(), Mockito.any(),
				Mockito.any(), Mockito.anyInt(), Mockito.any(),
				Mockito.anyBoolean(), Mockito.anyInt(), Mockito.any(),
				Mockito.anyBoolean(), Mockito.anyBoolean(), Mockito.any())
		).thenThrow(
			new GroupParentException.MustNotHaveChildParent(
				_GROUP_ID, _PARENT_GROUP_ID)
		);

		Assert.assertEquals(Arrays.asList("child"), _importSites("child"));

		_verifyReportEntry(
			"child", ExportImportReportEntryConstants.TYPE_WARNING,
			"is below it in the target system");
	}

	@Test
	public void testImportSitesWhenPassIsSiteScoped() throws Exception {
		Mockito.when(
			_portletDataContext.getParameterMap()
		).thenReturn(
			SiteExportImportParameterUtil.toSiteExportParameterMap(
				_getParameterMap("site"), "site")
		);

		Assert.assertTrue(
			_importSites(
				true
			).isEmpty());

		Mockito.verifyNoInteractions(_larSiteReader);
	}

	@Test
	public void testImportSitesWhenSelectedSiteIsMissingFromTheLARFile()
		throws Exception {

		_setUpLARSites(_createLARSite("carried", null));
		_setUpGroups("carried");

		Assert.assertEquals(
			Arrays.asList("carried"), _importSites("carried", "missing"));

		_verifyReportEntry(
			"missing", ExportImportReportEntryConstants.TYPE_ERROR,
			"is missing in the LAR file");
	}

	@Test
	public void testImportSitesWhenSiteExportImportIsDisabled()
		throws Exception {

		_setUpLARSites(_createLARSite("site", null));

		Mockito.when(
			_portletDataContext.getParameterMap()
		).thenReturn(
			_getParameterMap("site")
		);

		Assert.assertTrue(
			_importSites(
				false
			).isEmpty());

		Mockito.verifyNoInteractions(_larSiteReader);
	}

	@Test
	public void testImportSitesWhenSiteExportImportPermissionIsMissing()
		throws Exception {

		_setUpLARSites(_createLARSite("site", null));
		_setUpGroups("site");

		Mockito.when(
			_portletDataContext.getParameterMap()
		).thenReturn(
			_getParameterMap("site")
		);

		try (MockedStatic<FeatureFlagManagerUtil> mockedStatic =
				Mockito.mockStatic(FeatureFlagManagerUtil.class);
			MockedStatic<GroupPermissionUtil> groupPermissionUtilMockedStatic =
				Mockito.mockStatic(GroupPermissionUtil.class)) {

			mockedStatic.when(
				() -> FeatureFlagManagerUtil.isEnabled(
					Mockito.anyLong(), Mockito.eq("LPD-85946"))
			).thenReturn(
				true
			);

			groupPermissionUtilMockedStatic.when(
				() -> GroupPermissionUtil.check(
					Mockito.any(), Mockito.anyLong(),
					Mockito.eq(ActionKeys.EXPORT_IMPORT_LAYOUTS))
			).thenThrow(
				new PrincipalException.MustHavePermission(
					_USER_ID, Group.class.getName(), _GROUP_ID,
					ActionKeys.EXPORT_IMPORT_LAYOUTS)
			);

			try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
					SiteImporterImpl.class.getName(), LoggerTestUtil.ERROR)) {

				_siteImporter.importSites(
					_portletDataContext,
					(sitePortletDataContext, userId) -> Assert.fail(
						"The site was imported without the \"" +
							ActionKeys.EXPORT_IMPORT_LAYOUTS + "\" permission"),
					_USER_ID);

				List<LogEntry> logEntries = logCapture.getLogEntries();

				Assert.assertEquals(
					logEntries.toString(), 1, logEntries.size());

				LogEntry logEntry = logEntries.get(0);

				Assert.assertSame(
					PrincipalException.MustHavePermission.class,
					logEntry.getThrowable(
					).getClass());
			}
		}

		Mockito.verifyNoInteractions(_exportImportReportEntryLocalService);
	}

	@Test
	public void testImportSitesWhenSiteIsNotMovable() throws Exception {
		_setUpLARSites(
			_createLARSite("child", "parent"), _createLARSite("other", null));
		_setUpGroups("child", "parent", "other");

		Mockito.when(
			_groupLocalService.updateGroup(
				Mockito.anyLong(), Mockito.anyLong(), Mockito.any(),
				Mockito.any(), Mockito.anyInt(), Mockito.any(),
				Mockito.anyBoolean(), Mockito.anyInt(), Mockito.any(),
				Mockito.anyBoolean(), Mockito.anyBoolean(), Mockito.any())
		).thenThrow(
			new GroupKeyException()
		);

		_runWithExpectedWarn(
			GroupKeyException.class,
			() -> Assert.assertEquals(
				Arrays.asList("child", "other"),
				_importSites("child", "other")));

		_verifyReportEntry(
			"child", ExportImportReportEntryConstants.TYPE_WARNING,
			"Unable to move the site");
	}

	@Test
	public void testImportSitesWhenSiteIsNotSelected() throws Exception {
		_setUpLARSites(
			_createLARSite("selected", null),
			_createLARSite("unselected", null));
		_setUpGroups("selected", "unselected");

		Assert.assertEquals(
			Arrays.asList("selected"), _importSites("selected"));

		Mockito.verifyNoInteractions(_exportImportReportEntryLocalService);
	}

	@Test
	public void testImportSitesWhenSiteIsNotSupported() throws Exception {
		_setUpLARSites(_createLARSite("site", null));

		Group group = _setUpGroups("site");

		Mockito.when(
			_exportImportSiteProvider.isSupported(group)
		).thenReturn(
			false
		);

		Mockito.when(
			group.getExternalReferenceCode()
		).thenReturn(
			"site"
		);

		Assert.assertTrue(
			_importSites(
				"site"
			).isEmpty());

		_verifyReportEntry(
			"site", ExportImportReportEntryConstants.TYPE_ERROR,
			"is not supported");
	}

	@Test
	public void testImportSitesWhenTargetSiteIsMissing() throws Exception {
		_setUpLARSites(_createLARSite("site", null));

		Assert.assertTrue(
			_importSites(
				"site"
			).isEmpty());

		_verifyReportEntry(
			"site", ExportImportReportEntryConstants.TYPE_ERROR,
			"does not exist in the target system");
	}

	@Test
	public void testUpdateParentSite() throws Exception {
		Group parentGroup = _mockGroup(0);

		Mockito.when(
			parentGroup.getGroupId()
		).thenReturn(
			_PARENT_GROUP_ID
		);

		Mockito.when(
			_groupLocalService.fetchGroupByExternalReferenceCode(
				"parent", _COMPANY_ID)
		).thenReturn(
			parentGroup
		);

		Group group = _mockGroup(0);

		_updateParentSite(_createLARSite("child", "parent"), group);

		Mockito.verify(
			_groupLocalService
		).updateGroup(
			group.getGroupId(), _PARENT_GROUP_ID, group.getNameMap(),
			group.getDescriptionMap(), group.getType(), group.getTypeSettings(),
			group.isManualMembership(), group.getMembershipRestriction(),
			group.getFriendlyURL(), group.isInheritContent(), group.isActive(),
			null
		);

		Mockito.verifyNoInteractions(_exportImportReportEntryLocalService);
	}

	@Test
	public void testUpdateParentSiteWhenGroupKeyIsDuplicated()
		throws Exception {

		_setUpParentSite();

		Mockito.when(
			_groupLocalService.updateGroup(
				Mockito.anyLong(), Mockito.anyLong(), Mockito.any(),
				Mockito.any(), Mockito.anyInt(), Mockito.any(),
				Mockito.anyBoolean(), Mockito.anyInt(), Mockito.any(),
				Mockito.anyBoolean(), Mockito.anyBoolean(), Mockito.any())
		).thenThrow(
			new DuplicateGroupException()
		);

		_runWithExpectedWarn(
			DuplicateGroupException.class,
			() -> _updateParentSite(
				_createLARSite("child", "parent"), _mockGroup(0)));

		_verifyReportEntry(
			"child", ExportImportReportEntryConstants.TYPE_WARNING,
			"Unable to move the site");
	}

	@Test
	public void testUpdateParentSiteWhenGroupKeyIsInvalid() throws Exception {
		_setUpParentSite();

		Mockito.when(
			_groupLocalService.updateGroup(
				Mockito.anyLong(), Mockito.anyLong(), Mockito.any(),
				Mockito.any(), Mockito.anyInt(), Mockito.any(),
				Mockito.anyBoolean(), Mockito.anyInt(), Mockito.any(),
				Mockito.anyBoolean(), Mockito.anyBoolean(), Mockito.any())
		).thenThrow(
			new GroupKeyException()
		);

		_runWithExpectedWarn(
			GroupKeyException.class,
			() -> _updateParentSite(
				_createLARSite("child", "parent"), _mockGroup(0)));

		_verifyReportEntry(
			"child", ExportImportReportEntryConstants.TYPE_WARNING,
			"Unable to move the site");
	}

	@Test
	public void testUpdateParentSiteWhenParentSiteExternalReferenceCodeIsNull()
		throws Exception {

		_updateParentSite(_createLARSite("child", null), _mockGroup(0));

		Mockito.verifyNoInteractions(_groupLocalService);
		Mockito.verifyNoInteractions(_exportImportReportEntryLocalService);
	}

	@Test
	public void testUpdateParentSiteWhenParentSiteIsAlreadyTheParent()
		throws Exception {

		Group parentGroup = _mockGroup(0);

		Mockito.when(
			parentGroup.getGroupId()
		).thenReturn(
			_PARENT_GROUP_ID
		);

		Mockito.when(
			_groupLocalService.fetchGroupByExternalReferenceCode(
				"parent", _COMPANY_ID)
		).thenReturn(
			parentGroup
		);

		_updateParentSite(
			_createLARSite("child", "parent"), _mockGroup(_PARENT_GROUP_ID));

		_verifyNoSiteWasMoved();

		Mockito.verifyNoInteractions(_exportImportReportEntryLocalService);
	}

	@Test
	public void testUpdateParentSiteWhenParentSiteIsBelowTheSite()
		throws Exception {

		Group parentGroup = _mockGroup(0);

		Mockito.when(
			parentGroup.getGroupId()
		).thenReturn(
			_PARENT_GROUP_ID
		);

		Mockito.when(
			_groupLocalService.fetchGroupByExternalReferenceCode(
				"parent", _COMPANY_ID)
		).thenReturn(
			parentGroup
		);

		Mockito.when(
			_groupLocalService.updateGroup(
				Mockito.anyLong(), Mockito.anyLong(), Mockito.any(),
				Mockito.any(), Mockito.anyInt(), Mockito.any(),
				Mockito.anyBoolean(), Mockito.anyInt(), Mockito.any(),
				Mockito.anyBoolean(), Mockito.anyBoolean(), Mockito.any())
		).thenThrow(
			new GroupParentException.MustNotHaveChildParent(
				_GROUP_ID, _PARENT_GROUP_ID)
		);

		_updateParentSite(_createLARSite("child", "parent"), _mockGroup(0));

		_verifyReportEntry(
			"child", ExportImportReportEntryConstants.TYPE_WARNING,
			"is below it in the target system");
	}

	@Test
	public void testUpdateParentSiteWhenParentSiteIsMissing() throws Exception {
		LARSite larSite = _createLARSite("child", "parent");

		_updateParentSite(larSite, _mockGroup(0));

		_verifyReportEntry(
			"child", ExportImportReportEntryConstants.TYPE_WARNING,
			"does not exist in the target system or in the LAR file");

		_verifyNoSiteWasMoved();
	}

	private LARSite _createLARSite(
		String externalReferenceCode, String parentExternalReferenceCode) {

		return new LARSite(
			0, "Site", externalReferenceCode, _SOURCE_GROUP_ID,
			parentExternalReferenceCode, "Global");
	}

	private Map<String, String[]> _getParameterMap(
		String... selectedSiteExternalReferenceCodes) {

		return HashMapBuilder.put(
			PortletDataHandlerKeys.SITE_EXTERNAL_REFERENCE_CODES,
			selectedSiteExternalReferenceCodes
		).build();
	}

	private List<String> _importSites(boolean siteExportImportEnabled)
		throws Exception {

		List<String> importedSiteExternalReferenceCodes = new ArrayList<>();

		try (MockedStatic<FeatureFlagManagerUtil> mockedStatic =
				Mockito.mockStatic(FeatureFlagManagerUtil.class);
			MockedStatic<GroupPermissionUtil> groupPermissionUtilMockedStatic =
				Mockito.mockStatic(GroupPermissionUtil.class)) {

			mockedStatic.when(
				() -> FeatureFlagManagerUtil.isEnabled(
					Mockito.anyLong(), Mockito.eq("LPD-85946"))
			).thenReturn(
				siteExportImportEnabled
			);

			_siteImporter.importSites(
				_portletDataContext,
				(sitePortletDataContext, userId) ->
					importedSiteExternalReferenceCodes.add(
						SiteExportImportParameterUtil.
							getCurrentSiteExternalReferenceCode(
								sitePortletDataContext)),
				_USER_ID);
		}

		return importedSiteExternalReferenceCodes;
	}

	private List<String> _importSites(
			String... selectedSiteExternalReferenceCodes)
		throws Exception {

		Mockito.when(
			_portletDataContext.getParameterMap()
		).thenReturn(
			_getParameterMap(selectedSiteExternalReferenceCodes)
		);

		return _importSites(true);
	}

	private Group _mockGroup(long parentGroupId) {
		Group group = Mockito.mock(Group.class);

		Mockito.when(
			group.getGroupId()
		).thenReturn(
			_GROUP_ID
		);

		Mockito.when(
			group.getParentGroupId()
		).thenReturn(
			parentGroupId
		);

		return group;
	}

	private PortletDataContext _mockGroupPortletDataContext(
		Map<String, String[]> parameterMap) {

		PortletDataContext portletDataContext = Mockito.mock(
			PortletDataContext.class);

		Mockito.when(
			portletDataContext.getParameterMap()
		).thenReturn(
			parameterMap
		);

		Mockito.when(
			portletDataContext.getZipEntryAsString(Mockito.anyString())
		).thenReturn(
			String.format(
				"<root><header company-group-id=\"%d\" company-id=\"%d\" " +
					"user-personal-site-group-id=\"%d\" /></root>",
				_SOURCE_COMPANY_GROUP_ID, _SOURCE_COMPANY_ID,
				_SOURCE_USER_PERSONAL_SITE_GROUP_ID)
		);

		_sitePortletDataContexts.add(portletDataContext);

		return portletDataContext;
	}

	private void _runWithExpectedWarn(
			Class<? extends Throwable> throwableClass,
			UnsafeRunnable<Exception> unsafeRunnable)
		throws Exception {

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				SiteImporterImpl.class.getName(), LoggerTestUtil.WARN)) {

			unsafeRunnable.run();

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Assert.assertSame(
				throwableClass,
				logEntry.getThrowable(
				).getClass());
		}
	}

	private Group _setUpGroups(String... externalReferenceCodes)
		throws Exception {

		Group group = null;

		for (String externalReferenceCode : externalReferenceCodes) {
			group = _mockGroup(0);

			Mockito.when(
				_groupLocalService.fetchGroupByExternalReferenceCode(
					externalReferenceCode, _COMPANY_ID)
			).thenReturn(
				group
			);

			Mockito.when(
				_groupService.fetchGroupByExternalReferenceCode(
					externalReferenceCode, _COMPANY_ID)
			).thenReturn(
				group
			);
		}

		return group;
	}

	private void _setUpLARSites(LARSite... larSites) throws Exception {
		Mockito.when(
			_larSiteReader.getLARSites(_portletDataContext)
		).thenReturn(
			ListUtil.fromArray(larSites)
		);
	}

	private void _setUpParentSite() throws Exception {
		Group parentGroup = _mockGroup(0);

		Mockito.when(
			parentGroup.getGroupId()
		).thenReturn(
			_PARENT_GROUP_ID
		);

		Mockito.when(
			_groupLocalService.fetchGroupByExternalReferenceCode(
				"parent", _COMPANY_ID)
		).thenReturn(
			parentGroup
		);
	}

	private void _updateParentSite(LARSite larSite, Group group) {
		ReflectionTestUtil.invoke(
			_siteImporter, "_updateParentSite",
			new Class<?>[] {
				PortletDataContext.class, Group.class, LARSite.class
			},
			_portletDataContext, group, larSite);
	}

	private void _verifyNoSiteWasMoved() throws Exception {
		Mockito.verify(
			_groupLocalService, Mockito.never()
		).updateGroup(
			Mockito.anyLong(), Mockito.anyLong(), Mockito.any(), Mockito.any(),
			Mockito.anyInt(), Mockito.any(), Mockito.anyBoolean(),
			Mockito.anyInt(), Mockito.any(), Mockito.anyBoolean(),
			Mockito.anyBoolean(), Mockito.any()
		);
	}

	private void _verifyReportEntry(
		String externalReferenceCode, int type, String message) {

		Mockito.verify(
			_exportImportReportEntryLocalService
		).getOrAddExportImportReportEntry(
			Mockito.anyLong(), Mockito.anyLong(),
			Mockito.eq(externalReferenceCode), Mockito.anyLong(),
			Mockito.anyLong(), Mockito.anyLong(), Mockito.eq(type),
			Mockito.contains(message), Mockito.isNull(), Mockito.eq("sites")
		);
	}

	private static final long _COMPANY_ID = RandomTestUtil.randomLong();

	private static final long _GROUP_ID = RandomTestUtil.randomLong();

	private static final long _PARENT_GROUP_ID = RandomTestUtil.randomLong();

	private static final long _SOURCE_COMPANY_GROUP_ID =
		RandomTestUtil.randomLong();

	private static final long _SOURCE_COMPANY_ID = RandomTestUtil.randomLong();

	private static final long _SOURCE_GROUP_ID = RandomTestUtil.randomLong();

	private static final long _SOURCE_USER_PERSONAL_SITE_GROUP_ID =
		RandomTestUtil.randomLong();

	private static final long _USER_ID = RandomTestUtil.randomLong();

	private ExportImportReportEntryLocalService
		_exportImportReportEntryLocalService;
	private ExportImportSiteProvider _exportImportSiteProvider;
	private GroupLocalService _groupLocalService;
	private GroupService _groupService;
	private LARSiteReader _larSiteReader;
	private PortletDataContext _portletDataContext;
	private PortletDataContextFactory _portletDataContextFactory;
	private SiteImporter _siteImporter;
	private final List<PortletDataContext> _sitePortletDataContexts =
		new ArrayList<>();

}