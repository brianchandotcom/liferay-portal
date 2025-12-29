/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.digital.sales.room.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.digital.sales.room.test.util.DigitalSalesRoomTestUtil;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntryModel;
import com.liferay.fragment.service.FragmentCollectionLocalService;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.headless.digital.sales.room.client.dto.v1_0.DigitalSalesRoom;
import com.liferay.headless.digital.sales.room.client.dto.v1_0.DigitalSalesRoomTemplate;
import com.liferay.headless.digital.sales.room.client.resource.v1_0.DigitalSalesRoomResource;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Stefano Motta
 */
@FeatureFlag("LPD-66359")
@RunWith(Arquillian.class)
public class DigitalSalesRoomTemplateResourceTest
	extends BaseDigitalSalesRoomTemplateResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_dsrRoomObjectDefinition = DigitalSalesRoomTestUtil.getObjectDefinition(
			DigitalSalesRoomResourceTest.class);
	}

	@Ignore
	@Override
	@Test
	public void testGetDigitalSalesRoomDigitalSalesRoomTemplatesPage()
		throws Exception {

		super.testGetDigitalSalesRoomDigitalSalesRoomTemplatesPage();
	}

	@Override
	@Test
	public void testPostDigitalSalesRoomDigitalSalesRoomTemplate()
		throws Exception {

		super.testPostDigitalSalesRoomDigitalSalesRoomTemplate();

		_testPostDigitalSalesRoomDigitalSalesRoomTemplateWithDigitalSalesRoom();
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {
			"clientName", "description", "name", "primaryColor",
			"secondaryColor"
		};
	}

	@Override
	protected DigitalSalesRoomTemplate randomDigitalSalesRoomTemplate()
		throws Exception {

		return new DigitalSalesRoomTemplate() {
			{
				clientName = RandomTestUtil.randomString();
				description = RandomTestUtil.randomString();
				name = RandomTestUtil.randomString();
				primaryColor = RandomTestUtil.randomString();
				secondaryColor = RandomTestUtil.randomString();
			}
		};
	}

	@Override
	protected DigitalSalesRoomTemplate
			testDeleteDigitalSalesRoomTemplate_addDigitalSalesRoomTemplate()
		throws Exception {

		return digitalSalesRoomTemplateResource.postDigitalSalesRoomTemplate(
			randomDigitalSalesRoomTemplate());
	}

	@Override
	protected DigitalSalesRoomTemplate
			testGetDigitalSalesRoomTemplate_addDigitalSalesRoomTemplate()
		throws Exception {

		return digitalSalesRoomTemplateResource.postDigitalSalesRoomTemplate(
			randomDigitalSalesRoomTemplate());
	}

	@Override
	protected DigitalSalesRoomTemplate
			testGetDigitalSalesRoomTemplatesPage_addDigitalSalesRoomTemplate(
				DigitalSalesRoomTemplate digitalSalesRoomTemplate)
		throws Exception {

		return digitalSalesRoomTemplateResource.postDigitalSalesRoomTemplate(
			digitalSalesRoomTemplate);
	}

	@Override
	protected DigitalSalesRoomTemplate
			testPostDigitalSalesRoomDigitalSalesRoomTemplate_addDigitalSalesRoomTemplate(
				DigitalSalesRoomTemplate digitalSalesRoomTemplate)
		throws Exception {

		return digitalSalesRoomTemplateResource.postDigitalSalesRoomTemplate(
			digitalSalesRoomTemplate);
	}

	@Override
	protected DigitalSalesRoomTemplate
			testPostDigitalSalesRoomTemplate_addDigitalSalesRoomTemplate(
				DigitalSalesRoomTemplate digitalSalesRoomTemplate)
		throws Exception {

		return digitalSalesRoomTemplateResource.postDigitalSalesRoomTemplate(
			digitalSalesRoomTemplate);
	}

	private void _testPostDigitalSalesRoomDigitalSalesRoomTemplateWithDigitalSalesRoom()
		throws Exception {

		User user = UserTestUtil.getAdminUser(
			_dsrRoomObjectDefinition.getCompanyId());

		DigitalSalesRoomResource digitalSalesRoomResource =
			DigitalSalesRoomResource.builder(
			).authentication(
				user.getEmailAddress(), PropsValues.DEFAULT_ADMIN_PASSWORD
			).build();

		DigitalSalesRoom digitalSalesRoom =
			digitalSalesRoomResource.postDigitalSalesRoom(
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

		DigitalSalesRoomTemplate randomDigitalSalesRoomTemplate =
			randomDigitalSalesRoomTemplate();

		DigitalSalesRoomTemplate digitalSalesRoomTemplate =
			digitalSalesRoomTemplateResource.
				postDigitalSalesRoomDigitalSalesRoomTemplate(
					digitalSalesRoom.getId(), randomDigitalSalesRoomTemplate);

		Assert.assertEquals(
			digitalSalesRoom.getClientName(),
			digitalSalesRoomTemplate.getClientName());
		Assert.assertEquals(
			randomDigitalSalesRoomTemplate.getDescription(),
			digitalSalesRoomTemplate.getDescription());
		Assert.assertEquals(
			randomDigitalSalesRoomTemplate.getName() + " (Template)",
			digitalSalesRoomTemplate.getName());
		Assert.assertEquals(
			digitalSalesRoom.getPrimaryColor(),
			digitalSalesRoomTemplate.getPrimaryColor());
		Assert.assertEquals(
			digitalSalesRoom.getSecondaryColor(),
			digitalSalesRoomTemplate.getSecondaryColor());

		FragmentCollection fragmentCollection =
			_fragmentCollectionLocalService.fetchFragmentCollection(
				digitalSalesRoomTemplate.getId(), "digital-sales-room");

		Assert.assertTrue(
			ArrayUtil.containsAll(
				TransformUtil.transformToArray(
					_fragmentEntryLocalService.getFragmentEntries(
						digitalSalesRoomTemplate.getId(),
						fragmentCollection.getFragmentCollectionId(), 0),
					FragmentEntryModel::getName, String.class),
				new String[] {"DSR Header Main", "DSR Header User"}));

		Assert.assertTrue(
			ArrayUtil.containsAll(
				TransformUtil.transformToArray(
					_layoutLocalService.getLayouts(
						digitalSalesRoomTemplate.getId(), false),
					layout -> layout.getName(LocaleUtil.getSiteDefault()),
					String.class),
				new String[] {"Documents", "Onboarding"}));
	}

	private ObjectDefinition _dsrRoomObjectDefinition;

	@Inject
	private FragmentCollectionLocalService _fragmentCollectionLocalService;

	@Inject
	private FragmentEntryLocalService _fragmentEntryLocalService;

	@Inject
	private LayoutLocalService _layoutLocalService;

}