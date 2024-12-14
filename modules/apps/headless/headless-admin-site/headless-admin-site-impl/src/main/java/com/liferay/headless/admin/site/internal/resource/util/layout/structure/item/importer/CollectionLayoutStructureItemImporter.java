/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.resource.util.layout.structure.item.importer;

import com.liferay.headless.admin.site.dto.v1_0.EmptyCollectionConfig;
import com.liferay.headless.admin.site.dto.v1_0.PageCollectionDefinition;
import com.liferay.headless.admin.site.dto.v1_0.PageElement;
import com.liferay.headless.admin.site.internal.resource.util.layout.structure.LayoutStructureUtil;
import com.liferay.headless.admin.site.internal.resource.util.layout.structure.item.importer.context.LayoutStructureItemImporterContext;
import com.liferay.layout.util.CollectionPaginationUtil;
import com.liferay.layout.util.structure.CollectionStyledLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.layout.util.structure.collection.EmptyCollectionOptions;

import java.util.Objects;

/**
 * @author Eudaldo Alonso
 */
public class CollectionLayoutStructureItemImporter
	implements LayoutStructureItemImporter {

	@Override
	public LayoutStructureItem addLayoutStructureItem(
			LayoutStructure layoutStructure,
			LayoutStructureItemImporterContext
				layoutStructureItemImporterContext,
			PageElement pageElement)
		throws Exception {

		CollectionStyledLayoutStructureItem
			collectionStyledLayoutStructureItem =
				(CollectionStyledLayoutStructureItem)
					layoutStructure.addCollectionStyledLayoutStructureItem(
						pageElement.getExternalReferenceCode(),
						LayoutStructureUtil.getParentExternalReferenceCode(
							pageElement, layoutStructure),
						pageElement.getPosition());

		PageCollectionDefinition pageCollectionDefinition =
			(PageCollectionDefinition)pageElement.getDefinition();

		if (pageCollectionDefinition == null) {
			return collectionStyledLayoutStructureItem;
		}

		collectionStyledLayoutStructureItem.setDisplayAllItems(
			pageCollectionDefinition.getDisplayAllItems());
		collectionStyledLayoutStructureItem.setEmptyCollectionOptions(
			_toEmptyCollectionOptions(
				pageCollectionDefinition.getEmptyCollectionConfig()));
		collectionStyledLayoutStructureItem.setDisplayAllPages(
			pageCollectionDefinition.getDisplayAllPages());
		collectionStyledLayoutStructureItem.setListItemStyle(
			pageCollectionDefinition.getListItemStyle());
		collectionStyledLayoutStructureItem.setListStyle(
			pageCollectionDefinition.getListStyle());
		collectionStyledLayoutStructureItem.setNumberOfColumns(
			pageCollectionDefinition.getNumberOfColumns());
		collectionStyledLayoutStructureItem.setNumberOfItems(
			pageCollectionDefinition.getNumberOfItems());
		collectionStyledLayoutStructureItem.setNumberOfItemsPerPage(
			pageCollectionDefinition.getNumberOfItemsPerPage());
		collectionStyledLayoutStructureItem.setNumberOfPages(
			pageCollectionDefinition.getNumberOfPages());
		collectionStyledLayoutStructureItem.setPaginationType(
			_toPaginationType(pageCollectionDefinition.getPaginationType()));
		collectionStyledLayoutStructureItem.setTemplateKey(
			pageCollectionDefinition.getTemplateKey());
		collectionStyledLayoutStructureItem.setName(
			pageCollectionDefinition.getName());

		return collectionStyledLayoutStructureItem;
	}

	private EmptyCollectionOptions _toEmptyCollectionOptions(
		EmptyCollectionConfig emptyCollectionConfig) {

		if (emptyCollectionConfig == null) {
			return null;
		}

		return new EmptyCollectionOptions() {
			{
				setDisplayMessage(emptyCollectionConfig::getDisplayMessage);
				setMessage(emptyCollectionConfig::getMessage_i18n);
			}
		};
	}

	private String _toPaginationType(
		PageCollectionDefinition.PaginationType paginationType) {

		if (Objects.equals(
				paginationType,
				PageCollectionDefinition.PaginationType.NUMERIC)) {

			return CollectionPaginationUtil.PAGINATION_TYPE_NUMERIC;
		}

		if (Objects.equals(
				paginationType,
				PageCollectionDefinition.PaginationType.REGULAR)) {

			return CollectionPaginationUtil.PAGINATION_TYPE_REGULAR;
		}

		return CollectionPaginationUtil.PAGINATION_TYPE_NONE;
	}

}