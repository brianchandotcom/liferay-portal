/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayButton from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import EditAppContext from 'app-builder-web/js/pages/apps/edit/EditAppContext.es';
import {Sidebar} from 'data-engine-taglib';
import React, {useContext, useEffect, useState} from 'react';

import ButtonInfo from '../../../../components/button-info/ButtonInfo.es';
import {UPDATE_STEP} from '../configReducer.es';
import DataAndViewsTab from './DataAndViewsTab.es';

export default function EditAppSidebar() {
	const {
		config: {currentStep, dataObject, formView, stepIndex, tableView},
		dispatchConfig,
	} = useContext(EditAppContext);

	const [currentTab, setCurrentTab] = useState();

	const tabs = [
		{
			content: DataAndViewsTab,
			infoItems: [
				{
					...dataObject,
					label: Liferay.Language.get('data-object'),
				},
				{
					...formView,
					label: Liferay.Language.get('form-view'),
				},
				{
					...tableView,
					label: Liferay.Language.get('table-view'),
				},
			],
			show: stepIndex === 0,
			title: Liferay.Language.get('data-and-views'),
		},
	];

	const onChangeStepName = ({target}) => {
		dispatchConfig({
			step: {...currentStep, name: target.value},
			stepIndex,
			type: UPDATE_STEP,
		});
	};

	useEffect(() => {
		setCurrentTab(null);
	}, [currentStep]);

	return (
		<Sidebar className="app-builder-workflow-app__sidebar">
			<Sidebar.Header>
				{!currentTab ? (
					<h3 className="title">
						{Liferay.Language.get('step-configuration')}
					</h3>
				) : (
					<div className="tab-title">
						<ClayButton
							data-testid="back-button"
							displayType="secondary"
							onClick={() => setCurrentTab(null)}
							small
						>
							<span className="icon-monospaced">
								<ClayIcon symbol="angle-left" />
							</span>
						</ClayButton>

						<h3>{currentTab.title}</h3>
					</div>
				)}
			</Sidebar.Header>

			<Sidebar.Body>
				{!currentTab ? (
					<>
						<ClayForm.Group className="form-group-outlined">
							<label>
								{Liferay.Language.get('step-name')}

								<span className="reference-mark">
									<ClayIcon symbol="asterisk" />
								</span>
							</label>

							<ClayInput
								onChange={onChangeStepName}
								type="text"
								value={currentStep.name}
							/>
						</ClayForm.Group>

						{tabs.map(
							({infoItems, show, title}, index) =>
								show && (
									<ClayButton
										className="tab-button"
										displayType="secondary"
										key={index}
										onClick={() =>
											setCurrentTab(tabs[index])
										}
									>
										<div className="text-dark">
											<span>{title}</span>

											<ButtonInfo items={infoItems} />
										</div>

										<ClayIcon
											className="dropdown-button-asset"
											symbol="angle-right"
										/>
									</ClayButton>
								)
						)}
					</>
				) : (
					currentTab.content()
				)}
			</Sidebar.Body>
		</Sidebar>
	);
}
