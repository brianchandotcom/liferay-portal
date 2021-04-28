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

package com.liferay.headless.commerce.bom.internal.resource.v1_0;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.service.AssetCategoryService;
import com.liferay.commerce.product.constants.CPAttachmentFileEntryConstants;
import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPAttachmentFileEntryService;
import com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSettings;
import com.liferay.commerce.shop.by.diagram.search.CPDefinitionDiagramSearcher;
import com.liferay.commerce.shop.by.diagram.service.CPDefinitionDiagramSettingsService;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.headless.commerce.bom.dto.v1_0.Brand;
import com.liferay.headless.commerce.bom.dto.v1_0.Folder;
import com.liferay.headless.commerce.bom.dto.v1_0.Item;
import com.liferay.headless.commerce.bom.dto.v1_0.ItemData;
import com.liferay.headless.commerce.bom.dto.v1_0.Product;
import com.liferay.headless.commerce.bom.dto.v1_0.Spot;
import com.liferay.headless.commerce.bom.internal.dto.v1_0.converter.BreadcrumbDTOConverter;
import com.liferay.headless.commerce.bom.internal.dto.v1_0.converter.util.BreadcrumbDTOConverterUtil;
import com.liferay.headless.commerce.bom.resource.v1_0.FolderResource;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	properties = "OSGI-INF/liferay/rest/v1_0/folder.properties",
	scope = ServiceScope.PROTOTYPE, service = FolderResource.class
)
public class FolderResourceImpl extends BaseFolderResourceImpl {

	@Override
	public Folder getFolder(Long id) throws Exception {
		AssetCategory assetCategory = null;

		if (id > 0) {
			assetCategory = _assetCategoryService.getCategory(
				GetterUtil.getLong(id));
		}

		Folder folder = new Folder();

		folder.setBreadcrumbs(
			BreadcrumbDTOConverterUtil.getBreadcrumbs(
				_breadcrumbDTOConverter, assetCategory,
				contextAcceptLanguage.getPreferredLocale()));

		ItemData itemData = new ItemData();

		itemData.setBrands(new Brand[0]);
		itemData.setItems(_getItems(id));
		itemData.setProducts(new Product[0]);
		itemData.setSpots(new Spot[0]);

		folder.setData(itemData);

		return folder;
	}

	protected SearchContext buildSearchContext(
		long companyId, long id, String keywords, int start, int end,
		Sort sort) {

		LinkedHashMap<String, Object> params = new LinkedHashMap<>();

		if (params != null) {
			params.put("keywords", keywords);
		}

		SearchContext searchContext = new SearchContext();

		searchContext.setAttributes(
			HashMapBuilder.<String, Serializable>put(
				Field.NAME, keywords
			).put(
				"categoryId", id
			).put(
				"params", params
			).put(
				"parentCategoryId", id
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

	private Item[] _getItems(long folderId) throws Exception {
		try {
			List<Item> itemList = new ArrayList<>();

			Indexer<?> indexer = CPDefinitionDiagramSearcher.getInstance();

			Hits hits = indexer.search(
				buildSearchContext(
					contextCompany.getCompanyId(), folderId, StringPool.BLANK,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null));

			for (Document document : hits.getDocs()) {
				String className = document.get(Field.ENTRY_CLASS_NAME);
				long classPK = GetterUtil.getLong(
					document.get(Field.ENTRY_CLASS_PK));

				if (className.equals(
						CPDefinitionDiagramSettings.class.getName())) {

					itemList.add(
						_toItem(
							_cpDefinitionDiagramSettingsService.
								getCPDefinitionDiagramSettings(classPK)));
				}
				else if (className.equals(AssetCategory.class.getName())) {
					itemList.add(
						_toItem(_assetCategoryService.getCategory(classPK)));
				}
			}

			Item[] items = new Item[itemList.size()];

			return itemList.toArray(items);
		}
		catch (PortalException portalException) {
			throw new Exception(portalException);
		}
	}

	private Item _toItem(AssetCategory assetCategory) throws PortalException {
		String thumbnail = StringPool.BLANK;

		List<CPAttachmentFileEntry> cpAttachmentFileEntries =
			_cpAttachmentFileEntryService.getCPAttachmentFileEntries(
				_portal.getClassNameId(AssetCategory.class),
				assetCategory.getCategoryId(),
				CPAttachmentFileEntryConstants.TYPE_IMAGE,
				WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		if (!cpAttachmentFileEntries.isEmpty()) {
			CPAttachmentFileEntry cpAttachmentFileEntry =
				cpAttachmentFileEntries.get(0);

			FileEntry fileEntry = cpAttachmentFileEntry.getFileEntry();

			thumbnail = DLUtil.getDownloadURL(
				fileEntry, fileEntry.getFileVersion(), null, null);
		}

		String thumbnailURL = thumbnail;

		return new Item() {
			{
				id = assetCategory.getCategoryId();
				name = assetCategory.getName();
				thumbnail = thumbnailURL;
				type = Item.Type.create("folder");
				url = "/folders/" + id;
			}
		};
	}

	private Item _toItem(
			CPDefinitionDiagramSettings cpDefinitionDiagramSettings)
		throws PortalException {

		String thumbnail = StringPool.BLANK;

		CPDefinition cpDefinition =
			cpDefinitionDiagramSettings.getCPDefinition();

		CPAttachmentFileEntry cpAttachmentFileEntry =
			cpDefinitionDiagramSettings.fetchCPAttachmentFileEntry();

		if (cpAttachmentFileEntry != null) {
			FileEntry fileEntry = cpAttachmentFileEntry.getFileEntry();

			thumbnail = DLUtil.getDownloadURL(
				fileEntry, fileEntry.getFileVersion(), null, null);
		}

		String thumbnailURL = thumbnail;

		return new Item() {
			{
				id =
					cpDefinitionDiagramSettings.
						getCPDefinitionDiagramSettingsId();
				name = cpDefinitionDiagramSettings.getName();
				slug = null;
				thumbnail = thumbnailURL;
				type = Item.Type.create("diagram");
				url = "/products/" + cpDefinition.getCProductId() + "/diagram";
			}
		};
	}

	@Reference
	private AssetCategoryService _assetCategoryService;

	@Reference
	private BreadcrumbDTOConverter _breadcrumbDTOConverter;

	@Reference
	private CPAttachmentFileEntryService _cpAttachmentFileEntryService;

	@Reference
	private CPDefinitionDiagramSettingsService
		_cpDefinitionDiagramSettingsService;

	@Reference
	private Portal _portal;

}