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

package com.liferay.portal.notifications.test.util;

import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserNotificationDelivery;
import com.liferay.portal.kernel.model.UserNotificationDeliveryConstants;
import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.notifications.UserNotificationDefinition;
import com.liferay.portal.kernel.service.UserNotificationDeliveryLocalService;
import com.liferay.portal.kernel.service.UserNotificationEventLocalService;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.mail.MailServiceTestUtil;
import com.liferay.portal.test.rule.Inject;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Roberto Díaz
 * @author Sergio González
 */
public abstract class BaseUserNotificationTestCase {

	@Before
	public void setUp() throws Exception {
		user = UserTestUtil.addOmniAdminUser();

		group = GroupTestUtil.addGroup();

		addContainerModel();

		_userNotificationDeliveries = _getUserNotificationDeliveries(
			user.getUserId());
	}

	@After
	public void tearDown() throws Exception {
		_deleteUserNotificationEvents(user.getUserId());

		_deleteUserNotificationDeliveries();
	}

	@Test
	public void testAddUserNotification() throws Exception {
		subscribeToContainer();

		BaseModel<?> baseModel = addBaseModel();

		Assert.assertEquals(1, MailServiceTestUtil.getInboxSize());

		List<JSONObject> userNotificationEventsJSONObjects =
			getUserNotificationEventsJSONObjects(user.getUserId());

		Assert.assertEquals(
			userNotificationEventsJSONObjects.toString(), 1,
			userNotificationEventsJSONObjects.size());

		for (JSONObject userNotificationEventsJSONObject :
				userNotificationEventsJSONObjects) {

			Assert.assertTrue(
				isValidUserNotificationEventObject(
					(Long)baseModel.getPrimaryKeyObj(),
					userNotificationEventsJSONObject));
			Assert.assertEquals(
				UserNotificationDefinition.NOTIFICATION_TYPE_ADD_ENTRY,
				userNotificationEventsJSONObject.getInt("notificationType"));
		}
	}

	@Test
	public void testAddUserNotificationWhenEmailNotificationsDisabled()
		throws Exception {

		subscribeToContainer();

		_updateUserNotificationDelivery(
			UserNotificationDefinition.NOTIFICATION_TYPE_ADD_ENTRY,
			UserNotificationDeliveryConstants.TYPE_EMAIL, false);

		BaseModel<?> baseModel = addBaseModel();

		Assert.assertEquals(0, MailServiceTestUtil.getInboxSize());

		List<JSONObject> userNotificationEventsJSONObjects =
			getUserNotificationEventsJSONObjects(user.getUserId());

		Assert.assertEquals(
			userNotificationEventsJSONObjects.toString(), 1,
			userNotificationEventsJSONObjects.size());

		for (JSONObject userNotificationEventsJSONObject :
				userNotificationEventsJSONObjects) {

			Assert.assertTrue(
				isValidUserNotificationEventObject(
					(Long)baseModel.getPrimaryKeyObj(),
					userNotificationEventsJSONObject));
			Assert.assertEquals(
				UserNotificationDefinition.NOTIFICATION_TYPE_ADD_ENTRY,
				userNotificationEventsJSONObject.getInt("notificationType"));
		}
	}

	@Test
	public void testAddUserNotificationWhenNotificationsDisabled()
		throws Exception {

		subscribeToContainer();

		_updateUserNotificationsDelivery(false);

		addBaseModel();

		Assert.assertEquals(0, MailServiceTestUtil.getInboxSize());

		List<JSONObject> userNotificationEventsJSONObjects =
			getUserNotificationEventsJSONObjects(user.getUserId());

		Assert.assertEquals(
			userNotificationEventsJSONObjects.toString(), 0,
			userNotificationEventsJSONObjects.size());
	}

	@Test
	public void testAddUserNotificationWhenWebsiteNotificationsDisabled()
		throws Exception {

		subscribeToContainer();

		_updateUserNotificationDelivery(
			UserNotificationDefinition.NOTIFICATION_TYPE_ADD_ENTRY,
			UserNotificationDeliveryConstants.TYPE_WEBSITE, false);

		addBaseModel();

		Assert.assertEquals(1, MailServiceTestUtil.getInboxSize());

		List<JSONObject> userNotificationEventsJSONObjects =
			getUserNotificationEventsJSONObjects(user.getUserId());

		Assert.assertEquals(
			userNotificationEventsJSONObjects.toString(), 0,
			userNotificationEventsJSONObjects.size());
	}

