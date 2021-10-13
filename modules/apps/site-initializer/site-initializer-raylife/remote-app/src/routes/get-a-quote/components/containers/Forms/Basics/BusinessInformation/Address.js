/* eslint-disable react-hooks/exhaustive-deps */
import React, {useEffect, useRef} from 'react';
import {useFormContext} from 'react-hook-form';
import {ControlledInput} from '~/common/components/connectors/Controlled/Input';
import {ZIPControlledInput} from '~/common/components/connectors/Controlled/Input/WithMask/ZIP';
import {StatesControlledSelect} from '~/common/components/connectors/Controlled/Select/States';
import {Input} from '~/common/components/fragments/Forms/Input';
import {useLocation} from '~/routes/get-a-quote/hooks/useLocation';

const setFormPath = (value) =>
	`basics.businessInformation.business.location.${value}`;

export const BusinessInformationAddress = () => {
	const ref = useRef();
	const {setAutoComplete} = useLocation();
	const {control, register, setValue} = useFormContext();

	useEffect(() => {
		if (ref.current) {
			setAutoComplete(ref.current, updateFormWithGoogleAddress);
		}
	}, [ref]);

	const updateFormWithGoogleAddress = (address) => {
		// We need to put shouldValidate at least in one Field
		// to force validation to others

		setValue(setFormPath('city'), address.city, {shouldValidate: true});
		setValue(setFormPath('state'), address.state);
		setValue(setFormPath('zip'), address.zip);
		setValue(
			setFormPath('address'),
			`${address.streetNumber} ${address.street}`
		);
	};

	return (
		<>
			<div
				className="content-row"
				style={{
					display: 'grid',
					gridTemplateColumns: '1fr 29.7%',
				}}
			>
				<ControlledInput
					control={control}
					inputProps={{
						placeholder: 'Street address',
						ref,
					}}
					label="Physical Business Address"
					name={setFormPath('address')}
					rules={{required: 'Business address is required.'}}
				/>

				<Input
					{...register(setFormPath('addressApt'))}
					label="&nbsp;"
					placeholder="Apt/Suite (optional)"
				/>

				<div
					className="content-row"
					style={{
						display: 'grid',
						gridTemplateColumns: '1fr 25.2%',
					}}
				>
					<ControlledInput
						control={control}
						label="City"
						name={setFormPath('city')}
						rules={{required: 'City is required.'}}
					/>

					<StatesControlledSelect
						control={control}
						label="State"
						name={setFormPath('state')}
						rules={{
							required: 'This field is required.',
						}}
					/>
				</div>

				<div className="content-row">
					<ZIPControlledInput
						control={control}
						label="ZIP"
						name={setFormPath('zip')}
						rules={{
							required: 'ZIP is required.',
						}}
					/>
				</div>
			</div>
		</>
	);
};
