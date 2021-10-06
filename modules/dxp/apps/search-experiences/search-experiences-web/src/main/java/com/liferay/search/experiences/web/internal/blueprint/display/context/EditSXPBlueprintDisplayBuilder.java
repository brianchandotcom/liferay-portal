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

package com.liferay.search.experiences.web.internal.blueprint.display.context;

import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringBundler;
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
import com.liferay.search.experiences.constants.SXPElementTypes;
import com.liferay.search.experiences.constants.SXPPortletKeys;
import com.liferay.search.experiences.model.SXPBlueprint;
import com.liferay.search.experiences.model.SXPElement;
import com.liferay.search.experiences.service.SXPElementService;
import com.liferay.search.experiences.web.internal.blueprint.constants.SXPBlueprintMVCCommandNames;
import com.liferay.search.experiences.web.internal.blueprint.util.SXPBlueprintAssetUtil;
import com.liferay.search.experiences.web.internal.blueprint.util.SXPBlueprintContributorUtil;
import com.liferay.search.experiences.web.internal.blueprint.util.SXPBlueprintIndexFieldUtil;
import com.liferay.search.experiences.web.internal.blueprint.util.SXPBlueprintRequestUtil;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceURL;
import javax.portlet.WindowStateException;

/**
 * @author Kevin Tan
 * @author Petteri Karttunen
 */
public class EditSXPBlueprintDisplayBuilder extends EditEntryDisplayBuilder {

	public EditSXPBlueprintDisplayBuilder(
		RenderRequest renderRequest, RenderResponse renderResponse,
		SXPElementService sxpElementService, JSONFactory jsonFactory,
		Language language) {

		super(
			renderRequest, renderResponse, sxpElementService, jsonFactory,
			language);

		_sxpBlueprint = getSXPBlueprint();

		_sxpBlueprintId = SXPBlueprintRequestUtil.getSXPBlueprintId(
			renderRequest);
	}

	public EntryDisplayContext build() {
		EntryDisplayContext entryDisplayContext = new EntryDisplayContext();

		entryDisplayContext.setId(_sxpBlueprintId);

		setData(entryDisplayContext, _getProps());
		_setPageTitle(entryDisplayContext);
		setRedirect(entryDisplayContext);

		return entryDisplayContext;
	}

	protected SXPBlueprint getSXPBlueprint() {
		Optional<SXPBlueprint> optional =
			SXPBlueprintRequestUtil.getSXPBlueprint(
				renderRequest, renderResponse);

		return optional.orElse(null);
	}

	private JSONObject _getEntityJSONObject() {
		String[] entityClassNames = {
			Group.class.getName(), Organization.class.getName(),
			Role.class.getName(), Team.class.getName(), User.class.getName(),
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
			"entityJSON", _getEntityJSONObject()
		).put(
			"indexFields",
			SXPBlueprintIndexFieldUtil.getFieldsJSONArray(
				themeDisplay.getCompanyId())
		).put(
			"keywordQueryContributors",
			SXPBlueprintContributorUtil.getKeywordQueryContributors()
		).put(
			"modelPrefilterContributors",
			SXPBlueprintContributorUtil.getModelPrefilterContributors()
		).put(
			"queryElements", _getQueryElementsJSONArray()
		).put(
			"queryPrefilterContributors",
			SXPBlueprintContributorUtil.getQueryPrefilterContributors()
		).put(
			"redirectURL", getRedirect()
		).put(
			"searchableAssetTypes",
			SXPBlueprintAssetUtil.getSearchableAssetNamesJSONArray(
				themeDisplay.getCompanyId(), themeDisplay.getLocale())
		).put(
			"searchResultsURL", _getSearchResultsURL()
		).put(
			"submitFormURL",
			getSubmitFormURL(
				SXPBlueprintMVCCommandNames.EDIT_SXP_BLUEPRINT,
				_sxpBlueprint != null)
		).put(
			"sxpBlueprintId", _sxpBlueprintId
		).put(
			"validateSXPBlueprintURL", _getValidateSXPBlueprintURL()
		).build();

		if (_sxpBlueprint != null) {
			props.put(
				"initialConfigurationString",
				_sxpBlueprint.getConfigurationsJSON());
			props.put(
				"initialDescription",
				getDescriptionJSONObject(_sxpBlueprint.getDescriptionMap()));
			props.put(
				"initialSelectedElementsString",
				_sxpBlueprint.getElementInstancesJSON());
			props.put(
				"initialTitle",
				getTitleJSONObject(_sxpBlueprint.getTitleMap()));
		}

		return props;
	}

