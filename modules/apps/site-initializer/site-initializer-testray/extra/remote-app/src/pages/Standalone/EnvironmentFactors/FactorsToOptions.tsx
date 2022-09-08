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

import {useEffect, useState} from 'react';
import {UseFormRegister} from 'react-hook-form';

import Form from '../../../components/Form';
import yupSchema from '../../../schema/yup';
import {TestrayFactor} from '../../../services/rest';
import {testrayFactorCategoryRest} from '../../../services/rest/TestrayFactorCategory';

type FactorOptionForm = typeof yupSchema.enviroment.__outputType;

type FactorsToOptionsProps = {
	factors: TestrayFactor[];
	register: UseFormRegister<FactorOptionForm>;
	selectedEnvironmentFactors: {label: string; value: number}[];
	setValue: any;
};

const FactorsToOptions: React.FC<FactorsToOptionsProps> = ({
	factors,
	register,
	selectedEnvironmentFactors,
	setValue,
}) => {
	const [factorOptionsList, setFactorOptionsList] = useState<
		TestrayFactor[][]
	>([[] as any]);

	useEffect(() => {
		const factorOptionIds = factors.map(
			(factor) => factor.factorOption?.id
		);

		setValue('factorOptionIds', factorOptionIds);
	}, [factors, setValue]);

	useEffect(() => {
		testrayFactorCategoryRest
			.getFactorCategoryItems(
				selectedEnvironmentFactors.map(({value}) => ({
					factorCategory: {id: value},
				})) as TestrayFactor[]
			)
			.then(setFactorOptionsList);
	}, [selectedEnvironmentFactors]);

	return (
		<>
			{selectedEnvironmentFactors.map((environmentFactor, index) => {
				const defaultValue = factors.find(
					({factorCategory}) =>
						factorCategory?.id === Number(environmentFactor.value)
				)?.factorOption?.id;

				const options = (factorOptionsList[index] || []).map(
					({id, name}: any) => ({
						label: name,
						value: id,
					})
				);

				return (
					<Form.Select
						defaultValue={defaultValue}
						key={index}
						label={environmentFactor.label}
						name={`factorOptionIds.${index}`}
						options={options}
						register={register}
						required={!!options.length}
					/>
				);
			})}
		</>
	);
};

export default FactorsToOptions;
