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

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useContext, useState} from 'react';

import FrontendDataSetContext from '../../FrontendDataSetContext';
import {triggerAction} from '../../utils/actionItems/index';

function CreationMenu({inEmptyState, primaryItems}) {
	const frontendDataSetContext = useContext(FrontendDataSetContext);

	const {loadData} = frontendDataSetContext;

	const [active, setActive] = useState(false);

	const buttonDataTooltipAlign = 'top';
	const buttonLabel = primaryItems[0].label ?? Liferay.Language.get('new');
	const buttonOnClick = () => {
		const item = primaryItems[0];

		item.onClick?.({
			loadData,
		});

		if (item.href || item.target) {
			triggerAction(item, frontendDataSetContext);
		}
	};

	return (
		primaryItems?.length > 0 && (
			<ul
				className={classNames('navbar-nav', {
					'd-inline-flex': inEmptyState,
				})}
			>
				<li className="nav-item">
					{primaryItems.length > 1 ? (
						<ClayDropDown
							active={active}
							onActiveChange={setActive}
							trigger={
								inEmptyState ? (
									<ClayButton displayType="secondary">
										{Liferay.Language.get('new')}
									</ClayButton>
								) : (
									<ClayButtonWithIcon
										aria-label={Liferay.Language.get('new')}
										className="nav-btn nav-btn-monospaced"
										symbol="plus"
										title={Liferay.Language.get('new')}
									/>
								)
							}
						>
							<ClayDropDown.ItemList>
								{primaryItems.map((item, i) => (
									<ClayDropDown.Item
										key={i}
										onClick={(event) => {
											event.preventDefault();

											setActive(false);

											item.onClick?.({
												loadData,
											});

											if (item.href || item.target) {
												triggerAction(
													item,
													frontendDataSetContext
												);
											}
										}}
									>
										{item.label}
									</ClayDropDown.Item>
								))}
							</ClayDropDown.ItemList>
						</ClayDropDown>
					) : inEmptyState ? (
						<ClayButton
							data-tooltip-align={buttonDataTooltipAlign}
							displayType="secondary"
							onClick={() => buttonOnClick()}
						>
							{buttonLabel}
						</ClayButton>
					) : (
						<ClayButtonWithIcon
							aria-label={buttonLabel}
							className="nav-btn nav-btn-monospaced"
							data-tooltip-align={buttonDataTooltipAlign}
							onClick={() => buttonOnClick()}
							symbol="plus"
							title={buttonLabel}
						/>
					)}
				</li>
			</ul>
		)
	);
}

CreationMenu.propTypes = {
	primaryItems: PropTypes.arrayOf(
		PropTypes.shape({
			href: PropTypes.string,
			label: PropTypes.string,
			onClick: PropTypes.func,
			target: PropTypes.oneOf(['modal', 'sidePanel', 'event', 'link']),
		})
	).isRequired,
	secondaryItems: PropTypes.arrayOf(
		PropTypes.shape({
			href: PropTypes.string,
			label: PropTypes.string,
			onClick: PropTypes.func,
			target: PropTypes.oneOf(['modal', 'sidePanel', 'event', 'link']),
		})
	),
};

export default CreationMenu;
