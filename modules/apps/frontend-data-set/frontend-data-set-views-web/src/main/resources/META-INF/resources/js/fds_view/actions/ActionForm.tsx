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
import {IFDSAction, SECTIONS} from '../Actions';

const ACTION_TYPE = {
	ASYNC: 'async',
	HEADLESS: 'headless',
	LINK: 'link',
	MODAL: 'modal',
	SIDEPANEL: 'sidePanel',
};

const ITEM_ACTION_TYPES = [
	{
		label: Liferay.Language.get('link'),
		value: ACTION_TYPE.LINK,
	},
];

const CREATION_ACTION_TYPES = [
	{
		label: Liferay.Language.get('link'),
		value: ACTION_TYPE.LINK,
	},
	{
		label: Liferay.Language.get('modal'),
		value: ACTION_TYPE.MODAL,
	},
	{
		label: Liferay.Language.get('side-panel'),
		value: ACTION_TYPE.SIDEPANEL,
	},
];

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

const MODAL_SIZES = [
	{
		label: Liferay.Language.get('full-screen'),
		value: 'full-screen',
	},
	{
		label: Liferay.Language.get('large'),
		value: 'lg',
	},
	{
		label: Liferay.Language.get('small'),
		value: 'sm',
	},
];

const translationExists = ({translations}: {translations: any}) => {
	return Boolean(Object.keys(translations).find((key) => translations[key]));
};

interface IFDSActionFormProps {
	activeTab: number;
	editing?: boolean;
	fdsView: FDSViewType;
	initialValues?: IFDSAction;
	loadFDSActions: () => void;
	namespace: string;
	sections: typeof SECTIONS;
	setActiveSection: (arg: string) => void;
	spritemap: string;
}

