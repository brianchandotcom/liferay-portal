/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Specifications} from '../../../src/main/resources/META-INF/resources/js/core/MarketplaceProduct';
import {ProductSpecification} from '../../../src/main/resources/META-INF/resources/js/types';

const productSpecifications = [
	{
		specificationKey: Specifications.CPU,
		value: '0',
	},
	{
		specificationKey: Specifications.LATEST_VERSION,
		value: 'Version 1.0',
	},
	{
		specificationKey: Specifications.PRICE_MODEL,
		value: 'Free',
	},
	{
		specificationKey: Specifications.PUBLISHER_WEBSITE_URL,
		value: 'https://comehereforhelp.com/docs',
	},
	{
		specificationKey: Specifications.RAM,
		value: '0',
	},
	{
		specificationKey: Specifications.SUPPORT_EMAIL_ADDRESS,
		value: 'support@liferay.com',
	},
	{
		specificationKey: Specifications.SUPPORT_PHONE,
		value: '999-999-999',
	},
	{
		specificationKey: Specifications.TYPE,
		value: 'cloud',
	},
] as ProductSpecification[];

export default productSpecifications;
