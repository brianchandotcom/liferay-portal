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
import React, {useCallback, useEffect, useState} from 'react';

import updateItemLocalConfig from '../../../../../../app/actions/updateItemLocalConfig';
import {SelectField} from '../../../../../../app/components/fragment-configuration-fields/SelectField';
import {COMMON_STYLES_ROLES} from '../../../../../../app/config/constants/commonStylesRoles';
import {
	useDispatch,
	useSelector,
} from '../../../../../../app/contexts/StoreContext';
import selectLanguageId from '../../../../../../app/selectors/selectLanguageId';
import updateFormItemConfig from '../../../../../../app/thunks/updateFormItemConfig';
import {formIsMapped} from '../../../../../../app/utils/formIsMapped';
import {getEditableLocalizedValue} from '../../../../../../app/utils/getEditableLocalizedValue';
import Collapse from '../../../../../../common/components/Collapse';
import CurrentLanguageFlag from '../../../../../../common/components/CurrentLanguageFlag';
import {LayoutSelector} from '../../../../../../common/components/LayoutSelector';
import useControlledState from '../../../../../../core/hooks/useControlledState';
import {useId} from '../../../../../../core/hooks/useId';
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

	if (successMessageConfig.layout?.layoutUuid) {
		return LAYOUT_OPTION;
	}

	return EMBEDDED_OPTION;
}
