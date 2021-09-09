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

package com.liferay.portal.workflow.metrics.internal.model.listener;

import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.document.UpdateByQueryDocumentRequest;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.query.TermsQuery;
import com.liferay.portal.search.script.ScriptBuilder;
import com.liferay.portal.search.script.ScriptType;
import com.liferay.portal.search.script.Scripts;
import com.liferay.portal.workflow.metrics.internal.petra.executor.WorkflowMetricsPortalExecutor;
import com.liferay.portal.workflow.metrics.internal.search.index.WorkflowMetricsIndex;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Feliphe Marinho
 */
@Component(immediate = true, service = ModelListener.class)
public class UserModelListener extends BaseModelListener<User> {

	@Override
	public void onBeforeUpdate(User originalUser, User user)
		throws ModelListenerException {

		User currentUser = _userLocalService.fetchUserById(user.getUserId());

		TransactionCommitCallbackUtil.registerCallback(
			() -> {
				if (Objects.equals(
						currentUser.getFullName(), user.getFullName())) {

					return null;
				}

				_workflowMetricsPortalExecutor.execute(
					() -> {
						BooleanQuery nestedBooleanQuery =
							_queries.booleanQuery();

						TermsQuery termsQuery = _queries.terms(
							"tasks.assigneeIds");

						termsQuery.addValues(String.valueOf(user.getUserId()));

						nestedBooleanQuery.addMustQueryClauses(termsQuery);

						nestedBooleanQuery.addMustQueryClauses(
							_queries.term(
								"tasks.assigneeType", User.class.getName()));

						ScriptBuilder scriptBuilder = _scripts.builder();

						searchEngineAdapter.execute(
							new UpdateByQueryDocumentRequest(
								_queries.nested("tasks", nestedBooleanQuery),
								scriptBuilder.idOrCode(
									StringUtil.read(
										getClass(),
										"dependencies/workflow-metrics-" +
											"update-task-assignee-" +
												"script.painless")
								).language(
									"painless"
								).putParameter(
									"userId", user.getUserId()
								).putParameter(
									"userName", user.getFullName()
								).scriptType(
									ScriptType.INLINE
								).build(),
								_instanceWorkflowMetricsIndex.getIndexName(
									user.getCompanyId())));
					});

				return null;
			});
	}

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(search.engine.impl=Elasticsearch)"
	)
	protected volatile SearchEngineAdapter searchEngineAdapter;

	@Reference(target = "(workflow.metrics.index.entity.name=instance)")
	private WorkflowMetricsIndex _instanceWorkflowMetricsIndex;

	@Reference
	private Queries _queries;

	@Reference
	private Scripts _scripts;

	@Reference
	private UserLocalService _userLocalService;

	@Reference
	private WorkflowMetricsPortalExecutor _workflowMetricsPortalExecutor;

}