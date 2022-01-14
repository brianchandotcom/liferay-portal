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
				'col-12 mt-5': isMobileDevice,
			})}
		>
			<WarningMessage
				displayError={errors?.continueButton?.message || errorModal}
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
