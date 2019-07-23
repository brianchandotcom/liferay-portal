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

import React, {useContext} from 'react';
import PropTypes from 'prop-types';
import ClayButton from '@clayui/button';
import ClayModal from '@clayui/modal';

import {OTHER_REASON_VALUE} from '../constants.es';

import ThemeContext from '../ThemeContext.es';

const ModalContentForm = ({
	handleClose,
	handleInputChange,
	handleSubmit,
	isSending,
	pathTermsOfUse,
	reason,
	reasons
}) => (
	<form onSubmit={handleSubmit}>
		<ClayModal.Body>
			<p>
				You are about to report a violation of our{' '}
				<a href={pathTermsOfUse}>Terms of Use</a>. All reports are
				strictly confidential.
			</p>
			<div className="form-group">
				<label className="control-label" htmlFor="reason">
					Reason for the Report
				</label>
				<select
					className="form-control"
					id="reason"
					name="reason"
					onChange={handleInputChange}
					value={reason}
				>
					{Object.entries(reasons).map(([value, text]) => (
						<option key={value} value={value}>
							{text}
						</option>
					))}
					<option value={OTHER_REASON_VALUE}>Other Reason</option>
				</select>
			</div>
			{reason === OTHER_REASON_VALUE && (
				<div className="form-group">
					<label className="control-label" htmlFor="otherReason">
						Other Reason
					</label>
					<input
						className="form-control"
						id="otherReason"
						name="otherReason"
						onChange={handleInputChange}
					/>
				</div>
			)}
		</ClayModal.Body>
		<ClayModal.Footer
			last={
				<ClayButton.Group spaced>
					<ClayButton displayType="secondary" onClick={handleClose}>
						{'Cancel'}
					</ClayButton>
					<ClayButton
						disabled={isSending}
						displayType="primary"
						type="submit"
					>
						{'Report'}
					</ClayButton>
				</ClayButton.Group>
			}
		/>
	</form>
);
ModalContentForm.propTypes = {
	handleClose: PropTypes.func.isRequired,
	handleInputChange: PropTypes.func.isRequired,
	handleSubmit: PropTypes.func.isRequired,
	isSending: PropTypes.bool.isRequired,
	pathTermsOfUse: PropTypes.string.isRequired,
	reason: PropTypes.string.isRequired,
	reasons: PropTypes.string.isRequired
};

const ModalContentSuccess = ({companyName, handleClose}) => (
	<>
		<ClayModal.Body>
			<p>
				<strong>Thank you for your report.</strong>
			</p>
			<p>
				Although we cannot disclose our final decision, we do review
				every report and appreciate your effort to make sure{' '}
				<strong>{companyName}</strong> is a safe environment for
				everyone.
			</p>
		</ClayModal.Body>
		<ClayModal.Footer
			last={
				<ClayButton.Group spaced>
					<ClayButton displayType="secondary" onClick={handleClose}>
						{'Close'}
					</ClayButton>
				</ClayButton.Group>
			}
		/>
	</>
);
ModalContentSuccess.propTypes = {
	companyName: PropTypes.string.isRequired,
	handleClose: PropTypes.func.isRequired
};

const FlagsModal = ({
	companyName,
	handleClose,
	handleInputChange,
	handleSubmit,
	isSending,
	isSuccessful,
	pathTermsOfUse,
	reason,
	reasons
}) => {
	const {spritemap} = useContext(ThemeContext);

	return (
		<ClayModal onClose={handleClose} size="sm" spritemap={spritemap}>
			{onClose => (
				<>
					<ClayModal.Header>
						Report Inappropriate Content
					</ClayModal.Header>
					{!isSuccessful ? (
						<ModalContentForm
							handleClose={onClose}
							handleInputChange={handleInputChange}
							handleSubmit={handleSubmit}
							isSending={isSending}
							pathTermsOfUse={pathTermsOfUse}
							reason={reason}
							reasons={reasons}
						/>
					) : (
						<ModalContentSuccess
							companyName={companyName}
							handleClose={onClose}
						/>
					)}
				</>
			)}
		</ClayModal>
	);
};
FlagsModal.propTypes = {
	handleClose: PropTypes.func.isRequired,
	isSuccessful: PropTypes.bool.isRequired
};

export default FlagsModal;
