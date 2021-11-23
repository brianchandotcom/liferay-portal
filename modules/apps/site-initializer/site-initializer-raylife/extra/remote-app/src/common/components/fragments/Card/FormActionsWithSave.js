import ClayIcon from '@clayui/icon';
import React, {useState} from 'react';
import {useFormContext} from 'react-hook-form';
import ProgressSavedModal from '~/routes/get-a-quote/components/containers/Forms/Modal/ProgressSaved';

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
		}
		catch (error) {
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
					<button
						className="btn btn-flat"
						onClick={onPrevious}
						type="button"
					>
						Previous
					</button>
				)}

				<div>
					{onSave && (
						<button
							className="btn btn-outline"
							disabled={!email || emailHasError || loading}
							onClick={onClickSaveAndExit}
							type="button"
						>
							Save & Exit
						</button>
					)}

					{onNext && (
						<button
							className="btn btn-secondary continue"
							disabled={!isValid}
							onClick={onNext}
							type="submit"
						>
							Continue
							<ClayIcon symbol="angle-right" />
						</button>
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
