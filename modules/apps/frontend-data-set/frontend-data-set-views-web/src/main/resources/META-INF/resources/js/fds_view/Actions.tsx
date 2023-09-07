/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayBreadcrumb from '@clayui/breadcrumb';
import ClayButton from '@clayui/button';
import ClayForm, {ClayInput, ClaySelectWithOption} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayPanel from '@clayui/panel';
import ClayTabs from '@clayui/tabs';
import {InputLocalized} from 'frontend-js-components-web';
import {fetch} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import {API_URL, OBJECT_RELATIONSHIP} from '../Constants';
import {IFDSViewSectionInterface} from '../FDSView';
import OrderableTable from '../components/OrderableTable';
import RequiredMark from '../components/RequiredMark';
import openDefaultFailureToast from '../utils/openDefaultFailureToast';
import openDefaultSuccessToast from '../utils/openDefaultSuccessToast';

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

const SECTIONS = {
	ACTIONS: 'actions',
	NEW_ITEM_ACTION: 'new-item-action',
};

const TYPES = [
	{
		label: Liferay.Language.get('asynchronous'),
		value: 'async',
	},
	{
		label: Liferay.Language.get('headless'),
		value: 'headless',
	},
	{
		label: Liferay.Language.get('link'),
		value: 'link',
	},
	{
		label: Liferay.Language.get('modal'),
		value: 'modal',
	},
	{
		label: Liferay.Language.get('side-panel'),
		value: 'side-panel',
	},
];

interface IFDSAction {
	[OBJECT_RELATIONSHIP.FDS_VIEW_FDS_ACTION]: any;
	icon: string;
	id: number;
	type: string;
	url: string;
}

const noop = () => {};

