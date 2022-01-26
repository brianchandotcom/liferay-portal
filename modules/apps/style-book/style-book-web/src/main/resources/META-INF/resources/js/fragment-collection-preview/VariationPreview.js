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

import {fetch, objectToFormData} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

export function VariationPreview({
	fragmentEntryKey,
	label,
	namespace,
	previewURL,
	variation,
}) {
	const [html, setHTML] = useState('');

	useEffect(() => {
		fetch(previewURL, {
			body: objectToFormData({
				[`_${namespace}_configurationValues`]: JSON.stringify(
					variation.reduce(
						(configurationValues, {name, value}) => ({
							...configurationValues,
							[name]: value,
						}),
						{}
					)
				),
			}),
			method: 'POST',
		})
			.then((response) => response.text())
			.then((data) => {
				setHTML(data);
			})
			.catch((error) => {
				console.error(error);
				setHTML('');
			});
	}, [fragmentEntryKey, namespace, previewURL, variation]);

	return (
		<article className="d-flex flex-column-reverse">
			<h4 className="mb-0 mt-2 text-secondary">{label}</h4>

			<div
				className="align-items-center d-flex flex-grow-1 justify-content-center overflow-hidden p-4 variation-preview__content"
				dangerouslySetInnerHTML={{__html: html}}
			/>
		</article>
	);
}
