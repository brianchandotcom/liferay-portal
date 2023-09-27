/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useModal} from '@clayui/modal';
import {useIsMounted} from '@liferay/frontend-js-react-web';
import {fetch, objectToFormData} from 'frontend-js-web';
import {useState} from 'react';

import {
	OTHER_REASON_VALUE,
	STATUS_ERROR,
	STATUS_LOGIN,
	STATUS_REPORT,
	STATUS_SUCCESS,
} from '../constants.es';

const useFlags = ({
	baseData,
	forceLogin,
	namespace,
	reasons,
	signedIn,
	uri,
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

	const clearFields = () => {
		setOtherReason('');
		setReporterEmailAddress('');
		setSelectedReason(Object.keys(reasons)[0]);
	};

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

	const form = {
		otherReason,
		reporterEmailAddress,
		selectedReason,
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
						clearFields();
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

	return {
		error,
		form,
		handleClickShow,
		handleInputChange,
		handleSubmitReport,
		isSending,
		observer,
		onClose,
		reportDialogOpen,
		selectedReason,
		setStatus,
		status,
	};
};

export default useFlags;
