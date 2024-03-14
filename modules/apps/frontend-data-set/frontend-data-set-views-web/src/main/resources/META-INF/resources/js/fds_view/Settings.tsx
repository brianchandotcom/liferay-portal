/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {Option, Picker, Text} from '@clayui/core';
import DropDown from '@clayui/drop-down';
import {ClayToggle} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import {ClayTooltipProvider} from '@clayui/tooltip';
import {fetch, navigate} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import {IFDSViewSectionProps} from '../FDSView';
import {API_URL, OBJECT_RELATIONSHIP} from '../utils/constants';
import openDefaultFailureToast from '../utils/openDefaultFailureToast';
import openDefaultSuccessToast from '../utils/openDefaultSuccessToast';

interface IVisualizationMode {
	label: string;
	name: string;
	thumbnail: string;
	url: string;
}

const NOT_CONFIGURED_VISUALIZATION_MODE: Omit<IVisualizationMode, 'url'> = {
	label: Liferay.Language.get('go-to-visualization-modes'),
	name: Liferay.Language.get('not-configured'),
	thumbnail: 'plus',
};

const Settings = ({
	fdsView,
	fdsViewsURL,
	onActiveSectionChange,
	onFDSViewUpdate,
	spritemap,
}: IFDSViewSectionProps) => {
	const FDS_VISUALIZATION_MODES: Array<IVisualizationMode> = [
		{
			label: Liferay.Language.get('cards'),
			name: 'cards',
			thumbnail: 'cards2',
			url: `${API_URL.FDS_CARDS_SECTIONS}?filter=(${OBJECT_RELATIONSHIP.FDS_VIEW_FDS_CARDS_SECTION_ERC} eq '${fdsView.externalReferenceCode}')`,
		},
		{
			label: Liferay.Language.get('list'),
			name: 'list',
			thumbnail: 'list',
			url: `${API_URL.FDS_LIST_SECTIONS}?filter=(${OBJECT_RELATIONSHIP.FDS_VIEW_FDS_LIST_SECTION_ERC} eq '${fdsView.externalReferenceCode}')`,
		},
		{
			label: Liferay.Language.get('table'),
			name: 'table',
			thumbnail: 'table',
			url: `${API_URL.FDS_FIELDS}?filter=(${OBJECT_RELATIONSHIP.FDS_VIEW_FDS_FIELD_ID} eq '${fdsView.id}')&nestedFields=${OBJECT_RELATIONSHIP.FDS_VIEW_FDS_FIELD}`,
		},
	];
	const [defaultVisualizationMode, setDefaultVisualizationMode] = useState(
		NOT_CONFIGURED_VISUALIZATION_MODE.name
	);
	const [enableCustomView, setEnableCustomView] = useState(false);
	const [visualizationModes, setVisualizationModes] = useState<
		Array<IVisualizationMode>
	>([]);

	const getActiveVisualizationModes = async () => {
		const visualizationConfigRequests = FDS_VISUALIZATION_MODES.map(
			(viewMode) => fetch(viewMode.url)
		);

		Promise.all(visualizationConfigRequests)
			.then((visualizationConfigResults) =>
				Promise.all(
					visualizationConfigResults.map((result) => result.json())
				)
			)
			.then(
				([cards, list, table]) => {
					const activeViews: Array<IVisualizationMode> = [];

					FDS_VISUALIZATION_MODES.forEach((view) => {
						if (
							view.name === 'cards' &&
							cards.items &&
							cards.items.length
						) {
							activeViews.push(view);
						}
						if (
							view.name === 'list' &&
							list.items &&
							list.items.length
						) {
							activeViews.push(view);
						}
						if (
							view.name === 'table' &&
							table.items &&
							table.items.length
						) {
							activeViews.push(view);
						}
					});

					setVisualizationModes(activeViews);

					setDefaultVisualizationMode(() => {
						if (
							activeViews.find(
								(view: IVisualizationMode) =>
									view.name ===
									fdsView.defaultVisualizationMode
							)
						) {
							return fdsView.defaultVisualizationMode;
						}
						else {
							return activeViews.length
								? activeViews[0].name
								: NOT_CONFIGURED_VISUALIZATION_MODE.name;
						}
					});
				},
				() => {
					setVisualizationModes([]);

					setDefaultVisualizationMode(
						NOT_CONFIGURED_VISUALIZATION_MODE.name
					);
				}
			);
	};

	const updateFDSViewSettings = async () => {
		const body = {
			defaultVisualizationMode,
		};

		const response = await fetch(
			`${API_URL.FDS_VIEWS}/by-external-reference-code/${fdsView.externalReferenceCode}`,
			{
				body: JSON.stringify(body),
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

		if (responseJSON?.id) {
			openDefaultSuccessToast();

			onFDSViewUpdate(responseJSON);
		}
		else {
			openDefaultFailureToast();
		}
	};

	useEffect(() => {
		getActiveVisualizationModes();

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	return (
		<ClayLayout.Sheet className="mt-3" size="lg">
			<ClayLayout.SheetHeader className="mb-4">
				<h2 className="sheet-title">
					{Liferay.Language.get('settings')}
				</h2>
			</ClayLayout.SheetHeader>

			<ClayLayout.SheetSection>
				<h3 className="sheet-subtitle">
					{Liferay.Language.get('fragment-defaults')}
				</h3>

				<ClayLayout.Row className="align-items-center justify-content-between">
					<ClayLayout.Col size={9}>
						<div>
							<label htmlFor="view-mode-picker" id="view-mode">
								{Liferay.Language.get(
									'default-visualization-mode'
								)}
							</label>

							<ClayTooltipProvider>
								<span
									className="ml-1 text-secondary"
									data-tooltip-align="top"
									title={Liferay.Language.get(
										'default-visualization-mode-tooltip'
									)}
								>
									<ClayIcon
										spritemap={spritemap}
										symbol="question-circle-full"
									/>
								</span>
							</ClayTooltipProvider>
						</div>

						<div>
							{Liferay.Language.get(
								'default-visualization-mode-help'
							)}
						</div>
					</ClayLayout.Col>

					<ClayLayout.Col size={3}>
						<Picker
							aria-labelledby="view-mode"
							id="view-mode-picker"
							items={visualizationModes}
							onSelectionChange={(option: React.Key) => {
								if (
									option ===
									NOT_CONFIGURED_VISUALIZATION_MODE.name
								) {
									onActiveSectionChange(1);
								}
								else {
									setDefaultVisualizationMode(
										option as string
									);
								}
							}}
							placeholder={NOT_CONFIGURED_VISUALIZATION_MODE.name}
							selectedKey={defaultVisualizationMode}
						>
							{visualizationModes.length ? (
								({label, name, thumbnail}) => (
									<Option key={name} textValue={label}>
										<ClayIcon
											className="mr-3"
											symbol={thumbnail}
										/>

										{label}
									</Option>
								)
							) : (
								<DropDown.Group
									header={Liferay.Language.get(
										'not-configured'
									)}
								>
									<Option
										key={
											NOT_CONFIGURED_VISUALIZATION_MODE.name
										}
										textValue={
											NOT_CONFIGURED_VISUALIZATION_MODE.name
										}
									>
										<ClayLayout.Row>
											<ClayLayout.Col>
												<Text size={3}>
													{
														NOT_CONFIGURED_VISUALIZATION_MODE.label
													}
												</Text>
											</ClayLayout.Col>
										</ClayLayout.Row>
									</Option>
								</DropDown.Group>
							)}
						</Picker>
					</ClayLayout.Col>
				</ClayLayout.Row>
			</ClayLayout.SheetSection>

			<ClayLayout.SheetSection className="invisible mt-4">
				<h3 className="sheet-subtitle">
					{Liferay.Language.get('user-customization')}
				</h3>

				<ClayLayout.Row className="align-items-center justify-content-between">
					<ClayLayout.Col size={10}>
						<div>
							<label
								htmlFor="custom-view-toggle"
								id="custom-views"
							>
								{Liferay.Language.get('enable-custom-views')}
							</label>
						</div>

						<div>
							{Liferay.Language.get(
								'enable-custom-views-explanation'
							)}
						</div>
					</ClayLayout.Col>

					<ClayLayout.Col
						className="d-flex justify-content-end"
						size={2}
					>
						<ClayToggle
							aria-labelledby="custom-view-toggle"
							id="custom-view-toggle"
							onKeyDown={(_event) => {
								setEnableCustomView(!enableCustomView);
							}}
							onToggle={setEnableCustomView}
							toggled={enableCustomView}
						/>
					</ClayLayout.Col>
				</ClayLayout.Row>
			</ClayLayout.SheetSection>

			<ClayLayout.SheetFooter>
				<ClayButton.Group spaced>
					<ClayButton onClick={updateFDSViewSettings}>
						{Liferay.Language.get('save')}
					</ClayButton>

					<ClayButton
						displayType="secondary"
						onClick={() => navigate(fdsViewsURL)}
					>
						{Liferay.Language.get('cancel')}
					</ClayButton>
				</ClayButton.Group>
			</ClayLayout.SheetFooter>
		</ClayLayout.Sheet>
	);
};

export default Settings;
