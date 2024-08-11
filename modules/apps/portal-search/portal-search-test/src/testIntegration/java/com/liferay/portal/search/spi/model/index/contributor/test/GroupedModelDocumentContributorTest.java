/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.spi.model.index.contributor.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Joshua Cords
 */
@RunWith(Arquillian.class)
public class GroupedModelDocumentContributorTest
	extends BaseModelDocumentContributorTest {

	@Test
	public void testContributeGroupExternalReferenceCode() throws Exception {
		testContribute(
			blogsEntry, group.getExternalReferenceCode(),
			"groupExternalReferenceCode");
		testContribute(
			journalArticle, group.getExternalReferenceCode(),
			"groupExternalReferenceCode");
		testContribute(
			journalFolder, group.getExternalReferenceCode(),
			"groupExternalReferenceCode");
	}

	@Test
	public void testContributeScopeGroupExternalReferenceCode()
		throws Exception {

		testContribute(
			blogsEntry, group.getExternalReferenceCode(),
			"scopeGroupExternalReferenceCode");
		testContribute(
			journalArticle, group.getExternalReferenceCode(),
			"scopeGroupExternalReferenceCode");
		testContribute(
			journalFolder, group.getExternalReferenceCode(),
			"scopeGroupExternalReferenceCode");
	}

}