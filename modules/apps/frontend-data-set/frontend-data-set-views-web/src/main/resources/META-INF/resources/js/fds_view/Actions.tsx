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
import React, {useState} from 'react';

import OrderableTable from '../components/OrderableTable';

const types = [
	{
		label: 'Async',
		value: 'async',
	},
	{
		label: 'Headless',
		value: 'headless',
	},
	{
		label: 'Link',
		value: 'link',
	},
	{
		label: 'Modal',
		value: 'modal',
	},
	{
		label: 'Side Panel',
		value: 'side-panel',
	},
];

const messageTypes = [
	{
		label: 'Info',
		value: 'info',
	},
	{
		label: 'Secondary',
		value: 'secondary',
	},
	{
		label: 'Success',
		value: 'success',
	},
	{
		label: 'Danger',
		value: 'danger',
	},
	{
		label: 'Warning',
		value: 'warning',
	},
];

const noop = () => {};

const Actions = () => {
	const [activeBreadcrumb, setActiveBreadcrumb] = useState(0);
	const [activeTab, setActiveTab] = useState(0);

	return (
		<ClayLayout.ContainerFluid>
			<ClayBreadcrumb
				items={[
					{
						active: activeBreadcrumb === 0,
						href: '#1',
						label: Liferay.Language.get('actions'),
					},
					{
						active: activeBreadcrumb === 1,
						href: '#2',
						label: Liferay.Language.get('new-item-action'),
					},
				]}
			/>

			<ClayLayout.ContainerFluid className="bg-white mb-4 p-0 rounded-sm">
				{activeBreadcrumb === 0 && (
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

							<ClayTabs.Item>
								{Liferay.Language.get('bulk-actions')}
							</ClayTabs.Item>
						</ClayTabs>

						<ClayTabs.Content active={activeTab} fade>
							<ClayTabs.TabPane aria-labelledby="tab-1">
								<OrderableTable
									className="mt-0 p-1"
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
										'start-creating-one-action-to-interact-with-your-data'
									)}
									noItemsTitle={Liferay.Language.get(
										'no-actions-were-created'
									)}
									onCancelButtonClick={noop}
									onCreationButtonClick={() =>
										setActiveBreadcrumb(1)
									}
									onOrderChange={noop}
									onSaveButtonClick={noop}
								/>
							</ClayTabs.TabPane>

							<ClayTabs.TabPane aria-labelledby="tab-2">
								2. Proin efficitur imperdiet dolor, a iaculis
								orci lacinia eu.
							</ClayTabs.TabPane>

							<ClayTabs.TabPane aria-labelledby="tab-3">
								3. Proin efficitur imperdiet dolor, a iaculis
								orci lacinia eu.
							</ClayTabs.TabPane>
						</ClayTabs.Content>
					</>
				)}

				{activeBreadcrumb === 1 && (
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
										<ClayForm.Group>
											<label htmlFor="actionLabelInput">
												{Liferay.Language.get('label')}
											</label>

											<ClayInput
												id="actionLabelInput"
												placeholder={Liferay.Language.get(
													'action-name'
												)}
											/>
										</ClayForm.Group>
									</ClayLayout.Col>

									<ClayLayout.Col size={4}>
										<ClayForm.Group>
											<label htmlFor="iconInput">
												{Liferay.Language.get('icon')}
											</label>

											<ClayInput
												id="iconInput"
												placeholder={Liferay.Language.get(
													'select-an-option'
												)}
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
								'action-behaviour'
							)}
						>
							<ClayPanel.Body>
								<ClayLayout.Row justify="start">
									<ClayLayout.Col size={4}>
										<ClayForm.Group>
											<label htmlFor="actionTypeSelect">
												{Liferay.Language.get('type')}
											</label>

											<ClaySelectWithOption
												defaultValue="link"
												id="actionTypeSelect"
												options={types}
												placeholder={Liferay.Language.get(
													'select-an-option'
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
														'this-key-allows-the-action-to-be-performed-when-the-permission-is-associated-with-it'
													)}
												>
													<ClayIcon symbol="question-circle-full" />
												</span>
											</label>

											<ClayInput
												id="headlessActionKeyInput"
												placeholder={Liferay.Language.get(
													'add-value-here'
												)}
											/>
										</ClayForm.Group>

										<ClayLayout.Row>
											<ClayLayout.Col size={8}>
												<ClayForm.Group>
													<label htmlFor="confirmationMessageInput">
														{Liferay.Language.get(
															'confirmation-message'
														)}

														<span
															className="label-icon lfr-portal-tooltip ml-2"
															title={Liferay.Language.get(
																'the-user-will-see-this-message-before-performing-the-action'
															)}
														>
															<ClayIcon symbol="question-circle-full" />
														</span>
													</label>

													<ClayInput
														id="confirmationMessageInput"
														placeholder={Liferay.Language.get(
															'add-a-message-here'
														)}
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
														options={messageTypes}
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
