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

import com.liferay.commerce.bom.model.CommerceBOMDefinition;
import com.liferay.commerce.bom.model.CommerceBOMFolder;
import com.liferay.commerce.bom.search.CommerceBOMSearcher;
import com.liferay.commerce.bom.service.CommerceBOMDefinitionService;
import com.liferay.commerce.bom.service.CommerceBOMFolderService;
import com.liferay.commerce.product.model.CPAttachmentFileEntry;
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
import com.liferay.petra.string.StringBundler;
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
import com.liferay.portal.kernel.webserver.WebServerServletTokenUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/folder.properties",
	scope = ServiceScope.PROTOTYPE, service = FolderResource.class
)
public class FolderResourceImpl extends BaseFolderResourceImpl {

	@Override
	public Folder getFolder(Long id) throws Exception {
		CommerceBOMFolder commerceBOMFolder = null;

		if (id > 0) {
			commerceBOMFolder = _commerceBOMFolderService.getCommerceBOMFolder(
				GetterUtil.getLong(id));
		}

		Folder folder = new Folder();

		folder.setBreadcrumbs(
			BreadcrumbDTOConverterUtil.getBreadcrumbs(
				_breadcrumbDTOConverter, commerceBOMFolder,
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

		Map<String, Serializable> attributes = new HashMap<>();

		attributes.put(Field.NAME, keywords);
		attributes.put("commerceBOMFolderId", id);
		attributes.put("params", params);
		attributes.put("parentCommerceBOMFolderId", id);

		searchContext.setAttributes(attributes);

		searchContext.setCompanyId(companyId);
		searchContext.setStart(start);
		searchContext.setEnd(end);

		searchContext.setKeywords(keywords);

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		if (sort != null) {
			searchContext.setSorts(sort);
		}

		return searchContext;
	}

	private Item[] _getItems(long folderId) throws PortalException {
		List<Item> itemList = new ArrayList();

		Indexer<?> indexer = CommerceBOMSearcher.getInstance();

		Hits hits = indexer.search(
			buildSearchContext(
				contextCompany.getCompanyId(), folderId, StringPool.BLANK,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null));

		for (Document document : hits.getDocs()) {
			String className = document.get(Field.ENTRY_CLASS_NAME);
			long classPK = GetterUtil.getLong(
				document.get(Field.ENTRY_CLASS_PK));

			if (className.equals(CommerceBOMDefinition.class.getName())) {
				itemList.add(
					_toItem(
						_commerceBOMDefinitionService.getCommerceBOMDefinition(
							classPK)));
			}
			else if (className.equals(CommerceBOMFolder.class.getName())) {
				itemList.add(
					_toItem(
						_commerceBOMFolderService.getCommerceBOMFolder(
							classPK)));
			}
		}

		Item[] items = new Item[itemList.size()];

		return itemList.toArray(items);
	}

	private Item _toItem(CommerceBOMDefinition commerceBOMDefinition)
		throws PortalException {

		CPAttachmentFileEntry cpAttachmentFileEntry =
			commerceBOMDefinition.fetchCPAttachmentFileEntry();

		FileEntry fileEntry = cpAttachmentFileEntry.getFileEntry();

		String thumbnailURL = DLUtil.getDownloadURL(
			fileEntry, fileEntry.getFileVersion(), null, null);

		return new Item() {
			{
				id = commerceBOMDefinition.getCommerceBOMDefinitionId();
				name = commerceBOMDefinition.getName();
				slug = commerceBOMDefinition.getFriendlyUrl();
				thumbnail = thumbnailURL;
				type = Item.Type.create("area");
				url = "/areas/" + id;
			}
		};
	}

	private Item _toItem(CommerceBOMFolder commerceBOMFolder) {
		StringBundler sb = new StringBundler(4);

		sb.append("/image/organization_logo?img_id=");
		sb.append(commerceBOMFolder.getLogoId());
		sb.append("&t=");
		sb.append(
			WebServerServletTokenUtil.getToken(commerceBOMFolder.getLogoId()));

		return new Item() {
			{
				id = commerceBOMFolder.getCommerceBOMFolderId();
				name = commerceBOMFolder.getName();
				thumbnail = sb.toString();
				type = Item.Type.create("folder");
				url = "/folders/" + id;
			}
		};
	}

	@Reference
	private BreadcrumbDTOConverter _breadcrumbDTOConverter;

	@Reference
	private CommerceBOMDefinitionService _commerceBOMDefinitionService;

	@Reference
	private CommerceBOMFolderService _commerceBOMFolderService;

}