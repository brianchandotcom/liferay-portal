/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.order.content.web.internal.asset.display.page;

import com.liferay.asset.display.page.portlet.BaseAssetDisplayPageFriendlyURLResolver;
import com.liferay.asset.display.page.util.AssetDisplayPageUtil;
import com.liferay.commerce.constants.CommerceOrderWebKeys;
import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.context.CommerceContextFactory;
import com.liferay.commerce.friendly.url.CommerceFriendlyURL;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.order.CommerceOrderHttpHelper;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.info.item.InfoItemReference;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProvider;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutFriendlyURLComposite;
import com.liferay.portal.kernel.portlet.FriendlyURLResolver;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.InheritableMap;
import com.liferay.portal.kernel.util.Portal;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(service = FriendlyURLResolver.class)
public class CommerceOrderAssetDisplayPageFriendlyURLResolver
	extends BaseAssetDisplayPageFriendlyURLResolver {

	@Override
	public String getActualURL(
			long companyId, long groupId, boolean privateLayout,
			String mainPath, String friendlyURL, Map<String, String[]> params,
			Map<String, Object> requestContext)
		throws PortalException {

		String urlSeparator = getURLSeparator();

		long commerceOrderId = Long.valueOf(
			friendlyURL.substring(urlSeparator.length()));

		CommerceOrder commerceOrder =
			_commerceOrderLocalService.fetchCommerceOrder(commerceOrderId);

		if (commerceOrder == null) {
			return null;
		}

		HttpServletRequest httpServletRequest =
			(HttpServletRequest)requestContext.get("request");

		_setCurrentCommerceOrder(commerceOrder, httpServletRequest);

		httpServletRequest.setAttribute(
			CommerceWebKeys.COMMERCE_ORDER, commerceOrder);

		LayoutDisplayPageObjectProvider<?> layoutDisplayPageObjectProvider =
			_getLayoutDisplayPageObjectProvider(commerceOrder);

		if ((layoutDisplayPageObjectProvider != null) &&
			AssetDisplayPageUtil.hasAssetDisplayPage(
				groupId, layoutDisplayPageObjectProvider.getClassNameId(),
				layoutDisplayPageObjectProvider.getClassPK(),
				layoutDisplayPageObjectProvider.getClassTypeId())) {

			return super.getActualURL(
				companyId, groupId, privateLayout, mainPath, friendlyURL,
				params, requestContext);
		}

		return _getBasicLayoutURL(
			groupId, mainPath, params, requestContext, commerceOrder);
	}

	@Override
	public LayoutFriendlyURLComposite getLayoutFriendlyURLComposite(
			long companyId, long groupId, boolean privateLayout,
			String friendlyURL, Map<String, String[]> params,
			Map<String, Object> requestContext)
		throws PortalException {

		String urlSeparator = getURLSeparator();

		long commerceOrderId = Long.valueOf(
			friendlyURL.substring(urlSeparator.length()));

		CommerceOrder commerceOrder =
			_commerceOrderLocalService.fetchCommerceOrder(commerceOrderId);

		if (commerceOrder == null) {
			return null;
		}

		LayoutDisplayPageObjectProvider<?> layoutDisplayPageObjectProvider =
			_getLayoutDisplayPageObjectProvider(commerceOrder);

		if ((layoutDisplayPageObjectProvider != null) &&
			AssetDisplayPageUtil.hasAssetDisplayPage(
				groupId, layoutDisplayPageObjectProvider.getClassNameId(),
				layoutDisplayPageObjectProvider.getClassPK(),
				layoutDisplayPageObjectProvider.getClassTypeId())) {

			return super.getLayoutFriendlyURLComposite(
				companyId, groupId, privateLayout, friendlyURL, params,
				requestContext);
		}

		Layout layout = _getOrderLayout(commerceOrder, groupId);

		return new LayoutFriendlyURLComposite(
			layout, getURLSeparator() + commerceOrder.getCommerceOrderId(),
			false);
	}

	@Override
	public String getURLSeparator() {
		return _commerceFriendlyURL.getOrderURLSeparator(
			CompanyThreadLocal.getCompanyId());
	}

	private String _getBasicLayoutURL(
			long groupId, String mainPath, Map<String, String[]> params,
			Map<String, Object> requestContext, CommerceOrder commerceOrder)
		throws PortalException {

		HttpServletRequest httpServletRequest =
			(HttpServletRequest)requestContext.get("request");

		Layout layout = _getOrderLayout(commerceOrder, groupId);

		String layoutActualURL = _portal.getLayoutActualURL(layout, mainPath);

		InheritableMap<String, String[]> actualParams = new InheritableMap<>();

		if (params != null) {
			actualParams.setParentMap(params);
		}

		actualParams.put("p_p_lifecycle", new String[] {"0"});
		actualParams.put("p_p_mode", new String[] {"view"});

		String queryString = HttpComponentsUtil.parameterMapToString(
			actualParams, false);

		if (layoutActualURL.contains(StringPool.QUESTION)) {
			layoutActualURL =
				layoutActualURL + StringPool.AMPERSAND + queryString;
		}
		else {
			layoutActualURL =
				layoutActualURL + StringPool.QUESTION + queryString;
		}

		_portal.addPageSubtitle(
			String.valueOf(commerceOrder.getCommerceOrderId()),
			httpServletRequest);

		httpServletRequest.setAttribute(
			CommerceWebKeys.COMMERCE_ORDER_ID,
			commerceOrder.getCommerceOrderId());

		return layoutActualURL;
	}

	private LayoutDisplayPageObjectProvider<?>
		_getLayoutDisplayPageObjectProvider(CommerceOrder commerceOrder) {

		LayoutDisplayPageProvider<?> layoutDisplayPageProvider =
			layoutDisplayPageProviderRegistry.
				getLayoutDisplayPageProviderByClassName(
					CommerceOrder.class.getName());

		InfoItemReference infoItemReference = new InfoItemReference(
			CommerceOrder.class.getName(), commerceOrder.getCommerceOrderId());

		return layoutDisplayPageProvider.getLayoutDisplayPageObjectProvider(
			infoItemReference);
	}

	private Layout _getOrderLayout(CommerceOrder commerceOrder, long groupId)
		throws PortalException {

		try {
			long plid = _portal.getPlidFromPortletId(
				groupId, CommercePortletKeys.COMMERCE_ORDER_CONTENT);

			if ((plid > 0) && (commerceOrder != null) &&
				!commerceOrder.isOpen()) {

				return _layoutLocalService.getLayout(plid);
			}

			plid = _portal.getPlidFromPortletId(
				groupId, CommercePortletKeys.COMMERCE_OPEN_ORDER_CONTENT);

			if ((plid > 0) && (commerceOrder != null) &&
				commerceOrder.isOpen()) {

				return _layoutLocalService.getLayout(plid);
			}

			plid = _portal.getPlidFromPortletId(
				groupId, CommercePortletKeys.COMMERCE_CART_CONTENT);

			if (plid > 0) {
				return _layoutLocalService.getLayout(plid);
			}

			return null;
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}

			throw portalException;
		}
	}

	private void _setCurrentCommerceOrder(
			CommerceOrder commerceOrder, HttpServletRequest httpServletRequest)
		throws PortalException {

		httpServletRequest.setAttribute(
			CommerceOrderWebKeys.MERGE_GUEST_ORDER, Boolean.FALSE);

		CommerceContext commerceContext = _commerceContextFactory.create(
			httpServletRequest);

		httpServletRequest.setAttribute(
			CommerceWebKeys.COMMERCE_CONTEXT, commerceContext);

		CommerceOrder currentCommerceOrder =
			_commerceOrderHttpHelper.getCurrentCommerceOrder(
				httpServletRequest);

		if ((currentCommerceOrder == null) ||
			(commerceOrder.getCommerceOrderId() !=
				currentCommerceOrder.getCommerceOrderId())) {

			_commerceOrderHttpHelper.setCurrentCommerceOrder(
				httpServletRequest, commerceOrder);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceOrderAssetDisplayPageFriendlyURLResolver.class);

	@Reference
	private CommerceContextFactory _commerceContextFactory;

	@Reference
	private CommerceFriendlyURL _commerceFriendlyURL;

	@Reference
	private CommerceOrderHttpHelper _commerceOrderHttpHelper;

	@Reference
	private CommerceOrderLocalService _commerceOrderLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

}