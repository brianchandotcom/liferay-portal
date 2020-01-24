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
import ClayForm, {ClayRadio, ClayRadioGroup, ClayInput} from '@clayui/form';
import ClayModal from '@clayui/modal';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

const noop = () => {};

const CheckinModal = ({
	checkedOut,
	observer,
	onModalClose = noop,
	callback = noop,
	dlVersionNumberIncreaseValues
}) => {
	const {MAJOR, MINOR, NONE} = dlVersionNumberIncreaseValues;
	const [changeLog, setChangeLog] = useState('');
	const [versionIncrease, setVersionIncrease] = useState(
		checkedOut ? MAJOR : MINOR
	);

	const handleChangeChangeLog = event => {
		setChangeLog(event.target.value);
	};

	const handleSubmit = event => {
		event.preventDefault();
		callback(versionIncrease, changeLog);
	};

	return (
		<ClayModal observer={observer} size="md">
			<ClayModal.Header>
				{Liferay.Language.get('describe-your-changes')}
			</ClayModal.Header>

			<form onSubmit={handleSubmit}>
				<ClayModal.Body>
					<fieldset className="fieldset">
						<div className="h5">
							{Liferay.Language.get(
								'select-whether-this-is-a-major-or-minor-version'
							)}
						</div>
						<ClayRadioGroup
							name="versionIncrease"
							onSelectedValueChange={setVersionIncrease}
							selectedValue={versionIncrease}
						>
							<ClayRadio
								label={Liferay.Language.get('major-version')}
								value={MAJOR}
							/>
							<ClayRadio
								label={Liferay.Language.get('minor-version')}
								value={MINOR}
							/>
							<ClayRadio
								label={Liferay.Language.get(
									'keep-current-version-number'
								)}
								value={NONE}
							/>
						</ClayRadioGroup>
						{versionIncrease !== NONE && (
							<ClayForm.Group>
								<label htmlFor="changeLog">
									{Liferay.Language.get('version-notes')}
								</label>
								<ClayInput
									id="changeLog"
									label={Liferay.Language.get(
										'version-notes'
									)}
									name="changeLog"
									onChange={handleChangeChangeLog}
									type="text"
								/>
							</ClayForm.Group>
						)}
					</fieldset>
				</ClayModal.Body>

				<ClayModal.Footer
					last={
						<ClayButton.Group spaced>
							<ClayButton
								displayType="secondary"
								onClick={onModalClose}
							>
								{Liferay.Language.get('cancel')}
							</ClayButton>

							<ClayButton displayType="primary" type="submit">
								{Liferay.Language.get('save')}
							</ClayButton>
						</ClayButton.Group>
					}
				/>
			</form>
		</ClayModal>
	);
};

CheckinModal.propTypes = {
	callback: PropTypes.func,
	checkedOut: PropTypes.bool.isRequired,
	dlVersionNumberIncreaseValues: PropTypes.shape({
		MAJOR: PropTypes.string.isRequired,
		MINOR: PropTypes.string.isRequired,
		NONE: PropTypes.string.isRequired
	}).isRequired,
	observer: PropTypes.object.isRequired,
	onModalClose: PropTypes.func
};

export default CheckinModal;
