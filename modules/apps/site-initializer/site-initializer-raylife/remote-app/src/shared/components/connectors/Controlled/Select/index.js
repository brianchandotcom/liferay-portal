import React from 'react';
import {Controller} from 'react-hook-form';
import {MoreInfoButton} from '~/shared/components/fragments/Buttons/MoreInfo';
import {Select} from '~/shared/components/fragments/Forms/Select';

export const ControlledSelect = ({
	name,
	label,
	control,
	rules,
	children,
	moreInfoProps = undefined,
	inputProps = {},
	defaultValue = '',
	...props
}) => {
	return (
		<Controller
			control={control}
			defaultValue={defaultValue}
			name={name}
			render={({field, fieldState}) => (
				<Select
					{...field}
					error={fieldState.error}
					label={label}
					renderActions={
						moreInfoProps && <MoreInfoButton {...moreInfoProps} />
					}
					required={rules?.required}
					{...inputProps}
				>
					{children}
				</Select>
			)}
			rules={rules}
			{...props}
		/>
	);
};
