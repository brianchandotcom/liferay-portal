/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClaySticker from '@clayui/sticker';
import {useIsMounted} from '@liferay/frontend-js-react-web';
import React, {useEffect, useState} from 'react';

import {getProduct} from '../utilities/data';

function StorefrontTooltipContent({channelId, selectedPin}) {
	const [productDetails, updateProductDetails] = useState(null);
	const [loading, updateLoading] = useState(false);
	const isMounted = useIsMounted();

	useEffect(() => {
		if (selectedPin.mappedProduct.type !== 'sku') {
			return;
		}

		updateLoading(true);

		getProduct(selectedPin.mappedProduct.productId, channelId).then(
			(product) => {
				if (!isMounted()) {
					return;
				}

				console.log(product);

				updateLoading(false);
				updateProductDetails(product);
			}
		);
	}, [channelId, isMounted, selectedPin]);

	const productName =
		productDetails?.name[themeDisplay.getLanguageId()] ||
		productDetails?.name[themeDisplay.getDefaultLanguageId()];

	return (
		<div>
			{loading && <ClayLoadingIndicator className="my-3" small />}

			{!loading && productDetails && (
				<div className="row">
					{productDetails.thumbnail && (
						<div className="col-auto">
							<ClaySticker size="xl">
								<ClaySticker.Image
									alt={productName}
									src={productDetails.thumbnail}
								/>
							</ClaySticker>
						</div>
					)}

					<div className="col">{productName}</div>
				</div>
			)}

			{!loading && !productDetails && <div>asd</div>}
		</div>
	);
}

export default StorefrontTooltipContent;
