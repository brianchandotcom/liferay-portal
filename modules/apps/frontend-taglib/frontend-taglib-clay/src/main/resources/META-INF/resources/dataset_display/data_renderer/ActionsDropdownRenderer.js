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

import DatasetDisplayContext from '../DatasetDisplayContext';
import {ACTION_ITEM_TARGETS} from '../utilities/actionItems/constants';
import {formatActionUrl} from '../utilities/index';
import {
	openPermissionsModal,
	resolveModalSize,
} from '../utilities/modals/index';

const {MODAL_PERMISSIONS} = ACTION_ITEM_TARGETS;

function isNotALink(target, onClick) {
	return Boolean((target && target !== 'link') || onClick);
}

function ActionItem(props) {
	function handleClickOnLink(e) {
		e.preventDefault();

		props.handleAction({
			method: props.method,
			onClick: props.onClick,
			size: props.size || 'lg',
			target: props.target,
			title: props.title,
			url: props.href,
		});

		props.closeMenu();
	}

	return (
		<ClayDropDown.Item
			data-senna-off
			href={props.href || '#'}
			onClick={
				isNotALink(props.target, props.onClick)
					? handleClickOnLink
					: null
			}
		>
			{props.icon && (
				<span className="pr-2">
					<ClayIcon symbol={props.icon} />
				</span>
			)}
			{props.label}
		</ClayDropDown.Item>
	);
}

function ActionsDropdownRenderer(props) {
	const {
		executeAsyncItemAction,
		highlightItems,
		openModal,
		openSidePanel,
	} = useContext(DatasetDisplayContext);

	const [active, setActive] = useState(false);
	const [loading, setLoading] = useState(false);

	function handleAction({
		method = '',
		onClick = '',
		size = '',
		target = '',
		title = '',
		url = '',
	}) {
		if (!!target && target.includes('modal')) {
			switch (target) {
				case MODAL_PERMISSIONS:
					openPermissionsModal(url);
					break;
				default:
					openModal({
						size: resolveModalSize(target),
						title,
						url,
					});
					break;
			}
		}

		if (target === 'sidePanel') {
			highlightItems([props.itemId]);
			openSidePanel({
				size: size || 'lg',
				title,
				url,
			});
		}

		if (target === 'async') {
			setLoading(true);
			executeAsyncItemAction(url, method).then(() => setLoading(false));
		}

		if (onClick && typeof window[onClick] === 'function') {
			window[onClick]();
		}
	}

	if (!props.actions || !props.actions.length) {
		return null;
	}

	if (props.actions.length === 1) {
		const action = props.actions[0];
		const formattedHref = formatActionUrl(action.href, props.itemData);

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

		return isNotALink(action.target, action.onClick) ? (
			<ClayLink
				className="btn btn-secondary btn-sm"
				data-senna-off
				href="#"
				monospaced={Boolean(action.icon)}
				onClick={(e) => {
					e.preventDefault();

					return handleAction({
						method: action.method,
						onClick: action.onClick,
						size: action.size,
						target: action.target,
						title: action.title,
						url: action.href,
					});
				}}
			>
				{content}
			</ClayLink>
		) : (
			<ClayLink
				className="btn btn-secondary btn-sm"
				href={formattedHref}
				monospaced={Boolean(action.icon)}
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
				<ClayDropDown.Group>
					{props.actions.map((action, i) => {
						return (
							<ActionItem
								key={i}
								{...action}
								closeMenu={() => setActive(false)}
								handleAction={handleAction}
								href={
									action.href &&
									formatActionUrl(action.href, props.itemData)
								}
							/>
						);
					})}
				</ClayDropDown.Group>
			</ClayDropDown.ItemList>
		</ClayDropDown>
	);
}

ActionsDropdownRenderer.propTypes = {
	actions: PropTypes.arrayOf(
		PropTypes.shape({
			href: PropTypes.string,
			icon: PropTypes.string,
			label: PropTypes.string.isRequired,
			method: PropTypes.oneOf(['get', 'delete']),
			onClick: PropTypes.string,
			target: PropTypes.oneOf(['modal', 'sidePanel', 'link', 'async']),
		})
	),
	itemData: PropTypes.object,
	itemId: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
};

export default ActionsDropdownRenderer;
