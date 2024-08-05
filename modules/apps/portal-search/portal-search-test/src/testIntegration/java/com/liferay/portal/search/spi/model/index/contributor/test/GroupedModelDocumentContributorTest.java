/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.spi.model.index.contributor.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;

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
		Group group = _groupLocalService.getGroup(TestPropsValues.getGroupId());

		testContribute(
			blogsEntry, group.getExternalReferenceCode(),
			_GROUP_EXTERNAL_REFERENCE_CODE_FIELD_NAME);
		testContribute(
			journalArticle, group.getExternalReferenceCode(),
			_GROUP_EXTERNAL_REFERENCE_CODE_FIELD_NAME);
		testContribute(
			journalFolder, group.getExternalReferenceCode(),
			_GROUP_EXTERNAL_REFERENCE_CODE_FIELD_NAME);
	}

	@Test
	public void testContributeScopeGroupExternalReferenceCode()
		throws Exception {

		Group group = _groupLocalService.getGroup(TestPropsValues.getGroupId());

		testContribute(
			blogsEntry, group.getExternalReferenceCode(),
			_SCOPE_GROUP_EXTERNAL_REFERENCE_CODE_FIELD_NAME);
		testContribute(
			journalArticle, group.getExternalReferenceCode(),
			_SCOPE_GROUP_EXTERNAL_REFERENCE_CODE_FIELD_NAME);
		testContribute(
			journalFolder, group.getExternalReferenceCode(),
			_SCOPE_GROUP_EXTERNAL_REFERENCE_CODE_FIELD_NAME);
	}

	private static final String _GROUP_EXTERNAL_REFERENCE_CODE_FIELD_NAME =
		"groupExternalReferenceCode";

	private static final String
		_SCOPE_GROUP_EXTERNAL_REFERENCE_CODE_FIELD_NAME =
			"scopeGroupExternalReferenceCode";

	@Inject
	private GroupLocalService _groupLocalService;

}