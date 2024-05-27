/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayForm, {ClayInput, ClaySelectWithOption} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import ClayPanel from '@clayui/panel';
import ClayTabs from '@clayui/tabs';
import classNames from 'classnames';
import {InputLocalized} from 'frontend-js-components-web';
import {fetch, openModal} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import {IDataSet} from '../../../DataSets';
import {FDSViewType} from '../../../FDSViews';
import RequiredMark from '../../../components/RequiredMark';
import Search from '../../../components/Search';
import ValidationFeedback from '../../../components/ValidationFeedback';
import {API_URL, OBJECT_RELATIONSHIP} from '../../../utils/constants';
import openDefaultFailureToast from '../../../utils/openDefaultFailureToast';
import openDefaultSuccessToast from '../../../utils/openDefaultSuccessToast';
import {IAction} from '../Actions';

const ACTION_METHOD = {
	DELETE: 'DELETE',
	GET: 'GET',
	PATCH: 'PATCH',
	POST: 'POST',
};

const ACTION_METHODS = () => {
	const methods = [];

	for (const method in ACTION_METHOD) {
		methods.push({label: method, value: method});
	}

	return methods;
};

const ACTION_TYPE = {
	ASYNC: 'async',
	HEADLESS: 'headless',
	LINK: 'link',
	MODAL: 'modal',
	SIDEPANEL: 'sidePanel',
};

