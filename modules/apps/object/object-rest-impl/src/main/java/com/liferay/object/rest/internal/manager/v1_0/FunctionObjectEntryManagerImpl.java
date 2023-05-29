/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.object.rest.internal.manager.v1_0;

import com.liferay.object.constants.ObjectActionKeys;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.dto.v1_0.Status;
import com.liferay.object.rest.internal.configuration.FunctionObjectEntryManagerConfiguration;
import com.liferay.object.rest.manager.v1_0.BaseObjectEntryManager;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.osgi.util.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.catapult.PortalCatapult;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.aggregation.Aggregation;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Feliphe Marinho
 */
@Component(
	configurationPid = "com.liferay.object.rest.internal.configuration.FunctionObjectEntryManagerConfiguration",
	factory = "com.liferay.object.rest.internal.manager.v1_0.FunctionObjectEntryManagerImpl",
	service = ObjectEntryManager.class
)
public class FunctionObjectEntryManagerImpl
	extends BaseObjectEntryManager implements ObjectEntryManager {

	@Override
	public ObjectEntry addObjectEntry(
			DTOConverterContext dtoConverterContext,
			ObjectDefinition objectDefinition, ObjectEntry objectEntry,
			String scopeKey)
		throws Exception {

		checkPortletResourcePermission(
			objectDefinition, scopeKey, dtoConverterContext.getUser(),
			ObjectActionKeys.ADD_OBJECT_ENTRY);

		return _toObjectEntry(
			_launch(
				new RequestBodyBuilder(
				).companyId(
					_companyId
				).locale(
					dtoConverterContext.getLocale()
				).objectDefinitionExternalReferenceCode(
					objectDefinition.getExternalReferenceCode()
				).objectEntry(
					objectEntry
				).scopeKey(
					scopeKey
				).uriInfo(
					dtoConverterContext.getUriInfo()
				).userId(
					dtoConverterContext.getUserId()
				).buildJSONObject(),
				_functionObjectEntryManagerConfiguration.postResourcePath(),
				dtoConverterContext.getUserId()),
			objectDefinition, scopeKey, dtoConverterContext.getUser());
	}

	@Override
	public void deleteObjectEntry(
			long companyId, DTOConverterContext dtoConverterContext,
			String externalReferenceCode, ObjectDefinition objectDefinition,
			String scopeKey)
		throws Exception {

		checkPortletResourcePermission(
			objectDefinition, scopeKey, dtoConverterContext.getUser(),
			ActionKeys.DELETE);

		_launch(
			new RequestBodyBuilder(
			).companyId(
				_companyId
			).externalReferenceCode(
				externalReferenceCode
			).locale(
				dtoConverterContext.getLocale()
			).objectDefinitionExternalReferenceCode(
				objectDefinition.getExternalReferenceCode()
			).scopeKey(
				scopeKey
			).uriInfo(
				dtoConverterContext.getUriInfo()
			).userId(
				dtoConverterContext.getUserId()
			).buildJSONObject(),
			_functionObjectEntryManagerConfiguration.deleteResourcePath(),
			dtoConverterContext.getUserId());
	}

	@Override
	public Page<ObjectEntry> getObjectEntries(
			long companyId, ObjectDefinition objectDefinition, String scopeKey,
			Aggregation aggregation, DTOConverterContext dtoConverterContext,
			String filterString, Pagination pagination, String search,
			Sort[] sorts)
		throws Exception {

		checkPortletResourcePermission(
			objectDefinition, scopeKey, dtoConverterContext.getUser(),
			ActionKeys.VIEW);

		return _toObjectEntries(
			_launch(
				new RequestBodyBuilder(
				).companyId(
					_companyId
				).aggregation(
					aggregation
				).filterString(
					filterString
				).locale(
					dtoConverterContext.getLocale()
				).objectDefinitionExternalReferenceCode(
					objectDefinition.getExternalReferenceCode()
				).pagination(
					pagination
				).scopeKey(
					scopeKey
				).search(
					search
				).sorts(
					sorts
				).uriInfo(
					dtoConverterContext.getUriInfo()
				).userId(
					dtoConverterContext.getUserId()
				).buildJSONObject(),
				_functionObjectEntryManagerConfiguration.getResourcePath(),
				dtoConverterContext.getUserId()),
			objectDefinition, pagination, scopeKey,
			dtoConverterContext.getUser());
	}

	@Override
	public ObjectEntry getObjectEntry(
			long companyId, DTOConverterContext dtoConverterContext,
			String externalReferenceCode, ObjectDefinition objectDefinition,
			String scopeKey)
		throws Exception {

		checkPortletResourcePermission(
			objectDefinition, scopeKey, dtoConverterContext.getUser(),
			ActionKeys.VIEW);

		if (Validator.isNull(externalReferenceCode)) {
			return null;
		}

		return _toObjectEntry(
			_launch(
				new RequestBodyBuilder(
				).companyId(
					_companyId
				).externalReferenceCode(
					externalReferenceCode
				).locale(
					dtoConverterContext.getLocale()
				).objectDefinitionExternalReferenceCode(
					objectDefinition.getExternalReferenceCode()
				).scopeKey(
					scopeKey
				).uriInfo(
					dtoConverterContext.getUriInfo()
				).userId(
					dtoConverterContext.getUserId()
				).buildJSONObject(),
				_functionObjectEntryManagerConfiguration.getOneResourcePath(),
				dtoConverterContext.getUserId()),
			objectDefinition, scopeKey, dtoConverterContext.getUser());
	}

	@Override
	public String getStorageLabel(Locale locale) {
		return _storageLabel;
	}

	@Override
	public String getStorageType() {
		return _storageType;
	}

	@Override
	public boolean isAllowedCompany(long companyId) {
		if (_companyId == companyId) {
			return true;
		}

		return false;
	}

	@Override
	public ObjectEntry updateObjectEntry(
			long companyId, DTOConverterContext dtoConverterContext,
			String externalReferenceCode, ObjectDefinition objectDefinition,
			ObjectEntry objectEntry, String scopeKey)
		throws Exception {

		checkPortletResourcePermission(
			objectDefinition, scopeKey, dtoConverterContext.getUser(),
			ActionKeys.UPDATE);

		return _toObjectEntry(
			_launch(
				new RequestBodyBuilder(
				).companyId(
					_companyId
				).externalReferenceCode(
					externalReferenceCode
				).locale(
					dtoConverterContext.getLocale()
				).objectDefinitionExternalReferenceCode(
					objectDefinition.getExternalReferenceCode()
				).objectEntry(
					objectEntry
				).scopeKey(
					scopeKey
				).uriInfo(
					dtoConverterContext.getUriInfo()
				).userId(
					dtoConverterContext.getUserId()
				).buildJSONObject(),
				_functionObjectEntryManagerConfiguration.patchResourcePath(),
				dtoConverterContext.getUserId()),
			objectDefinition, scopeKey, dtoConverterContext.getUser());
	}

	@Activate
	protected void activate(Map<String, Object> properties) throws Exception {
		_companyId = ConfigurationFactoryUtil.getCompanyId(
			_companyLocalService, properties);
		_functionObjectEntryManagerConfiguration =
			ConfigurableUtil.createConfigurable(
				FunctionObjectEntryManagerConfiguration.class, properties);
		_storageLabel = GetterUtil.getString(properties.get("name"));
		_storageType = GetterUtil.getString(properties.get("storage.type"));
	}

	private byte[] _launch(
			JSONObject payloadJSONObject, String resourcePath, long userId)
		throws Exception {

		return _portalCatapult.launch(
			_companyId,
			_functionObjectEntryManagerConfiguration.
				oAuth2ApplicationExternalReferenceCode(),
			payloadJSONObject, resourcePath, userId);
	}

	private Page<ObjectEntry> _toObjectEntries(
			byte[] bytes, ObjectDefinition objectDefinition,
			Pagination pagination, String scopeKey, User user)
		throws Exception {

		JSONObject jsonObject = _jsonFactory.createJSONObject(
			new String(bytes));

		return Page.of(
			JSONUtil.toList(
				(JSONArray)jsonObject.get("items"),
				itemJSONObject -> _toObjectEntry(
					itemJSONObject.toString(), objectDefinition, scopeKey,
					user)),
			pagination, (Integer)jsonObject.get("totalCount"));
	}

	private ObjectEntry _toObjectEntry(
		byte[] bytes, ObjectDefinition objectDefinition, String scopeKey,
		User user) {

		return _toObjectEntry(
			new String(bytes), objectDefinition, scopeKey, user);
	}

	private ObjectEntry _toObjectEntry(
		String json, ObjectDefinition objectDefinition, String scopeKey,
		User user) {

		ObjectEntry objectEntry = ObjectEntry.unsafeToDTO(json);

		objectEntry.setActions(
			HashMapBuilder.put(
				"delete", addDeleteAction(objectDefinition, scopeKey, user)
			).build());

		objectEntry.setExternalReferenceCode(
			() -> {
				Map<String, Object> properties = objectEntry.getProperties();

				return String.valueOf(properties.get("externalReferenceCode"));
			});

		if (objectEntry.getStatus() == null) {
			objectEntry.setStatus(
				new Status() {
					{
						code = WorkflowConstants.STATUS_APPROVED;
						label = WorkflowConstants.LABEL_APPROVED;
						label_i18n = language.get(
							user.getLocale(), WorkflowConstants.LABEL_APPROVED);
					}
				});
		}

		return objectEntry;
	}

	private long _companyId;

	@Reference
	private CompanyLocalService _companyLocalService;

	private FunctionObjectEntryManagerConfiguration
		_functionObjectEntryManagerConfiguration;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private PortalCatapult _portalCatapult;

	private String _storageLabel;
	private String _storageType;

}