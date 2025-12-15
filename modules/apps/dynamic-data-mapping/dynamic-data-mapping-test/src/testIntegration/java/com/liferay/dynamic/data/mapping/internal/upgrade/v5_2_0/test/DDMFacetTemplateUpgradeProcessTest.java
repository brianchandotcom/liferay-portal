/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.upgrade.v5_2_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.constants.DDMTemplateConstants;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.model.DDMTemplateVersion;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Yuri Monteiro
 */
@RunWith(Arquillian.class)
public class DDMFacetTemplateUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testUpgrade() throws Exception {
		Group group = GroupTestUtil.addGroup();

		String oldClassName =
			"com.liferay.portal.search.web.internal.custom.facet.display." +
				"context.CustomFacetTermDisplayContext";

		long oldClassNameId = _classNameLocalService.getClassNameId(
			oldClassName);

		long resourceClassNameId = _classNameLocalService.getClassNameId(
			"com.liferay.portlet.display.template.PortletDisplayTemplate");

		DDMTemplate ddmTemplate = _ddmTemplateLocalService.addTemplate(
			null, TestPropsValues.getUserId(), group.getGroupId(),
			oldClassNameId, 0, resourceClassNameId,
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY,
			DDMTemplateConstants.TEMPLATE_MODE_EDIT, "freemarker",
			RandomTestUtil.randomString(),
			ServiceContextTestUtil.getServiceContext(group.getGroupId()));

		Assert.assertEquals(oldClassNameId, ddmTemplate.getClassNameId());

		DDMTemplateVersion ddmTemplateVersion =
			ddmTemplate.getTemplateVersion();

		Assert.assertEquals(
			oldClassNameId, ddmTemplateVersion.getClassNameId());

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_CLASS_NAME, LoggerTestUtil.OFF)) {

			UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
				_upgradeStepRegistrator, _CLASS_NAME);

			upgradeProcess.upgrade();

			_entityCache.clearCache();
		}

		String newClassName =
			"com.liferay.portal.search.web.internal.custom.facet.portlet." +
				"CustomFacetPortlet";

		long newClassNameId = _classNameLocalService.getClassNameId(
			newClassName);

		ddmTemplate = _ddmTemplateLocalService.getDDMTemplate(
			ddmTemplate.getTemplateId());

		Assert.assertEquals(newClassNameId, ddmTemplate.getClassNameId());

		ddmTemplateVersion = ddmTemplate.getTemplateVersion();

		Assert.assertEquals(
			newClassNameId, ddmTemplateVersion.getClassNameId());
	}

	private static final String _CLASS_NAME =
		"com.liferay.dynamic.data.mapping.internal.upgrade.v5_2_0." +
			"DDMFacetTemplateUpgradeProcess";

	@Inject(
		filter = "(&(component.name=com.liferay.dynamic.data.mapping.internal.upgrade.registry.DDMServiceUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	@Inject
	private ClassNameLocalService _classNameLocalService;

	@Inject
	private DDMTemplateLocalService _ddmTemplateLocalService;

	@Inject
	private EntityCache _entityCache;

}