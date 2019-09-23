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

package com.liferay.segments.experiment.web.internal.display.context;

import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.segments.constants.SegmentsExperienceConstants;
import com.liferay.segments.constants.SegmentsExperimentConstants;
import com.liferay.segments.experiment.web.internal.configuration.SegmentsExperimentConfiguration;
import com.liferay.segments.experiment.web.internal.util.SegmentsExperimentUtil;
import com.liferay.segments.experiment.web.internal.util.comparator.SegmentsExperimentModifiedDateComparator;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.model.SegmentsExperiment;
import com.liferay.segments.model.SegmentsExperimentRel;
import com.liferay.segments.service.SegmentsExperienceService;
import com.liferay.segments.service.SegmentsExperimentRelService;
import com.liferay.segments.service.SegmentsExperimentService;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Stream;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eduardo García
 */
public class SegmentsExperimentDisplayContext {

	public SegmentsExperimentDisplayContext(
		HttpServletRequest httpServletRequest,
		LayoutLocalService layoutLocalService, Portal portal,
		RenderResponse renderResponse,
		SegmentsExperienceService segmentsExperienceService,
		SegmentsExperimentConfiguration segmentsExperimentConfiguration,
		SegmentsExperimentRelService segmentsExperimentRelService,
		SegmentsExperimentService segmentsExperimentService) {

		_httpServletRequest = httpServletRequest;
		_layoutLocalService = layoutLocalService;
		_portal = portal;
		_renderResponse = renderResponse;
		_segmentsExperienceService = segmentsExperienceService;
		_segmentsExperimentConfiguration = segmentsExperimentConfiguration;
		_segmentsExperimentRelService = segmentsExperimentRelService;
		_segmentsExperimentService = segmentsExperimentService;

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public String getAssetsPath() {
		return PortalUtil.getPathContext(_httpServletRequest) + "/assets";
	}

	public String getCalculateSegmentsExperimentEstimatedDurationURL() {
		return _getSegmentsExperimentActionURL(
			"/calculate_segments_experiment_estimated_duration");
	}

	public String getContentPageEditorPortletNamespace() {
		return _portal.getPortletNamespace(
			ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET);
	}

	public String getCreateSegmentsExperimentURL() {
		return _getSegmentsExperimentActionURL("/add_segments_experiment");
	}

	public String getCreateSegmentsVariantURL() {
		return _getContentPageEditorActionURL(
			"/content_layout/add_segments_experience");
	}

	public String getDeleteSegmentsExperimentURL() {
		return _getSegmentsExperimentActionURL("/delete_segments_experiment");
	}

	public String getDeleteSegmentsVariantURL() {
		return _getSegmentsExperimentActionURL(
			"/delete_segments_experiment_rel");
	}

	public String getEditSegmentsExperimentStatusURL() {
		return _getSegmentsExperimentActionURL(
			"/edit_segments_experiment_status");
	}

	public String getEditSegmentsExperimentURL() {
		return _getSegmentsExperimentActionURL("/edit_segments_experiment");
	}

	public String getEditSegmentsVariantLayoutURL() throws PortalException {
		Layout layout = _themeDisplay.getLayout();

		Layout draftLayout = _layoutLocalService.fetchLayout(
			_portal.getClassNameId(Layout.class), layout.getPlid());

		if (draftLayout == null) {
			return StringPool.BLANK;
		}

		String layoutFullURL = PortalUtil.getLayoutFullURL(
			draftLayout, _themeDisplay);

		layoutFullURL = HttpUtil.setParameter(
			layoutFullURL, "redirect", layoutFullURL);

		layoutFullURL = HttpUtil.setParameter(
			layoutFullURL, "p_l_mode", Constants.EDIT);

		return HttpUtil.setParameter(
			layoutFullURL, "p_l_back_url", _themeDisplay.getURLCurrent());
	}

	public String getEditSegmentsVariantURL() {
		return _getSegmentsExperimentActionURL("/edit_segments_experiment_rel");
	}

	public JSONArray getHistorySegmentsExperimentsJSONArray(Locale locale)
		throws PortalException {

		Layout layout = _themeDisplay.getLayout();

		List<SegmentsExperiment> segmentsExperiments =
			_segmentsExperimentService.getSegmentsExperiments(
				getSelectedSegmentsExperienceId(),
				_portal.getClassNameId(Layout.class), layout.getPlid(),
				SegmentsExperimentConstants.Status.
					getNonexclusiveStatusValues(),
				new SegmentsExperimentModifiedDateComparator());

		JSONArray segmentsExperimentsJSONArray =
			JSONFactoryUtil.createJSONArray();

		if (ListUtil.isEmpty(segmentsExperiments)) {
			return segmentsExperimentsJSONArray;
		}

		for (SegmentsExperiment segmentsExperiment : segmentsExperiments) {
			segmentsExperimentsJSONArray.put(
				SegmentsExperimentUtil.toSegmentsExperimentJSONObject(
					locale, segmentsExperiment));
		}

		return segmentsExperimentsJSONArray;
	}

	public String getRunSegmentsExperimenttURL() {
		return _getSegmentsExperimentActionURL("/run_segments_experiment");
	}

	public JSONArray getSegmentsExperiencesJSONArray(Locale locale)
		throws PortalException {

		List<SegmentsExperience> segmentsExperiences =
			_segmentsExperienceService.getSegmentsExperiences(
				_themeDisplay.getScopeGroupId(),
				_portal.getClassNameId(Layout.class), _themeDisplay.getPlid(),
				true);

		JSONArray segmentsExperiencesJSONArray =
			JSONFactoryUtil.createJSONArray();

		for (SegmentsExperience segmentsExperience : segmentsExperiences) {
			segmentsExperiencesJSONArray.put(
				JSONUtil.put(
					"name", segmentsExperience.getName(locale)
				).put(
					"segmentsExperienceId",
					String.valueOf(segmentsExperience.getSegmentsExperienceId())
				).put(
					"segmentsExperiment",
					SegmentsExperimentUtil.toSegmentsExperimentJSONObject(
						locale,
						_getActiveSegmentsExperimentOptional(
							segmentsExperience.getSegmentsExperienceId()
						).orElse(
							null
						))
				));
		}

		segmentsExperiencesJSONArray.put(
			JSONUtil.put(
				"name",
				SegmentsExperienceConstants.getDefaultSegmentsExperienceName(
					locale)
			).put(
				"segmentsExperienceId",
				String.valueOf(SegmentsExperienceConstants.ID_DEFAULT)
			).put(
				"segmentsExperiment",
				SegmentsExperimentUtil.toSegmentsExperimentJSONObject(
					locale,
					_getActiveSegmentsExperimentOptional(
						SegmentsExperienceConstants.ID_DEFAULT
					).orElse(
						null
					))
			));

		return segmentsExperiencesJSONArray;
	}

	public JSONArray getSegmentsExperimentGoalsJSONArray(Locale locale) {
		JSONArray segmentsExperimentGoalsJSONArray =
			JSONFactoryUtil.createJSONArray();

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		String[] goalsEnabled = _segmentsExperimentConfiguration.goalsEnabled();

		Stream<SegmentsExperimentConstants.Goal> stream = Arrays.stream(
			SegmentsExperimentConstants.Goal.values());

		stream.filter(
			goal -> ArrayUtil.contains(goalsEnabled, goal.name())
		).forEach(
			goal -> segmentsExperimentGoalsJSONArray.put(
				JSONUtil.put(
					"label", LanguageUtil.get(resourceBundle, goal.getLabel())
				).put(
					"value", goal.getLabel()
				))
		);

		return segmentsExperimentGoalsJSONArray;
	}

	public JSONObject getSegmentsExperimentJSONObject(Locale locale)
		throws PortalException {

		return SegmentsExperimentUtil.toSegmentsExperimentJSONObject(
			locale, _getSegmentsExperiment());
	}

	public JSONArray getSegmentsExperimentRelsJSONArray(Locale locale)
		throws PortalException {

		SegmentsExperiment segmentsExperiment = _getSegmentsExperiment();

		JSONArray segmentsExperimentRelsJSONArray =
			JSONFactoryUtil.createJSONArray();

		if (segmentsExperiment == null) {
			return segmentsExperimentRelsJSONArray;
		}

		List<SegmentsExperimentRel> segmentsExperimentRels =
			_segmentsExperimentRelService.getSegmentsExperimentRels(
				segmentsExperiment.getSegmentsExperimentId());

		for (SegmentsExperimentRel segmentsExperimentRel :
				segmentsExperimentRels) {

			segmentsExperimentRelsJSONArray.put(
				SegmentsExperimentUtil.toSegmentsExperimentRelJSONObject(
					locale, segmentsExperimentRel));
		}

		return segmentsExperimentRelsJSONArray;
	}

	public long getSelectedSegmentsExperienceId() throws PortalException {
		if (Validator.isNotNull(_segmentsExperienceId)) {
			return _segmentsExperienceId;
		}

		_segmentsExperienceId = _getRequestSegmentsExperienceId();

		if (_segmentsExperienceId == -1) {
			_segmentsExperienceId = SegmentsExperienceConstants.ID_DEFAULT;
		}

		return _segmentsExperienceId;
	}

	public String getViewSegmentsExperimentDetailsURL() throws PortalException {
		SegmentsExperiment segmentsExperiment = _getSegmentsExperiment();

		if (segmentsExperiment == null) {
			return StringPool.BLANK;
		}

		String asahFaroURL = PrefsPropsUtil.getString(
			segmentsExperiment.getCompanyId(), "liferayAnalyticsURL");

		if (Validator.isNull(asahFaroURL)) {
			return StringPool.BLANK;
		}

		return asahFaroURL + "/tests/overview/" +
			segmentsExperiment.getSegmentsExperimentKey();
	}

	public String getWinnerSegmentsExperienceId() {
		if (_segmentsExperiment == null) {
			return StringPool.BLANK;
		}

		long winnerSegmentsExperienceId =
			_segmentsExperiment.getWinnerSegmentsExperienceId();

		if (winnerSegmentsExperienceId == -1) {
			return StringPool.BLANK;
		}

		return String.valueOf(winnerSegmentsExperienceId);
	}

	private Optional<SegmentsExperiment> _getActiveSegmentsExperimentOptional(
			long segmentsExperienceId)
		throws PortalException {

		Layout layout = _themeDisplay.getLayout();

		return Optional.ofNullable(
			_segmentsExperimentService.fetchSegmentsExperiment(
				segmentsExperienceId, _portal.getClassNameId(Layout.class),
				layout.getPlid(),
				SegmentsExperimentConstants.Status.getExclusiveStatusValues()));
	}

	private String _getContentPageEditorActionURL(String action) {
		LiferayPortletResponse liferayPortletResponse =
			_portal.getLiferayPortletResponse(_renderResponse);

		PortletURL actionURL = liferayPortletResponse.createActionURL(
			ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET);

		actionURL.setParameter(ActionRequest.ACTION_NAME, action);

		return HttpUtil.addParameter(
			actionURL.toString(), "p_l_mode", Constants.EDIT);
	}

	private long _getRequestSegmentsExperienceId() throws PortalException {
		HttpServletRequest originalHttpServletRequest =
			_portal.getOriginalServletRequest(_httpServletRequest);

		long segmentsExperienceId = ParamUtil.getLong(
			originalHttpServletRequest, "segmentsExperienceId", -1);

		if (segmentsExperienceId != -1) {
			return segmentsExperienceId;
		}

		String segmentsExperienceKey = ParamUtil.getString(
			originalHttpServletRequest, "segmentsExperienceKey");

		return _getSegmentsExperienceId(
			_themeDisplay.getScopeGroupId(), segmentsExperienceKey);
	}

	private String _getRequestSegmentsExperimentKey() {
		HttpServletRequest originalHttpServletRequest =
			_portal.getOriginalServletRequest(_httpServletRequest);

		return ParamUtil.getString(
			originalHttpServletRequest, "segmentsExperimentKey");
	}

	private long _getSegmentsExperienceId(
			long groupId, String segmentsExperienceKey)
		throws PortalException {

		if (Objects.equals(
				segmentsExperienceKey,
				SegmentsExperienceConstants.KEY_DEFAULT)) {

			return SegmentsExperienceConstants.ID_DEFAULT;
		}

		if (Validator.isNotNull(segmentsExperienceKey)) {
			SegmentsExperience segmentsExperience =
				_segmentsExperienceService.fetchSegmentsExperience(
					groupId, segmentsExperienceKey);

			if (segmentsExperience != null) {
				return segmentsExperience.getSegmentsExperienceId();
			}
		}

		return -1;
	}

	private SegmentsExperiment _getSegmentsExperiment() throws PortalException {
		if (_segmentsExperiment != null) {
			return _segmentsExperiment;
		}

		long requestSegmentsExperienceId = _getRequestSegmentsExperienceId();

		if (requestSegmentsExperienceId != -1) {
			_segmentsExperiment = _getActiveSegmentsExperimentOptional(
				getSelectedSegmentsExperienceId()
			).orElse(
				null
			);

			return _segmentsExperiment;
		}

		String requestSegmentsExperimentKey =
			_getRequestSegmentsExperimentKey();

		if (Validator.isNotNull(requestSegmentsExperimentKey)) {
			SegmentsExperiment segmentsExperiment =
				_segmentsExperimentService.fetchSegmentsExperiment(
					_themeDisplay.getScopeGroupId(),
					requestSegmentsExperimentKey);

			if (segmentsExperiment != null) {
				_segmentsExperiment = segmentsExperiment;

				return _segmentsExperiment;
			}
		}

		_segmentsExperiment = _getActiveSegmentsExperimentOptional(
			getSelectedSegmentsExperienceId()
		).orElse(
			null
		);

		return _segmentsExperiment;
	}

	private String _getSegmentsExperimentActionURL(String action) {
		PortletURL actionURL = _renderResponse.createActionURL();

		actionURL.setParameter(ActionRequest.ACTION_NAME, action);

		return HttpUtil.addParameter(
			actionURL.toString(), "p_l_mode", Constants.VIEW);
	}

	private final HttpServletRequest _httpServletRequest;
	private final LayoutLocalService _layoutLocalService;
	private final Portal _portal;
	private final RenderResponse _renderResponse;
	private Long _segmentsExperienceId;
	private final SegmentsExperienceService _segmentsExperienceService;
	private SegmentsExperiment _segmentsExperiment;
	private final SegmentsExperimentConfiguration
		_segmentsExperimentConfiguration;
	private final SegmentsExperimentRelService _segmentsExperimentRelService;
	private final SegmentsExperimentService _segmentsExperimentService;
	private final ThemeDisplay _themeDisplay;

}