const Actions = ({fdsView, namespace, spritemap}: IFDSViewSectionInterface) => {
	const [activeSection, setActiveSection] = useState(SECTIONS.ACTIONS);
	const [activeTab, setActiveTab] = useState(0);
	const [availableIconSymbols, setAvailableIconSymbols] = useState<
		Array<{label: string; value: string}>
	>([]);
	const [confirmationMessage, setConfirmationMessage] = useState('');
	const [
		confirmationMessageTranslations,
		setConfirmationMessageTranslations,
	] = useState({});
	const [fdsActions, setFDSActions] = useState<Array<IFDSAction>>([]);
	const [iconSymbol, setIconSymbol] = useState('bolt');
	const [label, setLabel] = useState('');
	const [labelTranslations, setLabelTranslations] = useState({});
	const [loading, setLoading] = useState(true);
	const [newActionsOrder, setNewActionsOrder] = useState<string>('');
	const [type, setType] = useState('link');
	const [saveButtonDisabled, setSaveButtonDisabled] = useState(false);
	const [url, setURL] = useState('');

	const getFDSActions = async () => {
		setLoading(true);

		const response = await fetch(
			`${API_URL.FDS_ACTIONS}?filter=(${OBJECT_RELATIONSHIP.FDS_VIEW_FDS_ACTION_ID} eq '${fdsView.id}')&nestedFields=${OBJECT_RELATIONSHIP.FDS_VIEW_FDS_ACTION}&sort=dateCreated:desc`
		);

		if (!response.ok) {
			setLoading(false);

			openDefaultFailureToast();

			return;
		}

		const responseJSON = await response.json();

		const storedFDSActions: IFDSAction[] = responseJSON.items;

		let ordered = storedFDSActions;
		let notOrdered: IFDSAction[] = [];

		const fdsActionsOrder =
			storedFDSActions?.[0]?.[OBJECT_RELATIONSHIP.FDS_VIEW_FDS_ACTION]
				?.fdsActionsOrder;

		if (fdsActionsOrder) {
			const fdsActionsOrderArray = fdsActionsOrder.split(',') as string[];

			ordered = fdsActionsOrderArray
				.map((fdsActionId) =>
					storedFDSActions.find(
						(fdsAction) => fdsAction.id === Number(fdsActionId)
					)
				)
				.filter(Boolean) as IFDSAction[];

			notOrdered = storedFDSActions.filter(
				(fdsAction) =>
					!fdsActionsOrderArray.includes(String(fdsAction.id))
			);
		}

		setFDSActions([...notOrdered, ...ordered]);

		setLoading(false);
	};

	const saveFDSAction = async () => {
		setSaveButtonDisabled(true);

		const body = {
			[OBJECT_RELATIONSHIP.FDS_VIEW_FDS_ACTION_ID]: fdsView.id,
			icon: iconSymbol,
			type,
			url,
		} as any;

		if (Liferay.FeatureFlags['LPS-172017']) {
			body.confirmationMessage_i18n = confirmationMessageTranslations;
			body.label_i18n = labelTranslations;
		}
		else {
			body.confirmationMessage = confirmationMessage;
			body.label = labelTranslations;
		}

		const response = await fetch(API_URL.FDS_ACTIONS, {
			body: JSON.stringify(body),
			headers: {
				'Accept': 'application/json',
				'Content-Type': 'application/json',
			},
			method: 'POST',
		});

		if (!response.ok) {
			setSaveButtonDisabled(false);

			openDefaultFailureToast();

			return;
		}

		await response.json();

		setSaveButtonDisabled(false);

		openDefaultSuccessToast();

		setActiveSection(SECTIONS.ACTIONS);

		getFDSActions();
	};

	const updateFDSActionsOrder = async () => {
		const response = await fetch(
			`${API_URL.FDS_VIEWS}/by-external-reference-code/${fdsView.externalReferenceCode}`,
			{
				body: JSON.stringify({
					fdsActionsOrder: newActionsOrder,
				}),
				headers: {
					'Accept': 'application/json',
					'Content-Type': 'application/json',
				},
				method: 'PATCH',
			}
		);

		if (!response.ok) {
			openDefaultFailureToast();

			return;
		}

		const responseJSON = await response.json();

		const fdsFiltersOrder = responseJSON?.fdsActionsOrder;

		if (fdsFiltersOrder && fdsFiltersOrder === newActionsOrder) {
			openDefaultSuccessToast();

			setNewActionsOrder('');
		}
		else {
			openDefaultFailureToast();
		}
	};

	useEffect(() => {
		getFDSActions();

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

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
	const iconFormElementId = `${namespace}Icon`;
	const typeFormElementId = `${namespace}Type`;
	const urlFormElementId = `${namespace}URL`;
	const confirmationMessageFormElementId = `${namespace}ConfirmationMessage`;
	const confirmationMessageTypeFormElementId = `${namespace}ConfirmationMessageType`;

	const ConfirmationMessageRow = () => (
		<ClayLayout.Row>
			<ClayLayout.Col size={8}>
				<ClayForm.Group>
					{Liferay.FeatureFlags['LPS-172017'] ? (
						<InputLocalized
							label={Liferay.Language.get('confirmation-message')}
							onChange={setConfirmationMessageTranslations}
							placeholder={Liferay.Language.get(
								'add-a-message-here'
							)}
							translations={confirmationMessageTranslations}
						/>
					) : (
						<ClayForm.Group>
							<label htmlFor={confirmationMessageFormElementId}>
								{Liferay.Language.get('confirmation-message')}
							</label>

							<ClayInput
								id={confirmationMessageFormElementId}
								onChange={(event) =>
									setConfirmationMessage(event.target.value)
								}
								type="text"
								value={label}
							/>
						</ClayForm.Group>
					)}
				</ClayForm.Group>
			</ClayLayout.Col>

			<ClayLayout.Col size={4}>
				<ClayForm.Group>
					<label htmlFor={confirmationMessageTypeFormElementId}>
						{Liferay.Language.get('message-type')}
					</label>

					<ClaySelectWithOption
						defaultValue="info"
						id={confirmationMessageTypeFormElementId}
						options={MESSAGE_TYPES}
					/>
				</ClayForm.Group>
			</ClayLayout.Col>
		</ClayLayout.Row>
	);

	if (loading) {
		return <ClayLoadingIndicator />;
	}

	return (
		<ClayLayout.ContainerFluid>
			<ClayBreadcrumb
				className="my-2"
				items={[
					{
						active: activeSection === SECTIONS.ACTIONS,
						label: Liferay.Language.get('actions'),
						onClick: () => setActiveSection(SECTIONS.ACTIONS),
					},
					{
						active: activeSection === SECTIONS.NEW_ITEM_ACTION,
						label: Liferay.Language.get('new-item-action'),
						onClick: () =>
							setActiveSection(SECTIONS.NEW_ITEM_ACTION),
					},
				]}
			/>

			<ClayLayout.ContainerFluid className="bg-white mb-4 p-0 rounded-sm">
				{activeSection === SECTIONS.ACTIONS && (
					<>
						<h2 className="mb-0 p-4">
							{Liferay.Language.get('actions')}
						</h2>

						<ClayTabs
							active={activeTab}
							onActiveChange={setActiveTab}
						>
							<ClayTabs.Item>
								{Liferay.Language.get('item-actions')}
							</ClayTabs.Item>

							<ClayTabs.Item>
								{Liferay.Language.get('creation-actions')}
							</ClayTabs.Item>
						</ClayTabs>

						<ClayTabs.Content active={activeTab} fade>
							<ClayTabs.TabPane
								aria-labelledby={Liferay.Language.get(
									'actions'
								)}
							>
								<OrderableTable
									creationMenuItems={[
										{
											label: Liferay.Language.get(
												'add-action'
											),
											onClick: () =>
												setActiveSection(
													SECTIONS.NEW_ITEM_ACTION
												),
										},
									]}
									fields={[
										{
											label: Liferay.Language.get('icon'),
											name: 'icon',
										},
										{
											label: Liferay.Language.get(
												'label'
											),
											name: 'label',
										},
										{
											label: Liferay.Language.get('type'),
											name: 'type',
										},
									]}
									items={fdsActions}
									noItemsButtonLabel={Liferay.Language.get(
										'create-item-action'
									)}
									noItemsDescription={Liferay.Language.get(
										'start-creating-an-action-to-interact-with-your-data'
									)}
									noItemsTitle={Liferay.Language.get(
										'no-actions-were-created'
									)}
									onCancelButtonClick={noop}
									onOrderChange={({
										orderedItems,
									}: {
										orderedItems: IFDSAction[];
									}) => {
										setNewActionsOrder(
											orderedItems
												.map((filter) => filter.id)
												.join(',')
										);
									}}
									onSaveButtonClick={updateFDSActionsOrder}
								/>
							</ClayTabs.TabPane>

							<ClayTabs.TabPane
								aria-labelledby={Liferay.Language.get(
									'new-item-action'
								)}
							>
								2. Proin efficitur imperdiet dolor, a iaculis
								orci lacinia eu.
							</ClayTabs.TabPane>
						</ClayTabs.Content>
					</>
				)}

				{activeSection === SECTIONS.NEW_ITEM_ACTION && (
					<>
						<h2 className="mb-0 p-4">
							{Liferay.Language.get('new-item-action')}
						</h2>

						<ClayPanel
							collapsable
							defaultExpanded
							displayTitle={Liferay.Language.get(
								'display-options'
							)}
						>
							<ClayPanel.Body>
								<ClayLayout.Row>
									<ClayLayout.Col size={8}>
										{Liferay.FeatureFlags['LPS-172017'] ? (
											<InputLocalized
												label={Liferay.Language.get(
													'label'
												)}
												onChange={setLabelTranslations}
												placeholder={Liferay.Language.get(
													'action-name'
												)}
												required
												translations={labelTranslations}
											/>
										) : (
											<ClayForm.Group>
												<label
													htmlFor={labelFormElementId}
												>
													{Liferay.Language.get(
														'label'
													)}
												</label>

												<ClayInput
													id={labelFormElementId}
													onChange={(event) =>
														setLabel(
															event.target.value
														)
													}
													type="text"
													value={label}
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
											symbol={iconSymbol}
										/>

										<ClayForm.Group>
											<label htmlFor={iconFormElementId}>
												{Liferay.Language.get('icon')}
											</label>

											<ClaySelectWithOption
												id={iconFormElementId}
												onChange={(event) =>
													setIconSymbol(
														event.target.value
													)
												}
												options={availableIconSymbols}
												value={iconSymbol}
											/>
										</ClayForm.Group>
									</ClayLayout.Col>
								</ClayLayout.Row>
							</ClayPanel.Body>
						</ClayPanel>

						<ClayPanel
							collapsable
							defaultExpanded
							displayTitle={Liferay.Language.get(
								'action-behavior'
							)}
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
												id={typeFormElementId}
												onChange={(event) =>
													setType(event.target.value)
												}
												options={TYPES}
												placeholder={Liferay.Language.get(
													'please-select-an-option'
												)}
												value={type}
											/>
										</ClayForm.Group>
									</ClayLayout.Col>
								</ClayLayout.Row>

								<ClayLayout.Row justify="start">
									<ClayLayout.Col lg>
										<ClayForm.Group>
											<label htmlFor={urlFormElementId}>
												{Liferay.Language.get('url')}

												<RequiredMark />
											</label>

											<ClayInput
												component="textarea"
												id={urlFormElementId}
												onChange={(event) =>
													setURL(event.target.value)
												}
												placeholder={Liferay.Language.get(
													'add-a-url-here'
												)}
												value={url}
											/>
										</ClayForm.Group>

										<ConfirmationMessageRow />
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
								onClick={() =>
									setActiveSection(SECTIONS.ACTIONS)
								}
							>
								{Liferay.Language.get('cancel')}
							</ClayButton>
						</ClayButton.Group>
					</>
				)}
			</ClayLayout.ContainerFluid>
		</ClayLayout.ContainerFluid>
	);
};

export default Actions;
