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

import {ClayIconSpriteContext} from '@clayui/icon';
import {DragLayer} from 'data-engine-taglib';
import React from 'react';
import {DndProvider} from 'react-dnd';
import {HTML5Backend} from 'react-dnd-html5-backend';

import {MAPPED_EVENT_TYPES} from '../../core/actions/eventTypes.es';
import Pages from '../../core/components/Pages.es';
import {INITIAL_CONFIG_STATE} from '../../core/config/initialConfigState.es';
import {INITIAL_STATE} from '../../core/config/initialState.es';
import {ConfigProvider} from '../../core/hooks/useConfig.es';
import {FormNoopProvider} from '../../core/hooks/useForm.es';
import {getConnectedReactComponentAdapter} from '../../utils/ReactComponentAdapter.es';
import {parseProps} from '../../utils/parseProps.es';

/**
 * Render a form just for preview without actions/reducer just with FormNoopProvider,
 * this component is for compatibility with the FormBuilder in Metal.js.
 */
const LegacyFormBuilder = React.forwardRef(
	({instance, spritemap, ...otherProps}, ref) => {
		const {config, state} = parseProps({spritemap, ...otherProps});

		return (
			<ClayIconSpriteContext.Provider value={spritemap}>
				<DndProvider backend={HTML5Backend} context={window}>
					<ConfigProvider
						config={config}
						initialConfig={INITIAL_CONFIG_STATE}
					>
						<FormNoopProvider
							initialState={INITIAL_STATE}
							onAction={({payload, type}) =>
								instance?.context.dispatch(
									MAPPED_EVENT_TYPES[type] ?? type,
									payload
								)
							}
							value={state}
						>
							<DragLayer />
							<Pages {...state} ref={ref} />
						</FormNoopProvider>
					</ConfigProvider>
				</DndProvider>
			</ClayIconSpriteContext.Provider>
		);
	}
);

LegacyFormBuilder.displayName = 'LegacyFormBuilder';

export default getConnectedReactComponentAdapter(LegacyFormBuilder);
