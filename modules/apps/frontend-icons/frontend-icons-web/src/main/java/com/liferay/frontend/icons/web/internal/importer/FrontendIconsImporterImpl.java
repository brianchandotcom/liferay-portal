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

package com.liferay.frontend.icons.web.internal.importer;

import com.liferay.frontend.icons.importer.FrontendIconsImporter;
import com.liferay.frontend.icons.web.internal.model.FrontendIconsResource;
import com.liferay.frontend.icons.web.internal.model.FrontendIconsResourcePack;
import com.liferay.frontend.icons.web.internal.repository.FrontendIconsResourcePackRepository;
import com.liferay.frontend.icons.web.internal.util.SVGUtil;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Víctor Galán
 */
@Component(immediate = true, service = FrontendIconsImporter.class)
public class FrontendIconsImporterImpl implements FrontendIconsImporter {

	@Override
	public void importFrontendIcons(
			long companyId, String name, String spritemap)
		throws Exception {

		List<FrontendIconsResource> frontendIconsResources =
			SVGUtil.getFrontendIconsResources(spritemap);

		FrontendIconsResourcePack frontendIconsResourcePack =
			new FrontendIconsResourcePack(name);

		frontendIconsResourcePack.addFrontendIconsResources(
			frontendIconsResources);

		_frontendIconsResourcePackRepository.addFrontendIconsResourcePack(
			companyId, frontendIconsResourcePack);
	}

	@Reference
	private FrontendIconsResourcePackRepository
		_frontendIconsResourcePackRepository;

}