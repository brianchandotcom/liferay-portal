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

import classNames from 'classnames';
import React, {useCallback, useContext, useEffect} from 'react';
import {Controller, useFormContext} from 'react-hook-form';
import {MoreInfoButton} from '../../../../../../common/components/fragments/Buttons/MoreInfo';
import {Radio} from '../../../../../../common/components/fragments/Forms/Radio';
import {DEVICES} from '../../../../../../common/utils/constants';

import {TIP_EVENT} from '../../../../../../common/utils/events';
import {AppContext} from '../../../../context/AppContextProvider';
import {useProductQuotes} from '../../../../hooks/useProductQuotes';
import {useTriggerContext} from '../../../../hooks/useTriggerContext';

export function FormBasicProductQuote({form}) {
	const {productQuotes} = useProductQuotes();
	const {control, setValue} = useFormContext();
	const productQuoteId = form?.basics?.productQuote;
	const {
		state: {dimensions},
	} = useContext(AppContext);

	const isMobileDevice = dimensions.deviceSize === DEVICES.PHONE;

	useEffect(() => {
		if (productQuoteId && productQuotes.length) {
			const productQuote = productQuotes.find(
				({id}) => id === productQuoteId
			);
			setValue('basics.productQuoteName', productQuote.title);
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [productQuoteId, productQuotes]);

	const forceValidation = useCallback(() => {
		setValue('basics.productQuote', productQuoteId, {
			shouldValidate: true,
		});
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	useEffect(() => {
		forceValidation();
	}, [forceValidation]);

	const {isSelected, updateState} = useTriggerContext();

	return (
		<div className="card-content d-flex">
			<div className="ccol-12 d-flex flex-wrap p-0">
				<div
					className={classNames('mb-4 d-flex col-12', {
						'd-flex justify-content-start': !isMobileDevice,
						'justify-content-sm-center justify-content-center': isMobileDevice,
					})}
				>
					<label
						className={classNames('d-flex font-weight-bolder', {
							'text-paragraph justify-content-start': !isMobileDevice,
							'text-paragraph-lg justify-content-sm-center justify-content-center': isMobileDevice,
						})}
					>
						Select a product to quote.
					</label>
				</div>

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
												selected={isSelected(quote.id)}
												value={{
													inputName: field.name,
													templateName:
														quote.template.name,
													value: quote.id,
												}}
											/>
										)
									}
									selected={
										quote.id === form?.basics?.productQuote
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
	);
}
