/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useContext} from 'react';
import ProductComparison from '../../../../common/components/product-comparison';
import {
	STORAGE_KEYS,
	Storage,
} from '../../../../common/services/liferay/storage';
import {SelectedQuoteContext} from '../../context/SelectedQuoteContextProvider';

const applicationId = Storage.getItem(STORAGE_KEYS.APPLICATION_ID);

const QuoteInfo = () => {
	const [{product}] = useContext(SelectedQuoteContext);

	return (
		<div className="mt-4 mt-md-0 pt-0 quote-info">
			{product.id && (
				<ProductComparison
					highlightMostPopularText="Great Coverage"
					onClickPolicyDetails={() => {}}
					product={product}
					purchasable={false}
				/>
			)}

			<div className="font-weight-bolder mt-2 mt-lg-5 text-center text-lg-left text-uppercase">
				Application {`#${applicationId}`}
			</div>
		</div>
	);
};

export default QuoteInfo;
