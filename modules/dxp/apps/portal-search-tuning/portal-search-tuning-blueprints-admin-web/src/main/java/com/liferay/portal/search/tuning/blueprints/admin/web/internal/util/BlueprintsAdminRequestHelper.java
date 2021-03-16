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
import com.liferay.portal.search.tuning.blueprints.admin.web.internal.constants.BlueprintsAdminWebKeys;
import com.liferay.portal.search.tuning.blueprints.exception.NoSuchBlueprintException;
import com.liferay.portal.search.tuning.blueprints.model.Blueprint;
import com.liferay.portal.search.tuning.blueprints.service.BlueprintService;

import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(immediate = true, service = BlueprintsAdminRequestHelper.class)
public class BlueprintsAdminRequestHelper {

	public Optional<Blueprint> getBlueprintFromRequest(
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws Exception {

		long blueprintId = getIdFromRequest(portletRequest);

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

	public Map<Locale, String> getDescriptionFromRequest(
		PortletRequest portletRequest) {

		return LocalizationUtil.getLocalizationMap(
			portletRequest, BlueprintsAdminWebKeys.DESCRIPTION);
	}

	public long getIdFromRequest(PortletRequest portletRequest) {
		return ParamUtil.getLong(
			portletRequest, BlueprintsAdminWebKeys.BLUEPRINT_ID);
	}

	public Map<Locale, String> getTitleFromRequest(
		PortletRequest portletRequest) {

		return LocalizationUtil.getLocalizationMap(
			portletRequest, BlueprintsAdminWebKeys.TITLE);
	}

	public int getTypeFromRequest(PortletRequest portletRequest) {
		return ParamUtil.getInteger(
			portletRequest, BlueprintsAdminWebKeys.BLUEPRINT_TYPE);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BlueprintsAdminRequestHelper.class);

	@Reference
	private BlueprintService _blueprintService;

}