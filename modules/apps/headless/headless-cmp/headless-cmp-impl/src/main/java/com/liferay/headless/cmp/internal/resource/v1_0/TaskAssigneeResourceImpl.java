/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.cmp.internal.resource.v1_0;

import com.liferay.headless.cmp.dto.v1_0.TaskAssignee;
import com.liferay.headless.cmp.resource.v1_0.TaskAssigneeResource;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.portal.vulcan.pagination.Page;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Carolina Barbosa
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/task-assignee.properties",
	scope = ServiceScope.PROTOTYPE, service = TaskAssigneeResource.class
)
public class TaskAssigneeResourceImpl extends BaseTaskAssigneeResourceImpl {

	@Override
	public Page<TaskAssignee> getTaskAssigneesPage(String search)
		throws Exception {

		SearchResponse searchResponse = _searcher.search(
			_searchRequestBuilderFactory.builder(
			).companyId(
				contextCompany.getCompanyId()
			).emptySearchEnabled(
				true
			).entryClassNames(
				Role.class.getName(), User.class.getName()
			).query(
				_getBooleanQuery(search)
			).size(
				20
			).build());

		List<TaskAssignee> taskAssignees = new ArrayList<>();

		for (Document document : searchResponse.getDocuments()) {
			String name = document.getString(Field.NAME);

			String type = StringUtil.extractLast(
				document.getString(Field.ENTRY_CLASS_NAME), StringPool.PERIOD);

			if (StringUtil.equals(type, "User")) {
				name = document.getString("fullName");
			}

			if (Validator.isNull(name)) {
				continue;
			}

			String assigneeName = name;
			String assigneeType = type;

			taskAssignees.add(
				new TaskAssignee() {
					{
						setExternalReferenceCode(
							() -> document.getString("externalReferenceCode"));
						setImage(
							() -> {
								if (!StringUtil.equals(type, "User")) {
									return null;
								}

								User user = _userLocalService.fetchUser(
									document.getLong(Field.ENTRY_CLASS_PK));

								if (user == null) {
									return null;
								}

								return user.getPortraitURL(
									(ThemeDisplay)
										contextHttpServletRequest.getAttribute(
											WebKeys.THEME_DISPLAY));
							});
						setName(() -> assigneeName);
						setType(() -> assigneeType);
					}
				});
		}

		return Page.of(taskAssignees);
	}

	private BooleanQuery _getBooleanQuery(String search) {
		BooleanQuery booleanQuery = _queries.booleanQuery();

		BooleanQuery roleBooleanQuery = _queries.booleanQuery();

		Role guestRole = _roleLocalService.fetchRole(
			contextCompany.getCompanyId(), RoleConstants.GUEST);

		if (guestRole != null) {
			roleBooleanQuery.addMustNotQueryClauses(
				_queries.term(Field.ENTRY_CLASS_PK, guestRole.getRoleId()));
		}

		if (Validator.isNull(search)) {
			return booleanQuery.addShouldQueryClauses(roleBooleanQuery);
		}

		roleBooleanQuery.addMustQueryClauses(
			_queries.matchPhrasePrefix(Field.NAME, search),
			_queries.term(Field.ENTRY_CLASS_NAME, Role.class.getName()));

		BooleanQuery userBooleanQuery = _queries.booleanQuery();

		userBooleanQuery.addMustQueryClauses(
			_queries.matchPhrasePrefix("fullName", search),
			_queries.term(Field.ENTRY_CLASS_NAME, User.class.getName()));

		return booleanQuery.addShouldQueryClauses(
			roleBooleanQuery, userBooleanQuery);
	}

	@Reference
	private Queries _queries;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private Searcher _searcher;

	@Reference
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

	@Reference
	private UserLocalService _userLocalService;

}