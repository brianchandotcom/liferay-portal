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

import ClayForm, {ClayInput, ClaySelectWithOption} from '@clayui/form';
import React, {useState, useContext, useEffect} from 'react';

import {EDITABLE_FRAGMENT_ENTRY_PROCESSOR} from '../../../js/utils/constants';
import {EDITABLE_TYPES} from '../config/constants/editableTypes';
import {ConfigContext} from '../config/index';
import InfoItemService from '../services/InfoItemService';
import {useDispatch, useSelector} from '../store/index';
import updateEditableValues from '../thunks/updateEditableValues';
import MappingSelector from './MappingSelector';

const SOURCE_TYPES = {
	fromContentField: 'fromContentField',
	manual: 'manual'
};

const SOURCE_TYPES_OPTIONS = [
	{
		label: `${Liferay.Language.get('manual')}`,
		value: SOURCE_TYPES.manual
	},
	{
		label: `${Liferay.Language.get('from-content-field')}`,
		value: SOURCE_TYPES.fromContentField
	}
];

const TARGET_OPTIONS = [
	{
		label: `${Liferay.Language.get('self')}`,
		value: '_self'
	},
	{
		label: `${Liferay.Language.get('blank')}`,
		value: '_blank'
	},
	{
		label: `${Liferay.Language.get('parent')}`,
		value: '_parent'
	},
	{
		label: `${Liferay.Language.get('top')}`,
		value: '_top'
	}
];

export default function LinkConfigurationPanel({item}) {
	const {editableId, fragmentEntryLinkId} = item;

	const fragmentEntryLinks = useSelector(state => state.fragmentEntryLinks);
	const segmentsExperienceId = useSelector(
		state => state.segmentsExperienceId
	);
	const config = useContext(ConfigContext);
	const dispatch = useDispatch();

	const editableValue =
		fragmentEntryLinks[fragmentEntryLinkId].editableValues[
			EDITABLE_FRAGMENT_ENTRY_PROCESSOR
		][editableId];

	const editableConfig = editableValue.config || {};

	const isMapped = editableConfig.mappedField || editableConfig.fieldId;

	const [sourceType, setSourceType] = useState(
		isMapped ? SOURCE_TYPES.fromContentField : SOURCE_TYPES.manual
	);

	const updateRowConfig = newConfig => {
		const editableValues =
			fragmentEntryLinks[fragmentEntryLinkId].editableValues;
		const editableProcessorValues =
			editableValues[EDITABLE_FRAGMENT_ENTRY_PROCESSOR];

		const nextEditableValues = {
			...editableValues,

			[EDITABLE_FRAGMENT_ENTRY_PROCESSOR]: {
				...editableProcessorValues,
				[editableId]: {
					...editableProcessorValues[editableId],
					config: {
						...editableConfig,
						...newConfig
					}
				}
			}
		};

		dispatch(
			updateEditableValues({
				config,
				editableValues: nextEditableValues,
				fragmentEntryLinkId,
				segmentsExperienceId
			})
		);
	};

	const updateMapping = mappedItem => {
		InfoItemService.getAssetFieldValue({
			classNameId: mappedItem.classNameId,
			classPK: mappedItem.classPK,
			config,
			fieldId: mappedItem.fieldId,
			onNetworkStatus: dispatch
		}).then(response => {
			const {fieldValue} = response;

			updateRowConfig({href: fieldValue, ...mappedItem});
		});
	};

	return (
		<>
			<ClayForm.Group small>
				<label htmlFor="floatingToolbarLinkSourceOption">
					{Liferay.Language.get('link')}
				</label>
				<ClaySelectWithOption
					id="floatingToolbarLinkSourceOption"
					onChange={event => {
						updateRowConfig({
							classNameId: '',
							classPK: '',
							fieldId: '',
							href: '',
							mappedField: ''
						});
						setSourceType(event.target.value);
					}}
					options={SOURCE_TYPES_OPTIONS}
					type="text"
					value={sourceType}
				/>
			</ClayForm.Group>

			{sourceType === SOURCE_TYPES.fromContentField && (
				<MappingSelector
					fieldType={EDITABLE_TYPES.text}
					mappedItem={editableConfig}
					onMappingSelect={updateMapping}
				/>
			)}
			{sourceType === SOURCE_TYPES.manual ? (
				<ManualHrefInput
					initialHref={editableConfig.href}
					onChange={href => {
						updateRowConfig({href});
					}}
				/>
			) : (
				editableConfig.href && (
					<ClayForm.Group small>
						<label htmlFor="floatingToolbarLinkHrefOption">
							{Liferay.Language.get('url')}
						</label>
						<ClayInput
							id="floatingToolbarLinkHrefOption"
							readOnly
							type="text"
							value={editableConfig.href}
						/>
					</ClayForm.Group>
				)
			)}

			<ClayForm.Group small>
				<label htmlFor="floatingToolbarLinkTargetOption">
					{Liferay.Language.get('target')}
				</label>
				<ClaySelectWithOption
					id="floatingToolbarLinkTargetOption"
					onChange={event => {
						updateRowConfig({target: event.target.value});
					}}
					options={TARGET_OPTIONS}
					type="text"
					value={editableConfig.target}
				/>
			</ClayForm.Group>
		</>
	);
}

function ManualHrefInput({initialHref, onChange}) {
	const [href, setHref] = useState(initialHref);

	useEffect(() => {
		setHref(initialHref);
	}, [initialHref]);

	return (
		<ClayForm.Group small>
			<label htmlFor="floatingToolbarLinkHrefOption">
				{Liferay.Language.get('url')}
			</label>
			<ClayInput
				id="floatingToolbarLinkHrefOption"
				onBlur={() => {
					onChange(href);
				}}
				onChange={event => setHref(event.target.value)}
				type="text"
				value={href}
			/>
		</ClayForm.Group>
	);
}
