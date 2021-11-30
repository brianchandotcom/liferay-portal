import {useMutation, useQuery} from '@apollo/client';
import ClayForm from '@clayui/form';
import {useFormikContext} from 'formik';
import {useContext, useEffect, useState} from 'react';
import BaseButton from '../../../../common/components/BaseButton';
import Input from '../../../../common/components/Input';
import Select from '../../../../common/components/Select';
import {LiferayTheme} from '../../../../common/services/liferay';
import {
	accountSubscription,
	createSetupDXP,
	getDXPCDataCenterRegions,
	getKoroneikiAccounts,
	getUserAccountById,
} from '../../../../common/services/liferay/graphql/queries';
import {API_BASE_URL} from '../../../../common/utils';
import {isValidProjectId} from '../../../../common/utils/validations.form';
import {AppContext} from '../../../../routes/onboarding/context';
import AdminInputs from '../../components/AdminInputs';
import Layout from '../../components/Layout';
import {actionTypes} from '../../context/reducer';
import {getInitialDxpAdmin, steps} from '../../utils/constants';

const SetupDXP = () => {
	const [, dispatch] = useContext(AppContext);
	const {errors, setFieldValue, touched, values} = useFormikContext();
	const [baseButtonDisabled, setBaseButtonDisabled] = useState(true);

	const {data} = useQuery(getDXPCDataCenterRegions);
	const {data: userAccountData} = useQuery(getUserAccountById, {
		variables: {userAccountId: LiferayTheme.getUserId()},
	});

	const accountBriefs = userAccountData?.userAccount?.accountBriefs || [];
	const dXPCDataCenterRegions = data?.c?.dXPCDataCenterRegions?.items;

	const {data: getAccountSubscriptions} =
		useQuery(accountSubscription, {
			variables: {
				filter: accountBriefs
					.map(
						(
							{externalReferenceCode},
							index,
							{length: totalAccountBriefs}
						) =>
							`accountKey eq '${externalReferenceCode}' ${
								index + 1 < totalAccountBriefs ? ' or ' : ' '
							}`
					)
					.join(' '),
			},
		}) || [];

	const hasDisasterRecovery = getAccountSubscriptions?.c?.accountSubscriptions?.items.filter(
		(accountSubscription) =>
			!accountSubscription?.name.includes('HA DR') ||
			!accountSubscription?.name.includes('Std DR')
	);

	const {data: koroneikiAccount} =
		useQuery(getKoroneikiAccounts, {
			variables: {
				filter: accountBriefs
					.map(
						(
							{externalReferenceCode},
							index,
							{length: totalAccountBriefs}
						) =>
							`accountKey eq '${externalReferenceCode}' ${
								index + 1 < totalAccountBriefs ? ' or ' : ' '
							}`
					)
					.join(' '),
			},
		}) || [];

	const projectInfo = koroneikiAccount?.c?.koroneikiAccounts?.items.map(
		({code, dxpVersion}) => ({
			code,
			dxpVersion,
		})
	);

	function handleSkip() {
		window.location.href = `${API_BASE_URL}${LiferayTheme.getLiferaySiteName()}`;
	}

	useEffect(() => {
		const hasTouched = !Object.keys(touched).length;
		const hasError = Object.keys(errors).length;

		setBaseButtonDisabled(hasTouched || hasError);
	}, [touched, errors]);

	const [sendEmailData, {called}] = useMutation(createSetupDXP) || [];

	function sendEmail() {
		const dxp = values?.dxp || {};

		if (!called) {
			sendEmailData({
				variables: {
					SetupDXP: {
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
		}

		dispatch({
			payload: steps.success,
			type: actionTypes.CHANGE_STEP,
		});
	}

	return (
		<Layout
			className="pl-3 pt-1"
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
						onClick={() => {
							sendEmail();
						}}
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

					<p className="text-neutral-3 text-paragraph-lg">
						<strong>
							{projectInfo?.length ? projectInfo[0].code : ''}
						</strong>
					</p>
				</div>

				<div className="flex-fill">
					<label>Liferay DXP Version</label>

					<p className="text-neutral-3 text-paragraph-lg">
						<strong>
							{projectInfo?.length
								? projectInfo[0].dxpVersion
								: ''}
						</strong>
					</p>
				</div>
			</div>

			<ClayForm.Group className="mb-0">
				<ClayForm.Group className="mb-0 pb-1">
					<Input
						groupStyle="pb-1"
						helper={
							!errors?.dxp?.projectId &&
							'Lowercase letters and numbers only. Project IDs cannot be change.'
						}
						label="Project ID"
						name="dxp.projectId"
						placeholder="superbank1"
						required
						type="text"
						validations={[(value) => isValidProjectId(value)]}
					/>

					<Select
						groupStyle="mb-0"
						label="Primary Data Center Region"
						name="dxp.dataCenterRegion"
						options={dXPCDataCenterRegions?.map(
							({dxpcDataCenterRegionId, name}) => ({
								label: name,
								value: dxpcDataCenterRegionId,
							})
						)}
						required
					/>

					{hasDisasterRecovery?.length && (
						<Select
							groupStyle="mb-0 pt-2"
							label="Disaster Recovery Data Center Region"
							name="dxp.disasterDataCenterRegion"
							options={dXPCDataCenterRegions?.map(
								({dxpcDataCenterRegionId, name}) => ({
									label: name,
									value: dxpcDataCenterRegionId,
								})
							)}
							required
						/>
					)}
				</ClayForm.Group>

				{values.dxp.admins.map((admin, index) => (
					<AdminInputs id={index} key={index} value={admin} />
				))}
			</ClayForm.Group>

			<BaseButton
				borderless
				className="ml-3 my-2 text-brand-primary"
				eslint-disable-next-line
				lines-around-comment
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

export default SetupDXP;
