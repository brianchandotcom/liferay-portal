import React from 'react';
import {Controller, useFormContext} from 'react-hook-form';

import {TIP_EVENT} from '../../../../events';
import useFormActions from '../../../../hooks/useFormActions';
import {useProductQuotes} from '../../../../hooks/useProductQuotes';
import {useStepWizard} from '../../../../hooks/useStepWizard';
import {useTriggerContext} from '../../../../hooks/useTriggerContext';
import {AVAILABLE_STEPS} from '../../../../utils/constants';
import {MoreInfoButton} from '../../../fragments/Buttons/MoreInfo';
import {CardFormActionsWithSave} from '../../../fragments/Card/FormActionsWithSave';
import {Radio} from '../../../fragments/Forms/Radio';

export const FormBasicProductQuote = ({form}) => {
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
		<div className="card">
			<div className="card-content">
				<div className="content-column">
					<label>Select a product to quote.</label>
					<fieldset className="content-column" id="productQuote">
						<Controller
							control={control}
							defaultValue=""
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
		</div>
	);
};
