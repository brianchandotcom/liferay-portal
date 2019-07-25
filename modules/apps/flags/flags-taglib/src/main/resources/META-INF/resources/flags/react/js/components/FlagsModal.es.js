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

import {
	OTHER_REASON_VALUE,
	STATUS_ERROR,
	STATUS_LOGIN,
	STATUS_REPORT,
	STATUS_SUCCESS
} from '../constants.es';

import ThemeContext from '../ThemeContext.es';

const ModalContentForm = ({
	handleClose,
	handleInputChange,
	handleSubmit,
	isSending,
	pathTermsOfUse,
	reason,
	reasons,
	signedIn
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
			{!signedIn && (
				<div className="form-group">
					<label
						className="control-label"
						htmlFor="reporterEmailAddress"
					>
						{Liferay.Language.get('email-address')}
					</label>
					<input
						className="form-control"
						id="reporterEmailAddress"
						name="reporterEmailAddress"
						onChange={handleInputChange}
						type="email"
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
	reasons: PropTypes.string.isRequired,
	signedIn: PropTypes.bool.isRequired
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
				<ClayButton displayType="secondary" onClick={handleClose}>
					{'Close'}
				</ClayButton>
			}
		/>
	</>
);
ModalContentSuccess.propTypes = {
	companyName: PropTypes.string.isRequired,
	handleClose: PropTypes.func.isRequired
};

const ModalContentError = ({handleClose}) => (
	<>
		<ClayModal.Body>
			<p>
				<strong>
					{Liferay.Language.get(
						'an-error-occurred-while-sending-the-report.-please-try-again-in-a-few-minutes'
					)}
				</strong>
			</p>
		</ClayModal.Body>
		<ClayModal.Footer
			last={
				<ClayButton displayType="secondary" onClick={handleClose}>
					{'Close'}
				</ClayButton>
			}
		/>
	</>
);
ModalContentError.propTypes = {
	handleClose: PropTypes.func.isRequired
};

const ModalContentLogin = ({handleClose}) => (
	<>
		<ClayModal.Body>
			<p>
				<strong>
					{Liferay.Language.get(
						'please-sign-in-to-flag-this-as-inappropriate'
					)}
				</strong>
			</p>
		</ClayModal.Body>
		<ClayModal.Footer
			last={
				<ClayButton displayType="secondary" onClick={handleClose}>
					{'Close'}
				</ClayButton>
			}
		/>
	</>
);
ModalContentLogin.propTypes = {
	handleClose: PropTypes.func.isRequired
};

const ModalContent = ({companyName, handleClose, status, ...props}) => {
	switch (status) {
		case STATUS_LOGIN:
			return <ModalContentLogin handleClose={handleClose} />;

		case STATUS_REPORT:
			return <ModalContentForm {...props} handleClose={handleClose} />;

		case STATUS_SUCCESS:
			return (
				<ModalContentSuccess
					companyName={companyName}
					handleClose={handleClose}
				/>
			);

		case STATUS_ERROR:
		default:
			return <ModalContentError handleClose={handleClose} />;
	}
};

const FlagsModal = ({
	companyName,
	handleClose,
	handleInputChange,
	handleSubmit,
	isSending,
	pathTermsOfUse,
	reason,
	reasons,
	signedIn,
	status
}) => {
	const {spritemap} = useContext(ThemeContext);

	return (
		<ClayModal onClose={handleClose} size="sm" spritemap={spritemap}>
			{onClose => (
				<>
					<ClayModal.Header>
						Report Inappropriate Content
					</ClayModal.Header>
					<ModalContent
						companyName={companyName}
						handleClose={onClose}
						handleInputChange={handleInputChange}
						handleSubmit={handleSubmit}
						isSending={isSending}
						pathTermsOfUse={pathTermsOfUse}
						reason={reason}
						reasons={reasons}
						signedIn={signedIn}
						status={status}
					/>
				</>
			)}
		</ClayModal>
	);
};
FlagsModal.propTypes = {
	handleClose: PropTypes.func.isRequired
};

export default FlagsModal;
