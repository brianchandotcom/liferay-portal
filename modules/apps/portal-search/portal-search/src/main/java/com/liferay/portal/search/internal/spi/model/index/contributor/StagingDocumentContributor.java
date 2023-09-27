/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.spi.model.index.contributor;

import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentContributor;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	property = "service.ranking:Integer=-10000",
	service = DocumentContributor.class
)
public class StagingDocumentContributor implements DocumentContributor<Object> {

	@Override
	public void contribute(Document document, BaseModel<Object> baseModel) {
		String className = document.get(Field.ENTRY_CLASS_NAME);

		if (Validator.isNull(className)) {
			return;
		}

		Indexer<?> indexer = indexerRegistry.getIndexer(className);

		if (!indexer.isStagingAware()) {
			return;
		}

		Map<String, Field> fields = document.getFields();

		Field groupIdField = fields.get(Field.GROUP_ID);

		if (groupIdField == null) {
			return;
		}

		long groupId = GetterUtil.getLong(groupIdField.getValue());

		document.addKeyword(Field.STAGING_GROUP, _isStagingGroup(groupId));
	}

	@Reference
	protected GroupLocalService groupLocalService;

	@Reference
	protected IndexerRegistry indexerRegistry;

	private boolean _isStagingGroup(long groupId) {
		Group group = GroupUtil.fetchSiteGroup(groupLocalService, groupId);

		if (group == null) {
			return false;
		}

		return group.isStagingGroup();
	}

}