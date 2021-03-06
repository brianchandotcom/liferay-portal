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

package com.liferay.dynamic.data.mapping.internal.model.listener;

import com.liferay.dynamic.data.mapping.exception.NoSuchFormInstanceReportException;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceReportLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import org.powermock.api.mockito.PowerMockito;

/**
 * @author Marcos Martins
 */
@RunWith(MockitoJUnitRunner.class)
public class DDMFormInstanceRecordModelListenerTest extends PowerMockito {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_setUpDDMFormInstanceRecordModelListener();

		_ddmFormInstanceRecordModelListener.ddmFormInstanceReportLocalService =
			_ddmFormInstanceReportLocalService;
	}

	@Test
	public void testOnBeforeRemove() throws PortalException {
		when(
			_ddmFormInstanceReportLocalService.
				getFormInstanceReportByFormInstanceId(Mockito.anyLong())
		).thenThrow(
			new NoSuchFormInstanceReportException()
		);

		DDMFormInstanceRecord ddmFormInstanceRecord = mock(
			DDMFormInstanceRecord.class);

		when(
			ddmFormInstanceRecord.getFormInstanceId()
		).thenReturn(
			0L
		);

		_ddmFormInstanceRecordModelListener.onBeforeRemove(
			ddmFormInstanceRecord);
	}

	private void _setUpDDMFormInstanceRecordModelListener() {
		_ddmFormInstanceRecordModelListener =
			new DDMFormInstanceRecordModelListener();
	}

	private DDMFormInstanceRecordModelListener
		_ddmFormInstanceRecordModelListener;

	@Mock
	private DDMFormInstanceReportLocalService
		_ddmFormInstanceReportLocalService;

}