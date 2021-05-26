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

import ClayBadge from '@clayui/badge';
import ClayIcon from '@clayui/icon';
import ClayList from '@clayui/list';
import React from 'react';

function Rule(props) {
	const {ariaLabel, onListItemClick, quantity, subtext, text, title} = props;

	return (
		<button
			aria-label={ariaLabel}
			className="list-group-item list-group-item-action list-group-item-flex list-group-item-flush"
			onClick={() => onListItemClick(props)}
			role="tab"
		>
			<ClayList.ItemField
				className="page-accessibility-tool__sidebar--rule-list-item"
				expand
			>
				{title && <ClayList.ItemTitle>{title}</ClayList.ItemTitle>}
				{subtext && (
					<ClayList.ItemText subtext>{subtext}</ClayList.ItemText>
				)}
				{text && <ClayList.ItemText>{text}</ClayList.ItemText>}
			</ClayList.ItemField>
			{quantity && (
				<ClayList.ItemField className="align-self-center">
					<ClayBadge label={quantity} />
				</ClayList.ItemField>
			)}
			<ClayList.ItemField className="align-self-center">
				<ClayIcon symbol="angle-right-small" />
			</ClayList.ItemField>
		</button>
	);
}

export default Rule;
