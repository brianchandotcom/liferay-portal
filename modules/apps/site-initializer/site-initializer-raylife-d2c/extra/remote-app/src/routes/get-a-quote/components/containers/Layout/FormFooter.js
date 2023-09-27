/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import {useFormContext} from 'react-hook-form';
import {WarningBadge} from '../../../../../common/components/fragments/Badges/Warning';
import {ActionDesktop} from '../../form-actions/ActionDesktop';
import {ActionMobile} from '../../form-actions/ActionMobile';

const WarningMessage = ({displayError, isMobileDevice}) => {
	if (!displayError) {
		return null;
	}

	const WarningBadgeWrapper = () => (
		<WarningBadge>{displayError}</WarningBadge>
	);

	if (isMobileDevice) {
		return (
			<div className="mb-5">
				<WarningBadgeWrapper />
			</div>
		);
	}

	return <WarningBadgeWrapper />;
};

const FormFooterDesktop = ({
	formActionContext: {actionProps, errorModal, isMobileDevice, isValid},
}) => {
	const {
		formState: {errors},
	} = useFormContext();

	return (
		<div
			className={classNames({
				'col-12 mt-5 px-0': isMobileDevice,
				'hide': !actionProps.showContinueButton && isMobileDevice,
			})}
		>
			<WarningMessage
				displayError={
					errors?.continueButton?.message ||
					errorModal ||
					errors?.applicationObject?.message
				}
				isMobileDevice={isMobileDevice}
			/>

			<ActionDesktop
				{...actionProps}
				isMobileDevice={isMobileDevice}
				isValid={isValid}
			/>
		</div>
	);
};

const FormFooterMobile = ({
	formActionContext: {actionProps, isMobileDevice},
}) => (
	<div className="col-12 mt-5">
		{isMobileDevice && <ActionMobile {...actionProps} />}
	</div>
);

export {FormFooterDesktop, FormFooterMobile};
