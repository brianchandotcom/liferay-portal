/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.wiki.uad.entity;

import com.liferay.user.associated.data.entity.UADEntity;
import com.liferay.user.associated.data.test.util.BaseUADEntityTestCase;

import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.uad.constants.WikiUADConstants;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
public class WikiPageUADEntityTest extends BaseUADEntityTestCase {
	@Before
	@Override
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		super.setUp();
	}

	@Test
	public void testGetWikiPage() throws Exception {
		WikiPageUADEntity wikiPageUADEntity = (WikiPageUADEntity)uadEntity;

		Assert.assertEquals(_wikiPage, wikiPageUADEntity.getWikiPage());
	}

	@Override
	protected UADEntity createUADEntity(long userId, String uadEntityId) {
		return new WikiPageUADEntity(userId, uadEntityId, _wikiPage);
	}

	@Override
	protected String getUADRegistryKey() {
		return WikiUADConstants.CLASS_NAME_WIKI_PAGE;
	}

	@Mock
	private WikiPage _wikiPage;
}