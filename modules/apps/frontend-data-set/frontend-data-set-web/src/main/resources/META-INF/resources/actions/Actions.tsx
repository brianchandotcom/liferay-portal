/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {IItemsActions} from '..';
import {navigate, openConfirmModal} from 'frontend-js-web';
import React, {useContext, useState} from 'react';

import FrontendDataSetContext, {
	IFrontendDataSetContext,
} from '../FrontendDataSetContext';
import {ACTION_ITEM_TARGETS} from '../utils/actionItems/constants';
import filterItemActions from '../utils/actionItems/filterItemActions';
import {formatActionURL} from '../utils/actionItems/formatActionURL';
import {openPermissionsModal} from '../utils/modals/openPermissionsModal';
import {resolveModalSize} from '../utils/modals/resolveModalSize';

// @ts-ignore

import ViewsContext from '../views/ViewsContext';

// @ts-ignore

import ActionsDropdown from './ActionsDropdown';
import QuickActions from './QuickActions';

const {MODAL_PERMISSIONS} = ACTION_ITEM_TARGETS;

const QUICK_ACTIONS_MAX_NUMBER = 3;

function Actions({
	actions,
	itemData,
	itemId,
	menuActive,
	onMenuActiveChange,
}: {
	actions: Array<IItemsActions>;
	itemData: any;
	itemId: string | number;
	menuActive: boolean;
	onMenuActiveChange: Function;
}) {
	const {
		executeAsyncItemAction,
		highlightItems,
		inlineEditingSettings,
		loadData,
		onActionDropdownItemClick,
		openModal,
		openSidePanel,
		toggleItemInlineEdit,
	}: IFrontendDataSetContext = useContext(FrontendDataSetContext);

	const [
		{
			activeView: {quickActionsEnabled},
		},
	]: any = useContext(ViewsContext);

	const [loading, setLoading] = useState(false);

	const inlineEditingAvailable =
		inlineEditingSettings && itemData.actions?.update;
	const inlineEditingAlwaysOn =
		inlineEditingAvailable && inlineEditingSettings.alwaysOn;

	const formattedActions = filterItemActions(actions, itemData);

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
	}: {
		action: IItemsActions;
		closeMenu: any;
		event: any;
	}) => {
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

		const doAction = ({defaultPrevented}: {defaultPrevented: boolean}) => {
			if (target?.includes('modal')) {
				event.preventDefault();

				if (target === MODAL_PERMISSIONS) {
					openPermissionsModal(url);
				}
				else {
					openModal!({
						size: size || resolveModalSize(target),
						title,
						url,
					});
				}
			}
			else if (target === 'sidePanel') {
				event.preventDefault();

				highlightItems!([itemId]);

				openSidePanel!({
					size: 'lg',
					title,
					url,
				});
			}
			else if (target === 'async' || target === 'headless') {
				event.preventDefault();

				setLoading(true);

				executeAsyncItemAction!({
					errorMessage,
					method: method ?? data?.method,
					setActionItemLoading: setLoading,
					successMessage,
					url,
				});
			}
			else if (target === 'inlineEdit') {
				event.preventDefault();

				toggleItemInlineEdit!(itemId);
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
			doAction({defaultPrevented: false});
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
					itemId={itemId}
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

export default Actions;
