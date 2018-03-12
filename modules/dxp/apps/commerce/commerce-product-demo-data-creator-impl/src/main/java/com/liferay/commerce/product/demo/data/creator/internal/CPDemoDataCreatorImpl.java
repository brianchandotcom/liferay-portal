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

package com.liferay.commerce.product.demo.data.creator.internal;

import com.liferay.commerce.product.demo.data.creator.CPDemoDataCreator;
import com.liferay.commerce.product.demo.data.creator.internal.util.AssetCategoryDemoDataCreatorHelper;
import com.liferay.commerce.product.demo.data.creator.internal.util.AssetVocabularyDemoDataCreatorHelper;
import com.liferay.commerce.product.demo.data.creator.internal.util.CPDefinitionDemoDataCreatorHelper;
import com.liferay.commerce.product.demo.data.creator.internal.util.CPOptionCategoryDemoDataCreatorHelper;
import com.liferay.commerce.product.demo.data.creator.internal.util.CPOptionDemoDataCreatorHelper;
import com.liferay.commerce.product.demo.data.creator.internal.util.CPSpecificationOptionDemoDataCreatorHelper;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(immediate = true)
public class CPDemoDataCreatorImpl implements CPDemoDataCreator {

	@Override
	public void create(long userId, long groupId, boolean buildSkus)
		throws Exception {

		_assetCategoryDemoDataCreatorHelper.init();
		_cpDefinitionDemoDataCreatorHelper.init();
		_cpOptionDemoDataCreatorHelper.init();
		_cpSpecificationOptionDemoDataCreatorHelper.init();

		_assetVocabularyDemoDataCreatorHelper.addAssetVocabularies(
			userId, groupId);

		_cpDefinitionDemoDataCreatorHelper.addCPDefinitions(
			userId, groupId, buildSkus);
	}

	@Reference
	private AssetCategoryDemoDataCreatorHelper
		_assetCategoryDemoDataCreatorHelper;

	@Reference
	private AssetVocabularyDemoDataCreatorHelper
		_assetVocabularyDemoDataCreatorHelper;

	@Reference
	private CPDefinitionDemoDataCreatorHelper
		_cpDefinitionDemoDataCreatorHelper;

	@Reference
	private CPOptionCategoryDemoDataCreatorHelper
		_cpOptionCategoryDemoDataCreatorHelper;

	@Reference
	private CPOptionDemoDataCreatorHelper _cpOptionDemoDataCreatorHelper;

	@Reference
	private CPSpecificationOptionDemoDataCreatorHelper
		_cpSpecificationOptionDemoDataCreatorHelper;

}