	@Test
	public void testUpdateUserNotification() throws Exception {
		BaseModel<?> baseModel = addBaseModel();

		subscribeToContainer();

		BaseModel<?> updatedBasemodel = updateBaseModel(baseModel);

		Assert.assertEquals(1, MailServiceTestUtil.getInboxSize());

		List<JSONObject> userNotificationEventsJSONObjects =
			getUserNotificationEventsJSONObjects(user.getUserId());

		Assert.assertEquals(
			userNotificationEventsJSONObjects.toString(), 1,
			userNotificationEventsJSONObjects.size());

		for (JSONObject userNotificationEventsJSONObject :
				userNotificationEventsJSONObjects) {

			Assert.assertTrue(
				isValidUserNotificationEventObject(
					(Long)updatedBasemodel.getPrimaryKeyObj(),
					userNotificationEventsJSONObject));

			Assert.assertEquals(
				userNotificationEventsJSONObject.getInt("notificationType"),
				UserNotificationDefinition.NOTIFICATION_TYPE_UPDATE_ENTRY);
		}
	}

	@Test
	public void testUpdateUserNotificationWhenEmailNotificationsDisabled()
		throws Exception {

		_updateUserNotificationDelivery(
			UserNotificationDefinition.NOTIFICATION_TYPE_ADD_ENTRY,
			UserNotificationDeliveryConstants.TYPE_EMAIL, false);
		_updateUserNotificationDelivery(
			UserNotificationDefinition.NOTIFICATION_TYPE_UPDATE_ENTRY,
			UserNotificationDeliveryConstants.TYPE_EMAIL, false);

		BaseModel<?> baseModel = addBaseModel();

		subscribeToContainer();

		BaseModel<?> updatedBasemodel = updateBaseModel(baseModel);

		Assert.assertEquals(0, MailServiceTestUtil.getInboxSize());

		List<JSONObject> userNotificationEventsJSONObjects =
			getUserNotificationEventsJSONObjects(user.getUserId());

		Assert.assertEquals(
			userNotificationEventsJSONObjects.toString(), 1,
			userNotificationEventsJSONObjects.size());

		for (JSONObject userNotificationEventsJSONObject :
				userNotificationEventsJSONObjects) {

			Assert.assertTrue(
				isValidUserNotificationEventObject(
					(Long)updatedBasemodel.getPrimaryKeyObj(),
					userNotificationEventsJSONObject));

			Assert.assertEquals(
				userNotificationEventsJSONObject.getInt("notificationType"),
				UserNotificationDefinition.NOTIFICATION_TYPE_UPDATE_ENTRY);
		}
	}

	@Test
	public void testUpdateUserNotificationWhenNotificationsDisabled()
		throws Exception {

		_updateUserNotificationsDelivery(false);

		subscribeToContainer();

		updateBaseModel(addBaseModel());

		Assert.assertEquals(0, MailServiceTestUtil.getInboxSize());

		List<JSONObject> userNotificationEventsJSONObjects =
			getUserNotificationEventsJSONObjects(user.getUserId());

		Assert.assertEquals(
			userNotificationEventsJSONObjects.toString(), 0,
			userNotificationEventsJSONObjects.size());
	}

	@Test
	public void testUpdateUserNotificationWhenWebsiteNotificationsDisabled()
		throws Exception {

		_updateUserNotificationDelivery(
			UserNotificationDefinition.NOTIFICATION_TYPE_ADD_ENTRY,
			UserNotificationDeliveryConstants.TYPE_WEBSITE, false);
		_updateUserNotificationDelivery(
			UserNotificationDefinition.NOTIFICATION_TYPE_UPDATE_ENTRY,
			UserNotificationDeliveryConstants.TYPE_WEBSITE, false);

		BaseModel<?> baseModel = addBaseModel();

		subscribeToContainer();

		updateBaseModel(baseModel);

		Assert.assertEquals(1, MailServiceTestUtil.getInboxSize());

		List<JSONObject> userNotificationEventsJSONObjects =
			getUserNotificationEventsJSONObjects(user.getUserId());

		Assert.assertEquals(
			userNotificationEventsJSONObjects.toString(), 0,
			userNotificationEventsJSONObjects.size());
	}

	protected abstract BaseModel<?> addBaseModel() throws Exception;

	protected void addContainerModel() throws Exception {
	}

	protected abstract String getPortletId();

