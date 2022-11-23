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
import PropTypes from 'prop-types';
import React from 'react';

import useFlags from '../hooks/useFlags.es';
import FlagsModal from './FlagsModal.es';

const Flags = ({
	namespace,
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
	const {
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
		status,
	} = useFlags({baseData, forceLogin, namespace, reasons, signedIn, uri});

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
					form={form}
					handleClose={onClose}
					handleInputChange={handleInputChange}
					handleSubmit={handleSubmitReport}
					isSending={isSending}
					namespace={namespace}
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
	namespace: PropTypes.string,
	onlyIcon: PropTypes.bool,
	pathTermsOfUse: PropTypes.string.isRequired,
	reasons: PropTypes.object.isRequired,
	showIcon: PropTypes.bool,
	signedIn: PropTypes.bool,
	uri: PropTypes.string.isRequired,
};

export default Flags;
