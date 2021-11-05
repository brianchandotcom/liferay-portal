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

package com.liferay.search.experiences.internal.blueprint.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.search.experiences.internal.blueprint.util.SXPBlueprintTestUtil;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Wade Cao
 */
@RunWith(Arquillian.class)
public class SXPBlueprintBoostProximityTest extends BaseSXPBlueprintsTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		super.setUp();

		SXPBlueprintTestUtil.addExpandoColumn(
			group.getCompanyId(), _EXPANDO_COLUMN_GEOLOCATION, _expandoColumns,
			_expandoTables);

		addJournalArticle(
			"Branch SF", _EXPANDO_COLUMN_GEOLOCATION, 64.01, -117.42);
		addJournalArticle(
			"Branch LA", _EXPANDO_COLUMN_GEOLOCATION, 24.03, -107.44);

		setUpSXPBlueprint(getClass());
	}

	@Test
	public void testExpandoKeywordCustomFieldsLocationGeolocation()
		throws Exception {

		updateSXPBlueprint(_getConfigurationJSONString("64.01", "-117.42"));

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				SXPBlueprintTestUtil.getConfigurationTemporarySwapper(
					"2345", "true", _IP_ADDRESS[1])) {

			assertSearch("[branch sf, branch la]", "branch");
		}

		updateSXPBlueprint(_getConfigurationJSONString("24.03", "-107.44"));

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				SXPBlueprintTestUtil.getConfigurationTemporarySwapper(
					"2345", "true", _IP_ADDRESS[0])) {

			assertSearch("[branch la, branch sf]", "branch");
		}
	}

	@Test
	public void testSearch() throws Exception {
		updateSXPBlueprint(getEmptyConfigurationJSONString());

		assertSearchIgnoreRelevance("[branch la, branch sf]", "branch");
	}

	private String _getConfigurationJSONString(String lat, String lon) {
		String configurationJSONString = StringUtil.replace(
			getConfigurationJSONString(getClass()), "${configuration.lat}",
			lat);

		return StringUtil.replace(
			configurationJSONString, "${configuration.lon}", lon);
	}

	private static final String _EXPANDO_COLUMN_GEOLOCATION = "location";

	private static final String[] _IP_ADDRESS = {"34.94.32.140", "64.225.12.7"};

	@DeleteAfterTestRun
	private final List<ExpandoColumn> _expandoColumns = new ArrayList<>();

	@DeleteAfterTestRun
	private final List<ExpandoTable> _expandoTables = new ArrayList<>();

}