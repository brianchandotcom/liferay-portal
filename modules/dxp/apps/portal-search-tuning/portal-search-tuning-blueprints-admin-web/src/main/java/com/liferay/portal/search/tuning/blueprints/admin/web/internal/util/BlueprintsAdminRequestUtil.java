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

package com.liferay.portal.search.tuning.blueprints.admin.web.internal.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.search.tuning.blueprints.admin.web.internal.constants.BlueprintsAdminWebKeys;
import com.liferay.portal.search.tuning.blueprints.exception.NoSuchBlueprintException;
import com.liferay.portal.search.tuning.blueprints.exception.NoSuchElementException;
import com.liferay.portal.search.tuning.blueprints.model.Blueprint;
import com.liferay.portal.search.tuning.blueprints.model.Element;
import com.liferay.portal.search.tuning.blueprints.service.BlueprintService;
import com.liferay.portal.search.tuning.blueprints.service.ElementService;

import java.util.Locale;
import java.util.Map;
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
public class BlueprintsAdminRequestUtil {

	public static Optional<Blueprint> getBlueprint(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		long blueprintId = getBlueprintId(portletRequest);

		if (blueprintId <= 0) {
			return Optional.empty();
		}

		try {
			return Optional.of(_blueprintService.getBlueprint(blueprintId));
		}
		catch (NoSuchBlueprintException noSuchBlueprintException) {
			_log.error(
				"Blueprint " + blueprintId + " not found",
				noSuchBlueprintException);

			SessionErrors.add(
				portletRequest, BlueprintsAdminWebKeys.ERROR,
				noSuchBlueprintException.getMessage());
		}
		catch (PortalException portalException) {
			_log.error(portalException.getMessage(), portalException);

			SessionErrors.add(
				portletRequest, BlueprintsAdminWebKeys.ERROR,
				portalException.getMessage());
		}

		return Optional.empty();
	}

	public static long getBlueprintId(PortletRequest portletRequest) {
		return ParamUtil.getLong(
			portletRequest, BlueprintsAdminWebKeys.BLUEPRINT_ID);
	}

	public static String getConfiguration(PortletRequest portletRequest) {
		return ParamUtil.getString(
			portletRequest, BlueprintsAdminWebKeys.CONFIGURATION);
	}

	public static Map<Locale, String> getDescription(
		PortletRequest portletRequest) {

		return LocalizationUtil.getLocalizationMap(
			portletRequest, BlueprintsAdminWebKeys.DESCRIPTION);
	}

	public static Optional<Element> getElement(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		long elementId = getElementId(portletRequest);

		if (elementId <= 0) {
			return Optional.empty();
		}

		try {
			return Optional.of(_elementService.getElement(elementId));
		}
		catch (NoSuchElementException noSuchElementException) {
			_log.error(
				"Element " + elementId + " not found", noSuchElementException);

			SessionErrors.add(
				portletRequest, BlueprintsAdminWebKeys.ERROR,
				noSuchElementException.getMessage());
		}
		catch (PortalException portalException) {
			_log.error(portalException.getMessage(), portalException);

			SessionErrors.add(
				portletRequest, BlueprintsAdminWebKeys.ERROR,
				portalException.getMessage());
		}

		return Optional.empty();
	}

	public static long getElementId(PortletRequest portletRequest) {
		return ParamUtil.getLong(
			portletRequest, BlueprintsAdminWebKeys.ELEMENT_ID);
	}

	public static int getElementType(PortletRequest portletRequest) {
		return ParamUtil.getInteger(
			portletRequest, BlueprintsAdminWebKeys.ELEMENT_TYPE);
	}

	public static HttpServletRequest getHttpServletRequest(
		PortletRequest portletRequest) {

		return _portal.getHttpServletRequest(portletRequest);
	}

	public static String getKeywords(PortletRequest portletRequest) {
		return ParamUtil.getString(
			portletRequest, BlueprintsAdminWebKeys.KEYWORDS);
	}

	public static String getSelectedElements(PortletRequest portletRequest) {
		return ParamUtil.getString(
			portletRequest, BlueprintsAdminWebKeys.SELECTED_ELEMENTS);
	}

	public static Map<Locale, String> getTitle(PortletRequest portletRequest) {
		return LocalizationUtil.getLocalizationMap(
			portletRequest, BlueprintsAdminWebKeys.TITLE);
	}

	@Reference(unbind = "-")
	protected void setBlueprintService(BlueprintService blueprintService) {
		_blueprintService = blueprintService;
	}

	@Reference(unbind = "-")
	protected void setElementService(ElementService elementService) {
		_elementService = elementService;
	}

	@Reference(unbind = "-")
	protected void setPortal(Portal portal) {
		_portal = portal;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BlueprintsAdminRequestUtil.class);

	private static BlueprintService _blueprintService;
	private static ElementService _elementService;
	private static Portal _portal;

}