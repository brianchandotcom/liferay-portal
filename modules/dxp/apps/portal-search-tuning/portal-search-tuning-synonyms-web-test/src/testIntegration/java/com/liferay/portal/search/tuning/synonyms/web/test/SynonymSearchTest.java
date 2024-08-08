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
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
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
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.PortalPreferencesLocalServiceUtil;
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
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.portal.search.test.util.DocumentsAssert;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.IOException;
import java.io.InputStream;

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
		_companyId = TestPropsValues.getCompanyId();
		_originalName = PrincipalThreadLocal.getName();

		_user = UserTestUtil.getAdminUser(_companyId);

		long userId = _user.getUserId();

		PrincipalThreadLocal.setName(userId);

		_group = GroupTestUtil.addGroup(
			_companyId, userId, GroupConstants.DEFAULT_PARENT_GROUP_ID);

		_setUpLocales(userId);

		_setUpSynonyms();

		_addJournalArticles();
	}

	@AfterClass
	public static void tearDownClass() {
		PrincipalThreadLocal.setName(_originalName);

		PortalPreferencesLocalServiceUtil.updatePreferences(
			_companyId, PortletKeys.PREFS_OWNER_TYPE_COMPANY,
			_originalPortalPreferencesXML);
	}

	@Test
	public void testSearchOnLocalesWithDefaultSynonymFilters()
		throws Exception {

		_assertSearch("carro", LocaleUtil.PORTUGAL);
		_assertSearch("contento", LocaleUtil.ITALY);
		_assertSearch("dxp", LocaleUtil.US);
		_assertSearch("efectivo", LocaleUtil.SPAIN);
		_assertSearch("effectief", LocaleUtil.NETHERLANDS);
		_assertSearch("feliz", LocaleUtil.BRAZIL);
		_assertSearch("feliç", _CATALAN_LOCALE);
		_assertSearch("glücklich", LocaleUtil.GERMANY);
		_assertSearch("hatékony", LocaleUtil.HUNGARY);
		_assertSearch("lycklig", _SWEDISH_LOCALE);
		_assertSearch("maison", LocaleUtil.FRANCE);
		_assertSearch("tehokas", _FINNISH_LOCALE);
		_assertSearch("فعال", _ARABIC_LOCALE);
	}

	private static void _addJournalArticle(Map<Locale, String> localeStringMap)
		throws Exception {

		JournalTestUtil.addArticle(
			_group.getGroupId(), 0,
			PortalUtil.getClassNameId(JournalArticle.class), localeStringMap,
			null, localeStringMap, LocaleUtil.getSiteDefault(), false, true,
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _user.getUserId()));
	}

	private static void _addJournalArticles() throws Exception {
		_addJournalArticle(
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
		_addJournalArticle(
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

	private static void _addSynonymSet(String synonymSet) {
		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			new MockLiferayPortletActionRequest();

		mockLiferayPortletActionRequest.setAttribute(
			WebKeys.COMPANY_ID, _companyId);
		mockLiferayPortletActionRequest.addParameter("synonymSet", synonymSet);

		ReflectionTestUtil.invoke(
			_mvcActionCommand, "updateSynonymSet",
			new Class<?>[] {ActionRequest.class},
			mockLiferayPortletActionRequest);
	}

	private static String _getResourceAsString(
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

	private static String _getSearchEngineConfigurationPid() {
		SearchEngine searchEngine = SearchEngineHelperUtil.getSearchEngine();

		if (Objects.equals(searchEngine.getVendor(), "OpenSearch")) {
			return _CONFIGURATION_PID_OPENSEARCH_2;
		}

		return _CONFIGURATION_PID_ELASTICSEARCH;
	}

	private static String _loadOverrideTypeMappings() {
		try {
			return _getResourceAsString(
				SynonymSearchTest.class,
				"dependencies/" + SynonymSearchTest.class.getSimpleName() +
					"-overrideTypeMappings.json");
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	private static void _setUpLocales(long userId) {
		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(userId, true);

		_originalPortalPreferencesXML = PortletPreferencesFactoryUtil.toXML(
			portalPreferences);

		portalPreferences.setValue(
			"", "locales",
			"ar_SA,ca_ES,zh_CN,nl_NL,en_US,pt_PT,fi_FI,fr_FR,de_DE,hu_HU," +
				"it_IT,ja_JP,pt_BR,es_ES,sv_SE");

		PortalPreferencesLocalServiceUtil.updatePreferences(
			_companyId, PortletKeys.PREFS_OWNER_TYPE_COMPANY,
			PortletPreferencesFactoryUtil.toXML(portalPreferences));

		LanguageUtil.init();
	}

	private static Dictionary<String, Object> _setUpSearchEngineProperties()
		throws Exception {

		Configuration configuration = _configurationAdmin.getConfiguration(
			_getSearchEngineConfigurationPid(), StringPool.QUESTION);

		Dictionary<String, Object> properties = configuration.getProperties();

		if (properties == null) {
			properties = new HashMapDictionary<>();
		}

		properties.put("overrideTypeMappings", _loadOverrideTypeMappings());

		return properties;
	}

	private static void _setUpSynonyms() throws Exception {
		try (ConfigurationTemporarySwapper
				elasticSearchConfigurationTemporarySwapper =
					new ConfigurationTemporarySwapper(
							_getSearchEngineConfigurationPid(),
						_setUpSearchEngineProperties());

			 ConfigurationTemporarySwapper synonymConfigurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					_CONFIGURATION_PID_SYNONYMS,
					_setUpSynonymsProperties())) {

			_addSynonymSet("carro,automovel");
			_addSynonymSet("contento,soddisfatto");
			_addSynonymSet("dxp,portal");
			_addSynonymSet("efectivo,productivo");
			_addSynonymSet("effectief,productief");
			_addSynonymSet("feliz,alegre");
			_addSynonymSet("feliç,satisfet");
			_addSynonymSet("glücklich,heiter");
			_addSynonymSet("hatékony,produktív");
			_addSynonymSet("lycklig,nöjd");
			_addSynonymSet("maison,logement");
			_addSynonymSet("tehokas,tuottava");
			_addSynonymSet("منتج, فعال");
		}
	}

	private static Dictionary<String, Object> _setUpSynonymsProperties() {
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

	private void _assertSearch(String keyword, Locale locale) {
		String localizedFieldName = Field.getLocalizedName(locale, Field.TITLE);

		SearchRequestBuilder searchRequestBuilder =
			_searchRequestBuilderFactory.builder(
			).companyId(
				_companyId
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
			documents.toArray(new Document[0]), localizedFieldName, 2);
	}

	private static final Locale _ARABIC_LOCALE = new Locale("ar", "SA");

	private static final Locale _CATALAN_LOCALE = new Locale("ca", "ES");

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

	private static Long _companyId;

	@Inject
	private static ConfigurationAdmin _configurationAdmin;

	private static Group _group;

	@Inject
	private static Language _language;

	@Inject(
		filter = "mvc.command.name=/synonyms/edit_synonym_sets",
		type = MVCActionCommand.class
	)
	private static MVCActionCommand _mvcActionCommand;

	private static String _originalName;
	private static String _originalPortalPreferencesXML;
	private static User _user;

	@Inject
	private Searcher _searcher;

	@Inject
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

}