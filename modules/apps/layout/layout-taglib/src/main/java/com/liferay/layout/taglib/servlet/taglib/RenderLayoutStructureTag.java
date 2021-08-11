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

package com.liferay.layout.taglib.servlet.taglib;

import com.liferay.fragment.constants.FragmentEntryLinkConstants;
import com.liferay.layout.taglib.internal.display.context.RenderLayoutStructureDisplayContext;
import com.liferay.layout.taglib.internal.servlet.ServletContextUtil;
import com.liferay.layout.util.structure.CollectionStyledLayoutStructureItem;
import com.liferay.layout.util.structure.ColumnLayoutStructureItem;
import com.liferay.layout.util.structure.ContainerStyledLayoutStructureItem;
import com.liferay.layout.util.structure.DropZoneLayoutStructureItem;
import com.liferay.layout.util.structure.FragmentStyledLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.layout.util.structure.RowStyledLayoutStructureItem;
import com.liferay.taglib.util.IncludeTag;

import java.util.List;
import java.util.Map;

import javax.servlet.jsp.PageContext;

/**
 * @author Eudaldo Alonso
 */
public class RenderLayoutStructureTag extends IncludeTag {

	public Map<String, Object> getFieldValues() {
		return _fieldValues;
	}

	public LayoutStructure getLayoutStructure() {
		return _layoutStructure;
	}

	public String getMainItemId() {
		return _mainItemId;
	}

	public String getMode() {
		return _mode;
	}

	public boolean isShowPreview() {
		return _showPreview;
	}

	public void setFieldValues(Map<String, Object> fieldValues) {
		_fieldValues = fieldValues;
	}

	public void setLayoutStructure(LayoutStructure layoutStructure) {
		_layoutStructure = layoutStructure;
	}

	public void setMainItemId(String mainItemId) {
		_mainItemId = mainItemId;
	}

	public void setMode(String mode) {
		_mode = mode;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		setServletContext(ServletContextUtil.getServletContext());
	}

	public void setShowPreview(boolean showPreview) {
		_showPreview = showPreview;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_fieldValues = null;
		_layoutStructure = null;
		_mainItemId = null;
		_mode = FragmentEntryLinkConstants.VIEW;
		_showPreview = false;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected int processStartTag() throws Exception {
		super.processStartTag();

		RenderLayoutStructureDisplayContext
			renderLayoutStructureDisplayContext =
				new RenderLayoutStructureDisplayContext(
					getFieldValues(), getRequest(), getLayoutStructure(),
					getMainItemId(), getMode(), isShowPreview());

		_renderLayoutStructure(
			renderLayoutStructureDisplayContext.getMainChildrenItemIds(),
			renderLayoutStructureDisplayContext);

		return SKIP_BODY;
	}

	private void _renderLayoutStructure(
			List<String> childrenItemIds,
			RenderLayoutStructureDisplayContext
				renderLayoutStructureDisplayContext)
		throws Exception {

		for (String childrenItemId : childrenItemIds) {
			LayoutStructureItem layoutStructureItem =
				_layoutStructure.getLayoutStructureItem(childrenItemId);

			if (layoutStructureItem instanceof
					CollectionStyledLayoutStructureItem) {

				_renderLayoutStructure(
					layoutStructureItem.getChildrenItemIds(),
					renderLayoutStructureDisplayContext);
			}
			else if (layoutStructureItem instanceof ColumnLayoutStructureItem) {
				_renderLayoutStructure(
					layoutStructureItem.getChildrenItemIds(),
					renderLayoutStructureDisplayContext);
			}
			else if (layoutStructureItem instanceof
						ContainerStyledLayoutStructureItem) {

				_renderLayoutStructure(
					layoutStructureItem.getChildrenItemIds(),
					renderLayoutStructureDisplayContext);
			}
			else if (layoutStructureItem instanceof
						DropZoneLayoutStructureItem) {

				_renderLayoutStructure(
					layoutStructureItem.getChildrenItemIds(),
					renderLayoutStructureDisplayContext);
			}
			else if (layoutStructureItem instanceof
						FragmentStyledLayoutStructureItem) {

				_renderLayoutStructure(
					layoutStructureItem.getChildrenItemIds(),
					renderLayoutStructureDisplayContext);
			}
			else if (layoutStructureItem instanceof
						RowStyledLayoutStructureItem) {

				_renderLayoutStructure(
					layoutStructureItem.getChildrenItemIds(),
					renderLayoutStructureDisplayContext);
			}
			else {
				_renderLayoutStructure(
					layoutStructureItem.getChildrenItemIds(),
					renderLayoutStructureDisplayContext);
			}
		}
	}

	private static final String _PAGE = "/render_layout_structure/page.jsp";

	private Map<String, Object> _fieldValues;
	private LayoutStructure _layoutStructure;
	private String _mainItemId;
	private String _mode = FragmentEntryLinkConstants.VIEW;
	private boolean _showPreview;

}