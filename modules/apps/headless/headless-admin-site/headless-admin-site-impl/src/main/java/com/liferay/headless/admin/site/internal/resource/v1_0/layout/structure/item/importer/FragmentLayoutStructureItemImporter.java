/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.resource.v1_0.layout.structure.item.importer;

import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.contributor.util.FragmentCollectionContributorRegistryUtil;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalServiceUtil;
import com.liferay.fragment.service.FragmentEntryLocalServiceUtil;
import com.liferay.headless.admin.site.dto.v1_0.DefaultFragmentReference;
import com.liferay.headless.admin.site.dto.v1_0.FragmentInstancePageElementDefinition;
import com.liferay.headless.admin.site.dto.v1_0.ItemExternalReference;
import com.liferay.headless.admin.site.dto.v1_0.PageElement;
import com.liferay.headless.admin.site.internal.resource.v1_0.layout.structure.item.importer.context.LayoutStructureItemImporterContext;
import com.liferay.layout.util.structure.FragmentStyledLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Objects;

/**
 * @author Eudaldo Alonso
 */
public class FragmentLayoutStructureItemImporter
	implements LayoutStructureItemImporter {

	@Override
	public LayoutStructureItem addLayoutStructureItem(
			LayoutStructure layoutStructure,
			LayoutStructureItemImporterContext
				layoutStructureItemImporterContext,
			PageElement pageElement)
		throws Exception {

		FragmentInstancePageElementDefinition
			fragmentInstancePageElementDefinition =
				(FragmentInstancePageElementDefinition)
					pageElement.getPageElementDefinition();

		if (fragmentInstancePageElementDefinition == null) {
			return null;
		}

		FragmentEntryLink fragmentEntryLink = _addFragmentEntryLink(
			fragmentInstancePageElementDefinition,
			layoutStructureItemImporterContext);

		if (fragmentEntryLink == null) {
			return null;
		}

		FragmentStyledLayoutStructureItem fragmentStyledLayoutStructureItem =
			(FragmentStyledLayoutStructureItem)
				layoutStructure.addFragmentStyledLayoutStructureItem(
					fragmentEntryLink.getFragmentEntryLinkId(),
					pageElement.getExternalReferenceCode(),
					pageElement.getParentExternalReferenceCode(),
					pageElement.getPosition());

		fragmentStyledLayoutStructureItem.setCssClasses(
			SetUtil.fromArray(
				fragmentInstancePageElementDefinition.getCssClasses()));
		fragmentStyledLayoutStructureItem.setCustomCSS(
			fragmentInstancePageElementDefinition.getCustomCSS());
		fragmentStyledLayoutStructureItem.setIndexed(
			fragmentInstancePageElementDefinition.getIndexed());
		fragmentStyledLayoutStructureItem.setName(
			fragmentInstancePageElementDefinition.getName());

		return fragmentStyledLayoutStructureItem;
	}

	private FragmentEntryLink _addFragmentEntryLink(
			FragmentInstancePageElementDefinition
				fragmentInstancePageElementDefinition,
			LayoutStructureItemImporterContext
				layoutStructureItemImporterContext)
		throws Exception {

		Layout layout = layoutStructureItemImporterContext.getLayout();

		FragmentEntry fragmentEntry = _getFragmentEntry(
			fragmentInstancePageElementDefinition,
			layoutStructureItemImporterContext.getGroupId());

		int type = FragmentConstants.TYPE_COMPONENT;

		if (Objects.equals(
				FragmentInstancePageElementDefinition.FragmentType.BASIC,
				fragmentInstancePageElementDefinition.getFragmentType())) {

			type = FragmentConstants.TYPE_INPUT;
		}

		return FragmentEntryLinkLocalServiceUtil.addFragmentEntryLink(
			null, layoutStructureItemImporterContext.getUserId(),
			layout.getGroupId(), 0, fragmentEntry.getFragmentEntryId(),
			layoutStructureItemImporterContext.getSegmentsExperienceId(),
			layout.getPlid(), fragmentInstancePageElementDefinition.getCss(),
			fragmentInstancePageElementDefinition.getHtml(),
			fragmentInstancePageElementDefinition.getJs(),
			fragmentInstancePageElementDefinition.getConfiguration(),
			StringPool.BLANK, StringUtil.randomId(), 0,
			fragmentEntry.getFragmentEntryKey(), type,
			ServiceContextThreadLocal.getServiceContext());
	}

	private FragmentEntry _getFragmentEntry(
		FragmentInstancePageElementDefinition
			fragmentInstancePageElementDefinition,
		long groupId) {

		if (
				fragmentInstancePageElementDefinition.
					getFragmentReference() instanceof ItemExternalReference) {

			ItemExternalReference itemExternalReference =
				(ItemExternalReference)
					fragmentInstancePageElementDefinition.
						getFragmentReference();

			FragmentEntry fragmentEntry =
				FragmentEntryLocalServiceUtil.
					fetchFragmentEntryByExternalReferenceCode(
						itemExternalReference.getExternalReferenceCode(),
						groupId);

			if (fragmentEntry != null) {
				return fragmentEntry;
			}
		}

		DefaultFragmentReference defaultFragmentReference =
			(DefaultFragmentReference)
				fragmentInstancePageElementDefinition.getFragmentReference();

		return FragmentCollectionContributorRegistryUtil.getFragmentEntry(
			defaultFragmentReference.getDefaultFragmentKey());
	}

}