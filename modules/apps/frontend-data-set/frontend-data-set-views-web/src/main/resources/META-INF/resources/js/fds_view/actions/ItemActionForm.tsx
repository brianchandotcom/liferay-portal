/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayForm, {ClayInput, ClaySelectWithOption} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import ClayPanel from '@clayui/panel';
import classNames from 'classnames';
import {InputLocalized} from 'frontend-js-components-web';
import {fetch} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import {API_URL, OBJECT_RELATIONSHIP} from '../../Constants';
import {FDSViewType} from '../../FDSViews';
import RequiredMark from '../../components/RequiredMark';
import ValidationFeedback from '../../components/ValidationFeedback';
import openDefaultFailureToast from '../../utils/openDefaultFailureToast';
import openDefaultSuccessToast from '../../utils/openDefaultSuccessToast';
import {IFDSAction} from '../Actions';

const MESSAGE_TYPES = [
	{
		label: Liferay.Language.get('info'),
		value: 'info',
	},
	{
		label: Liferay.Language.get('secondary'),
		value: 'secondary',
	},
	{
		label: Liferay.Language.get('success'),
		value: 'success',
	},
	{
		label: Liferay.Language.get('danger'),
		value: 'danger',
	},
	{
		label: Liferay.Language.get('warning'),
		value: 'warning',
	},
];

const TYPES = [
	{
		label: Liferay.Language.get('link'),
		value: 'link',
	},
];

const translationExists = ({translations}: {translations: any}) => {
	return Boolean(Object.keys(translations).find((key) => translations[key]));
};
interface IFDSItemActionFormProps {
	editing?: boolean;
	fdsView: FDSViewType;
	initialValues?: IFDSAction;
	loadFDSActions: () => void;
	namespace: string;
	sections: {
		ACTIONS: string;
		EDIT_ITEM_ACTION: string;
		NEW_ITEM_ACTION: string;
	};
	setActiveSection: (arg: string) => void;
	spritemap: string;
}

