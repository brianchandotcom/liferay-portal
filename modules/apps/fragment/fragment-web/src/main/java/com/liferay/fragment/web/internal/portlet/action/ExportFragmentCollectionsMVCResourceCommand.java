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
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.service.FragmentCollectionService;
import com.liferay.fragment.web.internal.portlet.util.ExportUtil;
import com.liferay.portal.kernel.portlet.PortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Time;

import java.io.File;
import java.io.FileInputStream;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + FragmentPortletKeys.FRAGMENT,
		"mvc.command.name=/fragment/export_fragment_collections"
	},
	service = MVCResourceCommand.class
)
public class ExportFragmentCollectionsMVCResourceCommand
	implements MVCResourceCommand {

	@Override
	public boolean serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws PortletException {

		long[] exportFragmentCollectionIds = null;

		long fragmentCollectionId = ParamUtil.getLong(
			resourceRequest, "fragmentCollectionId");

		if (fragmentCollectionId > 0) {
			exportFragmentCollectionIds = new long[] {fragmentCollectionId};
		}
		else {
			exportFragmentCollectionIds = ParamUtil.getLongValues(
				resourceRequest, "rowIds");
		}

		try {
			List<FragmentCollection> fragmentCollections = new ArrayList<>();

			for (long exportFragmentCollectionId :
					exportFragmentCollectionIds) {

				FragmentCollection fragmentCollection =
					_fragmentCollectionService.fetchFragmentCollection(
						exportFragmentCollectionId);

				fragmentCollections.add(fragmentCollection);
			}

			File file = _exportUtil.exportFragmentCollections(
				fragmentCollections);

			PortletResponseUtil.sendFile(
				resourceRequest, resourceResponse,
				"collections-" + Time.getTimestamp() + ".zip",
				new FileInputStream(file), ContentTypes.APPLICATION_ZIP);
		}
		catch (Exception e) {
			throw new PortletException(e);
		}

		return false;
	}

	@Reference
	private ExportUtil _exportUtil;

	@Reference
	private FragmentCollectionService _fragmentCollectionService;

}