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

import {useEffect} from 'react';

import {EVENT_TYPES as CORE_EVENT_TYPES} from '../../../core/actions/eventTypes.es';
import {useForm} from '../../../core/hooks/useForm.es';
import {EVENT_TYPES} from '../eventTypes.es';

export const TranslationManager = () => {
	const dispatch = useForm();

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

						if (languageIds.length === 0) {
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
};
