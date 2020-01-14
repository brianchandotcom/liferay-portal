/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.workflow.metrics.util.comparator;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLADefinitionVersion;

/**
 * @author Rafael Praxedes
 */
public class WorkflowMetricsSLADefinitionVersionComparator
	extends OrderByComparator<WorkflowMetricsSLADefinitionVersion> {

	public WorkflowMetricsSLADefinitionVersionComparator() {
		this(false);
	}

	public WorkflowMetricsSLADefinitionVersionComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(
		WorkflowMetricsSLADefinitionVersion
			workflowMetricsSLADefinitionVersion1,
		WorkflowMetricsSLADefinitionVersion
			workflowMetricsSLADefinitionVersion2) {

		int value = 0;

		String version1 = workflowMetricsSLADefinitionVersion1.getVersion();
		String version2 = workflowMetricsSLADefinitionVersion2.getVersion();

		int[] versionParts1 = StringUtil.split(version1, StringPool.PERIOD, 0);
		int[] versionParts2 = StringUtil.split(version2, StringPool.PERIOD, 0);

		if ((versionParts1.length != 2) && (versionParts2.length != 2)) {
			value = 0;
		}
		else if (versionParts1.length != 2) {
			value = -1;
		}
		else if (versionParts2.length != 2) {
			value = 1;
		}
		else if (versionParts1[0] > versionParts2[0]) {
			value = 1;
		}
		else if (versionParts1[0] < versionParts2[0]) {
			value = -1;
		}
		else if (versionParts1[1] > versionParts2[1]) {
			value = 1;
		}
		else if (versionParts1[1] < versionParts2[1]) {
			value = -1;
		}

		if (_ascending) {
			return value;
		}

		return -value;
	}

	@Override
	public String getOrderBy() {
		if (_ascending) {
			return _ORDER_BY_ASC;
		}

		return _ORDER_BY_DESC;
	}

	@Override
	public String[] getOrderByFields() {
		return _ORDER_BY_FIELDS;
	}

	@Override
	public boolean isAscending() {
		return _ascending;
	}

	private static final String _ORDER_BY_ASC =
		"WorkflowMetricsSLADefinitionVersion.version ASC";

	private static final String _ORDER_BY_DESC =
		"WorkflowMetricsSLADefinitionVersion.version DESC";

	private static final String[] _ORDER_BY_FIELDS = {"version"};

	private final boolean _ascending;

}