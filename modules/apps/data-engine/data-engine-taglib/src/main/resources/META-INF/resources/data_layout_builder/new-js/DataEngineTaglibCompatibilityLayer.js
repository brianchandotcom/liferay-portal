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

import {useConfig, useForm, useFormState} from 'data-engine-js-components-web';
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
		const {dataDefinition, dataLayout} = this[SYMBOL_INTERNAL];

		return {
			dataDefinition,
			dataLayout,
		};
	}
}

/**
 * DataEngineTaglibCompatibilityLayer exposes the `state` and `dispatch` of the
 * application to be accessible via Liferay.componentReady, this implementation
 * is only for the use case of modules that use the data engine via taglib
 */
export const DataEngineTaglibCompatibilityLayer = () => {
	const {dataLayoutBuilderId} = useConfig();
	const dispatch = useForm();

	const {dataDefinition, dataLayout} = useFormState({
		schema: ['dataDefinition', 'dataLayout'],
	});

	const dataEngineCompatibilityLayerRef = useRef(null);

	useEffect(() => {
		dataEngineCompatibilityLayerRef.current = new DataEngineCompatibilityLayer(
			{
				dataDefinition,
				dataLayout,
				dispatch,
			}
		);
	}, [dataDefinition, dataLayout, dispatch]);

	useEffect(() => {
		Liferay.component(
			dataLayoutBuilderId,
			dataEngineCompatibilityLayerRef,
			{
				destroyOnNavigate: true,
			}
		);

		return () => {
			Liferay.destroyComponent(dataLayoutBuilderId);
		};
	}, [dataEngineCompatibilityLayerRef, dataLayoutBuilderId]);

	return null;
};
