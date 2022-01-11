import ClayButton from '@clayui/button';
import React, {useContext, useState} from 'react';
import {useFormContext} from 'react-hook-form';
import ProgressSavedModal from '../../../../routes/get-a-quote/components/containers/Forms/Modal/ProgressSaved';
import {AppContext} from '../../../../routes/get-a-quote/context/AppContextProvider';
import {DEVICES} from '../../../utils/constants';

import {WarningBadge} from '../Badges/Warning';

export function CardFormActionsMobile({onPrevious, onSave}) {
	const {
		formState: {errors},
		getValues,
		setValue,
	} = useFormContext();

	const {
		state: {dimensions},
	} = useContext(AppContext);

	const isMobileDevice = dimensions.deviceSize === DEVICES.PHONE;

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
		} catch (error) {
			console.error(error);
		}

		setLoading(false);
	};

	return (
		<>
			{(errors?.continueButton?.message || errorModal) && (
				<WarningBadge>
					{errors?.continueButton?.message || errorModal}
				</WarningBadge>
			)}
			<div className="col-12 d-flex justify-content-between mt-5">
				{onPrevious && isMobileDevice && (
					<ClayButton
						className="btn-borderless btn-style-neutral font-weight-bolder previous text-neutral-0 text-paragraph text-small-caps"
						displayType="null"
						onClick={onPrevious}
					>
						Previous
					</ClayButton>
				)}

				<div className="d-flex">
					{onSave && isMobileDevice && (
						<ClayButton
							className="btn btn-ghost btn-inverted btn-style-neutral font-weight-bolder mr-3 save-exit text-neutral-0 text-paragraph text-small-caps"
							disabled={!email || emailHasError || loading}
							displayType={null}
							onClick={onClickSaveAndExit}
						>
							Save & Exit
						</ClayButton>
					)}
				</div>

				<ProgressSavedModal
					email={email}
					isMobileDevice={true}
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
