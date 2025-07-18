/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.web.internal.display.context;

import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.workflow.web.internal.search.WorkflowDefinitionLinkSearchEntry;

import java.util.Locale;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Mateus Xavier
 */
public class WorkflowDefinitionLinkDisplayContextTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testCreateWorkflowDefinitionLinkSearchEntry() throws Exception {
		String script = "'\"></option><img onerror=alert(123) src=x>";

		Mockito.when(
			_workflowHandler.getType(Mockito.any(Locale.class))
		).thenReturn(
			script
		);

		Mockito.doCallRealMethod(
		).when(
			_workflowDefinitionLinkDisplayContext
		).createWorkflowDefinitionLinkSearchEntry(
			Mockito.any(WorkflowHandler.class), Mockito.any(Locale.class)
		);

		Mockito.when(
			_workflowHandler.getClassName()
		).thenReturn(
			RandomTestUtil.randomString()
		);

		WorkflowDefinitionLinkSearchEntry searchEntry =
			_workflowDefinitionLinkDisplayContext.
				createWorkflowDefinitionLinkSearchEntry(
					_workflowHandler, LocaleUtil.US);

		Assert.assertEquals(
			HtmlUtil.escapeAttribute(script), searchEntry.getResource());
	}

	private final WorkflowDefinitionLinkDisplayContext
		_workflowDefinitionLinkDisplayContext = Mockito.mock(
			WorkflowDefinitionLinkDisplayContext.class);
	private final WorkflowHandler<?> _workflowHandler = Mockito.mock(
		WorkflowHandler.class);

}