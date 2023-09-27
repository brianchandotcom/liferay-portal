/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.metrics.rest.internal.dto.v1_0.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLADefinition;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.SLAResult;
import com.liferay.portal.workflow.metrics.sla.processor.WorkflowMetricsSLAStatus;

import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Rafael Praxedes
 */
public class SLAResultUtil {

	public static SLAResult toSLAResult(
		Document document,
		Function<Long, WorkflowMetricsSLADefinition>
			workflowMetricsSLADefinitionFunction) {

		return _toSLAResult(
			() -> _parseDate(document.getDate("modifiedDate")),
			slaDefinitionId -> _getSLAName(
				slaDefinitionId, workflowMetricsSLADefinitionFunction),
			() -> document.getBoolean("onTime"),
			() -> _parseDate(document.getDate("overdueDate")),
			() -> document.getLong("remainingTime"),
			() -> document.getLong("slaDefinitionId"),
			() -> _getSLAResultStatus(document.getString("status")));
	}

	public static SLAResult toSLAResult(
		Map<String, Object> sourcesMap,
		Function<Long, WorkflowMetricsSLADefinition>
			workflowMetricsSLADefinitionFunction) {

		return _toSLAResult(
			() -> _parseDate(
				GetterUtil.getString(sourcesMap.get("modifiedDate"))),
			slaDefinitionId -> _getSLAName(
				slaDefinitionId, workflowMetricsSLADefinitionFunction),
			() -> GetterUtil.getBoolean(sourcesMap.get("onTime")),
			() -> _parseDate(
				GetterUtil.getString(sourcesMap.get("overdueDate"))),
			() -> GetterUtil.getLong(sourcesMap.get("remainingTime")),
			() -> GetterUtil.getLong(sourcesMap.get("slaDefinitionId")),
			() -> _getSLAResultStatus(
				GetterUtil.getString(sourcesMap.get("status"))));
	}

	private static String _getSLAName(
		long slaDefinitionId,
		Function<Long, WorkflowMetricsSLADefinition>
			workflowMetricsSLADefinitionFunction) {

		WorkflowMetricsSLADefinition workflowMetricsSLADefinition =
			workflowMetricsSLADefinitionFunction.apply(slaDefinitionId);

		if (workflowMetricsSLADefinition == null) {
			return StringPool.BLANK;
		}

		return workflowMetricsSLADefinition.getName();
	}

	private static SLAResult.Status _getSLAResultStatus(String status) {
		if (Objects.equals(status, WorkflowMetricsSLAStatus.COMPLETED.name()) ||
			Objects.equals(status, WorkflowMetricsSLAStatus.STOPPED.name())) {

			return SLAResult.Status.STOPPED;
		}
		else if (Objects.equals(status, WorkflowMetricsSLAStatus.NEW.name())) {
			return SLAResult.Status.NEW;
		}
		else if (Objects.equals(
					status, WorkflowMetricsSLAStatus.PAUSED.name())) {

			return SLAResult.Status.PAUSED;
		}

		return SLAResult.Status.RUNNING;
	}

	private static Date _parseDate(String dateString) {
		try {
			return DateUtil.parseDate(
				"yyyyMMddHHmmss", dateString, LocaleUtil.getDefault());
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception);
			}

			return null;
		}
	}

	private static SLAResult _toSLAResult(
		Supplier<Date> modifiedDateSupplier,
		Function<Long, String> nameFunction, Supplier<Boolean> onTimeSupplier,
		Supplier<Date> overdueDateSupplier,
		Supplier<Long> remainingTimeSupplier,
		Supplier<Long> slaDefinitionIdSupplier,
		Supplier<SLAResult.Status> slaResultStatusSupplier) {

		Long slaResultId = slaDefinitionIdSupplier.get();

		return new SLAResult() {
			{
				dateModified = modifiedDateSupplier.get();
				dateOverdue = overdueDateSupplier.get();
				id = slaResultId;
				name = nameFunction.apply(slaResultId);
				onTime = onTimeSupplier.get();
				remainingTime = remainingTimeSupplier.get();
				status = slaResultStatusSupplier.get();
			}
		};
	}

	private static final Log _log = LogFactoryUtil.getLog(SLAResultUtil.class);

}