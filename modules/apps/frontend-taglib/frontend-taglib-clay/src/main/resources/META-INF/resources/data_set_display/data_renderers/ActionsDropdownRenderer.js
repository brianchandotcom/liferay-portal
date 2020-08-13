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
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import PropTypes from 'prop-types';
import React, {useContext, useState} from 'react';

import DataSetDisplayContext from '../DataSetDisplayContext';
import {ACTION_ITEM_TARGETS} from '../utilities/actionItems/constants';
import {formatActionUrl} from '../utilities/index';
import {
	openPermissionsModal,
	resolveModalSize,
} from '../utilities/modals/index';

const {MODAL_PERMISSIONS} = ACTION_ITEM_TARGETS;

export function isLink(target, onClick) {
	return !(target && target !== 'link') && !onClick;
}

export function handleAction(
	{
		event,
		itemId = '',
		method,
		onClick = '',
		setLoading = () => {},
		size = '',
		target = '',
		title = '',
		url = '',
	},
	{executeAsyncItemAction, highlightItems, openModal, openSidePanel}
) {
	if (target?.includes('modal')) {
		event.preventDefault();

		if (target === MODAL_PERMISSIONS) {
			openPermissionsModal(url);
		}
		else {
			openModal({
				size: resolveModalSize(target),
				title,
				url,
			});
		}
	}
	else if (target === 'sidePanel') {
		event.preventDefault();

		highlightItems([itemId]);
		openSidePanel({
			size: size || 'lg',
			title,
			url,
		});
	}
	else if (target === 'async' || target === 'headless') {
		event.preventDefault();

		setLoading(true);
		executeAsyncItemAction(url, method).then(() => setLoading(false));
	}
	else if (target === 'blank') {
		event.preventDefault();

		window.open(url);
	}
	else if (onClick) {
		event.preventDefault();

		event.target.setAttribute('onClick', onClick);
		event.target.onclick();
		event.target.removeAttribute('onClick');
	}
}

function ActionItem({
	closeMenu,
	data,
	handleAction,
	href,
	icon,
	label,
	onClick,
	size,
	target,
	title,
}) {
	const context = useContext(DataSetDisplayContext);

	function handleClickOnLink(event) {
		event.preventDefault();

		handleAction(
			{
				event,
				method: data?.method,
				onClick,
				size: size || 'lg',
				target,
				title,
				url: href,
			},
			context
		);

		closeMenu();
	}

	const link = isLink(target, onClick);

	return (
		<ClayDropDown.Item
			href={link ? href : null}
			onClick={link ? null : handleClickOnLink}
		>
			{icon && (
				<span className="pr-2">
					<ClayIcon symbol={icon} />
				</span>
			)}
			{label}
		</ClayDropDown.Item>
	);
}

function ActionsDropdownRenderer({actions, itemData, itemId}) {
	const context = useContext(DataSetDisplayContext);
	const [active, setActive] = useState(false);
	const [loading, setLoading] = useState(false);

	const formattedActions = actions
		? actions.reduce((actions, action) => {
				if (action.permissionKey) {
					if (itemData.actions[action.permissionKey]) {
						if (action.target === 'headless') {
							return [
								...actions,
								{
									...action,
									...itemData.actions[action.permissionKey],
								},
							];
						}
						else {
							return [...actions, action];
						}
					}

					return actions;
				}

				return [...actions, action];
		  }, [])
		: [];

	if (!formattedActions || !formattedActions.length) {
		return null;
	}

	if (actions.length === 1) {
		const action = formattedActions[0];

		if (action.id && !action.href) {
			return null;
		}

		const formattedHref = formatActionUrl(action.href, itemData);

		if (loading) {
			return (
				<ClayButton className="btn-sm" disabled monospaced>
					<ClayLoadingIndicator small />
				</ClayButton>
			);
		}

		const content = action.icon ? (
			<ClayIcon symbol={action.icon} />
		) : (
			action.label
		);

		return isLink(action.target, action.onClick) ? (
			<ClayLink
				className="btn btn-secondary btn-sm"
				href={formattedHref}
				monospaced={Boolean(action.icon)}
			>
				{content}
			</ClayLink>
		) : (
			<ClayLink
				className="btn btn-secondary btn-sm"
				data-senna-off
				href="#"
				monospaced={Boolean(action.icon)}
				onClick={(event) => {
					handleAction(
						{
							event,
							itemId,
							method: action.data?.method,
							setLoading,
							url: action.href,
							...action,
						},
						context
					);
				}}
			>
				{content}
			</ClayLink>
		);
	}

	if (loading) {
		return (
			<ClayButton
				className="btn-sm"
				disabled
				displayType="secondary"
				monospaced
			>
				<ClayLoadingIndicator small />
			</ClayButton>
		);
	}

	const renderItems = (items) =>
		items.map(({items: nestedItems = [], separator, type, ...item}, i) => {
			if (type === 'group') {
				return (
					<ClayDropDown.Group {...item}>
						{separator && <ClayDropDown.Divider />}
						{renderItems(nestedItems)}
					</ClayDropDown.Group>
				);
			}

			return (
				<ActionItem
					{...item}
					closeMenu={() => setActive(false)}
					handleAction={handleAction}
					href={item.href && formatActionUrl(item.href, itemData)}
					key={i}
				/>
			);
		});

	return (
		<ClayDropDown
			active={active}
			onActiveChange={setActive}
			trigger={
				<ClayButton
					className="component-action dropdown-toggle"
					displayType="unstyled"
				>
					<ClayIcon symbol="ellipsis-v" />
				</ClayButton>
			}
		>
			<ClayDropDown.ItemList>
				{renderItems(formattedActions)}
			</ClayDropDown.ItemList>
		</ClayDropDown>
	);
}

ActionsDropdownRenderer.propTypes = {
	actions: PropTypes.arrayOf(
		PropTypes.shape({
			data: PropTypes.shape({
				method: PropTypes.oneOf(['get', 'delete']),
			}),
			href: PropTypes.string,
			icon: PropTypes.string,
			label: PropTypes.string.isRequired,
			onClick: PropTypes.string,
			permissionKey: PropTypes.string,
			target: PropTypes.oneOf([
				'modal',
				'sidePanel',
				'link',
				'async',
				'headless',
			]),
		})
	),
	itemData: PropTypes.object,
	itemId: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
};

export default ActionsDropdownRenderer;
