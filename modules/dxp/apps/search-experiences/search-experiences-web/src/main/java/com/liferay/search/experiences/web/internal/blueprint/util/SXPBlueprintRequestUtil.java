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

package com.liferay.search.experiences.web.internal.blueprint.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.search.experiences.exception.NoSuchSXPBlueprintException;
import com.liferay.search.experiences.model.SXPBlueprint;
import com.liferay.search.experiences.model.SXPElement;
import com.liferay.search.experiences.service.SXPBlueprintService;
import com.liferay.search.experiences.service.SXPElementService;
import com.liferay.search.experiences.web.internal.blueprint.constants.SXPBlueprintWebKeys;

import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(immediate = true, service = {})
public class SXPBlueprintRequestUtil {

	public static String getConfigurationsJSON(PortletRequest portletRequest) {
		return ParamUtil.getString(
			portletRequest, SXPBlueprintWebKeys.CONFIGURATIONS_JSON);
	}

	public static Map<Locale, String> getDescription(
		PortletRequest portletRequest) {

		return LocalizationUtil.getLocalizationMap(
			portletRequest, SXPBlueprintWebKeys.DESCRIPTION);
	}

	public static String getElementDefinitionJSON(
		PortletRequest portletRequest) {

		return ParamUtil.getString(
			portletRequest, SXPBlueprintWebKeys.ELEMENT_DEFINITION_JSON);
	}

	public static String getElementInstancesJSON(
		PortletRequest portletRequest) {

		return ParamUtil.getString(
			portletRequest, SXPBlueprintWebKeys.ELEMENT_INSTANCES_JSON);
	}

	public static boolean getHidden(PortletRequest portletRequest) {
		return ParamUtil.getBoolean(portletRequest, SXPBlueprintWebKeys.HIDDEN);
	}

	public static HttpServletRequest getHttpServletRequest(
		PortletRequest portletRequest) {

		return _portal.getHttpServletRequest(portletRequest);
	}

	public static String getKeywords(PortletRequest portletRequest) {
		return ParamUtil.getString(
			portletRequest, SXPBlueprintWebKeys.KEYWORDS);
	}

	public static String getPreviewAttributes(PortletRequest portletRequest) {
		return ParamUtil.getString(
			portletRequest, SXPBlueprintWebKeys.PREVIEW_ATTRIBUTES);
	}

	public static boolean getReadOnly(PortletRequest portletRequest) {
		return ParamUtil.getBoolean(
			portletRequest, SXPBlueprintWebKeys.READ_ONLY);
	}

	public static Optional<SXPBlueprint> getSXPBlueprint(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		long sxpBlueprintId = getSXPBlueprintId(portletRequest);

		if (sxpBlueprintId <= 0) {
			return Optional.empty();
		}

		try {
			return Optional.of(
				_sxpSXPBlueprintService.getSXPBlueprint(sxpBlueprintId));
		}
		catch (NoSuchSXPBlueprintException noSuchSXPBlueprintException) {
			_log.error(
				"SXPBlueprint " + sxpBlueprintId + " not found",
				noSuchSXPBlueprintException);

			SessionErrors.add(
				portletRequest, SXPBlueprintWebKeys.ERROR,
				noSuchSXPBlueprintException.getMessage());
		}
		catch (PortalException portalException) {
			_log.error(portalException.getMessage(), portalException);

			SessionErrors.add(
				portletRequest, SXPBlueprintWebKeys.ERROR,
				portalException.getMessage());
		}

		return Optional.empty();
	}

	public static long getSXPBlueprintId(PortletRequest portletRequest) {
		return ParamUtil.getLong(
			portletRequest, SXPBlueprintWebKeys.SXP_BLUEPRINT_ID);
	}

	public static Optional<SXPElement> getSXPElement(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		long sxpElementId = getSXPElementId(portletRequest);

		if (sxpElementId <= 0) {
			return Optional.empty();
		}

		try {
			return Optional.of(_sxpElementService.getSXPElement(sxpElementId));
		}
		catch (NoSuchElementException noSuchElementException) {
			_log.error(
				"SXPElement " + sxpElementId + " not found",
				noSuchElementException);

			SessionErrors.add(
				portletRequest, SXPBlueprintWebKeys.ERROR,
				noSuchElementException.getMessage());
		}
		catch (PortalException portalException) {
			_log.error(portalException.getMessage(), portalException);

			SessionErrors.add(
				portletRequest, SXPBlueprintWebKeys.ERROR,
				portalException.getMessage());
		}

		return Optional.empty();
	}

	public static long getSXPElementId(PortletRequest portletRequest) {
		return ParamUtil.getLong(
			portletRequest, SXPBlueprintWebKeys.SXP_ELEMENT_ID);
	}

	public static int getSXPElementType(PortletRequest portletRequest) {
		return ParamUtil.getInteger(
			portletRequest, SXPBlueprintWebKeys.SXP_ELEMENT_TYPE);
	}

	public static Map<Locale, String> getTitle(PortletRequest portletRequest) {
		return LocalizationUtil.getLocalizationMap(
			portletRequest, SXPBlueprintWebKeys.TITLE);
	}

	@Reference(unbind = "-")
	protected void setPortal(Portal portal) {
		_portal = portal;
	}

	@Reference(unbind = "-")
	protected void setSXPBlueprintService(
		SXPBlueprintService sxpBlueprintService) {

		_sxpSXPBlueprintService = sxpBlueprintService;
	}

	@Reference(unbind = "-")
	protected void setSXPElementService(SXPElementService sxpElementService) {
		_sxpElementService = sxpElementService;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SXPBlueprintRequestUtil.class);

	private static Portal _portal;
	private static SXPElementService _sxpElementService;
	private static SXPBlueprintService _sxpSXPBlueprintService;

}