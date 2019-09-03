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

import classNames from 'classnames';
import React, {useRef, useEffect, useState, useCallback} from 'react';
import ClayIcon from '@clayui/icon';
import PropTypes from 'prop-types';

const DisplayPageModalForm = React.forwardRef((props, ref) => {
	const [subtypes, setSubtypes] = useState([]);
	const nameInput = useRef(null);

	useEffect(() => {
		if (nameInput.current) {
			nameInput.current.focus();
		}
	}, []);

	const hasError = props.error !== null;

	const onChange = useCallback(
		event => {
			const selectedMappingId = event.target.selectedOptions[0].value;

			const mappingType = props.mappingTypes.find(
				mappingType => mappingType.id === selectedMappingId
			);

			if (mappingType) {
				setSubtypes(mappingType.subtypes);
			} else {
				setSubtypes([]);
			}
		},
		[props.mappingTypes]
	);

	return (
		<form onSubmit={props.onSubmit} ref={ref}>
			<div
				className={classNames({
					'form-group': true,
					'has-error': hasError
				})}
			>
				<label htmlFor={`${props.namespace}name`}>
					{Liferay.Language.get('name')}
					<span className="reference-mark">
						<ClayIcon symbol="asterisk" />
					</span>
				</label>

				<input
					className={'form-control'}
					defaultValue={props.displayPageName}
					name={`${props.namespace}name`}
					ref={nameInput}
				/>

				{hasError && (
					<div className="form-feedback-item">
						<span className="form-feedback-indicator mr-1">
							<ClayIcon symbol="exclamation-full" />
						</span>
						{props.error}
					</div>
				)}
			</div>

			{Array.isArray(props.mappingTypes) &&
				props.mappingTypes.length > 0 && (
					<fieldset>
						<div className="form-group">
							<label htmlFor={`${props.namespace}classNameId`}>
								{Liferay.Language.get('content-type')}
								<span className="reference-mark">
									<ClayIcon symbol="asterisk" />
								</span>
							</label>

							<select
								className="form-control"
								name={`${props.namespace}classNameId`}
								onChange={onChange}
							>
								<option
									label={`-- ${Liferay.Language.get(
										'no-selected'
									)} --`}
									value=""
								></option>
								{props.mappingTypes.map(mappingType => (
									<option
										key={mappingType.id}
										label={mappingType.label}
										value={mappingType.id}
									></option>
								))}
							</select>
						</div>

						{Array.isArray(subtypes) && subtypes.length > 0 && (
							<div className="form-group">
								<label
									htmlFor={`${props.namespace}classTypeId`}
								>
									{Liferay.Language.get('subtype')}
									<span className="reference-mark">
										<ClayIcon symbol="asterisk" />
									</span>
								</label>

								<select
									className="form-control"
									name={`${props.namespace}classTypeId`}
								>
									<option
										label={`-- ${Liferay.Language.get(
											'no-selected'
										)} --`}
										value=""
									></option>
									{subtypes.map(subtype => (
										<option
											key={subtype.id}
											label={subtype.label}
											value={subtype.id}
										></option>
									))}
								</select>
							</div>
						)}
					</fieldset>
				)}
		</form>
	);
});

DisplayPageModalForm.propTypes = {
	displayPageName: PropTypes.string,
	error: PropTypes.string,
	mappingType: PropTypes.arrayOf(
		PropTypes.shape({
			id: PropTypes.string,
			label: PropTypes.string,
			subtypes: PropTypes.arrayOf(
				PropTypes.shape({
					id: PropTypes.string,
					label: PropTypes.string
				})
			)
		})
	),
	namespace: PropTypes.string.isRequired,
	onSubmit: PropTypes.func.isRequire
};

export {DisplayPageModalForm};
export default DisplayPageModalForm;
