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

package com.liferay.commerce.product.content.web.internal.util;

import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.inventory.CommerceInventoryChecker;
import com.liferay.commerce.media.CommerceCatalogDefaultImage;
import com.liferay.commerce.media.CommerceMediaResolver;
import com.liferay.commerce.product.catalog.CPCatalogEntry;
import com.liferay.commerce.product.catalog.CPMedia;
import com.liferay.commerce.product.catalog.CPSku;
import com.liferay.commerce.product.constants.CPAttachmentFileEntryConstants;
import com.liferay.commerce.product.constants.CPContentContributorConstants;
import com.liferay.commerce.product.constants.CPOptionCategoryConstants;
import com.liferay.commerce.product.constants.CPWebKeys;
import com.liferay.commerce.product.content.render.CPContentRenderer;
import com.liferay.commerce.product.content.render.CPContentRendererRegistry;
import com.liferay.commerce.product.content.util.CPContentHelper;
import com.liferay.commerce.product.ddm.DDMHelper;
import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CPInstanceOptionValueRel;
import com.liferay.commerce.product.model.CPOptionCategory;
import com.liferay.commerce.product.model.CProduct;
import com.liferay.commerce.product.service.CPAttachmentFileEntryLocalService;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.CPDefinitionOptionValueRelLocalService;
import com.liferay.commerce.product.service.CPDefinitionSpecificationOptionValueLocalService;
import com.liferay.commerce.product.service.CPInstanceOptionValueRelLocalService;
import com.liferay.commerce.product.service.CPOptionCategoryLocalService;
import com.liferay.commerce.product.service.CProductLocalService;
import com.liferay.commerce.product.type.CPType;
import com.liferay.commerce.product.type.CPTypeServicesTracker;
import com.liferay.commerce.product.util.CPContentContributor;
import com.liferay.commerce.product.util.CPContentContributorRegistry;
import com.liferay.commerce.product.util.CPDefinitionHelper;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.commerce.wish.list.model.CommerceWishList;
import com.liferay.commerce.wish.list.service.CommerceWishListItemService;
import com.liferay.commerce.wish.list.service.CommerceWishListService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portlet.documentlibrary.lar.FileEntryUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(enabled = false, immediate = true, service = CPContentHelper.class)
public class CPContentHelperImpl implements CPContentHelper {

	@Override
	public String getAvailabilityEstimateLabel(
			HttpServletRequest httpServletRequest)
		throws Exception {

		JSONObject availabilityEstimateJSONObject =
			(JSONObject)getCPContentContributorValue(
				CPContentContributorConstants.AVAILABILITY_ESTIMATE_NAME,
				httpServletRequest);

		if (availabilityEstimateJSONObject == null) {
			return StringPool.BLANK;
		}

		return availabilityEstimateJSONObject.getString(
			CPContentContributorConstants.AVAILABILITY_ESTIMATE_NAME);
	}

	@Override
	public String getAvailabilityLabel(HttpServletRequest httpServletRequest)
		throws Exception {

		JSONObject availabilityJSONObject =
			(JSONObject)getCPContentContributorValue(
				CPContentContributorConstants.AVAILABILITY_NAME,
				httpServletRequest);

		if (availabilityJSONObject == null) {
			return StringPool.BLANK;
		}

		return availabilityJSONObject.getString(
			CPContentContributorConstants.AVAILABILITY_NAME);
	}

	@Override
	public List<CPDefinitionSpecificationOptionValue>
			getCategorizedCPDefinitionSpecificationOptionValues(
				long cpDefinitionId, long cpOptionCategoryId)
		throws PortalException {

		return _cpCatalogEntrySpecificationOptionValueLocalService.
			getCPDefinitionSpecificationOptionValues(
				cpDefinitionId, cpOptionCategoryId);
	}

