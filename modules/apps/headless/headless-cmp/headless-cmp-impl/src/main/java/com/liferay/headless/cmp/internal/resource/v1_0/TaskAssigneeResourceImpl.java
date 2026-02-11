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
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.portal.vulcan.pagination.Page;

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

		SearchHits searchHits = searchResponse.getSearchHits();

		return Page.of(
			transform(
				searchHits.getSearchHits(),
				searchHit -> _toTaskAssignee(searchHit.getDocument())));
	}

	private String _getAssigneeName(String assigneeType, Document document) {
		if (StringUtil.equals(assigneeType, "User")) {
			return document.getString("fullName");
		}

		return document.getString(Field.NAME);
	}

	private BooleanQuery _getBooleanQuery(String search) throws Exception {
		BooleanQuery booleanQuery = _queries.booleanQuery();

		BooleanQuery roleBooleanQuery = _queries.booleanQuery();

		Role guestRole = _roleLocalService.getRole(
			contextCompany.getCompanyId(), RoleConstants.GUEST);

		roleBooleanQuery.addMustNotQueryClauses(
			_queries.term(Field.ENTRY_CLASS_PK, guestRole.getRoleId()));

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

	private TaskAssignee _toTaskAssignee(Document document) {
		String assigneeType = StringUtil.extractLast(
			document.getString(Field.ENTRY_CLASS_NAME), StringPool.PERIOD);

		String assigneeName = _getAssigneeName(assigneeType, document);

		if (Validator.isNull(assigneeName)) {
			return null;
		}

		return new TaskAssignee() {
			{
				setExternalReferenceCode(
					() -> document.getString("externalReferenceCode"));
				setId(() -> document.getLong(Field.ENTRY_CLASS_PK));
				setName(() -> assigneeName);
				setPortrait(
					() -> {
						if (!StringUtil.equals(assigneeType, "User")) {
							return null;
						}

						User user = _userLocalService.fetchUser(
							document.getLong(Field.ENTRY_CLASS_PK));

						if ((user == null) || (user.getPortraitId() == 0)) {
							return null;
						}

						return user.getPortraitURL(
							new ThemeDisplay() {
								{
									setPathImage(_portal.getPathImage());
								}
							});
					});
				setType(() -> assigneeType);
			}
		};
	}

	@Reference
	private Portal _portal;

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