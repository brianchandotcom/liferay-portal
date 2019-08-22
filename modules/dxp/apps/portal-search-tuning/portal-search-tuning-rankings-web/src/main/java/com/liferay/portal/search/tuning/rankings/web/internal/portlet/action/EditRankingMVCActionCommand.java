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

package com.liferay.portal.search.tuning.rankings.web.internal.portlet.action;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.search.tuning.rankings.web.internal.constants.ResultRankingsPortletKeys;
import com.liferay.portal.search.tuning.rankings.web.internal.display.context.RankingEntryDisplayContextBuilder;
import com.liferay.portal.search.tuning.rankings.web.internal.index.Ranking;
import com.liferay.portal.search.tuning.rankings.web.internal.index.RankingCriteriaBuilderFactory;
import com.liferay.portal.search.tuning.rankings.web.internal.index.RankingIndexReader;
import com.liferay.portal.search.tuning.rankings.web.internal.index.RankingIndexWriter;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Kevin Tan
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ResultRankingsPortletKeys.RESULT_RANKINGS,
		"mvc.command.name=/results_ranking/edit"
	},
	service = MVCActionCommand.class
)
public class EditRankingMVCActionCommand extends BaseMVCActionCommand {

	public static final String PARAM_ALIASES = "aliases";

	public static final String PARAM_KEYWORDS = "keywords";

	protected static List<String> update(
		List<String> strings, String[] addStrings, String[] removeStrings) {

		List<String> newStrings;

		if (ListUtil.isEmpty(strings)) {
			newStrings = Arrays.asList(addStrings);
		}
		else {
			newStrings = new ArrayList<>(strings);

			Collections.addAll(newStrings, addStrings);
		}

		newStrings.removeAll(Arrays.asList(removeStrings));

		return newStrings;
	}

	protected void add(
			ActionRequest actionRequest, ActionResponse actionResponse,
			Action action)
		throws Exception {

		if (_DISALLOW_DUPLICATES &&
			_isDuplicateQueryString(actionRequest, action)) {

			SessionErrors.add(actionRequest, Exception.class);

			actionResponse.setRenderParameter("mvcPath", "/error.jsp");

			return;
		}

		Ranking ranking = addRanking(
			actionRequest, getIndexName(actionRequest, action._indexParam));

		String redirect = getSaveAndContinueRedirect(
			actionRequest, ranking, action._redirect);

		sendRedirect(actionRequest, actionResponse, redirect);
	}

	protected Ranking addRanking(ActionRequest actionRequest, String index) {
		Ranking.RankingBuilder rankingBuilder = new Ranking.RankingBuilder();

		String resultActionCmd = ParamUtil.getString(
			actionRequest, "resultActionCmd");
		String resultActionUid = ParamUtil.getString(
			actionRequest, "resultActionUid");

		if (!resultActionCmd.isEmpty() && !resultActionUid.isEmpty()) {
			if (resultActionCmd.equals(SearchRankingConstants.PIN)) {
				rankingBuilder.pins(
					Arrays.asList(new Ranking.Pin(0, resultActionUid)));
			}
			else {
				rankingBuilder.blocks(ListUtil.fromString(resultActionUid));
			}
		}

		String name = _getName(actionRequest);

		rankingBuilder.index(
			index
		).name(
			name
		).queryStrings(
			Arrays.asList(name)
		);

		String id = rankingIndexWriter.create(rankingBuilder.build());

		Optional<Ranking> optional = rankingIndexReader.fetchOptional(id);

		return optional.get();
	}

	protected void delete(
			ActionRequest actionRequest, ActionResponse actionResponse,
			Action action)
		throws IOException {

		String resultsRankingUid = action._resultsRankingUid;

		String[] deleteResultsRankingUids = null;

		if (Validator.isNotNull(resultsRankingUid)) {
			deleteResultsRankingUids = new String[] {resultsRankingUid};
		}
		else {
			deleteResultsRankingUids = ParamUtil.getStringValues(
				actionRequest, "rowIds");
		}

		for (String deleteResultsRankingUid : deleteResultsRankingUids) {
			deleteRanking(deleteResultsRankingUid);
		}

		sendRedirect(actionRequest, actionResponse, action._redirect);
	}

