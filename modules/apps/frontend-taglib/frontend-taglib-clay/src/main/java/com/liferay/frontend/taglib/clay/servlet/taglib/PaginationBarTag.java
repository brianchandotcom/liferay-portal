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

package com.liferay.frontend.taglib.clay.servlet.taglib;

import com.liferay.frontend.taglib.clay.internal.servlet.taglib.BaseContainerTag;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.PaginationBarDelta;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.PaginationBarLabels;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.taglib.util.TagResourceBundleUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

/**
 * @author Julien Castelain
 */
public class PaginationBarTag extends BaseContainerTag {

	@Override
	public int doStartTag() throws JspException {
		setAttributeNamespace(_ATTRIBUTE_NAMESPACE);

		if (getPaginationBarDeltas() == null) {
			setPaginationBarDeltas(
				Arrays.asList(
					new PaginationBarDelta(10), new PaginationBarDelta(20),
					new PaginationBarDelta(30), new PaginationBarDelta(50)));
		}

		if (_activeDelta == null) {
			PaginationBarDelta paginationBarDelta =
				getPaginationBarDeltas().get(0);

			Integer label = (Integer)paginationBarDelta.get("label");

			_activeDelta = label;
		}

		if (_activePage == null) {
			_activePage = 1;
		}

		if (_ellipsisBuffer == null) {
			_ellipsisBuffer = 2;
		}

		if (_paginationBarLabels == null) {
			PaginationBarLabels paginationBarLabels = new PaginationBarLabels();

			ResourceBundle resourceBundle =
				TagResourceBundleUtil.getResourceBundle(pageContext);

			paginationBarLabels.setPaginationResults(
				StringBundler.concat(
					LanguageUtil.get(resourceBundle, "showing"), " {0} ",
					StringUtil.toLowerCase(
						LanguageUtil.get(resourceBundle, "to")),
					" {1} ", LanguageUtil.get(resourceBundle, "of"), " {2}"));

			String perPageItems =
				"{0} " + LanguageUtil.get(resourceBundle, "items");

			paginationBarLabels.setPerPageItems(perPageItems);
			paginationBarLabels.setSelectPerPageItems(perPageItems);

			setPaginationBarLabels(paginationBarLabels);
		}

		return super.doStartTag();
	}

	public Integer getActiveDelta() {
		return _activeDelta;
	}

	public Integer getActivePage() {
		return _activePage;
	}

	public List<Integer> getDisabledPages() {
		return _disabledPages;
	}

	public Integer getEllipsisBuffer() {
		return _ellipsisBuffer;
	}

	public List<PaginationBarDelta> getPaginationBarDeltas() {
		return _paginationBarDeltas;
	}

	public PaginationBarLabels getPaginationBarLabels() {
		return _paginationBarLabels;
	}

	public Integer getTotalItems() {
		return _totalItems;
	}

	public boolean isShowDeltasDropDown() {
		return _showDeltasDropDown;
	}

	public void setActiveDelta(Integer activeDelta) {
		_activeDelta = activeDelta;
	}

	public void setActivePage(Integer activePage) {
		_activePage = activePage;
	}

	public void setDisabledPages(List<Integer> disabledPages) {
		_disabledPages = disabledPages;
	}

	public void setEllipsisBuffer(Integer ellipsisBuffer) {
		_ellipsisBuffer = ellipsisBuffer;
	}

	public void setPaginationBarDeltas(
		List<PaginationBarDelta> paginationBarDeltas) {

		_paginationBarDeltas = paginationBarDeltas;
	}

	public void setPaginationBarLabels(
		PaginationBarLabels paginationBarLabels) {

		_paginationBarLabels = paginationBarLabels;
	}

	public void setShowDeltasDropDown(boolean showDeltasDropDown) {
		_showDeltasDropDown = false;
	}

