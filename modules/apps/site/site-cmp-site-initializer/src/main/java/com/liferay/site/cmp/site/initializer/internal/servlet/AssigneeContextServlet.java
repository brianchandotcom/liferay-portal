/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cmp.site.initializer.internal.servlet;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.events.EventsProcessorUtil;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.Searcher;

import jakarta.servlet.Servlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Igor Franca
 */
@Component(
	property = {
		"osgi.http.whiteboard.servlet.name=com.liferay.site.cmp.site.initializer.internal.servlet.AssigneeContextServlet",
		"osgi.http.whiteboard.servlet.pattern=/cmp/assignee-context/*",
		"servlet.init.httpMethods=GET"
	},
	service = Servlet.class
)
public class AssigneeContextServlet extends HttpServlet {

	@Override
	public void service(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException, ServletException {

		_process(httpServletRequest, httpServletResponse);

		super.service(httpServletRequest, httpServletResponse);
	}

	@Override
	protected void doGet(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		JSONArray jsonArray = _jsonFactory.createJSONArray();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Searcher searcher = _searcherSnapshot.get();

		SearchRequestBuilderFactory searchRequestBuilderFactory =
			_searchRequestBuilderFactorySnapshot.get();

		SearchResponse searchResponse = searcher.search(
			searchRequestBuilderFactory.builder(
			).companyId(
				themeDisplay.getCompanyId()
			).emptySearchEnabled(
				true
			).entryClassNames(
				Role.class.getName(), User.class.getName()
			).query(
				_getBooleanQuery(
					themeDisplay.getCompanyId(),
					ParamUtil.getString(httpServletRequest, "search"))
			).size(
				20
			).build());

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

			jsonArray.put(
				JSONUtil.put(
					"externalReferenceCode",
					document.getString("externalReferenceCode")
				).put(
					"image",
					() -> {
						if (!StringUtil.equals(type, "User")) {
							return null;
						}

						User user = _userLocalService.fetchUser(
							document.getLong(Field.ENTRY_CLASS_PK));

						if (user == null) {
							return null;
						}

						return user.getPortraitURL(themeDisplay);
					}
				).put(
					"name", name
				).put(
					"type", type
				));
		}

		httpServletResponse.setContentType(ContentTypes.APPLICATION_JSON);
		httpServletResponse.setStatus(HttpServletResponse.SC_OK);

		ServletResponseUtil.write(
			httpServletResponse,
			JSONUtil.put(
				"items", jsonArray
			).toString());
	}

	private BooleanQuery _getBooleanQuery(long companyId, String search) {
		BooleanQuery booleanQuery = _queries.booleanQuery();

		BooleanQuery roleBooleanQuery = _queries.booleanQuery();

		Role guestRole = _roleLocalService.fetchRole(
			companyId, RoleConstants.GUEST);

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

	private void _process(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		try {
			EventsProcessorUtil.process(
				PropsKeys.SERVLET_SERVICE_EVENTS_PRE,
				PropsValues.SERVLET_SERVICE_EVENTS_PRE, httpServletRequest,
				httpServletResponse);
		}
		catch (ActionException actionException) {
			if (_log.isDebugEnabled()) {
				_log.debug(actionException);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssigneeContextServlet.class);

	private static final Snapshot<Searcher> _searcherSnapshot = new Snapshot<>(
		AssigneeContextServlet.class, Searcher.class);
	private static final Snapshot<SearchRequestBuilderFactory>
		_searchRequestBuilderFactorySnapshot = new Snapshot<>(
			AssigneeContextServlet.class, SearchRequestBuilderFactory.class);

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Queries _queries;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private UserLocalService _userLocalService;

}