/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import React from 'react';

import formatActionURL from '../../../common/utils/formatActionURL';
import ContentPreview from './ContentPreview';
import FilePreview from './FilePreview';

const Arrow = ({
	direction,
	handleClick,
}: {
	direction: string;
	handleClick: any;
}) => (
	<div className={`position-absolute pull-${direction}`}>
		<ClayButtonWithIcon
			displayType="secondary"
			onClick={handleClick}
			outline
			rounded
			symbol={`angle-${direction}`}
		/>
	</div>
);

export default function Carousel({
	contentViewURL,
	currentItem,
	handleClickNext,
	handleClickPrevious,
	showArrows = true,
}: {
	contentViewURL: string;
	currentItem: ItemData;
	handleClickNext: () => void;
	handleClickPrevious: () => void;
	showArrows: boolean;
}) {
	return (
		<div className="carousel height-100">
			{showArrows && (
				<Arrow direction="left" handleClick={handleClickPrevious} />
			)}

			<div className="preview-container">
				{currentItem.embedded?.file ? (
					<FilePreview file={currentItem.embedded.file} />
				) : (
					<ContentPreview
						url={formatActionURL(currentItem, contentViewURL)}
					/>
				)}
			</div>

			{showArrows && (
				<Arrow direction="right" handleClick={handleClickNext} />
			)}
		</div>
	);
}
