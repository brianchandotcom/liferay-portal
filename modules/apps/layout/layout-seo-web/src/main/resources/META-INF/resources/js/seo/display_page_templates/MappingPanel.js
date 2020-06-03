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

import ClayButton from '@clayui/button';
import ClayForm, {ClayInput, ClaySelectWithOption} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {PropTypes} from 'prop-types';
import React, {useState} from 'react';

const noop = () => {};

const normalizeField = ({key, label}) => ({
	label,
	value: key,
});

function MappingPanel({
	fields,
	initialSelectedField,
	initialSelectedSource,
	onChange = noop,
}) {
	const [isPanelOpen, setIsPanelOpen] = useState(false);
	const [selectedFieldValue, setSelectedFieldValue] = useState(
		normalizeField(initialSelectedField).value
	);

	const handleChangeField = (event) => {
		const {value} = event.target;
		setSelectedFieldValue(value);

		const field = normalizeField(fields.find(({key}) => key === value));

		onChange({
			field,
			source: initialSelectedSource,
		});
	};

	return (
		<div className="dpt-mapping-panel-wrapper">
			<ClayButton
				className="dpt-mapping-btn"
				displayType="secondary"
				id="titleSelector"
				monospaced
				onClick={() => {
					setIsPanelOpen((state) => !state);
				}}
			>
				<ClayIcon symbol="bolt" />
			</ClayButton>
			{isPanelOpen && (
				<div
					className="dpt-mapping-panel popover popover-scrollable"
					onClick={(event) => event.stopPropagation()}
				>
					<div className="popover-body">
						<ClayForm.Group small>
							<label htmlFor="mappingSelectorSourceSelect">
								{Liferay.Language.get('source')}
							</label>
							<ClayInput
								readOnly
								value={initialSelectedSource.label}
							/>
						</ClayForm.Group>
						<ClayForm.Group small>
							<label htmlFor="mappingSelectorFieldSelect">
								{Liferay.Language.get('field')}
							</label>
							<ClaySelectWithOption
								id="mappingSelectorFieldSelect"
								onChange={handleChangeField}
								options={fields.map(normalizeField)}
								value={selectedFieldValue.value}
							/>
						</ClayForm.Group>
					</div>
				</div>
			)}
		</div>
	);
}

MappingPanel.propTypes = {
	fields: PropTypes.arrayOf(
		PropTypes.shape({
			key: PropTypes.string,
			label: PropTypes.string,
		})
	).isRequired,
	initialSelectedField: PropTypes.shape({
		key: PropTypes.string,
		label: PropTypes.string,
	}).isRequired,
	selectedSource: PropTypes.shape({
		key: PropTypes.string,
		label: PropTypes.string,
	}).isRequired,
};

export default MappingPanel;
