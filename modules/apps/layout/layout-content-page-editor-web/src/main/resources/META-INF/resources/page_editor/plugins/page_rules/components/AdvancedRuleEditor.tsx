/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {CodeEditor} from '@liferay/object-js-components-web';
import React, {useEffect, useMemo, useState} from 'react';

import {config} from '../../../app/config';
import {LAYOUT_DATA_ITEM_TYPES} from '../../../app/config/constants/layoutDataItemTypes';
import {ObjectFields} from '../../../app/contexts/ObjectDataContext';
import {useSelector} from '../../../app/contexts/StoreContext';
import selectFormConfiguration from '../../../app/selectors/selectFormConfiguration';
import selectLayoutDataItemLabel from '../../../app/selectors/selectLayoutDataItemLabel';
import FormService from '../../../app/services/FormService';
import RulesService from '../../../app/services/RulesService';
import {CACHE_KEYS, getCacheItem, getCacheKey} from '../../../app/utils/cache';
import {isLayoutDataItemDeleted} from '../../../app/utils/isLayoutDataItemDeleted';
import useCache from '../../../app/utils/useCache';
import {visitSelectedInputLayoutDataItems} from '../../../app/utils/visitSelectedInputLayoutDataItems';
import {State} from '../../../types/State';
import {FragmentLayoutDataItem} from '../../../types/layout_data/FragmentLayoutDataItem';

type Props = {
	onChange: (value: string | undefined) => void;
	value?: string;
};

export default function AdvancedRuleEditor({onChange, value}: Props) {
	const state = useSelector((state: State) => state);

	const [codeEditorSidebarPanels, setCodeEditorSidebarPanels] = useState(
		() => config.codeEditorSidebarPanels
	);

	const roles = useCache({
		fetcher: () => RulesService.getRoles(),
		key: [CACHE_KEYS.roles],
	});

	const users = useCache({
		fetcher: () => RulesService.getUsers(),
		key: [CACHE_KEYS.users],
	});

	const rolesSection = useMemo(() => getRolesSection(roles), [roles]);

	const usersSection = useMemo(() => getUsersSection(users), [users]);

	const segmentEntriesSection = useMemo(
		() => ({
			items: Object.values(config.availableSegmentsEntries).map(
				(segmentEntry) => ({
					content: segmentEntry.segmentsEntryId,
					label: segmentEntry.name,
				})
			),
			label: Liferay.Language.get('segments'),
		}),
		[]
	);

	useEffect(() => {
		getFormFieldsSections(state).then((sections) => {
			setCodeEditorSidebarPanels([
				...config.codeEditorSidebarPanels,
				...sections,
				...(rolesSection ? [rolesSection] : []),
				...(usersSection ? [usersSection] : []),
				segmentEntriesSection,
			]);
		});
	}, [state, segmentEntriesSection, rolesSection, usersSection]);

	return (
		<CodeEditor
			error=""
			onChange={onChange}
			sidebarElements={codeEditorSidebarPanels}
			value={value}
		/>
	);
}

function getRolesSection(
	roles: Awaited<ReturnType<typeof RulesService.getRoles>> | null
) {
	if (!roles) {
		return null;
	}

	return {
		items: roles.map((role) => ({content: role.roleId, label: role.name})),
		label: Liferay.Language.get('roles'),
	};
}

function getUsersSection(
	users: Awaited<ReturnType<typeof RulesService.getUsers>> | null
) {
	if (!users) {
		return null;
	}

	return {
		items: users.map((user) => ({
			content: user.userId,
			label: user.screenName,
		})),
		label: Liferay.Language.get('users'),
	};
}

async function getFormFieldsSections(state: State) {
	const sections = [];

	const formItems = Object.values(state.layoutData.items).filter(
		(item) =>
			item.type === LAYOUT_DATA_ITEM_TYPES.form &&
			item.config.classNameId !== '0' &&
			!isLayoutDataItemDeleted(state.layoutData, item.itemId)
	);

	for (const formItem of formItems) {
		const selectedInputsData: Array<{
			fieldId: string;
			item: FragmentLayoutDataItem;
		}> = [];

		visitSelectedInputLayoutDataItems(
			state,
			formItem.itemId,
			(item, fieldId) => selectedInputsData.push({fieldId, item})
		);

		const {classNameId, classTypeId} = selectFormConfiguration(
			formItem,
			state.layoutData
		);

		if (!classNameId) {
			return [];
		}

		const selectedType = config.formTypes.find(
			({value}) => value === classNameId
		);

		if (!selectedType) {
			return [];
		}

		const cacheKey = getCacheKey([
			CACHE_KEYS.formFields,
			classNameId,
			classTypeId,
		]);

		const {data: fields} = getCacheItem(cacheKey);

		const promise = fields
			? Promise.resolve(fields)
			: FormService.getFormFields({
					classNameId,
					classTypeId,
				});

		const formFields = (await promise) as ObjectFields;

		const items = formFields
			.flatMap((field) => ('fields' in field ? field.fields : []))
			.filter(
				(field) =>
					'key' in field &&
					selectedInputsData.some(
						(inputField: any) => inputField.fieldId === field.key
					)
			)
			.map((field: any) => {
				const inputField = selectedInputsData.find(
					(inputField) => inputField.fieldId === field.key
				)!;

				return {
					content:
						'input__' + inputField.item.itemId.replaceAll('-', '_'),
					label: field.label,
				};
			});

		sections.push({
			items,
			label: `${selectedType.label} (${selectLayoutDataItemLabel(state, formItem)})`,
		});
	}

	return sections;
}
