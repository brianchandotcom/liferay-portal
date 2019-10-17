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
import {useIsMounted} from 'frontend-js-react-web';
import {fetch, objectToFormData} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useContext, useState} from 'react';

import ThemeContext from '../ThemeContext.es';
import {
	OTHER_REASON_VALUE,
	STATUS_ERROR,
	STATUS_LOGIN,
	STATUS_REPORT,
	STATUS_SUCCESS
} from '../constants.es';
import FlagsModal from './FlagsModal.es';

const Flags = ({
	baseData,
	companyName,
	disabled = false,
	forceLogin = false,
	message = Liferay.Language.get('report'),
	onlyIcon = false,
	pathTermsOfUse,
	reasons,
	signedIn = false,
	uri
}) => {
	const [isSending, setIsSending] = useState(false);
	const [reportDialogOpen, setReportDialogOpen] = useState(false);
	const [status, setStatus] = useState(
		forceLogin ? STATUS_LOGIN : STATUS_REPORT
	);

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
		setReportDialogOpen(false);
	};

	const handleInputChange = event => {
		const target = event.target;
		const value =
			target.type === 'checkbox' ? target.checked : target.value.trim();
		const name = target.name;

		if (name === 'otherReason') {
			setOtherReason(value);
		} else if (name === 'reporterEmailAddress') {
			setReporterEmailAddress(value);
		} else if (name === 'selectedReason') {
			setSelectedReason(value);
		}
	};

	const isMounted = useIsMounted();

	const handleSubmitReport = event => {
		event.preventDefault();

		setIsSending(true);

		const formDataObj = {
			...baseData,
			[`${namespace}reason`]: getReason()
		};

		if (!signedIn) {
			formDataObj[
				`${namespace}reporterEmailAddress`
			] = reporterEmailAddress;
		}

		fetch(uri, {
			body: objectToFormData(formDataObj),
			method: 'post'
		})
			.then(({status}) => {
				if (isMounted()) {
					if (status === Liferay.STATUS_CODE.OK) {
						setStatus(STATUS_SUCCESS);
					} else {
						setStatus(STATUS_ERROR);
					}
				}
			})
			.catch(() => {
				if (isMounted()) {
					setStatus(STATUS_ERROR);
				}
			});
	};

	const {observer} = useModal({
		onClose: handleClickClose
	});

	return (
		<>
			<ClayButton
				className={`btn-outline-borderless btn-outline-secondary ${
					onlyIcon ? 'lfr-portal-tooltip' : ''
				}`}
				data-title={onlyIcon ? message : undefined}
				disabled={disabled}
				displayType="secondary"
				monospaced={onlyIcon}
				onClick={handleClickShow}
				small
			>
				<span
					className={
						!onlyIcon ? 'inline-item inline-item-before' : undefined
					}
				>
					<ClayIcon symbol="flag-empty" />
				</span>
				<span className={onlyIcon ? 'sr-only' : undefined}>
					{message}
				</span>
			</ClayButton>
			{reportDialogOpen && (
				<FlagsModal
					companyName={companyName}
					handleClose={handleClickClose}
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
	companyName: PropTypes.string.isRequired,
	disabled: PropTypes.bool,
	forceLogin: PropTypes.bool,
	message: PropTypes.string,
	onlyIcon: PropTypes.bool,
	pathTermsOfUse: PropTypes.string.isRequired,
	reasons: PropTypes.object.isRequired,
	signedIn: PropTypes.bool,
	uri: PropTypes.string.isRequired
};

export default Flags;
