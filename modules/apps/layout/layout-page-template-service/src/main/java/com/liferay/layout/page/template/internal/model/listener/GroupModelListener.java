/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.internal.model.listener;

import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.service.FragmentCollectionLocalService;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateCollectionLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ModelListener;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(service = ModelListener.class)
public class GroupModelListener extends BaseModelListener<Group> {

	@Override
	public void onBeforeRemove(Group group) throws ModelListenerException {
		try {

			// Layout page template collections

			List<LayoutPageTemplateCollection> layoutPageTemplateCollections =
				_layoutPageTemplateCollectionLocalService.
					getLayoutPageTemplateCollections(group.getGroupId());

			for (LayoutPageTemplateCollection layoutPageTemplateCollection :
					layoutPageTemplateCollections) {

				_layoutPageTemplateCollectionLocalService.
					deleteLayoutPageTemplateCollection(
						layoutPageTemplateCollection.
							getLayoutPageTemplateCollectionId());
			}

			// Layout page template entries

			List<LayoutPageTemplateEntry> layoutPageTemplateEntries =
				_layoutPageTemplateEntryLocalService.
					getLayoutPageTemplateEntries(group.getGroupId());

			for (LayoutPageTemplateEntry layoutPageTemplateEntry :
					layoutPageTemplateEntries) {

				_layoutPageTemplateEntryLocalService.
					deleteLayoutPageTemplateEntry(layoutPageTemplateEntry);
			}

			// Fragment entry links

			_fragmentEntryLinkLocalService.deleteFragmentEntryLinks(
				group.getGroupId());

			// Fragment collections

			List<FragmentCollection> fragmentCollections =
				_fragmentCollectionLocalService.getFragmentCollections(
					group.getGroupId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);

			for (FragmentCollection fragmentCollection : fragmentCollections) {
				_fragmentCollectionLocalService.deleteFragmentCollection(
					fragmentCollection);
			}
		}
		catch (Exception exception) {
			throw new ModelListenerException(exception);
		}
	}

	@Reference(unbind = "-")
	private FragmentCollectionLocalService _fragmentCollectionLocalService;

	@Reference(unbind = "-")
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Reference(unbind = "-")
	private LayoutPageTemplateCollectionLocalService
		_layoutPageTemplateCollectionLocalService;

	@Reference(unbind = "-")
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

}