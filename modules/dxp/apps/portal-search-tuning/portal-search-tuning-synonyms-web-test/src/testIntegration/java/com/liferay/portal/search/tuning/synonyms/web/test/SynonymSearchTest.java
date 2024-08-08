/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.synonyms.web.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchEngine;
import com.liferay.portal.kernel.search.SearchEngineHelperUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.PortalPreferencesLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.portal.search.test.util.DocumentsAssert;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.IOException;
import java.io.InputStream;

import java.util.Arrays;
import java.util.Dictionary;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import javax.portlet.ActionRequest;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Tibor Lipusz
 * @author Petteri Karttunen
 */
@RunWith(Arquillian.class)
public class SynonymSearchTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_originalName = PrincipalThreadLocal.getName();

		_user = UserTestUtil.getAdminUser(_COMPANY_ID);

		long userId = _user.getUserId();

		PrincipalThreadLocal.setName(userId);

		_group = GroupTestUtil.addGroup(
			_COMPANY_ID, _user.getUserId(),
			GroupConstants.DEFAULT_PARENT_GROUP_ID);

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId(), _user.getUserId());

		try (ConfigurationTemporarySwapper
				elasticSearchConfigurationTemporarySwapper =
					new ConfigurationTemporarySwapper(
							_getSearchEngineConfigurationPid(),
						setUpSearchEngineProperties());

			ConfigurationTemporarySwapper synonymConfigurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					_CONFIGURATION_PID_SYNONYMS,
					setUpSynonymsProperties())) {

			addSynonymSet("carro,automovel");
			addSynonymSet("contento,soddisfatto");
			addSynonymSet("dxp,portal");
			addSynonymSet("efectivo,productivo");
			addSynonymSet("effectief,productief");
			addSynonymSet("feliz,alegre");
			addSynonymSet("feliç,satisfet");
			addSynonymSet("glücklich,heiter");
			addSynonymSet("hatékony,produktív");
			addSynonymSet("lycklig,nöjd");
			addSynonymSet("maison,logement");
			addSynonymSet("tehokas,tuottava");
			addSynonymSet("منتج, فعال");
		}

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(userId, true);

		_originalPortalPreferencesXML = PortletPreferencesFactoryUtil.toXML(
			portalPreferences);

		portalPreferences.setValue(
			"", "locales",
			"ar_SA,ca_ES,zh_CN,nl_NL,en_US,pt_PT,fi_FI,fr_FR,de_DE,hu_HU," +
				"it_IT,ja_JP,pt_BR,es_ES,sv_SE");

		PortalPreferencesLocalServiceUtil.updatePreferences(
			_COMPANY_ID, PortletKeys.PREFS_OWNER_TYPE_COMPANY,
			PortletPreferencesFactoryUtil.toXML(portalPreferences));

		GroupTestUtil.updateDisplaySettings(
			_group.getGroupId(),
			Arrays.asList(
				_ARABIC_LOCALE, _CATALAN_LOCALE, _FINNISH_LOCALE,
				_SWEDISH_LOCALE, LocaleUtil.SPAIN, LocaleUtil.US,
				LocaleUtil.PORTUGAL, LocaleUtil.BRAZIL, LocaleUtil.FRANCE,
				LocaleUtil.HUNGARY, LocaleUtil.CHINA, LocaleUtil.NETHERLANDS,
				LocaleUtil.GERMANY, LocaleUtil.ITALY, LocaleUtil.JAPAN),
			LocaleUtil.US);

		addJournalArticles();
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		PrincipalThreadLocal.setName(_originalName);

		PortalPreferencesLocalServiceUtil.updatePreferences(
			TestPropsValues.getCompanyId(),
			PortletKeys.PREFS_OWNER_TYPE_COMPANY,
			_originalPortalPreferencesXML);
	}

	@Test
	public void testSearchOnLocalesWithDefaultSynonymFilters()
		throws Exception {

		doAssertSearch("carro", Field.TITLE, LocaleUtil.PORTUGAL, 2);
		doAssertSearch("contento", Field.TITLE, LocaleUtil.ITALY, 2);
		doAssertSearch("dxp", Field.TITLE, LocaleUtil.US, 2);
		doAssertSearch("efectivo", Field.TITLE, LocaleUtil.SPAIN, 2);
		doAssertSearch("effectief", Field.TITLE, LocaleUtil.NETHERLANDS, 2);
		doAssertSearch("feliz", Field.TITLE, LocaleUtil.BRAZIL, 2);
		doAssertSearch("feliç", Field.TITLE, _CATALAN_LOCALE, 2);
		doAssertSearch("glücklich", Field.TITLE, LocaleUtil.GERMANY, 2);
		doAssertSearch("hatékony", Field.TITLE, LocaleUtil.HUNGARY, 2);
		doAssertSearch("lycklig", Field.TITLE, _SWEDISH_LOCALE, 2);
		doAssertSearch("maison", Field.TITLE, LocaleUtil.FRANCE, 2);
		doAssertSearch("tehokas", Field.TITLE, _FINNISH_LOCALE, 2);
		doAssertSearch("فعال", Field.TITLE, _ARABIC_LOCALE, 2);
	}

	protected static void addJournalArticle(Map<Locale, String> localeStringMap)
		throws Exception {

		JournalTestUtil.addArticle(
			_group.getGroupId(), 0,
			PortalUtil.getClassNameId(JournalArticle.class), localeStringMap,
			null, localeStringMap, LocaleUtil.getSiteDefault(), false, true,
			_serviceContext);
	}

	protected static void addJournalArticles() throws Exception {
		addJournalArticle(
			HashMapBuilder.put(
				_ARABIC_LOCALE, "فعال"
			).put(
				_CATALAN_LOCALE, "feliç"
			).put(
				_FINNISH_LOCALE, "tehokas"
			).put(
				_SWEDISH_LOCALE, "lycklig"
			).put(
				LocaleUtil.BRAZIL, "feliz"
			).put(
				LocaleUtil.FRANCE, "maison"
			).put(
				LocaleUtil.GERMANY, "glücklich"
			).put(
				LocaleUtil.HUNGARY, "hatékony"
			).put(
				LocaleUtil.ITALY, "contento"
			).put(
				LocaleUtil.NETHERLANDS, "effectief"
			).put(
				LocaleUtil.PORTUGAL, "carro"
			).put(
				LocaleUtil.SPAIN, "efectivo"
			).put(
				LocaleUtil.US, "dxp"
			).build());
		addJournalArticle(
			HashMapBuilder.put(
				_ARABIC_LOCALE, "منتج"
			).put(
				_CATALAN_LOCALE, "satisfet"
			).put(
				_FINNISH_LOCALE, "tuottava"
			).put(
				_SWEDISH_LOCALE, "nöjd"
			).put(
				LocaleUtil.BRAZIL, "alegre"
			).put(
				LocaleUtil.FRANCE, "logement"
			).put(
				LocaleUtil.GERMANY, "heiter"
			).put(
				LocaleUtil.HUNGARY, "produktív"
			).put(
				LocaleUtil.ITALY, "soddisfatto"
			).put(
				LocaleUtil.NETHERLANDS, "productief"
			).put(
				LocaleUtil.PORTUGAL, "automovel"
			).put(
				LocaleUtil.SPAIN, "productivo"
			).put(
				LocaleUtil.US, "portal"
			).build());
	}

	protected static void addSynonymSet(String synonymSet) {
		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			new MockLiferayPortletActionRequest();

		mockLiferayPortletActionRequest.setAttribute(
			WebKeys.COMPANY_ID, _COMPANY_ID);
		mockLiferayPortletActionRequest.addParameter("synonymSet", synonymSet);

		ReflectionTestUtil.invoke(
			_mvcActionCommand, "updateSynonymSet",
			new Class<?>[] {ActionRequest.class},
			mockLiferayPortletActionRequest);
	}

	protected static String getResourceAsString(
		Class<?> clazz, String resourceName) {

		try (InputStream inputStream = clazz.getResourceAsStream(
				resourceName)) {

			return StringUtil.read(inputStream);
		}
		catch (IOException ioException) {
			throw new RuntimeException(
				"Unable to load resource: " + resourceName, ioException);
		}
	}

	protected static String loadAdditionalIndexConfigurations() {
		try {
			return getResourceAsString(
				SynonymSearchTest.class,
				"dependencies/" + SynonymSearchTest.class.getSimpleName() +
					"-additionalIndexConfigurations.json");
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	protected static String loadOverrideTypeMappings() {
		try {
			return getResourceAsString(
				SynonymSearchTest.class,
				"dependencies/" + SynonymSearchTest.class.getSimpleName() +
					"-overrideTypeMappings.json");
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	protected static Dictionary<String, Object> setUpSearchEngineProperties()
		throws Exception {

		Configuration configuration = _configurationAdmin.getConfiguration(
			_getSearchEngineConfigurationPid(), StringPool.QUESTION);

		Dictionary<String, Object> properties = configuration.getProperties();

		if (properties == null) {
			properties = new HashMapDictionary<>();
		}

		properties.put("overrideTypeMappings", loadOverrideTypeMappings());

		return properties;
	}

	protected static Dictionary<String, Object> setUpSynonymsProperties() {
		return HashMapDictionaryBuilder.<String, Object>put(
			"filterNames",
			new String[] {
				"liferay_filter_synonym_ar", "liferay_filter_synonym_ca",
				"liferay_filter_synonym_de", "liferay_filter_synonym_en",
				"liferay_filter_synonym_es", "liferay_filter_synonym_fi",
				"liferay_filter_synonym_fr", "liferay_filter_synonym_hu",
				"liferay_filter_synonym_it", "liferay_filter_synonym_nl",
				"liferay_filter_synonym_pt_BR", "liferay_filter_synonym_pt_PT",
				"liferay_filter_synonym_sv"
			}
		).build();
	}

	protected void doAssertSearch(
		String keyword, String fieldName, Locale locale, int expectedCount) {

		String localizedFieldName = Field.getLocalizedName(locale, fieldName);

		SearchRequestBuilder searchRequestBuilder =
			_searchRequestBuilderFactory.builder(
			).companyId(
				_COMPANY_ID
			).entryClassNames(
				JournalArticle.class.getName()
			).groupIds(
				_group.getGroupId()
			).queryString(
				keyword
			);

		SearchResponse searchResponse = _searcher.search(
			searchRequestBuilder.build());

		List<Document> documents = searchResponse.getDocuments71();

		DocumentsAssert.assertCount(
			searchResponse.getRequestString(),
			documents.toArray(new Document[0]), localizedFieldName,
			expectedCount);
	}

	private static String _getSearchEngineConfigurationPid() {
		SearchEngine searchEngine = SearchEngineHelperUtil.getSearchEngine();

		if (Objects.equals(searchEngine.getVendor(), "OpenSearch")) {
			return _CONFIGURATION_PID_OPENSEARCH_2;
		}

		return _CONFIGURATION_PID_ELASTICSEARCH;
	}

	private static final Locale _ARABIC_LOCALE = new Locale("ar", "SA");

	private static final Locale _CATALAN_LOCALE = new Locale("ca", "ES");

	private static final Long _COMPANY_ID = CompanyThreadLocal.getCompanyId();

	private static final String _CONFIGURATION_PID_ELASTICSEARCH =
		"com.liferay.portal.search.elasticsearch7.configuration." +
			"ElasticsearchConfiguration";

	private static final String _CONFIGURATION_PID_OPENSEARCH_2 =
		"com.liferay.portal.search.opensearch2.configuration." +
			"OpenSearchConfiguration";

	private static final String _CONFIGURATION_PID_SYNONYMS =
		"com.liferay.portal.search.tuning.synonyms.web.internal." +
			"configuration.SynonymsConfiguration";

	private static final Locale _FINNISH_LOCALE = new Locale("fi", "FI");

	private static final Locale _SWEDISH_LOCALE = new Locale("sv", "SE");

	@Inject
	private static CompanyLocalService _companyLocalService;

	@Inject
	private static ConfigurationAdmin _configurationAdmin;

	private static Group _group;

	@Inject(
		filter = "mvc.command.name=/synonyms/edit_synonym_sets",
		type = MVCActionCommand.class
	)
	private static MVCActionCommand _mvcActionCommand;

	private static String _originalName;
	private static String _originalPortalPreferencesXML;
	private static ServiceContext _serviceContext;
	private static User _user;

	@Inject
	private Queries _queries;

	@Inject
	private Searcher _searcher;

	@Inject
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

}