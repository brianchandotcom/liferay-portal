/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.payment.web.internal.portlet.action;

import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.currency.util.CommercePriceFormatter;
import com.liferay.commerce.payment.exception.NoSuchPaymentEntryException;
import com.liferay.commerce.payment.gateway.CommercePaymentGateway;
import com.liferay.commerce.payment.model.CommercePaymentEntry;
import com.liferay.commerce.payment.service.CommercePaymentEntryService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.math.BigDecimal;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = {
		"javax.portlet.name=" + CommercePortletKeys.COMMERCE_PAYMENT,
		"mvc.command.name=/commerce_payment/edit_commerce_payment_entry"
	},
	service = MVCActionCommand.class
)
public class EditCommercePaymentEntryMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				CommercePaymentEntry commercePaymentEntry =
					_addOrUpdateCommercePaymentEntry(actionRequest);

				String redirect = getSaveAndContinueRedirect(
					actionRequest,
					commercePaymentEntry.getCommercePaymentEntryId());

				sendRedirect(actionRequest, actionResponse, redirect);
			}
			else if (cmd.equals(Constants.PUBLISH)) {
				CommercePaymentEntry commercePaymentEntry =
					_addOrUpdateCommercePaymentEntry(actionRequest);

				commercePaymentEntry = _commercePaymentGateway.refund(
					_portal.getHttpServletRequest(actionRequest),
					commercePaymentEntry);

				String redirect = getSaveAndContinueRedirect(
					actionRequest,
					commercePaymentEntry.getCommercePaymentEntryId());

				sendRedirect(actionRequest, actionResponse, redirect);
			}
			else if (cmd.equals("updateNote")) {
				long commercePaymentEntryId = ParamUtil.getLong(
					actionRequest, "commercePaymentEntryId");

				String note = ParamUtil.getString(actionRequest, "note");

				_commercePaymentEntryService.updateNote(
					commercePaymentEntryId, note);
			}
		}
		catch (Exception exception) {
			if (exception instanceof NoSuchPaymentEntryException ||
				exception instanceof PrincipalException) {

				SessionErrors.add(actionRequest, exception.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else {
				hideDefaultErrorMessage(actionRequest);
				hideDefaultSuccessMessage(actionRequest);

				SessionErrors.add(actionRequest, exception.getClass());

				String redirect = ParamUtil.getString(
					actionRequest, "redirect");

				sendRedirect(actionRequest, actionResponse, redirect);
			}
		}
	}

	protected String getSaveAndContinueRedirect(
			ActionRequest actionRequest, long commercePaymentEntryId)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		return PortletURLBuilder.create(
			PortletProviderUtil.getPortletURL(
				actionRequest, themeDisplay.getScopeGroup(),
				CommercePaymentEntry.class.getName(),
				PortletProvider.Action.EDIT)
		).setMVCRenderCommandName(
			"/commerce_payment/edit_commerce_payment_entry"
		).setParameter(
			"commercePaymentEntryId", commercePaymentEntryId
		).buildString();
	}

	private CommercePaymentEntry _addOrUpdateCommercePaymentEntry(
			ActionRequest actionRequest)
		throws Exception {

		long commercePaymentEntryId = ParamUtil.getLong(
			actionRequest, "commercePaymentEntryId");

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String amount = ParamUtil.getString(
			actionRequest, "amount", BigDecimal.ZERO.toString());

		amount = _commercePriceFormatter.parse(
			amount, themeDisplay.getLocale());

		BigDecimal formattedAmount = new BigDecimal(amount);

		String reasonKey = ParamUtil.getString(actionRequest, "reasonKey");

		if (commercePaymentEntryId > 0) {
			CommercePaymentEntry curCommercePaymentEntry =
				_commercePaymentEntryService.getCommercePaymentEntry(
					commercePaymentEntryId);

			return _commercePaymentEntryService.updateCommercePaymentEntry(
				curCommercePaymentEntry.getExternalReferenceCode(),
				curCommercePaymentEntry.getCommercePaymentEntryId(),
				curCommercePaymentEntry.getCommerceChannelId(), formattedAmount,
				curCommercePaymentEntry.getCallbackURL(),
				curCommercePaymentEntry.getCancelURL(),
				curCommercePaymentEntry.getCurrencyCode(),
				curCommercePaymentEntry.getErrorMessages(),
				curCommercePaymentEntry.getLanguageId(),
				curCommercePaymentEntry.getNote(),
				curCommercePaymentEntry.getPaymentIntegrationKey(),
				curCommercePaymentEntry.getPaymentIntegrationType(),
				curCommercePaymentEntry.getPaymentStatus(), reasonKey,
				curCommercePaymentEntry.getRedirectURL(),
				curCommercePaymentEntry.getTransactionCode(),
				curCommercePaymentEntry.getType());
		}

		long commerceChannelId = ParamUtil.getLong(
			actionRequest, "commerceChannelId");
		String className = ParamUtil.getString(actionRequest, "className");
		long classPK = ParamUtil.getLong(actionRequest, "classPK");
		String currencyCode = ParamUtil.getString(
			actionRequest, "currencyCode");
		String languageId = ParamUtil.getString(actionRequest, "languageId");
		String paymentIntegrationKey = ParamUtil.getString(
			actionRequest, "paymentIntegrationKey");
		int paymentIntegrationType = ParamUtil.getInteger(
			actionRequest, "paymentIntegrationType");
		String transactionCode = ParamUtil.getString(
			actionRequest, "transactionCode");
		int type = ParamUtil.getInteger(actionRequest, "type");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommercePaymentEntry.class.getName(), actionRequest);

		return _commercePaymentEntryService.addCommercePaymentEntry(
			_classNameLocalService.getClassNameId(className), classPK,
			commerceChannelId, formattedAmount, StringPool.BLANK,
			StringPool.BLANK, currencyCode, languageId, StringPool.BLANK,
			paymentIntegrationKey, paymentIntegrationType, reasonKey,
			transactionCode, type, serviceContext);
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private CommercePaymentEntryService _commercePaymentEntryService;

	@Reference
	private CommercePaymentGateway _commercePaymentGateway;

	@Reference
	private CommercePriceFormatter _commercePriceFormatter;

	@Reference
	private Portal _portal;

}