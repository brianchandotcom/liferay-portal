/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Vocabularies} from '../../../src/main/resources/META-INF/resources/js/core/MarketplaceProduct';
import {Category} from '../../../src/main/resources/META-INF/resources/js/types';

const categories = [
	{
		name: 'app category 1',
		vocabulary: Vocabularies.APP_CATEGORY,
	},
	{
		name: 'app categry 2',
		vocabulary: Vocabularies.APP_CATEGORY,
	},
	{
		name: 'edition 1',
		vocabulary: Vocabularies.EDITION,
	},
	{
		name: 'edition 2',
		vocabulary: Vocabularies.EDITION,
	},
	{
		name: 'platform offering 1',
		vocabulary: Vocabularies.PLATFORM_OFFERING,
	},
	{
		name: 'platform offering 2',
		vocabulary: Vocabularies.PLATFORM_OFFERING,
	},
] as Category[];

export default categories;
