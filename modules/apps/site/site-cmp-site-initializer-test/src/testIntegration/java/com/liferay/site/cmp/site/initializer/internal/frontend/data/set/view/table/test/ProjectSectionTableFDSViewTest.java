/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cmp.site.initializer.internal.frontend.data.set.view.table.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.frontend.data.set.view.FDSView;
import com.liferay.frontend.data.set.view.FDSViewRegistry;
import com.liferay.frontend.data.set.view.table.DateFDSTableSchemaField;
import com.liferay.frontend.data.set.view.table.FDSTableSchema;
import com.liferay.frontend.data.set.view.table.FDSTableSchemaField;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.skyscreamer.jsonassert.JSONAssert;

/**
 * @author Carolina Barbosa
 */
@FeatureFlags(
	featureFlags = {@FeatureFlag("LPD-17564"), @FeatureFlag("LPD-58677")}
)
@RunWith(Arquillian.class)
public class ProjectSectionTableFDSViewTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		List<FDSView> fdsViews = _fdsViewRegistry.getFDSViews(
			"com.liferay.site.cmp.site.initializer-project");

		FDSView fdsView = fdsViews.get(0);

		FDSTableSchema fdsTableSchema = fdsView.getFDSTableSchema(
			LocaleUtil.US);

		_fdsTableSchemaFieldsMap = fdsTableSchema.getFDSTableSchemaFieldsMap();
	}

	@Test
	public void testGetFDSTableSchema() throws Exception {
		_assertFDSTableSchemaField(
			null, "progressBarTableCellRenderer", "completion-rate",
			"embedded.completionRate");
		_assertFDSTableSchemaField(
			null, "date", "due-date", "embedded.dueDate");
		_assertFDSTableSchemaField(
			null, "userRelationshipTableCellRenderer", "manager",
			"embedded.r_userToCMPProjectManager_userERC");
		_assertFDSTableSchemaField(
			null, "userRelationshipTableCellRenderer", "sponsor",
			"embedded.r_userToCMPProjectSponsor_userERC");
		_assertFDSTableSchemaField(
			null, "stateTableCellRenderer", "state", "embedded.state");
		_assertFDSTableSchemaField(
			"actionLink", "simpleActionLinkTableCellRenderer", "title",
			"embedded.title");
	}

	private void _assertFDSTableSchemaField(
			String expectedActionId, String expectedContentRenderer,
			String expectedLabel, String fieldName)
		throws Exception {

		FDSTableSchemaField fdsTableSchemaField = _fdsTableSchemaFieldsMap.get(
			fieldName);

		Assert.assertEquals(
			expectedActionId, fdsTableSchemaField.getActionId());
		Assert.assertEquals(
			expectedContentRenderer, fdsTableSchemaField.getContentRenderer());

		if (fdsTableSchemaField instanceof DateFDSTableSchemaField) {
			DateFDSTableSchemaField dateFDSTableSchemaField =
				(DateFDSTableSchemaField)fdsTableSchemaField;

			JSONAssert.assertEquals(
				JSONUtil.put(
					"day", "numeric"
				).put(
					"month", "numeric"
				).put(
					"year", "numeric"
				).toString(),
				String.valueOf(dateFDSTableSchemaField.getFormat()), true);
		}

		Assert.assertEquals(expectedLabel, fdsTableSchemaField.getLabel());
	}

	private Map<String, FDSTableSchemaField> _fdsTableSchemaFieldsMap;

	@Inject
	private FDSViewRegistry _fdsViewRegistry;

}