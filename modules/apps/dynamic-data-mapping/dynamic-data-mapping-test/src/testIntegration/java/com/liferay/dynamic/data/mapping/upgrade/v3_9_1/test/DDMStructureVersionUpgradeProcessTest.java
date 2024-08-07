/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.upgrade.v3_9_1.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.service.DDMStructureVersionLocalService;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.service.change.tracking.CTService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Pedro Leite
 */
@RunWith(Arquillian.class)
public class DDMStructureVersionUpgradeProcessTest
	extends DDMStructureUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Override
	protected CTModel<?> addCTModel() throws Exception {
		DDMStructure ddmStructure = (DDMStructure)super.addCTModel();

		return ddmStructure.getStructureVersion();
	}

	@Override
	protected CTService<?> getCTService() {
		return _ddmStructureVersionLocalService;
	}

	@Override
	protected CTModel<?> updateCTModel(CTModel<?> ctModel) throws Exception {
		DDMStructureVersion ddmStructureVersion = (DDMStructureVersion)ctModel;

		DDMStructure ddmStructure = ddmStructureVersion.getStructure();

		setDefinition(ddmStructure);

		ddmStructureVersion.setDefinition(ddmStructure.getDefinition());

		return _ddmStructureVersionLocalService.updateDDMStructureVersion(
			ddmStructureVersion);
	}

	@Inject
	private DDMStructureVersionLocalService _ddmStructureVersionLocalService;

}