	public void setTotalItems(Integer totalItems) {
		_totalItems = totalItems;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_activeDelta = null;
		_activePage = null;
		_disabledPages = null;
		_ellipsisBuffer = null;
		_paginationBarDeltas = null;
		_paginationBarLabels = null;
		_showDeltasDropDown = true;
		_totalItems = null;
	}

	@Override
	protected String getHydratedModuleName() {
		return "frontend-taglib-clay/PaginationBar";
	}

	@Override
	protected Map<String, Object> prepareProps(Map<String, Object> props) {
		props.put("activeDelta", _activeDelta);
		props.put("activePage", _activePage);
		props.put("deltas", _paginationBarDeltas);
		props.put("disabledPages", _disabledPages);
		props.put("ellipsisBuffer", _ellipsisBuffer);
		props.put("labels", _paginationBarLabels);
		props.put("showDeltasDropDown", _showDeltasDropDown);
		props.put("totalItems", _totalItems);

		return super.prepareProps(props);
	}

	@Override
	protected String processCssClasses(Set<String> cssClasses) {
		cssClasses.add("pagination-bar");

		return super.processCssClasses(cssClasses);
	}

	@Override
	protected int processStartTag() throws Exception {
		super.processStartTag();

		JspWriter jspWriter = pageContext.getOut();

		ResourceBundle resourceBundle = TagResourceBundleUtil.getResourceBundle(
			pageContext);

		if (_showDeltasDropDown) {
			jspWriter.write(
				"<div class=\"dropdown pagination-items-per-page\">");
			jspWriter.write(
				"<button class=\"dropdown-toggle btn btn-unstyled\"");
			jspWriter.write(" type=\"button\">");
			jspWriter.write(_activeDelta.toString());
			jspWriter.write(StringPool.SPACE);

			jspWriter.write(
				StringUtil.toLowerCase(
					LanguageUtil.get(resourceBundle, "items")));

			IconTag iconTag = new IconTag();

			iconTag.setSymbol("caret-double-l");

			iconTag.doTag(pageContext);

			jspWriter.write("</button></div>");
		}

		jspWriter.write("<div class=\"pagination-results\">");
		jspWriter.write(LanguageUtil.get(resourceBundle, "showing"));
		jspWriter.write(StringPool.SPACE);

		Integer from = ((_activePage - 1) * _activeDelta) + 1;

		jspWriter.write(from.toString());

		jspWriter.write(StringPool.SPACE);
		jspWriter.write(
			StringUtil.toLowerCase(LanguageUtil.get(resourceBundle, "to")));
		jspWriter.write(StringPool.SPACE);

		Integer to = _activePage * _activeDelta;

		if (to > _totalItems) {
			to = _totalItems;
		}

		jspWriter.write(to.toString());

		jspWriter.write(StringPool.SPACE);
		jspWriter.write(
			StringUtil.toLowerCase(LanguageUtil.get(resourceBundle, "of")));
		jspWriter.write(StringPool.SPACE);
		jspWriter.write(_totalItems.toString());
		jspWriter.write(StringPool.SPACE);
		jspWriter.write("</div><ul class=\"pagination pagination-root\">");
		jspWriter.write("<li class=\"page-item disabled\">");

		ButtonTag buttonTag = new ButtonTag();

		buttonTag.setCssClass("page-link");
		buttonTag.setDisplayType("unstyled");
		buttonTag.setIcon("angle-left");

		buttonTag.doTag(pageContext);

		jspWriter.write("</li>");

		int totalPages = (int)Math.ceil(
			(double)_totalItems / (double)_activeDelta);

		if (_ellipsisBuffer != 0) {
			int activeIndex = _activePage - 1;

			int[] pageNumberItems = new int[totalPages];

			for (int i = 0; i < totalPages; i++) {
				pageNumberItems[i] = i + 1;
			}

			int firstItem = pageNumberItems[0];

			_processItem(pageContext, firstItem);

			int leftIndex = activeIndex - _ellipsisBuffer;

			int leftItemsStartIndex = 1;
			int leftItemsEndIndex = Math.max(leftIndex, 1);

			if (leftItemsStartIndex < leftItemsEndIndex) {
				int[] leftItems = Arrays.copyOfRange(
					pageNumberItems, leftItemsStartIndex, leftItemsEndIndex);

				if (leftItems.length > 1) {
					_processEllipsis(pageContext);
				}
				else if (leftItems.length == 1) {
					int leftItem = leftItems[0];

					_processItem(pageContext, leftItem);
				}
			}

			int lastIndex = pageNumberItems.length - 1;

			int centerItemsStartIndex = Math.max(
				activeIndex - _ellipsisBuffer, 1);
			int centerItemsEndIndex = Math.min(
				activeIndex + _ellipsisBuffer + 1, lastIndex);

			if (centerItemsStartIndex < centerItemsEndIndex) {
				int[] centerItems = Arrays.copyOfRange(
					pageNumberItems, centerItemsStartIndex,
					centerItemsEndIndex);

				for (int centerItem : centerItems) {
					_processItem(pageContext, centerItem);
				}
			}

			int rightItemsStartIndex = activeIndex + _ellipsisBuffer + 1;

			if (rightItemsStartIndex > lastIndex) {
				rightItemsStartIndex = lastIndex;
			}

			int rightItemsEndIndex = Math.max(lastIndex, rightItemsStartIndex);

			if (rightItemsStartIndex < rightItemsEndIndex) {
				int[] rightItems = Arrays.copyOfRange(
					pageNumberItems, rightItemsStartIndex, rightItemsEndIndex);

				if (rightItems.length > 1) {
					_processEllipsis(pageContext);
				}
				else if (rightItems.length == 1) {
					int rightItem = rightItems[0];

					_processItem(pageContext, rightItem);
				}
			}

			if (totalPages > 1) {
				int lastItem = pageNumberItems[lastIndex];

				_processItem(pageContext, lastItem);
			}
		}
		else {
			for (int i = 1; i < (totalPages + 1); i++) {
				_processItem(pageContext, i);
			}
		}

		jspWriter.write("<li class=\"page-item\">");

		buttonTag = new ButtonTag();

		buttonTag.setCssClass("page-link");
		buttonTag.setDisplayType("unstyled");
		buttonTag.setIcon("angle-right");

		buttonTag.doTag(pageContext);

		jspWriter.write("</li></ul>");

		return SKIP_BODY;
	}

