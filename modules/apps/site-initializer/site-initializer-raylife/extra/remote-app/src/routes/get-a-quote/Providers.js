import React, {useEffect, useState} from 'react';
import {FormProvider, useForm} from 'react-hook-form';
import {Template} from '~/common/components/Template';
import ClayIconProvider from '~/common/context/ClayIconProvider';
import {LiferayAdapt} from '~/common/services/liferay/adapter';
import {STORAGE_KEYS, Storage} from '~/common/services/liferay/storage';
import {AppProvider} from '~/routes/get-a-quote/context/AppContext';
import {getRaylifeApplicationById} from './services/RaylifeApplication';
import {getApplicationIdSearchParam} from './utils/util';

const Providers = ({children, initialValues}) => {
	const form = useForm({defaultValues: initialValues, mode: 'onChange'});

	return (
		<AppProvider>
			<ClayIconProvider>
				<FormProvider {...form}>
					<Template>{children}</Template>
				</FormProvider>
			</ClayIconProvider>
		</AppProvider>
	);
};

const InitProvider = ({children}) => {
	const [initialValues, setInitialValues] = useState({});
	const [loading, setLoading] = useState(false);

	const getInitialData = async () => {
		setLoading(true);

		const applicationId = getApplicationIdSearchParam();
		let initialData = {};

		try {
			if (applicationId) {
				const {data} = await getRaylifeApplicationById(applicationId);

				initialData = LiferayAdapt.adaptToRaylifeApplicationToForm(
					data
				);
			} else if (Storage.itemExist(STORAGE_KEYS.BACK_TO_EDIT)) {
				initialData = JSON.parse(
					Storage.getItem(STORAGE_KEYS.APPLICATION_FORM)
				);
			}
		} catch (error) {
			console.warn(error.message);
		}

		setInitialValues(initialData);
		setLoading(false);
	};

	useEffect(() => {
		getInitialData();
	}, []);

	if (loading) {
		return null;
	}

	return <Providers initialValues={initialValues}>{children}</Providers>;
};

export default InitProvider;
