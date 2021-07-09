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

import {useFormState} from 'data-engine-js-components-web';
import {getFields} from 'data-engine-js-components-web/js/utils/fields.es';
import React, {useEffect, useMemo} from 'react';

const getFielProperty = (fields, fieldName) => {
	return fields.find((field) => field.fieldName === fieldName)?.value[0];
};

const FormSettingsApi = React.forwardRef((_, ref) => {
	const {pages} = useFormState();

	useEffect(() => {
		const containerId = 'formSettingsAPI';

		Liferay.component(
			containerId,
			{
				reactComponentRef: ref,
			},
			{
				destroyOnNavigate: true,
			}
		);

		return () => {
			Liferay.destroyComponent(containerId);
		};
	}, [ref]);

	const fields = useMemo(() => getFields(pages), [pages]);

	ref.current = {
		getFields: () => fields,
		getObjectDefinitionId: () => {
			const storageType = getFielProperty(fields, 'storageType');
			const objectDefinitionId = getFielProperty(
				fields,
				'objectDefinitionId'
			);

			return storageType === 'object' && objectDefinitionId
				? objectDefinitionId
				: null;
		},
	};

	return null;
});

export default FormSettingsApi;
