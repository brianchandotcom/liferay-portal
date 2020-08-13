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

package com.liferay.headless.delivery.internal.resource.v1_0;

import com.liferay.expando.kernel.service.ExpandoColumnLocalService;
import com.liferay.expando.kernel.service.ExpandoTableLocalService;
import com.liferay.headless.common.spi.service.context.ServiceContextRequestUtil;
import com.liferay.headless.delivery.dto.v1_0.MessageBoardSection;
import com.liferay.headless.delivery.internal.dto.v1_0.converter.MessageBoardSectionDTOConverter;
import com.liferay.headless.delivery.internal.dto.v1_0.util.CustomFieldsUtil;
import com.liferay.headless.delivery.internal.dto.v1_0.util.EntityFieldsUtil;
import com.liferay.headless.delivery.internal.odata.entity.v1_0.MessageBoardSectionEntityModel;
import com.liferay.headless.delivery.resource.v1_0.MessageBoardSectionResource;
import com.liferay.message.boards.model.MBCategory;
import com.liferay.message.boards.service.MBCategoryService;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.aggregation.Aggregation;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.vulcan.util.ActionUtil;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Map;

import javax.ws.rs.core.MultivaluedMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/message-board-section.properties",
	scope = ServiceScope.PROTOTYPE, service = MessageBoardSectionResource.class
)
public class MessageBoardSectionResourceImpl
	extends BaseMessageBoardSectionResourceImpl implements EntityModelResource {

	@Override
	public void deleteMessageBoardSection(Long messageBoardSectionId)
		throws Exception {

		_mbCategoryService.deleteCategory(messageBoardSectionId, true);
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap) {
		return new MessageBoardSectionEntityModel(
			new ArrayList<>(
				EntityFieldsUtil.getEntityFields(
					_portal.getClassNameId(MBCategory.class.getName()),
					contextCompany.getCompanyId(), _expandoColumnLocalService,
					_expandoTableLocalService)));
	}

	@Override
	public MessageBoardSection getMessageBoardSection(
			Long messageBoardSectionId)
		throws Exception {

		return _toMessageBoardSection(
			_mbCategoryService.getCategory(messageBoardSectionId));
	}

	@Override
	public Page<MessageBoardSection>
			getMessageBoardSectionMessageBoardSectionsPage(
				Long parentMessageBoardSectionId, String search,
				Aggregation aggregation, Filter filter, Pagination pagination,
				Sort[] sorts)
		throws Exception {

		MBCategory mbCategory = _mbCategoryService.getCategory(
			parentMessageBoardSectionId);

		return _getMessageBoardSectionsPage(
			HashMapBuilder.put(
				"create",
				addAction(
					"ADD_CATEGORY",
					"postMessageBoardSectionMessageBoardSection",
					"com.liferay.message.boards", mbCategory.getGroupId())
			).put(
				"get",
				addAction(
					"VIEW", "getMessageBoardSectionMessageBoardSectionsPage",
					"com.liferay.message.boards", mbCategory.getGroupId())
			).build(),
			booleanQuery -> {
				BooleanFilter booleanFilter =
					booleanQuery.getPreBooleanFilter();

				booleanFilter.add(
					new TermFilter(
						Field.ASSET_PARENT_CATEGORY_ID,
						String.valueOf(mbCategory.getCategoryId())),
					BooleanClauseOccur.MUST);
			},
			mbCategory.getGroupId(), search, aggregation, filter, pagination,
			sorts);
	}

	@Override
	public Page<MessageBoardSection> getSiteMessageBoardSectionsPage(
			Long siteId, Boolean flatten, String search,
			Aggregation aggregation, Filter filter, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		return _getMessageBoardSectionsPage(
			HashMapBuilder.put(
				"create",
				addAction(
					"ADD_CATEGORY", "postSiteMessageBoardSection",
					"com.liferay.message.boards", siteId)
			).put(
				"get",
				addAction(
					"VIEW", "getSiteMessageBoardSectionsPage",
					"com.liferay.message.boards", siteId)
			).build(),
			booleanQuery -> {
				if (!GetterUtil.getBoolean(flatten)) {
					BooleanFilter booleanFilter =
						booleanQuery.getPreBooleanFilter();

					booleanFilter.add(
						new TermFilter(Field.ASSET_PARENT_CATEGORY_ID, "0"),
						BooleanClauseOccur.MUST);
				}
			},
			siteId, search, aggregation, filter, pagination, sorts);
	}

	@Override
	public MessageBoardSection postMessageBoardSectionMessageBoardSection(
			Long parentMessageBoardSectionId,
			MessageBoardSection messageBoardSection)
		throws Exception {

		MBCategory mbCategory = _mbCategoryService.getCategory(
			parentMessageBoardSectionId);

		return _addMessageBoardSection(
			mbCategory.getGroupId(), parentMessageBoardSectionId,
			messageBoardSection);
	}

	@Override
	public MessageBoardSection postSiteMessageBoardSection(
			Long siteId, MessageBoardSection messageBoardSection)
		throws Exception {

		return _addMessageBoardSection(siteId, 0L, messageBoardSection);
	}

	@Override
	public MessageBoardSection putMessageBoardSection(
			Long messageBoardSectionId, MessageBoardSection messageBoardSection)
		throws Exception {

		MBCategory mbCategory = _mbCategoryService.getCategory(
			messageBoardSectionId);

		return _toMessageBoardSection(
			_mbCategoryService.updateCategory(
				messageBoardSectionId, mbCategory.getParentCategoryId(),
				messageBoardSection.getTitle(),
				messageBoardSection.getDescription(),
				mbCategory.getDisplayStyle(), "", "", "", 0, false, "", "", 0,
				"", false, "", 0, false, "", "", false, false, false,
				ServiceContextRequestUtil.createServiceContext(
					_getExpandoBridgeAttributes(messageBoardSection),
					mbCategory.getGroupId(), contextHttpServletRequest, null)));
	}

	@Override
	public void putMessageBoardSectionSubscribe(Long messageBoardSectionId)
		throws Exception {

		MBCategory mbCategory = _mbCategoryService.getCategory(
			messageBoardSectionId);

		_mbCategoryService.subscribeCategory(
			mbCategory.getGroupId(), mbCategory.getCategoryId());
	}

	@Override
	public void putMessageBoardSectionUnsubscribe(Long messageBoardSectionId)
		throws Exception {

		MBCategory mbCategory = _mbCategoryService.getCategory(
			messageBoardSectionId);

		_mbCategoryService.unsubscribeCategory(
			mbCategory.getGroupId(), mbCategory.getCategoryId());
	}

	private MessageBoardSection _addMessageBoardSection(
			long siteId, Long parentMessageBoardSectionId,
			MessageBoardSection messageBoardSection)
		throws Exception {

		return _toMessageBoardSection(
			_mbCategoryService.addCategory(
				contextUser.getUserId(), parentMessageBoardSectionId,
				messageBoardSection.getTitle(),
				messageBoardSection.getDescription(),
				ServiceContextRequestUtil.createServiceContext(
					_getExpandoBridgeAttributes(messageBoardSection), siteId,
					contextHttpServletRequest,
					messageBoardSection.getViewableByAsString())));
	}

	private Map<String, Serializable> _getExpandoBridgeAttributes(
		MessageBoardSection messageBoardSection) {

		return CustomFieldsUtil.toMap(
			MBCategory.class.getName(), contextCompany.getCompanyId(),
			messageBoardSection.getCustomFields(),
			contextAcceptLanguage.getPreferredLocale());
	}

	private Page<MessageBoardSection> _getMessageBoardSectionsPage(
			Map<String, Map<String, String>> actions,
			UnsafeConsumer<BooleanQuery, Exception> booleanQueryUnsafeConsumer,
			Long siteId, String keywords, Aggregation aggregation,
			Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception {

		return SearchUtil.search(
			actions, booleanQueryUnsafeConsumer, filter, MBCategory.class,
			keywords, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> {
				searchContext.addVulcanAggregation(aggregation);
				searchContext.setCompanyId(contextCompany.getCompanyId());
				searchContext.setGroupIds(new long[] {siteId});
			},
			sorts,
			document -> _toMessageBoardSection(
				_mbCategoryService.getCategory(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)))));
	}

	private MessageBoardSection _toMessageBoardSection(MBCategory mbCategory)
		throws Exception {

		return _messageBoardSectionDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(),
				HashMapBuilder.put(
					"add-subcategory",
					addAction(
						"ADD_SUBCATEGORY", mbCategory,
						"postMessageBoardSectionMessageBoardSection")
				).put(
					"add-thread",
					ActionUtil.addAction(
						"ADD_MESSAGE", MessageBoardThreadResourceImpl.class,
						mbCategory.getCategoryId(),
						"postMessageBoardSectionMessageBoardThread",
						contextScopeChecker, mbCategory.getUserId(),
						MBCategory.class.getName(), mbCategory.getGroupId(),
						contextUriInfo)
				).put(
					"delete",
					addAction("DELETE", mbCategory, "deleteMessageBoardSection")
				).put(
					"get",
					addAction("VIEW", mbCategory, "getMessageBoardSection")
				).put(
					"replace",
					addAction("UPDATE", mbCategory, "putMessageBoardSection")
				).put(
					"subscribe",
					addAction(
						"SUBSCRIBE", mbCategory,
						"putMessageBoardSectionSubscribe")
				).put(
					"unsubscribe",
					addAction(
						"SUBSCRIBE", mbCategory,
						"putMessageBoardSectionUnsubscribe")
				).build(),
				_dtoConverterRegistry, mbCategory.getCategoryId(),
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser));
	}

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private ExpandoColumnLocalService _expandoColumnLocalService;

	@Reference
	private ExpandoTableLocalService _expandoTableLocalService;

	@Reference
	private MBCategoryService _mbCategoryService;

	@Reference
	private MessageBoardSectionDTOConverter _messageBoardSectionDTOConverter;

	@Reference
	private Portal _portal;

}