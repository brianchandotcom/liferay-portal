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

package com.liferay.commerce.product.internal.util;

import com.liferay.commerce.media.CommerceMediaResolverUtil;
import com.liferay.commerce.product.catalog.CPCatalogEntry;
import com.liferay.commerce.product.catalog.CPQuery;
import com.liferay.commerce.product.configuration.CPDisplayLayoutConfiguration;
import com.liferay.commerce.product.constants.CPConstants;
import com.liferay.commerce.product.constants.CPField;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.data.source.CPDataSourceResult;
import com.liferay.commerce.product.internal.catalog.DatabaseCPCatalogEntryImpl;
import com.liferay.commerce.product.internal.catalog.IndexCPCatalogEntryImpl;
import com.liferay.commerce.product.internal.search.CPDefinitionSearcher;
import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CProduct;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.permission.CommerceProductViewPermission;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.CPDefinitionOptionRelLocalService;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.product.service.CProductLocalService;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.product.url.CPFriendlyURL;
import com.liferay.commerce.product.util.CPDefinitionHelper;
import com.liferay.friendly.url.model.FriendlyURLEntry;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.SortFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 * @author Andrea Di Giorgi
 * @author Alessio Antonio Rendina
 */
@Component(service = CPDefinitionHelper.class)
public class CPDefinitionHelperImpl implements CPDefinitionHelper {

	@Override
	public CPCatalogEntry getCPCatalogEntry(Document document, Locale locale) {
		return new IndexCPCatalogEntryImpl(
			document, _cpDefinitionLocalService,
			_cpDefinitionOptionRelLocalService, _cpInstanceLocalService,
			locale);
	}

	@Override
	public CPCatalogEntry getCPCatalogEntry(
			long commerceAccountId, long groupId, long cpDefinitionId,
			Locale locale)
		throws PortalException {

		_commerceProductViewPermission.check(
			PermissionThreadLocal.getPermissionChecker(), commerceAccountId,
			groupId, cpDefinitionId);

		CPDefinition cpDefinition = _cpDefinitionLocalService.getCPDefinition(
			cpDefinitionId);

		if (!cpDefinition.isApproved() || !cpDefinition.isPublished()) {
			return null;
		}

		return new DatabaseCPCatalogEntryImpl(
			cpDefinition, _cpDefinitionOptionRelLocalService,
			_cpInstanceLocalService, locale);
	}

	@Override
	public String getDefaultImageFileURL(
			long commerceAccountId, long cpDefinitionId)
		throws PortalException {

		CPAttachmentFileEntry cpAttachmentFileEntry =
			_cpDefinitionLocalService.getDefaultImageCPAttachmentFileEntry(
				cpDefinitionId);

		if (cpAttachmentFileEntry == null) {
			CPDefinition cpDefinition =
				_cpDefinitionLocalService.getCPDefinition(cpDefinitionId);

			return CommerceMediaResolverUtil.getDefaultURL(
				cpDefinition.getGroupId());
		}

		return CommerceMediaResolverUtil.getURL(
			commerceAccountId,
			cpAttachmentFileEntry.getCPAttachmentFileEntryId());
	}

	@Override
	public String getFriendlyURL(long cpDefinitionId, ThemeDisplay themeDisplay)
		throws PortalException {

		CPDefinition cpDefinition = _cpDefinitionLocalService.getCPDefinition(
			cpDefinitionId);

		return _getFriendlyURL(cpDefinition.getCProductId(), themeDisplay);
	}

	@Override
	public CPDataSourceResult search(
			long groupId, SearchContext searchContext, CPQuery cpQuery,
			int start, int end)
		throws PortalException {

		List<CPCatalogEntry> cpCatalogEntries = new ArrayList<>();

		CPDefinitionSearcher cpDefinitionSearcher = _getCPDefinitionSearcher(
			groupId, searchContext, cpQuery, start, end);

		Hits hits = cpDefinitionSearcher.search(searchContext);

		Document[] documents = hits.getDocs();

		for (Document document : documents) {
			cpCatalogEntries.add(
				getCPCatalogEntry(document, searchContext.getLocale()));
		}

		return new CPDataSourceResult(cpCatalogEntries, hits.getLength());
	}

	@Override
	public long searchCount(
			long groupId, SearchContext searchContext, CPQuery cpQuery)
		throws PortalException {

		CPDefinitionSearcher cpDefinitionSearcher = _getCPDefinitionSearcher(
			groupId, searchContext, cpQuery, 0, 0);

		return cpDefinitionSearcher.searchCount(searchContext);
	}

	@Override
	public List<CPDefinition> searchCPDefinitions(
			long groupId, SearchContext searchContext, CPQuery cpQuery,
			int start, int end)
		throws PortalException {

		List<CPDefinition> cpDefinitions = new ArrayList<>();

		CPDefinitionSearcher cpDefinitionSearcher = _getCPDefinitionSearcher(
			groupId, searchContext, cpQuery, start, end);

		Hits hits = cpDefinitionSearcher.search(searchContext);

		Document[] documents = hits.getDocs();

		for (Document document : documents) {
			CPDefinition cpDefinition =
				_cpDefinitionLocalService.fetchCPDefinition(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)));

