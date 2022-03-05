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
