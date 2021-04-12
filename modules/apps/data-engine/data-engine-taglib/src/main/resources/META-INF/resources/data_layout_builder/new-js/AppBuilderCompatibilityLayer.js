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

import {
	useConfig,
	useForm,
	useFormState,
} from 'dynamic-data-mapping-form-renderer';
import {useEffect, useRef} from 'react';

const SYMBOL_INTERNAL = Symbol('data.engine.internal');

class DataEngineCompatibilityLayer {
	constructor(props) {
		this[SYMBOL_INTERNAL] = props;
	}

	get dispatch() {
		return this[SYMBOL_INTERNAL].dispatch;
	}

	get state() {
		const {
			dataDefinition,
			dataLayout,
			editingLanguageId,
			focusedField,
		} = this[SYMBOL_INTERNAL];

		return {
			dataDefinition,
			dataLayout,
			editingLanguageId,
			focusedField,
		};
	}

	get config() {
		return this[SYMBOL_INTERNAL].config;
	}
}

/**
 * AppBuilderCompatibilityLayer exposes the `configs`, `state` and `dispatch` of the
 * application to be accessible via Liferay.componentReady, this implementation
 * is only for the use case of the App Builder that is frozen.
 */
export const AppBuilderCompatibilityLayer = () => {
	const {dataLayoutBuilderId, ...config} = useConfig();
	const dispatch = useForm();

	const {dataDefinition, dataLayout} = useFormState({
		schema: ['dataDefinition', 'dataLayout'],
	});

	const {editingLanguageId, focusedField} = useFormState();

	const dataEngineCompatibilityLayerRef = useRef(null);
	const onReferenceRef = useRef(null);

	useEffect(() => {
		dataEngineCompatibilityLayerRef.current = new DataEngineCompatibilityLayer(
			{
				config,
				dataDefinition,
				dataLayout,
				dispatch,
				editingLanguageId,
				focusedCustomObjectField: focusedField,
				focusedField,
			}
		);

		if (onReferenceRef.current) {
			onReferenceRef.current();
		}
	}, [
		editingLanguageId,
		config,
		dataDefinition,
		dataLayout,
		dispatch,
		focusedField,
	]);

	useEffect(() => {
		Liferay.component(
			dataLayoutBuilderId,
			{
				...dataEngineCompatibilityLayerRef,
				onReference: (callback) => {
					onReferenceRef.current = callback;
				},
			},
			{
				destroyOnNavigate: true,
			}
		);

		return () => {
			Liferay.destroyComponent(dataLayoutBuilderId);
		};
	}, [dataEngineCompatibilityLayerRef, onReferenceRef, dataLayoutBuilderId]);

	return null;
};
