import React from 'react';
import {FormProvider, useForm} from 'react-hook-form';
import {Template} from '~/common/components/Template';
import ClayIconProvider from '~/common/context/ClayIconProvider';
import {STORAGE_KEYS, Storage} from '~/common/services/liferay/storage';
import {AppProvider} from '~/routes/get-a-quote/context/AppContext';

const getDefaultValues = () => {
	try {
		let data = '';

		if (Storage.itemExist(STORAGE_KEYS.BACK_TO_EDIT)) {
			data = JSON.parse(Storage.getItem(STORAGE_KEYS.APPLICATION_FORM));
		}

		return data;
	} catch (error) {
		console.warn(error.message);

		return {};
	}
};

export const Providers = ({children}) => {
	const defaultValues = getDefaultValues();
	const form = useForm({defaultValues, mode: 'onChange'});

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