	protected List<JSONObject> getUserNotificationEventsJSONObjects(long userId)
		throws Exception {

		List<UserNotificationEvent> userNotificationEvents =
			_userNotificationEventLocalService.getUserNotificationEvents(
				userId);

		List<JSONObject> userNotificationEventJSONObjects = new ArrayList<>(
			userNotificationEvents.size());

		for (UserNotificationEvent userNotificationEvent :
				userNotificationEvents) {

			userNotificationEventJSONObjects.add(
				_jsonFactory.createJSONObject(
					userNotificationEvent.getPayload()));
		}

		return userNotificationEventJSONObjects;
	}

	protected boolean isValidUserNotificationEventObject(
			long primaryKey, JSONObject userNotificationEventJSONObject)
		throws Exception {

		if (userNotificationEventJSONObject.getLong("classPK") != primaryKey) {
			return false;
		}

		return true;
	}

	protected abstract void subscribeToContainer() throws Exception;

	protected abstract BaseModel<?> updateBaseModel(BaseModel<?> baseModel)
		throws Exception;

	@DeleteAfterTestRun
	protected Group group;

	@DeleteAfterTestRun
	protected User user;

	private void _deleteUserNotificationDeliveries() {
		_userNotificationDeliveryLocalService.deleteUserNotificationDeliveries(
			user.getUserId());
	}

	private void _deleteUserNotificationEvents(long userId) {
		List<UserNotificationEvent> userNotificationEvents =
			_userNotificationEventLocalService.getUserNotificationEvents(
				userId);

		for (UserNotificationEvent userNotificationEvent :
				userNotificationEvents) {

			_userNotificationEventLocalService.deleteUserNotificationEvent(
				userNotificationEvent);
		}
	}

	private List<UserNotificationDelivery> _getUserNotificationDeliveries(
			long userId)
		throws Exception {

		List<UserNotificationDelivery> userNotificationDeliveries =
			new ArrayList<>();

		userNotificationDeliveries.add(
			_userNotificationDeliveryLocalService.getUserNotificationDelivery(
				userId, getPortletId(), 0,
				UserNotificationDefinition.NOTIFICATION_TYPE_ADD_ENTRY,
				UserNotificationDeliveryConstants.TYPE_EMAIL, true));
		userNotificationDeliveries.add(
			_userNotificationDeliveryLocalService.getUserNotificationDelivery(
				userId, getPortletId(), 0,
				UserNotificationDefinition.NOTIFICATION_TYPE_ADD_ENTRY,
				UserNotificationDeliveryConstants.TYPE_WEBSITE, true));
		userNotificationDeliveries.add(
			_userNotificationDeliveryLocalService.getUserNotificationDelivery(
				userId, getPortletId(), 0,
				UserNotificationDefinition.NOTIFICATION_TYPE_UPDATE_ENTRY,
				UserNotificationDeliveryConstants.TYPE_EMAIL, true));
		userNotificationDeliveries.add(
			_userNotificationDeliveryLocalService.getUserNotificationDelivery(
				userId, getPortletId(), 0,
				UserNotificationDefinition.NOTIFICATION_TYPE_UPDATE_ENTRY,
				UserNotificationDeliveryConstants.TYPE_WEBSITE, true));

		return userNotificationDeliveries;
	}

	private void _updateUserNotificationDelivery(
		int notificationType, int deliveryType, boolean deliver) {

		boolean exists = false;

		for (UserNotificationDelivery userNotificationDelivery :
				_userNotificationDeliveries) {

			if ((userNotificationDelivery.getNotificationType() !=
					notificationType) ||
				(userNotificationDelivery.getDeliveryType() != deliveryType)) {

				continue;
			}

			_userNotificationDeliveryLocalService.
				updateUserNotificationDelivery(
					userNotificationDelivery.getUserNotificationDeliveryId(),
					deliver);

			exists = true;

			break;
		}

		Assert.assertTrue("User notification does not exist", exists);
	}

	private void _updateUserNotificationsDelivery(boolean deliver) {
		for (UserNotificationDelivery userNotificationDelivery :
				_userNotificationDeliveries) {

			_userNotificationDeliveryLocalService.
				updateUserNotificationDelivery(
					userNotificationDelivery.getUserNotificationDeliveryId(),
					deliver);
		}
	}

	@Inject
	private JSONFactory _jsonFactory;

	private List<UserNotificationDelivery> _userNotificationDeliveries =
		new ArrayList<>();

	@Inject
	private UserNotificationDeliveryLocalService
		_userNotificationDeliveryLocalService;

	@Inject
	private UserNotificationEventLocalService
		_userNotificationEventLocalService;

}