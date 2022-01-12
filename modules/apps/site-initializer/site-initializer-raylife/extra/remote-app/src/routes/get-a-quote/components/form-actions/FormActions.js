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
import classNames from 'classnames';
import React, {useContext, useState} from 'react';
import {useFormContext} from 'react-hook-form';
import {WarningBadge} from '../../../../common/components/fragments/Badges/Warning';
import {DEVICES} from '../../../../common/utils/constants';
import {AppContext} from '../../context/AppContextProvider';

import ProgressSavedModal from '../containers/Forms/Modal/ProgressSaved';

export function CardFormActions({isValid = true, onNext, onPrevious, onSave}) {
	const {
		formState: {errors},
		getValues,
		setValue,
	} = useFormContext();
	const {state} = useContext(AppContext);

	const isMobileDevice = state.dimensions.deviceSize === DEVICES.PHONE;
	const productQuote = getValues('basics.productQuoteName');
	const email = getValues('basics.businessInformation.business.email');
	const emailHasError = !!errors?.basics?.businessInformation?.business
		?.email;

	const [showProgressModal, setShowProgressModal] = useState(false);
	const [loading, setLoading] = useState(false);
	const [errorModal, setErrorModal] = useState();

	const onClickSaveAndExit = async () => {
		setLoading(true);

		try {
			await onSave();
			setShowProgressModal(true);
		}
		catch (error) {
			console.error(error);
		}

		setLoading(false);
	};

	return (
		<>
			{(errors?.continueButton?.message || errorModal) &&
				!isMobileDevice && (
					<WarningBadge>
						{errors?.continueButton?.message || errorModal}
					</WarningBadge>
				)}
			<div
				className={classNames('d-flex justify-content-between', {
					'mt-5': !isMobileDevice,
				})}
			>
				{!isMobileDevice && onPrevious && (
					<ClayButton
						className="btn-borderless btn-style-neutral font-weight-bolder previous text-paragraph text-small-caps"
						displayType="null"
						onClick={onPrevious}
					>
						Previous
					</ClayButton>
				)}

				<div
					className={classNames('d-flex', {'w-100': isMobileDevice})}
				>
					{!isMobileDevice && onSave && (
						<ClayButton
							className="font-weight-bolder mr-3 save-exit text-paragraph text-small-caps"
							disabled={!email || emailHasError || loading}
							displayType="secondary"
							onClick={onClickSaveAndExit}
						>
							Save & Exit
						</ClayButton>
					)}

					{onNext && (
						<ClayButton
							className={classNames(
								'btn-solid btn-style-secondary continue font-weight-bolder text-paragraph text-small-caps',
								{'w-100': isMobileDevice}
							)}
							disabled={!isValid}
							onClick={onNext}
						>
							Continue
							<span className="inline-item inline-item-before ml-1">
								<ClayIcon symbol="angle-right" />
							</span>
						</ClayButton>
					)}
				</div>

				<ProgressSavedModal
					email={email}
					onClose={() => {
						setShowProgressModal(false);
						setValue(
							'basics.businessInformation.business.email',
							email,
							{
								shouldValidate: true,
							}
						);
					}}
					productQuote={productQuote}
					setError={(message) => setErrorModal(message)}
					show={showProgressModal}
				/>
			</div>
		</>
	);
}
