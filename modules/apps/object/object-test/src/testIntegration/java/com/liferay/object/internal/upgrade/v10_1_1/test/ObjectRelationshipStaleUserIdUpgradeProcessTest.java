/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.upgrade.v10_1_1.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.object.test.util.ObjectRelationshipTestUtil;
import com.liferay.portal.kernel.model.AuditedModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.test.BaseStaleUserIdUpgradeProcessTestCase;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Igor Costa
 */
@RunWith(Arquillian.class)
public class ObjectRelationshipStaleUserIdUpgradeProcessTest
	extends BaseStaleUserIdUpgradeProcessTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Override
	protected AuditedModel addAuditedModel(User user) throws Exception {
		return ObjectRelationshipTestUtil.addObjectRelationship(
			_objectRelationshipLocalService,
			ObjectDefinitionTestUtil.addCustomObjectDefinition(
				ObjectDefinitionTestUtil.getRandomName(), user.getUserId()),
			ObjectDefinitionTestUtil.addCustomObjectDefinition(
				ObjectDefinitionTestUtil.getRandomName(), user.getUserId()),
			user.getUserId());
	}

	@Override
	protected PersistedModelLocalService getPersistedModelLocalService() {
		return _objectRelationshipLocalService;
	}

	@Override
	protected void runUpgrade() throws Exception {
		UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
			_upgradeStepRegistrator,
			"com.liferay.object.internal.upgrade.v10_1_1." +
				"ObjectRelationshipStaleUserIdUpgradeProcess");

		upgradeProcess.upgrade();
	}

	@Override
	protected void updateUserId(AuditedModel auditedModel, long userId)
		throws Exception {

		_objectRelationshipLocalService.updateUserId(
			auditedModel.getCompanyId(), auditedModel.getUserId(), userId);
	}

	@Inject
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

	@Inject(
		filter = "component.name=com.liferay.object.internal.upgrade.registry.ObjectServiceUpgradeStepRegistrator"
	)
	private UpgradeStepRegistrator _upgradeStepRegistrator;

}