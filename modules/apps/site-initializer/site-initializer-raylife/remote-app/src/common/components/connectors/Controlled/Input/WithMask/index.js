import classNames from 'classnames';
import React from 'react';
import {Controller} from 'react-hook-form';
import {MoreInfoButton} from '~/common/components/fragments/Buttons/MoreInfo';
import {InputWithMask} from '~/common/components/fragments/Forms/Input/WithMask';

export const ControlledInputWithMask = ({
	name,
	label,
	rules,
	control,
	moreInfoProps = undefined,
	inputProps = {},
	renderInput = true,
	...props
}) => {
	const newRules = renderInput ? rules : {required: false};

	return (
		<Controller
			control={control}
			defaultValue=""
			name={name}
			render={({field, fieldState}) => (
				<InputWithMask
					className={classNames({
						'd-none': !renderInput,
					})}
					error={fieldState.error}
					label={label}
					renderActions={
						moreInfoProps && <MoreInfoButton {...moreInfoProps} />
					}
					required={newRules?.required}
					{...field}
					{...inputProps}
				/>
			)}
			rules={newRules}
			{...props}
		/>
	);
};
