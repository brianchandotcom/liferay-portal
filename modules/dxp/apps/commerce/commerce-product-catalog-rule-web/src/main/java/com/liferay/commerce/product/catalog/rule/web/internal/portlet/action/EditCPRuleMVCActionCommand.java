/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.product.catalog.rule.web.internal.portlet.action;

import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.exception.CPRuleTypeException;
import com.liferay.commerce.product.exception.NoSuchCPRuleException;
import com.liferay.commerce.product.model.CPRule;
import com.liferay.commerce.product.model.CPRuleAssetCategoryRel;
import com.liferay.commerce.product.service.CPRuleAssetCategoryRelService;
import com.liferay.commerce.product.service.CPRuleService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.List;
import java.util.concurrent.Callable;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CPPortletKeys.CP_CATALOG_RULE,
		"mvc.command.name=editCPRule"
	},
	service = MVCActionCommand.class
)
public class EditCPRuleMVCActionCommand extends BaseMVCActionCommand {

	protected void deleteCPRules(ActionRequest actionRequest)
		throws PortalException {

		long[] deleteCPRuleIds = null;

		long cpRuleId = ParamUtil.getLong(actionRequest, "cpRuleId");

		if (cpRuleId > 0) {
			deleteCPRuleIds = new long[] {cpRuleId};
		}
		else {
			deleteCPRuleIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "deleteCPRuleIds"), 0L);
		}

		for (long deleteCPRuleId : deleteCPRuleIds) {
			_cpRuleService.deleteCPRule(deleteCPRuleId);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				Callable<CPRule> cpRuleCallable = new CPRuleCallable(
					actionRequest);

				CPRule cpRule = TransactionInvokerUtil.invoke(
					_transactionConfig, cpRuleCallable);

				String redirect = getSaveAndContinueRedirect(
					actionRequest, cpRule);

				sendRedirect(actionRequest, actionResponse, redirect);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteCPRules(actionRequest);
			}
		}
		catch (Throwable t) {
			if (t instanceof NoSuchCPRuleException ||
				t instanceof PrincipalException) {

				SessionErrors.add(actionRequest, t.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else if (t instanceof CPRuleTypeException) {
				SessionErrors.add(actionRequest, t.getClass());

				String redirect = ParamUtil.getString(
					actionRequest, "redirect");

				sendRedirect(actionRequest, actionResponse, redirect);
			}
			else {
				_log.error(t);
			}
		}
	}

	protected String getSaveAndContinueRedirect(
		ActionRequest actionRequest, CPRule cpRule) {

		PortletURL portletURL = _portal.getControlPanelPortletURL(
			actionRequest, CPPortletKeys.CP_CATALOG_RULE,
			PortletRequest.RENDER_PHASE);

		if (cpRule != null) {
			portletURL.setParameter("mvcRenderCommandName", "editCPRule");
			portletURL.setParameter(
				"cpRuleId", String.valueOf(cpRule.getCPRuleId()));

			String redirect = ParamUtil.getString(actionRequest, "redirect");

			portletURL.setParameter("redirect", redirect);
		}

		return portletURL.toString();
	}

	protected CPRule updateCPRule(ActionRequest actionRequest)
		throws Exception {

		long cpRuleId = ParamUtil.getLong(actionRequest, "cpRuleId");

		String name = ParamUtil.getString(actionRequest, "name");
		boolean active = ParamUtil.getBoolean(actionRequest, "active");
		String type = ParamUtil.getString(actionRequest, "type");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CPRule.class.getName(), actionRequest);

		CPRule cpRule = null;

		if (cpRuleId <= 0) {
			cpRule = _cpRuleService.addCPRule(
				name, active, type, serviceContext);
		}
		else {
			cpRule = _cpRuleService.updateCPRule(
				cpRuleId, name, active, type, serviceContext);
		}

		if (cpRule != null) {
			updateCPRuleAssetCategoryRels(actionRequest, cpRule);
		}

		return cpRule;
	}

	protected void updateCPRuleAssetCategoryRels(
			ActionRequest actionRequest, CPRule cpRule)
		throws PortalException {

		String assetCategoryIds = ParamUtil.getString(
			actionRequest, "assetCategoryIds");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CPRuleAssetCategoryRel.class.getName(), actionRequest);

		String[] assetCategoryIdsArray = StringUtil.split(
			assetCategoryIds, StringPool.COMMA);

		List<CPRuleAssetCategoryRel> cpRuleAssetCategoryRels =
			_cpRuleAssetCategoryRelService.getCPRuleAssetCategoryRels(
				cpRule.getCPRuleId());

		for (CPRuleAssetCategoryRel cpRuleAssetCategoryRel :
				cpRuleAssetCategoryRels) {

			if (ArrayUtil.contains(
					assetCategoryIdsArray,
					String.valueOf(
						cpRuleAssetCategoryRel.getAssetCategoryId()))) {

				continue;
			}

			_cpRuleAssetCategoryRelService.deleteCPRuleAssetCategoryRel(
				cpRuleAssetCategoryRel.getCPRuleAssetCategoryRelId());
		}

		long[] cpRuleAssetCategoryIds =
			_cpRuleAssetCategoryRelService.getAssetCategoryIds(
				cpRule.getCPRuleId());

		for (String newAssetCategoryId : assetCategoryIdsArray) {
			long assetCategoryId = GetterUtil.getLong(newAssetCategoryId);

			if (ArrayUtil.contains(cpRuleAssetCategoryIds, assetCategoryId)) {
				continue;
			}

			if (assetCategoryId > 0) {
				_cpRuleAssetCategoryRelService.addCPRuleAssetCategoryRel(
					cpRule.getCPRuleId(), assetCategoryId, serviceContext);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditCPRuleMVCActionCommand.class);

	private static final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRED, new Class<?>[] {Exception.class});

	@Reference
	private CPRuleAssetCategoryRelService _cpRuleAssetCategoryRelService;

	@Reference
	private CPRuleService _cpRuleService;

	@Reference
	private Portal _portal;

	private class CPRuleCallable implements Callable<CPRule> {

		@Override
		public CPRule call() throws Exception {
			return updateCPRule(_actionRequest);
		}

		private CPRuleCallable(ActionRequest actionRequest) {
			_actionRequest = actionRequest;
		}

		private final ActionRequest _actionRequest;

	}

}