	@Override
	public List<CPMedia> getCPAttachmentFileEntries(
			long cpDefinitionId, ThemeDisplay themeDisplay)
		throws PortalException {

		List<CPMedia> cpMedias = new ArrayList<>();

		List<CPAttachmentFileEntry> cpAttachmentFileEntries =
			_cpAttachmentFileEntryLocalService.getCPAttachmentFileEntries(
				_portal.getClassNameId(CPDefinition.class), cpDefinitionId,
				CPAttachmentFileEntryConstants.TYPE_OTHER,
				WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		for (CPAttachmentFileEntry cpAttachmentFileEntry :
				cpAttachmentFileEntries) {

			cpMedias.add(new CPMediaImpl(cpAttachmentFileEntry, themeDisplay));
		}

		return cpMedias;
	}

	@Override
	public CPCatalogEntry getCPCatalogEntry(
			HttpServletRequest httpServletRequest)
		throws PortalException {

		CPCatalogEntry cpCatalogEntry =
			(CPCatalogEntry)httpServletRequest.getAttribute(
				CPWebKeys.CP_CATALOG_ENTRY);

		CommerceContext commerceContext =
			(CommerceContext)httpServletRequest.getAttribute(
				CommerceWebKeys.COMMERCE_CONTEXT);

		CommerceAccount commerceAccount = commerceContext.getCommerceAccount();

		long commerceAccountId = 0;

		if (commerceAccount != null) {
			commerceAccountId = commerceAccount.getCommerceAccountId();
		}

		if (cpCatalogEntry == null) {
			long productId = ParamUtil.getLong(httpServletRequest, "productId");

			try {
				CProduct cProduct = _cProductLocalService.fetchCProduct(
					productId);

				if (cProduct == null) {
					return null;
				}

				cpCatalogEntry = _cpDefinitionHelper.getCPCatalogEntry(
					commerceAccountId,
					commerceContext.getCommerceChannelGroupId(),
					cProduct.getPublishedCPDefinitionId(),
					_portal.getLocale(httpServletRequest));
			}
			catch (PortalException portalException) {
				_log.error(portalException, portalException);
			}
		}

		return cpCatalogEntry;
	}

	@Override
	public Object getCPContentContributorValue(
			String contributorKey, HttpServletRequest httpServletRequest)
		throws Exception {

		CPContentContributor cpContentContributor =
			_cpContentContributorRegistry.getCPContentContributor(
				contributorKey);

		if (cpContentContributor == null) {
			return null;
		}

		return cpContentContributor.getValue(
			getDefaultCPInstance(httpServletRequest), httpServletRequest);
	}

	@Override
	public String getCPContentRendererKey(
		String type, RenderRequest renderRequest) {

		PortletPreferences portletPreferences = renderRequest.getPreferences();

		String value = portletPreferences.getValue(
			type + "--cpTypeRendererKey", null);

		if (Validator.isNotNull(value)) {
			return value;
		}

		List<CPContentRenderer> cpContentRenderers = getCPContentRenderers(
			type);

		if (cpContentRenderers.isEmpty()) {
			return StringPool.BLANK;
		}

		CPContentRenderer cpContentRenderer = cpContentRenderers.get(0);

		if (cpContentRenderer == null) {
			return StringPool.BLANK;
		}

		return cpContentRenderer.getKey();
	}

	@Override
	public List<CPContentRenderer> getCPContentRenderers(String cpType) {
		return _cpContentRendererRegistry.getCPContentRenderers(cpType);
	}

	@Override
	public List<CPDefinitionSpecificationOptionValue>
			getCPDefinitionSpecificationOptionValues(long cpDefinitionId)
		throws PortalException {

		return _cpCatalogEntrySpecificationOptionValueLocalService.
			getCPDefinitionSpecificationOptionValues(
				cpDefinitionId,
				CPOptionCategoryConstants.DEFAULT_CP_OPTION_CATEGORY_ID);
	}

	@Override
	public List<CPOptionCategory> getCPOptionCategories(long companyId) {
		return _cpOptionCategoryLocalService.getCPOptionCategories(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	@Override
	public List<CPType> getCPTypes() {
		return _cpTypeServicesTracker.getCPTypes();
	}

	@Override
	public CPInstance getDefaultCPInstance(
			HttpServletRequest httpServletRequest)
		throws Exception {

		CPCatalogEntry cpCatalogEntry = getCPCatalogEntry(httpServletRequest);

		if (cpCatalogEntry == null) {
			return null;
		}

		if (!cpCatalogEntry.isIgnoreSKUCombinations()) {
			return null;
		}

		return _cpInstanceHelper.getDefaultCPInstance(
			cpCatalogEntry.getCPDefinitionId());
	}

	@Override
	public CPSku getDefaultCPSku(CPCatalogEntry cpCatalogEntry)
		throws Exception {

		return _cpInstanceHelper.getDefaultCPSku(cpCatalogEntry);
	}

	@Override
	public String getDownloadFileEntryURL(
			FileEntry fileEntry, ThemeDisplay themeDisplay)
		throws PortalException {

		CPMedia cpMedia = new CPMediaImpl(fileEntry, themeDisplay);

		return cpMedia.getDownloadUrl();
	}

	@Override
	public String getFriendlyURL(
			CPCatalogEntry cpCatalogEntry, ThemeDisplay themeDisplay)
		throws PortalException {

		return _cpDefinitionHelper.getFriendlyURL(
			cpCatalogEntry.getCPDefinitionId(), themeDisplay);
	}

	@Override
	public List<CPMedia> getImages(
			long cpDefinitionId, ThemeDisplay themeDisplay)
		throws PortalException {

		List<CPMedia> cpMedias = new ArrayList<>();

		List<CPAttachmentFileEntry> cpAttachmentFileEntries =
			_cpAttachmentFileEntryLocalService.getCPAttachmentFileEntries(
				_portal.getClassNameId(CPDefinition.class), cpDefinitionId,
				CPAttachmentFileEntryConstants.TYPE_IMAGE,
				WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		for (CPAttachmentFileEntry cpAttachmentFileEntry :
				cpAttachmentFileEntries) {

			cpMedias.add(new CPMediaImpl(cpAttachmentFileEntry, themeDisplay));
		}

		if (cpMedias.isEmpty()) {
			CPDefinition cpDefinition =
				_cpDefinitionLocalService.getCPDefinition(cpDefinitionId);

			FileEntry fileEntry = FileEntryUtil.fetchByPrimaryKey(
				_catalogCommerceMediaDefaultImage.getDefaultCatalogFileEntryId(
					cpDefinition.getGroupId()));

			if (fileEntry != null) {
				cpMedias.add(new CPMediaImpl(fileEntry, themeDisplay));
			}
			else {
				cpMedias.add(new CPMediaImpl(themeDisplay.getCompanyGroupId()));
			}
		}

		return cpMedias;
	}

	@Override
	public String getImageURL(FileEntry fileEntry, ThemeDisplay themeDisplay)
		throws Exception {

		CPMedia cpMedia = new CPMediaImpl(fileEntry, themeDisplay);

		return cpMedia.getUrl();
	}

	@Override
	public String getStockQuantity(HttpServletRequest httpServletRequest)
		throws Exception {

		JSONObject stockQuantityJSONObject =
			(JSONObject)getCPContentContributorValue(
				CPContentContributorConstants.STOCK_QUANTITY_NAME,
				httpServletRequest);

		if (stockQuantityJSONObject == null) {
			return StringPool.BLANK;
		}

		return stockQuantityJSONObject.getString(
			CPContentContributorConstants.STOCK_QUANTITY_NAME);
	}

	@Override
	public String getStockQuantityLabel(HttpServletRequest httpServletRequest)
		throws Exception {

		JSONObject stockQuantityJSONObject =
			(JSONObject)getCPContentContributorValue(
				CPContentContributorConstants.STOCK_QUANTITY_NAME,
				httpServletRequest);

		if (stockQuantityJSONObject == null) {
			return StringPool.BLANK;
		}

		return LanguageUtil.format(
			httpServletRequest, "stock-quantity-x",
			stockQuantityJSONObject.getString(
				CPContentContributorConstants.STOCK_QUANTITY_NAME));
	}

	@Override
	public String getSubscriptionInfoLabel(
			HttpServletRequest httpServletRequest)
		throws Exception {

		JSONObject subscriptionInfoJSONObject =
			(JSONObject)getCPContentContributorValue(
				CPContentContributorConstants.SUBSCRIPTION_INFO,
				httpServletRequest);

		if (subscriptionInfoJSONObject == null) {
			return StringPool.BLANK;
		}

		return subscriptionInfoJSONObject.getString(
			CPContentContributorConstants.SUBSCRIPTION_INFO);
	}

	@Override
	public ResourceURL getViewAttachmentURL(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws PortalException {

		ResourceURL resourceURL = liferayPortletResponse.createResourceURL();

		CPCatalogEntry cpCatalogEntry = getCPCatalogEntry(
			_portal.getHttpServletRequest(liferayPortletRequest));

		if (cpCatalogEntry != null) {
			resourceURL.setParameter(
				"cpDefinitionId",
				String.valueOf(cpCatalogEntry.getCPDefinitionId()));
		}

		resourceURL.setResourceID("/cp_content_web/view_cp_attachments");

		return resourceURL;
	}

	@Override
	public boolean hasChildCPDefinitions(long cpDefinitionId) {
		return _cpDefinitionLocalService.hasChildCPDefinitions(cpDefinitionId);
	}

	@Override
	public boolean hasCPDefinitionSpecificationOptionValues(long cpDefinitionId)
		throws PortalException {

		List<CPDefinitionSpecificationOptionValue>
			cpDefinitionSpecificationOptionValues =
				_cpCatalogEntrySpecificationOptionValueLocalService.
					getCPDefinitionSpecificationOptionValues(
						cpDefinitionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
						null);

		return !cpDefinitionSpecificationOptionValues.isEmpty();
	}

	@Override
	public boolean isInWishList(
			CPSku cpSku, CPCatalogEntry cpCatalogEntry,
			ThemeDisplay themeDisplay)
		throws Exception {

		CommerceWishList commerceWishList =
			_commerceWishListService.getDefaultCommerceWishList(
				themeDisplay.getScopeGroupId(), themeDisplay.getUserId());

		if (commerceWishList != null) {
			long commerceWishListId = commerceWishList.getCommerceWishListId();

			if (cpSku != null) {
				int itemByContainsCPInstanceCount =
					_commerceWishListItemService.
						getCommerceWishListItemByContainsCPInstanceCount(
							commerceWishListId, cpSku.getCPInstanceUuid());

				if (itemByContainsCPInstanceCount > 0) {
					return true;
				}

				return false;
			}

			int itemByContainsCProductCount =
				_commerceWishListItemService.
					getCommerceWishListItemByContainsCProductCount(
						commerceWishListId, cpCatalogEntry.getCProductId());

			if (itemByContainsCProductCount > 0) {
				return true;
			}

			return false;
		}

		return false;
	}

	@Override
	public void renderCPType(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		CPCatalogEntry cpCatalogEntry = getCPCatalogEntry(httpServletRequest);

		if (cpCatalogEntry == null) {
			return;
		}

		RenderRequest renderRequest =
			(RenderRequest)httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST);

		CPContentRenderer cpContentRenderer =
			_cpContentRendererRegistry.getCPContentRenderer(
				getCPContentRendererKey(
					cpCatalogEntry.getProductTypeName(), renderRequest));

		if (cpContentRenderer == null) {
			return;
		}

		cpContentRenderer.render(
			cpCatalogEntry, httpServletRequest, httpServletResponse);
	}

	@Override
	public String renderOptions(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortalException {

		CPCatalogEntry cpCatalogEntry = getCPCatalogEntry(
			_portal.getHttpServletRequest(renderRequest));

		if (cpCatalogEntry == null) {
			return StringPool.BLANK;
		}

		return _ddmHelper.renderPublicStoreOptions(
			cpCatalogEntry.getCPDefinitionId(), null,
			cpCatalogEntry.isIgnoreSKUCombinations(), renderRequest,
			renderResponse,
			_filterByInventoryAvailability(
				_cpInstanceHelper.getCPDefinitionOptionRelsMap(
					cpCatalogEntry.getCPDefinitionId(), false, true)));
	}

	private Map<CPDefinitionOptionRel, List<CPDefinitionOptionValueRel>>
		_filterByInventoryAvailability(
			Map<CPDefinitionOptionRel, List<CPDefinitionOptionValueRel>>
				cpDefinitionOptionRelsMap) {

		for (Map.Entry<CPDefinitionOptionRel, List<CPDefinitionOptionValueRel>>
				cpDefinitionOptionRelEntry :
					cpDefinitionOptionRelsMap.entrySet()) {

			CPDefinitionOptionRel cpDefinitionOptionRel =
				cpDefinitionOptionRelEntry.getKey();

			if (cpDefinitionOptionRel.isPriceContributor()) {
				cpDefinitionOptionRelEntry.setValue(
					_commerceInventoryChecker.filterByAvailability(
						cpDefinitionOptionRelEntry.getValue()));

				continue;
			}

			if (!cpDefinitionOptionRel.isSkuContributor()) {
				cpDefinitionOptionRelEntry.setValue(
					cpDefinitionOptionRelEntry.getValue());

				continue;
			}

			cpDefinitionOptionRelEntry.setValue(
				_cpDefinitionOptionValueRelLocalService.
					filterByCPInstanceOptionValueRels(
						cpDefinitionOptionRelEntry.getValue(),
						_cpInstanceOptionValueRelCommerceInventoryChecker.
							filterByAvailability(
								_cpInstanceOptionValueRelLocalService.
									getCPDefinitionOptionRelCPInstanceOptionValueRels(
										cpDefinitionOptionRel.
											getCPDefinitionOptionRelId()))));
		}

		return cpDefinitionOptionRelsMap;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CPContentHelperImpl.class);

	@Reference
	private CommerceCatalogDefaultImage _catalogCommerceMediaDefaultImage;

	@Reference(
		target = "(commerce.inventory.checker.target=CPDefinitionOptionValueRel)"
	)
	private CommerceInventoryChecker<CPDefinitionOptionValueRel>
		_commerceInventoryChecker;

	@Reference
	private CommerceMediaResolver _commerceMediaResolver;

	@Reference
	private CommerceWishListItemService _commerceWishListItemService;

	@Reference
	private CommerceWishListService _commerceWishListService;

	@Reference
	private CPAttachmentFileEntryLocalService
		_cpAttachmentFileEntryLocalService;

	@Reference
	private CPDefinitionSpecificationOptionValueLocalService
		_cpCatalogEntrySpecificationOptionValueLocalService;

	@Reference
	private CPContentContributorRegistry _cpContentContributorRegistry;

	@Reference
	private CPContentRendererRegistry _cpContentRendererRegistry;

	@Reference
	private CPDefinitionHelper _cpDefinitionHelper;

	@Reference
	private CPDefinitionLocalService _cpDefinitionLocalService;

	@Reference
	private CPDefinitionOptionValueRelLocalService
		_cpDefinitionOptionValueRelLocalService;

	@Reference
	private CPInstanceHelper _cpInstanceHelper;

	@Reference(
		target = "(commerce.inventory.checker.target=CPInstanceOptionValueRel)"
	)
	private CommerceInventoryChecker<CPInstanceOptionValueRel>
		_cpInstanceOptionValueRelCommerceInventoryChecker;

	@Reference
	private CPInstanceOptionValueRelLocalService
		_cpInstanceOptionValueRelLocalService;

	@Reference
	private CPOptionCategoryLocalService _cpOptionCategoryLocalService;

	@Reference
	private CProductLocalService _cProductLocalService;

	@Reference
	private CPTypeServicesTracker _cpTypeServicesTracker;

	@Reference
	private DDMHelper _ddmHelper;

	@Reference
	private Portal _portal;

}