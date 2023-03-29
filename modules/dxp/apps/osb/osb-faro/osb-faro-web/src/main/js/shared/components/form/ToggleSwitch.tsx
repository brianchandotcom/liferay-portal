import React from 'react';
import ToggleSwitch from 'shared/components/ToggleSwitch';
import {FieldProps} from 'formik';

const FormToggleSwitch: React.FC<
	FieldProps & React.HTMLAttributes<HTMLElement>
> = ({field: {value: checked, ...otherFields}, ...fieldProps}) => (
	<ToggleSwitch {...otherFields} {...fieldProps} checked={!!checked} />
);

export default FormToggleSwitch;
