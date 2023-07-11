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

import ClayAlert from '@clayui/alert';
import ClayForm, {ClayInput, ClayToggle} from '@clayui/form';
import {useControlledState} from '@liferay/layout-js-components-web';
import React, {useCallback, useEffect, useState} from 'react';

import {addMappingFields} from '../../../../../../app/actions/index';
import updateItemLocalConfig from '../../../../../../app/actions/updateItemLocalConfig';
import {SelectField} from '../../../../../../app/components/fragment_configuration_fields/SelectField';
import {COMMON_STYLES_ROLES} from '../../../../../../app/config/constants/commonStylesRoles';
import {
	useDispatch,
	useSelector,
} from '../../../../../../app/contexts/StoreContext';
import selectLanguageId from '../../../../../../app/selectors/selectLanguageId';
import InfoItemService from '../../../../../../app/services/InfoItemService';
import updateFormItemConfig from '../../../../../../app/thunks/updateFormItemConfig';
import {formIsMapped} from '../../../../../../app/utils/formIsMapped';
import {formIsRestricted} from '../../../../../../app/utils/formIsRestricted';
import {formIsUnavailable} from '../../../../../../app/utils/formIsUnavailable';
import {getEditableLocalizedValue} from '../../../../../../app/utils/getEditableLocalizedValue';
import getMappingFieldsKey from '../../../../../../app/utils/getMappingFieldsKey';
import Collapse from '../../../../../../common/components/Collapse';
import CurrentLanguageFlag from '../../../../../../common/components/CurrentLanguageFlag';
import {LayoutSelector} from '../../../../../../common/components/LayoutSelector';
import MappingFieldSelector from '../../../../../../common/components/MappingFieldSelector';
import {useId} from '../../../../../../common/hooks/useId';
import {CommonStyles} from './CommonStyles';
import ContainerDisplayOptions from './ContainerDisplayOptions';
import FormMappingOptions from './FormMappingOptions';

export function FormGeneralPanel({item}) {
	const dispatch = useDispatch();

	const onValueSelect = useCallback(
		(nextConfig) =>
			dispatch(
				updateFormItemConfig({
					itemConfig: nextConfig,
					itemId: item.itemId,
				})
			),
		[dispatch, item.itemId]
	);

	if (Liferay.FeatureFlags['LPS-169923']) {
		if (formIsUnavailable(item)) {
			return (
				<ClayAlert
					displayType="warning"
					title={`${Liferay.Language.get('warning')}:`}
				>
					{Liferay.Language.get(
						'this-content-is-currently-unavailable-or-has-been-deleted.-users-cannot-see-this-fragment'
					)}
				</ClayAlert>
			);
		}
		else if (formIsRestricted(item)) {
			return (
				<ClayAlert displayType="secondary">
					{Liferay.Language.get(
						'this-content-cannot-be-displayed-due-to-permission-restrictions'
					)}
				</ClayAlert>
			);
		}
	}

	return (
		<>
			<FormOptions item={item} onValueSelect={onValueSelect} />

			<CommonStyles
				commonStylesValues={item.config.styles || {}}
				item={item}
				role={COMMON_STYLES_ROLES.general}
			/>
		</>
	);
}

function FormOptions({item, onValueSelect}) {
	return (
		<div className="mb-3">
			<Collapse
				label={Liferay.Language.get('form-container-options')}
				open
			>
				<FormMappingOptions item={item} onValueSelect={onValueSelect} />

				{formIsMapped(item) && (
					<>
						<SuccessMessageOptions
							item={item}
							onValueSelect={onValueSelect}
						/>

						<ContainerDisplayOptions item={item} />
					</>
				)}
			</Collapse>
		</div>
	);
}

const EMBEDDED_OPTION = 'embedded';
const LAYOUT_OPTION = 'fromLayout';
const URL_OPTION = 'url';
const DISPLAY_PAGE_OPTION = 'displayPage';

const SUCCESS_MESSAGE_OPTIONS = [
	{
		label: Liferay.Language.get('embedded'),
		value: EMBEDDED_OPTION,
	},
	{
		label: Liferay.Language.get('page'),
		value: LAYOUT_OPTION,
	},
	{
		label: Liferay.Language.get('external-url'),
		value: URL_OPTION,
	},
	...(Liferay.FeatureFlags['LPS-183498']
		? [
				{
					label: Liferay.Language.get('entry-display-page'),
					value: DISPLAY_PAGE_OPTION,
				},
		  ]
		: []),
];

