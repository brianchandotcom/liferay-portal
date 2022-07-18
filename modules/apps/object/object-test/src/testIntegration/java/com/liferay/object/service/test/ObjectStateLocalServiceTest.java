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

package com.liferay.object.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.object.util.LocalizedMapUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Selton Guedes
 */
@RunWith(Arquillian.class)
public class ObjectStateLocalServiceTest
	extends BaseObjectStateLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testAddObjectState() throws PortalException {
		ListTypeEntry listTypeEntry =
			listTypeEntryLocalService.addListTypeEntry(
				TestPropsValues.getUserId(),
				listTypeDefinition.getListTypeDefinitionId(), "step4",
				LocalizedMapUtil.getLocalizedMap("step4"));

		Assert.assertNotNull(
			objectStateLocalService.getObjectStateFlowObjectState(
				listTypeEntry.getListTypeEntryId(),
				objectStateFlow.getObjectStateFlowId()));
	}

	@Test
	public void testDeleteListTypeEntryObjectStates() throws PortalException {
		ListTypeEntry listTypeEntry = listTypeEntries.get(0);

		listTypeEntryLocalService.deleteListTypeEntry(
			listTypeEntry.getListTypeEntryId());

		try {
			objectStateLocalService.getObjectStateFlowObjectState(
				listTypeEntry.getListTypeEntryId(),
				objectStateFlow.getObjectStateFlowId());

			Assert.fail();
		}
		catch (PortalException portalException) {
			Assert.assertEquals(
				portalException.getMessage(),
				StringBundler.concat(
					"No ObjectState exists with the key {listTypeEntryId=",
					listTypeEntry.getListTypeEntryId(), ", objectStateFlowId=",
					objectStateFlow.getObjectStateFlowId(), "}"));
		}
	}

}