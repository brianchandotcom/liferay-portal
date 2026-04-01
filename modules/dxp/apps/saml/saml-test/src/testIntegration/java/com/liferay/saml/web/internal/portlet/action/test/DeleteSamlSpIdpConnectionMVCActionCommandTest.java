/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.saml.persistence.model.SamlSpIdpConnection;
import com.liferay.saml.persistence.service.SamlSpIdpConnectionLocalService;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Lucas Miranda
 */
@RunWith(Arquillian.class)
public class DeleteSamlSpIdpConnectionMVCActionCommandTest
	extends BaseMVCActionCommandTestCase<SamlSpIdpConnection> {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Override
	protected SamlSpIdpConnection addBaseModel() {
		SamlSpIdpConnection samlSpIdpConnection =
			_samlSpIdpConnectionLocalService.createSamlSpIdpConnection(
				RandomTestUtil.nextLong());

		samlSpIdpConnection.setSamlIdpEntityId("TEST_IDP_ENTITY_ID");
		samlSpIdpConnection.setName(RandomTestUtil.randomString());

		return _samlSpIdpConnectionLocalService.updateSamlSpIdpConnection(
			samlSpIdpConnection);
	}

	@Override
	protected void assertProcessAction(
		SamlSpIdpConnection samlSpIdpConnection) {

		Assert.assertNull(samlSpIdpConnection);
	}

	@Override
	protected SamlSpIdpConnection fetchBaseModel(long primaryKey) {
		return _samlSpIdpConnectionLocalService.fetchSamlSpIdpConnection(
			primaryKey);
	}

	@Override
	protected MVCActionCommand getMVCActionCommand() {
		return _mvcActionCommand;
	}

	@Override
	protected Map<String, List<String>> getRequestParameters(
		SamlSpIdpConnection samlSpIdpConnection) {

		return Map.of(
			"samlSpIdpConnectionId",
			Collections.singletonList(
				String.valueOf(
					samlSpIdpConnection.getSamlSpIdpConnectionId())));
	}

	@Inject(
		filter = "mvc.command.name=/admin/delete_saml_sp_idp_connection",
		type = MVCActionCommand.class
	)
	private MVCActionCommand _mvcActionCommand;

	@Inject
	private SamlSpIdpConnectionLocalService _samlSpIdpConnectionLocalService;

}