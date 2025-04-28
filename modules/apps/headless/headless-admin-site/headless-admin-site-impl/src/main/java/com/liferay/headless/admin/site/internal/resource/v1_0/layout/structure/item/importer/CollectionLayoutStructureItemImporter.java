/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.resource.v1_0.layout.structure.item.importer;

import com.liferay.headless.admin.site.dto.v1_0.CollectionPageElementDefinition;
import com.liferay.headless.admin.site.dto.v1_0.EmptyCollectionConfig;
import com.liferay.headless.admin.site.dto.v1_0.PageElement;
import com.liferay.headless.admin.site.internal.resource.v1_0.layout.structure.item.importer.context.LayoutStructureItemImporterContext;
import com.liferay.headless.admin.site.internal.resource.v1_0.util.LayoutStructureUtil;
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

		CollectionPageElementDefinition collectionPageElementDefinition =
			(CollectionPageElementDefinition)
				pageElement.getPageElementDefinition();

		if (collectionPageElementDefinition == null) {
			return collectionStyledLayoutStructureItem;
		}

		collectionStyledLayoutStructureItem.setDisplayAllItems(
			collectionPageElementDefinition.getDisplayAllItems());
		collectionStyledLayoutStructureItem.setEmptyCollectionOptions(
			_toEmptyCollectionOptions(
				collectionPageElementDefinition.getEmptyCollectionConfig()));
		collectionStyledLayoutStructureItem.setDisplayAllPages(
			collectionPageElementDefinition.getDisplayAllPages());
		collectionStyledLayoutStructureItem.setListItemStyle(
			collectionPageElementDefinition.getListItemStyle());
		collectionStyledLayoutStructureItem.setListStyle(
			collectionPageElementDefinition.getListStyle());
		collectionStyledLayoutStructureItem.setNumberOfColumns(
			collectionPageElementDefinition.getNumberOfColumns());
		collectionStyledLayoutStructureItem.setNumberOfItems(
			collectionPageElementDefinition.getNumberOfItems());
		collectionStyledLayoutStructureItem.setNumberOfItemsPerPage(
			collectionPageElementDefinition.getNumberOfItemsPerPage());
		collectionStyledLayoutStructureItem.setNumberOfPages(
			collectionPageElementDefinition.getNumberOfPages());
		collectionStyledLayoutStructureItem.setPaginationType(
			_toPaginationType(
				collectionPageElementDefinition.getPaginationType()));
		collectionStyledLayoutStructureItem.setTemplateKey(
			collectionPageElementDefinition.getTemplateKey());
		collectionStyledLayoutStructureItem.setName(
			collectionPageElementDefinition.getName());

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
		CollectionPageElementDefinition.PaginationType paginationType) {

		if (Objects.equals(
				paginationType,
				CollectionPageElementDefinition.PaginationType.NUMERIC)) {

			return CollectionPaginationUtil.PAGINATION_TYPE_NUMERIC;
		}

		if (Objects.equals(
				paginationType,
				CollectionPageElementDefinition.PaginationType.REGULAR)) {

			return CollectionPaginationUtil.PAGINATION_TYPE_REGULAR;
		}

		return CollectionPaginationUtil.PAGINATION_TYPE_NONE;
	}

}