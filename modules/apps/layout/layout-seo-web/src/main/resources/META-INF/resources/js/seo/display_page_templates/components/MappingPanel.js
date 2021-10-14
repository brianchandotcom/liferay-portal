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

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayForm, {ClayInput, ClaySelectWithOption} from '@clayui/form';
import classNames from 'classnames';
import {PropTypes} from 'prop-types';
import React, {useRef, useState} from 'react';

import {FIELD_TYPES} from '../constants';
import useOnClickOutside from '../hooks/useOnClickOutside';

const noop = () => {};

const normalizeField = ({key, label}) => ({
	label,
	value: key,
});

function MappingPanel({
	isActive = false,
	name,
	fields,
	field: initialField,
	fieldType,
	source,
	onSelect = noop,
	clearSelectionOnClose = false,
}) {
	const [isPanelOpen, setIsPanelOpen] = useState(false);
	const [fieldValue, setFieldValue] = useState(
		initialField?.key || fields[0].key
	);
	const wrapperRef = useRef(null);

	const handleOnClose = () => {
		if (isPanelOpen) {
			setIsPanelOpen(false);

			if (clearSelectionOnClose) {
				setFieldValue(fields[0].key);
			}
		}
	};

	useOnClickOutside([wrapperRef.current], handleOnClose);

	const handleChangeField = (event) => {
		const {value} = event.target;
		const field = fields.find(({key}) => key === value);

		setFieldValue(field.key);
	};

	const handleOnSelect = () => {
		const field = fields.find(({key}) => key === fieldValue);

		onSelect({
			field,
			source,
		});
	};

	return (
		<div className="dpt-mapping-panel-wrapper" ref={wrapperRef}>
			<ClayButtonWithIcon
				className={classNames('dpt-mapping-btn', {
					active: isActive,
				})}
				displayType="secondary"
				monospaced
				onClick={() => {
					setIsPanelOpen((state) => !state);
				}}
				symbol="bolt"
				title={Liferay.Language.get('map')}
			/>
			{isPanelOpen && (
				<div
					className="dpt-mapping-panel popover popover-scrollable"
					onClick={(event) => event.stopPropagation()}
				>
					<div className="popover-body">
						<ClayForm.Group small>
							<label htmlFor={`${name}_mappingSelectorSource`}>
								{Liferay.Language.get('source')}
							</label>
							<ClayInput
								id={`${name}_mappingSelectorSource`}
								readOnly
								value={source.initialValue}
							/>
						</ClayForm.Group>
						<ClayForm.Group small>
							<label
								htmlFor={`${name}_mappingSelectorFieldSelect`}
							>
								{Liferay.Language.get('field')}
							</label>
							<ClaySelectWithOption
								id={`${name}_mappingSelectorFieldSelect`}
								onChange={handleChangeField}
								options={fields.map(normalizeField)}
								value={fieldValue}
							/>
						</ClayForm.Group>

						<ClayButton
							block
							disabled={initialField?.key === fieldValue}
							displayType="primary"
							onClick={handleOnSelect}
						>
							{fieldType === FIELD_TYPES.TEXT
								? Liferay.Language.get('add-field')
								: Liferay.Language.get('map-content')}
						</ClayButton>
					</div>
				</div>
			)}
		</div>
	);
}

MappingPanel.propTypes = {
	clearSelectionOnClose: PropTypes.bool,
	field: PropTypes.shape({
		key: PropTypes.string,
		label: PropTypes.string,
	}),
	fieldType: PropTypes.string,
	fields: PropTypes.arrayOf(
		PropTypes.shape({
			key: PropTypes.string,
			label: PropTypes.string,
		})
	).isRequired,
	isActive: PropTypes.bool,
	name: PropTypes.string.isRequired,
	onSelect: PropTypes.func,
	source: PropTypes.shape({
		initialValue: PropTypes.string,
	}).isRequired,
};

export default MappingPanel;
