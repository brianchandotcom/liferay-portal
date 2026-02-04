/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.resource.v1_0.layout.structure.item.importer.context;

import com.liferay.fragment.processor.FragmentEntryProcessorRegistry;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;

/**
 * @author Eudaldo Alonso
 */
public class LayoutStructureItemImporterContext {

	public LayoutStructureItemImporterContext(
		long companyId,
		FragmentEntryProcessorRegistry fragmentEntryProcessorRegistry,
		long groupId, InfoItemServiceRegistry infoItemServiceRegistry,
		Layout layout, long segmentsExperienceId, User user) {

		_companyId = companyId;
		_fragmentEntryProcessorRegistry = fragmentEntryProcessorRegistry;
		_groupId = groupId;
		_infoItemServiceRegistry = infoItemServiceRegistry;
		_layout = layout;
		_segmentsExperienceId = segmentsExperienceId;
		_user = user;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public FragmentEntryProcessorRegistry getFragmentEntryProcessorRegistry() {
		return _fragmentEntryProcessorRegistry;
	}

	public Group getGroup() throws PortalException {
		if (_group != null) {
			return _group;
		}

		_group = GroupLocalServiceUtil.getGroup(getGroupId());

		return _group;
	}

	public long getGroupId() {
		return _groupId;
	}

	public InfoItemServiceRegistry getInfoItemServiceRegistry() {
		return _infoItemServiceRegistry;
	}

	public Layout getLayout() {
		return _layout;
	}

	public long getSegmentsExperienceId() {
		return _segmentsExperienceId;
	}

	public User getUser() {
		return _user;
	}

	public long getUserId() {
		if (_user == null) {
			return 0;
		}

		return _user.getUserId();
	}

	private final long _companyId;
	private final FragmentEntryProcessorRegistry
		_fragmentEntryProcessorRegistry;
	private Group _group;
	private final long _groupId;
	private final InfoItemServiceRegistry _infoItemServiceRegistry;
	private final Layout _layout;
	private final long _segmentsExperienceId;
	private final User _user;

}