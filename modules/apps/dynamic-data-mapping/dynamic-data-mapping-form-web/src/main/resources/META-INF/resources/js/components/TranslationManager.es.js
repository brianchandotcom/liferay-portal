/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	EVENT_TYPES as CORE_EVENT_TYPES,
	useForm,
} from 'data-engine-js-components-web';
import {useEffect} from 'react';

import {EVENT_TYPES} from '../eventTypes.es';

export function TranslationManager() {
	const dispatch = useForm();

	useEffect(() => {
		const translationManager = document.querySelector(
			'.ddm-translation-manager'
		);

		if (translationManager) {
			translationManager.classList.remove('hide');
		}

		return () => {
			if (translationManager) {
				translationManager.classList.add('hide');
			}
		};
	}, []);

	useEffect(() => {
		let handles = [];

		const getComponent = async () => {
			const translationManager = await Liferay.componentReady(
				'translationManager'
			);

			handles = [
				translationManager.on('editingLocale', ({newValue}) => {
					dispatch({
						payload: {editingLanguageId: newValue},
						type: CORE_EVENT_TYPES.LANGUAGE.CHANGE,
					});
					dispatch({
						payload: {languageId: newValue},
						type: CORE_EVENT_TYPES.LANGUAGE.ADD,
					});
				}),
				translationManager.on(
					'availableLocales',
					({newValue, previousValue}) => {
						const languageIds = [];

						previousValue.forEach((value, key) => {
							if (!newValue.has(key)) {
								languageIds.push(key);
							}
						});

						if (!languageIds.length) {
							return;
						}

						dispatch({
							payload: {languageIds},
							type: CORE_EVENT_TYPES.LANGUAGE.DELETE,
						});
						dispatch({
							payload: {languageId: languageIds[0]},
							type: EVENT_TYPES.FORM_INFO.LANGUAGE_DELETE,
						});
					}
				),
			];
		};

		getComponent();

		return () => {
			handles.forEach((handle) => handle.detach());
		};
	}, [dispatch]);

	return null;
}
