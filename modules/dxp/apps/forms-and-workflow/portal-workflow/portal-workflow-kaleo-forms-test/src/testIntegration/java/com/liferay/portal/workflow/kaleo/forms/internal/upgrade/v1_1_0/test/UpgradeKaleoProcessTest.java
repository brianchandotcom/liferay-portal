/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess;
import com.liferay.portal.workflow.kaleo.forms.model.KaleoProcessLink;
import com.liferay.portal.workflow.kaleo.forms.service.KaleoProcessLocalServiceUtil;
import com.liferay.portal.workflow.kaleo.forms.test.util.KaleoProcessTestUtil;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

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
		setUpUpgradeKaleoProcess();
	}

	@Test
	public void testCreateKaleoProcess() throws Exception {
		KaleoProcess kaleoProcess = KaleoProcessTestUtil.addKaleoProcess(
			_CLASS_NAME_DDL_RECORD_SET);

		_upgradeKaleoProcess.upgrade();

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

	protected void setUpUpgradeKaleoProcess() {
		Registry registry = RegistryUtil.getRegistry();

		UpgradeStepRegistrator upgradeStepRegistror = registry.getService(
			"com.liferay.portal.workflow.kaleo.forms.internal.upgrade." +
				"KaleoFormsServiceUpgrade");

		upgradeStepRegistror.register(
			new UpgradeStepRegistrator.Registry() {

				@Override
				public void register(
					String bundleSymbolicName, String fromSchemaVersionString,
					String toSchemaVersionString, UpgradeStep... upgradeSteps) {

					register(
						fromSchemaVersionString, toSchemaVersionString,
						upgradeSteps);
				}

				@Override
				public void register(
					String fromSchemaVersionString,
					String toSchemaVersionString, UpgradeStep... upgradeSteps) {

					for (UpgradeStep upgradeStep : upgradeSteps) {
						Class<?> clazz = upgradeStep.getClass();

						String className = clazz.getName();

						if (className.contains(".v1_1_0.UpgradeKaleoProcess")) {
							_upgradeKaleoProcess = (UpgradeProcess)upgradeStep;
						}
					}
				}

			});
	}

	private static final String _CLASS_NAME_DDL_RECORD_SET =
		DDLRecordSet.class.getName();

	private UpgradeProcess _upgradeKaleoProcess;

}