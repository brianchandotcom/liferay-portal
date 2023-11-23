/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.transformer.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.data.engine.rest.dto.v2_0.DataDefinition;
import com.liferay.data.engine.rest.resource.v2_0.DataDefinitionResource;
import com.liferay.data.engine.rest.test.util.DataDefinitionTestUtil;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMTemplateTestUtil;
import com.liferay.dynamic.data.mapping.util.DDMFormValuesToFieldsConverter;
import com.liferay.journal.constants.JournalArticleConstants;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.journal.util.JournalConverter;
import com.liferay.journal.util.JournalHelper;
import com.liferay.layout.display.page.LayoutDisplayPageProviderRegistry;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.portlet.PortletRequestModel;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.service.ThemeLocalService;
import com.liferay.portal.kernel.servlet.HttpMethods;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.templateparser.TransformerListener;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TimeZoneUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Marcellus Tavares
 */
@RunWith(Arquillian.class)
public class JournalTransformerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		Class<?> journalUtilClass = ReflectionTestUtil.getFieldValue(
			JournalTestUtil.class, "_JOURNAL_UTIL_CLASS");

		ClassLoader classLoader = journalUtilClass.getClassLoader();

		Class<?> journalTransformerClass = classLoader.loadClass(
			"com.liferay.journal.internal.transformer.JournalTransformer");

		_journalTransformer = journalTransformerClass.newInstance();
		_transformMethod = ReflectionTestUtil.getMethod(
			journalTransformerClass, "transform", JournalArticle.class,
			DDMTemplate.class, JournalHelper.class, String.class,
			LayoutDisplayPageProviderRegistry.class, List.class,
			PortletRequestModel.class, boolean.class, String.class,
			ThemeDisplay.class, String.class);

		Bundle bundle = FrameworkUtil.getBundle(journalUtilClass);

		_serviceTrackerList = ServiceTrackerListFactory.open(
			bundle.getBundleContext(), TransformerListener.class,
			"(javax.portlet.name=" + JournalPortletKeys.JOURNAL + ")");
	}

	@AfterClass
	public static void tearDownClass() {
		_serviceTrackerList.close();
	}

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		DataDefinition dataDefinition =
			DataDefinitionTestUtil.addDataDefinition(
				"journal", _dataDefinitionResourceFactory, _group.getGroupId(),
				StringUtil.replace(
					_read("data_definition.json"),
					new String[] {"[$FIELD_SET_NAME$]"},
					new String[] {"FieldsGroup19507604"}),
				TestPropsValues.getUser());

		_journalArticle = JournalTestUtil.addArticleWithXMLContent(
			_group.getGroupId(),
			StringUtil.replace(
				_read("journal_content.xml"),
				new String[] {"[$FIELD_SET_NAME$]"},
				new String[] {"FieldsGroup19507604"}),
			dataDefinition.getDataDefinitionKey(), null);
	}

	@Test
	public void testFTLTransformation() throws Exception {
		Assert.assertEquals(
			"Joe Bloggs - print",
			_transformMethod.invoke(
				_journalTransformer, _journalArticle, null, _journalHelper,
				LocaleUtil.toLanguageId(LocaleUtil.US),
				_layoutDisplayPageProviderRegistry,
				ListUtil.filter(
					_serviceTrackerList.toList(),
					TransformerListener::isEnabled),
				null, false, "${name.getData()} - ${viewMode}", null,
				Constants.PRINT));
	}

	@Test
	public void testIncludeNestedFieldBackwardsCompatibility()
		throws Exception {

		DataDefinition dataDefinition =
			DataDefinitionTestUtil.addDataDefinition(
				"journal", _dataDefinitionResourceFactory, _group.getGroupId(),
				StringUtil.replace(
					_read("data_definition.json"),
					new String[] {"[$FIELD_SET_NAME$]"},
					new String[] {"birthdayFieldSet"}),
				TestPropsValues.getUser());

		_journalArticle = JournalTestUtil.addArticleWithXMLContent(
			_group.getGroupId(),
			StringUtil.replace(
				_read("journal_content.xml"),
				new String[] {"[$FIELD_SET_NAME$]"},
				new String[] {"birthdayFieldSet"}),
			dataDefinition.getDataDefinitionKey(), null);

		Assert.assertEquals(
			"2022-11-26",
			_transformMethod.invoke(
				_journalTransformer, _journalArticle, null, _journalHelper,
				LocaleUtil.toLanguageId(LocaleUtil.US),
				_layoutDisplayPageProviderRegistry,
				ListUtil.filter(
					_serviceTrackerList.toList(),
					TransformerListener::isEnabled),
				null, false, "${birthday.getData()}", null, Constants.VIEW));
	}

	@Test
	public void testLocaleTransformerListener() throws Exception {
		Assert.assertEquals(
			"Joe Bloggs",
			_transformMethod.invoke(
				_journalTransformer, _journalArticle, null, _journalHelper,
				LocaleUtil.toLanguageId(LocaleUtil.US),
				_layoutDisplayPageProviderRegistry,
				ListUtil.filter(
					_serviceTrackerList.toList(),
					TransformerListener::isEnabled),
				null, false, "${name.getData()}", null, Constants.VIEW));

		Assert.assertEquals(
			"Joao da Silva",
			_transformMethod.invoke(
				_journalTransformer, _journalArticle, null, _journalHelper,
				LocaleUtil.toLanguageId(LocaleUtil.BRAZIL),
				_layoutDisplayPageProviderRegistry,
				ListUtil.filter(
					_serviceTrackerList.toList(),
					TransformerListener::isEnabled),
				null, false, "${name.getData()}", null, Constants.VIEW));

		Assert.assertEquals(
			"Joe Bloggs",
			_transformMethod.invoke(
				_journalTransformer, _journalArticle, null, _journalHelper,
				LocaleUtil.toLanguageId(LocaleUtil.FRENCH),
				_layoutDisplayPageProviderRegistry,
				ListUtil.filter(
					_serviceTrackerList.toList(),
					TransformerListener::isEnabled),
				null, false, "${name.getData()}", null, Constants.VIEW));
	}

	@Test
	public void testLocaleTransformerListenerNestedFieldWithNoTranslation()
		throws Exception {

		Assert.assertEquals(
			"2022-11-26",
			_transformMethod.invoke(
				_journalTransformer, _journalArticle, null, _journalHelper,
				LocaleUtil.toLanguageId(LocaleUtil.US),
				_layoutDisplayPageProviderRegistry,
				ListUtil.filter(
					_serviceTrackerList.toList(),
					TransformerListener::isEnabled),
				null, false, "${FieldsGroup19507604.birthday.getData()}", null,
				Constants.VIEW));

		Assert.assertEquals(
			"2022-11-26",
			_transformMethod.invoke(
				_journalTransformer, _journalArticle, null, _journalHelper,
				LocaleUtil.toLanguageId(LocaleUtil.BRAZIL),
				_layoutDisplayPageProviderRegistry,
				ListUtil.filter(
					_serviceTrackerList.toList(),
					TransformerListener::isEnabled),
				null, false, "${FieldsGroup19507604.birthday.getData()}", null,
				Constants.VIEW));
	}

	@Test
	public void testRandomNamespaceAssetPublisherTemplate() throws Exception {
		PermissionChecker originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(TestPropsValues.getUser());

		PermissionThreadLocal.setPermissionChecker(permissionChecker);

		String pid =
			"com.liferay.asset.publisher.web.internal.configuration." +
				"AssetPublisherSelectionStyleConfiguration";

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.journal.internal.transformer.JournalTransformer",
				LoggerTestUtil.WARN)) {

			ConfigurationTestUtil.saveConfiguration(
				pid,
				HashMapDictionaryBuilder.<String, Object>put(
					"defaultSelectionStyle", "dynamic"
				).build());

			ThemeDisplay themeDisplay = new ThemeDisplay();

			Layout layout = LayoutTestUtil.addTypePortletLayout(
				_group.getGroupId());

			LayoutSet layoutSet = _layoutSetLocalService.getLayoutSet(
				_group.getGroupId(), false);

			themeDisplay.setCompany(
				_companyLocalService.getCompany(_group.getCompanyId()));
			themeDisplay.setLayout(layout);
			themeDisplay.setLayoutSet(layoutSet);
			themeDisplay.setLayoutTypePortlet(
				(LayoutTypePortlet)layout.getLayoutType());
			themeDisplay.setLocale(LocaleUtil.US);
			themeDisplay.setLookAndFeel(
				_themeLocalService.getTheme(
					_group.getCompanyId(), layoutSet.getThemeId()),
				null);
			themeDisplay.setPermissionChecker(permissionChecker);
			themeDisplay.setRealUser(TestPropsValues.getUser());
			themeDisplay.setScopeGroupId(_group.getGroupId());
			themeDisplay.setSiteGroupId(_group.getGroupId());
			themeDisplay.setTimeZone(TimeZoneUtil.getDefault());
			themeDisplay.setUser(TestPropsValues.getUser());

			MockHttpServletRequest mockHttpServletRequest =
				new MockHttpServletRequest();

			mockHttpServletRequest.setAttribute(
				WebKeys.CTX, mockHttpServletRequest.getServletContext());
			mockHttpServletRequest.setAttribute(WebKeys.LAYOUT, layout);
			mockHttpServletRequest.setAttribute(
				WebKeys.THEME_DISPLAY, themeDisplay);
			mockHttpServletRequest.setMethod(HttpMethods.GET);
			mockHttpServletRequest.setParameter(
				"currentURL", "http://localhost:8080/currentURL");

			themeDisplay.setRequest(mockHttpServletRequest);

			themeDisplay.setResponse(new MockHttpServletResponse());

			DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
				_group.getGroupId(), JournalArticle.class.getName());

			DDMTemplate ddmTemplate = DDMTemplateTestUtil.addTemplate(
				_group.getGroupId(), ddmStructure.getStructureId(),
				_portal.getClassNameId(JournalArticle.class),
				TemplateConstants.LANG_TYPE_FTL,
				new String(
					FileUtil.getBytes(
						getClass(),
						"dependencies" +
							"/random_namespace_asset_publisher_template.ftl")),
				LocaleUtil.US);

			JournalArticle journalArticle =
				JournalTestUtil.addArticleWithXMLContent(
					_group.getGroupId(),
					JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
					JournalArticleConstants.CLASS_NAME_ID_DEFAULT,
					DDMStructureTestUtil.getSampleStructuredContent(),
					ddmStructure.getStructureKey(),
					ddmTemplate.getTemplateKey(), LocaleUtil.US);

			_transformMethod.invoke(
				_journalTransformer, journalArticle, ddmTemplate,
				_journalHelper, LocaleUtil.toLanguageId(LocaleUtil.US),
				_layoutDisplayPageProviderRegistry,
				ListUtil.filter(
					_serviceTrackerList.toList(),
					TransformerListener::isEnabled),
				null, false, ddmTemplate.getScript(), themeDisplay,
				Constants.VIEW);

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Assert.assertEquals(
				"The article cannot include itself: " +
					journalArticle.getArticleId(),
				logEntry.getMessage());
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(
				originalPermissionChecker);

			ConfigurationTestUtil.deleteConfiguration(pid);
		}
	}

	@Test
	public void testRegexTransformerListener() throws Exception {
		initRegexTransformerListener();

		Assert.assertEquals(
			"Hello Joe Bloggs, Welcome to production.sample.com.",
			_transformMethod.invoke(
				_journalTransformer, _journalArticle, null, _journalHelper,
				LocaleUtil.toLanguageId(LocaleUtil.US),
				_layoutDisplayPageProviderRegistry,
				ListUtil.filter(
					_serviceTrackerList.toList(),
					TransformerListener::isEnabled),
				null, false,
				"Hello ${name.getData()}, Welcome to beta.sample.com.", null,
				Constants.VIEW));
	}

	@Test
	public void testTokensTransformerListener() throws Exception {
		Assert.assertEquals(
			String.valueOf(TestPropsValues.getCompanyId()),
			_transformMethod.invoke(
				_journalTransformer, _journalArticle, null, _journalHelper,
				LocaleUtil.toLanguageId(LocaleUtil.US),
				_layoutDisplayPageProviderRegistry,
				ListUtil.filter(
					_serviceTrackerList.toList(),
					TransformerListener::isEnabled),
				null, false, "@company_id@", null, Constants.VIEW));

		Assert.assertEquals(
			String.valueOf(TestPropsValues.getCompanyId()),
			_transformMethod.invoke(
				_journalTransformer, _journalArticle, null, _journalHelper,
				LocaleUtil.toLanguageId(LocaleUtil.US),
				_layoutDisplayPageProviderRegistry,
				ListUtil.filter(
					_serviceTrackerList.toList(),
					TransformerListener::isEnabled),
				null, false, "@@company_id@@", null, Constants.VIEW));
	}

	@Test
	public void testTransformSelectDDMFormFieldType() throws Exception {
		DataDefinition dataDefinition =
			DataDefinitionTestUtil.addDataDefinition(
				"journal", _dataDefinitionResourceFactory, _group.getGroupId(),
				_read(
					"data_definition_with_select_field_single_selection.json"),
				TestPropsValues.getUser());

		JournalArticle journalArticle =
			JournalTestUtil.addArticleWithXMLContent(
				_group.getGroupId(),
				_read("journal_content_with_select_field_single_selection.xml"),
				dataDefinition.getDataDefinitionKey(), null);

		Assert.assertEquals(
			"Option71814087",
			_transformMethod.invoke(
				_journalTransformer, journalArticle, null, _journalHelper,
				LocaleUtil.toLanguageId(LocaleUtil.US),
				_layoutDisplayPageProviderRegistry,
				ListUtil.filter(
					_serviceTrackerList.toList(),
					TransformerListener::isEnabled),
				null, false, "${Radio80408512.getData()}", null,
				Constants.VIEW));

		dataDefinition = DataDefinitionTestUtil.addDataDefinition(
			"journal", _dataDefinitionResourceFactory, _group.getGroupId(),
			_read("data_definition_with_select_field_multiple_selection.json"),
			TestPropsValues.getUser());

		journalArticle = JournalTestUtil.addArticleWithXMLContent(
			_group.getGroupId(),
			_read("journal_content_with_select_field_multiple_selection.xml"),
			dataDefinition.getDataDefinitionKey(), null);

		Assert.assertEquals(
			JSONUtil.putAll(
				"Option81316201", "Option25867365"
			).toString(),
			_transformMethod.invoke(
				_journalTransformer, journalArticle, null, _journalHelper,
				LocaleUtil.toLanguageId(LocaleUtil.US),
				_layoutDisplayPageProviderRegistry,
				ListUtil.filter(
					_serviceTrackerList.toList(),
					TransformerListener::isEnabled),
				null, false, "${CheckboxMultiple94681127.getData()}", null,
				Constants.VIEW));
	}

	@Test
	public void testViewCounterTransformerListener() throws Exception {
		Assert.assertEquals(
			StringBundler.concat(
				"<script type=\"text/javascript\">",
				"Liferay.Service('/assetentry/increment-view-counter',",
				"{userId:0, className:'",
				"com.liferay.journal.model.JournalArticle', classPK:",
				_journalArticle.getResourcePrimKey(), "});</script>"),
			_transformMethod.invoke(
				_journalTransformer, _journalArticle, null, _journalHelper,
				LocaleUtil.toLanguageId(LocaleUtil.US),
				_layoutDisplayPageProviderRegistry,
				ListUtil.filter(
					_serviceTrackerList.toList(),
					TransformerListener::isEnabled),
				null, false, "@view_counter@", null, Constants.VIEW));
	}

	protected void initRegexTransformerListener() {
		CacheRegistryUtil.setActive(true);

		List<Pattern> patterns = new ArrayList<>();
		List<String> replacements = new ArrayList<>();

		for (int i = 0; i < 100; i++) {
			String regex = TestPropsUtil.get(
				"journal.transformer.regex.pattern." + i);
			String replacement = TestPropsUtil.get(
				"journal.transformer.regex.replacement." + i);

			if (Validator.isNull(regex) || Validator.isNull(replacement)) {
				break;
			}

			patterns.add(Pattern.compile(regex));
			replacements.add(replacement);
		}

		ReflectionTestUtil.setFieldValue(
			_transformerListener, "_patterns", patterns);
		ReflectionTestUtil.setFieldValue(
			_transformerListener, "_replacements", replacements);
	}

	private String _read(String fileName) throws Exception {
		return new String(
			FileUtil.getBytes(getClass(), "dependencies/" + fileName));
	}

	private static Object _journalTransformer;
	private static ServiceTrackerList<TransformerListener> _serviceTrackerList;
	private static Method _transformMethod;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private DataDefinitionResource.Factory _dataDefinitionResourceFactory;

	@Inject
	private DDMFormValuesToFieldsConverter _ddmFormValuesToFieldsConverter;

	@DeleteAfterTestRun
	private Group _group;

	private JournalArticle _journalArticle;

	@Inject
	private JournalConverter _journalConverter;

	@Inject
	private JournalHelper _journalHelper;

	@Inject
	private Language _language;

	@Inject
	private LayoutDisplayPageProviderRegistry
		_layoutDisplayPageProviderRegistry;

	@Inject
	private LayoutSetLocalService _layoutSetLocalService;

	@Inject
	private Portal _portal;

	@Inject
	private ThemeLocalService _themeLocalService;

	@Inject(
		filter = "component.name=com.liferay.journal.internal.transformer.RegexTransformerListener"
	)
	private TransformerListener _transformerListener;

}