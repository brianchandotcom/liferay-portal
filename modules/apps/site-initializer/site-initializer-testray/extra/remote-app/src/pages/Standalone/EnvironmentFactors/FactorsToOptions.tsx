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

import {useEffect, useMemo, useState} from 'react';
import {UseFormRegister} from 'react-hook-form';

import Form from '../../../components/Form';
import yupSchema from '../../../schema/yup';
import {TestrayFactor, TestrayFactorOption} from '../../../services/rest';
import {testrayFactorCategoryRest} from '../../../services/rest/TestrayFactorCategory';

type FactorOptionForm = typeof yupSchema.enviroment.__outputType;

type FactorsToOptionsProps = {
	factors: TestrayFactor[];
	register: UseFormRegister<FactorOptionForm>;
	selectedEnvironmentFactors: {label: string; value: number}[];
	setValue: any;
	shouldRequestCategories: boolean;
};

const FactorsToOptions: React.FC<FactorsToOptionsProps> = ({
	factors,
	register,
	selectedEnvironmentFactors,
	setValue,
	shouldRequestCategories,
}) => {
	const [factorOptionsList, setFactorOptionsList] = useState<
		TestrayFactorOption[][]
	>([[] as any]);

	const factorOptionIds = useMemo(
		() => factors.map((factor) => factor.factorOption?.id),
		[factors]
	);

	useEffect(() => {
		if (shouldRequestCategories) {
			testrayFactorCategoryRest
				.getFactorCategoryItems(
					selectedEnvironmentFactors.map(({value}) => ({
						factorCategory: {id: value},
					})) as TestrayFactor[]
				)
				.then((factorOptionsList) => {
					const factorOptionIdsWithDefault = factorOptionsList.map(
						(factorOptionList, index) =>
							factorOptionIds[index] ??
							(factorOptionList || [])[0]?.id
					);

					setFactorOptionsList(factorOptionsList);
					setValue('factorOptionIds', factorOptionIdsWithDefault);
				});
		}
	}, [
		selectedEnvironmentFactors,
		setValue,
		factorOptionIds,
		shouldRequestCategories,
	]);

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
						defaultOption={false}
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
