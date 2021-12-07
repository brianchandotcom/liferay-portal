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

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import React, {useState} from 'react';

import colors from '../../utils/colors';

const MAX_LABELS = 10;

const Label = ({active, color, index, label, onMouseOut, onMouseOver}) => (
	<li className={active ? '' : 'dim'} data-item={index}>
		<svg height="20" width="20">
			<circle
				cx="10"
				cy="10"
				fill={color}
				onMouseOut={onMouseOut}
				onMouseOver={onMouseOver}
				r="9"
				strokeWidth="3"
			/>
		</svg>

		<p onMouseOut={onMouseOut} onMouseOver={onMouseOver} x="0em" y="1em">
			<span dy="2em" x="2.5em">
				{label}
			</span>
		</p>
	</li>
);

const ShowAll = ({expand, expanded, labelsLength}) => {
	if (labelsLength <= MAX_LABELS) {
		return null;
	}

	const toggleExpanded = () => {
		expand(!expanded);
	};

	return (
		<div className="show-more-action">
			<svg height="20" width="20">
				<g>
					<circle
						cx="10"
						cy="10"
						fill="#F0F5FF"
						r="9"
						strokeWidth="3"
					/>

					<ClayIcon
						symbol={expanded ? 'caret-top-l' : 'caret-bottom-l'}
					/>
				</g>
			</svg>

			<ClayButton
				className="show-more-button"
				displayType="unstyled"
				onClick={() => toggleExpanded()}
			>
				{expanded
					? Liferay.Language.get('show-less')
					: Liferay.Language.get('show-all')}
			</ClayButton>
		</div>
	);
};

export default function Legend({activeIndex, labels, onMouseOut, onMouseOver}) {
	const [isShowAllExpanded, setIsShowAllExpanded] = useState(false);

	const handleOnMouseOver = ({currentTarget}) => {
		const item = currentTarget.closest('li').dataset.item;
		onMouseOver(parseInt(item, 10));
	};

	return (
		<div className="legend-container well well-lg">
			<div className="legend-list">
				<ul>
					{labels
						.slice(
							0,
							isShowAllExpanded ? labels.length : MAX_LABELS
						)
						.map((label, index) => (
							<Label
								active={
									activeIndex === null ||
									activeIndex === index
								}
								color={colors(index)}
								index={index}
								key={`item-${index}`}
								label={label}
								onMouseOut={onMouseOut}
								onMouseOver={handleOnMouseOver}
							/>
						))}

					<ShowAll
						expand={setIsShowAllExpanded}
						expanded={isShowAllExpanded}
						labelsLength={labels.length}
					/>
				</ul>
			</div>
		</div>
	);
}
