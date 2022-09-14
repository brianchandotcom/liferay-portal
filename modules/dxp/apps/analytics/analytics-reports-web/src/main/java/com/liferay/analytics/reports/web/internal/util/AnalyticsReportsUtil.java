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

package com.liferay.analytics.reports.web.internal.util;

import com.liferay.analytics.reports.info.item.ClassNameClassPKInfoItemIdentifier;
import com.liferay.analytics.reports.web.internal.constants.AnalyticsReportsPortletKeys;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.InfoItemReference;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletURLFactory;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.Validator;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.WindowStateException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Sarai Díaz
 */
public class AnalyticsReportsUtil {

	public static final String ANALYTICS_CLOUD_TRIAL_URL =
		"https://www.liferay.com/products/analytics-cloud/get-started";

	public static String getAnalyticsReportsPanelURL(
			InfoItemReference infoItemReference,
			HttpServletRequest httpServletRequest, Portal portal,
			PortletURLFactory portletURLFactory)
		throws WindowStateException {

		PortletURL portletURL = PortletURLBuilder.create(
			portletURLFactory.create(
				httpServletRequest,
				AnalyticsReportsPortletKeys.ANALYTICS_REPORTS,
				RenderRequest.RENDER_PHASE)
		).setMVCPath(
			"/analytics_reports_panel.jsp"
		).setRedirect(
			portal.getCurrentCompleteURL(httpServletRequest)
		).setParameter(
			"className", infoItemReference.getClassName()
		).buildPortletURL();

		if (infoItemReference.getInfoItemIdentifier() instanceof
				ClassNameClassPKInfoItemIdentifier) {

			ClassNameClassPKInfoItemIdentifier
				classNameClassPKInfoItemIdentifier =
					(ClassNameClassPKInfoItemIdentifier)
						infoItemReference.getInfoItemIdentifier();

			portletURL.setParameter(
				"classPK",
				String.valueOf(
					classNameClassPKInfoItemIdentifier.getClassPK()));
			portletURL.setParameter(
				"classTypeName",
				classNameClassPKInfoItemIdentifier.getClassName());
		}
		else if (infoItemReference.getInfoItemIdentifier() instanceof
					ClassPKInfoItemIdentifier) {

			ClassPKInfoItemIdentifier classPKInfoItemIdentifier =
				(ClassPKInfoItemIdentifier)
					infoItemReference.getInfoItemIdentifier();

			portletURL.setParameter(
				"classPK",
				String.valueOf(classPKInfoItemIdentifier.getClassPK()));
		}

		portletURL.setWindowState(LiferayWindowState.EXCLUSIVE);

		return portletURL.toString();
	}

	public static String getAsahFaroBackendDataSourceId(long companyId) {
		return PrefsPropsUtil.getString(
			companyId, "liferayAnalyticsDataSourceId");
	}

	public static String getAsahFaroBackendSecuritySignature(long companyId) {
		return PrefsPropsUtil.getString(
			companyId, "liferayAnalyticsFaroBackendSecuritySignature");
	}

	public static String getAsahFaroBackendURL(long companyId) {
		return PrefsPropsUtil.getString(
			companyId, "liferayAnalyticsFaroBackendURL");
	}

	public static String getAsahProjectId(long companyId) {
		return PrefsPropsUtil.getString(companyId, "liferayAnalyticsProjectId");
	}

	public static boolean isAnalyticsConnected(long companyId) {
		if (Validator.isNull(
				PrefsPropsUtil.getString(
					companyId, "liferayAnalyticsDataSourceId")) ||
			Validator.isNull(
				PrefsPropsUtil.getString(
					companyId,
					"liferayAnalyticsFaroBackendSecuritySignature")) ||
			Validator.isNull(
				PrefsPropsUtil.getString(
					companyId, "liferayAnalyticsFaroBackendURL"))) {

			return false;
		}

		return true;
	}

	public static boolean isAnalyticsSynced(long companyId, long groupId) {
		if (!isAnalyticsConnected(companyId)) {
			return false;
		}

		if (PrefsPropsUtil.getBoolean(
				companyId, "liferayAnalyticsEnableAllGroupIds")) {

			return true;
		}

		String[] liferayAnalyticsGroupIds = PrefsPropsUtil.getStringArray(
			companyId, "liferayAnalyticsGroupIds", StringPool.COMMA);

		if (ArrayUtil.contains(
				liferayAnalyticsGroupIds, String.valueOf(groupId))) {

			return true;
		}

		return false;
	}

	public static boolean isShowAnalyticsReportsPanel(
			long companyId, HttpServletRequest httpServletRequest)
		throws PortalException {

		if (!CTCollectionThreadLocal.isProductionMode()) {
			return false;
		}

		String layoutMode = ParamUtil.getString(
			httpServletRequest, "p_l_mode", Constants.VIEW);

		if (layoutMode.equals(Constants.EDIT)) {
			return false;
		}

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(
				httpServletRequest);

		boolean hidePanel = GetterUtil.getBoolean(
			portalPreferences.getValue(
				AnalyticsReportsPortletKeys.ANALYTICS_REPORTS, "hide-panel"));

		if (!isAnalyticsConnected(companyId) && hidePanel) {
			return false;
		}

		return true;
	}

}