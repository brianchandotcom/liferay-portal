import {Formik} from 'formik';

const FormProvider = ({children, initialValues, validate}) => {
	return (
		<Formik
			initialValues={initialValues}
			validate={validate}
			validateOnChange
		>
			{children}
		</Formik>
	);
};

export default FormProvider;
