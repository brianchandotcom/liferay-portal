/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
						forceSelectOption
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
