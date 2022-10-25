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
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.segments.constants.SegmentsExperimentConstants;
import com.liferay.segments.constants.SegmentsPortletKeys;
import com.liferay.segments.experiment.web.internal.configuration.SegmentsExperimentConfiguration;
import com.liferay.segments.experiment.web.internal.util.SegmentsExperimentUtil;
import com.liferay.segments.experiment.web.internal.util.comparator.SegmentsExperimentModifiedDateComparator;
import com.liferay.segments.model.SegmentsExperiment;
import com.liferay.segments.service.SegmentsExperienceService;
import com.liferay.segments.service.SegmentsExperimentRelService;
import com.liferay.segments.service.SegmentsExperimentService;
import com.liferay.staging.StagingGroupHelper;

import java.util.Locale;
import java.util.Map;

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
			String backURL = ParamUtil.getString(resourceRequest, "backURL");
			String redirect = ParamUtil.getString(resourceRequest, "redirect");

			long plid = ParamUtil.getLong(resourceRequest, "plid");

			Layout layout = _layoutLocalService.getLayout(plid);

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

	private SegmentsExperiment _fetchSegmentsExperiment(
			Layout layout, long segmentsExperienceId)
		throws Exception {

		return _segmentsExperimentService.fetchSegmentsExperiment(
			segmentsExperienceId, _portal.getClassNameId(Layout.class),
			layout.getPlid(),
			SegmentsExperimentConstants.Status.getExclusiveStatusValues());
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

		Group group = layout.getGroup();

		return JSONUtil.put(
			"contentPageEditorNamespace",
			_getContentPageEditorPortletNamespace()
		).put(
			"endpoints",
			JSONUtil.put(
				"calculateSegmentsExperimentEstimatedDurationURL",
				_getSegmentsExperimentActionURL(
					"/calculate_segments_experiment_estimated_duration", group,
					httpServletRequest)
			).put(
				"createSegmentsExperimentURL",
				_getSegmentsExperimentActionURL(
					"/segments_experiment/add_segments_experiment", group,
					httpServletRequest)
			).put(
				"createSegmentsVariantURL",
				() -> {
					String url = PortletURLBuilder.create(
						_portal.getControlPanelPortletURL(
							httpServletRequest, group,
							ContentPageEditorPortletKeys.
								CONTENT_PAGE_EDITOR_PORTLET,
							0, 0, PortletRequest.ACTION_PHASE)
					).setActionName(
						"/layout_content_page_editor/add_segments_experience"
					).buildString();

					url = HttpComponentsUtil.addParameter(
						url,
						_getContentPageEditorPortletNamespace() + "p_l_mode",
						Constants.EDIT);
					url = HttpComponentsUtil.addParameter(
						url, _getContentPageEditorPortletNamespace() + "plid",
						layout.getPlid());
					url = HttpComponentsUtil.addParameter(
						url,
						_getContentPageEditorPortletNamespace() + "groupId",
						group.getGroupId());

					return url;
				}
			).put(
				"deleteSegmentsExperimentURL",
				_getSegmentsExperimentActionURL(
					"/segments_experiment/delete_segments_experiment", group,
					httpServletRequest)
			).put(
				"deleteSegmentsVariantURL",
				_getSegmentsExperimentActionURL(
					"/segments_experiment/delete_segments_experiment_rel",
					group, httpServletRequest)
			).put(
				"editSegmentsExperimentStatusURL",
				_getSegmentsExperimentActionURL(
					"/segments_experiment/edit_segments_experiment_status",
					group, httpServletRequest)
			).put(
				"editSegmentsExperimentURL",
				_getSegmentsExperimentActionURL(
					"/segments_experiment/edit_segments_experiment", group,
					httpServletRequest)
			).put(
				"editSegmentsVariantLayoutURL",
				_getEditSegmentsVariantLayoutURL(
					backURL, layout, redirect, segmentsExperienceId)
			).put(
				"editSegmentsVariantURL",
				_getSegmentsExperimentActionURL(
					"/segments_experiment/edit_segments_experiment_rel", group,
					httpServletRequest)
			).put(
				"runSegmentsExperimentURL",
				_getSegmentsExperimentActionURL(
					"/segments_experiment/run_segments_experiment", group,
					httpServletRequest)
			)
		).put(
			"imagesPath", _portal.getPathContext(httpServletRequest) + "/images"
		).put(
			"namespace",
			_portal.getPortletNamespace(SegmentsPortletKeys.SEGMENTS_EXPERIMENT)
		).put(
			"page",
			JSONUtil.put(
				"classNameId", _portal.getClassNameId(Layout.class.getName())
			).put(
				"classPK", layout.getPlid()
			).put(
				"type", layout.getType()
			)
		);
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

	private long _getLiveGroupId(long groupId) throws Exception {
		Group group = _stagingGroupHelper.fetchLiveGroup(groupId);

		if (group != null) {
			return group.getGroupId();
		}

		return groupId;
	}

	private JSONObject _getPropsJSONObject(
			HttpServletRequest httpServletRequest, Layout layout, Locale locale,
			String redirect, long segmentsExperienceId)
		throws Exception {

		Group group = layout.getGroup();

		return JSONUtil.put(
			"analyticsData",
			JSONUtil.put(
				"cloudTrialURL",
				SegmentsExperimentUtil.ANALYTICS_CLOUD_TRIAL_URL
			).put(
				"isConnected",
				SegmentsExperimentUtil.isAnalyticsConnected(
					group.getCompanyId())
			).put(
				"isSynced",
				SegmentsExperimentUtil.isAnalyticsSynced(
					group.getCompanyId(), _getLiveGroupId(group.getGroupId()))
			).put(
				"url",
				PrefsPropsUtil.getString(
					group.getCompanyId(), "liferayAnalyticsURL")
			)
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
			JSONUtil.toJSONArray(
				_segmentsExperimentService.getSegmentsExperiments(
					segmentsExperienceId, _portal.getClassNameId(Layout.class),
					layout.getPlid(),
					SegmentsExperimentConstants.Status.
						getNonexclusiveStatusValues(),
					new SegmentsExperimentModifiedDateComparator()),
				segmentsExperiment ->
					SegmentsExperimentUtil.toSegmentsExperimentJSONObject(
						locale, segmentsExperiment))
		).put(
			"initialSegmentsVariants",
			() -> {
				JSONArray segmentsExperimentRelsJSONArray =
					JSONFactoryUtil.createJSONArray();

				SegmentsExperiment segmentsExperiment =
					_fetchSegmentsExperiment(layout, segmentsExperienceId);

				if (segmentsExperiment == null) {
					return segmentsExperimentRelsJSONArray;
				}

				return JSONUtil.toJSONArray(
					_segmentsExperimentRelService.getSegmentsExperimentRels(
						segmentsExperiment.getSegmentsExperimentId()),
					segmentsExperimentRel ->
						SegmentsExperimentUtil.
							toSegmentsExperimentRelJSONObject(
								locale, segmentsExperimentRel));
			}
		).put(
			"pathToAssets", _portal.getPathContext(httpServletRequest)
		).put(
			"segmentsExperiences",
			JSONUtil.toJSONArray(
				_segmentsExperienceService.getSegmentsExperiences(
					layout.getGroupId(), _portal.getClassNameId(Layout.class),
					layout.getPlid(), true),
				segmentsExperience -> JSONUtil.put(
					"name", segmentsExperience.getName(locale)
				).put(
					"segmentsExperienceId",
					String.valueOf(segmentsExperience.getSegmentsExperienceId())
				).put(
					"segmentsExperiment",
					SegmentsExperimentUtil.toSegmentsExperimentJSONObject(
						locale,
						_fetchSegmentsExperiment(
							layout,
							segmentsExperience.getSegmentsExperienceId()))
				))
		).put(
			"segmentsExperiment",
			SegmentsExperimentUtil.toSegmentsExperimentJSONObject(
				locale, _fetchSegmentsExperiment(layout, segmentsExperienceId))
		).put(
			"segmentsExperimentGoals",
			JSONUtil.toJSONArray(
				SegmentsExperimentConstants.Goal.values(),
				goal -> {
					if (!ArrayUtil.contains(
							_segmentsExperimentConfiguration.goalsEnabled(),
							goal.name())) {

						return null;
					}

					return JSONUtil.put(
						"label", _language.get(locale, goal.getLabel())
					).put(
						"value", goal.getLabel()
					);
				})
		).put(
			"selectedSegmentsExperienceId", segmentsExperienceId
		).put(
			"winnerSegmentsVariantId",
			() -> {
				SegmentsExperiment segmentsExperiment =
					_fetchSegmentsExperiment(layout, segmentsExperienceId);

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