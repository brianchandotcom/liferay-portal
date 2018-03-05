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

package com.liferay.layout.taglib.servlet.taglib.soy;

import com.liferay.frontend.taglib.soy.servlet.taglib.TemplateRendererTag;
import com.liferay.portal.kernel.servlet.taglib.util.OutputData;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Map;

import javax.servlet.ServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class SelectLayoutTag extends TemplateRendererTag {

	@Override
	public int doStartTag() {
		Map<String, Object> context = getContext();

		if (context.get("followURLOnTitleClick") == null) {
			putValue("followURLOnTitleClick", false);
		}

		if (context.get("itemSelectorSaveEvent") == null) {
			putValue(
				"itemSelectorSaveEvent",
				context.get("namespace") + "selectLayout");
		}

		if (context.get("multiSelection") == null) {
			putValue("multiSelection", false);
		}

		if (context.get("viewType") == null) {
			putValue("viewType", "tree");
		}

		setTemplateNamespace("SelectLayout.render");

		_outputStylesheetLink();

		return super.doStartTag();
	}

	@Override
	public String getModule() {
		return "layout-taglib/select_layout/js/SelectLayout.es";
	}

	public void setFollowURLOnTitleClick(boolean followURLOnTitleClick) {
		putValue("followURLOnTitleClick", followURLOnTitleClick);
	}

	public void setItemSelectorSaveEvent(String itemSelectorSaveEvent) {
		putValue("itemSelectorSaveEvent", itemSelectorSaveEvent);
	}

	public void setMultiSelection(boolean multiSelection) {
		putValue("multiSelection", multiSelection);
	}

	public void setNamespace(String namespace) {
		putValue("namespace", namespace);
	}

	public void setNodes(Object nodes) {
		putValue("nodes", nodes);
	}

	public void setPathThemeImages(String pathThemeImages) {
		putValue("pathThemeImages", pathThemeImages);
	}

	public void setViewType(String viewType) {
		putValue("viewType", viewType);
	}

	private OutputData _getOutputData() {
		ServletRequest servletRequest = getRequest();

		OutputData outputData = (OutputData)servletRequest.getAttribute(
			WebKeys.OUTPUT_DATA);

		if (outputData == null) {
			outputData = new OutputData();

			servletRequest.setAttribute(WebKeys.OUTPUT_DATA, outputData);
		}

		return outputData;
	}

	private void _outputStylesheetLink() {
		OutputData outputData = _getOutputData();

		StringBundler sb = new StringBundler(4);

		sb.append("<link data-senna-track=\"temporary\" href=\"");
		sb.append(PortalUtil.getPathModule());
		sb.append("/layout-taglib/select_layout/css/main.css");
		sb.append("\" rel=\"stylesheet\">");

		outputData.setData(
			SelectLayoutTag.class.getName() + "_CSS", WebKeys.PAGE_TOP, sb);
	}

}