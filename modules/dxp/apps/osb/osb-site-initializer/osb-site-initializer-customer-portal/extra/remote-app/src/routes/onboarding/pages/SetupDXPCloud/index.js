import {useMutation, useQuery} from '@apollo/client';
import ClayForm from '@clayui/form';
import {useFormikContext} from 'formik';
import {useEffect, useMemo, useState} from 'react';
import BaseButton from '../../../../common/components/BaseButton';
import Input from '../../../../common/components/Input';
import Select from '../../../../common/components/Select';
import {LiferayTheme} from '../../../../common/services/liferay';
import {
	addSetupDXPCloud,
	getSetupDXPCloudInfo,
} from '../../../../common/services/liferay/graphql/queries';
import {PARAMS_KEYS} from '../../../../common/services/liferay/search-params';
import {API_BASE_URL} from '../../../../common/utils';
import {isLowercaseAndNumbers} from '../../../../common/utils/validations.form';
import {useOnboarding} from '../../../../routes/onboarding/context';
import AdminInputs from '../../components/AdminInputs';
import Layout from '../../components/Layout';
import {actionTypes} from '../../context/reducer';
import {getInitialDxpAdmin, steps} from '../../utils/constants';

const SetupDXPCloud = () => {
	const [{project}, dispatch] = useOnboarding();
	const {errors, setFieldValue, touched, values} = useFormikContext();
	const [baseButtonDisabled, setBaseButtonDisabled] = useState(true);

	const {data} = useQuery(getSetupDXPCloudInfo, {
		variables: {
			accountSubscriptionsFilter: `(accountKey eq '${project.accountKey}') and (hasDisasterDataCenterRegion eq true)`,
		},
	});

	const dXPCDataCenterRegions = useMemo(
		() =>
			data?.c?.dXPCDataCenterRegions?.items.map(
				({dxpcDataCenterRegionId, name}) => ({
					label: name,
					value: dxpcDataCenterRegionId,
				})
			) || [],
		[data]
	);

	const hasDisasterRecovery = !!data?.c?.accountSubscriptions?.items?.length;
	const projectBrief = {
		code: project.code,
		dxpVersion: project.dxpVersion,
	};

	useEffect(() => {
		if (dXPCDataCenterRegions.length) {
			setFieldValue('dxp.dataCenterRegion', dXPCDataCenterRegions[0]);

			if (hasDisasterRecovery) {
				setFieldValue(
					'dxp.disasterDataCenterRegion',
					dXPCDataCenterRegions[0]
				);
			}
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [dXPCDataCenterRegions, hasDisasterRecovery]);

	const handleSkip = () => {
		window.location.href = `${API_BASE_URL}/${LiferayTheme.getLiferaySiteName()}/overview?${
			PARAMS_KEYS.PROJECT_APPLICATION_EXTERNAL_REFERENCE_CODE
		}=${project.accountKey}`;
	};

	useEffect(() => {
		const hasTouched = !Object.keys(touched).length;
		const hasError = Object.keys(errors).length;

		setBaseButtonDisabled(hasTouched || hasError);
	}, [touched, errors]);

	const [sendEmailData, {called, error}] = useMutation(addSetupDXPCloud);

	const sendEmail = () => {
		const dxp = values?.dxp;

		if (!called && dxp) {
			sendEmailData({
				variables: {
					SetupDXPCloud: {
						admins: JSON.stringify(dxp.admins),
						dataCenterRegion: JSON.stringify(dxp.dataCenterRegion),
						disasterDataCenterRegion: JSON.stringify(
							dxp.disasterDataCenterRegion
						),
						projectId: JSON.stringify(dxp.projectId),
					},
					scopeKey: LiferayTheme.getScopeGroupId(),
				},
			});

			if (!error) {
				dispatch({
					payload: steps.successDxpCloud,
					type: actionTypes.CHANGE_STEP,
				});
			}
		}
	};

	return (
		<Layout
			className="pt-1 px-3"
			footerProps={{
				leftButton: (
					<BaseButton borderless onClick={handleSkip}>
						Skip for now
					</BaseButton>
				),
				middleButton: (
					<BaseButton
						disabled={baseButtonDisabled}
						displayType="primary"
						onClick={() => sendEmail()}
					>
						Submit
					</BaseButton>
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
					<label>Project Name</label>

					<p className="text-neutral-6 text-paragraph-lg">
						<strong>{projectBrief ? projectBrief.code : ''}</strong>
					</p>
				</div>

				<div className="flex-fill">
					<label>Liferay DXP Version</label>

					<p className="text-neutral-6 text-paragraph-lg">
						<strong>
							{projectBrief ? projectBrief.dxpVersion : ''}
						</strong>
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
						required
						type="text"
						validations={[(value) => isLowercaseAndNumbers(value)]}
					/>

					<Select
						groupStyle="mb-0"
						label="Primary Data Center Region"
						name="dxp.dataCenterRegion"
						options={dXPCDataCenterRegions}
						required
					/>

					{!!hasDisasterRecovery && (
						<Select
							groupStyle="mb-0 pt-2"
							label="Disaster Recovery Data Center Region"
							name="dxp.disasterDataCenterRegion"
							options={dXPCDataCenterRegions}
							required
						/>
					)}
				</ClayForm.Group>

				{values.dxp.admins.map((admin, index) => (
					<AdminInputs admin={admin} id={index} key={index} />
				))}
			</ClayForm.Group>

			<BaseButton
				borderless
				className="ml-3 my-2 text-brand-primary"
				onClick={() => {
					setFieldValue('dxp.admins', [
						...values.dxp.admins,
						getInitialDxpAdmin(),
					]);
					setBaseButtonDisabled(true);
				}}
				prependIcon="plus"
				small
			>
				Add Another Admin
			</BaseButton>
		</Layout>
	);
};

export default SetupDXPCloud;
