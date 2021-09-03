import ClayIcon from '@clayui/icon';
import React from 'react';
import {useFormContext} from 'react-hook-form';

import {WarningBadge} from '../Badges/Warning';

export const CardFormActionsWithSave = ({
	isValid = true,
	onNext,
	onPrevious,
	onSave,
}) => {
	const {
		formState: {errors},
	} = useFormContext();

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
							onClick={onSave}
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
			</div>
		</>
	);
};
