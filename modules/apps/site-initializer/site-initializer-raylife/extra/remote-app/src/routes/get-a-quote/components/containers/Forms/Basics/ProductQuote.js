import React, {useEffect} from 'react';
import {Controller, useFormContext} from 'react-hook-form';
import {MoreInfoButton} from '~/common/components/fragments/Buttons/MoreInfo';
import {CardFormActionsWithSave} from '~/common/components/fragments/Card/FormActionsWithSave';
import {Radio} from '~/common/components/fragments/Forms/Radio';
import {STORAGE_KEYS, Storage} from '~/common/services/liferay/storage';
import {TIP_EVENT} from '~/common/utils/events';
import useFormActions from '~/routes/get-a-quote/hooks/useFormActions';
import {useProductQuotes} from '~/routes/get-a-quote/hooks/useProductQuotes';
import {useStepWizard} from '~/routes/get-a-quote/hooks/useStepWizard';
import {useTriggerContext} from '~/routes/get-a-quote/hooks/useTriggerContext';
import {AVAILABLE_STEPS} from '~/routes/get-a-quote/utils/constants';

const getSelectedProductName = () => {
	try {
		return JSON.parse(Storage.getItem(STORAGE_KEYS.PRODUCT))?.productName;
	}
	catch (error) {
		return '';
	}
};

export const FormBasicProductQuote = ({form}) => {
	const {control, setValue} = useFormContext();
	const {selectedStep} = useStepWizard();
	const {productQuotes} = useProductQuotes();
	const {onNext, onPrevious, onSave} = useFormActions(
		form,
		AVAILABLE_STEPS.BASICS_BUSINESS_INFORMATION,
		AVAILABLE_STEPS.BUSINESS
	);

	const defaultProductId = productQuotes.find(
		({title}) => title === getSelectedProductName()
	)?.id;

	const {isSelected, updateState} = useTriggerContext();

	useEffect(() => {
		if (defaultProductId && !form.basics.productQuote) {
			setValue('basics.productQuote', defaultProductId);
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [defaultProductId, form.basics.productQuote]);

	return (
		<div className="card">
			<div className="card-content">
				<div className="content-column">
					<label>Select a product to quote.</label>

					<fieldset className="content-column" id="productQuote">
						<Controller
							control={control}
							defaultValue={defaultProductId}
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
