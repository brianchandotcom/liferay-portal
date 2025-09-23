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
	ariaLabel,
	control,
	handleClick,
}: {
	ariaLabel: string;
	control: 'next' | 'previous';
	handleClick: any;
}) => (
	<div className={`position-absolute carousel-${control}`}>
		<ClayButtonWithIcon
			aria-label={ariaLabel}
			displayType="secondary"
			onClick={handleClick}
			outline
			rounded
			symbol={`angle-${{next: 'right', previous: 'left'}[control]}`}
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
		<div className="carousel d-flex h-100">
			{showArrows && (
				<>
					<Arrow
						ariaLabel={Liferay.Language.get('previous')}
						control="previous"
						handleClick={handleClickPrevious}
					/>
					<Arrow
						ariaLabel={Liferay.Language.get('next')}
						control="next"
						handleClick={handleClickNext}
					/>
				</>
			)}

			<div className="h-100 mx-6 preview-container w-100">
				{currentItem.embedded?.file ? (
					<FilePreview file={currentItem.embedded.file} />
				) : (
					<ContentPreview
						url={formatActionURL(currentItem, contentViewURL)}
					/>
				)}
			</div>
		</div>
	);
}