const ActionForm = ({
	activeTab,
	editing = false,
	fdsView,
	initialValues,
	loadFDSActions,
	namespace,
	sections,
	setActiveSection,
	spritemap,
}: IFDSActionFormProps) => {
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
	const [saveButtonDisabled, setSaveButtonDisabled] = useState(!editing);
	const [titleTranslations, setTitleTranslations] = useState(
		initialValues?.title_i18n ?? {}
	);
	const [titleValidationError, setTitleValidationError] = useState(false);
	const [urlValidationError, setURLValidationError] = useState(false);

	const [actionData, setActionData] = useState({
		confirmationMessage: initialValues?.confirmationMessage ?? '',
		confirmationMessageType:
			initialValues?.confirmationMessageType ?? 'warning',
		iconSymbol: initialValues?.icon ?? '',
		label: initialValues?.label ?? '',
		modalSize: initialValues?.modalSize ?? '',
		permissionKey: initialValues?.permissionKey ?? '',
		title: initialValues?.title ?? '',
		type: initialValues?.type ?? 'link',
		url: initialValues?.url ?? '',
	});

	const saveFDSAction = async () => {
		setSaveButtonDisabled(true);

		const {
			confirmationMessageType,
			iconSymbol,
			modalSize,
			permissionKey,
			title,
			type,
			url,
		} = actionData;

		const relationShip =
			activeTab === 0
				? OBJECT_RELATIONSHIP.FDS_VIEW_FDS_ACTION_ITEM_ID
				: OBJECT_RELATIONSHIP.FDS_VIEW_FDS_ACTION_CREATION_ID;

		const body = {
			confirmationMessage_i18n: confirmationMessageTranslations,
			icon: iconSymbol,
			label_i18n: labelTranslations,
			modalSize,
			permissionKey,
			[relationShip]: fdsView.id,
			type,
			url,
		} as any;

		if (Liferay.FeatureFlags['LPS-172017']) {
			body.confirmationMessage_i18n = confirmationMessageTranslations;
			body.label_i18n = labelTranslations;
			body.title_i18n = titleTranslations;

			if (Object.keys(confirmationMessageTranslations).length) {
				body.confirmationMessageType = confirmationMessageType;
			}
		}
		else {
			body.confirmationMessage = actionData.confirmationMessage;
			body.label = actionData.label;
			body.title = title;

			if (actionData.confirmationMessage) {
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

		const activeSection =
			activeTab === 0 ? sections.ITEM_ACTIONS : sections.CREATION_ACTIONS;
		setActiveSection(activeSection);

		loadFDSActions();
	};

	const validateForm = () => {
		let valid = true;

		if (
			!actionData.url ||
			!translationExists({translations: labelTranslations})
		) {
			valid = false;
		}

		if (Liferay.FeatureFlags['LPS-172017']) {
			if (
				!translationExists({translations: labelTranslations}) ||
				((actionData.type === ACTION_TYPE.MODAL ||
					actionData.type === ACTION_TYPE.SIDEPANEL) &&
					!translationExists({translations: titleTranslations}))
			) {
				valid = false;
			}
		}

		if (!Liferay.FeatureFlags['LPS-172017']) {
			if (
				!actionData.label ||
				((actionData.type === ACTION_TYPE.MODAL ||
					actionData.type === ACTION_TYPE.SIDEPANEL) &&
					!actionData.title)
			) {
				valid = false;
			}
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

	useEffect(() => {
		validateForm();
	});

	const iconFormElementId = `${namespace}Icon`;
	const confirmationMessageFormElementId = `${namespace}ConfirmationMessage`;
	const confirmationMessageTypeFormElementId = `${namespace}ConfirmationMessageType`;
	const labelFormElementId = `${namespace}Label`;
	const permissionKeyFormElementId = `${namespace}PermissionKey`;
	const titleFormElementId = `${namespace}Title`;
	const typeFormElementId = `${namespace}Type`;
	const urlFormElementId = `${namespace}URL`;
	const modalSizeFormElementId = `${namespace}ModalSize`;

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
									id={labelFormElementId}
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
								<ClayForm.Group>
									<label htmlFor={labelFormElementId}>
										{Liferay.Language.get('label')}
									</label>

									<ClayInput
										id={labelFormElementId}
										onChange={(event) =>
											setActionData({
												...actionData,
												label: event.target.value,
											})
										}
										type="text"
										value={actionData.label}
									/>
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
									options={
										!activeTab
											? ITEM_ACTION_TYPES
											: CREATION_ACTION_TYPES
									}
									placeholder={Liferay.Language.get(
										'please-select-an-option'
									)}
									value={actionData.type}
								/>
							</ClayForm.Group>
						</ClayLayout.Col>

						{actionData.type === ACTION_TYPE.MODAL && (
							<ClayLayout.Col size={4}>
								<ClayForm.Group>
									<label htmlFor={typeFormElementId}>
										{Liferay.Language.get('variant')}

										<RequiredMark />
									</label>

									<ClaySelectWithOption
										id={modalSizeFormElementId}
										onBlur={() => {
											validateForm();
										}}
										onChange={(event) =>
											setActionData({
												...actionData,
												modalSize: event.target.value,
											})
										}
										options={MODAL_SIZES}
										placeholder={Liferay.Language.get(
											'please-select-an-option'
										)}
										value={actionData.modalSize}
									/>
								</ClayForm.Group>
							</ClayLayout.Col>
						)}
					</ClayLayout.Row>

					{activeTab === 1 &&
						(actionData.type === ACTION_TYPE.MODAL ||
							actionData.type === ACTION_TYPE.SIDEPANEL) && (
							<ClayLayout.Row>
								<ClayLayout.Col>
									{Liferay.FeatureFlags['LPS-172017'] ? (
										<InputLocalized
											error={
												titleValidationError
													? Liferay.Language.get(
															'this-field-is-required'
													  )
													: undefined
											}
											id={titleFormElementId}
											label={Liferay.Language.get(
												'title'
											)}
											onBlur={() => {
												setTitleValidationError(
													!translationExists({
														translations: titleTranslations,
													})
												);

												validateForm();
											}}
											onChange={setTitleTranslations}
											placeholder={Liferay.Language.get(
												actionData.type ===
													ACTION_TYPE.MODAL
													? 'action-modal-title-placeholder'
													: 'action-side-panel-title-placeholder'
											)}
											required
											translations={titleTranslations}
										/>
									) : (
										<ClayForm.Group
											className={classNames({
												'has-error': titleValidationError,
											})}
										>
											<label htmlFor={titleFormElementId}>
												{Liferay.Language.get('title')}
											</label>

											<ClayInput
												id={titleFormElementId}
												onBlur={() => {
													setTitleValidationError(
														!actionData.title
													);

													validateForm();
												}}
												onChange={(event) =>
													setActionData({
														...actionData,
														title:
															event.target.value,
													})
												}
												type="text"
												value={actionData.title}
											/>

											{labelValidationError && (
												<ValidationFeedback />
											)}
										</ClayForm.Group>
									)}
								</ClayLayout.Col>
							</ClayLayout.Row>
						)}

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
									value={actionData.permissionKey}
								/>
							</ClayForm.Group>

							{activeTab === 0 &&
								actionData.type === ACTION_TYPE.LINK && (
									<ClayLayout.Row>
										<ClayLayout.Col size={8}>
											<ClayForm.Group>
												{Liferay.FeatureFlags[
													'LPS-172017'
												] ? (
													<InputLocalized
														id={
															confirmationMessageFormElementId
														}
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
																		event
																			.target
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
																event.target
																	.value,
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
								)}
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
					onClick={() => {
						const activeSection =
							activeTab === 0
								? sections.ITEM_ACTIONS
								: sections.CREATION_ACTIONS;
						setActiveSection(activeSection);
					}}
				>
					{Liferay.Language.get('cancel')}
				</ClayButton>
			</ClayButton.Group>
		</>
	);
};

export default ActionForm;
