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

import {fetch} from 'frontend-js-web';

export default function ({batchEngineImportTaskId, namespace}) {
	document
		.getElementById(
			`${namespace}downloadImportFile${batchEngineImportTaskId}`
		)
		.addEventListener('click', (event) => {
			event.preventDefault();
			fetch(
				`/o/headless-batch-engine/v1.0/import-task/${batchEngineImportTaskId}/content`
			).then((response) => {
				response.blob().then((blob) => {
					const LinkElement = document.createElement('a');

					LinkElement.href = URL.createObjectURL(blob);

					const fileName = response.headers
						.get('Content-Disposition')
						.match(/filename=(.*)/)[1];

					LinkElement.download = fileName;

					document.body.appendChild(LinkElement);

					LinkElement.click();

					LinkElement.remove();
				});
			});
		});
}
