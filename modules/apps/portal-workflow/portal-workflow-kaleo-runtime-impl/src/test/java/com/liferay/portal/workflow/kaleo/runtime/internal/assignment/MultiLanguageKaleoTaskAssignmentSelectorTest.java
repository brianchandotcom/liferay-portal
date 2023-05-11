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

package com.liferay.portal.workflow.kaleo.runtime.internal.assignment;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.workflow.kaleo.KaleoTaskAssignmentFactory;
import com.liferay.portal.workflow.kaleo.model.KaleoInstance;
import com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignment;
import com.liferay.portal.workflow.kaleo.model.impl.KaleoTaskAssignmentImpl;
import com.liferay.portal.workflow.kaleo.runtime.ExecutionContext;
import com.liferay.portal.workflow.kaleo.runtime.assignment.KaleoTaskAssignmentSelector;
import com.liferay.portal.workflow.kaleo.service.KaleoInstanceLocalService;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Jiaxu Wei
 */
public class MultiLanguageKaleoTaskAssignmentSelectorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testUseJavaScriptingKaleoTaskAssignmentSelector()
		throws PortalException {

		MultiLanguageKaleoTaskAssignmentSelector
			multiLanguageKaleoTaskAssignmentSelector =
				_getMultiLanguageKaleoTaskAssignmentSelector();

		TestJavaScriptingKaleoTaskAssignmentSelector
			testJavaScriptingKaleoTaskAssignmentSelector =
				_getTestJavaScriptingKaleoTaskAssignmentSelector();

		multiLanguageKaleoTaskAssignmentSelector.addKaleoTaskAssignmentSelector(
			testJavaScriptingKaleoTaskAssignmentSelector,
			HashMapBuilder.put(
				"scripting.language", (Object)"java"
			).build());

		Collection<KaleoTaskAssignment> kaleoTaskAssignments =
			multiLanguageKaleoTaskAssignmentSelector.getKaleoTaskAssignments(
				_getKaleoTaskAssignment(
					testJavaScriptingKaleoTaskAssignmentSelector.getClass(),
					"java"),
				_getExecutionContext());

		Assert.assertEquals(
			kaleoTaskAssignments.toString(), 1, kaleoTaskAssignments.size());

		Iterator<KaleoTaskAssignment> iterator =
			kaleoTaskAssignments.iterator();

		KaleoTaskAssignment kaleoTaskAssignment = iterator.next();

		Assert.assertEquals(_USER_ID, kaleoTaskAssignment.getAssigneeClassPK());
	}

	private ExecutionContext _getExecutionContext() {
		ExecutionContext executionContext = Mockito.mock(
			ExecutionContext.class);

		Mockito.when(
			executionContext.getKaleoInstanceToken()
		).thenReturn(
			Mockito.mock(KaleoInstanceToken.class)
		);

		return executionContext;
	}

	private KaleoInstanceLocalService _getKaleoInstanceLocalService() {
		KaleoInstanceLocalService kaleoInstanceLocalService = Mockito.mock(
			KaleoInstanceLocalService.class);

		Mockito.when(
			kaleoInstanceLocalService.updateKaleoInstance(Mockito.any())
		).thenReturn(
			Mockito.mock(KaleoInstance.class)
		);

		return kaleoInstanceLocalService;
	}

	private KaleoTaskAssignment _getKaleoTaskAssignment(
		Class<? extends KaleoTaskAssignmentSelector> clazz,
		String scriptLanguage) {

		KaleoTaskAssignment kaleoTaskAssignment = Mockito.mock(
			KaleoTaskAssignment.class);

		Mockito.when(
			kaleoTaskAssignment.getAssigneeScriptLanguage()
		).thenReturn(
			scriptLanguage
		);

		Mockito.when(
			kaleoTaskAssignment.getAssigneeScript()
		).thenReturn(
			clazz.getName()
		);

		Mockito.when(
			kaleoTaskAssignment.getUserId()
		).thenReturn(
			_USER_ID
		);

		return kaleoTaskAssignment;
	}

	private MultiLanguageKaleoTaskAssignmentSelector
		_getMultiLanguageKaleoTaskAssignmentSelector() {

		MultiLanguageKaleoTaskAssignmentSelector
			multiLanguageKaleoTaskAssignmentSelector =
				new MultiLanguageKaleoTaskAssignmentSelector();

		ReflectionTestUtil.setFieldValue(
			multiLanguageKaleoTaskAssignmentSelector,
			"_kaleoInstanceLocalService", _getKaleoInstanceLocalService());

		return multiLanguageKaleoTaskAssignmentSelector;
	}

	private TestJavaScriptingKaleoTaskAssignmentSelector
		_getTestJavaScriptingKaleoTaskAssignmentSelector() {

		KaleoTaskAssignmentFactory kaleoTaskAssignmentFactory = Mockito.mock(
			KaleoTaskAssignmentFactory.class);

		Mockito.when(
			kaleoTaskAssignmentFactory.createKaleoTaskAssignment()
		).thenReturn(
			new KaleoTaskAssignmentImpl()
		);

		return new TestJavaScriptingKaleoTaskAssignmentSelector(
			kaleoTaskAssignmentFactory);
	}

	private static final long _USER_ID = RandomTestUtil.randomLong();

	private static class TestJavaScriptingKaleoTaskAssignmentSelector
		implements KaleoTaskAssignmentSelector {

		public TestJavaScriptingKaleoTaskAssignmentSelector(
			KaleoTaskAssignmentFactory kaleoTaskAssignmentFactory) {

			_kaleoTaskAssignmentFactory = kaleoTaskAssignmentFactory;
		}

		@Override
		public Collection<KaleoTaskAssignment> getKaleoTaskAssignments(
				KaleoTaskAssignment kaleoTaskAssignment,
				ExecutionContext executionContext)
			throws PortalException {

			KaleoTaskAssignment newKaleoTaskAssignment =
				_kaleoTaskAssignmentFactory.createKaleoTaskAssignment();

			newKaleoTaskAssignment.setAssigneeClassName(User.class.getName());
			newKaleoTaskAssignment.setAssigneeClassPK(
				kaleoTaskAssignment.getUserId());

			return Collections.singletonList(newKaleoTaskAssignment);
		}

		private final KaleoTaskAssignmentFactory _kaleoTaskAssignmentFactory;

	}

}