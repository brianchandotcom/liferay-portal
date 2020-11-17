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
import {getConnectedReactComponentAdapter} from 'dynamic-data-mapping-form-renderer';
import Soy from 'metal-soy';
import React, {forwardRef} from 'react';

import RuleList from './RuleList.es';
import templates from './RuleListReactAdapter.soy';

/**
 * This creates a compatibility layer for the Field component on React, allowing
 * it to be called by Metal+Soy file.
 */
export const RuleListReactAdapter = forwardRef(
	({dataProvider, instance, pages, rules, spritemap, ...field}, ref) => {
		const emit = (name, event) => instance.emit(name, event);

		return (
			<ClayIconSpriteContext.Provider value={field.spritemap}>
				<RuleList
					dataProvider={dataProvider}
					onRuleAdded={(event) => emit('ruleAdded', event)}
					onRuleCancelled={(event) => emit('ruleCancelled', event)}
					onRuleDeleted={(event) => emit('ruleDeleted', event)}
					onRuleEdited={(event) => emit('ruleEdited', event)}
					pages={pages}
					ref={ref}
					rules={rules}
					spritemap={spritemap}
					{...field}
				/>
			</ClayIconSpriteContext.Provider>
		);
	}
);

const ReactComponentAdapter = getConnectedReactComponentAdapter(
	RuleListReactAdapter
);

Soy.register(ReactComponentAdapter, templates);

export default ReactComponentAdapter;
