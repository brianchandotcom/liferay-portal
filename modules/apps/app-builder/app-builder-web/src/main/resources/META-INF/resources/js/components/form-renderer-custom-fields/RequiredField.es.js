/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayForm, {ClayRadio, ClayRadioGroup, ClayToggle} from '@clayui/form';
import ClayPopover from '@clayui/popover';
import {ClayTooltipProvider} from '@clayui/tooltip';
import {DataLayoutBuilderActions} from 'data-engine-taglib';
import React, {useContext, useEffect, useRef, useState} from 'react';

import isClickOutside from '../../utils/clickOutside.es';

const FORM_VIEW_LEVEL = 'form-view-level';
const OBJECT_VIEW_LEVEL = 'object-view-level';

const VIEW_LEVEL = {
	[FORM_VIEW_LEVEL]: {
		label: Liferay.Language.get('only-for-this-form'),
	},
	[OBJECT_VIEW_LEVEL]: {
		disabled: true,
		label: Liferay.Language.get('for-all-forms-of-this-object'),
	},
};

function getRequiredAtFormViewLevel(dataLayoutFields, fieldName) {
	return dataLayoutFields[fieldName]?.required ?? false;
}

export default ({AppContext, dataLayoutBuilder}) => {
	const popoverRef = useRef(null);
	const triggerRef = useRef(null);
	const [showPopover, setShowPopover] = useState(false);
	const [selectedViewLevel, setSelectedViewLevel] = useState(FORM_VIEW_LEVEL);
	const [
		{
			dataLayout: {dataLayoutFields},
			focusedField: {fieldName},
		},
		dispatch,
	] = useContext(AppContext);

	const requiredAtFormViewLevel = getRequiredAtFormViewLevel(
		dataLayoutFields,
		fieldName
	);

	function setRequireAtFormViewLevel(toggle) {

		// Mark as required within an edited field in the data engine

		dispatch({
			payload: {
				dataLayoutFields: {
					...dataLayoutFields,
					[fieldName]: {
						...dataLayoutFields[fieldName],
						required: toggle,
					},
				},
			},
			type: DataLayoutBuilderActions.UPDATE_DATA_LAYOUT_FIELDS,
		});

		// Mark as required within an edited field in the form builder

		dataLayoutBuilder.dispatch('fieldEdited', {
			fieldName,
			propertyName: 'required',
			propertyValue: toggle,
		});
	}

	useEffect(() => {
		const handler = ({target}) => {
			const outside = isClickOutside(
				target,
				popoverRef?.current,
				triggerRef?.current
			);

			if (outside) {
				setShowPopover(false);
			}
		};

		window.addEventListener('click', handler);

		return () => window.removeEventListener('click', handler);
	}, [popoverRef, triggerRef]);

	return (
		<div className="d-flex form-renderer-required-field justify-content-between">
			<ClayForm.Group className="form-renderer-required-field__toggle">
				<ClayToggle
					label={Liferay.Language.get('required-field')}
					onToggle={setRequireAtFormViewLevel}
					toggled={requiredAtFormViewLevel}
				/>
			</ClayForm.Group>

			<ClayTooltipProvider>
				<ClayButtonWithIcon
					borderless
					disabled={!requiredAtFormViewLevel}
					displayType="secondary"
					onClick={() => setShowPopover(!showPopover)}
					ref={triggerRef}
					small
					symbol="ellipsis-v"
					title={Liferay.Language.get('required-options')}
				/>
			</ClayTooltipProvider>

			<ClayPopover
				alignPosition="bottom-right"
				className="form-renderer-required-field__popover"
				header={Liferay.Language.get('required-options')}
				onShowChange={setShowPopover}
				ref={popoverRef}
				show={showPopover}
			>
				<div className="mt-2">
					<ClayRadioGroup
						onSelectedValueChange={setSelectedViewLevel}
						selectedValue={selectedViewLevel}
					>
						{Object.keys(VIEW_LEVEL).map((key) => (
							<ClayRadio
								key={key}
								value={key}
								{...VIEW_LEVEL[key]}
							/>
						))}
					</ClayRadioGroup>
				</div>
			</ClayPopover>
		</div>
	);
};
