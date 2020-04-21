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
import com.liferay.headless.delivery.dto.v1_0.Layout;
import com.liferay.headless.delivery.dto.v1_0.PageDefinition;
import com.liferay.headless.delivery.dto.v1_0.PageElement;
import com.liferay.info.display.contributor.InfoDisplayContributorTracker;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Rubén Pulido
 */
public class PageDefinitionConverterUtil {

	public static PageDefinition toPageDefinition(
		FragmentCollectionContributorTracker
			fragmentCollectionContributorTracker,
		FragmentEntryConfigurationParser fragmentEntryConfigurationParser,
		FragmentRendererTracker fragmentRendererTracker,
		InfoDisplayContributorTracker infoDisplayContributorTracker,
		com.liferay.portal.kernel.model.Layout layout) {

		PageDefinitionConverter pageDefinitionConverter =
			_serviceTracker.getService();

		return pageDefinitionConverter.toPageDefinition(
			fragmentCollectionContributorTracker,
			fragmentEntryConfigurationParser, fragmentRendererTracker,
			infoDisplayContributorTracker, layout);
	}

	public static PageDefinition toPageDefinition(
		FragmentCollectionContributorTracker
			fragmentCollectionContributorTracker,
		FragmentEntryConfigurationParser fragmentEntryConfigurationParser,
		FragmentRendererTracker fragmentRendererTracker,
		InfoDisplayContributorTracker infoDisplayContributorTracker,
		com.liferay.portal.kernel.model.Layout layout,
		boolean saveInlineContent, boolean saveMappingConfiguration) {

		PageDefinitionConverter pageDefinitionConverter =
			_serviceTracker.getService();

		return pageDefinitionConverter.toPageDefinition(
			fragmentCollectionContributorTracker,
			fragmentEntryConfigurationParser, fragmentRendererTracker,
			infoDisplayContributorTracker, layout, saveInlineContent,
			saveMappingConfiguration);
	}

	public static PageDefinition toPageDefinition(
		FragmentCollectionContributorTracker
			fragmentCollectionContributorTracker,
		FragmentEntryConfigurationParser fragmentEntryConfigurationParser,
		FragmentRendererTracker fragmentRendererTracker,
		InfoDisplayContributorTracker infoDisplayContributorTracker,
		com.liferay.portal.kernel.model.Layout layout,
		boolean saveInlineContent, boolean saveMappingConfiguration,
		long segmentsExperienceId) {

		PageDefinitionConverter pageDefinitionConverter =
			_serviceTracker.getService();

		return pageDefinitionConverter.toPageDefinition(
			fragmentCollectionContributorTracker,
			fragmentEntryConfigurationParser, fragmentRendererTracker,
			infoDisplayContributorTracker, layout, saveInlineContent,
			saveMappingConfiguration, segmentsExperienceId);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #toPageElement(FragmentCollectionContributorTracker,
	 *             FragmentEntryConfigurationParser, FragmentRendererTracker,
	 *             long, InfoDisplayContributorTracker, LayoutStructure,
	 *             LayoutStructureItem, boolean, boolean, long)}
	 */
	@Deprecated
	public static PageElement toPageElement(
		FragmentCollectionContributorTracker
			fragmentCollectionContributorTracker,
		FragmentEntryConfigurationParser fragmentEntryConfigurationParser,
		FragmentRendererTracker fragmentRendererTracker,
		LayoutStructure layoutStructure,
		LayoutStructureItem layoutStructureItem, boolean saveInlineContent,
		boolean saveMappingConfiguration) {

		throw new UnsupportedOperationException();
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #toPageElement(FragmentCollectionContributorTracker,
	 *             FragmentEntryConfigurationParser, FragmentRendererTracker,
	 *             long, InfoDisplayContributorTracker, LayoutStructure,
	 *             LayoutStructureItem, boolean, boolean, long)}
	 */
	@Deprecated
	public static PageElement toPageElement(
		FragmentCollectionContributorTracker
			fragmentCollectionContributorTracker,
		FragmentEntryConfigurationParser fragmentEntryConfigurationParser,
		FragmentRendererTracker fragmentRendererTracker,
		LayoutStructure layoutStructure,
		LayoutStructureItem layoutStructureItem, boolean saveInlineContent,
		boolean saveMappingConfiguration, long segmentsExperienceId) {

		throw new UnsupportedOperationException();
	}

	public static PageElement toPageElement(
		FragmentCollectionContributorTracker
			fragmentCollectionContributorTracker,
		FragmentEntryConfigurationParser fragmentEntryConfigurationParser,
		FragmentRendererTracker fragmentRendererTracker, long groupId,
		InfoDisplayContributorTracker infoDisplayContributorTracker,
		LayoutStructure layoutStructure,
		LayoutStructureItem layoutStructureItem, boolean saveInlineContent,
		boolean saveMappingConfiguration, long segmentsExperienceId) {

		PageDefinitionConverter pageDefinitionConverter =
			_serviceTracker.getService();

		return pageDefinitionConverter.toPageElement(
			fragmentCollectionContributorTracker,
			fragmentEntryConfigurationParser, fragmentRendererTracker, groupId,
			infoDisplayContributorTracker, layoutStructure, layoutStructureItem,
			saveInlineContent, saveMappingConfiguration, segmentsExperienceId);
	}

	private static final ServiceTracker
		<PageDefinitionConverter, PageDefinitionConverter> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(PageDefinitionConverter.class);

		ServiceTracker<PageDefinitionConverter, PageDefinitionConverter>
			serviceTracker = new ServiceTracker<>(
				bundle.getBundleContext(), PageDefinitionConverter.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}