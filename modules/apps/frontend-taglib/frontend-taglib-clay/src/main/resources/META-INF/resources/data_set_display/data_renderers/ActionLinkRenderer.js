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

import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import PropTypes from 'prop-types';
import React, {useContext} from 'react';

import DataSetDisplayContext from '../DataSetDisplayContext';
import {formatActionUrl} from '../utilities/index';
import DefaultContent from './DefaultRenderer';

function ActionLinkRenderer({actions, itemData, itemId, options, value}) {
	const {
		executeAsyncItemAction,
		highlightItems,
		openModal,
		openSidePanel,
	} = useContext(DataSetDisplayContext);

	if (!actions || !actions.length) {
		return value ? <DefaultContent value={value} /> : null;
	}

	let currentAction = options?.actionId
		? actions.find((action) => action.id === options.actionId)
		: actions[0];

	if (!currentAction) {
		return null;
	}

	if (currentAction.permissionKey) {
		if (itemData.actions[currentAction.permissionKey]) {
			if (currentAction.target === 'headless') {
				currentAction = {
					...currentAction,
					...itemData.actions[currentAction.id],
				};
			}
		}
		else {
			return value ? <DefaultContent value={value} /> : null;
		}
	}

	const formattedHref =
		currentAction.href && formatActionUrl(currentAction.href, itemData);

	function handleClickOnLink(event) {
		if (currentAction.target === 'modal') {
			event.preventDefault();

			openModal({
				size: currentAction.size || 'lg',
				title: currentAction.title,
				url: formattedHref,
			});
		}
		else if (currentAction.target === 'sidePanel') {
			event.preventDefault();

			highlightItems([itemId]);
			openSidePanel({
				size: currentAction.size || 'lg',
				title: currentAction.title,
				url: formattedHref,
			});
		}
		else if (
			currentAction.target === 'async' ||
			currentAction.target === 'headless'
		) {
			event.preventDefault();

			executeAsyncItemAction(formattedHref, currentAction.method);
		}
		else if (currentAction.onClick) {
			event.preventDefault();

			event.target.setAttribute('onClick', currentAction.onClick);
			event.target.onclick();
			event.target.removeAttribute('onClick');
		}
	}

	function isNotALink() {
		return Boolean(
			(currentAction.target && currentAction.target !== 'link') ||
				currentAction.onClick
		);
	}

	return (
		<div className="table-list-title">
			<ClayLink
				data-senna-off
				href={formattedHref || '#'}
				onClick={isNotALink() ? handleClickOnLink : null}
			>
				{value || <ClayIcon symbol={currentAction.icon} />}
			</ClayLink>
		</div>
	);
}

ActionLinkRenderer.propTypes = {
	actions: PropTypes.arrayOf(
		PropTypes.shape({
			disabled: PropTypes.bool,
			href: PropTypes.string,
			icon: PropTypes.string,
			method: PropTypes.oneOf(['get', 'delete']),
			onClick: PropTypes.string,
			size: PropTypes.string,
			target: PropTypes.oneOf([
				'modal',
				'sidePanel',
				'link',
				'async',
				'headless',
			]),
			title: PropTypes.string,
		})
	),
	itemData: PropTypes.object,
	itemId: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
	options: PropTypes.shape({
		actionId: PropTypes.string,
	}),
	value: PropTypes.string,
};

export default ActionLinkRenderer;
