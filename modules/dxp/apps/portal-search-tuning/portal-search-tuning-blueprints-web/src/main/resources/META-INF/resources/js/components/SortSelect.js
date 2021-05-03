/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayButton from '@clayui/button';
import ClayForm, {ClaySelect} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import {PropTypes} from 'prop-types';
import React from 'react';

export default function SortSelect({onChange, value}) {
	const DEFAULT_DIRECTION = 'desc';
	const DEFAULT_FIELD = 'sort1';

	const DIRECTIONS = {
		['asc']: {
			icon: 'order-arrow-up',
			title: Liferay.Language.get('ascending'),
		},
		['desc']: {
			icon: 'order-arrow-down',
			title: Liferay.Language.get('descending'),
		},
	};

	const SORT_OPTIONS = [
		{
			label: Liferay.Language.get('relevancy'),
			value: 'sort1',
		},
		{
			label: Liferay.Language.get('title'),
			value: 'sort2',
		},
		{
			label: Liferay.Language.get('modified'),
			value: 'sort3',
		},
		{
			label: Liferay.Language.get('geodistance'),
			value: 'sort4',
		},
	];

	function _handleChangeSortField(event) {
		onChange({
			direction: value.direction ? value.direction : DEFAULT_DIRECTION,
			field: event.target.value,
		});
	}

	function _handleChangeSortDirection() {
		onChange({
			direction: value.direction === 'asc' ? 'desc' : 'asc',
			field: value.field ? value.field : DEFAULT_FIELD,
		});
	}

	return (
		<ClayLayout.Col size={5}>
			<ClayForm.Group className="form-group-autofit" small>
				<div className="form-group-item form-group-item-label form-group-item-shrink">
					<label>{Liferay.Language.get('sort-by')}</label>
				</div>

				<div className="form-group-item">
					<ClaySelect
						aria-label={Liferay.Language.get('sort-by')}
						onChange={_handleChangeSortField}
						value={value.field ? value.field : DEFAULT_FIELD}
					>
						{SORT_OPTIONS.map((item) => (
							<ClaySelect.Option
								key={item.value}
								label={item.label}
								value={item.value}
							/>
						))}
					</ClaySelect>
				</div>

				<ClayButton
					aria-label={Liferay.Language.get('sort-direction')}
					displayType="secondary"
					onClick={_handleChangeSortDirection}
					title={
						value.direction
							? DIRECTIONS[value.direction].title
							: DIRECTIONS[DEFAULT_DIRECTION].title
					}
				>
					<ClayIcon
						symbol={
							value.direction
								? DIRECTIONS[value.direction].icon
								: DIRECTIONS[DEFAULT_DIRECTION].icon
						}
					/>
				</ClayButton>
			</ClayForm.Group>
		</ClayLayout.Col>
	);
}

SortSelect.propTypes = {
	onChange: PropTypes.func,
	value: PropTypes.object,
};
