/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {navigate, openConfirmModal} from 'frontend-js-web';
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

function Actions({actions, itemData, itemId, menuActive, onMenuActiveChange}) {
	const {
		executeAsyncItemAction,
		highlightItems,
		inlineEditingSettings,
		loadData,
		onActionDropdownItemClick,
		openModal,
		openSidePanel,
		toggleItemInlineEdit,
	} = useContext(FrontendDataSetContext);

	const [
		{
			activeView: {quickActionsEnabled},
		},
	] = useContext(ViewsContext);

	const [loading, setLoading] = useState(false);

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

	const handleClick = ({action, closeMenu, event}) => {
		const {data, href, method, onClick, target} = action;

		const {
			confirmationMessage,
			errorMessage,
			size,
			status,
			successMessage,
			title,
		} = data ?? {};

		const url = formatActionURL(href, itemData);

		const doAction = ({defaultPrevented}) => {
			if (target?.includes('modal')) {
				event.preventDefault();

				if (target === MODAL_PERMISSIONS) {
					openPermissionsModal(url);
				}
				else {
					openModal({
						size: size || resolveModalSize(target),
						title,
						url,
					});
				}
			}
			else if (target === 'sidePanel') {
				event.preventDefault();

				highlightItems([itemId]);

				openSidePanel({
					size: 'lg',
					title,
					url,
				});
			}
			else if (target === 'async' || target === 'headless') {
				event.preventDefault();

				setLoading(true);

				executeAsyncItemAction({
					errorMessage,
					method: method ?? data?.method,
					setActionItemLoading: setLoading,
					successMessage,
					url,
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

			const exposedProps = {
				action,
				event,
				itemData,
				loadData,
				openSidePanel,
			};

			if (onClick) {
				onClick(exposedProps);
			}

			if (onActionDropdownItemClick) {
				onActionDropdownItemClick(exposedProps);
			}

			if (target === 'link' && defaultPrevented) {
				navigate(url);
			}
		};

		if (confirmationMessage) {
			let defaultPrevented = false;

			if (target === 'link') {
				event.preventDefault();

				defaultPrevented = true;
			}

			openConfirmModal({
				message: confirmationMessage,
				onConfirm: (isConfirmed) => {
					if (isConfirmed) {
						doAction({defaultPrevented});
					}
				},
				status,
				title,
			});
		}
		else {
			doAction();
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
				itemData={itemData}
				itemId={itemId}
				loading={loading}
				menuActive={menuActive}
				onClick={handleClick}
				onMenuActiveChange={onMenuActiveChange}
				setLoading={setLoading}
			/>
		</>
	);
}

const actionType = PropTypes.shape({
	data: PropTypes.shape({
		confirmationMessage: PropTypes.string,
		errorMessage: PropTypes.string,
		method: PropTypes.oneOf(['delete', 'get', 'patch', 'post']),
		permissionKey: PropTypes.string,
		size: PropTypes.oneOf(['sm', 'lg', 'full-screen']),
		successMessage: PropTypes.string,
		title: PropTypes.string,
	}),
	href: PropTypes.string,
	icon: PropTypes.string,
	label: PropTypes.string.isRequired,
	method: PropTypes.oneOf(['delete', 'get', 'patch', 'post']),
	onClick: PropTypes.func,
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
