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

import ClayCard from '@clayui/card';
import ClayIcon from '@clayui/icon';
import React from 'react';

const CardItem = React.forwardRef(({item}, ref) => {
	return (
		<li
			className="mb-2 page-editor__fragments-widgets__tab-card-item"
			ref={ref}
		>
			<ClayCard
				aria-label={item.label}
				displayType={item.preview ? 'image' : 'file'}
				selectable
			>
				<ClayCard.AspectRatio className="card-item-first">
					{item.preview ? (
						<img
							alt="thumbnail"
							className="aspect-ratio-item aspect-ratio-item-center-middle aspect-ratio-item-fluid"
							src={item.preview}
						/>
					) : (
						<div className="aspect-ratio-item aspect-ratio-item-center-middle aspect-ratio-item-fluid card-type-asset-icon">
							<ClayIcon symbol={item.icon} />
						</div>
					)}
				</ClayCard.AspectRatio>
				<ClayCard.Body>
					<ClayCard.Row>
						<div className="autofit-col autofit-col-expand">
							<section className="autofit-section">
								<ClayCard.Description
									className="lfr-portal-tooltip"
									data-tooltip-align="center"
									displayType="title"
									title={item.label}
								>
									{item.label}
								</ClayCard.Description>
							</section>
						</div>
					</ClayCard.Row>
				</ClayCard.Body>
			</ClayCard>
		</li>
	);
});

export default CardItem;
