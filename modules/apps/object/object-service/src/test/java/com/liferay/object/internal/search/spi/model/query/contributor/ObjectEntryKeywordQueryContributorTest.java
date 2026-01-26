/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.search.spi.model.query.contributor;

import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.bag.ObjectFieldBag;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectViewLocalService;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.generic.NestedQuery;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.search.localization.SearchLocalizationHelper;
import com.liferay.portal.search.spi.model.query.contributor.helper.KeywordQueryContributorHelper;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

/**
 * @author Nícolas Moura
 * @author Yuri Monteiro
 */
public class ObjectEntryKeywordQueryContributorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testContributeWithCustomObjectDefinition() throws Exception {
		ObjectDefinition objectDefinition = _mockObjectDefinition(false);

		ObjectFieldBag objectFieldBag = objectDefinition.getObjectFieldBag();

		_mockObjectFields(objectFieldBag);

		BooleanQuery booleanQuery = _mockBooleanQuery(null);

		ObjectEntryKeywordQueryContributor contributor =
			_createObjectEntryKeywordQueryContributor(objectDefinition);

		contributor.contribute(
			RandomTestUtil.randomString(), booleanQuery,
			_mockKeywordQueryContributorHelper());

		Mockito.verify(
			objectFieldBag, Mockito.never()
		).getIndexedObjectFields();

		Mockito.verify(
			objectFieldBag
		).getNonsystemIndexedObjectFields();
	}

	@Test
	public void testContributeWithModifiableSystemObjectDefinition()
		throws Exception {

		ObjectDefinition objectDefinition = _mockObjectDefinition(true);

		ObjectFieldBag objectFieldBag = objectDefinition.getObjectFieldBag();

		_mockObjectFields(objectFieldBag);

		ArgumentCaptor<Query> argumentCaptor = ArgumentCaptor.forClass(
			Query.class);

		BooleanQuery booleanQuery = _mockBooleanQuery(argumentCaptor);

		ObjectEntryKeywordQueryContributor contributor =
			_createObjectEntryKeywordQueryContributor(objectDefinition);

		contributor.contribute(
			RandomTestUtil.randomString(), booleanQuery,
			_mockKeywordQueryContributorHelper());

		Mockito.verify(
			objectFieldBag
		).getIndexedObjectFields();

		Mockito.verify(
			objectFieldBag, Mockito.never()
		).getNonsystemIndexedObjectFields();

		List<Query> queries = argumentCaptor.getAllValues();

		Assert.assertEquals(1, _countNestedQueries(queries));
	}

	private SearchContext _buildSearchContext() {
		SearchContext searchContext = new SearchContext();

		searchContext.setAndSearch(false);
		searchContext.setAttribute("searchByObjectView", Boolean.FALSE);
		searchContext.setLocale(LocaleUtil.US);

		searchContext.getQueryConfig();

		return searchContext;
	}

	private int _countNestedQueries(List<Query> queries) {
		int count = 0;

		for (Query query : queries) {
			if (query instanceof NestedQuery) {
				count++;
			}
		}

		return count;
	}

	private ObjectEntryKeywordQueryContributor
		_createObjectEntryKeywordQueryContributor(
			ObjectDefinition objectDefinition) {

		return new ObjectEntryKeywordQueryContributor(
			objectDefinition, Mockito.mock(ObjectFieldLocalService.class),
			Mockito.mock(ObjectViewLocalService.class),
			Mockito.mock(SearchLocalizationHelper.class));
	}

	private BooleanQuery _mockBooleanQuery(ArgumentCaptor<Query> argumentCaptor)
		throws Exception {

		BooleanQuery booleanQuery = Mockito.mock(BooleanQuery.class);

		if (argumentCaptor != null) {
			Mockito.when(
				booleanQuery.add(
					argumentCaptor.capture(),
					Mockito.any(BooleanClauseOccur.class))
			).thenReturn(
				null
			);
		}
		else {
			Mockito.when(
				booleanQuery.add(
					Mockito.any(Query.class),
					Mockito.any(BooleanClauseOccur.class))
			).thenReturn(
				null
			);
		}

		return booleanQuery;
	}

	private KeywordQueryContributorHelper _mockKeywordQueryContributorHelper() {
		KeywordQueryContributorHelper helper = Mockito.mock(
			KeywordQueryContributorHelper.class);

		Mockito.when(
			helper.getSearchContext()
		).thenReturn(
			_buildSearchContext()
		);

		return helper;
	}

	private ObjectDefinition _mockObjectDefinition(
		boolean modifiableAndSystem) {

		ObjectDefinition objectDefinition = Mockito.mock(
			ObjectDefinition.class);

		Mockito.when(
			objectDefinition.isModifiableAndSystem()
		).thenReturn(
			modifiableAndSystem
		);

		ObjectFieldBag objectFieldBag = Mockito.mock(ObjectFieldBag.class);

		Mockito.when(
			objectDefinition.getObjectFieldBag()
		).thenReturn(
			objectFieldBag
		);

		return objectDefinition;
	}

	private void _mockObjectFields(ObjectFieldBag objectFieldBag) {
		ObjectField metadataObjectField = _mockTextObjectField(
			RandomTestUtil.randomString(), true);
		ObjectField objectField = _mockTextObjectField(
			RandomTestUtil.randomString(), false);

		Mockito.when(
			objectFieldBag.getIndexedObjectFields()
		).thenReturn(
			Arrays.asList(metadataObjectField, objectField)
		);

		Mockito.when(
			objectFieldBag.getNonsystemIndexedObjectFields()
		).thenReturn(
			Arrays.asList(objectField)
		);
	}

	private ObjectField _mockTextObjectField(String name, boolean metadata) {
		ObjectField objectField = Mockito.mock(ObjectField.class);

		Mockito.when(
			objectField.getBusinessType()
		).thenReturn(
			ObjectFieldConstants.BUSINESS_TYPE_TEXT
		);

		Mockito.when(
			objectField.getDBType()
		).thenReturn(
			ObjectFieldConstants.DB_TYPE_STRING
		);

		Mockito.when(
			objectField.getIndexedLanguageId()
		).thenReturn(
			null
		);

		Mockito.when(
			objectField.getName()
		).thenReturn(
			name
		);

		Mockito.when(
			objectField.isIndexed()
		).thenReturn(
			true
		);

		Mockito.when(
			objectField.isIndexedAsKeyword()
		).thenReturn(
			false
		);

		Mockito.when(
			objectField.isLocalized()
		).thenReturn(
			false
		);

		Mockito.when(
			objectField.isMetadata()
		).thenReturn(
			metadata
		);

		return objectField;
	}

}