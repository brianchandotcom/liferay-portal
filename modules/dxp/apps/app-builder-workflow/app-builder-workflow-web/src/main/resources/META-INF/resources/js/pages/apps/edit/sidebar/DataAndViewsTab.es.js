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

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import {ClayRadio, ClayRadioGroup} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {ClayTooltipProvider} from '@clayui/tooltip';
import SelectObjects from 'app-builder-web/js/pages/apps/SelectObjectsDropDown.es';
import EditAppContext, {
	UPDATE_DATA_DEFINITION_ID,
	UPDATE_DATA_LAYOUT_ID,
	UPDATE_DATA_LIST_VIEW_ID,
} from 'app-builder-web/js/pages/apps/edit/EditAppContext.es';
import React, {useContext} from 'react';

import SelectDropdown from '../../../../components/select-dropdown/SelectDropdown.es';
import {
	ADD_STEP_FORM_VIEW,
	REMOVE_STEP_FORM_VIEW,
	UPDATE_DATA_OBJECT,
	UPDATE_FORM_VIEW,
	UPDATE_STEP_FORM_VIEW,
	UPDATE_STEP_FORM_VIEW_READONLY,
	UPDATE_TABLE_VIEW,
} from '../configReducer.es';

const NoObjectEmptyState = () => (
	<div className="taglib-empty-result-message">
		<div className="text-center">
			<div className="taglib-empty-result-message-header" />

			<h3>{Liferay.Language.get('no-object-selected')}</h3>

			<p className="empty-message-color taglib-empty-result-message-description">
				{Liferay.Language.get(
					'select-a-data-object-to-start-gathering-business-data'
				)}
			</p>
		</div>
	</div>
);

const SelectFormView = (props) => {
	props = {
		...props,
		emptyResultMessage: Liferay.Language.get(
			'no-form-views-were-found-with-this-name-try-searching-again-with-a-different-name'
		),
		label: Liferay.Language.get('select-a-form-view'),
		stateProps: {
			emptyProps: {
				label: Liferay.Language.get('there-are-no-form-views-yet'),
			},
			loadingProps: {
				label: Liferay.Language.get('retrieving-all-form-views'),
			},
		},
	};

	return <SelectDropdown {...props} />;
};

const SelectTableView = (props) => {
	props = {
		...props,
		emptyResultMessage: Liferay.Language.get(
			'no-table-views-were-found-with-this-name-try-searching-again-with-a-different-name'
		),
		label: Liferay.Language.get('select-a-table-view'),
		stateProps: {
			emptyProps: {
				label: Liferay.Language.get('there-are-no-table-views-yet'),
			},
			loadingProps: {
				label: Liferay.Language.get('retrieving-all-table-views'),
			},
		},
	};

	return <SelectDropdown {...props} />;
};

