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

					switchLanguage(
						dataEngineReactComponentRef,
						selectedLanguageId
					);
				}
			);

			switchLanguage(dataEngineReactComponentRef, currentLanguageId, {
				preserveValue: true,
			});
		}
	);

	return {
		dispose() {
			if (localeChangedHandler) {
				localeChangedHandler.detach();
			}
		},
	};
}

function switchLanguage(
	dataEngineReactComponentRef,
	languageId,
	{preserveValue = false} = {}
) {
	if (dataEngineReactComponentRef?.current) {
		dataEngineReactComponentRef.current.updateEditingLanguageId({
			editingLanguageId: languageId,
			preserveValue,
		});
	}
}
