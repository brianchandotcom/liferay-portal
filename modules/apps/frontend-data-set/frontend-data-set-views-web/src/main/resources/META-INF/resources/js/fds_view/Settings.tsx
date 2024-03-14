/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {Option, Picker, Text} from '@clayui/core';
import DropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import {ClayTooltipProvider} from '@clayui/tooltip';
import {fetch, navigate} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import {IFDSViewSectionProps} from '../FDSView';
import {
	API_URL,
	DEFAULT_VISUALIZATION_MODES,
	OBJECT_RELATIONSHIP,
} from '../utils/constants';
import openDefaultFailureToast from '../utils/openDefaultFailureToast';
import openDefaultSuccessToast from '../utils/openDefaultSuccessToast';
import {TVisualizationMode} from '../utils/types';

const NOT_CONFIGURED_VISUALIZATION_MODE = {
	label: Liferay.Language.get('go-to-visualization-modes'),
	thumbnail: 'plus',
	type: Liferay.Language.get('not-configured'),
};

const Settings = ({
	fdsView,
	fdsViewsURL,
	onActiveSectionChange,
	onFDSViewUpdate,
	spritemap,
}: IFDSViewSectionProps) => {
	const [defaultVisualizationMode, setDefaultVisualizationMode] = useState(
		NOT_CONFIGURED_VISUALIZATION_MODE.type
	);
	const [visualizationModes, setVisualizationModes] = useState<
		Array<TVisualizationMode>
	>([]);

	const getActiveVisualizationModes = async () => {
		const fields = [
			OBJECT_RELATIONSHIP.FDS_VIEW_FDS_CARDS_SECTION,
			OBJECT_RELATIONSHIP.FDS_VIEW_FDS_LIST_SECTION,
			OBJECT_RELATIONSHIP.FDS_VIEW_FDS_FIELD,
		].join(',');

		const response = await fetch(
			`${API_URL.FDS_VIEWS}/by-external-reference-code/${fdsView.externalReferenceCode}?fields=${fields}&nestedFields=${fields}`
		);

		if (!response.ok) {
			openDefaultFailureToast();

			setVisualizationModes([]);

			return;
		}

		const responseJSON = await response.json();

		const {
			fdsViewFDSCardsSectionRelationship: cards,
			fdsViewFDSFieldRelationship: table,
			fdsViewFDSListSectionRelationship: list,
		} = responseJSON;

		const activeViews: Array<TVisualizationMode> = [];

		(DEFAULT_VISUALIZATION_MODES as Array<TVisualizationMode>).forEach(
			(view) => {
				if (view.type === 'cards' && cards && cards.length) {
					activeViews.push(view);
				}
				if (view.type === 'list' && list && list.length) {
					activeViews.push(view);
				}
				if (view.type === 'table' && table && table.length) {
					activeViews.push(view);
				}
			}
		);

		setVisualizationModes(activeViews);

		setDefaultVisualizationMode(() => {
			if (
				activeViews.find(
					(view: TVisualizationMode) =>
						view.type === fdsView.defaultVisualizationMode
				)
			) {
				return fdsView.defaultVisualizationMode;
			}
			else {
				return activeViews.length
					? activeViews[0].type
					: NOT_CONFIGURED_VISUALIZATION_MODE.type;
			}
		});
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
									NOT_CONFIGURED_VISUALIZATION_MODE.type
								) {
									onActiveSectionChange(1);
								}
								else {
									setDefaultVisualizationMode(
										option as string
									);
								}
							}}
							placeholder={NOT_CONFIGURED_VISUALIZATION_MODE.type}
							selectedKey={defaultVisualizationMode}
						>
							{visualizationModes.length ? (
								({label, thumbnail, type}) => (
									<Option key={type} textValue={label}>
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
											NOT_CONFIGURED_VISUALIZATION_MODE.type
										}
										textValue={
											NOT_CONFIGURED_VISUALIZATION_MODE.type
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
