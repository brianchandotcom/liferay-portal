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

import EditAppContext from 'app-builder-web/js/pages/apps/edit/EditAppContext.es';
import React, {useContext, useState} from 'react';

import SelectDropdown from '../../../../components/select-dropdown/SelectDropdown.es';
import MissingRequiredFieldsPopover from '../MissingRequiredFieldsPopover.es';
import {OpenButton} from './DataAndViewsTab.es';

const Item = ({
	id,
	missingRequiredFields: {customField, nativeField} = {},
	name,
}) => {
	const {
		config: {dataObject},
		openFormViewModal,
		updateFormView,
	} = useContext(EditAppContext);
	const [showPopover, setShowPopover] = useState(false);

	const onClickPopover = () => {
		setShowPopover(false);

		openFormViewModal({
			dataDefinitionId: dataObject.id,
			dataLayoutId: id,
			defaultLanguageId: dataObject.defaultLanguageId,
			selectFormView: updateFormView,
		});
	};

	return (
		<>
			<span
				className="float-left text-left text-truncate w50"
				title={name}
			>
				{name}
			</span>

			{(customField || nativeField) && (
				<MissingRequiredFieldsPopover
					dataObjectName={dataObject.name}
					nativeField={nativeField}
					onClick={onClickPopover}
					setShowPopover={setShowPopover}
					showPopover={showPopover}
					triggerClassName="dropdown-button-asset help-cursor"
				/>
			)}
		</>
	);
};

function SelectFormView({openButtonProps, ...props}) {
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

	return (
		<div className="d-flex">
			<SelectDropdown {...props} />

			<OpenButton {...openButtonProps} />
		</div>
	);
}

SelectFormView.Item = Item;

export default SelectFormView;
