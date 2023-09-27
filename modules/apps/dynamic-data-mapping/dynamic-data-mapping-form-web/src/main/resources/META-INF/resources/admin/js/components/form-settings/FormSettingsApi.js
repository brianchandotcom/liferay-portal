/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getFields, useFormState} from 'data-engine-js-components-web';
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
