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
import com.liferay.fragment.constants.FragmentWebKeys;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.renderer.DefaultFragmentRendererContext;
import com.liferay.fragment.renderer.FragmentRendererController;
import com.liferay.fragment.service.FragmentEntryLinkLocalServiceUtil;
import com.liferay.frontend.taglib.clay.servlet.taglib.ButtonTag;
import com.liferay.frontend.taglib.clay.servlet.taglib.ColTag;
import com.liferay.frontend.taglib.clay.servlet.taglib.ContainerTag;
import com.liferay.frontend.taglib.clay.servlet.taglib.PaginationBarTag;
import com.liferay.frontend.taglib.clay.servlet.taglib.RowTag;
import com.liferay.frontend.taglib.servlet.taglib.ComponentTag;
import com.liferay.info.constants.InfoDisplayWebKeys;
import com.liferay.info.exception.NoSuchFormVariationException;
import com.liferay.info.form.InfoForm;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.info.list.renderer.DefaultInfoListRendererContext;
import com.liferay.info.list.renderer.InfoListRenderer;
import com.liferay.layout.display.page.LayoutDisplayPageProvider;
import com.liferay.layout.display.page.constants.LayoutDisplayPageWebKeys;
import com.liferay.layout.helper.CollectionPaginationHelper;
import com.liferay.layout.responsive.ResponsiveLayoutStructureUtil;
import com.liferay.layout.taglib.internal.display.context.RenderCollectionLayoutStructureItemDisplayContext;
import com.liferay.layout.taglib.internal.display.context.RenderLayoutStructureDisplayContext;
import com.liferay.layout.taglib.internal.servlet.ServletContextUtil;
import com.liferay.layout.util.constants.LayoutStructureConstants;
import com.liferay.layout.util.structure.CollectionStyledLayoutStructureItem;
import com.liferay.layout.util.structure.ColumnLayoutStructureItem;
import com.liferay.layout.util.structure.ContainerStyledLayoutStructureItem;
import com.liferay.layout.util.structure.DropZoneLayoutStructureItem;
import com.liferay.layout.util.structure.FormStyledLayoutStructureItem;
import com.liferay.layout.util.structure.FragmentStyledLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructureItemCSSUtil;
import com.liferay.layout.util.structure.RootLayoutStructureItem;
import com.liferay.layout.util.structure.RowStyledLayoutStructureItem;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.layoutconfiguration.util.RuntimePageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutTemplate;
import com.liferay.portal.kernel.model.LayoutTemplateConstants;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutTemplateLocalServiceUtil;
import com.liferay.portal.kernel.servlet.PipingServletResponse;
import com.liferay.portal.kernel.template.StringTemplateResource;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.taglib.util.IncludeTag;

import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

/**
 * @author Eudaldo Alonso
 */
