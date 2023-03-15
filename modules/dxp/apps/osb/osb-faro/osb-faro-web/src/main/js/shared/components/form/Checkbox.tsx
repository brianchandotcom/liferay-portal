import Checkbox from 'shared/components/Checkbox';
import React from 'react';
import {FieldProps} from 'formik';

const FormCheckbox: React.FC<FieldProps> = ({
	field: {value: checked, ...otherFields},
	...fieldProps
}) => <Checkbox {...otherFields} {...fieldProps} checked={!!checked} />;

export default FormCheckbox;
