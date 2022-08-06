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
import ClayIcon from '@clayui/icon';
import {useModal} from '@clayui/modal';
import {useIsMounted} from '@liferay/frontend-js-react-web';
import {fetch, objectToFormData} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useContext, useState} from 'react';

import ThemeContext from '../ThemeContext.es';
import {
	OTHER_REASON_VALUE,
	STATUS_ERROR,
	STATUS_LOGIN,
	STATUS_REPORT,
	STATUS_SUCCESS,
} from '../constants.es';
import FlagsModal from './FlagsModal.es';

const Flags = ({
	baseData,
	btnProps,
	captchaURI,
	companyName,
	disabled = false,
	forceLogin = false,
	message = Liferay.Language.get('report'),
	onlyIcon = false,
	pathTermsOfUse,
	showIcon = true,
	reasons,
	signedIn = false,
	uri,
	viewMode,
}) => {
	const [isSending, setIsSending] = useState(false);
	const [reportDialogOpen, setReportDialogOpen] = useState(false);
	const [status, setStatus] = useState(
		forceLogin ? STATUS_LOGIN : STATUS_REPORT
	);
	const [error, setError] = useState(null);

	const [otherReason, setOtherReason] = useState('');
	const [reporterEmailAddress, setReporterEmailAddress] = useState('');
	const [selectedReason, setSelectedReason] = useState(
		Object.keys(reasons)[0]
	);

	const {namespace} = useContext(ThemeContext);

	const getReason = () => {
		if (selectedReason === OTHER_REASON_VALUE) {
			return otherReason || Liferay.Language.get('no-reason-specified');
		}

		return selectedReason;
	};

	const handleClickShow = () => {
		setReportDialogOpen(true);
	};

	const handleClickClose = () => {
		setError(false);
		setReportDialogOpen(false);
	};

	const handleInputChange = (event) => {
		const target = event.target;
		const value =
			target.type === 'checkbox' ? target.checked : target.value.trim();
		const name = target.name;

		if (name === 'otherReason') {
			setOtherReason(value);
		}
		else if (name === 'reporterEmailAddress') {
			setReporterEmailAddress(value);
		}
		else if (name === 'selectedReason') {
			setSelectedReason(value);
		}
	};

	const isMounted = useIsMounted();

	const handleSubmitReport = (event) => {
		event.preventDefault();

		setIsSending(true);

		const formDataObj = {
			...baseData,
			[`${namespace}reason`]: getReason(),
			[`${namespace}reporterEmailAddress`]: signedIn
				? Liferay.ThemeDisplay.getUserEmailAddress()
				: reporterEmailAddress,
		};

		fetch(uri, {
			body: objectToFormData(formDataObj, new FormData(event.target)),
			method: 'post',
		})
			.then((res) => res.json())
			.then(({error}) => {
				if (isMounted()) {
					setError(error);
					setIsSending(false);
					if (!error) {
						setStatus(STATUS_SUCCESS);
					}
				}
			})
			.catch(() => {
				if (isMounted()) {
					setStatus(STATUS_ERROR);
				}
			});
	};

	const {observer, onClose} = useModal({
		onClose: handleClickClose,
	});

	return (
		<>
			<ClayButton
				className={`btn-outline-borderless btn-outline-secondary ${
					onlyIcon ? 'lfr-portal-tooltip' : ''
				}`}
				data-title={onlyIcon ? message : undefined}
				disabled={!viewMode || disabled}
				displayType="secondary"
				monospaced={onlyIcon}
				onClick={handleClickShow}
				small
				{...btnProps}
			>
				{showIcon && (
					<span
						className={
							onlyIcon
								? undefined
								: 'inline-item inline-item-before'
						}
					>
						<ClayIcon symbol="flag-empty" />
					</span>
				)}

				<span className={onlyIcon ? 'sr-only' : undefined}>
					{message}
				</span>
			</ClayButton>
			{reportDialogOpen && (
				<FlagsModal
					captchaURI={captchaURI}
					companyName={companyName}
					error={error}
					handleClose={onClose}
					handleInputChange={handleInputChange}
					handleSubmit={handleSubmitReport}
					isSending={isSending}
					observer={observer}
					pathTermsOfUse={pathTermsOfUse}
					reasons={reasons}
					selectedReason={selectedReason}
					signedIn={signedIn}
					status={status}
				/>
			)}
		</>
	);
};
Flags.propTypes = {
	baseData: PropTypes.object.isRequired,
	btnProps: PropTypes.object,
	captchaURI: PropTypes.string.isRequired,
	companyName: PropTypes.string.isRequired,
	disabled: PropTypes.bool,
	forceLogin: PropTypes.bool,
	message: PropTypes.string,
	onlyIcon: PropTypes.bool,
	pathTermsOfUse: PropTypes.string.isRequired,
	reasons: PropTypes.object.isRequired,
	showIcon: PropTypes.bool,
	signedIn: PropTypes.bool,
	uri: PropTypes.string.isRequired,
};

export default Flags;
