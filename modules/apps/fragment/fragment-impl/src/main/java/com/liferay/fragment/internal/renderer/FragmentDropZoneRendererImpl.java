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

package com.liferay.fragment.internal.renderer;

import com.liferay.fragment.exception.FragmentEntryContentException;
import com.liferay.fragment.renderer.FragmentDropZoneRenderer;
import com.liferay.layout.taglib.servlet.taglib.RenderLayoutStructureTag;
import com.liferay.petra.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.servlet.PipingServletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eudaldo Alonso
 */
@Component(service = FragmentDropZoneRenderer.class)
public class FragmentDropZoneRendererImpl implements FragmentDropZoneRenderer {

	@Override
	public String renderDropZone(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String mainItemId,
			String mode, boolean showPreview)
		throws PortalException {

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		PipingServletResponse pipingServletResponse = new PipingServletResponse(
			httpServletResponse, unsyncStringWriter);

		try {
			RenderLayoutStructureTag renderLayoutStructureTag =
				new RenderLayoutStructureTag();

			renderLayoutStructureTag.setMainItemId(mainItemId);
			renderLayoutStructureTag.setMode(mode);
			renderLayoutStructureTag.setRenderActionHandler(false);
			renderLayoutStructureTag.setShowPreview(showPreview);

			renderLayoutStructureTag.doTag(
				httpServletRequest, pipingServletResponse);
		}
		catch (Exception exception) {
			throw new FragmentEntryContentException(exception);
		}

		return unsyncStringWriter.toString();
	}

}