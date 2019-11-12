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
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.tuning.rankings.web.internal.configuration.DefaultResultRankingsConfiguration;
import com.liferay.portal.search.tuning.rankings.web.internal.configuration.ResultRankingsConfiguration;
import com.liferay.portal.search.tuning.rankings.web.internal.constants.ResultRankingsPortletKeys;
import com.liferay.portal.search.tuning.rankings.web.internal.index.DuplicateQueryStringsDetector;
import com.liferay.portal.search.tuning.rankings.web.internal.index.Ranking;
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

	@Override
	public void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		EditRankingMVCActionRequest editRankingMVCActionRequest =
			new EditRankingMVCActionRequest(actionRequest);

		if (editRankingMVCActionRequest.isCmd(Constants.ADD)) {
			add(actionRequest, actionResponse, editRankingMVCActionRequest);
		}
		else if (editRankingMVCActionRequest.isCmd(Constants.UPDATE)) {
			update(actionRequest, actionResponse, editRankingMVCActionRequest);
		}
		else if (editRankingMVCActionRequest.isCmd(Constants.DELETE)) {
			delete(actionRequest, actionResponse, editRankingMVCActionRequest);
		}
	}

	protected void add(
			ActionRequest actionRequest, ActionResponse actionResponse,
			EditRankingMVCActionRequest editRankingMVCActionRequest)
		throws Exception {

		try {
			Ranking ranking = doAdd(actionRequest, editRankingMVCActionRequest);

			String redirect = getSaveAndContinueRedirect(
				actionRequest, ranking,
				editRankingMVCActionRequest.getRedirect());

			sendRedirect(actionRequest, actionResponse, redirect);
		}
		catch (DuplicateQueryStringException dqse) {
			SessionErrors.add(actionRequest, Exception.class);

			actionResponse.setRenderParameter("mvcPath", "/error.jsp");
		}
	}

	protected void delete(
			ActionRequest actionRequest, ActionResponse actionResponse,
			EditRankingMVCActionRequest editRankingMVCActionRequest)
		throws IOException {

		doDelete(actionRequest, editRankingMVCActionRequest);

		sendRedirect(
			actionRequest, actionResponse,
			editRankingMVCActionRequest.getRedirect());
	}

	protected Ranking doAdd(
		ActionRequest actionRequest,
		EditRankingMVCActionRequest editRankingMVCActionRequest) {

		guardDuplicateQueryString(actionRequest, editRankingMVCActionRequest);

		Ranking.RankingBuilder rankingBuilder = new Ranking.RankingBuilder();

		String resultActionCmd = ParamUtil.getString(
			actionRequest, "resultActionCmd");
		String resultActionUid = ParamUtil.getString(
			actionRequest, "resultActionUid");

		if (!resultActionCmd.isEmpty() && !resultActionUid.isEmpty()) {
			if (resultActionCmd.equals("pin")) {
				rankingBuilder.pins(
					Arrays.asList(new Ranking.Pin(0, resultActionUid)));
			}
			else {
				rankingBuilder.blocks(ListUtil.fromString(resultActionUid));
			}
		}

		rankingBuilder.index(
			getIndexName(
				actionRequest, editRankingMVCActionRequest.getIndexName())
		).name(
			editRankingMVCActionRequest.getQueryString()
		).queryString(
			editRankingMVCActionRequest.getQueryString()
		);

		String id = rankingIndexWriter.create(rankingBuilder.build());

		Optional<Ranking> optional = rankingIndexReader.fetchOptional(id);

		return optional.get();
	}

	protected void doDelete(
		ActionRequest actionRequest,
		EditRankingMVCActionRequest editRankingMVCActionRequest) {

		String resultsRankingUid =
			editRankingMVCActionRequest.getResultsRankingUid();

		String[] deleteResultsRankingUids = null;

		if (Validator.isNotNull(resultsRankingUid)) {
			deleteResultsRankingUids = new String[] {resultsRankingUid};
		}
		else {
			deleteResultsRankingUids = ParamUtil.getStringValues(
				actionRequest, "rowIds");
		}

		for (String deleteResultsRankingUid : deleteResultsRankingUids) {
			rankingIndexWriter.remove(deleteResultsRankingUid);
		}
	}

	protected void doUpdate(
		ActionRequest actionRequest,
		EditRankingMVCActionRequest editRankingMVCActionRequest) {

		guardDuplicateAliases(actionRequest, editRankingMVCActionRequest);
		guardDuplicateQueryString(actionRequest, editRankingMVCActionRequest);

		String id = editRankingMVCActionRequest.getResultsRankingUid();

		Optional<Ranking> optional = rankingIndexReader.fetchOptional(id);

		if (!optional.isPresent()) {
			return;
		}

		Ranking ranking = optional.get();

		Ranking.RankingBuilder rankingBuilder = new Ranking.RankingBuilder(
			ranking);

		String[] hiddenIdsAdded = ParamUtil.getStringValues(
			actionRequest, "hiddenIdsAdded");
		String[] hiddenIdsRemoved = ParamUtil.getStringValues(
			actionRequest, "hiddenIdsRemoved");

		rankingBuilder.aliases(
			_getAliases(editRankingMVCActionRequest)
		).blocks(
			_update(ranking.getBlockIds(), hiddenIdsAdded, hiddenIdsRemoved)
		).inactive(
			_isInactive(editRankingMVCActionRequest)
		).index(
			_getIndexName(actionRequest, editRankingMVCActionRequest)
		).name(
			_getNameForUpdate(ranking.getName(), editRankingMVCActionRequest)
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

		rankingIndexWriter.update(rankingBuilder.build());
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
			EditRankingMVCActionRequest.PARAM_ALIASES,
			StringUtil.merge(ranking.getAliases(), StringPool.COMMA), false);
		portletURL.setParameter(
			EditRankingMVCActionRequest.PARAM_KEYWORDS,
			ranking.getQueryString(), false);
		portletURL.setWindowState(actionRequest.getWindowState());

		return portletURL.toString();
	}

	protected void guardDuplicateAliases(
		ActionRequest actionRequest,
		EditRankingMVCActionRequest editRankingMVCActionRequest) {

		_guardDuplicateQueryStrings(
			actionRequest, editRankingMVCActionRequest,
			_getAliases(editRankingMVCActionRequest));
	}

	protected void guardDuplicateQueryString(
		ActionRequest actionRequest,
		EditRankingMVCActionRequest editRankingMVCActionRequest) {

		_guardDuplicateQueryStrings(
			actionRequest, editRankingMVCActionRequest,
			Arrays.asList(editRankingMVCActionRequest.getQueryString()));
	}

	protected void update(
			ActionRequest actionRequest, ActionResponse actionResponse,
			EditRankingMVCActionRequest editRankingMVCActionRequest)
		throws IOException {

		try {
			doUpdate(actionRequest, editRankingMVCActionRequest);

			sendRedirect(
				actionRequest, actionResponse,
				editRankingMVCActionRequest.getRedirect());
		}
		catch (DuplicateQueryStringException dqse) {
			SessionErrors.add(actionRequest, Exception.class);

			actionResponse.setRenderParameter("mvcPath", "/error.jsp");
		}
	}

	@Reference
	protected DuplicateQueryStringsDetector duplicateQueryStringsDetector;

	@Reference
	protected Portal portal;

	@Reference
	protected RankingIndexReader rankingIndexReader;

	@Reference
	protected RankingIndexWriter rankingIndexWriter;

	private static List<String> _update(
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

	private boolean _detectedDuplicateQueryStrings(
		ActionRequest actionRequest,
		EditRankingMVCActionRequest editRankingMVCActionRequest,
		List<String> queryStrings) {

		String index = _getIndexName(
			actionRequest, editRankingMVCActionRequest);

		String resultsRankingUid = ParamUtil.getString(
			actionRequest, "resultsRankingUid");

		if (duplicateQueryStringsDetector.detect(
				duplicateQueryStringsDetector.builder().index(
					index).queryStrings(
						queryStrings).unlessRankingId(
							resultsRankingUid).build())) {

			return true;
		}

		return false;
	}

	private List<String> _getAliases(
		EditRankingMVCActionRequest editRankingMVCActionRequest) {

		List<String> strings = new ArrayList<>(
			editRankingMVCActionRequest.getAliases());

		Stream<String> stream = strings.stream();

		Predicate<String> predicate = this::_isUpdateSpecial;

		return stream.filter(
			predicate.negate()
		).collect(
			Collectors.toList()
		);
	}

	private String _getIndexName(
		ActionRequest actionRequest,
		EditRankingMVCActionRequest editRankingMVCActionRequest) {

		String index = editRankingMVCActionRequest.getIndexName();

		if (Validator.isBlank(index)) {
			long companyId = portal.getCompanyId(actionRequest);

			index = "liferay-" + companyId;
		}

		return index;
	}

	private String _getNameForUpdate(
		String oldName,
		EditRankingMVCActionRequest editRankingMVCActionRequest) {

		List<String> strings = editRankingMVCActionRequest.getAliases();

		Stream<String> stream = strings.stream();

		return stream.filter(
			this::_isUpdateSpecial
		).map(
			this::_stripUpdateSpecial
		).findAny(
		).orElse(
			oldName
		);
	}

	private void _guardDuplicateQueryStrings(
		ActionRequest actionRequest,
		EditRankingMVCActionRequest editRankingMVCActionRequest,
		List<String> queryStrings) {

		boolean inactive = ParamUtil.getBoolean(actionRequest, "inactive");

		if (_resultRankingsConfiguration.allowDuplicateQueryStrings() ||
			inactive) {

			return;
		}

		if (_detectedDuplicateQueryStrings(
				actionRequest, editRankingMVCActionRequest, queryStrings)) {

			throw new DuplicateQueryStringException();
		}
	}

	private boolean _isInactive(
		EditRankingMVCActionRequest editRankingMVCActionRequest) {

		return editRankingMVCActionRequest.getInactive();
	}

	private boolean _isUpdateSpecial(String string) {
		return string.startsWith(_UPDATE_SPECIAL);
	}

	private String _stripUpdateSpecial(String string) {
		return string.substring(_UPDATE_SPECIAL.length());
	}

	private static final String _UPDATE_SPECIAL = StringPool.GREATER_THAN;

	private final ResultRankingsConfiguration _resultRankingsConfiguration =
		new DefaultResultRankingsConfiguration();

	private class DuplicateQueryStringException extends RuntimeException {
	}

	private class EditRankingMVCActionRequest {

		public static final String PARAM_ALIASES = "aliases";

		public static final String PARAM_KEYWORDS = "keywords";

		public EditRankingMVCActionRequest(ActionRequest actionRequest) {
			_cmd = ParamUtil.getString(actionRequest, Constants.CMD);
			_redirect = ParamUtil.getString(actionRequest, "redirect");
			_inactive = ParamUtil.getBoolean(actionRequest, "inactive");
			_indexName = ParamUtil.getString(actionRequest, "index-name");
			_queryString = ParamUtil.getString(actionRequest, PARAM_KEYWORDS);
			_resultsRankingUid = ParamUtil.getString(
				actionRequest, "resultsRankingUid");

			_aliases = Arrays.asList(
				ParamUtil.getStringValues(actionRequest, PARAM_ALIASES));
		}

		public List<String> getAliases() {
			return Collections.unmodifiableList(_aliases);
		}

		public boolean getInactive() {
			return _inactive;
		}

		public String getIndexName() {
			return _indexName;
		}

		public String getQueryString() {
			return _queryString;
		}

		public String getRedirect() {
			return _redirect;
		}

		public String getResultsRankingUid() {
			return _resultsRankingUid;
		}

		public boolean isCmd(String cmd) {
			return Objects.equals(cmd, _cmd);
		}

		private final List<String> _aliases;
		private final String _cmd;
		private final boolean _inactive;
		private final String _indexName;
		private final String _queryString;
		private final String _redirect;
		private final String _resultsRankingUid;

	}

}