/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import {fetch, objectToFormData, runScriptsInElement} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

export function VariationPreview({
	fragmentEntryKey,
	label,
	namespace,
	previewURL,
	showLabel,
	variation,
}) {
	const [element, setElement] = useState(null);
	const [visible, setVisible] = useState(false);

	useEffect(() => {
		setVisible(false);

		if (!element) {
			return;
		}

		if (!('IntersectionObserver' in window)) {
			setVisible(true);

			return;
		}

		const intersectionObserver = new IntersectionObserver((entries) => {
			const visible = entries.some(
				(entry) => entry.isIntersecting && entry.target === element
			);

			if (visible) {
				setVisible(true);
				intersectionObserver.disconnect();
			}
		});

		intersectionObserver.observe(element);

		return () => {
			intersectionObserver.disconnect();
		};
	}, [element]);

	useEffect(() => {
		if (!element || !visible) {
			return;
		}

		const loadingIndicator = document.createElement('span');
		loadingIndicator.setAttribute('aria-hidden', 'true');
		loadingIndicator.className = 'flex-grow-0 loading-animation m-0';

		element.appendChild(loadingIndicator);

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
				element.innerHTML = data;

				runScriptsInElement(element);
			})
			.catch((error) => {
				console.error(error);
				element.innerHTML = '';
			});
	}, [element, fragmentEntryKey, namespace, previewURL, variation, visible]);

	return (
		<article className="d-flex flex-column-reverse">
			<div className="cadmin">
				<h4
					className={classNames('mb-0 mt-2 text-secondary', {
						'sr-only': !showLabel,
					})}
				>
					{label}
				</h4>
			</div>

			<div
				className="align-items-center d-flex flex-grow-1 justify-content-center overflow-hidden p-4 variation-preview__content"
				ref={setElement}
			/>
		</article>
	);
}
