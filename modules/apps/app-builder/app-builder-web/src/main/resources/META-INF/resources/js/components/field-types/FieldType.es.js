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

import React from 'react';
import ClayIcon from '@clayui/icon';
import ClaySticker from '@clayui/sticker';

export default ({description, icon, label, name}) => {
	return (
		<div
			className="autofit-row autofit-row-center field-type p-0 pt-3 pb-3"
			data-field-type-name={name}
		>
			<div className="autofit-col pl-2 pr-2">
				<ClayIcon symbol="drag" />
			</div>
			<div className="autofit-col pr-2">
				<ClaySticker
					className="app-builder-field-sticker"
					displayType="light"
					size="md"
				>
					<ClayIcon symbol={icon} />
				</ClaySticker>
			</div>
			<div className="autofit-col autofit-col-expand pr-2">
				<h4 className="list-group-title text-truncate">
					<span>{label}</span>
				</h4>
				{description && (
					<p className="list-group-subtitle text-truncate">
						<small>{description}</small>
					</p>
				)}
			</div>
		</div>
	);
};