export default function DataAndViewsTab() {
	const {
		config: {
			currentStep,
			dataObject,
			formView,
			listItems: {fetching, formViews, tableViews},
			stepIndex,
			tableView,
		},
		dispatch,
		dispatchConfig,
	} = useContext(EditAppContext);

	const {appWorkflowDataLayoutLinks: stepFormViews = []} = currentStep;

	const availableFormViews = formViews.map((form) => ({
		...form,
		disabled:
			stepFormViews.findIndex(
				({dataLayoutId}) => dataLayoutId === form.id
			) > -1,
	}));

	const addStepFormView = () => {
		dispatchConfig({
			type: ADD_STEP_FORM_VIEW,
		});
	};

	const removeStepFormView = (index) => {
		dispatchConfig({
			index,
			type: REMOVE_STEP_FORM_VIEW,
		});
	};

	const updateDataObject = (newDataObject) => {
		if (newDataObject.id !== dataObject.id) {
			dispatchConfig({
				dataObject: newDataObject,
				type: UPDATE_DATA_OBJECT,
			});

			dispatch({
				...newDataObject,
				type: UPDATE_DATA_DEFINITION_ID,
			});
		}
	};

	const updateFormView = (formView) => {
		dispatchConfig({
			formView,
			type: UPDATE_FORM_VIEW,
		});

		dispatch({
			...formView,
			type: UPDATE_DATA_LAYOUT_ID,
		});
	};

	const updateStepFormView = (formView, index) => {
		dispatchConfig({
			formView,
			index,
			type: UPDATE_STEP_FORM_VIEW,
		});
	};

	const updateStepFormViewReadOnly = (readOnly, index) => {
		dispatchConfig({
			index,
			readOnly,
			type: UPDATE_STEP_FORM_VIEW_READONLY,
		});
	};

	const updateTableView = (tableView) => {
		dispatchConfig({
			tableView,
			type: UPDATE_TABLE_VIEW,
		});

		dispatch({
			...tableView,
			type: UPDATE_DATA_LIST_VIEW_ID,
		});
	};

	return (
		<>
			{stepIndex > 0 ? (
				<>
					{stepFormViews.map(
						({dataLayoutId, name, readOnly}, index) => (
							<div className="step-form-view" key={index}>
								<label id="form-view-label">
									{Liferay.Language.get('form-view')}
								</label>

								<SelectFormView
									ariaLabelId="form-view-label"
									items={availableFormViews}
									onSelect={(formView) =>
										updateStepFormView(formView, index)
									}
									selectedValue={name}
								/>

								<div className="d-flex justify-content-between mt-2">
									<ClayRadioGroup
										className="pt-1"
										inline
										onSelectedValueChange={(readOnly) =>
											updateStepFormViewReadOnly(
												readOnly,
												index
											)
										}
										selectedValue={readOnly}
									>
										<ClayRadio
											disabled={!dataLayoutId}
											label={Liferay.Language.get(
												'editable'
											)}
											value={false}
										/>

										<ClayRadio
											disabled={!dataLayoutId}
											label={Liferay.Language.get(
												'read-only'
											)}
											value={true}
										/>
									</ClayRadioGroup>

									{stepFormViews.length > 1 && (
										<ClayTooltipProvider>
											<ClayButtonWithIcon
												borderless
												data-tooltip-align="bottom"
												data-tooltip-delay="0"
												displayType="secondary"
												onClick={() =>
													removeStepFormView(index)
												}
												small
												symbol="trash"
												title={Liferay.Language.get(
													'remove'
												)}
											/>
										</ClayTooltipProvider>
									)}
								</div>
							</div>
						)
					)}

					<ClayButton
						className="mt-3 w-100"
						displayType="secondary"
						onClick={addStepFormView}
					>
						<span className="text-secondary">
							{Liferay.Language.get('add-new-form-view')}
						</span>
					</ClayButton>
				</>
			) : (
				<>
					<div className="main-section">
						<label id="select-object-label">
							{Liferay.Language.get('main-data-object')}
						</label>

						<ClayTooltipProvider>
							<ClayIcon
								className="ml-2 text-muted tooltip-icon"
								data-tooltip-align="top"
								data-tooltip-delay="0"
								symbol="question-circle-full"
								title={Liferay.Language.get(
									'a-data-object-stores-your-business-data-and-is-composed-by-data-fields'
								)}
							/>
						</ClayTooltipProvider>

						<SelectObjects
							label={Liferay.Language.get('select-object')}
							onSelect={updateDataObject}
							selectedValue={dataObject}
						/>
					</div>

					{dataObject.name ? (
						<div className="py-3">
							<h5 className="text-secondary text-uppercase">
								{Liferay.Language.get('gather-data')}
							</h5>

							<label id="form-view-label">
								{Liferay.Language.get('form-view')}
							</label>

							<SelectFormView
								ariaLabelId="form-view-label"
								isLoading={fetching}
								items={formViews}
								onSelect={updateFormView}
								selectedValue={formView.name}
							/>

							<h5 className="mt-3 text-secondary text-uppercase">
								{Liferay.Language.get('display-data')}
							</h5>

							<label id="table-view-label">
								{Liferay.Language.get('table-view')}
							</label>

							<SelectTableView
								ariaLabelId="table-view-label"
								isLoading={fetching}
								items={tableViews}
								onSelect={updateTableView}
								selectedValue={tableView.name}
							/>
						</div>
					) : (
						<NoObjectEmptyState />
					)}
				</>
			)}
		</>
	);
}
