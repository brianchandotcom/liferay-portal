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

package com.liferay.portal.kernel.lar.bundle.stagedmodeldatahandlerregistryutil;

import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.User;
import com.liferay.portlet.exportimport.lar.PortletDataContext;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandler;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Peter Fellwock
 */
@Component(
	immediate = true,
	property = {"service.ranking:Integer=" + Integer.MAX_VALUE}
)
public class TestStagedModelDataHandler
	implements StagedModelDataHandler<User> {

	public static final String[] CLASS_NAMES =
		{TestStagedModelDataHandler.class.getName()};

	@Override
	public void deleteStagedModel(
		String uuid, long groupId, String className, String extraData) {
	}

	@Override
	public void deleteStagedModel(User user) {
	}

	@Override
	public void exportStagedModel(
		PortletDataContext portletDataContext, User user) {
	}

	@Override
	public User fetchMissingReference(String uuid, long groupId) {
		return null;
	}

	@Override
	public User fetchStagedModelByUuidAndGroupId(String uuid, long groupId) {
		return null;
	}

	@Override
	public List<User> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return null;
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(User user) {
		return null;
	}

	@Override
	public int[] getExportableStatuses() {
		return null;
	}

	@Override
	public Map<String, String> getReferenceAttributes(
		PortletDataContext portletDataContext, User user) {

		return null;
	}

	/**
	 * @deprecated As of 7.0.0
	 */
	@Deprecated
	@Override
	public void importCompanyStagedModel(
		PortletDataContext portletDataContext, Element element) {
	}

	/**
	 * @deprecated As of 7.0.0
	 */
	@Deprecated
	@Override
	public void importCompanyStagedModel(
		PortletDataContext portletDataContext, String uuid, long classPK) {
	}

	@Override
	public void importMissingReference(
		PortletDataContext portletDataContext, Element referenceElement) {
	}

	@Override
	public void importMissingReference(
		PortletDataContext portletDataContext, String uuid, long groupId,
		long classPK) {
	}

	@Override
	public void importStagedModel(
		PortletDataContext portletDataContext, User user) {
	}

	@Override
	public void restoreStagedModel(
		PortletDataContext portletDataContext, User user) {
	}

	@Override
	public boolean validateReference(
		PortletDataContext portletDataContext, Element referenceElement) {

		return false;
	}

}