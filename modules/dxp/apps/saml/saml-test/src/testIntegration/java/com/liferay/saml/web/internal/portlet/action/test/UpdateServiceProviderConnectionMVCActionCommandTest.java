/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.saml.persistence.model.SamlIdpSpConnection;
import com.liferay.saml.persistence.service.SamlIdpSpConnectionLocalService;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Rafael Praxedes
 */
@RunWith(Arquillian.class)
public class UpdateServiceProviderConnectionMVCActionCommandTest
	extends BaseMVCActionCommandTestCase<SamlIdpSpConnection> {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Override
	protected SamlIdpSpConnection addBaseModel() {
		SamlIdpSpConnection samlIdpSpConnection =
			_samlIdpSpConnectionLocalService.createSamlIdpSpConnection(
				RandomTestUtil.nextLong());

		samlIdpSpConnection.setSamlSpEntityId(RandomTestUtil.randomString());
		samlIdpSpConnection.setName(RandomTestUtil.randomString());

		return _samlIdpSpConnectionLocalService.updateSamlIdpSpConnection(
			samlIdpSpConnection);
	}

	@Override
	protected void assertProcessAction(
		SamlIdpSpConnection samlIdpSpConnection) {

		Assert.assertEquals(_name, samlIdpSpConnection.getName());
	}

	@Override
	protected SamlIdpSpConnection fetchBaseModel(long primaryKey) {
		return _samlIdpSpConnectionLocalService.fetchSamlIdpSpConnection(
			primaryKey);
	}

	@Override
	protected MVCActionCommand getMVCActionCommand() {
		return _mvcActionCommand;
	}

	@Override
	protected Map<String, List<String>> getRequestParameters(
		SamlIdpSpConnection samlIdpSpConnection) {

		_name = RandomTestUtil.randomString();

		return Map.of(
			"currentURL",
			Collections.singletonList(
				"http://localhost:" + PortalUtil.getPortalServerPort(false)),
			"name", Collections.singletonList(_name), "samlIdpSpConnectionId",
			Collections.singletonList(
				String.valueOf(samlIdpSpConnection.getSamlIdpSpConnectionId())),
			"samlSpEntityId",
			Collections.singletonList(samlIdpSpConnection.getSamlSpEntityId()));
	}

	@Inject(
		filter = "mvc.command.name=/admin/update_service_provider_connection",
		type = MVCActionCommand.class
	)
	private MVCActionCommand _mvcActionCommand;

	private String _name;

	@Inject
	private SamlIdpSpConnectionLocalService _samlIdpSpConnectionLocalService;

}