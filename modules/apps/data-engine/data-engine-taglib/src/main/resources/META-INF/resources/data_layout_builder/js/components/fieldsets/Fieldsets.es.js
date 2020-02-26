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
import FieldType from '../field-types/FieldType.es';

export default function FieldSets() {
	const [{fieldsets}] = useContext(AppContext);

	return (
		<>
			{fieldsets.map(fieldset => (
				<FieldType
					description={`${
						fieldset.dataDefinitionFields.length
					} ${Liferay.Language.get('fields')}`}
					icon="forms"
					key={fieldset.dataDefinitionKey}
					label={fieldset.name[themeDisplay.getLanguageId()]}
				/>
			))}
		</>
	);
}
