import { Formik } from "formik";

const FormProvider = ({ children, initialValues, validate }) => {
    return (
        <Formik initialValues={initialValues} validate={validate}>
            {children}
        </Formik>
    );
};

export default FormProvider;