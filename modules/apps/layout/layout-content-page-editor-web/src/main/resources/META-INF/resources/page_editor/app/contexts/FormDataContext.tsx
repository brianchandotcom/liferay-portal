/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {
	Dispatch,
	ReactNode,
	SetStateAction,
	useCallback,
	useContext,
	useState,
} from 'react';

import {MappingFields} from '../../types/MappingField';
import {config} from '../config/index';
import FormService from '../services/FormService';
import {CACHE_KEYS} from '../utils/cache';
import useCache from '../utils/useCache';

type FormDataMap = Record<string, {fields: MappingFields; label?: string}>;

const FormDataContext = React.createContext<{
	map: FormDataMap;
	setMap: Dispatch<SetStateAction<FormDataMap>>;
}>({
	map: {},
	setMap: () => {},
});

function FormDataContextProvider({children}: {children: ReactNode}) {
	const [map, setMap] = useState({});

	return (
		<FormDataContext.Provider value={{map, setMap}}>
			{children}
		</FormDataContext.Provider>
	);
}

type Props =
	| {classNameId: string; classTypeId: string; name?: never}
	| {classNameId?: never; classTypeId?: never; name: string};

function useFormMappingFields(props: Props) {
	const map = useContext(FormDataContext).map;

	const entry = map[buildKey(props)];

	if (entry) {
		return entry.fields;
	}

	return [];
}

function useFormMappingFieldsLabel(props: Props) {
	const map = useContext(FormDataContext).map;

	const entry = map[buildKey(props)];

	if (entry) {
		return entry.label;
	}

	return null;
}

function useSaveFormMappingFields({
	classNameId,
	classTypeId,
}: {
	classNameId: string;
	classTypeId: string;
}) {
	const {setMap} = useContext(FormDataContext);

	const fields = useCache({
		fetcher: () => FormService.getFormFields({classNameId, classTypeId}),
		key: [CACHE_KEYS.formFields, classNameId, classTypeId],
	});

	return useCallback(() => {
		if (!fields) {
			return;
		}

		const label = config.formTypes.find(
			({value}) => value === classNameId
		)?.label;

		setMap((currentMap) =>
			buildMap({classNameId, classTypeId, currentMap, fields, label})
		);
	}, [classNameId, classTypeId, fields, setMap]);
}

function buildKey(props: Props): string {
	if ('name' in props && props.name) {
		return props.name;
	}

	return `${props.classNameId}_${props.classTypeId}`;
}

function buildMap({
	currentMap,
	fields,
	label,
	...props
}: Props & {
	currentMap: FormDataMap;
	fields: MappingFields;
	label?: string;
}): FormDataMap {
	let map = {...currentMap};

	const key = buildKey(props);

	if (!(key in map)) {
		map[key] = {fields, label};
	}

	for (const field of fields) {
		if ('fields' in field && field.name && !(field.name in map)) {
			map = {
				...map,
				...buildMap({
					currentMap: map,
					fields: field.fields,
					label: field.label,
					name: field.name,
				}),
			};
		}
	}

	return map;
}

export {
	FormDataContextProvider,
	useFormMappingFields,
	useFormMappingFieldsLabel,
	useSaveFormMappingFields,
};
