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

import DatasetDisplayContext from '../DatasetDisplayContext';
import {formatActionUrl} from '../utilities/index';
import DefaultContent from './DefaultRenderer';

function ActionLinkRenderer(props) {
	const {
		executeAsyncItemAction,
		highlightItems,
		openModal,
		openSidePanel,
	} = useContext(DatasetDisplayContext);

	if (!props.actions || !props.actions.length) {
		if (props.value) {
			return <DefaultContent value={props.value} />;
		}

		return null;
	}

	const currentAction =
		props.options && props.options.actionId
			? props.actions.find(
					(action) => action.id === props.options.actionId
			  )
			: props.actions[0];

	if (!currentAction) {
		return null;
	}

	const formattedHref =
		currentAction.href &&
		formatActionUrl(currentAction.href, props.itemData);

	function handleClickOnLink(e) {
		e.preventDefault();

		if (currentAction.target === 'modal') {
			openModal({
				size: currentAction.size || 'lg',
				title: currentAction.title,
				url: formattedHref,
			});
		}

		if (currentAction.target === 'sidePanel') {
			highlightItems([props.itemId]);
			openSidePanel({
				size: currentAction.size || 'lg',
				title: currentAction.title,
				url: formattedHref,
			});
		}

		if (currentAction.target === 'async') {
			executeAsyncItemAction(formattedHref, currentAction.method);
		}

		if (currentAction.onClick) {
			eval(currentAction.onClick);
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
				{props.value || <ClayIcon symbol={currentAction.icon} />}
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
			target: PropTypes.oneOf(['modal', 'sidePanel', 'link', 'async']),
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
