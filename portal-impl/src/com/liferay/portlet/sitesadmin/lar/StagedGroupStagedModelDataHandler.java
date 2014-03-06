/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.sitesadmin.lar;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;

import java.util.Map;

/**
 * @author Daniel Kocsis
 */
public class StagedGroupStagedModelDataHandler
	extends BaseStagedModelDataHandler<StagedGroup> {

	public static final String[] CLASS_NAMES = {StagedGroup.class.getName()};

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException, SystemException {

		throw new UnsupportedOperationException();
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(StagedGroup stagedGroup) {
		return stagedGroup.getName();
	}

	@Override
	public boolean validateReference(
		PortletDataContext portletDataContext, Element referenceElement) {

		String groupIdAttribute = referenceElement.attributeValue("group-id");

		if (Validator.isNull(groupIdAttribute)) {
			return true;
		}

		Group existingGroup = fetchExistingGroup(
			portletDataContext, referenceElement);

		if (existingGroup == null) {
			return false;
		}

		Map<Long, Long> groupIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				Group.class);

		groupIds.put(
			GetterUtil.getLong(groupIdAttribute), existingGroup.getGroupId());

		return true;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, StagedGroup stagedGroup)
		throws Exception {

		return;
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, StagedGroup stagedGroup)
		throws Exception {

		return;
	}

	protected Group fetchExistingGroup(
		PortletDataContext portletDataContext, Element referenceElement) {

		String groupIdAttribute = referenceElement.attributeValue("group-id");
		String liveGroupIdAttribute = referenceElement.attributeValue(
			"live-group-id");

		if (Validator.isNull(groupIdAttribute) ||
			Validator.isNull(liveGroupIdAttribute)) {

			return null;
		}

		long groupId = GetterUtil.getLong(groupIdAttribute);
		long liveGroupId = GetterUtil.getLong(liveGroupIdAttribute);

		return fetchExistingGroup(portletDataContext, groupId, liveGroupId);
	}

	protected Group fetchExistingGroup(
		PortletDataContext portletDataContext, long groupId, long liveGroupId) {

		long existingGroupId = liveGroupId;

		if (groupId == portletDataContext.getSourceCompanyGroupId()) {
			existingGroupId = portletDataContext.getCompanyGroupId();
		}
		else if (groupId == portletDataContext.getSourceGroupId()) {
			existingGroupId = portletDataContext.getGroupId();
		}

		// In case of remote staging finds valid mappings only when the
		// reference's group is properly staged or in case of local staging when
		// the references don't change between stage and live

		try {
			return GroupLocalServiceUtil.getGroup(existingGroupId);
		}
		catch (Exception e) {
			return null;
		}
	}

}