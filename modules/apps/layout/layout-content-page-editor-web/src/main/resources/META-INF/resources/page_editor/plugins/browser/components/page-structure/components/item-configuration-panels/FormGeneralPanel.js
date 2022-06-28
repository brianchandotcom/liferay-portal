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
import React, {useCallback, useEffect, useMemo, useState} from 'react';

import updateItemLocalConfig from '../../../../../../app/actions/updateItemLocalConfig';
import {SelectField} from '../../../../../../app/components/fragment-configuration-fields/SelectField';
import {COMMON_STYLES_ROLES} from '../../../../../../app/config/constants/commonStylesRoles';
import {FORM_MAPPING_SOURCES} from '../../../../../../app/config/constants/formMappingSources';
import {
	useDispatch,
	useSelector,
} from '../../../../../../app/contexts/StoreContext';
import selectLanguageId from '../../../../../../app/selectors/selectLanguageId';
import selectSegmentsExperienceId from '../../../../../../app/selectors/selectSegmentsExperienceId';
import FormService from '../../../../../../app/services/FormService';
import updateItemConfig from '../../../../../../app/thunks/updateItemConfig';
import {CACHE_KEYS} from '../../../../../../app/utils/cache';
import {formIsMapped} from '../../../../../../app/utils/formIsMapped';
import {getEditableLocalizedValue} from '../../../../../../app/utils/getEditableLocalizedValue';
import useCache from '../../../../../../app/utils/useCache';
import {useId} from '../../../../../../app/utils/useId';
import Collapse from '../../../../../../common/components/Collapse';
import CurrentLanguageFlag from '../../../../../../common/components/CurrentLanguageFlag';
import {LayoutSelector} from '../../../../../../common/components/LayoutSelector';
import useControlledState from '../../../../../../core/hooks/useControlledState';
import {CommonStyles} from './CommonStyles';

export function FormGeneralPanel({item}) {
	const dispatch = useDispatch();
	const segmentsExperienceId = useSelector(selectSegmentsExperienceId);

	const onValueSelect = useCallback(
		(nextConfig) =>
			dispatch(
				updateItemConfig({
					itemConfig: nextConfig,
					itemId: item.itemId,
					segmentsExperienceId,
				})
			),
		[dispatch, item.itemId, segmentsExperienceId]
	);

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
	const formTypes = useCache({
		fetcher: () => FormService.getAvailableEditPageInfoItemFormProviders(),
		key: [CACHE_KEYS.formTypes],
	});

	const availableFormTypes = useMemo(
		() =>
			formTypes
				? [
						{
							label: Liferay.Language.get('none'),
							value: '',
						},
						...formTypes,
				  ]
				: [],
		[formTypes]
	);

	const {classNameId, classTypeId} = item.config;

	const selectedType = availableFormTypes.find(
		({value}) => value === classNameId
	);

	const selectedSubtype = selectedType?.subtypes.find(
		({value}) => value === classTypeId
	);

	return (
		<div className="mb-3">
			<Collapse label={Liferay.Language.get('form-options')} open>
				{!!availableFormTypes.length && (
					<SelectField
						disabled={!availableFormTypes.length}
						field={{
							label: Liferay.Language.get('content-type'),
							name: 'classNameId',
							typeOptions: {
								validValues: availableFormTypes,
							},
						}}
						onValueSelect={(_name, classNameId) => {
							const type = availableFormTypes.find(
								({value}) => value === classNameId
							);

							return onValueSelect({
								classNameId,
								classTypeId: type?.subtypes?.[0]?.value || '0',
								formConfig:
									FORM_MAPPING_SOURCES.otherContentType,
							});
						}}
						value={selectedType ? classNameId : ''}
					/>
				)}

				{selectedType?.subtypes?.length > 0 && (
					<SelectField
						disabled={!availableFormTypes.length}
						field={{
							label: Liferay.Language.get('subtype'),
							name: 'classTypeId',
							typeOptions: {
								validValues: [
									{
										label: Liferay.Language.get('none'),
										value: '',
									},
									...selectedType.subtypes,
								],
							},
						}}
						onValueSelect={(_name, classTypeId) =>
							onValueSelect({
								classNameId: item.config.classNameId,
								classTypeId,
								formConfig:
									FORM_MAPPING_SOURCES.otherContentType,
							})
						}
						value={selectedSubtype ? classTypeId : ''}
					/>
				)}

				{formIsMapped(item) && (
					<SuccessMessageOptions
						item={item}
						onValueSelect={onValueSelect}
					/>
				)}
			</Collapse>
		</div>
	);
}

const EMBEDDED_OPTION = 'embedded';
const LAYOUT_OPTION = 'fromLayout';
const URL_OPTION = 'url';

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
		label: Liferay.Language.get('url'),
		value: URL_OPTION,
	},
];

function SuccessMessageOptions({item, onValueSelect}) {
	const {successMessage: successMessageConfig = {}} = item.config;

	const languageId = useSelector(selectLanguageId);
	const dispatch = useDispatch();

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
		getEditableLocalizedValue(successMessageConfig.message, languageId)
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
					mappedLayout={successMessageConfig}
					onLayoutSelect={(layout) =>
						onValueSelect({
							successMessage: {...layout},
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
						label={Liferay.Language.get('preview-in-edit-mode')}
						onToggle={(checked) => {
							setShowMessagePreview(checked);

							dispatch(
								updateItemLocalConfig({
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
					<label htmlFor={urlId}>{Liferay.Language.get('url')}</label>

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
								type="text"
								value={url || ''}
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

function getSelectedOption(successMessageConfig) {
	if (successMessageConfig.url) {
		return URL_OPTION;
	}

	if (successMessageConfig.message) {
		return EMBEDDED_OPTION;
	}

	if (successMessageConfig.layoutUuid) {
		return LAYOUT_OPTION;
	}

	return EMBEDDED_OPTION;
}
