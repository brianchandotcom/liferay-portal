import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import React, {useState} from 'react';
import {useFormContext} from 'react-hook-form';
import ProgressSavedModal from '../../../../routes/get-a-quote/components/containers/Forms/Modal/ProgressSaved';

import {WarningBadge} from '../Badges/Warning';

export function CardFormActionsWithSave({
	isValid = true,
	onNext,
	onPrevious,
	onSave,
}) {
	const {
		formState: {errors},
		getValues,
	} = useFormContext();

	const email = getValues('basics.businessInformation.business.email');
	const emailHasError = !!errors?.basics?.businessInformation?.business
		?.email;

	const [showProgressModal, setShowProgressModal] = useState(false);
	const [loading, setLoading] = useState(false);

	const onClickSaveAndExit = async () => {
		setLoading(true);

		try {
			await onSave();
			setShowProgressModal(true);
		} catch (error) {
			alert('Unable to save your information. Please try again');
		}

		setLoading(false);
	};

	return (
		<>
			{errors?.continueButton?.message && (
				<WarningBadge>{errors?.continueButton?.message}</WarningBadge>
			)}
			<div className="card-actions">
				{onPrevious && (
					<ClayButton
						className="btn btn-borderless font-weight-bolder previous text-paragraph text-small-caps"
						displayType="null"
						onClick={onPrevious}
					>
						Previous
					</ClayButton>
				)}

				<div>
					{onSave && (
						<ClayButton
							className="font-weight-bolder save-exit text-paragraph text-small-caps"
							disabled={!email || emailHasError || loading}
							displayType="secondary"
							onClick={onClickSaveAndExit}
						>
							Save & Exit
						</ClayButton>
					)}

					{onNext && (
						<ClayButton
							className="btn btn-solid btn-variant-secondary continue font-weight-bolder text-paragraph text-small-caps"
							disabled={!isValid}
							onClick={onNext}
						>
							Continue
							<span className="inline-item inline-item-before">
								<ClayIcon symbol="angle-right" />
							</span>
						</ClayButton>
					)}
				</div>

				<ProgressSavedModal
					email={email}
					onClose={() => setShowProgressModal(false)}
					show={showProgressModal}
				/>
			</div>
		</>
	);
}
