/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

import {addMappingFields} from '../../app/actions/index';
import {useDispatch, useSelector} from '../../app/contexts/StoreContext';
import InfoItemService from '../../app/services/InfoItemService';
import getMappingFieldsKey from '../../app/utils/getMappingFieldsKey';
import MappingFieldSelector from './MappingFieldSelector';

function filterFields(fieldSets) {
	return fieldSets.reduce((acc, fieldSet) => {
		const newFields = fieldSet.fields.filter(
			(field) => field.type === 'display-page'
		);

		if (newFields.length) {
			return [
				...acc,
				{
					...fieldSet,
					fields: newFields,
				},
			];
		}

		return acc;
	}, []);
}

export default function DisplayPageSelector({
	mappingIds,
	onConfigChange,
	selectedValue,
}) {
	const dispatch = useDispatch();

	const mappingFields = useSelector((state) => state.mappingFields);

	const [displayPageFields, setDisplayPageFields] = useState(null);

	useEffect(() => {
		const key = getMappingFieldsKey(mappingIds);

		const fieldSets = mappingFields[key];

		if (fieldSets) {
			setDisplayPageFields(filterFields(fieldSets));
		}
		else {
			InfoItemService.getAvailableStructureMappingFields({
				...mappingIds,
			}).then((newFields) => {
				dispatch(addMappingFields({fields: newFields, key}));
			});
		}
	}, [dispatch, mappingIds, mappingFields]);

	return (
		<MappingFieldSelector
			className="mb-3"
			defaultLabel={`-- ${Liferay.Language.get('none')} --`}
			fields={displayPageFields}
			label={Liferay.Language.get('display-page')}
			onValueSelect={(event) =>
				onConfigChange({
					displayPage:
						event.target.value === 'unmapped'
							? null
							: event.target.value,
				})
			}
			value={selectedValue}
		/>
	);
}

DisplayPageSelector.propTypes = {
	mappingIds: PropTypes.shape({
		classNameId: PropTypes.string,
		classTypeId: PropTypes.string,
	}).isRequired,
	onConfigChange: PropTypes.func.isRequired,
	selectedValue: PropTypes.string,
};
