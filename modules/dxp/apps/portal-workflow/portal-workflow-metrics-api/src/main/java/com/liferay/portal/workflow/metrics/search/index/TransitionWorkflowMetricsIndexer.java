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

package com.liferay.portal.workflow.metrics.search.index;

import com.liferay.portal.search.document.Document;
import com.liferay.portal.workflow.metrics.model.AddTransitionRequest;
import com.liferay.portal.workflow.metrics.model.DeleteTransitionRequest;

/**
 * @author Rafael Praxedes
 */
public interface TransitionWorkflowMetricsIndexer {

	public Document addTransition(AddTransitionRequest addTransitionRequest);

	public void deleteTransition(
		DeleteTransitionRequest deleteTransitionRequest);

}