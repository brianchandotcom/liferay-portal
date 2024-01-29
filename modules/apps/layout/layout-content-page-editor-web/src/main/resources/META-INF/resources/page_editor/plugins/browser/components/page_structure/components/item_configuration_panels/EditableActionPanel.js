/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import {useId} from 'frontend-js-components-web';
import {debounce, openToast, sub} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useCallback, useMemo, useState} from 'react';

import {CheckboxField} from '../../../../../../app/components/fragment_configuration_fields/CheckboxField';
import {SelectField} from '../../../../../../app/components/fragment_configuration_fields/SelectField';
import {EDITABLE_FRAGMENT_ENTRY_PROCESSOR} from '../../../../../../app/config/constants/editableFragmentEntryProcessor';
import {EDITABLE_TYPES} from '../../../../../../app/config/constants/editableTypes';
import {LAYOUT_TYPES} from '../../../../../../app/config/constants/layoutTypes';
import {config} from '../../../../../../app/config/index';
import {useCollectionConfig} from '../../../../../../app/contexts/CollectionItemContext';
import {
	useDispatch,
	useSelector,
	useSelectorCallback,
} from '../../../../../../app/contexts/StoreContext';
import selectEditableValue from '../../../../../../app/selectors/selectEditableValue';
import selectEditableValues from '../../../../../../app/selectors/selectEditableValues';
import selectLanguageId from '../../../../../../app/selectors/selectLanguageId';
import InfoItemService from '../../../../../../app/services/InfoItemService';
import updateEditableValues from '../../../../../../app/thunks/updateEditableValues';
import {CACHE_KEYS} from '../../../../../../app/utils/cache';
import isMapped from '../../../../../../app/utils/editable_value/isMapped';
import {updateIn} from '../../../../../../app/utils/updateIn';
import useCache from '../../../../../../app/utils/useCache';
import CurrentLanguageFlag from '../../../../../../common/components/CurrentLanguageFlag';
import DisplayPageSelector from '../../../../../../common/components/DisplayPageSelector';
import {LayoutSelector} from '../../../../../../common/components/LayoutSelector';
import MappingSelector from '../../../../../../common/components/MappingSelector';
import {getEditableItemPropTypes} from '../../../../../../prop_types/index';

const INTERACTION_NONE = 'none';
const INTERACTION_NOTIFICATION = 'notification';
const INTERACTION_PAGE = 'page';
const INTERACTION_URL = 'url';
const INTERACTION_DISPLAY_PAGE = 'displayPage';

