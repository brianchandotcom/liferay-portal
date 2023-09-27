/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayForm, {ClayInput, ClayToggle} from '@clayui/form';
import ClayPanel from '@clayui/panel';
import {useId} from 'frontend-js-components-web';
import {openToast} from 'frontend-js-web';
import React, {useCallback, useEffect, useState} from 'react';

import {addMappingFields} from '../../../../../../app/actions/index';
import updateItemLocalConfig from '../../../../../../app/actions/updateItemLocalConfig';
import {CheckboxField} from '../../../../../../app/components/fragment_configuration_fields/CheckboxField';
import {SelectField} from '../../../../../../app/components/fragment_configuration_fields/SelectField';
import {TextField} from '../../../../../../app/components/fragment_configuration_fields/TextField';
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
import {setIn} from '../../../../../../app/utils/setIn';
import CurrentLanguageFlag from '../../../../../../common/components/CurrentLanguageFlag';
import {LayoutSelector} from '../../../../../../common/components/LayoutSelector';
import MappingFieldSelector from '../../../../../../common/components/MappingFieldSelector';
import {CommonStyles} from './CommonStyles';
import ContainerDisplayOptions from './ContainerDisplayOptions';
import FormMappingOptions from './FormMappingOptions';

export function FormGeneralPanel({item}) {
	const dispatch = useDispatch();

	const onValueSelect = useCallback(
		(nextConfig, overridePreviousConfig = true) =>
			dispatch(
				updateFormItemConfig({
					itemConfig: nextConfig,
					itemId: item.itemId,
					overridePreviousConfig,
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
		<div className="mb-3 panel-group-sm">
			<ClayPanel
				collapsable
				defaultExpanded
				displayTitle={Liferay.Language.get('form-container-options')}
				displayType="unstyled"
				showCollapseIcon
			>
				<ClayPanel.Body>
					<FormMappingOptions
						item={item}
						onValueSelect={onValueSelect}
					/>

					{formIsMapped(item) && (
						<>
							<SuccessInteractionOptions
								item={item}
								onValueSelect={onValueSelect}
							/>

							<ContainerDisplayOptions item={item} />
						</>
					)}
				</ClayPanel.Body>
			</ClayPanel>
		</div>
	);
}

const EMBEDDED_OPTION = 'embedded';
const LAYOUT_OPTION = 'page';
const URL_OPTION = 'url';
const DISPLAY_PAGE_OPTION = 'displayPage';
const STAY_OPTION = 'none';

const SUCCESS_MESSAGE_OPTIONS = [
	{
		label: Liferay.Language.get('stay-in-page'),
		value: STAY_OPTION,
	},
	{
		label: Liferay.Language.get('show-embedded-message'),
		value: EMBEDDED_OPTION,
	},
	{
		label: Liferay.Language.get('go-to-page'),
		value: LAYOUT_OPTION,
	},
	{
		label: Liferay.Language.get('go-to-external-url'),
		value: URL_OPTION,
	},
	{
		label: Liferay.Language.get('go-to-entry-display-page'),
		value: DISPLAY_PAGE_OPTION,
	},
];

function SuccessInteractionOptions({item, onValueSelect}) {
	const {successMessage: interactionConfig = {}} = item.config;

	const {
		displayPage,
		layout,
		message,
		notificationText,
		showNotification,
		type,
		url,
	} = interactionConfig || {};

	const dispatch = useDispatch();

	const languageId = useSelector(selectLanguageId);

	const helpTextId = useId();
	const previewId = useId();

	const localizedNotificationText = getEditableLocalizedValue(
		notificationText,
		languageId,
		Liferay.Language.get('your-information-was-successfully-received')
	);

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

	const onConfigChange = useCallback(
		(config, override = false) => {
			const nextConfig = override
				? config
				: {
						...interactionConfig,
						...config,
				  };

			onValueSelect({successMessage: nextConfig}, false);
		},
		[interactionConfig, onValueSelect]
	);

	const [showNotificationPreview, setShowNotificationPreview] = useState(
		item.config.showNotificationPreview
	);

	const onPreviewNotification = (checked) => {
		setShowNotificationPreview(checked);

		dispatch(
			updateItemLocalConfig({
				disableUndo: true,
				itemConfig: {
					showNotificationPreview: checked,
				},
				itemId: item.itemId,
			})
		);
	};

	const hidePreview = () => {
		const previewElement = document.getElementById(previewId);

		previewElement?.remove();
	};

	return (
		<>
			<SelectField
				field={{
					label: Liferay.Language.get('success-interaction'),
					name: 'source',
					typeOptions: {
						validValues: SUCCESS_MESSAGE_OPTIONS,
					},
				}}
				onValueSelect={(_name, type) => onConfigChange({type}, true)}
				value={type || EMBEDDED_OPTION}
			/>

			{type === LAYOUT_OPTION && (
				<LayoutSelector
					mappedLayout={layout}
					onLayoutSelect={(selectedLayout) =>
						onConfigChange({layout: selectedLayout})
					}
				/>
			)}

			{(!type || type === EMBEDDED_OPTION) && (
				<>
					<ClayForm.Group small>
						<ClayInput.Group className="align-items-end" small>
							<ClayInput.GroupItem>
								<TextField
									field={{
										label: Liferay.Language.get(
											'embedded-message'
										),
									}}
									onValueSelect={(_, value) =>
										onConfigChange({
											message: setIn(
												message || {},
												languageId,
												value
											),
											type: EMBEDDED_OPTION,
										})
									}
									value={getEditableLocalizedValue(
										message,
										languageId,
										Liferay.Language.get(
											'thank-you.-your-information-was-successfully-received'
										)
									)}
								/>
							</ClayInput.GroupItem>

							<ClayInput.GroupItem shrink>
								<CurrentLanguageFlag />
							</ClayInput.GroupItem>
						</ClayInput.Group>
					</ClayForm.Group>
					<ClayForm.Group small>
						<ClayToggle
							label={Liferay.Language.get(
								'preview-embedded-message'
							)}
							onToggle={(checked) => {
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
							toggled={Boolean(item.config.showMessagePreview)}
						/>
					</ClayForm.Group>
				</>
			)}

			{type === URL_OPTION && (
				<ClayForm.Group small>
					<ClayInput.Group className="align-items-end" small>
						<ClayInput.GroupItem>
							<TextField
								aria-describedby={helpTextId}
								field={{
									label: Liferay.Language.get('external-url'),
									typeOptions: {
										placeholder: 'https://url.com',
									},
								}}
								onValueSelect={(_, value) =>
									onConfigChange({
										url: setIn(
											url || {},
											languageId,
											value
										),
									})
								}
								value={getEditableLocalizedValue(
									url,
									languageId
								)}
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

			{type === DISPLAY_PAGE_OPTION && (
				<DisplayPageSelector
					item={item}
					onConfigChange={onConfigChange}
					selectedValue={displayPage}
					type={type}
				/>
			)}

			{type !== URL_OPTION && (
				<>
					<ClayForm.Group small>
						<CheckboxField
							field={{
								label: Liferay.Language.get(
									'show-notification-when-form-is-submitted'
								),
								name: 'showNotification',
							}}
							onValueSelect={(name, value) => {
								onConfigChange({[name]: value});
							}}
							value={showNotification}
						/>
					</ClayForm.Group>

					{showNotification && (
						<>
							<ClayForm.Group small>
								<ClayInput.Group
									className="align-items-end c-mb-2"
									small
								>
									<ClayInput.GroupItem>
										<TextField
											field={{
												label: Liferay.Language.get(
													'success-notification-text'
												),
											}}
											onValueSelect={(_, value) => {
												if (showNotificationPreview) {
													onPreviewNotification(
														false
													);
													hidePreview();
												}
												onConfigChange({
													notificationText: setIn(
														notificationText || {},
														languageId,
														value
													),
												});
											}}
											value={localizedNotificationText}
										/>
									</ClayInput.GroupItem>

									<ClayInput.GroupItem shrink>
										<CurrentLanguageFlag />
									</ClayInput.GroupItem>
								</ClayInput.Group>

								<ClayButton
									aria-label={Liferay.Language.get(
										'preview-success-notification'
									)}
									displayType="secondary"
									onClick={() => {
										onPreviewNotification(true);
										openToast({
											message: localizedNotificationText,
											onClose: () =>
												onPreviewNotification(false),
											toastProps: {
												id: previewId,
											},
										});
									}}
								>
									{Liferay.Language.get('preview')}
								</ClayButton>
							</ClayForm.Group>
						</>
					)}
				</>
			)}
		</>
	);
}

function DisplayPageSelector({item, onConfigChange, selectedValue, type}) {
	const dispatch = useDispatch();

	const mappingFields = useSelector((state) => state.mappingFields);

	const [displayPageFields, setDisplayPageFields] = useState(null);

	useEffect(() => {
		if (type !== DISPLAY_PAGE_OPTION) {
			return;
		}

		const {classNameId, classTypeId} = item.config;

		const key = getMappingFieldsKey({classNameId, classTypeId});

		const fieldSets = mappingFields[key];

		if (fieldSets) {
			setDisplayPageFields(filterFields(fieldSets));
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
	}, [dispatch, item, mappingFields, type]);

	return (
		<MappingFieldSelector
			className="mb-3"
			defaultLabel={`-- ${Liferay.Language.get('none')} --`}
			fields={displayPageFields}
			label={Liferay.Language.get('display-page')}
			onValueSelect={(event) =>
				onConfigChange({
					displayPage:
						event.target.value === 'unmapped'
							? null
							: event.target.value,
				})
			}
			value={selectedValue}
		/>
	);
}

function filterFields(fieldSets) {
	return fieldSets.reduce((acc, fieldSet) => {
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
