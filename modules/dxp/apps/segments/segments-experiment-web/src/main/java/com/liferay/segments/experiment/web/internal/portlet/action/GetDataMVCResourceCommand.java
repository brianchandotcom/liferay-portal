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

package com.liferay.segments.experiment.web.internal.portlet.action;

import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.segments.constants.SegmentsExperimentConstants;
import com.liferay.segments.constants.SegmentsPortletKeys;
import com.liferay.segments.experiment.web.internal.configuration.SegmentsExperimentConfiguration;
import com.liferay.segments.experiment.web.internal.util.SegmentsExperimentUtil;
import com.liferay.segments.experiment.web.internal.util.comparator.SegmentsExperimentModifiedDateComparator;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.model.SegmentsExperiment;
import com.liferay.segments.model.SegmentsExperimentRel;
import com.liferay.segments.service.SegmentsExperienceService;
import com.liferay.segments.service.SegmentsExperimentRelService;
import com.liferay.segments.service.SegmentsExperimentService;
import com.liferay.staging.StagingGroupHelper;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Stream;

import javax.portlet.PortletRequest;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(
	configurationPid = "com.liferay.segments.experiment.web.internal.configuration.SegmentsExperimentConfiguration",
	configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true,
	property = {
		"javax.portlet.name=" + SegmentsPortletKeys.SEGMENTS_EXPERIMENT,
		"mvc.command.name=/segments_experiment/get_data"
	},
	service = MVCResourceCommand.class
)
public class GetDataMVCResourceCommand extends BaseMVCResourceCommand {

