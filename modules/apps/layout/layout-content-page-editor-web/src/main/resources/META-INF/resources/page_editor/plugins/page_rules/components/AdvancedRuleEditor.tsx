/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {CodeEditor} from '@liferay/object-js-components-web';
import {useId} from 'frontend-js-components-web';
import {sub} from 'frontend-js-web';
import React, {useEffect, useMemo, useState} from 'react';

import {config} from '../../../app/config';
import {LAYOUT_DATA_ITEM_TYPES} from '../../../app/config/constants/layoutDataItemTypes';
import {LAYOUT_TYPES} from '../../../app/config/constants/layoutTypes';
import {
	useRuleValidation,
	useScriptError,
	useScriptInputRef,
} from '../../../app/contexts/RulesModalContext';
import {useSelector} from '../../../app/contexts/StoreContext';
import selectFormConfiguration from '../../../app/selectors/selectFormConfiguration';
import selectLayoutDataItemLabel from '../../../app/selectors/selectLayoutDataItemLabel';
import FormService from '../../../app/services/FormService';
import RulesService from '../../../app/services/RulesService';
import {CACHE_KEYS, getCacheItem, getCacheKey} from '../../../app/utils/cache';
import {isLayoutDataItemDeleted} from '../../../app/utils/isLayoutDataItemDeleted';
import useCache from '../../../app/utils/useCache';
import {visitSelectedInputLayoutDataItems} from '../../../app/utils/visitSelectedInputLayoutDataItems';
import {MappingField, MappingFields} from '../../../types/MappingField';
import {State} from '../../../types/State';
import {FragmentLayoutDataItem} from '../../../types/layout_data/FragmentLayoutDataItem';
import {MappingFieldItem} from '../utils/useMappingFieldItems';

type Props = {
	mappingFieldItems: MappingFieldItem[];
	onChange: (value: string | undefined) => void;
	value?: string;
};

type CodeEditorSidebarPanel = (typeof config.codeEditorSidebarPanels)[number];

export default function AdvancedRuleEditor({
	mappingFieldItems,
	onChange,
	value,
}: Props) {
	const id = useId();

	const state = useSelector((state: State) => state);

	const [error, setError] = useState<string>('');
	const {scriptError} = useScriptError();
	const scriptInputRef = useScriptInputRef();

	const editorRef = React.useRef<CodeMirror.Editor>(null);

	const [codeEditorSidebarPanels, setCodeEditorSidebarPanels] = useState<
		typeof config.codeEditorSidebarPanels
	>(() => config.codeEditorSidebarPanels);

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
			key: 'segments',
			label: Liferay.Language.get('segments'),
		}),
		[]
	);

	useEffect(() => {
		getFormFieldsSections(state, mappingFieldItems).then((sections) => {
			setCodeEditorSidebarPanels([
				...config.codeEditorSidebarPanels,
				...sections,
				...(rolesSection ? [rolesSection] : []),
				...(usersSection ? [usersSection] : []),
				segmentEntriesSection,
			]);
		});
	}, [
		mappingFieldItems,
		rolesSection,
		segmentEntriesSection,
		state,
		usersSection,
	]);

	useRuleValidation(() => {
		setError(scriptError || '');
	});

	return (
		<div
			id={id}
			onFocus={() => {
				editorRef.current?.focus();
			}}
			ref={scriptInputRef}
			tabIndex={-1}
		>
			<CodeEditor
				error={error}
				onChange={onChange}
				ref={editorRef}
				sidebarElements={codeEditorSidebarPanels}
				value={value}
			/>
		</div>
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
		key: 'roles',
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
		key: 'users',
		label: Liferay.Language.get('users'),
	};
}

async function getFormFieldsSections(
	state: State,
	mappingFieldItems: MappingFieldItem[]
) {
	const sections: CodeEditorSidebarPanel[] = [];

	if (
		config.layoutType === LAYOUT_TYPES.display &&
		mappingFieldItems.length
	) {
		sections.push({
			items: mappingFieldItems.map((field) => ({
				content: field.value,
				label: field.label,
			})),
			key: 'mappingFields',
			label: sub(
				Liferay.Language.get('x-default'),
				config.selectedMappingTypes?.subtype
					? config.selectedMappingTypes.subtype.label
					: config.selectedMappingTypes?.type.label ?? ''
			),
		});
	}

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
			continue;
		}

		const selectedType = config.formTypes.find(
			({value}) => value === classNameId
		);

		if (!selectedType) {
			continue;
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

		const formFields = (await promise) as MappingFields;

		const items = formFields
			.flatMap((field) => ('fields' in field ? field.fields : [field]))
			.filter(
				(field): field is MappingField =>
					'key' in field &&
					selectedInputsData.some(
						(inputField: any) => inputField.fieldId === field.key
					) &&
					field.type !== 'upload'
			)
			.map((field) => {
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
			key: `form_${formItem.itemId}`,
			label: `${selectedType.label} (${selectLayoutDataItemLabel(state, formItem)})`,
		});
	}

	return sections;
}
