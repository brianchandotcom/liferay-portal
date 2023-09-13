/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.web.internal.object.definitions.display.context;

import com.liferay.application.list.PanelCategory;
import com.liferay.application.list.PanelCategoryRegistry;
import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.change.tracking.configuration.CTSettingsConfiguration;
import com.liferay.object.constants.ObjectActionKeys;
import com.liferay.object.constants.ObjectWebKeys;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManagerRegistry;
import com.liferay.object.scope.ObjectScopeProvider;
import com.liferay.object.scope.ObjectScopeProviderRegistry;
import com.liferay.object.web.internal.display.context.helper.ObjectRequestHelper;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowStateException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marco Leo
 */
public class ObjectDefinitionsDetailsDisplayContext
	extends BaseObjectDefinitionsDisplayContext {

	public ObjectDefinitionsDetailsDisplayContext(
		ConfigurationProvider configurationProvider,
		HttpServletRequest httpServletRequest,
		ModelResourcePermission<ObjectDefinition>
			objectDefinitionModelResourcePermission,
		ObjectEntryManagerRegistry objectEntryManagerRegistry,
		ObjectScopeProviderRegistry objectScopeProviderRegistry,
		PanelCategoryRegistry panelCategoryRegistry) {

		super(httpServletRequest, objectDefinitionModelResourcePermission);

		_configurationProvider = configurationProvider;
		_objectEntryManagerRegistry = objectEntryManagerRegistry;
		_objectScopeProviderRegistry = objectScopeProviderRegistry;
		_panelCategoryRegistry = panelCategoryRegistry;

		_objectRequestHelper = new ObjectRequestHelper(httpServletRequest);
	}

	public String getEditObjectDefinitionURL() throws Exception {
		return PortletURLBuilder.create(
			getPortletURL()
		).setMVCRenderCommandName(
			"/object_definitions/edit_object_definition"
		).setParameter(
			"objectDefinitionId", "{id}"
		).buildString();
	}

	public List<Map<String, Object>> getNonrelationshipObjectFieldsInfo() {
		List<ObjectField> objectFields = ListUtil.filter(
			getObjectFields(),
			objectField -> Validator.isNull(objectField.getRelationshipType()));

		List<Map<String, Object>> nonrelationshipObjectFieldsInfo =
			new ArrayList<>();

		for (ObjectField objectField : objectFields) {
			nonrelationshipObjectFieldsInfo.add(
				HashMapBuilder.<String, Object>put(
					"label",
					LocalizationUtil.getLocalizationMap(objectField.getLabel())
				).put(
					"name", objectField.getName()
				).build());
		}

		return nonrelationshipObjectFieldsInfo;
	}

	public ObjectDefinition getObjectDefinition() {
		HttpServletRequest httpServletRequest =
			objectRequestHelper.getRequest();

		return (ObjectDefinition)httpServletRequest.getAttribute(
			ObjectWebKeys.OBJECT_DEFINITION);
	}

	public List<ObjectField> getObjectFields() {
		HttpServletRequest httpServletRequest =
			objectRequestHelper.getRequest();

		return (List<ObjectField>)httpServletRequest.getAttribute(
			ObjectWebKeys.OBJECT_FIELDS);
	}

	public String getPermissionsURL(String modelResource) throws Exception {
		PortletURL portletURL = PortletURLBuilder.create(
			PortalUtil.getControlPanelPortletURL(
				_objectRequestHelper.getRequest(),
				"com_liferay_portlet_configuration_web_portlet_" +
					"PortletConfigurationPortlet",
				ActionRequest.RENDER_PHASE)
		).setMVCPath(
			"/edit_permissions.jsp"
		).setRedirect(
			_objectRequestHelper.getCurrentURL()
		).setParameter(
			"modelResource", modelResource
		).setParameter(
			"modelResourceDescription", "{name}"
		).setParameter(
			"resourcePrimKey", "{id}"
		).buildPortletURL();

		try {
			portletURL.setWindowState(LiferayWindowState.POP_UP);
		}
		catch (WindowStateException windowStateException) {
			throw new PortalException(windowStateException);
		}

		return portletURL.toString();
	}

	public String getScope() {
		ObjectDefinition objectDefinition = getObjectDefinition();

		return ParamUtil.getString(
			objectRequestHelper.getRequest(), "scope",
			objectDefinition.getScope());
	}

	public List<KeyValuePair> getScopeKeyValuePairs(String scope) {
		List<KeyValuePair> keyValuePairs = new ArrayList<>();

		ObjectScopeProvider objectScopeProvider =
			_objectScopeProviderRegistry.getObjectScopeProvider(scope);

		for (String panelCategoryKey :
				objectScopeProvider.getRootPanelCategoryKeys()) {

			if (panelCategoryKey.equals(PanelCategoryKeys.COMMERCE)) {
				continue;
			}

			PanelCategory panelCategory =
				_panelCategoryRegistry.getPanelCategory(panelCategoryKey);

			List<PanelCategory> childPanelCategories =
				_panelCategoryRegistry.getChildPanelCategories(
					panelCategoryKey);

			for (PanelCategory childPanelCategory : childPanelCategories) {
				keyValuePairs.add(
					new KeyValuePair(
						childPanelCategory.getKey(),
						StringBundler.concat(
							panelCategory.getLabel(
								objectRequestHelper.getLocale()),
							" > ",
							childPanelCategory.getLabel(
								objectRequestHelper.getLocale()))));
			}
		}

		return keyValuePairs;
	}

	public JSONArray getStorageTypesJSONArray() throws Exception {
		return JSONUtil.toJSONArray(
			_objectEntryManagerRegistry.getObjectEntryManagers(
				_objectRequestHelper.getCompanyId()),
			objectEntryManager -> JSONUtil.put(
				"label",
				objectEntryManager.getStorageLabel(
					_objectRequestHelper.getLocale())
			).put(
				"value", objectEntryManager.getStorageType()
			));
	}

	public boolean hasPublishObjectPermission() {
		PortletResourcePermission portletResourcePermission =
			objectDefinitionModelResourcePermission.
				getPortletResourcePermission();

		return portletResourcePermission.contains(
			objectRequestHelper.getPermissionChecker(), null,
			ObjectActionKeys.PUBLISH_OBJECT_DEFINITION);
	}

	public boolean isChangeTrackingEnabled() throws Exception {
		CTSettingsConfiguration ctSettingsConfiguration =
			_configurationProvider.getCompanyConfiguration(
				CTSettingsConfiguration.class,
				_objectRequestHelper.getCompanyId());

		return ctSettingsConfiguration.enabled();
	}

	private final ConfigurationProvider _configurationProvider;
	private final ObjectEntryManagerRegistry _objectEntryManagerRegistry;
	private final ObjectRequestHelper _objectRequestHelper;
	private final ObjectScopeProviderRegistry _objectScopeProviderRegistry;
	private final PanelCategoryRegistry _panelCategoryRegistry;

}