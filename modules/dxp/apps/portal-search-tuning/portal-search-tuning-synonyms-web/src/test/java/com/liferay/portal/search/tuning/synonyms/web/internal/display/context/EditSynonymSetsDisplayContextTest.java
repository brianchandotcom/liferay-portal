/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.synonyms.web.internal.display.context;

import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.HashMap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Wade Cao
 */
public class EditSynonymSetsDisplayContextTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_editSynonymSetsDisplayContext = new EditSynonymSetsDisplayContext();
	}

	@Test
	public void testGetterSetter() {
		_editSynonymSetsDisplayContext.setBackURL("backURL");
		_editSynonymSetsDisplayContext.setData(new HashMap<String, Object>());
		_editSynonymSetsDisplayContext.setFormName("formName");
		_editSynonymSetsDisplayContext.setInputName("inputName");
		_editSynonymSetsDisplayContext.setOriginalInputName(
			"originalInputName");
		_editSynonymSetsDisplayContext.setRedirect("redirect");
		_editSynonymSetsDisplayContext.setSynonymSetId("synonymSetId");

		Assert.assertEquals(
			"backURL", _editSynonymSetsDisplayContext.getBackURL());
		Assert.assertNotNull(_editSynonymSetsDisplayContext.getData());
		Assert.assertEquals(
			"formName", _editSynonymSetsDisplayContext.getFormName());
		Assert.assertEquals(
			"inputName", _editSynonymSetsDisplayContext.getInputName());
		Assert.assertEquals(
			"originalInputName",
			_editSynonymSetsDisplayContext.getOriginalInputName());
		Assert.assertEquals(
			"redirect", _editSynonymSetsDisplayContext.getRedirect());
		Assert.assertEquals(
			"synonymSetId", _editSynonymSetsDisplayContext.getSynonymSetId());
	}

	private EditSynonymSetsDisplayContext _editSynonymSetsDisplayContext;

}