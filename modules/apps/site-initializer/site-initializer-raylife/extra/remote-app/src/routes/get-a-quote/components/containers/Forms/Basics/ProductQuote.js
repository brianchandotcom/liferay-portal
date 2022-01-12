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

import React, {useContext, useEffect} from 'react';
import {Controller, useFormContext} from 'react-hook-form';
import {MoreInfoButton} from '../../../../../../common/components/fragments/Buttons/MoreInfo';
import {Radio} from '../../../../../../common/components/fragments/Forms/Radio';
import {
	STORAGE_KEYS,
	Storage,
} from '../../../../../../common/services/liferay/storage';
import {DEVICES} from '../../../../../../common/utils/constants';
import {TIP_EVENT} from '../../../../../../common/utils/events';
import {clearExitAlert} from '../../../../../../common/utils/exitAlert';
import {getLiferaySiteName} from '../../../../../../common/utils/liferay';
import {smoothScroll} from '../../../../../../common/utils/scroll';
import {AppContext} from '../../../../context/AppContextProvider';
import {useProductQuotes} from '../../../../hooks/useProductQuotes';
import {useStepWizard} from '../../../../hooks/useStepWizard';
import {useTriggerContext} from '../../../../hooks/useTriggerContext';
import {AVAILABLE_STEPS} from '../../../../utils/constants';
import FormCard from '../../../card/FormCard';
import {CardFormActions} from '../../../form-actions/FormActions';
import {CardFormActionsMobile} from '../../../form-actions/FormActionsMobile';

export function FormBasicProductQuote({form}) {
	const {control, setValue} = useFormContext();
	const {selectedStep, setSection} = useStepWizard();
	const {productQuotes} = useProductQuotes();

	useEffect(() => {
		const productQuoteId = form?.basics?.productQuote;

		if (productQuotes.length && productQuoteId) {
			const productQuote = productQuotes.find(
				({id}) => id === productQuoteId
			);
			setValue('basics.productQuoteName', productQuote.title);
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [form?.basics?.productQuote, productQuotes]);

	const goToPreviousPage = () => {
		clearExitAlert();

		window.location.href = getLiferaySiteName();

		if (Storage.itemExist(STORAGE_KEYS.BACK_TO_EDIT)) {
			Storage.removeItem(STORAGE_KEYS.BACK_TO_EDIT);
		}
	};

	const onNext = () => {
		setSection(AVAILABLE_STEPS.BASICS_BUSINESS_TYPE);

		smoothScroll();
	};

	const {isSelected, updateState} = useTriggerContext();

	return (
		<FormCard
			footer={<CardFormActionsMobile onPrevious={goToPreviousPage} />}
		>
			<div className="card-content d-flex">
				<div className="content-column">
					<label className="mb-3">
						<h6 className="font-weight-bolder text-paragraph">
							Select a product to quote.
						</h6>
					</label>

					<fieldset
						className="d-flex flex-column mb-4 spacer-3"
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
											form?.basics?.productQuote
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

			<CardFormActions
				isValid={!!form?.basics?.productQuote}
				onNext={onNext}
				onPrevious={goToPreviousPage}
			/>
		</FormCard>
	);
}
