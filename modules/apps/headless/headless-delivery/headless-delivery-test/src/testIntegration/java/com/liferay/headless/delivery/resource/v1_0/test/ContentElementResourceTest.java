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

package com.liferay.headless.delivery.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalServiceUtil;
import com.liferay.headless.delivery.client.dto.v1_0.ContentElement;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.odata.entity.EntityField;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class ContentElementResourceTest
	extends BaseContentElementResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_contentElementFieldValueMap = new IdentityHashMap<>();
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"title"};
	}

	@Override
	protected String getFilterString(
		EntityField entityField, String operator,
		ContentElement contentElement) {

		String entityFieldName = entityField.getName();

		if (entityFieldName.equals("priority")) {
			StringBundler sb = new StringBundler(5);

			sb.append(entityFieldName);
			sb.append(" ");
			sb.append(operator);
			sb.append(" ");
			sb.append(
				String.valueOf(
					_getValueFromMap(contentElement, entityFieldName)));

			return sb.toString();
		}

		return super.getFilterString(entityField, operator, contentElement);
	}

	@Override
	protected String[] getIgnoredEntityFieldNames() {
		return new String[] {
			"contentType", "creatorId", "dateCreated", "dateModified"
		};
	}

	@Override
	protected ContentElement randomContentElement() throws Exception {
		ContentElement contentElement = super.randomContentElement();

		_assignValueInMap(
			contentElement, "priority", RandomTestUtil.randomDouble());

		return contentElement;
	}

	@Override
	protected ContentElement
			testGetAssetLibraryContentElementsPage_addContentElement(
				Long assetLibraryId, ContentElement contentElement)
		throws Exception {

		DepotEntry depotEntry = DepotEntryLocalServiceUtil.getDepotEntry(
			assetLibraryId);

		return _addContentElement(
			contentElement,
			(Double)_getValueFromMap(contentElement, "priority"),
			depotEntry.getGroupId());
	}

	@Override
	protected ContentElement testGetSiteContentElementsPage_addContentElement(
			Long siteId, ContentElement contentElement)
		throws Exception {

		return _addContentElement(
			contentElement,
			(Double)_getValueFromMap(contentElement, "priority"), siteId);
	}

	@Override
	protected ContentElement testGraphQLContentElement_addContentElement()
		throws Exception {

		return _addContentElement(
			randomContentElement(), null, testGroup.getGroupId());
	}

	private ContentElement _addContentElement(
			ContentElement contentElement, Double priority, Long siteId)
		throws Exception {

		ContentElement element = _toContentElement(
			JournalTestUtil.addArticle(
				siteId, 0L, String.valueOf(contentElement.getId()),
				contentElement.getTitle(), contentElement.getTitle(),
				contentElement.getTitle(), priority));

		_assignValueInMap(element, "priority", priority);

		return element;
	}

	private void _assignValueInMap(
		ContentElement contentElement, String fieldName, Object fieldValue) {

		_contentElementFieldValueMap.computeIfAbsent(
			contentElement, k -> new HashMap<>()
		).put(
			fieldName, fieldValue
		);
	}

	private Object _getValueFromMap(
		ContentElement contentElement, String fieldName) {

		Map<String, Object> fieldValueMap = _contentElementFieldValueMap.get(
			contentElement);

		if (fieldValueMap == null) {
			return null;
		}

		return fieldValueMap.getOrDefault(fieldName, null);
	}

	private ContentElement _toContentElement(JournalArticle journalArticle) {
		return new ContentElement() {
			{
				id = journalArticle.getId();
				title = journalArticle.getTitle();
			}
		};
	}

	private Map<ContentElement, Map<String, Object>>
		_contentElementFieldValueMap;

}