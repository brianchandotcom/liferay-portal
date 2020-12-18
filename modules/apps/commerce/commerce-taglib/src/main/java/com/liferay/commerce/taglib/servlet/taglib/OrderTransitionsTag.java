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

import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.order.CommerceOrderValidatorRegistry;
import com.liferay.commerce.order.status.CommerceOrderStatus;
import com.liferay.commerce.order.status.CommerceOrderStatusRegistry;
import com.liferay.commerce.service.CommerceOrderServiceUtil;
import com.liferay.commerce.taglib.servlet.taglib.internal.servlet.ServletContextUtil;
import com.liferay.commerce.util.CommerceWorkflowedModelHelper;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
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
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException, portalException);
			}

			return SKIP_BODY;
		}

		return super.doStartTag();
	}

	public long getCommerceOrderId() {
		return _commerceOrderId;
	}

	public String getCssClass() {
		return _cssClass;
	}

	public void setCommerceOrderId(long commerceOrderId) {
		_commerceOrderId = commerceOrderId;
	}

	public void setCssClass(String cssClass) {
		if (Validator.isNull(cssClass) || cssClass.equals(StringPool.NULL)) {
			cssClass = "btn btn-secondary";
		}

		_cssClass = cssClass;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		commerceWorkflowedModelHelper =
			ServletContextUtil.getCommerceOrderHelper();
		commerceOrderStatusRegistry =
			ServletContextUtil.getCommerceOrderStatusRegistry();
		commerceOrderModelResourcePermission =
			ServletContextUtil.getCommerceOrderModelResourcePermission();
		commerceOrderValidatorRegistry =
			ServletContextUtil.getCommerceOrderValidatorRegistry();
		servletContext = ServletContextUtil.getServletContext();
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_commerceOrder = null;
		_commerceOrderId = 0;
		_commerceOrderTransitionOVPs = null;
		_cssClass = null;
		_pathThemeImages = null;
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

	protected ModelResourcePermission<CommerceOrder>
		commerceOrderModelResourcePermission;
	protected CommerceOrderStatusRegistry commerceOrderStatusRegistry;
	protected CommerceOrderValidatorRegistry commerceOrderValidatorRegistry;
	protected CommerceWorkflowedModelHelper commerceWorkflowedModelHelper;

	private List<ObjectValuePair<Long, String>> _getCommerceOrderTransitionOVPs(
			CommerceOrder commerceOrder, ThemeDisplay themeDisplay)
		throws PortalException {

		List<ObjectValuePair<Long, String>> transitionOVPs = new ArrayList<>();

		if (commerceOrder == null) {
			return transitionOVPs;
		}

		CommerceOrderStatus inProgressCommerceOrderStatus =
			commerceOrderStatusRegistry.getCommerceOrderStatus(
				CommerceOrderConstants.ORDER_STATUS_IN_PROGRESS);

		if (!commerceOrder.isOpen()) {
			transitionOVPs.add(new ObjectValuePair<>(0L, "reorder"));
		}
		else if (inProgressCommerceOrderStatus.isTransitionCriteriaMet(
					commerceOrder)) {

			if (commerceOrder.isApproved()) {
				transitionOVPs.add(new ObjectValuePair<>(0L, "checkout"));
			}
			else if (commerceOrder.isDraft()) {
				transitionOVPs.add(new ObjectValuePair<>(0L, "submit"));
			}
		}

		transitionOVPs.addAll(
			commerceWorkflowedModelHelper.getWorkflowTransitions(
				themeDisplay.getUserId(), commerceOrder.getCompanyId(),
				commerceOrder.getModelClassName(),
				commerceOrder.getCommerceOrderId()));

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