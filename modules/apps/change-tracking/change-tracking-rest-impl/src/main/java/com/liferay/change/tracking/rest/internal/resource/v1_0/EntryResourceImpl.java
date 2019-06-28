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

package com.liferay.change.tracking.rest.internal.resource.v1_0;

import com.liferay.change.tracking.definition.CTDefinitionRegistryUtil;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.rest.dto.v1_0.Entry;
import com.liferay.change.tracking.rest.resource.v1_0.EntryResource;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.util.GetterUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Máté Thurzó
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/entry.properties",
	scope = ServiceScope.PROTOTYPE, service = EntryResource.class
)
public class EntryResourceImpl extends BaseEntryResourceImpl {

	@Override
	public Entry getEntry(Long entryId) throws Exception {
		CTEntry ctEntry = _ctEntryLocalService.getCTEntry(
			GetterUtil.getLong(entryId));

		if (ctEntry == null) {
			return new Entry();
		}

		return _toEntry(ctEntry);
	}

	private Entry _toEntry(CTEntry ctEntry) {
		return new Entry() {
			{
				affectedByEntriesCount =
					_ctEntryLocalService.getRelatedOwnerCTEntriesCount(
						ctEntry.getCtEntryId(), new QueryDefinition<>());
				changeType = ctEntry.getChangeType();
				classNameId = ctEntry.getModelClassNameId();
				classPK = ctEntry.getModelClassPK();
				collision = ctEntry.isCollision();

				contentType =
					CTDefinitionRegistryUtil.
						getVersionEntityContentTypeLanguageKey(
							ctEntry.getModelClassNameId());

				dateModified = ctEntry.getModifiedDate();
				entryId = ctEntry.getCtEntryId();
				key = ctEntry.getModelResourcePrimKey();

				siteName = CTDefinitionRegistryUtil.getVersionEntitySiteName(
					ctEntry.getModelClassNameId(), ctEntry.getModelClassPK());

				title = CTDefinitionRegistryUtil.getVersionEntityTitle(
					ctEntry.getModelClassNameId(), ctEntry.getModelClassPK());

				userName = ctEntry.getUserName();

				version = String.valueOf(
					CTDefinitionRegistryUtil.getVersionEntityVersion(
						ctEntry.getModelClassNameId(),
						ctEntry.getModelClassPK()));
			}
		};
	}

	@Reference
	private CTEntryLocalService _ctEntryLocalService;

}