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

package com.liferay.commerce.taglib.servlet.taglib;

import com.liferay.commerce.constants.CommerceOrderActionKeys;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.order.CommerceOrderHelper;
import com.liferay.commerce.service.CommerceOrderServiceUtil;
import com.liferay.commerce.taglib.servlet.taglib.internal.servlet.ServletContextUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.util.IncludeTag;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * @author Alessio Antonio Rendina
 */
public class OrderTransitionsTag extends IncludeTag {

	@Override
	public int doStartTag() throws JspException {
		try {
			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

			_commerceOrder = CommerceOrderServiceUtil.fetchCommerceOrder(
				_commerceOrderId);

			_commerceOrderTransitionOVPs = _getCommerceOrderTransitionOVPs(
				_commerceOrder, themeDisplay);

			_pathThemeImages = themeDisplay.getPathThemeImages();
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}

			return SKIP_BODY;
		}

		return super.doStartTag();
	}

	public void setCommerceOrderId(long commerceOrderId) {
		_commerceOrderId = commerceOrderId;
	}

	public void setCssClass(String cssClass) {
		if (Validator.isNull(cssClass) || cssClass.equals(StringPool.NULL)) {
			cssClass = "btn btn-default";
		}

		_cssClass = cssClass;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		commerceOrderHelper = ServletContextUtil.getCommerceOrderHelper();
		commerceOrderModelResourcePermission =
			ServletContextUtil.getCommerceOrderModelResourcePermission();
		servletContext = ServletContextUtil.getServletContext();
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_commerceOrderId = 0;
		_cssClass = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		request.setAttribute(
			"liferay-commerce:order-transitions:commerceOrder", _commerceOrder);
		request.setAttribute(
			"liferay-commerce:order-transitions:commerceOrderTransitionOVPs",
			_commerceOrderTransitionOVPs);
		request.setAttribute(
			"liferay-commerce:order-transitions:cssClass", _cssClass);
		request.setAttribute(
			"liferay-commerce:order-transitions:pathThemeImages",
			_pathThemeImages);
	}

	protected CommerceOrderHelper commerceOrderHelper;
	protected ModelResourcePermission<CommerceOrder>
		commerceOrderModelResourcePermission;

	private List<ObjectValuePair<Long, String>> _getCommerceOrderTransitionOVPs(
			CommerceOrder commerceOrder, ThemeDisplay themeDisplay)
		throws PortalException {

		List<ObjectValuePair<Long, String>> transitionOVPs = new ArrayList<>();

		if (commerceOrder == null) {
			return transitionOVPs;
		}

		if (!commerceOrder.isOpen()) {
			transitionOVPs.add(
				new ObjectValuePair<Long, String>(0L, "reorder"));
		}

		ObjectValuePair<Long, String> approveOVP = null;

		if (commerceOrder.isOpen() && commerceOrder.isPending() &&
			commerceOrderModelResourcePermission.contains(
				themeDisplay.getPermissionChecker(), commerceOrder,
				CommerceOrderActionKeys.APPROVE_COMMERCE_ORDER)) {

			approveOVP = new ObjectValuePair<>(0L, "approve");

			transitionOVPs.add(approveOVP);
		}

		if (commerceOrder.isOpen() && commerceOrder.isApproved() &&
			commerceOrderModelResourcePermission.contains(
				themeDisplay.getPermissionChecker(), commerceOrder,
				CommerceOrderActionKeys.CHECKOUT_COMMERCE_ORDER)) {

			transitionOVPs.add(new ObjectValuePair<>(0L, "checkout"));
		}

		if (commerceOrder.isOpen() && commerceOrder.isDraft() &&
			!commerceOrder.isEmpty() &&
			commerceOrderModelResourcePermission.contains(
				themeDisplay.getPermissionChecker(), commerceOrder,
				ActionKeys.UPDATE)) {

			transitionOVPs.add(new ObjectValuePair<>(0L, "submit"));
		}

		int start = transitionOVPs.size();

		transitionOVPs.addAll(
			commerceOrderHelper.getWorkflowTransitions(
				themeDisplay.getUserId(), commerceOrder));

		if (approveOVP != null) {
			for (int i = start; i < transitionOVPs.size(); i++) {
				ObjectValuePair<Long, String> objectValuePair =
					transitionOVPs.get(i);

				String value = objectValuePair.getValue();

				if (value.equals(approveOVP.getValue())) {
					approveOVP.setValue("force-" + value);

					break;
				}
			}
		}

		return transitionOVPs;
	}

	private static final String _PAGE = "/order_transitions/page.jsp";

	private static final Log _log = LogFactoryUtil.getLog(
		OrderTransitionsTag.class);

	private CommerceOrder _commerceOrder;
	private long _commerceOrderId;
	private List<ObjectValuePair<Long, String>> _commerceOrderTransitionOVPs;
	private String _cssClass;
	private String _pathThemeImages;

}