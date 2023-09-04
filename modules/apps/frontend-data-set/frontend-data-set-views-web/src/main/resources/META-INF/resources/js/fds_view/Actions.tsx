/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayBreadcrumb from '@clayui/breadcrumb';
import ClayButton from '@clayui/button';
import ClayForm, {ClayInput, ClaySelectWithOption} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import ClayPanel from '@clayui/panel';
import ClayTabs from '@clayui/tabs';
import {InputLocalized} from 'frontend-js-components-web';
import {fetch} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import OrderableTable from '../components/OrderableTable';
import RequiredMark from '../components/RequiredMark';

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

const noop = () => {};

const Actions = ({spritemap}: {spritemap: string}) => {
	const [activeSection, setActiveSection] = useState(SECTIONS.ACTIONS);
	const [activeTab, setActiveTab] = useState(0);
	const [availableIconSymbols, setAvailableIconSymbols] = useState<
		Array<{label: string; value: string}>
	>([]);
	const [
		confirmationMessageTranslations,
		setConfirmationMessageTranslations,
	] = useState({});
	const [iconSymbol, setIconSymbol] = useState('bolt');
	const [labelTranslations, setLabelTranslations] = useState({});

	useEffect(() => {
		const getIcons = async () => {
			const response = await fetch(spritemap);

			const responseText = await response.text();

			if (responseText.length) {
				const XMLString = await new window.DOMParser().parseFromString(
					responseText,
					'text/xml'
				);

				const availableIconSymbolElements = XMLString.querySelectorAll(
					'symbol'
				);

				const iconSymbols = Array.from(
					availableIconSymbolElements!
				).map((element) => ({
					label: element.id,
					value: element.id,
				}));

				setAvailableIconSymbols(iconSymbols);
			}
		};

		getIcons();
	}, [spritemap]);

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
									className="mt-0 p-1"
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
									items={[]}
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
									onOrderChange={noop}
									onSaveButtonClick={noop}
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
											<label htmlFor="iconInput">
												{Liferay.Language.get('icon')}
											</label>

											<ClaySelectWithOption
												defaultValue="bolt"
												id="iconInput"
												onChange={(event) =>
													setIconSymbol(
														event.target.value
													)
												}
												options={availableIconSymbols}
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
											<label htmlFor="actionTypeSelect">
												{Liferay.Language.get('type')}

												<RequiredMark />
											</label>

											<ClaySelectWithOption
												defaultValue="link"
												id="actionTypeSelect"
												options={TYPES}
												placeholder={Liferay.Language.get(
													'please-select-an-option'
												)}
											/>
										</ClayForm.Group>
									</ClayLayout.Col>
								</ClayLayout.Row>

								<ClayLayout.Row justify="start">
									<ClayLayout.Col lg>
										<ClayForm.Group>
											<label htmlFor="urlInput">
												{Liferay.Language.get('url')}

												<RequiredMark />
											</label>

											<ClayInput
												component="textarea"
												id="urlInput"
												placeholder={Liferay.Language.get(
													'add-a-url-here'
												)}
											/>
										</ClayForm.Group>

										<ClayForm.Group>
											<label htmlFor="headlessActionKeyInput">
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
												id="headlessActionKeyInput"
												placeholder={Liferay.Language.get(
													'add-a-value-here'
												)}
											/>
										</ClayForm.Group>

										<ClayLayout.Row>
											<ClayLayout.Col size={8}>
												<ClayForm.Group>
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
														translations={
															confirmationMessageTranslations
														}
													/>
												</ClayForm.Group>
											</ClayLayout.Col>

											<ClayLayout.Col size={4}>
												<ClayForm.Group>
													<label htmlFor="messageTypeInput">
														{Liferay.Language.get(
															'message-type'
														)}
													</label>

													<ClaySelectWithOption
														defaultValue="info"
														id="messageTypeInput"
														options={MESSAGE_TYPES}
													/>
												</ClayForm.Group>
											</ClayLayout.Col>
										</ClayLayout.Row>
									</ClayLayout.Col>
								</ClayLayout.Row>
							</ClayPanel.Body>
						</ClayPanel>

						<ClayButton.Group className="pb-4 px-4" spaced>
							<ClayButton onClick={noop}>
								{Liferay.Language.get('save')}
							</ClayButton>

							<ClayButton displayType="secondary" onClick={noop}>
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