public class RenderLayoutStructureTag extends IncludeTag {

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
					getRequest(), getLayoutStructure(), getMainItemId(),
					getMode(), isShowPreview());

		_renderLayoutStructure(
			renderLayoutStructureDisplayContext.getMainChildrenItemIds(),
			renderLayoutStructureDisplayContext);

		return SKIP_BODY;
	}

	protected static final String COLLECTION_ELEMENT_INDEX =
		RenderLayoutStructureTag.class.getName() + "#COLLECTION_ELEMENT_INDEX";

	protected static final String LAYOUT_STRUCTURE =
		RenderLayoutStructureTag.class.getName() + "#LAYOUT_STRUCTURE";

	private InfoForm _getInfoForm(
		FormStyledLayoutStructureItem formStyledLayoutStructureItem) {

		long classNameId = formStyledLayoutStructureItem.getClassNameId();

		if (classNameId <= 0) {
			return null;
		}

		InfoItemServiceTracker infoItemServiceTracker =
			ServletContextUtil.getInfoItemServiceTracker();

		InfoItemFormProvider<Object> infoItemFormProvider =
			infoItemServiceTracker.getFirstInfoItemService(
				InfoItemFormProvider.class,
				PortalUtil.getClassName(classNameId));

		if (infoItemFormProvider != null) {
			try {
				return infoItemFormProvider.getInfoForm(
					String.valueOf(
						formStyledLayoutStructureItem.getClassTypeId()));
			}
			catch (NoSuchFormVariationException noSuchFormVariationException) {
				if (_log.isDebugEnabled()) {
					_log.debug(noSuchFormVariationException);
				}

				return null;
			}
		}

		return null;
	}

	private String _getLayoutMode() {
		HttpServletRequest httpServletRequest = getRequest();

		return ParamUtil.getString(
			httpServletRequest, "p_l_mode", Constants.VIEW);
	}

	private boolean _includeCommonStyles(FragmentEntryLink fragmentEntryLink)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			fragmentEntryLink.getEditableValues());

		JSONObject stylesFragmentEntryEntryProcessorJSONObject =
			jsonObject.getJSONObject(_KEY_STYLES_FRAGMENT_ENTRY_PROCESSOR);

		if (stylesFragmentEntryEntryProcessorJSONObject == null) {
			return false;
		}

		if (stylesFragmentEntryEntryProcessorJSONObject.getBoolean(
				"hasCommonStyles")) {

			return true;
		}

		return false;
	}

	private void _renderCollectionStyledLayoutStructureItem(
			InfoForm infoForm, LayoutStructureItem layoutStructureItem,
			RenderLayoutStructureDisplayContext
				renderLayoutStructureDisplayContext)
		throws Exception {

		List<String> collectionStyledLayoutStructureItemIds =
			renderLayoutStructureDisplayContext.
				getCollectionStyledLayoutStructureItemIds();

		collectionStyledLayoutStructureItemIds.add(
			layoutStructureItem.getItemId());

		JspWriter jspWriter = pageContext.getOut();

		CollectionStyledLayoutStructureItem
			collectionStyledLayoutStructureItem =
				(CollectionStyledLayoutStructureItem)layoutStructureItem;

		HttpServletRequest httpServletRequest = getRequest();

		HttpServletResponse httpServletResponse =
			(HttpServletResponse)pageContext.getResponse();

		RenderCollectionLayoutStructureItemDisplayContext
			renderCollectionLayoutStructureItemDisplayContext =
				new RenderCollectionLayoutStructureItemDisplayContext(
					collectionStyledLayoutStructureItem, httpServletRequest,
					httpServletResponse);

		jspWriter.write("<div class=\"");

		if (renderLayoutStructureDisplayContext.isCommonStylesFFEnabled()) {
			jspWriter.write(
				LayoutStructureItemCSSUtil.getLayoutStructureItemUniqueCssClass(
					collectionStyledLayoutStructureItem));
			jspWriter.write(StringPool.SPACE);
			jspWriter.write(
				LayoutStructureItemCSSUtil.getLayoutStructureItemCssClass(
					layoutStructureItem));
		}
		else {
			jspWriter.write(
				renderLayoutStructureDisplayContext.getCssClass(
					collectionStyledLayoutStructureItem));
		}

		jspWriter.write("\" style=\"");
		jspWriter.write(
			renderLayoutStructureDisplayContext.getStyle(
				collectionStyledLayoutStructureItem));
		jspWriter.write("\">");

		List<Object> collection =
			renderCollectionLayoutStructureItemDisplayContext.getCollection();

		if (ListUtil.isEmpty(collection)) {
			_renderEmptyState(jspWriter);

			collectionStyledLayoutStructureItemIds.remove(
				collectionStyledLayoutStructureItemIds.size() - 1);

			return;
		}

		InfoListRenderer<Object> infoListRenderer =
			(InfoListRenderer<Object>)
				renderCollectionLayoutStructureItemDisplayContext.
					getInfoListRenderer();

		if (infoListRenderer != null) {
			UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

			PipingServletResponse pipingServletResponse =
				new PipingServletResponse(
					httpServletResponse, unsyncStringWriter);

			DefaultInfoListRendererContext defaultInfoListRendererContext =
				new DefaultInfoListRendererContext(
					httpServletRequest, pipingServletResponse);

			defaultInfoListRendererContext.setListItemRendererKey(
				collectionStyledLayoutStructureItem.getListItemStyle());
			defaultInfoListRendererContext.setTemplateKey(
				collectionStyledLayoutStructureItem.getTemplateKey());

			infoListRenderer.render(collection, defaultInfoListRendererContext);

			jspWriter.write(unsyncStringWriter.toString());
		}
		else {
			LayoutDisplayPageProvider<?> currentLayoutDisplayPageProvider =
				(LayoutDisplayPageProvider<?>)httpServletRequest.getAttribute(
					LayoutDisplayPageWebKeys.LAYOUT_DISPLAY_PAGE_PROVIDER);

			try {
				httpServletRequest.setAttribute(
					LayoutDisplayPageWebKeys.LAYOUT_DISPLAY_PAGE_PROVIDER,
					renderCollectionLayoutStructureItemDisplayContext.
						getCollectionLayoutDisplayPageProvider());

				int numberOfRows =
					renderCollectionLayoutStructureItemDisplayContext.
						getNumberOfRows();

				ContainerTag containerTag = new ContainerTag();

				containerTag.setCssClass("overflow-hidden px-0");
				containerTag.setFluid(true);
				containerTag.setPageContext(pageContext);

				containerTag.doStartTag();

				for (int i = 0; i < numberOfRows; i++) {
					RowTag rowTag = new RowTag();

					StringBundler cssClassSB = new StringBundler(3);

					cssClassSB.append("align-items-");
					cssClassSB.append(
						collectionStyledLayoutStructureItem.
							getVerticalAlignment());

					if (!collectionStyledLayoutStructureItem.isGutters()) {
						cssClassSB.append(" no-gutters");
					}

					rowTag.setCssClass(cssClassSB.toString());

					rowTag.setPageContext(pageContext);

					rowTag.doStartTag();

					int numberOfColumns =
						collectionStyledLayoutStructureItem.
							getNumberOfColumns();

					for (int j = 0; j < numberOfColumns; j++) {
						int index = (i * numberOfColumns) + j;

						int numberOfItemsToDisplay =
							renderCollectionLayoutStructureItemDisplayContext.
								getNumberOfItemsToDisplay();

						if ((index >= numberOfItemsToDisplay) ||
							(index >= collection.size())) {

							break;
						}

						httpServletRequest.setAttribute(
							InfoDisplayWebKeys.INFO_LIST_DISPLAY_OBJECT,
							collection.get(index));
						httpServletRequest.setAttribute(
							InfoDisplayWebKeys.
								INFO_LIST_DISPLAY_OBJECT_ITEM_TYPE,
							renderCollectionLayoutStructureItemDisplayContext.
								getCollectionItemType());

						ColTag colTag = new ColTag();

						int columnSize = LayoutStructureConstants.COLUMN_SIZES
							[numberOfColumns - 1][j];

						colTag.setMd(String.valueOf(columnSize));

						colTag.setPageContext(pageContext);

						colTag.doStartTag();

						httpServletRequest.setAttribute(
							COLLECTION_ELEMENT_INDEX, index);

						_renderLayoutStructure(
							layoutStructureItem.getChildrenItemIds(), index,
							infoForm, renderLayoutStructureDisplayContext);

						httpServletRequest.removeAttribute(
							COLLECTION_ELEMENT_INDEX);

						colTag.doEndTag();
					}

					rowTag.doEndTag();
				}

				containerTag.doEndTag();
			}
			finally {
				httpServletRequest.removeAttribute(
					InfoDisplayWebKeys.INFO_LIST_DISPLAY_OBJECT);
				httpServletRequest.removeAttribute(
					InfoDisplayWebKeys.INFO_LIST_DISPLAY_OBJECT_ITEM_TYPE);

				httpServletRequest.setAttribute(
					LayoutDisplayPageWebKeys.LAYOUT_DISPLAY_PAGE_PROVIDER,
					currentLayoutDisplayPageProvider);
			}
		}

		if (Objects.equals(
				collectionStyledLayoutStructureItem.getPaginationType(),
				CollectionPaginationHelper.PAGINATION_TYPE_NUMERIC)) {

			PaginationBarTag paginationBarTag = new PaginationBarTag();

			paginationBarTag.setActiveDelta(
				renderCollectionLayoutStructureItemDisplayContext.
					getMaxNumberOfItemsPerPage());
			paginationBarTag.setActivePage(
				renderCollectionLayoutStructureItemDisplayContext.
					getActivePage());
			paginationBarTag.setAdditionalProps(
				renderCollectionLayoutStructureItemDisplayContext.
					getNumericCollectionPaginationAdditionalProps());
			paginationBarTag.setCssClass("pb-2 pt-3");
			paginationBarTag.setPropsTransformer(
				"render_layout_structure/js" +
					"/NumericCollectionPaginationPropsTransformer");
			paginationBarTag.setShowDeltasDropDown(false);
			paginationBarTag.setTotalItems(
				renderCollectionLayoutStructureItemDisplayContext.
					getTotalNumberOfItems());

			paginationBarTag.doTag(pageContext);
		}

		if (Objects.equals(
				collectionStyledLayoutStructureItem.getPaginationType(),
				CollectionPaginationHelper.PAGINATION_TYPE_SIMPLE)) {

			jspWriter.write("<div class=\"d-flex flex-grow-1 h-100 ");
			jspWriter.write("justify-content-center py-3\" ");
			jspWriter.write("id=\"paginationButtons_");
			jspWriter.write(collectionStyledLayoutStructureItem.getItemId());
			jspWriter.write("\">");

			ButtonTag previousButtonTag = new ButtonTag();

			previousButtonTag.setCssClass(
				"font-weight-semi-bold mr-3 previous text-secondary");
			previousButtonTag.setDisplayType("unstyled");
			previousButtonTag.setDynamicAttribute(
				StringPool.BLANK, "disabled",
				Objects.equals(
					renderCollectionLayoutStructureItemDisplayContext.
						getActivePage(),
					1));
			previousButtonTag.setId(
				"paginationPreviousButton_" +
					collectionStyledLayoutStructureItem.getItemId());
			previousButtonTag.setLabel(
				LanguageUtil.get(getRequest(), "previous"));

			previousButtonTag.doTag(pageContext);

			ButtonTag nextButtonTag = new ButtonTag();

			nextButtonTag.setCssClass(
				"font-weight-semi-bold ml-3 next text-secondary");
			nextButtonTag.setDisplayType("unstyled");
			nextButtonTag.setDynamicAttribute(
				StringPool.BLANK, "disabled",
				Objects.equals(
					renderCollectionLayoutStructureItemDisplayContext.
						getActivePage(),
					renderCollectionLayoutStructureItemDisplayContext.
						getNumberOfPages()));
			nextButtonTag.setId(
				"paginationNextButton_" +
					collectionStyledLayoutStructureItem.getItemId());
			nextButtonTag.setLabel(LanguageUtil.get(getRequest(), "next"));

			nextButtonTag.doTag(pageContext);

			jspWriter.write("</div>");

			ComponentTag componentTag = new ComponentTag();

			componentTag.setComponentId(
				"paginationComponent" +
					collectionStyledLayoutStructureItem.getItemId());
			componentTag.setContext(
				renderCollectionLayoutStructureItemDisplayContext.
					getSimpleCollectionPaginationContext());
			componentTag.setModule(
				"render_layout_structure/js/SimpleCollectionPagination");

			componentTag.doTag(pageContext);
		}

		jspWriter.write("</div>");

		collectionStyledLayoutStructureItemIds.remove(
			collectionStyledLayoutStructureItemIds.size() - 1);
	}

	private void _renderColumnLayoutStructureItem(
			int collectionElementIndex, InfoForm infoForm,
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
			layoutStructureItem.getChildrenItemIds(), collectionElementIndex,
			infoForm, renderLayoutStructureDisplayContext);

		colTag.doEndTag();
	}

	private void _renderContainerStyledLayoutStructureItem(
			int collectionElementIndex, InfoForm infoForm,
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

		String htmlTag = containerStyledLayoutStructureItem.getHtmlTag();

		if (Validator.isNull(htmlTag)) {
			htmlTag = "div";
		}

		jspWriter.write(StringPool.LESS_THAN);
		jspWriter.write(htmlTag);
		jspWriter.write(" class=\"");

		if (renderLayoutStructureDisplayContext.isCommonStylesFFEnabled()) {
			jspWriter.write(
				LayoutStructureItemCSSUtil.getLayoutStructureItemUniqueCssClass(
					containerStyledLayoutStructureItem));
			jspWriter.write(StringPool.SPACE);
			jspWriter.write(
				LayoutStructureItemCSSUtil.getLayoutStructureItemCssClass(
					layoutStructureItem));

			if (Objects.equals(
					containerStyledLayoutStructureItem.getWidthType(),
					"fixed")) {

				jspWriter.write(" container-fluid container-fluid-max-xl");
			}

			if (!Objects.equals(
					containerStyledLayoutStructureItem.getDisplay(), "none")) {

				if (Objects.equals(
						containerStyledLayoutStructureItem.getContentDisplay(),
						"flex-column")) {

					jspWriter.write(" d-flex flex-column");
				}
				else if (Objects.equals(
							containerStyledLayoutStructureItem.
								getContentDisplay(),
							"flex-row")) {

					jspWriter.write(" d-flex flex-row");
				}
			}
		}
		else {
			jspWriter.write(
				renderLayoutStructureDisplayContext.getCssClass(
					containerStyledLayoutStructureItem));
		}

		jspWriter.write("\" style=\"");
		jspWriter.write(
			renderLayoutStructureDisplayContext.getStyle(
				containerStyledLayoutStructureItem));
		jspWriter.write("\">");

		_renderLayoutStructure(
			layoutStructureItem.getChildrenItemIds(), collectionElementIndex,
			infoForm, renderLayoutStructureDisplayContext);

		jspWriter.write("</");
		jspWriter.write(htmlTag);
		jspWriter.write(StringPool.GREATER_THAN);

		if (Validator.isNotNull(containerLinkHref)) {
			jspWriter.write("</a>");
		}
	}

	private void _renderDropZoneLayoutStructureItem(
			int collectionElementIndex, InfoForm infoForm,
			LayoutStructureItem layoutStructureItem,
			RenderLayoutStructureDisplayContext
				renderLayoutStructureDisplayContext)
		throws Exception {

		HttpServletRequest httpServletRequest = getRequest();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		if (Objects.equals(layout.getType(), LayoutConstants.TYPE_PORTLET)) {
			LayoutTypePortlet layoutTypePortlet = _updateLayoutTemplate(
				layout, themeDisplay.getLayoutTypePortlet(),
				themeDisplay.getThemeId());

			String layoutTemplateId = layoutTypePortlet.getLayoutTemplateId();

			if (Validator.isNull(layoutTemplateId)) {
				layoutTemplateId = PropsValues.DEFAULT_LAYOUT_TEMPLATE_ID;
			}

			LayoutTemplate layoutTemplate =
				LayoutTemplateLocalServiceUtil.getLayoutTemplate(
					layoutTemplateId, false, themeDisplay.getThemeId());

			String themeId = themeDisplay.getThemeId();

			if (layoutTemplate != null) {
				themeId = layoutTemplate.getThemeId();
			}

			String templateContent = LayoutTemplateLocalServiceUtil.getContent(
				layoutTypePortlet.getLayoutTemplateId(), false,
				themeDisplay.getThemeId());

			if (Validator.isNotNull(templateContent)) {
				HttpServletRequest originalHttpServletRequest =
					(HttpServletRequest)httpServletRequest.getAttribute(
						"ORIGINAL_HTTP_SERVLET_REQUEST");

				String templateId =
					themeId + LayoutTemplateConstants.CUSTOM_SEPARATOR +
						layoutTypePortlet.getLayoutTemplateId();

				RuntimePageUtil.processTemplate(
					originalHttpServletRequest,
					(HttpServletResponse)pageContext.getResponse(),
					new StringTemplateResource(templateId, templateContent),
					LayoutTemplateLocalServiceUtil.getLangType(
						layoutTypePortlet.getLayoutTemplateId(), false,
						themeDisplay.getThemeId()));
			}
		}
		else {
			_renderLayoutStructure(
				layoutStructureItem.getChildrenItemIds(),
				collectionElementIndex, infoForm,
				renderLayoutStructureDisplayContext);
		}
	}

	private void _renderEmptyState(JspWriter jspWriter) throws Exception {
		jspWriter.write("<div class=\"c-empty-state\">");
		jspWriter.write("<div class=\"c-empty-state-title mt-0\">");
		jspWriter.write("<span class=\"text-truncate-inline\">");
		jspWriter.write("<span class=\"text-truncate\">");
		jspWriter.write(LanguageUtil.get(getRequest(), "no-results-found"));
		jspWriter.write("</span></span></div>");
		jspWriter.write("<div class=\"c-empty-state-text\">");
		jspWriter.write(
			LanguageUtil.get(getRequest(), "sorry,-no-results-were-found"));
		jspWriter.write("</div></div></div>");
	}

	private void _renderFormStyledLayoutStructureItem(
			int collectionElementIndex, InfoForm infoForm,
			LayoutStructureItem layoutStructureItem,
			RenderLayoutStructureDisplayContext
				renderLayoutStructureDisplayContext)
		throws Exception {

		JspWriter jspWriter = pageContext.getOut();

		jspWriter.write("<form class=\"");

		if (renderLayoutStructureDisplayContext.isCommonStylesFFEnabled()) {
			jspWriter.write(
				LayoutStructureItemCSSUtil.getLayoutStructureItemUniqueCssClass(
					layoutStructureItem));
			jspWriter.write(StringPool.SPACE);
			jspWriter.write(
				LayoutStructureItemCSSUtil.getLayoutStructureItemCssClass(
					layoutStructureItem));
		}
		else {
			jspWriter.write(
				renderLayoutStructureDisplayContext.getCssClass(
					(FormStyledLayoutStructureItem)layoutStructureItem));
		}

		jspWriter.write("\" style=\"");
		jspWriter.write(
			renderLayoutStructureDisplayContext.getStyle(
				(FormStyledLayoutStructureItem)layoutStructureItem));
		jspWriter.write("\">");

		_renderLayoutStructure(
			layoutStructureItem.getChildrenItemIds(), collectionElementIndex,
			infoForm, renderLayoutStructureDisplayContext);

		jspWriter.write("</form>");
	}

	private void _renderFragmentStyledLayoutStructureItem(
			int collectionElementIndex, InfoForm infoForm,
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
				DefaultFragmentRendererContext defaultFragmentRendererContext =
					renderLayoutStructureDisplayContext.
						getDefaultFragmentRendererContext(
							collectionElementIndex, fragmentEntryLink, infoForm,
							fragmentStyledLayoutStructureItem.getItemId());

				FragmentRendererController fragmentRendererController =
					ServletContextUtil.getFragmentRendererController();

				HttpServletResponse httpServletResponse =
					(HttpServletResponse)pageContext.getResponse();

				String html = fragmentRendererController.render(
					defaultFragmentRendererContext, httpServletRequest,
					httpServletResponse);

				if (GetterUtil.getBoolean(
						httpServletRequest.getAttribute(
							FragmentWebKeys.
								ACCESS_ALLOWED_TO_FRAGMENT_ENTRY_LINK_ID +
									fragmentEntryLink.getFragmentEntryLinkId()),
						true)) {

					_write(
						fragmentEntryLink, fragmentStyledLayoutStructureItem,
						jspWriter, renderLayoutStructureDisplayContext);
				}
				else {
					jspWriter.write("<div>");
				}

				jspWriter.write(html);
				jspWriter.write("</div>");
			}
		}

		if (Objects.equals(layout.getType(), LayoutConstants.TYPE_PORTLET)) {
			jspWriter.write("</div>");
		}
	}

	private void _renderLayoutStructure(
			List<String> childrenItemIds, int collectionElementIndex,
			InfoForm infoForm,
			RenderLayoutStructureDisplayContext
				renderLayoutStructureDisplayContext)
		throws Exception {

		for (String childrenItemId : childrenItemIds) {
			LayoutStructureItem layoutStructureItem =
				_layoutStructure.getLayoutStructureItem(childrenItemId);

			if (layoutStructureItem instanceof
					CollectionStyledLayoutStructureItem) {

				_renderCollectionStyledLayoutStructureItem(
					infoForm, layoutStructureItem,
					renderLayoutStructureDisplayContext);
			}
			else if (layoutStructureItem instanceof ColumnLayoutStructureItem) {
				_renderColumnLayoutStructureItem(
					collectionElementIndex, infoForm, layoutStructureItem,
					renderLayoutStructureDisplayContext);
			}
			else if (layoutStructureItem instanceof
						ContainerStyledLayoutStructureItem) {

				ContainerStyledLayoutStructureItem
					containerStyledLayoutStructureItem =
						(ContainerStyledLayoutStructureItem)layoutStructureItem;

				if (Objects.equals(_getLayoutMode(), Constants.SEARCH) &&
					!containerStyledLayoutStructureItem.isIndexed()) {

					continue;
				}

				_renderContainerStyledLayoutStructureItem(
					collectionElementIndex, infoForm, layoutStructureItem,
					renderLayoutStructureDisplayContext);
			}
			else if (layoutStructureItem instanceof
						DropZoneLayoutStructureItem) {

				_renderDropZoneLayoutStructureItem(
					collectionElementIndex, infoForm, layoutStructureItem,
					renderLayoutStructureDisplayContext);
			}
			else if (layoutStructureItem instanceof
						FormStyledLayoutStructureItem) {

				FormStyledLayoutStructureItem formStyledLayoutStructureItem =
					(FormStyledLayoutStructureItem)layoutStructureItem;

				if (Objects.equals(_getLayoutMode(), Constants.SEARCH) &&
					!formStyledLayoutStructureItem.isIndexed()) {

					continue;
				}

				_renderFormStyledLayoutStructureItem(
					collectionElementIndex,
					_getInfoForm(formStyledLayoutStructureItem),
					layoutStructureItem, renderLayoutStructureDisplayContext);
			}
			else if (layoutStructureItem instanceof
						FragmentStyledLayoutStructureItem) {

				FragmentStyledLayoutStructureItem
					fragmentStyledLayoutStructureItem =
						(FragmentStyledLayoutStructureItem)layoutStructureItem;

				if (Objects.equals(_getLayoutMode(), Constants.SEARCH) &&
					!fragmentStyledLayoutStructureItem.isIndexed()) {

					continue;
				}

				_renderFragmentStyledLayoutStructureItem(
					collectionElementIndex, infoForm, layoutStructureItem,
					renderLayoutStructureDisplayContext);
			}
			else if (layoutStructureItem instanceof
						RowStyledLayoutStructureItem) {

				RowStyledLayoutStructureItem rowStyledLayoutStructureItem =
					(RowStyledLayoutStructureItem)layoutStructureItem;

				if (Objects.equals(_getLayoutMode(), Constants.SEARCH) &&
					!rowStyledLayoutStructureItem.isIndexed()) {

					continue;
				}

				_renderRowStyledLayoutStructureItem(
					collectionElementIndex, infoForm, layoutStructureItem,
					renderLayoutStructureDisplayContext);
			}
			else {
				_renderLayoutStructure(
					layoutStructureItem.getChildrenItemIds(),
					collectionElementIndex, infoForm,
					renderLayoutStructureDisplayContext);
			}
		}
	}

	private void _renderLayoutStructure(
			List<String> childrenItemIds,
			RenderLayoutStructureDisplayContext
				renderLayoutStructureDisplayContext)
		throws Exception {

		HttpServletRequest httpServletRequest = getRequest();

		httpServletRequest.setAttribute(LAYOUT_STRUCTURE, _layoutStructure);

		_renderLayoutStructure(
			childrenItemIds,
			GetterUtil.getInteger(
				httpServletRequest.getAttribute(COLLECTION_ELEMENT_INDEX), -1),
			null, renderLayoutStructureDisplayContext);
	}

	private void _renderRowStyledLayoutStructureItem(
			int collectionElementIndex, InfoForm infoForm,
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

		if (renderLayoutStructureDisplayContext.isCommonStylesFFEnabled()) {
			jspWriter.write(
				LayoutStructureItemCSSUtil.getLayoutStructureItemUniqueCssClass(
					rowStyledLayoutStructureItem));
			jspWriter.write(StringPool.SPACE);
			jspWriter.write(
				LayoutStructureItemCSSUtil.getLayoutStructureItemCssClass(
					layoutStructureItem));
		}
		else {
			jspWriter.write(
				renderLayoutStructureDisplayContext.getCssClass(
					rowStyledLayoutStructureItem));
		}

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
				collectionElementIndex, infoForm,
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
				collectionElementIndex, infoForm,
				renderLayoutStructureDisplayContext);

			rowTag.doEndTag();
		}

		jspWriter.write("</div>");
	}

	private LayoutTypePortlet _updateLayoutTemplate(
			Layout layout, LayoutTypePortlet layoutTypePortlet, String themeId)
		throws Exception {

		String layoutTemplateId = layoutTypePortlet.getLayoutTemplateId();

		if (Validator.isNull(layoutTemplateId)) {
			return layoutTypePortlet;
		}

		LayoutTemplate layoutTemplate =
			LayoutTemplateLocalServiceUtil.getLayoutTemplate(
				layoutTemplateId, false, themeId);

		if (layoutTemplate != null) {
			return layoutTypePortlet;
		}

		layoutTypePortlet.setLayoutTemplateId(
			layout.getUserId(), PropsValues.DEFAULT_LAYOUT_TEMPLATE_ID);

		layout = LayoutLocalServiceUtil.updateLayout(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			layout.getTypeSettings());

		return (LayoutTypePortlet)layout.getLayoutType();
	}

	private void _write(
			FragmentEntryLink fragmentEntryLink,
			FragmentStyledLayoutStructureItem fragmentStyledLayoutStructureItem,
			JspWriter jspWriter,
			RenderLayoutStructureDisplayContext
				renderLayoutStructureDisplayContext)
		throws Exception {

		jspWriter.write("<div class=\"");

		if (renderLayoutStructureDisplayContext.isCommonStylesFFEnabled()) {
			if (!_includeCommonStyles(fragmentEntryLink)) {
				jspWriter.write(
					LayoutStructureItemCSSUtil.
						getLayoutStructureItemUniqueCssClass(
							fragmentStyledLayoutStructureItem));
			}

			jspWriter.write(StringPool.SPACE);
			jspWriter.write(
				LayoutStructureItemCSSUtil.getFragmentEntryLinkCssClass(
					fragmentEntryLink));
		}
		else {
			jspWriter.write(
				renderLayoutStructureDisplayContext.getCssClass(
					fragmentStyledLayoutStructureItem));
		}

		jspWriter.write("\" style=\"");
		jspWriter.write(
			renderLayoutStructureDisplayContext.getStyle(
				fragmentStyledLayoutStructureItem));
		jspWriter.write("\">");
	}

	private static final String _KEY_STYLES_FRAGMENT_ENTRY_PROCESSOR =
		"com.liferay.fragment.entry.processor.styles." +
			"StylesFragmentEntryProcessor";

	private static final String _PAGE = "/render_layout_structure/page.jsp";

	private static final Log _log = LogFactoryUtil.getLog(
		RenderLayoutStructureTag.class);

	private LayoutStructure _layoutStructure;
	private String _mainItemId;
	private String _mode = FragmentEntryLinkConstants.VIEW;
	private boolean _showPreview;

}