const ItemActionForm = ({
	editing = false,
	fdsView,
	initialValues,
	loadFDSActions,
	namespace,
	sections,
	setActiveSection,
	spritemap,
}: IFDSItemActionFormProps) => {
	const [availableIconSymbols, setAvailableIconSymbols] = useState<
		Array<{label: string; value: string}>
	>([]);
	const [
		confirmationMessageTranslations,
		setConfirmationMessageTranslations,
	] = useState(initialValues?.confirmationMessage_i18n ?? {});
	const [labelTranslations, setLabelTranslations] = useState(
		initialValues?.label_i18n ?? {}
	);
	const [labelValidationError, setLabelValidationError] = useState(false);
	const [saveButtonDisabled, setSaveButtonDisabled] = useState(true);
	const [urlValidationError, setURLValidationError] = useState(false);

	const [actionData, setActionData] = useState({
		confirmationMessage: initialValues?.confirmationMessage ?? '',
		confirmationMessageType:
			initialValues?.confirmationMessageType ?? 'warning',
		iconSymbol: initialValues?.icon ?? '',
		label: initialValues?.label ?? '',
		permissionKey: initialValues?.permissionKey ?? '',
		type: initialValues?.type ?? 'link',
		url: initialValues?.url ?? '',
	});

	const saveFDSAction = async () => {
		setSaveButtonDisabled(true);

		const {
			confirmationMessage,
			confirmationMessageType,
			iconSymbol,
			label,
			permissionKey,
			type,
			url,
		} = actionData;

		const body = {
			[OBJECT_RELATIONSHIP.FDS_VIEW_FDS_ACTION_ID]: fdsView.id,
			icon: iconSymbol,
			permissionKey,
			type,
			url,
		} as any;

		if (Liferay.FeatureFlags['LPS-172017']) {
			body.confirmationMessage_i18n = confirmationMessageTranslations;
			body.label_i18n = labelTranslations;

			if (Object.keys(confirmationMessageTranslations).length) {
				body.confirmationMessageType = confirmationMessageType;
			}
		}
		else {
			body.confirmationMessage = confirmationMessage;
			body.label = label;

			if (confirmationMessage) {
				body.confirmationMessageType = confirmationMessageType;
			}
		}

		let fetchURL = API_URL.FDS_ACTIONS;
		let fetchMethod = 'POST';

		if (editing) {
			fetchURL = `${API_URL.FDS_ACTIONS}/${initialValues?.id}`;
			fetchMethod = 'PUT';
		}

		const response = await fetch(fetchURL, {
			body: JSON.stringify(body),
			headers: {
				'Accept': 'application/json',
				'Content-Type': 'application/json',
			},
			method: fetchMethod,
		});

		if (!response.ok) {
			setSaveButtonDisabled(false);

			openDefaultFailureToast();

			return;
		}

		setSaveButtonDisabled(false);

		openDefaultSuccessToast();

		setActiveSection(sections.ACTIONS);

		loadFDSActions();
	};

	const validateForm = () => {
		let valid = true;

		if (!actionData.url) {
			valid = false;
		}

		if (
			Liferay.FeatureFlags['LPS-172017'] &&
			!translationExists({translations: labelTranslations})
		) {
			valid = false;
		}

		if (!Liferay.FeatureFlags['LPS-172017'] && !actionData.label) {
			valid = false;
		}

		setSaveButtonDisabled(!valid);
	};

	useEffect(() => {
		const getIcons = async () => {
			const response = await fetch(spritemap);

			const responseText = await response.text();

			if (responseText.length) {
				const spritemapDocument = new DOMParser().parseFromString(
					responseText,
					'text/xml'
				);

				const symbolElements = spritemapDocument.querySelectorAll(
					'symbol'
				);

				const iconSymbols = Array.from(symbolElements!).map(
					(element) => ({
						label: element.id,
						value: element.id,
					})
				);

				setAvailableIconSymbols(iconSymbols);
			}
		};

		getIcons();
	}, [spritemap]);

	const labelFormElementId = `${namespace}Label`;
	const permissionKeyFormElementId = `${namespace}PermissionKey`;
	const iconFormElementId = `${namespace}Icon`;
	const typeFormElementId = `${namespace}Type`;
	const urlFormElementId = `${namespace}URL`;
	const confirmationMessageFormElementId = `${namespace}ConfirmationMessage`;
	const confirmationMessageTypeFormElementId = `${namespace}ConfirmationMessageType`;

	return (
		<>
			<h2 className="mb-0 p-4">
				{editing
					? initialValues?.label
					: Liferay.Language.get('new-item-action')}
			</h2>

			<ClayPanel
				collapsable
				defaultExpanded
				displayTitle={Liferay.Language.get('display-options')}
			>
				<ClayPanel.Body>
					<ClayLayout.Row>
						<ClayLayout.Col size={8}>
							{Liferay.FeatureFlags['LPS-172017'] ? (
								<InputLocalized
									error={
										labelValidationError
											? Liferay.Language.get(
													'this-field-is-required'
											  )
											: undefined
									}
									label={Liferay.Language.get('label')}
									onBlur={() => {
										setLabelValidationError(
											!translationExists({
												translations: labelTranslations,
											})
										);

										validateForm();
									}}
									onChange={setLabelTranslations}
									placeholder={Liferay.Language.get(
										'action-name'
									)}
									required
									translations={labelTranslations}
								/>
							) : (
								<ClayForm.Group
									className={classNames({
										'has-error': labelValidationError,
									})}
								>
									<label htmlFor={labelFormElementId}>
										{Liferay.Language.get('label')}
									</label>

									<ClayInput
										id={labelFormElementId}
										onBlur={() => {
											setLabelValidationError(
												!actionData.label
											);

											validateForm();
										}}
										onChange={(event) =>
											setActionData({
												...actionData,
												label: event.target.value,
											})
										}
										type="text"
										value={actionData.label}
									/>

									{labelValidationError && (
										<ValidationFeedback />
									)}
								</ClayForm.Group>
							)}
						</ClayLayout.Col>

						<ClayLayout.Col
							className="align-items-center d-flex justify-content-center"
							size={4}
						>
							<ClayIcon
								className="mr-4"
								symbol={actionData.iconSymbol}
							/>

							<ClayForm.Group>
								<label htmlFor={iconFormElementId}>
									{Liferay.Language.get('icon')}
								</label>

								<ClaySelectWithOption
									id={iconFormElementId}
									onChange={(event) =>
										setActionData({
											...actionData,
											iconSymbol: event.target.value,
										})
									}
									options={[
										{
											label: Liferay.Language.get(
												'select'
											),
											value: '',
										},
										...availableIconSymbols,
									]}
									value={actionData.iconSymbol || ''}
								/>
							</ClayForm.Group>
						</ClayLayout.Col>
					</ClayLayout.Row>
				</ClayPanel.Body>
			</ClayPanel>

			<ClayPanel
				collapsable
				defaultExpanded
				displayTitle={Liferay.Language.get('action-behavior')}
			>
				<ClayPanel.Body>
					<ClayLayout.Row justify="start">
						<ClayLayout.Col size={4}>
							<ClayForm.Group>
								<label htmlFor={typeFormElementId}>
									{Liferay.Language.get('type')}

									<RequiredMark />
								</label>

								<ClaySelectWithOption
									disabled={editing}
									id={typeFormElementId}
									onChange={(event) =>
										setActionData({
											...actionData,
											type: event.target.value,
										})
									}
									options={TYPES}
									placeholder={Liferay.Language.get(
										'please-select-an-option'
									)}
									value={actionData.type}
								/>
							</ClayForm.Group>
						</ClayLayout.Col>
					</ClayLayout.Row>

					<ClayLayout.Row justify="start">
						<ClayLayout.Col lg>
							<ClayForm.Group
								className={classNames({
									'has-error': urlValidationError,
								})}
							>
								<label htmlFor={urlFormElementId}>
									{Liferay.Language.get('url')}

									<RequiredMark />
								</label>

								<ClayInput
									component="textarea"
									id={urlFormElementId}
									onBlur={() => {
										setURLValidationError(!actionData.url);

										validateForm();
									}}
									onChange={(event) =>
										setActionData({
											...actionData,
											url: event.target.value,
										})
									}
									placeholder={Liferay.Language.get(
										'add-a-url-here'
									)}
									value={actionData.url}
								/>

								{urlValidationError && <ValidationFeedback />}
							</ClayForm.Group>

							<ClayForm.Group>
								<label htmlFor={permissionKeyFormElementId}>
									{Liferay.Language.get(
										'headless-action-key'
									)}

									<span
										className="label-icon lfr-portal-tooltip ml-2"
										title={Liferay.Language.get(
											'headless-action-key-help'
										)}
									>
										<ClayIcon symbol="question-circle-full" />
									</span>
								</label>

								<ClayInput
									id={permissionKeyFormElementId}
									onChange={(event) =>
										setActionData({
											...actionData,
											permissionKey: event.target.value,
										})
									}
									placeholder={Liferay.Language.get(
										'add-a-value-here'
									)}
								/>
							</ClayForm.Group>

							<ClayLayout.Row>
								<ClayLayout.Col size={8}>
									<ClayForm.Group>
										{Liferay.FeatureFlags['LPS-172017'] ? (
											<InputLocalized
												label={Liferay.Language.get(
													'confirmation-message'
												)}
												onChange={
													setConfirmationMessageTranslations
												}
												placeholder={Liferay.Language.get(
													'add-a-message-here'
												)}
												tooltip={Liferay.Language.get(
													'the-user-will-see-this-message-before-performing-the-action'
												)}
												translations={
													confirmationMessageTranslations
												}
											/>
										) : (
											<>
												<label
													htmlFor={
														confirmationMessageFormElementId
													}
												>
													{Liferay.Language.get(
														'confirmation-message'
													)}
												</label>

												<ClayInput
													id={
														confirmationMessageFormElementId
													}
													onChange={(event) =>
														setActionData({
															...actionData,
															confirmationMessage:
																event.target
																	.value,
														})
													}
													type="text"
													value={
														actionData.confirmationMessage
													}
												/>
											</>
										)}
									</ClayForm.Group>
								</ClayLayout.Col>

								<ClayLayout.Col size={4}>
									<ClayForm.Group>
										<label
											htmlFor={
												confirmationMessageTypeFormElementId
											}
										>
											{Liferay.Language.get(
												'message-type'
											)}
										</label>

										<ClaySelectWithOption
											id={
												confirmationMessageTypeFormElementId
											}
											onChange={(event) =>
												setActionData({
													...actionData,
													confirmationMessageType:
														event.target.value,
												})
											}
											options={MESSAGE_TYPES}
											value={
												actionData.confirmationMessageType
											}
										/>
									</ClayForm.Group>
								</ClayLayout.Col>
							</ClayLayout.Row>
						</ClayLayout.Col>
					</ClayLayout.Row>
				</ClayPanel.Body>
			</ClayPanel>

			<ClayButton.Group className="pb-4 px-4" spaced>
				<ClayButton
					disabled={saveButtonDisabled}
					onClick={saveFDSAction}
				>
					{Liferay.Language.get('save')}
				</ClayButton>

				<ClayButton
					displayType="secondary"
					onClick={() => setActiveSection(sections.ACTIONS)}
				>
					{Liferay.Language.get('cancel')}
				</ClayButton>
			</ClayButton.Group>
		</>
	);
};

export default ItemActionForm;
