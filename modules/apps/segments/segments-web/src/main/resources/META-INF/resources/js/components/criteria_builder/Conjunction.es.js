/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayDropdown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

import {SUPPORTED_CONJUNCTIONS} from '../../utils/constants';

function Conjunction({className, conjunctionName, editing, onSelect}) {
	const [active, setActive] = useState(false);

	const classnames = classNames(
		{
			'conjunction-button': editing,
			'conjunction-label': !editing,
		},
		className
	);

	const [activeLabel, setActiveLabel] = useState(null);
	useEffect(() => {
		const selectedConjunction = SUPPORTED_CONJUNCTIONS.find(
			(conjunction) =>
				conjunction.name.toLowerCase() === conjunctionName.toLowerCase()
		);

		setActiveLabel(selectedConjunction.label);
	}, [conjunctionName]);

	function _handleItemClick(conjunctionName) {
		setActive(false);

		onSelect(conjunctionName);
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
					{activeLabel}

					<ClayIcon className="ml-2" symbol="caret-bottom" />
				</ClayButton>
			}
		>
			<ClayDropdown.ItemList>
				{SUPPORTED_CONJUNCTIONS.map((conjunction) => {
					return (
						<ClayDropdown.Item
							className="text-capitalize"
							key={conjunction.name}
							onClick={() => _handleItemClick(conjunction.name)}
						>
							{conjunction.label}
						</ClayDropdown.Item>
					);
				})}
			</ClayDropdown.ItemList>
		</ClayDropdown>
	) : (
		<div className={classnames}>{activeLabel}</div>
	);
}

Conjunction.propTypes = {
	className: PropTypes.string,
	conjunctionName: PropTypes.string.isRequired,
	editing: PropTypes.bool.isRequired,
	onSelect: PropTypes.func.isRequired,
};

export default Conjunction;
