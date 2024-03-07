/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {State} from '@liferay/frontend-js-state-web';
import {
	EVENT_TYPES,
	useConfig,
	useForm,
	useFormState,
} from 'data-engine-js-components-web';
import {activeLanguageIdsAtom} from 'frontend-js-components-web';
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
export function DataEngineTaglibCompatibilityLayer() {
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

	useEffect(() => {
		const {dispose} = State.subscribe(
			activeLanguageIdsAtom,
			(activeLanguageIds) => {
				dispatch({
					payload: {activeLanguageIds},
					type: EVENT_TYPES.LANGUAGE.UPDATE,
				});
			}
		);

		return dispose;
	}, [dispatch]);

	return null;
}
