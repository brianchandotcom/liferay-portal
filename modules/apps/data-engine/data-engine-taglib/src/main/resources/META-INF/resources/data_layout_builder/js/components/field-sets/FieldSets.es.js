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

import React, {useContext} from 'react';

import AppContext from '../../AppContext.es';
import {DRAG_FIELDSET} from '../../drag-and-drop/dragTypes.es';
import {containsFieldSet} from '../../utils/dataDefinition.es';
import FieldType from '../field-types/FieldType.es';

export default function FieldSets() {
	const [{dataDefinition, fieldSets}] = useContext(AppContext);

	return (
		<>
			{fieldSets.map(fieldSet => (
				<FieldType
					description={`${
						fieldSet.dataDefinitionFields.length
					} ${Liferay.Language.get('fields')}`}
					disabled={containsFieldSet(dataDefinition, fieldSet.id)}
					dragType={DRAG_FIELDSET}
					fieldSet={fieldSet}
					icon="forms"
					key={fieldSet.dataDefinitionKey}
					label={fieldSet.name[themeDisplay.getLanguageId()]}
				/>
			))}
		</>
	);
}
