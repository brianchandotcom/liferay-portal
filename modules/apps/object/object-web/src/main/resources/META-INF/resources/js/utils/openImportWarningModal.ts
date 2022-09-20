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

export function openImportWarningModal({handleImport}: IProps) {
	openModal({
		bodyHTML: `
			<div class="text-secondary">
				<p>
					${Liferay.Language.get(
						'there-is-an-object-definition-with-the-same-external-reference-code-as-the-imported-one'
					)}
				</p>

				<p>
					${Liferay.Language.get(
						'before-importing-the-new-object-definition-you-may-want-to-back-up-its-entries-to-prevent-data-loss'
					)}
				</p>

				<p>
					${Liferay.Language.get('do-you-want-to-proceed-with-the-import-process')}
				</p>
			</div>
		`,
		buttons: [
			{
				displayType: 'secondary',
				label: Liferay.Language.get('cancel'),
				type: 'cancel',
			},
			{
				displayType: 'warning',
				label: Liferay.Language.get('continue'),
				onClick: ({processClose}: ClickEvent) => {
					processClose();
					handleImport();
				},
			},
		],
		center: true,
		status: 'warning',
		title: Liferay.Language.get('update-existing-object-definition'),
	});
}

interface IProps {
	handleImport: () => void;
}

interface ClickEvent {
	processClose: () => void;
}
