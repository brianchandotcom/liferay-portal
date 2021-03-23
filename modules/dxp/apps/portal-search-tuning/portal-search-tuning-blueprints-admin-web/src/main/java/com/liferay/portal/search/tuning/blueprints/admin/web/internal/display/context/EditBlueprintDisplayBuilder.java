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

package com.liferay.portal.search.tuning.blueprints.admin.web.internal.display.context;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.Team;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.search.tuning.blueprints.admin.web.internal.constants.BlueprintsAdminMVCCommandNames;
import com.liferay.portal.search.tuning.blueprints.admin.web.internal.util.BlueprintsAdminAssetUtil;
import com.liferay.portal.search.tuning.blueprints.admin.web.internal.util.BlueprintsAdminComponentUtil;
import com.liferay.portal.search.tuning.blueprints.admin.web.internal.util.BlueprintsAdminFieldsUtil;
import com.liferay.portal.search.tuning.blueprints.constants.BlueprintTypes;
import com.liferay.portal.search.tuning.blueprints.constants.BlueprintsPortletKeys;
import com.liferay.portal.search.tuning.blueprints.model.Blueprint;
import com.liferay.portal.search.tuning.blueprints.service.BlueprintService;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceURL;
import javax.portlet.WindowStateException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Kevin Tan
 */
public class EditBlueprintDisplayBuilder extends EditEntryDisplayBuilder {

	public EditBlueprintDisplayBuilder(
		BlueprintService blueprintService,
		HttpServletRequest httpServletRequest, Language language,
		JSONFactory jsonFactory, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		super(
			blueprintService, httpServletRequest, language, jsonFactory,
			renderRequest, renderResponse);
	}

	public BlueprintDisplayContext build() {
		BlueprintDisplayContext blueprintDisplayContext =
			new BlueprintDisplayContext();

		setConfigurationId(blueprintDisplayContext);
		setConfigurationType(blueprintDisplayContext);
		setData(blueprintDisplayContext, _getProps());
		setPageTitle(blueprintDisplayContext);
		setRedirect(blueprintDisplayContext);

		return blueprintDisplayContext;
	}

	private JSONObject _getEntityJSONObject() {
		String[] entityClassNames = {
			Group.class.getName(), Organization.class.getName(),
			Team.class.getName(), Role.class.getName(), User.class.getName(),
			UserGroup.class.getName()
		};

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		for (String entityClassName : entityClassNames) {
			jsonObject.put(
				entityClassName, _getSelectEntityJSONObject(entityClassName));
		}

		return jsonObject;
	}

	private Map<String, Object> _getProps() {
		Map<String, Object> props = HashMapBuilder.<String, Object>put(
			"blueprintId", blueprintId
		).put(
			"blueprintType", blueprintType
		).put(
			"entityJSON", _getEntityJSONObject()
		).put(
			"indexFields",
			BlueprintsAdminFieldsUtil.getFieldsJSONArray(
				themeDisplay.getCompanyId())
		).put(
			"keywordQueryContributors",
			BlueprintsAdminComponentUtil.getKeywordQueryContributors()
		).put(
			"modelPrefilterContributors",
			BlueprintsAdminComponentUtil.getModelPrefilterContributors()
		).put(
			"queryElements", _getQueryElementsJSONArray()
		).put(
			"queryPrefilterContributors",
			BlueprintsAdminComponentUtil.getQueryPrefilterContributors()
		).put(
			"redirectURL", getRedirect()
		).put(
			"searchableAssetTypes",
			BlueprintsAdminAssetUtil.getSearchableAssetNames(
				themeDisplay.getCompanyId())
		).put(
			"searchResultsURL", _getSearchResultsURL()
		).put(
			"submitFormURL",
			getSubmitFormURL(BlueprintsAdminMVCCommandNames.EDIT_BLUEPRINT)
		).build();

		if (blueprint != null) {
			props.put(
				"initialConfigurationString", blueprint.getConfiguration());
			props.put("initialDescription", getDescriptionJSONObject());
			props.put(
				"initialSelectedElementsString",
				blueprint.getSelectedElements());
			props.put("initialTitle", getTitleJSONObject());
		}

		return props;
	}

	private JSONArray _getQueryElementsJSONArray() {
		int blueprintsTotalCount = blueprintService.getGroupBlueprintsCount(
			themeDisplay.getCompanyGroupId(), WorkflowConstants.STATUS_APPROVED,
			BlueprintTypes.QUERY_ELEMENT);

		List<Blueprint> queryElements = blueprintService.getGroupBlueprints(
			themeDisplay.getCompanyGroupId(), BlueprintTypes.QUERY_ELEMENT, 0,
			blueprintsTotalCount);

		JSONArray queryElementsJSONArray = jsonFactory.createJSONArray();

		for (Blueprint blueprint : queryElements) {
			try {
				JSONObject jsonObject = jsonFactory.createJSONObject(
					blueprint.getConfiguration());

				queryElementsJSONArray.put(jsonObject);
			}
			catch (Exception exception) {
				_log.error(exception, exception);
			}
		}

		return queryElementsJSONArray;
	}

	private String _getSearchResultsURL() {
		ResourceURL resourceURL = renderResponse.createResourceURL();

		resourceURL.setResourceID(
			BlueprintsAdminMVCCommandNames.PREVIEW_BLUEPRINT);

		return resourceURL.toString();
	}

	private JSONObject _getSelectEntityJSONObject(String className) {
		try {
			PortletURL portletURL = PortletProviderUtil.getPortletURL(
				renderRequest, className, PortletProvider.Action.BROWSE);

			if (portletURL == null) {
				return null;
			}

			boolean multiple = false;

			if (className.equals(User.class.getName())) {
				_prepareSelectEntityURL(
					portletURL, BlueprintsAdminMVCCommandNames.SELECT_USERS);

				multiple = true;
			}

			/*
			LPS-129056 - Address selector functionality

			else if (className.equals(Organization.class.getName())) {
				_prepareSelectEntityURL(
					portletURL,
					BlueprintsAdminMVCCommandNames.SELECT_ORGANIZATIONS);

				multiple = true;
			}
			*/

			return JSONUtil.put(
				"multiple", multiple
			).put(
				"title",
				_getSelectEntityTitle(themeDisplay.getLocale(), className)
			).put(
				"url", portletURL.toString()
			);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to get select entity", exception);
			}

			return null;
		}
	}

	private String _getSelectEntityTitle(Locale locale, String className) {
		String title = ResourceActionsUtil.getModelResource(locale, className);

		return LanguageUtil.format(locale, "select-x", title);
	}

	private void _prepareSelectEntityURL(
			PortletURL portletURL, String mvcRenderCommandName)
		throws WindowStateException {

		portletURL = PortalUtil.getControlPanelPortletURL(
			renderRequest, BlueprintsPortletKeys.BLUEPRINTS_ADMIN,
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter("mvcRenderCommandName", mvcRenderCommandName);
		portletURL.setParameter("eventName", "selectEntity");
		portletURL.setWindowState(LiferayWindowState.POP_UP);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditBlueprintDisplayBuilder.class);

}