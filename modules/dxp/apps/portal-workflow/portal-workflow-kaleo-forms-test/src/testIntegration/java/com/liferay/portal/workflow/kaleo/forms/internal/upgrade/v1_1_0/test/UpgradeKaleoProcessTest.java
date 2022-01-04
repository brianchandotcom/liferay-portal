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

package com.liferay.portal.workflow.kaleo.forms.internal.upgrade.v1_1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess;
import com.liferay.portal.workflow.kaleo.forms.model.KaleoProcessLink;
import com.liferay.portal.workflow.kaleo.forms.service.KaleoProcessLocalServiceUtil;
import com.liferay.portal.workflow.kaleo.forms.test.util.KaleoProcessTestUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Inácio Nery
 */
@RunWith(Arquillian.class)
public class UpgradeKaleoProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_setUpUpgradeKaleoProcess();
	}

	@Test
	public void testCreateKaleoProcess() throws Exception {
		KaleoProcess kaleoProcess = KaleoProcessTestUtil.addKaleoProcess(
			_CLASS_NAME_DDL_RECORD_SET);

		_kaleoProcessUpgradeProcess.upgrade();

		EntityCacheUtil.clearCache();

		kaleoProcess = KaleoProcessLocalServiceUtil.getKaleoProcess(
			kaleoProcess.getKaleoProcessId());

		DDLRecordSet ddlRecordSet = kaleoProcess.getDDLRecordSet();

		DDMStructure ddmStructure = ddlRecordSet.getDDMStructure();

		long kaleoProcessClassNameId = PortalUtil.getClassNameId(
			KaleoProcess.class.getName());

		Assert.assertEquals(
			kaleoProcessClassNameId, ddmStructure.getClassNameId());

		DDMTemplate ddmTemplate = kaleoProcess.getDDMTemplate();

		Assert.assertEquals(
			kaleoProcessClassNameId, ddmTemplate.getResourceClassNameId());

		for (KaleoProcessLink kaleoProcessLink :
				kaleoProcess.getKaleoProcessLinks()) {

			ddmTemplate = DDMTemplateLocalServiceUtil.getDDMTemplate(
				kaleoProcessLink.getDDMTemplateId());

			Assert.assertEquals(
				kaleoProcessClassNameId, ddmTemplate.getResourceClassNameId());
		}
	}

	private void _setUpUpgradeKaleoProcess() {
		_upgradeStepRegistrator.register(
			new UpgradeStepRegistrator.Registry() {

				@Override
				public void register(
					String fromSchemaVersionString,
					String toSchemaVersionString, UpgradeStep... upgradeSteps) {

					for (UpgradeStep upgradeStep : upgradeSteps) {
						Class<?> clazz = upgradeStep.getClass();

						String className = clazz.getName();

						if (className.contains(
								".v1_1_0.KaleoProcessUpgradeProcess")) {

							_kaleoProcessUpgradeProcess =
								(UpgradeProcess)upgradeStep;
						}
					}
				}

				@Override
				public void registerInitialUpgradeSteps(
					UpgradeStep... upgradeSteps) {
				}

			});
	}

	private static final String _CLASS_NAME_DDL_RECORD_SET =
		DDLRecordSet.class.getName();

	private UpgradeProcess _kaleoProcessUpgradeProcess;

	@Inject(
		filter = "component.name=com.liferay.portal.workflow.kaleo.forms.internal.upgrade.KaleoFormsServiceUpgrade"
	)
	private UpgradeStepRegistrator _upgradeStepRegistrator;

}