/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.search.tuning.synonyms.web.internal.portlet.action;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.index.IndexNameBuilder;
import com.liferay.portal.search.tuning.synonyms.index.name.SynonymSetIndexName;
import com.liferay.portal.search.tuning.synonyms.web.internal.BaseSynonymsWebTestCase;
import com.liferay.portal.search.tuning.synonyms.web.internal.index.SynonymSet;
import com.liferay.portal.search.tuning.synonyms.web.internal.synchronizer.IndexToFilterSynchronizer;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Wade Cao
 */
public class DeleteSynonymSetsMVCActionCommandTest
	extends BaseSynonymsWebTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_deleteSynonymSetsMVCActionCommand =
			new DeleteSynonymSetsMVCActionCommand();

		ReflectionTestUtil.setFieldValue(
			_deleteSynonymSetsMVCActionCommand, "_indexNameBuilder",
			_indexNameBuilder);
		ReflectionTestUtil.setFieldValue(
			_deleteSynonymSetsMVCActionCommand, "_indexToFilterSynchronizer",
			_indexToFilterSynchronizer);
		ReflectionTestUtil.setFieldValue(
			_deleteSynonymSetsMVCActionCommand, "_portal", portal);
		ReflectionTestUtil.setFieldValue(
			_deleteSynonymSetsMVCActionCommand, "_synonymSetIndexNameBuilder",
			synonymSetIndexNameBuilder);
		ReflectionTestUtil.setFieldValue(
			_deleteSynonymSetsMVCActionCommand, "_synonymSetIndexReader",
			synonymSetIndexReader);
		ReflectionTestUtil.setFieldValue(
			_deleteSynonymSetsMVCActionCommand, "_synonymSetStorageAdapter",
			synonymSetStorageAdapter);
	}

	@Test
	public void testDoProcessAction() throws Exception {
		setUpHttpServletRequestParameterValues(
			_httpServletRequest, "rowIds", new String[] {"id"});
		setUpPortal(_httpServletRequest);
		setUpPortalUtil();
		setUpPortletRequest(_actionRequest);
		setUpSynonymSetIndexNameBuilder();
		setUpSynonymSetIndexReader("id", "car,automobile");

		_deleteSynonymSetsMVCActionCommand.doProcessAction(
			_actionRequest, _actionResponse);

		Mockito.verify(
			_actionRequest, Mockito.times(3)
		).getAttribute(
			Mockito.anyString()
		);
	}

	@Test
	public void testGetDeletedSynonymSets() throws Exception {
		setUpHttpServletRequestParameterValues(
			_httpServletRequest, "rowIds", new String[] {"id"});
		setUpPortal(_httpServletRequest);
		setUpPortalUtil();
		setUpSynonymSetIndexReader("id", "car,automobile");

		List<SynonymSet> synonymSets =
			_deleteSynonymSetsMVCActionCommand.getDeletedSynonymSets(
				_actionRequest, Mockito.mock(SynonymSetIndexName.class));

		Assert.assertEquals(1, synonymSets.size(), 0.0);

		Stream<SynonymSet> synonymSetsStream = synonymSets.stream();

		synonymSetsStream.forEach(
			synonymSet -> {
				Assert.assertEquals("car,automobile", synonymSet.getSynonyms());
				Assert.assertEquals("id", synonymSet.getSynonymSetDocumentId());
			});
	}

	@Test
	public void testRemoveSynonymSets() throws Exception {
		SynonymSet.SynonymSetBuilder synonymSetBuilder =
			new SynonymSet.SynonymSetBuilder();

		_deleteSynonymSetsMVCActionCommand.removeSynonymSets(
			Mockito.mock(SynonymSetIndexName.class),
			Arrays.asList(
				synonymSetBuilder.synonyms(
					"car,atumobile"
				).synonymSetDocumentId(
					"id-1"
				).build(),
				synonymSetBuilder.synonyms(
					"clever,smart"
				).synonymSetDocumentId(
					"id-2"
				).build()));

		Mockito.verify(
			synonymSetStorageAdapter, Mockito.times(2)
		).delete(
			Mockito.any(), Mockito.anyString()
		);
	}

	private final ActionRequest _actionRequest = Mockito.mock(
		ActionRequest.class);
	private final ActionResponse _actionResponse = Mockito.mock(
		ActionResponse.class);
	private DeleteSynonymSetsMVCActionCommand
		_deleteSynonymSetsMVCActionCommand;
	private final HttpServletRequest _httpServletRequest = Mockito.mock(
		HttpServletRequest.class);
	private final IndexNameBuilder _indexNameBuilder = Mockito.mock(
		IndexNameBuilder.class);
	private final IndexToFilterSynchronizer _indexToFilterSynchronizer =
		Mockito.mock(IndexToFilterSynchronizer.class);

}