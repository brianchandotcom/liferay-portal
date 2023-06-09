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

import ClayForm, {ClayInput, ClayToggle} from '@clayui/form';
import {debounce, openToast, sub} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useMemo, useState} from 'react';

import updateItemLocalConfig from '../../../../../../app/actions/updateItemLocalConfig';
import {CheckboxField} from '../../../../../../app/components/fragment_configuration_fields/CheckboxField';
import {SelectField} from '../../../../../../app/components/fragment_configuration_fields/SelectField';
import {EDITABLE_FRAGMENT_ENTRY_PROCESSOR} from '../../../../../../app/config/constants/editableFragmentEntryProcessor';
import {EDITABLE_TYPES} from '../../../../../../app/config/constants/editableTypes';
import {config} from '../../../../../../app/config/index';
import {
	useDispatch,
	useSelector,
	useSelectorCallback,
} from '../../../../../../app/contexts/StoreContext';
import selectEditableValue from '../../../../../../app/selectors/selectEditableValue';
import selectEditableValues from '../../../../../../app/selectors/selectEditableValues';
import selectLanguageId from '../../../../../../app/selectors/selectLanguageId';
import updateEditableValues from '../../../../../../app/thunks/updateEditableValues';
import {updateIn} from '../../../../../../app/utils/updateIn';
import CurrentLanguageFlag from '../../../../../../common/components/CurrentLanguageFlag';
import {LayoutSelector} from '../../../../../../common/components/LayoutSelector';
import MappingSelector from '../../../../../../common/components/MappingSelector';
import {useId} from '../../../../../../common/hooks/useId';
import {getEditableItemPropTypes} from '../../../../../../prop_types/index';

const INTERACTION_NONE = 'none';
const INTERACTION_NOTIFICATION = 'notification';
const INTERACTION_PAGE = 'page';
const INTERACTION_URL = 'url';

const INTERACTION_OPTIONS = [
	{
		label: Liferay.Language.get('none'),
		value: INTERACTION_NONE,
	},
	{
		label: Liferay.Language.get('show-notification'),
		value: INTERACTION_NOTIFICATION,
	},
	{
		label: Liferay.Language.get('go-to-page'),
		value: INTERACTION_PAGE,
	},
	{
		label: Liferay.Language.get('go-to-external-url'),
		value: INTERACTION_URL,
	},
];

const INTERACTION_DATA = {
	error: {
		fields: {
			interaction: 'errorInteraction',
			page: 'errorPage',
			reload: 'errorReload',
			showPreview: 'errorShowPreview',
			text: 'errorText',
			url: 'errorURL',
		},
		label: Liferay.Language.get('error'),
		type: 'danger',
	},
	success: {
		fields: {
			interaction: 'successInteraction',
			page: 'successPage',
			reload: 'successReload',
			showPreview: 'successShowPreview',
			text: 'successText',
			url: 'successURL',
		},
		label: Liferay.Language.get('success'),
		type: 'success',
	},
};

export default function EditableActionPanel({item}) {
	const dispatch = useDispatch();

	const editableValues = useSelectorCallback(
		(state) => selectEditableValues(state, item.fragmentEntryLinkId),
		[item.fragmentEntryLinkId]
	);

	const editableValue = useSelectorCallback(
		(state) =>
			selectEditableValue(
				state,
				item.fragmentEntryLinkId,
				item.editableId,
				EDITABLE_FRAGMENT_ENTRY_PROCESSOR
			),
		[item.fragmentEntryLinkId]
	);

	const onValueSelect = (name, value) => {
		dispatch(
			updateEditableValues({
				editableValues: updateIn(
					editableValues,
					[
						EDITABLE_FRAGMENT_ENTRY_PROCESSOR,
						[item.editableId],
						'config',
						name,
					],
					() => value
				),
				fragmentEntryLinkId: item.fragmentEntryLinkId,
			})
		);
	};

	return (
		<>
			<MappingSelector
				fieldSelectorLabel={Liferay.Language.get('action')}
				fieldType={EDITABLE_TYPES.action}
				itemSelectorURL={config.actionableInfoItemSelectorURL}
				mappedItem={editableValue.config.mappedAction || {}}
				onMappingSelect={(action) => {
					onValueSelect('mappedAction', action);
				}}
			/>

			{editableValue.config.mappedAction && (
				<>
					<InteractionSelector
						config={editableValue.config}
						data={INTERACTION_DATA.success}
						fragmentId={item.parentId}
						onValueSelect={onValueSelect}
					/>

					<InteractionSelector
						config={editableValue.config}
						data={INTERACTION_DATA.error}
						fragmentId={item.parentId}
						onValueSelect={onValueSelect}
					/>
				</>
			)}
		</>
	);
}

EditableActionPanel.propTypes = {
	item: getEditableItemPropTypes(),
};

