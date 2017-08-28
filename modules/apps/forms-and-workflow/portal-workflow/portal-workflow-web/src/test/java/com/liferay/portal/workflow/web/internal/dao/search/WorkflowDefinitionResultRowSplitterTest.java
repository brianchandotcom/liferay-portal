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

package com.liferay.portal.workflow.web.internal.dao.search;

import com.liferay.portal.kernel.dao.search.ResultRow;
import com.liferay.portal.kernel.dao.search.ResultRowSplitter;
import com.liferay.portal.kernel.dao.search.ResultRowSplitterEntry;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.workflow.DefaultWorkflowDefinition;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Inácio Nery
 */
public class WorkflowDefinitionResultRowSplitterTest {

	@Test
	public void testSplitDefinitions() {
		addWorkflowDefinition(true);
		addWorkflowDefinition(false);

		List<ResultRowSplitterEntry> resultRowSplitterEntryList =
			_resultRowSplitter.split(_resultRows);

		Assert.assertEquals(
			resultRowSplitterEntryList.toString(), 2,
			resultRowSplitterEntryList.size());

		ResultRowSplitterEntry resultRowSplitterEntry =
			resultRowSplitterEntryList.get(0);

		Assert.assertEquals("published", resultRowSplitterEntry.getTitle());

		List<ResultRow> resultRows = resultRowSplitterEntry.getResultRows();

		Assert.assertEquals(resultRows.toString(), 1, resultRows.size());

		resultRowSplitterEntry = resultRowSplitterEntryList.get(1);

		Assert.assertEquals("not-published", resultRowSplitterEntry.getTitle());

		resultRows = resultRowSplitterEntry.getResultRows();

		Assert.assertEquals(resultRows.toString(), 1, resultRows.size());
	}

	@Test
	public void testSplitNoDefinitions() {
		List<ResultRowSplitterEntry> resultRowSplitterEntryList =
			_resultRowSplitter.split(_resultRows);

		Assert.assertEquals(
			resultRowSplitterEntryList.toString(), 0,
			resultRowSplitterEntryList.size());
	}

	@Test
	public void testSplitNotPublishedDefinitions() {
		addWorkflowDefinition(false);

		List<ResultRowSplitterEntry> resultRowSplitterEntryList =
			_resultRowSplitter.split(_resultRows);

		Assert.assertEquals(
			resultRowSplitterEntryList.toString(), 1,
			resultRowSplitterEntryList.size());

		ResultRowSplitterEntry resultRowSplitterEntry =
			resultRowSplitterEntryList.get(0);

		Assert.assertEquals("not-published", resultRowSplitterEntry.getTitle());

		List<ResultRow> resultRows = resultRowSplitterEntry.getResultRows();

		Assert.assertEquals(resultRows.toString(), 1, resultRows.size());
	}

	@Test
	public void testSplitPublishedDefinitions() {
		addWorkflowDefinition(true);

		List<ResultRowSplitterEntry> resultRowSplitterEntryList =
			_resultRowSplitter.split(_resultRows);

		Assert.assertEquals(
			resultRowSplitterEntryList.toString(), 1,
			resultRowSplitterEntryList.size());

		ResultRowSplitterEntry resultRowSplitterEntry =
			resultRowSplitterEntryList.get(0);

		Assert.assertEquals("published", resultRowSplitterEntry.getTitle());

		List<ResultRow> resultRows = resultRowSplitterEntry.getResultRows();

		Assert.assertEquals(resultRows.toString(), 1, resultRows.size());
	}

	protected void addWorkflowDefinition(boolean active) {
		DefaultWorkflowDefinition defaultWorkflowDefinition =
			new DefaultWorkflowDefinition();

		defaultWorkflowDefinition.setActive(active);

		ResultRow resultRow = new com.liferay.taglib.search.ResultRow(
			defaultWorkflowDefinition, RandomTestUtil.randomLong(),
			RandomTestUtil.randomInt());

		_resultRows.add(resultRow);
	}

	private final List<ResultRow> _resultRows = new ArrayList<>();
	private final ResultRowSplitter _resultRowSplitter =
		new WorkflowDefinitionResultRowSplitter();

}