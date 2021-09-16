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

package com.liferay.search.experiences.internal.importer;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.search.experiences.importer.SXPImporter;
import com.liferay.search.experiences.service.SXPBlueprintLocalService;
import com.liferay.search.experiences.service.SXPElementLocalService;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(immediate = true, service = SXPImporter.class)
public class SXPImporterImpl implements SXPImporter {

	@Override
	public void importSXPBlueprint(
			long companyId, long groupId, long userId, JSONObject jsonObject)
		throws PortalException {

		_addSXPBlueprint(
			jsonObject, _createServiceContext(companyId, groupId, userId));
	}

	@Override
	public void importSXPElement(
			long companyId, long groupId, long userId, JSONObject jsonObject,
			boolean readOnly)
		throws PortalException {

		_addSXPElement(
			jsonObject, readOnly,
			_createServiceContext(companyId, groupId, userId));
	}

	private void _addSXPBlueprint(
			JSONObject jsonObject, ServiceContext serviceContext)
		throws PortalException {

		JSONObject payloadJSONObject = jsonObject.getJSONObject(
			"blueprint-payload");

		if (payloadJSONObject == null) {
			throw new PortalException("blueprint-payload is required");
		}

		_saveSXPBlueprint(
			_getConfigurationsJSON(payloadJSONObject),
			_getDescriptionMap(payloadJSONObject),
			_getElementInstancesJSON(payloadJSONObject),
			_getTitleMap(payloadJSONObject), serviceContext);
	}

	private void _addSXPElement(
			JSONObject jsonObject, boolean readOnly,
			ServiceContext serviceContext)
		throws PortalException {

		JSONObject payloadJSONObject = jsonObject.getJSONObject(
			"element-payload");

		if (payloadJSONObject == null) {
			throw new PortalException("element-payload is required");
		}

		_saveSXPElement(
			_getDescriptionMap(payloadJSONObject),
			_getElementDefinitionJSON(payloadJSONObject), readOnly,
			_getTitleMap(payloadJSONObject), jsonObject.getInt("type"),
			serviceContext);
	}

	private ServiceContext _createServiceContext(
			long companyId, long groupId, long userId)
		throws PortalException {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setCompanyId(companyId);
		serviceContext.setScopeGroupId(groupId);
		serviceContext.setUserId(userId);

		return serviceContext;
	}

	private String _getConfigurationsJSON(JSONObject jsonObject)
		throws PortalException {

		JSONObject configurationsJSONObject = jsonObject.getJSONObject(
			"configurationsJSON");

		if (configurationsJSONObject == null) {
			throw new PortalException("configurationsJSON is missing");
		}

		return configurationsJSONObject.toJSONString();
	}

	private Map<Locale, String> _getDescriptionMap(JSONObject jsonObject) {
		JSONObject descriptionJSONObject = jsonObject.getJSONObject(
			"description");

		if (descriptionJSONObject == null) {
			return null;
		}

		return _getLocalizationMap(descriptionJSONObject);
	}

	private String _getElementDefinitionJSON(JSONObject jsonObject)
		throws PortalException {

		JSONObject elementDefinitionJSONObject = jsonObject.getJSONObject(
			"elementDefinitionJSON");

		if (elementDefinitionJSONObject == null) {
			throw new PortalException("elementDefinitionJSON is required");
		}

		return elementDefinitionJSONObject.toJSONString();
	}

	private String _getElementInstancesJSON(JSONObject jsonObject)
		throws PortalException {

		JSONObject elementInstancesJSONObject = jsonObject.getJSONObject(
			"elementInstancesJSON");

		if (elementInstancesJSONObject == null) {
			throw new PortalException("elementInstancesJSON is required");
		}

		return elementInstancesJSONObject.toJSONString();
	}

	private Map<Locale, String> _getLocalizationMap(JSONObject jsonObject) {
		Map<Locale, String> map = new HashMap<>();

		Set<String> languageIds = jsonObject.keySet();

		Stream<String> stream = languageIds.stream();

		stream.forEach(
			s -> {
				if ((s != null) && (s.length() == 5) && s.contains("_")) {
					String[] arr = s.split("_");

					map.put(
						new Locale(arr[0], arr[1]), jsonObject.getString(s));
				}
			});

		return map;
	}

	private Map<Locale, String> _getTitleMap(JSONObject jsonObject)
		throws PortalException {

		JSONObject titleJSONObject = jsonObject.getJSONObject("title");

		if (titleJSONObject == null) {
			throw new PortalException("title is required");
		}

		return _getLocalizationMap(titleJSONObject);
	}

	private void _saveSXPBlueprint(
			String configurationsJSON, Map<Locale, String> descriptionMap,
			String elementInstancesJSON, Map<Locale, String> titleMap,
			ServiceContext serviceContext)
		throws PortalException {

		_sxpBlueprintLocalService.addSXPBlueprint(
			serviceContext.getUserId(), serviceContext.getScopeGroupId(),
			configurationsJSON, descriptionMap, elementInstancesJSON, titleMap,
			serviceContext);
	}

	private void _saveSXPElement(
			Map<Locale, String> descriptionMap, String elementDefinitionJSON,
			boolean readOnly, Map<Locale, String> titleMap, int type,
			ServiceContext serviceContext)
		throws PortalException {

		_sxpElementLocalService.addSXPElement(
			serviceContext.getUserId(), serviceContext.getScopeGroupId(),
			descriptionMap, elementDefinitionJSON, readOnly, titleMap, type,
			serviceContext);
	}

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private SXPBlueprintLocalService _sxpBlueprintLocalService;

	@Reference
	private SXPElementLocalService _sxpElementLocalService;

}