const ACTION_TYPES = [
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

const ITEM_ACTION_TYPES = [
	{
		label: Liferay.Language.get('async'),
		value: ACTION_TYPE.ASYNC,
	},
	{
		label: Liferay.Language.get('headless'),
		value: ACTION_TYPE.HEADLESS,
	},
].concat(ACTION_TYPES);

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

const ActionForm = ({
	activeTab,
	dataSet,
	editing = false,
	initialValues,
	namespace,
	onCancel,
	onSave,
	spritemap,
}: {
	activeTab: number;
	dataSet: IDataSet | FDSViewType;
	editing?: boolean;
	initialValues?: IAction;
	namespace: string;
	onCancel: () => void;
	onSave: () => void;
	spritemap: string;
}) => {
	const [activeMessageTab, setActiveMessageTab] = useState(0);
	const [availableIconSymbols, setAvailableIconSymbols] = useState<
		Array<{label: string; value: string}>
	>([]);
	const [
		confirmationMessageTranslations,
		setConfirmationMessageTranslations,
	] = useState(initialValues?.confirmationMessage_i18n ?? {});
	const [errorMessageTranslations, setErrorMessageTranslations] = useState(
		initialValues?.errorMessage_i18n ?? {}
	);
	const [labelTranslations, setLabelTranslations] = useState(
		initialValues?.label_i18n ?? {}
	);
	const [labelValidationError, setLabelValidationError] = useState(false);
	const [
		permissionKeyValidationError,
		setPermissionKeyValidationError,
	] = useState(false);
	const [saveButtonDisabled, setSaveButtonDisabled] = useState(!editing);
	const [
		successMessageTranslations,
		setSuccessMessageTranslations,
	] = useState(initialValues?.successMessage_i18n ?? {});
	const [titleTranslations, setTitleTranslations] = useState(
		initialValues?.title_i18n ?? {}
	);
	const [urlValidationError, setURLValidationError] = useState(false);

	const [actionData, setActionData] = useState({
		confirmationMessage: initialValues?.confirmationMessage ?? '',
		confirmationMessageType:
			initialValues?.confirmationMessageType ?? 'warning',
		iconSymbol: initialValues?.icon ?? '',
		label: initialValues?.label ?? '',
		method: initialValues?.method ?? '',
		modalSize: initialValues?.modalSize ?? '',
		permissionKey: initialValues?.permissionKey ?? '',
		title: initialValues?.title ?? '',
		type: initialValues?.type ?? 'link',
		url: initialValues?.url ?? '',
	});

	const handleActionTypeChange = (event: any) => {
		const type = event.target.value;

		setActionData({
			...actionData,
			method: type === ACTION_TYPE.ASYNC ? ACTION_METHOD.DELETE : '',
			modalSize: type === ACTION_TYPE.MODAL ? MODAL_SIZES[0].value : '',
			type,
		});
	};

	const saveAction = async () => {
		setSaveButtonDisabled(true);

		const {
			confirmationMessageType,
			iconSymbol,
			method,
			modalSize,
			permissionKey,
			type,
			url,
		} = actionData;

		const relationship: string =
			activeTab === 0
				? OBJECT_RELATIONSHIP.DATA_SET_ITEM_ACTION_ID
				: OBJECT_RELATIONSHIP.DATA_SET_CREATION_ACTION_ID;

		const body = {
			confirmationMessage_i18n: confirmationMessageTranslations,
			icon: iconSymbol,
			label_i18n: labelTranslations,
			method,
			modalSize,
			permissionKey,
			[relationship]: dataSet.id,
			title_i18n: titleTranslations,
			type,
			url,
		} as any;

		if (Object.keys(confirmationMessageTranslations).length) {
			body.confirmationMessageType = confirmationMessageType;
		}

		if (
			actionData.type === ACTION_TYPE.ASYNC ||
			actionData.type === ACTION_TYPE.HEADLESS
		) {
			body.errorMessage_i18n = errorMessageTranslations;
			body.successMessage_i18n = successMessageTranslations;
		}

		if (actionData.type === ACTION_TYPE.ASYNC) {
			body.method = method;
		}

		let apiURL = API_URL.ACTIONS;
		let fetchMethod = 'POST';

		if (editing) {
			apiURL = `${apiURL}/${initialValues?.id}`;
			fetchMethod = 'PUT';
		}

		const response = await fetch(apiURL, {
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

		onSave();
	};

	const validateForm = ({
		labelTranslations,
		permissionKey,
		url,
	}: {
		labelTranslations: Partial<
			Liferay.Language.FullyLocalizedValue<string>
		>;
		permissionKey: string;
		url: string;
	}) => {
		let valid = true;

		if (
			(!url && actionData.type !== ACTION_TYPE.HEADLESS) ||
			(!permissionKey && actionData.type === ACTION_TYPE.HEADLESS) ||
			!translationExists({
				translations: labelTranslations,
			})
		) {
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

	const iconFormElementId = `${namespace}Icon`;
	const confirmationMessageFormElementId = `${namespace}ConfirmationMessage`;
	const confirmationMessageTypeFormElementId = `${namespace}ConfirmationMessageType`;
	const errorMessageFormElementId = `${namespace}ErrorMessage`;
	const labelFormElementId = `${namespace}Label`;
	const methodFormElementId = `${namespace}Method`;
	const modalSizeFormElementId = `${namespace}ModalSize`;
	const permissionKeyFormElementId = `${namespace}PermissionKey`;
	const successMessageFormElementId = `${namespace}SuccessMessage`;
	const titleFormElementId = `${namespace}Title`;
	const typeFormElementId = `${namespace}Type`;
	const urlFormElementId = `${namespace}URL`;

	const ModalBody = ({closeModal}: {closeModal: Function}) => {
		const [filteredIconSymbols, setFilteredIconSymbols] = useState<
			Array<{label: string; value: string}>
		>(availableIconSymbols);
		const [query, setQuery] = useState('');

		const onSearch = (query: string) => {
			setQuery(query);

			const regexp = new RegExp(query, 'i');

			setFilteredIconSymbols(
				query
					? availableIconSymbols.filter((item) =>
							String(item.value).match(regexp)
					  )
					: availableIconSymbols
			);
		};

		return (
			<>
				<Search onSearch={onSearch} query={query} />

				<ClayLayout.SheetSection>
					<ul className="list-unstyled mt-4 row">
						{filteredIconSymbols.map((item) => {
							return (
								<li
									className="col-md-4"
									key={item.value}
									onClick={() => {
										setActionData({
											...actionData,
											iconSymbol: item.value,
										});
										closeModal();
									}}
								>
									<ClayIcon
										className="mr-2"
										spritemap={spritemap}
										symbol={item.value}
									/>

									<span>{item.label}</span>
								</li>
							);
						})}
					</ul>
				</ClayLayout.SheetSection>
			</>
		);
	};

	return (
		<>
			<h2 className="mb-0 p-4">
				{editing && initialValues?.label}

				{!editing &&
					activeTab === 0 &&
					Liferay.Language.get('new-item-action')}

				{!editing &&
					activeTab === 1 &&
					Liferay.Language.get('new-creation-action')}
			</h2>

			<ClayPanel
				collapsable
				defaultExpanded
				displayTitle={Liferay.Language.get('display-options')}
				displayType="unstyled"
			>
				<ClayPanel.Body>
					<ClayLayout.Row>
						<ClayLayout.Col size={8}>
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
								onChange={(translations) => {
									setLabelTranslations(translations);

									setLabelValidationError(
										!translationExists({
											translations,
										})
									);

									validateForm({
										labelTranslations: translations,
										permissionKey: actionData.permissionKey,
										url: actionData.url,
									});
								}}
								placeholder={Liferay.Language.get(
									'action-name'
								)}
								required
								translations={labelTranslations}
							/>
						</ClayLayout.Col>

						<ClayLayout.Col size={4}>
							<ClayForm.Group>
								<label htmlFor={iconFormElementId}>
									{Liferay.Language.get('icon')}
								</label>

								<ClayInput.Group>
									<ClayInput.GroupItem prepend shrink>
										<ClayInput.GroupText>
											<ClayIcon
												spritemap={spritemap}
												symbol={actionData.iconSymbol}
											/>
										</ClayInput.GroupText>
									</ClayInput.GroupItem>

									<ClayInput.GroupItem append>
										<ClayInput
											onChange={({target: {value}}) =>
												setActionData({
													...actionData,
													iconSymbol: value,
												})
											}
											placeholder={Liferay.Language.get(
												'no-icon-selected'
											)}
											type="text"
											value={actionData.iconSymbol}
										/>
									</ClayInput.GroupItem>

									<ClayButtonWithIcon
										aria-label={Liferay.Language.get(
											actionData.iconSymbol !== ''
												? 'change-icon'
												: 'add-icon'
										)}
										className="ml-2"
										displayType="secondary"
										id={iconFormElementId}
										onClick={() =>
											openModal({
												bodyComponent: ModalBody,
												containerProps: {
													className:
														'dsm-actions-icon-selection-modal',
												},
												size: 'lg',
												title: Liferay.Language.get(
													'select-an-icon'
												),
											})
										}
										symbol={
											actionData.iconSymbol !== ''
												? 'change'
												: 'plus'
										}
									/>

									{actionData.iconSymbol !== '' && (
										<ClayButtonWithIcon
											aria-label={Liferay.Language.get(
												'remove-icon'
											)}
											className="ml-2"
											displayType="secondary"
											onClick={() =>
												setActionData({
													...actionData,
													iconSymbol: '',
												})
											}
											symbol="trash"
										/>
									)}
								</ClayInput.Group>
							</ClayForm.Group>
						</ClayLayout.Col>
					</ClayLayout.Row>
				</ClayPanel.Body>
			</ClayPanel>

			<ClayPanel
				collapsable
				defaultExpanded
				displayTitle={Liferay.Language.get('action-behavior')}
				displayType="unstyled"
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
										handleActionTypeChange(event)
									}
									options={
										activeTab === 0
											? ITEM_ACTION_TYPES
											: ACTION_TYPES
									}
									placeholder={Liferay.Language.get(
										'please-select-an-option'
									)}
									value={actionData.type}
								/>
							</ClayForm.Group>
						</ClayLayout.Col>

						{actionData.type === ACTION_TYPE.ASYNC && (
							<ClayLayout.Col size={4}>
								<ClayForm.Group>
									<label htmlFor={methodFormElementId}>
										{Liferay.Language.get('method')}

										<RequiredMark />
									</label>

									<ClaySelectWithOption
										id={methodFormElementId}
										onChange={(event) =>
											setActionData({
												...actionData,
												method: event.target.value,
											})
										}
										options={ACTION_METHODS()}
										placeholder={Liferay.Language.get(
											'please-select-an-option'
										)}
										value={actionData.method}
									/>
								</ClayForm.Group>
							</ClayLayout.Col>
						)}

						{actionData.type === ACTION_TYPE.MODAL && (
							<ClayLayout.Col size={4}>
								<ClayForm.Group>
									<label htmlFor={modalSizeFormElementId}>
										{Liferay.Language.get('variant')}

										<RequiredMark />
									</label>

									<ClaySelectWithOption
										id={modalSizeFormElementId}
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

					{(actionData.type === ACTION_TYPE.MODAL ||
						actionData.type === ACTION_TYPE.SIDEPANEL) && (
						<ClayLayout.Row>
							<ClayLayout.Col>
								<InputLocalized
									helpMessage={Liferay.Language.get(
										'side-panel-title-help'
									)}
									id={titleFormElementId}
									label={Liferay.Language.get('title')}
									onChange={(translations) => {
										setTitleTranslations(translations);
									}}
									placeholder={
										actionData.type === ACTION_TYPE.MODAL
											? Liferay.Language.get(
													'add-the-title-of-the-modal'
											  )
											: Liferay.Language.get(
													'add-the-title-of-the-side-panel'
											  )
									}
									translations={titleTranslations}
								/>
							</ClayLayout.Col>
						</ClayLayout.Row>
					)}

					{actionData.type !== ACTION_TYPE.HEADLESS && (
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
										onChange={(event) => {
											const url = event.target.value;

											setActionData({
												...actionData,
												url,
											});

											setURLValidationError(!url);

											validateForm({
												labelTranslations,
												permissionKey:
													actionData.permissionKey,
												url,
											});
										}}
										placeholder={Liferay.Language.get(
											'add-a-url-here'
										)}
										value={actionData.url}
									/>

									{urlValidationError && (
										<ValidationFeedback />
									)}
								</ClayForm.Group>
							</ClayLayout.Col>
						</ClayLayout.Row>
					)}

					<ClayLayout.Row justify="start">
						<ClayLayout.Col>
							<ClayForm.Group
								className={classNames({
									'has-error': permissionKeyValidationError,
								})}
							>
								<label htmlFor={permissionKeyFormElementId}>
									{Liferay.Language.get(
										'headless-action-key'
									)}

									{actionData.type ===
										ACTION_TYPE.HEADLESS && (
										<RequiredMark />
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
									onChange={(event) => {
										const permissionKey =
											event.target.value;

										setActionData({
											...actionData,
											permissionKey,
										});

										if (
											actionData.type ===
											ACTION_TYPE.HEADLESS
										) {
											setPermissionKeyValidationError(
												!permissionKey
											);
										}

										validateForm({
											labelTranslations,
											permissionKey,
											url: actionData.url,
										});
									}}
									placeholder={Liferay.Language.get(
										'add-a-value-here'
									)}
									value={actionData.permissionKey}
								/>

								{permissionKeyValidationError && (
									<ValidationFeedback />
								)}
							</ClayForm.Group>
						</ClayLayout.Col>
					</ClayLayout.Row>

					{activeTab === 0 && (
						<ClayLayout.Row>
							<ClayLayout.Col size={8}>
								<ClayForm.Group>
									<InputLocalized
										id={confirmationMessageFormElementId}
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
								</ClayForm.Group>
							</ClayLayout.Col>

							<ClayLayout.Col size={4}>
								<ClayForm.Group>
									<label
										htmlFor={
											confirmationMessageTypeFormElementId
										}
									>
										{Liferay.Language.get('message-type')}
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
					)}
				</ClayPanel.Body>
			</ClayPanel>

			{(actionData.type === ACTION_TYPE.ASYNC ||
				actionData.type === ACTION_TYPE.HEADLESS) && (
				<ClayPanel
					collapsable
					defaultExpanded
					displayTitle={Liferay.Language.get('status-messages')}
					displayType="unstyled"
				>
					<ClayPanel.Body>
						<ClayForm.Text className="c-pb-3">
							{Liferay.Language.get(
								'you-can-write-status-messages-related-to-this-action'
							)}
						</ClayForm.Text>

						<ClayTabs
							activation="automatic"
							active={activeMessageTab}
							onActiveChange={(tab: number) => {
								setActiveMessageTab(tab);
							}}
						>
							<ClayTabs.Item>
								{Liferay.Language.get('success')}
							</ClayTabs.Item>

							<ClayTabs.Item>
								{Liferay.Language.get('error')}
							</ClayTabs.Item>
						</ClayTabs>

						<ClayTabs.Content
							active={activeMessageTab}
							className="action-messages"
							fade
						>
							<ClayTabs.TabPane
								aria-labelledby={Liferay.Language.get(
									'success'
								)}
							>
								<ClayForm.Group>
									<InputLocalized
										id={successMessageFormElementId}
										label={Liferay.Language.get('message')}
										onChange={setSuccessMessageTranslations}
										placeholder={Liferay.Language.get(
											'add-a-message-here'
										)}
										tooltip={Liferay.Language.get(
											'the-user-will-see-this-message-if-the-action-is-successful'
										)}
										translations={
											successMessageTranslations
										}
									/>
								</ClayForm.Group>
							</ClayTabs.TabPane>

							<ClayTabs.TabPane
								aria-labelledby={Liferay.Language.get('error')}
							>
								<ClayForm.Group>
									<InputLocalized
										id={errorMessageFormElementId}
										label={Liferay.Language.get('message')}
										onChange={setErrorMessageTranslations}
										placeholder={Liferay.Language.get(
											'add-a-message-here'
										)}
										tooltip={Liferay.Language.get(
											'the-user-will-see-this-message-if-the-action-fails'
										)}
										translations={errorMessageTranslations}
									/>
								</ClayForm.Group>
							</ClayTabs.TabPane>
						</ClayTabs.Content>
					</ClayPanel.Body>
				</ClayPanel>
			)}

			<ClayButton.Group className="pb-4 px-4" spaced>
				<ClayButton disabled={saveButtonDisabled} onClick={saveAction}>
					{Liferay.Language.get('save')}
				</ClayButton>

				<ClayButton displayType="secondary" onClick={onCancel}>
					{Liferay.Language.get('cancel')}
				</ClayButton>
			</ClayButton.Group>
		</>
	);
};

export default ActionForm;
