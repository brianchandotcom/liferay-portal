/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.admin.site.client.dto.v1_0.FriendlyUrlHistory;
import com.liferay.headless.admin.site.client.dto.v1_0.SitePage;
import com.liferay.headless.admin.site.client.dto.v1_0.WidgetPageSettings;
import com.liferay.headless.admin.site.client.pagination.Page;
import com.liferay.headless.admin.site.client.pagination.Pagination;
import com.liferay.headless.admin.site.client.resource.v1_0.SitePageResource;
import com.liferay.headless.batch.engine.client.dto.v1_0.ExportTask;
import com.liferay.headless.batch.engine.client.dto.v1_0.ImportTask;
import com.liferay.headless.batch.engine.client.http.HttpInvoker;
import com.liferay.headless.batch.engine.client.resource.v1_0.ExportTaskResource;
import com.liferay.headless.batch.engine.client.resource.v1_0.ImportTaskResource;
import com.liferay.petra.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.util.PropsValues;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.zip.ZipInputStream;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rubén Pulido
 */
@FeatureFlags("LPD-35443")
@RunWith(Arquillian.class)
public class SitePageResourceTest extends BaseSitePageResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		User adminUser = UserTestUtil.getAdminUser(
			TestPropsValues.getCompanyId());

		_contentExportTaskResource = ExportTaskResource.builder(
		).authentication(
			adminUser.getEmailAddress(), PropsValues.DEFAULT_ADMIN_PASSWORD
		).header(
			HttpHeaders.ACCEPT, ContentTypes.APPLICATION_OCTET_STREAM
		).build();

		_exportTaskResource = ExportTaskResource.builder(
		).authentication(
			adminUser.getEmailAddress(), PropsValues.DEFAULT_ADMIN_PASSWORD
		).header(
			HttpHeaders.ACCEPT, ContentTypes.APPLICATION_JSON
		).parameter(
			"siteId", String.valueOf(testGroup.getGroupId())
		).build();

		_importGroup = GroupTestUtil.addGroup();

		_importTaskResource = ImportTaskResource.builder(
		).authentication(
			adminUser.getEmailAddress(), PropsValues.DEFAULT_ADMIN_PASSWORD
		).header(
			HttpHeaders.ACCEPT, ContentTypes.APPLICATION_JSON
		).header(
			HttpHeaders.CONTENT_TYPE, ContentTypes.APPLICATION_JSON
		).parameter(
			"siteExternalReferenceCode", _importGroup.getExternalReferenceCode()
		).build();

		_logCaptures.add(
			LoggerTestUtil.configureLog4JLogger(
				"com.liferay.batch.engine.internal." +
					"BatchEngineExportTaskExecutorImpl",
				LoggerTestUtil.ERROR));
		_logCaptures.add(
			LoggerTestUtil.configureLog4JLogger(
				"com.liferay.batch.engine.internal." +
					"BatchEngineImportTaskExecutorImpl",
				LoggerTestUtil.ERROR));
	}

	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		GroupTestUtil.deleteGroup(_importGroup);

		_logCaptures.forEach(LogCapture::close);
	}

	@Test
	public void testBatchExportImportSitePages() throws Exception {
		StringBundler sb = new StringBundler(4);

		String className = "com.liferay.headless.admin.site.dto.v1_0.SitePage";

		SitePage sitePage1 = randomSitePage();
		SitePage sitePage2 = randomSitePage();

		assertEquals(
			sitePage1,
			sitePageResource.postByExternalReferenceCodeSitePage(
				testGroup.getExternalReferenceCode(), sitePage1));
		assertEquals(
			sitePage2,
			sitePageResource.postByExternalReferenceCodeSitePage(
				testGroup.getExternalReferenceCode(), sitePage2));

		try {
			JSONArray itemsJSONArray = _getExportedItemsJSONArray(className);

			_testPostImportTask(className, itemsJSONArray);

			Page<SitePage> page =
				sitePageResource.
					getSiteSiteByExternalReferenceCodeSitePagesPage(
						_importGroup.getExternalReferenceCode(), null, null,
						null, Pagination.of(1, 10), null);

			Assert.assertEquals(2, page.getTotalCount());

			assertEquals(
				sitePage1,
				sitePageResource.getSiteSiteByExternalReferenceCodeSitePage(
					_importGroup.getExternalReferenceCode(),
					sitePage1.getExternalReferenceCode()));
			assertEquals(
				sitePage2,
				sitePageResource.getSiteSiteByExternalReferenceCodeSitePage(
					_importGroup.getExternalReferenceCode(),
					sitePage2.getExternalReferenceCode()));
		}
		catch (Throwable throwable) {
			sb.append(className);
			sb.append(": ");
			sb.append(throwable.getMessage());
			sb.append("\n");
		}

		if (sb.length() > 0) {
			throw new AssertionError(sb.toString());
		}
	}

	@Override
	@Test
	public void testDeleteSiteSiteByExternalReferenceCodeSitePage()
		throws Exception {

		SitePage postSitePage =
			testGetSiteSiteByExternalReferenceCodeSitePagesPage_addSitePage(
				testGroup.getExternalReferenceCode(), randomSitePage());

		sitePageResource.deleteSiteSiteByExternalReferenceCodeSitePage(
			testGroup.getExternalReferenceCode(),
			postSitePage.getExternalReferenceCode());

		Assert.assertNull(
			_layoutLocalService.fetchLayoutByExternalReferenceCode(
				postSitePage.getExternalReferenceCode(),
				testGroup.getGroupId()));
	}

	@Override
	@Test
	public void testGetSiteSiteByExternalReferenceCodeSitePage()
		throws Exception {

		SitePage postSitePage =
			testGetSiteSiteByExternalReferenceCodeSitePagesPage_addSitePage(
				testGroup.getExternalReferenceCode(), randomSitePage());

		SitePage getSitePage =
			sitePageResource.getSiteSiteByExternalReferenceCodeSitePage(
				testGroup.getExternalReferenceCode(),
				postSitePage.getExternalReferenceCode());

		assertEquals(postSitePage, getSitePage);
		assertValid(getSitePage);

		_testGetSiteSiteByExternalReferenceCodeSitePageWithNestedFields(
			testGetSiteSiteByExternalReferenceCodeSitePagesPage_addSitePage(
				testGroup.getExternalReferenceCode(), randomSitePage()));
	}

	@Ignore
	@Override
	@Test
	public void testGetSiteSiteByExternalReferenceCodeSitePagePermissionsPage()
		throws Exception {

		super.testGetSiteSiteByExternalReferenceCodeSitePagePermissionsPage();
	}

	@Override
	@Test
	public void testGetSiteSiteByExternalReferenceCodeSitePagesPage()
		throws Exception {

		super.testGetSiteSiteByExternalReferenceCodeSitePagesPage();
	}

	@Override
	@Test
	public void testGetSiteSiteByExternalReferenceCodeSitePagesPageWithPagination()
		throws Exception {

		super.
			testGetSiteSiteByExternalReferenceCodeSitePagesPageWithPagination();
	}

	@Ignore
	@Override
	@Test
	public void testGetSiteSiteExternalReferenceCodeSitePagePermissionsPage()
		throws Exception {

		super.testGetSiteSiteExternalReferenceCodeSitePagePermissionsPage();
	}

	@Ignore
	@Override
	@Test
	public void testPatchSiteSiteByExternalReferenceCodeSitePage()
		throws Exception {

		super.testPatchSiteSiteByExternalReferenceCodeSitePage();
	}

	@Ignore
	@Override
	@Test
	public void testPutSiteSiteByExternalReferenceCodeSitePage()
		throws Exception {

		super.testPutSiteSiteByExternalReferenceCodeSitePage();
	}

	@Ignore
	@Override
	@Test
	public void testPutSiteSiteByExternalReferenceCodeSitePagePermissionsPage()
		throws Exception {

		super.testPutSiteSiteByExternalReferenceCodeSitePagePermissionsPage();
	}

	@Ignore
	@Override
	@Test
	public void testPutSiteSiteExternalReferenceCodeSitePagePermissionsPage()
		throws Exception {

		super.testPutSiteSiteExternalReferenceCodeSitePagePermissionsPage();
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {
			"externalReferenceCode", "friendlyUrlPath_i18n", "name_i18n",
			"pageSettings", "type", "uuid"
		};
	}

	@Override
	protected SitePage randomSitePage() throws Exception {
		SitePage sitePage = super.randomSitePage();

		sitePage.setFriendlyUrlPath_i18n(
			HashMapBuilder.put(
				LocaleUtil.toBCP47LanguageId(LocaleUtil.SPAIN),
				StringPool.FORWARD_SLASH +
					StringUtil.toLowerCase(RandomTestUtil.randomString())
			).put(
				LocaleUtil.toBCP47LanguageId(LocaleUtil.US),
				StringPool.FORWARD_SLASH +
					StringUtil.toLowerCase(RandomTestUtil.randomString())
			).build());
		sitePage.setName_i18n(
			HashMapBuilder.put(
				LocaleUtil.toBCP47LanguageId(LocaleUtil.US),
				RandomTestUtil.randomString()
			).put(
				LocaleUtil.toBCP47LanguageId(LocaleUtil.SPAIN),
				RandomTestUtil.randomString()
			).build());
		sitePage.setPageSettings(
			new WidgetPageSettings() {
				{
					setHiddenFromNavigation(false);
					setLayoutTemplateId("1_column");
					setType(Type.WIDGET_PAGE_SETTINGS);
				}
			});
		sitePage.setType(SitePage.Type.WIDGET_PAGE);

		return sitePage;
	}

	@Override
	protected SitePage
			testGetSiteSiteByExternalReferenceCodeSitePagesPage_addSitePage(
				String siteExternalReferenceCode, SitePage sitePage)
		throws Exception {

		return testPostByExternalReferenceCodeSitePage_addSitePage(sitePage);
	}

	protected String
		testGetSiteSiteByExternalReferenceCodeSitePagesPage_getSiteExternalReferenceCode() {

		return testGroup.getExternalReferenceCode();
	}

	@Override
	protected SitePage testPostByExternalReferenceCodeSitePage_addSitePage(
			SitePage sitePage)
		throws Exception {

		return sitePageResource.postByExternalReferenceCodeSitePage(
			testGroup.getExternalReferenceCode(), sitePage);
	}

	private void _assertExecuteStatusEquals(
			ExportTask.ExecuteStatus expectedExecuteStatus,
			ExportTask exportTask, ExportTaskResource exportTaskResource)
		throws Exception {

		String externalReferenceCode = exportTask.getExternalReferenceCode();

		while (true) {
			exportTask =
				exportTaskResource.getExportTaskByExternalReferenceCode(
					externalReferenceCode);

			ExportTask.ExecuteStatus executeStatus =
				exportTask.getExecuteStatus();

			if ((executeStatus == ExportTask.ExecuteStatus.COMPLETED) ||
				(executeStatus == ExportTask.ExecuteStatus.FAILED)) {

				if (expectedExecuteStatus != executeStatus) {
					throw new AssertionError(exportTask.getErrorMessage());
				}

				break;
			}
		}
	}

	private void _assertNestedFields(SitePage sitePage) throws Exception {
		FriendlyUrlHistory friendlyUrlHistory =
			sitePage.getFriendlyUrlHistory();

		JSONObject jsonObject = _jsonFactory.createJSONObject(
			GetterUtil.getString(friendlyUrlHistory.getFriendlyUrlPath_i18n()));

		Layout layout = _layoutLocalService.getLayoutByExternalReferenceCode(
			sitePage.getExternalReferenceCode(), testGroup.getGroupId());

		Map<Locale, String> friendlyURLMap = layout.getFriendlyURLMap();

		Assert.assertEquals(
			jsonObject.toString(), friendlyURLMap.size(), jsonObject.length());

		for (Map.Entry<Locale, String> entry : friendlyURLMap.entrySet()) {
			String key = LocaleUtil.toBCP47LanguageId(entry.getKey());

			JSONArray jsonArray = jsonObject.getJSONArray(key);

			Assert.assertEquals(jsonArray.toString(), 1, jsonArray.length());
			Assert.assertEquals(
				jsonArray.toString(), entry.getValue(), jsonArray.getString(0));
		}
	}

	private JSONArray _getExportedItemsJSONArray(String className)
		throws Exception {

		ExportTask exportTask = _exportTaskResource.postExportTask(
			className, "jsont", null, null, null, null);

		String externalReferenceCode = exportTask.getExternalReferenceCode();

		_assertExecuteStatusEquals(
			ExportTask.ExecuteStatus.COMPLETED, exportTask,
			_exportTaskResource);

		String json = null;

		HttpInvoker.HttpResponse httpResponse =
			_contentExportTaskResource.
				getExportTaskByExternalReferenceCodeContentHttpResponse(
					externalReferenceCode);

		try (InputStream inputStream = new UnsyncByteArrayInputStream(
				httpResponse.getBinaryContent())) {

			ZipInputStream zipInputStream = new ZipInputStream(inputStream);

			zipInputStream.getNextEntry();

			json = StringUtil.read(zipInputStream);
		}

		JSONObject jsonObject = _jsonFactory.createJSONObject(json);

		JSONObject actionsJSONObject = jsonObject.getJSONObject("actions");

		JSONObject createBatchJSONObject = actionsJSONObject.getJSONObject(
			"createBatch");

		Assert.assertNotNull(createBatchJSONObject.getString("href"));

		return jsonObject.getJSONArray("items");
	}

	private SitePageResource _getSitePageResource() throws Exception {
		User testCompanyAdminUser = UserTestUtil.getAdminUser(
			testCompany.getCompanyId());

		SitePageResource.Builder builder = SitePageResource.builder();

		return builder.authentication(
			testCompanyAdminUser.getEmailAddress(),
			PropsValues.DEFAULT_ADMIN_PASSWORD
		).endpoint(
			testCompany.getVirtualHostname(), 8080, "http"
		).locale(
			LocaleUtil.getDefault()
		).parameters(
			"nestedFields", "friendlyUrlHistory"
		).build();
	}

	private void
			_testGetSiteSiteByExternalReferenceCodeSitePageWithNestedFields(
				SitePage sitePage)
		throws Exception {

		SitePageResource curSitePageResource = _getSitePageResource();

		_assertNestedFields(
			curSitePageResource.getSiteSiteByExternalReferenceCodeSitePage(
				testGroup.getExternalReferenceCode(),
				sitePage.getExternalReferenceCode()));
	}

	private void _testPostImportTask(String className, JSONArray itemsJSONArray)
		throws Exception {

		ImportTask importTask = _importTaskResource.postImportTask(
			className, null, "INSERT", null, null, null, null, null,
			itemsJSONArray);

		String externalReferenceCode = importTask.getExternalReferenceCode();

		while (true) {
			importTask =
				_importTaskResource.getImportTaskByExternalReferenceCode(
					externalReferenceCode);

			if (Objects.equals(
					importTask.getExecuteStatusAsString(), "COMPLETED")) {

				break;
			}
			else if (Objects.equals(
						importTask.getExecuteStatusAsString(), "FAILED")) {

				throw new AssertionError(
					StringBundler.concat(
						"Unable to import task for ", className, ":\n",
						importTask.getErrorMessage()));
			}
		}
	}

	private ExportTaskResource _contentExportTaskResource;
	private ExportTaskResource _exportTaskResource;
	private Group _importGroup;
	private ImportTaskResource _importTaskResource;

	@Inject
	private JSONFactory _jsonFactory;

	@Inject
	private LayoutLocalService _layoutLocalService;

	private final List<LogCapture> _logCaptures = new ArrayList<>();

	@Inject
	private UserLocalService _userLocalService;

}