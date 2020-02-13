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

import {ClayButtonWithIcon} from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import {ClayCheckbox} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import ClayLink from '@clayui/link';
import classNames from 'classnames';
import React, {
	useCallback,
	useContext,
	useEffect,
	useRef,
	useState
} from 'react';

import MillerColumnsContext from './MillerColumnsContext.es';

const ITEM_STATES_COLORS = {
	'conversion-draft': 'info',
	draft: 'secondary',
	pending: 'info'
};

const noop = () => {};

const MillerColumnsColumnItem = ({
	actions = [],
	active,
	bulkActions = '',
	checked,
	description,
	draggable,
	hasChild,
	itemId,
	selectable,
	states = [],
	title,
	url
}) => {
	const {actionHandlers = {}, namespace = ''} = useContext(
		MillerColumnsContext
	);

	const [dropdownActionsActive, setDropdownActionsActive] = useState();

	const getDropdownActions = useCallback(
		actions => {
			const dropdownActions = [];

			actions.map(action => {
				if (!action.quickAction && action.url) {
					dropdownActions.push({
						...action,
						handler:
							action.handler || actionHandlers[action.id] || noop
					});
				}
			});

			return dropdownActions;
		},
		[actionHandlers]
	);

	const getQuickActions = useCallback(
		actions => {
			const quickActions = [];

			actions.map(action => {
				if (action.quickAction && action.url) {
					quickActions.push({
						...action,
						handler:
							action.handler || actionHandlers[action.id] || noop
					});
				}
			});

			return quickActions;
		},
		[actionHandlers]
	);

	const dropdownActions = useRef(getDropdownActions(actions));
	const quickActions = useRef(getQuickActions(actions));

	useEffect(() => {
		dropdownActions.current = getDropdownActions(actions);
		quickActions.current = getQuickActions(actions);
	}, [actions, getDropdownActions, getQuickActions]);

	return (
		<li
			className={classNames(
				'autofit-row autofit-row-center list-group-item-flex miller-columns-item',
				{
					active
				}
			)}
			data-actions={bulkActions}
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
						value={itemId}
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

						{states.map(state => (
							<ClayLabel
								className="inline-item-after"
								displayType={ITEM_STATES_COLORS[state.id]}
								key={state.id}
							>
								{state.label}
							</ClayLabel>
						))}
					</h5>
				)}
			</div>

			{quickActions.current.map(action => (
				<div
					className="autofit-col miller-columns-item-quick-action"
					key={action.id}
				>
					<ClayLink
						borderless
						displayType="secondary"
						href={action.url}
						monospaced
						outline
					>
						<ClayIcon symbol={action.icon} />
					</ClayLink>
				</div>
			))}

			{dropdownActions.current.length > 0 && (
				<div className="autofit-col miller-columns-item-actions">
					<ClayDropDown
						active={dropdownActionsActive}
						onActiveChange={setDropdownActionsActive}
						trigger={
							<ClayButtonWithIcon
								borderless
								displayType="secondary"
								small
								symbol="ellipsis-v"
							/>
						}
					>
						<ClayDropDown.ItemList>
							{dropdownActions.current.map(action => (
								<ClayDropDown.Item
									href={action.url}
									id={action.id}
									key={action.id}
									onClick={action.handler}
								>
									{action.label}
								</ClayDropDown.Item>
							))}
						</ClayDropDown.ItemList>
					</ClayDropDown>
				</div>
			)}

			{hasChild && (
				<div className="autofit-col autofit-padded-no-gutters miller-columns-item-child-indicator text-muted">
					<ClayIcon symbol="caret-right" />
				</div>
			)}
		</li>
	);
};

export default MillerColumnsColumnItem;
