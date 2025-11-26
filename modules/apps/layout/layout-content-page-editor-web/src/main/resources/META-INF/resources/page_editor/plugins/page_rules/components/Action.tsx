/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ScreenReaderAnnouncerContext} from '@liferay/layout-js-components-web';
import {sub} from 'frontend-js-web';
import React, {ComponentProps, useContext, useRef} from 'react';

import {useSelectorCallback} from '../../../app/contexts/StoreContext';
import isInputFragment from '../../../app/utils/isInputFragment';
import useActionValues from '../../../app/utils/useActionValues';
import RuleBuilderItem from './RuleBuilderItem';
import RuleSelect from './RuleSelect';

export interface Action {
	id: string;
	itemId?: string;
	readOnly?: boolean;
	type: 'show' | 'hide' | 'enable' | 'disable' | undefined;
}

interface ActionProps {
	action: Action;
	inputFragmentItems: {label: string; value: string}[];
	layoutDataItems: {label: string; value: string}[];
	onActionChange: (action: Action) => void;
	onDeleteAction: () => void;
	showDeleteButton: boolean;
	wrapperRef?: ComponentProps<typeof RuleBuilderItem>['wrapperRef'];
}

export const ACTION_TYPE_ITEMS = {
	disable: {
		label: Liferay.Language.get('disable'),
		value: 'disable',
	},
	enable: {
		label: Liferay.Language.get('enable'),
		value: 'enable',
	},
	hide: {
		label: Liferay.Language.get('hide'),
		value: 'hide',
	},
	show: {
		label: Liferay.Language.get('show'),
		value: 'show',
	},
} as const;

export const ACTION_ITEMS = [
	{
		label: Liferay.Language.get('fragment'),
		value: 'fragment',
	},
] as const;

export default function Action({
	action,
	inputFragmentItems,
	layoutDataItems,
	onActionChange,
	onDeleteAction,
	showDeleteButton,
	wrapperRef,
}: ActionProps) {
	const {sendMessage} = useContext(ScreenReaderAnnouncerContext);

	const [{description}] = useActionValues({
		actions: [action],
		items: [...layoutDataItems, ...inputFragmentItems],
	});

	const selectRef = useRef<HTMLButtonElement | undefined>();

	const actionTypes = useSelectorCallback(
		(state) => {
			if (action.readOnly && action.itemId) {
				const isFormFragment = isInputFragment(
					state.layoutData.items[action.itemId],
					state.fragmentEntryLinks
				);

				if (!isFormFragment) {
					return [ACTION_TYPE_ITEMS.hide, ACTION_TYPE_ITEMS.show];
				}
			}

			return Object.values(ACTION_TYPE_ITEMS);
		},
		[action.itemId]
	);

	const completeAction = !!action.itemId;

	return (
		<RuleBuilderItem
			aria-label={
				completeAction
					? description
					: Liferay.Language.get('incomplete-action')
			}
			description={description}
			onDeleteButtonClick={onDeleteAction}
			onItemSelected={() => {
				selectRef.current?.focus();
			}}
			showDeleteButton={showDeleteButton}
			type="action"
			wrapperRef={wrapperRef}
		>
			<RuleSelect
				aria-label={sub(
					Liferay.Language.get('select-x'),
					Liferay.Language.get('action')
				)}
				items={actionTypes}
				onSelectionChange={(type) => onActionChange({...action, type})}
				selectedKey={action.type}
				triggerRef={selectRef}
			/>

			{action.type ? (
				<FragmentSelector
					itemId={action.itemId}
					layoutDataItems={
						action.type === 'enable' || action.type === 'disable'
							? inputFragmentItems
							: layoutDataItems
					}
					onItemIdChanged={(itemId) => {
						onActionChange({
							...action,
							itemId,
						});

						sendMessage(Liferay.Language.get('action-completed'));
					}}
					readOnly={action.readOnly}
				/>
			) : null}
		</RuleBuilderItem>
	);
}

function FragmentSelector({
	itemId,
	layoutDataItems,
	onItemIdChanged,
	readOnly,
}: {
	itemId: string | undefined;
	layoutDataItems: {label: string; value: string}[];
	onItemIdChanged: (itemId: string) => void;
	readOnly?: boolean;
}) {
	const selectedKey = layoutDataItems.some((item) => item.value === itemId)
		? itemId
		: undefined;

	return (
		<RuleSelect
			aria-label={sub(
				Liferay.Language.get('select-x-for-the-action'),
				Liferay.Language.get('fragment')
			)}
			items={layoutDataItems}
			onSelectionChange={onItemIdChanged}
			readOnly={readOnly}
			selectedKey={selectedKey}
		/>
	);
}
