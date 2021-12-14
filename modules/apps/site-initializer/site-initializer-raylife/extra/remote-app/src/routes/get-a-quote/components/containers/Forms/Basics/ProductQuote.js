import React from 'react';
import {Controller, useFormContext} from 'react-hook-form';
import {MoreInfoButton} from '../../../../../../common/components/fragments/Buttons/MoreInfo';
import {CardFormActionsWithSave} from '../../../../../../common/components/fragments/Card/FormActionsWithSave';
import FormCard from '../../../../../../common/components/fragments/Card/FormCard';
import {Radio} from '../../../../../../common/components/fragments/Forms/Radio';
import {TIP_EVENT} from '../../../../../../common/utils/events';
import useFormActions from '../../../../hooks/useFormActions';
import {useProductQuotes} from '../../../../hooks/useProductQuotes';
import {useStepWizard} from '../../../../hooks/useStepWizard';
import {useTriggerContext} from '../../../../hooks/useTriggerContext';
import {AVAILABLE_STEPS} from '../../../../utils/constants';

export function FormBasicProductQuote({form}) {
	const {control} = useFormContext();
	const {selectedStep} = useStepWizard();
	const {productQuotes} = useProductQuotes();
	const {onNext, onPrevious, onSave} = useFormActions(
		form,
		AVAILABLE_STEPS.BASICS_BUSINESS_INFORMATION,
		AVAILABLE_STEPS.BUSINESS
	);

	const {isSelected, updateState} = useTriggerContext();

	return (
		<FormCard>
			<div className="card-content d-flex">
				<div className="content-column">
					<label className="mb-3">
						<h6 className="font-weight-bolder text-paragraph">
							Select a product to quote.
						</h6>
					</label>

					<fieldset
						className="d-flex flex-column spacer-3"
						id="productQuote"
					>
						<Controller
							control={control}
							defaultValue={form?.basics?.productQuote}
							name="basics.productQuote"
							render={({field}) =>
								productQuotes.map((quote) => (
									<Radio
										{...field}
										description={quote.description}
										key={quote.id}
										label={quote.title}
										renderActions={
											quote.template.allowed && (
												<MoreInfoButton
													callback={() =>
														updateState(quote.id)
													}
													event={TIP_EVENT}
													selected={isSelected(
														quote.id
													)}
													value={{
														inputName: field.name,
														step: selectedStep,
														templateName:
															quote.template.name,
														value: quote.id,
													}}
												/>
											)
										}
										selected={
											quote.id ===
											form.basics.productQuote
										}
										sideLabel={quote.period}
										value={quote.id}
									/>
								))
							}
							rules={{required: true}}
						/>
					</fieldset>
				</div>
			</div>

			<CardFormActionsWithSave
				isValid={!!form.basics.productQuote}
				onNext={onNext}
				onPrevious={onPrevious}
				onSave={onSave}
			/>
		</FormCard>
	);
}
