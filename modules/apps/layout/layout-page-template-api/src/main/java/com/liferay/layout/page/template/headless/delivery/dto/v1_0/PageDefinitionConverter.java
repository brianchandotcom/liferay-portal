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

package com.liferay.layout.page.template.headless.delivery.dto.v1_0;

import com.liferay.fragment.contributor.FragmentCollectionContributorTracker;
import com.liferay.fragment.renderer.FragmentRendererTracker;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.headless.delivery.dto.v1_0.PageDefinition;
import com.liferay.headless.delivery.dto.v1_0.PageElement;
import com.liferay.info.display.contributor.InfoDisplayContributorTracker;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.portal.kernel.model.Layout;

/**
 * @author Jürgen Kappler
 */
public interface PageDefinitionConverter {

	public PageDefinition toPageDefinition(
		FragmentCollectionContributorTracker
			fragmentCollectionContributorTracker,
		FragmentEntryConfigurationParser fragmentEntryConfigurationParser,
		FragmentRendererTracker fragmentRendererTracker,
		InfoDisplayContributorTracker infoDisplayContributorTracker,
		Layout layout);

	public PageDefinition toPageDefinition(
		FragmentCollectionContributorTracker
			fragmentCollectionContributorTracker,
		FragmentEntryConfigurationParser fragmentEntryConfigurationParser,
		FragmentRendererTracker fragmentRendererTracker,
		InfoDisplayContributorTracker infoDisplayContributorTracker,
		Layout layout, boolean saveInlineContent,
		boolean saveMappingConfiguration);

	public PageDefinition toPageDefinition(
		FragmentCollectionContributorTracker
			fragmentCollectionContributorTracker,
		FragmentEntryConfigurationParser fragmentEntryConfigurationParser,
		FragmentRendererTracker fragmentRendererTracker,
		InfoDisplayContributorTracker infoDisplayContributorTracker,
		Layout layout, boolean saveInlineContent,
		boolean saveMappingConfiguration, long segmentsExperienceId);

	public PageElement toPageElement(
		FragmentCollectionContributorTracker
			fragmentCollectionContributorTracker,
		FragmentEntryConfigurationParser fragmentEntryConfigurationParser,
		FragmentRendererTracker fragmentRendererTracker, long groupId,
		InfoDisplayContributorTracker infoDisplayContributorTracker,
		LayoutStructure layoutStructure,
		LayoutStructureItem layoutStructureItem, boolean saveInlineContent,
		boolean saveMappingConfiguration, long segmentsExperienceId);

}