/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import React, {useContext} from 'react';
import {useFormContext} from 'react-hook-form';
import Modal from '../../../../../../common/components/modal';
import {
	STORAGE_KEYS,
	Storage,
} from '../../../../../../common/services/liferay/storage';
import {countCompletedFields} from '../../../../../../common/utils';
import {RAYLIFE_PAGES} from '../../../../../../common/utils/constants';
import {clearExitAlert} from '../../../../../../common/utils/exitAlert';
import {
	getLiferaySiteName,
	redirectTo,
} from '../../../../../../common/utils/liferay';
import {
	APPLICATION_STATUS,
	AVAILABLE_STEPS,
} from '../../../../../get-a-quote/utils/constants';
import {AppContext} from '../../../../context/AppContextProvider';
import {createQuoteRetrieve} from '../../../../services/QuoteRetrieve';
import {updateRaylifeApplicationStatus} from '../../../../services/RaylifeApplication';

const liferaySiteName = getLiferaySiteName();

const ProgressSaved = ({
	email,
	isMobileDevice = false,
	onClose,
	productQuote,
	setError,
	show,
}) => {
	const {
		control: {_fields},
	} = useFormContext();

	const {
		state: {selectedStep},
	} = useContext(AppContext);

	const onSendLinkAndExit = async () => {
		try {
			const applicationId = Storage.getItem(STORAGE_KEYS.APPLICATION_ID);

			await createQuoteRetrieve({
				productName: productQuote,
				quoteRetrieveLink: `${origin}${liferaySiteName}/get-a-quote?applicationId=${applicationId}`,
				retrieveEmail: email,
			});

			let status = APPLICATION_STATUS.OPEN;

			if (
				AVAILABLE_STEPS.BASICS_BUSINESS_INFORMATION.index !==
				selectedStep.index
			) {
				status = APPLICATION_STATUS.INCOMPLETE;
			}

			if (
				AVAILABLE_STEPS.BUSINESS.index === selectedStep.index &&
				countCompletedFields(_fields?.business || {}) === 0
			) {
				status = APPLICATION_STATUS.INCOMPLETE;
			}

			if (
				AVAILABLE_STEPS.EMPLOYEES.index === selectedStep.index &&
				countCompletedFields(_fields?.employees || {}) === 0
			) {
				status = APPLICATION_STATUS.INCOMPLETE;
			}

			if (
				AVAILABLE_STEPS.PROPERTY.index === selectedStep.index &&
				countCompletedFields(_fields?.property || {}) === 0
			) {
				status = APPLICATION_STATUS.INCOMPLETE;
			}

			await updateRaylifeApplicationStatus(applicationId, status);

			clearExitAlert();

			redirectTo(RAYLIFE_PAGES.HOME);
		}
		catch (error) {
			setError('Unable to save your information. Please try again.');
			onClose();
		}
	};

	return (
		<Modal
			backdropLight={isMobileDevice}
			closeable={!isMobileDevice}
			footer={
				<div
					className={classNames(
						'align-items-center d-flex flex-row ml-2 mr-1 mt-auto',
						{
							'flex-wrap justify-content-center': isMobileDevice,
							'justify-content-between': !isMobileDevice,
						}
					)}
				>
					<button
						className={classNames(
							'btn btn-link link text-link-md text-small-caps',
							{
								'mb-1 text-neutral-0': isMobileDevice,
								'text-neutral-7': !isMobileDevice,
							}
						)}
						onClick={onClose}
					>
						Continue Quote
					</button>

					<button
						className={classNames(
							'btn btn-primary rounded text-link-md text-small-caps',
							{
								'w-100': isMobileDevice,
							}
						)}
						onClick={onSendLinkAndExit}
					>
						Send Link &amp; Exit
					</button>
				</div>
			}
			onClose={onClose}
			show={show}
			size={isMobileDevice ? 'small-mobile' : 'medium'}
		>
			<div
				className={classNames(
					'align-items-center d-flex flex-column justify-content-between  progress-saved-content',
					{
						'mt-5': !isMobileDevice,
						'my-3': isMobileDevice,
					}
				)}
			>
				<div className="align-items-center d-flex flex-column progress-saved-body w-100">
					<div className="align-items-center bg-success d-flex flex-shrink-0 justify-content-center progress-saved-icon rounded-circle">
						<ClayIcon symbol="check" />
					</div>

					<h2 className="font-weight-bolder">
						Your progress is saved
					</h2>

					<div
						className={classNames(
							'font-weight-normal pt-1 text-center  text-paragraph',
							{
								'text-neutral-0': isMobileDevice,
								'text-neutral-8': !isMobileDevice,
							}
						)}
					>
						<p>
							We will send a link to&nbsp;<b>{email}</b>.
						</p>

						<p>
							Use the link to pick up where you left off at any
							time.
						</p>
					</div>
				</div>
			</div>
		</Modal>
	);
};

export default ProgressSaved;
