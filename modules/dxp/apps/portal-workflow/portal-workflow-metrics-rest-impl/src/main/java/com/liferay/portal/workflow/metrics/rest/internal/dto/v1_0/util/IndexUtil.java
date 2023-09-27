/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.metrics.rest.internal.dto.v1_0.util;

import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.Index;

import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author Rafael Praxedes
 */
public class IndexUtil {

	public static Index toIndex(
		String indexEntityName, Language language,
		ResourceBundle resourceBundle) {

		return new Index() {
			{
				key = indexEntityName;
				label = language.get(
					resourceBundle, _labelMap.get(indexEntityName));

				setGroup(
					() -> {
						if (indexEntityName.startsWith("sla")) {
							return Index.Group.SLA;
						}

						return Index.Group.METRIC;
					});
			}
		};
	}

	private static final Map<String, String> _labelMap =
		LinkedHashMapBuilder.put(
			"instance", "workflow-metrics-instances"
		).put(
			"node", "workflow-metrics-nodes"
		).put(
			"process", "workflow-metrics-processes"
		).put(
			"sla-instance-result", "workflow-sla-instance-results"
		).put(
			"sla-task-result", "workflow-sla-task-results"
		).put(
			"task", "workflow-metrics-tasks"
		).put(
			"transition", "workflow-metrics-transitions"
		).build();

}