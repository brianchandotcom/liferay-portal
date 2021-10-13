import React from 'react';
import {Controller} from 'react-hook-form';
import {MoreInfoButton} from '~/common/components/fragments/Buttons/MoreInfo';
import {Switch} from '~/common/components/fragments/Forms/Switch';

export const ControlledSwitch = ({
	name,
	label,
	control,
	rules,
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
				<Switch
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
