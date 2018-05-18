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

package com.liferay.commerce.product.definitions.web.internal.portlet.action;

import com.liferay.asset.kernel.exception.AssetCategoryException;
import com.liferay.asset.kernel.exception.AssetTagException;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetLink;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.kernel.service.AssetLinkLocalService;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.definitions.web.servlet.taglib.ui.CPDefinitionScreenNavigationConstants;
import com.liferay.commerce.product.exception.CPDefinitionMetaDescriptionException;
import com.liferay.commerce.product.exception.CPDefinitionMetaKeywordsException;
import com.liferay.commerce.product.exception.CPDefinitionMetaTitleException;
import com.liferay.commerce.product.exception.CPFriendlyURLEntryException;
import com.liferay.commerce.product.exception.NoSuchCPDefinitionException;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.TrashedModel;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.trash.kernel.service.TrashEntryService;
import com.liferay.trash.kernel.util.TrashUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CPPortletKeys.CP_DEFINITIONS,
		"mvc.command.name=editProductDefinition"
	},
	service = MVCActionCommand.class
)
public class EditCPDefinitionMVCActionCommand extends BaseMVCActionCommand {

	protected void deleteCPDefinitions(
			ActionRequest actionRequest, boolean moveToTrash)
		throws Exception {

		long[] deleteCPDefinitionIds = null;

		long cpDefinitionId = ParamUtil.getLong(
			actionRequest, "cpDefinitionId");

		if (cpDefinitionId > 0) {
			deleteCPDefinitionIds = new long[] {cpDefinitionId};
		}
		else {
			deleteCPDefinitionIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "deleteCPDefinitionIds"),
				0L);
		}

		List<TrashedModel> trashedModels = new ArrayList<>();

		for (long deleteCPDefinitionId : deleteCPDefinitionIds) {
			if (moveToTrash) {
				CPDefinition cpDefinition =
					_cpDefinitionService.moveCPDefinitionToTrash(
						deleteCPDefinitionId);

				trashedModels.add(cpDefinition);
			}
			else {
				_cpDefinitionService.deleteCPDefinition(deleteCPDefinitionId);
			}
		}

		if (moveToTrash && !trashedModels.isEmpty()) {
			TrashUtil.addTrashSessionMessages(actionRequest, trashedModels);

			SessionMessages.add(
				actionRequest,
				_portal.getPortletId(actionRequest) +
					SessionMessages.KEY_SUFFIX_HIDE_DEFAULT_SUCCESS_MESSAGE);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				CPDefinition cpDefinition = updateCPDefinition(actionRequest);

				String redirect = getSaveAndContinueRedirect(
					actionRequest, cpDefinition);

				sendRedirect(actionRequest, actionResponse, redirect);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteCPDefinitions(actionRequest, false);
			}
			else if (cmd.equals(Constants.MOVE_TO_TRASH)) {
				deleteCPDefinitions(actionRequest, true);
			}
			else if (cmd.equals("updateCategorization")) {
				updateCategorization(actionRequest);
			}
			else if (cmd.equals("updateCPDisplayLayout")) {
				updateCPDisplayLayout(actionRequest);
			}
			else if (cmd.equals("updateShippingInfo")) {
				updateShippingInfo(actionRequest);
			}
			else if (cmd.equals("updateTaxCategoryInfo")) {
				updateTaxCategoryInfo(actionRequest);
			}
			else if (cmd.equals(Constants.RESTORE)) {
				restoreTrashEntries(actionRequest);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchCPDefinitionException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else if (e instanceof AssetCategoryException ||
					 e instanceof AssetTagException ||
					 e instanceof CPDefinitionMetaDescriptionException ||
					 e instanceof CPDefinitionMetaKeywordsException ||
					 e instanceof CPDefinitionMetaTitleException ||
					 e instanceof CPFriendlyURLEntryException) {

				SessionErrors.add(actionRequest, e.getClass(), e);

				String redirect = ParamUtil.getString(
					actionRequest, "redirect");

				sendRedirect(actionRequest, actionResponse, redirect);
			}
			else {
				throw e;
			}
		}
	}

	protected long[] getAssetCategoryIds(CPDefinition cpDefinition) {
		AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
			cpDefinition.getModelClassName(), cpDefinition.getCPDefinitionId());

		if (assetEntry == null) {
			return new long[0];
		}

		return assetEntry.getCategoryIds();
	}

	protected double getAssetEntryPriority(CPDefinition cpDefinition) {
		AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
			cpDefinition.getModelClassName(), cpDefinition.getCPDefinitionId());

		if (assetEntry == null) {
			return 0;
		}

		return assetEntry.getPriority();
	}

	protected long[] getAssetLinkIds(CPDefinition cpDefinition) {
		AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
			cpDefinition.getModelClassName(), cpDefinition.getCPDefinitionId());

		if (assetEntry == null) {
			return new long[0];
		}

		List<Long> assetLinkIds = new ArrayList<>();

		List<AssetLink> assetLinks = _assetLinkLocalService.getLinks(
			assetEntry.getEntryId());

		for (AssetLink assetLink : assetLinks) {
			assetLinkIds.add(assetLink.getLinkId());
		}

		return ArrayUtil.toLongArray(assetLinkIds);
	}

	protected String[] getAssetTagNames(CPDefinition cpDefinition) {
		AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
			cpDefinition.getModelClassName(), cpDefinition.getCPDefinitionId());

		if (assetEntry == null) {
			return new String[0];
		}

		return assetEntry.getTagNames();
	}

	protected String getSaveAndContinueRedirect(
			ActionRequest actionRequest, CPDefinition cpDefinition)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletURL portletURL = PortletProviderUtil.getPortletURL(
			actionRequest, themeDisplay.getScopeGroup(),
			CPDefinition.class.getName(), PortletProvider.Action.EDIT);

		portletURL.setParameter(
			"mvcRenderCommandName", "editProductDefinition");
		portletURL.setParameter(
			"cpDefinitionId", String.valueOf(cpDefinition.getCPDefinitionId()));
		portletURL.setParameter(
			"screenNavigationCategoryKey",
			CPDefinitionScreenNavigationConstants.CATEGORY_KEY_DETAILS);

		return portletURL.toString();
	}

	protected void restoreTrashEntries(ActionRequest actionRequest)
		throws Exception {

		long[] restoreTrashEntryIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "restoreTrashEntryIds"), 0L);

		for (long restoreTrashEntryId : restoreTrashEntryIds) {
			_trashEntryService.restoreEntry(restoreTrashEntryId);
		}
	}

	protected void updateCategorization(ActionRequest actionRequest)
		throws PortalException {

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CPDefinition.class.getName(), actionRequest);

		long cpDefinitionId = ParamUtil.getLong(
			actionRequest, "cpDefinitionId");

		_cpDefinitionService.updateCPDefinitionCategorization(
			cpDefinitionId, serviceContext);
	}

	protected CPDefinition updateCPDefinition(ActionRequest actionRequest)
		throws Exception {

		long cpDefinitionId = ParamUtil.getLong(
			actionRequest, "cpDefinitionId");

		Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "nameMapAsXML");
		Map<Locale, String> shortDescriptionMap =
			LocalizationUtil.getLocalizationMap(
				actionRequest, "shortDescriptionMapAsXML");
		Map<Locale, String> descriptionMap =
			LocalizationUtil.getLocalizationMap(
				actionRequest, "descriptionMapAsXML");
		String productTypeName = ParamUtil.getString(
			actionRequest, "productTypeName");
		Map<Locale, String> urlTitleMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "urlTitleMapAsXML");
		Map<Locale, String> metaTitleMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "metaTitleMapAsXML");
		Map<Locale, String> metaDescriptionMap =
			LocalizationUtil.getLocalizationMap(
				actionRequest, "metaDescriptionMapAsXML");
		Map<Locale, String> metaKeywordsMap =
			LocalizationUtil.getLocalizationMap(
				actionRequest, "metaKeywordsMapAsXML");
		boolean published = ParamUtil.getBoolean(actionRequest, "published");

		int displayDateMonth = ParamUtil.getInteger(
			actionRequest, "displayDateMonth");
		int displayDateDay = ParamUtil.getInteger(
			actionRequest, "displayDateDay");
		int displayDateYear = ParamUtil.getInteger(
			actionRequest, "displayDateYear");
		int displayDateHour = ParamUtil.getInteger(
			actionRequest, "displayDateHour");
		int displayDateMinute = ParamUtil.getInteger(
			actionRequest, "displayDateMinute");
		int displayDateAmPm = ParamUtil.getInteger(
			actionRequest, "displayDateAmPm");

		if (displayDateAmPm == Calendar.PM) {
			displayDateHour += 12;
		}

		int expirationDateMonth = ParamUtil.getInteger(
			actionRequest, "expirationDateMonth");
		int expirationDateDay = ParamUtil.getInteger(
			actionRequest, "expirationDateDay");
		int expirationDateYear = ParamUtil.getInteger(
			actionRequest, "expirationDateYear");
		int expirationDateHour = ParamUtil.getInteger(
			actionRequest, "expirationDateHour");
		int expirationDateMinute = ParamUtil.getInteger(
			actionRequest, "expirationDateMinute");
		int expirationDateAmPm = ParamUtil.getInteger(
			actionRequest, "expirationDateAmPm");

		if (expirationDateAmPm == Calendar.PM) {
			expirationDateHour += 12;
		}

		boolean neverExpire = ParamUtil.getBoolean(
			actionRequest, "neverExpire");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CPDefinition.class.getName(), actionRequest);

		CPDefinition cpDefinition = null;

		if (cpDefinitionId <= 0) {

			// Add commerce product definition

			cpDefinition = _cpDefinitionService.addCPDefinition(
				nameMap, shortDescriptionMap, descriptionMap, urlTitleMap,
				metaTitleMap, metaDescriptionMap, metaKeywordsMap,
				productTypeName, true, null, published, displayDateMonth,
				displayDateDay, displayDateYear, displayDateHour,
				displayDateMinute, expirationDateMonth, expirationDateDay,
				expirationDateYear, expirationDateHour, expirationDateMinute,
				neverExpire, serviceContext);
		}
		else {

			// Update commerce product definition

			CPDefinition oldCPDefinition = _cpDefinitionService.getCPDefinition(
				cpDefinitionId);

			serviceContext.setAssetCategoryIds(
				getAssetCategoryIds(oldCPDefinition));
			serviceContext.setAssetLinkEntryIds(
				getAssetLinkIds(oldCPDefinition));
			serviceContext.setAssetPriority(
				getAssetEntryPriority(oldCPDefinition));
			serviceContext.setAssetTagNames(getAssetTagNames(oldCPDefinition));

			cpDefinition = _cpDefinitionService.updateCPDefinition(
				cpDefinitionId, nameMap, shortDescriptionMap, descriptionMap,
				urlTitleMap, metaTitleMap, metaDescriptionMap, metaKeywordsMap,
				oldCPDefinition.isIgnoreSKUCombinations(), null, published,
				displayDateMonth, displayDateDay, displayDateYear,
				displayDateHour, displayDateMinute, expirationDateMonth,
				expirationDateDay, expirationDateYear, expirationDateHour,
				expirationDateMinute, neverExpire, serviceContext);
		}

		return cpDefinition;
	}

	protected void updateCPDisplayLayout(ActionRequest actionRequest)
		throws PortalException {

		long cpDefinitionId = ParamUtil.getLong(
			actionRequest, "cpDefinitionId");

		String layoutUuid = ParamUtil.getString(actionRequest, "layoutUuid");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CPDefinition.class.getName(), actionRequest);

		_cpDefinitionService.updateCPDisplayLayout(
			cpDefinitionId, layoutUuid, serviceContext);
	}

	protected void updateShippingInfo(ActionRequest actionRequest)
		throws PortalException {

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CPDefinition.class.getName(), actionRequest);

		long cpDefinitionId = ParamUtil.getLong(
			actionRequest, "cpDefinitionId");

		boolean shippable = ParamUtil.getBoolean(actionRequest, "shippable");
		boolean freeShipping = ParamUtil.getBoolean(
			actionRequest, "freeShipping");
		boolean shipSeparately = ParamUtil.getBoolean(
			actionRequest, "shipSeparately");
		double shippingExtraPrice = ParamUtil.getDouble(
			actionRequest, "shippingExtraPrice");
		double width = ParamUtil.getDouble(actionRequest, "width");
		double height = ParamUtil.getDouble(actionRequest, "height");
		double depth = ParamUtil.getDouble(actionRequest, "depth");
		double weight = ParamUtil.getDouble(actionRequest, "weight");

		_cpDefinitionService.updateShippingInfo(
			cpDefinitionId, shippable, freeShipping, shipSeparately,
			shippingExtraPrice, width, height, depth, weight, serviceContext);
	}

	protected void updateTaxCategoryInfo(ActionRequest actionRequest)
		throws PortalException {

		long cpDefinitionId = ParamUtil.getLong(
			actionRequest, "cpDefinitionId");

		long cpTaxCategoryId = ParamUtil.getLong(
			actionRequest, "cpTaxCategoryId");
		boolean taxExempt = ParamUtil.getBoolean(actionRequest, "taxExempt");
		boolean telcoOrElectronics = ParamUtil.getBoolean(
			actionRequest, "telcoOrElectronics");

		_cpDefinitionService.updateTaxCategoryInfo(
			cpDefinitionId, cpTaxCategoryId, taxExempt, telcoOrElectronics);
	}

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private AssetLinkLocalService _assetLinkLocalService;

	@Reference
	private CPDefinitionService _cpDefinitionService;

	@Reference
	private Portal _portal;

	@Reference
	private TrashEntryService _trashEntryService;

}