/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useMemo} from 'react';

import {config} from '../../../app/config/index';
import {useSelector} from '../../../app/contexts/StoreContext';
import getMappingFieldsKey from '../../../app/utils/getMappingFieldsKey';
import {MappingFieldAttributes} from '../../../types/MappingField';
import {filterAndConvertMappingFields} from '../components/Condition';

export type MappingFieldItem = {
	attributes?: MappingFieldAttributes;
	label: string;
	type: string;
	value: string;
};

export default function useMappingFieldItems() {
	const mappingFields = useSelector((state) =>
		config.selectedMappingTypes
			? state.mappingFields[
					getMappingFieldsKey(config.selectedMappingTypes)
				]
			: null
	);

	return useMemo(
		() => filterAndConvertMappingFields(mappingFields ?? null),
		[mappingFields]
	);
}
