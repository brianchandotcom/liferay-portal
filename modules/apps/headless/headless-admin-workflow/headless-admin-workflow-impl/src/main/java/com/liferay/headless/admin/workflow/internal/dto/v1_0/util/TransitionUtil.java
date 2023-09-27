/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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