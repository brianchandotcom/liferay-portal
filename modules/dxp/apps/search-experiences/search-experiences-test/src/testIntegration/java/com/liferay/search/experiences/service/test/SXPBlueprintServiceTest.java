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

package com.liferay.search.experiences.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.workflow.WorkflowThreadLocal;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.search.experiences.model.SXPBlueprint;
import com.liferay.search.experiences.rest.dto.v1_0.FrameworkConfiguration;
import com.liferay.search.experiences.rest.dto.v1_0.SXPBlueprintConfiguration;
import com.liferay.search.experiences.service.SXPBlueprintService;

import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author André de Oliveira
 */
@RunWith(Arquillian.class)
public class SXPBlueprintServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testSXPBlueprint() throws Exception {
		WorkflowThreadLocal.setEnabled(false);

		SXPBlueprintConfiguration sxpBlueprintConfiguration1 =
			new SXPBlueprintConfiguration();

		FrameworkConfiguration frameworkConfiguration1 =
			new FrameworkConfiguration();

		sxpBlueprintConfiguration1.setFrameworkConfiguration(
			frameworkConfiguration1);

		frameworkConfiguration1.setApplyIndexerClauses(true);

		SXPBlueprint sxpBlueprint1 = _sxpBlueprintService.addSXPBlueprint(
			sxpBlueprintConfiguration1.toString(), _toMap("Description"), null,
			_toMap("Title"), ServiceContextTestUtil.getServiceContext());

		SXPBlueprint sxpBlueprint2 = _sxpBlueprintService.getSXPBlueprint(
			sxpBlueprint1.getSXPBlueprintId());

		SXPBlueprintConfiguration sxpBlueprintConfiguration2 =
			SXPBlueprintConfiguration.toDTO(
				sxpBlueprint2.getConfigurationsJSON());

		FrameworkConfiguration frameworkConfiguration2 =
			sxpBlueprintConfiguration2.getFrameworkConfiguration();

		Assert.assertTrue(frameworkConfiguration2.getApplyIndexerClauses());

		frameworkConfiguration2.setApplyIndexerClauses(false);

		_sxpBlueprintService.updateSXPBlueprint(
			sxpBlueprint2.getSXPBlueprintId(),
			sxpBlueprintConfiguration2.toString(),
			sxpBlueprint2.getDescriptionMap(), null,
			sxpBlueprint2.getTitleMap(),
			ServiceContextTestUtil.getServiceContext());

		SXPBlueprint sxpBlueprint3 = _sxpBlueprintService.getSXPBlueprint(
			sxpBlueprint2.getSXPBlueprintId());

		SXPBlueprintConfiguration sxpBlueprintConfiguration3 =
			SXPBlueprintConfiguration.toDTO(
				sxpBlueprint3.getConfigurationsJSON());

		FrameworkConfiguration frameworkConfiguration3 =
			sxpBlueprintConfiguration3.getFrameworkConfiguration();

		Assert.assertFalse(frameworkConfiguration3.getApplyIndexerClauses());
	}

	private Map<Locale, String> _toMap(String string) {
		return HashMapBuilder.put(
			LocaleUtil.getDefault(), string
		).build();
	}

	@Inject
	private SXPBlueprintService _sxpBlueprintService;

}