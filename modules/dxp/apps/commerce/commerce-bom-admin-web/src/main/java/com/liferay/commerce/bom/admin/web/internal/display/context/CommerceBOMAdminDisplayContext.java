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

package com.liferay.commerce.bom.admin.web.internal.display.context;

import com.liferay.commerce.application.item.selector.criterion.CommerceApplicationModelItemSelectorCriterion;
import com.liferay.commerce.bom.admin.web.internal.display.context.util.CommerceBOMAdminRequestHelper;
import com.liferay.commerce.bom.constants.CommerceBOMFolderConstants;
import com.liferay.commerce.bom.model.CommerceBOMDefinition;
import com.liferay.commerce.bom.model.CommerceBOMFolder;
import com.liferay.commerce.bom.model.CommerceBOMFolderApplicationRel;
import com.liferay.commerce.bom.search.CommerceBOMSearcher;
import com.liferay.commerce.bom.service.CommerceBOMDefinitionService;
import com.liferay.commerce.bom.service.CommerceBOMFolderApplicationRelService;
import com.liferay.commerce.bom.service.CommerceBOMFolderService;
import com.liferay.commerce.product.configuration.AttachmentsConfiguration;
import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.item.selector.criteria.image.criterion.ImageItemSelectorCriterion;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.users.admin.configuration.UserFileUploadsConfiguration;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceBOMAdminDisplayContext {

	public CommerceBOMAdminDisplayContext(
		AttachmentsConfiguration attachmentsConfiguration,
		ModelResourcePermission<CommerceBOMDefinition>
			commerceBOMDefinitionModelResourcePermission,
		CommerceBOMDefinitionService commerceBOMDefinitionService,
		CommerceBOMFolderApplicationRelService
			commerceBOMFolderApplicationRelService,
		ModelResourcePermission<CommerceBOMFolder>
			commerceBOMFolderModelResourcePermission,
		CommerceBOMFolderService commerceBOMFolderService,
		HttpServletRequest httpServletRequest, ItemSelector itemSelector,
		UserFileUploadsConfiguration userFileUploadsConfiguration) {

		_attachmentsConfiguration = attachmentsConfiguration;
		_commerceBOMDefinitionModelResourcePermission =
			commerceBOMDefinitionModelResourcePermission;
		_commerceBOMDefinitionService = commerceBOMDefinitionService;
		_commerceBOMFolderApplicationRelService =
			commerceBOMFolderApplicationRelService;
		_commerceBOMFolderModelResourcePermission =
			commerceBOMFolderModelResourcePermission;
		_commerceBOMFolderService = commerceBOMFolderService;
		_itemSelector = itemSelector;
		_userFileUploadsConfiguration = userFileUploadsConfiguration;

		_commerceBOMAdminRequestHelper = new CommerceBOMAdminRequestHelper(
			httpServletRequest);
	}

	public void addPortletBreadcrumbEntries() throws Exception {
		ThemeDisplay themeDisplay =
			_commerceBOMAdminRequestHelper.getThemeDisplay();

		PortletURL portletURL = getPortletURL();

		portletURL.setParameter(
			"commerceBOMFolderId",
			String.valueOf(
				CommerceBOMFolderConstants.DEFAULT_COMMERCE_BOM_FOLDER_ID));

		PortalUtil.addPortletBreadcrumbEntry(
			_commerceBOMAdminRequestHelper.getRequest(),
			themeDisplay.translate("home"), portletURL.toString(),
			HashMapBuilder.<String, Object>put(
				"commerce-bom-folder-id",
				CommerceBOMFolderConstants.DEFAULT_COMMERCE_BOM_FOLDER_ID
			).put(
				"direction-right", Boolean.TRUE.toString()
			).build());

		CommerceBOMFolder commerceBOMFolder = getCommerceBOMFolder();

		if (commerceBOMFolder == null) {
			return;
		}

		List<CommerceBOMFolder> ancestorCommerceBOMFolders =
			commerceBOMFolder.getAncestors();

		Collections.reverse(ancestorCommerceBOMFolders);

		for (CommerceBOMFolder ancestorCommerceBOMFolder :
				ancestorCommerceBOMFolders) {

			portletURL.setParameter(
				"commerceBOMFolderId",
				String.valueOf(
					ancestorCommerceBOMFolder.getCommerceBOMFolderId()));

			PortalUtil.addPortletBreadcrumbEntry(
				_commerceBOMAdminRequestHelper.getRequest(),
				ancestorCommerceBOMFolder.getName(), portletURL.toString(),
				HashMapBuilder.<String, Object>put(
					"commerce-bom-folder-id",
					ancestorCommerceBOMFolder.getCommerceBOMFolderId()
				).put(
					"direction-right", Boolean.TRUE.toString()
				).build());
		}

		portletURL.setParameter(
			"commerceBOMFolderId",
			String.valueOf(commerceBOMFolder.getCommerceBOMFolderId()));

		if (commerceBOMFolder.getCommerceBOMFolderId() !=
				CommerceBOMFolderConstants.DEFAULT_COMMERCE_BOM_FOLDER_ID) {

			CommerceBOMFolder unescapedCommerceBOMFolder =
				commerceBOMFolder.toUnescapedModel();

			PortalUtil.addPortletBreadcrumbEntry(
				_commerceBOMAdminRequestHelper.getRequest(),
				unescapedCommerceBOMFolder.getName(), portletURL.toString(),
				HashMapBuilder.<String, Object>put(
					"commerce-bom-folder-id",
					commerceBOMFolder.getCommerceBOMFolderId()
				).put(
					"direction-right", Boolean.TRUE.toString()
				).build());
		}
	}

	public String getCommerceApplicationModelItemSelectorUrl()
		throws PortalException {

		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			RequestBackedPortletURLFactoryUtil.create(
				_commerceBOMAdminRequestHelper.getRequest());

		CommerceApplicationModelItemSelectorCriterion
			commerceApplicationModelItemSelectorCriterion =
				new CommerceApplicationModelItemSelectorCriterion();

		commerceApplicationModelItemSelectorCriterion.
			setDesiredItemSelectorReturnTypes(
				Collections.<ItemSelectorReturnType>singletonList(
					new UUIDItemSelectorReturnType()));

		PortletURL itemSelectorURL = _itemSelector.getItemSelectorURL(
			requestBackedPortletURLFactory,
			"commerceApplicationModelsSelectItem",
			commerceApplicationModelItemSelectorCriterion);

		String checkedCommerceApplicationModelIds = StringUtil.merge(
			getCheckedCommerceApplicationModelIds());

		itemSelectorURL.setParameter(
			"checkedCommerceApplicationModelIds",
			checkedCommerceApplicationModelIds);

		return itemSelectorURL.toString();
	}

	public CommerceBOMDefinition getCommerceBOMDefinition()
		throws PortalException {

		long commerceBOMDefinitionId = ParamUtil.getLong(
			_commerceBOMAdminRequestHelper.getRequest(),
			"commerceBOMDefinitionId");

		if (commerceBOMDefinitionId > 0) {
			return _commerceBOMDefinitionService.getCommerceBOMDefinition(
				commerceBOMDefinitionId);
		}

		return null;
	}

	public long getCommerceBOMDefinitionId() throws PortalException {
		CommerceBOMDefinition commerceBOMDefinition =
			getCommerceBOMDefinition();

		if (commerceBOMDefinition == null) {
			return 0;
		}

		return commerceBOMDefinition.getCommerceBOMDefinitionId();
	}

	public CommerceBOMFolder getCommerceBOMFolder() throws PortalException {
		long commerceBOMFolderId = ParamUtil.getLong(
			_commerceBOMAdminRequestHelper.getRequest(), "commerceBOMFolderId");

		if (commerceBOMFolderId > 0) {
			return _commerceBOMFolderService.getCommerceBOMFolder(
				commerceBOMFolderId);
		}

		return null;
	}

	public List<CommerceBOMFolderApplicationRel>
			getCommerceBOMFolderApplicationRels(int start, int end)
		throws PortalException {

		if (getCommerceBOMFolderId() > 0) {
			return _commerceBOMFolderApplicationRelService.
				getCommerceBOMFolderApplicationRelsByCommerceBOMFolderId(
					getCommerceBOMFolderId(), start, end);
		}

		return Collections.emptyList();
	}

	public SearchContainer<CommerceBOMFolderApplicationRel>
			getCommerceBOMFolderApplicationRelSearchContainer()
		throws PortalException {

		if (_commerceBOMFolderApplicationRelSearchContainer != null) {
			return _commerceBOMFolderApplicationRelSearchContainer;
		}

		_commerceBOMFolderApplicationRelSearchContainer = new SearchContainer<>(
			_commerceBOMAdminRequestHelper.getLiferayPortletRequest(),
			getPortletURL(), null, null);

		_commerceBOMFolderApplicationRelSearchContainer.setEmptyResultsMessage(
			"no-models-were-found");

		_commerceBOMFolderApplicationRelSearchContainer.setOrderByCol(
			getOrderByCol());
		_commerceBOMFolderApplicationRelSearchContainer.setOrderByType(
			getOrderByType());

		int total =
			_commerceBOMFolderApplicationRelService.
				getCommerceBOMFolderApplicationRelsCountByCommerceBOMFolderId(
					getCommerceBOMFolderId());

		_commerceBOMFolderApplicationRelSearchContainer.setTotal(total);

		List<CommerceBOMFolderApplicationRel> results =
			getCommerceBOMFolderApplicationRels(
				_commerceBOMFolderApplicationRelSearchContainer.getStart(),
				_commerceBOMFolderApplicationRelSearchContainer.getEnd());

		_commerceBOMFolderApplicationRelSearchContainer.setResults(results);

		return _commerceBOMFolderApplicationRelSearchContainer;
	}

	public long getCommerceBOMFolderId() throws PortalException {
		CommerceBOMFolder commerceBOMFolder = getCommerceBOMFolder();

		if (commerceBOMFolder == null) {
			return CommerceBOMFolderConstants.DEFAULT_COMMERCE_BOM_FOLDER_ID;
		}

		return commerceBOMFolder.getCommerceBOMFolderId();
	}

	public CPAttachmentFileEntry getCPAttachmentFileEntry()
		throws PortalException {

		CommerceBOMDefinition commerceBOMDefinition =
			getCommerceBOMDefinition();

		if (commerceBOMDefinition == null) {
			return null;
		}

		return commerceBOMDefinition.fetchCPAttachmentFileEntry();
	}

	public String[] getImageExtensions() {
		return _attachmentsConfiguration.imageExtensions();
	}

	public long getImageMaxSize() {
		return _attachmentsConfiguration.imageMaxSize();
	}

	public String getItemSelectorUrl() {
		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			RequestBackedPortletURLFactoryUtil.create(
				_commerceBOMAdminRequestHelper.getRequest());

		ImageItemSelectorCriterion imageItemSelectorCriterion =
			new ImageItemSelectorCriterion();

		imageItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			Collections.<ItemSelectorReturnType>singletonList(
				new FileEntryItemSelectorReturnType()));

		PortletURL itemSelectorURL = _itemSelector.getItemSelectorURL(
			requestBackedPortletURLFactory, "addCPAttachmentFileEntry",
			imageItemSelectorCriterion);

		return itemSelectorURL.toString();
	}

	public String getKeywords() {
		if (Validator.isNotNull(_keywords)) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(
			_commerceBOMAdminRequestHelper.getRequest(), "keywords");

		return _keywords;
	}

	public String getOrderByCol() {
		return ParamUtil.getString(
			_commerceBOMAdminRequestHelper.getRequest(),
			SearchContainer.DEFAULT_ORDER_BY_COL_PARAM, "name");
	}

	public String getOrderByType() {
		return ParamUtil.getString(
			_commerceBOMAdminRequestHelper.getRequest(),
			SearchContainer.DEFAULT_ORDER_BY_TYPE_PARAM, "asc");
	}

	public PortletURL getPortletURL() throws PortalException {
		LiferayPortletResponse liferayPortletResponse =
			_commerceBOMAdminRequestHelper.getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		HttpServletRequest httpServletRequest =
			PortalUtil.getOriginalServletRequest(
				_commerceBOMAdminRequestHelper.getRequest());

		String backURL = ParamUtil.getString(httpServletRequest, "backURL");

		if (Validator.isNotNull(backURL)) {
			portletURL.setParameter("backURL", backURL);
		}

		String redirect = ParamUtil.getString(
			_commerceBOMAdminRequestHelper.getRequest(), "redirect");

		if (Validator.isNotNull(redirect)) {
			portletURL.setParameter("redirect", redirect);
		}

		String delta = ParamUtil.getString(
			_commerceBOMAdminRequestHelper.getRequest(), "delta");

		if (Validator.isNotNull(delta)) {
			portletURL.setParameter("delta", delta);
		}

		String deltaEntry = ParamUtil.getString(
			_commerceBOMAdminRequestHelper.getRequest(), "deltaEntry");

		if (Validator.isNotNull(deltaEntry)) {
			portletURL.setParameter("deltaEntry", deltaEntry);
		}

		String keywords = getKeywords();

		if (Validator.isNotNull(keywords)) {
			portletURL.setParameter("keywords", keywords);
		}

		portletURL.setParameter(
			"commerceBOMFolderId", String.valueOf(getCommerceBOMFolderId()));

		return portletURL;
	}

	public SearchContainer<Object> getSearchContainer() throws PortalException {
		if (_searchContainer != null) {
			return _searchContainer;
		}

		_searchContainer = new SearchContainer<>(
			_commerceBOMAdminRequestHelper.getLiferayPortletRequest(),
			getPortletURL(), null, null);

		_searchContainer.setEmptyResultsMessage("no-results-were-found");

		_searchContainer.setOrderByCol(getOrderByCol());
		_searchContainer.setOrderByType(getOrderByType());

		boolean orderByAsc = false;

		if (Objects.equals(getOrderByType(), "asc")) {
			orderByAsc = true;
		}

		Sort sort = new Sort(Field.NAME, Sort.STRING_TYPE, !orderByAsc);

		SearchContext searchContext = buildSearchContext(
			_commerceBOMAdminRequestHelper.getCompanyId(), getKeywords(),
			_searchContainer.getStart(), _searchContainer.getEnd(), sort);

		Indexer<?> indexer = CommerceBOMSearcher.getInstance();

		Hits hits = indexer.search(searchContext);

		_searchContainer.setTotal(hits.getLength());

		List<Object> results = new ArrayList<>();

		Document[] documents = hits.getDocs();

		for (Document document : documents) {
			String className = document.get(Field.ENTRY_CLASS_NAME);
			long classPK = GetterUtil.getLong(
				document.get(Field.ENTRY_CLASS_PK));

			if (className.equals(CommerceBOMDefinition.class.getName())) {
				results.add(
					_commerceBOMDefinitionService.getCommerceBOMDefinition(
						classPK));
			}
			else if (className.equals(CommerceBOMFolder.class.getName())) {
				results.add(
					_commerceBOMFolderService.getCommerceBOMFolder(classPK));
			}
		}

		_searchContainer.setResults(results);

		return _searchContainer;
	}

	public UserFileUploadsConfiguration getUserFileUploadsConfiguration() {
		return _userFileUploadsConfiguration;
	}

	public boolean hasCommerceBOMDefinitionPermissions(
			long commerceBOMDefinitionId, String actionId)
		throws PortalException {

		return _commerceBOMDefinitionModelResourcePermission.contains(
			_commerceBOMAdminRequestHelper.getPermissionChecker(),
			commerceBOMDefinitionId, actionId);
	}

	public boolean hasCommerceBOMFolderPermissions(
			long commerceBOMFolderId, String actionId)
		throws PortalException {

		return _commerceBOMFolderModelResourcePermission.contains(
			_commerceBOMAdminRequestHelper.getPermissionChecker(),
			commerceBOMFolderId, actionId);
	}

	public boolean hasPermissions(String actionId) {
		PortletResourcePermission portletResourcePermission =
			_commerceBOMFolderModelResourcePermission.
				getPortletResourcePermission();

		return portletResourcePermission.contains(
			_commerceBOMAdminRequestHelper.getPermissionChecker(), null,
			actionId);
	}

	protected SearchContext buildSearchContext(
			long companyId, String keywords, int start, int end, Sort sort)
		throws PortalException {

		LinkedHashMap<String, Object> params = new LinkedHashMap<>();

		if (params != null) {
			params.put("keywords", keywords);
		}

		SearchContext searchContext = new SearchContext();

		searchContext.setAttributes(
			HashMapBuilder.<String, Serializable>put(
				Field.NAME, keywords
			).put(
				"commerceBOMFolderId", getCommerceBOMFolderId()
			).put(
				"params", params
			).put(
				"parentCommerceBOMFolderId", getCommerceBOMFolderId()
			).build());

		searchContext.setCompanyId(companyId);
		searchContext.setEnd(end);
		searchContext.setStart(start);

		searchContext.setKeywords(keywords);

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		if (sort != null) {
			searchContext.setSorts(sort);
		}

		return searchContext;
	}

	protected long[] getCheckedCommerceApplicationModelIds()
		throws PortalException {

		List<Long> commerceApplicationModelIdsList = new ArrayList<>();

		List<CommerceBOMFolderApplicationRel> commerceBOMFolderApplicationRels =
			getCommerceBOMFolderApplicationRels(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (CommerceBOMFolderApplicationRel commerceBOMFolderApplicationRel :
				commerceBOMFolderApplicationRels) {

			commerceApplicationModelIdsList.add(
				commerceBOMFolderApplicationRel.
					getCommerceApplicationModelId());
		}

		if (commerceApplicationModelIdsList.isEmpty()) {
			return new long[0];
		}

		Stream<Long> stream = commerceApplicationModelIdsList.stream();

		LongStream longStream = stream.mapToLong(l -> l);

		return longStream.toArray();
	}

	private final AttachmentsConfiguration _attachmentsConfiguration;
	private final CommerceBOMAdminRequestHelper _commerceBOMAdminRequestHelper;
	private final ModelResourcePermission<CommerceBOMDefinition>
		_commerceBOMDefinitionModelResourcePermission;
	private final CommerceBOMDefinitionService _commerceBOMDefinitionService;
	private SearchContainer<CommerceBOMFolderApplicationRel>
		_commerceBOMFolderApplicationRelSearchContainer;
	private final CommerceBOMFolderApplicationRelService
		_commerceBOMFolderApplicationRelService;
	private final ModelResourcePermission<CommerceBOMFolder>
		_commerceBOMFolderModelResourcePermission;
	private final CommerceBOMFolderService _commerceBOMFolderService;
	private final ItemSelector _itemSelector;
	private String _keywords;
	private SearchContainer<Object> _searchContainer;
	private final UserFileUploadsConfiguration _userFileUploadsConfiguration;

}