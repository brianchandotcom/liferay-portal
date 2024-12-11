/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
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
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PropsValues;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipInputStream;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rubén Pulido
 */
@FeatureFlags("LPS-35443")
@RunWith(Arquillian.class)
public class SitePageBatchExportImportTaskResourceTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		User adminUser = UserTestUtil.getAdminUser(
			TestPropsValues.getCompanyId());

		_contentExportTaskResource = ExportTaskResource.builder(
		).authentication(
			adminUser.getEmailAddress(), PropsValues.DEFAULT_ADMIN_PASSWORD
		).header(
			HttpHeaders.ACCEPT, ContentTypes.APPLICATION_OCTET_STREAM
		).build();

		_exportGroup = GroupTestUtil.addGroup();

		_exportTaskResource = ExportTaskResource.builder(
		).authentication(
			adminUser.getEmailAddress(), PropsValues.DEFAULT_ADMIN_PASSWORD
		).header(
			HttpHeaders.ACCEPT, ContentTypes.APPLICATION_JSON
		).parameter(
			"siteId", String.valueOf(_exportGroup.getGroupId())
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

		_sitePageResource = SitePageResource.builder(
		).authentication(
			adminUser.getEmailAddress(), PropsValues.DEFAULT_ADMIN_PASSWORD
		).header(
			"X-Liferay-Accept-All-Languages", "true"
		).build();
	}

	@After
	public void tearDown() {
		_logCaptures.forEach(LogCapture::close);
	}

	@Test
	public void testBatchExportImportSitePages() throws Exception {
		StringBundler sb = new StringBundler(4);

		String className = "com.liferay.headless.admin.site.dto.v1_0.SitePage";

		SitePage sitePage1 =
			_sitePageResource.postByExternalReferenceCodeSitePage(
				_exportGroup.getExternalReferenceCode(), _randomSitePage());
		SitePage sitePage2 =
			_sitePageResource.postByExternalReferenceCodeSitePage(
				_exportGroup.getExternalReferenceCode(), _randomSitePage());

		try {
			JSONArray itemsJSONArray = _getExportedItemsJSONArray(className);

			_testPostImportTask(className, itemsJSONArray);

			Page<SitePage> page =
				_sitePageResource.
					getSiteSiteByExternalReferenceCodeSitePagesPage(
						_importGroup.getExternalReferenceCode(), null, null,
						null, Pagination.of(1, 10), null);

			Assert.assertEquals(2, page.getTotalCount());

			_assertEquals(
				sitePage1,
				_sitePageResource.getSiteSiteByExternalReferenceCodeSitePage(
					_importGroup.getExternalReferenceCode(),
					sitePage1.getExternalReferenceCode()));
			_assertEquals(
				sitePage2,
				_sitePageResource.getSiteSiteByExternalReferenceCodeSitePage(
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

	private void _assertEquals(
		SitePage expectedSitePage, SitePage actualSitePage) {

		expectedSitePage.setDateCreated((Date)null);
		expectedSitePage.setDateModified((Date)null);
		expectedSitePage.setDatePublished((Date)null);
		expectedSitePage.setSiteExternalReferenceCode((String)null);

		actualSitePage.setDateCreated((Date)null);
		actualSitePage.setDateModified((Date)null);
		actualSitePage.setDatePublished((Date)null);
		actualSitePage.setSiteExternalReferenceCode((String)null);

		Assert.assertEquals(
			expectedSitePage.toString(), actualSitePage.toString());
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

		Assert.assertNotNull(actionsJSONObject);

		JSONObject createBatchJSONObject = actionsJSONObject.getJSONObject(
			"createBatch");

		Assert.assertNotNull(createBatchJSONObject);

		Assert.assertNotNull(createBatchJSONObject.getString("href"));

		return jsonObject.getJSONArray("items");
	}

	private SitePage _randomSitePage() throws Exception {
		return new SitePage() {
			{
				creatorExternalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				datePublished = RandomTestUtil.nextDate();
				externalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				uuid = StringUtil.toLowerCase(RandomTestUtil.randomString());

				setFriendlyUrlPath_i18n(
					HashMapBuilder.put(
						LocaleUtil.toBCP47LanguageId(LocaleUtil.SPAIN),
						() -> {
							String randomString = StringUtil.toLowerCase(
								RandomTestUtil.randomString());

							return StringPool.FORWARD_SLASH + randomString;
						}
					).put(
						LocaleUtil.toBCP47LanguageId(LocaleUtil.US),
						() -> {
							String randomString = StringUtil.toLowerCase(
								RandomTestUtil.randomString());

							return StringPool.FORWARD_SLASH + randomString;
						}
					).build());
				setName_i18n(
					HashMapBuilder.put(
						LocaleUtil.toBCP47LanguageId(LocaleUtil.US),
						RandomTestUtil.randomString()
					).put(
						LocaleUtil.toBCP47LanguageId(LocaleUtil.SPAIN),
						RandomTestUtil.randomString()
					).build());
				setPageSettings(
					new WidgetPageSettings() {
						{
							setHiddenFromNavigation(true);
							setLayoutTemplateId("1_column");
							setType(Type.WIDGET_PAGE_SETTINGS);
						}
					});
				setType(SitePage.Type.WIDGET_PAGE);
			}
		};
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
	private Group _exportGroup;
	private ExportTaskResource _exportTaskResource;
	private Group _importGroup;
	private ImportTaskResource _importTaskResource;

	@Inject
	private JSONFactory _jsonFactory;

	private final List<LogCapture> _logCaptures = new ArrayList<>();
	private SitePageResource _sitePageResource;

}