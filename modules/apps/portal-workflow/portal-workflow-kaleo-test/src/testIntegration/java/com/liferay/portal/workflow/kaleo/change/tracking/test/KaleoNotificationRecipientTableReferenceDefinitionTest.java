/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.change.tracking.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.workflow.kaleo.definition.NotificationReceptionType;
import com.liferay.portal.workflow.kaleo.definition.Recipient;
import com.liferay.portal.workflow.kaleo.definition.Task;
import com.liferay.portal.workflow.kaleo.definition.UserRecipient;
import com.liferay.portal.workflow.kaleo.model.KaleoInstance;
import com.liferay.portal.workflow.kaleo.model.KaleoNode;
import com.liferay.portal.workflow.kaleo.model.KaleoNotification;
import com.liferay.portal.workflow.kaleo.service.KaleoNotificationRecipientLocalService;

import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * @author Brooke Dalton
 */
@RunWith(Arquillian.class)
public class KaleoNotificationRecipientTableReferenceDefinitionTest
	extends BaseKaleoTableReferenceDefinitionTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_kaleoInstance = addKaleoInstance();

		KaleoNode kaleoNode = addKaleoNode(
			_kaleoInstance,
			new Task(RandomTestUtil.randomString(), StringPool.BLANK));

		_kaleoNotification = addKaleoNotification(_kaleoInstance, kaleoNode);
	}

	@Override
	protected CTModel<?> addCTModel() throws Exception {
		Recipient recipient = new UserRecipient();

		recipient.setNotificationReceptionType(
			NotificationReceptionType.parse("email"));

		return _kaleoNotificationRecipientLocalService.
			addKaleoNotificationRecipient(
				_kaleoInstance.getKaleoDefinitionId(),
				_kaleoInstance.getKaleoDefinitionVersionId(),
				_kaleoNotification.getKaleoNotificationId(), recipient,
				serviceContext);
	}

	private KaleoInstance _kaleoInstance;
	private KaleoNotification _kaleoNotification;

	@Inject
	private KaleoNotificationRecipientLocalService
		_kaleoNotificationRecipientLocalService;

}