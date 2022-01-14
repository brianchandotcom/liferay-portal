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

import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import Modal from '../../../../../../common/components/modal';
import {
	STORAGE_KEYS,
	Storage,
} from '../../../../../../common/services/liferay/storage';
import {clearExitAlert} from '../../../../../../common/utils/exitAlert';
import {getLiferaySiteName} from '../../../../../../common/utils/liferay';
import {createQuoteRetrieve} from '../../../../services/QuoteRetrieve';

const liferaySiteName = getLiferaySiteName();

const ProgressSaved = ({
	email,
	isMobileDevice = false,
	onClose,
	productQuote,
	setError,
	show,
}) => {
	const onSendLinkAndExit = async () => {
		try {
			const applicationId = Storage.getItem(STORAGE_KEYS.APPLICATION_ID);

			await createQuoteRetrieve({
				productName: productQuote,
				quoteRetrieveLink: `${origin}${liferaySiteName}/get-a-quote?applicationId=${applicationId}`,
				retrieveEmail: email,
			});

			clearExitAlert();

			window.location.href = liferaySiteName;
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
