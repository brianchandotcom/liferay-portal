import React from 'react';
import {Controller} from 'react-hook-form';
import {MoreInfoButton} from '~/common/components/fragments/Buttons/MoreInfo';
import {Input} from '~/common/components/fragments/Forms/Input';

export const ControlledInput = ({
	name,
	label,
	rules,
	control,
	moreInfoProps = undefined,
	inputProps = {},
	...props
}) => {
	return (
		<Controller
			control={control}
			defaultValue=""
			name={name}
			render={({field, fieldState}) => (
				<Input
					{...field}
					error={fieldState.error}
					label={label}
					renderActions={
						moreInfoProps && <MoreInfoButton {...moreInfoProps} />
					}
					required={rules?.required}
					{...inputProps}
				/>
			)}
			rules={rules}
			{...props}
		/>
	);
};
