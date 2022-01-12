import classNames from 'classnames';
import React, {useContext, useState} from 'react';
import {useFormContext} from 'react-hook-form';
import {WarningBadge} from '../../../../common/components/fragments/Badges/Warning';
import {DEVICES} from '../../../../common/utils/constants';
import {AppContext} from '../../context/AppContextProvider';

import ProgressSavedModal from '../containers/Forms/Modal/ProgressSaved';
import {ActionDesktop} from './ActionDesktop';
import {ActionMobile} from './ActionMobile';

const WarningMessage = ({displayError, isMobileDevice}) => {
	const WarningBadgeWrapper = () => (
		<WarningBadge>{displayError}</WarningBadge>
	);

	if (!displayError) {
		return null;
	}

	if (isMobileDevice) {
		return (
			<div className="mb-5">
				<WarningBadgeWrapper />
			</div>
		);
	}

	return <WarningBadgeWrapper />;
};

export function CardFormActions({
	isValid = true,
	onNext,
	onPrevious,
	onSave,
	rodape,
}) {
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
			if (onSave) {
				await onSave();
			}

			setShowProgressModal(true);
		} catch (error) {
			console.error(error);
		}

		setLoading(false);
	};

	const actionProps = {
		onClickSaveAndExit,
		onNext,
		onPrevious,
		onSave,
		onSaveDisabled: !email || emailHasError || loading,
	};

	return (
		<>
			<div className={classNames({'col-12 mt-5': isMobileDevice})}>
				<WarningMessage
					displayError={errors?.continueButton?.message || errorModal}
					isMobileDevice={isMobileDevice}
				/>

				{isMobileDevice && <ActionMobile {...actionProps} />}

				{!rodape && (
					<ActionDesktop
						{...actionProps}
						isMobileDevice={isMobileDevice}
						isValid={isValid}
					/>
				)}
			</div>

			<ProgressSavedModal
				email={email}
				isMobileDevice={isMobileDevice}
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
		</>
	);
}
