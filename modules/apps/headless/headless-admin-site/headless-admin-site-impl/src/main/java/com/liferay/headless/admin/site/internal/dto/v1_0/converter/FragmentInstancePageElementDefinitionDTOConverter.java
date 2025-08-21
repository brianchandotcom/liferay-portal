/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.dto.v1_0.converter;

import com.liferay.fragment.contributor.FragmentCollectionContributorRegistry;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.headless.admin.site.dto.v1_0.DefaultFragmentReference;
import com.liferay.headless.admin.site.dto.v1_0.FragmentInstancePageElementDefinition;
import com.liferay.headless.admin.site.dto.v1_0.ItemExternalReference;
import com.liferay.headless.admin.site.dto.v1_0.PageElementDefinition;
import com.liferay.layout.util.structure.FragmentStyledLayoutStructureItem;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = "dto.class.name=com.liferay.layout.util.structure.FragmentStyledLayoutStructureItem",
	service = DTOConverter.class
)
public class FragmentInstancePageElementDefinitionDTOConverter
	implements DTOConverter
		<FragmentStyledLayoutStructureItem,
		 FragmentInstancePageElementDefinition> {

	@Override
	public String getContentType() {
		return FragmentInstancePageElementDefinition.class.getSimpleName();
	}

	@Override
	public FragmentInstancePageElementDefinition toDTO(
			DTOConverterContext dtoConverterContext,
			FragmentStyledLayoutStructureItem fragmentStyledLayoutStructureItem)
		throws Exception {

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.fetchFragmentEntryLink(
				fragmentStyledLayoutStructureItem.getFragmentEntryLinkId());

		if (fragmentEntryLink == null) {
			throw new UnsupportedOperationException();
		}

		return new FragmentInstancePageElementDefinition() {
			{
				setConfiguration(fragmentEntryLink::getConfiguration);
				setCss(fragmentEntryLink::getCss);
				setCssClasses(
					() -> {
						if (SetUtil.isEmpty(
								fragmentStyledLayoutStructureItem.
									getCssClasses())) {

							return null;
						}

						return ArrayUtil.toStringArray(
							fragmentStyledLayoutStructureItem.getCssClasses());
					});
				setCustomCSS(fragmentStyledLayoutStructureItem::getCustomCSS);
				setFragmentReference(
					() -> {
						FragmentEntry fragmentEntry =
							_fragmentEntryLocalService.fetchFragmentEntry(
								fragmentEntryLink.getFragmentEntryId());

						if (fragmentEntry != null) {
							return new ItemExternalReference() {
								{
									setExternalReferenceCode(
										fragmentEntry::
											getExternalReferenceCode);
								}
							};
						}

						Map<String, FragmentEntry> fragmentEntries =
							_fragmentCollectionContributorRegistry.
								getFragmentEntries();

						if (!fragmentEntries.containsKey(
								fragmentEntryLink.getRendererKey())) {

							return null;
						}

						return new DefaultFragmentReference() {
							{
								setDefaultFragmentKey(
									fragmentEntryLink::getRendererKey);
							}
						};
					});
				setFragmentType(
					() -> {
						if (fragmentEntryLink.isTypeComponent()) {
							return FragmentType.BASIC;
						}

						return FragmentType.FORM;
					});
				setHtml(fragmentEntryLink::getHtml);
				setIndexed(fragmentStyledLayoutStructureItem::isIndexed);
				setJs(fragmentEntryLink::getJs);
				setName(fragmentStyledLayoutStructureItem::getName);
				setNamespace(fragmentEntryLink::getNamespace);
				setType(PageElementDefinition.Type.FRAGMENT);
			}
		};
	}

	@Reference
	private FragmentCollectionContributorRegistry
		_fragmentCollectionContributorRegistry;

	@Reference
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Reference
	private FragmentEntryLocalService _fragmentEntryLocalService;

}