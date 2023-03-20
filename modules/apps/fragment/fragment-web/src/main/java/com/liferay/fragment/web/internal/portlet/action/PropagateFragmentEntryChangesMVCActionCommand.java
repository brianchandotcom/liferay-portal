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

package com.liferay.fragment.web.internal.portlet.action;

import com.liferay.fragment.constants.FragmentPortletKeys;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseTransactionalMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(
	property = {
		"javax.portlet.name=" + FragmentPortletKeys.FRAGMENT,
		"mvc.command.name=/fragment/propagate_fragment_entry_changes"
	},
	service = MVCActionCommand.class
)
public class PropagateFragmentEntryChangesMVCActionCommand
	extends BaseTransactionalMVCActionCommand {

	@Override
	protected void doTransactionalCommand(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long[] fragmentEntryLinkIds = ParamUtil.getLongValues(
			actionRequest, "rowIds");

		for (long fragmentEntryLinkId : fragmentEntryLinkIds) {
			FragmentEntryLink fragmentEntryLink =
				_fragmentEntryLinkLocalService.fetchFragmentEntryLink(
					fragmentEntryLinkId);

			ActionableDynamicQuery actionableDynamicQuery =
				_fragmentEntryLinkLocalService.getActionableDynamicQuery();

			actionableDynamicQuery.setAddCriteriaMethod(
				dynamicQuery -> {
					Property fragmentEntryIdProperty =
						PropertyFactoryUtil.forName("fragmentEntryId");

					dynamicQuery.add(
						fragmentEntryIdProperty.eq(
							fragmentEntryLink.getFragmentEntryId()));

					Property plidProperty = PropertyFactoryUtil.forName("plid");

					dynamicQuery.add(
						plidProperty.eq(fragmentEntryLink.getPlid()));
				});
			actionableDynamicQuery.setCompanyId(
				fragmentEntryLink.getCompanyId());
			actionableDynamicQuery.setGroupId(fragmentEntryLink.getGroupId());
			actionableDynamicQuery.setPerformActionMethod(
				(FragmentEntryLink curFragmentEntryLink) ->
					_fragmentEntryLinkLocalService.updateLatestChanges(
						curFragmentEntryLink.getFragmentEntryLinkId()));

			actionableDynamicQuery.performActions();
		}
	}

	@Reference
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

}