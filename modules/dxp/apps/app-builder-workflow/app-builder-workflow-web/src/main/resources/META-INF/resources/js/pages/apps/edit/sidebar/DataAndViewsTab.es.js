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

import ClayAlert from '@clayui/alert';
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
import {sub} from 'app-builder-web/js/utils/lang.es';
import {concatValues} from 'app-builder-web/js/utils/utils.es';
import classNames from 'classnames';
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
		state: {app},
	} = useContext(EditAppContext);

	const {
		appWorkflowDataLayoutLinks: stepFormViews = [],
		errors: {
			formViews: {duplicatedFields = [], errorIndexes = []} = {},
		} = {},
	} = currentStep;

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

	const updateDataObject = (dataObject) => {
		dispatchConfig({
			dataObject,
			type: UPDATE_DATA_OBJECT,
		});

		dispatch({
			...dataObject,
			type: UPDATE_DATA_DEFINITION_ID,
		});
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

	const duplicatedFieldsMessage =
		duplicatedFields.length === 1
			? Liferay.Language.get(
					'the-field-x-is-present-in-multiple-form-views'
			  )
			: Liferay.Language.get(
					'the-fields-x-are-present-in-multiple-form-views'
			  );

	const fields = duplicatedFields.map((field) => `"${field}"`);

	const someFields = fields.slice(0, 5);
	someFields.push('others*');

	const duplicatedFieldsValues =
		duplicatedFields.length <= 5
			? concatValues(fields)
			: concatValues(someFields);

	return (
		<>
			{stepIndex > 0 ? (
				<>
					{duplicatedFields.length > 0 && (
						<ClayAlert
							className="fields-alert-container mt-2"
							displayType="danger"
							title={`${Liferay.Language.get('error')}:`}
						>
							{`${sub(duplicatedFieldsMessage, [
								duplicatedFieldsValues,
							])} `}

							{duplicatedFields.length >= 5 && (
								<ClayTooltipProvider delay="0">
									<a
										className="text-primary"
										data-tooltip-align="bottom"
										title={duplicatedFields
											.slice(5, duplicatedFields.length)
											.join('\n')}
									>
										{Liferay.Language.get(
											'see-other-fields'
										)}
									</a>
								</ClayTooltipProvider>
							)}
						</ClayAlert>
					)}

					{stepFormViews.map(
						({dataLayoutId, name, readOnly}, index) => (
							<div
								className={classNames(
									'step-form-view',
									errorIndexes.includes(index) &&
										'border-error'
								)}
								key={index}
							>
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
						className="w-100"
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
							defaultValue={app.dataDefinitionId}
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
