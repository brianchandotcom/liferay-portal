/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ScreenReaderAnnouncerContext} from '@liferay/layout-js-components-web';
import {sub} from 'frontend-js-web';
import React, {ComponentProps, useContext, useRef} from 'react';

import useActionValues from '../../../app/utils/useActionValues';
import RuleBuilderItem from './RuleBuilderItem';
import RuleSelect from './RuleSelect';

export interface Action {
	action?: 'fragment';
	id: string;
	itemId?: string;
	type: 'show' | 'hide' | undefined;
}

interface ActionProps {
	action: Action;
	layoutDataItems: {label: string; value: string}[];
	onActionChange: (action: Action) => void;
	onDeleteAction: () => void;
	showDeleteButton: boolean;
	wrapperRef?: ComponentProps<typeof RuleBuilderItem>['wrapperRef'];
}

export const ACTION_TYPE_ITEMS = [
	{
		label: Liferay.Language.get('show'),
		value: 'show',
	},

	{
		label: Liferay.Language.get('hide'),
		value: 'hide',
	},
] as const;

export const ACTION_ITEMS = [
	{
		label: Liferay.Language.get('fragment'),
		value: 'fragment',
	},
] as const;

export default function Action({
	action,
	layoutDataItems,
	onActionChange,
	onDeleteAction,
	showDeleteButton,
	wrapperRef,
}: ActionProps) {
	const {sendMessage} = useContext(ScreenReaderAnnouncerContext);

	const [{description}] = useActionValues({
		actions: [action],
		items: layoutDataItems,
	});

	const selectRef = useRef<HTMLButtonElement | undefined>();

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
				items={ACTION_TYPE_ITEMS}
				onSelectionChange={(type) => onActionChange({...action, type})}
				selectedKey={action.type}
				triggerRef={selectRef}
			/>

			{action.type ? (
				<RuleSelect
					aria-label={Liferay.Language.get(
						'select-item-for-the-action'
					)}
					items={ACTION_ITEMS}
					onSelectionChange={(selectedAction) =>
						onActionChange({
							...action,
							action: selectedAction,
							itemId: undefined,
						})
					}
					selectedKey={action.action}
				/>
			) : null}

			{action.action ? (
				<FragmentSelector
					itemId={action.itemId}
					layoutDataItems={layoutDataItems}
					onItemIdChanged={(itemId) => {
						onActionChange({
							...action,
							itemId,
						});

						sendMessage(Liferay.Language.get('action-completed'));
					}}
				/>
			) : null}
		</RuleBuilderItem>
	);
}

function FragmentSelector({
	itemId,
	layoutDataItems,
	onItemIdChanged,
}: {
	itemId: string | undefined;
	layoutDataItems: {label: string; value: string}[];
	onItemIdChanged: (itemId: string) => void;
}) {
	return (
		<RuleSelect
			aria-label={sub(
				Liferay.Language.get('select-x'),
				Liferay.Language.get('fragment')
			)}
			items={layoutDataItems}
			onSelectionChange={onItemIdChanged}
			selectedKey={itemId}
		/>
	);
}
