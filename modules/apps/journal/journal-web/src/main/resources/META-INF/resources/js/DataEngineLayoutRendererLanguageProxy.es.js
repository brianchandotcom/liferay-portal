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

export default function ({currentLanguageId, namespace}) {
	let localeChangedHandler = null;
	let defaultLocaleChangedHandler = null;

	Liferay.componentReady(`${namespace}dataEngineLayoutRenderer`).then(
		(dataEngineLayoutRenderer) => {
			const dataEngineReactComponentRef =
				dataEngineLayoutRenderer?.reactComponentRef;

			localeChangedHandler = Liferay.after(
				'inputLocalized:localeChanged',
				(event) => {
					const selectedLanguageId = event.item.getAttribute(
						'data-value'
					);

					switchLanguage({
						dataEngineReactComponentRef,
						languageId: selectedLanguageId,
						namespace,
					});
				}
			);

			defaultLocaleChangedHandler = Liferay.after(
				'inputLocalized:defaultLocaleChanged',
				(event) => {
					const selectedLanguageId = event.item.getAttribute(
						'data-value'
					);

					const defaultLanguageIdInput = document.getElementById(
						`${namespace}defaultLanguageId`
					);

					defaultLanguageIdInput.value = selectedLanguageId;
				}
			);

			switchLanguage({
				dataEngineReactComponentRef,
				languageId: currentLanguageId,
				namespace,
				preserveValue: true,
			});
		}
	);

	return {
		dispose() {
			if (localeChangedHandler) {
				localeChangedHandler.detach();
			}

			if (defaultLocaleChangedHandler) {
				defaultLocaleChangedHandler.detach();
			}
		},
	};
}

function switchLanguage({
	dataEngineReactComponentRef,
	languageId,
	namespace,
	preserveValue = false,
}) {
	if (dataEngineReactComponentRef?.current) {
		const defaultLanguageIdInput = document.getElementById(
			`${namespace}defaultLanguageId`
		);

		dataEngineReactComponentRef.current.updateEditingLanguageId({
			defaultLanguageId: defaultLanguageIdInput.value,
			editingLanguageId: languageId,
			preserveValue,
		});
	}
}