function InteractionSelector({config, data, fragmentId, onValueSelect}) {
	const {fields, label, type} = data;

	const languageId = useSelector(selectLanguageId);
	const fragmentConfig = useSelector(
		({layoutData}) => layoutData.items[fragmentId].config
	);

	const dispatch = useDispatch();
	const previewId = useId();
	const textInputId = useId();

	const [textValue, setTextValue] = useState(config[fields.text] || {});
	const [URLValue, setURLValue] = useState(config[fields.url] || {});
	const [showPreview, setShowPreview] = useState(
		fragmentConfig[fields.showPreview]
	);

	const debouncedOnValueSelect = useMemo(
		() => debounce((name, value) => onValueSelect(name, value), 300),
		[onValueSelect]
	);

	const onPreviewToggle = (checked) => {
		setShowPreview(checked);

		dispatch(
			updateItemLocalConfig({
				disableUndo: true,
				itemConfig: {
					[fields.showPreview]: checked,
				},
				itemId: fragmentId,
			})
		);
	};

	const hidePreview = () => {
		const previewElement = document.getElementById(previewId);

		previewElement.remove();
	};

	return (
		<>
			<SelectField
				field={{
					label: sub(Liferay.Language.get('x-interaction'), label),
					name: fields.interaction,
					typeOptions: {
						validValues: INTERACTION_OPTIONS,
					},
				}}
				onValueSelect={(name, value) => {
					onValueSelect(name, value);
				}}
				value={config[fields.interaction]}
			/>

			{[INTERACTION_NONE, INTERACTION_NOTIFICATION].includes(
				config[fields.interaction]
			) && (
				<CheckboxField
					field={{
						label: sub(
							Liferay.Language.get('reload-page-after-x'),
							label
						),
						name: fields.reload,
					}}
					onValueSelect={(name, value) => {
						onValueSelect(name, value);
					}}
					value={config[fields.reload]}
				/>
			)}

			{config[fields.interaction] === INTERACTION_NOTIFICATION && (
				<>
					<ClayForm.Group>
						<label htmlFor={textInputId}>
							{sub(Liferay.Language.get('x-text'), label)}
						</label>

						<ClayInput.Group small>
							<ClayInput.GroupItem>
								<ClayInput
									id={textInputId}
									onChange={(event) => {
										if (showPreview) {
											onPreviewToggle(false);
											hidePreview();
										}

										const nextTextValue = {
											...config[fields.text],
											[languageId]: event.target.value,
										};

										setTextValue(nextTextValue);

										debouncedOnValueSelect(
											fields.text,
											nextTextValue
										);
									}}
									type="text"
									value={textValue[languageId]}
								/>
							</ClayInput.GroupItem>

							<ClayInput.GroupItem shrink>
								<CurrentLanguageFlag />
							</ClayInput.GroupItem>
						</ClayInput.Group>
					</ClayForm.Group>

					<ClayToggle
						label={sub(
							Liferay.Language.get('preview-x-notification'),
							label
						)}
						onToggle={(checked) => {
							onPreviewToggle(checked);

							if (checked) {
								openToast({
									message: textValue[languageId],
									onClose: () => onPreviewToggle(false),
									toastProps: {
										id: previewId,
									},
									type,
								});
							}
							else {
								hidePreview();
							}
						}}
						toggled={showPreview}
					/>
				</>
			)}

			{config[fields.interaction] === INTERACTION_PAGE && (
				<LayoutSelector
					label={sub(Liferay.Language.get('x-page'), label)}
					mappedLayout={config[fields.page]}
					onLayoutSelect={(layout) => {
						onValueSelect(fields.page, layout);
					}}
				/>
			)}

			{config[fields.interaction] === INTERACTION_URL && (
				<ClayForm.Group>
					<label htmlFor={textInputId}>
						{sub(Liferay.Language.get('x-external-url'), label)}
					</label>

					<ClayInput.Group small>
						<ClayInput.GroupItem>
							<ClayInput
								id={textInputId}
								onChange={(event) => {
									const nextURLValue = {
										...config[fields.url],
										[languageId]: event.target.value,
									};

									setURLValue(nextURLValue);

									debouncedOnValueSelect(
										fields.url,
										nextURLValue
									);
								}}
								type="text"
								value={URLValue[languageId]}
							/>
						</ClayInput.GroupItem>

						<ClayInput.GroupItem shrink>
							<CurrentLanguageFlag />
						</ClayInput.GroupItem>
					</ClayInput.Group>
				</ClayForm.Group>
			)}
		</>
	);
}

InteractionSelector.propTypes = {
	config: PropTypes.object.isRequired,
	data: PropTypes.object.isRequired,
	fragmentId: PropTypes.string.isRequired,
	onValueSelect: PropTypes.func.isRequired,
};
