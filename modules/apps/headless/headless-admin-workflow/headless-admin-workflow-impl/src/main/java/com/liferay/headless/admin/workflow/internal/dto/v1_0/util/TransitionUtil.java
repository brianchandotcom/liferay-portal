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

package com.liferay.headless.admin.workflow.internal.dto.v1_0.util;

import com.liferay.headless.admin.workflow.dto.v1_0.Transition;
import com.liferay.portal.kernel.workflow.DefaultWorkflowTransition;
import com.liferay.portal.kernel.workflow.WorkflowTransition;

import java.util.Locale;

/**
 * @author Inácio Nery
 */
public class TransitionUtil {

	public static Transition toTransition(Locale locale, String name) {
		DefaultWorkflowTransition defaultWorkflowTransition =
			new DefaultWorkflowTransition();

		defaultWorkflowTransition.setName(name);

		return toTransition(locale, defaultWorkflowTransition);
	}

	public static Transition toTransition(
		Locale locale, WorkflowTransition workflowTransition) {

		Transition transition = new Transition();

		transition.setLabel(
			LabelUtil.getLabel(
				workflowTransition.getName(), workflowTransition.getLabelMap(),
				locale));
		transition.setName(workflowTransition.getName());
		transition.setSourceNodeName(workflowTransition.getSourceNodeName());
		transition.setTargetNodeName(workflowTransition.getTargetNodeName());

		return transition;
	}

}