	private void _processEllipsis(PageContext pageContext) throws Exception {
		JspWriter jspWriter = pageContext.getOut();

		jspWriter.write("<li class=\"dropdown page-item\">");

		ButtonTag buttonTag = new ButtonTag();

		buttonTag.setCssClass("dropdown-toggle page-link");
		buttonTag.setDisplayType("unstyled");
		buttonTag.setLabel("...");

		buttonTag.doTag(pageContext);

		jspWriter.write("</li>");
	}

	private void _processItem(PageContext pageContext, int item)
		throws Exception {

		JspWriter jspWriter = pageContext.getOut();

		jspWriter.write("<li class=\"page-item");

		if (item == _activePage) {
			jspWriter.write(" active");
		}

		if ((_disabledPages != null) &&
			_disabledPages.contains(Integer.valueOf(item))) {

			jspWriter.write(" disabled");
		}

		jspWriter.write("\">");

		ButtonTag buttonTag = new ButtonTag();

		buttonTag.setCssClass("page-link");
		buttonTag.setDisplayType("unstyled");
		buttonTag.setLabel(String.valueOf(item));

		buttonTag.doTag(pageContext);

		jspWriter.write("</li>");
	}

	private static final String _ATTRIBUTE_NAMESPACE =
		"clay:management-toolbar:";

	private Integer _activeDelta;
	private Integer _activePage;
	private List<Integer> _disabledPages;
	private Integer _ellipsisBuffer;
	private List<PaginationBarDelta> _paginationBarDeltas;
	private PaginationBarLabels _paginationBarLabels;
	private boolean _showDeltasDropDown = true;
	private Integer _totalItems;

}