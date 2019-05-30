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
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutDeserializerTracker;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutSerializerSerializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutSerializerSerializeResponse;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutSerializerTracker;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructureLayout;
import com.liferay.dynamic.data.mapping.service.base.DDMStructureLayoutLocalServiceBaseImpl;
import com.liferay.dynamic.data.mapping.validator.DDMFormLayoutValidator;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Marcellus Tavares
 */
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
		structureLayout.setStructureVersionId(structureVersionId);
		structureLayout.setDefinition(serialize(ddmFormLayout));

		return ddmStructureLayoutPersistence.update(structureLayout);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public DDMStructureLayout addStructureLayout(
			long userId, long groupId, long structureVersionId,
			long classNameId, Map<Locale, String> name,
			Map<Locale, String> description, String definition,
			String structureLayoutKey, ServiceContext serviceContext)
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
		structureLayout.setStructureVersionId(structureVersionId);
		structureLayout.setNameMap(name);
		structureLayout.setDescriptionMap(description);
		structureLayout.setDefinition(definition);

		if (Validator.isNull(structureLayoutKey)) {
			structureLayoutKey = String.valueOf(
				counterLocalService.increment());
		}
		else {
			structureLayoutKey = StringUtil.toUpperCase(
				structureLayoutKey.trim());
		}

		structureLayout.setStructureLayoutKey(structureLayoutKey);

		return ddmStructureLayoutPersistence.update(structureLayout);
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public void deleteStructureLayout(DDMStructureLayout structureLayout) {
		ddmStructureLayoutPersistence.remove(structureLayout);
	}

	@Override
	public void deleteStructureLayout(long structureLayoutId)
		throws PortalException {

		DDMStructureLayout structureLayout =
			ddmStructureLayoutPersistence.findByPrimaryKey(structureLayoutId);

		ddmStructureLayoutLocalService.deleteStructureLayout(structureLayout);
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
			groupId, classNameId, getStructureLayoutKey(structureLayoutKey));
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

		DDMFormLayoutDeserializer ddmFormLayoutDeserializer =
			ddmFormLayoutDeserializerTracker.getDDMFormLayoutDeserializer(
				"json");

		DDMFormLayoutDeserializerDeserializeRequest.Builder builder =
			DDMFormLayoutDeserializerDeserializeRequest.Builder.newBuilder(
				structureLayout.getDefinition());

		DDMFormLayoutDeserializerDeserializeResponse
			ddmFormLayoutDeserializerDeserializeResponse =
				ddmFormLayoutDeserializer.deserialize(builder.build());

		return ddmFormLayoutDeserializerDeserializeResponse.getDDMFormLayout();
	}

	@Override
	public List<DDMStructureLayout> getStructureLayouts(
			long groupId, int start, int end)
		throws PortalException {

		return ddmStructureLayoutPersistence.findByGroupId(groupId, start, end);
	}

	@Override
	public int getStructureLayoutsCount(long groupId) {
		return ddmStructureLayoutPersistence.countByGroupId(groupId);
	}

	public List<DDMStructureLayout> search(
			long companyId, long[] groupIds, long classNameId, String keywords,
			int start, int end,
			OrderByComparator<DDMStructureLayout> orderByComparator)
		throws PortalException {

		SearchContext searchContext =
			ddmSearchHelper.buildStructureLayoutSearchContext(
				companyId, groupIds, classNameId, keywords, keywords,
				StringPool.BLANK, null, WorkflowConstants.STATUS_ANY, start,
				end, orderByComparator);

		return ddmSearchHelper.doSearch(
			searchContext, DDMStructureLayout.class,
			ddmStructureLayoutPersistence::findByPrimaryKey);
	}

	public int searchCount(
			long companyId, long[] groupIds, long classNameId, String keywords)
		throws PortalException {

		SearchContext searchContext =
			ddmSearchHelper.buildStructureLayoutSearchContext(
				companyId, groupIds, classNameId, keywords, keywords,
				StringPool.BLANK, null, WorkflowConstants.STATUS_ANY,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		return ddmSearchHelper.doSearchCount(
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

	protected String getStructureLayoutKey(String structureLayoutKey) {
		if (structureLayoutKey != null) {
			return StringUtil.toUpperCase(structureLayoutKey.trim());
		}

		return StringPool.BLANK;
	}

	protected String serialize(DDMFormLayout ddmFormLayout) {
		DDMFormLayoutSerializer ddmFormLayoutSerializer =
			ddmFormLayoutSerializerTracker.getDDMFormLayoutSerializer("json");

		DDMFormLayoutSerializerSerializeRequest.Builder builder =
			DDMFormLayoutSerializerSerializeRequest.Builder.newBuilder(
				ddmFormLayout);

		DDMFormLayoutSerializerSerializeResponse
			ddmFormLayoutSerializerSerializeResponse =
				ddmFormLayoutSerializer.serialize(builder.build());

		return ddmFormLayoutSerializerSerializeResponse.getContent();
	}

	protected void validate(DDMFormLayout ddmFormLayout)
		throws PortalException {

		ddmFormLayoutValidator.validate(ddmFormLayout);
	}

	@ServiceReference(type = DDMFormLayoutDeserializerTracker.class)
	protected DDMFormLayoutDeserializerTracker ddmFormLayoutDeserializerTracker;

	@ServiceReference(type = DDMFormLayoutSerializerTracker.class)
	protected DDMFormLayoutSerializerTracker ddmFormLayoutSerializerTracker;

	@ServiceReference(type = DDMFormLayoutValidator.class)
	protected DDMFormLayoutValidator ddmFormLayoutValidator;

	@ServiceReference(type = DDMSearchHelper.class)
	protected DDMSearchHelper ddmSearchHelper;

}