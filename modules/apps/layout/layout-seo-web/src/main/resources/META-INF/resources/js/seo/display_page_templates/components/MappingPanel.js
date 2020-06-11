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
import classNames from 'classnames';
import {PropTypes} from 'prop-types';
import React, {useRef, useState} from 'react';

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
	field,
	source,
	onChange = noop,
}) {
	const [isPanelOpen, setIsPanelOpen] = useState(false);
	const wrapperRef = useRef(null);

	useOnClickOutside([wrapperRef.current], () => setIsPanelOpen(false));

	const handleChangeField = (event) => {
		const {value} = event.target;

		const field = fields.find(({key}) => key === value);

		onChange({
			field,
			source,
		});
	};

	return (
		<div className="dpt-mapping-panel-wrapper" ref={wrapperRef}>
			<ClayButton
				className={classNames('dpt-mapping-btn lfr-portal-tooltip', {
					active: isActive,
				})}
				displayType="secondary"
				monospaced
				onClick={() => {
					setIsPanelOpen((state) => !state);
				}}
				title={Liferay.Language.get('map')}
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
								value={field.key}
							/>
						</ClayForm.Group>
					</div>
				</div>
			)}
		</div>
	);
}

MappingPanel.propTypes = {
	field: PropTypes.shape({
		key: PropTypes.string,
		label: PropTypes.string,
	}).isRequired,
	fields: PropTypes.arrayOf(
		PropTypes.shape({
			key: PropTypes.string,
			label: PropTypes.string,
		})
	).isRequired,
	isActive: PropTypes.bool,
	name: PropTypes.string.isRequired,
	source: PropTypes.shape({
		initialValue: PropTypes.string,
	}).isRequired,
};

export default MappingPanel;
