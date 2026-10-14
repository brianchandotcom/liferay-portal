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
public class DateFacetTemplateUpgradeProcessTest {

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
		if (_templateIds.isEmpty()) {
			return;
		}

		String templateIdsString = StringUtil.merge(_templateIds);

		_db.runSQL(
			StringBundler.concat(
				"delete from ResourcePermission where name = '",
				DDMTemplate.class.getName(), "' and primKeyId in (",
				templateIdsString, ")"));
		_db.runSQL(
			"delete from DDMTemplateVersion where templateId in (" +
				templateIdsString + ")");
		_db.runSQL(
			"delete from DDMTemplate where templateId in (" +
				templateIdsString + ")");
	}

	@Test
	public void testUpgrade() throws Exception {
		long checkboxTemplateId = _addTemplate("DATE-FACET-CHECKBOX-FTL");
		long customTemplateId = _addTemplate(RandomTestUtil.randomString());
		long linkTemplateId = _addTemplate("DATE-FACET-LINK-FTL");
		long radioTemplateId = _addTemplate("DATE-FACET-RADIO-FTL");

		_assertTemplateRows(true, checkboxTemplateId);
		_assertTemplateRows(true, linkTemplateId);
		_assertTemplateRows(true, radioTemplateId);

		UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
			_upgradeStepRegistrator,
			"com.liferay.portal.search.web.internal.upgrade.v2_1_2." +
				"DateFacetTemplateUpgradeProcess");

		upgradeProcess.upgrade();

		_assertTemplateRows(false, checkboxTemplateId);
		_assertTemplateRows(false, linkTemplateId);
		_assertTemplateRows(false, radioTemplateId);
		_assertTemplateRows(true, customTemplateId);
	}

	private long _addTemplate(String templateKey) throws Exception {
		DDMTemplate ddmTemplate = DDMTemplateTestUtil.addTemplate(
			TestPropsValues.getGroupId(), _classNameId, 0, _resourceClassNameId,
			templateKey, TemplateConstants.LANG_TYPE_FTL,
			RandomTestUtil.randomString(), LocaleUtil.getSiteDefault());

		_templateIds.add(ddmTemplate.getTemplateId());

		return ddmTemplate.getTemplateId();
	}

	private void _assertTemplateRows(boolean exists, long templateId)
		throws Exception {

		Assert.assertEquals(
			exists,
			_hasRows(
				"select templateId from DDMTemplate where templateId = " +
					templateId));
		Assert.assertEquals(
			exists,
			_hasRows(
				"select templateVersionId from DDMTemplateVersion where " +
					"templateId = " + templateId));
		Assert.assertEquals(
			exists,
			_hasRows(
				StringBundler.concat(
					"select resourcePermissionId from ResourcePermission ",
					"where name = '", DDMTemplate.class.getName(),
					"' and primKeyId = ", templateId)));
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
	private long _resourceClassNameId;
	private final List<Long> _templateIds = new ArrayList<>();

	@Inject(
		filter = "component.name=com.liferay.portal.search.web.internal.upgrade.registry.SearchWebUpgradeStepRegistrator"
	)
	private UpgradeStepRegistrator _upgradeStepRegistrator;

}