function SuccessMessageOptions({item, onValueSelect}) {
	const {successMessage: successMessageConfig = {}} = item.config;

	const languageId = useSelector(selectLanguageId);
	const dispatch = useDispatch();

	const helpTextId = useId();

	const [selectedSource, setSelectedSource] = useState(
		getSelectedOption(successMessageConfig)
	);
	const [successMessage, setSuccessMessage] = useControlledState(
		getEditableLocalizedValue(
			successMessageConfig.message,
			languageId,
			Liferay.Language.get(
				'thank-you.-your-information-was-successfully-received'
			)
		)
	);

	useEffect(() => {
		if (Object.keys(successMessageConfig).length) {
			const nextSelectedSource = getSelectedOption(successMessageConfig);

			setSelectedSource(nextSelectedSource);
		}
	}, [successMessageConfig]);

	const [url, setUrl] = useControlledState(
		getEditableLocalizedValue(successMessageConfig.url, languageId)
	);
	const [showMessagePreview, setShowMessagePreview] = useControlledState(
		Boolean(item.config.showMessagePreview)
	);

	const urlId = useId();
	const successTextId = useId();

	useEffect(() => {
		return () => {
			dispatch(
				updateItemLocalConfig({
					disableUndo: true,
					itemConfig: {
						showMessagePreview: false,
					},
					itemId: item.itemId,
				})
			);
		};
	}, [item.itemId, dispatch]);

	return (
		<>
			<SelectField
				field={{
					label: Liferay.Language.get('success-message'),
					name: 'source',
					typeOptions: {
						validValues: SUCCESS_MESSAGE_OPTIONS,
					},
				}}
				onValueSelect={(_name, type) => {
					setSelectedSource(type);

					onValueSelect({
						successMessage: {},
					});
				}}
				value={selectedSource}
			/>

			{selectedSource === LAYOUT_OPTION && (
				<LayoutSelector
					mappedLayout={successMessageConfig?.layout}
					onLayoutSelect={(layout) =>
						onValueSelect({
							successMessage: {layout},
						})
					}
				/>
			)}

			{selectedSource === EMBEDDED_OPTION && (
				<>
					<ClayForm.Group small>
						<label htmlFor={successTextId}>
							{Liferay.Language.get('success-text')}
						</label>

						<ClayInput.Group small>
							<ClayInput.GroupItem>
								<ClayInput
									id={successTextId}
									onBlur={() =>
										onValueSelect({
											successMessage: {
												message: {
													...(successMessageConfig?.message ||
														{}),
													[languageId]: successMessage,
												},
											},
										})
									}
									onChange={(event) =>
										setSuccessMessage(event.target.value)
									}
									onKeyDown={(event) => {
										if (event.key === 'Enter') {
											onValueSelect({
												successMessage: {
													message: {
														...(successMessageConfig?.message ||
															{}),
														[languageId]: successMessage,
													},
												},
											});
										}
									}}
									type="text"
									value={successMessage || ''}
								/>
							</ClayInput.GroupItem>

							<ClayInput.GroupItem shrink>
								<CurrentLanguageFlag />
							</ClayInput.GroupItem>
						</ClayInput.Group>
					</ClayForm.Group>

					<ClayToggle
						label={Liferay.Language.get('preview-success-state')}
						onToggle={(checked) => {
							setShowMessagePreview(checked);

							dispatch(
								updateItemLocalConfig({
									disableUndo: true,
									itemConfig: {
										showMessagePreview: checked,
									},
									itemId: item.itemId,
								})
							);
						}}
						toggled={showMessagePreview}
					/>
				</>
			)}

			{selectedSource === URL_OPTION && (
				<ClayForm.Group small>
					<label htmlFor={urlId}>
						{Liferay.Language.get('external-url')}
					</label>

					<ClayInput.Group small>
						<ClayInput.GroupItem>
							<ClayInput
								id={urlId}
								onBlur={() =>
									onValueSelect({
										successMessage: {
											url: {
												...(successMessageConfig?.url ||
													{}),
												[languageId]: url,
											},
										},
									})
								}
								onChange={(event) => setUrl(event.target.value)}
								placeholder="https://url.com"
								type="text"
								value={url || ''}
							/>
						</ClayInput.GroupItem>

						<ClayInput.GroupItem shrink>
							<CurrentLanguageFlag />
						</ClayInput.GroupItem>
					</ClayInput.Group>

					<p
						className="m-0 mt-1 small text-secondary"
						id={helpTextId}
					>
						{Liferay.Language.get(
							'urls-must-have-a-valid-protocol'
						)}
					</p>
				</ClayForm.Group>
			)}

			{selectedSource === DISPLAY_PAGE_OPTION && (
				<DisplayPageSelector
					item={item}
					onValueSelect={onValueSelect}
					selectedSource={selectedSource}
					selectedValue={successMessageConfig?.displayPage}
				/>
			)}
		</>
	);
}

function DisplayPageSelector({
	item,
	onValueSelect,
	selectedSource,
	selectedValue,
}) {
	const dispatch = useDispatch();

	const mappingFields = useSelector((state) => state.mappingFields);

	const [displayPageFields, setDisplayPageFields] = useState(null);

	useEffect(() => {
		if (selectedSource !== DISPLAY_PAGE_OPTION) {
			return;
		}

		const {classNameId, classTypeId} = item.config;

		const key = getMappingFieldsKey({classNameId, classTypeId});

		const fields = mappingFields[key];

		if (fields) {
			setDisplayPageFields(filterFields(fields));
		}
		else {
			InfoItemService.getAvailableStructureMappingFields({
				classNameId,
				classTypeId,
				onNetworkStatus: dispatch,
			}).then((newFields) => {
				dispatch(addMappingFields({fields: newFields, key}));
			});
		}
	}, [dispatch, item, mappingFields, selectedSource]);

	return (
		<MappingFieldSelector
			className="mb-3"
			defaultLabel={`-- ${Liferay.Language.get('none')} --`}
			fields={displayPageFields}
			label={Liferay.Language.get('display-page')}
			onValueSelect={(event) =>
				onValueSelect({
					successMessage: {
						displayPage:
							event.target.value === 'unmapped'
								? null
								: event.target.value,
					},
				})
			}
			value={selectedValue}
		/>
	);
}

function filterFields(fields) {
	return fields.reduce((acc, fieldSet) => {
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

function getSelectedOption(successMessageConfig) {
	if (successMessageConfig.url) {
		return URL_OPTION;
	}

	if (successMessageConfig.message) {
		return EMBEDDED_OPTION;
	}

	if (successMessageConfig.layout?.layoutUuid) {
		return LAYOUT_OPTION;
	}

	if (successMessageConfig.displayPage) {
		return DISPLAY_PAGE_OPTION;
	}

	return EMBEDDED_OPTION;
}
