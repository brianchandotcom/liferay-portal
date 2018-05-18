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