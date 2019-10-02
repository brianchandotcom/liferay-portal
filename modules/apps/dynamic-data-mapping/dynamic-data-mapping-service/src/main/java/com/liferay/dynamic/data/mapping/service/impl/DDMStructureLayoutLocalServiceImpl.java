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

package com.liferay.dynamic.data.mapping.service.impl;

import com.liferay.dynamic.data.mapping.internal.search.util.DDMSearchHelper;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutSerializerSerializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutSerializerSerializeResponse;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructureLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.service.base.DDMStructureLayoutLocalServiceBaseImpl;
import com.liferay.dynamic.data.mapping.validator.DDMFormLayoutValidator;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(
	property = "model.class.name=com.liferay.dynamic.data.mapping.model.DDMStructureLayout",
	service = AopService.class
)
public class DDMStructureLayoutLocalServiceImpl
	extends DDMStructureLayoutLocalServiceBaseImpl {

	@Override
	public DDMStructureLayout addStructureLayout(
			long userId, long groupId, long structureVersionId,
			DDMFormLayout ddmFormLayout, ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		validate(ddmFormLayout);

		long structureLayoutId = counterLocalService.increment();

		DDMStructureLayout structureLayout =
			ddmStructureLayoutPersistence.create(structureLayoutId);

		structureLayout.setUuid(serviceContext.getUuid());
		structureLayout.setGroupId(groupId);
		structureLayout.setCompanyId(user.getCompanyId());
		structureLayout.setUserId(user.getUserId());
		structureLayout.setUserName(user.getFullName());
		structureLayout.setStructureLayoutKey(
			String.valueOf(counterLocalService.increment()));
		structureLayout.setStructureVersionId(structureVersionId);
		structureLayout.setDefinition(serialize(ddmFormLayout));

		return ddmStructureLayoutPersistence.update(structureLayout);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 *             #addStructureLayout(long, long, long, long, Map, Map, String,
	 *             String, ServiceContext)}
	 */
	@Deprecated
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public DDMStructureLayout addStructureLayout(
			long userId, long groupId, long structureVersionId,
			Map<Locale, String> name, Map<Locale, String> description,
			String definition, ServiceContext serviceContext)
		throws PortalException {

		return addStructureLayout(
			userId, groupId, 0L,
			String.valueOf(counterLocalService.increment()), structureVersionId,
			name, description, definition, serviceContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public DDMStructureLayout addStructureLayout(
			long userId, long groupId, long classNameId,
			String structureLayoutKey, long structureVersionId,
			Map<Locale, String> name, Map<Locale, String> description,
			String definition, ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		long structureLayoutId = counterLocalService.increment();

		DDMStructureLayout structureLayout =
			ddmStructureLayoutPersistence.create(structureLayoutId);

		structureLayout.setUuid(serviceContext.getUuid());
		structureLayout.setGroupId(groupId);
		structureLayout.setCompanyId(user.getCompanyId());
		structureLayout.setUserId(user.getUserId());
		structureLayout.setUserName(user.getFullName());
		structureLayout.setCreateDate(new Date());
		structureLayout.setModifiedDate(new Date());
		structureLayout.setClassNameId(classNameId);
		structureLayout.setStructureLayoutKey(
			Optional.ofNullable(
				structureLayoutKey
			).orElseGet(
				() -> String.valueOf(counterLocalService.increment())
			));
		structureLayout.setStructureVersionId(structureVersionId);
		structureLayout.setNameMap(name);
		structureLayout.setDescriptionMap(description);
		structureLayout.setDefinition(definition);

		return ddmStructureLayoutPersistence.update(structureLayout);
	}

	@Override
	public void deleteDDMStructureLayouts(
			long classNameId, DDMStructureVersion ddmStructureVersion)
		throws PortalException {

		List<DDMStructureLayout> ddmStructureLayouts =
			ddmStructureLayoutPersistence.findByG_C_SV(
				ddmStructureVersion.getGroupId(), classNameId,
				ddmStructureVersion.getStructureVersionId());

		for (DDMStructureLayout ddmStructureLayout : ddmStructureLayouts) {
			deleteDDMStructureLayout(ddmStructureLayout);
		}
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public void deleteStructureLayout(DDMStructureLayout structureLayout) {
		deleteDDMStructureLayout(structureLayout);
	}

	@Override
	public void deleteStructureLayout(long structureLayoutId)
		throws PortalException {

		deleteDDMStructureLayout(structureLayoutId);
	}

	@Override
	public DDMStructureLayout fetchStructureLayout(long structureLayoutId) {
		return ddmStructureLayoutPersistence.fetchByPrimaryKey(
			structureLayoutId);
	}

	@Override
	public DDMStructureLayout fetchStructureLayout(
		long groupId, long classNameId, String structureLayoutKey) {

		return ddmStructureLayoutPersistence.fetchByG_C_S(
			groupId, classNameId, GetterUtil.getString(structureLayoutKey));
	}

	@Override
	public DDMStructureLayout getStructureLayout(long structureLayoutId)
		throws PortalException {

		return ddmStructureLayoutPersistence.findByPrimaryKey(
			structureLayoutId);
	}

	@Override
	public DDMStructureLayout getStructureLayout(
			long groupId, long classNameId, String structureLayoutKey)
		throws PortalException {

		return ddmStructureLayoutPersistence.findByG_C_S(
			groupId, classNameId, GetterUtil.getString(structureLayoutKey));
	}

	@Override
	public DDMStructureLayout getStructureLayoutByStructureVersionId(
			long structureVersionId)
		throws PortalException {

		return ddmStructureLayoutPersistence.findByStructureVersionId(
			structureVersionId);
	}

	@Override
	public DDMFormLayout getStructureLayoutDDMFormLayout(
		DDMStructureLayout structureLayout) {

		DDMFormLayoutDeserializerDeserializeRequest.Builder builder =
			DDMFormLayoutDeserializerDeserializeRequest.Builder.newBuilder(
				structureLayout.getDefinition());

		DDMFormLayoutDeserializerDeserializeResponse
			ddmFormLayoutDeserializerDeserializeResponse =
				_jsonDDMFormLayoutDeserializer.deserialize(builder.build());

		return ddmFormLayoutDeserializerDeserializeResponse.getDDMFormLayout();
	}

	@Override
	public List<DDMStructureLayout> getStructureLayouts(
			long groupId, int start, int end)
		throws PortalException {

		return ddmStructureLayoutPersistence.findByGroupId(groupId, start, end);
	}

	@Override
	public List<DDMStructureLayout> getStructureLayouts(
		long groupId, long classNameId, int start, int end,
		OrderByComparator<DDMStructureLayout> orderByComparator) {

		return ddmStructureLayoutPersistence.findByG_C(
			groupId, classNameId, start, end, orderByComparator);
	}

	@Override
	public List<DDMStructureLayout> getStructureLayouts(
		long groupId, long classNameId, long structureVersionId, int start,
		int end, OrderByComparator<DDMStructureLayout> orderByComparator) {

		return ddmStructureLayoutPersistence.findByG_C_SV(
			groupId, classNameId, structureVersionId, start, end,
			orderByComparator);
	}

	@Override
	public int getStructureLayoutsCount(long groupId) {
		return ddmStructureLayoutPersistence.countByGroupId(groupId);
	}

	@Override
	public int getStructureLayoutsCount(long groupId, long classNameId) {
		return ddmStructureLayoutPersistence.countByG_C(groupId, classNameId);
	}

	@Override
	public int getStructureLayoutsCount(
		long groupId, long classNameId, long structureVersionId) {

		return ddmStructureLayoutPersistence.countByG_C_SV(
			groupId, classNameId, structureVersionId);
	}

	public List<DDMStructureLayout> search(
			long companyId, long[] groupIds, long classNameId, String keywords,
			int start, int end,
			OrderByComparator<DDMStructureLayout> orderByComparator)
		throws PortalException {

		SearchContext searchContext =
			_ddmSearchHelper.buildStructureLayoutSearchContext(
				companyId, groupIds, classNameId, keywords, keywords,
				StringPool.BLANK, null, WorkflowConstants.STATUS_ANY, start,
				end, orderByComparator);

		return _ddmSearchHelper.doSearch(
			searchContext, DDMStructureLayout.class,
			ddmStructureLayoutPersistence::findByPrimaryKey);
	}

	public int searchCount(
			long companyId, long[] groupIds, long classNameId, String keywords)
		throws PortalException {

		SearchContext searchContext =
			_ddmSearchHelper.buildStructureLayoutSearchContext(
				companyId, groupIds, classNameId, keywords, keywords,
				StringPool.BLANK, null, WorkflowConstants.STATUS_ANY,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		return _ddmSearchHelper.doSearchCount(
			searchContext, DDMStructureLayout.class);
	}

	@Override
	public DDMStructureLayout updateStructureLayout(
			long structureLayoutId, DDMFormLayout ddmFormLayout,
			ServiceContext serviceContext)
		throws PortalException {

		DDMStructureLayout structureLayout =
			ddmStructureLayoutPersistence.findByPrimaryKey(structureLayoutId);

		validate(ddmFormLayout);

		structureLayout.setDefinition(serialize(ddmFormLayout));

		return ddmStructureLayoutPersistence.update(structureLayout);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public DDMStructureLayout updateStructureLayout(
			long structureLayoutId, long structureVersionId,
			Map<Locale, String> name, Map<Locale, String> description,
			String definition, ServiceContext serviceContext)
		throws PortalException {

		DDMStructureLayout structureLayout =
			ddmStructureLayoutPersistence.findByPrimaryKey(structureLayoutId);

		structureLayout.setModifiedDate(new Date());
		structureLayout.setStructureVersionId(structureVersionId);
		structureLayout.setNameMap(name);
		structureLayout.setDescriptionMap(description);
		structureLayout.setDefinition(definition);

		return ddmStructureLayoutPersistence.update(structureLayout);
	}

	protected String serialize(DDMFormLayout ddmFormLayout) {
		DDMFormLayoutSerializerSerializeRequest.Builder builder =
			DDMFormLayoutSerializerSerializeRequest.Builder.newBuilder(
				ddmFormLayout);

		DDMFormLayoutSerializerSerializeResponse
			ddmFormLayoutSerializerSerializeResponse =
				_jsonDDMFormLayoutSerializer.serialize(builder.build());

		return ddmFormLayoutSerializerSerializeResponse.getContent();
	}

	protected void validate(DDMFormLayout ddmFormLayout)
		throws PortalException {

		_ddmFormLayoutValidator.validate(ddmFormLayout);
	}

	@Reference
	private DDMFormLayoutValidator _ddmFormLayoutValidator;

	@Reference
	private DDMSearchHelper _ddmSearchHelper;

	@Reference(target = "(ddm.form.layout.deserializer.type=json)")
	private DDMFormLayoutDeserializer _jsonDDMFormLayoutDeserializer;

	@Reference(target = "(ddm.form.layout.serializer.type=json)")
	private DDMFormLayoutSerializer _jsonDDMFormLayoutSerializer;

}