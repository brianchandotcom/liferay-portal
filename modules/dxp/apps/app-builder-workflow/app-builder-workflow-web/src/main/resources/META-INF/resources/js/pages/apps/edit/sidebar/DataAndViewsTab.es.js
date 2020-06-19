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

import ClayIcon from '@clayui/icon';
import {ClayTooltipProvider} from '@clayui/tooltip';
import SelectObjects from 'app-builder-web/js/pages/apps/SelectObjectsDropDown.es';
import EditAppContext, {
	UPDATE_DATA_DEFINITION_ID,
	UPDATE_DATA_LAYOUT_ID,
	UPDATE_DATA_LIST_VIEW_ID,
} from 'app-builder-web/js/pages/apps/edit/EditAppContext.es';
import React, {useContext} from 'react';

import {
	SelectFormView,
	SelectTableView,
} from '../../../../components/select-dropdown/SelectDropdown.es';
import {
	UPDATE_DATA_OBJECT,
	UPDATE_FORM_VIEW,
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

export default () => {
	const {
		config: {dataObject, formView, tableView},
		dispatch,
		dispatchConfig,
		state: {app},
	} = useContext(EditAppContext);

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
						defaultValue={app.dataLayoutId}
						objectId={dataObject.id}
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
						defaultValue={app.dataListViewId}
						objectId={dataObject.id}
						onSelect={updateTableView}
						selectedValue={tableView.name}
					/>
				</div>
			) : (
				<NoObjectEmptyState />
			)}
		</>
	);
};
