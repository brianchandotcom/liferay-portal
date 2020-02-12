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

import {ClayCheckbox} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import classNames from 'classnames';
import React, {useContext} from 'react';

import MillerColumnsContext from './MillerColumnsContext.es';

const ITEM_STATES = {
	'conversion-draft': {
		color: 'info',
		text: 'conversion-draft'
	},
	draft: {
		color: 'secondary',
		text: 'draft'
	},
	pending: {
		color: 'info',
		text: 'pending'
	}
};

const MillerColumnsColumnItem = ({
	active,
	checked,
	description,
	draggable,
	hasChild,
	id,
	selectable,
	state,
	title,
	url
}) => {
	const {namespace = ''} = useContext(MillerColumnsContext);

	return (
		<li
			className={classNames(
				'autofit-row autofit-row-center list-group-item-flex miller-columns-item',
				{
					active
				}
			)}
		>
			<a className="miller-columns-item-mask" href={url}>
				<span className="sr-only">{`${Liferay.Language.get(
					'select'
				)} ${title}`}</span>
			</a>

			{draggable && (
				<div className="autofit-col autofit-padded-no-gutters h2 miller-columns-item-drag-handler">
					<ClayIcon symbol="drag" />
				</div>
			)}

			{selectable && (
				<div className="autofit-col">
					<ClayCheckbox
						checked={checked}
						name={`${namespace}rowIds`}
						value={id}
					/>
				</div>
			)}

			<div className="autofit-col autofit-col-expand">
				<h4 className="list-group-title">
					<span className="text-truncate">{title}</span>
				</h4>

				{description && (
					<h5 className="list-group-subtitle small text-truncate">
						{description}

						{state && ITEM_STATES[state] && (
							<ClayLabel
								className="inline-item-after"
								displayType="secondary"
							>
								{Liferay.Language.get(ITEM_STATES[state].text)}
							</ClayLabel>
						)}
					</h5>
				)}
			</div>

			<div className="autofit-col autofit-padded-no-gutters text-muted">
				{hasChild && <ClayIcon symbol="caret-right" />}
			</div>
		</li>
	);
};

export default MillerColumnsColumnItem;
