import ClayForm, {ClayInput} from '@clayui/form';
import {useFormikContext} from 'formik';
import {useContext} from 'react';
import BaseButton from '~/shared/components/BaseButton';
import Input from '~/shared/components/Input';
import Select from '~/shared/components/Select';
import {AppContext} from '../context';
import {changeStep} from '../context/actions';
import {getInitialInvite, getRolesList, steps} from '../utils/constants';
import Layout from './layout';

const HorizontalInputs = ({id}) => {
	return (
		<ClayInput.Group>
			<ClayInput.GroupItem className="m-0">
				<Input
					groupStyle="m-0"
					label="Email"
					name={`invites[${id}].email`}
					placeholder="username@superbank.com"
					type="email"
				/>
			</ClayInput.GroupItem>

			<ClayInput.GroupItem className="m-0">
				<Select
					groupStyle="m-0"
					label="Role"
					name={`invites[${id}].roleId`}
					options={getRolesList().map((option) => ({
						label: option.name,
						value: option.id,
					}))}
				/>
			</ClayInput.GroupItem>
		</ClayInput.Group>
	);
};

const Invites = () => {
	const [, dispatch] = useContext(AppContext);
	const {setFieldValue, values} = useFormikContext();

	return (
		<Layout
			footerProps={{
				leftButton: <BaseButton borderless>Skip for now</BaseButton>,
				middleButton: (
					<BaseButton
						displayType="primary"
						onClick={() => dispatch(changeStep(steps.dxp))}
					>
						Send Invitations
					</BaseButton>
				),
			}}
			headerProps={{
				helper:
					'Team members will receive an email invitation to access this project on Customer Portal.',
				title: 'Invite Your Team Members',
			}}
		>
			<div className="invites-form overflow-auto px-3">
				<ClayForm.Group className="m-0">
					{values.invites.map((invite, index) => (
						<HorizontalInputs id={index} key={index} />
					))}
				</ClayForm.Group>

				<BaseButton
					borderless
					className="mb-3 ml-3 mt-2 text-brand-primary"
					onClick={() =>
						setFieldValue('invites', [
							...values.invites,
							getInitialInvite(),
						])
					}
					prependIcon="plus"
					small
				>
					Add More Members
				</BaseButton>
			</div>

			<div className="invites-helper px-3">
				<hr className="mt-0 mx-3" />

				<div className="mx-3">
					<a
						className="btn font-weight-bold p-0 text-link-sm"
						href="https://liferay.com/pt"
						rel="noreferrer"
						target="_blank"
					>
						Learn more about Customer Portal roles
					</a>
				</div>
			</div>
		</Layout>
	);
};

export default Invites;
