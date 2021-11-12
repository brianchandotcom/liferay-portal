import ClayForm, {ClayInput} from '@clayui/form';
import {useFormikContext} from 'formik';
import {useContext} from 'react';
import BaseButton from '~/common/components/BaseButton';
import Input from '~/common/components/Input';
import Select from '~/common/components/Select';
import Layout from '../../components/Layout';
import {AppContext} from '../../context';
import {getInitialDxpAdmin, getRoles} from '../../utils/constants';

const AdminInputs = ({id}) => {
	return (
		<ClayForm.Group className="mb-0 pb-1">
			<hr className="mb-4 mt-4 mx-3" />

			<Input
				groupStyle="pt-1"
				label="System Admin’s Email Address"
				name={`admins[${id}].email`}
				placeholder="username@superbank.com"
				required
				type="email"
			/>

			<ClayInput.Group className="mb-0">
				<ClayInput.GroupItem className="m-0">
					<Input
						label="System Admin’s First Name"
						name={`admins[${id}].firstName`}
						required
						type="text"
					/>
				</ClayInput.GroupItem>

				<ClayInput.GroupItem className="m-0">
					<Input
						label="System Admin’s Last Name"
						name={`admins[${id}].lastName`}
						required
						type="text"
					/>
				</ClayInput.GroupItem>
			</ClayInput.Group>

			<Input
				groupStyle="mb-0"
				label="System Admin’s Github Username"
				name={`admins[${id}].github`}
				required
				type="text"
			/>
		</ClayForm.Group>
	);
};

const SetupDXP = () => {
	const [state] = useContext(AppContext);
	const {setFieldValue, values} = useFormikContext();

	return (
		<Layout
			className="pl-3 pt-1"
			footerProps={{
				leftButton: <BaseButton borderless>Skip for now</BaseButton>,
				middleButton: (
					<BaseButton displayType="primary">Submit</BaseButton>
				),
			}}
			headerProps={{
				helper:
					'We’ll need a few details to finish building your DXP environment(s).',
				title: 'Set up DXP Cloud',
			}}
		>
			<div className="d-flex justify-content-between mb-2 pb-1 pl-3">
				<div className="flex-fill">
					<label>Organization Name</label>

					<p className="text-neutral-3 text-paragraph-lg">
						<strong>{state.dxp.organization}</strong>
					</p>
				</div>

				<div className="flex-fill">
					<label>Liferay DXP Version</label>

					<p className="text-neutral-3 text-paragraph-lg">
						<strong>{state.dxp.version}</strong>
					</p>
				</div>
			</div>

			<ClayForm.Group className="mb-0">
				<ClayForm.Group className="mb-0 pb-1">
					<Input
						groupStyle="pb-1"
						helper="Lowercase letters and numbers only. Project IDs cannot be change."
						label="Project ID"
						name="dxp.projectId"
						placeholder="superbank1"
						required
						type="text"
					/>

					<Select
						groupStyle="mb-0"
						label="Primary Data Center Region"
						name="dxp.dataCenterRegion"
						options={getRoles().map(({id, name}) => ({
							label: name,
							value: id,
						}))}
						required
					/>
				</ClayForm.Group>

				{values.dxp.admins.map((admin, index) => (
					<AdminInputs id={index} key={index} />
				))}
			</ClayForm.Group>

			<BaseButton
				borderless
				className="ml-3 my-2 text-brand-primary"
				onClick={() =>
					setFieldValue('dxp.admins', [
						...values.dxp.admins,
						getInitialDxpAdmin(),
					])
				}
				prependIcon="plus"
				small
			>
				Add Another Admin
			</BaseButton>
		</Layout>
	);
};

export default SetupDXP;
