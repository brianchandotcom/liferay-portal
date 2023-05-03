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

package com.liferay.segments.experiment.web.internal.processor;

import com.liferay.analytics.settings.rest.manager.AnalyticsSettingsManager;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.cookies.CookiesManagerUtil;
import com.liferay.portal.kernel.cookies.constants.CookiesConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.segments.constants.SegmentsExperimentConstants;
import com.liferay.segments.experiment.web.internal.constants.SegmentsExperimentWebKeys;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.model.SegmentsExperiment;
import com.liferay.segments.model.SegmentsExperimentRel;
import com.liferay.segments.processor.SegmentsExperienceRequestProcessor;
import com.liferay.segments.service.SegmentsExperienceLocalService;
import com.liferay.segments.service.SegmentsExperimentLocalService;
import com.liferay.segments.service.SegmentsExperimentRelLocalService;

import java.util.List;
import java.util.Objects;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 */
@Component(
	property = "segments.experience.request.processor.priority:Integer=50",
	service = {
		SegmentsExperienceRequestProcessor.class,
		SegmentsExperimentSegmentsExperienceRequestProcessor.class
	}
)
public class SegmentsExperimentSegmentsExperienceRequestProcessor
	implements SegmentsExperienceRequestProcessor {

	public void cleanCookieLogoutAction(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		_unsetCookie(httpServletRequest, httpServletResponse);
	}

	@Override
	public long[] getSegmentsExperienceIds(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, long groupId, long plid,
			long[] segmentsExperienceIds)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		try {
			if (!_analyticsSettingsManager.isSiteIdSynced(
					themeDisplay.getCompanyId(),
					themeDisplay.getScopeGroupId())) {

				return segmentsExperienceIds;
			}
		}
		catch (PortalException portalException) {
			throw portalException;
		}
		catch (Exception exception) {
			throw new PortalException(exception);
		}

		long segmentsExperienceId = _getSelectedSegmentsExperienceId(
			httpServletRequest, themeDisplay);

		if (segmentsExperienceId != -1) {
			return new long[] {segmentsExperienceId};
		}

		String segmentsExperimentKey = _getSelectedSegmentsExperimentKey(
			httpServletRequest);

		if (Validator.isNotNull(segmentsExperimentKey)) {
			SegmentsExperiment segmentsExperiment =
				_segmentsExperimentLocalService.fetchSegmentsExperiment(
					themeDisplay.getScopeGroupId(), segmentsExperimentKey);

			if (segmentsExperiment != null) {
				return new long[] {
					segmentsExperiment.getSegmentsExperienceId()
				};
			}
		}

		segmentsExperienceId = _getCurrentSegmentsExperienceId(
			groupId, plid, httpServletRequest);

		if (segmentsExperienceId != -1) {
			SegmentsExperiment segmentsExperiment =
				_segmentsExperimentLocalService.fetchSegmentsExperiment(
					segmentsExperienceId, _portal.getClassNameId(Layout.class),
					plid,
					SegmentsExperimentConstants.Status.getSplitStatusValues());

			if (segmentsExperiment != null) {
				httpServletRequest.setAttribute(
					SegmentsExperimentWebKeys.SEGMENTS_EXPERIMENT,
					segmentsExperiment);

				if (_log.isDebugEnabled()) {
					_log.debug(
						StringBundler.concat(
							"Serving previous experience ",
							segmentsExperienceId, " as its experiment ",
							segmentsExperiment.getSegmentsExperimentId(),
							" is still running"));
				}

				return new long[] {segmentsExperienceId};
			}
		}

		_unsetCookie(httpServletRequest, httpServletResponse);

		if (ArrayUtil.isEmpty(segmentsExperienceIds)) {
			segmentsExperienceId =
				_segmentsExperienceLocalService.
					fetchDefaultSegmentsExperienceId(plid);
		}
		else {
			segmentsExperienceId = segmentsExperienceIds[0];
		}

		List<SegmentsExperiment> segmentsExperiments =
			_segmentsExperimentLocalService.
				getSegmentsExperienceSegmentsExperiments(
					new long[] {segmentsExperienceId},
					_portal.getClassNameId(Layout.class), plid,
					SegmentsExperimentConstants.Status.getSplitStatusValues(),
					0, 1);

		if (segmentsExperiments.isEmpty()) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No experiment running for the user experiences " +
						StringUtil.merge(segmentsExperienceIds));
			}

			return segmentsExperienceIds;
		}

		SegmentsExperiment segmentsExperiment = segmentsExperiments.get(0);

		List<SegmentsExperimentRel> segmentsExperimentRels =
			_segmentsExperimentRelLocalService.getSegmentsExperimentRels(
				segmentsExperiment.getSegmentsExperimentId());

		if (segmentsExperimentRels.isEmpty()) {
			return segmentsExperienceIds;
		}

		segmentsExperienceId = _getSegmentsExperimentSegmentsExperienceId(
			segmentsExperiment.getSegmentsExperienceId(),
			segmentsExperimentRels);

		_setCookie(
			httpServletRequest, httpServletResponse,
			themeDisplay.getURLCurrent(), segmentsExperienceId);

		httpServletRequest.setAttribute(
			SegmentsExperimentWebKeys.SEGMENTS_EXPERIMENT, segmentsExperiment);

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"Serving experience ", segmentsExperienceId,
					" for running experiment ",
					segmentsExperiment.getSegmentsExperimentId()));
		}

		return new long[] {segmentsExperienceId};
	}

	@Override
	public long[] getSegmentsExperienceIds(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, long groupId, long plid,
			long[] segmentsEntryIds, long[] segmentsExperienceIds)
		throws PortalException {

		return getSegmentsExperienceIds(
			httpServletRequest, httpServletResponse, groupId, plid,
			segmentsExperienceIds);
	}

	private Cookie _getCookie(HttpServletRequest httpServletRequest) {
		Cookie[] cookies = httpServletRequest.getCookies();

		if (ArrayUtil.isEmpty(cookies)) {
			return null;
		}

		for (Cookie cookie : cookies) {
			if (Objects.equals(
					cookie.getName(), _AB_TEST_VARIANT_ID_COOKIE_NAME)) {

				return cookie;
			}
		}

		return null;
	}

	private long _getCurrentSegmentsExperienceId(
		long groupId, long plid, HttpServletRequest httpServletRequest) {

		Cookie cookie = _getCookie(httpServletRequest);

		if (cookie == null) {
			return -1;
		}

		return _getSegmentsExperienceId(groupId, cookie.getValue(), plid);
	}

	private long _getSegmentsExperienceId(
		long groupId, String segmentsExperienceKey, long plid) {

		if (Validator.isNotNull(segmentsExperienceKey)) {
			SegmentsExperience segmentsExperience =
				_segmentsExperienceLocalService.fetchSegmentsExperience(
					groupId, segmentsExperienceKey, plid);

			if (segmentsExperience != null) {
				return segmentsExperience.getSegmentsExperienceId();
			}
		}

		return -1;
	}

	private String _getSegmentsExperienceKey(long segmentsExperienceId) {
		SegmentsExperience segmentsExperience =
			_segmentsExperienceLocalService.fetchSegmentsExperience(
				segmentsExperienceId);

		return segmentsExperience.getSegmentsExperienceKey();
	}

	private long _getSegmentsExperimentSegmentsExperienceId(
		long controlSegmentsExperienceId,
		List<SegmentsExperimentRel> segmentsExperimentRels) {

		double random = Math.random();

		for (SegmentsExperimentRel segmentsExperimentRel :
				segmentsExperimentRels) {

			random -= segmentsExperimentRel.getSplit();

			if (random <= 0.0D) {
				return segmentsExperimentRel.getSegmentsExperienceId();
			}
		}

		return controlSegmentsExperienceId;
	}

	private long _getSelectedSegmentsExperienceId(
		HttpServletRequest httpServletRequest, ThemeDisplay themeDisplay) {

		if (!themeDisplay.isSignedIn()) {
			return -1;
		}

		long selectedSegmentsExperienceId = ParamUtil.getLong(
			httpServletRequest, "segmentsExperienceId", -1);

		if (selectedSegmentsExperienceId != -1) {
			SegmentsExperience segmentsExperience =
				_segmentsExperienceLocalService.fetchSegmentsExperience(
					selectedSegmentsExperienceId);

			if (segmentsExperience != null) {
				return selectedSegmentsExperienceId;
			}
		}

		String selectedSegmentsExperienceKey = ParamUtil.getString(
			httpServletRequest, "segmentsExperienceKey");

		return _getSegmentsExperienceId(
			themeDisplay.getScopeGroupId(), selectedSegmentsExperienceKey,
			themeDisplay.getPlid());
	}

	private String _getSelectedSegmentsExperimentKey(
		HttpServletRequest httpServletRequest) {

		HttpServletRequest originalHttpServletRequest =
			_portal.getOriginalServletRequest(httpServletRequest);

		return ParamUtil.getString(
			originalHttpServletRequest, "segmentsExperimentKey");
	}

	private void _setCookie(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, String path,
		long segmentsExperienceId) {

		Cookie abTestVariantIdCookie = new Cookie(
			_AB_TEST_VARIANT_ID_COOKIE_NAME,
			_getSegmentsExperienceKey(segmentsExperienceId));

		String domain = CookiesManagerUtil.getDomain(httpServletRequest);

		if (Validator.isNotNull(domain)) {
			abTestVariantIdCookie.setDomain(domain);
		}

		abTestVariantIdCookie.setMaxAge(CookiesConstants.MAX_AGE);
		abTestVariantIdCookie.setPath(path);

		CookiesManagerUtil.addCookie(
			CookiesConstants.CONSENT_TYPE_PERSONALIZATION,
			abTestVariantIdCookie, httpServletRequest, httpServletResponse);
	}

	private void _unsetCookie(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		Cookie cookie = _getCookie(httpServletRequest);

		if (cookie == null) {
			return;
		}

		CookiesManagerUtil.deleteCookies(
			CookiesManagerUtil.getDomain(httpServletRequest),
			httpServletRequest, httpServletResponse, cookie.getName());
	}

	private static final String _AB_TEST_VARIANT_ID_COOKIE_NAME =
		"ab_test_variant_id";

	private static final Log _log = LogFactoryUtil.getLog(
		SegmentsExperimentSegmentsExperienceRequestProcessor.class);

	@Reference
	private AnalyticsSettingsManager _analyticsSettingsManager;

	@Reference
	private Portal _portal;

	@Reference
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

	@Reference
	private SegmentsExperimentLocalService _segmentsExperimentLocalService;

	@Reference
	private SegmentsExperimentRelLocalService
		_segmentsExperimentRelLocalService;

}