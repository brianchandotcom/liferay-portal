/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useMemo, useState} from 'react';
import {useForm} from 'react-hook-form';
import {useParams} from 'react-router-dom';

import Form from '../../../../../../components/Form';
import Modal from '../../../../../../components/Modal/index';
import SearchBuilder from '../../../../../../core/SearchBuilder';
import {withVisibleContent} from '../../../../../../hoc/withVisibleContent';
import {useFetch} from '../../../../../../hooks/useFetch';
import {FormModalOptions} from '../../../../../../hooks/useFormModal';
import i18n from '../../../../../../i18n';
import yupSchema, {yupResolver} from '../../../../../../schema/yup';
import {
	APIResponse,
	TestrayFactor,
	TestrayFactorOption,
	TestrayRun,
	testrayFactorCategoryRest,
	testrayFactorRest,
	testrayRunImpl,
} from '../../../../../../services/rest';

type RunForm = Omit<typeof yupSchema.run.__outputType, 'id'>;

type RunFormModalProps = {
	modal: FormModalOptions;
};

const RunFormModal: React.FC<RunFormModalProps> = ({
	modal: {
		modalState,
		observer,
		onClose,
		onError,
		onSave,
		onSubmit,
		submitting,
	},
}) => {
	const selectedRun: TestrayRun = modalState;

	const {
		formState: {errors},
		handleSubmit,
		register,
		setValue,
	} = useForm<RunForm>({
		defaultValues: selectedRun as any,
		resolver: yupResolver(yupSchema.factorToRun),
	});
	const {buildId, routineId} = useParams();
	const [factorOptionsList, setFactorOptionsList] = useState<
		TestrayFactorOption[][]
	>([[] as any]);

	const filter = selectedRun
		? SearchBuilder.eq('runId', selectedRun.id)
		: SearchBuilder.eq('routineId', routineId as string);

	const {data: factorsData} = useFetch<APIResponse<TestrayFactor>>(
		testrayFactorRest.resource,
		{
			params: {
				filter,
				pageSize: 1000,
			},
			transformData: (response) =>
				testrayFactorRest.transformDataFromList(response),
		}
	);

	const {data: runResponse} = useFetch<APIResponse<RunForm>>(
		selectedRun ? null : testrayRunImpl.resource,
		{
			params: {
				filter: SearchBuilder.eq('buildId', buildId as string),
				pageSize: 1000,
			},
		}
	);

	const getLastRunNumber = () => {
		const runs = runResponse?.items.map((item) => item.number) || [];

		return runs[runs?.length - 1];
	};

	const lastRunNumber = getLastRunNumber();

	const factorItems = useMemo(() => factorsData?.items || [], [
		factorsData?.items,
	]);

	const _onSubmit = (form: RunForm) => {
		const factorOptionIds = (
			((form as any).factorOptionIds as string[]) || []
		).map(Number);

		const runName = factorOptionsList
			.map(
				(factorOptions) =>
					factorOptions.find(({id}) => factorOptionIds.includes(id))
						?.name
			)
			.filter(Boolean)
			.join(' | ');

		const newRun = !(form as any)?.id;

		onSubmit(
			{
				...form,
				buildId: (buildId as unknown) as number,
				name: runName,
				number: (lastRunNumber ?? 0) + 1,
			},
			{
				create: (data) => testrayRunImpl.create(data),
				update: (id, data) => testrayRunImpl.update(id, data),
			}
		)
			.then((run) =>
				testrayFactorRest.selectEnvironmentFactor(
					factorItems,
					factorOptionIds,
					run.id,
					newRun
				)
			)
			.then(onSave)
			.catch(onError);
	};

	useEffect(() => {
		if (factorItems.length) {
			testrayFactorCategoryRest
				.getFactorCategoryItems(factorItems)
				.then(setFactorOptionsList);
		}
	}, [factorItems]);

	useEffect(() => {
		setValue(
			'factorOptionIds' as any,
			factorItems.map(({factorOption}) => String(factorOption?.id))
		);
	}, [factorItems, setValue]);

	return (
		<Modal
			last={
				<Form.Footer
					onClose={onClose}
					onSubmit={handleSubmit(_onSubmit)}
					primaryButtonProps={{loading: submitting}}
				/>
			}
			observer={observer}
			size="full-screen"
			title={i18n.translate('select-options')}
			visible
		>
			{factorItems.map((factorItem, index) => (
				<Form.Select
					defaultValue={factorItem.factorOption?.id}
					errors={errors}
					key={index}
					label={factorItem.factorCategory?.name}
					name={`factorOptionIds.${index}`}
					options={(factorOptionsList[index] || []).map(
						({id, name}) => ({
							label: name,
							value: id,
						})
					)}
					register={register}
				/>
			))}
		</Modal>
	);
};

export default withVisibleContent(RunFormModal);