	@Activate
	protected void activate(Map<String, Object> properties) {
		_segmentsExperimentConfiguration = ConfigurableUtil.createConfigurable(
			SegmentsExperimentConfiguration.class, properties);
	}

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			resourceRequest);

		try {
			long plid = ParamUtil.getLong(resourceRequest, "plid");

			Layout layout = _layoutLocalService.getLayout(plid);

			String backURL = ParamUtil.getString(resourceRequest, "backURL");
			String redirect = ParamUtil.getString(resourceRequest, "redirect");

			long segmentsExperienceId = ParamUtil.getLong(
				resourceRequest, "segmentsExperienceId");

			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse,
				JSONUtil.put(
					"context",
					_getContextJSONObject(
						backURL, layout,
						_portal.getHttpServletRequest(resourceRequest),
						redirect, segmentsExperienceId)
				).put(
					"props",
					_getPropsJSONObject(
						_portal.getHttpServletRequest(resourceRequest), layout,
						_portal.getLocale(httpServletRequest), redirect,
						segmentsExperienceId)
				));
		}
		catch (Exception exception) {
			_log.error(exception);

			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse,
				JSONUtil.put(
					"error",
					_language.get(
						httpServletRequest, "an-unexpected-error-occurred")));
		}
	}

	private Optional<SegmentsExperiment> _getActiveSegmentsExperimentOptional(
			Layout layout, long segmentsExperienceId)
		throws Exception {

		return Optional.ofNullable(
			_segmentsExperimentService.fetchSegmentsExperiment(
				segmentsExperienceId, _portal.getClassNameId(Layout.class),
				layout.getPlid(),
				SegmentsExperimentConstants.Status.getExclusiveStatusValues()));
	}

	private JSONObject _getAnalyticsDataJSONObject(
		long companyId, long groupId) {

		return JSONUtil.put(
			"cloudTrialURL", SegmentsExperimentUtil.ANALYTICS_CLOUD_TRIAL_URL
		).put(
			"isConnected",
			SegmentsExperimentUtil.isAnalyticsConnected(companyId)
		).put(
			"isSynced",
			() -> SegmentsExperimentUtil.isAnalyticsSynced(companyId, groupId)
		).put(
			"url",
			() -> PrefsPropsUtil.getString(companyId, "liferayAnalyticsURL")
		);
	}

	private String _getCalculateSegmentsExperimentEstimatedDurationURL(
		HttpServletRequest httpServletRequest, Group group) {

		return _getSegmentsExperimentActionURL(
			"/calculate_segments_experiment_estimated_duration", group,
			httpServletRequest);
	}

	private String _getContentPageEditorActionURL(
		String action, Group group, HttpServletRequest httpServletRequest) {

		return HttpComponentsUtil.addParameter(
			PortletURLBuilder.create(
				_portal.getControlPanelPortletURL(
					httpServletRequest, group,
					ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET, 0,
					0, PortletRequest.ACTION_PHASE)
			).setActionName(
				action
			).buildString(),
			"p_l_mode", Constants.EDIT);
	}

	private String _getContentPageEditorPortletNamespace() {
		return _portal.getPortletNamespace(
			ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET);
	}

	private JSONObject _getContextJSONObject(
			String backURL, Layout layout,
			HttpServletRequest httpServletRequest, String redirect,
			long segmentsExperienceId)
		throws Exception {

		return JSONUtil.put(
			"contentPageEditorNamespace",
			_getContentPageEditorPortletNamespace()
		).put(
			"endpoints",
			_getEndpointsJSONObject(
				backURL, layout, httpServletRequest, redirect,
				segmentsExperienceId)
		).put(
			"imagesPath", _getImagesPath(httpServletRequest)
		).put(
			"namespace", _getSegmentsExperimentPortletNamespace()
		).put(
			"page", _getPageJSONObject(layout)
		);
	}

	private String _getCreateSegmentsExperimentURL(
		Group group, HttpServletRequest httpServletRequest) {

		return _getSegmentsExperimentActionURL(
			"/segments_experiment/add_segments_experiment", group,
			httpServletRequest);
	}

	private String _getCreateSegmentsVariantURL(
		Group group, HttpServletRequest httpServletRequest, Layout layout) {

		return HttpComponentsUtil.addParameter(
			HttpComponentsUtil.addParameter(
				_getContentPageEditorActionURL(
					"/layout_content_page_editor/add_segments_experience",
					group, httpServletRequest),
				_getContentPageEditorPortletNamespace() + "plid",
				layout.getPlid()),
			_getContentPageEditorPortletNamespace() + "groupId",
			group.getGroupId());
	}

	private String _getDeleteSegmentsExperimentURL(
		Group group, HttpServletRequest httpServletRequest) {

		return _getSegmentsExperimentActionURL(
			"/segments_experiment/delete_segments_experiment", group,
			httpServletRequest);
	}

	private String _getDeleteSegmentsVariantURL(
		Group group, HttpServletRequest httpServletRequest) {

		return _getSegmentsExperimentActionURL(
			"/segments_experiment/delete_segments_experiment_rel", group,
			httpServletRequest);
	}

	private String _getEditSegmentsExperimentStatusURL(
		Group group, HttpServletRequest httpServletRequest) {

		return _getSegmentsExperimentActionURL(
			"/segments_experiment/edit_segments_experiment_status", group,
			httpServletRequest);
	}

	private String _getEditSegmentsExperimentURL(
		Group group, HttpServletRequest httpServletRequest) {

		return _getSegmentsExperimentActionURL(
			"/segments_experiment/edit_segments_experiment", group,
			httpServletRequest);
	}

	private String _getEditSegmentsVariantLayoutURL(
		String backURL, Layout layout, String redirect,
		long segmentsExperienceId) {

		Layout draftLayout = _layoutLocalService.fetchDraftLayout(
			layout.getPlid());

		if (draftLayout == null) {
			return StringPool.BLANK;
		}

		if (segmentsExperienceId != -1) {
			backURL = HttpComponentsUtil.setParameter(
				backURL, "segmentsExperienceId", segmentsExperienceId);
		}

		redirect = HttpComponentsUtil.setParameter(
			redirect, "p_l_back_url", backURL);

		redirect = HttpComponentsUtil.setParameter(
			redirect, "p_l_mode", Constants.EDIT);
		redirect = HttpComponentsUtil.setParameter(
			redirect, "redirect", redirect);

		return redirect;
	}

	private String _getEditSegmentsVariantURL(
		Group group, HttpServletRequest httpServletRequest) {

		return _getSegmentsExperimentActionURL(
			"/segments_experiment/edit_segments_experiment_rel", group,
			httpServletRequest);
	}

	private JSONObject _getEndpointsJSONObject(
			String backURL, Layout layout,
			HttpServletRequest httpServletRequest, String redirect,
			long segmentsExperienceId)
		throws Exception {

		Group group = layout.getGroup();

		return JSONUtil.put(
			"calculateSegmentsExperimentEstimatedDurationURL",
			_getCalculateSegmentsExperimentEstimatedDurationURL(
				httpServletRequest, group)
		).put(
			"createSegmentsExperimentURL",
			_getCreateSegmentsExperimentURL(group, httpServletRequest)
		).put(
			"createSegmentsVariantURL",
			_getCreateSegmentsVariantURL(group, httpServletRequest, layout)
		).put(
			"deleteSegmentsExperimentURL",
			_getDeleteSegmentsExperimentURL(group, httpServletRequest)
		).put(
			"deleteSegmentsVariantURL",
			_getDeleteSegmentsVariantURL(group, httpServletRequest)
		).put(
			"editSegmentsExperimentStatusURL",
			_getEditSegmentsExperimentStatusURL(group, httpServletRequest)
		).put(
			"editSegmentsExperimentURL",
			_getEditSegmentsExperimentURL(group, httpServletRequest)
		).put(
			"editSegmentsVariantLayoutURL",
			_getEditSegmentsVariantLayoutURL(
				backURL, layout, redirect, segmentsExperienceId)
		).put(
			"editSegmentsVariantURL",
			_getEditSegmentsVariantURL(group, httpServletRequest)
		).put(
			"runSegmentsExperimentURL",
			_getRunSegmentsExperimenttURL(group, httpServletRequest)
		);
	}

	private JSONArray _getHistorySegmentsExperimentsJSONArray(
			Layout layout, Locale locale, long segmentsExperienceId)
		throws Exception {

		List<SegmentsExperiment> segmentsExperiments =
			_segmentsExperimentService.getSegmentsExperiments(
				segmentsExperienceId, _portal.getClassNameId(Layout.class),
				layout.getPlid(),
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

	private String _getImagesPath(HttpServletRequest httpServletRequest) {
		return _portal.getPathContext(httpServletRequest) + "/images";
	}

	private long _getLiveGroupId(long groupId) throws Exception {
		Group group = _stagingGroupHelper.fetchLiveGroup(groupId);

		if (group != null) {
			return group.getGroupId();
		}

		return groupId;
	}

	private JSONObject _getPageJSONObject(Layout layout) {
		return JSONUtil.put(
			"classNameId", _portal.getClassNameId(Layout.class.getName())
		).put(
			"classPK", layout.getPlid()
		).put(
			"type", layout.getType()
		);
	}

	private JSONObject _getPropsJSONObject(
			HttpServletRequest httpServletRequest, Layout layout, Locale locale,
			String redirect, long segmentsExperienceId)
		throws Exception {

		Group group = layout.getGroup();

		return JSONUtil.put(
			"analyticsData",
			_getAnalyticsDataJSONObject(
				_portal.getCompanyId(httpServletRequest),
				_getLiveGroupId(group.getGroupId()))
		).put(
			"hideSegmentsExperimentPanelURL",
			PortletURLBuilder.create(
				_portal.getControlPanelPortletURL(
					httpServletRequest, group,
					SegmentsPortletKeys.SEGMENTS_EXPERIMENT, 0, 0,
					PortletRequest.ACTION_PHASE)
			).setActionName(
				"/segments_experiment/hide_segments_experiment_panel"
			).setRedirect(
				redirect
			).setParameter(
				"p_l_mode", Constants.VIEW
			).buildString()
		).put(
			"historySegmentsExperiments",
			_getHistorySegmentsExperimentsJSONArray(
				layout, locale, segmentsExperienceId)
		).put(
			"initialSegmentsVariants",
			_getSegmentsExperimentRelsJSONArray(
				layout, locale, segmentsExperienceId)
		).put(
			"pathToAssets", _portal.getPathContext(httpServletRequest)
		).put(
			"segmentsExperiences",
			_getSegmentsExperiencesJSONArray(layout, locale)
		).put(
			"segmentsExperiment",
			_getSegmentsExperimentJSONObject(
				layout, locale, segmentsExperienceId)
		).put(
			"segmentsExperimentGoals",
			_getSegmentsExperimentGoalsJSONArray(locale)
		).put(
			"selectedSegmentsExperienceId", segmentsExperienceId
		).put(
			"winnerSegmentsVariantId",
			_getWinnerSegmentsExperienceId(layout, segmentsExperienceId)
		);
	}

	private String _getRunSegmentsExperimenttURL(
		Group group, HttpServletRequest httpServletRequest) {

		return _getSegmentsExperimentActionURL(
			"/segments_experiment/run_segments_experiment", group,
			httpServletRequest);
	}

	private JSONArray _getSegmentsExperiencesJSONArray(
			Layout layout, Locale locale)
		throws Exception {

		List<SegmentsExperience> segmentsExperiences =
			_segmentsExperienceService.getSegmentsExperiences(
				layout.getGroupId(), _portal.getClassNameId(Layout.class),
				layout.getPlid(), true);

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
							layout, segmentsExperience.getSegmentsExperienceId()
						).orElse(
							null
						))
				));
		}

		return segmentsExperiencesJSONArray;
	}

	private SegmentsExperiment _getSegmentsExperiment(
			Layout layout, long segmentsExperienceId)
		throws Exception {

		return _getActiveSegmentsExperimentOptional(
			layout, segmentsExperienceId
		).orElse(
			null
		);
	}

	private String _getSegmentsExperimentActionURL(
		String action, Group group, HttpServletRequest httpServletRequest) {

		return PortletURLBuilder.create(
			_portal.getControlPanelPortletURL(
				httpServletRequest, group,
				SegmentsPortletKeys.SEGMENTS_EXPERIMENT, 0, 0,
				PortletRequest.ACTION_PHASE)
		).setActionName(
			action
		).setParameter(
			"p_l_mode", Constants.VIEW
		).buildString();
	}

	private JSONArray _getSegmentsExperimentGoalsJSONArray(Locale locale) {
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
					"label", _language.get(resourceBundle, goal.getLabel())
				).put(
					"value", goal.getLabel()
				))
		);

		return segmentsExperimentGoalsJSONArray;
	}

	private JSONObject _getSegmentsExperimentJSONObject(
			Layout layout, Locale locale, long segmentsExperienceId)
		throws Exception {

		return SegmentsExperimentUtil.toSegmentsExperimentJSONObject(
			locale, _getSegmentsExperiment(layout, segmentsExperienceId));
	}

	private String _getSegmentsExperimentPortletNamespace() {
		return _portal.getPortletNamespace(
			SegmentsPortletKeys.SEGMENTS_EXPERIMENT);
	}

	private JSONArray _getSegmentsExperimentRelsJSONArray(
			Layout layout, Locale locale, long segmentExperienceId)
		throws Exception {

		SegmentsExperiment segmentsExperiment = _getSegmentsExperiment(
			layout, segmentExperienceId);

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

	private String _getWinnerSegmentsExperienceId(
			Layout layout, long segmentsExperienceId)
		throws Exception {

		SegmentsExperiment segmentsExperiment = _getSegmentsExperiment(
			layout, segmentsExperienceId);

		if (segmentsExperiment == null) {
			return StringPool.BLANK;
		}

		long winnerSegmentsExperienceId =
			segmentsExperiment.getWinnerSegmentsExperienceId();

		if (winnerSegmentsExperienceId == -1) {
			return StringPool.BLANK;
		}

		return String.valueOf(winnerSegmentsExperienceId);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GetDataMVCResourceCommand.class);

	@Reference
	private Language _language;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private SegmentsExperienceService _segmentsExperienceService;

	private volatile SegmentsExperimentConfiguration
		_segmentsExperimentConfiguration;

	@Reference
	private SegmentsExperimentRelService _segmentsExperimentRelService;

	@Reference
	private SegmentsExperimentService _segmentsExperimentService;

	@Reference
	private StagingGroupHelper _stagingGroupHelper;

}