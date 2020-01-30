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

package com.liferay.batch.engine.internal.test;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.batch.engine.BaseBatchEngineTaskItemDelegate;
import com.liferay.batch.engine.BatchEngineTaskItemDelegate;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.blogs.service.BlogsEntryService;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.headless.common.spi.service.context.ServiceContextUtil;
import com.liferay.headless.delivery.dto.v1_0.BlogPosting;
import com.liferay.headless.delivery.dto.v1_0.Image;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.odata.entity.CollectionEntityField;
import com.liferay.portal.odata.entity.DateTimeEntityField;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.entity.IntegerEntityField;
import com.liferay.portal.odata.entity.StringEntityField;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.LocalDateTimeUtil;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.io.Serializable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Ivica Cardic
 */
public class BaseBatchEngineTaskExecutorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		group = GroupTestUtil.addGroup();

		user = UserTestUtil.addGroupAdminUser(group);

		baseDate = dateFormat.parse(dateFormat.format(new Date()));

		Bundle bundle = FrameworkUtil.getBundle(
			BatchEngineImportTaskExecutorTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		_batchEngineTaskItemDelegateRegistration =
			bundleContext.registerService(
				BatchEngineTaskItemDelegate.class.getName(),
				new TestBlogPostingBatchEngineTaskItemDelegate(),
				new HashMapDictionary<String, String>());
	}

	@After
	public void tearDown() throws Exception {
		blogsEntryLocalService.deleteEntries(group.getGroupId());

		_batchEngineTaskItemDelegateRegistration.unregister();
	}

	public class BlogPostingEntityModel implements EntityModel {

		public BlogPostingEntityModel() {
			_entityFieldsMap = EntityModel.toEntityFieldsMap(
				new CollectionEntityField(
					new StringEntityField(
						"keywords", locale -> "assetTagNames.raw")),
				new CollectionEntityField(
					new IntegerEntityField(
						"taxonomyCategoryIds", locale -> "assetCategoryIds")),
				new DateTimeEntityField(
					"dateCreated",
					locale -> Field.getSortableFieldName(Field.CREATE_DATE),
					locale -> Field.CREATE_DATE),
				new DateTimeEntityField(
					"dateModified",
					locale -> Field.getSortableFieldName(Field.MODIFIED_DATE),
					locale -> Field.MODIFIED_DATE),
				new IntegerEntityField("creatorId", locale -> Field.USER_ID),
				new StringEntityField(
					"headline",
					locale -> Field.getSortableFieldName(Field.TITLE)));
		}

		@Override
		public Map<String, EntityField> getEntityFieldsMap() {
			return _entityFieldsMap;
		}

		@Override
		public String getName() {
			return "com_liferay_batch_engine_internal_test_" +
				"BlogPostingEntityModel";
		}

		private final Map<String, EntityField> _entityFieldsMap;

	}

	public class TestBlogPostingBatchEngineTaskItemDelegate
		extends BaseBatchEngineTaskItemDelegate<BlogPosting> {

		@Override
		public void createItem(
				BlogPosting blogPosting,
				Map<String, Serializable> queryParameters, User user)
			throws Exception {

			LocalDateTime localDateTime = LocalDateTimeUtil.toLocalDateTime(
				blogPosting.getDatePublished());

			Image image = blogPosting.getImage();

			_blogsEntryService.addEntry(
				blogPosting.getHeadline(), blogPosting.getAlternativeHeadline(),
				blogPosting.getFriendlyUrlPath(), blogPosting.getDescription(),
				blogPosting.getArticleBody(), localDateTime.getMonthValue() - 1,
				localDateTime.getDayOfMonth(), localDateTime.getYear(),
				localDateTime.getHour(), localDateTime.getMinute(), true, true,
				new String[0], _getCaption(image), _getImageSelector(image),
				null,
				ServiceContextUtil.createServiceContext(
					blogPosting.getTaxonomyCategoryIds(),
					blogPosting.getKeywords(), Collections.emptyMap(),
					blogPosting.getSiteId(),
					blogPosting.getViewableByAsString()));
		}

		@Override
		public void deleteItem(
				BlogPosting blogPosting,
				Map<String, Serializable> queryParameters, User user)
			throws Exception {

			_blogsEntryService.deleteEntry(blogPosting.getId());
		}

		@Override
		public EntityModel getEntityModel(
				Map<String, List<String>> multivaluedMap)
			throws Exception {

			return new BlogPostingEntityModel();
		}

		@Override
		public Page<BlogPosting> read(
				Filter filter, Pagination pagination, Sort[] sorts,
				Map<String, Serializable> parameters, String search, User user)
			throws Exception {

			long siteId = GetterUtil.getLong(parameters.get("siteId"));

			return SearchUtil.search(
				booleanQuery -> {
				},
				filter, BlogsEntry.class, search, pagination,
				queryConfig -> queryConfig.setSelectedFieldNames(
					Field.ENTRY_CLASS_PK),
				searchContext -> {
					searchContext.setAttribute(
						Field.STATUS, WorkflowConstants.STATUS_APPROVED);
					searchContext.setCompanyId(user.getCompanyId());
					searchContext.setGroupIds(new long[] {siteId});
				},
				document -> _toBlogPosting(
					_blogsEntryService.getEntry(
						GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK))),
					user),
				sorts, Collections.emptyMap());
		}

		@Override
		public void updateItem(
				BlogPosting blogPosting, Map<String, Serializable> parameters,
				User user)
			throws Exception {

			LocalDateTime localDateTime = LocalDateTimeUtil.toLocalDateTime(
				blogPosting.getDatePublished());

			Image image = blogPosting.getImage();

			BlogsEntry blogsEntry = _blogsEntryService.getEntry(
				blogPosting.getId());

			_blogsEntryService.updateEntry(
				blogPosting.getId(), blogPosting.getHeadline(),
				blogPosting.getAlternativeHeadline(),
				blogPosting.getFriendlyUrlPath(), blogPosting.getDescription(),
				blogPosting.getArticleBody(), localDateTime.getMonthValue() - 1,
				localDateTime.getDayOfMonth(), localDateTime.getYear(),
				localDateTime.getHour(), localDateTime.getMinute(), true, true,
				new String[0], _getCaption(image), _getImageSelector(image),
				null,
				ServiceContextUtil.createServiceContext(
					blogPosting.getTaxonomyCategoryIds(),
					blogPosting.getKeywords(), Collections.emptyMap(),
					blogsEntry.getGroupId(),
					blogPosting.getViewableByAsString()));
		}

		private String _getCaption(Image image) {
			if (image == null) {
				return null;
			}

			return image.getCaption();
		}

		private ImageSelector _getImageSelector(Image image) {
			if ((image == null) || (image.getImageId() == 0)) {
				return new ImageSelector();
			}

			try {
				FileEntry fileEntry = _dlAppService.getFileEntry(
					image.getImageId());

				return new ImageSelector(
					FileUtil.getBytes(fileEntry.getContentStream()),
					fileEntry.getFileName(), fileEntry.getMimeType(),
					"{\"height\": 0, \"width\": 0, \"x\": 0, \"y\": 0}");
			}
			catch (Exception exception) {
				throw new RuntimeException(
					"Unable to get file entry " + image.getImageId(),
					exception);
			}
		}

		private BlogPosting _toBlogPosting(BlogsEntry blogsEntry, User user)
			throws Exception {

			DTOConverter<BlogsEntry, BlogPosting> blogPostingDTOConverter =
				_dtoConverterRegistry.getDTOConverter(
					BlogsEntry.class.getName());

			return blogPostingDTOConverter.toDTO(
				new DefaultDTOConverterContext(
					false, Collections.emptyMap(), _dtoConverterRegistry,
					blogsEntry.getEntryId(), user.getLocale(), null, user));
		}

	}

	protected List<BlogsEntry> addBlogsEntries() throws Exception {
		List<BlogsEntry> blogsEntries = new ArrayList<>();

		for (int i = 0; i < ROWS_COUNT; i++) {
			blogsEntries.add(
				blogsEntryLocalService.addEntry(
					user.getUserId(), "headline" + i, "alternativeHeadline" + i,
					null, "articleBody" + i, new Date(baseDate.getTime()),
					false, false, null, null, null, null,
					ServiceContextTestUtil.getServiceContext(
						user.getCompanyId(), group.getGroupId(),
						user.getUserId())));
		}

		return blogsEntries;
	}

	protected static final String[] FIELD_NAMES = {
		"alternativeHeadline", "articleBody", "datePublished", "headline",
		"siteId"
	};

	protected static final int ROWS_COUNT = 18;

	protected static final ObjectMapper objectMapper = new ObjectMapper();

	protected Date baseDate;

	@Inject
	protected BlogsEntryLocalService blogsEntryLocalService;

	protected final DateFormat dateFormat = new SimpleDateFormat(
		"yyyy-MM-dd'T'HH:mm:00.000Z");

	@DeleteAfterTestRun
	protected Group group;

	@DeleteAfterTestRun
	protected User user;

	private ServiceRegistration<?> _batchEngineTaskItemDelegateRegistration;

	@Inject
	private BlogsEntryService _blogsEntryService;

	@Inject
	private DLAppService _dlAppService;

	@Inject
	private DTOConverterRegistry _dtoConverterRegistry;

}