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

package com.liferay.dynamic.data.mapping.form.evaluator.internal.expression;

import com.liferay.dynamic.data.mapping.expression.DDMExpressionActionHandler;
import com.liferay.dynamic.data.mapping.expression.ExecuteActionRequest;
import com.liferay.dynamic.data.mapping.expression.ExecuteActionResponse;

import java.util.Map;
import java.util.Optional;

/**
 * @author Leonardo Barros
 * @author Rafael Praxedes
 */
public class DDMFormEvaluatorExpressionActionHandler
	implements DDMExpressionActionHandler {

	public DDMFormEvaluatorExpressionActionHandler(
		Map<Integer, Integer> pageFlow) {

		_pageFlow = pageFlow;
	}

	@Override
	public ExecuteActionResponse executeAction(
		ExecuteActionRequest executeActionRequest) {

		String action = executeActionRequest.getAction();

		if (action.equals("jumpPage")) {
			return _jumpPage(executeActionRequest);
		}

		ExecuteActionResponse.Builder builder =
			ExecuteActionResponse.Builder.newBuilder(false);

		return builder.build();
	}

	private boolean _hasIntervalOnPageFlow(
		Integer fromPageIndex, Integer toPageIndex) {

		for (Map.Entry<Integer, Integer> entry : _pageFlow.entrySet()) {
			int fromPageFlowIndex = entry.getKey();
			int toPageFlowIndex = entry.getValue();

			if ((toPageIndex < fromPageFlowIndex) ||
				(fromPageIndex > toPageFlowIndex)) {

				continue;
			}

			return true;
		}

		return false;
	}

	private ExecuteActionResponse _jumpPage(
		ExecuteActionRequest executeActionRequest) {

		Optional<Integer> fromOptional =
			executeActionRequest.getParameterOptional("from");
		Optional<Integer> toOptional =
			executeActionRequest.getParameterOptional("to");

		if (fromOptional.isPresent() && toOptional.isPresent() &&
			!_hasIntervalOnPageFlow(fromOptional.get(), toOptional.get())) {

			_pageFlow.put(fromOptional.get(), toOptional.get());
		}

		ExecuteActionResponse.Builder builder =
			ExecuteActionResponse.Builder.newBuilder(true);

		return builder.build();
	}

	private final Map<Integer, Integer> _pageFlow;

}