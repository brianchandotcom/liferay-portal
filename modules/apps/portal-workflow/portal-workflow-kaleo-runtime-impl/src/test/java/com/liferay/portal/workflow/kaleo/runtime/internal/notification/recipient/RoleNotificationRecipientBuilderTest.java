/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.runtime.internal.notification.recipient;

import com.liferay.portal.kernel.model.User;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.workflow.kaleo.runtime.ExecutionContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Nathaly Gomes
 */
public class RoleNotificationRecipientBuilderTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testIsSelfAssignedUser()
		throws IllegalAccessException, InvocationTargetException,
			   NoSuchMethodException {

		ExecutionContext executionContext = Mockito.mock(
			ExecutionContext.class);

		Mockito.when(
			executionContext.getKaleoTaskInstanceToken()
		).thenReturn(
			null
		);

		RoleNotificationRecipientBuilder roleNotificationRecipientBuilder =
			new RoleNotificationRecipientBuilder();

		Class<?> clazz = roleNotificationRecipientBuilder.getClass();

		Method method = clazz.getDeclaredMethod(
			"_isSelfAssignedUser", ExecutionContext.class, User.class);

		method.setAccessible(true);

		Assert.assertFalse(
			(Boolean)method.invoke(
				roleNotificationRecipientBuilder, executionContext,
				Mockito.mock(User.class)));
	}

}