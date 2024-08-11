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
public class ExternalReferenceCodeModelDocumentContributorTest
	extends BaseModelDocumentContributorTest {

	@Test
	public void testContribute() throws Exception {
		testContribute(blogsEntry, "externalReferenceCode");
		testContribute(journalArticle, "externalReferenceCode");
		testContribute(journalFolder, "externalReferenceCode");
		testContribute(user, "externalReferenceCode");
	}

}