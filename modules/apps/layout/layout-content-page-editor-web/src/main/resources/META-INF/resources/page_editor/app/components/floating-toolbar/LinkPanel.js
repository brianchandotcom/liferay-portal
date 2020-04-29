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
import PropTypes from 'prop-types';
import React, {useCallback, useEffect, useState} from 'react';

import {getEditableItemPropTypes} from '../../../prop-types/index';
import {EDITABLE_FRAGMENT_ENTRY_PROCESSOR} from '../../config/constants/editableFragmentEntryProcessor';
import {EDITABLE_TYPES} from '../../config/constants/editableTypes';
import selectSegmentsExperienceId from '../../selectors/selectSegmentsExperienceId';
import {useDispatch, useSelector} from '../../store/index';
import updateEditableValues from '../../thunks/updateEditableValues';
import {useGetFieldValue} from '../CollectionItemContext';
import isMapped from '../fragment-content/isMapped';
import MappingSelector from './MappingSelector';

const SOURCE_TYPES = {
	fromContentField: 'fromContentField',
	manual: 'manual',
};

const SOURCE_TYPES_OPTIONS = [
	{
		label: `${Liferay.Language.get('manual')}`,
		value: SOURCE_TYPES.manual,
	},
	{
		label: `${Liferay.Language.get('from-content-field')}`,
		value: SOURCE_TYPES.fromContentField,
	},
];

const TARGET_OPTIONS = [
	{
		label: `${Liferay.Language.get('self')}`,
		value: '_self',
	},
	{
		label: `${Liferay.Language.get('blank')}`,
		value: '_blank',
	},
	{
		label: `${Liferay.Language.get('parent')}`,
		value: '_parent',
	},
	{
		label: `${Liferay.Language.get('top')}`,
		value: '_top',
	},
];

export default function LinkPanel({item}) {
	const {editableId, editableType, fragmentEntryLinkId} = item;

	const dispatch = useDispatch();
	const fragmentEntryLinks = useSelector((state) => state.fragmentEntryLinks);
	const languageId = useSelector((state) => state.languageId);
	const segmentsExperienceId = useSelector(selectSegmentsExperienceId);

	const editableValue =
		fragmentEntryLinks[fragmentEntryLinkId].editableValues[
			EDITABLE_FRAGMENT_ENTRY_PROCESSOR
		][editableId];

	const editableConfig = editableValue ? editableValue.config : {};

	const [sourceType, setSourceType] = useState(
		isMapped(editableConfig)
			? SOURCE_TYPES.fromContentField
			: SOURCE_TYPES.manual
	);

	const [href, setHref] = useState(editableConfig.href || '');

	const getFieldValue = useGetFieldValue();

	useEffect(() => {
		updateMappedHrefValue({
			classNameId: editableConfig.classNameId,
			classPK: editableConfig.classPK,
			fieldId: editableConfig.fieldId,
			languageId,
		});
	}, [
		editableConfig.classNameId,
		editableConfig.classPK,
		editableConfig.fieldId,
		languageId,
		updateMappedHrefValue,
	]);

	const updateRowConfig = useCallback(
		(newConfig) => {
			const editableValues =
				fragmentEntryLinks[fragmentEntryLinkId].editableValues;
			const editableProcessorValues =
				editableValues[EDITABLE_FRAGMENT_ENTRY_PROCESSOR];

			const config = {...newConfig};

			if (
				Object.keys(config).length > 0 &&
				editableType !== EDITABLE_TYPES.link
			) {
				config.mapperType = 'link';
			}

			const nextEditableValues = {
				...editableValues,

				[EDITABLE_FRAGMENT_ENTRY_PROCESSOR]: {
					...editableProcessorValues,
					[editableId]: {
						...editableProcessorValues[editableId],
						config,
					},
				},
			};

			dispatch(
				updateEditableValues({
					editableValues: nextEditableValues,
					fragmentEntryLinkId,
					segmentsExperienceId,
				})
			);
		},
		[
			dispatch,
			editableId,
			editableType,
			fragmentEntryLinkId,
			fragmentEntryLinks,
			segmentsExperienceId,
		]
	);

	const updateMappedHrefValue = useCallback(
		({classNameId, classPK, fieldId, languageId}) => {
			if (!classNameId || !classPK || !fieldId) {
				return;
			}

			getFieldValue({
				classNameId,
				classPK,
				fieldId,
				languageId,
				onNetworkStatus: () => {},
			}).then((fieldValue) => {
				setHref(fieldValue);
			});
		},
		[getFieldValue]
	);

	return (
		<>
			<ClayForm.Group small>
				<label htmlFor="floatingToolbarLinkSourceOption">
					{Liferay.Language.get('link')}
				</label>
				<ClaySelectWithOption
					id="floatingToolbarLinkSourceOption"
					onChange={(event) => {
						updateRowConfig({});
						setHref('');
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
					onMappingSelect={(mappedItem) => {
						updateRowConfig({
							...mappedItem,
							target: editableConfig.target,
						});

						updateMappedHrefValue({
							classNameId: mappedItem.classNameId,
							classPK: mappedItem.classPK,
							fieldId: mappedItem.fieldId,
							languageId,
						});
					}}
				/>
			)}
			{(sourceType === SOURCE_TYPES.manual || href) && (
				<ClayForm.Group>
					<label htmlFor="floatingToolbarLinkHrefOption">
						{Liferay.Language.get('url')}
					</label>
					<ClayInput
						id="floatingToolbarLinkHrefOption"
						onBlur={() => {
							const previousValue = editableConfig.href || '';

							if (previousValue !== href) {
								updateRowConfig({href});
							}
						}}
						onChange={(event) => {
							setHref(event.target.value);
						}}
						readOnly={sourceType !== SOURCE_TYPES.manual}
						sizing="sm"
						type="text"
						value={href}
					/>
				</ClayForm.Group>
			)}

			<ClayForm.Group small>
				<label htmlFor="floatingToolbarLinkTargetOption">
					{Liferay.Language.get('target')}
				</label>
				<ClaySelectWithOption
					id="floatingToolbarLinkTargetOption"
					onChange={(event) => {
						updateRowConfig({
							...editableConfig,
							target: event.target.value,
						});
					}}
					options={TARGET_OPTIONS}
					type="text"
					value={editableConfig.target}
				/>
			</ClayForm.Group>
		</>
	);
}

LinkPanel.propTypes = {
	item: getEditableItemPropTypes({
		config: PropTypes.oneOfType([
			PropTypes.shape({
				href: PropTypes.string,
				target: PropTypes.oneOf(Object.values(TARGET_OPTIONS)),
			}),
			PropTypes.shape({
				classNameId: PropTypes.string,
				classPK: PropTypes.string,
				fieldId: PropTypes.string,
				target: PropTypes.oneOf(Object.values(TARGET_OPTIONS)),
			}),
			PropTypes.shape({
				mappedField: PropTypes.string,
				target: PropTypes.oneOf(Object.values(TARGET_OPTIONS)),
			}),
		]),
	}),
};
