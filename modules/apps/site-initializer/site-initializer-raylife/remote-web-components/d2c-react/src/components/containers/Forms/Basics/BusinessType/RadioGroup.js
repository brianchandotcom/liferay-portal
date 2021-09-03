/* eslint-disable react-hooks/exhaustive-deps */
import React, {useEffect} from 'react';
import {Controller, useFormContext} from 'react-hook-form';

import {LiferayService} from '../../../../../services/liferay';
import {Radio} from '../../../../fragments/Forms/Radio';

export const BusinessTypeRadioGroup = ({businessTypes = [], form}) => {
	const {control, setValue} = useFormContext();

	useEffect(() => {
		if (form?.basics?.businessCategoryId) {
			setCategoryProperties();
		}
	}, [form?.basics?.businessCategoryId]);

	const setCategoryProperties = async () => {
		try {
			const categoryId = form.basics.businessCategoryId;

			const categoryProperties =
				await LiferayService.getCategoryProperties(categoryId);

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
		catch (error) {
			console.warn(error);
		}
	};

	return (
		<fieldset className="content-column" id="businessType">
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
							label={businessType.title}
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
