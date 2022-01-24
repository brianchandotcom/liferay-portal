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

export default function ({namespace}) {
	const radioInputs = document.querySelectorAll('input[name="selectFile"]');
	radioInputs.forEach((element) => {
		element.addEventListener('change', (event) => {
			const item = event.target.value;

			const showUploadFromComputer = document.getElementById(
				`${namespace}fileUpload`
			);

			if (item === 'server') {
				showUploadFromComputer.style.display = 'none';
			}
			else {
				showUploadFromComputer.style.display = 'block';
			}
		});
	});
}
