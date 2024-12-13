/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.resource.util.layout.structure.item.importer;

import com.liferay.fragment.contributor.FragmentCollectionContributorRegistry;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.headless.admin.site.dto.v1_0.DefaultFragmentReference;
import com.liferay.headless.admin.site.dto.v1_0.ItemExternalReference;
import com.liferay.headless.admin.site.dto.v1_0.PageElement;
import com.liferay.headless.admin.site.dto.v1_0.PageFragmentInstanceDefinition;
import com.liferay.headless.admin.site.internal.resource.util.layout.structure.item.importer.context.LayoutStructureItemImporterContext;
import com.liferay.layout.util.structure.FragmentStyledLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Eudaldo Alonso
 */
public class FragmentLayoutStructureItemImporter
	implements LayoutStructureItemImporter {

	public FragmentLayoutStructureItemImporter(
		FragmentCollectionContributorRegistry
			fragmentCollectionContributorRegistry,
		FragmentEntryLinkLocalService fragmentEntryLinkLocalService,
		FragmentEntryLocalService fragmentEntryLocalService) {

		_fragmentCollectionContributorRegistry =
			fragmentCollectionContributorRegistry;
		_fragmentEntryLinkLocalService = fragmentEntryLinkLocalService;
		_fragmentEntryLocalService = fragmentEntryLocalService;
	}

	@Override
	public LayoutStructureItem addLayoutStructureItem(
			LayoutStructure layoutStructure,
			LayoutStructureItemImporterContext
				layoutStructureItemImporterContext,
			PageElement pageElement)
		throws Exception {

		PageFragmentInstanceDefinition pageFragmentInstanceDefinition =
			(PageFragmentInstanceDefinition)pageElement.getDefinition();

		if (pageFragmentInstanceDefinition == null) {
			return null;
		}

		FragmentEntryLink fragmentEntryLink = _addFragmentEntryLink(
			layoutStructureItemImporterContext, pageFragmentInstanceDefinition);

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
			SetUtil.fromArray(pageFragmentInstanceDefinition.getCssClasses()));
		fragmentStyledLayoutStructureItem.setCustomCSS(
			pageFragmentInstanceDefinition.getCustomCSS());
		fragmentStyledLayoutStructureItem.setIndexed(
			pageFragmentInstanceDefinition.getIndexed());
		fragmentStyledLayoutStructureItem.setName(
			fragmentStyledLayoutStructureItem.getName());

		return fragmentStyledLayoutStructureItem;
	}

	private FragmentEntryLink _addFragmentEntryLink(
			LayoutStructureItemImporterContext
				layoutStructureItemImporterContext,
			PageFragmentInstanceDefinition pageFragmentInstanceDefinition)
		throws Exception {

		Layout layout = layoutStructureItemImporterContext.getLayout();

		FragmentEntry fragmentEntry = _getFragmentEntry(
			layoutStructureItemImporterContext.getGroupId(),
			pageFragmentInstanceDefinition);

		return _fragmentEntryLinkLocalService.addFragmentEntryLink(
			null, layoutStructureItemImporterContext.getUserId(),
			layout.getGroupId(), 0, fragmentEntry.getFragmentEntryId(),
			layoutStructureItemImporterContext.getSegmentsExperienceId(),
			layout.getPlid(), fragmentEntry.getCss(), fragmentEntry.getHtml(),
			fragmentEntry.getJs(), fragmentEntry.getConfiguration(),
			StringPool.BLANK, StringUtil.randomId(), 0,
			fragmentEntry.getFragmentEntryKey(), fragmentEntry.getType(),
			ServiceContextThreadLocal.getServiceContext());
	}

	private FragmentEntry _getFragmentEntry(
		long groupId,
		PageFragmentInstanceDefinition pageFragmentInstanceDefinition) {

		if (pageFragmentInstanceDefinition.getFragmentReference() instanceof
				ItemExternalReference) {

			ItemExternalReference itemExternalReference =
				(ItemExternalReference)
					pageFragmentInstanceDefinition.getFragmentReference();

			FragmentEntry fragmentEntry =
				_fragmentEntryLocalService.
					fetchFragmentEntryByExternalReferenceCode(
						itemExternalReference.getExternalReferenceCode(),
						groupId);

			if (fragmentEntry != null) {
				return fragmentEntry;
			}
		}

		DefaultFragmentReference defaultFragmentReference =
			(DefaultFragmentReference)
				pageFragmentInstanceDefinition.getFragmentReference();

		return _fragmentCollectionContributorRegistry.getFragmentEntry(
			defaultFragmentReference.getDefaultFragmentKey());
	}

	private final FragmentCollectionContributorRegistry
		_fragmentCollectionContributorRegistry;
	private final FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;
	private final FragmentEntryLocalService _fragmentEntryLocalService;

}