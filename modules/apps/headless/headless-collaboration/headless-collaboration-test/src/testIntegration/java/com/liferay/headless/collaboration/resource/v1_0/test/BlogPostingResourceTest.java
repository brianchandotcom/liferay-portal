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

package com.liferay.headless.collaboration.resource.v1_0.test;

import static com.liferay.portal.odata.entity.EntityField.Type;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.collaboration.dto.v1_0.BlogPosting;
import com.liferay.headless.collaboration.resource.v1_0.BlogPostingResource;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.text.DateFormat;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class BlogPostingResourceTest extends BaseBlogPostingResourceTestCase {

	@Override
	@Test
	public void testGetContentSpaceBlogPostingsPageWithFilterDateTimeEquals()
		throws Exception {

		BlogPosting blogPosting = invokePostContentSpaceBlogPosting(
			testGroup.getGroupId(), randomBlogPosting());

		Thread.sleep(1000);

		invokePostContentSpaceBlogPosting(
			testGroup.getGroupId(), randomBlogPosting());

		DateFormat dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		Map<String, Function<BlogPosting, Date>> map =
			getDateTimeEntityNameGetterMap();

		for (EntityField entityField : _getEntityFields(Type.DATE_TIME)) {
			String entityFieldName = entityField.getName();

			Function<BlogPosting, Date> function = map.get(entityFieldName);

			Date date = function.apply(blogPosting);

			Page<BlogPosting> page = invokeGetContentSpaceBlogPostingsPage(
				testGroup.getGroupId(),
				entityFieldName + " eq " + dateFormat.format(date),
				Pagination.of(2, 1), null);

			assertEquals(
				Collections.singletonList(blogPosting),
				(List<BlogPosting>)page.getItems());
		}
	}

	@Override
	@Test
	public void testGetContentSpaceBlogPostingsPageWithFilterStringEquals()
		throws Exception {

		BlogPosting blogPosting = randomBlogPosting();

		invokePostContentSpaceBlogPosting(testGroup.getGroupId(), blogPosting);

		invokePostContentSpaceBlogPosting(
			testGroup.getGroupId(), randomBlogPosting());

		for (EntityField entityField : _getEntityFields(Type.STRING)) {
			StringBundler sb = new StringBundler(4);

			String entityFieldName = entityField.getName();

			sb.append(entityFieldName);

			sb.append(" eq '");

			Map<String, Function<BlogPosting, String>> map =
				getStringEntityNameGetterMap();

			Function<BlogPosting, String> function = map.get(
				entityField.getName());

			sb.append(function.apply(blogPosting));

			sb.append("'");

			Page<BlogPosting> page = invokeGetContentSpaceBlogPostingsPage(
				testGroup.getGroupId(), sb.toString(), Pagination.of(2, 1),
				null);

			assertEquals(
				Collections.singletonList(blogPosting),
				(List<BlogPosting>)page.getItems());
		}
	}

	@Override
	@Test
	public void testPostContentSpaceBlogPosting() throws Exception {
		BlogPosting blogPosting = randomBlogPosting();

		BlogPosting postBlogPosting = invokePostContentSpaceBlogPosting(
			testGroup.getGroupId(), blogPosting);

		assertValid(postBlogPosting);
	}

	@Override
	protected boolean equals(
		BlogPosting blogPosting1, BlogPosting blogPosting2) {

		if (Objects.equals(
				blogPosting1.getArticleBody(), blogPosting2.getArticleBody()) &&
			Objects.equals(
				blogPosting1.getDescription(), blogPosting2.getDescription()) &&
			Objects.equals(
				blogPosting1.getHeadline(), blogPosting2.getHeadline())) {

			return true;
		}

		return false;
	}

	@Override
	protected Map<String, Function<BlogPosting, Date>>
		getDateTimeEntityNameGetterMap() {

		return new HashMap<String, Function<BlogPosting, Date>>() {
			{
				put("dateCreated", BlogPosting::getDateCreated);
				put("dateModified", BlogPosting::getDateModified);
			}
		};
	}

	@Override
	protected Map<String, Function<BlogPosting, String>>
		getStringEntityNameGetterMap() {

		return new HashMap<String, Function<BlogPosting, String>>() {
			{
				put("headline", BlogPosting::getHeadline);
			}
		};
	}

	protected BlogPosting randomBlogPosting() {
		return new BlogPosting() {
			{
				articleBody = RandomTestUtil.randomString();
				description = RandomTestUtil.randomString();
				headline = RandomTestUtil.randomString();
			}
		};
	}

	private Collection<EntityField> _getEntityFields() throws Exception {
		EntityModel entityModel =
			((EntityModelResource)_blogPostingResource).getEntityModel(null);

		Map<String, EntityField> entityFieldsMap =
			entityModel.getEntityFieldsMap();

		return entityFieldsMap.values();
	}

	private List<EntityField> _getEntityFields(EntityField.Type type)
		throws Exception {

		Collection<EntityField> entityFields = _getEntityFields();

		Stream<EntityField> stream = entityFields.stream();

		return stream.filter(
			entityField -> Objects.equals(entityField.getType(), type)
		).collect(
			Collectors.toList()
		);
	}

	@Inject
	private BlogPostingResource _blogPostingResource;

}