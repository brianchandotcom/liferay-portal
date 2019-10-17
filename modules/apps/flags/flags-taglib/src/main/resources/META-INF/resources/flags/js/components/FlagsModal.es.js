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
import ClayModal from '@clayui/modal';
import PropTypes from 'prop-types';
import React, {useContext} from 'react';

import ThemeContext from '../ThemeContext.es';
import {
	OTHER_REASON_VALUE,
	STATUS_ERROR,
	STATUS_LOGIN,
	STATUS_REPORT,
	STATUS_SUCCESS
} from '../constants.es';
import {sub} from '../utils.es';

const ModalContentForm = ({
	handleClose,
	handleInputChange,
	handleSubmit,
	isSending,
	pathTermsOfUse,
	reasons,
	selectedReason,
	signedIn
}) => {
	const {namespace} = useContext(ThemeContext);

	return (
		<form onSubmit={handleSubmit}>
			<ClayModal.Body>
				<p>
					{sub(
						Liferay.Language.get(
							'you-are-about-to-report-a-violation-of-our-x.-all-reports-are-strictly-confidential'
						),
						[
							<a href={pathTermsOfUse} key={pathTermsOfUse}>
								{Liferay.Language.get('terms-of-use')}
							</a>
						],
						false
					)}
				</p>
				<div className="form-group">
					<label
						className="control-label"
						htmlFor={`${namespace}selectedReason`}
					>
						{Liferay.Language.get('reason-for-the-report')}
					</label>
					<select
						className="form-control"
						id={`${namespace}selectedReason`}
						name="selectedReason"
						onChange={handleInputChange}
						value={selectedReason}
					>
						{Object.entries(reasons).map(([value, text]) => (
							<option key={value} value={value}>
								{text}
							</option>
						))}
						<option value={OTHER_REASON_VALUE}>
							{Liferay.Language.get('other-reason')}
						</option>
					</select>
				</div>
				{selectedReason === OTHER_REASON_VALUE && (
					<div className="form-group">
						<label
							className="control-label"
							htmlFor={`${namespace}otherReason`}
						>
							{Liferay.Language.get('other-reason')}
						</label>
						<input
							autoFocus
							className="form-control"
							id={`${namespace}otherReason`}
							name="otherReason"
							onChange={handleInputChange}
						/>
					</div>
				)}
				{!signedIn && (
					<div className="form-group">
						<label
							className="control-label"
							htmlFor={`${namespace}otherRreporterEmailAddresseason`}
						>
							{Liferay.Language.get('email-address')}
						</label>
						<input
							className="form-control"
							id={`${namespace}otherRreporterEmailAddresseason`}
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
						<ClayButton
							displayType="secondary"
							onClick={handleClose}
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>
						<ClayButton
							disabled={isSending}
							displayType="primary"
							type="submit"
						>
							{Liferay.Language.get('report')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</form>
	);
};

const ModalBodySuccess = ({companyName}) => (
	<ClayModal.Body>
		<p>
			<strong>{Liferay.Language.get('thank-you-for-your-report')}</strong>
		</p>
		<p>
			{sub(
				Liferay.Language.get(
					'although-we-cannot-disclose-our-final-decision,-we-do-review-every-report-and-appreciate-your-effort-to-make-sure-x-is-a-safe-environment-for-everyone'
				),
				[companyName]
			)}
		</p>
	</ClayModal.Body>
);

const ModalBodyError = () => (
	<ClayModal.Body>
		<p>
			<strong>
				{Liferay.Language.get(
					'an-error-occurred-while-sending-the-report.-please-try-again-in-a-few-minutes'
				)}
			</strong>
		</p>
	</ClayModal.Body>
);

const ModalBodyLogin = () => (
	<ClayModal.Body>
		<p>
			<strong>
				{Liferay.Language.get(
					'please-sign-in-to-flag-this-as-inappropriate'
				)}
			</strong>
		</p>
	</ClayModal.Body>
);

const ModalBody = ({companyName, status}) => {
	switch (status) {
		case STATUS_LOGIN:
			return <ModalBodyLogin />;

		case STATUS_SUCCESS:
			return <ModalBodySuccess companyName={companyName} />;

		case STATUS_ERROR:
		default:
			return <ModalBodyError />;
	}
};

const FlagsModal = ({
	companyName,
	handleClose,
	handleInputChange,
	handleSubmit,
	isSending,
	observer,
	pathTermsOfUse,
	reasons,
	selectedReason,
	signedIn,
	status
}) => {
	return (
		<ClayModal observer={observer} size="md">
			<ClayModal.Header>
				{Liferay.Language.get('report-inappropriate-content')}
			</ClayModal.Header>
			{status === STATUS_REPORT ? (
				<ModalContentForm
					handleClose={handleClose}
					handleInputChange={handleInputChange}
					handleSubmit={handleSubmit}
					isSending={isSending}
					pathTermsOfUse={pathTermsOfUse}
					reasons={reasons}
					selectedReason={selectedReason}
					signedIn={signedIn}
				/>
			) : (
				<>
					<ModalBody
						companyName={companyName}
						handleClose={handleClose}
						status={status}
					/>
					<ClayModal.Footer
						last={
							<ClayButton
								displayType="secondary"
								onClick={handleClose}
							>
								{Liferay.Language.get('close')}
							</ClayButton>
						}
					/>
				</>
			)}
		</ClayModal>
	);
};

FlagsModal.propTypes = {
	companyName: PropTypes.string.isRequired,
	handleClose: PropTypes.func.isRequired,
	handleInputChange: PropTypes.func.isRequired,
	handleSubmit: PropTypes.func.isRequired,
	isSending: PropTypes.bool.isRequired,
	pathTermsOfUse: PropTypes.string.isRequired,
	reasons: PropTypes.object.isRequired,
	selectedReason: PropTypes.string.isRequired,
	signedIn: PropTypes.bool.isRequired,
	status: PropTypes.oneOf([
		STATUS_ERROR,
		STATUS_LOGIN,
		STATUS_REPORT,
		STATUS_SUCCESS
	]).isRequired
};

export default FlagsModal;
