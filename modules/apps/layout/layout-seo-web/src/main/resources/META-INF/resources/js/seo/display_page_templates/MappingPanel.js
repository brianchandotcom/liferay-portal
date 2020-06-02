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

function normalizeFields(fields = []) {
	return fields.map(({key, label}) => ({
		label,
		value: key,
	}));
}

function MappingPanel({fields, initialSeletedField, selectedSource}) {
	const [isPanelOpen, setIsPanelOpen] = useState(false);
	const [seletedField, setSeletedField] = useState(initialSeletedField);

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
							<ClayInput readOnly value={selectedSource} />
						</ClayForm.Group>
						<ClayForm.Group small>
							<label htmlFor="mappingSelectorSourceSelect">
								{Liferay.Language.get('source')}
							</label>
							<ClaySelectWithOption
								aria-label={Liferay.Language.get('source')}
								id="mappingSelectorSourceSelect"
								onChange={(event) => {
									const {value} = event.target;
									setSeletedField(value);
								}}
								options={normalizeFields(fields)}
								value={seletedField}
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
	initialSeletedField: PropTypes.shape({
		key: PropTypes.string,
		label: PropTypes.string,
	}).isRequired,
	selectedSource: PropTypes.shape({
		key: PropTypes.string,
		label: PropTypes.string,
	}).isRequired,
};

export default MappingPanel;
