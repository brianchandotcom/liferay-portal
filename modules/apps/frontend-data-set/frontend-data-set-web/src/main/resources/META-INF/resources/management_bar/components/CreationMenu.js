/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useContext, useState} from 'react';

import FrontendDataSetContext from '../../FrontendDataSetContext';
import {triggerAction} from '../../utils/actionItems/index';

const EMPTY_STATE_BUTTON_PROPS = {
	'aria-label': undefined,
	'className': undefined,
	'displayType': 'secondary',
	'title': undefined,
};

const DropDown = ({inEmptyState, primaryItems}) => {
	const frontendDataSetContext = useContext(FrontendDataSetContext);

	const {loadData} = frontendDataSetContext;

	const [active, setActive] = useState(false);

	return (
		<ClayDropDown
			active={active}
			onActiveChange={setActive}
			trigger={
				<ClayButton
					aria-label={Liferay.Language.get('new')}
					className="nav-btn nav-btn-monospaced"
					title={Liferay.Language.get('new')}
					{...(inEmptyState ? EMPTY_STATE_BUTTON_PROPS : {})}
				>
					{inEmptyState ? (
						Liferay.Language.get('new')
					) : (
						<ClayIcon symbol="plus" />
					)}
				</ClayButton>
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
								triggerAction(item, frontendDataSetContext);
							}
						}}
					>
						{item.label}
					</ClayDropDown.Item>
				))}
			</ClayDropDown.ItemList>
		</ClayDropDown>
	);
};

function CreationMenu({inEmptyState, primaryItems}) {
	const frontendDataSetContext = useContext(FrontendDataSetContext);

	const {loadData} = frontendDataSetContext;

	return (
		primaryItems?.length > 0 && (
			<ul
				className={classNames('navbar-nav', {
					'd-inline-flex': inEmptyState,
				})}
			>
				<li className="nav-item">
					{primaryItems.length > 1 ? (
						<DropDown
							inEmptyState={inEmptyState}
							primaryItems={primaryItems}
						/>
					) : (
						<ClayButton
							aria-label={primaryItems[0].label}
							className="nav-btn nav-btn-monospaced"
							data-tooltip-align="top"
							onClick={() => {
								const item = primaryItems[0];

								item.onClick?.({
									loadData,
								});

								if (item.href || item.target) {
									triggerAction(item, frontendDataSetContext);
								}
							}}
							title={primaryItems[0].label}
							{...(inEmptyState ? EMPTY_STATE_BUTTON_PROPS : {})}
						>
							{inEmptyState ? (
								primaryItems[0].label
							) : (
								<ClayIcon symbol="plus" />
							)}
						</ClayButton>
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
