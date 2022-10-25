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
import com.liferay.info.form.InfoForm;
import com.liferay.info.item.InfoItemDetails;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemDetailsProvider;
import com.liferay.info.list.renderer.DefaultInfoListRendererContext;
import com.liferay.info.list.renderer.InfoListRenderer;
import com.liferay.layout.constants.LayoutWebKeys;
import com.liferay.layout.display.page.LayoutDisplayPageProvider;
import com.liferay.layout.display.page.constants.LayoutDisplayPageWebKeys;
import com.liferay.layout.helper.CollectionPaginationHelper;
import com.liferay.layout.page.template.util.LayoutStructureUtil;
import com.liferay.layout.responsive.ResponsiveLayoutStructureUtil;
import com.liferay.layout.taglib.internal.display.context.RenderCollectionLayoutStructureItemDisplayContext;
import com.liferay.layout.taglib.internal.display.context.RenderLayoutStructureDisplayContext;
import com.liferay.layout.taglib.internal.info.search.InfoSearchClassMapperTrackerUtil;
import com.liferay.layout.taglib.internal.servlet.ServletContextUtil;
import com.liferay.layout.taglib.internal.util.SegmentsExperienceUtil;
import com.liferay.layout.util.constants.LayoutStructureConstants;
import com.liferay.layout.util.structure.CollectionStyledLayoutStructureItem;
import com.liferay.layout.util.structure.ColumnLayoutStructureItem;
import com.liferay.layout.util.structure.ContainerStyledLayoutStructureItem;
import com.liferay.layout.util.structure.DropZoneLayoutStructureItem;
import com.liferay.layout.util.structure.FormStyledLayoutStructureItem;
import com.liferay.layout.util.structure.FragmentStyledLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.layout.util.structure.RowStyledLayoutStructureItem;
import com.liferay.layout.util.structure.collection.EmptyCollectionOptions;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.layoutconfiguration.util.RuntimePageUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutTemplate;
import com.liferay.portal.kernel.model.LayoutTemplateConstants;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutTemplateLocalServiceUtil;
import com.liferay.portal.kernel.servlet.PipingServletResponse;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
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

		if (_layoutStructure == null) {
			_layoutStructure = _getLayoutStructure();
		}

		if (_layoutStructure != null) {
			RenderLayoutStructureDisplayContext
				renderLayoutStructureDisplayContext =
					new RenderLayoutStructureDisplayContext(
						getRequest(), getLayoutStructure(), getMainItemId(),
						getMode(), isShowPreview());

			_renderLayoutStructure(
				renderLayoutStructureDisplayContext.getMainChildrenItemIds(),
				renderLayoutStructureDisplayContext);
		}

		return SKIP_BODY;
	}

	private LayoutStructure _getLayoutStructure() {
		HttpServletRequest httpServletRequest = getRequest();

		LayoutStructure layoutStructure =
			(LayoutStructure)httpServletRequest.getAttribute(
				LayoutWebKeys.LAYOUT_STRUCTURE);

		if (layoutStructure != null) {
			return layoutStructure;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return LayoutStructureUtil.getLayoutStructure(
			themeDisplay.getPlid(),
			SegmentsExperienceUtil.getSegmentsExperienceId(httpServletRequest));
	}

	private void _renderCollectionStyledLayoutStructureItem(
			InfoForm infoForm,
			CollectionStyledLayoutStructureItem
				collectionStyledLayoutStructureItem,
			RenderLayoutStructureDisplayContext
				renderLayoutStructureDisplayContext)
		throws Exception {

		JspWriter jspWriter = pageContext.getOut();

		jspWriter.write("<div class=\"");
		jspWriter.write(
			collectionStyledLayoutStructureItem.getUniqueCssClass());
		jspWriter.write(StringPool.SPACE);
		jspWriter.write(collectionStyledLayoutStructureItem.getCssClass());
		jspWriter.write("\" style=\"");

		HttpServletRequest httpServletRequest = getRequest();

		RenderCollectionLayoutStructureItemDisplayContext
			renderCollectionLayoutStructureItemDisplayContext =
				new RenderCollectionLayoutStructureItemDisplayContext(
					collectionStyledLayoutStructureItem, httpServletRequest);

		jspWriter.write(
			renderLayoutStructureDisplayContext.getStyle(
				collectionStyledLayoutStructureItem));

		jspWriter.write("\">");

		List<String> collectionStyledLayoutStructureItemIds =
			renderLayoutStructureDisplayContext.
				getCollectionStyledLayoutStructureItemIds();

		collectionStyledLayoutStructureItemIds.add(
			collectionStyledLayoutStructureItem.getItemId());

		List<Object> collection =
			renderCollectionLayoutStructureItemDisplayContext.getCollection();

		if (ListUtil.isEmpty(collection)) {
			_renderEmptyState(
				collectionStyledLayoutStructureItem.getEmptyCollectionOptions(),
				jspWriter);

			jspWriter.write("</div>");

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
					(HttpServletResponse)pageContext.getResponse(),
					unsyncStringWriter);

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

				StringBundler containerCSSClassSB = new StringBundler(
					"overflow-hidden px-0");

				if (Objects.equals(
						collectionStyledLayoutStructureItem.getListStyle(),
						"flex-column")) {

					containerCSSClassSB.append(" d-flex flex-column");
				}
				else if (Objects.equals(
							collectionStyledLayoutStructureItem.getListStyle(),
							"flex-row")) {

					containerCSSClassSB.append(" d-flex flex-row");
				}

				String align = collectionStyledLayoutStructureItem.getAlign();

				if (Validator.isNotNull(align)) {
					containerCSSClassSB.append(StringPool.SPACE);
					containerCSSClassSB.append(align);
				}

				String flexWrap =
					collectionStyledLayoutStructureItem.getFlexWrap();

				if (Validator.isNotNull(flexWrap)) {
					containerCSSClassSB.append(StringPool.SPACE);
					containerCSSClassSB.append(flexWrap);
				}

				String justify =
					collectionStyledLayoutStructureItem.getJustify();

				if (Validator.isNotNull(justify)) {
					containerCSSClassSB.append(StringPool.SPACE);
					containerCSSClassSB.append(justify);
				}

				containerTag.setCssClass(containerCSSClassSB.toString());

				containerTag.setFluid(true);
				containerTag.setPageContext(pageContext);

				containerTag.doStartTag();

				InfoItemServiceTracker infoItemServiceTracker =
					ServletContextUtil.getInfoItemServiceTracker();

				InfoItemDetailsProvider infoItemDetailsProvider =
					infoItemServiceTracker.getFirstInfoItemService(
						InfoItemDetailsProvider.class,
						InfoSearchClassMapperTrackerUtil.getClassName(
							renderCollectionLayoutStructureItemDisplayContext.
								getCollectionItemType()));

				for (int i = 0; i < numberOfRows; i++) {
					RowTag rowTag = new RowTag();

					StringBundler rowCSSClassSB = new StringBundler(3);

					rowCSSClassSB.append("align-items-");
					rowCSSClassSB.append(
						collectionStyledLayoutStructureItem.
							getVerticalAlignment());

					if (!collectionStyledLayoutStructureItem.isGutters()) {
						rowCSSClassSB.append(" no-gutters");
					}

					rowTag.setCssClass(rowCSSClassSB.toString());

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

						InfoItemDetails infoItemDetails =
							infoItemDetailsProvider.getInfoItemDetails(
								collection.get(index));

						httpServletRequest.setAttribute(
							InfoDisplayWebKeys.INFO_ITEM_REFERENCE,
							infoItemDetails.getInfoItemReference());

						ColTag colTag = new ColTag();

						int columnSize = LayoutStructureConstants.COLUMN_SIZES
							[numberOfColumns - 1][j];

						colTag.setMd(String.valueOf(columnSize));

						colTag.setPageContext(pageContext);

						colTag.doStartTag();

						_renderLayoutStructure(
							collectionStyledLayoutStructureItem.
								getChildrenItemIds(),
							infoForm, renderLayoutStructureDisplayContext);

						colTag.doEndTag();
					}

					rowTag.doEndTag();
				}

				containerTag.doEndTag();
			}
			finally {
				httpServletRequest.removeAttribute(
					InfoDisplayWebKeys.INFO_ITEM_REFERENCE);

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
			InfoForm infoForm,
			ColumnLayoutStructureItem columnLayoutStructureItem,
			RenderLayoutStructureDisplayContext
				renderLayoutStructureDisplayContext)
		throws Exception {

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
			columnLayoutStructureItem.getChildrenItemIds(), infoForm,
			renderLayoutStructureDisplayContext);

		colTag.doEndTag();
	}

	private void _renderContainerStyledLayoutStructureItem(
			InfoForm infoForm,
			ContainerStyledLayoutStructureItem
				containerStyledLayoutStructureItem,
			RenderLayoutStructureDisplayContext
				renderLayoutStructureDisplayContext)
		throws Exception {

		JspWriter jspWriter = pageContext.getOut();

		String containerLinkHref =
			renderLayoutStructureDisplayContext.getContainerLinkHref(
				containerStyledLayoutStructureItem);

		if (Validator.isNotNull(containerLinkHref)) {
			jspWriter.write("<a href=\"");
			jspWriter.write(containerLinkHref);
			jspWriter.write("\"style=\"color: inherit; text-decoration: ");
			jspWriter.write("none;\" target=\"");
			jspWriter.write(
				renderLayoutStructureDisplayContext.getContainerLinkTarget(
					containerStyledLayoutStructureItem));
			jspWriter.write("\">");
		}

		String htmlTag = containerStyledLayoutStructureItem.getHtmlTag();

		if (Validator.isNull(htmlTag)) {
			htmlTag = "div";
		}

		jspWriter.write(StringPool.LESS_THAN);
		jspWriter.write(htmlTag);
		jspWriter.write(" class=\"");
		jspWriter.write(containerStyledLayoutStructureItem.getUniqueCssClass());
		jspWriter.write(StringPool.SPACE);
		jspWriter.write(containerStyledLayoutStructureItem.getCssClass());
		jspWriter.write(StringPool.SPACE);
		jspWriter.write(
			containerStyledLayoutStructureItem.getStyledCssClasses());

		String colorCssClasses =
			renderLayoutStructureDisplayContext.getColorCssClasses(
				containerStyledLayoutStructureItem);

		if (Validator.isNotNull(colorCssClasses)) {
			jspWriter.write(StringPool.SPACE);
			jspWriter.write(colorCssClasses);
		}

		if (Objects.equals(
				containerStyledLayoutStructureItem.getWidthType(), "fixed")) {

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
						containerStyledLayoutStructureItem.getContentDisplay(),
						"flex-row")) {

				jspWriter.write(" d-flex flex-row");
			}

			String align = containerStyledLayoutStructureItem.getAlign();

			if (Validator.isNotNull(align)) {
				jspWriter.append(StringPool.SPACE);
				jspWriter.append(align);
			}

			String flexWrap = containerStyledLayoutStructureItem.getFlexWrap();

			if (Validator.isNotNull(flexWrap)) {
				jspWriter.append(StringPool.SPACE);
				jspWriter.append(flexWrap);
			}

			String justify = containerStyledLayoutStructureItem.getJustify();

			if (Validator.isNotNull(justify)) {
				jspWriter.append(StringPool.SPACE);
				jspWriter.append(justify);
			}
		}

		jspWriter.write("\" style=\"");

		String contentVisibility =
			containerStyledLayoutStructureItem.getContentVisibility();

		if (Validator.isNotNull(contentVisibility)) {
			jspWriter.append("content-visibility:");
			jspWriter.append(contentVisibility);
			jspWriter.append(StringPool.SEMICOLON);
		}

		jspWriter.write(
			renderLayoutStructureDisplayContext.getStyle(
				containerStyledLayoutStructureItem));
		jspWriter.write("\">");

		_renderLayoutStructure(
			containerStyledLayoutStructureItem.getChildrenItemIds(), infoForm,
			renderLayoutStructureDisplayContext);

		jspWriter.write("</");
		jspWriter.write(htmlTag);
		jspWriter.write(StringPool.GREATER_THAN);

		if (Validator.isNotNull(containerLinkHref)) {
			jspWriter.write("</a>");
		}
	}

	private void _renderDropZoneLayoutStructureItem(
			InfoForm infoForm, LayoutStructureItem layoutStructureItem,
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
				layoutStructureItem.getChildrenItemIds(), infoForm,
				renderLayoutStructureDisplayContext);
		}
	}

	private void _renderEmptyState(
			EmptyCollectionOptions emptyCollectionOptions, JspWriter jspWriter)
		throws Exception {

		if ((emptyCollectionOptions != null) &&
			!GetterUtil.getBoolean(
				emptyCollectionOptions.isDisplayMessage(), true)) {

			return;
		}

		jspWriter.write("<div class=\"c-empty-state\">");
		jspWriter.write("<div class=\"c-empty-state-text\">");

		String message = LanguageUtil.get(getRequest(), "no-results-found");

		if ((emptyCollectionOptions != null) &&
			(emptyCollectionOptions.getMessage() != null)) {

			HttpServletRequest httpServletRequest = getRequest();

			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			Map<String, String> messageMap =
				emptyCollectionOptions.getMessage();

			String customMessage = messageMap.get(
				String.valueOf(themeDisplay.getLocale()));

			if (customMessage != null) {
				message = customMessage;
			}
		}

		jspWriter.write(message);

		jspWriter.write("</div></div>");
	}

	private void _renderFormStyledLayoutStructureItem(
			InfoForm infoForm,
			FormStyledLayoutStructureItem formStyledLayoutStructureItem,
			RenderLayoutStructureDisplayContext
				renderLayoutStructureDisplayContext)
		throws Exception {

		if (infoForm == null) {
			return;
		}

		JspWriter jspWriter = pageContext.getOut();

		jspWriter.write("<form action=\"");
		jspWriter.write(
			renderLayoutStructureDisplayContext.getAddInfoItemActionURL());
		jspWriter.write("\" class=\"");
		jspWriter.write(formStyledLayoutStructureItem.getUniqueCssClass());
		jspWriter.write(StringPool.SPACE);
		jspWriter.write(formStyledLayoutStructureItem.getCssClass());
		jspWriter.write(StringPool.SPACE);
		jspWriter.write(formStyledLayoutStructureItem.getStyledCssClasses());

		if (Objects.equals(
				formStyledLayoutStructureItem.getWidthType(), "fixed")) {

			jspWriter.write(" container-fluid container-fluid-max-xl");
		}

		if (!Objects.equals(
				formStyledLayoutStructureItem.getDisplay(), "none")) {

			if (Objects.equals(
					formStyledLayoutStructureItem.getContentDisplay(),
					"flex-column")) {

				jspWriter.write(" d-flex flex-column");
			}
			else if (Objects.equals(
						formStyledLayoutStructureItem.getContentDisplay(),
						"flex-row")) {

				jspWriter.write(" d-flex flex-row");
			}

			String align = formStyledLayoutStructureItem.getAlign();

			if (Validator.isNotNull(align)) {
				jspWriter.append(StringPool.SPACE);
				jspWriter.append(align);
			}

			String flexWrap = formStyledLayoutStructureItem.getFlexWrap();

			if (Validator.isNotNull(flexWrap)) {
				jspWriter.append(StringPool.SPACE);
				jspWriter.append(flexWrap);
			}

			String justify = formStyledLayoutStructureItem.getJustify();

			if (Validator.isNotNull(justify)) {
				jspWriter.append(StringPool.SPACE);
				jspWriter.append(justify);
			}
		}

		jspWriter.write(
			"\" enctype=\"multipart/form-data\" method=\"POST\" style=\"");
		jspWriter.write(
			renderLayoutStructureDisplayContext.getStyle(
				formStyledLayoutStructureItem));
		jspWriter.write("\"><input name=\"redirect\" type=\"hidden\" value=\"");
		jspWriter.write(
			renderLayoutStructureDisplayContext.
				getFormStyledLayoutStructureItemRedirect(
					formStyledLayoutStructureItem));
		jspWriter.write("\"><input name=\"backURL\" type=\"hidden\" value=\"");

		HttpServletRequest httpServletRequest = getRequest();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		jspWriter.write(themeDisplay.getURLCurrent());

		jspWriter.write(
			"\"><input name=\"classNameId\" type=\"hidden\" value=\"");
		jspWriter.write(
			String.valueOf(formStyledLayoutStructureItem.getClassNameId()));
		jspWriter.write(
			"\"><input name=\"classTypeId\" type=\"hidden\" value=\"");
		jspWriter.write(
			String.valueOf(formStyledLayoutStructureItem.getClassTypeId()));
		jspWriter.write(
			"\"><input name=\"formItemId\" type=\"hidden\" value=\"");
		jspWriter.write(formStyledLayoutStructureItem.getItemId());
		jspWriter.write("\"><input name=\"groupId\" type=\"hidden\" value=\"");
		jspWriter.write(String.valueOf(themeDisplay.getScopeGroupId()));
		jspWriter.write("\"><input name=\"p_l_mode\" type=\"hidden\" value=\"");
		jspWriter.write(
			ParamUtil.getString(
				PortalUtil.getOriginalServletRequest(httpServletRequest),
				"p_l_mode", Constants.VIEW));
		jspWriter.write("\"><input name=\"plid\" type=\"hidden\" value=\"");
		jspWriter.write(String.valueOf(themeDisplay.getPlid()));
		jspWriter.write(
			"\"><input name=\"segmentsExperienceId\" type=\"hidden\" value=\"");
		jspWriter.write(
			String.valueOf(
				SegmentsExperienceUtil.getSegmentsExperienceId(getRequest())));
		jspWriter.write("\">");

		if (SessionErrors.contains(
				getRequest(), formStyledLayoutStructureItem.getItemId())) {

			jspWriter.write("<div class=\"alert alert-danger\">");
			jspWriter.write(
				renderLayoutStructureDisplayContext.getErrorMessage(
					formStyledLayoutStructureItem, infoForm));
			jspWriter.write("</div>");

			SessionErrors.remove(
				getRequest(), formStyledLayoutStructureItem.getItemId());
		}

		Map<String, String> infoFormParameterMap =
			(Map<String, String>)SessionMessages.get(
				getRequest(),
				"infoFormParameterMap" +
					formStyledLayoutStructureItem.getItemId());

		SessionMessages.add(
			getRequest(), "infoFormParameterMap", infoFormParameterMap);

		SessionMessages.remove(
			getRequest(),
			"infoFormParameterMap" + formStyledLayoutStructureItem.getItemId());

		_renderLayoutStructure(
			formStyledLayoutStructureItem.getChildrenItemIds(), infoForm,
			renderLayoutStructureDisplayContext);

		SessionMessages.remove(getRequest(), "infoFormParameterMap");

		jspWriter.write("</form>");
	}

	private void _renderFormStyledLayoutStructureItemSuccessMessage(
			FormStyledLayoutStructureItem formStyledLayoutStructureItem,
			RenderLayoutStructureDisplayContext
				renderLayoutStructureDisplayContext)
		throws Exception {

		JspWriter jspWriter = pageContext.getOut();

		jspWriter.write("<div class=\"font-weight-semi-bold bg-white");
		jspWriter.write("text-secondary text-center text-3 p-5\">");
		jspWriter.write(
			renderLayoutStructureDisplayContext.getSuccessMessage(
				formStyledLayoutStructureItem));
		jspWriter.write("</div>");

		SessionMessages.remove(
			getRequest(), formStyledLayoutStructureItem.getItemId());
	}

	private void _renderFragmentStyledLayoutStructureItem(
			InfoForm infoForm,
			FragmentStyledLayoutStructureItem fragmentStyledLayoutStructureItem,
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

		if (fragmentStyledLayoutStructureItem.getFragmentEntryLinkId() > 0) {
			FragmentEntryLink fragmentEntryLink =
				FragmentEntryLinkLocalServiceUtil.fetchFragmentEntryLink(
					fragmentStyledLayoutStructureItem.getFragmentEntryLinkId());

			if (fragmentEntryLink != null) {
				DefaultFragmentRendererContext defaultFragmentRendererContext =
					renderLayoutStructureDisplayContext.
						getDefaultFragmentRendererContext(
							fragmentEntryLink, infoForm,
							fragmentStyledLayoutStructureItem.getItemId());

				FragmentRendererController fragmentRendererController =
					ServletContextUtil.getFragmentRendererController();

				HttpServletResponse httpServletResponse =
					(HttpServletResponse)pageContext.getResponse();

				// LPS-164462 Call render before getting attribute value

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
			List<String> childrenItemIds, InfoForm infoForm,
			RenderLayoutStructureDisplayContext
				renderLayoutStructureDisplayContext)
		throws Exception {

		for (String childrenItemId : childrenItemIds) {
			LayoutStructureItem layoutStructureItem =
				_layoutStructure.getLayoutStructureItem(childrenItemId);

			if (layoutStructureItem instanceof
					CollectionStyledLayoutStructureItem) {

				_renderCollectionStyledLayoutStructureItem(
					infoForm,
					(CollectionStyledLayoutStructureItem)layoutStructureItem,
					renderLayoutStructureDisplayContext);
			}
			else if (layoutStructureItem instanceof ColumnLayoutStructureItem) {
				_renderColumnLayoutStructureItem(
					infoForm, (ColumnLayoutStructureItem)layoutStructureItem,
					renderLayoutStructureDisplayContext);
			}
			else if (layoutStructureItem instanceof
						ContainerStyledLayoutStructureItem) {

				ContainerStyledLayoutStructureItem
					containerStyledLayoutStructureItem =
						(ContainerStyledLayoutStructureItem)layoutStructureItem;

				if (Objects.equals(
						renderLayoutStructureDisplayContext.getLayoutMode(),
						Constants.SEARCH) &&
					!containerStyledLayoutStructureItem.isIndexed()) {

					continue;
				}

				_renderContainerStyledLayoutStructureItem(
					infoForm, containerStyledLayoutStructureItem,
					renderLayoutStructureDisplayContext);
			}
			else if (layoutStructureItem instanceof
						DropZoneLayoutStructureItem) {

				_renderDropZoneLayoutStructureItem(
					infoForm, layoutStructureItem,
					renderLayoutStructureDisplayContext);
			}
			else if (layoutStructureItem instanceof
						FormStyledLayoutStructureItem) {

				FormStyledLayoutStructureItem formStyledLayoutStructureItem =
					(FormStyledLayoutStructureItem)layoutStructureItem;

				if (Objects.equals(
						renderLayoutStructureDisplayContext.getLayoutMode(),
						Constants.SEARCH) &&
					!formStyledLayoutStructureItem.isIndexed()) {

					continue;
				}

				if (SessionMessages.contains(
						getRequest(),
						formStyledLayoutStructureItem.getItemId())) {

					_renderFormStyledLayoutStructureItemSuccessMessage(
						formStyledLayoutStructureItem,
						renderLayoutStructureDisplayContext);
				}
				else {
					_renderFormStyledLayoutStructureItem(
						renderLayoutStructureDisplayContext.getInfoForm(
							formStyledLayoutStructureItem),
						formStyledLayoutStructureItem,
						renderLayoutStructureDisplayContext);
				}
			}
			else if (layoutStructureItem instanceof
						FragmentStyledLayoutStructureItem) {

				FragmentStyledLayoutStructureItem
					fragmentStyledLayoutStructureItem =
						(FragmentStyledLayoutStructureItem)layoutStructureItem;

				if (Objects.equals(
						renderLayoutStructureDisplayContext.getLayoutMode(),
						Constants.SEARCH) &&
					!fragmentStyledLayoutStructureItem.isIndexed()) {

					continue;
				}

				_renderFragmentStyledLayoutStructureItem(
					infoForm, fragmentStyledLayoutStructureItem,
					renderLayoutStructureDisplayContext);
			}
			else if (layoutStructureItem instanceof
						RowStyledLayoutStructureItem) {

				RowStyledLayoutStructureItem rowStyledLayoutStructureItem =
					(RowStyledLayoutStructureItem)layoutStructureItem;

				if (Objects.equals(
						renderLayoutStructureDisplayContext.getLayoutMode(),
						Constants.SEARCH) &&
					!rowStyledLayoutStructureItem.isIndexed()) {

					continue;
				}

				_renderRowStyledLayoutStructureItem(
					infoForm, rowStyledLayoutStructureItem,
					renderLayoutStructureDisplayContext);
			}
			else {
				_renderLayoutStructure(
					layoutStructureItem.getChildrenItemIds(), infoForm,
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

		httpServletRequest.setAttribute(
			LayoutWebKeys.LAYOUT_STRUCTURE, _layoutStructure);

		_renderLayoutStructure(
			childrenItemIds, null, renderLayoutStructureDisplayContext);
	}

	private void _renderRowStyledLayoutStructureItem(
			InfoForm infoForm,
			RowStyledLayoutStructureItem rowStyledLayoutStructureItem,
			RenderLayoutStructureDisplayContext
				renderLayoutStructureDisplayContext)
		throws Exception {

		JspWriter jspWriter = pageContext.getOut();

		jspWriter.write("<div class=\"");
		jspWriter.write(rowStyledLayoutStructureItem.getUniqueCssClass());
		jspWriter.write(StringPool.SPACE);
		jspWriter.write(rowStyledLayoutStructureItem.getCssClass());
		jspWriter.write(StringPool.SPACE);
		jspWriter.write(rowStyledLayoutStructureItem.getStyledCssClasses());
		jspWriter.write("\" style=\"");
		jspWriter.write(
			renderLayoutStructureDisplayContext.getStyle(
				rowStyledLayoutStructureItem));
		jspWriter.write("\">");

		if (renderLayoutStructureDisplayContext.isIncludeContainer(
				rowStyledLayoutStructureItem)) {

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
				rowStyledLayoutStructureItem.getChildrenItemIds(), infoForm,
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
				rowStyledLayoutStructureItem.getChildrenItemIds(), infoForm,
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

		if (!renderLayoutStructureDisplayContext.includeCommonStyles(
				fragmentEntryLink)) {

			jspWriter.write(
				fragmentStyledLayoutStructureItem.getFragmentEntryLinkCssClass(
					fragmentEntryLink));
			jspWriter.write(StringPool.SPACE);
			jspWriter.write(
				fragmentStyledLayoutStructureItem.getUniqueCssClass());
			jspWriter.write(StringPool.SPACE);
			jspWriter.write(
				fragmentStyledLayoutStructureItem.getStyledCssClasses());
		}

		String colorCssClasses =
			renderLayoutStructureDisplayContext.getColorCssClasses(
				fragmentStyledLayoutStructureItem);

		if (Validator.isNotNull(colorCssClasses)) {
			jspWriter.write(StringPool.SPACE);
			jspWriter.write(colorCssClasses);
		}

		jspWriter.write("\" style=\"");
		jspWriter.write(
			renderLayoutStructureDisplayContext.getStyle(
				fragmentStyledLayoutStructureItem));
		jspWriter.write("\">");
	}

	private static final String _PAGE = "/render_layout_structure/page.jsp";

	private LayoutStructure _layoutStructure;
	private String _mainItemId;
	private String _mode = FragmentEntryLinkConstants.VIEW;
	private boolean _showPreview;

}