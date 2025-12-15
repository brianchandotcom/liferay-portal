/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.digital.sales.room.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.digital.sales.room.test.util.DigitalSalesRoomTestUtil;
import com.liferay.headless.digital.sales.room.client.dto.v1_0.DigitalSalesRoom;
import com.liferay.headless.digital.sales.room.client.dto.v1_0.UserAccountBrief;
import com.liferay.headless.digital.sales.room.client.resource.v1_0.DigitalSalesRoomResource;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.FeatureFlag;

import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * @author Stefano Motta
 */
@FeatureFlag("LPD-66359")
@RunWith(Arquillian.class)
public class UserAccountBriefResourceTest
	extends BaseUserAccountBriefResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		ObjectDefinition objectDefinition =
			DigitalSalesRoomTestUtil.getObjectDefinition(
				DigitalSalesRoomResourceTest.class);

		User user = UserTestUtil.getAdminUser(objectDefinition.getCompanyId());

		DigitalSalesRoomResource digitalSalesRoomResource =
			DigitalSalesRoomResource.builder(
			).authentication(
				user.getEmailAddress(), PropsValues.DEFAULT_ADMIN_PASSWORD
			).build();

		_digitalSalesRoom = digitalSalesRoomResource.postDigitalSalesRoom(
			new DigitalSalesRoom() {
				{
					accountId = 0L;
					channelId = 0L;
					channelName = RandomTestUtil.randomString();
					clientName = RandomTestUtil.randomString();
					description = RandomTestUtil.randomString();
					externalReferenceCode = RandomTestUtil.randomString();
					friendlyUrlPath = StringUtil.toLowerCase(
						StringPool.SLASH + RandomTestUtil.randomString());
					name = RandomTestUtil.randomString();
					primaryColor = RandomTestUtil.randomString();
					secondaryColor = RandomTestUtil.randomString();
				}
			});
	}

	@Override
	protected UserAccountBrief randomUserAccountBrief() throws Exception {
		User user = UserTestUtil.addUser();

		return new UserAccountBrief() {
			{
				emailAddress = user.getEmailAddress();
			}
		};
	}

	@Override
	protected Long
			testDeleteDigitalSalesRoomUserAccountBrief_getDigitalSalesRoomId()
		throws Exception {

		return _digitalSalesRoom.getId();
	}

	@Override
	protected Long
			testGetDigitalSalesRoomUserAccountBriefsPage_getDigitalSalesRoomId()
		throws Exception {

		return _digitalSalesRoom.getId();
	}

	private DigitalSalesRoom _digitalSalesRoom;

}