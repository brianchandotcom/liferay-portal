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
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
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

import java.util.Dictionary;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
 */
@RunWith(Arquillian.class)
public class SynonymSearchTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		try (ConfigurationTemporarySwapper
				elasticSearchConfigurationTemporarySwapper =
					new ConfigurationTemporarySwapper(
						_CONFIGURATION_PID_ELASTICSEARCH,
						setUpElasticsearchProperties());

			ConfigurationTemporarySwapper synonymConfigurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					_CONFIGURATION_PID_SYNONYMS,
					setUpSynonymsProperties())) {

			_originalName = PrincipalThreadLocal.getName();

			PrincipalThreadLocal.setName(TestPropsValues.getUserId());

			_company = CompanyTestUtil.addCompany();

			addSynonymSet("dxp,portal");
			addSynonymSet("efectivo,productivo");
			addSynonymSet("hatékony,produktív");
			addSynonymSet("maison,logement");
		}

		addJournalArticles();
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_companyLocalService.deleteCompany(_company);

		PrincipalThreadLocal.setName(_originalName);
	}

	@Test
	public void testSearchOnLocalesWithDefaultSynonymFilters()
		throws Exception {

		doAssertSearch("efectivo", Field.TITLE, LocaleUtil.SPAIN, 2);
		doAssertSearch("dxp", Field.TITLE, LocaleUtil.US, 2);
	}

	@Test
	public void testSearchOnLocaleWithCustomSynonymFilter() throws Exception {
		doAssertSearch("maison", Field.TITLE, LocaleUtil.FRANCE, 2);
	}

	@Test
	public void testSearchOnLocaleWithoutSynonymFilter() throws Exception {
		doAssertSearch("hatékony", Field.TITLE, LocaleUtil.HUNGARY, 1);
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
		_user = UserTestUtil.getAdminUser(_company.getCompanyId());

		_group = GroupTestUtil.addGroup(
			_company.getCompanyId(), _user.getUserId(),
			GroupConstants.DEFAULT_PARENT_GROUP_ID);

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId(), _user.getUserId());

		addJournalArticle(
			HashMapBuilder.put(
				LocaleUtil.FRANCE, "maison"
			).put(
				LocaleUtil.HUNGARY, "hatékony"
			).put(
				LocaleUtil.SPAIN, "efectivo"
			).put(
				LocaleUtil.US, "dxp"
			).build());
		addJournalArticle(
			HashMapBuilder.put(
				LocaleUtil.FRANCE, "logement"
			).put(
				LocaleUtil.HUNGARY, "produktív"
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
			WebKeys.COMPANY_ID, _company.getCompanyId());
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

	protected static Dictionary<String, Object> setUpElasticsearchProperties()
		throws Exception {

		Configuration configuration = _configurationAdmin.getConfiguration(
			_CONFIGURATION_PID_ELASTICSEARCH, StringPool.QUESTION);

		Dictionary<String, Object> properties = configuration.getProperties();

		if (properties == null) {
			properties = new HashMapDictionary<>();
		}

		properties.put(
			"additionalIndexConfigurations",
			loadAdditionalIndexConfigurations());
		properties.put("overrideTypeMappings", loadOverrideTypeMappings());

		return properties;
	}

	protected static Dictionary<String, Object> setUpSynonymsProperties() {
		return HashMapDictionaryBuilder.<String, Object>put(
			"filterNames",
			new String[] {
				"liferay_filter_synonym_en", "liferay_filter_synonym_es",
				"custom-synonym-filter-fr"
			}
		).build();
	}

	protected void doAssertSearch(
		String keyword, String fieldName, Locale locale, int expectedCount) {

		String localizedFieldName = Field.getLocalizedName(locale, fieldName);

		SearchRequestBuilder searchRequestBuilder =
			_searchRequestBuilderFactory.builder(
			).companyId(
				_company.getCompanyId()
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

	private static final String _CONFIGURATION_PID_ELASTICSEARCH =
		"com.liferay.portal.search.elasticsearch7.configuration." +
			"ElasticsearchConfiguration";

	private static final String _CONFIGURATION_PID_SYNONYMS =
		"com.liferay.portal.search.tuning.synonyms.web.internal." +
			"configuration.SynonymsConfiguration";

	private static Company _company;

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
	private static ServiceContext _serviceContext;
	private static User _user;

	@Inject
	private Queries _queries;

	@Inject
	private Searcher _searcher;

	@Inject
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

}