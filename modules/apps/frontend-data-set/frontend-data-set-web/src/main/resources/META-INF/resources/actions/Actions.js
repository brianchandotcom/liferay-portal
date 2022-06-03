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

import {openToast} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useContext, useState} from 'react';

import FrontendDataSetContext from '../FrontendDataSetContext';
import {ACTION_ITEM_TARGETS} from '../utils/actionItems/constants';
import {formatActionURL} from '../utils/index';
import {openPermissionsModal, resolveModalSize} from '../utils/modals/index';
import ViewsContext from '../views/ViewsContext';
import ActionsDropdown from './ActionsDropdown';
import QuickActions from './QuickActions';

const {MODAL_PERMISSIONS} = ACTION_ITEM_TARGETS;

const QUICK_ACTIONS_MAX_NUMBER = 3;

export function isLink(target, onClick) {
	return !(target && target !== 'link') && !onClick;
}

const formatActions = (actions, itemData) => {
	return actions
		? actions.reduce((actions, action) => {
				if (action.data?.permissionKey) {
					if (itemData.actions[action.data.permissionKey]) {
						if (action.target === 'headless') {
							return [
								...actions,
								{
									...action,
									...itemData.actions[
										action.data.permissionKey
									],
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
};

export function handleAction(
	{
		confirmationMessage,
		event,
		itemId,
		method,
		onClick,
		setLoading,
		size,
		successMessage,
		target,
		title,
		url,
	},
	{
		executeAsyncItemAction,
		highlightItems,
		openModal,
		openSidePanel,
		toggleItemInlineEdit,
	}
) {
	if (confirmationMessage && !confirm(confirmationMessage)) {
		return;
	}

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
		executeAsyncItemAction(url, method)
			.then(() => {
				openToast({
					message:
						successMessage ||
						Liferay.Language.get('action-completed'),
					type: 'success',
				});
				setLoading(false);
			})
			.catch((_) => {
				setLoading(false);
			});
	}
	else if (target === 'inlineEdit') {
		event.preventDefault();

		toggleItemInlineEdit(itemId);
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
function Actions({actions, itemData, itemId}) {
	const context = useContext(FrontendDataSetContext);
	const {inlineEditingSettings, onActionDropdownItemClick} = context;

	const [loading, setLoading] = useState(false);

	const [
		{
			activeView: {options},
		},
	] = useContext(ViewsContext);

	const {quickActionsEnabled} = options || {};

	const inlineEditingAvailable =
		inlineEditingSettings && itemData.actions?.update;
	const inlineEditingAlwaysOn =
		inlineEditingAvailable && inlineEditingSettings.alwaysOn;

	const formattedActions = formatActions(actions, itemData);

	if (inlineEditingAvailable && !inlineEditingAlwaysOn) {
		formattedActions.unshift({
			icon: 'fieldset',
			label: Liferay.Language.get('inline-edit'),
			target: 'inlineEdit',
		});
	}

	const handleClick = ({
		action,
		closeMenu,
		event,
		itemData,
		itemId,
		size = 'lg',
	}) => {
		if (onActionDropdownItemClick) {
			onActionDropdownItemClick({
				action,
				event,
				itemData,
			});
		}

		if (!isLink(action.target, action.onClick)) {
			event.preventDefault();

			const {data, onClick, target} = action;

			handleAction(
				{
					confirmationMessage: data?.confirmationMessage,
					event,
					itemId,
					method: action.method ?? action.data?.method,
					onClick,
					setLoading,
					size,
					successMessage: data?.successMessage,
					target,
					url: formatActionURL(action.href, itemData),
				},
				context
			);
		}

		if (closeMenu) {
			closeMenu();
		}
	};

	return (
		<>
			{quickActionsEnabled && formattedActions.length > 1 && (
				<QuickActions
					actions={formattedActions.slice(
						0,
						QUICK_ACTIONS_MAX_NUMBER
					)}
					itemData={itemData}
					onClick={handleClick}
				/>
			)}
			<ActionsDropdown
				actions={formattedActions}
				handleAction={handleAction}
				itemData={itemData}
				itemId={itemId}
				loading={loading}
				onClick={handleClick}
				setLoading={setLoading}
			/>
		</>
	);
}

const actionType = PropTypes.shape({
	data: PropTypes.shape({
		confirmationMessage: PropTypes.string,
		method: PropTypes.oneOf(['delete', 'get', 'patch', 'post']),
		permissionKey: PropTypes.string,
		successMessage: PropTypes.string,
	}),
	href: PropTypes.string,
	icon: PropTypes.string,
	label: PropTypes.string.isRequired,
	method: PropTypes.oneOf(['delete', 'get', 'patch', 'post']),
	onClick: PropTypes.string,
	target: PropTypes.oneOf([
		'async',
		'headless',
		'inlineEdit',
		'link',
		'modal',
		'modal-full-screen',
		'modal-lg',
		'modal-permissions',
		'modal-sm',
		'sidePanel',
	]),
});

export const actionsBasePropTypes = {
	actions: PropTypes.arrayOf(actionType),
	itemData: PropTypes.object,
	itemId: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
};

Actions.propTypes = actionsBasePropTypes;

export default Actions;
