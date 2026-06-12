/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.audience.criteria.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.audience.criteria.AudienceCriteria;
import com.liferay.audience.criteria.AudienceCriteriaProvider;
import com.liferay.audience.criteria.AudienceCriteriaType;
import com.liferay.client.extension.constants.ClientExtensionEntryConstants;
import com.liferay.client.extension.model.ClientExtensionEntry;
import com.liferay.client.extension.service.ClientExtensionEntryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eudaldo Alonso
 */
@RunWith(Arquillian.class)
public class AudienceCriteriaProviderTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testGetAudienceCriteriaTypes() throws Exception {
		List<AudienceCriteriaType> audienceCriteriaTypes =
			_audienceCriteriaProvider.getAudienceCriteriaTypes(
				TestPropsValues.getCompanyId(), LocaleUtil.getDefault());

		AudienceCriteriaType audienceCriteriaType = audienceCriteriaTypes.get(
			0);

		List<AudienceCriteria> audienceCriterias =
			audienceCriteriaType.getAudienceCriterias();

		Assert.assertEquals(
			audienceCriterias.toString(), 15, audienceCriterias.size());

		AudienceCriteria audienceCriteria = _getAudienceCriteria(
			audienceCriterias, "url");

		Assert.assertEquals(
			AudienceCriteria.Type.STRING, audienceCriteria.getType());
	}

	@Test
	public void testGetAudienceCriteriaTypesWithCustomAttribute()
		throws Exception {

		String name = RandomTestUtil.randomString();

		ClientExtensionEntry clientExtensionEntry = _addClientExtensionEntry(
			name);

		List<AudienceCriteriaType> audienceCriteriaTypes =
			_audienceCriteriaProvider.getAudienceCriteriaTypes(
				TestPropsValues.getCompanyId(), LocaleUtil.getDefault());

		AudienceCriteriaType audienceCriteriaType = audienceCriteriaTypes.get(
			1);

		List<AudienceCriteria> audienceCriterias =
			audienceCriteriaType.getAudienceCriterias();

		Assert.assertEquals(
			audienceCriterias.toString(), 1, audienceCriterias.size());

		AudienceCriteria audienceCriteria = _getAudienceCriteria(
			audienceCriterias,
			"custom:" + clientExtensionEntry.getExternalReferenceCode());

		Assert.assertEquals(name, audienceCriteria.getLabel());
		Assert.assertEquals(
			AudienceCriteria.Type.STRING, audienceCriteria.getType());
	}

	private ClientExtensionEntry _addClientExtensionEntry(String name)
		throws Exception {

		ClientExtensionEntry clientExtensionEntry =
			_clientExtensionEntryLocalService.addClientExtensionEntry(
				RandomTestUtil.randomString(), TestPropsValues.getUserId(),
				StringPool.BLANK,
				Collections.singletonMap(LocaleUtil.getDefault(), name),
				StringPool.BLANK, StringPool.BLANK,
				ClientExtensionEntryConstants.TYPE_AUDIENCES_CUSTOM_ATTRIBUTES,
				UnicodePropertiesBuilder.create(
					true
				).put(
					"symbols", RandomTestUtil.randomString()
				).put(
					"url", "http://" + RandomTestUtil.randomString() + ".com"
				).buildString());

		_clientExtensionEntries.add(clientExtensionEntry);

		return clientExtensionEntry;
	}

	private AudienceCriteria _getAudienceCriteria(
		List<AudienceCriteria> audienceCriterias, String key) {

		for (AudienceCriteria audienceCriteria : audienceCriterias) {
			if (key.equals(audienceCriteria.getKey())) {
				return audienceCriteria;
			}
		}

		return null;
	}

	@Inject
	private AudienceCriteriaProvider _audienceCriteriaProvider;

	@DeleteAfterTestRun
	private final List<ClientExtensionEntry> _clientExtensionEntries =
		new ArrayList<>();

	@Inject
	private ClientExtensionEntryLocalService _clientExtensionEntryLocalService;

}