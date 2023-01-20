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

import {openModal} from 'frontend-js-web';

import SampleCustomDataRenderer from './SampleCustomDataRenderer';

export default function propsTransformer({
	additionalProps: {greeting},
	selectedItemsKey,
	...otherProps
}) {
	return {
		...otherProps,
		customDataRenderers: {
			sampleCustomDataRenderer: SampleCustomDataRenderer,
		},
		onActionDropdownItemClick({action, itemData, loadData, openSidePanel}) {
			if (action.data.id === 'sampleMessage') {
				alert(`${greeting} ${itemData.title}!`);
			}
			else if (action.data.id === 'reload') {
				loadData();
			}
			else if (action.data.id === 'openSidePanel') {
				openSidePanel({url: 'about:blank'});
			}
		},
		onBulkActionItemClick({
			action,
			loadData,
			selectedData: {items, keyValues},
		}) {
			if (action.data.id === 'sampleBulkAction') {
				openModal({
					bodyHTML: `
						<ol>
							${items.map((item) => `<li>${item.title}</li>`).join('')}
						</ol>

						<p>
							Key field: <code>${selectedItemsKey}</code> <br>
							Values of key fields of selected items:
							<ol>
								${keyValues.map((keyValue) => `<li>${keyValue}</li>`).join('')}
							</ol>
						</p>
					`,
					buttons: [
						{
							label: 'OK',
							onClick: ({processClose}) => {
								processClose();

								loadData();
							},
						},
					],
					size: 'md',
					title: 'You selected:',
				});
			}
		},
	};
}
