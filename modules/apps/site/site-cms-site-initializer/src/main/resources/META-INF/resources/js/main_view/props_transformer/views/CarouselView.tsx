/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Card, ICardSchema, replaceTokens} from '@liferay/frontend-data-set-web';
import React, {Context, useContext, useMemo, useState} from 'react';

import FilePreview from '../../../common/components/FilePreview';

import '../../../../css/props_transformer/CarouselView.scss';

import {ClayButtonWithIcon} from '@clayui/button';
import classNames from 'classnames';

import ContentPreview from '../../../common/components/ContentPreview';

const VISIBLE_ITEMS_COUNT = 6;

const CarouselView = ({
	additionalProps,
	frontendDataSetContext,
	items,
	schema,
	...otherProps
}: {
	additionalProps: {
		contentViewURL: string;
	};
	frontendDataSetContext: Context<any>; // See IFrontendDataSetContext in modules/apps/frontend-data-set/frontend-data-set-web/src/main/resources/META-INF/resources/FrontendDataSetContext.tsx
	items: any[];
	schema: ICardSchema;
}) => {
	const {selectedItems} = useContext(frontendDataSetContext);
	const [selectedIndex, setSelectedIndex] = useState(0);

	const startIndex = Math.max(
		0,
		Math.min(
			selectedIndex - Math.floor(VISIBLE_ITEMS_COUNT / 2),
			items.length - VISIBLE_ITEMS_COUNT
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
		startIndex + VISIBLE_ITEMS_COUNT
	);

	const currentItem = useMemo(
		() => items[selectedIndex],
		[items, selectedIndex]
	);

	const cardWidth = 100 / VISIBLE_ITEMS_COUNT;

	return (
		<div className="fds-carousel-view">
			<div className="fds-carousel-view__preview">
				<div className="align-items-center d-flex fds-carousel-view__preview-wrapper h-100 justify-content-center w-100">
					{selectedItems && selectedItems?.length < 2 ? (
						currentItem.embedded?.file ? (
							<FilePreview file={currentItem.embedded.file} />
						) : (
							<ContentPreview
								url={replaceTokens(
									additionalProps.contentViewURL,
									currentItem
								)}
							/>
						)
					) : (
						<p>Placeholder</p>
					)}
				</div>
			</div>

			<div className="align-items-center c-gap-3 d-flex fds-carousel-view__navigation justify-content-center mt-4">
				<ClayButtonWithIcon
					aria-label={Liferay.Language.get('previous')}
					className="flex-shrink-0"
					displayType="secondary"
					onClick={handlePrevClick}
					rounded
					symbol="angle-left"
				/>

				<div className="align-items-center c-gap-3 d-flex fds-carousel-view__thumbnails justify-content-center w-100">
					{visibleItems.map((item, index) => {
						const actualIndex = startIndex + index;
						const classes = classNames(
							'fds-carousel-view__thumbnail',
							{
								selected: actualIndex === selectedIndex,
							}
						);

						return (
							<div
								className={classes}
								key={actualIndex}
								onClick={() => handleitemClick(actualIndex)}
								style={{
									width: `${cardWidth}%`,
								}}
							>
								<Card
									item={item}
									schema={schema}
									{...otherProps}
								/>
							</div>
						);
					})}
				</div>

				<ClayButtonWithIcon
					aria-label={Liferay.Language.get('next')}
					className="flex-shrink-0"
					displayType="secondary"
					onClick={handleNextClick}
					rounded
					symbol="angle-right"
				/>
			</div>
		</div>
	);
};

export default CarouselView;