const ERROR_INTERACTION_OPTIONS = [
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

const SUCCESS_INTERACTION_OPTIONS = [
	...ERROR_INTERACTION_OPTIONS,
	{
		label: Liferay.Language.get('go-to-entry-display-page'),
		value: INTERACTION_DISPLAY_PAGE,
	},
];

const INTERACTION_DATA = {
	error: {
		field: 'onError',
		label: Liferay.Language.get('error'),
		type: 'danger',
	},
	success: {
		field: 'onSuccess',
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

	const {mappedAction = {}} = editableValue.config;
	const {classNameId, fieldId} = mappedAction;

	const defaultError = useCache({
		fetcher: () =>
			InfoItemService.getInfoItemActionErrorMessage({
				classNameId,
				fieldId,
			}).then(({error, message}) => message || error),
		key: [CACHE_KEYS.actionError, classNameId, fieldId],
	});

	return (
		<>
			<MappingSelector
				fieldSelectorLabel={Liferay.Language.get('action')}
				fieldType={EDITABLE_TYPES.action}
				itemSelectorURL={config.actionableInfoItemSelectorURL}
				mappedItem={mappedAction}
				onMappingSelect={(action) => {
					onValueSelect('mappedAction', action);
				}}
			/>

			{isMapped(mappedAction) && (
				<>
					<InteractionSelector
						data={INTERACTION_DATA.success}
						fragmentId={item.parentId}
						interactionOptions={SUCCESS_INTERACTION_OPTIONS}
						itemConfig={editableValue.config}
						onValueSelect={onValueSelect}
					/>

					<InteractionSelector
						data={{
							...INTERACTION_DATA.error,
							defaultMessage: defaultError,
						}}
						fragmentId={item.parentId}
						interactionOptions={ERROR_INTERACTION_OPTIONS}
						itemConfig={editableValue.config}
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

function InteractionSelector({
	data,
	fragmentId,
	interactionOptions,
	itemConfig,
	onValueSelect,
}) {
	const {defaultMessage, field, label, type} = data;

	const interactionConfig = itemConfig[field];

	const {displayPageUniqueFieldId, interaction, page, reload, text, url} =
		interactionConfig || {};

	const languageId = useSelector(selectLanguageId);
	const fragmentConfig = useSelector(
		({layoutData}) => layoutData.items[fragmentId].config
	);

	const collectionConfig = useCollectionConfig();

	const previewId = useId();
	const textInputId = useId();

	const [textValue, setTextValue] = useState(text || {});
	const [URLValue, setURLValue] = useState(url || {});
	const [showPreview, setShowPreview] = useState(fragmentConfig.showPreview);

	const onConfigChange = useCallback(
		(name, value) => {
			const nextConfig = {...interactionConfig, [name]: value};

			onValueSelect(field, nextConfig);
		},
		[field, interactionConfig, onValueSelect]
	);

	const debouncedOnConfigChange = useMemo(
		() => debounce((name, value) => onConfigChange(name, value), 300),
		[onConfigChange]
	);

	const hidePreview = () => {
		const previewElement = document.getElementById(previewId);

		previewElement?.remove();
	};

	let mappingIds = null;

	if (
		config.layoutType === LAYOUT_TYPES.display &&
		itemConfig.mappedAction.mappedField
	) {
		const {selectedMappingTypes} = config;
		mappingIds = {
			classNameId: selectedMappingTypes.type.id,
			classTypeId: selectedMappingTypes.subtype.id,
		};
	}
	else {
		mappingIds = collectionConfig
			? collectionConfig.collection
			: itemConfig.mappedAction;
	}

	return (
		<>
			<SelectField
				field={{
					label: sub(Liferay.Language.get('x-interaction'), label),
					name: 'interaction',
					typeOptions: {
						validValues: interactionOptions,
					},
				}}
				onValueSelect={(name, value) => {
					onConfigChange(name, value);
				}}
				value={interaction}
			/>

			{interaction === INTERACTION_DISPLAY_PAGE && (
				<DisplayPageSelector
					mappingIds={mappingIds}
					onConfigChange={(layout) => {
						onConfigChange(
							'displayPageUniqueFieldId',
							layout.displayPage
						);
					}}
					selectedValue={displayPageUniqueFieldId}
				/>
			)}

			{(!interaction ||
				[INTERACTION_NONE, INTERACTION_NOTIFICATION].includes(
					interaction
				)) && (
				<CheckboxField
					field={{
						label: sub(
							Liferay.Language.get('reload-page-after-x'),
							label
						),
						name: 'reload',
					}}
					onValueSelect={(name, value) => {
						onConfigChange(name, value);
					}}
					value={reload}
				/>
			)}

			{interaction === INTERACTION_NOTIFICATION && (
				<>
					<ClayForm.Group>
						<label htmlFor={textInputId}>
							{sub(Liferay.Language.get('x-text'), label)}
						</label>

						<ClayInput.Group className="c-mb-2" small>
							<ClayInput.GroupItem>
								<ClayInput
									id={textInputId}
									onChange={(event) => {
										if (showPreview) {
											setShowPreview(false);
											hidePreview();
										}

										const nextTextValue = {
											...text,
											[languageId]: event.target.value,
										};

										setTextValue(nextTextValue);

										debouncedOnConfigChange(
											'text',
											nextTextValue
										);
									}}
									type="text"
									value={textValue[languageId] ?? ''}
								/>
							</ClayInput.GroupItem>

							<ClayInput.GroupItem shrink>
								<CurrentLanguageFlag />
							</ClayInput.GroupItem>
						</ClayInput.Group>

						<ClayButton
							aria-label={sub(
								Liferay.Language.get('preview-x-notification'),
								label
							)}
							disabled={showPreview}
							displayType="secondary"
							onClick={() => {
								setShowPreview(true);
								openToast({
									message:
										textValue[languageId] ||
										defaultMessage[languageId],
									onClose: () => setShowPreview(false),
									toastProps: {
										id: previewId,
									},
									type,
								});
							}}
							size="sm"
						>
							{Liferay.Language.get('preview')}
						</ClayButton>
					</ClayForm.Group>
				</>
			)}

			{interaction === INTERACTION_PAGE && (
				<LayoutSelector
					label={sub(Liferay.Language.get('x-page'), label)}
					mappedLayout={page}
					onLayoutSelect={(layout) => {
						onConfigChange('page', layout);
					}}
				/>
			)}

			{interaction === INTERACTION_URL && (
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
										...url,
										[languageId]: event.target.value,
									};

									setURLValue(nextURLValue);

									debouncedOnConfigChange(
										'url',
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
	data: PropTypes.object.isRequired,
	fragmentId: PropTypes.string.isRequired,
	itemConfig: PropTypes.object.isRequired,
	onValueSelect: PropTypes.func.isRequired,
};
