/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useEffect} from 'react';
import {Controller, useFormContext} from 'react-hook-form';
import {Radio} from '../../../../../../../common/components/fragments/Forms/Radio';

const BusinessTypeRadioGroup = ({
	businessTypes = [],
	form,
	setNewSelectedProduct,
}) => {
	const {control, setValue} = useFormContext();

	const selectedBusinessType = businessTypes.find(
		({id}) => form?.basics?.businessCategoryId === id
	);

	useEffect(() => {
		if (form?.basics?.businessCategoryId) {
			setCategoryProperties();
			setValue('basics.productCategory', selectedBusinessType?.name);
			setNewSelectedProduct(form.basics.businessCategoryId);
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [form?.basics?.businessCategoryId, selectedBusinessType]);

	const setCategoryProperties = () => {
		const categoryProperties =
			selectedBusinessType?.taxonomyCategoryProperties || [];

		if (categoryProperties && categoryProperties.length) {
			setValue(
				'basics.properties.businessClassCode',
				categoryProperties.find(({key}) => key === 'BCC')?.value
			);
			setValue(
				'basics.properties.naics',
				categoryProperties.find(({key}) => key === 'NAICS')?.value
			);
			setValue(
				'basics.properties.segment',
				categoryProperties.find(({key}) => key === 'Segment')?.value
			);
		}
	};

	return (
		<fieldset id="businessType">
			<Controller
				control={control}
				defaultValue=""
				name="basics.businessCategoryId"
				render={({field}) =>
					businessTypes.map((businessType) => (
						<Radio
							{...field}
							description={businessType.description}
							key={businessType.id}
							label={businessType.name}
							selected={
								businessType.id ===
								form?.basics?.businessCategoryId
							}
							value={businessType.id}
						/>
					))
				}
				rules={{required: 'Please, Select a field.'}}
			/>
		</fieldset>
	);
};

export default BusinessTypeRadioGroup;
