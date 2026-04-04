/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ChatbotItemTitle from './ChatbotItemTitle';

import type {
	IInternalRenderer,
	IItemsActions,
	IListSchema,
	IView,
} from '@liferay/frontend-data-set-web';

function applyStyles(itemsActions: Array<IItemsActions>): Array<IItemsActions> {
	return itemsActions.map((action: IItemsActions) => {
		const newItems = action.items ? applyStyles(action.items) : undefined;
		const itemsChanged = newItems !== action.items;

		if (!itemsChanged) {
			return action;
		}

		return {
			...action,
			...(itemsChanged && {items: newItems}),
		};
	});
}

export default function propsTransformer({itemsActions, ...otherProps}: any) {
	const customListTitleRenderer: IInternalRenderer = {
		component: ChatbotItemTitle,
		name: 'customListTitleRenderer',
		type: 'internal',
	};

	const views: Array<IView> = otherProps.views;

	const listView = views.find((view) => view.name === 'list')!;

	const listSchema = listView.schema as IListSchema;

	listView.setItemComponentProps = ({props}: {props: any}) => {
		const updatedProps = {
			...props,
			schema: {
				...listSchema,
				description: '',
				titleRendererName: 'customListTitleRenderer',
			},
		};

		return updatedProps;
	};

	return {
		...otherProps,
		customRenderers: {
			listSection: [customListTitleRenderer],
		},
		itemsActions: applyStyles(itemsActions),
		views,
	};
}
