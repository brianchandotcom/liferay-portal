/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.model.listener.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.test.rule.Inject;
import com.liferay.template.model.TemplateEntry;
import com.liferay.template.service.TemplateEntryLocalService;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Carolina Barbosa
 */
@RunWith(Arquillian.class)
public class TemplateEntryModelListenerTest extends BaseModelListenerTestCase {

	@Override
	@Test
	public void testOnBeforeCreate() throws Exception {
		super.testOnBeforeCreate();

		TemplateEntry templateEntry =
			_templateEntryLocalService.addTemplateEntry(
				null, companyAdminUser.getUserId(), group.getGroupId(), 0,
				objectDefinition1.getClassName(), StringPool.BLANK,
				serviceContext);

		Assert.assertEquals(
			objectDefinition2.getClassName(),
			templateEntry.getInfoItemClassName());
	}

	@Inject
	private TemplateEntryLocalService _templateEntryLocalService;

}