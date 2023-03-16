/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {useEffect, useState} from 'react';

import LiferayAccountBrief from '../interfaces/liferayAccountBrief';
import LiferayPicklist from '../interfaces/liferayPicklist';
import useGetCompanyExtenderByAccountEntryId from '../services/liferay/object/company-extenders/useGetCompanyExtenderByAccountEntryId';
import isObjectEmpty from '../utils/isObjectEmpty';

export default function useCompanyOptions(
	companyOptions: React.OptionHTMLAttributes<HTMLOptionElement>[],
	handleSelected: (
		country: LiferayPicklist,
		company: LiferayAccountBrief,
		currency: LiferayPicklist,
		accountExternalReferenceCodeSF?: string
	) => void,
	currentCompany?: LiferayAccountBrief,
	currentCountry?: LiferayPicklist,
	currentCurrency?: LiferayPicklist
) {
	const [selectedAccountBrief, setSelectedAccountBrief] = useState<
		LiferayAccountBrief | undefined
	>(currentCompany);

	const {data: companyExtender} = useGetCompanyExtenderByAccountEntryId(
		selectedAccountBrief?.id
	);

	useEffect(() => {
		if (
			companyExtender &&
			!isObjectEmpty(selectedAccountBrief) &&
			selectedAccountBrief
		) {
			handleSelected(
				currentCountry && !isObjectEmpty(currentCountry)
					? currentCountry
					: companyExtender.country,
				selectedAccountBrief,
				currentCurrency && !isObjectEmpty(currentCurrency)
					? currentCurrency
					: companyExtender.currency,
				companyExtender?.accountExternalReferenceCodeSF
			);
		}
	}, [
		companyExtender,
		currentCountry,
		currentCurrency,
		handleSelected,
		selectedAccountBrief,
	]);

	const onCompanySelected = (event: React.ChangeEvent<HTMLInputElement>) => {
		const optionSelected = companyOptions.find(
			(options) => options.value === +event.target.value
		);

		setSelectedAccountBrief({
			id: optionSelected?.value as number,
			name: optionSelected?.label as string,
		});
	};

	return {
		companyOptions,
		onCompanySelected,
	};
}
