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

import com.liferay.fragment.constants.FragmentActionKeys;
import com.liferay.fragment.constants.FragmentPortletKeys;
import com.liferay.fragment.contributor.FragmentCollectionContributorRegistry;
import com.liferay.fragment.renderer.FragmentRendererController;
import com.liferay.fragment.web.internal.constants.FragmentWebKeys;
import com.liferay.petra.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.servlet.PipingServletResponse;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.Portal;

import java.io.IOException;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pablo Molina
 */
@Component(
	property = {
		"javax.portlet.name=" + FragmentPortletKeys.FRAGMENT,
		"mvc.command.name=/fragment/render_fragment_entry"
	},
	service = MVCResourceCommand.class
)
public class RenderFragmentEntryMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		resourceRequest.setAttribute(
			FragmentWebKeys.FRAGMENT_COLLECTION_CONTRIBUTOR_TRACKER,
			_fragmentCollectionContributorRegistry);

		resourceRequest.setAttribute(
			FragmentActionKeys.FRAGMENT_RENDERER_CONTROLLER,
			_fragmentRendererController);

		try {
			RequestDispatcher requestDispatcher =
				_servletContext.getRequestDispatcher(
					"/render_fragment_entry.jsp");

			HttpServletRequest httpServletRequest =
				_portal.getHttpServletRequest(resourceRequest);

			HttpServletResponse httpServletResponse =
				_portal.getHttpServletResponse(resourceResponse);

			UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

			PipingServletResponse pipingServletResponse =
				new PipingServletResponse(
					httpServletResponse, unsyncStringWriter);

			requestDispatcher.include(
				httpServletRequest, pipingServletResponse);

			ServletResponseUtil.write(
				httpServletResponse, unsyncStringWriter.toString());
		}
		catch (IOException ioException) {
			_log.error(ioException);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RenderFragmentEntryMVCResourceCommand.class);

	@Reference
	private FragmentCollectionContributorRegistry
		_fragmentCollectionContributorRegistry;

	@Reference
	private FragmentRendererController _fragmentRendererController;

	@Reference
	private Portal _portal;

	@Reference(target = "(osgi.web.symbolicname=com.liferay.fragment.web)")
	private ServletContext _servletContext;

}