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

import classNames from 'classnames';
import React, {useEffect, useState} from 'react';
import ProductComparison from '../../../common/components/product-comparison';
import useWindowDimensions from '../../../common/hooks/useWindowDimensions';
import {STORAGE_KEYS, Storage} from '../../../common/services/liferay/storage';
import {RAYLIFE_PAGES} from '../../../common/utils/constants';
import {redirectTo} from '../../../common/utils/liferay';
import Carousel from '../components/Carousel';
import {getQuoteComparisons} from '../service/QuoteComparison';

const QuoteComparison = () => {
	const [quotes, setQuotes] = useState([]);

	const {
		device: {isMobile, isTablet},
	} = useWindowDimensions();

	const isMobileDevice = isMobile || isTablet;

	useEffect(() => {
		const quoteElements = document.querySelector(
			'section#content #main-content .container-fluid'
		);

		quoteElements.classList.add('quote-comparison-content');

		getQuoteComparisons()
			.then((data) => setQuotes(data.items))
			.catch((error) => console.error(error.message));
	}, []);

	const onClickPurchase = ({id}) => {
		Storage.setItem(STORAGE_KEYS.PRODUCT_ID, id);

		redirectTo(RAYLIFE_PAGES.SELECTED_QUOTE);
	};

	const onClickPolicyDetails = () => {};

	const quoteComparisonItems = document.querySelectorAll(
		'#quote-comparison-item'
	);
	const quoteComparisonContainer = document.getElementById(
		'quote-comparison-container'
	);

	return (
		<Carousel
			cardElements={quoteComparisonItems}
			isMobileDevice={isMobileDevice}
			items={quotes}
			scrollableContainer={quoteComparisonContainer}
		>
			<div
				className={classNames('d-flex quote-comparison', {
					'mb-4': isMobile,
					'mb-7': !isMobile,
				})}
				id="quote-comparison-container"
			>
				{quotes.map((quote, index) => (
					<ProductComparison
						isMobileDevice={isMobileDevice}
						key={index}
						onClickPolicyDetails={onClickPolicyDetails}
						onClickPurchase={onClickPurchase}
						product={quote}
					/>
				))}
			</div>
		</Carousel>
	);
};

export default QuoteComparison;
