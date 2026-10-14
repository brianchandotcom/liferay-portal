/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.upgrade.v2_1_2.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.test.util.DDMTemplateTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Selena Aungst
 */
@RunWith(Arquillian.class)
public class DateFacetDDMTemplateUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_classNameId = _classNameLocalService.getClassNameId(
			"com.liferay.portal.search.web.internal.date.facet.portlet." +
				"DateFacetPortlet");
		_db = DBManagerUtil.getDB();
		_resourceClassNameId = _classNameLocalService.getClassNameId(
			"com.liferay.portlet.display.template.PortletDisplayTemplate");
	}

	@After
	public void tearDown() throws Exception {
		if (_ddmTemplateIds.isEmpty()) {
			return;
		}

		String ddmTemplateIdsString = StringUtil.merge(_ddmTemplateIds);

		_db.runSQL(
			StringBundler.concat(
				"delete from ResourcePermission where name = '",
				DDMTemplate.class.getName(), "' and primKeyId in (",
				ddmTemplateIdsString, ")"));
		_db.runSQL(
			"delete from DDMTemplateVersion where templateId in (" +
				ddmTemplateIdsString + ")");
		_db.runSQL(
			"delete from DDMTemplate where templateId in (" +
				ddmTemplateIdsString + ")");
	}

	@Test
	public void testUpgrade() throws Exception {
		long checkboxDDMTemplateId = _addDDMTemplate("DATE-FACET-CHECKBOX-FTL");
		long customDDMTemplateId = _addDDMTemplate(
			RandomTestUtil.randomString());
		long linkDDMTemplateId = _addDDMTemplate("DATE-FACET-LINK-FTL");
		long radioDDMTemplateId = _addDDMTemplate("DATE-FACET-RADIO-FTL");

		_assertDDMTemplateRows(true, checkboxDDMTemplateId);
		_assertDDMTemplateRows(true, linkDDMTemplateId);
		_assertDDMTemplateRows(true, radioDDMTemplateId);

		UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
			_upgradeStepRegistrator,
			"com.liferay.portal.search.web.internal.upgrade.v2_1_2." +
				"DateFacetDDMTemplateUpgradeProcess");

		upgradeProcess.upgrade();

		_assertDDMTemplateRows(false, checkboxDDMTemplateId);
		_assertDDMTemplateRows(false, linkDDMTemplateId);
		_assertDDMTemplateRows(false, radioDDMTemplateId);
		_assertDDMTemplateRows(true, customDDMTemplateId);
	}

	private long _addDDMTemplate(String templateKey) throws Exception {
		DDMTemplate ddmTemplate = DDMTemplateTestUtil.addTemplate(
			TestPropsValues.getGroupId(), _classNameId, 0, _resourceClassNameId,
			templateKey, TemplateConstants.LANG_TYPE_FTL,
			RandomTestUtil.randomString(), LocaleUtil.getSiteDefault());

		_ddmTemplateIds.add(ddmTemplate.getTemplateId());

		return ddmTemplate.getTemplateId();
	}

	private void _assertDDMTemplateRows(boolean exists, long ddmTemplateId)
		throws Exception {

		Assert.assertEquals(
			exists,
			_hasRows(
				"select templateId from DDMTemplate where templateId = " +
					ddmTemplateId));
		Assert.assertEquals(
			exists,
			_hasRows(
				"select templateVersionId from DDMTemplateVersion where " +
					"templateId = " + ddmTemplateId));
		Assert.assertEquals(
			exists,
			_hasRows(
				StringBundler.concat(
					"select resourcePermissionId from ResourcePermission ",
					"where name = '", DDMTemplate.class.getName(),
					"' and primKeyId = ", ddmTemplateId)));
	}

	private boolean _hasRows(String sql) throws Exception {
		try (Connection connection = DataAccess.getConnection();

			PreparedStatement preparedStatement = connection.prepareStatement(
				sql);

			ResultSet resultSet = preparedStatement.executeQuery()) {

			return resultSet.next();
		}
	}

	private long _classNameId;

	@Inject
	private ClassNameLocalService _classNameLocalService;

	private DB _db;
	private final List<Long> _ddmTemplateIds = new ArrayList<>();
	private long _resourceClassNameId;

	@Inject(
		filter = "component.name=com.liferay.portal.search.web.internal.upgrade.registry.SearchWebUpgradeStepRegistrator"
	)
	private UpgradeStepRegistrator _upgradeStepRegistrator;

}