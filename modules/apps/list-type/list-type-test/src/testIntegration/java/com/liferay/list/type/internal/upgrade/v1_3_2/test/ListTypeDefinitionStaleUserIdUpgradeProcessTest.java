/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.list.type.internal.upgrade.v1_3_2.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.list.type.entry.util.ListTypeEntryUtil;
import com.liferay.list.type.service.ListTypeDefinitionLocalService;
import com.liferay.portal.kernel.model.AuditedModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.test.BaseStaleUserIdUpgradeProcessTestCase;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

import java.util.Collections;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Igor Costa
 */
@RunWith(Arquillian.class)
public class ListTypeDefinitionStaleUserIdUpgradeProcessTest
	extends BaseStaleUserIdUpgradeProcessTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Override
	protected AuditedModel addAuditedModel(User user) throws Exception {
		return _listTypeDefinitionLocalService.addListTypeDefinition(
			null, user.getUserId(),
			Collections.singletonMap(
				LocaleUtil.US, RandomTestUtil.randomString()),
			false,
			Collections.singletonList(
				ListTypeEntryUtil.createListTypeEntry(
					RandomTestUtil.randomString())),
			new ServiceContext());
	}

	@Override
	protected PersistedModelLocalService getPersistedModelLocalService() {
		return _listTypeDefinitionLocalService;
	}

	@Override
	protected void runUpgrade() throws Exception {
		UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
			_upgradeStepRegistrator,
			"com.liferay.list.type.internal.upgrade.v1_3_2." +
				"ListTypeDefinitionStaleUserIdUpgradeProcess");

		upgradeProcess.upgrade();
	}

	@Override
	protected void updateUserId(AuditedModel auditedModel, long userId)
		throws Exception {

		_listTypeDefinitionLocalService.updateUserId(
			auditedModel.getCompanyId(), auditedModel.getUserId(), userId);
	}

	@Inject
	private ListTypeDefinitionLocalService
		_listTypeDefinitionLocalService;

	@Inject(
		filter = "component.name=com.liferay.list.type.internal.upgrade.registry.ListTypeServiceUpgradeStepRegistrator"
	)
	private UpgradeStepRegistrator _upgradeStepRegistrator;

}