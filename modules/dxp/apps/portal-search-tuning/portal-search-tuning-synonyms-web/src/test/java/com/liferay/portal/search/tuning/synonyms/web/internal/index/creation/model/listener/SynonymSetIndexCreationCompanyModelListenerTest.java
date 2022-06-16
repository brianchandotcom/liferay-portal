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

package com.liferay.portal.search.tuning.synonyms.web.internal.index.creation.model.listener;

import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.tuning.synonyms.index.name.SynonymSetIndexNameBuilder;
import com.liferay.portal.search.tuning.synonyms.web.internal.BaseSynonymsWebTestCase;
import com.liferay.portal.search.tuning.synonyms.web.internal.index.SynonymSetIndexCreator;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Wade Cao
 */
public class SynonymSetIndexCreationCompanyModelListenerTest
	extends BaseSynonymsWebTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_synonymSetIndexCreationCompanyModelListener =
			new SynonymSetIndexCreationCompanyModelListener();

		ReflectionTestUtil.setFieldValue(
			_synonymSetIndexCreationCompanyModelListener,
			"_synonymSetIndexCreator", _synonymSetIndexCreator);
		ReflectionTestUtil.setFieldValue(
			_synonymSetIndexCreationCompanyModelListener,
			"_synonymSetIndexNameBuilder", _synonymSetIndexNameBuilder);
		ReflectionTestUtil.setFieldValue(
			_synonymSetIndexCreationCompanyModelListener,
			"_synonymSetIndexReader", synonymSetIndexReader);
	}

	@Test
	public void testOnAfterCreateWithIndexReaderFalse() {
		setUpSynonymSetIndexReader(false);

		_synonymSetIndexCreationCompanyModelListener.onAfterCreate(
			Mockito.mock(Company.class));

		Mockito.verify(
			_synonymSetIndexCreator, Mockito.times(1)
		).create(
			Mockito.any()
		);
	}

	@Test
	public void testOnAfterCreateWithIndexReaderTrue() {
		setUpSynonymSetIndexReader(true);

		_synonymSetIndexCreationCompanyModelListener.onAfterCreate(
			Mockito.mock(Company.class));

		Mockito.verify(
			_synonymSetIndexCreator, Mockito.never()
		).create(
			Mockito.any()
		);
	}

	@Test
	public void testOnBeforeRemoveWithIndexReaderFalse() {
		setUpSynonymSetIndexReader(false);

		_synonymSetIndexCreationCompanyModelListener.onBeforeRemove(
			Mockito.mock(Company.class));

		Mockito.verify(
			_synonymSetIndexCreator, Mockito.never()
		).delete(
			Mockito.any()
		);

		setUpSynonymSetIndexReader(true);
	}

	@Test
	public void testOnBeforeRemoveWithIndexReaderTrue() {
		setUpSynonymSetIndexReader(true);

		_synonymSetIndexCreationCompanyModelListener.onBeforeRemove(
			Mockito.mock(Company.class));

		Mockito.verify(
			_synonymSetIndexCreator, Mockito.times(1)
		).delete(
			Mockito.any()
		);
	}

	private SynonymSetIndexCreationCompanyModelListener
		_synonymSetIndexCreationCompanyModelListener;
	private final SynonymSetIndexCreator _synonymSetIndexCreator = Mockito.mock(
		SynonymSetIndexCreator.class);
	private final SynonymSetIndexNameBuilder _synonymSetIndexNameBuilder =
		Mockito.mock(SynonymSetIndexNameBuilder.class);

}