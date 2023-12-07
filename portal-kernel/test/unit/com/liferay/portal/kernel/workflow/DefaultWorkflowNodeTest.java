/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.workflow;

import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.HashMap;

import org.junit.Assert;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Feliphe Marinho
 * @author Paulo Albuquerque
 */
public class DefaultWorkflowNodeTest {

	@Test
	public void testGetLabel() {
		LanguageUtil languageUtil = new LanguageUtil();

		Language language = Mockito.mock(Language.class);

		Mockito.when(
			language.get(LocaleUtil.BRAZIL, "approve", null)
		).thenReturn(
			"Aprovar"
		);

		languageUtil.setLanguage(language);

		DefaultWorkflowNode defaultWorkflowNode = new DefaultWorkflowNode();

		defaultWorkflowNode.setLabelMap(new HashMap<>());
		defaultWorkflowNode.setName("approve");

		Assert.assertEquals(
			"Aprovar", defaultWorkflowNode.getLabel(LocaleUtil.BRAZIL));

		defaultWorkflowNode.setLabelMap(new HashMap<>());
		defaultWorkflowNode.setName("businessRuleReview");

		Assert.assertEquals(
			"businessRuleReview",
			defaultWorkflowNode.getLabel(LocaleUtil.BRAZIL));

		defaultWorkflowNode.setLabelMap(
			HashMapBuilder.put(
				LocaleUtil.getSiteDefault(), "Business Rule Review"
			).build());
		defaultWorkflowNode.setName("businessRuleReview");

		Assert.assertEquals(
			"Business Rule Review",
			defaultWorkflowNode.getLabel(LocaleUtil.BRAZIL));

		defaultWorkflowNode.setLabelMap(
			HashMapBuilder.put(
				LocaleUtil.BRAZIL, "Revisar Regra de Negócio"
			).build());
		defaultWorkflowNode.setName("businessRuleReview");

		Assert.assertEquals(
			"Revisar Regra de Negócio",
			defaultWorkflowNode.getLabel(LocaleUtil.BRAZIL));
	}

}