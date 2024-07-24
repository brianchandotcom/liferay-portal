/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.upgrade.v9_2_2.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.definition.util.ObjectDefinitionUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Pedro Tavares
 */
@RunWith(Arquillian.class)
public class ObjectDefinitionUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@After
	public void tearDown() throws Exception {
		_companyLocalService.forEachCompany(
			company -> _restoreExternalReferenceCodeValues(
				company.getCompanyId()));
	}

	@Test
	public void testUpgrade() throws Exception {
		for (ObjectDefinition objectDefinition :
				_objectDefinitionLocalService.
					getUnmodifiableSystemObjectDefinitions(
						TestPropsValues.getCompanyId())) {

			_objectDefinitionLocalService.updateExternalReferenceCode(
				objectDefinition.getObjectDefinitionId(),
				RandomTestUtil.randomString());
		}

		UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
			_upgradeStepRegistrator,
			"com.liferay.object.internal.upgrade.v9_2_2." +
				"ObjectDefinitionUpgradeProcess");

		upgradeProcess.upgrade();

		_multiVMPool.clear();

		for (ObjectDefinition objectDefinition :
				_objectDefinitionLocalService.
					getUnmodifiableSystemObjectDefinitions(
						TestPropsValues.getCompanyId())) {

			Assert.assertEquals(
				ObjectDefinitionUtil.
					getUnmodifiableSystemObjectDefinitionExternalReferenceCode(
						objectDefinition.getName()),
				objectDefinition.getExternalReferenceCode());
		}
	}

	private void _restoreExternalReferenceCodeValues(long companyId)
		throws PortalException {

		for (ObjectDefinition objectDefinition :
				_objectDefinitionLocalService.
					getUnmodifiableSystemObjectDefinitions(companyId)) {

			String externalReferenceCode =
				ObjectDefinitionUtil.
					getUnmodifiableSystemObjectDefinitionExternalReferenceCode(
						objectDefinition.getName());

			if (StringUtil.equals(
					externalReferenceCode,
					objectDefinition.getExternalReferenceCode())) {

				continue;
			}

			_objectDefinitionLocalService.updateExternalReferenceCode(
				objectDefinition.getObjectDefinitionId(),
				externalReferenceCode);
		}
	}

	@Inject
	private static ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject(
		filter = "component.name=com.liferay.object.internal.upgrade.registry.ObjectServiceUpgradeStepRegistrator"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private MultiVMPool _multiVMPool;

}