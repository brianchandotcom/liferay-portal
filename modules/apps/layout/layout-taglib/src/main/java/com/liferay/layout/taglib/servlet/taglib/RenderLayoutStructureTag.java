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
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.renderer.DefaultFragmentRendererContext;
import com.liferay.fragment.renderer.FragmentRendererController;
import com.liferay.fragment.service.FragmentEntryLinkLocalServiceUtil;
import com.liferay.frontend.taglib.clay.servlet.taglib.ColTag;
import com.liferay.frontend.taglib.clay.servlet.taglib.ContainerTag;
import com.liferay.frontend.taglib.clay.servlet.taglib.RowTag;
import com.liferay.info.constants.InfoDisplayWebKeys;
import com.liferay.layout.responsive.ResponsiveLayoutStructureUtil;
import com.liferay.layout.taglib.internal.display.context.RenderLayoutStructureDisplayContext;
import com.liferay.layout.taglib.internal.servlet.ServletContextUtil;
import com.liferay.layout.util.structure.CollectionStyledLayoutStructureItem;
import com.liferay.layout.util.structure.ColumnLayoutStructureItem;
import com.liferay.layout.util.structure.ContainerStyledLayoutStructureItem;
import com.liferay.layout.util.structure.DropZoneLayoutStructureItem;
import com.liferay.layout.util.structure.FragmentStyledLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.layout.util.structure.RootLayoutStructureItem;
import com.liferay.layout.util.structure.RowStyledLayoutStructureItem;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.util.IncludeTag;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;
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

	private void _renderColumnLayoutStructureItem(
			LayoutStructureItem layoutStructureItem,
			RenderLayoutStructureDisplayContext
				renderLayoutStructureDisplayContext)
		throws Exception {

		ColumnLayoutStructureItem columnLayoutStructureItem =
			(ColumnLayoutStructureItem)layoutStructureItem;

		RowStyledLayoutStructureItem rowStyledLayoutStructureItem =
			(RowStyledLayoutStructureItem)
				_layoutStructure.getLayoutStructureItem(
					columnLayoutStructureItem.getParentItemId());

		ColTag colTag = new ColTag();

		colTag.setCssClass(
			ResponsiveLayoutStructureUtil.getColumnCssClass(
				columnLayoutStructureItem, rowStyledLayoutStructureItem));
		colTag.setPageContext(pageContext);

		colTag.doStartTag();

		_renderLayoutStructure(
			layoutStructureItem.getChildrenItemIds(),
			renderLayoutStructureDisplayContext);

		colTag.doEndTag();
	}

	private void _renderContainerStyledLayoutStructureItem(
			LayoutStructureItem layoutStructureItem,
			RenderLayoutStructureDisplayContext
				renderLayoutStructureDisplayContext)
		throws Exception {

		JspWriter jspWriter = pageContext.getOut();

		ContainerStyledLayoutStructureItem containerStyledLayoutStructureItem =
			(ContainerStyledLayoutStructureItem)layoutStructureItem;

		HttpServletRequest httpServletRequest = getRequest();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		String containerLinkHref =
			renderLayoutStructureDisplayContext.getContainerLinkHref(
				containerStyledLayoutStructureItem,
				httpServletRequest.getAttribute(
					InfoDisplayWebKeys.INFO_LIST_DISPLAY_OBJECT),
				themeDisplay.getLocale());

		if (Validator.isNotNull(containerLinkHref)) {
			jspWriter.write("<a href=\"");
			jspWriter.write(containerLinkHref);
			jspWriter.write("\"style=\"color: inherit; text-decoration: ");
			jspWriter.write("none;\" target=\"");
			jspWriter.write(
				renderLayoutStructureDisplayContext.getContainerLinkTarget(
					containerStyledLayoutStructureItem,
					themeDisplay.getLocale()));
			jspWriter.write("\">");
		}

		jspWriter.write("<div class=\"");
		jspWriter.write(
			renderLayoutStructureDisplayContext.getCssClass(
				containerStyledLayoutStructureItem));
		jspWriter.write("\" style=\"");
		jspWriter.write(
			renderLayoutStructureDisplayContext.getStyle(
				containerStyledLayoutStructureItem));
		jspWriter.write("\">");

		_renderLayoutStructure(
			layoutStructureItem.getChildrenItemIds(),
			renderLayoutStructureDisplayContext);

		jspWriter.write("</div>");

		if (Validator.isNotNull(containerLinkHref)) {
			jspWriter.write("</a>");
		}
	}

	private void _renderFragmentStyledLayoutStructureItem(
			LayoutStructureItem layoutStructureItem,
			RenderLayoutStructureDisplayContext
				renderLayoutStructureDisplayContext)
		throws Exception {

		JspWriter jspWriter = pageContext.getOut();

		HttpServletRequest httpServletRequest = getRequest();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		if (Objects.equals(layout.getType(), LayoutConstants.TYPE_PORTLET)) {
			jspWriter.write("<div class=\"master-layout-fragment\">");
		}

		FragmentStyledLayoutStructureItem fragmentStyledLayoutStructureItem =
			(FragmentStyledLayoutStructureItem)layoutStructureItem;

		if (fragmentStyledLayoutStructureItem.getFragmentEntryLinkId() > 0) {
			FragmentEntryLink fragmentEntryLink =
				FragmentEntryLinkLocalServiceUtil.fetchFragmentEntryLink(
					fragmentStyledLayoutStructureItem.getFragmentEntryLinkId());

			if (fragmentEntryLink != null) {
				FragmentRendererController fragmentRendererController =
					ServletContextUtil.getFragmentRendererController();

				DefaultFragmentRendererContext defaultFragmentRendererContext =
					renderLayoutStructureDisplayContext.
						getDefaultFragmentRendererContext(
							fragmentEntryLink,
							fragmentStyledLayoutStructureItem.getItemId());

				jspWriter.write("<div class=\"");
				jspWriter.write(
					renderLayoutStructureDisplayContext.getCssClass(
						fragmentStyledLayoutStructureItem));
				jspWriter.write("\" style=\"");
				jspWriter.write(
					renderLayoutStructureDisplayContext.getStyle(
						fragmentStyledLayoutStructureItem));
				jspWriter.write("\">");

				HttpServletResponse httpServletResponse =
					(HttpServletResponse)pageContext.getResponse();

				jspWriter.write(
					fragmentRendererController.render(
						defaultFragmentRendererContext, httpServletRequest,
						httpServletResponse));

				jspWriter.write("</div>");
			}
		}

		if (Objects.equals(layout.getType(), LayoutConstants.TYPE_PORTLET)) {
			jspWriter.write("</div>");
		}
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
				_renderColumnLayoutStructureItem(
					layoutStructureItem, renderLayoutStructureDisplayContext);
			}
			else if (layoutStructureItem instanceof
						ContainerStyledLayoutStructureItem) {

				_renderContainerStyledLayoutStructureItem(
					layoutStructureItem, renderLayoutStructureDisplayContext);
			}
			else if (layoutStructureItem instanceof
						DropZoneLayoutStructureItem) {

				_renderLayoutStructure(
					layoutStructureItem.getChildrenItemIds(),
					renderLayoutStructureDisplayContext);
			}
			else if (layoutStructureItem instanceof
						FragmentStyledLayoutStructureItem) {

				_renderFragmentStyledLayoutStructureItem(
					layoutStructureItem, renderLayoutStructureDisplayContext);
			}
			else if (layoutStructureItem instanceof
						RowStyledLayoutStructureItem) {

				_renderRowStyledLayoutStructureItem(
					layoutStructureItem, renderLayoutStructureDisplayContext);
			}
			else {
				_renderLayoutStructure(
					layoutStructureItem.getChildrenItemIds(),
					renderLayoutStructureDisplayContext);
			}
		}
	}

	private void _renderRowStyledLayoutStructureItem(
			LayoutStructureItem layoutStructureItem,
			RenderLayoutStructureDisplayContext
				renderLayoutStructureDisplayContext)
		throws Exception {

		JspWriter jspWriter = pageContext.getOut();

		RowStyledLayoutStructureItem rowStyledLayoutStructureItem =
			(RowStyledLayoutStructureItem)layoutStructureItem;

		LayoutStructureItem parentLayoutStructureItem =
			_layoutStructure.getLayoutStructureItem(
				rowStyledLayoutStructureItem.getParentItemId());

		boolean includeContainer = false;

		if (parentLayoutStructureItem instanceof RootLayoutStructureItem) {
			HttpServletRequest httpServletRequest = getRequest();

			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			Layout layout = themeDisplay.getLayout();

			if (Objects.equals(
					layout.getType(), LayoutConstants.TYPE_PORTLET)) {

				includeContainer = true;
			}
			else {
				LayoutStructureItem rootParentLayoutStructureItem =
					_layoutStructure.getLayoutStructureItem(
						parentLayoutStructureItem.getParentItemId());

				if (rootParentLayoutStructureItem == null) {
					includeContainer = true;
				}
				else if (rootParentLayoutStructureItem instanceof
							DropZoneLayoutStructureItem) {

					LayoutStructureItem dropZoneParentLayoutStructureItem =
						_layoutStructure.getLayoutStructureItem(
							rootParentLayoutStructureItem.getParentItemId());

					if (dropZoneParentLayoutStructureItem instanceof
							RootLayoutStructureItem) {

						includeContainer = true;
					}
				}
			}
		}

		jspWriter.write("<div class=\"");
		jspWriter.write(
			renderLayoutStructureDisplayContext.getCssClass(
				rowStyledLayoutStructureItem));
		jspWriter.write("\" style=\"");
		jspWriter.write(
			renderLayoutStructureDisplayContext.getStyle(
				rowStyledLayoutStructureItem));
		jspWriter.write("\">");

		if (includeContainer) {
			ContainerTag containerTag = new ContainerTag();

			containerTag.setCssClass("p-0");
			containerTag.setFluid(true);
			containerTag.setPageContext(pageContext);

			containerTag.doStartTag();

			RowTag rowTag = new RowTag();

			rowTag.setCssClass(
				ResponsiveLayoutStructureUtil.getRowCssClass(
					rowStyledLayoutStructureItem));
			rowTag.setPageContext(pageContext);

			rowTag.doStartTag();

			_renderLayoutStructure(
				layoutStructureItem.getChildrenItemIds(),
				renderLayoutStructureDisplayContext);

			rowTag.doEndTag();

			containerTag.doEndTag();
		}
		else {
			RowTag rowTag = new RowTag();

			rowTag.setCssClass(
				ResponsiveLayoutStructureUtil.getRowCssClass(
					rowStyledLayoutStructureItem));
			rowTag.setPageContext(pageContext);

			rowTag.doStartTag();

			_renderLayoutStructure(
				layoutStructureItem.getChildrenItemIds(),
				renderLayoutStructureDisplayContext);

			rowTag.doEndTag();
		}

		jspWriter.write("</div>");
	}

	private static final String _PAGE = "/render_layout_structure/page.jsp";

	private Map<String, Object> _fieldValues;
	private LayoutStructure _layoutStructure;
	private String _mainItemId;
	private String _mode = FragmentEntryLinkConstants.VIEW;
	private boolean _showPreview;

}