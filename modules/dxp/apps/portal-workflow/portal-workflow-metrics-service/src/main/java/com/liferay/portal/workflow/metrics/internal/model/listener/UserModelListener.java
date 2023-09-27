/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.metrics.internal.model.listener;

import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.capabilities.SearchCapabilities;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.document.UpdateByQueryDocumentRequest;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.query.TermsQuery;
import com.liferay.portal.search.script.ScriptBuilder;
import com.liferay.portal.search.script.ScriptType;
import com.liferay.portal.search.script.Scripts;
import com.liferay.portal.workflow.metrics.internal.petra.executor.WorkflowMetricsPortalExecutor;
import com.liferay.portal.workflow.metrics.search.index.WorkflowMetricsIndex;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Feliphe Marinho
 */
@Component(service = ModelListener.class)
public class UserModelListener extends BaseModelListener<User> {

	@Override
	public void onBeforeUpdate(User originalUser, User user)
		throws ModelListenerException {

		if (!_searchCapabilities.isWorkflowMetricsSupported()) {
			return;
		}

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

	@Reference
	protected SearchEngineAdapter searchEngineAdapter;

	@Reference(target = "(workflow.metrics.index.entity.name=instance)")
	private WorkflowMetricsIndex _instanceWorkflowMetricsIndex;

	@Reference
	private Queries _queries;

	@Reference
	private Scripts _scripts;

	@Reference
	private SearchCapabilities _searchCapabilities;

	@Reference
	private UserLocalService _userLocalService;

	@Reference
	private WorkflowMetricsPortalExecutor _workflowMetricsPortalExecutor;

}