	private JSONArray _getQueryElementsJSONArray() {

		// TODO: restore getElementsCount in service

		int sxpBlueprintsTotalCount = sxpElementService.getElementsCount(
			themeDisplay.getCompanyGroupId(), WorkflowConstants.STATUS_APPROVED,
			SXPElementTypes.QUERY_SXP_ELEMENT);

		// TODO: restore getElementsCount in service

		List<SXPElement> queryElements = sxpElementService.getGroupElements(
			themeDisplay.getCompanyGroupId(), SXPElementTypes.QUERY_SXP_ELEMENT,
			0, sxpBlueprintsTotalCount);

		JSONArray queryElementsJSONArray = jsonFactory.createJSONArray();

		for (SXPElement sxpElement : queryElements) {
			try {
				if (!sxpElement.isHidden()) {
					JSONObject jsonObject = jsonFactory.createJSONObject(
						sxpElement.getElementDefinitionJSON());

					queryElementsJSONArray.put(jsonObject);
				}
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
			SXPBlueprintMVCCommandNames.PREVIEW_SXP_BLUEPRINT);

		return resourceURL.toString();
	}

	private JSONObject _getSelectEntityJSONObject(String className) {
		try {
			PortletURL portletURL = PortletProviderUtil.getPortletURL(
				renderRequest, className, PortletProvider.Action.BROWSE);

			if (portletURL == null) {
				return null;
			}

			portletURL.setWindowState(LiferayWindowState.POP_UP);

			boolean multiple = false;

			if (className.equals(User.class.getName())) {
				portletURL = _getSelectEntityURL(
					SXPBlueprintMVCCommandNames.SELECT_USERS);

				multiple = true;
			}
			else if (className.equals(Organization.class.getName())) {
				portletURL = _getSelectEntityURL(
					SXPBlueprintMVCCommandNames.SELECT_ORGANIZATIONS);

				multiple = true;
			}

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

	private PortletURL _getSelectEntityURL(String mvcRenderCommandName)
		throws WindowStateException {

		return PortletURLBuilder.create(
			PortalUtil.getControlPanelPortletURL(
				renderRequest, SXPPortletKeys.SXP_BLUEPRINT,
				PortletRequest.RENDER_PHASE)
		).setMVCRenderCommandName(
			mvcRenderCommandName
		).setParameter(
			"eventName", "selectEntity"
		).setWindowState(
			LiferayWindowState.POP_UP
		).build();
	}

	private String _getValidateSXPBlueprintURL() {
		ResourceURL resourceURL = renderResponse.createResourceURL();

		resourceURL.setResourceID(
			SXPBlueprintMVCCommandNames.VALIDATE_SXP_BLUEPRINT);

		return resourceURL.toString();
	}

	private void _setPageTitle(EntryDisplayContext entryDisplayContext) {
		StringBundler sb = new StringBundler(2);

		sb.append((_sxpBlueprint != null) ? "edit-" : "add-");
		sb.append("sxpBlueprint");

		entryDisplayContext.setPageTitle(
			language.get(httpServletRequest, sb.toString()));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditSXPBlueprintDisplayBuilder.class);

	private final SXPBlueprint _sxpBlueprint;
	private final long _sxpBlueprintId;

}