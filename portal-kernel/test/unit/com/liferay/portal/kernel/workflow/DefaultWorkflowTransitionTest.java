/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
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
public class DefaultWorkflowTransitionTest {

	@Test
	public void testGetLabel() {
		LanguageUtil languageUtil = new LanguageUtil();

		Language language = Mockito.mock(Language.class);

		Mockito.when(
			language.get(LocaleUtil.BRAZIL, "approve", null)
		).thenReturn(
			"Aprovar"
		);

		Mockito.when(
			language.get(LocaleUtil.getSiteDefault(), "proceed")
		).thenReturn(
			"Proceed"
		);

		languageUtil.setLanguage(language);

		DefaultWorkflowTransition defaultWorkflowTransition =
			new DefaultWorkflowTransition();

		// Desired locale, label map

		defaultWorkflowTransition.setLabelMap(
			HashMapBuilder.put(
				LocaleUtil.BRAZIL, "Revisar Regra de Negócio"
			).build());
		defaultWorkflowTransition.setName("businessRuleReview");

		Assert.assertEquals(
			"Revisar Regra de Negócio",
			defaultWorkflowTransition.getLabel(LocaleUtil.BRAZIL));

		// Desired locale, name as language key

		defaultWorkflowTransition.setLabelMap(new HashMap<>());
		defaultWorkflowTransition.setName("approve");

		Assert.assertEquals(
			"Aprovar", defaultWorkflowTransition.getLabel(LocaleUtil.BRAZIL));

		// Label map empty

		defaultWorkflowTransition.setLabelMap(new HashMap<>());
		defaultWorkflowTransition.setName("businessRuleReview");

		Assert.assertEquals(
			"businessRuleReview",
			defaultWorkflowTransition.getLabel(LocaleUtil.BRAZIL));

		// Label map null

		defaultWorkflowTransition.setLabelMap(null);
		defaultWorkflowTransition.setName("businessRuleReview");

		Assert.assertEquals(
			"businessRuleReview",
			defaultWorkflowTransition.getLabel(LocaleUtil.BRAZIL));

		// Label map with site default locale

		defaultWorkflowTransition.setLabelMap(
			HashMapBuilder.put(
				LocaleUtil.getSiteDefault(), "Business Rule Review"
			).build());
		defaultWorkflowTransition.setName("businessRuleReview");

		Assert.assertEquals(
			"Business Rule Review",
			defaultWorkflowTransition.getLabel(LocaleUtil.BRAZIL));

		// Name null

		defaultWorkflowTransition.setLabelMap(new HashMap<>());
		defaultWorkflowTransition.setName(null);

		Assert.assertEquals(
			"Proceed",
			defaultWorkflowTransition.getLabel(LocaleUtil.getSiteDefault()));
	}

}