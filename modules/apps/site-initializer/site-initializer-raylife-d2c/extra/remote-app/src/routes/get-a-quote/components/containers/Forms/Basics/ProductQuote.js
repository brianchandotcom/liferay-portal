/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLoadingIndicator from '@clayui/loading-indicator';
import classNames from 'classnames';
import React, {useCallback, useContext, useEffect} from 'react';
import {Controller, useFormContext} from 'react-hook-form';
import {MoreInfoButton} from '../../../../../../common/components/fragments/Buttons/MoreInfo';
import {Radio} from '../../../../../../common/components/fragments/Forms/Radio';

import {TIP_EVENT} from '../../../../../../common/utils/events';
import {AppContext} from '../../../../context/AppContextProvider';
import {useProductQuotes} from '../../../../hooks/useProductQuotes';
import {useTriggerContext} from '../../../../hooks/useTriggerContext';

export function FormBasicProductQuote({form}) {
	const {productQuotes} = useProductQuotes();
	const {control, setValue} = useFormContext();
	const productQuoteId = form?.basics?.productId;
	const {
		state: {
			dimensions: {
				device: {isMobile},
			},
		},
	} = useContext(AppContext);

	useEffect(() => {
		if (productQuoteId && productQuotes.length) {
			const productQuote = productQuotes.find(
				({id}) => id === productQuoteId
			);
			setValue('basics.productName', productQuote.title);
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [productQuoteId, productQuotes]);

	const forceValidation = useCallback(() => {
		setValue('basics.productId', productQuoteId, {
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
			<div className="col-12 d-flex flex-wrap p-0">
				<div
					className={classNames('mb-4 d-flex col-12', {
						'd-flex justify-content-start': !isMobile,
						'justify-content-sm-center justify-content-center': isMobile,
					})}
				>
					<label
						className={classNames('d-flex font-weight-bolder', {
							'text-paragraph justify-content-start': !isMobile,
							'text-paragraph-lg justify-content-sm-center justify-content-center': isMobile,
						})}
					>
						Select a product to quote.
					</label>
				</div>

				<fieldset
					className="d-flex flex-column mb-4 spacer-3 w-100"
					id="productQuote"
				>
					{!productQuotes.length && (
						<div className="align-items-center d-flex justify-content-center mt-5">
							<ClayLoadingIndicator
								displayType="primary"
								shape="squares"
								size="sm"
							/>
						</div>
					)}

					{!!productQuotes.length && (
						<Controller
							control={control}
							defaultValue={form?.basics?.productId}
							name="basics.productId"
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
														templateName:
															quote.template.name,
														value: quote.id,
													}}
												/>
											)
										}
										selected={
											quote.id === form?.basics?.productId
										}
										sideLabel={quote.period}
										value={quote.id}
									/>
								))
							}
							rules={{required: true}}
						/>
					)}
				</fieldset>
			</div>
		</div>
	);
}
