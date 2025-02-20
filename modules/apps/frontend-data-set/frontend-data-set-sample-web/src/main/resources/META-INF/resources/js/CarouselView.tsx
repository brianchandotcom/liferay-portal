/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Card, ICardSchema} from '@liferay/frontend-data-set-web';
import React, {useState} from 'react';

import '../css/CarouselView.scss';

const CarouselView = ({
	items,
	schema,
}: {
	items: Array<any>;
	schema: ICardSchema;
}) => {
	const [selectedIndex, setSelectedIndex] = useState(0);

	const visibleItemsCount = 5;

	const startIndex = Math.max(
		0,
		Math.min(
			selectedIndex - Math.floor(visibleItemsCount / 2),
			items.length - visibleItemsCount
		)
	);

	const handleitemClick = (index: number) => {
		setSelectedIndex(index);
	};

	const handlePrevClick = () => {
		const newIndex =
			selectedIndex === 0 ? items.length - 1 : selectedIndex - 1;
		setSelectedIndex(newIndex);
	};

	const handleNextClick = () => {
		const newIndex =
			selectedIndex === items.length - 1 ? 0 : selectedIndex + 1;
		setSelectedIndex(newIndex);
	};

	const visibleItems = items.slice(
		startIndex,
		startIndex + visibleItemsCount
	);

	return (
		<div className="fds-carousel-view-sample">
			<div className="card main-image-container">
				<div>
					<img
						alt={items[selectedIndex][schema.title]}
						className="main-image"
						src={
							schema.image
								? items[selectedIndex][schema.image]
								: undefined
						}
					/>

					<div className="card-info">
						<p>{items[selectedIndex][schema.description]}</p>
					</div>
				</div>
			</div>

			<div className="item-wrapper">
				<div className="nav-buttons">
					<button className="nav-button" onClick={handlePrevClick}>
						{'<'}
					</button>

					<button className="nav-button" onClick={handleNextClick}>
						{'>'}
					</button>
				</div>

				<div className="item-container">
					{visibleItems.map((item, index) => {
						const actualIndex = startIndex + index;

						return (
							<div
								className={`item card ${
									actualIndex === selectedIndex
										? 'selected'
										: ''
								}`}
								key={actualIndex}
								onClick={() => handleitemClick(actualIndex)}
							>
								<Card item={item} schema={schema} />
							</div>
						);
					})}
				</div>
			</div>
		</div>
	);
};

export default CarouselView;