			if (cpDefinition != null) {
				cpDefinitions.add(cpDefinition);
			}
		}

		return cpDefinitions;
	}

	private long _checkChannelGroupId(long groupId) {
		Group group = _groupLocalService.fetchGroup(groupId);

		String className = group.getClassName();

		if (className.equals(CommerceChannel.class.getName())) {
			return groupId;
		}

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.fetchCommerceChannelBySiteGroupId(
				groupId);

		if (commerceChannel != null) {
			return commerceChannel.getGroupId();
		}

		return groupId;
	}

	private CPDefinitionSearcher _getCPDefinitionSearcher(
		long groupId, SearchContext searchContext, CPQuery cpQuery, int start,
		int end) {

		CPDefinitionSearcher cpDefinitionSearcher = new CPDefinitionSearcher(
			cpQuery);

		searchContext.setAttribute(CPField.PUBLISHED, Boolean.TRUE);
		searchContext.setAttribute(
			"commerceChannelGroupId", _checkChannelGroupId(groupId));
		searchContext.setAttribute("secure", Boolean.TRUE);
		searchContext.setEnd(end);
		searchContext.setSorts(_getSorts(cpQuery));
		searchContext.setStart(start);

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setScoreEnabled(false);

		return cpDefinitionSearcher;
	}

	private String _getFriendlyURL(long cProductId, ThemeDisplay themeDisplay)
		throws PortalException {

		FriendlyURLEntry friendlyURLEntry = null;

		try {
			friendlyURLEntry =
				_friendlyURLEntryLocalService.getMainFriendlyURLEntry(
					_portal.getClassNameId(CProduct.class), cProductId);
		}
		catch (Exception exception) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"No friendly URL entry found for " + cProductId, exception);
			}

			return StringPool.BLANK;
		}

		long groupId = themeDisplay.getScopeGroupId();

		CProduct cProduct = _cProductLocalService.getCProduct(cProductId);

		String layoutUuid = _cpDefinitionLocalService.getLayoutUuid(
			groupId, cProduct.getPublishedCPDefinitionId());

		Layout layout = _getLayout(groupId, layoutUuid);

		if (layout == null) {
			CommerceChannel commerceChannel =
				_commerceChannelLocalService.fetchCommerceChannelBySiteGroupId(
					groupId);

			CPDisplayLayoutConfiguration cpDisplayLayoutConfiguration =
				_configurationProvider.getConfiguration(
					CPDisplayLayoutConfiguration.class,
					new GroupServiceSettingsLocator(
						commerceChannel.getGroupId(),
						CPConstants.RESOURCE_NAME_CP_DISPLAY_LAYOUT));

			layout = _getLayout(
				groupId, cpDisplayLayoutConfiguration.productLayoutUuid());
		}

		if (layout == null) {
			long plid = _portal.getPlidFromPortletId(
				groupId, CPPortletKeys.CP_CONTENT_WEB);

			if (plid > 0) {
				layout = _layoutLocalService.getLayout(plid);
			}
		}

		if (layout == null) {
			layout = themeDisplay.getLayout();
		}

		String currentSiteURL = _portal.getGroupFriendlyURL(
			layout.getLayoutSet(), themeDisplay, false, false);

		String urlSeparator = _cpFriendlyURL.getProductURLSeparator(
			themeDisplay.getCompanyId());

		String productFriendlyURL =
			currentSiteURL + urlSeparator +
				friendlyURLEntry.getUrlTitle(themeDisplay.getLanguageId());

		return _portal.addPreservedParameters(themeDisplay, productFriendlyURL);
	}

	private Layout _getLayout(long groupId, String layoutUuid) {
		Layout layout = null;

		if (Validator.isNotNull(layoutUuid)) {
			try {
				layout = _layoutLocalService.getLayoutByUuidAndGroupId(
					layoutUuid, groupId, true);
			}
			catch (PortalException portalException) {
				if (_log.isDebugEnabled()) {
					_log.debug(portalException);
				}
			}

			if (layout == null) {
				try {
					layout = _layoutLocalService.getLayoutByUuidAndGroupId(
						layoutUuid, groupId, false);
				}
				catch (PortalException portalException) {
					if (_log.isDebugEnabled()) {
						_log.debug(portalException);
					}
				}
			}
		}

		return layout;
	}

	private String _getOrderByCol(String sortField) {
		if (sortField.equals("modifiedDate")) {
			sortField = Field.MODIFIED_DATE;
		}

		return sortField;
	}

	private Sort _getSort(String orderByType, String sortField) {
		return SortFactoryUtil.getSort(
			CPDefinition.class, _getSortType(sortField),
			_getOrderByCol(sortField), orderByType);
	}

	private Sort[] _getSorts(CPQuery cpQuery) {
		Sort sort1 = _getSort(
			cpQuery.getOrderByType1(), cpQuery.getOrderByCol1());
		Sort sort2 = _getSort(
			cpQuery.getOrderByType2(), cpQuery.getOrderByCol2());

		return new Sort[] {sort1, sort2};
	}

	private int _getSortType(String fieldType) {
		int sortType = Sort.STRING_TYPE;

		if (fieldType.equals(Field.CREATE_DATE) ||
			fieldType.equals(Field.EXPIRATION_DATE) ||
			fieldType.equals(Field.PUBLISH_DATE) ||
			fieldType.equals("modifiedDate")) {

			sortType = Sort.LONG_TYPE;
		}
		else if (fieldType.equals(Field.PRIORITY) ||
				 fieldType.equals(CPField.BASE_PRICE)) {

			sortType = Sort.DOUBLE_TYPE;
		}

		return sortType;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CPDefinitionHelperImpl.class);

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private CommerceProductViewPermission _commerceProductViewPermission;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private CPDefinitionLocalService _cpDefinitionLocalService;

	@Reference
	private CPDefinitionOptionRelLocalService
		_cpDefinitionOptionRelLocalService;

	@Reference
	private CPFriendlyURL _cpFriendlyURL;

	@Reference
	private CPInstanceLocalService _cpInstanceLocalService;

	@Reference
	private CProductLocalService _cProductLocalService;

	@Reference
	private FriendlyURLEntryLocalService _friendlyURLEntryLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

}