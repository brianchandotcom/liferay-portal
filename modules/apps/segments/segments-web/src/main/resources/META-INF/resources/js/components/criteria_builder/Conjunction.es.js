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
import ClayDropdown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import getCN from 'classnames';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

import {conjunctionShape} from '../../utils/types.es';

function Conjunction({
	className,
	conjunctionName,
	editing,
	onSelect,
	supportedConjunctions = []
}) {
	const [active, setActive] = useState(false);

	const classnames = getCN(
		{
			'conjunction-button': editing,
			'conjunction-label': !editing
		},
		className
	);

	function _handleItemClick(conjuntionName) {
		setActive(false);

		onSelect(conjuntionName);
	}

	return editing ? (
		<ClayDropdown
			active={active}
			className={classnames}
			onActiveChange={setActive}
			trigger={
				<ClayButton
					className="text-capitalize"
					displayType="secondary"
					small
				>
					{conjunctionName}
					<ClayIcon className="ml-2" symbol="caret-bottom" />
				</ClayButton>
			}
		>
			<ClayDropdown.ItemList>
				{supportedConjunctions.map(conjuntion => {
					return (
						<ClayDropdown.Item
							className="text-capitalize"
							key={conjuntion.name}
							onClick={() => _handleItemClick(conjuntion.name)}
						>
							{conjuntion.label}
						</ClayDropdown.Item>
					);
				})}
			</ClayDropdown.ItemList>
		</ClayDropdown>
	) : (
		<div className={classnames}>{conjunctionName}</div>
	);
}

Conjunction.propTypes = {
	className: PropTypes.string,
	conjunctionName: PropTypes.string.isRequired,
	editing: PropTypes.bool.isRequired,
	onSelect: PropTypes.func.isRequired,
	supportedConjunctions: PropTypes.arrayOf(conjunctionShape)
};

export default Conjunction;
