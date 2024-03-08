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
import {navigate} from 'frontend-js-web';
import React, {useState} from 'react';

import {IFDSViewSectionProps} from '../FDSView';

const NOT_CONFIGURED_VIEW_MODE = {
	id: 0,
	label: Liferay.Language.get('configure-new-layout'),
	name: 'not-configured',
	thumbnail: 'plus',
};

const Settings = ({fdsViewsURL, spritemap}: IFDSViewSectionProps) => {
	const [enableCustomView, setEnableCustomView] = useState(false);
	const [viewModes, setViewModes] = useState([]);
	const handleViewModeChange = (option: any) => {
		return option;
	};
	const updateFDSViewSettings = async () => {
		setViewModes([]);
	};

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
					<ClayLayout.Col size={8}>
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
								'default-visualization-mode-explanation'
							)}
						</div>
					</ClayLayout.Col>

					<ClayLayout.Col className="" size={4}>
						<Picker
							aria-labelledby="view-mode"
							id="view-mode-picker"
							items={viewModes}
							onSelectionChange={handleViewModeChange}
							placeholder={Liferay.Language.get('not-configured')}
						>
							{viewModes.length ? (
								({label, name, thumbnail}) => (
									<Option key={name} textValue={name}>
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
										key={NOT_CONFIGURED_VIEW_MODE.name}
										textValue={
											NOT_CONFIGURED_VIEW_MODE.name
										}
									>
										<ClayLayout.Row className="mb-2 mt-2">
											<ClayLayout.Col
												className="align-self-center"
												size={1}
											>
												<ClayIcon
													symbol={
														NOT_CONFIGURED_VIEW_MODE.thumbnail
													}
												/>
											</ClayLayout.Col>

											<ClayLayout.Col size={10}>
												<Text size={4}>
													{
														NOT_CONFIGURED_VIEW_MODE.label
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

			<ClayLayout.SheetSection className="mt-4">
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
