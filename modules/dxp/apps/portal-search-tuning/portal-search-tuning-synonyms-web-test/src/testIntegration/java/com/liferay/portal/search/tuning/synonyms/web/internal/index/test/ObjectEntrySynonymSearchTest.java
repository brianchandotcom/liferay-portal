/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.synonyms.web.internal.index.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectEntryFolderConstants;
import com.liferay.object.field.builder.TextObjectFieldBuilder;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.bag.ObjectFieldBag;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.PortalPreferencesLocalServiceUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import jakarta.portlet.ActionRequest;

import java.io.Serializable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Felipe Lorenz
 */
@RunWith(Arquillian.class)
public class ObjectEntrySynonymSearchTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() throws Exception {
		_companyId = TestPropsValues.getCompanyId();
		_originalName = PrincipalThreadLocal.getName();

		_user = UserTestUtil.getAdminUser(_companyId);

		PrincipalThreadLocal.setName(_user.getUserId());

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(
				_user.getUserId(), true);

		_originalPortalPreferencesXML = PortletPreferencesFactoryUtil.toXML(
			portalPreferences);

		portalPreferences.setValue("", "locales", "en_US,pt_BR");

		PortalPreferencesLocalServiceUtil.updatePreferences(
			_companyId, PortletKeys.PREFS_OWNER_TYPE_COMPANY,
			PortletPreferencesFactoryUtil.toXML(portalPreferences));

		_addObjectDefinition();
		_addObjectEntry("PD initiative");
		_addObjectEntry("product delivery Initiative");
		_addObjectEntry("Query Builder Initiative");
		_addObjectEntry("UQB Initiative");
		_addSynonymSets();
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		if (_objectDefinition != null) {
			_objectDefinitionLocalService.deleteObjectDefinition(
				_objectDefinition);
		}

		_deleteSynonymSets();

		PrincipalThreadLocal.setName(_originalName);

		PortalPreferencesLocalServiceUtil.updatePreferences(
			_companyId, PortletKeys.PREFS_OWNER_TYPE_COMPANY,
			_originalPortalPreferencesXML);
	}

	@Test
	public void testSearch() {
		_testSearch("PD");
		_testSearch("product delivery");
		_testSearch("query");
		_testSearch("uqb");
	}

	private static void _addObjectDefinition() throws Exception {
		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.addCustomObjectDefinition(
				null, _user.getUserId(), 0, null, true, false, true, false,
				true, false, false, false, false, null,
				LocalizedMapUtil.getLocalizedMap(
					ObjectDefinitionTestUtil.getRandomName()),
				ObjectDefinitionTestUtil.getRandomName(), null, null,
				LocalizedMapUtil.getLocalizedMap(
					ObjectDefinitionTestUtil.getRandomName()),
				true, ObjectDefinitionConstants.SCOPE_COMPANY,
				ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT,
				Collections.emptyList(),
				Arrays.asList(
					new TextObjectFieldBuilder(
					).indexed(
						true
					).labelMap(
						LocalizedMapUtil.getLocalizedMap("Content")
					).localized(
						true
					).name(
						Field.CONTENT
					).build(),
					new TextObjectFieldBuilder(
					).indexed(
						true
					).labelMap(
						LocalizedMapUtil.getLocalizedMap("Title")
					).name(
						_TITLE_FIELD_NAME
					).build()),
				Collections.emptyList(),
				ServiceContextTestUtil.getServiceContext());

		ObjectFieldBag objectFieldBag = objectDefinition.getObjectFieldBag();

		ObjectField titleField = objectFieldBag.getObjectField(
			_TITLE_FIELD_NAME);

		_objectDefinitionLocalService.updateTitleObjectFieldId(
			objectDefinition.getObjectDefinitionId(),
			titleField.getObjectFieldId());

		_objectDefinition =
			_objectDefinitionLocalService.publishCustomObjectDefinition(
				_user.getUserId(), objectDefinition.getObjectDefinitionId());
	}

	private static void _addObjectEntry(String content) throws Exception {
		_objectEntryLocalService.addObjectEntry(
			0, _user.getUserId(), _objectDefinition.getObjectDefinitionId(),
			ObjectEntryFolderConstants.PARENT_OBJECT_ENTRY_FOLDER_ID_DEFAULT,
			null,
			HashMapBuilder.<String, Serializable>put(
				_TITLE_FIELD_NAME, "title"
			).put(
				Field.CONTENT + "_i18n",
				HashMapBuilder.put(
					LocaleUtil.toLanguageId(LocaleUtil.US), content
				).build()
			).build(),
			ServiceContextTestUtil.getServiceContext());
	}

	private static void _addSynonymSets() {
		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			new MockLiferayPortletActionRequest();

		mockLiferayPortletActionRequest.setAttribute(
			WebKeys.COMPANY_ID, _companyId);

		ReflectionTestUtil.invoke(
			_editSynonymSetsMVCActionCommand, "updateSynonymSets",
			new Class<?>[] {ActionRequest.class, String[].class},
			mockLiferayPortletActionRequest, _SYNONYM_SETS);
	}

	private static void _deleteSynonymSets() {
		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			new MockLiferayPortletActionRequest();

		mockLiferayPortletActionRequest.addParameter(
			"deleteAllSynonymSets", "true");
		mockLiferayPortletActionRequest.setAttribute(
			WebKeys.COMPANY_ID, _companyId);

		ReflectionTestUtil.invoke(
			_deleteSynonymSetsMVCActionCommand, "deleteSynonymSets",
			new Class<?>[] {ActionRequest.class},
			mockLiferayPortletActionRequest);
	}

	private void _testSearch(String keyword) {
		SearchRequestBuilder searchRequestBuilder =
			_searchRequestBuilderFactory.builder(
			).companyId(
				_companyId
			).entryClassNames(
				_objectDefinition.getClassName()
			).queryString(
				keyword
			);

		SearchResponse searchResponse = _searcher.search(
			searchRequestBuilder.build());

		List<Document> documents = searchResponse.getDocuments71();

		Assert.assertEquals(
			searchResponse.getRequestString(), 2, documents.size());
	}

	private static final String[] _SYNONYM_SETS = {
		"product delivery,PD", "query,uqb"
	};

	private static final String _TITLE_FIELD_NAME = "title";

	private static Long _companyId;

	@Inject(
		filter = "mvc.command.name=/synonyms/delete_synonym_sets",
		type = MVCActionCommand.class
	)
	private static MVCActionCommand _deleteSynonymSetsMVCActionCommand;

	@Inject(
		filter = "mvc.command.name=/synonyms/edit_synonym_sets",
		type = MVCActionCommand.class
	)
	private static MVCActionCommand _editSynonymSetsMVCActionCommand;

	private static ObjectDefinition _objectDefinition;

	@Inject
	private static ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private static ObjectEntryLocalService _objectEntryLocalService;

	private static String _originalName;
	private static String _originalPortalPreferencesXML;
	private static User _user;

	@Inject
	private Searcher _searcher;

	@Inject
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

}