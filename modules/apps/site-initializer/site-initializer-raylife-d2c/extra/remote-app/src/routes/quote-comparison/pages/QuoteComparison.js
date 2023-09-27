/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import React, {useEffect, useState} from 'react';
import ProductComparison from '../../../common/components/product-comparison';
import useWindowDimensions from '../../../common/hooks/useWindowDimensions';
import {STORAGE_KEYS, Storage} from '../../../common/services/liferay/storage';
import {RAYLIFE_PAGES} from '../../../common/utils/constants';
import {redirectTo} from '../../../common/utils/liferay';
import Carousel from '../components/Carousel';
import {getQuotes} from '../service/Quote';

const QuoteComparison = () => {
	const [quotes, setQuotes] = useState([]);

	const {
		device: {isMobile, isTablet},
	} = useWindowDimensions();

	const isMobileDevice = isMobile || isTablet;

	const sortData = (data) =>
		data.sort(
			(x, y) => JSON.parse(x.dataJSON).id - JSON.parse(y.dataJSON).id
		);

	useEffect(() => {
		const quoteElements = document.querySelector(
			'section#content #main-content .container-fluid'
		);

		quoteElements.classList.add('quote-comparison-content');

		getQuotes()
			.then((data) => setQuotes(sortData(data.items)))
			.catch((error) => console.error(error.message));
	}, []);

	const onClickPurchase = ({id}) => {
		Storage.setItem(STORAGE_KEYS.QUOTE_ID, id);

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