	protected void deleteRanking(String resultsRankingUid) {
		rankingIndexWriter.remove(resultsRankingUid);
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		Action action = new Action(actionRequest);

		if (action.isCmd(Constants.ADD)) {
			add(actionRequest, actionResponse, action);
		}
		else if (action.isCmd(Constants.UPDATE)) {
			update(actionRequest, actionResponse, action);
		}
		else if (action.isCmd(Constants.DELETE)) {
			delete(actionRequest, actionResponse, action);
		}
	}

	protected String getIndexName(
		ActionRequest actionRequest, String indexParam) {

		String index;

		if (Validator.isBlank(indexParam)) {
			long companyId = portal.getCompanyId(actionRequest);

			index = "liferay-" + companyId;
		}
		else {
			index = indexParam;
		}

		return index;
	}

	protected String getSaveAndContinueRedirect(
			ActionRequest actionRequest, Ranking ranking, String redirect)
		throws Exception {

		PortletConfig portletConfig = (PortletConfig)actionRequest.getAttribute(
			JavaConstants.JAVAX_PORTLET_CONFIG);

		LiferayPortletURL portletURL = PortletURLFactoryUtil.create(
			actionRequest, portletConfig.getPortletName(),
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter(
			"mvcRenderCommandName", "editResultsRankingEntry");
		portletURL.setParameter(Constants.CMD, Constants.UPDATE, false);
		portletURL.setParameter("redirect", redirect, false);
		portletURL.setParameter("resultsRankingUid", ranking.getId(), false);
		portletURL.setParameter(
			PARAM_ALIASES,
			StringUtil.merge(ranking.getQueryStrings(), StringPool.COMMA),
			false);
		portletURL.setParameter(PARAM_KEYWORDS, ranking.getName(), false);
		portletURL.setWindowState(actionRequest.getWindowState());

		return portletURL.toString();
	}

	protected void update(
			ActionRequest actionRequest, ActionResponse actionResponse,
			Action action)
		throws IOException {

		if (_DISALLOW_DUPLICATES && _isDuplicateAlias(actionRequest)) {
			SessionErrors.add(actionRequest, Exception.class);

			actionResponse.setRenderParameter("mvcPath", "/error.jsp");

			return;
		}

		updateRanking(actionRequest);

		sendRedirect(actionRequest, actionResponse, action._redirect);
	}

	protected void updateRanking(ActionRequest actionRequest) {
		String id = ParamUtil.getString(actionRequest, "resultsRankingUid");

		Optional<Ranking> optional = rankingIndexReader.fetchOptional(id);

		optional.ifPresent(ranking -> updateRanking(actionRequest, ranking));
	}

	protected void updateRanking(ActionRequest actionRequest, Ranking ranking) {
		Ranking.RankingBuilder rankingBuilder = new Ranking.RankingBuilder(
			ranking);

		String[] hiddenIdsAdded = ParamUtil.getStringValues(
			actionRequest, "hiddenIdsAdded");
		String[] hiddenIdsRemoved = ParamUtil.getStringValues(
			actionRequest, "hiddenIdsRemoved");

		rankingBuilder.blocks(
			update(ranking.getBlockIds(), hiddenIdsAdded, hiddenIdsRemoved)
		).inactive(
			_isInactive(actionRequest)
		).name(
			_getNameForUpdate(actionRequest, ranking.getName())
		).queryStrings(
			_getQueryStrings(actionRequest)
		);

		List<Ranking.Pin> pins = new ArrayList<>();

		String[] pinnedIds = ParamUtil.getStringValues(
			actionRequest, "pinnedIds");

		for (int i = 0; i < pinnedIds.length; i++) {
			pins.add(new Ranking.Pin(i, pinnedIds[i]));
		}

		if (ListUtil.isNotEmpty(pins)) {
			rankingBuilder.pins(pins);
		}
		else {
			rankingBuilder.pins(null);
		}

		_setInactive(actionRequest, rankingBuilder);

		rankingIndexWriter.update(rankingBuilder.build());
	}

	@Reference
	protected Portal portal;

	@Reference
	protected RankingCriteriaBuilderFactory rankingCriteriaBuilderFactory;

	@Reference
	protected RankingIndexReader rankingIndexReader;

	@Reference
	protected RankingIndexWriter rankingIndexWriter;

	protected static class Action {

		public boolean isCmd(String cmd) {
			return Objects.equals(cmd, _cmd);
		}

		private Action(ActionRequest actionRequest) {
			_cmd = ParamUtil.getString(actionRequest, Constants.CMD);
			_redirect = ParamUtil.getString(actionRequest, "redirect");
			_indexParam = ParamUtil.getString(actionRequest, "index-name");
			_queryString = ParamUtil.getString(
				actionRequest, EditRankingMVCActionCommand.PARAM_KEYWORDS);
			_resultsRankingUid = ParamUtil.getString(
				actionRequest, "resultsRankingUid");
		}

		private final String _cmd;
		private final String _indexParam;
		private final String _queryString;
		private final String _redirect;
		private final String _resultsRankingUid;

	}

	private String _asNameUpdate(String string) {
		return string.replace(_UPDATE_NAME_INDICATOR, StringPool.BLANK);
	}

	private List<String> _getAliasesParamValues(ActionRequest actionRequest) {
		return new ArrayList<>(
			Arrays.asList(
				ParamUtil.getStringValues(
					actionRequest, EditRankingMVCActionCommand.PARAM_ALIASES)));
	}

	private String _getName(ActionRequest actionRequest) {
		return ParamUtil.getString(
			actionRequest, EditRankingMVCActionCommand.PARAM_KEYWORDS);
	}

	private String _getNameForUpdate(
		ActionRequest actionRequest, String oldName) {

		List<String> strings = _getAliasesParamValues(actionRequest);

		Stream<String> stream = strings.stream();

		return stream.filter(
			this::_isNameUpdate
		).map(
			this::_asNameUpdate
		).findAny(
		).orElse(
			oldName
		);
	}

	private List<String> _getQueryStrings(ActionRequest actionRequest) {
		List<String> strings = _getAliasesParamValues(actionRequest);

		strings.remove(RankingEntryDisplayContextBuilder.INACTIVE_INDICATOR);

		Predicate<String> predicate = this::_isNameUpdate;

		Stream<String> stream = strings.stream();

		return stream.filter(
			predicate.negate()
		).collect(
			Collectors.toList()
		);
	}

	private boolean _isDuplicateAlias(ActionRequest actionRequest) {
		String index = ParamUtil.getString(actionRequest, "index-name");

		if (Validator.isBlank(index)) {
			long companyId = portal.getCompanyId(actionRequest);

			index = "liferay-" + companyId;
		}

		List<String> aliases = _getQueryStrings(actionRequest);

		if (aliases.isEmpty()) {
			return false;
		}

		String resultsRankingUid = ParamUtil.getString(
			actionRequest, "resultsRankingUid");

		return rankingIndexReader.exists(
			rankingCriteriaBuilderFactory.builder(
			).aliases(
				ArrayUtil.toStringArray(aliases)
			).index(
				index
			).id(
				resultsRankingUid
			).build());
	}

	private boolean _isDuplicateQueryString(
		ActionRequest actionRequest, Action action) {

		String index = action._indexParam;

		if (Validator.isBlank(index)) {
			long companyId = portal.getCompanyId(actionRequest);

			index = "liferay-" + companyId;
		}

		return rankingIndexReader.exists(
			rankingCriteriaBuilderFactory.builder(
			).index(
				index
			).queryString(
				action._queryString
			).build());
	}

	private boolean _isInactive(ActionRequest actionRequest) {
		List<String> strings = _getAliasesParamValues(actionRequest);

		return strings.contains(
			RankingEntryDisplayContextBuilder.INACTIVE_INDICATOR);
	}

	private boolean _isNameUpdate(String string) {
		return string.startsWith(_UPDATE_NAME_INDICATOR);
	}

	private void _setInactive(
		ActionRequest actionRequest, Ranking.RankingBuilder rankingBuilder) {

		int workflowAction = ParamUtil.getInteger(
			actionRequest, "workflowAction", WorkflowConstants.ACTION_PUBLISH);

		if (workflowAction == WorkflowConstants.ACTION_SAVE_DRAFT) {
			rankingBuilder.inactive(true);
		}
		else {
			rankingBuilder.inactive(false);
		}
	}

	private static final boolean _DISALLOW_DUPLICATES = false;

	private static final String _UPDATE_NAME_INDICATOR = StringPool.UNDERLINE;

}