import React from 'react';
import {Controller} from 'react-hook-form';

import {MoreInfoButton} from '../../../../fragments/Buttons/MoreInfo';
import {InputWithMask} from '../../../../fragments/Forms/Input/WithMask';

export const ControlledInputWithMask = ({
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
				<InputWithMask
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
